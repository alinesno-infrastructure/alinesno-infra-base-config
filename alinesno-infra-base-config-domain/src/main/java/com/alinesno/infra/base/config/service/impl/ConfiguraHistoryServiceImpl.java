package com.alinesno.infra.base.config.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alinesno.infra.base.config.entity.ConfiguraHistoryEntity;
import com.alinesno.infra.base.config.mapper.ConfiguraHistoryMapper;
import com.alinesno.infra.base.config.service.IConfiguraHistoryService;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author WeiXiaoJin
 * @version 1.0.0
 */
@Service
public class ConfiguraHistoryServiceImpl extends IBaseServiceImpl<ConfiguraHistoryEntity, ConfiguraHistoryMapper> implements IConfiguraHistoryService {

	// 日志记录
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ConfiguraHistoryServiceImpl.class);

}
