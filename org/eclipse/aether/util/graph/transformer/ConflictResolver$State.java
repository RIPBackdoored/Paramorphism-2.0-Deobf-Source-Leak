package org.eclipse.aether.util.graph.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.collection.DependencyGraphTransformationContext;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.util.ConfigUtils;

final class ConflictResolver$State {
   Object currentId;
   int totalConflictItems;
   final boolean verbose;
   final Map resolvedIds;
   final Collection potentialAncestorIds;
   final Map conflictIds;
   final List items;
   final Map infos;
   final Map stack;
   final List parentNodes;
   final List parentScopes;
   final List parentOptionals;
   final List parentInfos;
   final ConflictResolver$ConflictContext conflictCtx;
   final ConflictResolver$ScopeContext scopeCtx;
   final ConflictResolver$VersionSelector versionSelector;
   final ConflictResolver$ScopeSelector scopeSelector;
   final ConflictResolver$ScopeDeriver scopeDeriver;
   final ConflictResolver$OptionalitySelector optionalitySelector;
   final ConflictResolver this$0;

   ConflictResolver$State(ConflictResolver var1, DependencyNode var2, Map var3, int var4, DependencyGraphTransformationContext var5) throws RepositoryException {
      super();
      this.this$0 = var1;
      this.conflictIds = var3;
      this.verbose = ConfigUtils.getBoolean(var5.getSession(), false, "aether.conflictResolver.verbose");
      this.potentialAncestorIds = new HashSet(var4 * 2);
      this.resolvedIds = new HashMap(var4 * 2);
      this.items = new ArrayList(256);
      this.infos = new IdentityHashMap(64);
      this.stack = new IdentityHashMap(64);
      this.parentNodes = new ArrayList(64);
      this.parentScopes = new ArrayList(64);
      this.parentOptionals = new ArrayList(64);
      this.parentInfos = new ArrayList(64);
      this.conflictCtx = new ConflictResolver$ConflictContext(var2, var3, this.items);
      this.scopeCtx = new ConflictResolver$ScopeContext((String)null, (String)null);
      this.versionSelector = ConflictResolver.access$000(var1).getInstance(var2, var5);
      this.scopeSelector = ConflictResolver.access$100(var1).getInstance(var2, var5);
      this.scopeDeriver = ConflictResolver.access$200(var1).getInstance(var2, var5);
      this.optionalitySelector = ConflictResolver.access$300(var1).getInstance(var2, var5);
   }

   void prepare(Object var1, Collection var2) {
      this.currentId = var1;
      this.conflictCtx.conflictId = var1;
      this.conflictCtx.winner = null;
      this.conflictCtx.scope = null;
      this.conflictCtx.optional = null;
      this.items.clear();
      this.infos.clear();
      if (var2 != null) {
         this.potentialAncestorIds.addAll(var2);
      }

   }

   void finish() {
      List var1 = null;
      int var2 = 0;
      this.totalConflictItems += this.items.size();

      for(int var3 = this.items.size() - 1; var3 >= 0; --var3) {
         ConflictResolver$ConflictItem var4 = (ConflictResolver$ConflictItem)this.items.get(var3);
         if (var4.parent == var1) {
            var4.depth = var2;
         } else if (var4.parent != null) {
            var1 = var4.parent;
            ConflictResolver$NodeInfo var5 = (ConflictResolver$NodeInfo)this.infos.get(var1);
            var2 = var5.minDepth + 1;
            var4.depth = var2;
         }
      }

      this.potentialAncestorIds.add(this.currentId);
   }

   void winner() {
      this.resolvedIds.put(this.currentId, this.conflictCtx.winner != null ? this.conflictCtx.winner.node : null);
   }

   boolean loser(DependencyNode var1, Object var2) {
      DependencyNode var3 = (DependencyNode)this.resolvedIds.get(var2);
      return var3 != null && var3 != var1;
   }

