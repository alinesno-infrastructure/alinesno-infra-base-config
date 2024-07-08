package com.alinesno.infra.base.config.service.impl;

import com.alinesno.infra.base.config.api.dto.TreeSelectDto;
import com.alinesno.infra.base.config.entity.ConfigureCatalogEntity;
import com.alinesno.infra.base.config.mapper.ConfigureCatalogMapper;
import com.alinesno.infra.base.config.service.IConfigureCatalogService;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Configure指令类型Service业务层处理
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Slf4j
@Service
public class ConfigureCatalogServiceImpl extends IBaseServiceImpl<ConfigureCatalogEntity, ConfigureCatalogMapper> implements IConfigureCatalogService {

    @Override
    public List<ConfigureCatalogEntity> selectCatalogList(ConfigureCatalogEntity ConfigureCatalog) {

        List<ConfigureCatalogEntity> list = list() ;

        if(list == null || list.isEmpty()){

            list = new ArrayList<>() ;

            // 默认有一个选项是父类
            ConfigureCatalogEntity parent = new ConfigureCatalogEntity() ;
            parent.setName("父类对象");
            parent.setId(0L);

            list.add(parent) ;
        }

        return list ;

    }

    @Override
    public void insertCatalog(ConfigureCatalogEntity entity) {

        ConfigureCatalogEntity info = this.getById(entity.getParentId());
        if(info != null){
            entity.setAncestors(info.getAncestors() + "," + entity.getParentId());
        }

        this.save(entity) ;
    }

    @Override
    public List<TreeSelectDto> selectCatalogTreeList() {

        List<ConfigureCatalogEntity> deptTrees = buildDeptTree(list());
        return deptTrees.stream().map(TreeSelectDto::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param Configures 部门列表
     * @return 树结构列表
     */
    public List<ConfigureCatalogEntity> buildDeptTree(List<ConfigureCatalogEntity> Configures) {

        List<ConfigureCatalogEntity> returnList = new ArrayList<>();
        List<Long> tempList = Configures.stream().map(ConfigureCatalogEntity::getId).toList();

        for (ConfigureCatalogEntity dept : Configures) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(Configures, dept);
                returnList.add(dept);
            }
        }

        if (returnList.isEmpty()) {
            returnList = Configures;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ConfigureCatalogEntity> list, ConfigureCatalogEntity t) {
        // 得到子节点列表
        List<ConfigureCatalogEntity> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ConfigureCatalogEntity tChild : childList) {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ConfigureCatalogEntity> getChildList(List<ConfigureCatalogEntity> list, ConfigureCatalogEntity t) {
        List<ConfigureCatalogEntity> tlist = new ArrayList<>();
        for (ConfigureCatalogEntity n : list) {
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ConfigureCatalogEntity> list, ConfigureCatalogEntity t) {
        return !getChildList(list, t).isEmpty();
    }
}
