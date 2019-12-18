package kotlin.io;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"},
   d2 = {"Lkotlin/io/FileWalkDirection;", "", "(Ljava/lang/String;I)V", "TOP_DOWN", "BOTTOM_UP", "kotlin-stdlib"}
)
public final class FileWalkDirection extends Enum {
   public static final FileWalkDirection TOP_DOWN;
   public static final FileWalkDirection BOTTOM_UP;
   private static final FileWalkDirection[] $VALUES = new FileWalkDirection[]{TOP_DOWN = new FileWalkDirection("TOP_DOWN", 0), BOTTOM_UP = new FileWalkDirection("BOTTOM_UP", 1)};

   private FileWalkDirection(String var1, int var2) {
      super(var1, var2);
   }

   public static FileWalkDirection[] values() {
      return (FileWalkDirection[])$VALUES.clone();
   }

   public static FileWalkDirection valueOf(String var0) {
      return (FileWalkDirection)Enum.valueOf(FileWalkDirection.class, var0);
   }
}
