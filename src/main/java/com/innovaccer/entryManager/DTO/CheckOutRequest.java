package com.innovaccer.entryManager.DTO;

public class CheckOutRequest {

    private VisitorDto visitorDto;

    public VisitorDto getVisitorDto() {
        return visitorDto;
    }

    public void setVisitorDto(VisitorDto visitorDto) {
        this.visitorDto = visitorDto;
    }

    @Override
    public String toString() {
        return "CheckOutMeeting{" +
                "visitorDto=" + visitorDto +
                '}';
    }
}
