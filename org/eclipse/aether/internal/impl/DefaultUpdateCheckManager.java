package org.eclipse.aether.internal.impl;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.SessionData;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.impl.UpdateCheck;
import org.eclipse.aether.impl.UpdateCheckManager;
import org.eclipse.aether.impl.UpdatePolicyAnalyzer;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.AuthenticationDigest;
import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.transfer.ArtifactNotFoundException;
import org.eclipse.aether.transfer.ArtifactTransferException;
import org.eclipse.aether.transfer.MetadataNotFoundException;
import org.eclipse.aether.transfer.MetadataTransferException;
import org.eclipse.aether.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class DefaultUpdateCheckManager implements UpdateCheckManager, Service {
   private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUpdatePolicyAnalyzer.class);
   private UpdatePolicyAnalyzer updatePolicyAnalyzer;
   private static final String UPDATED_KEY_SUFFIX = ".lastUpdated";
   private static final String ERROR_KEY_SUFFIX = ".error";
   private static final String NOT_FOUND = "";
   private static final String SESSION_CHECKS = "updateCheckManager.checks";
   static final String CONFIG_PROP_SESSION_STATE = "aether.updateCheckManager.sessionState";
   private static final int STATE_ENABLED = 0;
   private static final int STATE_BYPASS = 1;
   private static final int STATE_DISABLED = 2;

   public DefaultUpdateCheckManager() {
      super();
   }

   @Inject
   DefaultUpdateCheckManager(UpdatePolicyAnalyzer var1) {
      super();
      this.setUpdatePolicyAnalyzer(var1);
   }

   public void initService(ServiceLocator var1) {
      this.setUpdatePolicyAnalyzer((UpdatePolicyAnalyzer)var1.getService(UpdatePolicyAnalyzer.class));
   }

   public DefaultUpdateCheckManager setUpdatePolicyAnalyzer(UpdatePolicyAnalyzer var1) {
      this.updatePolicyAnalyzer = (UpdatePolicyAnalyzer)Objects.requireNonNull(var1, "update policy analyzer cannot be null");
      return this;
   }

   public void checkArtifact(RepositorySystemSession var1, UpdateCheck var2) {
      if (var2.getLocalLastUpdated() != 0L && !this.isUpdatedRequired(var1, var2.getLocalLastUpdated(), var2.getPolicy())) {
         LOGGER.debug("Skipped remote request for {}, locally installed artifact up-to-date.", var2.getItem());
         var2.setRequired(false);
      } else {
         Artifact var3 = (Artifact)var2.getItem();
         RemoteRepository var4 = var2.getRepository();
         File var5 = (File)Objects.requireNonNull(var2.getFile(), String.format("The artifact '%s' has no file attached", var3));
         boolean var6 = var2.isFileValid() && var5.exists();
         File var7 = this.getTouchFile(var3, var5);
         Properties var8 = this.read(var7);
         String var9 = this.getUpdateKey(var1, var5, var4);
         String var10 = this.getDataKey(var3, var5, var4);
         String var11 = this.getError(var8, var10);
         long var12;
         if (var11 == null) {
            if (var6) {
               var12 = var5.lastModified();
            } else {
               var12 = 0L;
            }
         } else if (var11.length() <= 0) {
            var12 = this.getLastUpdated(var8, var10);
         } else {
            String var14 = this.getTransferKey(var1, var3, var5, var4);
            var12 = this.getLastUpdated(var8, var14);
         }

         if (var12 == 0L) {
            var2.setRequired(true);
         } else if (this.isAlreadyUpdated(var1, var9)) {
            if (LOGGER.isDebugEnabled()) {
               LOGGER.debug("Skipped remote request for " + var2.getItem() + ", already updated during this session.");
            }

            var2.setRequired(false);
            if (var11 != null) {
               var2.setException(this.newException(var11, var3, var4));
            }
         } else if (this.isUpdatedRequired(var1, var12, var2.getPolicy())) {
            var2.setRequired(true);
         } else if (var6) {
            LOGGER.debug("Skipped remote request for {}, locally cached artifact up-to-date.", var2.getItem());
            var2.setRequired(false);
         } else {
            int var16 = Utils.getPolicy(var1, var3, var4);
            int var15 = getCacheFlag(var11);
            if ((var16 & var15) != 0) {
               var2.setRequired(false);
               var2.setException(this.newException(var11, var3, var4));
            } else {
               var2.setRequired(true);
            }
         }

      }
   }

   private static int getCacheFlag(String var0) {
      return var0 != null && var0.length() > 0 ? 2 : 1;
   }

   private ArtifactTransferException newException(String var1, Artifact var2, RemoteRepository var3) {
      return (ArtifactTransferException)(var1 != null && var1.length() > 0 ? new ArtifactTransferException(var2, var3, "Failure to transfer " + var2 + " from " + var3.getUrl() + " was cached in the local repository, " + "resolution will not be reattempted until the update interval of " + var3.getId() + " has elapsed or updates are forced. Original error: " + var1, true) : new ArtifactNotFoundException(var2, var3, "Failure to find " + var2 + " in " + var3.getUrl() + " was cached in the local repository, " + "resolution will not be reattempted until the update interval of " + var3.getId() + " has elapsed or updates are forced", true));
   }

   public void checkMetadata(RepositorySystemSession var1, UpdateCheck var2) {
      if (var2.getLocalLastUpdated() != 0L && !this.isUpdatedRequired(var1, var2.getLocalLastUpdated(), var2.getPolicy())) {
         LOGGER.debug("Skipped remote request for {} locally installed metadata up-to-date.", var2.getItem());
         var2.setRequired(false);
      } else {
         Metadata var3 = (Metadata)var2.getItem();
         RemoteRepository var4 = var2.getRepository();
         File var5 = (File)Objects.requireNonNull(var2.getFile(), String.format("The metadata '%s' has no file attached", var3));
         boolean var6 = var2.isFileValid() && var5.exists();
         File var7 = this.getTouchFile(var3, var5);
         Properties var8 = this.read(var7);
         String var9 = this.getUpdateKey(var1, var5, var4);
         String var10 = this.getDataKey(var3, var5, var2.getAuthoritativeRepository());
         String var11 = this.getError(var8, var10);
         long var12;
         if (var11 == null) {
            if (var6) {
               var12 = this.getLastUpdated(var8, var10);
            } else {
               var12 = 0L;
            }
         } else if (var11.length() <= 0) {
            var12 = this.getLastUpdated(var8, var10);
         } else {
            String var14 = this.getTransferKey(var1, var3, var5, var4);
            var12 = this.getLastUpdated(var8, var14);
         }

         if (var12 == 0L) {
            var2.setRequired(true);
         } else if (this.isAlreadyUpdated(var1, var9)) {
            LOGGER.debug("Skipped remote request for {}, already updated during this session.", var2.getItem());
            var2.setRequired(false);
            if (var11 != null) {
               var2.setException(this.newException(var11, var3, var4));
            }
         } else if (this.isUpdatedRequired(var1, var12, var2.getPolicy())) {
            var2.setRequired(true);
         } else if (var6) {
            LOGGER.debug("Skipped remote request for {}, locally cached metadata up-to-date.", var2.getItem());
            var2.setRequired(false);
         } else {
            int var16 = Utils.getPolicy(var1, var3, var4);
            int var15 = getCacheFlag(var11);
            if ((var16 & var15) != 0) {
               var2.setRequired(false);
               var2.setException(this.newException(var11, var3, var4));
            } else {
               var2.setRequired(true);
            }
         }

      }
   }

   private MetadataTransferException newException(String var1, Metadata var2, RemoteRepository var3) {
      return (MetadataTransferException)(var1 != null && var1.length() > 0 ? new MetadataTransferException(var2, var3, "Failure to transfer " + var2 + " from " + var3.getUrl() + " was cached in the local repository, " + "resolution will not be reattempted until the update interval of " + var3.getId() + " has elapsed or updates are forced. Original error: " + var1, true) : new MetadataNotFoundException(var2, var3, "Failure to find " + var2 + " in " + var3.getUrl() + " was cached in the local repository, " + "resolution will not be reattempted until the update interval of " + var3.getId() + " has elapsed or updates are forced", true));
   }

   private long getLastUpdated(Properties var1, String var2) {
      String var3 = var1.getProperty(var2 + ".lastUpdated", "");

      long var10000;
      try {
         var10000 = var3.length() > 0 ? Long.parseLong(var3) : 1L;
      } catch (NumberFormatException var5) {
         LOGGER.debug((String)"Cannot parse lastUpdated date: '{}'. Ignoring.", (Object)var3, (Object)var5);
         return 1L;
      }

      return var10000;
   }

   private String getError(Properties var1, String var2) {
      return var1.getProperty(var2 + ".error");
   }

   private File getTouchFile(Artifact var1, File var2) {
      return new File(var2.getPath() + ".lastUpdated");
   }

   private File getTouchFile(Metadata var1, File var2) {
      return new File(var2.getParent(), "resolver-status.properties");
   }

   private String getDataKey(Artifact var1, File var2, RemoteRepository var3) {
      Object var4 = Collections.emptySet();
      if (var3.isRepositoryManager()) {
         var4 = new TreeSet();
         Iterator var5 = var3.getMirroredRepositories().iterator();

         while(var5.hasNext()) {
            RemoteRepository var6 = (RemoteRepository)var5.next();
            ((Set)var4).add(this.normalizeRepoUrl(var6.getUrl()));
         }
      }

      StringBuilder var8 = new StringBuilder(1024);
      var8.append(this.normalizeRepoUrl(var3.getUrl()));
      Iterator var9 = ((Set)var4).iterator();

      while(var9.hasNext()) {
         String var7 = (String)var9.next();
         var8.append('+').append(var7);
      }

      return var8.toString();
   }

   private String getTransferKey(RepositorySystemSession var1, Artifact var2, File var3, RemoteRepository var4) {
      return this.getRepoKey(var1, var4);
   }

   private String getDataKey(Metadata var1, File var2, RemoteRepository var3) {
      return var2.getName();
   }

   private String getTransferKey(RepositorySystemSession var1, Metadata var2, File var3, RemoteRepository var4) {
      return var3.getName() + '/' + this.getRepoKey(var1, var4);
   }

   private String getRepoKey(RepositorySystemSession var1, RemoteRepository var2) {
      StringBuilder var3 = new StringBuilder(128);
      Proxy var4 = var2.getProxy();
      if (var4 != null) {
         var3.append(AuthenticationDigest.forProxy(var1, var2)).append('@');
         var3.append(var4.getHost()).append(':').append(var4.getPort()).append('>');
      }

      var3.append(AuthenticationDigest.forRepository(var1, var2)).append('@');
      var3.append(var2.getContentType()).append('-');
      var3.append(var2.getId()).append('-');
      var3.append(this.normalizeRepoUrl(var2.getUrl()));
      return var3.toString();
   }

   private String normalizeRepoUrl(String var1) {
      String var2 = var1;
      if (var1 != null && var1.length() > 0 && !var1.endsWith("/")) {
         var2 = var1 + '/';
      }

      return var2;
   }

   private String getUpdateKey(RepositorySystemSession var1, File var2, RemoteRepository var3) {
      return var2.getAbsolutePath() + '|' + this.getRepoKey(var1, var3);
   }

   private int getSessionState(RepositorySystemSession var1) {
      String var2 = ConfigUtils.getString(var1, "true", "aether.updateCheckManager.sessionState");
      if (Boolean.parseBoolean(var2)) {
         return 0;
      } else {
         return "bypass".equalsIgnoreCase(var2) ? 1 : 2;
      }
   }

   private boolean isAlreadyUpdated(RepositorySystemSession var1, Object var2) {
      if (this.getSessionState(var1) >= 1) {
         return false;
      } else {
         SessionData var3 = var1.getData();
         Object var4 = var3.get("updateCheckManager.checks");
         return !(var4 instanceof Map) ? false : ((Map)var4).containsKey(var2);
      }
   }

   private void setUpdated(RepositorySystemSession var1, Object var2) {
      if (this.getSessionState(var1) < 2) {
         SessionData var3 = var1.getData();

         Object var4;
         for(var4 = var3.get("updateCheckManager.checks"); !(var4 instanceof Map); var4 = var3.get("updateCheckManager.checks")) {
            Object var5 = var4;
            var4 = new ConcurrentHashMap(256);
            if (var3.set("updateCheckManager.checks", var5, var4)) {
               break;
            }
         }

         ((Map)var4).put(var2, Boolean.TRUE);
      }
   }

   private boolean isUpdatedRequired(RepositorySystemSession var1, long var2, String var4) {
      return this.updatePolicyAnalyzer.isUpdatedRequired(var1, var2, var4);
   }

   private Properties read(File var1) {
      Properties var2 = (new TrackingFileManager()).read(var1);
      return var2 != null ? var2 : new Properties();
   }

   public void touchArtifact(RepositorySystemSession var1, UpdateCheck var2) {
      Artifact var3 = (Artifact)var2.getItem();
      File var4 = var2.getFile();
      File var5 = this.getTouchFile(var3, var4);
      String var6 = this.getUpdateKey(var1, var4, var2.getRepository());
      String var7 = this.getDataKey(var3, var4, var2.getAuthoritativeRepository());
      String var8 = this.getTransferKey(var1, var3, var4, var2.getRepository());
      this.setUpdated(var1, var6);
      Properties var9 = this.write(var5, var7, var8, var2.getException());
      if (var4.exists() && !this.hasErrors(var9)) {
         var5.delete();
      }

   }

   private boolean hasErrors(Properties var1) {
      Iterator var2 = var1.keySet().iterator();

      Object var3;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         var3 = var2.next();
      } while(!var3.toString().endsWith(".error"));

      return true;
   }

   public void touchMetadata(RepositorySystemSession var1, UpdateCheck var2) {
      Metadata var3 = (Metadata)var2.getItem();
      File var4 = var2.getFile();
      File var5 = this.getTouchFile(var3, var4);
      String var6 = this.getUpdateKey(var1, var4, var2.getRepository());
      String var7 = this.getDataKey(var3, var4, var2.getAuthoritativeRepository());
      String var8 = this.getTransferKey(var1, var3, var4, var2.getRepository());
      this.setUpdated(var1, var6);
      this.write(var5, var7, var8, var2.getException());
   }

   private Properties write(File var1, String var2, String var3, Exception var4) {
      HashMap var5 = new HashMap();
      String var6 = Long.toString(System.currentTimeMillis());
      if (var4 == null) {
         var5.put(var2 + ".error", (Object)null);
         var5.put(var2 + ".lastUpdated", var6);
         var5.put(var3 + ".lastUpdated", (Object)null);
      } else if (!(var4 instanceof ArtifactNotFoundException) && !(var4 instanceof MetadataNotFoundException)) {
         String var7 = var4.getMessage();
         if (var7 == null || var7.length() <= 0) {
            var7 = var4.getClass().getSimpleName();
         }

         var5.put(var2 + ".error", var7);
         var5.put(var2 + ".lastUpdated", (Object)null);
         var5.put(var3 + ".lastUpdated", var6);
      } else {
         var5.put(var2 + ".error", "");
         var5.put(var2 + ".lastUpdated", var6);
         var5.put(var3 + ".lastUpdated", (Object)null);
      }

      return (new TrackingFileManager()).update(var1, var5);
   }
}
