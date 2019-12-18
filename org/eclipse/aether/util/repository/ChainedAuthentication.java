package org.eclipse.aether.util.repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.eclipse.aether.repository.Authentication;
import org.eclipse.aether.repository.AuthenticationContext;
import org.eclipse.aether.repository.AuthenticationDigest;

final class ChainedAuthentication implements Authentication {
   private final Authentication[] authentications;

   ChainedAuthentication(Authentication... var1) {
      super();
      if (var1 != null && var1.length > 0) {
         this.authentications = (Authentication[])var1.clone();
      } else {
         this.authentications = new Authentication[0];
      }

   }

   ChainedAuthentication(Collection var1) {
      super();
      if (var1 != null && !var1.isEmpty()) {
         this.authentications = (Authentication[])var1.toArray(new Authentication[var1.size()]);
      } else {
         this.authentications = new Authentication[0];
      }

   }

   public void fill(AuthenticationContext var1, String var2, Map var3) {
      Authentication[] var4 = this.authentications;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Authentication var7 = var4[var6];
         var7.fill(var1, var2, var3);
      }

   }

   public void digest(AuthenticationDigest var1) {
      Authentication[] var2 = this.authentications;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Authentication var5 = var2[var4];
         var5.digest(var1);
      }

   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         ChainedAuthentication var2 = (ChainedAuthentication)var1;
         return Arrays.equals(this.authentications, var2.authentications);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.authentications);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(256);
      Authentication[] var2 = this.authentications;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Authentication var5 = var2[var4];
         if (var1.length() > 0) {
            var1.append(", ");
         }

         var1.append(var5);
      }

      return var1.toString();
   }
}
