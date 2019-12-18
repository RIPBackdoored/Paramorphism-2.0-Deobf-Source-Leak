package kotlin;

import java.io.*;
import org.jetbrains.annotations.*;
import kotlin.internal.*;
import kotlin.jvm.internal.*;
import kotlin.jvm.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0087@\u0018\u0000 \u001e*\u0006\b\u0000\u0010\u0001 \u00012\u00060\u0002j\u0002`\u0003:\u0002\u001e\u001fB\u0016\b\u0001\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00f8\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\u0010\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u00d6\u0003J\u000f\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\u0004\b\u0014\u0010\u0015J\u0012\u0010\u0016\u001a\u0004\u0018\u00018\u0000H\u0087\b¢\u0006\u0004\b\u0017\u0010\u0007J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\u000f\u0010\u001a\u001a\u00020\u001bH\u0016¢\u0006\u0004\b\u001c\u0010\u001dR\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000e\u0010\u000f\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 " }, d2 = { "Lkotlin/Result;", "T", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "value", "", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "isFailure", "", "isFailure-impl", "(Ljava/lang/Object;)Z", "isSuccess", "isSuccess-impl", "value$annotations", "()V", "equals", "other", "exceptionOrNull", "", "exceptionOrNull-impl", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "getOrNull", "getOrNull-impl", "hashCode", "", "toString", "", "toString-impl", "(Ljava/lang/Object;)Ljava/lang/String;", "Companion", "Failure", "kotlin-stdlib" })
@SinceKotlin(version = "1.3")
public final class Result<T> implements Serializable
{
    @Nullable
    private final Object value;
    public static final Companion Companion;
    
    @NotNull
    @Override
    public String toString() {
        return toString-impl(this.value);
    }
    
    @PublishedApi
    @Deprecated
    public static void value$annotations() {
    }
    
    @PublishedApi
    private Result(@Nullable final Object value) {
        super();
        this.value = value;
    }
    
    public static final boolean isSuccess-impl(final Object o) {
        return !(o instanceof Failure);
    }
    
    public static final boolean isFailure-impl(final Object o) {
        return o instanceof Failure;
    }
    
    @InlineOnly
    private static final T getOrNull-impl(final Object o) {
        return (T)(isFailure-impl(o) ? null : o);
    }
    
    @Nullable
    public static final Throwable exceptionOrNull-impl(final Object o) {
        return (o instanceof Failure) ? ((Failure)o).exception : null;
    }
    
    @NotNull
    public static String toString-impl(final Object o) {
        return (o instanceof Failure) ? o.toString() : ("Success(" + o + ')');
    }
    
    @PublishedApi
    @NotNull
    public static Object constructor-impl(@Nullable final Object o) {
        return o;
    }
    
    @NotNull
    public static final Result box-impl(@Nullable final Object o) {
        return new Result(o);
    }
    
    public static int hashCode-impl(final Object o) {
        return (o != null) ? o.hashCode() : 0;
    }
    
    public static boolean equals-impl(final Object o, @Nullable final Object o2) {
        return o2 instanceof Result && Intrinsics.areEqual(o, ((Result)o2).unbox-impl());
    }
    
    public static final boolean equals-impl0(@Nullable final Object o, @Nullable final Object o2) {
        throw null;
    }
    
    @Nullable
    public final Object unbox-impl() {
        return this.value;
    }
    
    static {
        Companion = new Companion(null);
    }
    
    @Override
    public int hashCode() {
        return hashCode-impl(this.value);
    }
    
    @Override
    public boolean equals(final Object o) {
        return equals-impl(this.value, o);
    }
    
    @Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0087\b\u00f8\u0001\u0000¢\u0006\u0002\u0010\bJ%\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\n\u001a\u0002H\u0005H\u0087\b\u00f8\u0001\u0000¢\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f" }, d2 = { "Lkotlin/Result$Companion;", "", "()V", "failure", "Lkotlin/Result;", "T", "exception", "", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "success", "value", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib" })
    public static final class Companion
    {
        @InlineOnly
        private final <T> Object success(final T t) {
            return Result.constructor-impl(t);
        }
        
        @InlineOnly
        private final <T> Object failure(final Throwable t) {
            return Result.constructor-impl(ResultKt.createFailure(t));
        }
        
        private Companion() {
            super();
        }
        
        public Companion(final DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
    
    @Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0096\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e" }, d2 = { "Lkotlin/Result$Failure;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "exception", "", "(Ljava/lang/Throwable;)V", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib" })
    public static final class Failure implements Serializable
    {
        @JvmField
        @NotNull
        public final Throwable exception;
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof Failure && Intrinsics.areEqual(this.exception, ((Failure)o).exception);
        }
        
        @Override
        public int hashCode() {
            return this.exception.hashCode();
        }
        
        @NotNull
        @Override
        public String toString() {
            return "Failure(" + this.exception + ')';
        }
        
        public Failure(@NotNull final Throwable exception) {
            super();
            this.exception = exception;
        }
    }
}
