package com.alinesno.infra.base.config.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 应用历史表，用于做历史记录
 * 
 * @author luoxiaodong
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("configure_history")
@Table(comment = "配置历史管理")
@Data
public class ConfigureHistoryEntity extends ConfigureEntity {

    @TableField(value = "conf_version")
    @ColumnType(length=32)
    @ColumnComment("配置版本号")
    private Integer confVersion;

    @TableField(value = "config_id")
    @ColumnType(length=255)
    @ColumnComment("所属配置")
    private Long configId ; //  所属配置

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
    @ColumnType(length=1)
    @ColumnComment("type")
    private int type;

    // 内容
    @TableField(value = "contents")
    @ColumnType(MySqlTypeConstant.TEXT)
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
