package org.eclipse.aether.internal.impl;

import javax.inject.Named;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.NoLocalRepositoryManagerException;
import org.eclipse.aether.spi.localrepo.LocalRepositoryManagerFactory;

@Named("simple")
public class SimpleLocalRepositoryManagerFactory implements LocalRepositoryManagerFactory {
   private float priority;

   public SimpleLocalRepositoryManagerFactory() {
      super();
   }

   public LocalRepositoryManager newInstance(RepositorySystemSession var1, LocalRepository var2) throws NoLocalRepositoryManagerException {
      if (!"".equals(var2.getContentType()) && !"simple".equals(var2.getContentType())) {
         throw new NoLocalRepositoryManagerException(var2);
      } else {
         return new SimpleLocalRepositoryManager(var2.getBasedir());
      }
   }

   public float getPriority() {
      return this.priority;
   }

   public SimpleLocalRepositoryManagerFactory setPriority(float var1) {
      this.priority = var1;
      return this;
   }
}
