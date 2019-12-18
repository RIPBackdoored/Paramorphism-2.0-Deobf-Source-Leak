package org.eclipse.aether.graph;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import org.eclipse.aether.artifact.Artifact;

public final class Dependency {
   private final Artifact artifact;
   private final String scope;
   private final Boolean optional;
   private final Set exclusions;

   public Dependency(Artifact var1, String var2) {
      this(var1, var2, false);
   }

   public Dependency(Artifact var1, String var2, Boolean var3) {
      this(var1, var2, (Boolean)var3, (Collection)null);
   }

   public Dependency(Artifact var1, String var2, Boolean var3, Collection var4) {
      this(var1, var2, Dependency$Exclusions.copy(var4), var3);
   }

   private Dependency(Artifact var1, String var2, Set var3, Boolean var4) {
      super();
      this.artifact = (Artifact)Objects.requireNonNull(var1, "artifact cannot be null");
      this.scope = var2 != null ? var2 : "";
      this.optional = var4;
      this.exclusions = var3;
   }

   public Artifact getArtifact() {
      return this.artifact;
   }

   public Dependency setArtifact(Artifact var1) {
      return this.artifact.equals(var1) ? this : new Dependency(var1, this.scope, this.exclusions, this.optional);
   }

   public String getScope() {
      return this.scope;
   }

   public Dependency setScope(String var1) {
      return !this.scope.equals(var1) && (var1 != null || this.scope.length() > 0) ? new Dependency(this.artifact, var1, this.exclusions, this.optional) : this;
   }

   public boolean isOptional() {
      return Boolean.TRUE.equals(this.optional);
   }

   public Boolean getOptional() {
      return this.optional;
   }

   public Dependency setOptional(Boolean var1) {
      return Objects.equals(this.optional, var1) ? this : new Dependency(this.artifact, this.scope, this.exclusions, var1);
   }

   public Collection getExclusions() {
      return this.exclusions;
   }

   public Dependency setExclusions(Collection var1) {
      return this.hasEquivalentExclusions(var1) ? this : new Dependency(this.artifact, this.scope, this.optional, var1);
   }

   private boolean hasEquivalentExclusions(Collection var1) {
      if (var1 != null && !var1.isEmpty()) {
         if (var1 instanceof Set) {
            return this.exclusions.equals(var1);
         } else {
            return var1.size() >= this.exclusions.size() && this.exclusions.containsAll(var1) && var1.containsAll(this.exclusions);
         }
      } else {
         return this.exclusions.isEmpty();
      }
   }

   public String toString() {
      return this.getArtifact() + " (" + this.getScope() + (this.isOptional() ? "?" : "") + ")";
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         Dependency var2 = (Dependency)var1;
         return Objects.equals(this.artifact, var2.artifact) && Objects.equals(this.scope, var2.scope) && Objects.equals(this.optional, var2.optional) && Objects.equals(this.exclusions, var2.exclusions);
      } else {
         return false;
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + this.artifact.hashCode();
      var2 = var2 * 31 + this.scope.hashCode();
      var2 = var2 * 31 + (this.optional != null ? this.optional.hashCode() : 0);
      var2 = var2 * 31 + this.exclusions.size();
      return var2;
   }
}
