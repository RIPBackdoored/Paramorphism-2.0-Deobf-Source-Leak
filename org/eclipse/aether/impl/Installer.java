package org.eclipse.aether.impl;

import org.eclipse.aether.*;
import org.eclipse.aether.installation.*;

public interface Installer
{
    InstallResult install(final RepositorySystemSession p0, final InstallRequest p1) throws InstallationException;
}
