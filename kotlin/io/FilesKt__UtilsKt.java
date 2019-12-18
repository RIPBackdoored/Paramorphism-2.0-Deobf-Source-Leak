package kotlin.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u001a(\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a(\u0010\r\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a8\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\u001a\b\u0002\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013\u001a&\u0010\u0016\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u000f*\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0002*\u00020\u0002\u001a\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d*\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0002¢\u0006\u0002\b\u001e\u001a\u0011\u0010\u001c\u001a\u00020\u001f*\u00020\u001fH\u0002¢\u0006\u0002\b\u001e\u001a\u0012\u0010 \u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0014\u0010\"\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u001b\u0010)\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002H\u0002¢\u0006\u0002\b*\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004¨\u0006+"},
   d2 = {"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"},
   xs = "kotlin/io/FilesKt"
)
class FilesKt__UtilsKt extends FilesKt__FileTreeWalkKt {
   @NotNull
   public static final File createTempDir(@NotNull String var0, @Nullable String var1, @Nullable File var2) {
      File var3 = File.createTempFile(var0, var1, var2);
      var3.delete();
      if (var3.mkdir()) {
         return var3;
      } else {
         throw (Throwable)(new IOException("Unable to create temporary directory " + var3 + '.'));
      }
   }

   public static File createTempDir$default(String var0, String var1, File var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var0 = "tmp";
      }

      if ((var3 & 2) != 0) {
         var1 = (String)null;
      }

      if ((var3 & 4) != 0) {
         var2 = (File)null;
      }

