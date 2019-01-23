package com.pphh.dfw.core.exception;

/**
 * Created by Shanzhen on 2019/1/23.
 */
public class DfwException extends Exception {

    public DfwException(String message) {
        super(message);
    }

    public DfwException(String message, Exception cause) {
        super(message, cause);
    }

}
