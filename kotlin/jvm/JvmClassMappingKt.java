package kotlin.jvm;

import java.lang.annotation.Annotation;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001f\u0010\u0018\u001a\u00020\u0019\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\r*\u0006\u0012\u0002\b\u00030\u001a¢\u0006\u0002\u0010\u001b\"'\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"-\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018G¢\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"&\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\u0002H\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u000e\";\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018Ç\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u000f\u0010\t\u001a\u0004\b\u0010\u0010\u000b\"+\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b\"-\u0010\u0013\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000b\"+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00078G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001c"},
   d2 = {"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "java", "Ljava/lang/Class;", "java$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "javaClass$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"}
)
@JvmName(
   name = "JvmClassMappingKt"
)
public final class JvmClassMappingKt {
   /** @deprecated */
   public static void java$annotations(KClass var0) {
   }

   @JvmName(
      name = "getJavaClass"
   )
   @NotNull
   public static final Class getJavaClass(@NotNull KClass var0) {
      return ((ClassBasedDeclarationContainer)var0).getJClass();
   }

   @Nullable
   public static final Class getJavaPrimitiveType(@NotNull KClass var0) {
      Class var1 = ((ClassBasedDeclarationContainer)var0).getJClass();
      if (var1.isPrimitive()) {
         if (var1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
         } else {
            return var1;
         }
      } else {
         String var10000 = var1.getName();
         Class var3;
         if (var10000 != null) {
            String var2 = var10000;
            switch(var2.hashCode()) {
            case -2056817302:
               if (var2.equals("java.lang.Integer")) {
                  var3 = Integer.TYPE;
                  return var3;
               }
               break;
            case -527879800:
               if (var2.equals("java.lang.Float")) {
                  var3 = Float.TYPE;
                  return var3;
               }
               break;
            case -515992664:
               if (var2.equals("java.lang.Short")) {
                  var3 = Short.TYPE;
                  return var3;
               }
               break;
            case 155276373:
               if (var2.equals("java.lang.Character")) {
                  var3 = Character.TYPE;
                  return var3;
               }
               break;
            case 344809556:
               if (var2.equals("java.lang.Boolean")) {
                  var3 = Boolean.TYPE;
                  return var3;
               }
               break;
            case 398507100:
               if (var2.equals("java.lang.Byte")) {
                  var3 = Byte.TYPE;
                  return var3;
               }
               break;
            case 398795216:
               if (var2.equals("java.lang.Long")) {
                  var3 = Long.TYPE;
                  return var3;
               }
               break;
            case 399092968:
               if (var2.equals("java.lang.Void")) {
                  var3 = Void.TYPE;
                  return var3;
               }
               break;
            case 761287205:
               if (var2.equals("java.lang.Double")) {
                  var3 = Double.TYPE;
                  return var3;
               }
            }
         }

         var3 = null;
         return var3;
      }
   }

   @NotNull
   public static final Class getJavaObjectType(@NotNull KClass var0) {
      Class var1 = ((ClassBasedDeclarationContainer)var0).getJClass();
      if (!var1.isPrimitive()) {
         return var1;
      } else {
         String var10000 = var1.getName();
         Class var3;
         if (var10000 != null) {
            String var2 = var10000;
            switch(var2.hashCode()) {
            case -1325958191:
               if (var2.equals("double")) {
                  var3 = Double.class;
                  return var3;
               }
               break;
            case 104431:
               if (var2.equals("int")) {
                  var3 = Integer.class;
                  return var3;
               }
               break;
            case 3039496:
               if (var2.equals("byte")) {
                  var3 = Byte.class;
                  return var3;
               }
               break;
            case 3052374:
               if (var2.equals("char")) {
                  var3 = Character.class;
                  return var3;
               }
               break;
            case 3327612:
               if (var2.equals("long")) {
                  var3 = Long.class;
                  return var3;
               }
               break;
            case 3625364:
               if (var2.equals("void")) {
                  var3 = Void.class;
                  return var3;
               }
               break;
            case 64711720:
               if (var2.equals("boolean")) {
                  var3 = Boolean.class;
                  return var3;
               }
               break;
            case 97526364:
               if (var2.equals("float")) {
                  var3 = Float.class;
                  return var3;
               }
               break;
            case 109413500:
               if (var2.equals("short")) {
                  var3 = Short.class;
                  return var3;
               }
            }
         }

         var3 = var1;
         return var3;
      }
   }

   @JvmName(
      name = "getKotlinClass"
   )
   @NotNull
   public static final KClass getKotlinClass(@NotNull Class var0) {
      return Reflection.getOrCreateKotlinClass(var0);
   }

   @NotNull
   public static final Class getJavaClass(@NotNull Object var0) {
      byte var1 = 0;
      return var0.getClass();
   }

   /** @deprecated */
   @Deprecated(
      message = "Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "(this as Any).javaClass"
),
      level = DeprecationLevel.ERROR
   )
   public static void javaClass$annotations(KClass var0) {
   }

   /** @deprecated */
   @JvmName(
      name = "getRuntimeClassOfKClassInstance"
   )
   @NotNull
   public static final Class getRuntimeClassOfKClassInstance(@NotNull KClass var0) {
      byte var1 = 0;
      return ((Object)var0).getClass();
   }

   private static final boolean isArrayOf(@NotNull Object[] var0) {
      Intrinsics.reifiedOperationMarker(4, "T");
      return Object.class.isAssignableFrom(var0.getClass().getComponentType());
   }

   @NotNull
   public static final KClass getAnnotationClass(@NotNull Annotation var0) {
      return getKotlinClass(var0.annotationType());
   }
}
