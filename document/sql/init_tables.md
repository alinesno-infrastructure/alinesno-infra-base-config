## 配置请求记录表

```sql
CREATE TABLE config_request_record (
    id BIGINT NOT NULL COMMENT '主键',
    add_time DATETIME DEFAULT NULL COMMENT '添加时间',
    application_id VARCHAR(64) DEFAULT NULL COMMENT '所属应用',
    delete_manager BIGINT DEFAULT NULL COMMENT '删除人员',
    delete_time DATETIME DEFAULT NULL,
    department_id VARCHAR(64) DEFAULT NULL COMMENT '部门权限',
    field_id VARCHAR(64) DEFAULT NULL COMMENT '字段权限',
    field_prop VARCHAR(128) DEFAULT NULL COMMENT '字段属性',
    has_delete INT DEFAULT NULL COMMENT '是否删除',
    has_status INT DEFAULT NULL COMMENT '状态',
    last_update_operator_id BIGINT DEFAULT NULL COMMENT '最后更新操作员',
    operator_id BIGINT DEFAULT NULL COMMENT '操作员',
    tenant_id BIGINT DEFAULT NULL COMMENT '所属租户',
    update_time DATETIME DEFAULT NULL COMMENT '最后更新时间',
    application_name VARCHAR(64) DEFAULT NULL COMMENT '应用名称',

    project_id BIGINT COMMENT '所属项目',
    config_id BIGINT COMMENT '所属配置',
    operation VARCHAR(64) COMMENT '操作说明',
    method_time BIGINT COMMENT '执行时间',
    method VARCHAR(128) COMMENT '类方法',
    params LONGTEXT COMMENT '请求参数',
    method_desc VARCHAR(64) COMMENT '方法描述',
    record_type VARCHAR(8) COMMENT '记录类型',
    ip VARCHAR(16) COMMENT '服务器IP',
    url VARCHAR(256) COMMENT '请求链接',
    agent VARCHAR(128) COMMENT '浏览器信息', 
    account_id BIGINT COMMENT '账户ID',
    login_name VARCHAR(64) COMMENT '登录名',
    account_name VARCHAR(64) COMMENT '用户名',
    role_power BIGINT COMMENT '角色权限'
) COMMENT='配置请求记录表';
```

## 密钥信息表
```sql
CREATE TABLE config_key (
    id BIGINT NOT NULL COMMENT '主键',
    add_time DATETIME DEFAULT NULL COMMENT '添加时间',
    application_id VARCHAR(64) DEFAULT NULL COMMENT '所属应用',
    delete_manager BIGINT DEFAULT NULL COMMENT '删除人员',
    delete_time DATETIME DEFAULT NULL,
    department_id VARCHAR(64) DEFAULT NULL COMMENT '部门权限',
    field_id VARCHAR(64) DEFAULT NULL COMMENT '字段权限',
    field_prop VARCHAR(128) DEFAULT NULL COMMENT '字段属性',
    has_delete INT DEFAULT NULL COMMENT '是否删除',
    has_status INT DEFAULT NULL COMMENT '状态',
    last_update_operator_id BIGINT DEFAULT NULL COMMENT '最后更新操作员',
    operator_id BIGINT DEFAULT NULL COMMENT '操作员',
    tenant_id BIGINT DEFAULT NULL COMMENT '所属租户',
    update_time DATETIME DEFAULT NULL COMMENT '最后更新时间',
    application_name VARCHAR(64) DEFAULT NULL COMMENT '应用名称',

    public_key VARCHAR(256) NOT NULL COMMENT '应用公钥',
    private_key VARCHAR(256) NOT NULL COMMENT '应用密钥',
    user_id BIGINT COMMENT '关联的用户ID',
    identity VARCHAR(50) UNIQUE COMMENT '应用代码标识，唯一'
) COMMENT='配置密钥表';
```

## 配置表
```sql
CREATE TABLE configure (
    id BIGINT NOT NULL COMMENT '主键',
    add_time DATETIME DEFAULT NULL COMMENT '添加时间',
    application_id VARCHAR(64) DEFAULT NULL COMMENT '所属应用',
    delete_manager BIGINT DEFAULT NULL COMMENT '删除人员',
    delete_time DATETIME DEFAULT NULL,
    department_id VARCHAR(64) DEFAULT NULL COMMENT '部门权限',
    field_id VARCHAR(64) DEFAULT NULL COMMENT '字段权限',
    field_prop VARCHAR(128) DEFAULT NULL COMMENT '字段属性',
    has_delete INT DEFAULT NULL COMMENT '是否删除',
    has_status INT DEFAULT NULL COMMENT '状态',
    last_update_operator_id BIGINT DEFAULT NULL COMMENT '最后更新操作员',
    operator_id BIGINT DEFAULT NULL COMMENT '操作员',
    tenant_id BIGINT DEFAULT NULL COMMENT '所属租户',
    update_time DATETIME DEFAULT NULL COMMENT '最后更新时间',
    application_name VARCHAR(64) DEFAULT NULL COMMENT '应用名称',
		
    project_id BIGINT COMMENT '所属项目',
    env VARCHAR(32) COMMENT '环境',
    env_name VARCHAR(32) COMMENT '名称',
    type VARCHAR(8) COMMENT '类型',
    contents TEXT COMMENT '内容',
    identity VARCHAR(32) COMMENT '标识',
    remarks VARCHAR(256) COMMENT '备注',
    version VARCHAR(4) COMMENT '版本',
    effective_time DATETIME COMMENT '生效时间',
    pwd VARCHAR(64) COMMENT '密码'

)COMMENT='配置表';
```