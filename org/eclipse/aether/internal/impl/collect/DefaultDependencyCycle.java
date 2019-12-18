package org.eclipse.aether.internal.impl.collect;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyCycle;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.util.artifact.ArtifactIdUtils;

final class DefaultDependencyCycle implements DependencyCycle {
   private final List dependencies;
   private final int cycleEntry;

   DefaultDependencyCycle(NodeStack var1, int var2, Dependency var3) {
      super();
      int var4 = var2 > 0 && var1.get(0).getDependency() == null ? 1 : 0;
      Dependency[] var5 = new Dependency[var1.size() - var4 + 1];
      int var6 = 0;

      for(int var7 = var5.length - 1; var6 < var7; ++var6) {
         DependencyNode var8 = var1.get(var6 + var4);
         var5[var6] = var8.getDependency();
         if (var5[var6] == null) {
            var5[var6] = new Dependency(var8.getArtifact(), (String)null);
         }
      }

      var5[var5.length - 1] = var3;
      this.dependencies = Collections.unmodifiableList(Arrays.asList(var5));
      this.cycleEntry = var2;
   }

   public List getPrecedingDependencies() {
      return this.dependencies.subList(0, this.cycleEntry);
   }

   public List getCyclicDependencies() {
      return this.dependencies.subList(this.cycleEntry, this.dependencies.size());
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(256);
      int var2 = 0;

      for(int var3 = this.dependencies.size(); var2 < var3; ++var2) {
         if (var2 > 0) {
            var1.append(" -> ");
         }

         var1.append(ArtifactIdUtils.toVersionlessId(((Dependency)this.dependencies.get(var2)).getArtifact()));
      }

      return var1.toString();
   }
}
