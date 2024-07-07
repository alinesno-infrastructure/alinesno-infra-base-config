package com.alinesno.infra.base.config.api.controller;

import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.base.config.service.IConfigureService;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 处理与ConfigureEntity相关的请求的Controller。
 * 继承自BaseController类并实现IConfigureService接口。
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Slf4j
@Api(tags = "Configure")
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/base/config/configure")
public class ConfigureController extends BaseController<ConfigureEntity, IConfigureService> {

    @Autowired
    private IConfigureService service;

    /**
     * 获取ConfigureEntity的DataTables数据。
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
        return this.toPage(model, this.getFeign(), page);
    }

    /**
     * 获取项目配置列表
     *
     * @return 包含项目配置列表的ResponseData对象
     */
    @ApiOperation("获取项目配置列表")
    @GetMapping("/getProjectConfig")
    public AjaxResult getProjectConfig(Long configId) {
        ConfigureEntity configure = service.getById(configId);
        return AjaxResult.success(configure);
    }

    /**
     * 添加项目配置
     *
     * @param configureEntity 配置实体
     * @return 包含操作结果的ResponseData对象
     */
    @ApiOperation("添加项目配置")
    @PostMapping("/addProjectConfig")
    public AjaxResult addProjectConfig(@RequestBody ConfigureEntity configureEntity) {
        service.save(configureEntity);
        return AjaxResult.success("项目配置添加成功");
    }

    /**
     * 更新项目配置
     *
     * @param configureEntity 配置实体
     * @return 包含操作结果的ResponseData对象
     */
    @ApiOperation("更新项目配置")
    @PutMapping("/updateProjectConfig")
    public AjaxResult updateProjectConfig(@RequestBody ConfigureEntity configureEntity) {
        service.update(configureEntity);
        return AjaxResult.success("项目配置更新成功");
    }

    @Override
    public IConfigureService getFeign() {
        return this.service;
    }
}
