package com.axungu.platform.core.service.impl;

import com.axungu.common.exception.ServiceException;
import com.axungu.platform.core.mappers.OauthRoleInfoMapper;
import com.axungu.platform.core.model.OauthRoleInfo;
import com.axungu.platform.core.service.OauthRoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * Created by shenyj on 17-3-24.
 */
@Service
public class OauthRoleInfoServiceImpl implements OauthRoleInfoService {

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    private OauthRoleInfoMapper oauthRoleInfoMapper;

    @Override
    public List<OauthRoleInfo> findAll() {
        return this.oauthRoleInfoMapper.findAll();
    }

    @Override
    public OauthRoleInfo findById(Long id) {
        return this.oauthRoleInfoMapper.findById(id);
    }

    @Override
    public void save(OauthRoleInfo oauthRoleInfo) throws ServiceException {
        try {
            if (oauthRoleInfo.getId() == null) {
                this.oauthRoleInfoMapper.insert(oauthRoleInfo);
            } else {
                this.oauthRoleInfoMapper.updateById(oauthRoleInfo);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        this.oauthRoleInfoMapper.deleteById(id);
    }

    @Override
    public void setAuthorities(final Long roleId, final String[] authorities) throws ServiceException {
        Exception exp = this.transactionTemplate.execute(status -> {
            try {
                oauthRoleInfoMapper.deleteAuthorities(roleId);
                oauthRoleInfoMapper.insertAuthorities(authorities, roleId);
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
        return this.oauthRoleInfoMapper.findAuthorities(roleId);
    }
}
