package kotlin.concurrent;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001aJ\u0010\u0000\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001aL\u0010\u0000\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a\u001a\u0010\u0010\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0001\u001aJ\u0010\u0010\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001aL\u0010\u0010\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a$\u0010\u0011\u001a\u00020\f2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a0\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00072\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a8\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a0\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a8\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a8\u0010\u0015\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a8\u0010\u0015\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b¨\u0006\u0016"},
   d2 = {"fixedRateTimer", "Ljava/util/Timer;", "name", "", "daemon", "", "startAt", "Ljava/util/Date;", "period", "", "action", "Lkotlin/Function1;", "Ljava/util/TimerTask;", "", "Lkotlin/ExtensionFunctionType;", "initialDelay", "timer", "timerTask", "schedule", "time", "delay", "scheduleAtFixedRate", "kotlin-stdlib"}
)
@JvmName(
   name = "TimersKt"
)
public final class TimersKt {
   @InlineOnly
   private static final TimerTask schedule(@NotNull Timer var0, long var1, Function1 var3) {
      byte var4 = 0;
      boolean var6 = false;
      TimerTask var5 = (TimerTask)(new TimersKt$timerTask$1(var3));
      var0.schedule(var5, var1);
      return var5;
   }

   @InlineOnly
   private static final TimerTask schedule(@NotNull Timer var0, Date var1, Function1 var2) {
      byte var3 = 0;
      boolean var5 = false;
      TimerTask var4 = (TimerTask)(new TimersKt$timerTask$1(var2));
      var0.schedule(var4, var1);
      return var4;
   }

   @InlineOnly
   private static final TimerTask schedule(@NotNull Timer var0, long var1, long var3, Function1 var5) {
      byte var6 = 0;
      boolean var8 = false;
      TimerTask var7 = (TimerTask)(new TimersKt$timerTask$1(var5));
      var0.schedule(var7, var1, var3);
      return var7;
   }

   @InlineOnly
   private static final TimerTask schedule(@NotNull Timer var0, Date var1, long var2, Function1 var4) {
      byte var5 = 0;
      boolean var7 = false;
      TimerTask var6 = (TimerTask)(new TimersKt$timerTask$1(var4));
      var0.schedule(var6, var1, var2);
      return var6;
   }

   @InlineOnly
   private static final TimerTask scheduleAtFixedRate(@NotNull Timer var0, long var1, long var3, Function1 var5) {
      byte var6 = 0;
      boolean var8 = false;
      TimerTask var7 = (TimerTask)(new TimersKt$timerTask$1(var5));
      var0.scheduleAtFixedRate(var7, var1, var3);
      return var7;
   }

   @InlineOnly
   private static final TimerTask scheduleAtFixedRate(@NotNull Timer var0, Date var1, long var2, Function1 var4) {
      byte var5 = 0;
      boolean var7 = false;
      TimerTask var6 = (TimerTask)(new TimersKt$timerTask$1(var4));
      var0.scheduleAtFixedRate(var6, var1, var2);
      return var6;
   }

   @PublishedApi
   @NotNull
   public static final Timer timer(@Nullable String var0, boolean var1) {
      return var0 == null ? new Timer(var1) : new Timer(var0, var1);
   }

   @InlineOnly
   private static final Timer timer(String var0, boolean var1, long var2, long var4, Function1 var6) {
      byte var7 = 0;
      Timer var8 = timer(var0, var1);
      boolean var10 = false;
      boolean var11 = false;
      TimerTask var12 = (TimerTask)(new TimersKt$timerTask$1(var6));
      var8.schedule(var12, var2, var4);
      return var8;
   }

   static Timer timer$default(String var0, boolean var1, long var2, long var4, Function1 var6, int var7, Object var8) {
      if ((var7 & 1) != 0) {
         var0 = (String)null;
      }

      if ((var7 & 2) != 0) {
         var1 = false;
      }

      if ((var7 & 4) != 0) {
         var2 = 0L;
      }

      boolean var13 = false;
      Timer var14 = timer(var0, var1);
      boolean var10 = false;
      boolean var11 = false;
      TimerTask var12 = (TimerTask)(new TimersKt$timerTask$1(var6));
      var14.schedule(var12, var2, var4);
      return var14;
   }

   @InlineOnly
   private static final Timer timer(String var0, boolean var1, Date var2, long var3, Function1 var5) {
      byte var6 = 0;
      Timer var7 = timer(var0, var1);
      boolean var9 = false;
      boolean var10 = false;
      TimerTask var11 = (TimerTask)(new TimersKt$timerTask$1(var5));
      var7.schedule(var11, var2, var3);
      return var7;
   }

   static Timer timer$default(String var0, boolean var1, Date var2, long var3, Function1 var5, int var6, Object var7) {
      if ((var6 & 1) != 0) {
         var0 = (String)null;
      }

      if ((var6 & 2) != 0) {
         var1 = false;
      }

      boolean var12 = false;
      Timer var13 = timer(var0, var1);
      boolean var9 = false;
      boolean var10 = false;
      TimerTask var11 = (TimerTask)(new TimersKt$timerTask$1(var5));
      var13.schedule(var11, var2, var3);
      return var13;
   }

   @InlineOnly
   private static final Timer fixedRateTimer(String var0, boolean var1, long var2, long var4, Function1 var6) {
      byte var7 = 0;
      Timer var8 = timer(var0, var1);
      boolean var10 = false;
      boolean var11 = false;
      TimerTask var12 = (TimerTask)(new TimersKt$timerTask$1(var6));
      var8.scheduleAtFixedRate(var12, var2, var4);
      return var8;
   }

   static Timer fixedRateTimer$default(String var0, boolean var1, long var2, long var4, Function1 var6, int var7, Object var8) {
      if ((var7 & 1) != 0) {
         var0 = (String)null;
      }

      if ((var7 & 2) != 0) {
         var1 = false;
      }

      if ((var7 & 4) != 0) {
         var2 = 0L;
      }

      boolean var13 = false;
      Timer var14 = timer(var0, var1);
      boolean var10 = false;
      boolean var11 = false;
      TimerTask var12 = (TimerTask)(new TimersKt$timerTask$1(var6));
      var14.scheduleAtFixedRate(var12, var2, var4);
      return var14;
   }

   @InlineOnly
   private static final Timer fixedRateTimer(String var0, boolean var1, Date var2, long var3, Function1 var5) {
      byte var6 = 0;
      Timer var7 = timer(var0, var1);
      boolean var9 = false;
      boolean var10 = false;
      TimerTask var11 = (TimerTask)(new TimersKt$timerTask$1(var5));
      var7.scheduleAtFixedRate(var11, var2, var3);
      return var7;
   }

   static Timer fixedRateTimer$default(String var0, boolean var1, Date var2, long var3, Function1 var5, int var6, Object var7) {
      if ((var6 & 1) != 0) {
         var0 = (String)null;
      }

      if ((var6 & 2) != 0) {
         var1 = false;
      }

      boolean var12 = false;
      Timer var13 = timer(var0, var1);
      boolean var9 = false;
      boolean var10 = false;
      TimerTask var11 = (TimerTask)(new TimersKt$timerTask$1(var5));
      var13.scheduleAtFixedRate(var11, var2, var3);
      return var13;
   }

   @InlineOnly
   private static final TimerTask timerTask(Function1 var0) {
      byte var1 = 0;
      return (TimerTask)(new TimersKt$timerTask$1(var0));
   }
}
