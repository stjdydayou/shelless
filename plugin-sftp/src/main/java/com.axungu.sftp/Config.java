package com.axungu.sftp;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 系统启动配置
 *
 * @author jtoms
 */
@Configuration("SftpConfig")
@MapperScan(basePackages = "com.axungu.sftp.core.mappers")
public class Config {

}

