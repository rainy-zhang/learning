package org.rainy.minis.web.property;

import org.springframework.util.NumberUtils;

import java.text.NumberFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class NumberEditor implements PropertyEditor {

    private Number value;
    private NumberFormat format;
    private final Class<? extends Number> numberType;

    public NumberEditor(Class<? extends Number> numberType, NumberFormat format) {
        this.numberType = numberType;
        this.format = format;
    }

    public NumberEditor(Class<? extends Number> numberType) {
        this.numberType = numberType;
    }

    @Override
    public void setValue(Object value) {
        this.value = NumberUtils.parseNumber((String) value, this.numberType, this.format);
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}
