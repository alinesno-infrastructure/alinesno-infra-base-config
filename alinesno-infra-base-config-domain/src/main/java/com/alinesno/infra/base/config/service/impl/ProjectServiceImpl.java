package com.alinesno.infra.base.config.service.impl;

import com.alinesno.infra.common.facade.enums.HasStatusEnums;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alinesno.infra.base.config.entity.ProjectEntity;
import com.alinesno.infra.base.config.mapper.ProjectMapper;
import com.alinesno.infra.base.config.service.IProjectService;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import org.sqids.Sqids;

import java.util.Arrays;
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
			Sqids sqids=Sqids.builder().build();
			String code = sqids.encode(Arrays.asList(1L,2L,3L)); // "86Rf07"

			ProjectEntity project = new ProjectEntity() ;

			project.setOperatorId(userId);
			project.setFieldProp(DEFAULT_PROJECT_FIELD);

			project.setName("默认日志应用");
			project.setRemark("包含所有的日志渠道查看权限，用于开发和验证场景");
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
//		wrapper.eq(ManagerProjectEntity::getOperatorId , userId) ;
		wrapper.orderByDesc(ProjectEntity::getAddTime) ;

		return list(wrapper) ;
	}

}
