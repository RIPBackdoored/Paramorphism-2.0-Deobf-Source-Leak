package kotlin.internal.jdk8;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.internal.jdk7.JDK7PlatformImplementations;
import kotlin.random.Random;
import kotlin.random.jdk8.PlatformThreadLocalRandom;
import kotlin.ranges.IntRange;
import kotlin.text.MatchGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0010\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"},
   d2 = {"Lkotlin/internal/jdk8/JDK8PlatformImplementations;", "Lkotlin/internal/jdk7/JDK7PlatformImplementations;", "()V", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "kotlin-stdlib-jdk8"}
)
public class JDK8PlatformImplementations extends JDK7PlatformImplementations {
   @Nullable
   public MatchGroup getMatchResultNamedGroup(@NotNull MatchResult var1, @NotNull String var2) {
      MatchResult var10000 = var1;
      if (!(var1 instanceof Matcher)) {
         var10000 = null;
      }

      Matcher var6 = (Matcher)var10000;
      if (var6 != null) {
         Matcher var3 = var6;
         int var5 = var3.start(var2);
         IntRange var4 = new IntRange(var5, var3.end(var2) - 1);
         return var4.getStart() >= 0 ? new MatchGroup(var3.group(var2), var4) : null;
      } else {
         throw (Throwable)(new UnsupportedOperationException("Retrieving groups by name is not supported on this platform."));
      }
   }

   @NotNull
   public Random defaultPlatformRandom() {
      return (Random)(new PlatformThreadLocalRandom());
   }

   public JDK8PlatformImplementations() {
      super();
   }
}
