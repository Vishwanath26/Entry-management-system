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

# Application Flow in Simple Words
In the homescreen user gets two button for Check-in and Check-out, on Check-in an API with 
endpoint("http://localhost:8080/meeting/set") is called and it saves all the details of visitor,
host and meeting in database, after which it triggers an email(using Gmail SMTP) and a 
message(using textLocal API) to host which contains detail of visitor checked-in.

On Check-out, API with endpoint("http://localhost:8080/meeting/checkout") is called and it updates
the checkout time in Database after which it sends an email to visitor about his visit details, 
after which it deletes the meeting details from database.

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
   
        a) MeetingService - this service sets the meeting for visitor where first visitor is searched 
                            in meeting table for ensuring no duplicate check-in before checkout.
                            It then saves the visitor details, host details(if not found in hostDB) and
                            meeting details and then trigger email and message to host about meeting
                            in a seperate thread(for making email and sms asynchronous).
             
        b) EmailService -   Gmail SMTP is used for sending email added with beautiful HTML templates.
                            
                            Credentials are stored in environment variables which are as follows - 
                            
                            Name of env variable   :      Value
                            gmailSenderEmail       :      udyogvihar26d@gmail.com
                            
                            gmailSenderPassword    :      Qwerty123@@@
        
        c) SendSmsService - Textlocal API(https://www.textlocal.in/) is used for sending messages which only
                            operates between 9 AM to 9 PM.
             
        d) CheckOutService - CheckOut service first checks for active check-in of current visitor, in case of
                             which it updates checkout time in DB and sends Email to visitor using EmailService
                             regarding his visit details, after which meeting, visitor are deleted from respective
                             DB's(there can be a single host to all meetings that's why it is not deleted).
             
    
# Corner Cases
  Following edge cases are handeled properly - 
 
   1) Invalid details filled  :  Form will not submit till all details are filled in appropriate manner,
                                 text-boxes are red if they are filled wrong.
                               
   2) Check-In without past Checkout : In this case backend checks for it and if there is no visitor email found
                                           in meeting table then, UI throws failure alert "Checkout First".
                                  
   3) Check-Out without Check-In : In this case backend checks for it and if there is no visitor email found
                                           in meeting table then, UI throws failure alert "Check-in First".

