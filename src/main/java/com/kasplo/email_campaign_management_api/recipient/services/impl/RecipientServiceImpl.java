package com.kasplo.email_campaign_management_api.recipient.services.impl;

import com.kasplo.email_campaign_management_api.campaign.entity.Campaign;
import com.kasplo.email_campaign_management_api.campaign.repository.CampaignRepository;
import com.kasplo.email_campaign_management_api.common.exception.CampaignNotFoundException;
import com.kasplo.email_campaign_management_api.common.exception.RecipientAlreadyExistsException;
import com.kasplo.email_campaign_management_api.common.mapper.CustomMapper;
import com.kasplo.email_campaign_management_api.recipient.dto.AddRecipientRequest;
import com.kasplo.email_campaign_management_api.recipient.dto.RecipientResponse;
import com.kasplo.email_campaign_management_api.recipient.entity.Recipient;
import com.kasplo.email_campaign_management_api.recipient.enums.RecipientStatus;
import com.kasplo.email_campaign_management_api.recipient.repository.RecipientRepository;
import com.kasplo.email_campaign_management_api.recipient.services.RecipientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RecipientServiceImpl implements RecipientService {


    private final RecipientRepository recipientRepository;
    private final CampaignRepository campaignRepository;

    public RecipientServiceImpl(RecipientRepository recipientRepository,CampaignRepository campaignRepository){
        this.recipientRepository = recipientRepository;
        this.campaignRepository = campaignRepository;
    }


    @Override
    public RecipientResponse createReceipt(UUID campaignId,AddRecipientRequest request) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(()-> new CampaignNotFoundException(campaignId));
        Optional<Recipient> existRecipient = recipientRepository
                .findByCampaignIdAndEmail(campaignId, request.email());
        if(existRecipient.isPresent()){
            throw new RecipientAlreadyExistsException("Recipient with email " + request.email() + " already exists in this campaign");
        }
        Recipient recipient = new Recipient();
        recipient.setCampaign(campaign);
        recipient.setName(request.name());
        recipient.setEmail(request.email());
        recipient.setStatus(RecipientStatus.PENDING);
        return CustomMapper.mapToRecipientResponse(recipientRepository.save(recipient));
    }

    @Override
    public Page<RecipientResponse> getAllReceipts(Pageable pageable) {
        return recipientRepository.findAll(pageable)
                .map(CustomMapper::mapToRecipientResponse);
    }
}
