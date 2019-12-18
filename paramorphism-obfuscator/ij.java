package paramorphism-obfuscator;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public final class ij {
   private final Terminal mq;
   private final int mr;
   @NotNull
   private final PrintStream ms;

   public final void a(@NotNull String var1, double var2, @NotNull String var4) {
      this.ms.print('\r');
      String var5 = var4;
      byte var7 = 0;
      int var8 = this.mr - 2 - var1.length() - 10;
      boolean var9 = false;
      int var6 = Math.max(var7, var8);
      if (var4.length() > var6) {
         byte var11 = 0;
         var9 = false;
         var5 = var4.substring(var11, var6);
      }

      int var10 = this.mr - 2 - var1.length() - var5.length();
      this.ms.print(var1);
      var8 = 0;

      int var12;
      for(var12 = (int)((double)var10 * var2); var8 < var12; ++var8) {
         this.ms.print('=');
      }

      if (var2 < 1.0D) {
         this.ms.print('>');
      }

      var8 = 0;

      for(var12 = var10 - 1 - (int)((double)var10 * var2); var8 < var12; ++var8) {
         this.ms.print('-');
      }

      this.ms.print(var5);
      this.ms.print('\r');
      this.ms.flush();
   }

   @NotNull
   public final PrintStream a() {
      return this.ms;
   }

   public ij(@NotNull PrintStream var1) {
      super();
      this.ms = var1;
      this.mq = TerminalBuilder.builder().dumb(true).build();
      byte var2 = 120;
      int var3 = this.mq.getWidth();
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      int var9 = var3 <= 10 ? 80 : var3;
      var4 = false;
      var9 = Math.min(var2, var9);
      this.mr = var9;
   }

   public ij(PrintStream var1, int var2, DefaultConstructorMarker var3) {
      if ((var2 & 1) != 0) {
         var1 = new PrintStream((OutputStream)(new FileOutputStream(FileDescriptor.out)));
      }

      this(var1);
   }

   public ij() {
      this((PrintStream)null, 1, (DefaultConstructorMarker)null);
   }
}
