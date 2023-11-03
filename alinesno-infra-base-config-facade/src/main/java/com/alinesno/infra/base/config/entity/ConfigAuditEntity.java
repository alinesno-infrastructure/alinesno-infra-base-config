package com.alinesno.infra.base.config.entity;
import java.util.Date;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 配置操作修改内容审计通知表
 * 
 * @author LuoXiaoDong
 * @version 1.0.0
 */
@SuppressWarnings("serial")
@TableName("config_audit")
@Data
public class ConfigAuditEntity extends InfraBaseEntity {
 

    /**
     * 配置项名称
     */
    @TableField("item_name")
	@ColumnType(length=255)
	@ColumnComment("配置项名称")
    private String itemName;

    /**
     * 变更内容
     */
    @TableField("changes")
	@ColumnType(length=255)
	@ColumnComment("变更内容")
    private String changes;

    /**
     * 邮箱
     */
    @TableField("email")
	@ColumnType(length=255)
	@ColumnComment("邮箱")
    private String email;

    /**
     * 发送时间
     */
    @TableField("send_time")
	@ColumnType(length=19)
	@ColumnComment("发送时间")
    private Date sendTime;

    /**
     * 变更人
     */
    @TableField("changer")
	@ColumnType(length=255)
	@ColumnComment("变更人")
    private String changer;

    /**
     * 变更类型
     */
    @TableField("change_type")
	@ColumnType(length=10)
	@ColumnComment("变更类型")
    private String changeType;

    /**
     * 变更来源
     */
    @TableField("change_source")
	@ColumnType(length=255)
	@ColumnComment("变更来源")
    private String changeSource;

    /**
     * 相关应用
     */
    @TableField("related_app")
	@ColumnType(length=255)
	@ColumnComment("相关应用")
    private String relatedApp;

    /**
     * 相关环境
     */
    @TableField("related_env")
	@ColumnType(length=50)
	@ColumnComment("相关环境")
    private String relatedEnv;

    /**
     * 变更描述
     */
    @TableField("change_description")
	@ColumnType(length=255)
	@ColumnComment("变更描述")
    private String changeDescription;
}
