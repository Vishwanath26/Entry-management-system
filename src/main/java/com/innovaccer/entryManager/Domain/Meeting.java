package com.innovaccer.entryManager.Domain;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "visitor_host_meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="vhm_id", nullable=false)
    private Long vhmId = 0L;

    @Column(name="visitor_id")
    private Long visitorId;

    @Column(name="host_id")
    private Long hostId;

    @Column(name="checkin_time")
    private DateTime checkinTime;

    @Column(name="checkout_time")
    private DateTime checkoutTime;

    public Long getVhmId() {
        return vhmId;
    }

    public Long getVisitorId() {
        return visitorId;
    }

    public Long getHostId() {
        return hostId;
    }

    public DateTime getCheckinTime() {
        return checkinTime;
    }

    public DateTime getCheckoutTime() {
        return checkoutTime;
    }
}
