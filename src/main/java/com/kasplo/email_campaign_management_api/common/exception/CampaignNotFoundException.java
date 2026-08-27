package com.kasplo.email_campaign_management_api.common.exception;

import java.util.UUID;

public class CampaignNotFoundException extends RuntimeException{
    public CampaignNotFoundException(UUID id){
        super("Campaign not found by this id - " + id );
    }
}
