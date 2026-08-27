package com.kasplo.email_campaign_management_api.campaign.services;

import com.kasplo.email_campaign_management_api.campaign.dto.CampaignResponse;
import com.kasplo.email_campaign_management_api.campaign.dto.CampaignStatisticsResponse;
import com.kasplo.email_campaign_management_api.campaign.dto.CreateCampaignRequest;
import com.kasplo.email_campaign_management_api.campaign.entity.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CampaignService {


    public CampaignResponse createCampaignService(CreateCampaignRequest createCampaignRequest);
    public Page<CampaignResponse>  getAllCampaigns(Pageable pageable);
    public CampaignResponse  getCampaignById(UUID campaignId);
    public CampaignResponse campaignSchedule(UUID campaignId);
    public void processScheduledCampaigns();
    public CampaignStatisticsResponse campaignStatistics(UUID campaignId);




}
