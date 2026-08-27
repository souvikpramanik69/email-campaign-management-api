package com.kasplo.email_campaign_management_api.campaign.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kasplo.email_campaign_management_api.campaign.controllers.CampaignController;
import com.kasplo.email_campaign_management_api.campaign.dto.CampaignResponse;
import com.kasplo.email_campaign_management_api.campaign.dto.CreateCampaignRequest;
import com.kasplo.email_campaign_management_api.campaign.enums.CampaignStatus;
import com.kasplo.email_campaign_management_api.campaign.services.CampaignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CampaignController.class)
class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @MockitoBean
    private CampaignService campaignService;

    @Test
    void shouldCreateCampaignSuccessfully() throws Exception {
        CreateCampaignRequest request = new CreateCampaignRequest("Summer Campaign", "Summer Sale", "sender@example.com", "Hello, this is our summer campaign", LocalDateTime.now().plusDays(1));

        CampaignResponse response = new CampaignResponse(UUID.randomUUID(), "Summer Campaign", CampaignStatus.DRAFT);

        when(campaignService.createCampaignService(any(CreateCampaignRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/campaign").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated());
    }

    @Test
    void shouldRejectInvalidCampaignInput() throws Exception {
        CreateCampaignRequest request = new CreateCampaignRequest("", "", "invalid-email", "", null);

        mockMvc.perform(post("/api/v1/campaign").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest());
    }
}