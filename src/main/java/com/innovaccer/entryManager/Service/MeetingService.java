package com.innovaccer.entryManager.Service;

import com.innovaccer.entryManager.DTO.*;
import com.innovaccer.entryManager.Domain.Email;
import com.innovaccer.entryManager.Domain.Host;
import com.innovaccer.entryManager.Domain.Meeting;
import com.innovaccer.entryManager.Domain.Visitor;
import com.innovaccer.entryManager.Repository.HostRepository;
import com.innovaccer.entryManager.Repository.MeetingRepository;
import com.innovaccer.entryManager.Repository.VisitorRepository;
import com.innovaccer.entryManager.Service.MessageService.SmsService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MeetingService {

    @Autowired
    VisitorRepository visitorRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    HostRepository hostRepository;

    @Autowired
    SendSmsService sendSmsService;

    @Autowired
    EmailService emailService;


    private Logger logger = LoggerFactory.getLogger(MeetingService.class.getName());

    public ApiResponse setMeetingForVisitor(MeetingRequest meetingRequest) {

        if (meetingRequest == null || meetingRequest.getHostDto() == null || meetingRequest.getVisitorDto() == null) {
            logger.info("either meetingRequest or host or visitor is null");
            return new ApiResponse("Improper data found make sure all values are filled", "failed");
        }

        String visitorValidationMessage = getVisitorValidationMsg(meetingRequest.getVisitorDto());
        //in-valid visitor
        if (!StringUtils.isEmpty(visitorValidationMessage)) {
            return new ApiResponse("Visitor " + visitorValidationMessage, "failed");
        }

        String hostValidationMsg = getHostValidationMsg(meetingRequest.getHostDto());
        //in-valid host
        if (!StringUtils.isEmpty(hostValidationMsg)) {
            return new ApiResponse("Host " + hostValidationMsg, "failed");
        }

        //check for active check-in of current visitor
        boolean isCurrentVisitorAlreadyCheckedIn = isCurrentVisitorAlreadyCheckedIn(meetingRequest.getVisitorDto());
        if (isCurrentVisitorAlreadyCheckedIn) {
            return new ApiResponse("Visitor already checked-in with email " + meetingRequest.getVisitorDto().getVisitorEmailId() + " please checkout first ", "failed");
        }

        //save the meeting
        saveMeeting(meetingRequest);

        //send email and sms in different thread
        new Thread(()->{
            sendSmsAndMailToHost(meetingRequest);
        }).start();
        logger.info("mail thread initiated");
        return new ApiResponse("Meeting scheduled", "passed");
    }

    private String getVisitorValidationMsg(VisitorDto visitorDto) {
        if (StringUtils.isEmpty(visitorDto.getVisitorEmailId())) {
            return "emailId cannot be null or empty";
        }

        if (StringUtils.isEmpty(visitorDto.getVisitorPhoneNum())) {
            return "phoneNumber cannot be null or empty";
        }

        if (StringUtils.isEmpty(visitorDto.getVisitorName())) {
            return "name cannot be null or empty";
        }
        return null;
    }

    private String getHostValidationMsg(HostDto hostDto) {
        if (StringUtils.isEmpty(hostDto.getHostEmailId())) {
            return "emailId cannot be null or empty";
        }

        if (StringUtils.isEmpty(hostDto.getHostPhoneNumber())) {
            return "phoneNumber cannot be null or empty";
        }

        if (StringUtils.isEmpty(hostDto.getHostName())) {
            return "name cannot be null or empty";
        }
        return null;
    }

    private boolean isCurrentVisitorAlreadyCheckedIn(VisitorDto visitorDto) {
        Visitor visitor = visitorRepository.getVisitorByEmailId(visitorDto.getVisitorEmailId());
        if (visitor != null && visitor.getVisitorId() != null) {
            int meetingRepositoryActiveCheckedInCnt = meetingRepository.getActiveCheckedInCnt(visitor.getVisitorId());
            return meetingRepositoryActiveCheckedInCnt > 0;
        }
        return false;
    }

    private void saveMeeting(MeetingRequest meetingRequest) {
        //save visitor
        Visitor visitor = new Visitor(0L, meetingRequest.getVisitorDto().getVisitorPhoneNum().trim(), meetingRequest.getVisitorDto().getVisitorEmailId().trim(), meetingRequest.getVisitorDto().getVisitorName().trim());
        visitor = visitorRepository.save(visitor);
        Host host;
        if (hostRepository.checkActiveHostByEmail(meetingRequest.getHostDto().getHostEmailId()) <= 0) {
            host = new Host(meetingRequest.getHostDto().getHostPhoneNumber().trim(), meetingRequest.getHostDto().getHostEmailId().trim(), meetingRequest.getHostDto().getHostName().trim());
            host = hostRepository.save(host);
        } else {
            host = hostRepository.getHostByEmailId(meetingRequest.getHostDto().getHostEmailId());
        }
        Meeting meeting = new Meeting(visitor.getVisitorId(), host.getHostId(), DateTime.now().toString(), null);
        meetingRepository.save(meeting);
    }


    public void sendSmsAndMailToHost(MeetingRequest meetingRequest) {
        //send sms
        Host host = hostRepository.getHostByEmailId(meetingRequest.getHostDto().getHostEmailId());
        Visitor visitor = visitorRepository.getVisitorByEmailId(meetingRequest.getVisitorDto().getVisitorEmailId());
        String messageToHost = "Hi " + host.getHostName() + " your meeting is scheduled with " + visitor.getVisitorName() + " having email " + visitor.getEmailId() + " and phone number " + visitor.getPhoneNumber();
        //logger.info(sendSmsService.sendSms(host.getPhoneNumber(), messageToHost));
        //emailService.sendEmail(new Email("Information regarding meeting scheduled", host.getEmailId(), EmailTemplate.HOST_INVITATION_TEMPLATE, visitor, "checkin"));
        logger.info("email and sms sent");
    }
}
