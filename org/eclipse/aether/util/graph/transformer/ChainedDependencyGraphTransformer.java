package org.eclipse.aether.util.graph.transformer;

import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.collection.DependencyGraphTransformationContext;
import org.eclipse.aether.collection.DependencyGraphTransformer;
import org.eclipse.aether.graph.DependencyNode;

public final class ChainedDependencyGraphTransformer implements DependencyGraphTransformer {
   private final DependencyGraphTransformer[] transformers;

   public ChainedDependencyGraphTransformer(DependencyGraphTransformer... var1) {
      super();
      if (var1 == null) {
         this.transformers = new DependencyGraphTransformer[0];
      } else {
         this.transformers = var1;
      }

   }

   public static DependencyGraphTransformer newInstance(DependencyGraphTransformer var0, DependencyGraphTransformer var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (DependencyGraphTransformer)(var1 == null ? var0 : new ChainedDependencyGraphTransformer(new DependencyGraphTransformer[]{var0, var1}));
      }
   }

   public DependencyNode transformGraph(DependencyNode var1, DependencyGraphTransformationContext var2) throws RepositoryException {
      DependencyGraphTransformer[] var3 = this.transformers;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         DependencyGraphTransformer var6 = var3[var5];
         var1 = var6.transformGraph(var1, var2);
      }

      return var1;
   }
}
