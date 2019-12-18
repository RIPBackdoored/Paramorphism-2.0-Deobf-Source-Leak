package com.fasterxml.jackson.databind.ser.std;

import java.util.Map;

public class NumberSerializers {
   protected NumberSerializers() {
      super();
   }

   public static void addAll(Map var0) {
      var0.put(Integer.class.getName(), new NumberSerializers$IntegerSerializer(Integer.class));
      var0.put(Integer.TYPE.getName(), new NumberSerializers$IntegerSerializer(Integer.TYPE));
      var0.put(Long.class.getName(), new NumberSerializers$LongSerializer(Long.class));
      var0.put(Long.TYPE.getName(), new NumberSerializers$LongSerializer(Long.TYPE));
      var0.put(Byte.class.getName(), NumberSerializers$IntLikeSerializer.instance);
      var0.put(Byte.TYPE.getName(), NumberSerializers$IntLikeSerializer.instance);
      var0.put(Short.class.getName(), NumberSerializers$ShortSerializer.instance);
      var0.put(Short.TYPE.getName(), NumberSerializers$ShortSerializer.instance);
      var0.put(Double.class.getName(), new NumberSerializers$DoubleSerializer(Double.class));
      var0.put(Double.TYPE.getName(), new NumberSerializers$DoubleSerializer(Double.TYPE));
      var0.put(Float.class.getName(), NumberSerializers$FloatSerializer.instance);
      var0.put(Float.TYPE.getName(), NumberSerializers$FloatSerializer.instance);
   }
}
