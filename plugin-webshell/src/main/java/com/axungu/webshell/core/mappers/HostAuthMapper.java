package com.axungu.webshell.core.mappers;

import com.axungu.webshell.core.model.HostAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:39
 */
public interface HostAuthMapper {
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
    HostAuth findById(@Param("id") Long id);

    /**
     * 添加管理
     *
     * @param auth
     */
    void insert(HostAuth auth);

    /**
     * 更新管理
     *
     * @param auth
     */
    void update(HostAuth auth);

    /**
     * 删除主机
     *
     * @param id
     */
    void delete(@Param("id") Long id);
}
