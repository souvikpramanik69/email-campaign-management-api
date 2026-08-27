package com.kasplo.email_campaign_management_api.recipient.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasplo.email_campaign_management_api.recipient.controllers.RecipientController;
import com.kasplo.email_campaign_management_api.recipient.dto.AddRecipientRequest;
import com.kasplo.email_campaign_management_api.recipient.services.RecipientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RecipientController.class)
class RecipientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private RecipientService recipientService;

    @Test
    void shouldAddRecipientSuccessfully() throws Exception {
        UUID campaignId = UUID.randomUUID();

        AddRecipientRequest request = new AddRecipientRequest("John Doe", "john@example.com");

        mockMvc.perform(post("/api/v1/campaign/{campaignId}/recipients", campaignId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated());

        verify(recipientService).createReceipt(any(UUID.class), any(AddRecipientRequest.class));
    }

    @Test
    void shouldRejectInvalidRecipientInput() throws Exception {
        UUID campaignId = UUID.randomUUID();

        AddRecipientRequest request = new AddRecipientRequest("", "invalid-email");

        mockMvc.perform(post("/api/v1/campaign/{campaignId}/recipients", campaignId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest());
    }
}
