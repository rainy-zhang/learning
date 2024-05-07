package org.rainy.minis.beans.xml;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.rainy.minis.beans.config.BeanDefinition;
import org.rainy.minis.beans.config.ConstructorArgumentValues;
import org.rainy.minis.beans.config.PropertyArgumentValues;
import org.rainy.minis.beans.support.BeanDefinitionRegistry;
import org.rainy.minis.core.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class BeanDefinitionReader {

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public BeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinition(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();

            if ("bean".equals(element.getName())) {
                loadBeanDefinitions(element);
            }

            if ("component-scan".equals(element.getName())) {
                loadComponentScan(element);
            }
        }
    }

    private void loadComponentScan(Element element) {
        this.beanDefinitionRegistry.registerBeanDefinitionByPackage(
                element.attributeValue("base-package")
        );
    }


    private void loadBeanDefinitions(Element beanElement) {
        String beanId = beanElement.attributeValue("id");
        String className = beanElement.attributeValue("class");
        String lazyInit = beanElement.attributeValue("lazy-init");
        String initMethod = beanElement.attributeValue("init-method");

        BeanDefinition beanDefinition = new BeanDefinition(beanId, className);
        if (StringUtils.isNotEmpty(lazyInit)) {
            beanDefinition.setLazyInit(Boolean.parseBoolean(lazyInit));
        }
        beanDefinition.setInitMethod(initMethod);
        List<Element> propertyElements = beanElement.elements("property");
        PropertyArgumentValues propertyArgumentValues = new PropertyArgumentValues();
        List<String> refs = new ArrayList<>();
        for (Element propertyElement : propertyElements) {
            String name = propertyElement.attributeValue("name");
            String type = propertyElement.attributeValue("type");
            String value = propertyElement.attributeValue("value");
            String ref = propertyElement.attributeValue("ref");

            if (type.equals("java.util.Map")) {
                Map<String, String> map = new HashMap<>();
                List<Element> entryElements = propertyElement.element("map").elements("entry");
                for (Element entryElement : entryElements) {
                    String entryKey = entryElement.attributeValue("key");
                    String entryValue = entryElement.attributeValue("value");
                    map.put(entryKey, entryValue);
                }

                propertyArgumentValues.addPropertyValue(
                        new PropertyArgumentValues.PropertyValue(type, name, map, false)
                );

                continue;
            }

            boolean isRef = true;
            if (StringUtils.isEmpty(ref)) {
                isRef = false;
            } else {
                value = ref;
                refs.add(ref);
            }

            propertyArgumentValues.addPropertyValue(
                    new PropertyArgumentValues.PropertyValue(type, name, value, isRef)
            );
        }
        beanDefinition.setPropertyArgumentValues(propertyArgumentValues);

        List<Element> constructorElements = beanElement.elements("constructor-arg");
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        for (Element constructorElement : constructorElements) {
            String type = constructorElement.attributeValue("type");
            String name = constructorElement.attributeValue("name");
            String value = constructorElement.attributeValue("value");
            String ref = constructorElement.attributeValue("ref");
            boolean isRef = true;
            if (StringUtils.isEmpty(ref)) {
                isRef = false;
            } else {
                value = ref;
                refs.add(ref);
            }
            constructorArgumentValues.addConstructValue(
                    new ConstructorArgumentValues.ConstructorArgumentValue(type, name, value, isRef)
            );
        }
        beanDefinition.setConstructorArgumentValues(constructorArgumentValues);

        beanDefinition.setDependsOn(refs.toArray(new String[0]));
        this.beanDefinitionRegistry.registerBeanDefinition(beanDefinition);

        // 所有beanDefinition都加载完成后，再开始初始化bean，否则被引用的bean不存在时初始化会报错
//        for (BeanDefinition beanDefinition : this.beanFactory.getBeanDefinitions()) {
//            if (!beanDefinition.isLazyInit()) {
//                try {
//                    beanFactory.getBean(beanDefinition.getId());
//                } catch (BeanException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }


}
