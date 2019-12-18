package org.eclipse.aether.repository;

import java.util.UUID;

public final class WorkspaceRepository implements ArtifactRepository {
   private final String type;
   private final Object key;

   public WorkspaceRepository() {
      this("workspace");
   }

   public WorkspaceRepository(String var1) {
      this(var1, (Object)null);
   }

   public WorkspaceRepository(String var1, Object var2) {
      super();
      this.type = var1 != null ? var1 : "";
      this.key = var2 != null ? var2 : UUID.randomUUID().toString().replace("-", "");
   }

   public String getContentType() {
      return this.type;
   }

   public String getId() {
      return "workspace";
   }

   public Object getKey() {
      return this.key;
   }

   public String toString() {
      return "(" + this.getContentType() + ")";
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         WorkspaceRepository var2 = (WorkspaceRepository)var1;
         return this.getContentType().equals(var2.getContentType()) && this.getKey().equals(var2.getKey());
      } else {
         return false;
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + this.getKey().hashCode();
      var2 = var2 * 31 + this.getContentType().hashCode();
      return var2;
   }
}
