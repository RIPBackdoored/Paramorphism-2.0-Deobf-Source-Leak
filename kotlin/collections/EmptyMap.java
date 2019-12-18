package kotlin.collections;

import java.io.*;
import kotlin.jvm.internal.markers.*;
import kotlin.*;
import org.jetbrains.annotations.*;
import java.util.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000L\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010&\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\b\u00c2\u0002\u0018\u00002\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00060\u0004j\u0002`\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0013\u0010\u001d\u001a\u00020\u00192\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\u0015\u0010\u001f\u001a\u0004\u0018\u00010\u00032\b\u0010\u001a\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010 \u001a\u00020\u0011H\u0016J\b\u0010!\u001a\u00020\u0019H\u0016J\b\u0010\"\u001a\u00020\u0002H\u0002J\b\u0010#\u001a\u00020$H\u0016R(\u0010\u0007\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006%" }, d2 = { "Lkotlin/collections/EmptyMap;", "", "", "", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "entries", "", "", "getEntries", "()Ljava/util/Set;", "keys", "getKeys", "serialVersionUID", "", "size", "", "getSize", "()I", "values", "", "getValues", "()Ljava/util/Collection;", "containsKey", "", "key", "containsValue", "value", "equals", "other", "get", "hashCode", "isEmpty", "readResolve", "toString", "", "kotlin-stdlib" })
final class EmptyMap implements Map, Serializable, KMappedMarker
{
    private static final long serialVersionUID = 8246714829545688274L;
    public static final EmptyMap INSTANCE;
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof Map && ((Map)o).isEmpty();
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @NotNull
    @Override
    public String toString() {
        return "{}";
    }
    
    public int getSize() {
        return 0;
    }
    
    @Override
    public final int size() {
        return this.getSize();
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return false;
    }
    
    public boolean containsValue(@NotNull final Void void1) {
        return false;
    }
    
    @Override
    public final boolean containsValue(final Object o) {
        return o instanceof Void && this.containsValue((Void)o);
    }
    
    @Nullable
    @Override
    public Void get(@Nullable final Object o) {
        return null;
    }
    
    @Override
    public final Object get(final Object o) {
        return this.get(o);
    }
    
    @NotNull
    public Set<Entry> getEntries() {
        return (Set<Entry>)EmptySet.INSTANCE;
    }
    
    @Override
    public final Set<Entry> entrySet() {
        return this.getEntries();
    }
    
    @NotNull
    public Set<Object> getKeys() {
        return (Set<Object>)EmptySet.INSTANCE;
    }
    
    @Override
    public final Set<Object> keySet() {
        return this.getKeys();
    }
    
    @NotNull
    public Collection getValues() {
        return EmptyList.INSTANCE;
    }
    
    @Override
    public final Collection values() {
        return this.getValues();
    }
    
    private final Object readResolve() {
        return EmptyMap.INSTANCE;
    }
    
    private EmptyMap() {
        super();
    }
    
    static {
        INSTANCE = new EmptyMap();
    }
    
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
    
    public Void put(final Object o, final Void void1) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
    
    @Override
    public void putAll(final Map map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
    
    @Override
    public Object remove(final Object o) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
