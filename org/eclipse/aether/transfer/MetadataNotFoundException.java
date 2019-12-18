package org.eclipse.aether.transfer;

import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;

public class MetadataNotFoundException extends MetadataTransferException {
   public MetadataNotFoundException(Metadata var1, LocalRepository var2) {
      super(var1, (RemoteRepository)null, (String)("Could not find metadata " + var1 + getString(" in ", var2)));
   }

   private static String getString(String var0, LocalRepository var1) {
      return var1 == null ? "" : var0 + var1.getId() + " (" + var1.getBasedir() + ")";
   }

   public MetadataNotFoundException(Metadata var1, RemoteRepository var2) {
      super(var1, var2, "Could not find metadata " + var1 + getString(" in ", var2));
   }

   public MetadataNotFoundException(Metadata var1, RemoteRepository var2, String var3) {
      super(var1, var2, var3);
   }

   public MetadataNotFoundException(Metadata var1, RemoteRepository var2, String var3, boolean var4) {
      super(var1, var2, var3, var4);
   }

   public MetadataNotFoundException(Metadata var1, RemoteRepository var2, String var3, Throwable var4) {
      super(var1, var2, var3, var4);
   }
}
