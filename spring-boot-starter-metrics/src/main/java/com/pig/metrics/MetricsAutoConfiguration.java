package com.pig.metrics;

import com.dianping.cat.Cat;
import com.dianping.cat.servlet.CatFilter;
import com.pig.metrics.mybatis.CatMybatisInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Slf4j
@Configuration
@EnableConfigurationProperties
@ConditionalOnClass(Cat.class)
public class MetricsAutoConfiguration {

    /**
     * mvc
     */
    @ConditionalOnClass(DispatcherServlet.class)
    @Bean
    public FilterRegistrationBean catFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CatFilter filter = new CatFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("cat-filter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * mybatis
     */
    @ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
    @Bean
    public Interceptor catInterceptor() {
        return new CatMybatisInterceptor();
    }
}
