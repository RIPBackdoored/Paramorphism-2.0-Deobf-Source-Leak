package org.eclipse.aether.util.graph.visitor;

import java.util.List;
import org.eclipse.aether.graph.DependencyNode;

public final class PostorderNodeListGenerator extends AbstractDepthFirstNodeListGenerator {
   private final Stack visits = new Stack();

   public PostorderNodeListGenerator() {
      super();
   }

   public boolean visitEnter(DependencyNode var1) {
      boolean var2 = !this.setVisited(var1);
      this.visits.push(var2);
      return !var2;
   }

   public boolean visitLeave(DependencyNode var1) {
      Boolean var2 = (Boolean)this.visits.pop();
      if (var2) {
         return true;
      } else {
         if (var1.getDependency() != null) {
            this.nodes.add(var1);
         }

         return true;
      }
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
