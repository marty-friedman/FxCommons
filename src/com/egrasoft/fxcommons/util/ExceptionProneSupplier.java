package com.egrasoft.fxcommons.util;

public interface ExceptionProneSupplier<T> {
    T run() throws Exception;
}
