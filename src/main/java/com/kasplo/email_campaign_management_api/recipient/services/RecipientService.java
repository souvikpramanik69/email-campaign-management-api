package com.kasplo.email_campaign_management_api.recipient.services;

import com.kasplo.email_campaign_management_api.campaign.dto.CampaignResponse;
import com.kasplo.email_campaign_management_api.recipient.dto.AddRecipientRequest;
import com.kasplo.email_campaign_management_api.recipient.dto.RecipientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RecipientService {


    public RecipientResponse createReceipt(UUID campaignId,AddRecipientRequest request);
    public Page<RecipientResponse> getAllReceipts(Pageable pageable);

}
