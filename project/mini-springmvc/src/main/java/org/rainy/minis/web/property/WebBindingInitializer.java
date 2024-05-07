package org.rainy.minis.web.property;

import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class WebBindingInitializer {

    private Map<String, String> propertyEditors;

    @Getter
    private final Map<Class<?>, PropertyEditor> propertyEditorMap = new HashMap<>();

    public void setPropertyEditors(Map<String, String> propertyEditors) {
        for (Map.Entry<String, String> entry : propertyEditors.entrySet()) {
            try {
                Class<?> clazz = Class.forName(entry.getKey());
                PropertyEditor editor = (PropertyEditor) Class.forName(entry.getValue()).getConstructor().newInstance();
                this.propertyEditorMap.put(clazz, editor);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

}
