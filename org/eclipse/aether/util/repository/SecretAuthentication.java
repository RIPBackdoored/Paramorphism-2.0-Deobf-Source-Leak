package org.eclipse.aether.util.repository;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import org.eclipse.aether.repository.Authentication;
import org.eclipse.aether.repository.AuthenticationContext;
import org.eclipse.aether.repository.AuthenticationDigest;

final class SecretAuthentication implements Authentication {
   private static final Object[] KEYS = new Object[16];
   private final String key;
   private final char[] value;
   private final int secretHash;

   SecretAuthentication(String var1, String var2) {
      this(var2 != null ? var2.toCharArray() : null, var1);
   }

   SecretAuthentication(String var1, char[] var2) {
      this(copy(var2), var1);
   }

   private SecretAuthentication(char[] var1, String var2) {
      super();
      this.key = (String)Objects.requireNonNull(var2, "authentication key cannot be null");
      if (var2.length() == 0) {
         throw new IllegalArgumentException("authentication key cannot be empty");
      } else {
         this.secretHash = Arrays.hashCode(var1) ^ KEYS[0].hashCode();
         this.value = this.xor(var1);
      }
   }

   private static char[] copy(char[] var0) {
      return var0 != null ? (char[])var0.clone() : null;
   }

   private char[] xor(char[] var1) {
      if (var1 != null) {
         int var2 = System.identityHashCode(this);

         for(int var3 = 0; var3 < var1.length; ++var3) {
            int var4 = KEYS[(var3 >> 1) % KEYS.length].hashCode();
            var4 ^= var2;
            var1[var3] = (char)(var1[var3] ^ ((var3 & 1) == 0 ? var4 & '\uffff' : var4 >>> 16));
         }
      }

      return var1;
   }

   private static void clear(char[] var0) {
      if (var0 != null) {
         for(int var1 = 0; var1 < var0.length; ++var1) {
            var0[var1] = 0;
         }
      }

   }

   public void fill(AuthenticationContext var1, String var2, Map var3) {
      char[] var4 = copy(this.value);
      this.xor(var4);
      var1.put(this.key, var4);
   }

   public void digest(AuthenticationDigest var1) {
      char[] var2 = copy(this.value);
      boolean var5 = false;

      try {
         var5 = true;
         this.xor(var2);
         var1.update(this.key);
         var1.update(var2);
         var5 = false;
      } finally {
         if (var5) {
            clear(var2);
         }
      }

      clear(var2);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         SecretAuthentication var2 = (SecretAuthentication)var1;
         if (Objects.equals(this.key, var2.key) && this.secretHash == var2.secretHash) {
            char[] var3 = copy(this.value);
            char[] var4 = copy(var2.value);
            boolean var8 = false;

            boolean var5;
            try {
               var8 = true;
               this.xor(var3);
               var2.xor(var4);
               var5 = Arrays.equals(var3, var4);
               var8 = false;
            } finally {
               if (var8) {
                  clear(var3);
                  clear(var4);
               }
            }

            clear(var3);
            clear(var4);
            return var5;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + this.key.hashCode();
      var2 = var2 * 31 + this.secretHash;
      return var2;
   }

   public String toString() {
      return this.key + "=" + (this.value != null ? "***" : "null");
   }

   static {
      for(int var0 = 0; var0 < KEYS.length; ++var0) {
         KEYS[var0] = new Object();
      }

   }
}
