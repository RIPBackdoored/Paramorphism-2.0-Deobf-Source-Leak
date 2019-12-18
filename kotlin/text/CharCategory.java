package kotlin.text;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0002\b \b\u0086\u0001\u0018\u0000 -2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001-B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,¨\u0006."},
   d2 = {"Lkotlin/text/CharCategory;", "", "value", "", "code", "", "(Ljava/lang/String;IILjava/lang/String;)V", "getCode", "()Ljava/lang/String;", "getValue", "()I", "contains", "", "char", "", "UNASSIGNED", "UPPERCASE_LETTER", "LOWERCASE_LETTER", "TITLECASE_LETTER", "MODIFIER_LETTER", "OTHER_LETTER", "NON_SPACING_MARK", "ENCLOSING_MARK", "COMBINING_SPACING_MARK", "DECIMAL_DIGIT_NUMBER", "LETTER_NUMBER", "OTHER_NUMBER", "SPACE_SEPARATOR", "LINE_SEPARATOR", "PARAGRAPH_SEPARATOR", "CONTROL", "FORMAT", "PRIVATE_USE", "SURROGATE", "DASH_PUNCTUATION", "START_PUNCTUATION", "END_PUNCTUATION", "CONNECTOR_PUNCTUATION", "OTHER_PUNCTUATION", "MATH_SYMBOL", "CURRENCY_SYMBOL", "MODIFIER_SYMBOL", "OTHER_SYMBOL", "INITIAL_QUOTE_PUNCTUATION", "FINAL_QUOTE_PUNCTUATION", "Companion", "kotlin-stdlib"}
)
public final class CharCategory extends Enum {
   public static final CharCategory UNASSIGNED;
   public static final CharCategory UPPERCASE_LETTER;
   public static final CharCategory LOWERCASE_LETTER;
   public static final CharCategory TITLECASE_LETTER;
   public static final CharCategory MODIFIER_LETTER;
   public static final CharCategory OTHER_LETTER;
   public static final CharCategory NON_SPACING_MARK;
   public static final CharCategory ENCLOSING_MARK;
   public static final CharCategory COMBINING_SPACING_MARK;
   public static final CharCategory DECIMAL_DIGIT_NUMBER;
   public static final CharCategory LETTER_NUMBER;
   public static final CharCategory OTHER_NUMBER;
   public static final CharCategory SPACE_SEPARATOR;
   public static final CharCategory LINE_SEPARATOR;
   public static final CharCategory PARAGRAPH_SEPARATOR;
   public static final CharCategory CONTROL;
   public static final CharCategory FORMAT;
   public static final CharCategory PRIVATE_USE;
   public static final CharCategory SURROGATE;
   public static final CharCategory DASH_PUNCTUATION;
   public static final CharCategory START_PUNCTUATION;
   public static final CharCategory END_PUNCTUATION;
   public static final CharCategory CONNECTOR_PUNCTUATION;
   public static final CharCategory OTHER_PUNCTUATION;
   public static final CharCategory MATH_SYMBOL;
   public static final CharCategory CURRENCY_SYMBOL;
   public static final CharCategory MODIFIER_SYMBOL;
   public static final CharCategory OTHER_SYMBOL;
   public static final CharCategory INITIAL_QUOTE_PUNCTUATION;
   public static final CharCategory FINAL_QUOTE_PUNCTUATION;
   private static final CharCategory[] $VALUES = new CharCategory[]{UNASSIGNED = new CharCategory("UNASSIGNED", 0, 0, "Cn"), UPPERCASE_LETTER = new CharCategory("UPPERCASE_LETTER", 1, 1, "Lu"), LOWERCASE_LETTER = new CharCategory("LOWERCASE_LETTER", 2, 2, "Ll"), TITLECASE_LETTER = new CharCategory("TITLECASE_LETTER", 3, 3, "Lt"), MODIFIER_LETTER = new CharCategory("MODIFIER_LETTER", 4, 4, "Lm"), OTHER_LETTER = new CharCategory("OTHER_LETTER", 5, 5, "Lo"), NON_SPACING_MARK = new CharCategory("NON_SPACING_MARK", 6, 6, "Mn"), ENCLOSING_MARK = new CharCategory("ENCLOSING_MARK", 7, 7, "Me"), COMBINING_SPACING_MARK = new CharCategory("COMBINING_SPACING_MARK", 8, 8, "Mc"), DECIMAL_DIGIT_NUMBER = new CharCategory("DECIMAL_DIGIT_NUMBER", 9, 9, "Nd"), LETTER_NUMBER = new CharCategory("LETTER_NUMBER", 10, 10, "Nl"), OTHER_NUMBER = new CharCategory("OTHER_NUMBER", 11, 11, "No"), SPACE_SEPARATOR = new CharCategory("SPACE_SEPARATOR", 12, 12, "Zs"), LINE_SEPARATOR = new CharCategory("LINE_SEPARATOR", 13, 13, "Zl"), PARAGRAPH_SEPARATOR = new CharCategory("PARAGRAPH_SEPARATOR", 14, 14, "Zp"), CONTROL = new CharCategory("CONTROL", 15, 15, "Cc"), FORMAT = new CharCategory("FORMAT", 16, 16, "Cf"), PRIVATE_USE = new CharCategory("PRIVATE_USE", 17, 18, "Co"), SURROGATE = new CharCategory("SURROGATE", 18, 19, "Cs"), DASH_PUNCTUATION = new CharCategory("DASH_PUNCTUATION", 19, 20, "Pd"), START_PUNCTUATION = new CharCategory("START_PUNCTUATION", 20, 21, "Ps"), END_PUNCTUATION = new CharCategory("END_PUNCTUATION", 21, 22, "Pe"), CONNECTOR_PUNCTUATION = new CharCategory("CONNECTOR_PUNCTUATION", 22, 23, "Pc"), OTHER_PUNCTUATION = new CharCategory("OTHER_PUNCTUATION", 23, 24, "Po"), MATH_SYMBOL = new CharCategory("MATH_SYMBOL", 24, 25, "Sm"), CURRENCY_SYMBOL = new CharCategory("CURRENCY_SYMBOL", 25, 26, "Sc"), MODIFIER_SYMBOL = new CharCategory("MODIFIER_SYMBOL", 26, 27, "Sk"), OTHER_SYMBOL = new CharCategory("OTHER_SYMBOL", 27, 28, "So"), INITIAL_QUOTE_PUNCTUATION = new CharCategory("INITIAL_QUOTE_PUNCTUATION", 28, 29, "Pi"), FINAL_QUOTE_PUNCTUATION = new CharCategory("FINAL_QUOTE_PUNCTUATION", 29, 30, "Pf")};
   private final int value;
   @NotNull
   private final String code;
   private static final Lazy categoryMap$delegate = LazyKt.lazy((Function0)CharCategory$Companion$categoryMap$2.INSTANCE);
   public static final CharCategory$Companion Companion = new CharCategory$Companion((DefaultConstructorMarker)null);

   public final boolean contains(char var1) {
      return Character.getType(var1) == this.value;
   }

   public final int getValue() {
      return this.value;
   }

   @NotNull
   public final String getCode() {
      return this.code;
   }

   private CharCategory(String var1, int var2, int var3, String var4) {
      super(var1, var2);
      this.value = var3;
      this.code = var4;
   }

   public static final Lazy access$getCategoryMap$cp() {
      return categoryMap$delegate;
   }

   public static CharCategory[] values() {
      return (CharCategory[])$VALUES.clone();
   }

   public static CharCategory valueOf(String var0) {
      return (CharCategory)Enum.valueOf(CharCategory.class, var0);
   }
}
