package org.rainy.minis.web.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class MappingRegistry {

    private final Map<String, Object> urlObjectMappings = new HashMap<>();
    private final Map<String, Method> urlMethodMappings = new HashMap<>();

    public void addObjectMapping(String url, Object obj) {
        this.urlObjectMappings.put(url, obj);
    }

    public void addMethodMapping(String url, Method method) {
        this.urlMethodMappings.put(url, method);
    }

    public Object getObjectMapping(String url) {
        return this.urlObjectMappings.get(url);
    }

    public Method getMethodMapping(String url) {
        return this.urlMethodMappings.get(url);
    }

}
