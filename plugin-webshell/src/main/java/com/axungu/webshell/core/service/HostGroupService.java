package com.axungu.webshell.core.service;

import com.dliyun.platform.common.exception.ServiceException;
import com.axungu.webshell.core.model.HostGroup;

import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:39
 */
public interface HostGroupService {
    /**
     * 分页查询主机列表
     *
     * @return
     */
    List<HostGroup> findAll();

    /**
     * 查询主机yung
     *
     * @param id
     * @return
     */
    HostGroup findById(Long id);

    /**
     * 添加管理
     *
     * @param group
     */
    void insertOrUpdate(HostGroup group);


    /**
     * 删除主机
     *
     * @param ids
     */
    void delete(Long[] ids) throws ServiceException;
}
