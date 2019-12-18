package org.eclipse.aether.util.graph.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.collection.DependencyGraphTransformationContext;
import org.eclipse.aether.collection.DependencyGraphTransformer;
import org.eclipse.aether.graph.DependencyNode;

public final class ConflictIdSorter implements DependencyGraphTransformer {
   public ConflictIdSorter() {
      super();
   }

   public DependencyNode transformGraph(DependencyNode var1, DependencyGraphTransformationContext var2) throws RepositoryException {
      Map var3 = (Map)var2.get(TransformationContextKeys.CONFLICT_IDS);
      if (var3 == null) {
         ConflictMarker var4 = new ConflictMarker();
         var4.transformGraph(var1, var2);
         var3 = (Map)var2.get(TransformationContextKeys.CONFLICT_IDS);
      }

      Map var16 = (Map)var2.get(TransformationContextKeys.STATS);
      long var5 = System.nanoTime();
      LinkedHashMap var7 = new LinkedHashMap(256);
      ConflictIdSorter$ConflictId var8 = null;
      Object var9 = var3.get(var1);
      if (var9 != null) {
         var8 = new ConflictIdSorter$ConflictId(var9, 0);
         var7.put(var9, var8);
      }

      IdentityHashMap var10 = new IdentityHashMap(var3.size());
      this.buildConflitIdDAG(var7, var1, var8, 0, var10, var3);
      long var11 = System.nanoTime();
      int var13 = this.topsortConflictIds(var7.values(), var2);
      if (var16 != null) {
         long var14 = System.nanoTime();
         var16.put("ConflictIdSorter.graphTime", var11 - var5);
         var16.put("ConflictIdSorter.topsortTime", var14 - var11);
         var16.put("ConflictIdSorter.conflictIdCount", var7.size());
         var16.put("ConflictIdSorter.conflictIdCycleCount", var13);
      }

      return var1;
   }

   private void buildConflitIdDAG(Map var1, DependencyNode var2, ConflictIdSorter$ConflictId var3, int var4, Map var5, Map var6) {
      if (var5.put(var2, Boolean.TRUE) == null) {
         ++var4;

         DependencyNode var8;
         ConflictIdSorter$ConflictId var10;
         for(Iterator var7 = var2.getChildren().iterator(); var7.hasNext(); this.buildConflitIdDAG(var1, var8, var10, var4, var5, var6)) {
            var8 = (DependencyNode)var7.next();
            Object var9 = var6.get(var8);
            var10 = (ConflictIdSorter$ConflictId)var1.get(var9);
            if (var10 == null) {
               var10 = new ConflictIdSorter$ConflictId(var9, var4);
               var1.put(var9, var10);
            } else {
               var10.pullup(var4);
            }

            if (var3 != null) {
               var3.add(var10);
            }
         }

      }
   }

   private int topsortConflictIds(Collection var1, DependencyGraphTransformationContext var2) {
      ArrayList var3 = new ArrayList(var1.size());
      ConflictIdSorter$RootQueue var4 = new ConflictIdSorter$RootQueue(var1.size() / 2);
      Iterator var5 = var1.iterator();

      ConflictIdSorter$ConflictId var6;
      while(var5.hasNext()) {
         var6 = (ConflictIdSorter$ConflictId)var5.next();
         if (var6.inDegree <= 0) {
            var4.add(var6);
         }
      }

      this.processRoots(var3, var4);
      boolean var9 = var3.size() < var1.size();

      label55:
      while(var3.size() < var1.size()) {
         var6 = null;
         Iterator var7 = var1.iterator();

         while(true) {
            ConflictIdSorter$ConflictId var8;
            do {
               do {
                  if (!var7.hasNext()) {
                     var6.inDegree = 0;
                     var4.add(var6);
                     this.processRoots(var3, var4);
                     continue label55;
                  }

                  var8 = (ConflictIdSorter$ConflictId)var7.next();
               } while(var8.inDegree <= 0);
            } while(var6 != null && var8.minDepth >= var6.minDepth && (var8.minDepth != var6.minDepth || var8.inDegree >= var6.inDegree));

            var6 = var8;
         }
      }

      Object var10 = Collections.emptySet();
      if (var9) {
         var10 = this.findCycles(var1);
      }

      var2.put(TransformationContextKeys.SORTED_CONFLICT_IDS, var3);
      var2.put(TransformationContextKeys.CYCLIC_CONFLICT_IDS, var10);
      return ((Collection)var10).size();
   }

   private void processRoots(List var1, ConflictIdSorter$RootQueue var2) {
      label19:
      while(true) {
         if (!var2.isEmpty()) {
            ConflictIdSorter$ConflictId var3 = var2.remove();
            var1.add(var3.key);
            Iterator var4 = var3.children.iterator();

            while(true) {
               if (!var4.hasNext()) {
                  continue label19;
               }

               ConflictIdSorter$ConflictId var5 = (ConflictIdSorter$ConflictId)var4.next();
               --var5.inDegree;
               if (var5.inDegree == 0) {
                  var2.add(var5);
               }
            }
         }

         return;
      }
   }

   private Collection findCycles(Collection var1) {
      HashSet var2 = new HashSet();
      HashMap var3 = new HashMap(128);
      IdentityHashMap var4 = new IdentityHashMap(var1.size());
      Iterator var5 = var1.iterator();

      while(var5.hasNext()) {
         ConflictIdSorter$ConflictId var6 = (ConflictIdSorter$ConflictId)var5.next();
         this.findCycles(var6, var4, var3, var2);
      }

      return var2;
   }

   private void findCycles(ConflictIdSorter$ConflictId var1, Map var2, Map var3, Collection var4) {
      Integer var5 = (Integer)var3.put(var1.key, var3.size());
      if (var5 != null) {
         var3.put(var1.key, var5);
         HashSet var6 = new HashSet();
         Iterator var7 = var3.entrySet().iterator();

         while(var7.hasNext()) {
            Entry var8 = (Entry)var7.next();
            if ((Integer)var8.getValue() >= var5) {
               var6.add(var8.getKey());
            }
         }

         var4.add(var6);
      } else {
         if (var2.put(var1, Boolean.TRUE) == null) {
            Iterator var9 = var1.children.iterator();

            while(var9.hasNext()) {
               ConflictIdSorter$ConflictId var10 = (ConflictIdSorter$ConflictId)var9.next();
               this.findCycles(var10, var2, var3, var4);
            }
         }

         var3.remove(var1.key);
      }

   }
}
