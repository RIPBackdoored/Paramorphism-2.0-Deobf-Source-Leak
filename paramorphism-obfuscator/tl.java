package paramorphism-obfuscator;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.streams.jdk8.StreamsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.resources.LazyResource;
import site.hackery.paramorphism.api.resources.Resource;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class tl implements ResourceSet {
   private final FileSystem bid;

   @Nullable
   public Resource get(@NotNull String var1) {
      Path var2 = this.bid.getPath(var1);
      return !Files.exists(var2, new LinkOption[0]) ? null : (Resource)(new LazyResource((Function0)(new tk(var2)), 0, 2, (DefaultConstructorMarker)null));
   }

   public void put(@NotNull String var1, @Nullable Resource var2) {
      throw (Throwable)(new UnsupportedOperationException());
   }

   public boolean contains(@NotNull String var1) {
      Path var2 = this.bid.getPath(var1);
      return Files.exists(var2, new LinkOption[0]);
   }

   @NotNull
   public Sequence all() {
      return SequencesKt.map(StreamsKt.asSequence(Files.walk(this.bid.getPath("/"))), (Function1)te.bhw);
   }

   @NotNull
   public Sequence names() {
      return SequencesKt.map(StreamsKt.asSequence(Files.walk(this.bid.getPath("/"))), (Function1)ti.bia);
   }

   public void close() {
      this.bid.close();
   }

   public tl(@NotNull FileSystem var1) {
      super();
      this.bid = var1;
   }
}
