package com.example.bootkotlin

import com.alibaba.druid.pool.DruidDataSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.sql.SQLException
import javax.annotation.Resource


@RunWith(SpringRunner::class)
@SpringBootTest()
@ActiveProfiles("multi-datasource")
class DruidMultiDataSourceTestCase {

    @Resource
    private val dataSourceCluster: DruidDataSource?=null
    @Resource
    private val dataSourceMaster: DruidDataSource? = null

    @Test
    @Throws(SQLException::class)
    fun testDataSourceOne() {

        assertThat(dataSourceCluster!!.url).isEqualTo("jdbc:mysql://localhost:3306/kotlin")
        assertThat(dataSourceCluster!!.username).isEqualTo("root")
        assertThat(dataSourceCluster!!.password).isEqualTo("123456")
        assertThat(dataSourceCluster!!.driverClassName).isEqualTo("com.mysql.jdbc.Driver")
    }

    @Test
    @Throws(SQLException::class)
    fun testDataSourceTwo() {

        assertThat(dataSourceMaster!!.url).isEqualTo("jdbc:mysql://localhost:3306/springboot")
        assertThat(dataSourceMaster!!.username).isEqualTo("root")
        assertThat(dataSourceMaster!!.password).isEqualTo("123456")
        assertThat(dataSourceMaster!!.driverClassName).isEqualTo("com.mysql.jdbc.Driver")
    }

}