package com.sunday.engine.common.register;


import com.sunday.engine.common.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class MappingRegister<K, V> implements Register<K, V>, UsingSingleValue<K, V> {
    protected Map<K, V> map = new HashMap<>();

    @Override
    public List<K> getKeys() {
        List<K> list = new ArrayList<>();
        map.keySet().forEach(list::add);
        return list;
    }

    @Override
    public K keyOf(V v) {
        return getKeys().stream().filter(key -> map.get(key).equals(v)).findFirst().orElse(null);
    }

    @Override
    public boolean hasKey(K k) {
        return map.containsKey(k);
    }

    @Override
    public boolean hasValue(V v) {
        return map.containsValue(v);
    }

    @Override
    public void register(K k, V v) {
        map.put(k, v);
    }

    @Override
    public void deregister(K k, V v) {
        if (hasKey(k))
            map.remove(k, v);
    }

    @Override
    public void deregisterKey(K k) {
        map.remove(k);
    }

    @Override
    public void foreachPaar(BiConsumer<K, V> biConsumer) {
        map.forEach(biConsumer::accept);
    }

    @Override
    public V getValue(K k) {
        return map.get(k);
    }
}
