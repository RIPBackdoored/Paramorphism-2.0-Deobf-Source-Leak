package org.eclipse.aether.internal.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.aether.RepositoryCache;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.impl.RemoteRepositoryManager;
import org.eclipse.aether.impl.UpdatePolicyAnalyzer;
import org.eclipse.aether.repository.Authentication;
import org.eclipse.aether.repository.AuthenticationSelector;
import org.eclipse.aether.repository.MirrorSelector;
import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.ProxySelector;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.RemoteRepository$Builder;
import org.eclipse.aether.repository.RepositoryPolicy;
import org.eclipse.aether.spi.connector.checksum.ChecksumPolicyProvider;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class DefaultRemoteRepositoryManager implements RemoteRepositoryManager, Service {
   private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRemoteRepositoryManager.class);
   private UpdatePolicyAnalyzer updatePolicyAnalyzer;
   private ChecksumPolicyProvider checksumPolicyProvider;

   public DefaultRemoteRepositoryManager() {
      super();
   }

   @Inject
   DefaultRemoteRepositoryManager(UpdatePolicyAnalyzer var1, ChecksumPolicyProvider var2) {
      super();
      this.setUpdatePolicyAnalyzer(var1);
      this.setChecksumPolicyProvider(var2);
   }

   public void initService(ServiceLocator var1) {
      this.setUpdatePolicyAnalyzer((UpdatePolicyAnalyzer)var1.getService(UpdatePolicyAnalyzer.class));
      this.setChecksumPolicyProvider((ChecksumPolicyProvider)var1.getService(ChecksumPolicyProvider.class));
   }

   public DefaultRemoteRepositoryManager setUpdatePolicyAnalyzer(UpdatePolicyAnalyzer var1) {
      this.updatePolicyAnalyzer = (UpdatePolicyAnalyzer)Objects.requireNonNull(var1, "update policy analyzer cannot be null");
      return this;
   }

   public DefaultRemoteRepositoryManager setChecksumPolicyProvider(ChecksumPolicyProvider var1) {
      this.checksumPolicyProvider = (ChecksumPolicyProvider)Objects.requireNonNull(var1, "checksum policy provider cannot be null");
      return this;
   }

   public List aggregateRepositories(RepositorySystemSession var1, List var2, List var3, boolean var4) {
      if (var3.isEmpty()) {
         return var2;
      } else {
         MirrorSelector var5 = var1.getMirrorSelector();
         AuthenticationSelector var6 = var1.getAuthenticationSelector();
         ProxySelector var7 = var1.getProxySelector();
         ArrayList var8 = new ArrayList(var2);
         Iterator var9 = var3.iterator();

         while(true) {
            label56:
            while(var9.hasNext()) {
               RemoteRepository var10 = (RemoteRepository)var9.next();
               RemoteRepository var11 = var10;
               if (var4) {
                  RemoteRepository var12 = var5.getMirror(var10);
                  if (var12 != null) {
                     this.logMirror(var1, var10, var12);
                     var11 = var12;
                  }
               }

               String var16 = this.getKey(var11);
               ListIterator var13 = var8.listIterator();

               while(var13.hasNext()) {
                  RemoteRepository var14 = (RemoteRepository)var13.next();
                  if (var16.equals(this.getKey(var14))) {
                     if (!var14.getMirroredRepositories().isEmpty() && !var11.getMirroredRepositories().isEmpty()) {
                        RemoteRepository var15 = this.mergeMirrors(var1, var14, var11);
                        if (var15 != var14) {
                           var13.set(var15);
                        }
                     }
                     continue label56;
                  }
               }

               if (var4) {
                  RemoteRepository$Builder var17 = null;
                  Authentication var18 = var6.getAuthentication(var11);
                  if (var18 != null) {
                     var17 = new RemoteRepository$Builder(var11);
                     var17.setAuthentication(var18);
                  }

                  Proxy var19 = var7.getProxy(var11);
                  if (var19 != null) {
                     if (var17 == null) {
                        var17 = new RemoteRepository$Builder(var11);
                     }

                     var17.setProxy(var19);
                  }

                  if (var17 != null) {
                     var11 = var17.build();
                  }
               }

               var8.add(var11);
            }

            return var8;
         }
      }
   }

   private void logMirror(RepositorySystemSession var1, RemoteRepository var2, RemoteRepository var3) {
      if (LOGGER.isDebugEnabled()) {
         RepositoryCache var4 = var1.getCache();
         if (var4 != null) {
            DefaultRemoteRepositoryManager$LoggedMirror var5 = new DefaultRemoteRepositoryManager$LoggedMirror(var2, var3);
            if (var4.get(var1, var5) != null) {
               return;
            }

            var4.put(var1, var5, Boolean.TRUE);
         }

         LOGGER.debug("Using mirror {} ({}) for {} ({}).", var3.getId(), var3.getUrl(), var2.getId(), var2.getUrl());
      }
   }

   private String getKey(RemoteRepository var1) {
      return var1.getId();
   }

   private RemoteRepository mergeMirrors(RepositorySystemSession var1, RemoteRepository var2, RemoteRepository var3) {
      RemoteRepository$Builder var4 = null;
      RepositoryPolicy var5 = null;
      RepositoryPolicy var6 = null;
      Iterator var7 = var3.getMirroredRepositories().iterator();

      while(true) {
         label29:
         while(var7.hasNext()) {
            RemoteRepository var8 = (RemoteRepository)var7.next();
            String var9 = this.getKey(var8);
            Iterator var10 = var2.getMirroredRepositories().iterator();

            while(var10.hasNext()) {
               RemoteRepository var11 = (RemoteRepository)var10.next();
               if (var9.equals(this.getKey(var11))) {
                  continue label29;
               }
            }

            if (var4 == null) {
               var4 = new RemoteRepository$Builder(var2);
               var5 = var2.getPolicy(false);
               var6 = var2.getPolicy(true);
            }

            var5 = this.merge(var1, var5, var8.getPolicy(false), false);
            var6 = this.merge(var1, var6, var8.getPolicy(true), false);
            var4.addMirroredRepository(var8);
         }

         if (var4 == null) {
            return var2;
         }

         return var4.setReleasePolicy(var5).setSnapshotPolicy(var6).build();
      }
   }

   public RepositoryPolicy getPolicy(RepositorySystemSession var1, RemoteRepository var2, boolean var3, boolean var4) {
      RepositoryPolicy var5 = var3 ? var2.getPolicy(false) : null;
      RepositoryPolicy var6 = var4 ? var2.getPolicy(true) : null;
      return this.merge(var1, var5, var6, true);
   }

   private RepositoryPolicy merge(RepositorySystemSession var1, RepositoryPolicy var2, RepositoryPolicy var3, boolean var4) {
      RepositoryPolicy var5;
      if (var3 == null) {
         if (var4) {
            var5 = this.merge(var2, var1.getUpdatePolicy(), var1.getChecksumPolicy());
         } else {
            var5 = var2;
         }
      } else if (var2 == null) {
         if (var4) {
            var5 = this.merge(var3, var1.getUpdatePolicy(), var1.getChecksumPolicy());
         } else {
            var5 = var3;
         }
      } else if (!var3.isEnabled()) {
         if (var4) {
            var5 = this.merge(var2, var1.getUpdatePolicy(), var1.getChecksumPolicy());
         } else {
            var5 = var2;
         }
      } else if (!var2.isEnabled()) {
         if (var4) {
            var5 = this.merge(var3, var1.getUpdatePolicy(), var1.getChecksumPolicy());
         } else {
            var5 = var3;
         }
      } else {
         String var6 = var1.getChecksumPolicy();
         if (!var4 || StringUtils.isEmpty(var6)) {
            var6 = this.checksumPolicyProvider.getEffectiveChecksumPolicy(var1, var2.getChecksumPolicy(), var3.getChecksumPolicy());
         }

         String var7 = var1.getUpdatePolicy();
         if (!var4 || StringUtils.isEmpty(var7)) {
            var7 = this.updatePolicyAnalyzer.getEffectiveUpdatePolicy(var1, var2.getUpdatePolicy(), var3.getUpdatePolicy());
         }

         var5 = new RepositoryPolicy(true, var7, var6);
      }

      return var5;
   }

   private RepositoryPolicy merge(RepositoryPolicy var1, String var2, String var3) {
      if (var1 != null) {
         if (StringUtils.isEmpty(var2)) {
            var2 = var1.getUpdatePolicy();
         }

         if (StringUtils.isEmpty(var3)) {
            var3 = var1.getChecksumPolicy();
         }

         if (!var1.getUpdatePolicy().equals(var2) || !var1.getChecksumPolicy().equals(var3)) {
            var1 = new RepositoryPolicy(var1.isEnabled(), var2, var3);
         }
      }

      return var1;
   }
}
