package kotlin.coroutines;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0001\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001!B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0000H\u0002J\u0013\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\u0006\u0010\u0011\u001a\u0002H\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\u00100\u0013H\u0016¢\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u0004\u0018\u0001H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018H\u0096\u0002¢\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u00020\u00012\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0016J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""},
   d2 = {"Lkotlin/coroutines/CombinedContext;", "Lkotlin/coroutines/CoroutineContext;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "left", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext$Element;)V", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "writeReplace", "Serialized", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
public final class CombinedContext implements CoroutineContext, Serializable {
   private final CoroutineContext left;
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
      CombinedContext var1 = (CombinedContext)this;
      int var2 = 2;

      while(true) {
         CoroutineContext var10000 = var1.left;
         if (!(var10000 instanceof CombinedContext)) {
            var10000 = null;
         }

         CombinedContext var3 = (CombinedContext)var10000;
         if ((CombinedContext)var10000 == null) {
            return var2;
         }

         var1 = var3;
         ++var2;
      }
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
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element");
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

   private final Object writeReplace() {
      int var1 = this.size();
      CoroutineContext[] var2 = new CoroutineContext[var1];
      Ref$IntRef var3 = new Ref$IntRef();
      var3.element = 0;
      this.fold(Unit.INSTANCE, (Function2)(new CombinedContext$writeReplace$1(var2, var3)));
      boolean var4 = var3.element == var1;
      boolean var5 = false;
      boolean var6 = false;
      var6 = false;
      boolean var7 = false;
      if (!var4) {
         boolean var8 = false;
         String var9 = "Check failed.";
         throw (Throwable)(new IllegalStateException(var9.toString()));
      } else {
         return new CombinedContext$Serialized(var2);
      }
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
