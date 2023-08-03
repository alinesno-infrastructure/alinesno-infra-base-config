package com.alinesno.infra.base.config.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alinesno.infra.base.config.entity.ConfiguraEntity;
import com.alinesno.infra.base.config.mapper.ConfiguraMapper;
import com.alinesno.infra.base.config.service.IConfiguraService;
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
public class ConfiguraServiceImpl extends IBaseServiceImpl<ConfiguraEntity, ConfiguraMapper> implements IConfiguraService {

	// 日志记录
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ConfiguraServiceImpl.class);

}
