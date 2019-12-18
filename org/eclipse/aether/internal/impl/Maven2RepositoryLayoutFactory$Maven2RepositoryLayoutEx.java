package org.eclipse.aether.internal.impl;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.spi.connector.layout.RepositoryLayout;

class Maven2RepositoryLayoutFactory$Maven2RepositoryLayoutEx extends Maven2RepositoryLayoutFactory$Maven2RepositoryLayout {
   public static final RepositoryLayout INSTANCE = new Maven2RepositoryLayoutFactory$Maven2RepositoryLayoutEx();

   private Maven2RepositoryLayoutFactory$Maven2RepositoryLayoutEx() {
      super((Maven2RepositoryLayoutFactory$1)null);
   }

   public List getChecksums(Artifact var1, boolean var2, URI var3) {
      return this.isSignature(var1.getExtension()) ? Collections.emptyList() : super.getChecksums(var1, var2, var3);
   }

   private boolean isSignature(String var1) {
      return var1.endsWith(".asc");
   }
}
