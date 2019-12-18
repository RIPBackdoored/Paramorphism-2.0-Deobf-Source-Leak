package org.eclipse.aether.repository;

import org.eclipse.aether.artifact.*;
import java.io.*;
import java.util.*;

public interface WorkspaceReader
{
    WorkspaceRepository getRepository();
    
    File findArtifact(final Artifact p0);
    
    List<String> findVersions(final Artifact p0);
}
