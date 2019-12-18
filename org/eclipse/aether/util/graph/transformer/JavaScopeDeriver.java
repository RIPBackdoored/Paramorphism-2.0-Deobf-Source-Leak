package org.eclipse.aether.util.graph.transformer;

import org.eclipse.aether.RepositoryException;

public final class JavaScopeDeriver extends ConflictResolver$ScopeDeriver {
   public JavaScopeDeriver() {
      super();
   }

   public void deriveScope(ConflictResolver$ScopeContext var1) throws RepositoryException {
      var1.setDerivedScope(this.getDerivedScope(var1.getParentScope(), var1.getChildScope()));
   }

   private String getDerivedScope(String var1, String var2) {
      String var3;
      if (!"system".equals(var2) && !"test".equals(var2)) {
         if (var1 != null && var1.length() > 0 && !"compile".equals(var1)) {
            if (!"test".equals(var1) && !"runtime".equals(var1)) {
               if (!"system".equals(var1) && !"provided".equals(var1)) {
                  var3 = "runtime";
               } else {
                  var3 = "provided";
               }
            } else {
               var3 = var1;
            }
         } else {
            var3 = var2;
         }
      } else {
         var3 = var2;
      }

      return var3;
   }
}
