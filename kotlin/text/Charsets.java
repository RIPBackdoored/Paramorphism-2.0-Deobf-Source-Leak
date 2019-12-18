package kotlin.text;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmName;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000bR\u0010\u0010\u0010\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lkotlin/text/Charsets;", "", "()V", "ISO_8859_1", "Ljava/nio/charset/Charset;", "US_ASCII", "UTF_16", "UTF_16BE", "UTF_16LE", "UTF_32", "UTF32", "()Ljava/nio/charset/Charset;", "UTF_32BE", "UTF32_BE", "UTF_32LE", "UTF32_LE", "UTF_8", "utf_32", "utf_32be", "utf_32le", "kotlin-stdlib"}
)
public final class Charsets {
   @JvmField
   @NotNull
   public static final Charset UTF_8;
   @JvmField
   @NotNull
   public static final Charset UTF_16;
   @JvmField
   @NotNull
   public static final Charset UTF_16BE;
   @JvmField
   @NotNull
   public static final Charset UTF_16LE;
   @JvmField
   @NotNull
   public static final Charset US_ASCII;
   @JvmField
   @NotNull
   public static final Charset ISO_8859_1;
   private static Charset utf_32;
   private static Charset utf_32le;
   private static Charset utf_32be;
   public static final Charsets INSTANCE;

   @JvmName(
      name = "UTF32"
   )
   @NotNull
   public final Charset UTF32() {
      Charset var10000 = utf_32;
      if (var10000 == null) {
         boolean var2 = false;
         boolean var3 = false;
         Charsets var4 = (Charsets)this;
         boolean var5 = false;
         Charset var6 = Charset.forName("UTF-32");
         utf_32 = var6;
         var10000 = var6;
      }

      return var10000;
   }

   @JvmName(
      name = "UTF32_LE"
   )
   @NotNull
   public final Charset UTF32_LE() {
      Charset var10000 = utf_32le;
      if (var10000 == null) {
         boolean var2 = false;
         boolean var3 = false;
         Charsets var4 = (Charsets)this;
         boolean var5 = false;
         Charset var6 = Charset.forName("UTF-32LE");
         utf_32le = var6;
         var10000 = var6;
      }

      return var10000;
   }

   @JvmName(
      name = "UTF32_BE"
   )
   @NotNull
   public final Charset UTF32_BE() {
      Charset var10000 = utf_32be;
      if (var10000 == null) {
         boolean var2 = false;
         boolean var3 = false;
         Charsets var4 = (Charsets)this;
         boolean var5 = false;
         Charset var6 = Charset.forName("UTF-32BE");
         utf_32be = var6;
         var10000 = var6;
      }

      return var10000;
   }

   private Charsets() {
      super();
   }

   static {
      Charsets var0 = new Charsets();
      INSTANCE = var0;
      UTF_8 = Charset.forName("UTF-8");
      UTF_16 = Charset.forName("UTF-16");
      UTF_16BE = Charset.forName("UTF-16BE");
      UTF_16LE = Charset.forName("UTF-16LE");
      US_ASCII = Charset.forName("US-ASCII");
      ISO_8859_1 = Charset.forName("ISO-8859-1");
   }
}
