package com.alinesno.infra.base.config.service.impl;

import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.base.config.entity.ConfigureHistoryEntity;
import com.alinesno.infra.base.config.mapper.ConfigureHistoryMapper;
import com.alinesno.infra.base.config.service.IConfigureHistoryService;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
public class ConfigureHistoryServiceImpl extends IBaseServiceImpl<ConfigureHistoryEntity, ConfigureHistoryMapper> implements IConfigureHistoryService {

	@Override
	public void saveHistory(ConfigureEntity configure) {

		ConfigureHistoryEntity history = new ConfigureHistoryEntity() ;
		BeanUtils.copyProperties(configure , history);

		history.setId(null);
		history.setConfigId(configure.getId());

		if(history.getConfVersion() == null || history.getConfVersion() == 0){
			history.setConfVersion(1);
		}else{
			history.setConfVersion(history.getConfVersion() + 1);
		}

		save(history);
	}
}
