package com.sunday.engine.databank;

import com.sunday.engine.common.Data;

import java.util.HashMap;
import java.util.Map;

public class ClassSensor<T extends Data> implements Data {
    private static Map<Class<? extends Data>, ClassSensor> map = new HashMap<>();
    private Class<T> sensedClass;
    private Data sensedInstance;

    public static ClassSensor getClassSensor(Class<? extends Data> clazz) {
        if (map.keySet().contains(clazz)) {
            return map.get(clazz);
        } else {
            ClassSensor classSensor = new ClassSensor(clazz);
            map.put(clazz, classSensor);
            return classSensor;
        }
    }

    public Data getSensoredInstance() {
        return sensedInstance;
    }

    public Class<T> getSensedClass() {
        return sensedClass;
    }

    private ClassSensor(Class<T> clazz) {
        sensedClass = clazz;
    }

    protected void setSensedInstance(Data sensedInstance) {
        this.sensedInstance = sensedInstance;
    }
}