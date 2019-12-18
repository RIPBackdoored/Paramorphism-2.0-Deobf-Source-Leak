package org.eclipse.aether.util.graph.visitor;

import java.util.List;
import org.eclipse.aether.graph.DependencyNode;

public final class PreorderNodeListGenerator extends AbstractDepthFirstNodeListGenerator {
   public PreorderNodeListGenerator() {
      super();
   }

   public boolean visitEnter(DependencyNode var1) {
      if (!this.setVisited(var1)) {
         return false;
      } else {
         if (var1.getDependency() != null) {
            this.nodes.add(var1);
         }

         return true;
      }
   }

   public boolean visitLeave(DependencyNode var1) {
      return true;
   }

   public String getClassPath() {
      return super.getClassPath();
   }

   public List getFiles() {
      return super.getFiles();
   }

   public List getArtifacts(boolean var1) {
      return super.getArtifacts(var1);
   }

   public List getDependencies(boolean var1) {
      return super.getDependencies(var1);
   }

   public List getNodes() {
      return super.getNodes();
   }
}
