package org.rainy.minis.web.handler;

import org.rainy.minis.web.annotation.RequestParam;
import org.rainy.minis.web.property.PropertyEditor;
import org.rainy.minis.web.property.PropertyEditorRegistrySupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class RequestHandlerAdapter implements HandlerAdapter {

    private final PropertyEditorRegistrySupport propertyEditorRegistrySupport;

    public RequestHandlerAdapter(PropertyEditorRegistrySupport propertyEditorRegistrySupport) {
        this.propertyEditorRegistrySupport = propertyEditorRegistrySupport;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
        if (handlerMethod == null) {
            return;
        }
        Object result = null;
        Object bean = handlerMethod.getBean();
        Method method = handlerMethod.getMethod();
        Parameter[] methodParameters = handlerMethod.getParameters();
        try {
            Object[] parameters = new Object[methodParameters.length];
            for (int i = 0; i < methodParameters.length; i++) {
                Parameter parameter = methodParameters[i];

                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                if (requestParam == null) {
                    continue;
                }
                String parameterName = requestParam.value();
                String parameterValue = request.getParameter(parameterName);
                if (parameterValue == null && requestParam.required()) {
                    throw new IllegalArgumentException(String.format("request parameter「%s」 is required", parameterName));
                }

                PropertyEditor editor = this.propertyEditorRegistrySupport.getEditor(parameter.getType());
                editor.setValue(parameterValue);
                parameters[i] = editor.getValue();
            }

            result = method.invoke(bean, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (result != null) {
            try {
                response.getWriter().write(result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
