package com.alinesno.infra.base.config.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

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
@Data
public class ConfigureAuditEntity extends InfraBaseEntity {
 
	@TableField("account_id") // 用户ID
	@ColumnType(length=255)
	@ColumnComment("accountId")
	private Long accountId;

	@TableField("username") // 用户名
	@ColumnType(length=255)
	@ColumnComment("用户名")
	private String username;

	@TableField("operation_type") // 操作类型
	@ColumnType(length=255)
	@ColumnComment("操作类型")
	private String operationType;

	@TableField("operation_time") // 操作时间
	@ColumnType(length=255)
	@ColumnComment("操作时间")
	private LocalDateTime operationTime;

	@TableField("operation_description") // 操作描述
	@ColumnType(length=255)
	@ColumnComment("操作描述")
	private String operationDescription;

	@TableField("operation_details") // 操作详情
	@ColumnType(length=255)
	@ColumnComment("operationDetails")
	private String operationDetails;

	@TableField("ip_address") // IP地址
	@ColumnType(length=255)
	@ColumnComment("ipAddress")
	private String ipAddress;

	@TableField("device_info") // 设备信息
	@ColumnType(length=255)
	@ColumnComment("deviceInfo")
	private String deviceInfo;

	@TableField("geolocation") // 地理位置
	@ColumnType(length=255)
	@ColumnComment("geolocation")
	private String geolocation;

	@TableField("create_time") // 创建时间
	@ColumnType(length=255)
	@ColumnComment("createTime")
	private Date createTime;
}
