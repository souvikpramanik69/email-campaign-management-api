package com.kasplo.email_campaign_management_api.campaign.repository;

import com.kasplo.email_campaign_management_api.campaign.entity.Campaign;
import com.kasplo.email_campaign_management_api.campaign.enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {


    @Query("""
    select c from Campaign c where c.status = :status and c.scheduledAt <= :now
""")
    List<Campaign> findCampaignsToProcess(@Param("status") CampaignStatus status, @Param("now") LocalDateTime now);

}
