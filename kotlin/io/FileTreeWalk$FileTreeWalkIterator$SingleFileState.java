package kotlin.io;

import java.io.File;
import kotlin.Metadata;
import kotlin._Assertions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"},
   d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$SingleFileState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootFile", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "visited", "", "step", "kotlin-stdlib"}
)
final class FileTreeWalk$FileTreeWalkIterator$SingleFileState extends FileTreeWalk$WalkState {
   private boolean visited;
   final FileTreeWalk$FileTreeWalkIterator this$0;

   @Nullable
   public File step() {
      if (this.visited) {
         return null;
      } else {
         this.visited = true;
         return this.getRoot();
      }
   }

   public FileTreeWalk$FileTreeWalkIterator$SingleFileState(@NotNull FileTreeWalk$FileTreeWalkIterator var1, File var2) {
      super(var2);
      this.this$0 = var1;
      if (_Assertions.ENABLED) {
         boolean var3 = var2.isFile();
         boolean var4 = false;
         if (_Assertions.ENABLED && !var3) {
            boolean var5 = false;
            String var6 = "rootFile must be verified to be file beforehand.";
            throw (Throwable)(new AssertionError(var6));
         }
      }

   }
}
