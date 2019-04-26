package com.dliyun.fort.gateway.core.service;

import com.dliyun.fort.gateway.core.model.HostGroup;
import com.dliyun.platform.common.exception.ServiceException;

import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:39
 */
public interface HostGroupService {
    /**
     * 分页查询主机分组列表
     *
     * @return
     */
    List<HostGroup> findAll();

    /**
     * 查询主机分组
     *
     * @param id
     * @return
     */
    HostGroup findById(Long id);

    /**
     * 插入或者仲想主机分组
     *
     * @param group
     */
    void insertOrUpdate(HostGroup group);


    /**
     * 删除主机分组
     *
     * @param ids
     * @throws ServiceException
     */
    void delete(Long[] ids) throws ServiceException;

    /**
     * 查询主机分组被授权的用户ID
     *
     * @param groupId
     */
    List<Long> findUserIds(Long groupId);


    /**
     * 保存主机分组被授权的用户ID
     *
     * @param groupId
     * @param userIds
     */
    void insertOrUpdateGroupUsers(Long groupId, Long[] userIds) throws ServiceException;
}
