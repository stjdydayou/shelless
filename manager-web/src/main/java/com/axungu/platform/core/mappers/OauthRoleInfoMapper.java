package com.axungu.platform.core.mappers;


import com.axungu.platform.core.model.OauthRoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OauthRoleInfoMapper {

    List<OauthRoleInfo> findAll();

    int deleteById(@Param("id") Long id);

    int insert(OauthRoleInfo oauthRoleInfo);

    OauthRoleInfo findById(@Param("id") Long id);

    int updateById(OauthRoleInfo oauthRoleInfo);

    void deleteAuthorities(@Param("roleId") Long roleId);

    void insertAuthorities(@Param("authorities") String[] authorities, @Param("roleId") Long roleId);

    List<String> findAuthorities(@Param("roleId") Long roleId);

}