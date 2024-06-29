package com.flipkart.defaultPolicy;

import com.flipkart.FlipCache;
import com.flipkart.policies.impl.LRUEvictionPolicy;
import com.flipkart.storage.impl.HashMapBasedStorage;

public class FlipCacheDefault<Key, Value> {

    public FlipCache<Key, Value> defaultCache(final int capacity) {
        return new FlipCache<Key, Value>(new LRUEvictionPolicy<>(),
                new HashMapBasedStorage<>(capacity));
    }
}