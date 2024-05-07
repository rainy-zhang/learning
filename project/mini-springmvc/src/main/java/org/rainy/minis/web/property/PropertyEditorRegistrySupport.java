package org.rainy.minis.web.property;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class PropertyEditorRegistrySupport {

    private final Map<Class<?>, PropertyEditor> customEditors = new HashMap<>();
    private final Map<Class<?>, PropertyEditor> defaultEditors = new HashMap<>();

    public PropertyEditorRegistrySupport() {
        this.initDefaultEditors();
    }

    public PropertyEditor getEditor(Class<?> type) {
        PropertyEditor propertyEditor = this.defaultEditors.get(type);
        if (propertyEditor == null) {
            propertyEditor = this.customEditors.get(type);
        }
        return propertyEditor;
    }

    public void registerCustomEditor(Class<?> type, PropertyEditor propertyEditor) {
        this.customEditors.put(type, propertyEditor);
    }

    private void initDefaultEditors() {
        this.defaultEditors.put(String.class, new StringEditor());
        this.defaultEditors.put(int.class, new NumberEditor(int.class));
        this.defaultEditors.put(long.class, new NumberEditor(long.class));
        this.defaultEditors.put(double.class, new NumberEditor(double.class));
        this.defaultEditors.put(Integer.class, new NumberEditor(Integer.class));
        this.defaultEditors.put(Long.class, new NumberEditor(Long.class));
        this.defaultEditors.put(Double.class, new NumberEditor(Double.class));
    }

}
