package com.alinesno.infra.base.config.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Project Entity
 * 
 * @author luoxiaodong
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("project_configure")
@Data
public class ProjectConfigureEntity extends InfraBaseEntity {

	@TableField("project_id")
	@ColumnType
	@ColumnComment("项目ID")
	private Long projectId ;

	@TableField("configure_id")
	@ColumnType
	@ColumnComment("项目配置")
	private Long configureId ;

}
