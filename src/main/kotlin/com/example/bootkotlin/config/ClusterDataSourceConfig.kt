package com.example.bootkotlin.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
        entityManagerFactoryRef = "entityManagerFactoryCluster",
        transactionManagerRef = "transactionManagerCluster",
        basePackages = ["com.example.bootkotlin.repository.cluster"]) //设置Repository所在位置
class ClusterDataSourceConfig {

    @Autowired
    @Qualifier("dataSourceCluster")
    private val dataSourceCluster: DataSource? = null

    @Autowired(required = false)
    private val jpaProperties: JpaProperties? = null

    @Bean(name = ["entityManagerCluster"])
    fun entityManagerCluster(builder: EntityManagerFactoryBuilder): EntityManager {
        return entityManagerFactoryCluster(builder).`object`.createEntityManager()
    }

    @Bean(name = ["entityManagerFactoryCluster"])
    fun entityManagerFactoryCluster(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder
                .dataSource(dataSourceCluster)
                .properties(getVendorProperties(dataSourceCluster))
                .packages("com.example.bootkotlin.entity.cluster") //设置实体类所在位置
                .persistenceUnit("clusterPersistenceUnit")
                .build()
    }

    private fun getVendorProperties(dataSource: DataSource?): Map<String, String> {
        return jpaProperties!!.getHibernateProperties(dataSource)
    }

    @Bean(name = ["transactionManagerCluster"])
    fun transactionManagerCluster(builder: EntityManagerFactoryBuilder): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactoryCluster(builder).`object`)
    }

}