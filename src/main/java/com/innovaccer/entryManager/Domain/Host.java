package com.innovaccer.entryManager.Domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Entity
@Table(name = "host")
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "host_id")
    private Long hostId = 0L;

    @Size(max = 10)
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Email
    @Column(name = "email_id", unique = true)
    private String emailId;

    @Column(name = "name")
    private String hostName;

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getHostName() {
        return hostName;
    }

    public Host(){

    }
    public Host(@Size(max = 10) String phoneNumber, @Email String emailId, String hostName) {
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.hostName = hostName;
    }
}
