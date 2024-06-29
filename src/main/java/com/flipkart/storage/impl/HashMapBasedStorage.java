package com.flipkart.storage.impl;

import com.flipkart.exception.NotFoundException;
import com.flipkart.exception.StorageFullException;
import com.flipkart.storage.Storage;
import java.util.*;
public class HashMapBasedStorage<Key, Value> implements Storage<Key, Value> {

    Map<Key, Value> storage;
    private final Integer capacity;

    public HashMapBasedStorage(Integer capacity) {
        this.capacity = capacity;
        storage = new HashMap<Key, Value>();
    }
    @Override
    public void add(Key key, Value value) {
        if (isStorageFull()) {
            throw new StorageFullException("Capacity Full");
            //System.out.println("Capacity Full");
        }
        storage.put(key, value);
    }

    @Override
    public void remove(Key key) {
        if (!storage.containsKey(key)) {
            throw new NotFoundException(key + "doesn't exist in cache.");
        }
        storage.remove(key);
    }

    @Override
    public Value get(Key key) {
        if (!storage.containsKey(key)) {
            throw new NotFoundException(key + "doesn't exist in cache.");
        }
        return storage.get(key);
    }

    @Override
    public Integer size() {
        return storage.size();
    }

    private boolean isStorageFull() {
        return storage.size() == capacity;
    }
}
