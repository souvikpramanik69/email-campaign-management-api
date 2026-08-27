package com.kasplo.email_campaign_management_api.common.exception;

public class RecipientAlreadyExistsException extends RuntimeException{

    public RecipientAlreadyExistsException(String message){
     super(message);
    }

}
