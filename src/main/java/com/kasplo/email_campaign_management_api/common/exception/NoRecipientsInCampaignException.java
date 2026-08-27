package com.kasplo.email_campaign_management_api.common.exception;

public class NoRecipientsInCampaignException extends RuntimeException {

    public NoRecipientsInCampaignException(String message) {
        super(message);
    }
}
