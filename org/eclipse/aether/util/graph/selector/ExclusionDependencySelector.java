package org.eclipse.aether.util.graph.selector;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.Exclusion;

public final class ExclusionDependencySelector implements DependencySelector {
   private final Exclusion[] exclusions;
   private int hashCode;

   public ExclusionDependencySelector() {
      super();
      this.exclusions = new Exclusion[0];
   }

   public ExclusionDependencySelector(Collection var1) {
      super();
      if (var1 != null && !var1.isEmpty()) {
         TreeSet var2 = new TreeSet(ExclusionDependencySelector$ExclusionComparator.INSTANCE);
         var2.addAll(var1);
         this.exclusions = (Exclusion[])var2.toArray(new Exclusion[var2.size()]);
      } else {
         this.exclusions = new Exclusion[0];
      }

   }

   private ExclusionDependencySelector(Exclusion[] var1) {
      super();
      this.exclusions = var1;
   }

   public boolean selectDependency(Dependency var1) {
      Artifact var2 = var1.getArtifact();
      Exclusion[] var3 = this.exclusions;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Exclusion var6 = var3[var5];
         if (this.matches(var6, var2)) {
            return false;
         }
      }

      return true;
   }

   private boolean matches(Exclusion var1, Artifact var2) {
      if (!this.matches(var1.getArtifactId(), var2.getArtifactId())) {
         return false;
      } else if (!this.matches(var1.getGroupId(), var2.getGroupId())) {
         return false;
      } else if (!this.matches(var1.getExtension(), var2.getExtension())) {
         return false;
      } else {
         return this.matches(var1.getClassifier(), var2.getClassifier());
      }
   }

   private boolean matches(String var1, String var2) {
      return "*".equals(var1) || var1.equals(var2);
   }

   public DependencySelector deriveChildSelector(DependencyCollectionContext var1) {
      Dependency var2 = var1.getDependency();
      Collection var3 = var2 != null ? var2.getExclusions() : null;
      if (var3 != null && !var3.isEmpty()) {
         Exclusion[] var4 = this.exclusions;
         int var5 = var4.length;
         Iterator var6 = var3.iterator();

         while(var6.hasNext()) {
            Exclusion var7 = (Exclusion)var6.next();
            int var8 = Arrays.binarySearch(var4, var7, ExclusionDependencySelector$ExclusionComparator.INSTANCE);
            if (var8 < 0) {
               var8 = -(var8 + 1);
               if (var5 >= var4.length) {
                  Exclusion[] var9 = new Exclusion[var4.length + var3.size()];
                  System.arraycopy(var4, 0, var9, 0, var8);
                  var9[var8] = var7;
                  System.arraycopy(var4, var8, var9, var8 + 1, var5 - var8);
                  var4 = var9;
               } else {
                  System.arraycopy(var4, var8, var4, var8 + 1, var5 - var8);
                  var4[var8] = var7;
               }

               ++var5;
            }
         }

         if (var4 == this.exclusions) {
            return this;
         } else {
            if (var4.length != var5) {
               Exclusion[] var10 = new Exclusion[var5];
               System.arraycopy(var4, 0, var10, 0, var5);
               var4 = var10;
            }

            return new ExclusionDependencySelector(var4);
         }
      } else {
         return this;
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (null != var1 && this.getClass().equals(var1.getClass())) {
         ExclusionDependencySelector var2 = (ExclusionDependencySelector)var1;
         return Arrays.equals(this.exclusions, var2.exclusions);
      } else {
         return false;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int var1 = this.getClass().hashCode();
         var1 = var1 * 31 + Arrays.hashCode(this.exclusions);
         this.hashCode = var1;
      }

      return this.hashCode;
   }
}
