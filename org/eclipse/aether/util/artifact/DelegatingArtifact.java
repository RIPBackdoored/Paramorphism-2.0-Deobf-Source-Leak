package org.eclipse.aether.util.artifact;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import org.eclipse.aether.artifact.AbstractArtifact;
import org.eclipse.aether.artifact.Artifact;

public abstract class DelegatingArtifact extends AbstractArtifact {
   private final Artifact delegate;

   protected DelegatingArtifact(Artifact var1) {
      super();
      this.delegate = (Artifact)Objects.requireNonNull(var1, "delegate artifact cannot be null");
   }

   protected abstract DelegatingArtifact newInstance(Artifact var1);

   public String getGroupId() {
      return this.delegate.getGroupId();
   }

   public String getArtifactId() {
      return this.delegate.getArtifactId();
   }

   public String getVersion() {
      return this.delegate.getVersion();
   }

   public Artifact setVersion(String var1) {
      Artifact var2 = this.delegate.setVersion(var1);
      return var2 != this.delegate ? this.newInstance(var2) : this;
   }

   public String getBaseVersion() {
      return this.delegate.getBaseVersion();
   }

   public boolean isSnapshot() {
      return this.delegate.isSnapshot();
   }

   public String getClassifier() {
      return this.delegate.getClassifier();
   }

   public String getExtension() {
      return this.delegate.getExtension();
   }

   public File getFile() {
      return this.delegate.getFile();
   }

   public Artifact setFile(File var1) {
      Artifact var2 = this.delegate.setFile(var1);
      return var2 != this.delegate ? this.newInstance(var2) : this;
   }

   public String getProperty(String var1, String var2) {
      return this.delegate.getProperty(var1, var2);
   }

   public Map getProperties() {
      return this.delegate.getProperties();
   }

   public Artifact setProperties(Map var1) {
      Artifact var2 = this.delegate.setProperties(var1);
      return var2 != this.delegate ? this.newInstance(var2) : this;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         return var1 instanceof DelegatingArtifact ? this.delegate.equals(((DelegatingArtifact)var1).delegate) : this.delegate.equals(var1);
      }
   }

   public int hashCode() {
      return this.delegate.hashCode();
   }

   public String toString() {
      return this.delegate.toString();
   }
}
