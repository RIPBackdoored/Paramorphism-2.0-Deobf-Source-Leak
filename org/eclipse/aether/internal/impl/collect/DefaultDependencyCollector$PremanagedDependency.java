package org.eclipse.aether.internal.impl.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.collection.DependencyManagement;
import org.eclipse.aether.collection.DependencyManager;
import org.eclipse.aether.graph.DefaultDependencyNode;
import org.eclipse.aether.graph.Dependency;

class DefaultDependencyCollector$PremanagedDependency {
   final String premanagedVersion;
   final String premanagedScope;
   final Boolean premanagedOptional;
   final Collection premanagedExclusions;
   final Map premanagedProperties;
   final int managedBits;
   final Dependency managedDependency;
   final boolean premanagedState;

   DefaultDependencyCollector$PremanagedDependency(String var1, String var2, Boolean var3, Collection var4, Map var5, int var6, Dependency var7, boolean var8) {
      super();
      this.premanagedVersion = var1;
      this.premanagedScope = var2;
      this.premanagedOptional = var3;
      this.premanagedExclusions = var4 != null ? Collections.unmodifiableCollection(new ArrayList(var4)) : null;
      this.premanagedProperties = var5 != null ? Collections.unmodifiableMap(new HashMap(var5)) : null;
      this.managedBits = var6;
      this.managedDependency = var7;
      this.premanagedState = var8;
   }

   static DefaultDependencyCollector$PremanagedDependency create(DependencyManager var0, Dependency var1, boolean var2, boolean var3) {
      DependencyManagement var4 = var0 != null ? var0.manageDependency(var1) : null;
      int var5 = 0;
      String var6 = null;
      String var7 = null;
      Boolean var8 = null;
      Collection var9 = null;
      Map var10 = null;
      if (var4 != null) {
         Artifact var11;
         if (var4.getVersion() != null && !var2) {
            var11 = var1.getArtifact();
            var6 = var11.getVersion();
            var1 = var1.setArtifact(var11.setVersion(var4.getVersion()));
            var5 |= 1;
         }

         if (var4.getProperties() != null) {
            var11 = var1.getArtifact();
            var10 = var11.getProperties();
            var1 = var1.setArtifact(var11.setProperties(var4.getProperties()));
            var5 |= 8;
         }

         if (var4.getScope() != null) {
            var7 = var1.getScope();
            var1 = var1.setScope(var4.getScope());
            var5 |= 2;
         }

         if (var4.getOptional() != null) {
            var8 = var1.isOptional();
            var1 = var1.setOptional(var4.getOptional());
            var5 |= 4;
         }

         if (var4.getExclusions() != null) {
            var9 = var1.getExclusions();
            var1 = var1.setExclusions(var4.getExclusions());
            var5 |= 16;
         }
      }

      return new DefaultDependencyCollector$PremanagedDependency(var6, var7, var8, var9, var10, var5, var1, var3);
   }

   public void applyTo(DefaultDependencyNode var1) {
      var1.setManagedBits(this.managedBits);
      if (this.premanagedState) {
         var1.setData("premanaged.version", this.premanagedVersion);
         var1.setData("premanaged.scope", this.premanagedScope);
         var1.setData("premanaged.optional", this.premanagedOptional);
         var1.setData("premanaged.exclusions", this.premanagedExclusions);
         var1.setData("premanaged.properties", this.premanagedProperties);
      }

   }
}
