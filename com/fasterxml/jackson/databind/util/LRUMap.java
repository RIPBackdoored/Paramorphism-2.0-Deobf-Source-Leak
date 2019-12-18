package com.fasterxml.jackson.databind.util;

import java.util.concurrent.*;
import java.io.*;

public class LRUMap<K, V> implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected final transient int _maxEntries;
    protected final transient ConcurrentHashMap<K, V> _map;
    protected transient int _jdkSerializeMaxEntries;
    
    public LRUMap(final int n, final int maxEntries) {
        super();
        this._map = new ConcurrentHashMap<K, V>(n, 0.8f, 4);
        this._maxEntries = maxEntries;
    }
    
    public V put(final K k, final V v) {
        if (this._map.size() >= this._maxEntries) {
            synchronized (this) {
                if (this._map.size() >= this._maxEntries) {
                    this.clear();
                }
            }
        }
        return this._map.put(k, v);
    }
    
    public V putIfAbsent(final K k, final V v) {
        if (this._map.size() >= this._maxEntries) {
            synchronized (this) {
                if (this._map.size() >= this._maxEntries) {
                    this.clear();
                }
            }
        }
        return this._map.putIfAbsent(k, v);
    }
    
    public V get(final Object o) {
        return this._map.get(o);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public int size() {
        return this._map.size();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        this._jdkSerializeMaxEntries = objectInputStream.readInt();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this._jdkSerializeMaxEntries);
    }
    
    protected Object readResolve() {
        return new LRUMap(this._jdkSerializeMaxEntries, this._jdkSerializeMaxEntries);
    }
}
