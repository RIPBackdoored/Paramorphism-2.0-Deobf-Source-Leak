package org.eclipse.aether.internal.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.spi.connector.layout.RepositoryLayout;
import org.eclipse.aether.spi.connector.layout.RepositoryLayout$Checksum;

class Maven2RepositoryLayoutFactory$Maven2RepositoryLayout implements RepositoryLayout {
   public static final RepositoryLayout INSTANCE = new Maven2RepositoryLayoutFactory$Maven2RepositoryLayout();

   private Maven2RepositoryLayoutFactory$Maven2RepositoryLayout() {
      super();
   }

   private URI toUri(String var1) {
      URI var10000;
      try {
         var10000 = new URI((String)null, (String)null, var1, (String)null);
      } catch (URISyntaxException var3) {
         throw new IllegalStateException(var3);
      }

      return var10000;
   }

   public URI getLocation(Artifact var1, boolean var2) {
      StringBuilder var3 = new StringBuilder(128);
      var3.append(var1.getGroupId().replace('.', '/')).append('/');
      var3.append(var1.getArtifactId()).append('/');
      var3.append(var1.getBaseVersion()).append('/');
      var3.append(var1.getArtifactId()).append('-').append(var1.getVersion());
      if (var1.getClassifier().length() > 0) {
         var3.append('-').append(var1.getClassifier());
      }

      if (var1.getExtension().length() > 0) {
         var3.append('.').append(var1.getExtension());
      }

      return this.toUri(var3.toString());
   }

   public URI getLocation(Metadata var1, boolean var2) {
      StringBuilder var3 = new StringBuilder(128);
      if (var1.getGroupId().length() > 0) {
         var3.append(var1.getGroupId().replace('.', '/')).append('/');
         if (var1.getArtifactId().length() > 0) {
            var3.append(var1.getArtifactId()).append('/');
            if (var1.getVersion().length() > 0) {
               var3.append(var1.getVersion()).append('/');
            }
         }
      }

      var3.append(var1.getType());
      return this.toUri(var3.toString());
   }

   public List getChecksums(Artifact var1, boolean var2, URI var3) {
      return this.getChecksums(var3);
   }

   public List getChecksums(Metadata var1, boolean var2, URI var3) {
      return this.getChecksums(var3);
   }

   private List getChecksums(URI var1) {
      return Arrays.asList(RepositoryLayout$Checksum.forLocation(var1, "SHA-1"), RepositoryLayout$Checksum.forLocation(var1, "MD5"));
   }

   Maven2RepositoryLayoutFactory$Maven2RepositoryLayout(Maven2RepositoryLayoutFactory$1 var1) {
      this();
   }
}
