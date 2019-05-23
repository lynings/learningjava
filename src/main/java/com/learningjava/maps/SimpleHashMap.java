package com.learningjava.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author lyning
 */
public class SimpleHashMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private int size;
    private Bucket<K, V>[] table;
    private int threshold;

    public boolean containsKey(K key) {
        int hash = this.hash(key);
        int index = this.index(hash);
        Bucket<K, V> bucket = this.table[index];
        return bucket != null
                && bucket.lookup(key) != null;
    }

    public void forEach(Consumer<K> action) {
        for (Bucket<K, V> bucket : this.table) {
            while (bucket != null) {
                action.accept(bucket.key);
                bucket = bucket.next;
            }
        }
    }

    public V get(K key) {
        int hash = this.hash(key);
        int index = this.index(hash);
        return this.getVal(index, key);
    }

    public V put(K key, V value) {
        if (this.tableEmpty() || this.nearByThreshold()) {
            this.resize();
        }
        int hash = this.hash(key);
        return this.putVal(key, value, hash);
    }

    public V remove(K key) {
        int hash = this.hash(key);
        int index = this.index(hash);
        return this.removeVal(index, key);
    }

    public int size() {
        return this.size;
    }

    public Iterable<V> values() {
        if (this.tableEmpty()) {
            return new ArrayList<>();
        }
        List<V> collections = new ArrayList<>();
        this.collectValues(collections);
        return collections;
    }

    private void collectValues(List<V> collections) {
        for (Bucket<K, V> bucket : this.table) {
            while (bucket != null) {
                collections.add(bucket.value);
                bucket = bucket.next;
            }
        }
    }

    private Bucket<K, V> findBucket(int index) {
        return this.tableEmpty()
                ? null
                : this.table[index];
    }

    private V getVal(int index, K key) {
        Bucket<K, V> bucket = this.findBucket(index);
        if (bucket == null || (bucket = bucket.lookup(key)) == null) {
            return null;
        }
        return bucket.value;
    }

    private void grow(int newCap) {
        if (this.tableEmpty()) {
            this.initTable(newCap);
            return;
        }
        this.table = this.rebuildTable(newCap);
    }

    private int hash(K key) {
        int hashcode;
        return key == null
                ? 0
                : (hashcode = key.hashCode()) ^ (hashcode >>> 16);
    }

    private int index(int hash) {
        return hash & (this.table.length - 1);
    }

    private void initTable(int newCap) {
        this.table = new Bucket[newCap];
    }

    private boolean nearByThreshold() {
        return this.size + 1 >= this.threshold;
    }

    private V putVal(K key, V value, int hash) {
        int index = this.index(hash);
        Bucket<K, V> bucket = this.table[index];

        if (bucket == null) {
            this.table[index] = new Bucket<>(hash, key, value);
        } else {
            while (bucket != null) {
                if (bucket.matchKey(key)) {
                    bucket.value = value;
                    return value;
                } else if (bucket.next == null) {
                    bucket.next = new Bucket<>(hash, key, value);
                    bucket = null;
                } else {
                    bucket = bucket.next;
                }
            }
        }
        this.size += 1;
        return value;
    }

    private Bucket<K, V>[] rebuildTable(int newCap) {
        Bucket<K, V>[] oldTable = this.table;
        Bucket<K, V>[] newTable = new Bucket[newCap];
        for (Bucket<K, V> bucket : oldTable) {
            if (bucket != null) {
                int index = this.index(bucket.hash);
                newTable[index] = bucket;
            }
        }
        return newTable;
    }

    private V removeVal(int index, K key) {
        Bucket<K, V> bucket = this.findBucket(index);
        Bucket<K, V> prev = null;
        while (bucket != null) {
            if (bucket.matchKey(key)) {
                if (prev == null) {
                    this.table[index] = null;
                } else {
                    prev.next = bucket.next;
                }
                this.size -= 1;
                return bucket.value;
            }
            prev = bucket;
            bucket = bucket.next;
        }
        return null;
    }

    private void resize() {
        int oldCap = this.tableCapacity();
        int newCap = 0;
        if (oldCap == 0) {
            oldCap = DEFAULT_INITIAL_CAPACITY;
            this.threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        } else {
            newCap = oldCap << 1;
            this.threshold = this.threshold << 1;
        }

        if (newCap == 0) {
            newCap = oldCap;
        }
        this.grow(newCap);
    }

    private int tableCapacity() {
        return this.table == null ? 0 : this.table.length;
    }

    private boolean tableEmpty() {
        return this.table == null || this.table.length == 0;
    }

    static class Bucket<K, V> {
        Bucket<K, V> next;
        int hash;
        K key;
        V value;

        public Bucket(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        public Bucket<K, V> lookup(K key) {
            Bucket<K, V> bucket = this;
            while (bucket != null) {
                if (bucket.matchKey(key)) {
                    return bucket;
                }
                bucket = bucket.next;
            }
            return null;
        }

        public boolean matchKey(K key) {
            return this.key == key || this.key.equals(key);
        }
    }
}
