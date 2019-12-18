package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.jvm.JvmName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0002\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0002\u001a\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b*\u00020\bH\u0001¢\u0006\u0002\u0010\r\u001a\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\bH\u0001¢\u0006\u0002\b\u0010\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"COROUTINES_DEBUG_METADATA_VERSION", "", "checkDebugMetadataVersion", "", "expected", "actual", "getDebugMetadataAnnotation", "Lkotlin/coroutines/jvm/internal/DebugMetadata;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getLabel", "getSpilledVariableFieldMapping", "", "", "(Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;)[Ljava/lang/String;", "getStackTraceElementImpl", "Ljava/lang/StackTraceElement;", "getStackTraceElement", "kotlin-stdlib"}
)
public final class DebugMetadataKt {
   private static final int COROUTINES_DEBUG_METADATA_VERSION = 1;

   @SinceKotlin(
      version = "1.3"
   )
   @JvmName(
      name = "getStackTraceElement"
   )
   @Nullable
   public static final StackTraceElement getStackTraceElement(@NotNull BaseContinuationImpl var0) {
      DebugMetadata var10000 = getDebugMetadataAnnotation(var0);
      if (var10000 != null) {
         DebugMetadata var1 = var10000;
         checkDebugMetadataVersion(1, var1.v());
         int var2 = getLabel(var0);
         int var3 = var2 < 0 ? -1 : var1.l()[var2];
         String var4 = ModuleNameRetriever.INSTANCE.getModuleName(var0);
         String var5 = var4 == null ? var1.c() : var4 + '/' + var1.c();
         return new StackTraceElement(var5, var1.m(), var1.f(), var3);
      } else {
         return null;
      }
   }

   private static final DebugMetadata getDebugMetadataAnnotation(@NotNull BaseContinuationImpl var0) {
      return (DebugMetadata)var0.getClass().getAnnotation(DebugMetadata.class);
   }

   private static final int getLabel(@NotNull BaseContinuationImpl var0) {
      int var1;
      try {
         Field var4 = var0.getClass().getDeclaredField("label");
         var4.setAccessible(true);
         Object var10000 = var4.get(var0);
         if (!(var10000 instanceof Integer)) {
            var10000 = null;
         }

         var1 = ((Integer)var10000 != null ? (Integer)var10000 : 0) - 1;
      } catch (Exception var3) {
         var1 = -1;
      }

      return var1;
   }

   private static final void checkDebugMetadataVersion(int var0, int var1) {
      if (var1 > var0) {
         String var2 = "Debug metadata version mismatch. Expected: " + var0 + ", got " + var1 + ". Please update the Kotlin standard library.";
         boolean var3 = false;
         throw (Throwable)(new IllegalStateException(var2.toString()));
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @JvmName(
      name = "getSpilledVariableFieldMapping"
   )
   @Nullable
   public static final String[] getSpilledVariableFieldMapping(@NotNull BaseContinuationImpl var0) {
      DebugMetadata var10000 = getDebugMetadataAnnotation(var0);
      if (var10000 != null) {
         DebugMetadata var1 = var10000;
         checkDebugMetadataVersion(1, var1.v());
         boolean var3 = false;
         ArrayList var2 = new ArrayList();
         int var8 = getLabel(var0);
         int[] var6 = var1.i();
         int var7 = var6.length;

         for(int var4 = 0; var4 < var7; ++var4) {
            int var5 = var6[var4];
            if (var5 == var8) {
               var2.add(var1.s()[var4]);
               var2.add(var1.n()[var4]);
            }
         }

         Collection var9 = (Collection)var2;
         boolean var10 = false;
         Object[] var11 = var9.toArray(new String[0]);
         if (var11 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         } else {
            return (String[])var11;
         }
      } else {
         return null;
      }
   }
}
