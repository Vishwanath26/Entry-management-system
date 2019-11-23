package com.innovaccer.entryManager.Domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "visitor")
public class Visitor {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private Long visitorId = 0L;

    @Size(max=10)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name="email_id")
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
