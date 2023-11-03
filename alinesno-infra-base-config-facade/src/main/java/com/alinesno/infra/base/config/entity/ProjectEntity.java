package com.alinesno.infra.base.config.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Project Entity
 * 
 * @author LuoXiaoDong
 * @version 1.0.0
 */
@SuppressWarnings("serial")
@TableName("project")
@Data
public class ProjectEntity extends InfraBaseEntity {
 
	@TableField("type")
	@ColumnType(length=255)
	@ColumnComment("Project")
	private String type; // 应用类型

	@TableField("version")
	@ColumnType(length=255)
	@ColumnComment("version")
	private String version; // 应用版本号

	@TableField("name")
	@ColumnType(length=255)
	@ColumnComment("name")
	private String name; // 应用名称

	@TableField("banner")
	@ColumnType(length=255)
	@ColumnComment("banner")
	private String banner; // 标识图标

	@TableField("icon")
	@ColumnType(length=255)
	@ColumnComment("icon")
	private String icon; // 应用icon

	@TableField("remark")
	@ColumnType(length=255)
	@ColumnComment("remark")
	private String remark; // 应用备注

	@TableField("open_key")
	@ColumnType(length=255)
	@ColumnComment("openKey")
	private String openKey; // 应用代码

	@TableField("code")
	@ColumnType(length=255)
	@ColumnComment("code")
	private String code;
}
