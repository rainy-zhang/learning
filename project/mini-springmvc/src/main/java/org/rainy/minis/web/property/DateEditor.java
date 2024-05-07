package org.rainy.minis.web.property;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class DateEditor implements PropertyEditor {

    private Date value;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void setValue(Object value) {
        try {
            this.value = dateFormat.parse((String) value);
        } catch (ParseException e) {
            try {
                this.value = dateTimeFormat.parse((String) value);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}
