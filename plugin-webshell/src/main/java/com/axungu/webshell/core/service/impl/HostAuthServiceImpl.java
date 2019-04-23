package com.axungu.webshell.core.service.impl;

import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.utils.DateUtil;
import com.axungu.webshell.core.mappers.HostAuthMapper;
import com.axungu.webshell.core.model.HostAuth;
import com.axungu.webshell.core.service.HostAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 21:24
 */
@Service
public class HostAuthServiceImpl implements HostAuthService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private HostAuthMapper hostAuthMapper;

    @Override
    public List<HostAuth> findAll() {
        return hostAuthMapper.findAll();
    }

    @Override
    public HostAuth findById(Long id) {
        return hostAuthMapper.findById(id);
    }

    @Override
    public void insertOrUpdate(HostAuth auth) {
        if (auth.getId() == null) {
            auth.setCreatedTime(DateUtil.current());
            this.hostAuthMapper.insert(auth);
        } else {
            this.hostAuthMapper.update(auth);
        }
    }

    @Override
    public void delete(Long[] ids) throws ServiceException {
        Exception exp = this.transactionTemplate.execute(status -> {
            if (ids == null) {
                return null;
            }
            for (Long id : ids) {
                this.hostAuthMapper.delete(id);
            }
            return null;
        });
        if (exp != null) {
            throw new ServiceException(exp);
        }
    }
}
