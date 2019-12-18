package org.jline.builtins.ssh;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;
import org.apache.sshd.common.Factory;
import org.apache.sshd.server.command.Command;

public class ShellFactoryImpl implements Factory {
   private final Consumer shell;

   public ShellFactoryImpl(Consumer var1) {
      super();
      this.shell = var1;
   }

   private static void flush(OutputStream... var0) {
      OutputStream[] var1 = var0;
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         OutputStream var4 = var1[var3];

         try {
            var4.flush();
         } catch (IOException var6) {
         }
      }

   }

   static void close(Closeable... var0) {
      Closeable[] var1 = var0;
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Closeable var4 = var1[var3];

         try {
            var4.close();
         } catch (IOException var6) {
         }
      }

   }

   public Command create() {
      return new ShellFactoryImpl$ShellImpl(this);
   }

   public Object create() {
      return this.create();
   }

   static Consumer access$000(ShellFactoryImpl var0) {
      return var0.shell;
   }

   static void access$100(OutputStream[] var0) {
      flush(var0);
   }
}
