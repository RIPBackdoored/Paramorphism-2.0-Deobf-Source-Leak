package org.eclipse.aether.util.graph.transformer;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.aether.RepositoryException;

public final class SimpleOptionalitySelector extends ConflictResolver$OptionalitySelector {
   public SimpleOptionalitySelector() {
      super();
   }

   public void selectOptionality(ConflictResolver$ConflictContext var1) throws RepositoryException {
      boolean var2 = this.chooseEffectiveOptionality(var1.getItems());
      var1.setOptional(var2);
   }

   private boolean chooseEffectiveOptionality(Collection var1) {
      boolean var2 = true;
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         ConflictResolver$ConflictItem var4 = (ConflictResolver$ConflictItem)var3.next();
         if (var4.getDepth() <= 1) {
            return var4.getDependency().isOptional();
         }

         if ((var4.getOptionalities() & 1) != 0) {
            var2 = false;
         }
      }

      return var2;
   }
}
