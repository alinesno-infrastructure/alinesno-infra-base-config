package com.alinesno.infra.base.config.service.impl;

import com.alinesno.infra.base.config.entity.ConfigEnvEntity;
import com.alinesno.infra.base.config.mapper.ConfigEvnMapper;
import com.alinesno.infra.base.config.service.IConfigEnvService;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author weixiaojin
 * @version 1.0.0
 */
@Slf4j
@Service
public class ConfigEnvServiceImpl extends IBaseServiceImpl<ConfigEnvEntity, ConfigEvnMapper> implements IConfigEnvService {

}
