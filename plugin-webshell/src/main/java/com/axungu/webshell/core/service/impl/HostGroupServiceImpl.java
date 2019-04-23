package com.axungu.webshell.core.service.impl;

import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.utils.DateUtil;
import com.axungu.webshell.core.mappers.HostGroupMapper;
import com.axungu.webshell.core.model.HostGroup;
import com.axungu.webshell.core.service.HostGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 21:23
 */
@Service
public class HostGroupServiceImpl implements HostGroupService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private HostGroupMapper hostGroupMapper;

    @Override
    public List<HostGroup> findAll() {
        return hostGroupMapper.findAll();
    }

    @Override
    public HostGroup findById(Long id) {
        return hostGroupMapper.findById(id);
    }

    @Override
    public void insertOrUpdate(HostGroup info) {
        if (info.getId() == null) {
            info.setCreatedTime(DateUtil.current());
            this.hostGroupMapper.insert(info);
        } else {
            this.hostGroupMapper.update(info);
        }
    }


    @Override
    public void delete(Long[] ids) throws ServiceException {
        Exception exp = this.transactionTemplate.execute(status -> {
            if (ids == null) {
                return null;
            }
            for (Long id : ids) {
                this.hostGroupMapper.delete(id);
            }
            return null;
        });
        if (exp != null) {
            throw new ServiceException(exp);
        }
    }
}
