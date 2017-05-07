package com.github.weeniearms.graffiti.generator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class GraphGeneratorFactory implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public GraphGenerator create(String source) {
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();

        if (source.trim().startsWith("@startuml")) {

            return
                    (GraphGenerator) autowireCapableBeanFactory.autowire(
                            PlantUmlGraphGenerator.class,
                            AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR,
                            false);
        }

        throw new IllegalArgumentException("Source format not supported");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
