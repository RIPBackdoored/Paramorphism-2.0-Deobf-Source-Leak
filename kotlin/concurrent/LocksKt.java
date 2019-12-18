package kotlin.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u001a\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a&\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\b¢\u0006\u0002\u0010\u0005\u001a&\u0010\u0006\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00072\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\b¢\u0006\u0002\u0010\b\u001a&\u0010\t\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\b¢\u0006\u0002\u0010\u0005¨\u0006\n"},
   d2 = {"read", "T", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "action", "Lkotlin/Function0;", "(Ljava/util/concurrent/locks/ReentrantReadWriteLock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withLock", "Ljava/util/concurrent/locks/Lock;", "(Ljava/util/concurrent/locks/Lock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "write", "kotlin-stdlib"}
)
@JvmName(
   name = "LocksKt"
)
public final class LocksKt {
   @InlineOnly
   private static final Object withLock(@NotNull Lock var0, Function0 var1) {
      byte var2 = 0;
      var0.lock();
      boolean var5 = false;

      Object var3;
      try {
         var5 = true;
         var3 = var1.invoke();
         var5 = false;
      } finally {
         if (var5) {
            InlineMarker.finallyStart(1);
            var0.unlock();
            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      var0.unlock();
      InlineMarker.finallyEnd(1);
      return var3;
   }

   @InlineOnly
   private static final Object read(@NotNull ReentrantReadWriteLock var0, Function0 var1) {
      byte var2 = 0;
      ReadLock var3 = var0.readLock();
      var3.lock();
      boolean var6 = false;

      Object var4;
      try {
         var6 = true;
         var4 = var1.invoke();
         var6 = false;
      } finally {
         if (var6) {
            InlineMarker.finallyStart(1);
            var3.unlock();
            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      var3.unlock();
      InlineMarker.finallyEnd(1);
      return var4;
   }

   @InlineOnly
   private static final Object write(@NotNull ReentrantReadWriteLock var0, Function0 var1) {
      byte var2 = 0;
      ReadLock var3 = var0.readLock();
      int var4 = var0.getWriteHoldCount() == 0 ? var0.getReadHoldCount() : 0;
      boolean var5 = false;
      boolean var6 = false;
      int var16 = 0;

      for(int var7 = var4; var16 < var7; ++var16) {
         boolean var9 = false;
         var3.unlock();
      }

      WriteLock var15 = var0.writeLock();
      var15.lock();
      boolean var13 = false;

      boolean var8;
      boolean var11;
      Object var17;
      boolean var18;
      int var19;
      int var20;
      try {
         var13 = true;
         var17 = var1.invoke();
         var13 = false;
      } finally {
         if (var13) {
            InlineMarker.finallyStart(1);
            var18 = false;
            var8 = false;
            var19 = 0;

            for(var20 = var4; var19 < var20; ++var19) {
               var11 = false;
               var3.lock();
            }

            var15.unlock();
            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      var18 = false;
      var8 = false;
      var19 = 0;

      for(var20 = var4; var19 < var20; ++var19) {
         var11 = false;
         var3.lock();
      }

      var15.unlock();
      InlineMarker.finallyEnd(1);
      return var17;
   }
}
