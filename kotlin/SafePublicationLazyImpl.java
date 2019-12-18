package kotlin;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0002\u0018\u0000 \u0013*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004:\u0001\u0013B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\tH\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u0088\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00028\u00008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0014"},
   d2 = {"Lkotlin/SafePublicationLazyImpl;", "T", "Lkotlin/Lazy;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "initializer", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)V", "_value", "", "final", "value", "getValue", "()Ljava/lang/Object;", "isInitialized", "", "toString", "", "writeReplace", "Companion", "kotlin-stdlib"}
)
final class SafePublicationLazyImpl implements Lazy, Serializable {
   private Function0 initializer;
   private Object _value;
   private final Object final;
   private static final AtomicReferenceFieldUpdater valueUpdater = AtomicReferenceFieldUpdater.newUpdater(SafePublicationLazyImpl.class, Object.class, "_value");
   public static final SafePublicationLazyImpl$Companion Companion = new SafePublicationLazyImpl$Companion((DefaultConstructorMarker)null);

   public Object getValue() {
      Object var1 = this._value;
      if (var1 != UNINITIALIZED_VALUE.INSTANCE) {
         return var1;
      } else {
         Function0 var2 = this.initializer;
         if (var2 != null) {
            Object var3 = var2.invoke();
            if (valueUpdater.compareAndSet(this, UNINITIALIZED_VALUE.INSTANCE, var3)) {
               this.initializer = (Function0)null;
               return var3;
            }
         }

         return this._value;
      }
   }

   public boolean isInitialized() {
      return this._value != UNINITIALIZED_VALUE.INSTANCE;
   }

   @NotNull
   public String toString() {
      return this.isInitialized() ? String.valueOf(this.getValue()) : "Lazy value not initialized yet.";
   }

   private final Object writeReplace() {
      return new InitializedLazyImpl(this.getValue());
   }

   public SafePublicationLazyImpl(@NotNull Function0 var1) {
      super();
      this.initializer = var1;
      this._value = UNINITIALIZED_VALUE.INSTANCE;
      this.final = UNINITIALIZED_VALUE.INSTANCE;
   }
}
