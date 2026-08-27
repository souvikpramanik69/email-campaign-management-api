package com.kasplo.email_campaign_management_api.recipient.controllers;

import com.kasplo.email_campaign_management_api.campaign.dto.CampaignResponse;
import com.kasplo.email_campaign_management_api.campaign.dto.CreateCampaignRequest;
import com.kasplo.email_campaign_management_api.common.response.ApiResponse;
import com.kasplo.email_campaign_management_api.recipient.dto.AddRecipientRequest;
import com.kasplo.email_campaign_management_api.recipient.dto.RecipientResponse;
import com.kasplo.email_campaign_management_api.recipient.services.RecipientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/campaign")
public class RecipientController{


    private final RecipientService recipientService;

    public RecipientController(RecipientService recipientService){
        this.recipientService = recipientService;
    }

    @PostMapping("/{campaignId}/recipients")
    public ResponseEntity<ApiResponse<RecipientResponse>> addRecipient(
            @PathVariable UUID campaignId,
            @Valid @RequestBody AddRecipientRequest request
    ) {
        RecipientResponse recipient = recipientService.createReceipt(campaignId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(201, "Recipient added successfully", recipient));
    }



    @GetMapping("/{campaignId}/recipients")
    public ResponseEntity<ApiResponse<Page<RecipientResponse>>> getAllRecipientsByCampaignId(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        return ResponseEntity.ok(ApiResponse.success(200, "Recipient fetched successfully", recipientService.getAllReceipts(PageRequest.of(page - 1, limit, sort))));
    }

}
