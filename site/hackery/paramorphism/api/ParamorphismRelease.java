package site.hackery.paramorphism.api;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"},
   d2 = {"Lsite/hackery/paramorphism/api/ParamorphismRelease;", "", "()V", "VERSION", "", "paramorphism"}
)
public final class ParamorphismRelease {
   @NotNull
   public static final String VERSION = "2.0";
   public static final ParamorphismRelease INSTANCE;

   private ParamorphismRelease() {
      super();
   }

   static {
      ParamorphismRelease var0 = new ParamorphismRelease();
      INSTANCE = var0;
   }
}
