package org.rainy.minis.web.handler;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface HandlerMapping {

    HandlerMethod getHandler(HttpServletRequest request);

}
