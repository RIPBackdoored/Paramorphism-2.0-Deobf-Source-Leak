package org.jline.utils;

final class ShutdownHooks$1 extends Thread {
   ShutdownHooks$1(String var1) {
      super(var1);
   }

   public void run() {
      ShutdownHooks.access$000();
   }
}
