package kotlin.collections;

import java.util.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012" }, d2 = { "kotlin/collections/ArraysKt___ArraysJvmKt$asList$6", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "get", "index", "(I)Ljava/lang/Double;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib" })
public static final class ArraysKt___ArraysJvmKt$asList$6 extends AbstractList<Double> implements RandomAccess {
    final double[] $this_asList;
    
    @Override
    public int getSize() {
        return this.$this_asList.length;
    }
    
    @Override
    public boolean isEmpty() {
        return this.$this_asList.length == 0;
    }
    
    public boolean contains(final double n) {
        return ArraysKt___ArraysKt.contains(this.$this_asList, n);
    }
    
    @Override
    public final boolean contains(final Object o) {
        return o instanceof Double && this.contains(((Number)o).doubleValue());
    }
    
    @NotNull
    @Override
    public Double get(final int n) {
        return this.$this_asList[n];
    }
    
    @Override
    public Object get(final int n) {
        return this.get(n);
    }
    
    public int indexOf(final double n) {
        return ArraysKt___ArraysKt.indexOf(this.$this_asList, n);
    }
    
    @Override
    public final int indexOf(final Object o) {
        if (o instanceof Double) {
            return this.indexOf(((Number)o).doubleValue());
        }
        return -1;
    }
    
    public int lastIndexOf(final double n) {
        return ArraysKt___ArraysKt.lastIndexOf(this.$this_asList, n);
    }
    
    @Override
    public final int lastIndexOf(final Object o) {
        if (o instanceof Double) {
            return this.lastIndexOf(((Number)o).doubleValue());
        }
        return -1;
    }
    
    ArraysKt___ArraysJvmKt$asList$6(final double[] $this_asList) {
        this.$this_asList = $this_asList;
        super();
    }
}