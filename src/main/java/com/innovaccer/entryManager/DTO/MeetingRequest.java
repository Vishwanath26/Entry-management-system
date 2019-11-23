package com.innovaccer.entryManager.DTO;

public class MeetingRequest {

    private VisitorDto visitorDto;
    private HostDto hostDto;

    public VisitorDto getVisitorDto() {
        return visitorDto;
    }

    public void setVisitorDto(VisitorDto visitorDto) {
        this.visitorDto = visitorDto;
    }

    public HostDto getHostDto() {
        return hostDto;
    }

    public void setHostDto(HostDto hostDto) {
        this.hostDto = hostDto;
    }

    @Override
    public String toString() {
        return "MeetingRequest{" +
                "visitorDto=" + visitorDto +
                ", hostDto=" + hostDto +
                '}';
    }
}
