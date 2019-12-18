package kotlin.coroutines;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000 \f2\u00060\u0001j\u0002`\u0002:\u0001\fB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\r"},
   d2 = {"Lkotlin/coroutines/CombinedContext$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "elements", "", "Lkotlin/coroutines/CoroutineContext;", "([Lkotlin/coroutines/CoroutineContext;)V", "getElements", "()[Lkotlin/coroutines/CoroutineContext;", "[Lkotlin/coroutines/CoroutineContext;", "readResolve", "", "Companion", "kotlin-stdlib"}
)
final class CombinedContext$Serialized implements Serializable {
   @NotNull
   private final CoroutineContext[] elements;
   private static final long serialVersionUID = 0L;
   public static final CombinedContext$Serialized$Companion Companion = new CombinedContext$Serialized$Companion((DefaultConstructorMarker)null);

   private final Object readResolve() {
      CoroutineContext[] var1 = this.elements;
      EmptyCoroutineContext var2 = EmptyCoroutineContext.INSTANCE;
      boolean var3 = false;
      Object var4 = var2;
      CoroutineContext[] var5 = var1;
      int var6 = var1.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         CoroutineContext var8 = var5[var7];
         CoroutineContext var10 = (CoroutineContext)var4;
         boolean var11 = false;
         var4 = var10.plus(var8);
      }

      return var4;
   }

   @NotNull
   public final CoroutineContext[] getElements() {
      return this.elements;
   }

   public CombinedContext$Serialized(@NotNull CoroutineContext[] var1) {
      super();
      this.elements = var1;
   }
}
