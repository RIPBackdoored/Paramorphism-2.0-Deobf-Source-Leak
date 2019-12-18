package org.eclipse.aether.util.graph.visitor;

import java.util.IdentityHashMap;
import java.util.Map;
import org.eclipse.aether.graph.DefaultDependencyNode;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.graph.DependencyVisitor;

public class CloningDependencyVisitor implements DependencyVisitor {
   private final Map clones = new IdentityHashMap(256);
   private final Stack parents = new Stack();
   private DependencyNode root;

   public CloningDependencyVisitor() {
      super();
   }

   public final DependencyNode getRootNode() {
      return this.root;
   }

   protected DependencyNode clone(DependencyNode var1) {
      return new DefaultDependencyNode(var1);
   }

   public final boolean visitEnter(DependencyNode var1) {
      boolean var2 = true;
      DependencyNode var3 = (DependencyNode)this.clones.get(var1);
      if (var3 == null) {
         var3 = this.clone(var1);
         this.clones.put(var1, var3);
      } else {
         var2 = false;
      }

      DependencyNode var4 = (DependencyNode)this.parents.peek();
      if (var4 == null) {
         this.root = var3;
      } else {
         var4.getChildren().add(var3);
      }

      this.parents.push(var3);
      return var2;
   }

   public final boolean visitLeave(DependencyNode var1) {
      this.parents.pop();
      return true;
   }
}
