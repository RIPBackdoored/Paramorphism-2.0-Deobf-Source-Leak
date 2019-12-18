package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0019\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"},
   d2 = {"Lkotlin/text/RegexOption;", "", "Lkotlin/text/FlagEnum;", "value", "", "mask", "(Ljava/lang/String;III)V", "getMask", "()I", "getValue", "IGNORE_CASE", "MULTILINE", "LITERAL", "UNIX_LINES", "COMMENTS", "DOT_MATCHES_ALL", "CANON_EQ", "kotlin-stdlib"}
)
public final class RegexOption extends Enum implements FlagEnum {
   public static final RegexOption IGNORE_CASE;
   public static final RegexOption MULTILINE;
   public static final RegexOption LITERAL;
   public static final RegexOption UNIX_LINES;
   public static final RegexOption COMMENTS;
   public static final RegexOption DOT_MATCHES_ALL;
   public static final RegexOption CANON_EQ;
   private static final RegexOption[] $VALUES = new RegexOption[]{IGNORE_CASE = new RegexOption("IGNORE_CASE", 0, 2, 0, 2, (DefaultConstructorMarker)null), MULTILINE = new RegexOption("MULTILINE", 1, 8, 0, 2, (DefaultConstructorMarker)null), LITERAL = new RegexOption("LITERAL", 2, 16, 0, 2, (DefaultConstructorMarker)null), UNIX_LINES = new RegexOption("UNIX_LINES", 3, 1, 0, 2, (DefaultConstructorMarker)null), COMMENTS = new RegexOption("COMMENTS", 4, 4, 0, 2, (DefaultConstructorMarker)null), DOT_MATCHES_ALL = new RegexOption("DOT_MATCHES_ALL", 5, 32, 0, 2, (DefaultConstructorMarker)null), CANON_EQ = new RegexOption("CANON_EQ", 6, 128, 0, 2, (DefaultConstructorMarker)null)};
   private final int value;
   private final int mask;

   public int getValue() {
      return this.value;
   }

   public int getMask() {
      return this.mask;
   }

   private RegexOption(String var1, int var2, int var3, int var4) {
      super(var1, var2);
      this.value = var3;
      this.mask = var4;
   }

   RegexOption(String var1, int var2, int var3, int var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 2) != 0) {
         var4 = var3;
      }

      this(var1, var2, var3, var4);
   }

   public static RegexOption[] values() {
      return (RegexOption[])$VALUES.clone();
   }

   public static RegexOption valueOf(String var0) {
      return (RegexOption)Enum.valueOf(RegexOption.class, var0);
   }
}
