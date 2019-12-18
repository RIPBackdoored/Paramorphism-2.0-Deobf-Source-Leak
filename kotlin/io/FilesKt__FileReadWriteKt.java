package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000z\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a!\u0010\n\u001a\u00020\u000b*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001a!\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001aB\u0010\u0010\u001a\u00020\u0001*\u00020\u000226\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001aJ\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\r26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001a7\u0010\u0018\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0019\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0002H\u0087\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0002H\u0087\b\u001a\u0017\u0010\u001f\u001a\u00020 *\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a\n\u0010!\u001a\u00020\u0004*\u00020\u0002\u001a\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0014\u0010$\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010%\u001a\u00020&*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a?\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010(*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010)\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070*\u0012\u0004\u0012\u0002H(0\u0019H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010,\u001a\u0012\u0010-\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010.\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010/\u001a\u000200*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u0082\u0002\b\n\u0006\b\u0011(+0\u0001¨\u00061"},
   d2 = {"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"},
   xs = "kotlin/io/FilesKt"
)
class FilesKt__FileReadWriteKt extends FilesKt__FilePathComponentsKt {
   @InlineOnly
   private static final InputStreamReader reader(@NotNull File var0, Charset var1) {
      byte var2 = 0;
      boolean var4 = false;
      InputStream var3 = (InputStream)(new FileInputStream(var0));
      var4 = false;
      return new InputStreamReader(var3, var1);
   }

   static InputStreamReader reader$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      boolean var5 = false;
      boolean var4 = false;
      InputStream var6 = (InputStream)(new FileInputStream(var0));
      var4 = false;
      return new InputStreamReader(var6, var1);
   }

   @InlineOnly
   private static final BufferedReader bufferedReader(@NotNull File var0, Charset var1, int var2) {
      byte var3 = 0;
      boolean var5 = false;
      boolean var7 = false;
      InputStream var6 = (InputStream)(new FileInputStream(var0));
      var7 = false;
      Reader var4 = (Reader)(new InputStreamReader(var6, var1));
      var5 = false;
      return var4 instanceof BufferedReader ? (BufferedReader)var4 : new BufferedReader(var4, var2);
   }

   static BufferedReader bufferedReader$default(File var0, Charset var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      boolean var8 = false;
      boolean var5 = false;
      boolean var7 = false;
      InputStream var6 = (InputStream)(new FileInputStream(var0));
      var7 = false;
      Reader var9 = (Reader)(new InputStreamReader(var6, var1));
      var5 = false;
      return var9 instanceof BufferedReader ? (BufferedReader)var9 : new BufferedReader(var9, var2);
   }

   @InlineOnly
   private static final OutputStreamWriter writer(@NotNull File var0, Charset var1) {
      byte var2 = 0;
      boolean var4 = false;
      OutputStream var3 = (OutputStream)(new FileOutputStream(var0));
      var4 = false;
      return new OutputStreamWriter(var3, var1);
   }

   static OutputStreamWriter writer$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      boolean var5 = false;
      boolean var4 = false;
      OutputStream var6 = (OutputStream)(new FileOutputStream(var0));
      var4 = false;
      return new OutputStreamWriter(var6, var1);
   }

   @InlineOnly
   private static final BufferedWriter bufferedWriter(@NotNull File var0, Charset var1, int var2) {
      byte var3 = 0;
      boolean var5 = false;
      boolean var7 = false;
      OutputStream var6 = (OutputStream)(new FileOutputStream(var0));
      var7 = false;
      Writer var4 = (Writer)(new OutputStreamWriter(var6, var1));
      var5 = false;
      return var4 instanceof BufferedWriter ? (BufferedWriter)var4 : new BufferedWriter(var4, var2);
   }

   static BufferedWriter bufferedWriter$default(File var0, Charset var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      boolean var8 = false;
      boolean var5 = false;
      boolean var7 = false;
      OutputStream var6 = (OutputStream)(new FileOutputStream(var0));
      var7 = false;
      Writer var9 = (Writer)(new OutputStreamWriter(var6, var1));
      var5 = false;
      return var9 instanceof BufferedWriter ? (BufferedWriter)var9 : new BufferedWriter(var9, var2);
   }

   @InlineOnly
   private static final PrintWriter printWriter(@NotNull File var0, Charset var1) {
      byte var2 = 0;
      short var4 = 8192;
      boolean var5 = false;
      boolean var7 = false;
      boolean var9 = false;
      OutputStream var8 = (OutputStream)(new FileOutputStream(var0));
      var9 = false;
      Writer var6 = (Writer)(new OutputStreamWriter(var8, var1));
      var7 = false;
      BufferedWriter var12 = var6 instanceof BufferedWriter ? (BufferedWriter)var6 : new BufferedWriter(var6, var4);
      Writer var13 = (Writer)var12;
      return new PrintWriter(var13);
   }

   static PrintWriter printWriter$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      boolean var14 = false;
      short var4 = 8192;
      boolean var5 = false;
      boolean var7 = false;
      boolean var9 = false;
      OutputStream var8 = (OutputStream)(new FileOutputStream(var0));
      var9 = false;
      Writer var6 = (Writer)(new OutputStreamWriter(var8, var1));
      var7 = false;
      BufferedWriter var12 = var6 instanceof BufferedWriter ? (BufferedWriter)var6 : new BufferedWriter(var6, var4);
      Writer var13 = (Writer)var12;
      return new PrintWriter(var13);
   }

   @NotNull
   public static final byte[] readBytes(@NotNull File var0) {
      boolean var2 = false;
      Closeable var1 = (Closeable)(new FileInputStream(var0));
      var2 = false;
      Throwable var3 = (Throwable)null;
      boolean var17 = false;

      byte[] var20;
      try {
         var17 = true;
         FileInputStream var4 = (FileInputStream)var1;
         boolean var5 = false;
         int var6 = 0;
         long var7 = var0.length();
         boolean var9 = false;
         boolean var10 = false;
         boolean var13 = false;
         if (var7 > (long)0) {
            throw (Throwable)(new OutOfMemoryError("File " + var0 + " is too big (" + var7 + " bytes) to fit in memory."));
         }

         int var14 = (int)var7;
         byte[] var21 = new byte[var14];

         while(true) {
            if (var14 > 0) {
               int var22 = var4.read(var21, var6, var14);
               if (var22 >= 0) {
                  var14 -= var22;
                  var6 += var22;
                  continue;
               }
            }

            byte[] var10000;
            if (var14 == 0) {
               var10000 = var21;
            } else {
               var10 = false;
               var10000 = Arrays.copyOf(var21, var6);
            }

            var20 = var10000;
            var17 = false;
            break;
         }
      } catch (Throwable var18) {
         var3 = var18;
         throw var18;
      } finally {
         if (var17) {
            CloseableKt.closeFinally(var1, var3);
         }
      }

      CloseableKt.closeFinally(var1, var3);
      return var20;
   }

   public static final void writeBytes(@NotNull File var0, @NotNull byte[] var1) {
      Closeable var2 = (Closeable)(new FileOutputStream(var0));
      boolean var3 = false;
      Throwable var4 = (Throwable)null;
      boolean var9 = false;

      try {
         var9 = true;
         FileOutputStream var5 = (FileOutputStream)var2;
         boolean var6 = false;
         var5.write(var1);
         Unit var12 = Unit.INSTANCE;
         var9 = false;
      } catch (Throwable var10) {
         var4 = var10;
         throw var10;
      } finally {
         if (var9) {
            CloseableKt.closeFinally(var2, var4);
         }
      }

      CloseableKt.closeFinally(var2, var4);
   }

   public static final void appendBytes(@NotNull File var0, @NotNull byte[] var1) {
      Closeable var2 = (Closeable)(new FileOutputStream(var0, true));
      boolean var3 = false;
      Throwable var4 = (Throwable)null;
      boolean var9 = false;

      try {
         var9 = true;
         FileOutputStream var5 = (FileOutputStream)var2;
         boolean var6 = false;
         var5.write(var1);
         Unit var12 = Unit.INSTANCE;
         var9 = false;
      } catch (Throwable var10) {
         var4 = var10;
         throw var10;
      } finally {
         if (var9) {
            CloseableKt.closeFinally(var2, var4);
         }
      }

      CloseableKt.closeFinally(var2, var4);
   }

   @NotNull
   public static final String readText(@NotNull File var0, @NotNull Charset var1) {
      byte[] var2 = FilesKt.readBytes(var0);
      boolean var3 = false;
      boolean var4 = false;
      return new String(var2, var1);
   }

   public static String readText$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return FilesKt.readText(var0, var1);
   }

   public static final void writeText(@NotNull File var0, @NotNull String var1, @NotNull Charset var2) {
      boolean var4 = false;
      byte[] var6 = var1.getBytes(var2);
      FilesKt.writeBytes(var0, var6);
   }

   public static void writeText$default(File var0, String var1, Charset var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = Charsets.UTF_8;
      }

      FilesKt.writeText(var0, var1, var2);
   }

   public static final void appendText(@NotNull File var0, @NotNull String var1, @NotNull Charset var2) {
      boolean var4 = false;
      byte[] var6 = var1.getBytes(var2);
      FilesKt.appendBytes(var0, var6);
   }

   public static void appendText$default(File var0, String var1, Charset var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = Charsets.UTF_8;
      }

      FilesKt.appendText(var0, var1, var2);
   }

   public static final void forEachBlock(@NotNull File var0, @NotNull Function2 var1) {
      FilesKt.forEachBlock(var0, 4096, var1);
   }

   public static final void forEachBlock(@NotNull File var0, int var1, @NotNull Function2 var2) {
      byte[] var3 = new byte[RangesKt.coerceAtLeast(var1, 512)];
      boolean var5 = false;
      Closeable var4 = (Closeable)(new FileInputStream(var0));
      var5 = false;
      Throwable var6 = (Throwable)null;
      boolean var12 = false;

      try {
         var12 = true;
         FileInputStream var7 = (FileInputStream)var4;
         boolean var8 = false;

         while(true) {
            int var9 = var7.read(var3);
            if (var9 <= 0) {
               Unit var15 = Unit.INSTANCE;
               var12 = false;
               break;
            }

            var2.invoke(var3, var9);
         }
      } catch (Throwable var13) {
         var6 = var13;
         throw var13;
      } finally {
         if (var12) {
            CloseableKt.closeFinally(var4, var6);
         }
      }

      CloseableKt.closeFinally(var4, var6);
   }

   public static final void forEachLine(@NotNull File var0, @NotNull Charset var1, @NotNull Function1 var2) {
      TextStreamsKt.forEachLine((Reader)(new BufferedReader((Reader)(new InputStreamReader((InputStream)(new FileInputStream(var0)), var1)))), var2);
   }

   public static void forEachLine$default(File var0, Charset var1, Function1 var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      FilesKt.forEachLine(var0, var1, var2);
   }

   @InlineOnly
   private static final FileInputStream inputStream(@NotNull File var0) {
      byte var1 = 0;
      return new FileInputStream(var0);
   }

   @InlineOnly
   private static final FileOutputStream outputStream(@NotNull File var0) {
      byte var1 = 0;
      return new FileOutputStream(var0);
   }

   @NotNull
   public static final List readLines(@NotNull File var0, @NotNull Charset var1) {
      ArrayList var2 = new ArrayList();
      FilesKt.forEachLine(var0, var1, (Function1)(new FilesKt__FileReadWriteKt$readLines$1(var2)));
      return (List)var2;
   }

   public static List readLines$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return FilesKt.readLines(var0, var1);
   }

   public static final Object useLines(@NotNull File var0, @NotNull Charset var1, @NotNull Function1 var2) {
      byte var3 = 0;
      short var5 = 8192;
      boolean var6 = false;
      boolean var8 = false;
      boolean var10 = false;
      InputStream var9 = (InputStream)(new FileInputStream(var0));
      var10 = false;
      Reader var7 = (Reader)(new InputStreamReader(var9, var1));
      var8 = false;
      Closeable var4 = (Closeable)(var7 instanceof BufferedReader ? (BufferedReader)var7 : new BufferedReader(var7, var5));
      boolean var18 = false;
      Throwable var19 = (Throwable)null;
      boolean var14 = false;

      Object var21;
      try {
         var14 = true;
         BufferedReader var20 = (BufferedReader)var4;
         var8 = false;
         var21 = var2.invoke(TextStreamsKt.lineSequence(var20));
         var14 = false;
      } catch (Throwable var16) {
         var19 = var16;
         throw var16;
      } finally {
         if (var14) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               CloseableKt.closeFinally(var4, var19);
            } else if (var19 == null) {
               var4.close();
            } else {
               try {
                  var4.close();
               } catch (Throwable var15) {
               }
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         CloseableKt.closeFinally(var4, var19);
      } else {
         var4.close();
      }

      InlineMarker.finallyEnd(1);
      return var21;
   }

   public static Object useLines$default(File var0, Charset var1, Function1 var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      boolean var18 = false;
      short var5 = 8192;
      boolean var6 = false;
      boolean var8 = false;
      boolean var10 = false;
      InputStream var9 = (InputStream)(new FileInputStream(var0));
      var10 = false;
      Reader var7 = (Reader)(new InputStreamReader(var9, var1));
      var8 = false;
      Closeable var19 = (Closeable)(var7 instanceof BufferedReader ? (BufferedReader)var7 : new BufferedReader(var7, var5));
      boolean var20 = false;
      Throwable var21 = (Throwable)null;
      boolean var14 = false;

      Object var23;
      try {
         var14 = true;
         BufferedReader var22 = (BufferedReader)var19;
         var8 = false;
         var23 = var2.invoke(TextStreamsKt.lineSequence(var22));
         var14 = false;
      } catch (Throwable var16) {
         var21 = var16;
         throw var16;
      } finally {
         if (var14) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               CloseableKt.closeFinally(var19, var21);
            } else if (var21 == null) {
               var19.close();
            } else {
               try {
                  var19.close();
               } catch (Throwable var15) {
               }
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         CloseableKt.closeFinally(var19, var21);
      } else {
         var19.close();
      }

      InlineMarker.finallyEnd(1);
      return var23;
   }

   public FilesKt__FileReadWriteKt() {
      super();
   }
}
