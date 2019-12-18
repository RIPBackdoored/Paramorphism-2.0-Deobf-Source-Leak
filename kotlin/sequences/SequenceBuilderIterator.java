package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result$Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\t\u0010\u0018\u001a\u00020\u0019H\u0096\u0002J\u000e\u0010\u001a\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001bJ\r\u0010\u001c\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001bJ\u001e\u0010\u001d\u001a\u00020\u00052\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u001fH\u0016ø\u0001\u0000¢\u0006\u0002\u0010 J\u0019\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010#J\u001f\u0010$\u001a\u00020\u00052\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010&R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0012R\u0012\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006'"},
   d2 = {"Lkotlin/sequences/SequenceBuilderIterator;", "T", "Lkotlin/sequences/SequenceScope;", "", "Lkotlin/coroutines/Continuation;", "", "()V", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "nextIterator", "nextStep", "getNextStep", "()Lkotlin/coroutines/Continuation;", "setNextStep", "(Lkotlin/coroutines/Continuation;)V", "nextValue", "Ljava/lang/Object;", "state", "", "Lkotlin/sequences/State;", "exceptionalState", "", "hasNext", "", "next", "()Ljava/lang/Object;", "nextNotReady", "resumeWith", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "yield", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "yieldAll", "iterator", "(Ljava/util/Iterator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}
)
final class SequenceBuilderIterator extends SequenceScope implements Iterator, Continuation, KMappedMarker {
   private int state;
   private Object nextValue;
   private Iterator nextIterator;
   @Nullable
   private Continuation nextStep;

   @Nullable
   public final Continuation getNextStep() {
      return this.nextStep;
   }

   public final void setNextStep(@Nullable Continuation var1) {
      this.nextStep = var1;
   }

   public boolean hasNext() {
      while(true) {
         switch(this.state) {
         case 1:
            Iterator var10000 = this.nextIterator;
            if (var10000 == null) {
               Intrinsics.throwNpe();
            }

            if (var10000.hasNext()) {
               this.state = 2;
               return true;
            }

            this.nextIterator = (Iterator)null;
         case 0:
            this.state = 5;
            Continuation var9 = this.nextStep;
            if (var9 == null) {
               Intrinsics.throwNpe();
            }

            Continuation var1 = var9;
            this.nextStep = (Continuation)null;
            Unit var3 = Unit.INSTANCE;
            boolean var4 = false;
            Result$Companion var5 = Result.Companion;
            boolean var7 = false;
            Object var8 = Result.constructor-impl(var3);
            var1.resumeWith(var8);
            break;
         case 2:
         case 3:
            return true;
         case 4:
            return false;
         default:
            throw this.exceptionalState();
         }
      }
   }

   public Object next() {
      switch(this.state) {
      case 0:
      case 1:
         return this.nextNotReady();
      case 2:
         this.state = 1;
         Iterator var10000 = this.nextIterator;
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         return var10000.next();
      case 3:
         this.state = 0;
         Object var1 = this.nextValue;
         this.nextValue = null;
         return var1;
      default:
         throw this.exceptionalState();
      }
   }

   private final Object nextNotReady() {
      if (!this.hasNext()) {
         throw (Throwable)(new NoSuchElementException());
      } else {
         return this.next();
      }
   }

   private final Throwable exceptionalState() {
      Throwable var10000;
      switch(this.state) {
      case 4:
         var10000 = (Throwable)(new NoSuchElementException());
         break;
      case 5:
         var10000 = (Throwable)(new IllegalStateException("Iterator has failed."));
         break;
      default:
         var10000 = (Throwable)(new IllegalStateException("Unexpected state of the iterator: " + this.state));
      }

      return var10000;
   }

   @Nullable
   public Object yield(Object var1, @NotNull Continuation var2) {
      this.nextValue = var1;
      this.state = 3;
      boolean var4 = false;
      this.setNextStep(var2);
      Object var10000 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      if (var10000 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var2);
      }

      return var10000;
   }

   @Nullable
   public Object yieldAll(@NotNull Iterator var1, @NotNull Continuation var2) {
      if (!var1.hasNext()) {
         return Unit.INSTANCE;
      } else {
         this.nextIterator = var1;
         this.state = 2;
         boolean var4 = false;
         this.setNextStep(var2);
         Object var10000 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
         if (var10000 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(var2);
         }

         return var10000;
      }
   }

   public void resumeWith(@NotNull Object var1) {
      boolean var3 = false;
      ResultKt.throwOnFailure(var1);
      this.state = 4;
   }

   @NotNull
   public CoroutineContext getContext() {
      return (CoroutineContext)EmptyCoroutineContext.INSTANCE;
   }

   public SequenceBuilderIterator() {
      super();
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
