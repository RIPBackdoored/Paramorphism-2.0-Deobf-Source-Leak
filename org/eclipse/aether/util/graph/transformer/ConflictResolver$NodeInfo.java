package org.eclipse.aether.util.graph.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

final class ConflictResolver$NodeInfo {
   int minDepth;
   Object derivedScopes;
   int derivedOptionalities;
   List children;
   static final int CHANGE_SCOPE = 1;
   static final int CHANGE_OPTIONAL = 2;
   private static final int OPT_FALSE = 1;
   private static final int OPT_TRUE = 2;

   ConflictResolver$NodeInfo(int var1, String var2, boolean var3) {
      super();
      this.minDepth = var1;
      this.derivedScopes = var2;
      this.derivedOptionalities = var3 ? 2 : 1;
   }

   int update(int var1, String var2, boolean var3) {
      if (var1 < this.minDepth) {
         this.minDepth = var1;
      }

      int var4;
      if (this.derivedScopes.equals(var2)) {
         var4 = 0;
      } else if (this.derivedScopes instanceof Collection) {
         var4 = ((Collection)this.derivedScopes).add(var2) ? 1 : 0;
      } else {
         HashSet var5 = new HashSet();
         var5.add((String)this.derivedScopes);
         var5.add(var2);
         this.derivedScopes = var5;
         var4 = 1;
      }

      int var6 = var3 ? 2 : 1;
      if ((this.derivedOptionalities & var6) == 0) {
         this.derivedOptionalities |= var6;
         var4 |= 2;
      }

      return var4;
   }

   void add(ConflictResolver$ConflictItem var1) {
      if (this.children == null) {
         this.children = new ArrayList(1);
      }

      this.children.add(var1);
   }
}
