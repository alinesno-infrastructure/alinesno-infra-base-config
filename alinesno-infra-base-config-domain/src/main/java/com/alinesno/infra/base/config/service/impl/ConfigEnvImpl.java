package com.alinesno.infra.base.config.service.impl;

import com.alinesno.infra.base.config.entity.ConfigEnvEntity;
import com.alinesno.infra.base.config.mapper.ConfigEvnapper;
import com.alinesno.infra.base.config.service.IConfigEnvService;
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
public class ConfigEnvImpl extends IBaseServiceImpl<ConfigEnvEntity, ConfigEvnapper> implements IConfigEnvService {

	// 日志记录
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ConfigEnvImpl.class);

}
