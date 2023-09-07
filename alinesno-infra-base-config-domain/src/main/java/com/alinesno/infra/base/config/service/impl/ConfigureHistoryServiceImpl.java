package com.alinesno.infra.base.config.service.impl;

import com.alinesno.infra.base.config.entity.ConfigureHistoryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alinesno.infra.base.config.mapper.ConfigureHistoryMapper;
import com.alinesno.infra.base.config.service.IConfigureHistoryService;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author weixiaojin
 * @version 1.0.0
 */
@Service
public class ConfigureHistoryServiceImpl extends IBaseServiceImpl<ConfigureHistoryEntity, ConfigureHistoryMapper> implements IConfigureHistoryService {

	// 日志记录
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ConfigureHistoryServiceImpl.class);

}
