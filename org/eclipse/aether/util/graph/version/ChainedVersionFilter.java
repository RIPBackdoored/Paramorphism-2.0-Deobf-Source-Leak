package org.eclipse.aether.util.graph.version;

import java.util.Arrays;
import java.util.Collection;
import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.VersionFilter;
import org.eclipse.aether.collection.VersionFilter$VersionFilterContext;

public final class ChainedVersionFilter implements VersionFilter {
   private final VersionFilter[] filters;
   private int hashCode;

   public static VersionFilter newInstance(VersionFilter var0, VersionFilter var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (VersionFilter)(var1 == null ? var0 : new ChainedVersionFilter(new VersionFilter[]{var0, var1}));
      }
   }

   public static VersionFilter newInstance(VersionFilter... var0) {
      if (var0.length <= 1) {
         return var0.length <= 0 ? null : var0[0];
      } else {
         return new ChainedVersionFilter((VersionFilter[])var0.clone());
      }
   }

   public static VersionFilter newInstance(Collection var0) {
      if (var0.size() <= 1) {
         return var0.isEmpty() ? null : (VersionFilter)var0.iterator().next();
      } else {
         return new ChainedVersionFilter((VersionFilter[])var0.toArray(new VersionFilter[var0.size()]));
      }
   }

   private ChainedVersionFilter(VersionFilter[] var1) {
      super();
      this.filters = var1;
   }

   public void filterVersions(VersionFilter$VersionFilterContext var1) throws RepositoryException {
      int var2 = 0;

      for(int var3 = this.filters.length; var2 < var3 && var1.getCount() > 0; ++var2) {
         this.filters[var2].filterVersions(var1);
      }

   }

   public VersionFilter deriveChildFilter(DependencyCollectionContext var1) {
      VersionFilter[] var2 = null;
      int var3 = 0;
      int var4 = 0;

      for(int var5 = this.filters.length; var4 < var5; ++var4) {
         VersionFilter var6 = this.filters[var4].deriveChildFilter(var1);
         if (var2 != null) {
            var2[var4 - var3] = var6;
         } else if (var6 != this.filters[var4]) {
            var2 = new VersionFilter[this.filters.length];
            System.arraycopy(this.filters, 0, var2, 0, var4);
            var2[var4 - var3] = var6;
         }

         if (var6 == null) {
            ++var3;
         }
      }

      if (var2 == null) {
         return this;
      } else {
         if (var3 > 0) {
            var4 = this.filters.length - var3;
            if (var4 <= 0) {
               return null;
            }

            if (var4 == 1) {
               return var2[0];
            }

            VersionFilter[] var7 = new VersionFilter[var4];
            System.arraycopy(var2, 0, var7, 0, var4);
            var2 = var7;
         }

         return new ChainedVersionFilter(var2);
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (null != var1 && this.getClass().equals(var1.getClass())) {
         ChainedVersionFilter var2 = (ChainedVersionFilter)var1;
         return Arrays.equals(this.filters, var2.filters);
      } else {
         return false;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int var1 = this.getClass().hashCode();
         var1 = var1 * 31 + Arrays.hashCode(this.filters);
         this.hashCode = var1;
      }

      return this.hashCode;
   }
}
