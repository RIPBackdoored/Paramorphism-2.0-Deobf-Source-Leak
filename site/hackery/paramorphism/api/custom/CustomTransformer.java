package site.hackery.paramorphism.api.custom;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.resources.ClassInfo;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u001b\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&¢\u0006\u0002\u0010\u0007J\u0015\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005H\u0016¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H&¨\u0006\f"},
   d2 = {"Lsite/hackery/paramorphism/api/custom/CustomTransformer;", "", "initialize", "", "classes", "", "Lsite/hackery/paramorphism/api/resources/ClassInfo;", "([Lsite/hackery/paramorphism/api/resources/ClassInfo;)V", "injectClasses", "()[Lsite/hackery/paramorphism/api/resources/ClassInfo;", "visitClass", "info", "paramorphism"}
)
public interface CustomTransformer {
   void initialize(@NotNull ClassInfo[] var1);

   @NotNull
   ClassInfo visitClass(@NotNull ClassInfo var1);

   @Nullable
   ClassInfo[] injectClasses();
}
