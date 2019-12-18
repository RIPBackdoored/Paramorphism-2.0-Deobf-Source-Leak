package org.eclipse.aether.util.graph.transformer;

import java.util.Iterator;
import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.collection.UnsolvableVersionConflictException;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.util.graph.visitor.PathRecordingDependencyVisitor;
import org.eclipse.aether.version.Version;
import org.eclipse.aether.version.VersionConstraint;

public final class NearestVersionSelector extends ConflictResolver$VersionSelector {
   public NearestVersionSelector() {
      super();
   }

   public void selectVersion(ConflictResolver$ConflictContext var1) throws RepositoryException {
      NearestVersionSelector$ConflictGroup var2 = new NearestVersionSelector$ConflictGroup();
      Iterator var3 = var1.getItems().iterator();

      while(true) {
         while(true) {
            while(var3.hasNext()) {
               ConflictResolver$ConflictItem var4 = (ConflictResolver$ConflictItem)var3.next();
               DependencyNode var5 = var4.getNode();
               VersionConstraint var6 = var5.getVersionConstraint();
               boolean var7 = false;
               boolean var8 = var6.getRange() != null;
               if (var8 && var2.constraints.add(var6) && var2.winner != null && !var6.containsVersion(var2.winner.getNode().getVersion())) {
                  var7 = true;
               }

               if (this.isAcceptable(var2, var5.getVersion())) {
                  var2.candidates.add(var4);
                  if (var7) {
                     this.backtrack(var2, var1);
                  } else if (var2.winner == null || this.isNearer(var4, var2.winner)) {
                     var2.winner = var4;
                  }
               } else if (var7) {
                  this.backtrack(var2, var1);
               }
            }

            var1.setWinner(var2.winner);
            return;
         }
      }
   }

   private void backtrack(NearestVersionSelector$ConflictGroup var1, ConflictResolver$ConflictContext var2) throws UnsolvableVersionConflictException {
      var1.winner = null;
      Iterator var3 = var1.candidates.iterator();

      while(true) {
         while(var3.hasNext()) {
            ConflictResolver$ConflictItem var4 = (ConflictResolver$ConflictItem)var3.next();
            if (!this.isAcceptable(var1, var4.getNode().getVersion())) {
               var3.remove();
            } else if (var1.winner == null || this.isNearer(var4, var1.winner)) {
               var1.winner = var4;
            }
         }

         if (var1.winner == null) {
            throw this.newFailure(var2);
         }

         return;
      }
   }

   private boolean isAcceptable(NearestVersionSelector$ConflictGroup var1, Version var2) {
      Iterator var3 = var1.constraints.iterator();

      VersionConstraint var4;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         var4 = (VersionConstraint)var3.next();
      } while(var4.containsVersion(var2));

      return false;
   }

   private boolean isNearer(ConflictResolver$ConflictItem var1, ConflictResolver$ConflictItem var2) {
      if (var1.isSibling(var2)) {
         return var1.getNode().getVersion().compareTo(var2.getNode().getVersion()) > 0;
      } else {
         return var1.getDepth() < var2.getDepth();
      }
   }

   private UnsolvableVersionConflictException newFailure(ConflictResolver$ConflictContext var1) {
      NearestVersionSelector$1 var2 = new NearestVersionSelector$1(this, var1);
      PathRecordingDependencyVisitor var3 = new PathRecordingDependencyVisitor(var2);
      var1.getRoot().accept(var3);
      return new UnsolvableVersionConflictException(var3.getPaths());
   }
}
