package com.alinesno.infra.base.config.service.impl;

import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.base.config.entity.ConfigureHistoryEntity;
import com.alinesno.infra.base.config.mapper.ConfigureHistoryMapper;
import com.alinesno.infra.base.config.service.IConfigureHistoryService;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

		LambdaQueryWrapper<ConfigureHistoryEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ConfigureHistoryEntity::getConfigId , configure.getId()) ;
		List<ConfigureHistoryEntity> preHistory = list(queryWrapper) ;

		ConfigureHistoryEntity history = new ConfigureHistoryEntity() ;
		BeanUtils.copyProperties(configure , history);

		history.setId(null);
		history.setConfigId(configure.getId());

		if(preHistory == null){
			history.setConfVersion(1);
		}else{
			history.setConfVersion(preHistory.size() + 1);
		}

		history.setAddTime(new Date());
		save(history);
	}
}
