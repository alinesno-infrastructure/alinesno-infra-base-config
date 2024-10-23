package com.alinesno.infra.base.config.service;

import com.alinesno.infra.base.config.api.dto.TreeSelectDto;
import com.alinesno.infra.base.config.entity.ConfigureCatalogEntity;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.services.IBaseService;

import java.util.List;

/**
 * Configure指令类型Service接口
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public interface IConfigureCatalogService extends IBaseService<ConfigureCatalogEntity> {

    /**
     * 查询出指令类型列表
     *
     * @param ConfigureCatalog
     * @param query
     * @return
     */
    List<ConfigureCatalogEntity> selectCatalogList(ConfigureCatalogEntity ConfigureCatalog, PermissionQuery query);

    /**
     * 保存用户类型
     * @param entity
     */
    void insertCatalog(ConfigureCatalogEntity entity);

    /**
     * 查询类型列表树
     * @return
     */
    List<TreeSelectDto> selectCatalogTreeList(PermissionQuery query);
}