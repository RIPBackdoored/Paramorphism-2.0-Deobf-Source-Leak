package org.eclipse.aether.deployment;

import org.eclipse.aether.RepositoryException;

public class DeploymentException extends RepositoryException {
   public DeploymentException(String var1) {
      super(var1);
   }

   public DeploymentException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
