package org.rainy.minis.web.listener;

import org.rainy.minis.web.context.WebApplicationContext;
import org.rainy.minis.web.context.WebApplicationContextImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class ContextLoaderListener implements ServletContextListener {

    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        String contextLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        WebApplicationContext wac = new WebApplicationContextImpl(contextLocation);
        wac.setServletContext(servletContext);
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }

}
