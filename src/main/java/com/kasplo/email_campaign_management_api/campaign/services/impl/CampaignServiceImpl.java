package com.kasplo.email_campaign_management_api.campaign.services.impl;

import com.kasplo.email_campaign_management_api.campaign.dto.CampaignResponse;
import com.kasplo.email_campaign_management_api.campaign.dto.CampaignStatisticsResponse;
import com.kasplo.email_campaign_management_api.campaign.dto.CreateCampaignRequest;
import com.kasplo.email_campaign_management_api.campaign.entity.Campaign;
import com.kasplo.email_campaign_management_api.campaign.enums.CampaignStatus;
import com.kasplo.email_campaign_management_api.campaign.repository.CampaignRepository;
import com.kasplo.email_campaign_management_api.campaign.services.CampaignService;
import com.kasplo.email_campaign_management_api.common.exception.*;
import com.kasplo.email_campaign_management_api.common.mapper.CustomMapper;
import com.kasplo.email_campaign_management_api.recipient.entity.Recipient;
import com.kasplo.email_campaign_management_api.recipient.enums.RecipientStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    public CampaignServiceImpl(CampaignRepository campaignRepository){
        this.campaignRepository = campaignRepository;
    }


    @Override
    public CampaignResponse createCampaignService(CreateCampaignRequest request) {
        // Schedule validation
        if (request.scheduledAt() != null &&
                request.scheduledAt().isBefore(LocalDateTime.now())) {
            throw new InvalidScheduledAtException(
                    "Scheduled time must be in the future"
            );
        }
        Campaign campaign = new Campaign();
        campaign.setContent(request.content());
        campaign.setName(request.name());
        campaign.setStatus(CampaignStatus.DRAFT);
        campaign.setSubject(request.subject());
        campaign.setScheduledAt(request.scheduledAt());
        campaign.setSenderEmail(request.senderEmail());
        return CustomMapper.mapToCampaignResponse(campaignRepository.save(campaign));
    }

    @Override
    public Page<CampaignResponse> getAllCampaigns(Pageable pageable) {
        return campaignRepository.findAll(pageable)
                .map(CustomMapper::mapToCampaignResponse);
    }

    @Override
    public CampaignResponse getCampaignById(UUID campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(() -> new CampaignNotFoundException(campaignId));
        return CustomMapper.mapToCampaignResponse(campaign);
    }

    @Override
    public CampaignResponse campaignSchedule(UUID campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(()->new CampaignNotFoundException(campaignId));
        if(!campaign.getStatus().equals(CampaignStatus.DRAFT)){
            throw new InvalidCampaignStateException("Campaign cannot be scheduled because it is already scheduled.");
        }
        if (campaign.getRecipients().isEmpty()) {
            throw new NoRecipientsInCampaignException(
                    "Campaign must have at least one recipient."
            );
        }
        if (campaign.getScheduledAt() == null ||
                !campaign.getScheduledAt().isAfter(LocalDateTime.now())) {
            throw new InvalidCampaignScheduleException(
                    "Campaign scheduled time must be in the future."
            );
        }
        campaign.setStatus(CampaignStatus.SCHEDULED);
        return CustomMapper.mapToCampaignResponse(campaignRepository.save(campaign));

    }



    @Override
    @Transactional
    public void processScheduledCampaigns() {

        LocalDateTime now = LocalDateTime.now();
        List<Campaign> campaigns = campaignRepository.findCampaignsToProcess(CampaignStatus.SCHEDULED, now);
        for (Campaign campaign : campaigns) {
            campaign.setStatus(CampaignStatus.PROCESSING);
            for (Recipient recipient : campaign.getRecipients()) {
                boolean delivered = ThreadLocalRandom.current().nextBoolean();
                if (delivered) {
                    recipient.setStatus(RecipientStatus.DELIVERED);
                }
                else {
                    recipient.setStatus(RecipientStatus.FAILED);
                }
            }
            campaign.setStatus(CampaignStatus.COMPLETED);
        }
    }

    @Override
    public CampaignStatisticsResponse campaignStatistics(UUID campaignId) {

        Campaign data = campaignRepository.findById(campaignId).orElseThrow(()-> new CampaignNotFoundException(campaignId));
        List<Recipient> recipients = data.getRecipients();

        Long totalRecipients = (long) recipients.size();
        Long deliveredCount = recipients.stream().filter(item->item.getStatus().equals(RecipientStatus.DELIVERED)).count();
        Long pendingCount = recipients.stream().filter(item->item.getStatus().equals(RecipientStatus.PENDING)).count();
        Long failedCount = recipients.stream().filter(item->item.getStatus().equals(RecipientStatus.FAILED)).count();
        return new CampaignStatisticsResponse(campaignId,totalRecipients,pendingCount,deliveredCount,failedCount);
    }


}
