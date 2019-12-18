package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class FromStringDeserializer$Std extends FromStringDeserializer {
   private static final long serialVersionUID = 1L;
   public static final int STD_FILE = 1;
   public static final int STD_URL = 2;
   public static final int STD_URI = 3;
   public static final int STD_CLASS = 4;
   public static final int STD_JAVA_TYPE = 5;
   public static final int STD_CURRENCY = 6;
   public static final int STD_PATTERN = 7;
   public static final int STD_LOCALE = 8;
   public static final int STD_CHARSET = 9;
   public static final int STD_TIME_ZONE = 10;
   public static final int STD_INET_ADDRESS = 11;
   public static final int STD_INET_SOCKET_ADDRESS = 12;
   public static final int STD_STRING_BUILDER = 13;
   protected final int _kind;

   protected FromStringDeserializer$Std(Class var1, int var2) {
      super(var1);
      this._kind = var2;
   }

   protected Object _deserialize(String var1, DeserializationContext var2) throws IOException {
      int var3;
      switch(this._kind) {
      case 1:
         return new File(var1);
      case 2:
         return new URL(var1);
      case 3:
         return URI.create(var1);
      case 4:
         Class var10000;
         try {
            var10000 = var2.findClass(var1);
         } catch (Exception var6) {
            return var2.handleInstantiationProblem(this._valueClass, var1, ClassUtil.getRootCause(var6));
         }

         return var10000;
      case 5:
         return var2.getTypeFactory().constructFromCanonical(var1);
      case 6:
         return Currency.getInstance(var1);
      case 7:
         return Pattern.compile(var1);
      case 8:
         var3 = this._firstHyphenOrUnderscore(var1);
         if (var3 < 0) {
            return new Locale(var1);
         } else {
            String var7 = var1.substring(0, var3);
            var1 = var1.substring(var3 + 1);
            var3 = this._firstHyphenOrUnderscore(var1);
            if (var3 < 0) {
               return new Locale(var7, var1);
            }

            String var8 = var1.substring(0, var3);
            return new Locale(var7, var8, var1.substring(var3 + 1));
         }
      case 9:
         return Charset.forName(var1);
      case 10:
         return TimeZone.getTimeZone(var1);
      case 11:
         return InetAddress.getByName(var1);
      case 12:
         int var4;
         if (var1.startsWith("[")) {
            var3 = var1.lastIndexOf(93);
            if (var3 == -1) {
               throw new InvalidFormatException(var2.getParser(), "Bracketed IPv6 address must contain closing bracket", var1, InetSocketAddress.class);
            }

            var4 = var1.indexOf(58, var3);
            int var5 = var4 > -1 ? Integer.parseInt(var1.substring(var4 + 1)) : 0;
            return new InetSocketAddress(var1.substring(0, var3 + 1), var5);
         } else {
            var3 = var1.indexOf(58);
            if (var3 >= 0 && var1.indexOf(58, var3 + 1) < 0) {
               var4 = Integer.parseInt(var1.substring(var3 + 1));
               return new InetSocketAddress(var1.substring(0, var3), var4);
            }

            return new InetSocketAddress(var1, 0);
         }
      case 13:
         return new StringBuilder(var1);
      default:
         VersionUtil.throwInternal();
         return null;
      }
   }

   protected Object _deserializeFromEmptyString() throws IOException {
      if (this._kind == 3) {
         return URI.create("");
      } else if (this._kind == 8) {
         return Locale.ROOT;
      } else {
         return this._kind == 13 ? new StringBuilder() : super._deserializeFromEmptyString();
      }
   }

   protected int _firstHyphenOrUnderscore(String var1) {
      int var2 = 0;

      for(int var3 = var1.length(); var2 < var3; ++var2) {
         char var4 = var1.charAt(var2);
         if (var4 == '_' || var4 == '-') {
            return var2;
         }
      }

      return -1;
   }
}
