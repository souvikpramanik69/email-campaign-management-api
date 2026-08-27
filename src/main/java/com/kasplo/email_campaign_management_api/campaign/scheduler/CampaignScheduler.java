package com.kasplo.email_campaign_management_api.campaign.scheduler;

import com.kasplo.email_campaign_management_api.campaign.services.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CampaignScheduler {

    private final CampaignService campaignService;

    public CampaignScheduler(CampaignService campaignService){
        this.campaignService = campaignService;
    }

    @Scheduled(fixedDelay = 10000)
    public void processCampaigns() {
        campaignService.processScheduledCampaigns();
    }
}
