package com.q1sj.log.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Q1sj
 * @date 2022.8.11 14:11
 */
public class TraceEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            Object property = propertySource.getProperty("logging.pattern.level");
            if (property != null) {
                return;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("logging.pattern.level", "%5p [%X{trace-id}]");
        addOrReplace(propertySources, map, "defaultProperties");
    }

    public static void addOrReplace(MutablePropertySources propertySources, Map<String, Object> map, String propertySourceName) {
        MapPropertySource target = null;
        if (propertySources.contains(propertySourceName)) {
            PropertySource<?> source = propertySources.get(propertySourceName);
            if (source instanceof MapPropertySource) {
                target = (MapPropertySource) source;
                for (String key : map.keySet()) {
                    if (!target.containsProperty(key)) {
                        target.getSource().put(key, map.get(key));
                    }
                }
            }
        }
        if (target == null) {
            target = new MapPropertySource(propertySourceName, map);
        }
        if (!propertySources.contains(propertySourceName)) {
            propertySources.addLast(target);
        }
    }
}
