package kotlin.text;

import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0005R'\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\r"},
   d2 = {"Lkotlin/text/CharCategory$Companion;", "", "()V", "categoryMap", "", "", "Lkotlin/text/CharCategory;", "getCategoryMap", "()Ljava/util/Map;", "categoryMap$delegate", "Lkotlin/Lazy;", "valueOf", "category", "kotlin-stdlib"}
)
public final class CharCategory$Companion {
   static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(CharCategory$Companion.class), "categoryMap", "getCategoryMap()Ljava/util/Map;"))};

   private final Map getCategoryMap() {
      Lazy var1 = CharCategory.access$getCategoryMap$cp();
      CharCategory$Companion var2 = CharCategory.Companion;
      KProperty var3 = $$delegatedProperties[0];
      boolean var4 = false;
      return (Map)var1.getValue();
   }

   @NotNull
   public final CharCategory valueOf(int var1) {
      CharCategory var10000 = (CharCategory)((CharCategory$Companion)this).getCategoryMap().get(var1);
      if (var10000 != null) {
         return var10000;
      } else {
         throw (Throwable)(new IllegalArgumentException("Category #" + var1 + " is not defined."));
      }
   }

   private CharCategory$Companion() {
      super();
   }

   public CharCategory$Companion(DefaultConstructorMarker var1) {
      this();
   }
}
