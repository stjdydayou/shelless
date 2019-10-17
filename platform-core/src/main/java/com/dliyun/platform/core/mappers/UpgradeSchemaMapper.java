package com.dliyun.platform.core.mappers;

import com.dliyun.platform.core.model.UpgradeSchema;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jtoms
 */
public interface UpgradeSchemaMapper {

    /**
     * 查询upgrade_schema_history表是否存在
     *
     * @return
     */
    List<String> findLikeUpgradeSchemaTables();

    /**
     * 创建upgrade_schema_history表
     */
    void createdTable();

    /**
     * 查询一个升级版本的信息
     *
     * @param pluginKey
     * @param version
     * @return
     */
    UpgradeSchema findUpgradeSchema(@Param("pluginKey") String pluginKey, @Param("version") String version);

    /**
     * 插入SQL升级记录
     *
     * @param upgradeSchema
     */
    void insertUpgradeSchema(UpgradeSchema upgradeSchema);

    /**
     * 执行SQL
     *
     * @param sql
     */
    void executeSql(@Param("sql") String sql);
}
