package org.rainy.minis.core.env;

/**
 * <p>
 * 继承 PropertyResolver 接口，用于获取属性
 * </p>
 *
 * @author zhangyu
 */
public interface Environment extends PropertyResolver {

    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptsProfiles(String... profiles);

}
