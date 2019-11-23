package com.innovaccer.entryManager.Domain;


import javax.persistence.*;

@Entity
@Table(name = "visitor_host_meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="vhm_id")
    private Long vhmId = 0L;

    @Column(name="visitor_id")
    private Long visitorId;

    @Column(name="host_id")
    private Long hostId;

    @Column(name="checkin_time")
    private String checkinTime;

    @Column(name="checkout_time")
    private String checkoutTime;

    public Long getVhmId() {
        return vhmId;
    }

    public Long getVisitorId() {
        return visitorId;
    }

    public Long getHostId() {
        return hostId;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public Meeting(){}

    public Meeting(Long visitorId, Long hostId, String checkinTime, String checkoutTime) {
        this.visitorId = visitorId;
        this.hostId = hostId;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
    }
}
