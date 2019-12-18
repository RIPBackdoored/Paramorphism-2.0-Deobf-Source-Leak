package org.eclipse.aether.artifact;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DefaultArtifact extends AbstractArtifact {
   private static final Pattern COORDINATE_PATTERN = Pattern.compile("([^: ]+):([^: ]+)(:([^: ]*)(:([^: ]+))?)?:([^: ]+)");
   private final String groupId;
   private final String artifactId;
   private final String version;
   private final String classifier;
   private final String extension;
   private final File file;
   private final Map properties;

   public DefaultArtifact(String var1) {
      this(var1, Collections.emptyMap());
   }

   public DefaultArtifact(String var1, Map var2) {
      super();
      Matcher var3 = COORDINATE_PATTERN.matcher(var1);
      if (!var3.matches()) {
         throw new IllegalArgumentException("Bad artifact coordinates " + var1 + ", expected format is <groupId>:<artifactId>[:<extension>[:<classifier>]]:<version>");
      } else {
         this.groupId = var3.group(1);
         this.artifactId = var3.group(2);
         this.extension = get(var3.group(4), "jar");
         this.classifier = get(var3.group(6), "");
         this.version = var3.group(7);
         this.file = null;
         this.properties = copyProperties(var2);
      }
   }

   private static String get(String var0, String var1) {
      return var0 != null && var0.length() > 0 ? var0 : var1;
   }

   public DefaultArtifact(String var1, String var2, String var3, String var4) {
      this(var1, var2, "", var3, var4);
   }

   public DefaultArtifact(String var1, String var2, String var3, String var4, String var5) {
      this(var1, var2, var3, var4, var5, (Map)null, (File)((File)null));
   }

   public DefaultArtifact(String var1, String var2, String var3, String var4, String var5, ArtifactType var6) {
      this(var1, var2, var3, var4, var5, (Map)null, (ArtifactType)var6);
   }

   public DefaultArtifact(String var1, String var2, String var3, String var4, String var5, Map var6, ArtifactType var7) {
      super();
      this.groupId = emptify(var1);
      this.artifactId = emptify(var2);
      if (var3 == null && var7 != null) {
         this.classifier = emptify(var7.getClassifier());
      } else {
         this.classifier = emptify(var3);
      }

      if (var4 == null && var7 != null) {
         this.extension = emptify(var7.getExtension());
      } else {
         this.extension = emptify(var4);
      }

      this.version = emptify(var5);
      this.file = null;
      this.properties = merge(var6, var7 != null ? var7.getProperties() : null);
   }

   private static Map merge(Map var0, Map var1) {
      Map var3;
      if ((var0 == null || var0.isEmpty()) && (var1 == null || var1.isEmpty())) {
         var3 = Collections.emptyMap();
      } else {
         HashMap var2 = new HashMap();
         if (var1 != null) {
            var2.putAll(var1);
         }

         if (var0 != null) {
            var2.putAll(var0);
         }

         var3 = Collections.unmodifiableMap(var2);
      }

      return var3;
   }

   public DefaultArtifact(String var1, String var2, String var3, String var4, String var5, Map var6, File var7) {
      super();
      this.groupId = emptify(var1);
      this.artifactId = emptify(var2);
      this.classifier = emptify(var3);
      this.extension = emptify(var4);
      this.version = emptify(var5);
      this.file = var7;
      this.properties = copyProperties(var6);
   }

   DefaultArtifact(String var1, String var2, String var3, String var4, String var5, File var6, Map var7) {
      super();
      this.groupId = emptify(var1);
      this.artifactId = emptify(var2);
      this.classifier = emptify(var3);
      this.extension = emptify(var4);
      this.version = emptify(var5);
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

   public String getClassifier() {
      return this.classifier;
   }

   public String getExtension() {
      return this.extension;
   }

   public File getFile() {
      return this.file;
   }

   public Map getProperties() {
      return this.properties;
   }
}
