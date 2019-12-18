package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.JsonDeserializer;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class JdkDeserializers {
   private static final HashSet _classNames = new HashSet();

   public JdkDeserializers() {
      super();
   }

   public static JsonDeserializer find(Class var0, String var1) {
      if (_classNames.contains(var1)) {
         FromStringDeserializer$Std var2 = FromStringDeserializer.findDeserializer(var0);
         if (var2 != null) {
            return var2;
         }

         if (var0 == UUID.class) {
            return new UUIDDeserializer();
         }

         if (var0 == StackTraceElement.class) {
            return new StackTraceElementDeserializer();
         }

         if (var0 == AtomicBoolean.class) {
            return new AtomicBooleanDeserializer();
         }

         if (var0 == ByteBuffer.class) {
            return new ByteBufferDeserializer();
         }
      }

      return null;
   }

   static {
      Class[] var0 = new Class[]{UUID.class, AtomicBoolean.class, StackTraceElement.class, ByteBuffer.class};
      Class[] var1 = var0;
      int var2 = var0.length;

      int var3;
      Class var4;
      for(var3 = 0; var3 < var2; ++var3) {
         var4 = var1[var3];
         _classNames.add(var4.getName());
      }

      var1 = FromStringDeserializer.types();
      var2 = var1.length;

      for(var3 = 0; var3 < var2; ++var3) {
         var4 = var1[var3];
         _classNames.add(var4.getName());
      }

   }
}
