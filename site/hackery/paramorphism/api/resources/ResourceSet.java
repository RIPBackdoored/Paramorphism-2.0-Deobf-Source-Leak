package site.hackery.paramorphism.api.resources;

import java.io.Closeable;
import kotlin.Metadata;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u0003H&J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H&J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\u0005H&J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0003H&J\u001a\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006H&¨\u0006\u000f"},
   d2 = {"Lsite/hackery/paramorphism/api/resources/ResourceSet;", "Ljava/io/Closeable;", "all", "Lkotlin/sequences/Sequence;", "Lkotlin/Pair;", "", "Lsite/hackery/paramorphism/api/resources/Resource;", "contains", "", "name", "get", "names", "put", "", "data", "paramorphism"}
)
public interface ResourceSet extends Closeable {
   @Nullable
   Resource get(@NotNull String var1);

   void put(@NotNull String var1, @Nullable Resource var2);

   boolean contains(@NotNull String var1);

   @NotNull
   Sequence all();

   @NotNull
   Sequence names();
}
