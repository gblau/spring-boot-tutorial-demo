package com.gblau;

import com.gb.common.util.Log;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;

/**
 * @author gblau
 * @date 2017-08-30
 */
public class MybatisConfig {
    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/blog?" +
                "useUnicode=true&" +
                "characterEncoding=UTF-8&" +
                "connectTimeout=60000&" +
                "socketTimeout=60000&" +
                "allowMultiQueries=true");
        dataSource.setUser("root");
        dataSource.setPassword("root");// elecon
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dataSource.setAcquireIncrement(5);
        dataSource.setInitialPoolSize(10);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(60);
        dataSource.setMaxIdleTime(120);
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sessionFactory() {
        SqlSessionFactoryBean sFactoryBean = new SqlSessionFactoryBean();
        sFactoryBean.setDataSource(this.dataSource());
        String packageSearchPath = "classpath*:mappers/*.xml";
        Resource[] resources = null;
        try {
            resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
        } catch (IOException e) {
            Log.error(e.getLocalizedMessage());
        }
        sFactoryBean.setMapperLocations(resources);
        sFactoryBean.setTypeAliasesPackage("com.gb.common.model.po");

        return sFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sessionTemplate() {
        SqlSessionTemplate sst = null;
        try {
            sst = new SqlSessionTemplate(sessionFactory().getObject());
        } catch (Exception e) {
            Log.error(e.getLocalizedMessage());
            return null;
        }
        return sst;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        return filter;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapper = new MapperScannerConfigurer();
        mapper.setBasePackage("com.gblau.engine.mapper");
        mapper.setSqlSessionFactoryBeanName("sessionFactory");
        return mapper;
    }
}