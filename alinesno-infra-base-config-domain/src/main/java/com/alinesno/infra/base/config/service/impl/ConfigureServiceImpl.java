package com.alinesno.infra.base.config.service.impl;

import com.alinesno.infra.base.config.api.dto.ConfigureDto;
import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.base.config.mapper.ConfiguraMapper;
import com.alinesno.infra.base.config.service.IConfigureService;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 配置管理服务实现类
 * </p>
 * 该类实现了 IConfiguraService 接口，提供了配置管理相关的服务功能。
 * </p>
 * 通过 LambdaQueryWrapper 构建查询条件，使用 BeanUtils 进行实体类和 DTO 之间的属性拷贝。
 * </p>
 * 该类继承了 IBaseServiceImpl，是基于 MyBatis-Plus 的通用 Service 实现类。
 * </p>
 * 通过 saveOrUpdate 方法实现了配置的发布和更新。
 * </p>
 * 使用了日志记录器来记录日志。
 * </p>
 * @author weixiaojin
 * @version 1.0.0
 */
@Service
public class ConfigureServiceImpl extends IBaseServiceImpl<ConfigureEntity, ConfiguraMapper> implements IConfigureService {

	// 日志记录
	private static final Logger log = LoggerFactory.getLogger(ConfigureServiceImpl.class);

	@Override
	public ConfigureDto getByCode(String code) {
		LambdaQueryWrapper<ConfigureEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ConfigureEntity::getIdentity, code);

		ConfigureEntity e = this.getOne(queryWrapper);

		ConfigureDto dto = new ConfigureDto();
		BeanUtils.copyProperties(e, dto);

		return dto;
	}

	@Override
	public void publishConfig(ConfigureDto configData) {
		String identity = configData.getIdentity();

		LambdaQueryWrapper<ConfigureEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ConfigureEntity::getIdentity, identity);

		ConfigureEntity e = this.getOne(queryWrapper);
		BeanUtils.copyProperties(configData, e);

		// update
		this.saveOrUpdate(e);
	}
}
