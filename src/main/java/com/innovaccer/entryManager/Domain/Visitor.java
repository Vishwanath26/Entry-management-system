package com.innovaccer.entryManager.Domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "visitor")
public class Visitor {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="visitor_id", nullable=false)
    private Long visitorId = 0L;

    @Size(max=10)
    @Column(name = "phone_number" ,unique = true)
    private String phoneNumber;

    @Email
    @Column(name="email_id",unique = true)
    private String emailId;


    public Long getVisitorId() {
        return visitorId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }
}
