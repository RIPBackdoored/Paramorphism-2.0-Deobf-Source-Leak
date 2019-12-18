package dev.paramorphism.runtime;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;

public class Main {
   public static int guard;

   public static void main(String[] var0) {
      guard = 1;
      do.run();
      var0.main<invokedynamic>(var0);
   }

   private static CallSite link(Lookup var0, String var1, MethodType var2) {
      return new ConstantCallSite(var0.findStatic(site.hackery.paramorphism.launch.Main.class, var1, var2));
   }
}
