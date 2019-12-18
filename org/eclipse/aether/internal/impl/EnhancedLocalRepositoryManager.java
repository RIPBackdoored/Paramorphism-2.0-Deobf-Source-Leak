package org.eclipse.aether.internal.impl;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.LocalArtifactRegistration;
import org.eclipse.aether.repository.LocalArtifactRequest;
import org.eclipse.aether.repository.LocalArtifactResult;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.util.ConfigUtils;

class EnhancedLocalRepositoryManager extends SimpleLocalRepositoryManager {
   private static final String LOCAL_REPO_ID = "";
   private final String trackingFilename;
   private final TrackingFileManager trackingFileManager;

   EnhancedLocalRepositoryManager(File var1, RepositorySystemSession var2) {
      super(var1, "enhanced");
      String var3 = ConfigUtils.getString(var2, "", "aether.enhancedLocalRepository.trackingFilename");
      if (var3.length() <= 0 || var3.contains("/") || var3.contains("\\") || var3.contains("..")) {
         var3 = "_remote.repositories";
      }

      this.trackingFilename = var3;
      this.trackingFileManager = new TrackingFileManager();
   }

   public LocalArtifactResult find(RepositorySystemSession var1, LocalArtifactRequest var2) {
      String var3 = this.getPathForArtifact(var2.getArtifact(), false);
      File var4 = new File(this.getRepository().getBasedir(), var3);
      LocalArtifactResult var5 = new LocalArtifactResult(var2);
      if (var4.isFile()) {
         var5.setFile(var4);
         Properties var6 = this.readRepos(var4);
         if (var6.get(this.getKey(var4, "")) != null) {
            var5.setAvailable(true);
         } else {
            String var7 = var2.getContext();
            Iterator var8 = var2.getRepositories().iterator();

            while(var8.hasNext()) {
               RemoteRepository var9 = (RemoteRepository)var8.next();
               if (var6.get(this.getKey(var4, this.getRepositoryKey(var9, var7))) != null) {
                  var5.setAvailable(true);
                  var5.setRepository(var9);
                  break;
               }
            }

            if (!var5.isAvailable() && !this.isTracked(var6, var4)) {
               var5.setAvailable(true);
            }
         }
      }

      return var5;
   }

   public void add(RepositorySystemSession var1, LocalArtifactRegistration var2) {
      Object var3;
      if (var2.getRepository() == null) {
         var3 = Collections.singleton("");
      } else {
         var3 = this.getRepositoryKeys(var2.getRepository(), var2.getContexts());
      }

      this.addArtifact(var2.getArtifact(), (Collection)var3, var2.getRepository() == null);
   }

   private Collection getRepositoryKeys(RemoteRepository var1, Collection var2) {
      HashSet var3 = new HashSet();
      if (var2 != null) {
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            var3.add(this.getRepositoryKey(var1, var5));
         }
      }

      return var3;
   }

   private void addArtifact(Artifact var1, Collection var2, boolean var3) {
      String var4 = this.getPathForArtifact((Artifact)Objects.requireNonNull(var1, "artifact cannot be null"), var3);
      File var5 = new File(this.getRepository().getBasedir(), var4);
      this.addRepo(var5, var2);
   }

   private Properties readRepos(File var1) {
      File var2 = this.getTrackingFile(var1);
      Properties var3 = this.trackingFileManager.read(var2);
      return var3 != null ? var3 : new Properties();
   }

   private void addRepo(File var1, Collection var2) {
      HashMap var3 = new HashMap();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         var3.put(this.getKey(var1, var5), "");
      }

      File var6 = this.getTrackingFile(var1);
      this.trackingFileManager.update(var6, var3);
   }

   private File getTrackingFile(File var1) {
      return new File(var1.getParentFile(), this.trackingFilename);
   }

   private String getKey(File var1, String var2) {
      return var1.getName() + '>' + var2;
   }

   private boolean isTracked(Properties var1, File var2) {
      if (var1 != null) {
         String var3 = var2.getName() + '>';
         Iterator var4 = var1.keySet().iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            if (var5.toString().startsWith(var3)) {
               return true;
            }
         }
      }

      return false;
   }
}
