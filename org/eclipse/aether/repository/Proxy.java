package org.eclipse.aether.repository;

import java.util.Objects;

public final class Proxy {
   public static final String TYPE_HTTP = "http";
   public static final String TYPE_HTTPS = "https";
   private final String type;
   private final String host;
   private final int port;
   private final Authentication auth;

   public Proxy(String var1, String var2, int var3) {
      this(var1, var2, var3, (Authentication)null);
   }

   public Proxy(String var1, String var2, int var3, Authentication var4) {
      super();
      this.type = var1 != null ? var1 : "";
      this.host = var2 != null ? var2 : "";
      this.port = var3;
      this.auth = var4;
   }

   public String getType() {
      return this.type;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public Authentication getAuthentication() {
      return this.auth;
   }

   public String toString() {
      return this.getHost() + ':' + this.getPort();
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         Proxy var2 = (Proxy)var1;
         return Objects.equals(this.type, var2.type) && Objects.equals(this.host, var2.host) && this.port == var2.port && Objects.equals(this.auth, var2.auth);
      } else {
         return false;
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + hash(this.host);
      var2 = var2 * 31 + hash(this.type);
      var2 = var2 * 31 + this.port;
      var2 = var2 * 31 + hash(this.auth);
      return var2;
   }

   private static int hash(Object var0) {
      return var0 != null ? var0.hashCode() : 0;
   }
}
