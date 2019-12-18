package com.fasterxml.jackson.databind.ser.std;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class StdJdkSerializers {
   public StdJdkSerializers() {
      super();
   }

   public static Collection all() {
      HashMap var0 = new HashMap();
      var0.put(URL.class, new ToStringSerializer(URL.class));
      var0.put(URI.class, new ToStringSerializer(URI.class));
      var0.put(Currency.class, new ToStringSerializer(Currency.class));
      var0.put(UUID.class, new UUIDSerializer());
      var0.put(Pattern.class, new ToStringSerializer(Pattern.class));
      var0.put(Locale.class, new ToStringSerializer(Locale.class));
      var0.put(AtomicBoolean.class, StdJdkSerializers$AtomicBooleanSerializer.class);
      var0.put(AtomicInteger.class, StdJdkSerializers$AtomicIntegerSerializer.class);
      var0.put(AtomicLong.class, StdJdkSerializers$AtomicLongSerializer.class);
      var0.put(File.class, FileSerializer.class);
      var0.put(Class.class, ClassSerializer.class);
      var0.put(Void.class, NullSerializer.instance);
      var0.put(Void.TYPE, NullSerializer.instance);

      try {
         var0.put(Timestamp.class, DateSerializer.instance);
         var0.put(Date.class, SqlDateSerializer.class);
         var0.put(Time.class, SqlTimeSerializer.class);
      } catch (NoClassDefFoundError var2) {
      }

      return var0.entrySet();
   }
}
