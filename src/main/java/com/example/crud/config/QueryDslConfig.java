package com.example.crud.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;


// query factory config for dsl
@Configuration
public class QueryDslConfig {
    @Bean
    public JPAQueryFactory getJPAQueryFactory(EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }
}

