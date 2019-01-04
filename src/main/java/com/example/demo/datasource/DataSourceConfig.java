package com.example.demo.datasource;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.datasource.model.CounterModel;
import com.example.demo.datasource.model.CreatureModel;
import com.example.demo.datasource.model.KLogModel;
import com.example.demo.datasource.model.PlayerModel;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 主資料庫配置，只能有一個@Primary的配置
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	entityManagerFactoryRef = DataSourceConfig.ENTITY_MANAGER, //
	transactionManagerRef = DataSourceConfig.TRANSACTION, //
	basePackages = {DataSourceConfig.REPO_PACKAGE} //
)
public class DataSourceConfig {

	public static final String DB_NAME = "data";
	public static final String REPO_PACKAGE = "com.example.demo.datasource.repo";

	public static final String DATA_SOURCE = "dataSource" + DB_NAME;
	public static final String ENTITY_MANAGER = "enityManager" + DB_NAME;
	public static final String TRANSACTION = "transaction" + DB_NAME;

	@Value("${datasource." + DB_NAME + ".driverClassName}")
	private String driverClassName;

	@Value("${datasource." + DB_NAME + ".url}")
	private String url;

	@Value("${datasource." + DB_NAME + ".username}")
	private String username;

	@Value("${datasource." + DB_NAME + ".password}")
	private String password;

	/**
	 * 資料庫來源
	 */
	@Primary
	@Bean(name = DATA_SOURCE)
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setMaximumPoolSize(16);
		return dataSource;
	}

	/**
	 * 資料庫 實例管理器工廠配置(entity位置)
	 */
	@Primary
	@Bean(name = ENTITY_MANAGER)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		LocalContainerEntityManagerFactoryBean em = packageEntities(builder).build();
		Properties properties = new Properties();
		properties.setProperty("hibernate.physical_naming_strategy",
				"org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
		em.setJpaProperties(properties);
		return em;
	}

	/**
	 * 資料庫事務管理器配置
	 */
	@Primary
	@Bean(name = TRANSACTION)
	public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory(builder).getObject());
		return txManager;
	}
	
	/**
	 * 添加提供repository的所有entity
	 */
	private Builder packageEntities(EntityManagerFactoryBuilder fBuilder) {
		Builder builder = fBuilder.dataSource(dataSource());
		
		// 角色存檔
		builder.packages(PlayerModel.class);
		
		// 生物設定
		builder.packages(CreatureModel.class);
		
		// 測試併發計數器
		builder.packages(CounterModel.class);
		
		// 測試用log
		builder.packages(KLogModel.class);
		
		return builder;
	}

}
