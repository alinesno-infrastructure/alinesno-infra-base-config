package com.alinesno.infra.base.config.api.controller;

import com.alinesno.infra.base.config.entity.ConfigureCatalogEntity;
import com.alinesno.infra.base.config.service.IConfigureCatalogService;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionQuery;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionScope;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理与BusinessLogEntity相关的请求的Controller。
 * 继承自BaseController类并实现IBusinessLogService接口。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/base/config/catalog")
public class ConfigureCatalogController extends BaseController<ConfigureCatalogEntity, IConfigureCatalogService> {

    @Autowired
    private IConfigureCatalogService service;

    /**
     * 获取BusinessLogEntity的DataTables数据。
     *
     * @param request HttpServletRequest对象。
     * @param model Model对象。
     * @param page DatatablesPageBean对象。
     * @return 包含DataTables数据的TableDataInfo对象。
     */
    @DataPermissionScope
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign(), page);
    }

    @DataPermissionQuery
    @GetMapping("/list")
    public AjaxResult list(ConfigureCatalogEntity ConfigureCatalog , PermissionQuery query) {
        List<ConfigureCatalogEntity> ConfigureCatalogEntities = service.selectCatalogList(ConfigureCatalog  , query);

        return AjaxResult.success("操作成功." , ConfigureCatalogEntities) ;
    }

    /**
     * 保存角色类型
     * @return
     */
    @DataPermissionSave
    @PostMapping("/insertCatalog")
    public AjaxResult insertCatalog(@RequestBody ConfigureCatalogEntity entity){

        // 查询父类，获取到项目信息
        ConfigureCatalogEntity parent = service.getById(entity.getParentId()) ;
        entity.setProjectId(parent.getId());

        service.insertCatalog(entity) ;

        return AjaxResult.success("操作成功.") ;
    }

    /**
     * 获取到子类
     * @param deptId
     * @return
     */
    @DataPermissionQuery
    @GetMapping("/excludeChild/{id}")
    public AjaxResult excludeChild(@PathVariable(value = "id", required = false) Long deptId  , PermissionQuery query)
    {
        List<ConfigureCatalogEntity> depts = service.selectCatalogList(new ConfigureCatalogEntity(), query);
        depts.removeIf(d -> d.getId().longValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return AjaxResult.success("操作成功." , depts);
    }

    @Override
    public IConfigureCatalogService getFeign() {
        return this.service;
    }
}

