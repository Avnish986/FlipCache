package com.flipkart.storage;

public interface Storage<Key, Value> {
    public void add(Key key, Value value);
    void remove(Key key);
    Value get(Key key);

    Integer size();
}
