package kotlin.text;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000B\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\u001a.\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u001b\u0010\u0004\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\b\tH\u0087\b\u001a&\u0010\u0000\u001a\u00020\u00012\u001b\u0010\u0004\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\b\tH\u0087\b\u001a5\u0010\n\u001a\u0002H\u000b\"\f\b\u0000\u0010\u000b*\u00060\fj\u0002`\r*\u0002H\u000b2\u0016\u0010\u000e\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00100\u000f\"\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011\u001a/\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0016\u0010\u000e\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00120\u000f\"\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0013\u001a/\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0016\u0010\u000e\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000f\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0014\u001a9\u0010\u0015\u001a\u00020\b\"\u0004\b\u0000\u0010\u000b*\u00060\fj\u0002`\r2\u0006\u0010\u0016\u001a\u0002H\u000b2\u0014\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0005H\u0000¢\u0006\u0002\u0010\u0018¨\u0006\u0019"},
   d2 = {"buildString", "", "capacity", "", "builderAction", "Lkotlin/Function1;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "append", "T", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "value", "", "", "(Ljava/lang/Appendable;[Ljava/lang/CharSequence;)Ljava/lang/Appendable;", "", "(Ljava/lang/StringBuilder;[Ljava/lang/Object;)Ljava/lang/StringBuilder;", "(Ljava/lang/StringBuilder;[Ljava/lang/String;)Ljava/lang/StringBuilder;", "appendElement", "element", "transform", "(Ljava/lang/Appendable;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringBuilderKt extends StringsKt__StringBuilderJVMKt {
   @InlineOnly
   private static final String buildString(Function1 var0) {
      byte var1 = 0;
      StringBuilder var2 = new StringBuilder();
      boolean var3 = false;
      boolean var4 = false;
      var0.invoke(var2);
      return var2.toString();
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final String buildString(int var0, Function1 var1) {
      byte var2 = 0;
      StringBuilder var3 = new StringBuilder(var0);
      boolean var4 = false;
      boolean var5 = false;
      var1.invoke(var3);
      return var3.toString();
   }

   @NotNull
   public static final Appendable append(@NotNull Appendable var0, @NotNull CharSequence... var1) {
      CharSequence[] var4 = var1;
      int var5 = var1.length;

      for(int var3 = 0; var3 < var5; ++var3) {
         CharSequence var2 = var4[var3];
         var0.append(var2);
      }

      return var0;
   }

   @NotNull
   public static final StringBuilder append(@NotNull StringBuilder var0, @NotNull String... var1) {
      String[] var4 = var1;
      int var5 = var1.length;

      for(int var3 = 0; var3 < var5; ++var3) {
         String var2 = var4[var3];
         var0.append(var2);
      }

      return var0;
   }

   @NotNull
   public static final StringBuilder append(@NotNull StringBuilder var0, @NotNull Object... var1) {
      Object[] var4 = var1;
      int var5 = var1.length;

      for(int var3 = 0; var3 < var5; ++var3) {
         Object var2 = var4[var3];
         var0.append(var2);
      }

      return var0;
   }

   public static final void appendElement(@NotNull Appendable var0, Object var1, @Nullable Function1 var2) {
      if (var2 != null) {
         var0.append((CharSequence)var2.invoke(var1));
      } else if (var1 != null ? var1 instanceof CharSequence : true) {
         var0.append((CharSequence)var1);
      } else if (var1 instanceof Character) {
         var0.append((Character)var1);
      } else {
         var0.append((CharSequence)String.valueOf(var1));
      }

   }

   public StringsKt__StringBuilderKt() {
      super();
   }
}
