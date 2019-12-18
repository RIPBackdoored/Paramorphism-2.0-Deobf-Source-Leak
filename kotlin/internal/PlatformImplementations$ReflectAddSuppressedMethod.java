package kotlin.internal;

import kotlin.*;
import java.lang.reflect.*;
import kotlin.jvm.*;
import org.jetbrains.annotations.*;
import kotlin.jvm.internal.*;
import kotlin.collections.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005" }, d2 = { "Lkotlin/internal/PlatformImplementations$ReflectAddSuppressedMethod;", "", "()V", "method", "Ljava/lang/reflect/Method;", "kotlin-stdlib" })
private static final class ReflectAddSuppressedMethod
{
    @JvmField
    @Nullable
    public static final Method method;
    public static final ReflectAddSuppressedMethod INSTANCE;
    
    private ReflectAddSuppressedMethod() {
        super();
    }
    
    static {
        INSTANCE = new ReflectAddSuppressedMethod();
        final Class<Throwable> clazz = Throwable.class;
        while (true) {
            for (final Method method3 : clazz.getMethods()) {
                final Method method2 = method3;
                if (Intrinsics.areEqual(method3.getName(), "addSuppressed") && Intrinsics.areEqual(ArraysKt___ArraysKt.singleOrNull(method3.getParameterTypes()), clazz)) {
                    final Method method4 = method2;
                    method = method4;
                    return;
                }
            }
            final Method method4 = null;
            continue;
        }
    }
}
