package com.alinesno.infra.base.config.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 应用配置管理
 * 
 * @author LuoXiaoDong
 * @version 1.0.0
 */
@SuppressWarnings("serial")
@TableName("configure")
@Data
public class ConfigureEntity extends InfraBaseEntity {
	
	@TableField(value = "project_id")
	@ColumnType(length=255)
	@ColumnComment("所属项目")
	private Long projectId ; //  所属项目

	// 环境
	@TableField(value = "env")
	@ColumnType(length=255)
	@ColumnComment("env")
	private String env;

	// 名称
	@TableField(value = "env_name")
	@ColumnType(length=255)
	@ColumnComment("name")
	private String name;

	// 类型
	@TableField(value = "type")
	@ColumnType(length=255)
	@ColumnComment("type")
	private String type;

	// 内容
	@TableField(value = "contents")
	@ColumnType(length=255)
	@ColumnComment("contents")
	private String contents;

	// 标识
	@TableField(value = "identity")
	@ColumnType(length=255)
	@ColumnComment("identity")
	private String identity;

	// 备注
	@TableField(value = "remarks")
	@ColumnType(length=255)
	@ColumnComment("remarks")
	private String remarks;

	// 版本
	@TableField(value = "version")
	@ColumnType(length=255)
	@ColumnComment("version")
	private String version;

	// 生效时间
	@TableField(value = "effective_time")
	@ColumnType(length=255)
	@ColumnComment("effectiveTime")
	private LocalDateTime effectiveTime;

	// 密码
	@TableField(value = "pwd")
	@ColumnType(length=255)
	@ColumnComment("pwd")
	private String pwd;
}
