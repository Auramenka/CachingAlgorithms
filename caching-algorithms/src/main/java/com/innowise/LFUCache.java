package com.innowise;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LFUCache<K, V> implements Cache<K, V> {

    private final int capacity;
    private int minFrequency;
    private final Map<K, V> valueMap;
    private final Map<K, Integer> frequencyMap;
    private final Map<Integer, LinkedHashMap<K, V>> frequencyList;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 0;
        this.valueMap = new HashMap<>();
        this.frequencyMap = new HashMap<>();
        this.frequencyList = new HashMap<>();

    }

    @Override
    public V get(K key) {
        if (!valueMap.containsKey(key)) {
            return null;
        }
        V value = valueMap.get(key);
        updateFrequency(key);
        return value;
    }

    @Override
    public void put(K key, V value) {
        if (capacity <= 0) return;

        if (valueMap.containsKey(key)) {
            updateExistingKey(key, value);
        } else {
            addNewKey(key, value);
        }
    }

    private void addNewKey(K key, V value) {
        if (valueMap.size() >= capacity) {
            removeLeastFrequentlyUsed();
        }
        valueMap.put(key, value);
        frequencyMap.put(key, 1);
        minFrequency = 1;
        addToFrequencyList(1, key, value);
    }

    private void addToFrequencyList(int frequency, K key, V value) {
        if (!frequencyList.containsKey(frequency)) {
            frequencyList.put(frequency, new LinkedHashMap<>());
        }
        frequencyList.get(frequency).put(key, value);
    }

    private void updateExistingKey(K key, V value) {
        valueMap.put(key, value);
        updateFrequency(key);
    }

    private void updateFrequency(K key) {
        int frequency = frequencyMap.get(key);
        frequencyMap.put(key, frequency + 1);

        frequencyList.get(frequency).remove(key);

        if (frequencyList.get(frequency).isEmpty()) {
            frequencyList.remove(frequency);
            if (minFrequency == frequency) {
                minFrequency++;
            }
        }

        addToFrequencyList(frequency + 1, key, valueMap.get(key));
    }

    private void removeLeastFrequentlyUsed() {
        K keyToEvict = frequencyList.get(minFrequency).keySet().iterator().next();
        frequencyList.get(minFrequency).remove(keyToEvict);
        if (frequencyList.get(minFrequency).isEmpty()) {
            frequencyList.remove(minFrequency);
        }
        valueMap.remove(keyToEvict);
        frequencyMap.remove(keyToEvict);
    }
}
