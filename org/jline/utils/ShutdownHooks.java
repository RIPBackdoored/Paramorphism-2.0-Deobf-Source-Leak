package org.jline.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ShutdownHooks {
   private static final List tasks = new ArrayList();
   private static Thread hook;

   public ShutdownHooks() {
      super();
   }

   public static synchronized ShutdownHooks$Task add(ShutdownHooks$Task var0) {
      Objects.requireNonNull(var0);
      if (hook == null) {
         hook = addHook(new ShutdownHooks$1("JLine Shutdown Hook"));
      }

      Log.debug("Adding shutdown-hook task: ", var0);
      tasks.add(var0);
      return var0;
   }

   private static synchronized void runTasks() {
      Log.debug("Running all shutdown-hook tasks");
      ShutdownHooks$Task[] var0 = (ShutdownHooks$Task[])tasks.toArray(new ShutdownHooks$Task[tasks.size()]);
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         ShutdownHooks$Task var3 = var0[var2];
         Log.debug("Running task: ", var3);

         try {
            var3.run();
         } catch (Throwable var5) {
            Log.warn("Task failed", var5);
         }
      }

      tasks.clear();
   }

   private static Thread addHook(Thread var0) {
      Log.debug("Registering shutdown-hook: ", var0);
      Runtime.getRuntime().addShutdownHook(var0);
      return var0;
   }

   public static synchronized void remove(ShutdownHooks$Task var0) {
      Objects.requireNonNull(var0);
      if (hook != null) {
         tasks.remove(var0);
         if (tasks.isEmpty()) {
            removeHook(hook);
            hook = null;
         }

      }
   }

   private static void removeHook(Thread var0) {
      Log.debug("Removing shutdown-hook: ", var0);

      try {
         Runtime.getRuntime().removeShutdownHook(var0);
      } catch (IllegalStateException var2) {
      }

   }

   static void access$000() {
      runTasks();
   }
}
