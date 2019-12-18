package org.eclipse.aether.repository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.eclipse.aether.RepositorySystemSession;

public final class AuthenticationDigest {
   private final MessageDigest digest;
   private final RepositorySystemSession session;
   private final RemoteRepository repository;
   private final Proxy proxy;

   public static String forRepository(RepositorySystemSession var0, RemoteRepository var1) {
      String var2 = "";
      Authentication var3 = var1.getAuthentication();
      if (var3 != null) {
         AuthenticationDigest var4 = new AuthenticationDigest(var0, var1, (Proxy)null);
         var3.digest(var4);
         var2 = var4.digest();
      }

      return var2;
   }

   public static String forProxy(RepositorySystemSession var0, RemoteRepository var1) {
      String var2 = "";
      Proxy var3 = var1.getProxy();
      if (var3 != null) {
         Authentication var4 = var3.getAuthentication();
         if (var4 != null) {
            AuthenticationDigest var5 = new AuthenticationDigest(var0, var1, var3);
            var4.digest(var5);
            var2 = var5.digest();
         }
      }

      return var2;
   }

   private AuthenticationDigest(RepositorySystemSession var1, RemoteRepository var2, Proxy var3) {
      super();
      this.session = var1;
      this.repository = var2;
      this.proxy = var3;
      this.digest = newDigest();
   }

   private static MessageDigest newDigest() {
      MessageDigest var10000;
      try {
         var10000 = MessageDigest.getInstance("SHA-1");
      } catch (NoSuchAlgorithmException var3) {
         try {
            var10000 = MessageDigest.getInstance("MD5");
         } catch (NoSuchAlgorithmException var2) {
            throw new IllegalStateException(var2);
         }

         return var10000;
      }

      return var10000;
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

   public void update(String... var1) {
      if (var1 != null) {
         String[] var2 = var1;
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            if (var5 != null) {
               this.digest.update(var5.getBytes(StandardCharsets.UTF_8));
            }
         }
      }

   }

   public void update(char... var1) {
      if (var1 != null) {
         char[] var2 = var1;
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char var5 = var2[var4];
            this.digest.update((byte)(var5 >> 8));
            this.digest.update((byte)(var5 & 255));
         }
      }

   }

   public void update(byte... var1) {
      if (var1 != null) {
         this.digest.update(var1);
      }

   }

   private String digest() {
      byte[] var1 = this.digest.digest();
      StringBuilder var2 = new StringBuilder(var1.length * 2);
      byte[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         byte var6 = var3[var5];
         int var7 = var6 & 255;
         if (var7 < 16) {
            var2.append('0');
         }

         var2.append(Integer.toHexString(var7));
      }

      return var2.toString();
   }
}
