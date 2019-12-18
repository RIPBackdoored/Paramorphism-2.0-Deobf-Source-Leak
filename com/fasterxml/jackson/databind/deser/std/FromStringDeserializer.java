package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public abstract class FromStringDeserializer extends StdScalarDeserializer {
   public static Class[] types() {
      return new Class[]{File.class, URL.class, URI.class, Class.class, JavaType.class, Currency.class, Pattern.class, Locale.class, Charset.class, TimeZone.class, InetAddress.class, InetSocketAddress.class, StringBuilder.class};
   }

   protected FromStringDeserializer(Class var1) {
      super(var1);
   }

   public static FromStringDeserializer$Std findDeserializer(Class var0) {
      boolean var1 = false;
      byte var2;
      if (var0 == File.class) {
         var2 = 1;
      } else if (var0 == URL.class) {
         var2 = 2;
      } else if (var0 == URI.class) {
         var2 = 3;
      } else if (var0 == Class.class) {
         var2 = 4;
      } else if (var0 == JavaType.class) {
         var2 = 5;
      } else if (var0 == Currency.class) {
         var2 = 6;
      } else if (var0 == Pattern.class) {
         var2 = 7;
      } else if (var0 == Locale.class) {
         var2 = 8;
      } else if (var0 == Charset.class) {
         var2 = 9;
      } else if (var0 == TimeZone.class) {
         var2 = 10;
      } else if (var0 == InetAddress.class) {
         var2 = 11;
      } else if (var0 == InetSocketAddress.class) {
         var2 = 12;
      } else {
         if (var0 != StringBuilder.class) {
            return null;
         }

         var2 = 13;
      }

      return new FromStringDeserializer$Std(var0, var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      String var3 = var1.getValueAsString();
      JsonToken var4;
      if (var3 == null) {
         var4 = var1.getCurrentToken();
         if (var4 == JsonToken.START_ARRAY) {
            return this._deserializeFromArray(var1, var2);
         } else if (var4 == JsonToken.VALUE_EMBEDDED_OBJECT) {
            Object var9 = var1.getEmbeddedObject();
            if (var9 == null) {
               return null;
            } else {
               return this._valueClass.isAssignableFrom(var9.getClass()) ? var9 : this._deserializeEmbedded(var9, var2);
            }
         } else {
            return var2.handleUnexpectedToken(this._valueClass, var1);
         }
      } else if (var3.length() != 0 && (var3 = var3.trim()).length() != 0) {
         var4 = null;

         Object var10000;
         try {
            var10000 = this._deserialize(var3, var2);
         } catch (MalformedURLException | IllegalArgumentException var8) {
            String var5 = "not a valid textual representation";
            String var6 = var8.getMessage();
            if (var6 != null) {
               var5 = var5 + ", problem: " + var6;
            }

            JsonMappingException var7 = var2.weirdStringException(var3, this._valueClass, var5);
            var7.initCause(var8);
            throw var7;
         }

         return var10000;
      } else {
         return this._deserializeFromEmptyString();
      }
   }

   protected abstract Object _deserialize(String var1, DeserializationContext var2) throws IOException;

   protected Object _deserializeEmbedded(Object var1, DeserializationContext var2) throws IOException {
      var2.reportInputMismatch((JsonDeserializer)this, "Don't know how to convert embedded Object of type %s into %s", var1.getClass().getName(), this._valueClass.getName());
      return null;
   }

   protected Object _deserializeFromEmptyString() throws IOException {
      return null;
   }
}
