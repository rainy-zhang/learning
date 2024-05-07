package org.rainy.minis.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface HandlerAdapter {

    void handle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod);

}
