package org.rainy.minis.web.context;

import org.rainy.minis.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class WebApplicationContextImpl extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;

    public WebApplicationContextImpl(String xmlPath) {
        super(xmlPath);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}
