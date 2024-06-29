package com.flipkart;

import com.flipkart.exception.NotFoundException;
import com.flipkart.exception.StorageFullException;
import com.flipkart.policies.EvictionPolicy;
import com.flipkart.storage.Storage;

public class FlipCache<Key, Value>{

    private final EvictionPolicy<Key> evictionPolicy;
    private final Storage<Key, Value> storage;

    public FlipCache(EvictionPolicy<Key> evictionPolicy, Storage<Key, Value> storage) {
        this.evictionPolicy = evictionPolicy;
        this.storage = storage;
    }

    public void put(Key key, Value value) {
        try {
            this.storage.add(key, value);
            this.evictionPolicy.keyAccessed(key);
        } catch (StorageFullException exception) {
            System.out.println("Storage Full. Evicting least accessed key.");
            Key keyToRemove = evictionPolicy.evictKey();
            if (keyToRemove == null) {
                throw new RuntimeException("Internal Server Error.");
            }
            this.storage.remove(keyToRemove);
            System.out.println("Evicting key " + keyToRemove);
            put(key, value);
        }
    }

    public Value get(Key key) {
        try {
            Value value = this.storage.get(key);
            this.evictionPolicy.keyAccessed(key);
            return value;
        } catch (NotFoundException notFoundException) {
            System.out.println("No key found.");
            return null;
        }
    }

    public Integer size() {
        return storage.size();
    }

    public Integer cacheMiss() {
        return evictionPolicy.getCacheMiss();
    }

}
