package site.hackery.paramorphism.api.resources;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0001\u0003\u000b\f\r¨\u0006\u000e"},
   d2 = {"Lsite/hackery/paramorphism/api/resources/Resource;", "", "order", "", "(I)V", "data", "", "getData", "()[B", "getOrder", "()I", "Lsite/hackery/paramorphism/api/resources/BufferResource;", "Lsite/hackery/paramorphism/api/resources/LazyResource;", "Lsite/hackery/paramorphism/api/resources/ManifestResource;", "paramorphism"}
)
public abstract class Resource {
   private final int order;

   @NotNull
   public abstract byte[] getData();

   public final int getOrder() {
      return this.order;
   }

   private Resource(int var1) {
      super();
      this.order = var1;
   }

   public Resource(int var1, DefaultConstructorMarker var2) {
      this(var1);
   }
}
