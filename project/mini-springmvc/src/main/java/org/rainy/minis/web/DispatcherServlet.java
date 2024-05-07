package org.rainy.minis.web;

import org.rainy.minis.exception.BeanException;
import org.rainy.minis.web.context.WebApplicationContext;
import org.rainy.minis.web.handler.*;
import org.rainy.minis.web.property.PropertyEditor;
import org.rainy.minis.web.property.PropertyEditorRegistrySupport;
import org.rainy.minis.web.property.WebBindingInitializer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class DispatcherServlet extends HttpServlet {

    private WebApplicationContext applicationContext;
    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.applicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        this.refresh();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HandlerMethod handler = this.handlerMapping.getHandler(request);
        this.handlerAdapter.handle(request, response, handler);
    }

    private void refresh() {
        this.initHandlerMapping();
        this.initHandlerAdapter();
    }


    private void initHandlerMapping() {
        try {
            this.handlerMapping = new RequestHandlerMapping(this.applicationContext);
        } catch (BeanException e) {
            e.printStackTrace();
        }
    }

    private void initHandlerAdapter() {
        PropertyEditorRegistrySupport propertyEditorRegistrySupport = new PropertyEditorRegistrySupport();
        try {
            WebBindingInitializer bindingInitializer = (WebBindingInitializer) this.applicationContext.getBean("webBindingInitializer");
            if (bindingInitializer != null) {
                for (Map.Entry<Class<?>, PropertyEditor> propertyEditorEntry : bindingInitializer.getPropertyEditorMap().entrySet()) {
                    propertyEditorRegistrySupport.registerCustomEditor(propertyEditorEntry.getKey(), propertyEditorEntry.getValue());
                }
            }
        } catch (BeanException e) {
            e.printStackTrace();
        }
        this.handlerAdapter = new RequestHandlerAdapter(propertyEditorRegistrySupport);
    }


}
