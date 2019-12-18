package kotlin.io;

import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0005\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0011H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0012H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0013H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014H\u0087\b\u001a\t\u0010\u0015\u001a\u00020\nH\u0087\b\u001a\u0013\u0010\u0015\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0011H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0012H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0013H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014H\u0087\b\u001a\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u001a\u001a\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\f\u0010\u001a\u001a\u00020\r*\u00020\u001bH\u0002\u001a\f\u0010\u001c\u001a\u00020\n*\u00020\u001dH\u0002\u001a\u0018\u0010\u001e\u001a\u00020\n*\u00020\u001b2\n\u0010\u001f\u001a\u00060 j\u0002`!H\u0002\u001a$\u0010\"\u001a\u00020\r*\u00020\u00042\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\rH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006'"},
   d2 = {"BUFFER_SIZE", "", "LINE_SEPARATOR_MAX_LENGTH", "decoder", "Ljava/nio/charset/CharsetDecoder;", "getDecoder", "()Ljava/nio/charset/CharsetDecoder;", "decoder$delegate", "Lkotlin/Lazy;", "print", "", "message", "", "", "", "", "", "", "", "", "", "println", "readLine", "", "inputStream", "Ljava/io/InputStream;", "endsWithLineSeparator", "Ljava/nio/CharBuffer;", "flipBack", "Ljava/nio/Buffer;", "offloadPrefixTo", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "tryDecode", "byteBuffer", "Ljava/nio/ByteBuffer;", "charBuffer", "isEndOfStream", "kotlin-stdlib"}
)
@JvmName(
   name = "ConsoleKt"
)
public final class ConsoleKt {
   static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinPackage(ConsoleKt.class, "kotlin-stdlib"), "decoder", "getDecoder()Ljava/nio/charset/CharsetDecoder;"))};
   private static final int BUFFER_SIZE = 32;
   private static final int LINE_SEPARATOR_MAX_LENGTH = 2;
   private static final Lazy decoder$delegate;

   static {
      decoder$delegate = LazyKt.lazy((Function0)ConsoleKt$decoder$2.INSTANCE);
   }

   @InlineOnly
   private static final void print(Object var0) {
      byte var1 = 0;
      System.out.print(var0);
   }

   @InlineOnly
   private static final void print(int var0) {
      byte var1 = 0;
      System.out.print(var0);
   }

   @InlineOnly
   private static final void print(long var0) {
      byte var2 = 0;
      System.out.print(var0);
   }

   @InlineOnly
   private static final void print(byte var0) {
      byte var1 = 0;
      System.out.print(var0);
   }

   @InlineOnly
   private static final void print(short var0) {
      byte var1 = 0;
      System.out.print(var0);
   }

   @InlineOnly
   private static final void print(char var0) {
      byte var1 = 0;
      System.out.print(var0);
   }

   @InlineOnly
   private static final void print(boolean var0) {
      byte var1 = 0;
      System.out.print(var0);
   }

   @InlineOnly
   private static final void print(float var0) {
      byte var1 = 0;
      System.out.print(var0);
   }

   @InlineOnly
   private static final void print(double var0) {
      byte var2 = 0;
      System.out.print(var0);
   }

   @InlineOnly
   private static final void print(char[] var0) {
      byte var1 = 0;
      System.out.print(var0);
   }

   @InlineOnly
   private static final void println(Object var0) {
      byte var1 = 0;
      System.out.println(var0);
   }

   @InlineOnly
   private static final void println(int var0) {
      byte var1 = 0;
      System.out.println(var0);
   }

   @InlineOnly
   private static final void println(long var0) {
      byte var2 = 0;
      System.out.println(var0);
   }

   @InlineOnly
   private static final void println(byte var0) {
      byte var1 = 0;
      System.out.println(var0);
   }

   @InlineOnly
   private static final void println(short var0) {
      byte var1 = 0;
      System.out.println(var0);
   }

   @InlineOnly
   private static final void println(char var0) {
      byte var1 = 0;
      System.out.println(var0);
   }

   @InlineOnly
   private static final void println(boolean var0) {
      byte var1 = 0;
      System.out.println(var0);
   }

   @InlineOnly
   private static final void println(float var0) {
      byte var1 = 0;
      System.out.println(var0);
   }

   @InlineOnly
   private static final void println(double var0) {
      byte var2 = 0;
      System.out.println(var0);
   }

   @InlineOnly
   private static final void println(char[] var0) {
      byte var1 = 0;
      System.out.println(var0);
   }

   @InlineOnly
   private static final void println() {
      byte var0 = 0;
      System.out.println();
   }

   private static final CharsetDecoder getDecoder() {
      Lazy var0 = decoder$delegate;
      Object var1 = null;
      KProperty var2 = $$delegatedProperties[0];
      boolean var3 = false;
      return (CharsetDecoder)var0.getValue();
   }

   @Nullable
   public static final String readLine() {
      return readLine(System.in, getDecoder());
   }

   @Nullable
   public static final String readLine(@NotNull InputStream var0, @NotNull CharsetDecoder var1) {
      boolean var2 = var1.maxCharsPerByte() <= (float)1;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var20 = false;
         String var19 = "Encodings with multiple chars per byte are not supported";
         throw (Throwable)(new IllegalArgumentException(var19.toString()));
      } else {
         ByteBuffer var16 = ByteBuffer.allocate(32);
         CharBuffer var17 = CharBuffer.allocate(4);
         StringBuilder var18 = new StringBuilder();
         int var5 = var0.read();
         if (var5 == -1) {
            return null;
         } else {
            do {
               var16.put((byte)var5);
               if (tryDecode(var1, var16, var17, false)) {
                  if (endsWithLineSeparator(var17)) {
                     break;
                  }

                  if (var17.remaining() < 2) {
                     offloadPrefixTo(var17, var18);
                  }
               }

               var5 = var0.read();
            } while(var5 != -1);

            boolean var6 = false;
            boolean var7 = false;
            boolean var9 = false;
            tryDecode(var1, var16, var17, true);
            var1.reset();
            var6 = false;
            var7 = false;
            CharBuffer var8 = var17;
            var9 = false;
            int var10 = var17.position();
            if (var10 > 0 && var17.get(var10 - 1) == '\n') {
               --var10;
               if (var10 > 0 && var17.get(var10 - 1) == '\r') {
                  --var10;
               }
            }

            var17.flip();
            boolean var11 = false;
            boolean var12 = false;
            int var21 = 0;

            for(int var13 = var10; var21 < var13; ++var21) {
               boolean var15 = false;
               var18.append(var8.get());
            }

            return var18.toString();
         }
      }
   }

   private static final boolean tryDecode(@NotNull CharsetDecoder var0, ByteBuffer var1, CharBuffer var2, boolean var3) {
      int var4 = var2.position();
      var1.flip();
      CoderResult var5 = var0.decode(var1, var2, var3);
      boolean var6 = false;
      boolean var7 = false;
      boolean var9 = false;
      if (var5.isError()) {
         var5.throwException();
      }

      boolean var10 = var2.position() > var4;
      var6 = false;
      var7 = false;
      var9 = false;
      if (var10) {
         var1.clear();
      } else {
         flipBack((Buffer)var1);
      }

      return var10;
   }

   private static final boolean endsWithLineSeparator(@NotNull CharBuffer var0) {
      int var1 = var0.position();
      return var1 > 0 && var0.get(var1 - 1) == '\n';
   }

   private static final void flipBack(@NotNull Buffer var0) {
      var0.position(var0.limit());
      var0.limit(var0.capacity());
   }

   private static final void offloadPrefixTo(@NotNull CharBuffer var0, StringBuilder var1) {
      var0.flip();
      int var2 = var0.limit() - 1;
      boolean var3 = false;
      boolean var4 = false;
      int var8 = 0;

      for(int var5 = var2; var8 < var5; ++var8) {
         boolean var7 = false;
         var1.append(var0.get());
      }

      var0.compact();
   }
}
