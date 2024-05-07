package org.rainy.minis.web.handler;

import org.rainy.minis.beans.annotation.Controller;
import org.rainy.minis.exception.BeanException;
import org.rainy.minis.web.annotation.RequestMapping;
import org.rainy.minis.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class RequestHandlerMapping implements HandlerMapping {

    private final WebApplicationContext context;
    private final MappingRegistry mappingRegistry;

    public RequestHandlerMapping(WebApplicationContext context) throws BeanException {
        this.context = context;
        this.mappingRegistry = new MappingRegistry();
        initMapping();
    }


    @Override
    public HandlerMethod getHandler(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        Method method = this.mappingRegistry.getMethodMapping(servletPath);
        Object controller = this.mappingRegistry.getObjectMapping(servletPath);
        if (method == null || controller == null) {
            return null;
        }

        return HandlerMethod.builder()
                .bean(controller)
                .method(method)
                .parameters(method.getParameters())
                .returnType(method.getReturnType())
                .build();
    }


    private void initMapping() throws BeanException {
        for (String beanName : this.context.getBeanNames()) {
            Object bean = this.context.getBean(beanName);
            Class<?> beanClass = bean.getClass();
            if (!beanClass.isAnnotationPresent(Controller.class)) {
                continue;
            }
            String parentUrl = "";
            RequestMapping requestMapping = beanClass.getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                parentUrl = requestMapping.value();
            }

            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                requestMapping = method.getAnnotation(RequestMapping.class);
                if (requestMapping == null) {
                    continue;
                }
                String url = parentUrl + requestMapping.value();
                this.mappingRegistry.addObjectMapping(url, bean);
                this.mappingRegistry.addMethodMapping(url, method);
            }
        }
    }


}