   boolean push(DependencyNode var1, Object var2) throws RepositoryException {
      if (var2 == null) {
         if (var1.getDependency() != null) {
            if (var1.getData().get("conflict.winner") != null) {
               return false;
            }

            throw new RepositoryException("missing conflict id for node " + var1);
         }
      } else if (!this.potentialAncestorIds.contains(var2)) {
         return false;
      }

      List var3 = var1.getChildren();
      if (this.stack.put(var3, Boolean.TRUE) != null) {
         return false;
      } else {
         int var4 = this.depth();
         String var5 = this.deriveScope(var1, var2);
         boolean var6 = this.deriveOptional(var1, var2);
         ConflictResolver$NodeInfo var7 = (ConflictResolver$NodeInfo)this.infos.get(var3);
         if (var7 == null) {
            var7 = new ConflictResolver$NodeInfo(var4, var5, var6);
            this.infos.put(var3, var7);
            this.parentInfos.add(var7);
            this.parentNodes.add(var1);
            this.parentScopes.add(var5);
            this.parentOptionals.add(var6);
         } else {
            int var8 = var7.update(var4, var5, var6);
            if (var8 == 0) {
               this.stack.remove(var3);
               return false;
            }

            this.parentInfos.add((Object)null);
            this.parentNodes.add(var1);
            this.parentScopes.add(var5);
            this.parentOptionals.add(var6);
            if (var7.children != null) {
               int var9;
               ConflictResolver$ConflictItem var10;
               if ((var8 & 1) != 0) {
                  for(var9 = var7.children.size() - 1; var9 >= 0; --var9) {
                     var10 = (ConflictResolver$ConflictItem)var7.children.get(var9);
                     String var11 = this.deriveScope(var10.node, (Object)null);
                     var10.addScope(var11);
                  }
               }

               if ((var8 & 2) != 0) {
                  for(var9 = var7.children.size() - 1; var9 >= 0; --var9) {
                     var10 = (ConflictResolver$ConflictItem)var7.children.get(var9);
                     boolean var12 = this.deriveOptional(var10.node, (Object)null);
                     var10.addOptional(var12);
                  }
               }
            }
         }

         return true;
      }
   }

   void pop() {
      int var1 = this.parentInfos.size() - 1;
      this.parentInfos.remove(var1);
      this.parentScopes.remove(var1);
      this.parentOptionals.remove(var1);
      DependencyNode var2 = (DependencyNode)this.parentNodes.remove(var1);
      this.stack.remove(var2.getChildren());
   }

   void add(DependencyNode var1) throws RepositoryException {
      DependencyNode var2 = this.parent();
      if (var2 == null) {
         ConflictResolver$ConflictItem var3 = this.newConflictItem(var2, var1);
         this.items.add(var3);
      } else {
         ConflictResolver$NodeInfo var5 = (ConflictResolver$NodeInfo)this.parentInfos.get(this.parentInfos.size() - 1);
         if (var5 != null) {
            ConflictResolver$ConflictItem var4 = this.newConflictItem(var2, var1);
            var5.add(var4);
            this.items.add(var4);
         }
      }

   }

   private ConflictResolver$ConflictItem newConflictItem(DependencyNode var1, DependencyNode var2) throws RepositoryException {
      return new ConflictResolver$ConflictItem(var1, var2, this.deriveScope(var2, (Object)null), this.deriveOptional(var2, (Object)null));
   }

   private int depth() {
      return this.parentNodes.size();
   }

   private DependencyNode parent() {
      int var1 = this.parentNodes.size();
      return var1 <= 0 ? null : (DependencyNode)this.parentNodes.get(var1 - 1);
   }

   private String deriveScope(DependencyNode var1, Object var2) throws RepositoryException {
      if ((var1.getManagedBits() & 2) == 0 && (var2 == null || !this.resolvedIds.containsKey(var2))) {
         int var3 = this.parentNodes.size();
         this.scopes(var3, var1.getDependency());
         if (var3 > 0) {
            this.scopeDeriver.deriveScope(this.scopeCtx);
         }

         return this.scopeCtx.derivedScope;
      } else {
         return this.scope(var1.getDependency());
      }
   }

   private void scopes(int var1, Dependency var2) {
      this.scopeCtx.parentScope = var1 > 0 ? (String)this.parentScopes.get(var1 - 1) : null;
      this.scopeCtx.derivedScope = this.scope(var2);
      this.scopeCtx.childScope = this.scope(var2);
   }

   private String scope(Dependency var1) {
      return var1 != null ? var1.getScope() : null;
   }

   private boolean deriveOptional(DependencyNode var1, Object var2) {
      Dependency var3 = var1.getDependency();
      boolean var4 = var3 != null && var3.isOptional();
      if (var4 || (var1.getManagedBits() & 4) != 0 || var2 != null && this.resolvedIds.containsKey(var2)) {
         return var4;
      } else {
         int var5 = this.parentNodes.size();
         return var5 > 0 ? (Boolean)this.parentOptionals.get(var5 - 1) : false;
      }
   }
}
