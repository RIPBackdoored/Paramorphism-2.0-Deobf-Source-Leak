package org.eclipse.aether.internal.impl.collect;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.util.ConfigUtils;

class DefaultDependencyCollector$Results {
   private final CollectResult result;
   final int maxExceptions;
   final int maxCycles;
   String errorPath;

   DefaultDependencyCollector$Results(CollectResult var1, RepositorySystemSession var2) {
      super();
      this.result = var1;
      this.maxExceptions = ConfigUtils.getInteger((RepositorySystemSession)var2, 50, "aether.dependencyCollector.maxExceptions");
      this.maxCycles = ConfigUtils.getInteger((RepositorySystemSession)var2, 10, "aether.dependencyCollector.maxCycles");
   }

   public void addException(Dependency var1, Exception var2, NodeStack var3) {
      if (this.maxExceptions < 0 || this.result.getExceptions().size() < this.maxExceptions) {
         this.result.addException(var2);
         if (this.errorPath == null) {
            StringBuilder var4 = new StringBuilder(256);

            for(int var5 = 0; var5 < var3.size(); ++var5) {
               if (var4.length() > 0) {
                  var4.append(" -> ");
               }

               Dependency var6 = var3.get(var5).getDependency();
               if (var6 != null) {
                  var4.append(var6.getArtifact());
               }
            }

            if (var4.length() > 0) {
               var4.append(" -> ");
            }

            var4.append(var1.getArtifact());
            this.errorPath = var4.toString();
         }
      }

   }

   public void addCycle(NodeStack var1, int var2, Dependency var3) {
      if (this.maxCycles < 0 || this.result.getCycles().size() < this.maxCycles) {
         this.result.addCycle(new DefaultDependencyCycle(var1, var2, var3));
      }

   }
}
