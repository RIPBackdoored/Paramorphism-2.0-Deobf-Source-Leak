package kotlin.io;

import java.io.File;
import kotlin.Metadata;
import kotlin._Assertions;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"},
   d2 = {"Lkotlin/io/FileTreeWalk$DirectoryState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootDir", "Ljava/io/File;", "(Ljava/io/File;)V", "kotlin-stdlib"}
)
abstract class FileTreeWalk$DirectoryState extends FileTreeWalk$WalkState {
   public FileTreeWalk$DirectoryState(@NotNull File var1) {
      super(var1);
      if (_Assertions.ENABLED) {
         boolean var2 = var1.isDirectory();
         boolean var3 = false;
         if (_Assertions.ENABLED && !var2) {
            boolean var4 = false;
            String var5 = "rootDir must be verified to be directory beforehand.";
            throw (Throwable)(new AssertionError(var5));
         }
      }

   }
}
