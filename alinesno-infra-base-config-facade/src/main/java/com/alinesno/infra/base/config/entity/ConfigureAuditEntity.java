package com.alinesno.infra.base.config.entity;

import java.time.LocalDateTime;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户操作记录表，用于安全审计日志
 * 
 * @author LuoXiaoDong
 * @version 1.0.0
 */
@SuppressWarnings("serial")
@TableName("configure_audit")
public class ConfigureAuditEntity extends InfraBaseEntity {
 
	@TableField("account_id") // 用户ID
	private Long accountId;

	@TableField("username") // 用户名
	private String username;

	@TableField("operation_type") // 操作类型
	private String operationType;

	@TableField("operation_time") // 操作时间
	private LocalDateTime operationTime;

	@TableField("operation_description") // 操作描述
	private String operationDescription;

	@TableField("operation_details") // 操作详情
	private String operationDetails;

	@TableField("ip_address") // IP地址
	private String ipAddress;

	@TableField("device_info") // 设备信息
	private String deviceInfo;

	@TableField("geolocation") // 地理位置
	private String geolocation;

	@TableField("create_time") // 创建时间
	private LocalDateTime createTime;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public LocalDateTime getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(LocalDateTime operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationDescription() {
		return operationDescription;
	}

	public void setOperationDescription(String operationDescription) {
		this.operationDescription = operationDescription;
	}

	public String getOperationDetails() {
		return operationDetails;
	}

	public void setOperationDetails(String operationDetails) {
		this.operationDetails = operationDetails;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(String geolocation) {
		this.geolocation = geolocation;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

}
