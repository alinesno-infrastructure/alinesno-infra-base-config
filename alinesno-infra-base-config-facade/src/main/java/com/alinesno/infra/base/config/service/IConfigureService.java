package com.alinesno.infra.base.config.service;

import com.alinesno.infra.base.config.api.dto.ConfigureDto;
import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.common.facade.services.IBaseService;

/**
 * 配置管理服务接口
 * </p>
 * 该接口继承了 IBaseService 接口，提供了基本的 CRUD 操作方法。
 * </p>
 * 定义了根据 code 查询配置信息和发布配置的方法。
 * </p>
 * 通过 ConfiguraDto 对象作为参数和返回值，实现了实体类和 DTO 之间的数据传输。
 * </p>
 * 配置管理服务的具体实现在 ConfiguraServiceImpl 类中。
 * </p>
 * @author LuoXiaoDong
 * @version 1.0.0
 */
public interface IConfigureService extends IBaseService<ConfigureEntity> {

    /**
     * 根据 code 查询配置信息
     *
     * @param code 配置的唯一标识
     * @return 配置信息的 DTO 对象
     */
    ConfigureDto getByCode(String code);

    /**
     * 发布配置
     *
     * @param configData 配置的 DTO 对象
     */
    void publishConfig(ConfigureDto configData);
}
