package com.flipkart.policies.impl;

import com.flipkart.policies.EvictionPolicy;

import java.util.LinkedHashSet;

public class LRUEvictionPolicy<Key> implements EvictionPolicy<Key> {

    LinkedHashSet<Key> list;
    static int cacheMiss = 0;

    public LRUEvictionPolicy() {
        this.list = new LinkedHashSet<>();
    }

    @Override
    public void keyAccessed(Key key) {
        if(list.contains(key)) {
            list.remove(key);
            list.add(key);
        } else {
            cacheMiss++;
            list.add(key);
        }
    }


    @Override
    public Key evictKey() {
        if(list == null || list.isEmpty()) {
            return null;
        }
        Key first = list.iterator().next();
        list.remove(first);
        return first;
    }

    @Override
    public int getCacheMiss() {
        return cacheMiss;
    }

}
