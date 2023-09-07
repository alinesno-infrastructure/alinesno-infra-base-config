package com.alinesno.infra.base.config.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alinesno.infra.base.config.entity.ConfigureAuditEntity;
import com.alinesno.infra.base.config.mapper.ConfigureAuditMapper;
import com.alinesno.infra.base.config.service.IConfigureAuditService;
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
public class ConfigureAuditServiceImpl extends IBaseServiceImpl<ConfigureAuditEntity, ConfigureAuditMapper> implements IConfigureAuditService {

	// 日志记录
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ConfigureAuditServiceImpl.class);

}
