package site.hackery.paramorphism.api.naming;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\u0018\u00002\u00020\u0001B'\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0019\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"},
   d2 = {"Lsite/hackery/paramorphism/api/naming/MappingDictionary;", "", "elementSet", "", "", "joiner", "inflation", "", "([Ljava/lang/String;Ljava/lang/String;I)V", "getElementSet", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getInflation", "()I", "getJoiner", "()Ljava/lang/String;", "paramorphism"}
)
public final class MappingDictionary {
   @NotNull
   private final String[] elementSet;
   @NotNull
   private final String joiner;
   private final int inflation;

   @NotNull
   public final String[] getElementSet() {
      return this.elementSet;
   }

   @NotNull
   public final String getJoiner() {
      return this.joiner;
   }

   public final int getInflation() {
      return this.inflation;
   }

   public MappingDictionary(@NotNull String[] var1, @NotNull String var2, int var3) {
      super();
      this.elementSet = var1;
      this.joiner = var2;
      this.inflation = var3;
   }

   public MappingDictionary(String[] var1, String var2, int var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 2) != 0) {
         var2 = "";
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      this(var1, var2, var3);
   }
}
