package org.eclipse.aether.artifact;

import java.util.Map;

public interface ArtifactType {
   String getId();

   String getExtension();

   String getClassifier();

   Map getProperties();
}
