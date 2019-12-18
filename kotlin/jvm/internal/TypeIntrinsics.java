package kotlin.jvm.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Function;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableCollection;
import kotlin.jvm.internal.markers.KMutableIterable;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.jvm.internal.markers.KMutableListIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.jvm.internal.markers.KMutableMap$Entry;
import kotlin.jvm.internal.markers.KMutableSet;

public class TypeIntrinsics {
   public TypeIntrinsics() {
      super();
   }

   private static Throwable sanitizeStackTrace(Throwable var0) {
      return Intrinsics.sanitizeStackTrace(var0, TypeIntrinsics.class.getName());
   }

   public static void throwCce(Object var0, String var1) {
      String var2 = var0 == null ? "null" : var0.getClass().getName();
      throwCce(var2 + " cannot be cast to " + var1);
   }

   public static void throwCce(String var0) {
      throw throwCce(new ClassCastException(var0));
   }

   public static ClassCastException throwCce(ClassCastException var0) {
      throw (ClassCastException)sanitizeStackTrace(var0);
   }

   public static boolean isMutableIterator(Object var0) {
      return var0 instanceof Iterator && (!(var0 instanceof KMappedMarker) || var0 instanceof KMutableIterator);
   }

   public static Iterator asMutableIterator(Object var0) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableIterator)) {
         throwCce(var0, "kotlin.collections.MutableIterator");
      }

      return castToIterator(var0);
   }

   public static Iterator asMutableIterator(Object var0, String var1) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableIterator)) {
         throwCce(var1);
      }

      return castToIterator(var0);
   }

   public static Iterator castToIterator(Object var0) {
      Iterator var10000;
      try {
         var10000 = (Iterator)var0;
      } catch (ClassCastException var2) {
         throw throwCce(var2);
      }

      return var10000;
   }

   public static boolean isMutableListIterator(Object var0) {
      return var0 instanceof ListIterator && (!(var0 instanceof KMappedMarker) || var0 instanceof KMutableListIterator);
   }

   public static ListIterator asMutableListIterator(Object var0) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableListIterator)) {
         throwCce(var0, "kotlin.collections.MutableListIterator");
      }

      return castToListIterator(var0);
   }

   public static ListIterator asMutableListIterator(Object var0, String var1) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableListIterator)) {
         throwCce(var1);
      }

      return castToListIterator(var0);
   }

   public static ListIterator castToListIterator(Object var0) {
      ListIterator var10000;
      try {
         var10000 = (ListIterator)var0;
      } catch (ClassCastException var2) {
         throw throwCce(var2);
      }

      return var10000;
   }

   public static boolean isMutableIterable(Object var0) {
      return var0 instanceof Iterable && (!(var0 instanceof KMappedMarker) || var0 instanceof KMutableIterable);
   }

   public static Iterable asMutableIterable(Object var0) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableIterable)) {
         throwCce(var0, "kotlin.collections.MutableIterable");
      }

      return castToIterable(var0);
   }

   public static Iterable asMutableIterable(Object var0, String var1) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableIterable)) {
         throwCce(var1);
      }

      return castToIterable(var0);
   }

   public static Iterable castToIterable(Object var0) {
      Iterable var10000;
      try {
         var10000 = (Iterable)var0;
      } catch (ClassCastException var2) {
         throw throwCce(var2);
      }

      return var10000;
   }

   public static boolean isMutableCollection(Object var0) {
      return var0 instanceof Collection && (!(var0 instanceof KMappedMarker) || var0 instanceof KMutableCollection);
   }

   public static Collection asMutableCollection(Object var0) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableCollection)) {
         throwCce(var0, "kotlin.collections.MutableCollection");
      }

      return castToCollection(var0);
   }

   public static Collection asMutableCollection(Object var0, String var1) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableCollection)) {
         throwCce(var1);
      }

      return castToCollection(var0);
   }

   public static Collection castToCollection(Object var0) {
      Collection var10000;
      try {
         var10000 = (Collection)var0;
      } catch (ClassCastException var2) {
         throw throwCce(var2);
      }

      return var10000;
   }

   public static boolean isMutableList(Object var0) {
      return var0 instanceof List && (!(var0 instanceof KMappedMarker) || var0 instanceof KMutableList);
   }

   public static List asMutableList(Object var0) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableList)) {
         throwCce(var0, "kotlin.collections.MutableList");
      }

      return castToList(var0);
   }

   public static List asMutableList(Object var0, String var1) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableList)) {
         throwCce(var1);
      }

      return castToList(var0);
   }

   public static List castToList(Object var0) {
      List var10000;
      try {
         var10000 = (List)var0;
      } catch (ClassCastException var2) {
         throw throwCce(var2);
      }

      return var10000;
   }

   public static boolean isMutableSet(Object var0) {
      return var0 instanceof Set && (!(var0 instanceof KMappedMarker) || var0 instanceof KMutableSet);
   }

   public static Set asMutableSet(Object var0) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableSet)) {
         throwCce(var0, "kotlin.collections.MutableSet");
      }

      return castToSet(var0);
   }

   public static Set asMutableSet(Object var0, String var1) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableSet)) {
         throwCce(var1);
      }

      return castToSet(var0);
   }

   public static Set castToSet(Object var0) {
      Set var10000;
      try {
         var10000 = (Set)var0;
      } catch (ClassCastException var2) {
         throw throwCce(var2);
      }

      return var10000;
   }

   public static boolean isMutableMap(Object var0) {
      return var0 instanceof Map && (!(var0 instanceof KMappedMarker) || var0 instanceof KMutableMap);
   }

   public static Map asMutableMap(Object var0) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableMap)) {
         throwCce(var0, "kotlin.collections.MutableMap");
      }

      return castToMap(var0);
   }

   public static Map asMutableMap(Object var0, String var1) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableMap)) {
         throwCce(var1);
      }

      return castToMap(var0);
   }

   public static Map castToMap(Object var0) {
      Map var10000;
      try {
         var10000 = (Map)var0;
      } catch (ClassCastException var2) {
         throw throwCce(var2);
      }

      return var10000;
   }

   public static boolean isMutableMapEntry(Object var0) {
      return var0 instanceof Entry && (!(var0 instanceof KMappedMarker) || var0 instanceof KMutableMap$Entry);
   }

   public static Entry asMutableMapEntry(Object var0) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableMap$Entry)) {
         throwCce(var0, "kotlin.collections.MutableMap.MutableEntry");
      }

      return castToMapEntry(var0);
   }

   public static Entry asMutableMapEntry(Object var0, String var1) {
      if (var0 instanceof KMappedMarker && !(var0 instanceof KMutableMap$Entry)) {
         throwCce(var1);
      }

      return castToMapEntry(var0);
   }

   public static Entry castToMapEntry(Object var0) {
      Entry var10000;
      try {
         var10000 = (Entry)var0;
      } catch (ClassCastException var2) {
         throw throwCce(var2);
      }

      return var10000;
   }

   public static int getFunctionArity(Object var0) {
      if (var0 instanceof FunctionBase) {
         return ((FunctionBase)var0).getArity();
      } else if (var0 instanceof Function0) {
         return 0;
      } else if (var0 instanceof Function1) {
         return 1;
      } else if (var0 instanceof Function2) {
         return 2;
      } else if (var0 instanceof Function3) {
         return 3;
      } else if (var0 instanceof Function4) {
         return 4;
      } else if (var0 instanceof Function5) {
         return 5;
      } else if (var0 instanceof Function6) {
         return 6;
      } else if (var0 instanceof Function7) {
         return 7;
      } else if (var0 instanceof Function8) {
         return 8;
      } else if (var0 instanceof Function9) {
         return 9;
      } else if (var0 instanceof Function10) {
         return 10;
      } else if (var0 instanceof Function11) {
         return 11;
      } else if (var0 instanceof Function12) {
         return 12;
      } else if (var0 instanceof Function13) {
         return 13;
      } else if (var0 instanceof Function14) {
         return 14;
      } else if (var0 instanceof Function15) {
         return 15;
      } else if (var0 instanceof Function16) {
         return 16;
      } else if (var0 instanceof Function17) {
         return 17;
      } else if (var0 instanceof Function18) {
         return 18;
      } else if (var0 instanceof Function19) {
         return 19;
      } else if (var0 instanceof Function20) {
         return 20;
      } else if (var0 instanceof Function21) {
         return 21;
      } else {
         return var0 instanceof Function22 ? 22 : -1;
      }
   }

   public static boolean isFunctionOfArity(Object var0, int var1) {
      return var0 instanceof Function && getFunctionArity(var0) == var1;
   }

   public static Object beforeCheckcastToFunctionOfArity(Object var0, int var1) {
      if (var0 != null && !isFunctionOfArity(var0, var1)) {
         throwCce(var0, "kotlin.jvm.functions.Function" + var1);
      }

      return var0;
   }

   public static Object beforeCheckcastToFunctionOfArity(Object var0, int var1, String var2) {
      if (var0 != null && !isFunctionOfArity(var0, var1)) {
         throwCce(var2);
      }

      return var0;
   }
}
