package com.egrasoft.fxcommons.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ErrorMap extends LinkedHashMap<Class<? extends Exception>, String> {
    public ErrorMap(List<Class<? extends Exception>> exceptions, List<String> messages) {
        if (exceptions.size() != messages.size())
            throw new IllegalArgumentException();
        putAll(IntStream.range(0, exceptions.size()).boxed().collect(Collectors.toMap(exceptions::get, messages::get)));
    }
}
