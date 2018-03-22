package com.example.bootkotlin.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManager
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryMaster",
        transactionManagerRef = "transactionManagerMaster",
        basePackages = ["com.example.bootkotlin.repository.master","com.example.bootkotlin.controller"]) //设置Repository所在位置
class MasterDataSourceConfig {

    @Autowired
    @Qualifier("dataSourceMaster")
    private val dataSourceMaster: DataSource? = null

    @Autowired(required = false)
    private val jpaProperties: JpaProperties? = null

    @Primary
    @Bean(name = ["entityManagerMaster"])
    fun entityManagerMaster(builder: EntityManagerFactoryBuilder): EntityManager {
        return entityManagerFactoryMaster(builder).`object`.createEntityManager()
    }

    @Primary
    @Bean(name = ["entityManagerFactoryMaster"])
    fun entityManagerFactoryMaster(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder
                .dataSource(dataSourceMaster)
                .properties(getVendorProperties(dataSourceMaster))
                .packages("com.example.bootkotlin.entity.master","com.example.bootkotlin.controller") //设置实体类所在位置
                .persistenceUnit("masterPersistenceUnit")
                .build()
    }

    private fun getVendorProperties(dataSource: DataSource?): Map<String, String> {
        return jpaProperties!!.getHibernateProperties(dataSource)
    }

    @Primary
    @Bean(name = ["transactionManagerMaster"])
    fun transactionManagerMaster(builder: EntityManagerFactoryBuilder): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactoryMaster(builder).`object`)
    }

}