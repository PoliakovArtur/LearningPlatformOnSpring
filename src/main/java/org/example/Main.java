package org.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.scan("org.example.config");

        Runtime.getRuntime().addShutdownHook(new Thread(webContext::close));

        Tomcat tomcat = new Tomcat();
        tomcat.getConnector().setPort(8080);
        Context context = tomcat.addContext("", null);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        Tomcat.addServlet(context, "dispatcherServlet", dispatcherServlet).addMapping("/");
        tomcat.start();
        System.out.println("http://localhost:8080/");
    }
}