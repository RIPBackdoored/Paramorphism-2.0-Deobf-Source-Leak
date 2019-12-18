package kotlin.streams.jdk8;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.sequences.Sequence;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a&\u0012\f\u0012\n \u0003*\u0004\u0018\u0001H\u0002H\u0002 \u0003*\u0012\u0012\f\u0012\n \u0003*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010\u00010\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0002\b\u0004"},
   d2 = {"<anonymous>", "Ljava/util/Spliterator;", "T", "kotlin.jvm.PlatformType", "get"}
)
final class StreamsKt$asStream$1 implements Supplier {
   final Sequence $this_asStream;

   public Object get() {
      return this.get();
   }

   public final Spliterator get() {
      return Spliterators.spliteratorUnknownSize(this.$this_asStream.iterator(), 16);
   }

   StreamsKt$asStream$1(Sequence var1) {
      super();
      this.$this_asStream = var1;
   }
}
