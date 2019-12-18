package org.eclipse.aether.util.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.aether.repository.WorkspaceReader;

class ChainedWorkspaceReader$Key {
   private final List keys = new ArrayList();

   ChainedWorkspaceReader$Key(List var1) {
      super();
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         WorkspaceReader var3 = (WorkspaceReader)var2.next();
         this.keys.add(var3.getRepository().getKey());
      }

   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return var1 != null && this.getClass().equals(var1.getClass()) ? this.keys.equals(((ChainedWorkspaceReader$Key)var1).keys) : false;
      }
   }

   public int hashCode() {
      return this.keys.hashCode();
   }
}
