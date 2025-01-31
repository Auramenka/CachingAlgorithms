package com.innowise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LFUCacheTest {

    private LFUCache<Integer, String> lfuCache;

    @BeforeEach
    public void setUp() {
        lfuCache = new LFUCache<>(2);
    }

    @Test
    public void testPutAndGet() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");

        assertEquals("A", lfuCache.get(1));
        assertEquals("B", lfuCache.get(2));
    }

    @Test
    public void testEviction() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        assertNull(lfuCache.get(1));
        assertEquals("B", lfuCache.get(2));
        assertEquals("C", lfuCache.get(3));
    }

    @Test
    public void testUpdateFrequency() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.get(1);

        lfuCache.put(3, "C");

        assertNull(lfuCache.get(2));
        assertEquals("A", lfuCache.get(1));
        assertEquals("C", lfuCache.get(3));
    }

    @Test
    public void testCapacity() {
        LFUCache<Integer, String> cache = new LFUCache<>(1);
        cache.put(1, "A");
        cache.put(2, "B");

        assertNull(cache.get(1));
        assertEquals("B", cache.get(2));
    }

    @Test
    public void testGetNonExistentKey() {
        assertNull(lfuCache.get(99));
    }

    @Test
    public void testDelete() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.delete(1);

        assertNull(lfuCache.get(1));
        assertEquals("B", lfuCache.get(2));
    }

    @Test
    public void testDeleteNotExistentKey() {
        lfuCache.put(1, "A");
        lfuCache.delete(2);
        assertEquals("A", lfuCache.get(1));
    }

    @Test
    public void testDeleteAll() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.deleteAll();

        assertNull(lfuCache.get(1));
        assertNull(lfuCache.get(2));
    }

    @Test
    public void testContains() {
        lfuCache.put(1, "A");

        assertTrue(lfuCache.contains(1));
        assertFalse(lfuCache.contains(2));
    }

    @Test
    public void testContainsAfterEviction() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        assertFalse(lfuCache.contains(1));
        assertTrue(lfuCache.contains(2));
        assertTrue(lfuCache.contains(3));
    }
}