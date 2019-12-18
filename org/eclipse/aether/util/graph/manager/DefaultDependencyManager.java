package org.eclipse.aether.util.graph.manager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencyManagement;
import org.eclipse.aether.collection.DependencyManager;
import org.eclipse.aether.graph.Dependency;

public final class DefaultDependencyManager implements DependencyManager {
   private final Map managedVersions;
   private final Map managedScopes;
   private final Map managedOptionals;
   private final Map managedLocalPaths;
   private final Map managedExclusions;
   private int hashCode;

   public DefaultDependencyManager() {
      this(Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap());
   }

   private DefaultDependencyManager(Map var1, Map var2, Map var3, Map var4, Map var5) {
      super();
      this.managedVersions = var1;
      this.managedScopes = var2;
      this.managedOptionals = var3;
      this.managedLocalPaths = var4;
      this.managedExclusions = var5;
   }

   public DependencyManager deriveChildManager(DependencyCollectionContext var1) {
      Object var2 = this.managedVersions;
      Object var3 = this.managedScopes;
      Object var4 = this.managedOptionals;
      Object var5 = this.managedLocalPaths;
      Object var6 = this.managedExclusions;
      Iterator var7 = var1.getManagedDependencies().iterator();

      while(var7.hasNext()) {
         Dependency var8 = (Dependency)var7.next();
         Artifact var9 = var8.getArtifact();
         Object var10 = this.getKey(var9);
         String var11 = var9.getVersion();
         if (var11.length() > 0 && !((Map)var2).containsKey(var10)) {
            if (var2 == this.managedVersions) {
               var2 = new HashMap(this.managedVersions);
            }

            ((Map)var2).put(var10, var11);
         }

         String var12 = var8.getScope();
         if (var12.length() > 0 && !((Map)var3).containsKey(var10)) {
            if (var3 == this.managedScopes) {
               var3 = new HashMap(this.managedScopes);
            }

            ((Map)var3).put(var10, var12);
         }

         Boolean var13 = var8.getOptional();
         if (var13 != null && !((Map)var4).containsKey(var10)) {
            if (var4 == this.managedOptionals) {
               var4 = new HashMap(this.managedOptionals);
            }

            ((Map)var4).put(var10, var13);
         }

         String var14 = var8.getArtifact().getProperty("localPath", (String)null);
         if (var14 != null && !((Map)var5).containsKey(var10)) {
            if (var5 == this.managedLocalPaths) {
               var5 = new HashMap(this.managedLocalPaths);
            }

            ((Map)var5).put(var10, var14);
         }

         if (!var8.getExclusions().isEmpty()) {
            if (var6 == this.managedExclusions) {
               var6 = new HashMap(this.managedExclusions);
            }

            Object var15 = (Collection)((Map)var6).get(var10);
            if (var15 == null) {
               var15 = new LinkedHashSet();
               ((Map)var6).put(var10, var15);
            }

            ((Collection)var15).addAll(var8.getExclusions());
         }
      }

      return new DefaultDependencyManager((Map)var2, (Map)var3, (Map)var4, (Map)var5, (Map)var6);
   }

   public DependencyManagement manageDependency(Dependency var1) {
      DependencyManagement var2 = null;
      Object var3 = this.getKey(var1.getArtifact());
      String var4 = (String)this.managedVersions.get(var3);
      if (var4 != null) {
         if (var2 == null) {
            var2 = new DependencyManagement();
         }

         var2.setVersion(var4);
      }

      String var5 = (String)this.managedScopes.get(var3);
      if (var5 != null) {
         if (var2 == null) {
            var2 = new DependencyManagement();
         }

         var2.setScope(var5);
         if (!"system".equals(var5) && var1.getArtifact().getProperty("localPath", (String)null) != null) {
            HashMap var6 = new HashMap(var1.getArtifact().getProperties());
            var6.remove("localPath");
            var2.setProperties(var6);
         }
      }

      if (var5 != null && "system".equals(var5) || var5 == null && "system".equals(var1.getScope())) {
         String var9 = (String)this.managedLocalPaths.get(var3);
         if (var9 != null) {
            if (var2 == null) {
               var2 = new DependencyManagement();
            }

            HashMap var7 = new HashMap(var1.getArtifact().getProperties());
            var7.put("localPath", var9);
            var2.setProperties(var7);
         }
      }

      Boolean var10 = (Boolean)this.managedOptionals.get(var3);
      if (var10 != null) {
         if (var2 == null) {
            var2 = new DependencyManagement();
         }

         var2.setOptional(var10);
      }

      Collection var11 = (Collection)this.managedExclusions.get(var3);
      if (var11 != null) {
         if (var2 == null) {
            var2 = new DependencyManagement();
         }

         LinkedHashSet var8 = new LinkedHashSet(var1.getExclusions());
         var8.addAll(var11);
         var2.setExclusions(var8);
      }

      return var2;
   }

   private Object getKey(Artifact var1) {
      return new DefaultDependencyManager$Key(var1);
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof DefaultDependencyManager;
      if (var2) {
         DefaultDependencyManager var3 = (DefaultDependencyManager)var1;
         var2 = Objects.equals(this.managedVersions, var3.managedVersions) && Objects.equals(this.managedScopes, var3.managedScopes) && Objects.equals(this.managedOptionals, var3.managedOptionals) && Objects.equals(this.managedExclusions, var3.managedExclusions);
      }

      return var2;
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         this.hashCode = Objects.hash(new Object[]{this.managedVersions, this.managedScopes, this.managedOptionals, this.managedExclusions});
      }

      return this.hashCode;
   }
}
