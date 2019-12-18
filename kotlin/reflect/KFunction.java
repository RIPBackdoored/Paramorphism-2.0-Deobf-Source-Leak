package kotlin.reflect;

import kotlin.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003R\u001a\u0010\u0004\u001a\u00020\u00058&X�\u0004�\u0006\f\u0012\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\bR\u001a\u0010\t\u001a\u00020\u00058&X�\u0004�\u0006\f\u0012\u0004\b\n\u0010\u0007\u001a\u0004\b\t\u0010\bR\u001a\u0010\u000b\u001a\u00020\u00058&X�\u0004�\u0006\f\u0012\u0004\b\f\u0010\u0007\u001a\u0004\b\u000b\u0010\bR\u001a\u0010\r\u001a\u00020\u00058&X�\u0004�\u0006\f\u0012\u0004\b\u000e\u0010\u0007\u001a\u0004\b\r\u0010\bR\u001a\u0010\u000f\u001a\u00020\u00058&X�\u0004�\u0006\f\u0012\u0004\b\u0010\u0010\u0007\u001a\u0004\b\u000f\u0010\b�\u0006\u0011" }, d2 = { "Lkotlin/reflect/KFunction;", "R", "Lkotlin/reflect/KCallable;", "Lkotlin/Function;", "isExternal", "", "isExternal$annotations", "()V", "()Z", "isInfix", "isInfix$annotations", "isInline", "isInline$annotations", "isOperator", "isOperator$annotations", "isSuspend", "isSuspend$annotations", "kotlin-stdlib" })
public interface KFunction<R> extends KCallable<R>, Function<R>
{
    boolean isInline();
    
    boolean isExternal();
    
    boolean isOperator();
    
    boolean isInfix();
    
    boolean isSuspend();
    
    @Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3)
    public static final class DefaultImpls
    {
        @SinceKotlin(version = "1.1")
        @Deprecated
        public static void isInline$annotations() {
        }
        
        @SinceKotlin(version = "1.1")
        @Deprecated
        public static void isExternal$annotations() {
        }
        
        @SinceKotlin(version = "1.1")
        @Deprecated
        public static void isOperator$annotations() {
        }
        
        @SinceKotlin(version = "1.1")
        @Deprecated
        public static void isInfix$annotations() {
        }
        
        @SinceKotlin(version = "1.1")
        @Deprecated
        public static void isSuspend$annotations() {
        }
    }
}
