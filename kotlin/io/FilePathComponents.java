package kotlin.io;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\r\b\u0080\b\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J#\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u0013HÖ\u0001J\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u0013J\t\u0010\u001f\u001a\u00020\rHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006 "},
   d2 = {"Lkotlin/io/FilePathComponents;", "", "root", "Ljava/io/File;", "segments", "", "(Ljava/io/File;Ljava/util/List;)V", "isRooted", "", "()Z", "getRoot", "()Ljava/io/File;", "rootName", "", "getRootName", "()Ljava/lang/String;", "getSegments", "()Ljava/util/List;", "size", "", "getSize", "()I", "component1", "component2", "copy", "equals", "other", "hashCode", "subPath", "beginIndex", "endIndex", "toString", "kotlin-stdlib"}
)
public final class FilePathComponents {
   @NotNull
   private final File root;
   @NotNull
   private final List segments;

   @NotNull
   public final String getRootName() {
      return this.root.getPath();
   }

   public final boolean isRooted() {
      CharSequence var1 = (CharSequence)this.root.getPath();
      boolean var2 = false;
      return var1.length() > 0;
   }

   public final int getSize() {
      return this.segments.size();
   }

   @NotNull
   public final File subPath(int var1, int var2) {
      if (var1 >= 0 && var1 <= var2 && var2 <= this.getSize()) {
         return new File(CollectionsKt.joinToString$default((Iterable)this.segments.subList(var1, var2), (CharSequence)File.separator, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null));
      } else {
         throw (Throwable)(new IllegalArgumentException());
      }
   }

   @NotNull
   public final File getRoot() {
      return this.root;
   }

   @NotNull
   public final List getSegments() {
      return this.segments;
   }

   public FilePathComponents(@NotNull File var1, @NotNull List var2) {
      super();
      this.root = var1;
      this.segments = var2;
   }

   @NotNull
   public final File component1() {
      return this.root;
   }

   @NotNull
   public final List component2() {
      return this.segments;
   }

   @NotNull
   public final FilePathComponents copy(@NotNull File var1, @NotNull List var2) {
      return new FilePathComponents(var1, var2);
   }

   public static FilePathComponents copy$default(FilePathComponents var0, File var1, List var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.root;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.segments;
      }

      return var0.copy(var1, var2);
   }

   @NotNull
   public String toString() {
      return "FilePathComponents(root=" + this.root + ", segments=" + this.segments + ")";
   }

   public int hashCode() {
      File var10000 = this.root;
      int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
      List var10001 = this.segments;
      return var1 + (var10001 != null ? var10001.hashCode() : 0);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof FilePathComponents) {
            FilePathComponents var2 = (FilePathComponents)var1;
            if (Intrinsics.areEqual((Object)this.root, (Object)var2.root) && Intrinsics.areEqual((Object)this.segments, (Object)var2.segments)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
