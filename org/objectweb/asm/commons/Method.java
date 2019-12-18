package org.objectweb.asm.commons;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.Type;

public class Method {
   private final String name;
   private final String descriptor;
   private static final Map PRIMITIVE_TYPE_DESCRIPTORS;

   public Method(String var1, String var2) {
      super();
      this.name = var1;
      this.descriptor = var2;
   }

   public Method(String var1, Type var2, Type[] var3) {
      this(var1, Type.getMethodDescriptor(var2, var3));
   }

   public static Method getMethod(java.lang.reflect.Method var0) {
      return new Method(var0.getName(), Type.getMethodDescriptor(var0));
   }

   public static Method getMethod(Constructor var0) {
      return new Method("<init>", Type.getConstructorDescriptor(var0));
   }

   public static Method getMethod(String var0) {
      return getMethod(var0, false);
   }

   public static Method getMethod(String var0, boolean var1) {
      int var2 = var0.indexOf(32);
      int var3 = var0.indexOf(40, var2) + 1;
      int var4 = var0.indexOf(41, var3);
      if (var2 != -1 && var3 != 0 && var4 != -1) {
         String var5 = var0.substring(0, var2);
         String var6 = var0.substring(var2 + 1, var3 - 1).trim();
         StringBuilder var7 = new StringBuilder();
         var7.append('(');

         int var8;
         do {
            var8 = var0.indexOf(44, var3);
            String var9;
            if (var8 == -1) {
               var9 = getDescriptorInternal(var0.substring(var3, var4).trim(), var1);
            } else {
               var9 = getDescriptorInternal(var0.substring(var3, var8).trim(), var1);
               var3 = var8 + 1;
            }

            var7.append(var9);
         } while(var8 != -1);

         var7.append(')').append(getDescriptorInternal(var5, var1));
         return new Method(var6, var7.toString());
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static String getDescriptorInternal(String var0, boolean var1) {
      if ("".equals(var0)) {
         return var0;
      } else {
         StringBuilder var2 = new StringBuilder();
         int var3 = 0;

         while((var3 = var0.indexOf("[]", var3) + 1) > 0) {
            var2.append('[');
         }

         String var4 = var0.substring(0, var0.length() - var2.length() * 2);
         String var5 = (String)PRIMITIVE_TYPE_DESCRIPTORS.get(var4);
         if (var5 != null) {
            var2.append(var5);
         } else {
            var2.append('L');
            if (var4.indexOf(46) < 0) {
               if (!var1) {
                  var2.append("java/lang/");
               }

               var2.append(var4);
            } else {
               var2.append(var4.replace('.', '/'));
            }

            var2.append(';');
         }

         return var2.toString();
      }
   }

   public String getName() {
      return this.name;
   }

   public String getDescriptor() {
      return this.descriptor;
   }

   public Type getReturnType() {
      return Type.getReturnType(this.descriptor);
   }

   public Type[] getArgumentTypes() {
      return Type.getArgumentTypes(this.descriptor);
   }

   public String toString() {
      return this.name + this.descriptor;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof Method)) {
         return false;
      } else {
         Method var2 = (Method)var1;
         return this.name.equals(var2.name) && this.descriptor.equals(var2.descriptor);
      }
   }

   public int hashCode() {
      return this.name.hashCode() ^ this.descriptor.hashCode();
   }

   static {
      HashMap var0 = new HashMap();
      var0.put("void", "V");
      var0.put("byte", "B");
      var0.put("char", "C");
      var0.put("double", "D");
      var0.put("float", "F");
      var0.put("int", "I");
      var0.put("long", "J");
      var0.put("short", "S");
      var0.put("boolean", "Z");
      PRIMITIVE_TYPE_DESCRIPTORS = var0;
   }
}
