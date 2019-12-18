package org.eclipse.aether.internal.impl;

import java.io.File;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.LocalArtifactRegistration;
import org.eclipse.aether.repository.LocalArtifactRequest;
import org.eclipse.aether.repository.LocalArtifactResult;
import org.eclipse.aether.repository.LocalMetadataRegistration;
import org.eclipse.aether.repository.LocalMetadataRequest;
import org.eclipse.aether.repository.LocalMetadataResult;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.RemoteRepository;

class SimpleLocalRepositoryManager implements LocalRepositoryManager {
   private final LocalRepository repository;

   SimpleLocalRepositoryManager(File var1) {
      this(var1, "simple");
   }

   SimpleLocalRepositoryManager(String var1) {
      this(var1 != null ? new File(var1) : null, "simple");
   }

   SimpleLocalRepositoryManager(File var1, String var2) {
      super();
      Objects.requireNonNull(var1, "base directory cannot be null");
      this.repository = new LocalRepository(var1.getAbsoluteFile(), var2);
   }

   public LocalRepository getRepository() {
      return this.repository;
   }

   String getPathForArtifact(Artifact var1, boolean var2) {
      StringBuilder var3 = new StringBuilder(128);
      var3.append(var1.getGroupId().replace('.', '/')).append('/');
      var3.append(var1.getArtifactId()).append('/');
      var3.append(var1.getBaseVersion()).append('/');
      var3.append(var1.getArtifactId()).append('-');
      if (var2) {
         var3.append(var1.getBaseVersion());
      } else {
         var3.append(var1.getVersion());
      }

      if (var1.getClassifier().length() > 0) {
         var3.append('-').append(var1.getClassifier());
      }

      if (var1.getExtension().length() > 0) {
         var3.append('.').append(var1.getExtension());
      }

      return var3.toString();
   }

   public String getPathForLocalArtifact(Artifact var1) {
      return this.getPathForArtifact(var1, true);
   }

   public String getPathForRemoteArtifact(Artifact var1, RemoteRepository var2, String var3) {
      return this.getPathForArtifact(var1, false);
   }

   public String getPathForLocalMetadata(Metadata var1) {
      return this.getPath(var1, "local");
   }

   public String getPathForRemoteMetadata(Metadata var1, RemoteRepository var2, String var3) {
      return this.getPath(var1, this.getRepositoryKey(var2, var3));
   }

   String getRepositoryKey(RemoteRepository var1, String var2) {
      String var3;
      if (var1.isRepositoryManager()) {
         StringBuilder var4 = new StringBuilder(128);
         var4.append(var1.getId());
         var4.append('-');
         TreeSet var5 = new TreeSet();
         Iterator var6 = var1.getMirroredRepositories().iterator();

         while(var6.hasNext()) {
            RemoteRepository var7 = (RemoteRepository)var6.next();
            var5.add(var7.getId());
         }

         SimpleDigest var9 = new SimpleDigest();
         var9.update(var2);
         Iterator var10 = var5.iterator();

         while(var10.hasNext()) {
            String var8 = (String)var10.next();
            var9.update(var8);
         }

         var4.append(var9.digest());
         var3 = var4.toString();
      } else {
         var3 = var1.getId();
      }

      return var3;
   }

   private String getPath(Metadata var1, String var2) {
      StringBuilder var3 = new StringBuilder(128);
      if (var1.getGroupId().length() > 0) {
         var3.append(var1.getGroupId().replace('.', '/')).append('/');
         if (var1.getArtifactId().length() > 0) {
            var3.append(var1.getArtifactId()).append('/');
            if (var1.getVersion().length() > 0) {
               var3.append(var1.getVersion()).append('/');
            }
         }
      }

      var3.append(this.insertRepositoryKey(var1.getType(), var2));
      return var3.toString();
   }

   private String insertRepositoryKey(String var1, String var2) {
      int var4 = var1.indexOf(46);
      String var3;
      if (var4 < 0) {
         var3 = var1 + '-' + var2;
      } else {
         var3 = var1.substring(0, var4) + '-' + var2 + var1.substring(var4);
      }

      return var3;
   }

   public LocalArtifactResult find(RepositorySystemSession var1, LocalArtifactRequest var2) {
      String var3 = this.getPathForArtifact(var2.getArtifact(), false);
      File var4 = new File(this.getRepository().getBasedir(), var3);
      LocalArtifactResult var5 = new LocalArtifactResult(var2);
      if (var4.isFile()) {
         var5.setFile(var4);
         var5.setAvailable(true);
      }

      return var5;
   }

   public void add(RepositorySystemSession var1, LocalArtifactRegistration var2) {
   }

   public String toString() {
      return String.valueOf(this.getRepository());
   }

   public LocalMetadataResult find(RepositorySystemSession var1, LocalMetadataRequest var2) {
      LocalMetadataResult var3 = new LocalMetadataResult(var2);
      Metadata var5 = var2.getMetadata();
      String var6 = var2.getContext();
      RemoteRepository var7 = var2.getRepository();
      String var4;
      if (var7 != null) {
         var4 = this.getPathForRemoteMetadata(var5, var7, var6);
      } else {
         var4 = this.getPathForLocalMetadata(var5);
      }

      File var8 = new File(this.getRepository().getBasedir(), var4);
      if (var8.isFile()) {
         var3.setFile(var8);
      }

      return var3;
   }

   public void add(RepositorySystemSession var1, LocalMetadataRegistration var2) {
   }
}
