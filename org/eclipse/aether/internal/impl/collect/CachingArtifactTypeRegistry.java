package org.eclipse.aether.internal.impl.collect;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.ArtifactType;
import org.eclipse.aether.artifact.ArtifactTypeRegistry;

class CachingArtifactTypeRegistry implements ArtifactTypeRegistry {
   private final ArtifactTypeRegistry delegate;
   private final Map types;

   public static ArtifactTypeRegistry newInstance(RepositorySystemSession var0) {
      return newInstance(var0.getArtifactTypeRegistry());
   }

   public static ArtifactTypeRegistry newInstance(ArtifactTypeRegistry var0) {
      return var0 != null ? new CachingArtifactTypeRegistry(var0) : null;
   }

   private CachingArtifactTypeRegistry(ArtifactTypeRegistry var1) {
      super();
      this.delegate = var1;
      this.types = new HashMap();
   }

   public ArtifactType get(String var1) {
      ArtifactType var2 = (ArtifactType)this.types.get(var1);
      if (var2 == null) {
         var2 = this.delegate.get(var1);
         this.types.put(var1, var2);
      }

      return var2;
   }
}
