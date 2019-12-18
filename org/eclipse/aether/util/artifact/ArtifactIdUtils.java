package org.eclipse.aether.util.artifact;

import java.util.Objects;
import org.eclipse.aether.artifact.Artifact;

public final class ArtifactIdUtils {
   private static final char SEP = ':';

   private ArtifactIdUtils() {
      super();
   }

   public static String toId(Artifact var0) {
      String var1 = null;
      if (var0 != null) {
         var1 = toId(var0.getGroupId(), var0.getArtifactId(), var0.getExtension(), var0.getClassifier(), var0.getVersion());
      }

      return var1;
   }

   public static String toId(String var0, String var1, String var2, String var3, String var4) {
      StringBuilder var5 = concat(var0, var1, var2, var3);
      var5.append(':');
      if (var4 != null) {
         var5.append(var4);
      }

      return var5.toString();
   }

   public static String toBaseId(Artifact var0) {
      String var1 = null;
      if (var0 != null) {
         var1 = toId(var0.getGroupId(), var0.getArtifactId(), var0.getExtension(), var0.getClassifier(), var0.getBaseVersion());
      }

      return var1;
   }

   public static String toVersionlessId(Artifact var0) {
      String var1 = null;
      if (var0 != null) {
         var1 = toVersionlessId(var0.getGroupId(), var0.getArtifactId(), var0.getExtension(), var0.getClassifier());
      }

      return var1;
   }

   public static String toVersionlessId(String var0, String var1, String var2, String var3) {
      return concat(var0, var1, var2, var3).toString();
   }

   private static StringBuilder concat(String var0, String var1, String var2, String var3) {
      StringBuilder var4 = new StringBuilder(128);
      if (var0 != null) {
         var4.append(var0);
      }

      var4.append(':');
      if (var1 != null) {
         var4.append(var1);
      }

      var4.append(':');
      if (var2 != null) {
         var4.append(var2);
      }

      if (var3 != null && var3.length() > 0) {
         var4.append(':').append(var3);
      }

      return var4;
   }

   public static boolean equalsId(Artifact var0, Artifact var1) {
      if (var0 != null && var1 != null) {
         if (!Objects.equals(var0.getArtifactId(), var1.getArtifactId())) {
            return false;
         } else if (!Objects.equals(var0.getGroupId(), var1.getGroupId())) {
            return false;
         } else if (!Objects.equals(var0.getExtension(), var1.getExtension())) {
            return false;
         } else if (!Objects.equals(var0.getClassifier(), var1.getClassifier())) {
            return false;
         } else {
            return Objects.equals(var0.getVersion(), var1.getVersion());
         }
      } else {
         return false;
      }
   }

   public static boolean equalsBaseId(Artifact var0, Artifact var1) {
      if (var0 != null && var1 != null) {
         if (!Objects.equals(var0.getArtifactId(), var1.getArtifactId())) {
            return false;
         } else if (!Objects.equals(var0.getGroupId(), var1.getGroupId())) {
            return false;
         } else if (!Objects.equals(var0.getExtension(), var1.getExtension())) {
            return false;
         } else if (!Objects.equals(var0.getClassifier(), var1.getClassifier())) {
            return false;
         } else {
            return Objects.equals(var0.getBaseVersion(), var1.getBaseVersion());
         }
      } else {
         return false;
      }
   }

   public static boolean equalsVersionlessId(Artifact var0, Artifact var1) {
      if (var0 != null && var1 != null) {
         if (!Objects.equals(var0.getArtifactId(), var1.getArtifactId())) {
            return false;
         } else if (!Objects.equals(var0.getGroupId(), var1.getGroupId())) {
            return false;
         } else if (!Objects.equals(var0.getExtension(), var1.getExtension())) {
            return false;
         } else {
            return Objects.equals(var0.getClassifier(), var1.getClassifier());
         }
      } else {
         return false;
      }
   }
}
