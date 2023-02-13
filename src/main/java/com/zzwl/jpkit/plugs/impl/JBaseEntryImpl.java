package com.zzwl.jpkit.plugs.impl;

import java.util.Map;

/**
 * @since 1.0
 **/
public class JBaseEntryImpl<K, V> implements Map.Entry<K, V> {
    private final K key;
    private V value;

    public JBaseEntryImpl(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }


    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }
}
