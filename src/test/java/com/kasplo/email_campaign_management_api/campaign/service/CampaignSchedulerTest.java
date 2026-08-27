package com.kasplo.email_campaign_management_api.campaign.service;

import com.kasplo.email_campaign_management_api.campaign.scheduler.CampaignScheduler;
import com.kasplo.email_campaign_management_api.campaign.services.CampaignService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CampaignSchedulerTest {

    @Mock
    private CampaignService campaignService;

    @InjectMocks
    private CampaignScheduler campaignScheduler;

    @Test
    void shouldProcessScheduledCampaigns() {
        campaignScheduler.processCampaigns();

        verify(campaignService, times(1)).processScheduledCampaigns();
    }
}