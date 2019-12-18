package org.eclipse.aether.util.graph.visitor;

import java.util.Objects;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.graph.DependencyVisitor;

public final class FilteringDependencyVisitor implements DependencyVisitor {
   private final DependencyFilter filter;
   private final DependencyVisitor visitor;
   private final Stack accepts;
   private final Stack parents;

   public FilteringDependencyVisitor(DependencyVisitor var1, DependencyFilter var2) {
      super();
      this.visitor = (DependencyVisitor)Objects.requireNonNull(var1, "dependency visitor delegate cannot be null");
      this.filter = var2;
      this.accepts = new Stack();
      this.parents = new Stack();
   }

   public DependencyVisitor getVisitor() {
      return this.visitor;
   }

   public DependencyFilter getFilter() {
      return this.filter;
   }

   public boolean visitEnter(DependencyNode var1) {
      boolean var2 = this.filter == null || this.filter.accept(var1, this.parents);
      this.accepts.push(var2);
      this.parents.push(var1);
      return var2 ? this.visitor.visitEnter(var1) : true;
   }

   public boolean visitLeave(DependencyNode var1) {
      this.parents.pop();
      Boolean var2 = (Boolean)this.accepts.pop();
      return var2 ? this.visitor.visitLeave(var1) : true;
   }
}
