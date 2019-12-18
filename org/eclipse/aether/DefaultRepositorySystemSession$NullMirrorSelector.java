package org.eclipse.aether;

import org.eclipse.aether.repository.MirrorSelector;
import org.eclipse.aether.repository.RemoteRepository;

class DefaultRepositorySystemSession$NullMirrorSelector implements MirrorSelector {
   public static final MirrorSelector INSTANCE = new DefaultRepositorySystemSession$NullMirrorSelector();

   DefaultRepositorySystemSession$NullMirrorSelector() {
      super();
   }

   public RemoteRepository getMirror(RemoteRepository var1) {
      return null;
   }
}
