package com.axungu.webshell.core.mappers;

import com.axungu.common.paginator.domain.PageBounds;
import com.axungu.common.paginator.domain.PageList;
import com.axungu.webshell.core.model.HostInfo;
import com.axungu.webshell.core.vo.HostInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:39
 */
public interface HostInfoMapper {
    /**
     * 分页查询主机列表
     *
     * @param bounds
     * @param vo
     * @return
     */
    PageList<HostInfo> findPage(PageBounds bounds, HostInfoVO vo);

    /**
     * 查询主机yung
     *
     * @param id
     * @return
     */
    HostInfo findById(@Param("id") Long id);

    /**
     * 添加管理
     *
     * @param info
     */
    void insert(HostInfo info);

    /**
     * 更新管理
     *
     * @param info
     */
    void update(HostInfo info);

    /**
     * 删除主机
     *
     * @param id
     */
    void delete(@Param("id") Long id);
}
