package com.kasplo.email_campaign_management_api.common.mapper;

import com.kasplo.email_campaign_management_api.campaign.dto.CampaignResponse;
import com.kasplo.email_campaign_management_api.campaign.entity.Campaign;
import com.kasplo.email_campaign_management_api.recipient.dto.RecipientResponse;
import com.kasplo.email_campaign_management_api.recipient.entity.Recipient;


public class CustomMapper {


    public static CampaignResponse mapToCampaignResponse(Campaign campaign){
      return new CampaignResponse(campaign.getId(),campaign.getName(),campaign.getStatus());
    }

    public static RecipientResponse mapToRecipientResponse(Recipient recipient){
        return new RecipientResponse(recipient.getName(),recipient.getEmail());
    }


}
