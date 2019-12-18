package org.eclipse.aether.util.graph.transformer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.collection.DependencyGraphTransformationContext;
import org.eclipse.aether.collection.DependencyGraphTransformer;
import org.eclipse.aether.graph.DefaultDependencyNode;
import org.eclipse.aether.graph.DependencyNode;

public final class ConflictResolver implements DependencyGraphTransformer {
   public static final String CONFIG_PROP_VERBOSE = "aether.conflictResolver.verbose";
   public static final String NODE_DATA_WINNER = "conflict.winner";
   public static final String NODE_DATA_ORIGINAL_SCOPE = "conflict.originalScope";
   public static final String NODE_DATA_ORIGINAL_OPTIONALITY = "conflict.originalOptionality";
   private final ConflictResolver$VersionSelector versionSelector;
   private final ConflictResolver$ScopeSelector scopeSelector;
   private final ConflictResolver$ScopeDeriver scopeDeriver;
   private final ConflictResolver$OptionalitySelector optionalitySelector;

   public ConflictResolver(ConflictResolver$VersionSelector var1, ConflictResolver$ScopeSelector var2, ConflictResolver$OptionalitySelector var3, ConflictResolver$ScopeDeriver var4) {
      super();
      this.versionSelector = (ConflictResolver$VersionSelector)Objects.requireNonNull(var1, "version selector cannot be null");
      this.scopeSelector = (ConflictResolver$ScopeSelector)Objects.requireNonNull(var2, "scope selector cannot be null");
      this.optionalitySelector = (ConflictResolver$OptionalitySelector)Objects.requireNonNull(var3, "optionality selector cannot be null");
      this.scopeDeriver = (ConflictResolver$ScopeDeriver)Objects.requireNonNull(var4, "scope deriver cannot be null");
   }

   public DependencyNode transformGraph(DependencyNode var1, DependencyGraphTransformationContext var2) throws RepositoryException {
      List var3 = (List)var2.get(TransformationContextKeys.SORTED_CONFLICT_IDS);
      if (var3 == null) {
         ConflictIdSorter var4 = new ConflictIdSorter();
         var4.transformGraph(var1, var2);
         var3 = (List)var2.get(TransformationContextKeys.SORTED_CONFLICT_IDS);
      }

      Map var15 = (Map)var2.get(TransformationContextKeys.STATS);
      long var5 = System.nanoTime();
      Collection var7 = (Collection)var2.get(TransformationContextKeys.CYCLIC_CONFLICT_IDS);
      if (var7 == null) {
         throw new RepositoryException("conflict id cycles have not been identified");
      } else {
         Map var8 = (Map)var2.get(TransformationContextKeys.CONFLICT_IDS);
         if (var8 == null) {
            throw new RepositoryException("conflict groups have not been identified");
         } else {
            HashMap var9 = new HashMap();
            Iterator var10 = var7.iterator();

            while(var10.hasNext()) {
               Collection var11 = (Collection)var10.next();

               Object var14;
               for(Iterator var12 = var11.iterator(); var12.hasNext(); ((Collection)var14).addAll(var11)) {
                  Object var13 = var12.next();
                  var14 = (Collection)var9.get(var13);
                  if (var14 == null) {
                     var14 = new HashSet();
                     var9.put(var13, var14);
                  }
               }
            }

            ConflictResolver$State var16 = new ConflictResolver$State(this, var1, var8, var3.size(), var2);
            Iterator var17 = var3.iterator();

            while(var17.hasNext()) {
               Object var19 = var17.next();
               var16.prepare(var19, (Collection)var9.get(var19));
               this.gatherConflictItems(var1, var16);
               var16.finish();
               if (!var16.items.isEmpty()) {
                  ConflictResolver$ConflictContext var20 = var16.conflictCtx;
                  var16.versionSelector.selectVersion(var20);
                  if (var20.winner == null) {
                     throw new RepositoryException("conflict resolver did not select winner among " + var16.items);
                  }

                  DependencyNode var22 = var20.winner.node;
                  var16.scopeSelector.selectScope(var20);
                  if (var16.verbose) {
                     var22.setData("conflict.originalScope", var22.getDependency().getScope());
                  }

                  var22.setScope(var20.scope);
                  var16.optionalitySelector.selectOptionality(var20);
                  if (var16.verbose) {
                     var22.setData("conflict.originalOptionality", var22.getDependency().isOptional());
                  }

                  var22.setOptional(var20.optional);
                  this.removeLosers(var16);
               }

               var16.winner();
               if (!var17.hasNext() && !var7.isEmpty() && var16.conflictCtx.winner != null) {
                  DependencyNode var21 = var16.conflictCtx.winner.node;
                  var16.prepare(var16, (Collection)null);
                  this.gatherConflictItems(var21, var16);
               }
            }

            if (var15 != null) {
               long var18 = System.nanoTime();
               var15.put("ConflictResolver.totalTime", var18 - var5);
               var15.put("ConflictResolver.conflictItemCount", var16.totalConflictItems);
            }

            return var1;
         }
      }
   }

   private boolean gatherConflictItems(DependencyNode var1, ConflictResolver$State var2) throws RepositoryException {
      Object var3 = var2.conflictIds.get(var1);
      if (var2.currentId.equals(var3)) {
         var2.add(var1);
      } else {
         if (var2.loser(var1, var3)) {
            return false;
         }

         if (var2.push(var1, var3)) {
            Iterator var4 = var1.getChildren().iterator();

            while(var4.hasNext()) {
               DependencyNode var5 = (DependencyNode)var4.next();
               if (!this.gatherConflictItems(var5, var2)) {
                  var4.remove();
               }
            }

            var2.pop();
         }
      }

      return true;
   }

   private void removeLosers(ConflictResolver$State var1) {
      ConflictResolver$ConflictItem var2 = var1.conflictCtx.winner;
      List var3 = null;
      ListIterator var4 = null;
      boolean var5 = false;
      Iterator var6 = var1.items.iterator();

      while(true) {
         while(true) {
            while(true) {
               ConflictResolver$ConflictItem var7;
               do {
                  if (!var6.hasNext()) {
                     return;
                  }

                  var7 = (ConflictResolver$ConflictItem)var6.next();
               } while(var7 == var2);

               if (var7.parent != var3) {
                  var4 = var7.parent.listIterator();
                  var3 = var7.parent;
                  var5 = false;
               }

               while(var4.hasNext()) {
                  DependencyNode var8 = (DependencyNode)var4.next();
                  if (var8 == var7.node) {
                     if (var1.verbose && !var5 && var7.parent != var2.parent) {
                        var5 = true;
                        DefaultDependencyNode var9 = new DefaultDependencyNode(var8);
                        var9.setData("conflict.winner", var2.node);
                        var9.setData("conflict.originalScope", var9.getDependency().getScope());
                        var9.setData("conflict.originalOptionality", var9.getDependency().isOptional());
                        var9.setScope((String)var7.getScopes().iterator().next());
                        var9.setChildren(Collections.emptyList());
                        var4.set(var9);
                        break;
                     }

                     var4.remove();
                     break;
                  }
               }
            }
         }
      }
   }

   static ConflictResolver$VersionSelector access$000(ConflictResolver var0) {
      return var0.versionSelector;
   }

   static ConflictResolver$ScopeSelector access$100(ConflictResolver var0) {
      return var0.scopeSelector;
   }

   static ConflictResolver$ScopeDeriver access$200(ConflictResolver var0) {
      return var0.scopeDeriver;
   }

   static ConflictResolver$OptionalitySelector access$300(ConflictResolver var0) {
      return var0.optionalitySelector;
   }
}
