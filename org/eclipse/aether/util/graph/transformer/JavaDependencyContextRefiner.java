package org.eclipse.aether.util.graph.transformer;

import java.util.Iterator;
import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.collection.DependencyGraphTransformationContext;
import org.eclipse.aether.collection.DependencyGraphTransformer;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;

public final class JavaDependencyContextRefiner implements DependencyGraphTransformer {
   public JavaDependencyContextRefiner() {
      super();
   }

   public DependencyNode transformGraph(DependencyNode var1, DependencyGraphTransformationContext var2) throws RepositoryException {
      String var3 = var1.getRequestContext();
      if ("project".equals(var3)) {
         String var4 = this.getClasspathScope(var1);
         if (var4 != null) {
            var3 = var3 + '/' + var4;
            var1.setRequestContext(var3);
         }
      }

      Iterator var6 = var1.getChildren().iterator();

      while(var6.hasNext()) {
         DependencyNode var5 = (DependencyNode)var6.next();
         this.transformGraph(var5, var2);
      }

      return var1;
   }

   private String getClasspathScope(DependencyNode var1) {
      Dependency var2 = var1.getDependency();
      if (var2 == null) {
         return null;
      } else {
         String var3 = var2.getScope();
         if (!"compile".equals(var3) && !"system".equals(var3) && !"provided".equals(var3)) {
            if ("runtime".equals(var3)) {
               return "runtime";
            } else {
               return "test".equals(var3) ? "test" : null;
            }
         } else {
            return "compile";
         }
      }
   }
}
