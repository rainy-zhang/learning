package org.rainy.minis.beans.factory;

/**
 * <p>
 * 将 AutowireCapableBeanFactory、ListableBeanFactory 和 ConfigurableBeanFactory 合并在一起。
 * </p>
 *
 * @author zhangyu
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory {


}
