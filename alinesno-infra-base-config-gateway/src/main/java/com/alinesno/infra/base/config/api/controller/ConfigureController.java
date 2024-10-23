package com.alinesno.infra.base.config.api.controller;

import cn.hutool.core.util.IdUtil;
import com.alinesno.infra.base.config.api.dto.ConfigureDto;
import com.alinesno.infra.base.config.entity.ConfigEnvEntity;
import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.base.config.entity.ProjectConfigureEntity;
import com.alinesno.infra.base.config.entity.ProjectEntity;
import com.alinesno.infra.base.config.service.IConfigEnvService;
import com.alinesno.infra.base.config.service.IConfigureCatalogService;
import com.alinesno.infra.base.config.service.IConfigureService;
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
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IConfigEnvService envService ;

    @Autowired
    private IProjectService projectService ;

    @Autowired
    private IConfigureCatalogService catalogService ;

    /**
     * 获取ConfigureEntity的DataTables数据。
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

        // TODO 临时处理，后期优化
        String projectId = request.getParameter("projectId") ;
        if(StringUtils.isNotBlank(projectId)){
            Map<String , Object> map = new HashMap<>() ;
            map.put("projectId" , projectId) ;
            page.setCondition(map) ;

            TableDataInfo tableDataInfo =  this.toPage(model, this.getFeign(), page);
            List<ConfigureEntity> configureEntities = (List<ConfigureEntity>) tableDataInfo.getRows();
            List<ConfigureDto> dtos = new ArrayList<>() ;

            List<ProjectConfigureEntity> projectConfigureEntities = projectService.queryProjectConfig(Long.parseLong(projectId)) ;

            configureEntities.forEach(item -> {
                ConfigureDto dto = new ConfigureDto() ;

                BeanUtils.copyProperties(item , dto);
                dto.setSelected(checkSelect(item , projectConfigureEntities));

                dtos.add(dto) ;
            });
            tableDataInfo.setRows(dtos);

            return tableDataInfo ;
        }else{
            return this.toPage(model, this.getFeign(), page);
        }

    }

    // 判断是否选中
    private boolean checkSelect(ConfigureEntity item, List<ProjectConfigureEntity> projectConfigureEntities) {
        boolean isSelect = false ;
        for(ProjectConfigureEntity configure : projectConfigureEntities){
            if(item.getId().equals(configure.getConfigureId())){
                isSelect = true ;
                break ;
            }
        };
        return isSelect ;
    }

    /**
     * 获取项目配置列表
     *
     * @return 包含项目配置列表的ResponseData对象
     */
    @DataPermissionQuery
    @GetMapping("/getProjectAndEnv")
    public AjaxResult getProjectAndEnv(PermissionQuery query) {

        AjaxResult result = AjaxResult.success("获取环境配置成功.");

        LambdaQueryWrapper<ProjectEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntityClass(ProjectEntity.class);
        query.toWrapper(queryWrapper);
        List<ProjectEntity> projectList = projectService.list(queryWrapper);

        LambdaQueryWrapper<ConfigEnvEntity> eQueryWrapper = new LambdaQueryWrapper<>();
        eQueryWrapper.setEntityClass(ConfigEnvEntity.class);
        query.toWrapper(eQueryWrapper);
        List<ConfigEnvEntity> envList = envService.list(eQueryWrapper);

        result.put("envList" , envList) ;
        result.put("projectList" , projectList) ;

        return result ;
    }

    /**
     * 获取项目配置列表
     *
     * @return 包含项目配置列表的ResponseData对象
     */
    @ApiOperation("获取项目配置列表")
    @GetMapping("/getConfigContent")
    public AjaxResult getConfigContent(Long configId) {
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
    @PostMapping("/addConfigContent")
    public AjaxResult addConfigContent(@RequestBody ConfigureEntity configureEntity) {
        service.addConfigContent(configureEntity);
        return AjaxResult.success("项目配置添加成功");
    }

    /**
     * 更新项目配置
     *
     * @param configureEntity 配置实体
     * @return 包含操作结果的ResponseData对象
     */
    @ApiOperation("更新项目配置")
    @PutMapping("/updateConfigContent")
    public AjaxResult updateConfigContent(@RequestBody ConfigureEntity configureEntity) {
        service.updateConfigContent(configureEntity);
        return AjaxResult.success("项目配置更新成功");
    }

    @DataPermissionSave
    @Override
    public AjaxResult save(Model model, @RequestBody ConfigureEntity entity) throws Exception {
        entity.setIdentity(IdUtil.getSnowflakeNextId()+"");
        return super.save(model, entity);
    }

    @DataPermissionQuery
    @GetMapping("/catalogTreeSelect")
    public AjaxResult catalogTreeSelect(PermissionQuery query){
        return AjaxResult.success("success" , catalogService.selectCatalogTreeList(query)) ;
    }

    @Override
    public IConfigureService getFeign() {
        return this.service;
    }
}
