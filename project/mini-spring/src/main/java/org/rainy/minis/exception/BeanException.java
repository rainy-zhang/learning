package org.rainy.minis.exception;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class BeanException extends Exception {

    public BeanException(Exception e) {
        super(e);
    }

    public BeanException(String message) {
        super(message);
    }

    public BeanException() {
        super();
    }

}
