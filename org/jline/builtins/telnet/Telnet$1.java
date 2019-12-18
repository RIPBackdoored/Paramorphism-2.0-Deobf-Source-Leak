package org.jline.builtins.telnet;

class Telnet$1 extends ConnectionManager {
   final Telnet this$0;

   Telnet$1(Telnet var1, int var2, int var3, int var4, int var5, ConnectionFilter var6, String var7, boolean var8) {
      super(var2, var3, var4, var5, var6, var7, var8);
      this.this$0 = var1;
   }

   protected Connection createConnection(ThreadGroup var1, ConnectionData var2) {
      return new Telnet$1$1(this, var1, var2);
   }
}
