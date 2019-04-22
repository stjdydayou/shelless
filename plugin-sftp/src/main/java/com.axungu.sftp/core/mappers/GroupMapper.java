package com.axungu.sftp.core.mappers;

import java.util.List;
import java.util.Map;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:39
 */
public interface GroupMapper {
    /**
     * 分页查询主机列表
     *
     * @return
     */
    List<Map> findAll();
}
