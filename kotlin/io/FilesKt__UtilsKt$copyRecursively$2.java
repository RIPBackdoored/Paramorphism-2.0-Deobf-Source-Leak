package kotlin.io;

import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0002\b\u0006"},
   d2 = {"<anonymous>", "", "f", "Ljava/io/File;", "e", "Ljava/io/IOException;", "invoke"}
)
final class FilesKt__UtilsKt$copyRecursively$2 extends Lambda implements Function2 {
   final Function2 $onError;

   public Object invoke(Object var1, Object var2) {
      this.invoke((File)var1, (IOException)var2);
      return Unit.INSTANCE;
   }

   public final void invoke(@NotNull File var1, @NotNull IOException var2) {
      if ((OnErrorAction)this.$onError.invoke(var1, var2) == OnErrorAction.TERMINATE) {
         throw (Throwable)(new TerminateException(var1));
      }
   }

   FilesKt__UtilsKt$copyRecursively$2(Function2 var1) {
      super(2);
      this.$onError = var1;
   }
}
