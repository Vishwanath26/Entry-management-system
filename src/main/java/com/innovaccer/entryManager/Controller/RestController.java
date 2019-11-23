package com.innovaccer.entryManager.Controller;

import com.innovaccer.entryManager.DTO.ApiResponse;
import com.innovaccer.entryManager.DTO.MeetingRequest;
import com.innovaccer.entryManager.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    MeetingService meetingService;

    @PostMapping("/meeting/set")
    public ResponseEntity<ApiResponse> setVisitorMeeting(@RequestBody MeetingRequest meetingRequest)
    {
       ApiResponse apiResponse = meetingService.setMeetingForVisitor(meetingRequest);
       if(apiResponse.getResponseType().equals("failed"))
       {
           return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
       }
       else
       {
           return new ResponseEntity<>(apiResponse, HttpStatus.OK);
       }
    }
}
