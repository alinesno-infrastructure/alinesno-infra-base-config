package com.alinesno.infra.base.config.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 用户操作记录和请求记录
 * 
 * @author weixiaojin
 * @since 2019年4月8日 下午10:15:43
 */
@SuppressWarnings("serial")
@TableName("config_request_record")
@Data
public class RequestRecordEntity extends InfraBaseEntity {
	
	@TableField(value = "project_id")
	@ColumnType(length=255)
	@ColumnComment("用户操作记录和请求记录")
	private Long projectId ; //  所属项目
  
	@TableField(value = "config_id")
	@ColumnType(length=255)
	@ColumnComment("configI")
	private Long configId ; //  所属配置
	
	/* @Excel(name = "操作说明") */
	@TableField
	@ColumnType(length=255)
	@ColumnComment("operation")
	private String operation;

	@Excel(name = "执行时间")
	@TableField("method_time")
	@ColumnType(length=255)
	@ColumnComment("methodTime")
	private long methodTime;

	@Excel(name = "类方法")
	@TableField("method")
	@ColumnType(length=255)
	@ColumnComment("method")
	private String method;

	@Excel(name = "请求参数")
	@Lob
	@TableField("params")
	@Basic(fetch = FetchType.LAZY)
	@ColumnType(length=255)
	@ColumnComment("params")
	private String params;

	@Excel(name = "方法描述")
	@TableField("method_desc")
	@ColumnType(length=255)
	@ColumnComment("methodDesc")
	private String methodDesc;

	@Excel(name = "请求记录")
	@TableField("record_type")
	@ColumnType(length=255)
	@ColumnComment("recordType")
	private String recordType; // 记录类型

	@Excel(name = "服务器IP")
	@TableField("ip")
	@ColumnType(length=255)
	@ColumnComment("ip")
	private String ip; // 服务器ip

	@Excel(name = "请求链接")
	@TableField("url")
	@ColumnType(length=255)
	@ColumnComment("url")
	private String url; // 请求链接

	@Excel(name = "浏览器信息")
	@TableField("agent")
	@ColumnType(length=255)
	@ColumnComment("agent")
	private String agent; // 浏览器信息

	@TableField("account_id")
	@ColumnType(length=255)
	@ColumnComment("accountId")
	private Long accountId;

	@Excel(name = "登陆名")
	@TableField("login_name")
	@ColumnType(length=255)
	@ColumnComment("loginName")
	private String loginName;

	@Excel(name = "用户名")
	@TableField("account_name")
	@ColumnType(length=255)
	@ColumnComment("accountName")
	private String accountName;

	@TableField("role_power")
	@ColumnType(length=255)
	@ColumnComment("rolePower")
	private String rolePower;
}
