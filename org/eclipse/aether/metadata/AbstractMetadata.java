package org.eclipse.aether.metadata;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractMetadata implements Metadata {
   public AbstractMetadata() {
      super();
   }

   private Metadata newInstance(Map var1, File var2) {
      return new DefaultMetadata(this.getGroupId(), this.getArtifactId(), this.getVersion(), this.getType(), this.getNature(), var2, var1);
   }

   public Metadata setFile(File var1) {
      File var2 = this.getFile();
      return (Metadata)(Objects.equals(var2, var1) ? this : this.newInstance(this.getProperties(), var1));
   }

   public Metadata setProperties(Map var1) {
      Map var2 = this.getProperties();
      return (Metadata)(!var2.equals(var1) && (var1 != null || !var2.isEmpty()) ? this.newInstance(copyProperties(var1), this.getFile()) : this);
   }

   public String getProperty(String var1, String var2) {
      String var3 = (String)this.getProperties().get(var1);
      return var3 != null ? var3 : var2;
   }

   protected static Map copyProperties(Map var0) {
      return var0 != null && !var0.isEmpty() ? Collections.unmodifiableMap(new HashMap(var0)) : Collections.emptyMap();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(128);
      if (this.getGroupId().length() > 0) {
         var1.append(this.getGroupId());
      }

      if (this.getArtifactId().length() > 0) {
         var1.append(':').append(this.getArtifactId());
      }

      if (this.getVersion().length() > 0) {
         var1.append(':').append(this.getVersion());
      }

      var1.append('/').append(this.getType());
      return var1.toString();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Metadata)) {
         return false;
      } else {
         Metadata var2 = (Metadata)var1;
         return Objects.equals(this.getArtifactId(), var2.getArtifactId()) && Objects.equals(this.getGroupId(), var2.getGroupId()) && Objects.equals(this.getVersion(), var2.getVersion()) && Objects.equals(this.getType(), var2.getType()) && Objects.equals(this.getNature(), var2.getNature()) && Objects.equals(this.getFile(), var2.getFile()) && Objects.equals(this.getProperties(), var2.getProperties());
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + this.getGroupId().hashCode();
      var2 = var2 * 31 + this.getArtifactId().hashCode();
      var2 = var2 * 31 + this.getType().hashCode();
      var2 = var2 * 31 + this.getNature().hashCode();
      var2 = var2 * 31 + this.getVersion().hashCode();
      var2 = var2 * 31 + hash(this.getFile());
      return var2;
   }

   private static int hash(Object var0) {
      return var0 != null ? var0.hashCode() : 0;
   }
}
