package com.application.configuracion.bd;

import org.springframework.stereotype.Service;

@Service
public class ConnectionPoolServiceHikari implements ConnectionPoolService {

    private static final int DOUBLE = 2;
    private static final int EFFECTIVE_SPINDLE_COUNT = 1;

    @Override
    public int obtainSize(String propertyKey) {
        return calculateSize();
    }

    private int calculateSize() {
        int processors = Runtime.getRuntime().availableProcessors();
        return (processors * DOUBLE) + EFFECTIVE_SPINDLE_COUNT;
    }
}
