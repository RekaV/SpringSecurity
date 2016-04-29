package com.eda.rest.sendgrid;

@SuppressWarnings("serial")
public class SendGridException extends Exception {
    public SendGridException(Exception e) {
        super(e);
    }
}
