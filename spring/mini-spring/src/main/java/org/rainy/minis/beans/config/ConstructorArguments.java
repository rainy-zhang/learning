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
public class ConstructorArguments {

    private final List<ConstructorValue> constructorValues = new ArrayList<>();

    public void addConstructValue(ConstructorValue constructorValue) {
        this.constructorValues.add(constructorValue);
    }

    public List<ConstructorValue> getConstructValues() {
        return this.constructorValues;
    }
    public ConstructorValue getConstructValue(String name) {
        for (ConstructorValue value : this.constructorValues) {
            if (value.name != null && !value.name.equals(name)) {
                continue;
            }
            return value;
        }
        return null;
    }

    public ConstructorValue getConstructValue(Integer index) {
        return this.constructorValues.get(index);
    }

    public boolean isEmpty() {
        return this.constructorValues.isEmpty();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ConstructorValue {
        private final String type;
        private String name;
        private final Object value;
        private final boolean isRef;

        public ConstructorValue(String type, Object value, boolean isRef) {
            this.type = type;
            this.value = value;
            this.isRef = isRef;
        }
    }

}
