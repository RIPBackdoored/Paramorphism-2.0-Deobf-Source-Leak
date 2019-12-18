package kotlin;

import java.io.*;
import kotlin.jvm.functions.*;
import org.jetbrains.annotations.*;
import kotlin.jvm.internal.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\u001f\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\bH\u0002R\u0010\u0010\n\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u0088\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00028\u00008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0013" }, d2 = { "Lkotlin/SynchronizedLazyImpl;", "T", "Lkotlin/Lazy;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "initializer", "Lkotlin/Function0;", "lock", "", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)V", "_value", "value", "getValue", "()Ljava/lang/Object;", "isInitialized", "", "toString", "", "writeReplace", "kotlin-stdlib" })
final class SynchronizedLazyImpl<T> implements Lazy<T>, Serializable
{
    private Function0<? extends T> initializer;
    private Object _value;
    private final Object lock;
    
    @Override
    public T getValue() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        kotlin/SynchronizedLazyImpl._value:Ljava/lang/Object;
        //     4: astore_1       
        //     5: aload_1        
        //     6: getstatic       kotlin/UNINITIALIZED_VALUE.INSTANCE:Lkotlin/UNINITIALIZED_VALUE;
        //     9: if_acmpeq       14
        //    12: aload_1        
        //    13: areturn        
        //    14: aload_0        
        //    15: getfield        kotlin/SynchronizedLazyImpl.lock:Ljava/lang/Object;
        //    18: astore_2       
        //    19: iconst_0       
        //    20: istore_3       
        //    21: iconst_0       
        //    22: istore          4
        //    24: aload_2        
        //    25: monitorenter   
        //    26: nop            
        //    27: iconst_0       
        //    28: istore          5
        //    30: aload_0        
        //    31: getfield        kotlin/SynchronizedLazyImpl._value:Ljava/lang/Object;
        //    34: astore          6
        //    36: aload           6
        //    38: getstatic       kotlin/UNINITIALIZED_VALUE.INSTANCE:Lkotlin/UNINITIALIZED_VALUE;
        //    41: if_acmpeq       49
        //    44: aload           6
        //    46: goto            83
        //    49: aload_0        
        //    50: getfield        kotlin/SynchronizedLazyImpl.initializer:Lkotlin/jvm/functions/Function0;
        //    53: dup            
        //    54: ifnonnull       60
        //    57: invokestatic    kotlin/jvm/internal/Intrinsics.throwNpe:()V
        //    60: invokeinterface kotlin/jvm/functions/Function0.invoke:()Ljava/lang/Object;
        //    65: astore          7
        //    67: aload_0        
        //    68: aload           7
        //    70: putfield        kotlin/SynchronizedLazyImpl._value:Ljava/lang/Object;
        //    73: aload_0        
        //    74: aconst_null    
        //    75: checkcast       Lkotlin/jvm/functions/Function0;
        //    78: putfield        kotlin/SynchronizedLazyImpl.initializer:Lkotlin/jvm/functions/Function0;
        //    81: aload           7
        //    83: nop            
        //    84: astore          4
        //    86: aload_2        
        //    87: monitorexit    
        //    88: aload           4
        //    90: goto            100
        //    93: astore          4
        //    95: aload_2        
        //    96: monitorexit    
        //    97: aload           4
        //    99: athrow         
        //   100: areturn        
        //    Signature:
        //  ()TT;
        //    StackMapTable: 00 06 FC 00 0E 07 00 05 FF 00 22 00 07 07 00 02 07 00 05 07 00 05 01 01 01 07 00 05 00 00 4A 07 00 3F 56 07 00 05 FF 00 09 00 04 07 00 02 07 00 05 07 00 05 01 00 01 07 00 44 FF 00 06 00 07 07 00 02 07 00 05 07 00 05 01 07 00 05 01 07 00 05 00 01 07 00 05
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type
        //  -----  -----  -----  -----  ----
        //  26     86     93     100    Any
        //  93     95     93     100    Any
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
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
    
    public SynchronizedLazyImpl(@NotNull final Function0<? extends T> initializer, @Nullable final Object o) {
        super();
        this.initializer = initializer;
        this._value = UNINITIALIZED_VALUE.INSTANCE;
        Object lock = o;
        if (o == null) {
            lock = this;
        }
        this.lock = lock;
    }
    
    public SynchronizedLazyImpl(final Function0 function0, Object o, final int n, final DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x2) != 0x0) {
            o = null;
        }
        this(function0, o);
    }
}
