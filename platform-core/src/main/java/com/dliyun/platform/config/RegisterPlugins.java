package com.dliyun.platform.config;

import com.dliyun.platform.PluginInfo;
import com.dliyun.platform.common.plugin.Plugin;
import com.dliyun.platform.common.plugin.PluginModuleInfo;
import com.dliyun.platform.common.plugin.RegisterPlugin;
import com.dliyun.platform.common.plugin.UpgradeSqlInfo;
import com.dliyun.platform.common.utils.DateUtil;
import com.dliyun.platform.core.mappers.UpgradeSchemaMapper;
import com.dliyun.platform.core.model.UpgradeSchema;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class RegisterPlugins implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private UpgradeSchemaMapper upgradeSchemaMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @PostConstruct
    public void register() throws Exception {
        checkUpgradeSchemaHistoryTable();

        //注册用户插件
        Map<String, Object> pluginsMap = applicationContext.getBeansWithAnnotation(Plugin.class);
        for (String pluginName : pluginsMap.keySet()) {
            Object o = pluginsMap.get(pluginName);
            if (o instanceof RegisterPlugin) {
                Plugin pluginAnnotation = AnnotationUtils.findAnnotation(o.getClass(), Plugin.class);
                if (pluginAnnotation != null) {
                    RegisterPlugin registerPlugin = ((RegisterPlugin) o);

                    List<PluginModuleInfo> listModules = registerPlugin.registerModule();

                    PluginInfo pluginInfo = new PluginInfo();
                    pluginInfo.setKey(pluginAnnotation.key());
                    pluginInfo.setFaicon(pluginAnnotation.faicon());
                    pluginInfo.setName(pluginAnnotation.name());
                    pluginInfo.addModules(listModules);

                    if (PluginInfo.REGISTERED_PLUGINS.containsKey(pluginInfo.getKey())) {
                        log.warn(String.format("注册插件[%s,%s]失败，插件{PluginInfo.key}在系统中必需唯一", pluginAnnotation.name(), pluginName));
                        continue;
                    }
                    PluginInfo.REGISTERED_PLUGINS.put(pluginAnnotation.key(), pluginInfo);


                    List<UpgradeSqlInfo> listUpgradeSqls = registerPlugin.getListUpgradeSqls();
                    if (listUpgradeSqls != null && listUpgradeSqls.size() > 0) {

                        for (UpgradeSqlInfo upgradeSqlInfo : listUpgradeSqls) {
                            UpgradeSchema existUpgradeSchema = this.upgradeSchemaMapper.findUpgradeSchema(pluginInfo.getKey(), upgradeSqlInfo.getVersion());

                            if (existUpgradeSchema != null) {
                                if (StringUtils.equals(existUpgradeSchema.getFileMd5(), upgradeSqlInfo.getFileMd5())) {
                                    continue;
                                }
                                if (!StringUtils.equals(existUpgradeSchema.getFileMd5(), upgradeSqlInfo.getFileMd5())) {
                                    throw new Exception(String.format("[%s,%s,%s.sql]被篡改，已经执行过的SQL禁止修改！", pluginAnnotation.name(), pluginName, upgradeSqlInfo.getScript()));
                                }
                            }


                            log.info(String.format("执行[%s,%s,%s]升级SQL", pluginAnnotation.name(), pluginName, upgradeSqlInfo.getScript()));
                            transactionTemplate.execute(status -> {
                                UpgradeSchema upgradeSchema = new UpgradeSchema();
                                upgradeSchema.setPluginKey(pluginInfo.getKey());
                                upgradeSchema.setVersion(upgradeSqlInfo.getVersion());
                                upgradeSchema.setScript(upgradeSqlInfo.getScript());
                                upgradeSchema.setFileMd5(upgradeSqlInfo.getFileMd5());
                                upgradeSchema.setExecuteTime(DateUtil.current());

                                try {
                                    for (String sql : upgradeSqlInfo.getSqls()) {
                                        this.upgradeSchemaMapper.executeSql(sql);
                                        log.info(String.format("执行[ %s ]成功", sql));
                                    }
                                    this.upgradeSchemaMapper.insertUpgradeSchema(upgradeSchema);
                                } catch (Exception e) {
                                    status.setRollbackOnly();
                                    log.error("SQL 执行失败", e);
                                }
                                return null;
                            });

                        }
                    }

                    log.info(String.format("注册插件[%s,%s]成功", pluginAnnotation.name(), pluginName));
                }

            } else {
                log.warn(String.format("%s 插件注册失败，@Plugin注解必要实现com.dliyun.platform.common.plugin.RegisterPlugin", pluginName));
            }
        }
    }

    private void checkUpgradeSchemaHistoryTable() {
        List<String> listLikeUpgradeSchemaTable = this.upgradeSchemaMapper.findLikeUpgradeSchemaTables();
        if (listLikeUpgradeSchemaTable == null || !listLikeUpgradeSchemaTable.contains("upgrade_schema_history")) {
            this.upgradeSchemaMapper.createdTable();
        }
    }
}
