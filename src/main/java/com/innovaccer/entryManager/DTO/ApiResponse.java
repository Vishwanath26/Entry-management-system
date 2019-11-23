package com.innovaccer.entryManager.DTO;

public class ApiResponse {

    private String responseMessage;
    private String responseType;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public ApiResponse(String responseMessage, String responseType) {
        this.responseMessage = responseMessage;
        this.responseType = responseType;
    }
}
