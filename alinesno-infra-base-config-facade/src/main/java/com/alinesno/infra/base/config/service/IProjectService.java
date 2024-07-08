package com.alinesno.infra.base.config.service;

import com.alinesno.infra.base.config.api.dto.ConfigureContentDto;
import com.alinesno.infra.base.config.entity.ProjectConfigureEntity;
import com.alinesno.infra.base.config.entity.ProjectEntity;
import com.alinesno.infra.common.facade.services.IBaseService;

import java.util.List;

/**
 * 项目管理服务
 * 
 * @author luoxiaodong
 * @version 1.0.0
 */
public interface IProjectService extends IBaseService<ProjectEntity> {

    /**
     * 保存应用文档类型
     * @param documentStr
     */
    void saveDocumentType(String projectId , String documentStr) ;

    /**
     * 初始化默认应用
     * @param userId
     */
    void initDefaultApp(long userId);

    /**
     * 获取到默认应用标识
     * @return
     */
    ProjectEntity getDefaultProject(long userId);

    /**
     * 判断应用是否开启
     * @param projectCode
     * @return
     */
    boolean isOpen(String projectCode);

    /**
     * 获取最新的项目列表
     * @param userId
     * @return
     */
    List<ProjectEntity> latestList(long userId);

    /**
     * 更新项目配置信息
     * @param id
     * @param config
     */
    void updateProjectConfigure(long id, String config);

    /**
     * 获取项目配置
     *
     * @return
     */
    List<ConfigureContentDto> getConfigContent(ProjectEntity projectCode , String env);

    /**
     * 查询项目配置信息
     * @param projectId
     * @return
     */
    List<ProjectConfigureEntity> queryProjectConfig(long projectId);

}
