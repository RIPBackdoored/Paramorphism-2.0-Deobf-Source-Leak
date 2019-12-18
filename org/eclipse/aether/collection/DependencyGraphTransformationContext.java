package org.eclipse.aether.collection;

import org.eclipse.aether.RepositorySystemSession;

public interface DependencyGraphTransformationContext {
   RepositorySystemSession getSession();

   Object get(Object var1);

   Object put(Object var1, Object var2);
}
