package com.innovaccer.entryManager.Domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "visitor")
public class Visitor {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="visitor_id")
    private Long visitorId = 0L;

    @Size(max=10)
    @Column(name = "phone_number" ,unique = true)
    private String phoneNumber;

    @Email
    @Column(name="email_id",unique = true)
    private String emailId;

    @Column(name="name")
    private String visitorName;

    public Long getVisitorId() {
        return visitorId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public Visitor(){

    }

    public Visitor( Long visitorId,String phoneNumber, @Email String emailId, @Email String visitorName) {
        this.visitorId = visitorId;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.visitorName = visitorName;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "visitorId=" + visitorId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", visitorName='" + visitorName + '\'' +
                '}';
    }
}
