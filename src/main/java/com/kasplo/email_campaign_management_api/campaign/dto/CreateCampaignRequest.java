package com.kasplo.email_campaign_management_api.campaign.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateCampaignRequest(

        @NotBlank(message = "Campaign name is required") @Size(max = 255)
        String name,

        @NotBlank(message = "Subject is required")
        String subject,

        @NotBlank(message = "Sender email is required") @Email(message = "Invalid sender email")
        String senderEmail,

        @NotBlank(message = "Email content is required")
        String content,

        @NotNull(message = "Scheduled date is required")
        LocalDateTime scheduledAt
) {
}