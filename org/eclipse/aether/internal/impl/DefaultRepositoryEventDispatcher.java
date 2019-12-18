package org.eclipse.aether.internal.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.aether.RepositoryEvent;
import org.eclipse.aether.RepositoryListener;
import org.eclipse.aether.impl.RepositoryEventDispatcher;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class DefaultRepositoryEventDispatcher implements RepositoryEventDispatcher, Service {
   private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRepositoryEventDispatcher.class);
   private Collection listeners = new ArrayList();

   public DefaultRepositoryEventDispatcher() {
      super();
   }

   @Inject
   DefaultRepositoryEventDispatcher(Set var1) {
      super();
      this.setRepositoryListeners(var1);
   }

   public DefaultRepositoryEventDispatcher addRepositoryListener(RepositoryListener var1) {
      this.listeners.add(Objects.requireNonNull(var1, "repository listener cannot be null"));
      return this;
   }

   public DefaultRepositoryEventDispatcher setRepositoryListeners(Collection var1) {
      if (var1 == null) {
         this.listeners = new ArrayList();
      } else {
         this.listeners = var1;
      }

      return this;
   }

   public void initService(ServiceLocator var1) {
      this.setRepositoryListeners(var1.getServices(RepositoryListener.class));
   }

   public void dispatch(RepositoryEvent var1) {
      if (!this.listeners.isEmpty()) {
         Iterator var2 = this.listeners.iterator();

         while(var2.hasNext()) {
            RepositoryListener var3 = (RepositoryListener)var2.next();
            this.dispatch(var1, var3);
         }
      }

      RepositoryListener var4 = var1.getSession().getRepositoryListener();
      if (var4 != null) {
         this.dispatch(var1, var4);
      }

   }

   private void dispatch(RepositoryEvent var1, RepositoryListener var2) {
      // $FF: Couldn't be decompiled
   }

   private void logError(Throwable var1, Object var2) {
      String var3 = "Failed to dispatch repository event to " + var2.getClass().getCanonicalName() + ": " + var1.getMessage();
      if (LOGGER.isDebugEnabled()) {
         LOGGER.warn(var3, var1);
      } else {
         LOGGER.warn(var3);
      }

   }
}
