package com.flipkart;


import com.flipkart.policies.EvictionPolicy;
import com.flipkart.policies.impl.LRUEvictionPolicy;
import com.flipkart.policies.impl.TimeBoundEvictionPolicy;
import com.flipkart.storage.Storage;
import com.flipkart.storage.impl.HashMapBasedStorage;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter FlipCache capacity:");
        int capacity = sc.nextInt();
        sc.nextLine();

        System.out.println("Choose 1 or 2 as per the eviction policy:");
        System.out.println("1. TimeBoundEvictionPolicy");
        System.out.println("2. LRUEvictionPolicy");
        int evictionChoice = sc.nextInt();
        sc.nextLine();

        Storage<String, String> storage = new HashMapBasedStorage<>(capacity);
        EvictionPolicy<String> evictionPolicy;

        switch (evictionChoice) {
            case 1:
                System.out.println("Enter the TTL (in milliseconds) for TimeBoundEvictionPolicy:");
                long ttl = sc.nextLong();
                evictionPolicy = new TimeBoundEvictionPolicy<>(ttl);
                break;
            case 2:
                evictionPolicy = new LRUEvictionPolicy<>();
                break;
            default:
                System.out.println("Invalid choice. Using LRUEvictionPolicy by default.");
                evictionPolicy = new LRUEvictionPolicy<>();
                break;
        }

        FlipCache<String, String> flipCache = new FlipCache<>(evictionPolicy, storage);

        flipCache.put("1", "one");
        System.out.println("Cache miss: " + flipCache.cacheMiss());
        flipCache.put("1", "two");
        System.out.println("Cache miss: " + flipCache.cacheMiss());
        flipCache.put("3", "three");
        System.out.println("Cache miss: " + flipCache.cacheMiss());

        System.out.println("Cache size: " + flipCache.size());

        flipCache.get("1");
        System.out.println("Cache miss: " + flipCache.cacheMiss());
        flipCache.put("4", "four");
        System.out.println("Cache miss: " + flipCache.cacheMiss());

        System.out.println("Cache size: " + flipCache.size());
        System.out.println("Value for key 2: " + flipCache.get("3"));

        sc.close();
        /* Output using LRUEvictionPolicy
        Enter the capacity for FlipCache:
        2
        Choose the eviction policy:
        1. TimeBoundEvictionPolicy
        2. LRUEvictionPolicy
        2
        Storage Full. Evicting least accessed key.
        Evicting key 1
        Cache size: 2
        No key found.
        Storage Full. Evicting least accessed key.
        Evicting key 2
        Cache size: 2
        Value for key 2: three
         */


        /* Output using TimeBoundEvictionPolicy
        Enter the capacity for FlipCache:
        2
        Choose the eviction policy:
        1. TimeBoundEvictionPolicy
        2. LRUEvictionPolicy
        1
        Enter the TTL (in milliseconds) for TimeBoundEvictionPolicy:
        1
        Storage Full. Evicting least accessed key.
        Evicting key 1
        Cache size: 2
        No key found.
        Storage Full. Evicting least accessed key.
        Evicting key 2
        Cache size: 2
        Value for key 2: three
         */

        /* Output using cacheMiss
        Enter FlipCache capacity:
        2
        Choose 1 or 2 as per the eviction policy:
        1. TimeBoundEvictionPolicy
        2. LRUEvictionPolicy
        2
        Cache miss: 1
        Cache miss: 2
        Storage Full. Evicting least accessed key.
        Evicting key 1
        Cache miss: 3
        Cache size: 2
        No key found.
        Cache miss: 3
        Storage Full. Evicting least accessed key.
        Evicting key 2
        Cache miss: 4
        Cache size: 2
        Value for key 2: three
         */
    }
}
