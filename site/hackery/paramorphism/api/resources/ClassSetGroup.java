package site.hackery.paramorphism.api.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000bJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0012\u001a\u00020\u000bJ\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0012\u001a\u00020\u000bH\u0002R\u0019\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"Lsite/hackery/paramorphism/api/resources/ClassSetGroup;", "", "classSets", "", "Lsite/hackery/paramorphism/api/resources/ClassSet;", "([Lsite/hackery/paramorphism/api/resources/ClassSet;)V", "getClassSets", "()[Lsite/hackery/paramorphism/api/resources/ClassSet;", "[Lsite/hackery/paramorphism/api/resources/ClassSet;", "lookupCache", "", "", "", "all", "Lkotlin/sequences/Sequence;", "Lsite/hackery/paramorphism/api/resources/ClassInfo;", "exists", "", "name", "lookup", "lookupContainingClassSet", "paramorphism"}
)
public final class ClassSetGroup {
   private final Map lookupCache;
   @NotNull
   private final ClassSet[] classSets;

   private final ClassSet lookupContainingClassSet(String var1) {
      Integer var2 = (Integer)this.lookupCache.get(var1);
      if (var2 != null) {
         return var2 < 0 ? null : this.classSets[var2];
      } else {
         ClassSet[] var5 = this.classSets;
         int var6 = var5.length;

         for(int var3 = 0; var3 < var6; ++var3) {
            ClassSet var4 = var5[var3];
            if (var4.contains(var1)) {
               this.lookupCache.put(var1, var3);
               return var4;
            }
         }

         this.lookupCache.put(var1, -1);
         return null;
      }
   }

   @Nullable
   public final ClassInfo lookup(@NotNull String var1) {
      ClassSet var10000 = this.lookupContainingClassSet(var1);
      if (var10000 != null) {
         ClassSet var2 = var10000;
         return var2.get(var1);
      } else {
         return null;
      }
   }

   public final boolean exists(@NotNull String var1) {
      return this.lookupContainingClassSet(var1) != null;
   }

   @NotNull
   public final Sequence all() {
      ClassSet[] var1 = this.classSets;
      boolean var2 = false;
      Collection var4 = (Collection)(new ArrayList(var1.length));
      boolean var5 = false;
      ClassSet[] var6 = var1;
      int var7 = var1.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         ClassSet var9 = var6[var8];
         boolean var11 = false;
         Sequence var13 = var9.all();
         var4.add(var13);
      }

      Collection var14 = (Collection)((List)var4);
      var2 = false;
      Object[] var10000 = var14.toArray(new Sequence[0]);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         return SequencesKt.distinct(SequencesKt.flatten(SequencesKt.sequenceOf((Sequence[])Arrays.copyOf((Sequence[])var10000, ((Sequence[])var10000).length))));
      }
   }

   @NotNull
   public final ClassSet[] getClassSets() {
      return this.classSets;
   }

   public ClassSetGroup(@NotNull ClassSet[] var1) {
      super();
      this.classSets = var1;
      boolean var2 = false;
      Map var4 = (Map)(new LinkedHashMap());
      this.lookupCache = var4;
   }
}
