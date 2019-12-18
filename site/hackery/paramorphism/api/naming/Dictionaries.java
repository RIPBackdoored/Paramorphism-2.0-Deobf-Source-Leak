package site.hackery.paramorphism.api.naming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u000fH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00040\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0014"},
   d2 = {"Lsite/hackery/paramorphism/api/naming/Dictionaries;", "", "()V", "ALPHABET", "Lsite/hackery/paramorphism/api/naming/MappingDictionary;", "getALPHABET", "()Lsite/hackery/paramorphism/api/naming/MappingDictionary;", "ALPHABET_UPPER", "getALPHABET_UPPER", "JAVA_KEYWORDS", "getJAVA_KEYWORDS", "LOOKALIKES", "getLOOKALIKES", "dictionaryMap", "", "", "getDictionaryMap", "()Ljava/util/Map;", "fromString", "dictionary", "paramorphism"}
)
public final class Dictionaries {
   @NotNull
   private static final MappingDictionary ALPHABET;
   @NotNull
   private static final MappingDictionary ALPHABET_UPPER;
   @NotNull
   private static final MappingDictionary JAVA_KEYWORDS;
   @NotNull
   private static final MappingDictionary LOOKALIKES;
   @NotNull
   private static final Map dictionaryMap;
   public static final Dictionaries INSTANCE;

   private final MappingDictionary fromString(String var1) {
      boolean var3 = false;
      if (var1 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         char[] var15 = var1.toCharArray();
         var3 = false;
         Collection var5 = (Collection)(new ArrayList(var15.length));
         boolean var6 = false;
         char[] var7 = var15;
         int var8 = var15.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            char var10 = var7[var9];
            boolean var12 = false;
            String var16 = String.valueOf(var10);
            var5.add(var16);
         }

         List var22 = (List)var5;
         Collection var2 = (Collection)var22;
         var3 = false;
         Object[] var10000 = var2.toArray(new String[0]);
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         } else {
            Object[] var23 = var10000;
            String[] var24 = (String[])var23;
            Object var17 = null;
            byte var18 = 6;
            byte var19 = 0;
            Object var20 = null;
            String[] var21 = var24;
            return new MappingDictionary(var21, (String)var20, var19, var18, (DefaultConstructorMarker)var17);
         }
      }
   }

   @NotNull
   public final MappingDictionary getALPHABET() {
      return ALPHABET;
   }

   @NotNull
   public final MappingDictionary getALPHABET_UPPER() {
      return ALPHABET_UPPER;
   }

   @NotNull
   public final MappingDictionary getJAVA_KEYWORDS() {
      return JAVA_KEYWORDS;
   }

   @NotNull
   public final MappingDictionary getLOOKALIKES() {
      return LOOKALIKES;
   }

   @NotNull
   public final Map getDictionaryMap() {
      return dictionaryMap;
   }

   private Dictionaries() {
      super();
   }

   static {
      Dictionaries var0 = new Dictionaries();
      INSTANCE = var0;
      ALPHABET = var0.fromString("abcdefghijklmnopqrstuvwxyz");
      ALPHABET_UPPER = var0.fromString("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
      JAVA_KEYWORDS = new MappingDictionary(new String[]{"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while"}, " ", 0, 4, (DefaultConstructorMarker)null);
      LOOKALIKES = new MappingDictionary(new String[]{"L", "I", "1", "i", "l", ""}, (String)null, 0, 6, (DefaultConstructorMarker)null);
      dictionaryMap = MapsKt.mapOf(new Pair[]{TuplesKt.to("alphabet", ALPHABET), TuplesKt.to("alphabet_upper", ALPHABET_UPPER), TuplesKt.to("java_keywords", JAVA_KEYWORDS), TuplesKt.to("lookalikes", LOOKALIKES)});
   }
}
