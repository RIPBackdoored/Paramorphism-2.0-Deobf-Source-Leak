package site.hackery.paramorphism.api.resources;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.jar.Manifest;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lsite/hackery/paramorphism/api/resources/ManifestResource;", "Lsite/hackery/paramorphism/api/resources/Resource;", "manifest", "Ljava/util/jar/Manifest;", "order", "", "(Ljava/util/jar/Manifest;I)V", "data", "", "getData", "()[B", "paramorphism"}
)
public final class ManifestResource extends Resource {
   private final Manifest manifest;

   @NotNull
   public byte[] getData() {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      this.manifest.write((OutputStream)var1);
      return var1.toByteArray();
   }

   public ManifestResource(@NotNull Manifest var1, int var2) {
      super(var2, (DefaultConstructorMarker)null);
      this.manifest = var1;
   }

   public ManifestResource(Manifest var1, int var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = 1;
      }

      this(var1, var2);
   }
}
