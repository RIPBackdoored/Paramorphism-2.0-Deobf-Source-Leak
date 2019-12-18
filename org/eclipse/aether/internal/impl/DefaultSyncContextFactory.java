package org.eclipse.aether.internal.impl;

import javax.inject.Named;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.SyncContext;
import org.eclipse.aether.impl.SyncContextFactory;

@Named
public class DefaultSyncContextFactory implements SyncContextFactory {
   public DefaultSyncContextFactory() {
      super();
   }

   public SyncContext newInstance(RepositorySystemSession var1, boolean var2) {
      return new DefaultSyncContextFactory$DefaultSyncContext();
   }
}
