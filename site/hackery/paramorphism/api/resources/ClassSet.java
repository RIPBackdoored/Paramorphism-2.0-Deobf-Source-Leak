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
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\bH&J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H&J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H&¨\u0006\u000e"},
   d2 = {"Lsite/hackery/paramorphism/api/resources/ClassSet;", "Ljava/io/Closeable;", "all", "Lkotlin/sequences/Sequence;", "Lsite/hackery/paramorphism/api/resources/ClassInfo;", "contains", "", "name", "", "get", "names", "put", "", "info", "paramorphism"}
)
public interface ClassSet extends Closeable {
   @Nullable
   ClassInfo get(@NotNull String var1);

   void put(@NotNull ClassInfo var1);

   boolean contains(@NotNull String var1);

   @NotNull
   Sequence all();

   @NotNull
   Sequence names();
}
