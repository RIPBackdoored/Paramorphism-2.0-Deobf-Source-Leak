package kotlin;

import java.io.*;
import kotlin.jvm.functions.*;
import kotlin.jvm.internal.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006�\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\tH\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e�\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u0088\u000e�\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00028\u00008VX\u0096\u0004�\u0006\u0006\u001a\u0004\b\u000b\u0010\f�\u0006\u0012" }, d2 = { "Lkotlin/UnsafeLazyImpl;", "T", "Lkotlin/Lazy;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "initializer", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)V", "_value", "", "value", "getValue", "()Ljava/lang/Object;", "isInitialized", "", "toString", "", "writeReplace", "kotlin-stdlib" })
public final class UnsafeLazyImpl<T> implements Lazy<T>, Serializable
{
    private Function0<? extends T> initializer;
    private Object _value;
    
    @Override
    public T getValue() {
        if (this._value == UNINITIALIZED_VALUE.INSTANCE) {
            final Function0<? extends T> initializer = this.initializer;
            if (initializer == null) {
                Intrinsics.throwNpe();
            }
            this._value = initializer.invoke();
            this.initializer = null;
        }
        return (T)this._value;
    }
    
    @Override
    public boolean isInitialized() {
        return this._value != UNINITIALIZED_VALUE.INSTANCE;
    }
    
    @NotNull
    @Override
    public String toString() {
        return this.isInitialized() ? String.valueOf(this.getValue()) : "Lazy value not initialized yet.";
    }
    
    private final Object writeReplace() {
        return new InitializedLazyImpl(this.getValue());
    }
    
    public UnsafeLazyImpl(@NotNull final Function0<? extends T> initializer) {
        super();
        this.initializer = initializer;
        this._value = UNINITIALIZED_VALUE.INSTANCE;
    }
}
