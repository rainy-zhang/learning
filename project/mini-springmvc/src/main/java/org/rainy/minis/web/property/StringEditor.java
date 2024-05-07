package org.rainy.minis.web.property;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class StringEditor implements PropertyEditor {

    private String value;

    @Override
    public void setValue(Object value) {
        this.value = value.toString();
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}
