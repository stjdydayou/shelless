package com.axungu.webshell.core.service;

import com.axungu.common.exception.ServiceException;
import com.axungu.webshell.core.model.HostAuth;

import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:39
 */
public interface HostAuthService {
    /**
     * 分页查询主机列表
     *
     * @return
     */
    List<HostAuth> findAll();

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    HostAuth findById(Long id);

    /**
     * 保存管理
     *
     * @param auth
     */
    void insertOrUpdate(HostAuth auth);

    /**
     * 删除主机
     *
     * @param ids
     * @throws ServiceException
     */
    void delete(Long[] ids) throws ServiceException;
}
