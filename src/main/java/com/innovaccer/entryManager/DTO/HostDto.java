package com.innovaccer.entryManager.DTO;

public class HostDto {

    private String hostName;
    private String hostEmailId;
    private String hostPhoneNumber;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostEmailId() {
        return hostEmailId;
    }

    public void setHostEmailId(String hostEmailId) {
        this.hostEmailId = hostEmailId;
    }

    public String getHostPhoneNumber() {
        return hostPhoneNumber;
    }

    public void setHostPhoneNumber(String hostPhoneNumber) {
        this.hostPhoneNumber = hostPhoneNumber;
    }

    @Override
    public String toString() {
        return "HostDto{" +
                "hostName='" + hostName + '\'' +
                ", hostEmailId='" + hostEmailId + '\'' +
                ", hostPhoneNumber='" + hostPhoneNumber + '\'' +
                '}';
    }
}
