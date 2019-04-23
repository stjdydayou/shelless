package com.axungu.platform.core.service;

import com.axungu.common.exception.ServiceException;
import com.axungu.platform.core.model.SystemOauthRoleInfo;

import java.util.List;

/**
 *
 * @author shenyj
 * @date 17-3-24
 */
public interface SystemOauthRoleInfoService {

    /**
     * 查询所有角色
     *
     * @return
     */
    List<SystemOauthRoleInfo> findAll();

    /**
     * 查询一个角色
     *
     * @param id
     * @return
     */
    SystemOauthRoleInfo findById(Long id);

    /**
     * 保存角色
     *
     * @param oauthRoleInfo
     * @throws ServiceException
     */
    void save(SystemOauthRoleInfo oauthRoleInfo) throws ServiceException;

    /**
     * 删除角色
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 设置角色权限
     *
     * @param roleId
     * @param authorities
     * @throws ServiceException
     */
    void setAuthorities(Long roleId, String[] authorities) throws ServiceException;

    /**
     * 查询角色拥有的权限
     *
     * @param roleId
     * @return
     */
    List<String> findAuthorities(Long roleId);

}
