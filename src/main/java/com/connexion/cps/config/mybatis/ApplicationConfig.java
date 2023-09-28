package com.connexion.cps.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * mybatis mapper scan
 *
 * 
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.connexion.cps.mapper.*")
public class ApplicationConfig
{

}
