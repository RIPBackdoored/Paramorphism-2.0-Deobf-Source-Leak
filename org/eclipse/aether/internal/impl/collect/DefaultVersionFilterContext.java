package org.eclipse.aether.internal.impl.collect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.collection.VersionFilter$VersionFilterContext;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.ArtifactRepository;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.version.Version;
import org.eclipse.aether.version.VersionConstraint;

final class DefaultVersionFilterContext implements VersionFilter$VersionFilterContext {
   private final RepositorySystemSession session;
   private Dependency dependency;
   VersionRangeResult result;
   int count;
   byte[] deleted = new byte[64];

   DefaultVersionFilterContext(RepositorySystemSession var1) {
      super();
      this.session = var1;
   }

   public void set(Dependency var1, VersionRangeResult var2) {
      this.dependency = var1;
      this.result = var2;
      this.count = var2.getVersions().size();
      if (this.deleted.length < this.count) {
         this.deleted = new byte[this.count];
      } else {
         for(int var3 = this.count - 1; var3 >= 0; --var3) {
            this.deleted[var3] = 0;
         }
      }

   }

   public List get() {
      if (this.count == this.result.getVersions().size()) {
         return this.result.getVersions();
      } else if (this.count <= 1) {
         return this.count <= 0 ? Collections.emptyList() : Collections.singletonList(this.iterator().next());
      } else {
         ArrayList var1 = new ArrayList(this.count);
         Iterator var2 = this.iterator();

         while(var2.hasNext()) {
            Version var3 = (Version)var2.next();
            var1.add(var3);
         }

         return var1;
      }
   }

   public RepositorySystemSession getSession() {
      return this.session;
   }

   public Dependency getDependency() {
      return this.dependency;
   }

   public VersionConstraint getVersionConstraint() {
      return this.result.getVersionConstraint();
   }

   public int getCount() {
      return this.count;
   }

   public ArtifactRepository getRepository(Version var1) {
      return this.result.getRepository(var1);
   }

   public List getRepositories() {
      return Collections.unmodifiableList(this.result.getRequest().getRepositories());
   }

   public Iterator iterator() {
      return (Iterator)(this.count > 0 ? new DefaultVersionFilterContext$VersionIterator(this) : Collections.emptySet().iterator());
   }

   public String toString() {
      return this.dependency + " " + this.result.getVersions();
   }
}
