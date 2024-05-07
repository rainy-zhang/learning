package org.rainy.minis.web.context;

import org.rainy.minis.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface WebApplicationContext extends ApplicationContext {

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();

    void setServletContext(ServletContext servletContext);

}
