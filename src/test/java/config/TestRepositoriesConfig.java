package config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.containers.MySQLContainer;
import org.xml.sax.SAXException;
import repository.XmlHandlerForHibernateConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("org.example.repositories")
public class TestRepositoriesConfig {
    private static final String PROPERTY_SOURCE = "application-test.yml";

    private StandardServiceRegistry standardServiceRegistry;

    private SessionFactory sessionFactory;

    private List<Session> sessions = new ArrayList<>();

    private MySQLContainer<?> mySQLContainer;

    @Value("${hibernate.properties-short-path}")
    private String hibernateShortPath;

    @Value("${hibernate.properties-full-path}")
    private String hibernateFullPath;

    @Value("${hibernate.properties-source-path}")
    private String hibernateSourcePath;

    @Value("${script.path}")
    private String scriptPath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer ymlPropertiesConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource(PROPERTY_SOURCE));
        configurer.setProperties(yamlPropertiesFactoryBean.getObject());
        return configurer;
    }

    @PostConstruct
    public void configureHibernateAndRunContainer() {
        mySQLContainer = new MySQLContainer<>("mysql:latest")
                        .withInitScript(scriptPath);
        mySQLContainer.start();
        String url = mySQLContainer.getJdbcUrl();
        String username = mySQLContainer.getUsername();
        String password = mySQLContainer.getPassword();
        createHibernateConfig(url, username, password);
        try(BufferedReader reader = new BufferedReader(new FileReader(hibernateFullPath))) {
            reader.lines().forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
        this.standardServiceRegistry = standardServiceRegistryBuilder
                .configure(hibernateShortPath)
                .build();
        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
        Metadata metadata = metadataSources.buildMetadata();
        this.sessionFactory = metadata.buildSessionFactory();
    }

    private void createHibernateConfig(String url, String userName, String password) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newDefaultInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XmlHandlerForHibernateConfig handler = new XmlHandlerForHibernateConfig(url, userName, password, hibernateFullPath);
            saxParser.parse(hibernateSourcePath, handler);
            handler.close();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
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
        mySQLContainer.stop();
        sessions.forEach(Session::close);
        sessionFactory.close();
        standardServiceRegistry.close();
    }
}
