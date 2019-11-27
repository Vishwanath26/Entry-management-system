package com.innovaccer.entryManager.Domain;

public class Email {
    private String subject;
    private String reciepent;
    private String template;
    private Visitor visitor;
    private String activityType;

    public String getTemplate() {
        return template;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getSubject() {
        return subject;
    }

    public String getReciepent() {
        return reciepent;
    }

    public void setReciepent(String reciepent) {
        this.reciepent = reciepent;
    }

    private void setSubject(String subject) {
        if (preValidate("subject", subject))
            this.subject = subject;
    }

    public void setTemplate(String template) {
        this.template = template;
    }


    public Email(String subject, String reciepent, String template, Visitor visitor, String activityType) {
        this.subject = subject;
        this.reciepent = reciepent;
        this.template = template;
        this.visitor = visitor;
        this.activityType = activityType;
    }

    private boolean preValidate(String classFieldName, Object emailInfo) {
        boolean response = false;
        switch (classFieldName) {
            case "subject":
                if (ValidateString(emailInfo.toString())) {
                    response = true;
                }
                break;

            case "reciepent":
                if (ValidateString(emailInfo.toString())) {
                    response = true;
                }
                break;
            case "template":
                if (ValidateString(emailInfo.toString())) {
                    response = true;
                }
                break;
            default:
                response = false;
        }
        return response;
    }

    private boolean ValidateString(String string) {
        boolean response = false;
        if (string != null && !string.isEmpty()) {
            response = true;
        }
        return response;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

}