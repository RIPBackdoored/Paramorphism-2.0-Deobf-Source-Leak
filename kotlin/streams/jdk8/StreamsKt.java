package kotlin.streams.jdk8;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmName;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001*\u00020\u0005H\u0007\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u0007H\u0007\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\b0\u0001\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\t\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u0001H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\f*\u00020\u0003H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f*\u00020\u0005H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f*\u00020\u0007H\u0007\u001a\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\b0\f\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\tH\u0007¨\u0006\r"},
   d2 = {"asSequence", "Lkotlin/sequences/Sequence;", "", "Ljava/util/stream/DoubleStream;", "", "Ljava/util/stream/IntStream;", "", "Ljava/util/stream/LongStream;", "T", "Ljava/util/stream/Stream;", "asStream", "toList", "", "kotlin-stdlib-jdk8"},
   pn = "kotlin.streams"
)
@JvmName(
   name = "StreamsKt"
)
public final class StreamsKt {
   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence asSequence(@NotNull Stream var0) {
      boolean var1 = false;
      return (Sequence)(new StreamsKt$asSequence$$inlined$Sequence$1(var0));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence asSequence(@NotNull IntStream var0) {
      boolean var1 = false;
      return (Sequence)(new StreamsKt$asSequence$$inlined$Sequence$2(var0));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence asSequence(@NotNull LongStream var0) {
      boolean var1 = false;
      return (Sequence)(new StreamsKt$asSequence$$inlined$Sequence$3(var0));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence asSequence(@NotNull DoubleStream var0) {
      boolean var1 = false;
      return (Sequence)(new StreamsKt$asSequence$$inlined$Sequence$4(var0));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Stream asStream(@NotNull Sequence var0) {
      return StreamSupport.stream((Supplier)(new StreamsKt$asStream$1(var0)), 16, false);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List toList(@NotNull Stream var0) {
      return (List)var0.collect(Collectors.toList());
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List toList(@NotNull IntStream var0) {
      return ArraysKt.asList(var0.toArray());
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List toList(@NotNull LongStream var0) {
      return ArraysKt.asList(var0.toArray());
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List toList(@NotNull DoubleStream var0) {
      return ArraysKt.asList(var0.toArray());
   }
}
