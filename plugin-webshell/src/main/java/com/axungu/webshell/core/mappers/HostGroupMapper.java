package com.axungu.webshell.core.mappers;

import com.dliyun.platform.common.paginator.domain.PageList;
import com.axungu.webshell.core.model.HostGroup;
import org.apache.ibatis.annotations.Param;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:39
 */
public interface HostGroupMapper {
    /**
     * 分页查询主机列表
     *
     * @return
     */
    PageList<HostGroup> findAll();

    /**
     * 查询主机yung
     *
     * @param id
     * @return
     */
    HostGroup findById(@Param("id") Long id);

    /**
     * 添加管理
     *
     * @param group
     */
    void insert(HostGroup group);

    /**
     * 更新管理
     *
     * @param group
     */
    void update(HostGroup group);

    /**
     * 删除主机
     *
     * @param id
     */
    void delete(@Param("id") Long id);
}
