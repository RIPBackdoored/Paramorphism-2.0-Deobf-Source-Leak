package site.hackery.paramorphism.api.resources;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001d\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lsite/hackery/paramorphism/api/resources/LazyResource;", "Lsite/hackery/paramorphism/api/resources/Resource;", "resolve", "Lkotlin/Function0;", "", "order", "", "(Lkotlin/jvm/functions/Function0;I)V", "data", "getData", "()[B", "paramorphism"}
)
public final class LazyResource extends Resource {
   private final Function0 resolve;

   @NotNull
   public byte[] getData() {
      return (byte[])this.resolve.invoke();
   }

   public LazyResource(@NotNull Function0 var1, int var2) {
      super(var2, (DefaultConstructorMarker)null);
      this.resolve = var1;
   }

   public LazyResource(Function0 var1, int var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = 1;
      }

      this(var1, var2);
   }
}
