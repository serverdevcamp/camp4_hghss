package com.rest.recruit.config;



import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;


/**
 * MYSQL을 위한 CONFIG 파일
 * 모든 Config 파일은 보안에 취약하므로 gitignore를 꼭 합시다.
 * 본 프로젝트에서는 코드를 보여주기위해 gitignore에 추가하지 않음.
 */
@Configuration
@MapperScan(value = "com.rest.recruit.mapper")
public class AppConfig {
    @Bean
    public DataSource getDataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost:3306/hghssdb?allowMultiQueries=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC");
        dataSource.setPassword("welcome1");

        return dataSource;
    }
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setConfiguration(configuration);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sessionFactory.getObject();
    }
}