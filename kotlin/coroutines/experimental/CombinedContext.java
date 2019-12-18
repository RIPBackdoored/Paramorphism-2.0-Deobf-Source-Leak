package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0000H\u0002J\u0013\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J5\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\u0006\u0010\u0013\u001a\u0002H\u00122\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00120\u0015H\u0016¢\u0006\u0002\u0010\u0016J(\u0010\u0017\u001a\u0004\u0018\u0001H\u0018\"\b\b\u0000\u0010\u0018*\u00020\u00042\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001aH\u0096\u0002¢\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0014\u0010\u001e\u001a\u00020\u00012\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001aH\u0016J\b\u0010\u001f\u001a\u00020\u001dH\u0002J\b\u0010 \u001a\u00020!H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\""},
   d2 = {"Lkotlin/coroutines/experimental/CombinedContext;", "Lkotlin/coroutines/experimental/CoroutineContext;", "left", "element", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "(Lkotlin/coroutines/experimental/CoroutineContext;Lkotlin/coroutines/experimental/CoroutineContext$Element;)V", "getElement", "()Lkotlin/coroutines/experimental/CoroutineContext$Element;", "getLeft", "()Lkotlin/coroutines/experimental/CoroutineContext;", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "(Lkotlin/coroutines/experimental/CoroutineContext$Key;)Lkotlin/coroutines/experimental/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "kotlin-stdlib-coroutines"}
)
public final class CombinedContext implements CoroutineContext {
   @NotNull
   private final CoroutineContext left;
   @NotNull
   private final CoroutineContext$Element element;

   @Nullable
   public CoroutineContext$Element get(@NotNull CoroutineContext$Key var1) {
      CombinedContext var2 = (CombinedContext)this;

      while(true) {
         CoroutineContext$Element var10000 = var2.element.get(var1);
         if (var10000 != null) {
            CoroutineContext$Element var8 = var10000;
            boolean var4 = false;
            boolean var5 = false;
            boolean var7 = false;
            return var8;
         }

         CoroutineContext var3 = var2.left;
         if (!(var3 instanceof CombinedContext)) {
            return var3.get(var1);
         }

         var2 = (CombinedContext)var3;
      }
   }

   public Object fold(Object var1, @NotNull Function2 var2) {
      return var2.invoke(this.left.fold(var1, var2), this.element);
   }

   @NotNull
   public CoroutineContext minusKey(@NotNull CoroutineContext$Key var1) {
      if (this.element.get(var1) != null) {
         boolean var3 = false;
         boolean var4 = false;
         boolean var6 = false;
         return this.left;
      } else {
         CoroutineContext var2 = this.left.minusKey(var1);
         return var2 == this.left ? (CoroutineContext)this : (var2 == EmptyCoroutineContext.INSTANCE ? (CoroutineContext)this.element : (CoroutineContext)(new CombinedContext(var2, this.element)));
      }
   }

   private final int size() {
      return this.left instanceof CombinedContext ? ((CombinedContext)this.left).size() + 1 : 2;
   }

   private final boolean contains(CoroutineContext$Element var1) {
      return Intrinsics.areEqual((Object)this.get(var1.getKey()), (Object)var1);
   }

   private final boolean containsAll(CombinedContext var1) {
      CoroutineContext var3;
      for(CombinedContext var2 = var1; this.contains(var2.element); var2 = (CombinedContext)var3) {
         var3 = var2.left;
         if (!(var3 instanceof CombinedContext)) {
            if (var3 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.CoroutineContext.Element");
            }

            return this.contains((CoroutineContext$Element)var3);
         }
      }

      return false;
   }

   public boolean equals(@Nullable Object var1) {
      return (CombinedContext)this == var1 || var1 instanceof CombinedContext && ((CombinedContext)var1).size() == this.size() && ((CombinedContext)var1).containsAll(this);
   }

   public int hashCode() {
      return this.left.hashCode() + this.element.hashCode();
   }

   @NotNull
   public String toString() {
      return "[" + (String)this.fold("", (Function2)CombinedContext$toString$1.INSTANCE) + "]";
   }

   @NotNull
   public final CoroutineContext getLeft() {
      return this.left;
   }

   @NotNull
   public final CoroutineContext$Element getElement() {
      return this.element;
   }

   public CombinedContext(@NotNull CoroutineContext var1, @NotNull CoroutineContext$Element var2) {
      super();
      this.left = var1;
      this.element = var2;
   }

   @NotNull
   public CoroutineContext plus(@NotNull CoroutineContext var1) {
      return CoroutineContext$DefaultImpls.plus(this, var1);
   }
}
