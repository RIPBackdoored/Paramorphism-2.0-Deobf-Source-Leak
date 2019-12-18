package org.eclipse.aether.impl;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.deployment.DeployRequest;
import org.eclipse.aether.deployment.DeployResult;
import org.eclipse.aether.deployment.DeploymentException;

public interface Deployer {
   DeployResult deploy(RepositorySystemSession var1, DeployRequest var2) throws DeploymentException;
}
