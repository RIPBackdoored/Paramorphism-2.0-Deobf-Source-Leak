package paramorphism-obfuscator;

import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@DebugMetadata(
   f = "Tokenization.kt",
   l = {12, 18},
   i = {0, 0, 0, 0, 0, 0, 1, 1, 1},
   s = {"L$0", "L$1", "I$0", "I$1", "C$0", "L$3", "L$0", "L$1", "I$0"},
   n = {"$this$sequence", "previousDelimeter", "lastDelimiterIndex", "index", "character", "delimiter", "$this$sequence", "previousDelimeter", "lastDelimiterIndex"},
   m = "invokeSuspend",
   c = "site.hackery.paramorphism.strategy.strategies.obfuscation.remapper.flatmappingstate.TokenizationKt$tokenizeSegments$1"
)
public final class of extends RestrictedSuspendLambda implements Function2 {
   private SequenceScope yk;
   public Object yl;
   public Object ym;
   public Object yn;
   public Object yo;
   public int yp;
   public int yq;
   public int yr;
   public char ys;
   public int yt;
   public final String yu;
   public final char[] yv;

   @Nullable
   public final Object invokeSuspend(@NotNull Object var1) {
      Object var24 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      SequenceScope var2;
      String var3;
      int var4;
      int var5;
      char var6;
      CharSequence var7;
      int var8;
      String var9;
      switch(this.yt) {
      case 0:
         ResultKt.throwOnFailure(var1);
         var2 = this.yk;
         var3 = "";
         var4 = -1;
         var7 = (CharSequence)this.yu;
         var8 = var7.length();
         var5 = 0;
         break;
      case 1:
         var9 = (String)this.yo;
         var8 = this.yr;
         var7 = (CharSequence)this.yn;
         var6 = this.ys;
         var5 = this.yq;
         var4 = this.yp;
         var3 = (String)this.ym;
         var2 = (SequenceScope)this.yl;
         ResultKt.throwOnFailure(var1);
         var3 = var9;
         var4 = var5++;
         break;
      case 2:
         var4 = this.yp;
         var3 = (String)this.ym;
         var2 = (SequenceScope)this.yl;
         ResultKt.throwOnFailure(var1);
         return Unit.INSTANCE;
      default:
         throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }

      String var17;
      og var10001;
      while(var5 < var8) {
         var6 = var7.charAt(var5);
         if (ArraysKt.contains(this.yv, var6)) {
            var9 = String.valueOf(var6);
            String var10 = this.yu;
            int var11 = var4 + 1;
            boolean var12 = false;
            if (var10 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var17 = var10.substring(var11, var5);
            var10001 = new og(var3, var17, var9);
            this.yl = var2;
            this.ym = var3;
            this.yp = var4;
            this.yq = var5;
            this.ys = var6;
            this.yn = var7;
            this.yr = var8;
            this.yo = var9;
            this.yt = 1;
            if (var2.yield(var10001, this) == var24) {
               return var24;
            }

            var3 = var9;
            var4 = var5++;
         } else {
            ++var5;
         }
      }

      String var26 = this.yu;
      int var25 = var4 + 1;
      int var27 = this.yu.length();
      boolean var28 = false;
      if (var26 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         var17 = var26.substring(var25, var27);
         String var21 = "";
         var10001 = new og(var3, var17, var21);
         this.yl = var2;
         this.ym = var3;
         this.yp = var4;
         this.yt = 2;
         if (var2.yield(var10001, this) == var24) {
            return var24;
         } else {
            return Unit.INSTANCE;
         }
      }
   }

   public of(String var1, char[] var2, Continuation var3) {
      super(2, var3);
      this.yu = var1;
      this.yv = var2;
   }

   @NotNull
   public final Continuation create(@Nullable Object var1, @NotNull Continuation var2) {
      of var3 = new of(this.yu, this.yv, var2);
      var3.yk = (SequenceScope)var1;
      return var3;
   }

   public final Object invoke(Object var1, Object var2) {
      return ((of)this.create(var1, (Continuation)var2)).invokeSuspend(Unit.INSTANCE);
   }
}
