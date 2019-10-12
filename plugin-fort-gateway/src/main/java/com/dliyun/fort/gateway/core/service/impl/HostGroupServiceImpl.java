package com.dliyun.fort.gateway.core.service.impl;

import com.dliyun.fort.gateway.core.mappers.HostGroupMapper;
import com.dliyun.fort.gateway.core.model.HostGroup;
import com.dliyun.fort.gateway.core.service.HostGroupService;
import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.utils.DateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    public int delete(Long[] ids) throws ServiceException {
        AtomicInteger successCount = new AtomicInteger();
        Exception exp = this.transactionTemplate.execute(status -> {
            if (ids == null) {
                return null;
            }
            for (Long id : ids) {
                Long c = this.hostGroupMapper.findHostCount(id);
                if (c.compareTo(0L) > 0) {
                    continue;
                }
                successCount.getAndIncrement();
                this.hostGroupMapper.delete(id);
                this.hostGroupMapper.deleteGroupUsers(id);
            }
            return null;
        });
        if (exp != null) {
            throw new ServiceException(exp);
        }
        return successCount.get();
    }

    @Override
    public List<Long> findUserIds(Long groupId) {
        return this.hostGroupMapper.findUserIds(groupId);
    }

    @Override
    public void insertOrUpdateGroupUsers(Long groupId, Long[] userIds) throws ServiceException {
        Exception exp = this.transactionTemplate.execute(status -> {
            this.hostGroupMapper.deleteGroupUsers(groupId);
            if (userIds != null && userIds.length > 0) {
                for (Long uid : userIds) {
                    String id = DigestUtils.md5Hex(groupId + "@" + uid);
                    this.hostGroupMapper.insertGroupUser(id, groupId, uid);
                }
            }
            return null;
        });
        if (exp != null) {
            throw new ServiceException(exp);
        }
    }

    @Override
    public Long findHostCount(Long groupId) {
        return this.hostGroupMapper.findHostCount(groupId);
    }
}
