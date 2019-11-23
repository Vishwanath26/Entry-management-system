package com.innovaccer.entryManager.Domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Entity
@Table(name = "host")
public class Host {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="host_id", nullable=false)
    private Long hostId = 0L;

    @Size(max=10)
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;

    @Email
    @Column(name="email_id",unique = true)
    private String emailId;

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
}
