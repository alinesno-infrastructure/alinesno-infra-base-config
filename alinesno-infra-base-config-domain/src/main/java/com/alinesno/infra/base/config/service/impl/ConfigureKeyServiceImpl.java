package com.alinesno.infra.base.config.service.impl;

import com.alinesno.infra.base.config.entity.ConfigureKeyEntity;
import com.alinesno.infra.base.config.mapper.ConfigureKeyMapper;
import com.alinesno.infra.base.config.service.IConfigureKeyService;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author weixiaojin
 * @version 1.0.0
 */
@Service
public class ConfigureKeyServiceImpl extends IBaseServiceImpl<ConfigureKeyEntity, ConfigureKeyMapper> implements IConfigureKeyService {

	// 日志记录
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ConfigureKeyServiceImpl.class);

}
