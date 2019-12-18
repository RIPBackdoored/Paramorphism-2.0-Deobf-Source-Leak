package site.hackery.paramorphism.api.resources;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"},
   d2 = {"Lsite/hackery/paramorphism/api/resources/BufferResource;", "Lsite/hackery/paramorphism/api/resources/Resource;", "data", "", "order", "", "([BI)V", "getData", "()[B", "paramorphism"}
)
public final class BufferResource extends Resource {
   @NotNull
   private final byte[] data;

   @NotNull
   public byte[] getData() {
      return this.data;
   }

   public BufferResource(@NotNull byte[] var1, int var2) {
      super(var2, (DefaultConstructorMarker)null);
      this.data = var1;
   }

   public BufferResource(byte[] var1, int var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = 1;
      }

      this(var1, var2);
   }
}
