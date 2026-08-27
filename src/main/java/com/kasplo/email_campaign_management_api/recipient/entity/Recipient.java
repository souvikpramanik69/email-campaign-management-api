package com.kasplo.email_campaign_management_api.recipient.entity;

import com.kasplo.email_campaign_management_api.campaign.entity.Campaign;
import com.kasplo.email_campaign_management_api.recipient.enums.RecipientStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "recipients", uniqueConstraints = { @UniqueConstraint(name = "uk_campaign_email", columnNames = {"campaign_id", "email"})})
@Getter
@Setter
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column( updatable = false)
    private UUID id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private RecipientStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
