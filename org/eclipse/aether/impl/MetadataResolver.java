package org.eclipse.aether.impl;

import java.util.Collection;
import java.util.List;
import org.eclipse.aether.RepositorySystemSession;

public interface MetadataResolver {
   List resolveMetadata(RepositorySystemSession var1, Collection var2);
}
