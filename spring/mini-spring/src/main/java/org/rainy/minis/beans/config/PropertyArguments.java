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
public class PropertyArguments {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    public void addPropertyValue(String propertyType, String propertyName, Object propertyValue, boolean isRef) {
        this.propertyValueList.add(new PropertyValue(propertyType, propertyName, propertyValue, isRef));
    }

    public boolean containsProperty(String propertyName) {
        return this.getPropertyValue(propertyName) != null;
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue property : this.propertyValueList) {
            if (property.name.equals(propertyName)) {
                return property;
            }
        }
        return null;
    }

    public Object getValue(String propertyName) {
        PropertyValue propertyValue = this.getPropertyValue(propertyName);
        return propertyValue == null ? null : propertyValue.value;
    }

    public List<PropertyValue> getPropertyValues() {
        return this.propertyValueList;
    }

    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
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
