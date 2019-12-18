package kotlin.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 ,2\u00060\u0001j\u0002`\u0002:\u0002,-B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u001d\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n¢\u0006\u0002\u0010\u000bB\u000f\b\u0001\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u001d2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u0017J\u0011\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086\u0004J\"\u0010 \u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00170\"J\u0016\u0010 \u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004J\u0016\u0010$\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040&2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010'\u001a\u00020\u001bJ\u0006\u0010(\u001a\u00020\rJ\b\u0010)\u001a\u00020\u0004H\u0016J\b\u0010*\u001a\u00020+H\u0002R\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006."},
   d2 = {"Lkotlin/text/Regex;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "(Ljava/lang/String;)V", "option", "Lkotlin/text/RegexOption;", "(Ljava/lang/String;Lkotlin/text/RegexOption;)V", "options", "", "(Ljava/lang/String;Ljava/util/Set;)V", "nativePattern", "Ljava/util/regex/Pattern;", "(Ljava/util/regex/Pattern;)V", "_options", "getOptions", "()Ljava/util/Set;", "getPattern", "()Ljava/lang/String;", "containsMatchIn", "", "input", "", "find", "Lkotlin/text/MatchResult;", "startIndex", "", "findAll", "Lkotlin/sequences/Sequence;", "matchEntire", "matches", "replace", "transform", "Lkotlin/Function1;", "replacement", "replaceFirst", "split", "", "limit", "toPattern", "toString", "writeReplace", "", "Companion", "Serialized", "kotlin-stdlib"}
)
public final class Regex implements Serializable {
   private Set _options;
   private final Pattern nativePattern;
   public static final Regex$Companion Companion = new Regex$Companion((DefaultConstructorMarker)null);

   @NotNull
   public final String getPattern() {
      return this.nativePattern.pattern();
   }

   @NotNull
   public final Set getOptions() {
      Set var10000 = this._options;
      if (var10000 == null) {
         int var1 = this.nativePattern.flags();
         boolean var2 = false;
         EnumSet var3 = EnumSet.allOf(RegexOption.class);
         boolean var4 = false;
         boolean var5 = false;
         boolean var7 = false;
         CollectionsKt.retainAll((Iterable)var3, (Function1)(new Regex$fromInt$$inlined$apply$lambda$1(var1)));
         Set var8 = Collections.unmodifiableSet((Set)var3);
         var2 = false;
         boolean var9 = false;
         var5 = false;
         this._options = var8;
         var10000 = var8;
      }

      return var10000;
   }

   public final boolean matches(@NotNull CharSequence var1) {
      return this.nativePattern.matcher(var1).matches();
   }

   public final boolean containsMatchIn(@NotNull CharSequence var1) {
      return this.nativePattern.matcher(var1).find();
   }

   @Nullable
   public final MatchResult find(@NotNull CharSequence var1, int var2) {
      return RegexKt.access$findNext(this.nativePattern.matcher(var1), var2, var1);
   }

   public static MatchResult find$default(Regex var0, CharSequence var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return var0.find(var1, var2);
   }

   @NotNull
   public final Sequence findAll(@NotNull CharSequence var1, int var2) {
      return SequencesKt.generateSequence((Function0)(new Regex$findAll$1(this, var1, var2)), (Function1)Regex$findAll$2.INSTANCE);
   }

   public static Sequence findAll$default(Regex var0, CharSequence var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return var0.findAll(var1, var2);
   }

   @Nullable
   public final MatchResult matchEntire(@NotNull CharSequence var1) {
      return RegexKt.access$matchEntire(this.nativePattern.matcher(var1), var1);
   }

   @NotNull
   public final String replace(@NotNull CharSequence var1, @NotNull String var2) {
      return this.nativePattern.matcher(var1).replaceAll(var2);
   }

   @NotNull
   public final String replace(@NotNull CharSequence var1, @NotNull Function1 var2) {
      MatchResult var10000 = find$default(this, var1, 0, 2, (Object)null);
      if (var10000 == null) {
         return var1.toString();
      } else {
         MatchResult var3 = var10000;
         int var4 = 0;
         int var5 = var1.length();
         StringBuilder var6 = new StringBuilder(var5);

         do {
            if (var3 == null) {
               Intrinsics.throwNpe();
            }

            var6.append(var1, var4, var3.getRange().getStart());
            var6.append((CharSequence)var2.invoke(var3));
            var4 = var3.getRange().getEndInclusive() + 1;
            var3 = var3.next();
         } while(var4 < var5 && var3 != null);

         if (var4 < var5) {
            var6.append(var1, var4, var5);
         }

         return var6.toString();
      }
   }

   @NotNull
   public final String replaceFirst(@NotNull CharSequence var1, @NotNull String var2) {
      return this.nativePattern.matcher(var1).replaceFirst(var2);
   }

   @NotNull
   public final List split(@NotNull CharSequence var1, int var2) {
      boolean var3 = var2 >= 0;
      boolean var4 = false;
      boolean var5 = false;
      if (!var3) {
         boolean var16 = false;
         String var15 = "Limit must be non-negative, but was " + var2 + '.';
         throw (Throwable)(new IllegalArgumentException(var15.toString()));
      } else {
         Matcher var12 = this.nativePattern.matcher(var1);
         if (var12.find() && var2 != 1) {
            ArrayList var13 = new ArrayList(var2 > 0 ? RangesKt.coerceAtMost(var2, 10) : 10);
            int var14 = 0;
            int var6 = var2 - 1;

            int var8;
            boolean var9;
            String var11;
            do {
               var8 = var12.start();
               var9 = false;
               var11 = var1.subSequence(var14, var8).toString();
               var13.add(var11);
               var14 = var12.end();
            } while((var6 < 0 || var13.size() != var6) && var12.find());

            var8 = var1.length();
            var9 = false;
            var11 = var1.subSequence(var14, var8).toString();
            var13.add(var11);
            return (List)var13;
         } else {
            return CollectionsKt.listOf(var1.toString());
         }
      }
   }

   public static List split$default(Regex var0, CharSequence var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return var0.split(var1, var2);
   }

   @NotNull
   public String toString() {
      return this.nativePattern.toString();
   }

   @NotNull
   public final Pattern toPattern() {
      return this.nativePattern;
   }

   private final Object writeReplace() {
      return new Regex$Serialized(this.nativePattern.pattern(), this.nativePattern.flags());
   }

   @PublishedApi
   public Regex(@NotNull Pattern var1) {
      super();
      this.nativePattern = var1;
   }

   public Regex(@NotNull String var1) {
      this(Pattern.compile(var1));
   }

   public Regex(@NotNull String var1, @NotNull RegexOption var2) {
      this(Pattern.compile(var1, Regex$Companion.access$ensureUnicodeCase(Companion, var2.getValue())));
   }

   public Regex(@NotNull String var1, @NotNull Set var2) {
      this(Pattern.compile(var1, Regex$Companion.access$ensureUnicodeCase(Companion, RegexKt.access$toInt((Iterable)var2))));
   }
}
