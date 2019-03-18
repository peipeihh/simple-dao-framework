package com.pphh.dfw.core.function;

import java.util.Objects;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 2019/3/18
 */
@FunctionalInterface
public interface DfwFunction {

    /**
     * Performs this operation on the given argument.
     */
    void accept() throws Exception;

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default DfwFunction andThen(DfwFunction after) {
        Objects.requireNonNull(after);
        return () -> {
            accept();
            after.accept();
        };
    }
}
