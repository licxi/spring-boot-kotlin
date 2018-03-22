package com.example.bootkotlin.config

import com.alibaba.druid.pool.DruidDataSource
import com.alibaba.druid.support.http.StatViewServlet
import com.alibaba.druid.support.http.WebStatFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import java.sql.SQLException
import javax.sql.DataSource

/**
 * 配置Druid
 */
//@Configuration
class DruidConfig {

    @Value("\${spring.datasource.url}")
    private val dbUrl: String? = null

    @Value("\${spring.datasource.username}")
    private val username: String? = null

    @Value("\${spring.datasource.password}")
    private val password: String? = null

    @Value("\${spring.datasource.driver-class-name}")
    private val driverClassName: String? = null

    @Value("\${spring.datasource.initialSize}")
    private val initialSize: Int = 0

    @Value("\${spring.datasource.minIdle}")
    private val minIdle: Int = 0

    @Value("\${spring.datasource.maxActive}")
    private val maxActive: Int = 0

    @Value("\${spring.datasource.maxWait}")
    private val maxWait: Int = 0

    @Value("\${spring.datasource.timeBetweenEvictionRunsMillis}")
    private val timeBetweenEvictionRunsMillis: Int = 0

    @Value("\${spring.datasource.minEvictableIdleTimeMillis}")
    private val minEvictableIdleTimeMillis: Int = 0

    @Value("\${spring.datasource.validationQuery}")
    private val validationQuery: String? = null

    @Value("\${spring.datasource.testWhileIdle}")
    private val testWhileIdle: Boolean = false

    @Value("\${spring.datasource.testOnBorrow}")
    private val testOnBorrow: Boolean = false

    @Value("\${spring.datasource.testOnReturn}")
    private val testOnReturn: Boolean = false

    @Value("\${spring.datasource.filters}")
    private val filters: String? = null

    //@Bean
    fun druidServlet(): ServletRegistrationBean {
        val servletRegistrationBean = ServletRegistrationBean()
        servletRegistrationBean.setServlet(StatViewServlet())
        servletRegistrationBean.addUrlMappings("/druid/*")
        val initParameters = mutableMapOf<String, String>()
        initParameters["loginUsername"] = "admin"// 用户名
        initParameters["loginPassword"] = "admin"// 密码
        initParameters["resetEnable"] = "false"// 禁用HTML页面上的“Reset All”功能
        //initParameters.put("allow", "192.138.1.15") // IP白名单 (没有配置或者为空，则允许所有访问)
        //initParameters.put("deny", "192.168.20.38")// IP黑名单 (存在共同时，deny优先于allow)
        servletRegistrationBean.initParameters = initParameters
        return servletRegistrationBean
    }

    //@Bean
    fun filterRegistrationBean(): FilterRegistrationBean {
        val filterRegistrationBean = FilterRegistrationBean()
        filterRegistrationBean.filter = WebStatFilter()
        filterRegistrationBean.addUrlPatterns("/*")
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*")
        return filterRegistrationBean
    }


    //@Bean
    fun druidDataSource(): DataSource {
        val datasource = DruidDataSource()
        datasource.url = dbUrl
        datasource.username = username
        datasource.password = password
        datasource.driverClassName = driverClassName
        datasource.initialSize = initialSize
        datasource.minIdle = minIdle
        datasource.maxActive = maxActive
        datasource.maxWait = maxWait.toLong()
        datasource.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis.toLong()
        datasource.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis.toLong()
        datasource.validationQuery = validationQuery
        datasource.isTestWhileIdle = testWhileIdle
        datasource.isTestOnBorrow = testOnBorrow
        datasource.isTestOnReturn = testOnReturn
        try {
            datasource.setFilters(filters)
        } catch (e: SQLException) {
            // logger.error("druid configuration initialization filter", e)
            e.printStackTrace()
        }

        return datasource
    }
}