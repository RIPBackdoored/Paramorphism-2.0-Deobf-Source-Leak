package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u00002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0007¢\u0006\u0004\b\t\u0010\n\u001a5\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001H\u0007¢\u0006\u0004\b\t\u0010\f\u001a~\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0014\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u000f2\u001a\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u00112(\u0010\u0012\u001a$\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u0013H\u0082\b¢\u0006\u0002\u0010\u0014\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"EMPTY", "", "", "[Ljava/lang/Object;", "MAX_SIZE", "", "collectionToArray", "collection", "", "toArray", "(Ljava/util/Collection;)[Ljava/lang/Object;", "a", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "toArrayImpl", "empty", "Lkotlin/Function0;", "alloc", "Lkotlin/Function1;", "trim", "Lkotlin/Function2;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)[Ljava/lang/Object;", "kotlin-stdlib"}
)
@JvmName(
   name = "CollectionToArray"
)
public final class CollectionToArray {
   private static final Object[] EMPTY = new Object[0];
   private static final int MAX_SIZE = 2147483645;

   @JvmName(
      name = "toArray"
   )
   @NotNull
   public static final Object[] toArray(@NotNull Collection var0) {
      boolean var1 = false;
      int var2 = var0.size();
      Object[] var10000;
      if (var2 == 0) {
         boolean var3 = false;
         var10000 = EMPTY;
      } else {
         Iterator var10 = var0.iterator();
         if (!var10.hasNext()) {
            boolean var4 = false;
            var10000 = EMPTY;
         } else {
            boolean var5 = false;
            Object[] var11 = new Object[var2];
            int var12 = 0;

            while(true) {
               while(true) {
                  var11[var12++] = var10.next();
                  if (var12 >= var11.length) {
                     if (!var10.hasNext()) {
                        var10000 = var11;
                        return var10000;
                     }

                     int var6 = var12 * 3 + 1 >>> 1;
                     if (var6 <= var12) {
                        if (var12 >= 2147483645) {
                           throw (Throwable)(new OutOfMemoryError());
                        }

                        var6 = 2147483645;
                     }

                     var11 = Arrays.copyOf(var11, var6);
                  } else if (!var10.hasNext()) {
                     boolean var9 = false;
                     var10000 = Arrays.copyOf(var11, var12);
                     return var10000;
                  }
               }
            }
         }
      }

      return var10000;
   }

   @JvmName(
      name = "toArray"
   )
   @NotNull
   public static final Object[] toArray(@NotNull Collection var0, @Nullable Object[] var1) {
      if (var1 == null) {
         throw (Throwable)(new NullPointerException());
      } else {
         boolean var2 = false;
         int var3 = var0.size();
         boolean var4;
         Object[] var10000;
         if (var3 == 0) {
            var4 = false;
            if (var1.length > 0) {
               var1[0] = null;
            }

            var10000 = var1;
         } else {
            Iterator var5 = var0.iterator();
            if (!var5.hasNext()) {
               var4 = false;
               if (var1.length > 0) {
                  var1[0] = null;
               }

               var10000 = var1;
            } else {
               boolean var6 = false;
               if (var3 <= var1.length) {
                  var10000 = var1;
               } else {
                  Object var13 = Array.newInstance(var1.getClass().getComponentType(), var3);
                  if (var13 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                  }

                  var10000 = (Object[])var13;
               }

               Object[] var7 = var10000;
               int var11 = 0;

               while(true) {
                  while(true) {
                     var7[var11++] = var5.next();
                     if (var11 >= var7.length) {
                        if (!var5.hasNext()) {
                           var10000 = var7;
                           return var10000;
                        }

                        int var12 = var11 * 3 + 1 >>> 1;
                        if (var12 <= var11) {
                           if (var11 >= 2147483645) {
                              throw (Throwable)(new OutOfMemoryError());
                           }

                           var12 = 2147483645;
                        }

                        var7 = Arrays.copyOf(var7, var12);
                     } else if (!var5.hasNext()) {
                        boolean var10 = false;
                        if (var7 == var1) {
                           var1[var11] = null;
                           var10000 = var1;
                        } else {
                           var10000 = Arrays.copyOf(var7, var11);
                        }

                        return var10000;
                     }
                  }
               }
            }
         }

         return var10000;
      }
   }

   private static final Object[] toArrayImpl(Collection var0, Function0 var1, Function1 var2, Function2 var3) {
      byte var4 = 0;
      int var5 = var0.size();
      if (var5 == 0) {
         return (Object[])var1.invoke();
      } else {
         Iterator var6 = var0.iterator();
         if (!var6.hasNext()) {
            return (Object[])var1.invoke();
         } else {
            Object[] var7 = (Object[])var2.invoke(var5);
            int var8 = 0;

            while(true) {
               while(true) {
                  var7[var8++] = var6.next();
                  if (var8 >= var7.length) {
                     if (!var6.hasNext()) {
                        return var7;
                     }

                     int var9 = var8 * 3 + 1 >>> 1;
                     if (var9 <= var8) {
                        if (var8 >= 2147483645) {
                           throw (Throwable)(new OutOfMemoryError());
                        }

                        var9 = 2147483645;
                     }

                     var7 = Arrays.copyOf(var7, var9);
                  } else if (!var6.hasNext()) {
                     return (Object[])var3.invoke(var7, var8);
                  }
               }
            }
         }
      }
   }
}
