package kotlin.io;

import kotlin.*;
import java.io.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 5, xi = 1, d1 = { "\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0007" }, d2 = { "walk", "Lkotlin/io/FileTreeWalk;", "Ljava/io/File;", "direction", "Lkotlin/io/FileWalkDirection;", "walkBottomUp", "walkTopDown", "kotlin-stdlib" }, xs = "kotlin/io/FilesKt")
class FilesKt__FileTreeWalkKt extends FilesKt__FileReadWriteKt
{
    @NotNull
    public static final FileTreeWalk walk(@NotNull final File file, @NotNull final FileWalkDirection fileWalkDirection) {
        return new FileTreeWalk(file, fileWalkDirection);
    }
    
    public static FileTreeWalk walk$default(final File file, FileWalkDirection top_DOWN, final int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            top_DOWN = FileWalkDirection.TOP_DOWN;
        }
        return walk(file, top_DOWN);
    }
    
    @NotNull
    public static final FileTreeWalk walkTopDown(@NotNull final File file) {
        return walk(file, FileWalkDirection.TOP_DOWN);
    }
    
    @NotNull
    public static final FileTreeWalk walkBottomUp(@NotNull final File file) {
        return walk(file, FileWalkDirection.BOTTOM_UP);
    }
    
    public FilesKt__FileTreeWalkKt() {
        super();
    }
}
