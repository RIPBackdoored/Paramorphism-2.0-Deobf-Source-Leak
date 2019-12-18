package org.eclipse.aether.util.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.aether.repository.MirrorSelector;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.RemoteRepository$Builder;

public final class DefaultMirrorSelector implements MirrorSelector {
   private static final String WILDCARD = "*";
   private static final String EXTERNAL_WILDCARD = "external:*";
   private final List mirrors = new ArrayList();

   public DefaultMirrorSelector() {
      super();
   }

   public DefaultMirrorSelector add(String var1, String var2, String var3, boolean var4, String var5, String var6) {
      this.mirrors.add(new DefaultMirrorSelector$MirrorDef(var1, var2, var3, var4, var5, var6));
      return this;
   }

   public RemoteRepository getMirror(RemoteRepository var1) {
      DefaultMirrorSelector$MirrorDef var2 = this.findMirror(var1);
      if (var2 == null) {
         return null;
      } else {
         RemoteRepository$Builder var3 = new RemoteRepository$Builder(var2.id, var1.getContentType(), var2.url);
         var3.setRepositoryManager(var2.repositoryManager);
         if (var2.type != null && var2.type.length() > 0) {
            var3.setContentType(var2.type);
         }

         var3.setSnapshotPolicy(var1.getPolicy(true));
         var3.setReleasePolicy(var1.getPolicy(false));
         var3.setMirroredRepositories(Collections.singletonList(var1));
         return var3.build();
      }
   }

   private DefaultMirrorSelector$MirrorDef findMirror(RemoteRepository var1) {
      String var2 = var1.getId();
      if (var2 != null && !this.mirrors.isEmpty()) {
         Iterator var3 = this.mirrors.iterator();

         DefaultMirrorSelector$MirrorDef var4;
         while(var3.hasNext()) {
            var4 = (DefaultMirrorSelector$MirrorDef)var3.next();
            if (var2.equals(var4.mirrorOfIds) && matchesType(var1.getContentType(), var4.mirrorOfTypes)) {
               return var4;
            }
         }

         var3 = this.mirrors.iterator();

         while(var3.hasNext()) {
            var4 = (DefaultMirrorSelector$MirrorDef)var3.next();
            if (matchPattern(var1, var4.mirrorOfIds) && matchesType(var1.getContentType(), var4.mirrorOfTypes)) {
               return var4;
            }
         }
      }

      return null;
   }

   static boolean matchPattern(RemoteRepository var0, String var1) {
      boolean var2 = false;
      String var3 = var0.getId();
      if (!"*".equals(var1) && !var1.equals(var3)) {
         String[] var4 = var1.split(",");
         String[] var5 = var4;
         int var6 = var4.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String var8 = var5[var7];
            if (var8.length() > 1 && var8.startsWith("!")) {
               if (var8.substring(1).equals(var3)) {
                  var2 = false;
                  break;
               }
            } else {
               if (var8.equals(var3)) {
                  var2 = true;
                  break;
               }

               if ("external:*".equals(var8) && isExternalRepo(var0)) {
                  var2 = true;
               } else if ("*".equals(var8)) {
                  var2 = true;
               }
            }
         }
      } else {
         var2 = true;
      }

      return var2;
   }

   static boolean isExternalRepo(RemoteRepository var0) {
      boolean var1 = "localhost".equals(var0.getHost()) || "127.0.0.1".equals(var0.getHost()) || "file".equalsIgnoreCase(var0.getProtocol());
      return !var1;
   }

   static boolean matchesType(String var0, String var1) {
      boolean var2 = false;
      if (var1 != null && var1.length() > 0 && !"*".equals(var1)) {
         if (var1.equals(var0)) {
            var2 = true;
         } else {
            String[] var3 = var1.split(",");
            String[] var4 = var3;
            int var5 = var3.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String var7 = var4[var6];
               if (var7.length() > 1 && var7.startsWith("!")) {
                  if (var7.substring(1).equals(var0)) {
                     var2 = false;
                     break;
                  }
               } else {
                  if (var7.equals(var0)) {
                     var2 = true;
                     break;
                  }

                  if ("*".equals(var7)) {
                     var2 = true;
                  }
               }
            }
         }
      } else {
         var2 = true;
      }

      return var2;
   }
}
