package com.alinesno.infra.base.config.api.controller;

import com.alinesno.infra.base.config.entity.ProjectEntity;
import com.alinesno.infra.base.config.service.IProjectService;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
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
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));

        log.debug("page = {}", ToStringBuilder.reflectionToString(page));

        long userId = 1L ; // CurrentAccountJwt.getUserId();
        long count = service.count(new LambdaQueryWrapper<ProjectEntity>().eq(ProjectEntity::getOperatorId , userId));

        // 初始化默认应用
        if (count == 0) {
            service.initDefaultApp(userId);
        }

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

    @Override
    public IProjectService getFeign() {
        return this.service;
    }
}
