package com.sunday.engine.databank;

import com.sunday.engine.common.Data;
import com.sunday.engine.common.Signal;

public interface Port<T extends Data> {
    void addDataInstance(T t);

    boolean containsDataInstance(T t);

    void deleteDataInstance(T t);

    void broadcast(T t, Signal signal);
}