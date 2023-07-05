package kr.co.kakaopayinscorp.contract.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


class TransactionManager {
}

@Configuration
@EnableTransactionManagement
class TransactionConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }
    @Bean
    @Primary
    fun dataSource(): DataSource {
        return dataSourceProperties().initializeDataSourceBuilder().build()
    }

    @Bean
    @Primary
    fun transactionManager(): JpaTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.dataSource = dataSource()
        return transactionManager
    }
}