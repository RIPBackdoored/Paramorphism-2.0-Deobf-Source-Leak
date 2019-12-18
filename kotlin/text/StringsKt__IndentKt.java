package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¢\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\u0082\b¢\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002¨\u0006\u0015"},
   d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__IndentKt {
   @NotNull
   public static final String trimMargin(@NotNull String var0, @NotNull String var1) {
      return StringsKt.replaceIndentByMargin(var0, "", var1);
   }

   public static String trimMargin$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "|";
      }

      return StringsKt.trimMargin(var0, var1);
   }

   @NotNull
   public static final String replaceIndentByMargin(@NotNull String var0, @NotNull String var1, @NotNull String var2) {
      CharSequence var3 = (CharSequence)var2;
      boolean var4 = false;
      boolean var45 = !StringsKt.isBlank(var3);
      var4 = false;
      boolean var5 = false;
      if (!var45) {
         boolean var49 = false;
         String var48 = "marginPrefix must be non-blank string.";
         throw (Throwable)(new IllegalArgumentException(var48.toString()));
      } else {
         List var46 = StringsKt.lines((CharSequence)var0);
         int var47 = var0.length() + var1.length() * var46.size();
         Function1 var6 = getIndentFunction$StringsKt__IndentKt(var1);
         boolean var7 = false;
         int var8 = CollectionsKt.getLastIndex(var46);
         Iterable var9 = (Iterable)var46;
         boolean var10 = false;
         Collection var12 = (Collection)(new ArrayList());
         boolean var13 = false;
         boolean var15 = false;
         int var16 = 0;
         Iterator var17 = var9.iterator();

         while(var17.hasNext()) {
            Object var18 = var17.next();
            int var19 = var16++;
            boolean var20 = false;
            if (var19 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            boolean var24 = false;
            String var25 = (String)var18;
            boolean var27 = false;
            String var50;
            if ((var19 == 0 || var19 == var8) && StringsKt.isBlank((CharSequence)var25)) {
               var50 = null;
            } else {
               label91: {
                  boolean var29 = false;
                  CharSequence var30 = (CharSequence)var25;
                  boolean var31 = false;
                  int var32 = 0;
                  int var33 = var30.length();

                  int var10000;
                  while(true) {
                     if (var32 >= var33) {
                        var10000 = -1;
                        break;
                     }

                     char var34 = var30.charAt(var32);
                     boolean var35 = false;
                     if (!CharsKt.isWhitespace(var34)) {
                        var10000 = var32;
                        break;
                     }

                     ++var32;
                  }

                  int var36 = var10000;
                  if (var36 == -1) {
                     var50 = null;
                  } else if (StringsKt.startsWith$default(var25, var2, var36, false, 4, (Object)null)) {
                     int var51 = var36 + var2.length();
                     boolean var52 = false;
                     if (var25 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                     }

                     var50 = var25.substring(var51);
                  } else {
                     var50 = null;
                  }

                  if (var50 != null) {
                     String var37 = var50;
                     boolean var38 = false;
                     boolean var39 = false;
                     var50 = (String)var6.invoke(var37);
                     if (var50 != null) {
                        break label91;
                     }
                  }

                  var50 = var25;
               }
            }

            if (var50 != null) {
               String var40 = var50;
               boolean var41 = false;
               boolean var42 = false;
               boolean var44 = false;
               var12.add(var40);
            }
         }

         return ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)var12), (Appendable)(new StringBuilder(var47)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
      }
   }

   public static String replaceIndentByMargin$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = "";
      }

      if ((var3 & 2) != 0) {
         var2 = "|";
      }

      return StringsKt.replaceIndentByMargin(var0, var1, var2);
   }

   @NotNull
   public static final String trimIndent(@NotNull String var0) {
      return StringsKt.replaceIndent(var0, "");
   }

   @NotNull
   public static final String replaceIndent(@NotNull String var0, @NotNull String var1) {
      List var2 = StringsKt.lines((CharSequence)var0);
      Iterable var4 = (Iterable)var2;
      boolean var5 = false;
      Collection var7 = (Collection)(new ArrayList());
      boolean var8 = false;
      Iterator var9 = var4.iterator();

      Object var10;
      String var11;
      boolean var12;
      while(var9.hasNext()) {
         var10 = var9.next();
         var11 = (String)var10;
         var12 = false;
         CharSequence var13 = (CharSequence)var11;
         boolean var14 = false;
         if (!StringsKt.isBlank(var13)) {
            var7.add(var10);
         }
      }

      var4 = (Iterable)((List)var7);
      var5 = false;
      var7 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10)));
      var8 = false;
      var9 = var4.iterator();

      while(var9.hasNext()) {
         var10 = var9.next();
         var11 = (String)var10;
         var12 = false;
         Integer var39 = indentWidth$StringsKt__IndentKt(var11);
         var7.add(var39);
      }

      Integer var10000 = (Integer)CollectionsKt.min((Iterable)((List)var7));
      int var3 = var10000 != null ? var10000 : 0;
      int var40 = var0.length() + var1.length() * var2.size();
      Function1 var6 = getIndentFunction$StringsKt__IndentKt(var1);
      boolean var41 = false;
      int var42 = CollectionsKt.getLastIndex(var2);
      Iterable var43 = (Iterable)var2;
      boolean var44 = false;
      Collection var45 = (Collection)(new ArrayList());
      boolean var46 = false;
      boolean var15 = false;
      int var16 = 0;
      Iterator var17 = var43.iterator();

      while(var17.hasNext()) {
         Object var18 = var17.next();
         int var19 = var16++;
         boolean var20 = false;
         if (var19 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         boolean var24 = false;
         String var25 = (String)var18;
         boolean var27 = false;
         String var48;
         if ((var19 == 0 || var19 == var42) && StringsKt.isBlank((CharSequence)var25)) {
            var48 = null;
         } else {
            label79: {
               boolean var29 = false;
               var48 = StringsKt.drop(var25, var3);
               if (var48 != null) {
                  String var30 = var48;
                  boolean var31 = false;
                  boolean var32 = false;
                  var48 = (String)var6.invoke(var30);
                  if (var48 != null) {
                     break label79;
                  }
               }

               var48 = var25;
            }
         }

         if (var48 != null) {
            String var33 = var48;
            boolean var34 = false;
            boolean var35 = false;
            boolean var37 = false;
            var45.add(var33);
         }
      }

      return ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)var45), (Appendable)(new StringBuilder(var40)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
   }

   public static String replaceIndent$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "";
      }

      return StringsKt.replaceIndent(var0, var1);
   }

   @NotNull
   public static final String prependIndent(@NotNull String var0, @NotNull String var1) {
      return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence((CharSequence)var0), (Function1)(new StringsKt__IndentKt$prependIndent$1(var1))), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
   }

   public static String prependIndent$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "    ";
      }

      return StringsKt.prependIndent(var0, var1);
   }

   private static final int indentWidth$StringsKt__IndentKt(@NotNull String var0) {
      CharSequence var1 = (CharSequence)var0;
      boolean var2 = false;
      int var3 = 0;
      int var4 = var1.length();

      int var10000;
      while(true) {
         if (var3 >= var4) {
            var10000 = -1;
            break;
         }

         char var5 = var1.charAt(var3);
         boolean var6 = false;
         if (!CharsKt.isWhitespace(var5)) {
            var10000 = var3;
            break;
         }

         ++var3;
      }

      int var7 = var10000;
      var2 = false;
      boolean var8 = false;
      boolean var9 = false;
      return var7 == -1 ? var0.length() : var7;
   }

   private static final Function1 getIndentFunction$StringsKt__IndentKt(String var0) {
      CharSequence var1 = (CharSequence)var0;
      boolean var2 = false;
      return var1.length() == 0 ? (Function1)StringsKt__IndentKt$getIndentFunction$1.INSTANCE : (Function1)(new StringsKt__IndentKt$getIndentFunction$2(var0));
   }

   private static final String reindent$StringsKt__IndentKt(@NotNull List var0, int var1, Function1 var2, Function1 var3) {
      byte var4 = 0;
      int var5 = CollectionsKt.getLastIndex(var0);
      Iterable var6 = (Iterable)var0;
      boolean var7 = false;
      Collection var9 = (Collection)(new ArrayList());
      boolean var10 = false;
      boolean var12 = false;
      int var13 = 0;
      Iterator var14 = var6.iterator();

      while(var14.hasNext()) {
         Object var15 = var14.next();
         int var16 = var13++;
         boolean var17 = false;
         if (var16 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw (Throwable)(new ArithmeticException("Index overflow has happened."));
            }

            CollectionsKt.throwIndexOverflow();
         }

         boolean var21 = false;
         String var22 = (String)var15;
         boolean var24 = false;
         String var33;
         if ((var16 == 0 || var16 == var5) && StringsKt.isBlank((CharSequence)var22)) {
            var33 = null;
         } else {
            label47: {
               var33 = (String)var3.invoke(var22);
               if (var33 != null) {
                  String var25 = var33;
                  boolean var26 = false;
                  boolean var27 = false;
                  var33 = (String)var2.invoke(var25);
                  if (var33 != null) {
                     break label47;
                  }
               }

               var33 = var22;
            }
         }

         if (var33 != null) {
            String var28 = var33;
            boolean var29 = false;
            boolean var30 = false;
            boolean var32 = false;
            var9.add(var28);
         }
      }

      return ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)var9), (Appendable)(new StringBuilder(var1)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
   }

   public StringsKt__IndentKt() {
      super();
   }
}
