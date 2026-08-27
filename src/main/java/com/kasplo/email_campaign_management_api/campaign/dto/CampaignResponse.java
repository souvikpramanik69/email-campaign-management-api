package com.kasplo.email_campaign_management_api.campaign.dto;

import com.kasplo.email_campaign_management_api.campaign.enums.CampaignStatus;

import java.util.UUID;

public record CampaignResponse(UUID uuid, String name, CampaignStatus status) {
}
