package org.example.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
public class HibernateConfig {

    private StandardServiceRegistry standardServiceRegistry;

    private SessionFactory sessionFactory;

    private List<Session> sessions = new ArrayList<>();

    @Value("${hibernate.properties-path}")
    private String hibernateProperties;

    @PostConstruct
    public void configureHibernate() {
        StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
        this.standardServiceRegistry = standardServiceRegistryBuilder.configure(hibernateProperties).build();
        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
        Metadata metadata = metadataSources.buildMetadata();
        this.sessionFactory = metadata.buildSessionFactory();
    }

    @Bean
    @Scope("prototype")
    public Session session() {
        Session session = sessionFactory.openSession();
        sessions.add(session);
        return session;
    }

    @PreDestroy
    public void closeResources() {
        sessions.forEach(Session::close);
        sessionFactory.close();
        standardServiceRegistry.close();
    }
}