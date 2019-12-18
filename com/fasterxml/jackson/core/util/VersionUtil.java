package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

public class VersionUtil {
   private static final Pattern V_SEP = Pattern.compile("[-_./;:]");

   protected VersionUtil() {
      super();
   }

   /** @deprecated */
   @Deprecated
   public Version version() {
      return Version.unknownVersion();
   }

   public static Version versionFor(Class var0) {
      Version var1 = packageVersionFor(var0);
      return var1 == null ? Version.unknownVersion() : var1;
   }

   public static Version packageVersionFor(Class var0) {
      Version var1 = null;

      try {
         String var2 = var0.getPackage().getName() + ".PackageVersion";
         Class var3 = Class.forName(var2, true, var0.getClassLoader());

         try {
            var1 = ((Versioned)var3.getDeclaredConstructor().newInstance()).version();
         } catch (Exception var5) {
            throw new IllegalArgumentException("Failed to get Versioned out of " + var3);
         }
      } catch (Exception var6) {
      }

      return var1 == null ? Version.unknownVersion() : var1;
   }

   /** @deprecated */
   @Deprecated
   public static Version mavenVersionFor(ClassLoader var0, String var1, String var2) {
      InputStream var3 = var0.getResourceAsStream("META-INF/maven/" + var1.replaceAll("\\.", "/") + "/" + var2 + "/pom.properties");
      if (var3 != null) {
         boolean var12 = false;

         label37: {
            Version var8;
            try {
               var12 = true;
               Properties var4 = new Properties();
               var4.load(var3);
               String var5 = var4.getProperty("version");
               String var6 = var4.getProperty("artifactId");
               String var7 = var4.getProperty("groupId");
               var8 = parseVersion(var5, var7, var6);
               var12 = false;
            } catch (IOException var13) {
               var12 = false;
               break label37;
            } finally {
               if (var12) {
                  _close(var3);
               }
            }

            _close(var3);
            return var8;
         }

         _close(var3);
      }

      return Version.unknownVersion();
   }

   public static Version parseVersion(String var0, String var1, String var2) {
      if (var0 != null && (var0 = var0.trim()).length() > 0) {
         String[] var3 = V_SEP.split(var0);
         return new Version(parseVersionPart(var3[0]), var3.length > 1 ? parseVersionPart(var3[1]) : 0, var3.length > 2 ? parseVersionPart(var3[2]) : 0, var3.length > 3 ? var3[3] : null, var1, var2);
      } else {
         return Version.unknownVersion();
      }
   }

   protected static int parseVersionPart(String var0) {
      int var1 = 0;
      int var2 = 0;

      for(int var3 = var0.length(); var2 < var3; ++var2) {
         char var4 = var0.charAt(var2);
         if (var4 > '9' || var4 < '0') {
            break;
         }

         var1 = var1 * 10 + (var4 - 48);
      }

      return var1;
   }

   private static final void _close(Closeable var0) {
      try {
         var0.close();
      } catch (IOException var2) {
      }

   }

   public static final void throwInternal() {
      throw new RuntimeException("Internal error: this code path should never get executed");
   }
}
