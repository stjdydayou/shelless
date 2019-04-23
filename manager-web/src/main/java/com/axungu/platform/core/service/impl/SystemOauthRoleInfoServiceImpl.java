package com.axungu.platform.core.service.impl;

import com.axungu.common.exception.ServiceException;
import com.axungu.platform.core.mappers.SystemOauthRoleInfoMapper;
import com.axungu.platform.core.model.OauthRoleInfo;
import com.axungu.platform.core.service.SystemOauthRoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * Created by shenyj on 17-3-24.
 */
@Service
public class SystemOauthRoleInfoServiceImpl implements SystemOauthRoleInfoService {

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    private SystemOauthRoleInfoMapper systemOauthRoleInfoMapper;

    @Override
    public List<OauthRoleInfo> findAll() {
        return this.systemOauthRoleInfoMapper.findAll();
    }

    @Override
    public OauthRoleInfo findById(Long id) {
        return this.systemOauthRoleInfoMapper.findById(id);
    }

    @Override
    public void save(OauthRoleInfo oauthRoleInfo) throws ServiceException {
        try {
            if (oauthRoleInfo.getId() == null) {
                this.systemOauthRoleInfoMapper.insert(oauthRoleInfo);
            } else {
                this.systemOauthRoleInfoMapper.updateById(oauthRoleInfo);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        this.systemOauthRoleInfoMapper.deleteById(id);
    }

    @Override
    public void setAuthorities(final Long roleId, final String[] authorities) throws ServiceException {
        Exception exp = this.transactionTemplate.execute(status -> {
            try {
                systemOauthRoleInfoMapper.deleteAuthorities(roleId);
                systemOauthRoleInfoMapper.insertAuthorities(authorities, roleId);
                return null;
            } catch (Exception e) {
                status.setRollbackOnly();
                return e;
            }

        });

        if (exp != null) {
            throw new ServiceException(exp);
        }
    }

    @Override
    public List<String> findAuthorities(Long roleId) {
        return this.systemOauthRoleInfoMapper.findAuthorities(roleId);
    }
}
