package paramorphism-obfuscator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Iterator;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class ml extends Lambda implements Function0 {
   public final kg vb;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final lg a() {
      MessageDigest var1 = MessageDigest.getInstance("SHA-256");
      var1.reset();
      File var4 = this.vb.i().getInput();
      boolean var5 = false;
      InputStream var7 = (InputStream)(new FileInputStream(var4));
      short var9 = 8192;
      boolean var6 = false;
      Iterator var8 = (Iterator)ByteStreamsKt.iterator(var7 instanceof BufferedInputStream ? (BufferedInputStream)var7 : new BufferedInputStream(var7, var9));
      var5 = false;
      Iterator var3 = var8;

      while(var3.hasNext()) {
         byte var2 = ((Number)var3.next()).byteValue();
         var1.update(var2);
      }

      return new lg((new BigInteger(var1.digest())).longValue());
   }

   public ml(kg var1) {
      super(0);
      this.vb = var1;
   }
}
