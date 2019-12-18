package org.eclipse.aether.internal.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.util.ConfigUtils;

final class PrioritizedComponents {
   private static final String FACTORY_SUFFIX = "Factory";
   private final Map configProps;
   private final boolean useInsertionOrder;
   private final List components;
   private int firstDisabled;

   PrioritizedComponents(RepositorySystemSession var1) {
      this(var1.getConfigProperties());
   }

   PrioritizedComponents(Map var1) {
      super();
      this.configProps = var1;
      this.useInsertionOrder = ConfigUtils.getBoolean(this.configProps, false, "aether.priority.implicit");
      this.components = new ArrayList();
      this.firstDisabled = 0;
   }

   public void add(Object var1, float var2) {
      Class var3 = getImplClass(var1);
      int var4 = this.components.size();
      var2 = this.useInsertionOrder ? (float)(-var4) : ConfigUtils.getFloat(this.configProps, var2, getConfigKeys(var3));
      PrioritizedComponent var5 = new PrioritizedComponent(var1, var3, var2, var4);
      if (!this.useInsertionOrder) {
         var4 = Collections.binarySearch(this.components, var5);
         if (var4 < 0) {
            var4 = -var4 - 1;
         } else {
            ++var4;
         }
      }

      this.components.add(var4, var5);
      if (var4 <= this.firstDisabled && !var5.isDisabled()) {
         ++this.firstDisabled;
      }

   }

   private static Class getImplClass(Object var0) {
      Class var1 = var0.getClass();
      int var2 = var1.getName().indexOf("$$");
      if (var2 >= 0) {
         Class var3 = var1.getSuperclass();
         if (var3 != null && var2 == var3.getName().length() && var1.getName().startsWith(var3.getName())) {
            var1 = var3;
         }
      }

      return var1;
   }

   static String[] getConfigKeys(Class var0) {
      ArrayList var1 = new ArrayList();
      var1.add("aether.priority." + var0.getName());
      String var2 = var0.getSimpleName();
      var1.add("aether.priority." + var2);
      if (var2.endsWith("Factory")) {
         var1.add("aether.priority." + var2.substring(0, var2.length() - "Factory".length()));
      }

      return (String[])var1.toArray(new String[var1.size()]);
   }

   public boolean isEmpty() {
      return this.components.isEmpty();
   }

   public List getAll() {
      return this.components;
   }

   public List getEnabled() {
      return this.components.subList(0, this.firstDisabled);
   }

   public void list(StringBuilder var1) {
      for(int var2 = 0; var2 < this.components.size(); ++var2) {
         if (var2 > 0) {
            var1.append(", ");
         }

         PrioritizedComponent var3 = (PrioritizedComponent)this.components.get(var2);
         var1.append(var3.getType().getSimpleName());
         if (var3.isDisabled()) {
            var1.append(" (disabled)");
         }
      }

   }

   public String toString() {
      return this.components.toString();
   }
}
