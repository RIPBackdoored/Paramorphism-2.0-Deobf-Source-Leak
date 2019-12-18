package org.eclipse.aether.repository;

import java.util.Map;

public interface Authentication {
   void fill(AuthenticationContext var1, String var2, Map var3);

   void digest(AuthenticationDigest var1);
}
