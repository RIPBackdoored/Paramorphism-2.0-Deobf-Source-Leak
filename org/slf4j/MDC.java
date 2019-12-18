package org.slf4j;

import java.util.Map;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticMDCBinder;
import org.slf4j.spi.MDCAdapter;

public class MDC {
   static final String NULL_MDCA_URL = "http://www.slf4j.org/codes.html#null_MDCA";
   static final String NO_STATIC_MDC_BINDER_URL = "http://www.slf4j.org/codes.html#no_static_mdc_binder";
   static MDCAdapter mdcAdapter;

   private MDC() {
      super();
   }

   private static MDCAdapter bwCompatibleGetMDCAdapterFromBinder() throws NoClassDefFoundError {
      MDCAdapter var10000;
      try {
         var10000 = StaticMDCBinder.getSingleton().getMDCA();
      } catch (NoSuchMethodError var1) {
         return StaticMDCBinder.SINGLETON.getMDCA();
      }

      return var10000;
   }

   public static void put(String var0, String var1) throws IllegalArgumentException {
      if (var0 == null) {
         throw new IllegalArgumentException("key parameter cannot be null");
      } else if (mdcAdapter == null) {
         throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
      } else {
         mdcAdapter.put(var0, var1);
      }
   }

   public static MDC$MDCCloseable putCloseable(String var0, String var1) throws IllegalArgumentException {
      put(var0, var1);
      return new MDC$MDCCloseable(var0, (MDC$1)null);
   }

   public static String get(String var0) throws IllegalArgumentException {
      if (var0 == null) {
         throw new IllegalArgumentException("key parameter cannot be null");
      } else if (mdcAdapter == null) {
         throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
      } else {
         return mdcAdapter.get(var0);
      }
   }

   public static void remove(String var0) throws IllegalArgumentException {
      if (var0 == null) {
         throw new IllegalArgumentException("key parameter cannot be null");
      } else if (mdcAdapter == null) {
         throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
      } else {
         mdcAdapter.remove(var0);
      }
   }

   public static void clear() {
      if (mdcAdapter == null) {
         throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
      } else {
         mdcAdapter.clear();
      }
   }

   public static Map getCopyOfContextMap() {
      if (mdcAdapter == null) {
         throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
      } else {
         return mdcAdapter.getCopyOfContextMap();
      }
   }

   public static void setContextMap(Map var0) {
      if (mdcAdapter == null) {
         throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
      } else {
         mdcAdapter.setContextMap(var0);
      }
   }

   public static MDCAdapter getMDCAdapter() {
      return mdcAdapter;
   }

   static {
      try {
         mdcAdapter = bwCompatibleGetMDCAdapterFromBinder();
      } catch (NoClassDefFoundError var2) {
         mdcAdapter = new NOPMDCAdapter();
         String var1 = var2.getMessage();
         if (var1 == null || !var1.contains("StaticMDCBinder")) {
            throw var2;
         }

         Util.report("Failed to load class \"org.slf4j.impl.StaticMDCBinder\".");
         Util.report("Defaulting to no-operation MDCAdapter implementation.");
         Util.report("See http://www.slf4j.org/codes.html#no_static_mdc_binder for further details.");
      } catch (Exception var3) {
         Util.report("MDC binding unsuccessful.", var3);
      }

   }
}
