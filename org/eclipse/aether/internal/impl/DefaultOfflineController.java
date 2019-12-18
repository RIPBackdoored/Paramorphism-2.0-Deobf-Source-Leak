package org.eclipse.aether.internal.impl;

import java.util.regex.Pattern;
import javax.inject.Named;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.impl.OfflineController;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.transfer.RepositoryOfflineException;
import org.eclipse.aether.util.ConfigUtils;

@Named
public class DefaultOfflineController implements OfflineController {
   static final String CONFIG_PROP_OFFLINE_PROTOCOLS = "aether.offline.protocols";
   static final String CONFIG_PROP_OFFLINE_HOSTS = "aether.offline.hosts";
   private static final Pattern SEP = Pattern.compile("\\s*,\\s*");

   public DefaultOfflineController() {
      super();
   }

   public void checkOffline(RepositorySystemSession var1, RemoteRepository var2) throws RepositoryOfflineException {
      if (!this.isOfflineProtocol(var1, var2) && !this.isOfflineHost(var1, var2)) {
         throw new RepositoryOfflineException(var2);
      }
   }

   private boolean isOfflineProtocol(RepositorySystemSession var1, RemoteRepository var2) {
      String[] var3 = this.getConfig(var1, "aether.offline.protocols");
      if (var3 != null) {
         String var4 = var2.getProtocol();
         if (var4.length() > 0) {
            String[] var5 = var3;
            int var6 = var3.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String var8 = var5[var7];
               if (var8.equalsIgnoreCase(var4)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private boolean isOfflineHost(RepositorySystemSession var1, RemoteRepository var2) {
      String[] var3 = this.getConfig(var1, "aether.offline.hosts");
      if (var3 != null) {
         String var4 = var2.getHost();
         if (var4.length() > 0) {
            String[] var5 = var3;
            int var6 = var3.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String var8 = var5[var7];
               if (var8.equalsIgnoreCase(var4)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private String[] getConfig(RepositorySystemSession var1, String var2) {
      String var3 = ConfigUtils.getString(var1, "", var2).trim();
      return var3.length() <= 0 ? null : SEP.split(var3);
   }
}
