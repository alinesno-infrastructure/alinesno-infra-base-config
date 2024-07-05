package com.alinesno.infra.base.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 应用历史表，用于做历史记录
 * 
 * @author luoxiaodong
 * @version 1.0.0
 */
@SuppressWarnings("serial")
@TableName("configure_history")
@Data
public class ConfigureHistoryEntity extends ConfigureEntity {



}
