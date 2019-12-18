package org.eclipse.aether.metadata;

import java.io.File;
import java.util.Map;
import java.util.Objects;

public final class DefaultMetadata extends AbstractMetadata {
   private final String groupId;
   private final String artifactId;
   private final String version;
   private final String type;
   private final Metadata$Nature nature;
   private final File file;
   private final Map properties;

   public DefaultMetadata(String var1, Metadata$Nature var2) {
      this("", "", "", var1, var2, (Map)null, (File)((File)null));
   }

   public DefaultMetadata(String var1, String var2, Metadata$Nature var3) {
      this(var1, "", "", var2, var3, (Map)null, (File)((File)null));
   }

   public DefaultMetadata(String var1, String var2, String var3, Metadata$Nature var4) {
      this(var1, var2, "", var3, var4, (Map)null, (File)((File)null));
   }

   public DefaultMetadata(String var1, String var2, String var3, String var4, Metadata$Nature var5) {
      this(var1, var2, var3, var4, var5, (Map)null, (File)((File)null));
   }

   public DefaultMetadata(String var1, String var2, String var3, String var4, Metadata$Nature var5, File var6) {
      this(var1, var2, var3, var4, var5, (Map)null, (File)var6);
   }

   public DefaultMetadata(String var1, String var2, String var3, String var4, Metadata$Nature var5, Map var6, File var7) {
      super();
      this.groupId = emptify(var1);
      this.artifactId = emptify(var2);
      this.version = emptify(var3);
      this.type = emptify(var4);
      this.nature = (Metadata$Nature)Objects.requireNonNull(var5, "metadata nature cannot be null");
      this.file = var7;
      this.properties = copyProperties(var6);
   }

   DefaultMetadata(String var1, String var2, String var3, String var4, Metadata$Nature var5, File var6, Map var7) {
      super();
      this.groupId = emptify(var1);
      this.artifactId = emptify(var2);
      this.version = emptify(var3);
      this.type = emptify(var4);
      this.nature = var5;
      this.file = var6;
      this.properties = var7;
   }

   private static String emptify(String var0) {
      return var0 == null ? "" : var0;
   }

   public String getGroupId() {
      return this.groupId;
   }

   public String getArtifactId() {
      return this.artifactId;
   }

   public String getVersion() {
      return this.version;
   }

   public String getType() {
      return this.type;
   }

   public Metadata$Nature getNature() {
      return this.nature;
   }

   public File getFile() {
      return this.file;
   }

   public Map getProperties() {
      return this.properties;
   }
}
