package com.kasplo.email_campaign_management_api.campaign.entity;

import com.kasplo.email_campaign_management_api.campaign.enums.CampaignStatus;
import com.kasplo.email_campaign_management_api.recipient.entity.Recipient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "campaigns")
@Getter
@Setter
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column( updatable = false)
    private UUID id;

    private String name;

    private String subject;

    @Column(name = "sender_email")
    private String senderEmail;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<Recipient> recipients = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
