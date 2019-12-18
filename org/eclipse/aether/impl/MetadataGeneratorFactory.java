package org.eclipse.aether.impl;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.deployment.DeployRequest;
import org.eclipse.aether.installation.InstallRequest;

public interface MetadataGeneratorFactory {
   MetadataGenerator newInstance(RepositorySystemSession var1, InstallRequest var2);

   MetadataGenerator newInstance(RepositorySystemSession var1, DeployRequest var2);

   float getPriority();
}
