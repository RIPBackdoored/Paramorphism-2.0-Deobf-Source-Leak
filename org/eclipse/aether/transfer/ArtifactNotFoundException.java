package org.eclipse.aether.transfer;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.RemoteRepository;

public class ArtifactNotFoundException extends ArtifactTransferException {
   public ArtifactNotFoundException(Artifact var1, RemoteRepository var2) {
      super(var1, var2, getMessage(var1, var2));
   }

   private static String getMessage(Artifact var0, RemoteRepository var1) {
      StringBuilder var2 = new StringBuilder(256);
      var2.append("Could not find artifact ").append(var0);
      var2.append(getString(" in ", var1));
      if (var0 != null) {
         String var3 = var0.getProperty("localPath", (String)null);
         if (var3 != null && var1 == null) {
            var2.append(" at specified path ").append(var3);
         }

         String var4 = var0.getProperty("downloadUrl", (String)null);
         if (var4 != null) {
            var2.append(", try downloading from ").append(var4);
         }
      }

      return var2.toString();
   }

   public ArtifactNotFoundException(Artifact var1, RemoteRepository var2, String var3) {
      super(var1, var2, var3);
   }

   public ArtifactNotFoundException(Artifact var1, RemoteRepository var2, String var3, boolean var4) {
      super(var1, var2, var3, var4);
   }

   public ArtifactNotFoundException(Artifact var1, RemoteRepository var2, String var3, Throwable var4) {
      super(var1, var2, var3, var4);
   }
}
