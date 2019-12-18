package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.JsonSerializer;
import java.util.HashMap;

public class StdArraySerializers {
   protected static final HashMap _arraySerializers = new HashMap();

   protected StdArraySerializers() {
      super();
   }

   public static JsonSerializer findStandardImpl(Class var0) {
      return (JsonSerializer)_arraySerializers.get(var0.getName());
   }

   static {
      _arraySerializers.put(boolean[].class.getName(), new StdArraySerializers$BooleanArraySerializer());
      _arraySerializers.put(byte[].class.getName(), new ByteArraySerializer());
      _arraySerializers.put(char[].class.getName(), new StdArraySerializers$CharArraySerializer());
      _arraySerializers.put(short[].class.getName(), new StdArraySerializers$ShortArraySerializer());
      _arraySerializers.put(int[].class.getName(), new StdArraySerializers$IntArraySerializer());
      _arraySerializers.put(long[].class.getName(), new StdArraySerializers$LongArraySerializer());
      _arraySerializers.put(float[].class.getName(), new StdArraySerializers$FloatArraySerializer());
      _arraySerializers.put(double[].class.getName(), new StdArraySerializers$DoubleArraySerializer());
   }
}
