<template>
  <div>
    <el-row class="acp-dashboard-panel" :gutter="20">
      <el-col class="panel-col" :span="19">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-link"></i> 服务特征能力</div>
          </div>
          <div class="panel-body" style="height: auto;position: relative;">
              <div class="direct-box">
                <ul>
                  <li class="box-item" v-for="item in configCenterAdvantages" :key="item">
                    <div class="dire-panel">
                      <div class="panel-title">
                        <i class="dire-panel-icon" :class="item.icon" :alt="item.name" />
                        {{ item.title }} 
                      </div>
                      <div class="panel-describe">
                        {{ item.description }}
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
          </div>
        </div>
      </el-col>
      <el-col :span="5">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-link"></i> 应用场景</div>
          </div>
          <div class="panel-body" style="height: auto;position: relative;">
            <div class="acp-app-list">
              <ul>
                <li class="app-items" style="width:100%" v-for="item in secureConfigScenarios" :key="item">
                  <div class="app-info">
                    <div class="app-item-title">
                      <i :class="item.icon" :alt="item.name" />
                        {{ item.name }}
                    </div>
                    <div class="app-item desc">{{ item.description }}</div>
                  </div>
                </li>
              </ul>
            </div>
          </div>

        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script setup>

const configCenterAdvantages = [
  { icon: 'fa-solid fa-shield-alt', title: '安全隔离', description: '通过严格的权限管理和访问控制，保护敏感配置不被未授权访问，同时支持加密存储，增强数据安全性。' },
  { icon: 'fa-solid fa-users-cog', title: '协作与审计', description: '支持团队协作，允许多人同时编辑配置，并记录每一次更改，便于追踪和审计，确保配置变更的可追溯性。' },
  { icon: 'fa-solid fa-cogs', title: '统一管理', description: '集中控制所有应用的配置，无论是在开发、测试还是生产环境，确保配置的一致性和准确性，简化配置管理流程。' },
  { icon: 'fa-solid fa-sync-alt', title: '实时更新', description: '配置更改可以实时推送至所有相关应用实例，无需重启服务即可生效，提高应用的灵活性和响应速度。' },
  { icon: 'fa-solid fa-chart-bar', title: '数据分析与监控', description: '提供配置变更历史和分析工具，帮助识别配置问题，预测潜在风险，同时监控配置状态，确保系统稳定运行。' },
];

const secureConfigScenarios = [
  { icon: 'fa-solid fa-lock', name: '金融行业配置管理', description: '确保敏感配置（如密钥、密码）的安全存储与传输，防止数据泄露，符合合规要求。' },
  { icon: 'fa-solid fa-user-secret', name: '医疗健康数据保护', description: '严格控制患者信息和诊断结果的访问权限，保障患者隐私，遵循HIPAA等法规标准。' },
  { icon: 'fa-solid fa-shield-alt', name: '政府机构安全配置', description: '为政府网站和公共服务提供安全的配置管理，强化加密通信，数据安全。' },
];

</script>