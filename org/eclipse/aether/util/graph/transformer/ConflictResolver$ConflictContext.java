package org.eclipse.aether.util.graph.transformer;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.eclipse.aether.graph.DependencyNode;

public final class ConflictResolver$ConflictContext {
   final DependencyNode root;
   final Map conflictIds;
   final Collection items;
   Object conflictId;
   ConflictResolver$ConflictItem winner;
   String scope;
   Boolean optional;

   ConflictResolver$ConflictContext(DependencyNode var1, Map var2, Collection var3) {
      super();
      this.root = var1;
      this.conflictIds = var2;
      this.items = Collections.unmodifiableCollection(var3);
   }

   public ConflictResolver$ConflictContext(DependencyNode var1, Object var2, Map var3, Collection var4) {
      this(var1, var3, var4);
      this.conflictId = var2;
   }

   public DependencyNode getRoot() {
      return this.root;
   }

   public boolean isIncluded(DependencyNode var1) {
      return this.conflictId.equals(this.conflictIds.get(var1));
   }

   public Collection getItems() {
      return this.items;
   }

   public ConflictResolver$ConflictItem getWinner() {
      return this.winner;
   }

   public void setWinner(ConflictResolver$ConflictItem var1) {
      this.winner = var1;
   }

   public String getScope() {
      return this.scope;
   }

   public void setScope(String var1) {
      this.scope = var1;
   }

   public Boolean getOptional() {
      return this.optional;
   }

   public void setOptional(Boolean var1) {
      this.optional = var1;
   }

   public String toString() {
      return this.winner + " @ " + this.scope + " < " + this.items;
   }
}
