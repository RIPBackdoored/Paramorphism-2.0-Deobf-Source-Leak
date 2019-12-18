package org.eclipse.aether.internal.impl;

import javax.inject.Named;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.spi.connector.layout.RepositoryLayout;
import org.eclipse.aether.spi.connector.layout.RepositoryLayoutFactory;
import org.eclipse.aether.transfer.NoRepositoryLayoutException;
import org.eclipse.aether.util.ConfigUtils;

@Named("maven2")
public final class Maven2RepositoryLayoutFactory implements RepositoryLayoutFactory {
   static final String CONFIG_PROP_SIGNATURE_CHECKSUMS = "aether.checksums.forSignature";
   private float priority;

   public Maven2RepositoryLayoutFactory() {
      super();
   }

   public float getPriority() {
      return this.priority;
   }

   public Maven2RepositoryLayoutFactory setPriority(float var1) {
      this.priority = var1;
      return this;
   }

   public RepositoryLayout newInstance(RepositorySystemSession var1, RemoteRepository var2) throws NoRepositoryLayoutException {
      if (!"default".equals(var2.getContentType())) {
         throw new NoRepositoryLayoutException(var2);
      } else {
         boolean var3 = ConfigUtils.getBoolean(var1, false, "aether.checksums.forSignature");
         return var3 ? Maven2RepositoryLayoutFactory$Maven2RepositoryLayout.INSTANCE : Maven2RepositoryLayoutFactory$Maven2RepositoryLayoutEx.INSTANCE;
      }
   }
}
