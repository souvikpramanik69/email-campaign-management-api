CREATE TABLE campaigns (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    subject VARCHAR(500) NOT NULL,
    sender_email VARCHAR(320) NOT NULL,
    content TEXT NOT NULL,
    scheduled_at TIMESTAMP WITH TIME ZONE,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_campaign_status
        CHECK (status IN ('DRAFT', 'SCHEDULED', 'PROCESSING', 'COMPLETED'))
);

CREATE TABLE recipients (
    id UUID PRIMARY KEY,
    campaign_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(320) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_recipient_campaign
        FOREIGN KEY (campaign_id)
        REFERENCES campaigns(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_recipient_status
        CHECK (status IN ('PENDING', 'DELIVERED', 'FAILED'))
);

CREATE UNIQUE INDEX uk_recipient_campaign_email
    ON recipients (campaign_id, LOWER(email));

CREATE INDEX idx_campaigns_status
    ON campaigns (status);

CREATE INDEX idx_campaigns_created_at
    ON campaigns (created_at DESC);

CREATE INDEX idx_campaigns_name
    ON campaigns (name);

CREATE INDEX idx_campaigns_scheduled_at
    ON campaigns (scheduled_at);

CREATE INDEX idx_campaigns_status_scheduled_at
    ON campaigns (status, scheduled_at);

CREATE INDEX idx_recipients_campaign_id
    ON recipients (campaign_id);

CREATE INDEX idx_recipients_campaign_status
    ON recipients (campaign_id, status);

ALTER TABLE recipients
    ADD CONSTRAINT chk_recipient_email
    CHECK (email ~* '^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$');

ALTER TABLE campaigns
    ADD CONSTRAINT chk_sender_email
    CHECK (sender_email ~* '^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$');