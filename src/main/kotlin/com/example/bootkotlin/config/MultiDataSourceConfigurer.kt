package com.example.bootkotlin.config

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

/**
 * 配置多数据源
 */
@Configuration
@Profile("multi-datasource")
class MultiDataSourceConfigurer {
    @Primary
    //@Bean(initMethod = "init")
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    fun dataSourceMaster(): DataSource {
        return DruidDataSourceBuilder.create().build()
    }

    //@Bean(initMethod = "init")
    @Bean
    @ConfigurationProperties("spring.datasource.druid.cluster")
    fun dataSourceCluster(): DataSource {
        return DruidDataSourceBuilder.create().build()
    }
}