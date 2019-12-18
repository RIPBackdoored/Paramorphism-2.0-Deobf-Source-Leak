package org.eclipse.aether.internal.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.impl.LocalRepositoryProvider;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.NoLocalRepositoryManagerException;
import org.eclipse.aether.spi.localrepo.LocalRepositoryManagerFactory;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class DefaultLocalRepositoryProvider implements LocalRepositoryProvider, Service {
   private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLocalRepositoryProvider.class);
   private Collection managerFactories = new ArrayList();

   public DefaultLocalRepositoryProvider() {
      super();
   }

   @Inject
   DefaultLocalRepositoryProvider(Set var1) {
      super();
      this.setLocalRepositoryManagerFactories(var1);
   }

   public void initService(ServiceLocator var1) {
      this.setLocalRepositoryManagerFactories(var1.getServices(LocalRepositoryManagerFactory.class));
   }

   public DefaultLocalRepositoryProvider addLocalRepositoryManagerFactory(LocalRepositoryManagerFactory var1) {
      this.managerFactories.add(Objects.requireNonNull(var1, "local repository manager factory cannot be null"));
      return this;
   }

   public DefaultLocalRepositoryProvider setLocalRepositoryManagerFactories(Collection var1) {
      if (var1 == null) {
         this.managerFactories = new ArrayList(2);
      } else {
         this.managerFactories = var1;
      }

      return this;
   }

   public LocalRepositoryManager newLocalRepositoryManager(RepositorySystemSession var1, LocalRepository var2) throws NoLocalRepositoryManagerException {
      PrioritizedComponents var3 = new PrioritizedComponents(var1);
      Iterator var4 = this.managerFactories.iterator();

      while(var4.hasNext()) {
         LocalRepositoryManagerFactory var5 = (LocalRepositoryManagerFactory)var4.next();
         var3.add(var5, var5.getPriority());
      }

      ArrayList var10 = new ArrayList();
      Iterator var11 = var3.getEnabled().iterator();

      while(true) {
         if (var11.hasNext()) {
            PrioritizedComponent var14 = (PrioritizedComponent)var11.next();

            LocalRepositoryManager var10000;
            try {
               LocalRepositoryManager var15 = ((LocalRepositoryManagerFactory)var14.getComponent()).newInstance(var1, var2);
               if (LOGGER.isDebugEnabled()) {
                  StringBuilder var8 = new StringBuilder(256);
                  var8.append("Using manager ").append(var15.getClass().getSimpleName());
                  Utils.appendClassLoader(var8, var15);
                  var8.append(" with priority ").append(var14.getPriority());
                  var8.append(" for ").append(var2.getBasedir());
                  LOGGER.debug(var8.toString());
               }

               var10000 = var15;
            } catch (NoLocalRepositoryManagerException var9) {
               var10.add(var9);
               continue;
            }

            return var10000;
         }

         if (LOGGER.isDebugEnabled() && var10.size() > 1) {
            String var12 = "Could not obtain local repository manager for " + var2;
            Iterator var6 = var10.iterator();

            while(var6.hasNext()) {
               Exception var7 = (Exception)var6.next();
               LOGGER.debug((String)var12, (Throwable)var7);
            }
         }

         StringBuilder var13 = new StringBuilder(256);
         if (var3.isEmpty()) {
            var13.append("No local repository managers registered");
         } else {
            var13.append("Cannot access ").append(var2.getBasedir());
            var13.append(" with type ").append(var2.getContentType());
            var13.append(" using the available factories ");
            var3.list(var13);
         }

         throw new NoLocalRepositoryManagerException(var2, var13.toString(), var10.size() == 1 ? (NoLocalRepositoryManagerException)var10.get(0) : null);
      }
   }
}
