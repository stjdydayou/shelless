package com.axungu.webshell.core.service.impl;

import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.paginator.domain.PageBounds;
import com.dliyun.platform.common.paginator.domain.PageResult;
import com.dliyun.platform.common.utils.DateUtil;
import com.axungu.webshell.core.mappers.HostInfoMapper;
import com.axungu.webshell.core.model.HostInfo;
import com.axungu.webshell.core.service.HostInfoService;
import com.axungu.webshell.core.vo.HostInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 21:26
 */

@Slf4j
@Service
public class HostInfoServiceImpl implements HostInfoService {

    @Autowired
    private HostInfoMapper hostInfoMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public PageResult<HostInfo> findPage(PageBounds bounds, HostInfoVO vo) {
        return hostInfoMapper.findPage(bounds, vo).getPageResult();
    }

    @Override
    public HostInfo findById(Long id) {
        return hostInfoMapper.findById(id);
    }

    @Override
    public void insertOrUpdate(HostInfo info) {
        if (info.getId() == null) {
            info.setCreatedTime(DateUtil.current());
            this.hostInfoMapper.insert(info);
        } else {
            this.hostInfoMapper.update(info);
        }
    }

    @Override
    public void delete(Long[] ids) throws ServiceException {

        Exception exp = this.transactionTemplate.execute(status -> {
            if (ids == null) {
                return null;
            }
            for (Long id : ids) {
                this.hostInfoMapper.delete(id);
            }
            return null;
        });
        if (exp != null) {
            throw new ServiceException(exp);
        }
    }
}
