package com.kasplo.email_campaign_management_api.campaign.controllers;

import com.kasplo.email_campaign_management_api.campaign.dto.CampaignResponse;
import com.kasplo.email_campaign_management_api.campaign.dto.CampaignStatisticsResponse;
import com.kasplo.email_campaign_management_api.campaign.dto.CreateCampaignRequest;
import com.kasplo.email_campaign_management_api.campaign.services.CampaignService;
import com.kasplo.email_campaign_management_api.common.response.ApiResponse;
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
@RequestMapping("/api/v1")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService){
        this.campaignService = campaignService;
    }

    @PostMapping("/campaign")
    public ResponseEntity<ApiResponse<CampaignResponse>> createCampaign(@RequestBody @Valid CreateCampaignRequest createCampaignRequest){
      return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success(201,"Campaign created successfully",campaignService.createCampaignService(createCampaignRequest)));
    }

    @GetMapping("/campaign/{campaignId}/statistics")
    public ResponseEntity<ApiResponse<CampaignStatisticsResponse>> getCampaignStatistics(@PathVariable("campaignId")String campaignId){
      return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success(200,"Campaign statistics has beed retrieved successfully",campaignService.campaignStatistics(UUID.fromString(campaignId))));
    }

   @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<ApiResponse<CampaignResponse>> getCampaignById(@PathVariable("campaignId")String campaignId){
      return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success(200,"Campaign fetched successfully",campaignService.getCampaignById(UUID.fromString(campaignId))));
    }

    @PatchMapping("/campaign/{campaignId}/schedule")
    public ResponseEntity<ApiResponse<CampaignResponse>> campaignSchedule(@PathVariable("campaignId")String campaignId){
      return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success(200,"Campaign scheduled successfully",campaignService.campaignSchedule(UUID.fromString(campaignId))));
    }

    @GetMapping("/campaigns")
    public ResponseEntity<ApiResponse<Page<CampaignResponse>>> getAllCampaigns(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return ResponseEntity.ok(ApiResponse.success(200, "All campaign has been fetched successfully", campaignService.getAllCampaigns(pageable)));
    }


}
