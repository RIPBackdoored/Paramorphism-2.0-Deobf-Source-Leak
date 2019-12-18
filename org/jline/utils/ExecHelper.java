package org.jline.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.Objects;

public final class ExecHelper {
   private ExecHelper() {
      super();
   }

   public static String exec(boolean var0, String... var1) throws IOException {
      Objects.requireNonNull(var1);

      String var10000;
      try {
         Log.trace("Running: ", var1);
         ProcessBuilder var2 = new ProcessBuilder(var1);
         if (var0) {
            var2.redirectInput(Redirect.INHERIT);
         }

         Process var3 = var2.start();
         String var4 = waitAndCapture(var3);
         Log.trace("Result: ", var4);
         if (var3.exitValue() != 0) {
            if (var4.endsWith("\n")) {
               var4 = var4.substring(0, var4.length() - 1);
            }

            throw new IOException("Error executing '" + String.join(" ", (CharSequence[])var1) + "': " + var4);
         }

         var10000 = var4;
      } catch (InterruptedException var5) {
         throw (IOException)(new InterruptedIOException("Command interrupted")).initCause(var5);
      }

      return var10000;
   }

   public static String waitAndCapture(Process var0) throws IOException, InterruptedException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      InputStream var2 = null;
      InputStream var3 = null;
      OutputStream var4 = null;
      boolean var8 = false;

      try {
         var8 = true;
         var2 = var0.getInputStream();

         int var5;
         while((var5 = var2.read()) != -1) {
            var1.write(var5);
         }

         var3 = var0.getErrorStream();

         while(true) {
            if ((var5 = var3.read()) == -1) {
               var4 = var0.getOutputStream();
               var0.waitFor();
               var8 = false;
               break;
            }

            var1.write(var5);
         }
      } finally {
         if (var8) {
            close(var2, var4, var3);
         }
      }

      close(var2, var4, var3);
      return var1.toString();
   }

   private static void close(Closeable... var0) {
      Closeable[] var1 = var0;
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Closeable var4 = var1[var3];
         if (var4 != null) {
            try {
               var4.close();
            } catch (Exception var6) {
            }
         }
      }

   }
}
