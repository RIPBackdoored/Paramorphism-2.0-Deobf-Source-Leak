package kotlin;

import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0007\b\u0016¢\u0006\u0002\u0010\u0003B\u0011\b\u0016\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lkotlin/KotlinNullPointerException;", "Ljava/lang/NullPointerException;", "Lkotlin/NullPointerException;", "()V", "message", "", "(Ljava/lang/String;)V", "kotlin-stdlib"}
)
public class KotlinNullPointerException extends NullPointerException {
   public KotlinNullPointerException() {
      super();
   }

   public KotlinNullPointerException(@Nullable String var1) {
      super(var1);
   }
}
