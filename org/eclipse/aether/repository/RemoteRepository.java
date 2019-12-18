package org.eclipse.aether.repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RemoteRepository implements ArtifactRepository {
   private static final Pattern URL_PATTERN = Pattern.compile("([^:/]+(:[^:/]{2,}+(?=://))?):(//([^@/]*@)?([^/:]+))?.*");
   private final String id;
   private final String type;
   private final String url;
   private final String host;
   private final String protocol;
   private final RepositoryPolicy releasePolicy;
   private final RepositoryPolicy snapshotPolicy;
   private final Proxy proxy;
   private final Authentication authentication;
   private final List mirroredRepositories;
   private final boolean repositoryManager;

   RemoteRepository(RemoteRepository$Builder var1) {
      super();
      if (var1.prototype != null) {
         this.id = (var1.delta & 1) != 0 ? var1.id : var1.prototype.id;
         this.type = (var1.delta & 2) != 0 ? var1.type : var1.prototype.type;
         this.url = (var1.delta & 4) != 0 ? var1.url : var1.prototype.url;
         this.releasePolicy = (var1.delta & 8) != 0 ? var1.releasePolicy : var1.prototype.releasePolicy;
         this.snapshotPolicy = (var1.delta & 16) != 0 ? var1.snapshotPolicy : var1.prototype.snapshotPolicy;
         this.proxy = (var1.delta & 32) != 0 ? var1.proxy : var1.prototype.proxy;
         this.authentication = (var1.delta & 64) != 0 ? var1.authentication : var1.prototype.authentication;
         this.repositoryManager = (var1.delta & 256) != 0 ? var1.repositoryManager : var1.prototype.repositoryManager;
         this.mirroredRepositories = (var1.delta & 128) != 0 ? copy(var1.mirroredRepositories) : var1.prototype.mirroredRepositories;
      } else {
         this.id = var1.id;
         this.type = var1.type;
         this.url = var1.url;
         this.releasePolicy = var1.releasePolicy;
         this.snapshotPolicy = var1.snapshotPolicy;
         this.proxy = var1.proxy;
         this.authentication = var1.authentication;
         this.repositoryManager = var1.repositoryManager;
         this.mirroredRepositories = copy(var1.mirroredRepositories);
      }

      Matcher var2 = URL_PATTERN.matcher(this.url);
      if (var2.matches()) {
         this.protocol = var2.group(1);
         String var3 = var2.group(5);
         this.host = var3 != null ? var3 : "";
      } else {
         this.protocol = "";
         this.host = "";
      }

   }

   private static List copy(List var0) {
      return var0 != null && !var0.isEmpty() ? Collections.unmodifiableList(Arrays.asList(var0.toArray(new RemoteRepository[var0.size()]))) : Collections.emptyList();
   }

   public String getId() {
      return this.id;
   }

   public String getContentType() {
      return this.type;
   }

   public String getUrl() {
      return this.url;
   }

   public String getProtocol() {
      return this.protocol;
   }

   public String getHost() {
      return this.host;
   }

   public RepositoryPolicy getPolicy(boolean var1) {
      return var1 ? this.snapshotPolicy : this.releasePolicy;
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public Authentication getAuthentication() {
      return this.authentication;
   }

   public List getMirroredRepositories() {
      return this.mirroredRepositories;
   }

   public boolean isRepositoryManager() {
      return this.repositoryManager;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(256);
      var1.append(this.getId());
      var1.append(" (").append(this.getUrl());
      var1.append(", ").append(this.getContentType());
      boolean var2 = this.getPolicy(false).isEnabled();
      boolean var3 = this.getPolicy(true).isEnabled();
      if (var2 && var3) {
         var1.append(", releases+snapshots");
      } else if (var2) {
         var1.append(", releases");
      } else if (var3) {
         var1.append(", snapshots");
      } else {
         var1.append(", disabled");
      }

      if (this.isRepositoryManager()) {
         var1.append(", managed");
      }

      var1.append(")");
      return var1.toString();
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         RemoteRepository var2 = (RemoteRepository)var1;
         return Objects.equals(this.url, var2.url) && Objects.equals(this.type, var2.type) && Objects.equals(this.id, var2.id) && Objects.equals(this.releasePolicy, var2.releasePolicy) && Objects.equals(this.snapshotPolicy, var2.snapshotPolicy) && Objects.equals(this.proxy, var2.proxy) && Objects.equals(this.authentication, var2.authentication) && Objects.equals(this.mirroredRepositories, var2.mirroredRepositories) && this.repositoryManager == var2.repositoryManager;
      } else {
         return false;
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + hash(this.url);
      var2 = var2 * 31 + hash(this.type);
      var2 = var2 * 31 + hash(this.id);
      var2 = var2 * 31 + hash(this.releasePolicy);
      var2 = var2 * 31 + hash(this.snapshotPolicy);
      var2 = var2 * 31 + hash(this.proxy);
      var2 = var2 * 31 + hash(this.authentication);
      var2 = var2 * 31 + hash(this.mirroredRepositories);
      var2 = var2 * 31 + (this.repositoryManager ? 1 : 0);
      return var2;
   }

   private static int hash(Object var0) {
      return var0 != null ? var0.hashCode() : 0;
   }
}
