package com.alinesno.infra.base.config.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.Data;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 环境配置实体类
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("config_env")
@Table(comment = "配置环境管理")
@Data
public class ConfigEnvEntity extends InfraBaseEntity {
    /**
     * 环境名称
     */
    @TableField("name")
	@ColumnType(length=255)
	@ColumnComment("环境名称")
    private String name;

    /**
     * 环境标识
     */
    @TableField("code")
	@ColumnType(length=255)
	@ColumnComment("环境标识")
    private String code;

    /**
     * 环境描述
     */
    @TableField("remark")
	@ColumnType(length=255)
	@ColumnComment("环境描述")
    private String remark;

    /**
     * 是否启用
     */
    @TableField("enabled")
	@ColumnType(length=1)
	@ColumnComment("是否启用")
    private Boolean enabled;

    /**
     * 创建时间
     */
    @TableField("create_time")
	@ColumnType(length=19)
	@ColumnComment("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
	@ColumnType(length=19)
	@ColumnComment("更新时间")
    private Date updateTime;
}
