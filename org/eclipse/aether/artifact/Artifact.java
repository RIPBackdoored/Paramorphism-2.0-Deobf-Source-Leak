package org.eclipse.aether.artifact;

import java.io.File;
import java.util.Map;

public interface Artifact {
   String getGroupId();

   String getArtifactId();

   String getVersion();

   Artifact setVersion(String var1);

   String getBaseVersion();

   boolean isSnapshot();

   String getClassifier();

   String getExtension();

   File getFile();

   Artifact setFile(File var1);

   String getProperty(String var1, String var2);

   Map getProperties();

   Artifact setProperties(Map var1);
}
