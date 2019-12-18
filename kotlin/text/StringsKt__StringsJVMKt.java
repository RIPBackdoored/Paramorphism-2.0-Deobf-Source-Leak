package kotlin.text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000~\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\r\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0011\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0019\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a)\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a\n\u0010\u0017\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0017\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\u0015\u0010\u001a\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u0015\u0010\u001c\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010\u001d\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010 \u001a\u00020\u0011*\u00020\u00022\u0006\u0010!\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\f\u0010$\u001a\u00020\u0002*\u00020\u0014H\u0007\u001a \u0010$\u001a\u00020\u0002*\u00020\u00142\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010\n\u001a\u00020\tH\u0087\b\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010'\u001a\u00020(H\u0087\b\u001a\n\u0010)\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010)\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\f\u0010*\u001a\u00020\u0002*\u00020\rH\u0007\u001a*\u0010*\u001a\u00020\u0002*\u00020\r2\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\f\u0010,\u001a\u00020\r*\u00020\u0002H\u0007\u001a*\u0010,\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\u001c\u0010-\u001a\u00020#*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a \u0010/\u001a\u00020#*\u0004\u0018\u00010\u00022\b\u0010!\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a2\u00100\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00104\u001a*\u00100\u001a\u00020\u0002*\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00105\u001a:\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00106\u001a2\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00107\u001a\r\u00108\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\n\u00109\u001a\u00020#*\u00020(\u001a\u001d\u0010:\u001a\u00020\u0011*\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010:\u001a\u00020\u0011*\u00020\u00022\u0006\u0010>\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010?\u001a\u00020\u0011*\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010?\u001a\u00020\u0011*\u00020\u00022\u0006\u0010>\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010@\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010A\u001a\u00020\u0011H\u0087\b\u001a4\u0010B\u001a\u00020#*\u00020(2\u0006\u0010C\u001a\u00020\u00112\u0006\u0010!\u001a\u00020(2\u0006\u0010D\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a4\u0010B\u001a\u00020#*\u00020\u00022\u0006\u0010C\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00022\u0006\u0010D\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0012\u0010E\u001a\u00020\u0002*\u00020(2\u0006\u0010F\u001a\u00020\u0011\u001a$\u0010G\u001a\u00020\u0002*\u00020\u00022\u0006\u0010H\u001a\u00020<2\u0006\u0010I\u001a\u00020<2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010G\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010H\u001a\u00020<2\u0006\u0010I\u001a\u00020<2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\"\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00020N*\u00020(2\u0006\u0010O\u001a\u00020P2\b\b\u0002\u0010Q\u001a\u00020\u0011\u001a\u001c\u0010R\u001a\u00020#*\u00020\u00022\u0006\u0010S\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010R\u001a\u00020#*\u00020\u00022\u0006\u0010S\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0015\u0010T\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010T\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u0017\u0010U\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\r\u0010V\u001a\u00020\u0014*\u00020\u0002H\u0087\b\u001a3\u0010V\u001a\u00020\u0014*\u00020\u00022\u0006\u0010W\u001a\u00020\u00142\b\b\u0002\u0010X\u001a\u00020\u00112\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a \u0010V\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\r\u0010Y\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010Y\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u0017\u0010Z\u001a\u00020P*\u00020\u00022\b\b\u0002\u0010[\u001a\u00020\u0011H\u0087\b\u001a\r\u0010\\\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\"%\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006]"},
   d2 = {"CASE_INSENSITIVE_ORDER", "Ljava/util/Comparator;", "", "Lkotlin/Comparator;", "Lkotlin/String$Companion;", "getCASE_INSENSITIVE_ORDER", "(Lkotlin/jvm/internal/StringCompanionObject;)Ljava/util/Comparator;", "String", "stringBuffer", "Ljava/lang/StringBuffer;", "stringBuilder", "Ljava/lang/StringBuilder;", "bytes", "", "charset", "Ljava/nio/charset/Charset;", "offset", "", "length", "chars", "", "codePoints", "", "capitalize", "locale", "Ljava/util/Locale;", "codePointAt", "index", "codePointBefore", "codePointCount", "beginIndex", "endIndex", "compareTo", "other", "ignoreCase", "", "concatToString", "startIndex", "contentEquals", "charSequence", "", "decapitalize", "decodeToString", "throwOnInvalidSequence", "encodeToByteArray", "endsWith", "suffix", "equals", "format", "args", "", "", "(Ljava/lang/String;Ljava/util/Locale;[Ljava/lang/Object;)Ljava/lang/String;", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "intern", "isBlank", "nativeIndexOf", "ch", "", "fromIndex", "str", "nativeLastIndexOf", "offsetByCodePoints", "codePointOffset", "regionMatches", "thisOffset", "otherOffset", "repeat", "n", "replace", "oldChar", "newChar", "oldValue", "newValue", "replaceFirst", "split", "", "regex", "Ljava/util/regex/Pattern;", "limit", "startsWith", "prefix", "substring", "toByteArray", "toCharArray", "destination", "destinationOffset", "toLowerCase", "toPattern", "flags", "toUpperCase", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringsJVMKt extends StringsKt__StringNumberConversionsKt {
   @InlineOnly
   private static final int nativeIndexOf(@NotNull String var0, char var1, int var2) {
      return var0.indexOf(var1, var2);
   }

   @InlineOnly
   private static final int nativeIndexOf(@NotNull String var0, String var1, int var2) {
      return var0.indexOf(var1, var2);
   }

   @InlineOnly
   private static final int nativeLastIndexOf(@NotNull String var0, char var1, int var2) {
      return var0.lastIndexOf(var1, var2);
   }

   @InlineOnly
   private static final int nativeLastIndexOf(@NotNull String var0, String var1, int var2) {
      return var0.lastIndexOf(var1, var2);
   }

   public static final boolean equals(@Nullable String var0, @Nullable String var1, boolean var2) {
      if (var0 == null) {
         return var1 == null;
      } else {
         return !var2 ? var0.equals(var1) : var0.equalsIgnoreCase(var1);
      }
   }

   public static boolean equals$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.equals(var0, var1, var2);
   }

   @NotNull
   public static final String replace(@NotNull String var0, char var1, char var2, boolean var3) {
      if (!var3) {
         return var0.replace(var1, var2);
      } else {
         CharSequence var10000 = (CharSequence)var0;
         char[] var10002 = new char[]{var1};
         byte var4 = 0;
         char[] var5 = var10002;
         return SequencesKt.joinToString$default(StringsKt.splitToSequence$default(var10000, var5, var3, var4, 4, (Object)null), (CharSequence)String.valueOf(var2), (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
      }
   }

   public static String replace$default(String var0, char var1, char var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replace(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replace(@NotNull String var0, @NotNull String var1, @NotNull String var2, boolean var3) {
      CharSequence var10000 = (CharSequence)var0;
      String[] var10002 = new String[]{var1};
      byte var4 = 0;
      String[] var5 = var10002;
      return SequencesKt.joinToString$default(StringsKt.splitToSequence$default(var10000, var5, var3, var4, 4, (Object)null), (CharSequence)var2, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
   }

   public static String replace$default(String var0, String var1, String var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replace(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceFirst(@NotNull String var0, char var1, char var2, boolean var3) {
      int var4 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, var3, 2, (Object)null);
      String var10000;
      if (var4 < 0) {
         var10000 = var0;
      } else {
         int var6 = var4 + 1;
         CharSequence var7 = (CharSequence)String.valueOf(var2);
         boolean var8 = false;
         var10000 = StringsKt.replaceRange((CharSequence)var0, var4, var6, var7).toString();
      }

      return var10000;
   }

   public static String replaceFirst$default(String var0, char var1, char var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replaceFirst(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceFirst(@NotNull String var0, @NotNull String var1, @NotNull String var2, boolean var3) {
      int var4 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, var3, 2, (Object)null);
      String var10000;
      if (var4 < 0) {
         var10000 = var0;
      } else {
         int var6 = var4 + var1.length();
         boolean var7 = false;
         var10000 = StringsKt.replaceRange((CharSequence)var0, var4, var6, (CharSequence)var2).toString();
      }

      return var10000;
   }

   public static String replaceFirst$default(String var0, String var1, String var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replaceFirst(var0, var1, var2, var3);
   }

   @InlineOnly
   private static final String toUpperCase(@NotNull String var0) {
      return var0.toUpperCase();
   }

   @InlineOnly
   private static final String toLowerCase(@NotNull String var0) {
      return var0.toLowerCase();
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final String concatToString(@NotNull char[] var0) {
      boolean var1 = false;
      return new String(var0);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final String concatToString(@NotNull char[] var0, int var1, int var2) {
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length);
      int var3 = var2 - var1;
      boolean var4 = false;
      return new String(var0, var1, var3);
   }

   public static String concatToString$default(char[] var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length;
      }

      return StringsKt.concatToString(var0, var1, var2);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final char[] toCharArray(@NotNull String var0, int var1, int var2) {
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length());
      char[] var4 = new char[var2 - var1];
      byte var5 = 0;
      boolean var6 = false;
      var0.getChars(var1, var2, var4, var5);
      return var4;
   }

   public static char[] toCharArray$default(String var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length();
      }

      return StringsKt.toCharArray(var0, var1, var2);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final String decodeToString(@NotNull byte[] var0) {
      boolean var1 = false;
      return new String(var0, Charsets.UTF_8);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final String decodeToString(@NotNull byte[] var0, int var1, int var2, boolean var3) {
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length);
      if (!var3) {
         int var6 = var2 - var1;
         boolean var5 = false;
         return new String(var0, var1, var6, Charsets.UTF_8);
      } else {
         CharsetDecoder var4 = Charsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
         return var4.decode(ByteBuffer.wrap(var0, var1, var2 - var1)).toString();
      }
   }

   public static String decodeToString$default(byte[] var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.length;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.decodeToString(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final byte[] encodeToByteArray(@NotNull String var0) {
      Charset var2 = Charsets.UTF_8;
      boolean var3 = false;
      return var0.getBytes(var2);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final byte[] encodeToByteArray(@NotNull String var0, int var1, int var2, boolean var3) {
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length());
      if (!var3) {
         boolean var12 = false;
         String var11 = var0.substring(var1, var2);
         Charset var13 = Charsets.UTF_8;
         boolean var14 = false;
         if (var11 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         } else {
            return var11.getBytes(var13);
         }
      } else {
         CharsetEncoder var4 = Charsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
         ByteBuffer var5 = var4.encode(CharBuffer.wrap((CharSequence)var0, var1, var2));
         byte[] var15;
         if (var5.hasArray() && var5.arrayOffset() == 0) {
            int var10000 = var5.remaining();
            byte[] var10001 = var5.array();
            if (var10001 == null) {
               Intrinsics.throwNpe();
            }

            if (var10000 == var10001.length) {
               var15 = var5.array();
               return var15;
            }
         }

         byte[] var6 = new byte[var5.remaining()];
         boolean var7 = false;
         boolean var8 = false;
         boolean var10 = false;
         var5.get(var6);
         var15 = var6;
         return var15;
      }
   }

   public static byte[] encodeToByteArray$default(String var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.length();
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.encodeToByteArray(var0, var1, var2, var3);
   }

   @InlineOnly
   private static final char[] toCharArray(@NotNull String var0) {
      return var0.toCharArray();
   }

   @InlineOnly
   private static final char[] toCharArray(@NotNull String var0, char[] var1, int var2, int var3, int var4) {
      var0.getChars(var3, var4, var1, var2);
      return var1;
   }

   static char[] toCharArray$default(String var0, char[] var1, int var2, int var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = 0;
      }

      if ((var5 & 8) != 0) {
         var4 = var0.length();
      }

      boolean var7 = false;
      if (var0 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         var0.getChars(var3, var4, var1, var2);
         return var1;
      }
   }

   @InlineOnly
   private static final String format(@NotNull String var0, Object... var1) {
      byte var2 = 0;
      return String.format(var0, Arrays.copyOf(var1, var1.length));
   }

   @InlineOnly
   private static final String format(@NotNull StringCompanionObject var0, String var1, Object... var2) {
      byte var3 = 0;
      return String.format(var1, Arrays.copyOf(var2, var2.length));
   }

   @InlineOnly
   private static final String format(@NotNull String var0, Locale var1, Object... var2) {
      byte var3 = 0;
      return String.format(var1, var0, Arrays.copyOf(var2, var2.length));
   }

   @InlineOnly
   private static final String format(@NotNull StringCompanionObject var0, Locale var1, String var2, Object... var3) {
      byte var4 = 0;
      return String.format(var1, var2, Arrays.copyOf(var3, var3.length));
   }

   @NotNull
   public static final List split(@NotNull CharSequence var0, @NotNull Pattern var1, int var2) {
      boolean var3 = var2 >= 0;
      boolean var4 = false;
      boolean var5 = false;
      if (!var3) {
         boolean var6 = false;
         String var7 = "Limit must be non-negative, but was " + var2 + '.';
         throw (Throwable)(new IllegalArgumentException(var7.toString()));
      } else {
         return ArraysKt.asList(var1.split(var0, var2 == 0 ? -1 : var2));
      }
   }

   public static List split$default(CharSequence var0, Pattern var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return StringsKt.split(var0, var1, var2);
   }

   @InlineOnly
   private static final String substring(@NotNull String var0, int var1) {
      return var0.substring(var1);
   }

   @InlineOnly
   private static final String substring(@NotNull String var0, int var1, int var2) {
      return var0.substring(var1, var2);
   }

   public static final boolean startsWith(@NotNull String var0, @NotNull String var1, boolean var2) {
      return !var2 ? var0.startsWith(var1) : StringsKt.regionMatches(var0, 0, var1, 0, var1.length(), var2);
   }

   public static boolean startsWith$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   public static final boolean startsWith(@NotNull String var0, @NotNull String var1, int var2, boolean var3) {
      return !var3 ? var0.startsWith(var1, var2) : StringsKt.regionMatches(var0, var2, var1, 0, var1.length(), var3);
   }

   public static boolean startsWith$default(String var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.startsWith(var0, var1, var2, var3);
   }

   public static final boolean endsWith(@NotNull String var0, @NotNull String var1, boolean var2) {
      return !var2 ? var0.endsWith(var1) : StringsKt.regionMatches(var0, var0.length() - var1.length(), var1, 0, var1.length(), true);
   }

   public static boolean endsWith$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   @InlineOnly
   private static final String String(byte[] var0, int var1, int var2, Charset var3) {
      byte var4 = 0;
      return new String(var0, var1, var2, var3);
   }

   @InlineOnly
   private static final String String(byte[] var0, Charset var1) {
      byte var2 = 0;
      return new String(var0, var1);
   }

   @InlineOnly
   private static final String String(byte[] var0, int var1, int var2) {
      byte var3 = 0;
      return new String(var0, var1, var2, Charsets.UTF_8);
   }

   @InlineOnly
   private static final String String(byte[] var0) {
      byte var1 = 0;
      return new String(var0, Charsets.UTF_8);
   }

   @InlineOnly
   private static final String String(char[] var0) {
      byte var1 = 0;
      return new String(var0);
   }

   @InlineOnly
   private static final String String(char[] var0, int var1, int var2) {
      byte var3 = 0;
      return new String(var0, var1, var2);
   }

   @InlineOnly
   private static final String String(int[] var0, int var1, int var2) {
      byte var3 = 0;
      return new String(var0, var1, var2);
   }

   @InlineOnly
   private static final String String(StringBuffer var0) {
      byte var1 = 0;
      return new String(var0);
   }

   @InlineOnly
   private static final String String(StringBuilder var0) {
      byte var1 = 0;
      return new String(var0);
   }

   @InlineOnly
   private static final int codePointAt(@NotNull String var0, int var1) {
      return var0.codePointAt(var1);
   }

   @InlineOnly
   private static final int codePointBefore(@NotNull String var0, int var1) {
      return var0.codePointBefore(var1);
   }

   @InlineOnly
   private static final int codePointCount(@NotNull String var0, int var1, int var2) {
      return var0.codePointCount(var1, var2);
   }

   public static final int compareTo(@NotNull String var0, @NotNull String var1, boolean var2) {
      return var2 ? var0.compareToIgnoreCase(var1) : var0.compareTo(var1);
   }

   public static int compareTo$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.compareTo(var0, var1, var2);
   }

   @InlineOnly
   private static final boolean contentEquals(@NotNull String var0, CharSequence var1) {
      return var0.contentEquals(var1);
   }

   @InlineOnly
   private static final boolean contentEquals(@NotNull String var0, StringBuffer var1) {
      return var0.contentEquals(var1);
   }

   @InlineOnly
   private static final String intern(@NotNull String var0) {
      return var0.intern();
   }

   public static final boolean isBlank(@NotNull CharSequence var0) {
      boolean var10000;
      if (var0.length() != 0) {
         Iterable var1 = (Iterable)StringsKt.getIndices(var0);
         boolean var2 = false;
         if (var1 instanceof Collection && ((Collection)var1).isEmpty()) {
            var10000 = true;
         } else {
            Iterator var3 = var1.iterator();

            while(true) {
               if (!var3.hasNext()) {
                  var10000 = true;
                  break;
               }

               int var4 = ((IntIterator)var3).nextInt();
               boolean var6 = false;
               if (!CharsKt.isWhitespace(var0.charAt(var4))) {
                  var10000 = false;
                  break;
               }
            }
         }

         if (!var10000) {
            var10000 = false;
            return var10000;
         }
      }

      var10000 = true;
      return var10000;
   }

   @InlineOnly
   private static final int offsetByCodePoints(@NotNull String var0, int var1, int var2) {
      return var0.offsetByCodePoints(var1, var2);
   }

   public static final boolean regionMatches(@NotNull CharSequence var0, int var1, @NotNull CharSequence var2, int var3, int var4, boolean var5) {
      return var0 instanceof String && var2 instanceof String ? StringsKt.regionMatches((String)var0, var1, (String)var2, var3, var4, var5) : StringsKt.regionMatchesImpl(var0, var1, var2, var3, var4, var5);
   }

   public static boolean regionMatches$default(CharSequence var0, int var1, CharSequence var2, int var3, int var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return StringsKt.regionMatches(var0, var1, var2, var3, var4, var5);
   }

   public static final boolean regionMatches(@NotNull String var0, int var1, @NotNull String var2, int var3, int var4, boolean var5) {
      return !var5 ? var0.regionMatches(var1, var2, var3, var4) : var0.regionMatches(var5, var1, var2, var3, var4);
   }

   public static boolean regionMatches$default(String var0, int var1, String var2, int var3, int var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return StringsKt.regionMatches(var0, var1, var2, var3, var4, var5);
   }

   @InlineOnly
   private static final String toLowerCase(@NotNull String var0, Locale var1) {
      return var0.toLowerCase(var1);
   }

   @InlineOnly
   private static final String toUpperCase(@NotNull String var0, Locale var1) {
      return var0.toUpperCase(var1);
   }

   @InlineOnly
   private static final byte[] toByteArray(@NotNull String var0, Charset var1) {
      return var0.getBytes(var1);
   }

   static byte[] toByteArray$default(String var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      boolean var4 = false;
      if (var0 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         return var0.getBytes(var1);
      }
   }

   @InlineOnly
   private static final Pattern toPattern(@NotNull String var0, int var1) {
      byte var2 = 0;
      return Pattern.compile(var0, var1);
   }

   static Pattern toPattern$default(String var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 0;
      }

      boolean var4 = false;
      return Pattern.compile(var0, var1);
   }

   @NotNull
   public static final String capitalize(@NotNull String var0) {
      CharSequence var1 = (CharSequence)var0;
      boolean var2 = false;
      String var10000;
      if (var1.length() > 0) {
         char var7 = var0.charAt(0);
         var2 = false;
         if (Character.isLowerCase(var7)) {
            StringBuilder var10 = new StringBuilder();
            byte var8 = 0;
            byte var3 = 1;
            StringBuilder var5 = var10;
            boolean var4 = false;
            String var6 = var0.substring(var8, var3);
            var2 = false;
            if (var6 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var6 = var6.toUpperCase();
            var10 = var5.append(var6);
            var8 = 1;
            var5 = var10;
            boolean var9 = false;
            var6 = var0.substring(var8);
            var10000 = var5.append(var6).toString();
            return var10000;
         }
      }

      var10000 = var0;
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final String capitalize(@NotNull String var0, @NotNull Locale var1) {
      CharSequence var2 = (CharSequence)var0;
      boolean var3 = false;
      if (var2.length() > 0) {
         char var16 = var0.charAt(0);
         boolean var4 = false;
         if (Character.isLowerCase(var16)) {
            var3 = false;
            StringBuilder var17 = new StringBuilder();
            boolean var5 = false;
            boolean var6 = false;
            boolean var8 = false;
            boolean var10 = false;
            char var11 = Character.toTitleCase(var16);
            var10 = false;
            char var13 = Character.toUpperCase(var16);
            byte var18;
            String var19;
            boolean var20;
            if (var11 != var13) {
               var17.append(var11);
            } else {
               var18 = 0;
               byte var14 = 1;
               boolean var15 = false;
               var19 = var0.substring(var18, var14);
               var20 = false;
               if (var19 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var19 = var19.toUpperCase(var1);
               var17.append(var19);
            }

            var18 = 1;
            var20 = false;
            var19 = var0.substring(var18);
            var17.append(var19);
            return var17.toString();
         }
      }

      return var0;
   }

   @NotNull
   public static final String decapitalize(@NotNull String var0) {
      CharSequence var1 = (CharSequence)var0;
      boolean var2 = false;
      String var10000;
      if (var1.length() > 0) {
         char var7 = var0.charAt(0);
         var2 = false;
         if (Character.isUpperCase(var7)) {
            StringBuilder var10 = new StringBuilder();
            byte var8 = 0;
            byte var3 = 1;
            StringBuilder var5 = var10;
            boolean var4 = false;
            String var6 = var0.substring(var8, var3);
            var2 = false;
            if (var6 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var6 = var6.toLowerCase();
            var10 = var5.append(var6);
            var8 = 1;
            var5 = var10;
            boolean var9 = false;
            var6 = var0.substring(var8);
            var10000 = var5.append(var6).toString();
            return var10000;
         }
      }

      var10000 = var0;
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final String decapitalize(@NotNull String var0, @NotNull Locale var1) {
      CharSequence var2 = (CharSequence)var0;
      boolean var3 = false;
      String var10000;
      if (var2.length() > 0) {
         char var8 = var0.charAt(0);
         var3 = false;
         if (!Character.isLowerCase(var8)) {
            StringBuilder var11 = new StringBuilder();
            byte var9 = 0;
            byte var4 = 1;
            StringBuilder var6 = var11;
            boolean var5 = false;
            String var7 = var0.substring(var9, var4);
            var3 = false;
            if (var7 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var7 = var7.toLowerCase(var1);
            var11 = var6.append(var7);
            var9 = 1;
            var6 = var11;
            boolean var10 = false;
            var7 = var0.substring(var9);
            var10000 = var6.append(var7).toString();
            return var10000;
         }
      }

      var10000 = var0;
      return var10000;
   }

   @NotNull
   public static final String repeat(@NotNull CharSequence var0, int var1) {
      boolean var2 = var1 >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var20 = false;
         String var19 = "Count 'n' must be non-negative, but was " + var1 + '.';
         throw (Throwable)(new IllegalArgumentException(var19.toString()));
      } else {
         String var10000;
         switch(var1) {
         case 0:
            var10000 = "";
            break;
         case 1:
            var10000 = var0.toString();
            break;
         default:
            switch(var0.length()) {
            case 0:
               var10000 = "";
               break;
            case 1:
               char var15 = var0.charAt(0);
               var3 = false;
               var4 = false;
               char var5 = var15;
               boolean var6 = false;
               int var7 = var1;
               char[] var8 = new char[var1];

               for(int var9 = 0; var9 < var7; ++var9) {
                  boolean var13 = false;
                  var8[var9] = var5;
               }

               boolean var22 = false;
               var10000 = new String(var8);
               break;
            default:
               StringBuilder var16 = new StringBuilder(var1 * var0.length());
               int var17 = 1;
               int var18 = var1;
               if (var17 <= var1) {
                  while(true) {
                     var16.append(var0);
                     if (var17 == var18) {
                        break;
                     }

                     ++var17;
                  }
               }

               var10000 = var16.toString();
            }
         }

         return var10000;
      }
   }

   @NotNull
   public static final Comparator getCASE_INSENSITIVE_ORDER(@NotNull StringCompanionObject var0) {
      return String.CASE_INSENSITIVE_ORDER;
   }

   public StringsKt__StringsJVMKt() {
      super();
   }
}
