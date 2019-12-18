package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@JacksonStdImpl
public class StdKeyDeserializer extends KeyDeserializer implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final int TYPE_BOOLEAN = 1;
   public static final int TYPE_BYTE = 2;
   public static final int TYPE_SHORT = 3;
   public static final int TYPE_CHAR = 4;
   public static final int TYPE_INT = 5;
   public static final int TYPE_LONG = 6;
   public static final int TYPE_FLOAT = 7;
   public static final int TYPE_DOUBLE = 8;
   public static final int TYPE_LOCALE = 9;
   public static final int TYPE_DATE = 10;
   public static final int TYPE_CALENDAR = 11;
   public static final int TYPE_UUID = 12;
   public static final int TYPE_URI = 13;
   public static final int TYPE_URL = 14;
   public static final int TYPE_CLASS = 15;
   public static final int TYPE_CURRENCY = 16;
   public static final int TYPE_BYTE_ARRAY = 17;
   protected final int _kind;
   protected final Class _keyClass;
   protected final FromStringDeserializer _deser;

   protected StdKeyDeserializer(int var1, Class var2) {
      this(var1, var2, (FromStringDeserializer)null);
   }

   protected StdKeyDeserializer(int var1, Class var2, FromStringDeserializer var3) {
      super();
      this._kind = var1;
      this._keyClass = var2;
      this._deser = var3;
   }

   public static StdKeyDeserializer forType(Class var0) {
      if (var0 != String.class && var0 != Object.class && var0 != CharSequence.class) {
         byte var1;
         if (var0 == UUID.class) {
            var1 = 12;
         } else if (var0 == Integer.class) {
            var1 = 5;
         } else if (var0 == Long.class) {
            var1 = 6;
         } else if (var0 == Date.class) {
            var1 = 10;
         } else if (var0 == Calendar.class) {
            var1 = 11;
         } else if (var0 == Boolean.class) {
            var1 = 1;
         } else if (var0 == Byte.class) {
            var1 = 2;
         } else if (var0 == Character.class) {
            var1 = 4;
         } else if (var0 == Short.class) {
            var1 = 3;
         } else if (var0 == Float.class) {
            var1 = 7;
         } else if (var0 == Double.class) {
            var1 = 8;
         } else if (var0 == URI.class) {
            var1 = 13;
         } else if (var0 == URL.class) {
            var1 = 14;
         } else if (var0 == Class.class) {
            var1 = 15;
         } else {
            FromStringDeserializer$Std var2;
            if (var0 == Locale.class) {
               var2 = FromStringDeserializer.findDeserializer(Locale.class);
               return new StdKeyDeserializer(9, var0, var2);
            }

            if (var0 == Currency.class) {
               var2 = FromStringDeserializer.findDeserializer(Currency.class);
               return new StdKeyDeserializer(16, var0, var2);
            }

            if (var0 != byte[].class) {
               return null;
            }

            var1 = 17;
         }

         return new StdKeyDeserializer(var1, var0);
      } else {
         return StdKeyDeserializer$StringKD.forType(var0);
      }
   }

   public Object deserializeKey(String var1, DeserializationContext var2) throws IOException {
      if (var1 == null) {
         return null;
      } else {
         Object var10000;
         try {
            Object var3 = this._parse(var1, var2);
            if (var3 == null) {
               return this._keyClass.isEnum() && var2.getConfig().isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL) ? null : var2.handleWeirdKey(this._keyClass, var1, "not a valid representation");
            }

            var10000 = var3;
         } catch (Exception var4) {
            return var2.handleWeirdKey(this._keyClass, var1, "not a valid representation, problem: (%s) %s", var4.getClass().getName(), var4.getMessage());
         }

         return var10000;
      }
   }

   public Class getKeyClass() {
      return this._keyClass;
   }

   protected Object _parse(String var1, DeserializationContext var2) throws Exception {
      int var3;
      Object var11;
      switch(this._kind) {
      case 1:
         if ("true".equals(var1)) {
            return Boolean.TRUE;
         } else {
            if ("false".equals(var1)) {
               return Boolean.FALSE;
            }

            return var2.handleWeirdKey(this._keyClass, var1, "value not 'true' or 'false'");
         }
      case 2:
         var3 = this._parseInt(var1);
         if (var3 >= -128 && var3 <= 255) {
            return (byte)var3;
         }

         return var2.handleWeirdKey(this._keyClass, var1, "overflow, value cannot be represented as 8-bit value");
      case 3:
         var3 = this._parseInt(var1);
         if (var3 >= -32768 && var3 <= 32767) {
            return (short)var3;
         }

         return var2.handleWeirdKey(this._keyClass, var1, "overflow, value cannot be represented as 16-bit value");
      case 4:
         if (var1.length() == 1) {
            return var1.charAt(0);
         }

         return var2.handleWeirdKey(this._keyClass, var1, "can only convert 1-character Strings");
      case 5:
         return this._parseInt(var1);
      case 6:
         return this._parseLong(var1);
      case 7:
         return (float)this._parseDouble(var1);
      case 8:
         return this._parseDouble(var1);
      case 9:
         try {
            var11 = this._deser._deserialize(var1, var2);
         } catch (IllegalArgumentException var10) {
            return this._weirdKey(var2, var1, var10);
         }

         return var11;
      case 10:
         return var2.parseDate(var1);
      case 11:
         return var2.constructCalendar(var2.parseDate(var1));
      case 12:
         UUID var15;
         try {
            var15 = UUID.fromString(var1);
         } catch (Exception var9) {
            return this._weirdKey(var2, var1, var9);
         }

         return var15;
      case 13:
         URI var14;
         try {
            var14 = URI.create(var1);
         } catch (Exception var8) {
            return this._weirdKey(var2, var1, var8);
         }

         return var14;
      case 14:
         URL var13;
         try {
            var13 = new URL(var1);
         } catch (MalformedURLException var7) {
            return this._weirdKey(var2, var1, var7);
         }

         return var13;
      case 15:
         Class var12;
         try {
            var12 = var2.findClass(var1);
         } catch (Exception var6) {
            return var2.handleWeirdKey(this._keyClass, var1, "unable to parse key as Class");
         }

         return var12;
      case 16:
         try {
            var11 = this._deser._deserialize(var1, var2);
         } catch (IllegalArgumentException var5) {
            return this._weirdKey(var2, var1, var5);
         }

         return var11;
      case 17:
         byte[] var10000;
         try {
            var10000 = var2.getConfig().getBase64Variant().decode(var1);
         } catch (IllegalArgumentException var4) {
            return this._weirdKey(var2, var1, var4);
         }

         return var10000;
      default:
         throw new IllegalStateException("Internal error: unknown key type " + this._keyClass);
      }
   }

   protected int _parseInt(String var1) throws IllegalArgumentException {
      return Integer.parseInt(var1);
   }

   protected long _parseLong(String var1) throws IllegalArgumentException {
      return Long.parseLong(var1);
   }

   protected double _parseDouble(String var1) throws IllegalArgumentException {
      return NumberInput.parseDouble(var1);
   }

   protected Object _weirdKey(DeserializationContext var1, String var2, Exception var3) throws IOException {
      return var1.handleWeirdKey(this._keyClass, var2, "problem: %s", var3.getMessage());
   }
}
