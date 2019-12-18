package org.eclipse.aether.util.graph.transformer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.aether.RepositoryException;

public final class JavaScopeSelector extends ConflictResolver$ScopeSelector {
   public JavaScopeSelector() {
      super();
   }

   public void selectScope(ConflictResolver$ConflictContext var1) throws RepositoryException {
      String var2 = var1.getWinner().getDependency().getScope();
      if (!"system".equals(var2)) {
         var2 = this.chooseEffectiveScope(var1.getItems());
      }

      var1.setScope(var2);
   }

   private String chooseEffectiveScope(Collection var1) {
      HashSet var2 = new HashSet();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         ConflictResolver$ConflictItem var4 = (ConflictResolver$ConflictItem)var3.next();
         if (var4.getDepth() <= 1) {
            return var4.getDependency().getScope();
         }

         var2.addAll(var4.getScopes());
      }

      return this.chooseEffectiveScope((Set)var2);
   }

   private String chooseEffectiveScope(Set var1) {
      if (var1.size() > 1) {
         var1.remove("system");
      }

      String var2 = "";
      if (var1.size() == 1) {
         var2 = (String)var1.iterator().next();
      } else if (var1.contains("compile")) {
         var2 = "compile";
      } else if (var1.contains("runtime")) {
         var2 = "runtime";
      } else if (var1.contains("provided")) {
         var2 = "provided";
      } else if (var1.contains("test")) {
         var2 = "test";
      }

      return var2;
   }
}
