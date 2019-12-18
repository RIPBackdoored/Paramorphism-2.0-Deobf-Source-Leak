package kotlin.text;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000T\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\u0010\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0005\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u0002\u001a\u001d\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001f\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0005H\u0087\b\u001a\u0012\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u0007\u001a\u001f\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u0003\u001a\u0004\u0018\u00010\bH\u0087\b\u001a\u001f\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u0003\u001a\u0004\u0018\u00010\tH\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\nH\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u000bH\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\fH\u0087\b\u001a\u001f\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u0003\u001a\u0004\u0018\u00010\u0005H\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\rH\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u000eH\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u0010H\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u0011H\u0087\b\u001a\u001f\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u0003\u001a\u0004\u0018\u00010\u0012H\u0087\b\u001a%\u0010\u0000\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u000e\u0010\u0003\u001a\n\u0018\u00010\u0006j\u0004\u0018\u0001`\u0007H\u0087\b\u001a\u0014\u0010\u0013\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u0007H\u0007\u001a!\u0010\u0014\u001a\u00020\u0015*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\n¨\u0006\u0017"},
   d2 = {"appendln", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "value", "", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "Ljava/lang/StringBuffer;", "", "", "", "", "", "", "", "", "", "", "clear", "set", "", "index", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringBuilderJVMKt extends StringsKt__RegexExtensionsKt {
   @InlineOnly
   private static final void set(@NotNull StringBuilder var0, int var1, char var2) {
      byte var3 = 0;
      var0.setCharAt(var1, var2);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final StringBuilder clear(@NotNull StringBuilder var0) {
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      var0.setLength(0);
      return var0;
   }

   @NotNull
   public static final Appendable appendln(@NotNull Appendable var0) {
      return var0.append((CharSequence)SystemProperties.LINE_SEPARATOR);
   }

   @InlineOnly
   private static final Appendable appendln(@NotNull Appendable var0, CharSequence var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final Appendable appendln(@NotNull Appendable var0, char var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @NotNull
   public static final StringBuilder appendln(@NotNull StringBuilder var0) {
      return var0.append(SystemProperties.LINE_SEPARATOR);
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, StringBuffer var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, CharSequence var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, String var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, Object var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, StringBuilder var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append((CharSequence)var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, char[] var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, char var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, boolean var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, int var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, short var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, byte var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, long var1) {
      byte var3 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, float var1) {
      byte var2 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   @InlineOnly
   private static final StringBuilder appendln(@NotNull StringBuilder var0, double var1) {
      byte var3 = 0;
      return StringsKt.appendln(var0.append(var1));
   }

   public StringsKt__StringBuilderJVMKt() {
      super();
   }
}
