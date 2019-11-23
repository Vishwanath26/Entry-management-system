package com.innovaccer.entryManager.DTO;

public class VisitorDto {

    private String visitorName;
    private String visitorEmailId;
    private String visitorPhoneNum;

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorEmailId() {
        return visitorEmailId;
    }

    public void setVisitorEmailId(String visitorEmailId) {
        this.visitorEmailId = visitorEmailId;
    }

    public String getVisitorPhoneNum() {
        return visitorPhoneNum;
    }

    public void setVisitorPhoneNum(String visitorPhoneNum) {
        this.visitorPhoneNum = visitorPhoneNum;
    }

    @Override
    public String toString() {
        return "VisitorDto{" +
                "visitorName='" + visitorName + '\'' +
                ", visitorEmailId='" + visitorEmailId + '\'' +
                ", visitorPhoneNum='" + visitorPhoneNum + '\'' +
                '}';
    }
}
