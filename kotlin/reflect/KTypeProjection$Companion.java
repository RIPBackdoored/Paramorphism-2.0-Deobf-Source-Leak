package kotlin.reflect;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"},
   d2 = {"Lkotlin/reflect/KTypeProjection$Companion;", "", "()V", "STAR", "Lkotlin/reflect/KTypeProjection;", "getSTAR", "()Lkotlin/reflect/KTypeProjection;", "contravariant", "type", "Lkotlin/reflect/KType;", "covariant", "invariant", "kotlin-stdlib"}
)
public final class KTypeProjection$Companion {
   @NotNull
   public final KTypeProjection getSTAR() {
      return KTypeProjection.access$getSTAR$cp();
   }

   @NotNull
   public final KTypeProjection invariant(@NotNull KType var1) {
      return new KTypeProjection(KVariance.INVARIANT, var1);
   }

   @NotNull
   public final KTypeProjection contravariant(@NotNull KType var1) {
      return new KTypeProjection(KVariance.IN, var1);
   }

   @NotNull
   public final KTypeProjection covariant(@NotNull KType var1) {
      return new KTypeProjection(KVariance.OUT, var1);
   }

   private KTypeProjection$Companion() {
      super();
   }

   public KTypeProjection$Companion(DefaultConstructorMarker var1) {
      this();
   }
}
