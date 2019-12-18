package paramorphism-obfuscator;

import java.io.Closeable;
import java.io.InputStream;
import kotlin.Pair;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.BufferResource;

public final class tf extends Lambda implements Function1 {
   public static final tf bhx = new tf();

   public Object invoke(Object var1) {
      return this.a((Pair)var1);
   }

   @NotNull
   public final Pair a(@NotNull Pair var1) {
      String var2 = (String)var1.component1();
      InputStream var3 = (InputStream)var1.component2();
      Closeable var4 = (Closeable)var3;
      boolean var5 = false;
      Throwable var6 = (Throwable)null;
      boolean var17 = false;

      BufferResource var20;
      try {
         var17 = true;
         InputStream var7 = (InputStream)var4;
         boolean var8 = false;
         var20 = new BufferResource(ByteStreamsKt.readBytes(var7), 0, 2, (DefaultConstructorMarker)null);
         var17 = false;
      } catch (Throwable var18) {
         var6 = var18;
         throw var18;
      } finally {
         if (var17) {
            CloseableKt.closeFinally(var4, var6);
         }
      }

      CloseableKt.closeFinally(var4, var6);
      return new Pair(var2, var20);
   }

   public tf() {
      super(1);
   }
}
