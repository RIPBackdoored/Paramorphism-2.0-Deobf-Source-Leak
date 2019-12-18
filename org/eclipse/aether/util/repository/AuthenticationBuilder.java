package org.eclipse.aether.util.repository;

import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import org.eclipse.aether.repository.Authentication;

public final class AuthenticationBuilder {
   private final List authentications = new ArrayList();

   public AuthenticationBuilder() {
      super();
   }

   public Authentication build() {
      if (this.authentications.isEmpty()) {
         return null;
      } else {
         return (Authentication)(this.authentications.size() == 1 ? (Authentication)this.authentications.get(0) : new ChainedAuthentication(this.authentications));
      }
   }

   public AuthenticationBuilder addUsername(String var1) {
      return this.addString("username", var1);
   }

   public AuthenticationBuilder addPassword(String var1) {
      return this.addSecret("password", var1);
   }

   public AuthenticationBuilder addPassword(char[] var1) {
      return this.addSecret("password", var1);
   }

   public AuthenticationBuilder addNtlm(String var1, String var2) {
      this.addString("ntlm.workstation", var1);
      return this.addString("ntlm.domain", var2);
   }

   public AuthenticationBuilder addPrivateKey(String var1, String var2) {
      if (var1 != null) {
         this.addString("privateKey.path", var1);
         this.addSecret("privateKey.passphrase", var2);
      }

      return this;
   }

   public AuthenticationBuilder addPrivateKey(String var1, char[] var2) {
      if (var1 != null) {
         this.addString("privateKey.path", var1);
         this.addSecret("privateKey.passphrase", var2);
      }

      return this;
   }

   public AuthenticationBuilder addHostnameVerifier(HostnameVerifier var1) {
      if (var1 != null) {
         this.authentications.add(new ComponentAuthentication("ssl.hostnameVerifier", var1));
      }

      return this;
   }

   public AuthenticationBuilder addString(String var1, String var2) {
      if (var2 != null) {
         this.authentications.add(new StringAuthentication(var1, var2));
      }

      return this;
   }

   public AuthenticationBuilder addSecret(String var1, String var2) {
      if (var2 != null) {
         this.authentications.add(new SecretAuthentication(var1, var2));
      }

      return this;
   }

   public AuthenticationBuilder addSecret(String var1, char[] var2) {
      if (var2 != null) {
         this.authentications.add(new SecretAuthentication(var1, var2));
      }

      return this;
   }

   public AuthenticationBuilder addCustom(Authentication var1) {
      if (var1 != null) {
         this.authentications.add(var1);
      }

      return this;
   }
}
