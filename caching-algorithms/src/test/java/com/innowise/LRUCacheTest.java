package com.innowise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    private LRUCache<Integer, String> lruCache;

    @BeforeEach
    public void setUp() {
        lruCache = new LRUCache<>(2);
    }

    @Test
    public void testPutAndGet() {
        lruCache.put(1, "one");
        lruCache.put(2, "two");

        assertEquals("one", lruCache.get(1));
        assertEquals("two", lruCache.get(2));
    }

    @Test
    public void testEviction() {
        lruCache.put(1, "one");
        lruCache.put(2, "two");
        lruCache.put(3, "three");

        assertNull(lruCache.get(1));
        assertEquals("two", lruCache.get(2));
        assertEquals("three", lruCache.get(3));
    }

    @Test
    public void testUpdateValue() {
        lruCache.put(1, "one");
        lruCache.put(1, "uno");

        assertEquals("uno", lruCache.get(1));
    }

    @Test
    public void testOrderOfAccess() {
        lruCache.put(1, "one");
        lruCache.put(2, "two");
        lruCache.get(1);
        lruCache.put(3, "three");

        assertNull(lruCache.get(2));
        assertEquals("one", lruCache.get(1));
        assertEquals("three", lruCache.get(3));
    }

    @Test
    public void testEmptyCache() {
        assertNull(lruCache.get(1));
    }
}