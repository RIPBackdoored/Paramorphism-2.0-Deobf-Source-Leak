package kotlin.text;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000>\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0014\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0082\b\u001a\u001e\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u0016\u0010\r\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\f\u0010\u000e\u001a\u00020\u000f*\u00020\u0010H\u0002\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\u0012\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0002¨\u0006\u0014"},
   d2 = {"fromInt", "", "T", "Lkotlin/text/FlagEnum;", "", "value", "", "findNext", "Lkotlin/text/MatchResult;", "Ljava/util/regex/Matcher;", "from", "input", "", "matchEntire", "range", "Lkotlin/ranges/IntRange;", "Ljava/util/regex/MatchResult;", "groupIndex", "toInt", "", "kotlin-stdlib"}
)
public final class RegexKt {
   private static final int toInt(@NotNull Iterable var0) {
      byte var2 = 0;
      boolean var3 = false;
      int var4 = var2;

      FlagEnum var7;
      for(Iterator var5 = var0.iterator(); var5.hasNext(); var4 |= var7.getValue()) {
         Object var6 = var5.next();
         var7 = (FlagEnum)var6;
         boolean var9 = false;
      }

      return var4;
   }

   private static final Set fromInt(int var0) {
      byte var1 = 0;
      Intrinsics.reifiedOperationMarker(4, "T");
      EnumSet var2 = EnumSet.allOf(Enum.class);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      CollectionsKt.retainAll((Iterable)var2, (Function1)(new RegexKt$fromInt$$inlined$apply$lambda$1(var0)));
      return Collections.unmodifiableSet((Set)var2);
   }

   private static final MatchResult findNext(@NotNull Matcher var0, int var1, CharSequence var2) {
      return !var0.find(var1) ? null : (MatchResult)(new MatcherMatchResult(var0, var2));
   }

   private static final MatchResult matchEntire(@NotNull Matcher var0, CharSequence var1) {
      return !var0.matches() ? null : (MatchResult)(new MatcherMatchResult(var0, var1));
   }

   private static final IntRange range(@NotNull java.util.regex.MatchResult var0) {
      return RangesKt.until(var0.start(), var0.end());
   }

   private static final IntRange range(@NotNull java.util.regex.MatchResult var0, int var1) {
      return RangesKt.until(var0.start(var1), var0.end(var1));
   }

   public static final MatchResult access$findNext(Matcher var0, int var1, CharSequence var2) {
      return findNext(var0, var1, var2);
   }

   public static final MatchResult access$matchEntire(Matcher var0, CharSequence var1) {
      return matchEntire(var0, var1);
   }

   public static final int access$toInt(Iterable var0) {
      return toInt(var0);
   }

   public static final IntRange access$range(java.util.regex.MatchResult var0) {
      return range(var0);
   }

   public static final IntRange access$range(java.util.regex.MatchResult var0, int var1) {
      return range(var0, var1);
   }
}
