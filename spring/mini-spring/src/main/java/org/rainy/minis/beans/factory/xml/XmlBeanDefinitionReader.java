package org.rainy.minis.beans.factory.xml;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.rainy.minis.beans.config.ConstructorArguments;
import org.rainy.minis.beans.config.PropertyArguments;
import org.rainy.minis.beans.config.BeanDefinition;
import org.rainy.minis.beans.factory.BeanFactory;
import org.rainy.minis.beans.factory.support.SimpleBeanFactory;
import org.rainy.minis.exception.BeanException;
import org.rainy.minis.resource.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class XmlBeanDefinitionReader {

    private final BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element beanElement = (Element) resource.next();

            String beanId = beanElement.attributeValue("id");
            String className = beanElement.attributeValue("class");
            String lazy = beanElement.attributeValue("lazy-init");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, className);
            if (StringUtils.isNotEmpty(lazy)) {
                beanDefinition.setLazyInit(Boolean.parseBoolean(lazy));
            }
            List<Element> propertyElements = beanElement.elements("property");
            PropertyArguments propertyArguments = new PropertyArguments();
            List<String> refs = new ArrayList<>();
            for (Element propertyElement : propertyElements) {
                String type = propertyElement.attributeValue("type");
                String name = propertyElement.attributeValue("name");
                String value = propertyElement.attributeValue("value");
                String ref = propertyElement.attributeValue("ref");
                boolean isRef = true;
                if (StringUtils.isEmpty(ref)) {
                    isRef = false;
                } else {
                    value = ref;
                    refs.add(ref);
                }

                propertyArguments.addPropertyValue(
                        new PropertyArguments.PropertyValue(type, name, value, isRef)
                );
            }
            beanDefinition.setPropertyArguments(propertyArguments);

            List<Element> constructorElements = beanElement.elements("constructor-arg");
            ConstructorArguments constructorArguments = new ConstructorArguments();
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
                constructorArguments.addConstructValue(
                        new ConstructorArguments.ConstructorValue(type, name, value, isRef)
                );
            }
            beanDefinition.setConstructorArguments(constructorArguments);

            beanDefinition.setDependsOn(refs.toArray(new String[0]));
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }

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
