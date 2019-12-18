package org.eclipse.aether.artifact;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class DefaultArtifactType implements ArtifactType {
   private final String id;
   private final String extension;
   private final String classifier;
   private final Map properties;

   public DefaultArtifactType(String var1) {
      this(var1, var1, "", "none", false, false);
   }

   public DefaultArtifactType(String var1, String var2, String var3, String var4) {
      this(var1, var2, var3, var4, true, false);
   }

   public DefaultArtifactType(String var1, String var2, String var3, String var4, boolean var5, boolean var6) {
      super();
      this.id = (String)Objects.requireNonNull(var1, "type id cannot be null");
      if (var1.length() == 0) {
         throw new IllegalArgumentException("type id cannot be empty");
      } else {
         this.extension = emptify(var2);
         this.classifier = emptify(var3);
         HashMap var7 = new HashMap();
         var7.put("type", var1);
         var7.put("language", var4 != null && var4.length() > 0 ? var4 : "none");
         var7.put("includesDependencies", Boolean.toString(var6));
         var7.put("constitutesBuildPath", Boolean.toString(var5));
         this.properties = Collections.unmodifiableMap(var7);
      }
   }

   public DefaultArtifactType(String var1, String var2, String var3, Map var4) {
      super();
      this.id = (String)Objects.requireNonNull(var1, "type id cannot be null");
      if (var1.length() == 0) {
         throw new IllegalArgumentException("type id cannot be empty");
      } else {
         this.extension = emptify(var2);
         this.classifier = emptify(var3);
         this.properties = AbstractArtifact.copyProperties(var4);
      }
   }

   private static String emptify(String var0) {
      return var0 == null ? "" : var0;
   }

   public String getId() {
      return this.id;
   }

   public String getExtension() {
      return this.extension;
   }

   public String getClassifier() {
      return this.classifier;
   }

   public Map getProperties() {
      return this.properties;
   }
}
