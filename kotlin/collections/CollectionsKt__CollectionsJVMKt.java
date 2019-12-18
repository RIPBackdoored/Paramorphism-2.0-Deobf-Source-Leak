package kotlin.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u00002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0081\b\u001a\u0011\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0081\b\u001a\"\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0081\b¢\u0006\u0002\u0010\n\u001a4\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0006\"\u0004\b\u0000\u0010\u000b2\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0006H\u0081\b¢\u0006\u0002\u0010\r\u001a\u001f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000f\"\u0004\b\u0000\u0010\u000b2\u0006\u0010\u0010\u001a\u0002H\u000b¢\u0006\u0002\u0010\u0011\u001a1\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\b\u0000\u0010\u000b*\n\u0012\u0006\b\u0001\u0012\u0002H\u000b0\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0000¢\u0006\u0002\u0010\u0015\u001a\u001f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000f\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u0017H\u0087\b¨\u0006\u0018"},
   d2 = {"checkCountOverflow", "", "count", "checkIndexOverflow", "index", "copyToArrayImpl", "", "", "collection", "", "(Ljava/util/Collection;)[Ljava/lang/Object;", "T", "array", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "listOf", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "copyToArrayOfAny", "isVarargs", "", "([Ljava/lang/Object;Z)[Ljava/lang/Object;", "toList", "Ljava/util/Enumeration;", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__CollectionsJVMKt {
   @NotNull
   public static final List listOf(Object var0) {
      return Collections.singletonList(var0);
   }

   @InlineOnly
   private static final List toList(@NotNull Enumeration var0) {
      byte var1 = 0;
      return (List)Collections.list(var0);
   }

   @InlineOnly
   private static final Object[] copyToArrayImpl(Collection var0) {
      byte var1 = 0;
      return CollectionToArray.toArray(var0);
   }

   @InlineOnly
   private static final Object[] copyToArrayImpl(Collection var0, Object[] var1) {
      byte var2 = 0;
      if (var1 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
      } else {
         Object[] var10000 = CollectionToArray.toArray(var0, var1);
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         } else {
            return var10000;
         }
      }
   }

   @NotNull
   public static final Object[] copyToArrayOfAny(@NotNull Object[] var0, boolean var1) {
      return var1 && Intrinsics.areEqual((Object)var0.getClass(), (Object)Object[].class) ? var0 : Arrays.copyOf(var0, var0.length, Object[].class);
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final int checkIndexOverflow(int var0) {
      byte var1 = 0;
      if (var0 < 0) {
         if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            throw (Throwable)(new ArithmeticException("Index overflow has happened."));
         }

         CollectionsKt.throwIndexOverflow();
      }

      return var0;
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final int checkCountOverflow(int var0) {
      byte var1 = 0;
      if (var0 < 0) {
         if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            throw (Throwable)(new ArithmeticException("Count overflow has happened."));
         }

         CollectionsKt.throwCountOverflow();
      }

      return var0;
   }

   public CollectionsKt__CollectionsJVMKt() {
      super();
   }
}
