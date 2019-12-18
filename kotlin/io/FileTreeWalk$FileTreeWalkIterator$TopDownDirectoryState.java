package kotlin.io;

import java.io.File;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "", "step", "kotlin-stdlib"}
)
final class FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState extends FileTreeWalk$DirectoryState {
   private boolean rootVisited;
   private File[] fileList;
   private int fileIndex;
   final FileTreeWalk$FileTreeWalkIterator this$0;

   @Nullable
   public File step() {
      Function1 var5;
      if (!this.rootVisited) {
         var5 = FileTreeWalk.access$getOnEnter$p(this.this$0.this$0);
         if (var5 != null) {
            if (!(Boolean)var5.invoke(this.getRoot())) {
               return null;
            }
         }

         this.rootVisited = true;
         return this.getRoot();
      } else {
         Unit var3;
         if (this.fileList != null) {
            int var10000 = this.fileIndex;
            File[] var10001 = this.fileList;
            if (var10001 == null) {
               Intrinsics.throwNpe();
            }

            if (var10000 >= var10001.length) {
               var5 = FileTreeWalk.access$getOnLeave$p(this.this$0.this$0);
               if (var5 != null) {
                  var3 = (Unit)var5.invoke(this.getRoot());
               }

               return null;
            }
         }

         label65: {
            File[] var4;
            if (this.fileList == null) {
               this.fileList = this.getRoot().listFiles();
               if (this.fileList == null) {
                  Function2 var2 = FileTreeWalk.access$getOnFail$p(this.this$0.this$0);
                  if (var2 != null) {
                     var3 = (Unit)var2.invoke(this.getRoot(), new AccessDeniedException(this.getRoot(), (File)null, "Cannot list files in a directory", 2, (DefaultConstructorMarker)null));
                  }
               }

               if (this.fileList == null) {
                  break label65;
               }

               var4 = this.fileList;
               if (var4 == null) {
                  Intrinsics.throwNpe();
               }

               if (var4.length == 0) {
                  break label65;
               }
            }

            var4 = this.fileList;
            if (var4 == null) {
               Intrinsics.throwNpe();
            }

            int var1;
            this.fileIndex = (var1 = this.fileIndex) + 1;
            return var4[var1];
         }

         var5 = FileTreeWalk.access$getOnLeave$p(this.this$0.this$0);
         if (var5 != null) {
            var3 = (Unit)var5.invoke(this.getRoot());
         }

         return null;
      }
   }

   public FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState(@NotNull FileTreeWalk$FileTreeWalkIterator var1, File var2) {
      super(var2);
      this.this$0 = var1;
   }
}
