package org.eclipse.aether.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RemoteRepository$Builder {
   private static final RepositoryPolicy DEFAULT_POLICY = new RepositoryPolicy();
   static final int ID = 1;
   static final int TYPE = 2;
   static final int URL = 4;
   static final int RELEASES = 8;
   static final int SNAPSHOTS = 16;
   static final int PROXY = 32;
   static final int AUTH = 64;
   static final int MIRRORED = 128;
   static final int REPOMAN = 256;
   int delta;
   RemoteRepository prototype;
   String id;
   String type;
   String url;
   RepositoryPolicy releasePolicy;
   RepositoryPolicy snapshotPolicy;
   Proxy proxy;
   Authentication authentication;
   List mirroredRepositories;
   boolean repositoryManager;

   public RemoteRepository$Builder(String var1, String var2, String var3) {
      super();
      this.releasePolicy = DEFAULT_POLICY;
      this.snapshotPolicy = DEFAULT_POLICY;
      this.id = var1 != null ? var1 : "";
      this.type = var2 != null ? var2 : "";
      this.url = var3 != null ? var3 : "";
   }

   public RemoteRepository$Builder(RemoteRepository var1) {
      super();
      this.releasePolicy = DEFAULT_POLICY;
      this.snapshotPolicy = DEFAULT_POLICY;
      this.prototype = (RemoteRepository)Objects.requireNonNull(var1, "remote repository prototype cannot be null");
   }

   public RemoteRepository build() {
      return this.prototype != null && this.delta == 0 ? this.prototype : new RemoteRepository(this);
   }

   private void delta(int var1, Object var2, Object var3) {
      boolean var4 = Objects.equals(var2, var3);
      if (var4) {
         this.delta &= ~var1;
      } else {
         this.delta |= var1;
      }

   }

   public RemoteRepository$Builder setId(String var1) {
      this.id = var1 != null ? var1 : "";
      if (this.prototype != null) {
         this.delta(1, this.id, this.prototype.getId());
      }

      return this;
   }

   public RemoteRepository$Builder setContentType(String var1) {
      this.type = var1 != null ? var1 : "";
      if (this.prototype != null) {
         this.delta(2, this.type, this.prototype.getContentType());
      }

      return this;
   }

   public RemoteRepository$Builder setUrl(String var1) {
      this.url = var1 != null ? var1 : "";
      if (this.prototype != null) {
         this.delta(4, this.url, this.prototype.getUrl());
      }

      return this;
   }

   public RemoteRepository$Builder setPolicy(RepositoryPolicy var1) {
      this.releasePolicy = var1 != null ? var1 : DEFAULT_POLICY;
      this.snapshotPolicy = var1 != null ? var1 : DEFAULT_POLICY;
      if (this.prototype != null) {
         this.delta(8, this.releasePolicy, this.prototype.getPolicy(false));
         this.delta(16, this.snapshotPolicy, this.prototype.getPolicy(true));
      }

      return this;
   }

   public RemoteRepository$Builder setReleasePolicy(RepositoryPolicy var1) {
      this.releasePolicy = var1 != null ? var1 : DEFAULT_POLICY;
      if (this.prototype != null) {
         this.delta(8, this.releasePolicy, this.prototype.getPolicy(false));
      }

      return this;
   }

   public RemoteRepository$Builder setSnapshotPolicy(RepositoryPolicy var1) {
      this.snapshotPolicy = var1 != null ? var1 : DEFAULT_POLICY;
      if (this.prototype != null) {
         this.delta(16, this.snapshotPolicy, this.prototype.getPolicy(true));
      }

      return this;
   }

   public RemoteRepository$Builder setProxy(Proxy var1) {
      this.proxy = var1;
      if (this.prototype != null) {
         this.delta(32, this.proxy, this.prototype.getProxy());
      }

      return this;
   }

   public RemoteRepository$Builder setAuthentication(Authentication var1) {
      this.authentication = var1;
      if (this.prototype != null) {
         this.delta(64, this.authentication, this.prototype.getAuthentication());
      }

      return this;
   }

   public RemoteRepository$Builder setMirroredRepositories(List var1) {
      if (this.mirroredRepositories == null) {
         this.mirroredRepositories = new ArrayList();
      } else {
         this.mirroredRepositories.clear();
      }

      if (var1 != null) {
         this.mirroredRepositories.addAll(var1);
      }

      if (this.prototype != null) {
         this.delta(128, this.mirroredRepositories, this.prototype.getMirroredRepositories());
      }

      return this;
   }

   public RemoteRepository$Builder addMirroredRepository(RemoteRepository var1) {
      if (var1 != null) {
         if (this.mirroredRepositories == null) {
            this.mirroredRepositories = new ArrayList();
            if (this.prototype != null) {
               this.mirroredRepositories.addAll(this.prototype.getMirroredRepositories());
            }
         }

         this.mirroredRepositories.add(var1);
         if (this.prototype != null) {
            this.delta |= 128;
         }
      }

      return this;
   }

   public RemoteRepository$Builder setRepositoryManager(boolean var1) {
      this.repositoryManager = var1;
      if (this.prototype != null) {
         this.delta(256, this.repositoryManager, this.prototype.isRepositoryManager());
      }

      return this;
   }
}
