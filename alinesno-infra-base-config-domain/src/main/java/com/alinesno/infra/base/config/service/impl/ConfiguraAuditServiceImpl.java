package com.alinesno.infra.base.config.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alinesno.infra.base.config.entity.ConfiguraAuditEntity;
import com.alinesno.infra.base.config.mapper.ConfiguraAuditMapper;
import com.alinesno.infra.base.config.service.IConfiguraAuditService;
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
public class ConfiguraAuditServiceImpl extends IBaseServiceImpl<ConfiguraAuditEntity, ConfiguraAuditMapper> implements IConfiguraAuditService {

	// 日志记录
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ConfiguraAuditServiceImpl.class);

}
