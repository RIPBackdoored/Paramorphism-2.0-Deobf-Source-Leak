package kotlin.io;

import java.io.File;
import java.util.ArrayDeque;
import kotlin.Metadata;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\r\u000e\u000fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0002J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0082\u0010R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
   d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;", "Lkotlin/collections/AbstractIterator;", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk;)V", "state", "Ljava/util/ArrayDeque;", "Lkotlin/io/FileTreeWalk$WalkState;", "computeNext", "", "directoryState", "Lkotlin/io/FileTreeWalk$DirectoryState;", "root", "gotoNext", "BottomUpDirectoryState", "SingleFileState", "TopDownDirectoryState", "kotlin-stdlib"}
)
final class FileTreeWalk$FileTreeWalkIterator extends AbstractIterator {
   private final ArrayDeque state;
   final FileTreeWalk this$0;

   protected void computeNext() {
      File var1 = this.gotoNext();
      if (var1 != null) {
         this.setNext(var1);
      } else {
         this.done();
      }

   }

   private final FileTreeWalk$DirectoryState directoryState(File var1) {
      // $FF: Couldn't be decompiled
   }

   private final File gotoNext() {
      while(true) {
         FileTreeWalk$WalkState var10000 = (FileTreeWalk$WalkState)this.state.peek();
         if (var10000 != null) {
            FileTreeWalk$WalkState var1 = var10000;
            File var2 = var1.step();
            if (var2 == null) {
               this.state.pop();
               continue;
            }

            if (!Intrinsics.areEqual((Object)var2, (Object)var1.getRoot()) && var2.isDirectory() && this.state.size() < FileTreeWalk.access$getMaxDepth$p(this.this$0)) {
               this.state.push(this.directoryState(var2));
               continue;
            }

            return var2;
         }

         return null;
      }
   }

   public FileTreeWalk$FileTreeWalkIterator(FileTreeWalk var1) {
      super();
      this.this$0 = var1;
      this.state = new ArrayDeque();
      if (FileTreeWalk.access$getStart$p(var1).isDirectory()) {
         this.state.push(this.directoryState(FileTreeWalk.access$getStart$p(var1)));
      } else if (FileTreeWalk.access$getStart$p(var1).isFile()) {
         this.state.push(new FileTreeWalk$FileTreeWalkIterator$SingleFileState(this, FileTreeWalk.access$getStart$p(var1)));
      } else {
         this.done();
      }

   }
}
