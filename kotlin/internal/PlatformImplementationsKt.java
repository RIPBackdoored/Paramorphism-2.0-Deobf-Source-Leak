package kotlin.internal;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\"\u0010\b\u001a\u0002H\t\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0083\b¢\u0006\u0002\u0010\f\u001a\b\u0010\r\u001a\u00020\u0005H\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"}
)
public final class PlatformImplementationsKt {
   @JvmField
   @NotNull
   public static final PlatformImplementations IMPLEMENTATIONS;

   @InlineOnly
   private static final Object castToBaseType(Object var0) {
      byte var1 = 0;

      try {
         Intrinsics.reifiedOperationMarker(1, "T");
         return (Object)var0;
      } catch (ClassCastException var5) {
         ClassLoader var3 = var0.getClass().getClassLoader();
         Intrinsics.reifiedOperationMarker(4, "T");
         ClassLoader var4 = Object.class.getClassLoader();
         throw (new ClassCastException("Instance classloader: " + var3 + ", base type classloader: " + var4)).initCause((Throwable)var5);
      }
   }

   private static final int getJavaVersion() {
      int var0 = 65542;
      String var10000 = System.getProperty("java.specification.version");
      if (var10000 != null) {
         String var1 = var10000;
         int var2 = StringsKt.indexOf$default((CharSequence)var1, '.', 0, false, 6, (Object)null);
         int var3;
         if (var2 < 0) {
            try {
               boolean var13 = false;
               var3 = Integer.parseInt(var1) * 65536;
            } catch (NumberFormatException var11) {
               var3 = var0;
            }

            return var3;
         } else {
            var3 = StringsKt.indexOf$default((CharSequence)var1, '.', var2 + 1, false, 4, (Object)null);
            if (var3 < 0) {
               var3 = var1.length();
            }

            byte var6 = 0;
            boolean var7 = false;
            if (var1 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
               String var4 = var1.substring(var6, var2);
               int var15 = var2 + 1;
               boolean var8 = false;
               if (var1 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               } else {
                  String var5 = var1.substring(var15, var3);

                  int var14;
                  try {
                     var7 = false;
                     int var9 = Integer.parseInt(var4) * 65536;
                     var7 = false;
                     int var10 = Integer.parseInt(var5);
                     var14 = var9 + var10;
                  } catch (NumberFormatException var12) {
                     var14 = var0;
                  }

                  return var14;
               }
            }
         }
      } else {
         return var0;
      }
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.2"
   )
   public static final boolean apiVersionIsAtLeast(int var0, int var1, int var2) {
      return KotlinVersion.CURRENT.isAtLeast(var0, var1, var2);
   }

   static {
      PlatformImplementations var10000;
      label78: {
         boolean var0 = false;
         boolean var1 = false;
         boolean var2 = false;
         int var3 = getJavaVersion();
         Object var4;
         boolean var5;
         ClassLoader var7;
         ClassLoader var8;
         if (var3 >= 65544) {
            try {
               var4 = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
               var5 = false;

               try {
                  if (var4 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                  }

                  var10000 = (PlatformImplementations)var4;
                  break label78;
               } catch (ClassCastException var12) {
                  var7 = var4.getClass().getClassLoader();
                  var8 = PlatformImplementations.class.getClassLoader();
                  throw (new ClassCastException("Instance classloader: " + var7 + ", base type classloader: " + var8)).initCause((Throwable)var12);
               }
            } catch (ClassNotFoundException var16) {
               try {
                  var4 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
                  var5 = false;

                  try {
                     if (var4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                     }

                     var10000 = (PlatformImplementations)var4;
                     break label78;
                  } catch (ClassCastException var11) {
                     var7 = var4.getClass().getClassLoader();
                     var8 = PlatformImplementations.class.getClassLoader();
                     throw (new ClassCastException("Instance classloader: " + var7 + ", base type classloader: " + var8)).initCause((Throwable)var11);
                  }
               } catch (ClassNotFoundException var15) {
               }
            }
         }

         if (var3 >= 65543) {
            try {
               var4 = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
               var5 = false;

               try {
                  if (var4 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                  }

                  var10000 = (PlatformImplementations)var4;
                  break label78;
               } catch (ClassCastException var10) {
                  var7 = var4.getClass().getClassLoader();
                  var8 = PlatformImplementations.class.getClassLoader();
                  throw (new ClassCastException("Instance classloader: " + var7 + ", base type classloader: " + var8)).initCause((Throwable)var10);
               }
            } catch (ClassNotFoundException var14) {
               try {
                  var4 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                  var5 = false;

                  try {
                     if (var4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                     }

                     var10000 = (PlatformImplementations)var4;
                     break label78;
                  } catch (ClassCastException var9) {
                     var7 = var4.getClass().getClassLoader();
                     var8 = PlatformImplementations.class.getClassLoader();
                     throw (new ClassCastException("Instance classloader: " + var7 + ", base type classloader: " + var8)).initCause((Throwable)var9);
                  }
               } catch (ClassNotFoundException var13) {
               }
            }
         }

         var10000 = new PlatformImplementations();
      }

      IMPLEMENTATIONS = var10000;
   }
}
