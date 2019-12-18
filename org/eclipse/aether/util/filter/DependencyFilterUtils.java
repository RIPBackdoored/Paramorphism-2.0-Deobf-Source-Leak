package org.eclipse.aether.util.filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import org.eclipse.aether.graph.DependencyFilter;

public final class DependencyFilterUtils {
   private DependencyFilterUtils() {
      super();
   }

   public static DependencyFilter notFilter(DependencyFilter var0) {
      return new NotDependencyFilter(var0);
   }

   public static DependencyFilter andFilter(DependencyFilter... var0) {
      return (DependencyFilter)(var0 != null && var0.length == 1 ? var0[0] : new AndDependencyFilter(var0));
   }

   public static DependencyFilter andFilter(Collection var0) {
      return (DependencyFilter)(var0 != null && var0.size() == 1 ? (DependencyFilter)var0.iterator().next() : new AndDependencyFilter(var0));
   }

   public static DependencyFilter orFilter(DependencyFilter... var0) {
      return (DependencyFilter)(var0 != null && var0.length == 1 ? var0[0] : new OrDependencyFilter(var0));
   }

   public static DependencyFilter orFilter(Collection var0) {
      return (DependencyFilter)(var0 != null && var0.size() == 1 ? (DependencyFilter)var0.iterator().next() : new OrDependencyFilter(var0));
   }

   public static DependencyFilter classpathFilter(String... var0) {
      return classpathFilter((Collection)(var0 != null ? Arrays.asList(var0) : null));
   }

   public static DependencyFilter classpathFilter(Collection var0) {
      HashSet var1 = new HashSet();
      if (var0 != null) {
         Iterator var2 = var0.iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            String[] var4 = var3.split("[+,]");
            String[] var5 = var4;
            int var6 = var4.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String var8 = var5[var7];
               var8 = var8.trim();
               if (var8.length() > 0) {
                  var1.add(var8);
               }
            }
         }
      }

      HashSet var9 = new HashSet();
      Iterator var10 = var1.iterator();

      while(var10.hasNext()) {
         String var12 = (String)var10.next();
         if ("compile".equals(var12)) {
            Collections.addAll(var9, new String[]{"compile", "provided", "system"});
         } else if ("runtime".equals(var12)) {
            Collections.addAll(var9, new String[]{"compile", "runtime"});
         } else if ("test".equals(var12)) {
            Collections.addAll(var9, new String[]{"compile", "provided", "system", "runtime", "test"});
         } else {
            var9.add(var12);
         }
      }

      HashSet var11 = new HashSet();
      Collections.addAll(var11, new String[]{"compile", "provided", "system", "runtime", "test"});
      var11.removeAll(var9);
      return new ScopeDependencyFilter((Collection)null, var11);
   }
}
