package com.alinesno.infra.base.config.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alinesno.infra.base.config.api.dto.ConfigureContentDto;
import com.alinesno.infra.base.config.entity.*;
import com.alinesno.infra.base.config.enums.ConfigTypeEnum;
import com.alinesno.infra.base.config.mapper.ProjectMapper;
import com.alinesno.infra.base.config.service.*;
import com.alinesno.infra.base.config.utils.ContentTypeUtils;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.common.facade.enums.HasStatusEnums;
import com.alinesno.infra.common.web.log.utils.SpringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ProjectServiceImpl extends IBaseServiceImpl<ProjectEntity, ProjectMapper> implements IProjectService {

	private static final String DEFAULT_PROJECT_FIELD = "default" ;

	@Autowired
	private IProjectConfigureService projectConfigureService ;

	@Autowired
	private IConfigEnvService envService ;

	@Autowired
	private IConfigureService configureService ;

	@Override
	public void saveDocumentType(String projectId, String documentStr) {
		ProjectEntity e = getById(projectId) ;
		update(e) ;
	}

	@Override
	public void initDefaultApp(long userId) {

		LambdaQueryWrapper<ProjectEntity> wrapper = new LambdaQueryWrapper<>() ;
		wrapper.eq(ProjectEntity::getOperatorId , userId) ;

		long count = count(wrapper) ;

		if(count == 0) {  // 账户应用为空
			String code = IdUtil.getSnowflakeNextIdStr() ;

			ProjectEntity project = new ProjectEntity() ;

			project.setOperatorId(userId);
			project.setFieldProp(DEFAULT_PROJECT_FIELD);

			project.setName("默认配置应用");
			project.setRemark("包含所有的配置渠道查看权限，用于开发和验证场景");
			project.setCode(code);

			save(project) ;
		}

	}

	/**
	 * 根据用户ID获取默认项目。
	 *
	 * @param userId 用户的ID，用于查询操作人与项目的关系。
	 * @return 返回满足条件的默认项目实体，如果不存在，则返回null。
	 *
	 * 此方法通过查询满足特定条件（默认项目字段和操作人ID）的项目实体来实现。
	 * 使用LambdaQueryWrapper来构建查询条件，以简化代码并提高可读性。
	 */
	@Override
	public ProjectEntity getDefaultProject(long userId) {
		return getOne(new LambdaQueryWrapper<ProjectEntity>()
				.eq(ProjectEntity::getFieldProp , DEFAULT_PROJECT_FIELD)
				.eq(ProjectEntity::getOperatorId , userId));
	}

	/**
	 * 检查项目是否处于合法状态。
	 *
	 * 本方法通过项目代码查询项目信息，并判断该项目的状态是否为合法状态。
	 * 使用LambdaQueryWrapper进行条件查询，提高了代码的可读性和简洁性。
	 *
	 * @param projectCode 项目的唯一标识代码。
	 * @return 如果项目处于合法状态，则返回true；否则返回false。
	 */
	@Override
	public boolean isOpen(String projectCode) {
		// 根据项目代码查询项目实体
		ProjectEntity project = getOne(new LambdaQueryWrapper<ProjectEntity>()
				.eq(ProjectEntity::getCode, projectCode));

		// 判断项目的状态是否为合法状态，返回相应的布尔值
		return project.getHasStatus() == HasStatusEnums.LEGAL.value;
	}

	@Override
	public List<ProjectEntity> latestList(long userId) {

		LambdaQueryWrapper<ProjectEntity> wrapper = new LambdaQueryWrapper<>() ;
		wrapper.orderByDesc(ProjectEntity::getAddTime) ;

		return list(wrapper) ;
	}

	@Override
	public void updateProjectConfigure(long id, String config) {

		List<Long> configIds = ContentTypeUtils.convertToLongList(config) ;
		ProjectEntity project = getById(id) ;

		// 先删除之前的配置
		LambdaUpdateWrapper<ProjectConfigureEntity> wrapper = new LambdaUpdateWrapper<>() ;
		wrapper.eq(ProjectConfigureEntity::getProjectId , id) ;
		projectConfigureService.remove(wrapper);

		// 添加新的配置
		List<ProjectConfigureEntity> entities = new ArrayList<>() ;
		configIds.forEach(item -> {
			ProjectConfigureEntity e = new ProjectConfigureEntity() ;
			e.setProjectId(id);
			e.setConfigureId(item);
			entities.add(e) ;
		});
		projectConfigureService.saveBatch(entities) ;

		// 更新配置总数
		project.setCountConfig(entities.size());
		update(project) ;
	}

	@SneakyThrows
	@Override
	public List<ConfigureContentDto> getConfigContent(ProjectEntity project, String env){

		// 获取到所有的配置id
		LambdaQueryWrapper<ProjectConfigureEntity> wrapper = new LambdaQueryWrapper<>() ;
		wrapper.eq(ProjectConfigureEntity::getProjectId , project.getId()) ;
		List<ProjectConfigureEntity> list = projectConfigureService.list(wrapper);

		List<Long> configIds = new ArrayList<>() ;
		list.forEach(item -> {
			configIds.add(item.getConfigureId()) ;
		});
		List<ConfigureEntity> allConfigureEntities = configureService.listByIds(configIds) ;

		// 过滤配置
		List<ConfigureContentDto> configureEntities = new ArrayList<>() ;
		allConfigureEntities.forEach(item -> {
			if(item.getEnv().equals(env)){
				ConfigureContentDto configureContentDto = new ConfigureContentDto() ;
				configureContentDto.setContent(item.getContents());
				configureContentDto.setType(ConfigTypeEnum.getTypeByCode(item.getType()));
				configureEntities.add(configureContentDto) ;
			}
		});

		// 拼接yaml配置文件
        return configureEntities;
	}

	@Override
	public List<ProjectConfigureEntity> queryProjectConfig(long projectId) {
		LambdaQueryWrapper<ProjectConfigureEntity> wrapper = new LambdaQueryWrapper<>() ;
		wrapper.eq(ProjectConfigureEntity::getProjectId , projectId) ;
        return projectConfigureService.list(wrapper);
	}

	@Override
	public void saveProject(ProjectEntity entity) {

		this.save(entity) ;

		// 保存到配置分类里面（每个项目是一个类）
		if(StringUtils.isNotEmpty(entity.getCode())) {

			IConfigureCatalogService catalogService = SpringUtils.getBean(IConfigureCatalogService.class) ;

			ConfigureCatalogEntity catalogEntity = new ConfigureCatalogEntity() ;
			BeanUtils.copyProperties(entity, catalogEntity);

			catalogEntity.setName(entity.getName());
			catalogEntity.setDescription(entity.getRemark());
			catalogEntity.setParentId(0L);
			catalogEntity.setProjectId(entity.getId());

			catalogService.save(catalogEntity) ;

			initEnv(entity);
		}
	}

	private void initEnv(ProjectEntity entity) {

		LambdaQueryWrapper<ConfigEnvEntity> wrapper = new LambdaQueryWrapper<>() ;
		wrapper.eq(ConfigEnvEntity::getOrgId , entity.getOrgId());
		long count = envService.count(wrapper) ;

		List<ConfigEnvEntity> environments = new ArrayList<>();

		// 开发环境
		ConfigEnvEntity devEnv = new ConfigEnvEntity();
		devEnv.setName("开发环境");
		devEnv.setCode("dev");
		devEnv.setRemark("这是开发人员编写和测试代码的地方，通常每个开发者都有自己的本地开发环境。");
		environments.add(devEnv);

		// 测试环境
		ConfigEnvEntity testEnv = new ConfigEnvEntity();
		testEnv.setName("测试环境");
		testEnv.setCode("test");
		testEnv.setRemark("有时分为单元测试环境、集成测试环境和系统测试环境，用于确保代码符合功能和性能要求。");
		environments.add(testEnv);

		// 用户验收测试环境
		ConfigEnvEntity uatEnv = new ConfigEnvEntity();
		uatEnv.setName("用户验收测试环境");
		uatEnv.setCode("uat");
		uatEnv.setRemark("用户或客户在类似生产环境的条件下测试应用，确认功能满足业务需求。");
		environments.add(uatEnv);

		// 预生产环境
		ConfigEnvEntity preEnv = new ConfigEnvEntity();
		preEnv.setName("预生产环境");
		preEnv.setCode("pre");
		preEnv.setRemark("也被称为“暂存环境”，用于最终测试和演示即将发布的版本，确保其在生产环境中能正常工作。");
		environments.add(preEnv);

		// 生产环境
		ConfigEnvEntity prodEnv = new ConfigEnvEntity();
		prodEnv.setName("生产环境");
		prodEnv.setCode("prod");
		prodEnv.setRemark("正式对外提供服务的环境，应用程序在这里运行，供真实用户使用。");
		environments.add(prodEnv);

		if(count < environments.size()){

			for(ConfigEnvEntity env : environments){
				env.setOrgId(entity.getOrgId());
				env.setOperatorId(entity.getOperatorId());
				env.setDepartmentId(entity.getDepartmentId());
			}

			envService.remove(wrapper);
			envService.saveBatch(environments);
		}
	}

}
