# Entry-management-system
This is an entry management web application made using Java Spring-Boot, Postgre-SQL and ReactJS in frontend.

# Problem-statement
Given the visitors that we have in office and outside, there is a need for an entry management
software, which takes visitor's and his host's details(Name, Email and Phone number) and sends 
mail and message to host regarding meeting scheduled on check-in and send all visit details to 
visitor at checkout.

# Setup
Any Java IDE will work fine with with Postgre-SQL and React JS (npm version >= 6.13).
User should have a basic internet connectivity as sms and email service require that.

# Approach
   # Frontend
   Entire Frontend is built on ReactJS which has 3 componenents - 
   1 - App Component - It contains homepage with check-in and checkout buttons.
   2 - Form Component - It contains the form details to be filled at check-in and checkout.
   3 - Alert Component - It displays the alert on success/failure of check-in/checkout
   
   # Backend
   Backend is made using Java Spring Boot framework(with JPA)
   
   1 - Controller - There is one single RESTController
   
   2 - Domain - There are 4 domains for Email, Host, Visitor and Meeting.
   
   3 - DTO's(Data Transfer Objects) - 
   
        a) - There is an Email template DTO with beautiful HTML templates for emails.
        
        b) - There are other DTO's for ApiResponse, MeetingRequest, CheckOutRequest, HostDTO and VisitorDTO.
        
   4 - Repository - There are 3 repositories for interaction with database - 
   
        a) - VisitorRepository   b) - MeetingRepository   c) - HostRepository
        
   5 - Service  - This is the main part of the project, we have following services - 
   
        a) - MeetingService - this service sets the meeting for visitor where first visitor is checked in meeting
             table is checked for making sure no duplicate check-in before checkout.
             It then saves the visitor details, host details(if not found in hostDB) and meeting details and then
             trigger email and message to host about meeting in seperate thread.
             
        b) - EmailService - Gmail SMTP service is used for sending email added with beautiful HTML templates.
        
        c) - SendSmsService - Textlocal API(https://www.textlocal.in/) is used for sending messages which only
             operates between 9 AM to 9 PM.
             
        d) - CheckOutService - CheckOut service first checks for active check-in of current visitor, in case of
             which it checks him out by updating checkout time in DB and sends Email to visitor regarding his visit
             details, after which meeting, visitor are deleted from respective DB's(there can be a single host to all
             meetings that's why it is preserved.
             
    


