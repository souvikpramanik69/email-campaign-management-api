package com.kasplo.email_campaign_management_api.campaign.dto;

import java.util.UUID;

public record CampaignStatisticsResponse(UUID campaignId,Long totalRecipients,Long pendingCount,Long deliveredCount,Long failedCount) {
}
