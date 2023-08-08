package com.alinesno.infra.base.config.entity;

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
public class ConfigureEntity extends InfraBaseEntity {
	
	@TableField(value = "project_id")
	private Long projectId ; //  所属项目

	// 环境
	@TableField(value = "env")
	private String env;

	// 名称
	@TableField(value = "name")
	private String name;

	// 类型
	@TableField(value = "type")
	private String type;

	// 内容
	@TableField(value = "contents")
	private String contents;

	// 标识
	@TableField(value = "identity")
	private String identity;

	// 备注
	@TableField(value = "remarks")
	private String remarks;

	// 版本
	@TableField(value = "version")
	private String version;

	// 生效时间
	@TableField(value = "effective_time")
	private LocalDateTime effectiveTime;

	// 密码
	@TableField(value = "pwd")
	private String pwd;

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public LocalDateTime getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(LocalDateTime effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
 
}
