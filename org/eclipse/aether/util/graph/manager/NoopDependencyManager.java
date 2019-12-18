package org.eclipse.aether.util.graph.manager;

import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencyManagement;
import org.eclipse.aether.collection.DependencyManager;
import org.eclipse.aether.graph.Dependency;

public final class NoopDependencyManager implements DependencyManager {
   public static final DependencyManager INSTANCE = new NoopDependencyManager();

   public NoopDependencyManager() {
      super();
   }

   public DependencyManager deriveChildManager(DependencyCollectionContext var1) {
      return this;
   }

   public DependencyManagement manageDependency(Dependency var1) {
      return null;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return null != var1 && this.getClass().equals(var1.getClass());
      }
   }

   public int hashCode() {
      return this.getClass().hashCode();
   }
}
