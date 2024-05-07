package org.rainy.minis.beans.factory;

import org.rainy.minis.exception.BeanException;

import java.util.Map;

/**
 * <p>
 * 将 BeanFactory 内部管理的 Bean 作为一个集合来对待，获取Bean 的数量，得到所有 Bean 的名字，按照某个类型获取 Bean 列表等等。
 * </p>
 *
 * @author zhangyu
 */
public interface ListableBeanFactory extends BeanFactory {

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansForType(Class<T> type) throws BeanException;

}
