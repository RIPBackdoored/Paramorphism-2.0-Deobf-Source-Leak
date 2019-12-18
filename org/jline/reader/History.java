package org.jline.reader;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public interface History extends Iterable<Entry>
{
    void attach(final LineReader p0);
    
    void load() throws IOException;
    
    void save() throws IOException;
    
    void write(final Path p0, final boolean p1) throws IOException;
    
    void append(final Path p0, final boolean p1) throws IOException;
    
    void read(final Path p0, final boolean p1) throws IOException;
    
    void purge() throws IOException;
    
    int size();
    
    default boolean isEmpty() {
        return this.size() == 0;
    }
    
    int index();
    
    int first();
    
    int last();
    
    String get(final int p0);
    
    default void add(final String s) {
        this.add(Instant.now(), s);
    }
    
    void add(final Instant p0, final String p1);
    
    default boolean isPersistable(final Entry entry) {
        return true;
    }
    
    ListIterator<Entry> iterator(final int p0);
    
    default ListIterator<Entry> iterator() {
        return this.iterator(this.first());
    }
    
    default Iterator<Entry> reverseIterator() {
        return this.reverseIterator(this.last());
    }
    
    default Iterator<Entry> reverseIterator(final int n) {
        return new Iterator<Entry>(this, n) {
            private final ListIterator<Entry> it = this.this$0.iterator(this.val$index + 1);
            final int val$index;
            final History this$0;
            
            History$1(final History this$0, final int val$index) {
                this.this$0 = this$0;
                this.val$index = val$index;
                super();
            }
            
            @Override
            public boolean hasNext() {
                return this.it.hasPrevious();
            }
            
            @Override
            public Entry next() {
                return this.it.previous();
            }
            
            @Override
            public Object next() {
                return this.next();
            }
        };
    }
    
    String current();
    
    boolean previous();
    
    boolean next();
    
    boolean moveToFirst();
    
    boolean moveToLast();
    
    boolean moveTo(final int p0);
    
    void moveToEnd();
    
    default Iterator iterator() {
        return this.iterator();
    }
    
    public interface Entry
    {
        int index();
        
        Instant time();
        
        String line();
    }
}
