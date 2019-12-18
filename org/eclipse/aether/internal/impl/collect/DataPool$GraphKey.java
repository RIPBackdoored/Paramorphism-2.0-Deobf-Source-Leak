package org.eclipse.aether.internal.impl.collect;

import java.util.List;
import java.util.Objects;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.collection.DependencyManager;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.collection.DependencyTraverser;
import org.eclipse.aether.collection.VersionFilter;

final class DataPool$GraphKey {
   private final Artifact artifact;
   private final List repositories;
   private final DependencySelector selector;
   private final DependencyManager manager;
   private final DependencyTraverser traverser;
   private final VersionFilter filter;
   private final int hashCode;

   DataPool$GraphKey(Artifact var1, List var2, DependencySelector var3, DependencyManager var4, DependencyTraverser var5, VersionFilter var6) {
      super();
      this.artifact = var1;
      this.repositories = var2;
      this.selector = var3;
      this.manager = var4;
      this.traverser = var5;
      this.filter = var6;
      this.hashCode = Objects.hash(new Object[]{var1, var2, var3, var4, var5, var6});
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof DataPool$GraphKey)) {
         return false;
      } else {
         DataPool$GraphKey var2 = (DataPool$GraphKey)var1;
         return Objects.equals(this.artifact, var2.artifact) && Objects.equals(this.repositories, var2.repositories) && Objects.equals(this.selector, var2.selector) && Objects.equals(this.manager, var2.manager) && Objects.equals(this.traverser, var2.traverser) && Objects.equals(this.filter, var2.filter);
      }
   }

   public int hashCode() {
      return this.hashCode;
   }
}
