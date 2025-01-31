package com.innowise;


import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {

    private final int capacity;
    private final Map<K, V> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            cache.remove(key);
        } else if (cache.size() >= capacity) {
            removeEldestEntry();
        }
        cache.put(key, value);
    }

    @Override
    public void delete(K key) {
        cache.remove(key);
    }

    @Override
    public void deleteAll() {
        cache.clear();
    }

    @Override
    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    private void removeEldestEntry() {
        K eldestKey = cache.keySet().iterator().next();
        cache.remove(eldestKey);
    }
}