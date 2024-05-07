package org.rainy.minis.beans.support;

import org.rainy.minis.beans.config.BeanDefinition;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class DefaultBeanDefinitionRegistry implements BeanDefinitionRegistry {

    protected final List<String> beanDefinitionNames = new ArrayList<>();
    protected final Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        String beanName = beanDefinition.getId();
        this.beanDefinitions.put(beanName, beanDefinition);
        this.beanDefinitionNames.add(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitions.get(beanName);
    }

    @Override
    public void removeBeanDefinition(String beanName) {
        this.beanDefinitions.remove(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(new String[0]);
    }

    @Override
    public void registerBeanDefinitionByPackage(String packageName) {
        List<String> classNames = scanPackage(packageName);
        for (String className : classNames) {
            String beanName = className.substring(className.lastIndexOf("."));
            this.registerBeanDefinition(
                    new BeanDefinition(beanName, className)
            );
        }
    }

    private List<String> scanPackage(String packageName) {
        // 根据包名获取绝对路径
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        if (url == null) {
            return Collections.emptyList();
        }
        List<String> classNames = new ArrayList<>();
        File packageFile = new File(url.getFile());
        File[] files = packageFile.listFiles();
        if (files == null) {
            return classNames;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                classNames.addAll(
                        scanPackage(file.getPath())
                );
            } else {
                classNames.add(packageName + "." + file.getName().replace(".class", ""));
            }
        }
        return classNames;
    }

}
