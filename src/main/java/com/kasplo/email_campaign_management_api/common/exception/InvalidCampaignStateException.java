package com.kasplo.email_campaign_management_api.common.exception;

public class InvalidCampaignStateException extends RuntimeException{
    public InvalidCampaignStateException(String message){
      super(message);
    }
}
