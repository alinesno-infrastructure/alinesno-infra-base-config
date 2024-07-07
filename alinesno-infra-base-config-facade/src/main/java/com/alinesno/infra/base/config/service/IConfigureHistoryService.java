package com.alinesno.infra.base.config.service;

import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.base.config.entity.ConfigureHistoryEntity;
import com.alinesno.infra.common.facade.services.IBaseService;

/**
 * 配置历史表管理
 * 
 * @author luoxiaodong
 * @version 1.0.0
 */
public interface IConfigureHistoryService extends IBaseService<ConfigureHistoryEntity> {

    /**
     * 保存配置历史
     *
     * @param configureEntity 配置实体
     */
    void saveHistory(ConfigureEntity configureEntity);

}
