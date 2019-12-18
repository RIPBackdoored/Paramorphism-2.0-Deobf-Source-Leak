package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000b0\r\u001a\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0002\u001a\u0017\u0010\u0016\u001a\u00020\u000e*\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0087\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u000eH\u0087\b\u001a5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c*\u00020\u00022\u0018\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0004\u0012\u0002H\u001c0\rH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001f\u0082\u0002\b\n\u0006\b\u0011(\u001e0\u0001¨\u0006 "},
   d2 = {"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}
)
@JvmName(
   name = "TextStreamsKt"
)
public final class TextStreamsKt {
   @InlineOnly
   private static final BufferedReader buffered(@NotNull Reader var0, int var1) {
      byte var2 = 0;
      return var0 instanceof BufferedReader ? (BufferedReader)var0 : new BufferedReader(var0, var1);
   }

   static BufferedReader buffered$default(Reader var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 8192;
      }

      boolean var4 = false;
      return var0 instanceof BufferedReader ? (BufferedReader)var0 : new BufferedReader(var0, var1);
   }

   @InlineOnly
   private static final BufferedWriter buffered(@NotNull Writer var0, int var1) {
      byte var2 = 0;
      return var0 instanceof BufferedWriter ? (BufferedWriter)var0 : new BufferedWriter(var0, var1);
   }

   static BufferedWriter buffered$default(Writer var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 8192;
      }

      boolean var4 = false;
      return var0 instanceof BufferedWriter ? (BufferedWriter)var0 : new BufferedWriter(var0, var1);
   }

   public static final void forEachLine(@NotNull Reader var0, @NotNull Function1 var1) {
      boolean var3 = false;
      short var5 = 8192;
      boolean var6 = false;
      Closeable var4 = (Closeable)(var0 instanceof BufferedReader ? (BufferedReader)var0 : new BufferedReader(var0, var5));
      boolean var21 = false;
      Throwable var22 = (Throwable)null;
      boolean var18 = false;

      try {
         var18 = true;
         BufferedReader var7 = (BufferedReader)var4;
         boolean var8 = false;
         Sequence var9 = lineSequence(var7);
         boolean var10 = false;
         Function1 var12 = var1;
         boolean var13 = false;
         Iterator var14 = var9.iterator();

         while(true) {
            if (!var14.hasNext()) {
               Unit var23 = Unit.INSTANCE;
               var18 = false;
               break;
            }

            Object var15 = var14.next();
            var12.invoke(var15);
         }
      } catch (Throwable var19) {
         var22 = var19;
         throw var19;
      } finally {
         if (var18) {
            CloseableKt.closeFinally(var4, var22);
         }
      }

      CloseableKt.closeFinally(var4, var22);
   }

   @NotNull
   public static final List readLines(@NotNull Reader var0) {
      boolean var2 = false;
      ArrayList var1 = new ArrayList();
      forEachLine(var0, (Function1)(new TextStreamsKt$readLines$1(var1)));
      return (List)var1;
   }

   public static final Object useLines(@NotNull Reader var0, @NotNull Function1 var1) {
      byte var2 = 0;
      short var4 = 8192;
      boolean var5 = false;
      Closeable var3 = (Closeable)(var0 instanceof BufferedReader ? (BufferedReader)var0 : new BufferedReader(var0, var4));
      boolean var15 = false;
      Throwable var16 = (Throwable)null;
      boolean var11 = false;

      Object var17;
      try {
         var11 = true;
         BufferedReader var6 = (BufferedReader)var3;
         boolean var7 = false;
         var17 = var1.invoke(lineSequence(var6));
         var11 = false;
      } catch (Throwable var13) {
         var16 = var13;
         throw var13;
      } finally {
         if (var11) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               CloseableKt.closeFinally(var3, var16);
            } else if (var16 == null) {
               var3.close();
            } else {
               try {
                  var3.close();
               } catch (Throwable var12) {
               }
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         CloseableKt.closeFinally(var3, var16);
      } else {
         var3.close();
      }

      InlineMarker.finallyEnd(1);
      return var17;
   }

   @InlineOnly
   private static final StringReader reader(@NotNull String var0) {
      byte var1 = 0;
      return new StringReader(var0);
   }

   @NotNull
   public static final Sequence lineSequence(@NotNull BufferedReader var0) {
      return SequencesKt.constrainOnce((Sequence)(new LinesSequence(var0)));
   }

   @NotNull
   public static final String readText(@NotNull Reader var0) {
      StringWriter var1 = new StringWriter();
      copyTo$default(var0, (Writer)var1, 0, 2, (Object)null);
      return var1.toString();
   }

   public static final long copyTo(@NotNull Reader var0, @NotNull Writer var1, int var2) {
      long var3 = 0L;
      char[] var5 = new char[var2];

      for(int var6 = var0.read(var5); var6 >= 0; var6 = var0.read(var5)) {
         var1.write(var5, 0, var6);
         var3 += (long)var6;
      }

      return var3;
   }

   public static long copyTo$default(Reader var0, Writer var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      return copyTo(var0, var1, var2);
   }

   @InlineOnly
   private static final String readText(@NotNull URL var0, Charset var1) {
      byte var2 = 0;
      byte[] var3 = readBytes(var0);
      boolean var4 = false;
      boolean var5 = false;
      return new String(var3, var1);
   }

   static String readText$default(URL var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      boolean var6 = false;
      byte[] var7 = readBytes(var0);
      boolean var4 = false;
      boolean var5 = false;
      return new String(var7, var1);
   }

   @NotNull
   public static final byte[] readBytes(@NotNull URL var0) {
      Closeable var1 = (Closeable)var0.openStream();
      boolean var2 = false;
      Throwable var3 = (Throwable)null;
      boolean var8 = false;

      byte[] var11;
      try {
         var8 = true;
         InputStream var4 = (InputStream)var1;
         boolean var5 = false;
         var11 = ByteStreamsKt.readBytes(var4);
         var8 = false;
      } catch (Throwable var9) {
         var3 = var9;
         throw var9;
      } finally {
         if (var8) {
            CloseableKt.closeFinally(var1, var3);
         }
      }

      CloseableKt.closeFinally(var1, var3);
      return var11;
   }
}
