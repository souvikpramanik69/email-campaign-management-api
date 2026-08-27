package com.kasplo.email_campaign_management_api.recipient.repository;

import com.kasplo.email_campaign_management_api.recipient.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, UUID> {

    @Query("""
    select r from Campaign c join c.recipients r where c.id = :campaignId  and r.email = :email
""")
    Optional<Recipient> findByCampaignIdAndEmail(@Param("campaignId") UUID campaignId, @Param("email") String email);

    long countByCampaignId(UUID campaignId);
}
