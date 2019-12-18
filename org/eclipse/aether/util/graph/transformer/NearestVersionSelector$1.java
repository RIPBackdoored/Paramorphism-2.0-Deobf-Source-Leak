package org.eclipse.aether.util.graph.transformer;

import java.util.List;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;

class NearestVersionSelector$1 implements DependencyFilter {
   final ConflictResolver$ConflictContext val$context;
   final NearestVersionSelector this$0;

   NearestVersionSelector$1(NearestVersionSelector var1, ConflictResolver$ConflictContext var2) {
      super();
      this.this$0 = var1;
      this.val$context = var2;
   }

   public boolean accept(DependencyNode var1, List var2) {
      return this.val$context.isIncluded(var1);
   }
}
