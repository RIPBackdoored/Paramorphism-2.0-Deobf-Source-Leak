package org.eclipse.aether.util.graph.visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.graph.DependencyVisitor;

public final class PathRecordingDependencyVisitor implements DependencyVisitor {
   private final DependencyFilter filter;
   private final List paths;
   private final Stack parents;
   private final Map visited;
   private final boolean excludeChildrenOfMatches;

   public PathRecordingDependencyVisitor(DependencyFilter var1) {
      this(var1, true);
   }

   public PathRecordingDependencyVisitor(DependencyFilter var1, boolean var2) {
      super();
      this.filter = var1;
      this.excludeChildrenOfMatches = var2;
      this.paths = new ArrayList();
      this.parents = new Stack();
      this.visited = new IdentityHashMap(128);
   }

   public DependencyFilter getFilter() {
      return this.filter;
   }

   public List getPaths() {
      return this.paths;
   }

   public boolean visitEnter(DependencyNode var1) {
      boolean var2 = this.filter == null || this.filter.accept(var1, this.parents);
      this.parents.push(var1);
      if (var2) {
         DependencyNode[] var3 = new DependencyNode[this.parents.size()];
         int var4 = 0;

         for(int var5 = this.parents.size(); var4 < var5; ++var4) {
            var3[var5 - var4 - 1] = (DependencyNode)this.parents.get(var4);
         }

         this.paths.add(Arrays.asList(var3));
         if (this.excludeChildrenOfMatches) {
            return false;
         }
      }

      return this.visited.put(var1, Boolean.TRUE) == null;
   }

   public boolean visitLeave(DependencyNode var1) {
      this.parents.pop();
      this.visited.remove(var1);
      return true;
   }
}
