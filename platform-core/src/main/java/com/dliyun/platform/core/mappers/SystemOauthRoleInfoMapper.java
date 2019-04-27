package com.dliyun.platform.core.mappers;


import com.dliyun.platform.core.model.SystemOauthRoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jtoms
 */
public interface SystemOauthRoleInfoMapper {

    List<SystemOauthRoleInfo> findAll();

    int deleteById(@Param("id") Long id);

    int insert(SystemOauthRoleInfo oauthRoleInfo);

    SystemOauthRoleInfo findById(@Param("id") Long id);

    int updateById(SystemOauthRoleInfo oauthRoleInfo);

    void deleteAuthorities(@Param("roleId") Long roleId);

    void insertAuthorities(@Param("authorities") String[] authorities, @Param("roleId") Long roleId);

    List<String> findAuthorities(@Param("roleId") Long roleId);

}