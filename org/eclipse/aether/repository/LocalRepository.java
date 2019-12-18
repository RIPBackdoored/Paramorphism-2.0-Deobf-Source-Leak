package org.eclipse.aether.repository;

import java.io.File;
import java.util.Objects;

public final class LocalRepository implements ArtifactRepository {
   private final File basedir;
   private final String type;

   public LocalRepository(String var1) {
      this(var1 != null ? new File(var1) : null, "");
   }

   public LocalRepository(File var1) {
      this(var1, "");
   }

   public LocalRepository(File var1, String var2) {
      super();
      this.basedir = var1;
      this.type = var2 != null ? var2 : "";
   }

   public String getContentType() {
      return this.type;
   }

   public String getId() {
      return "local";
   }

   public File getBasedir() {
      return this.basedir;
   }

   public String toString() {
      return this.getBasedir() + " (" + this.getContentType() + ")";
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         LocalRepository var2 = (LocalRepository)var1;
         return Objects.equals(this.basedir, var2.basedir) && Objects.equals(this.type, var2.type);
      } else {
         return false;
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + hash(this.basedir);
      var2 = var2 * 31 + hash(this.type);
      return var2;
   }

   private static int hash(Object var0) {
      return var0 != null ? var0.hashCode() : 0;
   }
}
