package ru.citydom.testwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.citydom.testwork.endpoint", "ru.citydom.testwork.service", "ru.citydom.testwork.config" })
@EnableJpaRepositories("ru.citydom.testwork.dao")
@EnableAutoConfiguration
@EntityScan(
        basePackageClasses = {  Jsr310JpaConverters.class },
        value = "ru.citydom.testwork.entity"
)
@EnableTransactionManagement
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

/*    @Bean
    public FilterRegistrationBean commonFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/*");
        registration.setName("CommonFilter");
        registration.setOrder(2);
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setIncludeHeaders(false);
        registration.setFilter(loggingFilter);
        return registration;
    }*/


}