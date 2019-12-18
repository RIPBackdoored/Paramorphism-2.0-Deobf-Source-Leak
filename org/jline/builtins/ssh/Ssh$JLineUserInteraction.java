package org.jline.builtins.ssh;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import org.apache.sshd.client.auth.keyboard.UserInteraction;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.config.keys.FilePasswordProvider;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

class Ssh$JLineUserInteraction implements UserInteraction, FilePasswordProvider {
   private final Terminal terminal;
   private final LineReader reader;
   private final PrintStream stderr;

   public Ssh$JLineUserInteraction(Terminal var1, LineReader var2, PrintStream var3) {
      super();
      this.terminal = var1;
      this.reader = var2;
      this.stderr = var3;
   }

   public String getPassword(String var1) throws IOException {
      return this.readLine("Enter password for " + var1 + ":", false);
   }

   public void welcome(ClientSession var1, String var2, String var3) {
      this.terminal.writer().println(var2);
   }

   public String[] interactive(ClientSession var1, String var2, String var3, String var4, String[] var5, boolean[] var6) {
      String[] var7 = new String[var5.length];

      try {
         for(int var8 = 0; var8 < var5.length; ++var8) {
            var7[var8] = this.readLine(var5[var8], var6[var8]);
         }
      } catch (Exception var9) {
         this.stderr.append(var9.getClass().getSimpleName()).append(" while read prompts: ").println(var9.getMessage());
      }

      return var7;
   }

   public boolean isInteractionAllowed(ClientSession var1) {
      return true;
   }

   public void serverVersionInfo(ClientSession var1, List var2) {
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         this.terminal.writer().append('\t').println(var4);
      }

   }

   public String getUpdatedPassword(ClientSession var1, String var2, String var3) {
      String var10000;
      try {
         var10000 = this.readLine(var2, false);
      } catch (Exception var5) {
         this.stderr.append(var5.getClass().getSimpleName()).append(" while reading password: ").println(var5.getMessage());
         return null;
      }

      return var10000;
   }

   private String readLine(String var1, boolean var2) {
      return this.reader.readLine(var1 + " ", var2 ? null : '\u0000');
   }
}
