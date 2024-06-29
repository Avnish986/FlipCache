package com.flipkart.policies.impl;

import com.flipkart.policies.EvictionPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeBoundEvictionPolicy<Key> implements EvictionPolicy<Key> {
    private final Map<Key, Long> accessTimesMap;
    private final long ttl;
    static int cacheMiss = 0;

    public TimeBoundEvictionPolicy(long ttl) {
        this.accessTimesMap = new HashMap<>();
        this.ttl = ttl;
    }

    @Override
    public void keyAccessed(Key key) {
        if(!accessTimesMap.containsKey(key)) {
            cacheMiss++;
        }
        accessTimesMap.put(key, System.currentTimeMillis());
    }

    /*
    evict key will evict keys based on the ttl being set
        and remove the keys by iterating over the map
     */
        /*
    1 -> 1000
    2 -> 2000
    3 -> 3000
    ttl = 200
    curr 6000
    then all keys will be evicted can't return a single key
     */

    //    @Override
//    public Key evictKey() {
//        long currentTime = System.currentTimeMillis();
//        List<Key> keysToBeEvicted = new ArrayList<>();
//
//        for (Map.Entry<Key, Long> entry : accessTimesMap.entrySet()) {
//            if (currentTime - entry.getValue() >= ttl) {
//                keysToBeEvicted.add(entry.getKey());
//            }
//        }
//
//        for (Key key : keysToBeEvicted) {
//            accessTimesMap.remove(key);
//        }
//
//        return keysToBeEvicted.isEmpty() ? null : keysToBeEvicted.get(keysToBeEvicted.size() - 1);
//    }
    @Override
    public Key evictKey() {
        long currentTime = System.currentTimeMillis();
        Key keyToBeEvicted = null;
        long keyStayTime = Long.MAX_VALUE;

        for (Map.Entry<Key, Long> entry : accessTimesMap.entrySet()) {
            if (currentTime - entry.getValue() >= ttl && entry.getValue() < keyStayTime) {
                keyStayTime = entry.getValue();
                keyToBeEvicted = entry.getKey();
            }
        }

        if (keyToBeEvicted != null) {
            accessTimesMap.remove(keyToBeEvicted);
        }
        return keyToBeEvicted;
    }

    @Override
    public int getCacheMiss() {
        return cacheMiss;
    }

}
