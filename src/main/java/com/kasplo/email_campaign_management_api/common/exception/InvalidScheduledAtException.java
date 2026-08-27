package com.kasplo.email_campaign_management_api.common.exception;

public class InvalidScheduledAtException extends RuntimeException {

    public InvalidScheduledAtException(String message) {
        super(message);
    }
}