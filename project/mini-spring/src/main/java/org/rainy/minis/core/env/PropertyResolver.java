package org.rainy.minis.core.env;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface PropertyResolver {

    boolean containsProperty(String key);

    String getProperty(String key);

    String getProperty(String key, String defaultValue);

    <T> T getProperty(String key, Class<T> targetType);

    <T> T getProperty(String key, Class<T> targetType, T defaultValue);

    <T> Class<T> getPropertyAsClass(String key, Class<T> targetType);

    String getRequireProperty(String key) throws IllegalStateException;

    <T> T getRequireProperty(String key, Class<T> targetType) throws IllegalStateException;

    String resolvePlaceholders(String text);

    String resolveRequirePlaceholders(String text) throws IllegalStateException;

}
