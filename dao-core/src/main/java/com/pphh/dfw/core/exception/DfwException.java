package com.pphh.dfw.core.exception;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 2019/1/23
 */
public class DfwException extends Exception {

    public DfwException(String message) {
        super(message);
    }

    public DfwException(String message, Exception cause) {
        super(message, cause);
    }

}
