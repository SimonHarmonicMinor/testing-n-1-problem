package com.example.demo.testutil;

import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.TestComponent;

import javax.sql.DataSource;

import static com.example.demo.testutil.QueryCountService.QUERY_COUNT_HOLDER;

@TestComponent
public class DatasourceProxyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof DataSource dataSource) {
            return ProxyDataSourceBuilder.create(dataSource)
                       .countQuery(QUERY_COUNT_HOLDER)
                       .build();
        }
        return bean;
    }
}
