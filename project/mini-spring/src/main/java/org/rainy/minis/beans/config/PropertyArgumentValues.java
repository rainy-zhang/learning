package org.rainy.minis.beans.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class PropertyArgumentValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    public void addPropertyValue(String propertyType, String propertyName, Object propertyValue, boolean isRef) {
        this.propertyValueList.add(new PropertyValue(propertyType, propertyName, propertyValue, isRef));
    }

    public boolean containsProperty(String propertyName) {
        return this.getProperty(propertyName) != null;
    }

    public PropertyValue getProperty(String propertyName) {
        for (PropertyValue property : this.propertyValueList) {
            if (property.name.equals(propertyName)) {
                return property;
            }
        }
        return null;
    }

    public PropertyValue getProperty(int index) {
        return this.propertyValueList.get(index);
    }

    public Object getValue(String propertyName) {
        PropertyValue propertyValue = this.getProperty(propertyName);
        return propertyValue == null ? null : propertyValue.value;
    }

    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }

    public int getPropertyValueCount() {
        return this.propertyValueList.size();
    }

    public void removePropertyValue(String propertyName) {
        this.propertyValueList.removeIf(propertyValue -> propertyValue.name.equals(propertyName));
    }

    public void removePropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.remove(propertyValue);
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class PropertyValue {
        private final String type;
        private final String name;
        private final Object value;
        private final boolean isRef;

    }

}
