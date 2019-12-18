package org.eclipse.aether.internal.impl;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.graph.DependencyVisitor;
import org.eclipse.aether.resolution.ArtifactRequest;

class ArtifactRequestBuilder implements DependencyVisitor {
   private final RequestTrace trace;
   private List requests;

   ArtifactRequestBuilder(RequestTrace var1) {
      super();
      this.trace = var1;
      this.requests = new ArrayList();
   }

   public List getRequests() {
      return this.requests;
   }

   public boolean visitEnter(DependencyNode var1) {
      if (var1.getDependency() != null) {
         ArtifactRequest var2 = new ArtifactRequest(var1);
         var2.setTrace(this.trace);
         this.requests.add(var2);
      }

      return true;
   }

   public boolean visitLeave(DependencyNode var1) {
      return true;
   }
}
