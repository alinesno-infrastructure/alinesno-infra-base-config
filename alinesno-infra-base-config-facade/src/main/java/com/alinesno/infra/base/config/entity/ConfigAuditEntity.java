package com.alinesno.infra.base.config.entity;
import java.util.Date;

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
public class ConfigAuditEntity extends InfraBaseEntity {
 

    /**
     * 配置项名称
     */
    @TableField("item_name")
    private String itemName;

    /**
     * 变更内容
     */
    @TableField("changes")
    private String changes;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 发送时间
     */
    @TableField("send_time")
    private Date sendTime;

    /**
     * 变更人
     */
    @TableField("changer")
    private String changer;

    /**
     * 变更类型
     */
    @TableField("change_type")
    private String changeType;

    /**
     * 变更来源
     */
    @TableField("change_source")
    private String changeSource;

    /**
     * 相关应用
     */
    @TableField("related_app")
    private String relatedApp;

    /**
     * 相关环境
     */
    @TableField("related_env")
    private String relatedEnv;

    /**
     * 变更描述
     */
    @TableField("change_description")
    private String changeDescription;

    // Getters and Setters
  
    /**
     * 获取配置项名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置配置项名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 获取变更内容
     */
    public String getChanges() {
        return changes;
    }

    /**
     * 设置变更内容
     */
    public void setChanges(String changes) {
        this.changes = changes;
    }

    /**
     * 获取邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取变更人
     */
    public String getChanger() {
        return changer;
    }

    /**
     * 设置变更人
     */
    public void setChanger(String changer) {
        this.changer = changer;
    }

    /**
     * 获取变更类型
     */
    public String getChangeType() {
        return changeType;
    }

    /**
     * 设置变更类型
     */
    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    /**
     * 获取变更来源
     */
    public String getChangeSource() {
        return changeSource;
    }

    /**
     * 设置变更来源
     */
    public void setChangeSource(String changeSource) {
        this.changeSource = changeSource;
    }

    /**
     * 获取相关应用
     */
    public String getRelatedApp() {
        return relatedApp;
    }

    /**
     * 设置相关应用
     */
    public void setRelatedApp(String relatedApp) {
        this.relatedApp = relatedApp;
    }

    /**
     * 获取相关环境
     */
    public String getRelatedEnv() {
        return relatedEnv;
    }

    /**
     * 设置相关环境
     */
    public void setRelatedEnv(String relatedEnv) {
        this.relatedEnv = relatedEnv;
    }

    /**
     * 获取变更描述
     */
    public String getChangeDescription() {
        return changeDescription;
    }

    /**
     * 设置变更描述
     */
    public void setChangeDescription(String changeDescription) {
        this.changeDescription = changeDescription;
    }
}
