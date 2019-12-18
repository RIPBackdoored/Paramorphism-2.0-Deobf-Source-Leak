package paramorphism-obfuscator;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.streams.jdk8.StreamsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.resources.BufferResource;
import site.hackery.paramorphism.api.resources.Resource;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class tc implements ResourceSet {
   private final ZipFile bhu;

   @Nullable
   public Resource get(@NotNull String var1) {
      ZipEntry var2 = this.bhu.getEntry(var1);
      if (var2 != null) {
         Closeable var3 = (Closeable)this.bhu.getInputStream(var2);
         boolean var4 = false;
         Throwable var5 = (Throwable)null;
         boolean var17 = false;

         byte[] var20;
         try {
            var17 = true;
            InputStream var6 = (InputStream)var3;
            boolean var7 = false;
            var20 = ByteStreamsKt.readBytes(var6);
            var17 = false;
         } catch (Throwable var18) {
            var5 = var18;
            throw var18;
         } finally {
            if (var17) {
               CloseableKt.closeFinally(var3, var5);
            }
         }

         CloseableKt.closeFinally(var3, var5);
         Object var11 = null;
         byte var12 = 2;
         byte var13 = 0;
         return (Resource)(new BufferResource(var20, var13, var12, (DefaultConstructorMarker)var11));
      } else {
         return null;
      }
   }

   public void put(@NotNull String var1, @Nullable Resource var2) {
      throw (Throwable)(new UnsupportedOperationException());
   }

   public boolean contains(@NotNull String var1) {
      ZipEntry var2 = this.bhu.getEntry(var1);
      return var2 != null;
   }

   @NotNull
   public Sequence all() {
      return SequencesKt.map(SequencesKt.filter(SequencesKt.map(StreamsKt.asSequence(this.bhu.stream()), (Function1)(new th(this))), (Function1)tg.bhy), (Function1)tf.bhx);
   }

   @NotNull
   public Sequence names() {
      return SequencesKt.map(StreamsKt.asSequence(this.bhu.stream()), (Function1)td.bhv);
   }

   public void close() {
      this.bhu.close();
   }

   public tc(@NotNull File var1) {
      super();
      this.bhu = new ZipFile(var1);
   }

   public static final ZipFile a(tc var0) {
      return var0.bhu;
   }
}
