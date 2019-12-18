package org.eclipse.aether.repository;

import java.io.Closeable;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.eclipse.aether.RepositorySystemSession;

public final class AuthenticationContext implements Closeable {
   public static final String USERNAME = "username";
   public static final String PASSWORD = "password";
   public static final String NTLM_DOMAIN = "ntlm.domain";
   public static final String NTLM_WORKSTATION = "ntlm.workstation";
   public static final String PRIVATE_KEY_PATH = "privateKey.path";
   public static final String PRIVATE_KEY_PASSPHRASE = "privateKey.passphrase";
   public static final String HOST_KEY_ACCEPTANCE = "hostKey.acceptance";
   public static final String HOST_KEY_REMOTE = "hostKey.remote";
   public static final String HOST_KEY_LOCAL = "hostKey.local";
   public static final String SSL_CONTEXT = "ssl.context";
   public static final String SSL_HOSTNAME_VERIFIER = "ssl.hostnameVerifier";
   private final RepositorySystemSession session;
   private final RemoteRepository repository;
   private final Proxy proxy;
   private final Authentication auth;
   private final Map authData;
   private boolean fillingAuthData;

   public static AuthenticationContext forRepository(RepositorySystemSession var0, RemoteRepository var1) {
      return newInstance(var0, var1, (Proxy)null, var1.getAuthentication());
   }

   public static AuthenticationContext forProxy(RepositorySystemSession var0, RemoteRepository var1) {
      Proxy var2 = var1.getProxy();
      return newInstance(var0, var1, var2, var2 != null ? var2.getAuthentication() : null);
   }

   private static AuthenticationContext newInstance(RepositorySystemSession var0, RemoteRepository var1, Proxy var2, Authentication var3) {
      return var3 == null ? null : new AuthenticationContext(var0, var1, var2, var3);
   }

   private AuthenticationContext(RepositorySystemSession var1, RemoteRepository var2, Proxy var3, Authentication var4) {
      super();
      this.session = (RepositorySystemSession)Objects.requireNonNull(var1, "repository system session cannot be null");
      this.repository = var2;
      this.proxy = var3;
      this.auth = var4;
      this.authData = new HashMap();
   }

   public RepositorySystemSession getSession() {
      return this.session;
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public String get(String var1) {
      return (String)this.get(var1, (Map)null, String.class);
   }

   public Object get(String var1, Class var2) {
      return this.get(var1, (Map)null, var2);
   }

   public Object get(String var1, Map var2, Class var3) {
      Objects.requireNonNull(var1, "authentication key cannot be null");
      if (var1.length() == 0) {
         throw new IllegalArgumentException("authentication key cannot be empty");
      } else {
         Object var4;
         synchronized(this.authData) {
            var4 = this.authData.get(var1);
            if (var4 == null && !this.authData.containsKey(var1) && !this.fillingAuthData) {
               if (this.auth != null) {
                  boolean var10 = false;

                  try {
                     var10 = true;
                     this.fillingAuthData = true;
                     this.auth.fill(this, var1, var2);
                     var10 = false;
                  } finally {
                     if (var10) {
                        this.fillingAuthData = false;
                     }
                  }

                  this.fillingAuthData = false;
                  var4 = this.authData.get(var1);
               }

               if (var4 == null) {
                  this.authData.put(var1, var4);
               }
            }
         }

         return this.convert(var4, var3);
      }
   }

   private Object convert(Object var1, Class var2) {
      if (!var2.isInstance(var1)) {
         if (String.class.equals(var2)) {
            if (var1 instanceof File) {
               var1 = ((File)var1).getPath();
            } else if (var1 instanceof char[]) {
               var1 = new String((char[])((char[])var1));
            }
         } else if (File.class.equals(var2)) {
            if (var1 instanceof String) {
               var1 = new File((String)var1);
            }
         } else if (char[].class.equals(var2) && var1 instanceof String) {
            var1 = ((String)var1).toCharArray();
         }
      }

      return var2.isInstance(var1) ? var2.cast(var1) : null;
   }

   public void put(String var1, Object var2) {
      Objects.requireNonNull(var1, "authentication key cannot be null");
      if (var1.length() == 0) {
         throw new IllegalArgumentException("authentication key cannot be empty");
      } else {
         synchronized(this.authData) {
            Object var4 = this.authData.put(var1, var2);
            if (var4 instanceof char[]) {
               Arrays.fill((char[])((char[])var4), '\u0000');
            }
         }

      }
   }

   public void close() {
      synchronized(this.authData) {
         Iterator var2 = this.authData.values().iterator();

         while(var2.hasNext()) {
            Object var3 = var2.next();
            if (var3 instanceof char[]) {
               Arrays.fill((char[])((char[])var3), '\u0000');
            }
         }

         this.authData.clear();
      }
   }

   public static void close(AuthenticationContext var0) {
      if (var0 != null) {
         var0.close();
      }

   }
}
