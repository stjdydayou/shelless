package com.dliyun.fort.gateway.core.service;

import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.paginator.domain.PageBounds;
import com.dliyun.platform.common.paginator.domain.PageResult;
import com.dliyun.fort.gateway.core.model.HostInfo;
import com.dliyun.fort.gateway.core.vo.HostInfoVO;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:39
 */
public interface HostInfoService {
    /**
     * 分页查询主机列表
     *
     * @param bounds
     * @param vo
     * @return
     */
    PageResult<HostInfo> findPage(PageBounds bounds, HostInfoVO vo);

    /**
     * 查询主机yung
     *
     * @param id
     * @return
     */
    HostInfo findById(Long id);

    /**
     * 添加管理
     *
     * @param info
     */
    void insertOrUpdate(HostInfo info);

    /**
     * 删除主机
     *
     * @param ids
     */
    void delete(Long[] ids) throws ServiceException;
}
