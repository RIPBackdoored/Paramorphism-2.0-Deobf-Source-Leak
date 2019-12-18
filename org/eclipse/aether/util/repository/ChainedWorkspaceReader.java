package org.eclipse.aether.util.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.WorkspaceReader;
import org.eclipse.aether.repository.WorkspaceRepository;

public final class ChainedWorkspaceReader implements WorkspaceReader {
   private List readers = new ArrayList();
   private WorkspaceRepository repository;

   public ChainedWorkspaceReader(WorkspaceReader... var1) {
      super();
      if (var1 != null) {
         Collections.addAll(this.readers, var1);
      }

      StringBuilder var2 = new StringBuilder();

      WorkspaceReader var4;
      for(Iterator var3 = this.readers.iterator(); var3.hasNext(); var2.append(var4.getRepository().getContentType())) {
         var4 = (WorkspaceReader)var3.next();
         if (var2.length() > 0) {
            var2.append('+');
         }
      }

      this.repository = new WorkspaceRepository(var2.toString(), new ChainedWorkspaceReader$Key(this.readers));
   }

   public static WorkspaceReader newInstance(WorkspaceReader var0, WorkspaceReader var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (WorkspaceReader)(var1 == null ? var0 : new ChainedWorkspaceReader(new WorkspaceReader[]{var0, var1}));
      }
   }

   public File findArtifact(Artifact var1) {
      File var2 = null;
      Iterator var3 = this.readers.iterator();

      while(var3.hasNext()) {
         WorkspaceReader var4 = (WorkspaceReader)var3.next();
         var2 = var4.findArtifact(var1);
         if (var2 != null) {
            break;
         }
      }

      return var2;
   }

   public List findVersions(Artifact var1) {
      LinkedHashSet var2 = new LinkedHashSet();
      Iterator var3 = this.readers.iterator();

      while(var3.hasNext()) {
         WorkspaceReader var4 = (WorkspaceReader)var3.next();
         var2.addAll(var4.findVersions(var1));
      }

      return Collections.unmodifiableList(new ArrayList(var2));
   }

   public WorkspaceRepository getRepository() {
      ChainedWorkspaceReader$Key var1 = new ChainedWorkspaceReader$Key(this.readers);
      if (!var1.equals(this.repository.getKey())) {
         this.repository = new WorkspaceRepository(this.repository.getContentType(), var1);
      }

      return this.repository;
   }
}
