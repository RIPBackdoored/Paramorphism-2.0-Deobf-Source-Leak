package kotlin.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u000b\u001a\u00020\f*\u00020\bH\u0002¢\u0006\u0002\b\r\u001a\u001c\u0010\u000e\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fH\u0000\u001a\f\u0010\u0011\u001a\u00020\u0012*\u00020\u0002H\u0000\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0018\u0010\u0004\u001a\u00020\u0002*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\"\u0018\u0010\u0007\u001a\u00020\b*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0013"},
   d2 = {"isRooted", "", "Ljava/io/File;", "(Ljava/io/File;)Z", "root", "getRoot", "(Ljava/io/File;)Ljava/io/File;", "rootName", "", "getRootName", "(Ljava/io/File;)Ljava/lang/String;", "getRootLength", "", "getRootLength$FilesKt__FilePathComponentsKt", "subPath", "beginIndex", "endIndex", "toComponents", "Lkotlin/io/FilePathComponents;", "kotlin-stdlib"},
   xs = "kotlin/io/FilesKt"
)
class FilesKt__FilePathComponentsKt {
   private static final int getRootLength$FilesKt__FilePathComponentsKt(@NotNull String var0) {
      int var1 = StringsKt.indexOf$default((CharSequence)var0, File.separatorChar, 0, false, 4, (Object)null);
      if (var1 == 0) {
         if (var0.length() > 1 && var0.charAt(1) == File.separatorChar) {
            var1 = StringsKt.indexOf$default((CharSequence)var0, File.separatorChar, 2, false, 4, (Object)null);
            if (var1 >= 0) {
               var1 = StringsKt.indexOf$default((CharSequence)var0, File.separatorChar, var1 + 1, false, 4, (Object)null);
               if (var1 >= 0) {
                  return var1 + 1;
               }

               return var0.length();
            }
         }

         return 1;
      } else if (var1 > 0 && var0.charAt(var1 - 1) == ':') {
         ++var1;
         return var1;
      } else {
         return var1 == -1 && StringsKt.endsWith$default((CharSequence)var0, ':', false, 2, (Object)null) ? var0.length() : 0;
      }
   }

   @NotNull
   public static final String getRootName(@NotNull File var0) {
      String var1 = var0.getPath();
      byte var2 = 0;
      int var3 = getRootLength$FilesKt__FilePathComponentsKt(var0.getPath());
      boolean var4 = false;
      if (var1 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         return var1.substring(var2, var3);
      }
   }

   @NotNull
   public static final File getRoot(@NotNull File var0) {
      return new File(FilesKt.getRootName(var0));
   }

   public static final boolean isRooted(@NotNull File var0) {
      return getRootLength$FilesKt__FilePathComponentsKt(var0.getPath()) > 0;
   }

   @NotNull
   public static final FilePathComponents toComponents(@NotNull File var0) {
      String var1 = var0.getPath();
      int var2 = getRootLength$FilesKt__FilePathComponentsKt(var1);
      byte var5 = 0;
      boolean var6 = false;
      String var3 = var1.substring(var5, var2);
      var6 = false;
      String var4 = var1.substring(var2);
      CharSequence var18 = (CharSequence)var4;
      boolean var7 = false;
      List var10000;
      if (var18.length() == 0) {
         var6 = false;
         var10000 = CollectionsKt.emptyList();
      } else {
         Iterable var19 = (Iterable)StringsKt.split$default((CharSequence)var4, new char[]{File.separatorChar}, false, 0, 6, (Object)null);
         var7 = false;
         Collection var9 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var19, 10)));
         boolean var10 = false;
         Iterator var11 = var19.iterator();

         while(var11.hasNext()) {
            Object var12 = var11.next();
            String var13 = (String)var12;
            boolean var14 = false;
            File var16 = new File(var13);
            var9.add(var16);
         }

         var10000 = (List)var9;
      }

      List var17 = var10000;
      return new FilePathComponents(new File(var3), var17);
   }

   @NotNull
   public static final File subPath(@NotNull File var0, int var1, int var2) {
      return FilesKt.toComponents(var0).subPath(var1, var2);
   }

   public FilesKt__FilePathComponentsKt() {
      super();
   }
}
