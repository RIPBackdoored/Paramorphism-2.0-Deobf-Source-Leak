package org.eclipse.aether;

public interface RepositoryCache {
   void put(RepositorySystemSession var1, Object var2, Object var3);

   Object get(RepositorySystemSession var1, Object var2);
}
