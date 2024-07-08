package com.alinesno.infra.base.config.api.dto;

import com.alinesno.infra.base.config.entity.ConfigureCatalogEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Data
public class TreeSelectDto implements Serializable {

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelectDto> children;

    public TreeSelectDto(ConfigureCatalogEntity ConfigureCatalog) {
        this.id = ConfigureCatalog.getId() ;
        this.label = ConfigureCatalog.getName();
        this.children = ConfigureCatalog.getChildren().stream().map(TreeSelectDto::new).collect(Collectors.toList());
    }
}
