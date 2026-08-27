package com.kasplo.email_campaign_management_api.recipient.service;

import com.kasplo.email_campaign_management_api.campaign.entity.Campaign;
import com.kasplo.email_campaign_management_api.campaign.repository.CampaignRepository;
import com.kasplo.email_campaign_management_api.common.exception.RecipientAlreadyExistsException;
import com.kasplo.email_campaign_management_api.recipient.dto.AddRecipientRequest;
import com.kasplo.email_campaign_management_api.recipient.entity.Recipient;
import com.kasplo.email_campaign_management_api.recipient.repository.RecipientRepository;
import com.kasplo.email_campaign_management_api.recipient.services.impl.RecipientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipientServiceTest {

    @Mock
    private RecipientRepository recipientRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private RecipientServiceImpl recipientService;

    @Test
    void shouldAddRecipientSuccessfully() {
        UUID campaignId = UUID.randomUUID();

        Campaign campaign = new Campaign();
        campaign.setId(campaignId);

        AddRecipientRequest request = new AddRecipientRequest("John Doe", "john@example.com");

        Recipient savedRecipient = new Recipient();
        savedRecipient.setId(UUID.randomUUID());
        savedRecipient.setName("John Doe");
        savedRecipient.setEmail("john@example.com");
        savedRecipient.setCampaign(campaign);

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));

        when(recipientRepository.findByCampaignIdAndEmail(campaignId, "john@example.com")).thenReturn(Optional.empty());

        when(recipientRepository.save(any(Recipient.class))).thenReturn(savedRecipient);

        recipientService.createReceipt(campaignId, request);

        verify(recipientRepository).save(any(Recipient.class));
    }

    @Test
    void shouldNotAddDuplicateRecipientToSameCampaign() {
        UUID campaignId = UUID.randomUUID();

        Campaign campaign = new Campaign();
        campaign.setId(campaignId);

        AddRecipientRequest request = new AddRecipientRequest("John Doe", "john@example.com");

        Recipient existingRecipient = new Recipient();
        existingRecipient.setId(UUID.randomUUID());
        existingRecipient.setName("John Doe");
        existingRecipient.setEmail("john@example.com");
        existingRecipient.setCampaign(campaign);

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));

        when(recipientRepository.findByCampaignIdAndEmail(campaignId, "john@example.com")).thenReturn(Optional.of(existingRecipient));

        assertThrows(RecipientAlreadyExistsException.class, () -> recipientService.createReceipt(campaignId, request));

        verify(recipientRepository, never()).save(any(Recipient.class));
    }
}