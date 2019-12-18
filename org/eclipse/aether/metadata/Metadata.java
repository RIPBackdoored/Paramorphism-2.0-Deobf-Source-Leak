package org.eclipse.aether.metadata;

import java.io.File;
import java.util.Map;

public interface Metadata {
   String getGroupId();

   String getArtifactId();

   String getVersion();

   String getType();

   Metadata$Nature getNature();

   File getFile();

   Metadata setFile(File var1);

   String getProperty(String var1, String var2);

   Map getProperties();

   Metadata setProperties(Map var1);
}
