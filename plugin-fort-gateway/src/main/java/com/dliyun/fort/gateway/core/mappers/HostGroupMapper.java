package com.dliyun.fort.gateway.core.mappers;

import com.dliyun.fort.gateway.core.model.HostGroup;
import com.dliyun.platform.common.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 查询主机分组被授权的用户ID
     *
     * @param groupId
     * @return
     */
    List<Long> findUserIds(@Param("groupId") Long groupId);

    /**
     * 保存主机分组被授权的用户ID
     *
     * @param id
     * @param groupId
     * @param uid
     */
    void insertGroupUser(@Param("id") String id, @Param("groupId") Long groupId, @Param("uid") Long uid);

    /**
     * 删除分组下的用户
     *
     * @param groupId
     */
    void deleteGroupUsers(@Param("groupId") Long groupId);

    /**
     * 查询分组下的主机数量
     *
     * @param groupId
     * @return
     */
    Long findHostCount(@Param("groupId") Long groupId);

}
