package kotlin.text;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0019\b\u0086\u0001\u0018\u0000 \u001b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001a¨\u0006\u001c"},
   d2 = {"Lkotlin/text/CharDirectionality;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "UNDEFINED", "LEFT_TO_RIGHT", "RIGHT_TO_LEFT", "RIGHT_TO_LEFT_ARABIC", "EUROPEAN_NUMBER", "EUROPEAN_NUMBER_SEPARATOR", "EUROPEAN_NUMBER_TERMINATOR", "ARABIC_NUMBER", "COMMON_NUMBER_SEPARATOR", "NONSPACING_MARK", "BOUNDARY_NEUTRAL", "PARAGRAPH_SEPARATOR", "SEGMENT_SEPARATOR", "WHITESPACE", "OTHER_NEUTRALS", "LEFT_TO_RIGHT_EMBEDDING", "LEFT_TO_RIGHT_OVERRIDE", "RIGHT_TO_LEFT_EMBEDDING", "RIGHT_TO_LEFT_OVERRIDE", "POP_DIRECTIONAL_FORMAT", "Companion", "kotlin-stdlib"}
)
public final class CharDirectionality extends Enum {
   public static final CharDirectionality UNDEFINED;
   public static final CharDirectionality LEFT_TO_RIGHT;
   public static final CharDirectionality RIGHT_TO_LEFT;
   public static final CharDirectionality RIGHT_TO_LEFT_ARABIC;
   public static final CharDirectionality EUROPEAN_NUMBER;
   public static final CharDirectionality EUROPEAN_NUMBER_SEPARATOR;
   public static final CharDirectionality EUROPEAN_NUMBER_TERMINATOR;
   public static final CharDirectionality ARABIC_NUMBER;
   public static final CharDirectionality COMMON_NUMBER_SEPARATOR;
   public static final CharDirectionality NONSPACING_MARK;
   public static final CharDirectionality BOUNDARY_NEUTRAL;
   public static final CharDirectionality PARAGRAPH_SEPARATOR;
   public static final CharDirectionality SEGMENT_SEPARATOR;
   public static final CharDirectionality WHITESPACE;
   public static final CharDirectionality OTHER_NEUTRALS;
   public static final CharDirectionality LEFT_TO_RIGHT_EMBEDDING;
   public static final CharDirectionality LEFT_TO_RIGHT_OVERRIDE;
   public static final CharDirectionality RIGHT_TO_LEFT_EMBEDDING;
   public static final CharDirectionality RIGHT_TO_LEFT_OVERRIDE;
   public static final CharDirectionality POP_DIRECTIONAL_FORMAT;
   private static final CharDirectionality[] $VALUES = new CharDirectionality[]{UNDEFINED = new CharDirectionality("UNDEFINED", 0, -1), LEFT_TO_RIGHT = new CharDirectionality("LEFT_TO_RIGHT", 1, 0), RIGHT_TO_LEFT = new CharDirectionality("RIGHT_TO_LEFT", 2, 1), RIGHT_TO_LEFT_ARABIC = new CharDirectionality("RIGHT_TO_LEFT_ARABIC", 3, 2), EUROPEAN_NUMBER = new CharDirectionality("EUROPEAN_NUMBER", 4, 3), EUROPEAN_NUMBER_SEPARATOR = new CharDirectionality("EUROPEAN_NUMBER_SEPARATOR", 5, 4), EUROPEAN_NUMBER_TERMINATOR = new CharDirectionality("EUROPEAN_NUMBER_TERMINATOR", 6, 5), ARABIC_NUMBER = new CharDirectionality("ARABIC_NUMBER", 7, 6), COMMON_NUMBER_SEPARATOR = new CharDirectionality("COMMON_NUMBER_SEPARATOR", 8, 7), NONSPACING_MARK = new CharDirectionality("NONSPACING_MARK", 9, 8), BOUNDARY_NEUTRAL = new CharDirectionality("BOUNDARY_NEUTRAL", 10, 9), PARAGRAPH_SEPARATOR = new CharDirectionality("PARAGRAPH_SEPARATOR", 11, 10), SEGMENT_SEPARATOR = new CharDirectionality("SEGMENT_SEPARATOR", 12, 11), WHITESPACE = new CharDirectionality("WHITESPACE", 13, 12), OTHER_NEUTRALS = new CharDirectionality("OTHER_NEUTRALS", 14, 13), LEFT_TO_RIGHT_EMBEDDING = new CharDirectionality("LEFT_TO_RIGHT_EMBEDDING", 15, 14), LEFT_TO_RIGHT_OVERRIDE = new CharDirectionality("LEFT_TO_RIGHT_OVERRIDE", 16, 15), RIGHT_TO_LEFT_EMBEDDING = new CharDirectionality("RIGHT_TO_LEFT_EMBEDDING", 17, 16), RIGHT_TO_LEFT_OVERRIDE = new CharDirectionality("RIGHT_TO_LEFT_OVERRIDE", 18, 17), POP_DIRECTIONAL_FORMAT = new CharDirectionality("POP_DIRECTIONAL_FORMAT", 19, 18)};
   private final int value;
   private static final Lazy directionalityMap$delegate = LazyKt.lazy((Function0)CharDirectionality$Companion$directionalityMap$2.INSTANCE);
   public static final CharDirectionality$Companion Companion = new CharDirectionality$Companion((DefaultConstructorMarker)null);

   public final int getValue() {
      return this.value;
   }

   private CharDirectionality(String var1, int var2, int var3) {
      super(var1, var2);
      this.value = var3;
   }

   public static final Lazy access$getDirectionalityMap$cp() {
      return directionalityMap$delegate;
   }

   public static CharDirectionality[] values() {
      return (CharDirectionality[])$VALUES.clone();
   }

   public static CharDirectionality valueOf(String var0) {
      return (CharDirectionality)Enum.valueOf(CharDirectionality.class, var0);
   }
}
