package com.practice.exceptions;

public class CannotReportIncidentException extends RuntimeException{
    public CannotReportIncidentException(String message){
        super(message);
    }
}
