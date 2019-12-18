package org.eclipse.aether.util.graph.version;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.VersionFilter;
import org.eclipse.aether.collection.VersionFilter$VersionFilterContext;
import org.eclipse.aether.util.ConfigUtils;

public final class ContextualSnapshotVersionFilter implements VersionFilter {
   public static final String CONFIG_PROP_ENABLE = "aether.snapshotFilter";
   private final SnapshotVersionFilter filter = new SnapshotVersionFilter();

   public ContextualSnapshotVersionFilter() {
      super();
   }

   private boolean isEnabled(RepositorySystemSession var1) {
      return ConfigUtils.getBoolean(var1, false, "aether.snapshotFilter");
   }

   public void filterVersions(VersionFilter$VersionFilterContext var1) {
      if (this.isEnabled(var1.getSession())) {
         this.filter.filterVersions(var1);
      }

   }

   public VersionFilter deriveChildFilter(DependencyCollectionContext var1) {
      if (!this.isEnabled(var1.getSession())) {
         Artifact var2 = var1.getArtifact();
         if (var2 == null) {
            return this;
         }

         if (var2.isSnapshot()) {
            return null;
         }
      }

      return this.filter;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return null != var1 && this.getClass().equals(var1.getClass());
      }
   }

   public int hashCode() {
      return this.getClass().hashCode();
   }
}
