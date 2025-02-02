package com.alinesno.infra.base.config.api.controller;

import cn.hutool.core.util.IdUtil;
import com.alinesno.infra.base.config.entity.ProjectEntity;
import com.alinesno.infra.base.config.service.IProjectService;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionQuery;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionScope;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 处理与ProjectEntity相关的请求的Controller。
 * 继承自BaseController类并实现IProjectService接口。
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Slf4j
@Api(tags = "Project")
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/base/config/project")
public class ProjectController extends BaseController<ProjectEntity, IProjectService> {

    @Autowired
    private IProjectService service;

    /**
     * 获取ProjectEntity的DataTables数据。
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

    /**
     * 更新项目配置
     * @return
     */
    @GetMapping("/updateProjectConfigure")
    public AjaxResult updateProjectConfigure(long id ,  String config){
        log.debug("config = {}" , config);
        service.updateProjectConfigure(id , config) ;
        return ok();
    }

    @DataPermissionSave
    @Override
    public AjaxResult save(Model model, @RequestBody ProjectEntity entity) throws Exception {
        entity.setCode(IdUtil.nanoId(8));
        log.debug("project info = {}" , entity);
        return super.save(model, entity);
    }

    /**
     * 查询所有的项目
     * @return
     */
    @DataPermissionQuery
    @GetMapping("/listAllProject")
    public AjaxResult listAllProject(PermissionQuery permissionQuery){
        LambdaQueryWrapper<ProjectEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntityClass(ProjectEntity.class);
        permissionQuery.toWrapper(queryWrapper);
        return AjaxResult.success(service.list(queryWrapper));
    }

    @Override
    public IProjectService getFeign() {
        return this.service;
    }
}
