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
public class ConstructorArgumentValues {

    private final List<ConstructorArgumentValue> constructorArgumentValues = new ArrayList<>();

    public void addConstructValue(ConstructorArgumentValue constructorArgumentValue) {
        this.constructorArgumentValues.add(constructorArgumentValue);
    }

    public ConstructorArgumentValue getConstructorValue(String name) {
        for (ConstructorArgumentValue value : this.constructorArgumentValues) {
            if (value.name != null && !value.name.equals(name)) {
                continue;
            }
            return value;
        }
        return null;
    }

    public ConstructorArgumentValue getConstructorValue(Integer index) {
        return this.constructorArgumentValues.get(index);
    }

    public int getArgumentCount() {
        return this.constructorArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.constructorArgumentValues.isEmpty();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ConstructorArgumentValue {
        private final String type;
        private String name;
        private final Object value;
        private final boolean isRef;

        public ConstructorArgumentValue(String type, Object value, boolean isRef) {
            this.type = type;
            this.value = value;
            this.isRef = isRef;
        }
    }

}
