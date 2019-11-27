package com.innovaccer.entryManager.Service;


import com.innovaccer.entryManager.DTO.*;
import com.innovaccer.entryManager.Domain.Email;
import com.innovaccer.entryManager.Domain.Host;
import com.innovaccer.entryManager.Domain.Meeting;
import com.innovaccer.entryManager.Domain.Visitor;
import com.innovaccer.entryManager.Repository.HostRepository;
import com.innovaccer.entryManager.Repository.MeetingRepository;
import com.innovaccer.entryManager.Repository.VisitorRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CheckOutService {

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

    public ApiResponse checkOutMeeting(CheckOutRequest checkOutRequest) {
        if (checkOutRequest == null || checkOutRequest.getVisitorDto() == null) {
            logger.info("either meetingRequest or visitor is null");
            return new ApiResponse("Improper data found make sure all values are filled", "failed");
        }

        String visitorValidationMessage = getVisitorValidationMsg(checkOutRequest.getVisitorDto());
        //in-valid visitor
        if (!StringUtils.isEmpty(visitorValidationMessage)) {
            return new ApiResponse("Visitor " + visitorValidationMessage, "failed");
        }

        //check for active check-in of current visitor
        boolean isCurrentVisitorAlreadyCheckedIn = isCurrentVisitorAlreadyCheckedIn(checkOutRequest.getVisitorDto());
        if (isCurrentVisitorAlreadyCheckedIn) {
            //Main checkout call
            checkOut(checkOutRequest);

            //send email in different thread
            new Thread(() -> {
                sendEmailToVisitor(checkOutRequest);
            }).start();
            logger.info("mail thread initiated");
            return new ApiResponse("Visitor checked-out with email " + checkOutRequest.getVisitorDto().getVisitorEmailId(), "passed");
        } else {
            return new ApiResponse("No visitor has active check-in with email " + checkOutRequest.getVisitorDto().getVisitorEmailId() + "First Check-In", "failed");
        }

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

    private boolean isCurrentVisitorAlreadyCheckedIn(VisitorDto visitorDto) {
        Visitor visitor = visitorRepository.getVisitorByEmailId(visitorDto.getVisitorEmailId());
        if (visitor != null && visitor.getVisitorId() != null) {
            int meetingRepositoryActiveCheckedInCnt = meetingRepository.getActiveCheckedInCnt(visitor.getVisitorId());
            return meetingRepositoryActiveCheckedInCnt > 0;
        }
        return false;
    }

    private void checkOut(CheckOutRequest checkOutRequest) {
        //updates checkout time in meeting table
        Visitor visitor = visitorRepository.getVisitorByEmailId(checkOutRequest.getVisitorDto().getVisitorEmailId());
        meetingRepository.updateCheckoutTime(visitor.getVisitorId(), DateTime.now().toString());
    }

    private void sendEmailToVisitor(CheckOutRequest checkOutRequest) {
        //send email regarding meeting details, host details to checked out visitor
        Visitor visitor = visitorRepository.getVisitorByEmailId(checkOutRequest.getVisitorDto().getVisitorEmailId());
        Meeting meeting = meetingRepository.getAllDetails(visitor.getVisitorId());
        //emailService.sendEmail(new Email("Information regarding visit details", visitor.getEmailId(), EmailTemplate.VISITOR_CHECKOUT_TEMPLATE, visitor, "checkout"));
        meetingRepository.deleteMeeting(visitor.getVisitorId());
        visitorRepository.deleteVisitor(visitor.getVisitorId());
    }

}
