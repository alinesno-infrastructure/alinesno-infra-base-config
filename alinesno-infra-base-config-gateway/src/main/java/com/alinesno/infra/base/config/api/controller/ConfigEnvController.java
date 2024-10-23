package com.alinesno.infra.base.config.api.controller;

import com.alinesno.infra.base.config.entity.ConfigEnvEntity;
import com.alinesno.infra.base.config.service.IConfigEnvService;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理与ConfigEnvEntity相关的请求的Controller。
 * 继承自BaseController类并实现IConfigEnvService接口。
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Slf4j
@Api(tags = "ConfigEnv")
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/base/config/env")
public class ConfigEnvController extends BaseController<ConfigEnvEntity, IConfigEnvService> {

    @Autowired
    private IConfigEnvService service;

    /**
     * 获取ConfigEnvEntity的DataTables数据。
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

    @DataPermissionSave
    @Override
    public AjaxResult save(Model model, ConfigEnvEntity entity) throws Exception {
        return super.save(model, entity);
    }

    @Override
    public IConfigEnvService getFeign() {
        return this.service;
    }
}
