package com.hackpro.authserver.domain;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;

import lombok.Data;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Data
@Table(value = "approval")
public class Approval {

	@PrimaryKey("approval_id")
	private UUID approvalId;

	@Column("user_id")
	private String userId;

	@Column("client_id")
	private String clientId;

	@Column("scope")
	private String scope;

	@Column("status")
	private ApprovalStatus status;

	@Column("expires_at")
	private Date expiresAt;

	@Column("last_updated_at")
	private Date lastUpdatedAt;

}
