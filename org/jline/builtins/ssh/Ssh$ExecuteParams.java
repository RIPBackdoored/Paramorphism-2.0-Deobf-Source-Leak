package org.jline.builtins.ssh;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class Ssh$ExecuteParams {
   private final String command;
   private final Map env;
   private final InputStream in;
   private final OutputStream out;
   private final OutputStream err;

   public Ssh$ExecuteParams(String var1, Map var2, InputStream var3, OutputStream var4, OutputStream var5) {
      super();
      this.command = var1;
      this.env = var2;
      this.in = var3;
      this.out = var4;
      this.err = var5;
   }

   public String getCommand() {
      return this.command;
   }

   public Map getEnv() {
      return this.env;
   }

   public InputStream getIn() {
      return this.in;
   }

   public OutputStream getOut() {
      return this.out;
   }

   public OutputStream getErr() {
      return this.err;
   }
}
