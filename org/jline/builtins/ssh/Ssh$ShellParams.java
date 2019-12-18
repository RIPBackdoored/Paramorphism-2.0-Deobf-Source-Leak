package org.jline.builtins.ssh;

import java.util.Map;
import org.jline.terminal.Terminal;

public class Ssh$ShellParams {
   private final Map env;
   private final Terminal terminal;
   private final Runnable closer;

   public Ssh$ShellParams(Map var1, Terminal var2, Runnable var3) {
      super();
      this.env = var1;
      this.terminal = var2;
      this.closer = var3;
   }

   public Map getEnv() {
      return this.env;
   }

   public Terminal getTerminal() {
      return this.terminal;
   }

   public Runnable getCloser() {
      return this.closer;
   }
}
