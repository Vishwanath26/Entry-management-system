package com.innovaccer.entryManager.Service;

import com.innovaccer.entryManager.DTO.EmailTemplate;
import com.innovaccer.entryManager.Domain.Email;
import com.innovaccer.entryManager.Domain.Host;
import com.innovaccer.entryManager.Domain.Meeting;
import com.innovaccer.entryManager.Domain.Visitor;
import com.innovaccer.entryManager.Repository.HostRepository;
import com.innovaccer.entryManager.Repository.MeetingRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EnvironmentAware {

    private static String gmailSenderEmailId;
    private static String gmailSenderPassword;
    private Environment environment;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    HostRepository hostRepository;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.gmailSenderEmailId = environment.getProperty("gmailSenderEmail");
        this.gmailSenderPassword = environment.getProperty("gmailSenderPassword");
    }

    public boolean sendEmail(Email emailInfo) {

        boolean response = false;

        Properties props = getSMTPProperties();
        Session session = getSessionInfo(props);
        String emailContent = getEmailTemplate(emailInfo.getTemplate());
        emailContent = updateEmailData(emailContent, emailInfo.getVisitor(), emailInfo.getActivityType());
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(gmailSenderEmailId));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailInfo.getReciepent()));
            message.setSubject(emailInfo.getSubject());
            message.setContent(emailContent, "text/html");

            Transport.send(message);
            System.out.println("send success");
        } catch (MessagingException mexp) {
            mexp.printStackTrace();
        }
        return response;
    }

    public Properties getSMTPProperties() {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        return props;
    }

    public Session getSessionInfo(Properties props) {
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gmailSenderEmailId, gmailSenderPassword);
            }
        });
        return session;
    }

    public String getEmailTemplate(String template) {
        String userDirPath = System.getProperty("user.dir");
        File file = new File( userDirPath +"/src/main/java/com/innovaccer/entryManager/DTO/EmailTemplates/" +  getTemplateType(template));
        String html_trimmed = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader(file));
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                String tmp = s.trim();
                html_trimmed = html_trimmed.concat(tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        return html_trimmed;
    }

    private String getTemplateType(String template) {
        String response = "";
        if (template != null && !template.isEmpty())
            switch (template) {
                case EmailTemplate.HOST_INVITATION_TEMPLATE:
                    response = EmailTemplate.HOST_INVITATION_TEMPLATE;
                    break;
                case EmailTemplate.VISITOR_CHECKOUT_TEMPLATE:
                    response = EmailTemplate.VISITOR_CHECKOUT_TEMPLATE;
            }
        return response;

    }

    private String handleDateTime(String datetime) {
        LocalDate date = null;
        LocalTime time = null;
        try {
            date = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").parseDateTime(datetime).toLocalDate();
            time = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").parseDateTime(datetime).toLocalTime().withMillisOfSecond(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.toString() + " " + time.toString();
    }

    private String updateEmailData(String emailTemplate, Visitor visitor, String activityType) {
        if (activityType == "checkin") {
            emailTemplate = emailTemplate.replace("{meetingPerson}", visitor.getVisitorName() + "<br></br>");
            emailTemplate = emailTemplate.replace("{meetingEmail}", visitor.getEmailId() + "<br></br>");
            emailTemplate = emailTemplate.replace("{meetingNumber}", visitor.getPhoneNumber() + "<br></br>");
        }
        if (activityType == "checkout") {
            Meeting meeting = meetingRepository.getAllDetails(visitor.getVisitorId());
            System.out.println(meeting);
            emailTemplate = emailTemplate.replace("{visitorName}", visitor.getVisitorName() + "<br></br>");
            emailTemplate = emailTemplate.replace("{visitorMobile}", visitor.getPhoneNumber() + "<br></br>");
            emailTemplate = emailTemplate.replace("{checkInTime}", handleDateTime(meeting.getCheckinTime()) + "<br></br>");
            emailTemplate = emailTemplate.replace("{checkOutTime}", handleDateTime(meeting.getCheckoutTime()) + "<br></br>");
            emailTemplate = emailTemplate.replace("{hostName}", hostRepository.getHostByHostId(meeting.getHostId()).getHostName() + "<br></br>");
        }
        return emailTemplate;
    }
}
