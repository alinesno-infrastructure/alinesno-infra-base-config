package com.alinesno.infra.base.config.service;

/**
 * 初始化管理配置中心示例数据，用于完善项目
 */
public interface IInitDataService {

    /**
     * 初始化组织环境
     * @param groupId
     */
    void initEnv(long groupId) ;

    /**
     * 初始化分组级别
     * @param groupId
     */
    void initCatalog(long groupId) ;

    /**
     * 初始化示例数据
     * @param groupId
     */
    void initDemo(long groupId) ;

}