      return FilesKt.createTempDir(var0, var1, var2);
   }

   @NotNull
   public static final File createTempFile(@NotNull String var0, @Nullable String var1, @Nullable File var2) {
      return File.createTempFile(var0, var1, var2);
   }

   public static File createTempFile$default(String var0, String var1, File var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var0 = "tmp";
      }

      if ((var3 & 2) != 0) {
         var1 = (String)null;
      }

      if ((var3 & 4) != 0) {
         var2 = (File)null;
      }

      return FilesKt.createTempFile(var0, var1, var2);
   }

   @NotNull
   public static final String getExtension(@NotNull File var0) {
      return StringsKt.substringAfterLast(var0.getName(), '.', "");
   }

   @NotNull
   public static final String getInvariantSeparatorsPath(@NotNull File var0) {
      return File.separatorChar != '/' ? StringsKt.replace$default(var0.getPath(), File.separatorChar, '/', false, 4, (Object)null) : var0.getPath();
   }

   @NotNull
   public static final String getNameWithoutExtension(@NotNull File var0) {
      return StringsKt.substringBeforeLast$default(var0.getName(), ".", (String)null, 2, (Object)null);
   }

   @NotNull
   public static final String toRelativeString(@NotNull File var0, @NotNull File var1) {
      String var10000 = toRelativeStringOrNull$FilesKt__UtilsKt(var0, var1);
      if (var10000 != null) {
         return var10000;
      } else {
         throw (Throwable)(new IllegalArgumentException("this and base files have different roots: " + var0 + " and " + var1 + '.'));
      }
   }

   @NotNull
   public static final File relativeTo(@NotNull File var0, @NotNull File var1) {
      return new File(FilesKt.toRelativeString(var0, var1));
   }

   @NotNull
   public static final File relativeToOrSelf(@NotNull File var0, @NotNull File var1) {
      String var10000 = toRelativeStringOrNull$FilesKt__UtilsKt(var0, var1);
      File var7;
      if (var10000 != null) {
         String var2 = var10000;
         boolean var3 = false;
         boolean var4 = false;
         boolean var6 = false;
         var7 = new File(var2);
      } else {
         var7 = var0;
      }

      return var7;
   }

   @Nullable
   public static final File relativeToOrNull(@NotNull File var0, @NotNull File var1) {
      String var10000 = toRelativeStringOrNull$FilesKt__UtilsKt(var0, var1);
      File var7;
      if (var10000 != null) {
         String var2 = var10000;
         boolean var3 = false;
         boolean var4 = false;
         boolean var6 = false;
         var7 = new File(var2);
      } else {
         var7 = null;
      }

      return var7;
   }

   private static final String toRelativeStringOrNull$FilesKt__UtilsKt(@NotNull File var0, File var1) {
      FilePathComponents var2 = normalize$FilesKt__UtilsKt(FilesKt.toComponents(var0));
      FilePathComponents var3 = normalize$FilesKt__UtilsKt(FilesKt.toComponents(var1));
      if ((Intrinsics.areEqual((Object)var2.getRoot(), (Object)var3.getRoot()) ^ 1) != 0) {
         return null;
      } else {
         int var4 = var3.getSize();
         int var5 = var2.getSize();
         boolean var8 = false;
         boolean var9 = false;
         boolean var11 = false;
         int var12 = 0;
         boolean var15 = false;

         for(int var16 = Math.min(var5, var4); var12 < var16 && Intrinsics.areEqual((Object)((File)var2.getSegments().get(var12)), (Object)((File)var3.getSegments().get(var12))); ++var12) {
         }

         int var6 = var12;
         StringBuilder var7 = new StringBuilder();
         int var17 = var4 - 1;
         int var18 = var12;
         if (var17 >= var12) {
            label55: {
               while(!Intrinsics.areEqual((Object)((File)var3.getSegments().get(var17)).getName(), (Object)"..")) {
                  var7.append("..");
                  if (var17 != var6) {
                     var7.append(File.separatorChar);
                  }

                  if (var17 == var18) {
                     break label55;
                  }

                  --var17;
               }

               return null;
            }
         }

         if (var6 < var5) {
            if (var6 < var4) {
               var7.append(File.separatorChar);
            }

            CollectionsKt.joinTo$default((Iterable)CollectionsKt.drop((Iterable)var2.getSegments(), var6), (Appendable)var7, (CharSequence)File.separator, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null);
         }

         return var7.toString();
      }
   }

   @NotNull
   public static final File copyTo(@NotNull File var0, @NotNull File var1, boolean var2, int var3) {
      if (!var0.exists()) {
         throw (Throwable)(new NoSuchFileException(var0, (File)null, "The source file doesn't exist.", 2, (DefaultConstructorMarker)null));
      } else {
         if (var1.exists()) {
            boolean var4 = !var2 ? true : !var1.delete();
            if (var4) {
               throw (Throwable)(new FileAlreadyExistsException(var0, var1, "The destination file already exists."));
            }
         }

         if (var0.isDirectory()) {
            if (!var1.mkdirs()) {
               throw (Throwable)(new FileSystemException(var0, var1, "Failed to create target directory."));
            }
         } else {
            File var10000 = var1.getParentFile();
            if (var10000 != null) {
               var10000.mkdirs();
            }

            boolean var5 = false;
            Closeable var30 = (Closeable)(new FileInputStream(var0));
            var5 = false;
            Throwable var6 = (Throwable)null;
            boolean var20 = false;

            try {
               var20 = true;
               FileInputStream var7 = (FileInputStream)var30;
               boolean var9 = false;
               boolean var11 = false;
               Closeable var10 = (Closeable)(new FileOutputStream(var1));
               var11 = false;
               Throwable var12 = (Throwable)null;
               boolean var25 = false;

               try {
                  var25 = true;
                  FileOutputStream var13 = (FileOutputStream)var10;
                  boolean var15 = false;
                  long var31 = ByteStreamsKt.copyTo((InputStream)var7, (OutputStream)var13, var3);
                  var25 = false;
               } catch (Throwable var26) {
                  var12 = var26;
                  throw var26;
               } finally {
                  if (var25) {
                     CloseableKt.closeFinally(var10, var12);
                  }
               }

               CloseableKt.closeFinally(var10, var12);
               var20 = false;
            } catch (Throwable var28) {
               var6 = var28;
               throw var28;
            } finally {
               if (var20) {
                  CloseableKt.closeFinally(var30, var6);
               }
            }

            CloseableKt.closeFinally(var30, var6);
         }

         return var1;
      }
   }

   public static File copyTo$default(File var0, File var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 8192;
      }

      return FilesKt.copyTo(var0, var1, var2, var3);
   }

   public static final boolean copyRecursively(@NotNull File var0, @NotNull File var1, boolean var2, @NotNull Function2 var3) {
      if (!var0.exists()) {
         return (OnErrorAction)var3.invoke(var0, new NoSuchFileException(var0, (File)null, "The source file doesn't exist.", 2, (DefaultConstructorMarker)null)) != OnErrorAction.TERMINATE;
      } else {
         try {
            Iterator var5 = FilesKt.walkTopDown(var0).onFail((Function2)(new FilesKt__UtilsKt$copyRecursively$2(var3))).iterator();

            File var4;
            label66:
            do {
               while(var5.hasNext()) {
                  var4 = (File)var5.next();
                  if (!var4.exists()) {
                     continue label66;
                  }

                  String var6 = FilesKt.toRelativeString(var4, var0);
                  File var7 = new File(var1, var6);
                  if (var7.exists() && (!var4.isDirectory() || !var7.isDirectory())) {
                     boolean var8 = !var2 ? true : (var7.isDirectory() ? !FilesKt.deleteRecursively(var7) : !var7.delete());
                     if (var8) {
                        if ((OnErrorAction)var3.invoke(var7, new FileAlreadyExistsException(var4, var7, "The destination file already exists.")) == OnErrorAction.TERMINATE) {
                           return false;
                        }
                        continue;
                     }
                  }

                  if (var4.isDirectory()) {
                     var7.mkdirs();
                  } else if (FilesKt.copyTo$default(var4, var7, var2, 0, 4, (Object)null).length() != var4.length() && (OnErrorAction)var3.invoke(var4, new IOException("Source file wasn't copied completely, length of destination file differs.")) == OnErrorAction.TERMINATE) {
                     return false;
                  }
               }

               return true;
            } while((OnErrorAction)var3.invoke(var4, new NoSuchFileException(var4, (File)null, "The source file doesn't exist.", 2, (DefaultConstructorMarker)null)) != OnErrorAction.TERMINATE);

            return false;
         } catch (TerminateException var9) {
            return false;
         }
      }
   }

   public static boolean copyRecursively$default(File var0, File var1, boolean var2, Function2 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = (Function2)FilesKt__UtilsKt$copyRecursively$1.INSTANCE;
      }

      return FilesKt.copyRecursively(var0, var1, var2, var3);
   }

   public static final boolean deleteRecursively(@NotNull File var0) {
      Sequence var1 = (Sequence)FilesKt.walkBottomUp(var0);
      boolean var2 = true;
      boolean var3 = false;
      boolean var4 = var2;

      File var7;
      for(Iterator var5 = var1.iterator(); var5.hasNext(); var4 = (var7.delete() || !var7.exists()) && var4) {
         Object var6 = var5.next();
         var7 = (File)var6;
         boolean var9 = false;
      }

      return var4;
   }

   public static final boolean startsWith(@NotNull File var0, @NotNull File var1) {
      FilePathComponents var2 = FilesKt.toComponents(var0);
      FilePathComponents var3 = FilesKt.toComponents(var1);
      if ((Intrinsics.areEqual((Object)var2.getRoot(), (Object)var3.getRoot()) ^ 1) != 0) {
         return false;
      } else {
         return var2.getSize() < var3.getSize() ? false : var2.getSegments().subList(0, var3.getSize()).equals(var3.getSegments());
      }
   }

   public static final boolean startsWith(@NotNull File var0, @NotNull String var1) {
      return FilesKt.startsWith(var0, new File(var1));
   }

   public static final boolean endsWith(@NotNull File var0, @NotNull File var1) {
      FilePathComponents var2 = FilesKt.toComponents(var0);
      FilePathComponents var3 = FilesKt.toComponents(var1);
      if (var3.isRooted()) {
         return Intrinsics.areEqual((Object)var0, (Object)var1);
      } else {
         int var4 = var2.getSize() - var3.getSize();
         return var4 < 0 ? false : var2.getSegments().subList(var4, var2.getSize()).equals(var3.getSegments());
      }
   }

   public static final boolean endsWith(@NotNull File var0, @NotNull String var1) {
      return FilesKt.endsWith(var0, new File(var1));
   }

   @NotNull
   public static final File normalize(@NotNull File var0) {
      FilePathComponents var1 = FilesKt.toComponents(var0);
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      return FilesKt.resolve(var1.getRoot(), CollectionsKt.joinToString$default((Iterable)normalize$FilesKt__UtilsKt(var1.getSegments()), (CharSequence)File.separator, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null));
   }

   private static final FilePathComponents normalize$FilesKt__UtilsKt(@NotNull FilePathComponents var0) {
      return new FilePathComponents(var0.getRoot(), normalize$FilesKt__UtilsKt(var0.getSegments()));
   }

   private static final List normalize$FilesKt__UtilsKt(@NotNull List var0) {
      List var1 = (List)(new ArrayList(var0.size()));
      Iterator var3 = var0.iterator();

      while(true) {
         while(true) {
            while(var3.hasNext()) {
               File var2 = (File)var3.next();
               String var10000 = var2.getName();
               if (var10000 != null) {
                  String var4 = var10000;
                  switch(var4.hashCode()) {
                  case 46:
                     if (var4.equals(".")) {
                        continue;
                     }
                     break;
                  case 1472:
                     if (var4.equals("..")) {
                        if (!var1.isEmpty() && (Intrinsics.areEqual((Object)((File)CollectionsKt.last(var1)).getName(), (Object)"..") ^ 1) != 0) {
                           var1.remove(var1.size() - 1);
                           continue;
                        }

                        var1.add(var2);
                        continue;
                     }
                  }
               }

               var1.add(var2);
            }

            return var1;
         }
      }
   }

   @NotNull
   public static final File resolve(@NotNull File var0, @NotNull File var1) {
      if (FilesKt.isRooted(var1)) {
         return var1;
      } else {
         String var2 = var0.toString();
         CharSequence var3 = (CharSequence)var2;
         boolean var4 = false;
         return var3.length() != 0 && !StringsKt.endsWith$default((CharSequence)var2, File.separatorChar, false, 2, (Object)null) ? new File(var2 + File.separatorChar + var1) : new File(var2 + var1);
      }
   }

   @NotNull
   public static final File resolve(@NotNull File var0, @NotNull String var1) {
      return FilesKt.resolve(var0, new File(var1));
   }

   @NotNull
   public static final File resolveSibling(@NotNull File var0, @NotNull File var1) {
      FilePathComponents var2 = FilesKt.toComponents(var0);
      File var3 = var2.getSize() == 0 ? new File("..") : var2.subPath(0, var2.getSize() - 1);
      return FilesKt.resolve(FilesKt.resolve(var2.getRoot(), var3), var1);
   }

   @NotNull
   public static final File resolveSibling(@NotNull File var0, @NotNull String var1) {
      return FilesKt.resolveSibling(var0, new File(var1));
   }

   public FilesKt__UtilsKt() {
      super();
   }
}
