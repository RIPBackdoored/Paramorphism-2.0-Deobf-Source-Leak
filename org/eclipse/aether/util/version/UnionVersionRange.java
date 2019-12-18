package org.eclipse.aether.util.version;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.aether.version.Version;
import org.eclipse.aether.version.VersionRange;
import org.eclipse.aether.version.VersionRange$Bound;

final class UnionVersionRange implements VersionRange {
   private final Set ranges;
   private final VersionRange$Bound lowerBound;
   private final VersionRange$Bound upperBound;

   public static VersionRange from(VersionRange... var0) {
      return var0 == null ? from((Collection)Collections.emptySet()) : from((Collection)Arrays.asList(var0));
   }

   public static VersionRange from(Collection var0) {
      return (VersionRange)(var0 != null && var0.size() == 1 ? (VersionRange)var0.iterator().next() : new UnionVersionRange(var0));
   }

   private UnionVersionRange(Collection var1) {
      super();
      if (var1 != null && !var1.isEmpty()) {
         this.ranges = new HashSet(var1);
         VersionRange$Bound var2 = null;
         VersionRange$Bound var3 = null;
         Iterator var4 = this.ranges.iterator();

         VersionRange var5;
         VersionRange$Bound var6;
         int var7;
         label64:
         while(true) {
            while(true) {
               if (!var4.hasNext()) {
                  break label64;
               }

               var5 = (VersionRange)var4.next();
               var6 = var5.getLowerBound();
               if (var6 == null) {
                  var2 = null;
                  break label64;
               }

               if (var2 == null) {
                  var2 = var6;
               } else {
                  var7 = var6.getVersion().compareTo(var2.getVersion());
                  if (var7 < 0 || var7 == 0 && !var2.isInclusive()) {
                     var2 = var6;
                  }
               }
            }
         }

         var4 = this.ranges.iterator();

         label46:
         while(true) {
            while(true) {
               if (!var4.hasNext()) {
                  break label46;
               }

               var5 = (VersionRange)var4.next();
               var6 = var5.getUpperBound();
               if (var6 == null) {
                  var3 = null;
                  break label46;
               }

               if (var3 == null) {
                  var3 = var6;
               } else {
                  var7 = var6.getVersion().compareTo(var3.getVersion());
                  if (var7 > 0 || var7 == 0 && !var3.isInclusive()) {
                     var3 = var6;
                  }
               }
            }
         }

         this.lowerBound = var2;
         this.upperBound = var3;
      } else {
         this.ranges = Collections.emptySet();
         this.lowerBound = null;
         this.upperBound = null;
      }

   }

   public boolean containsVersion(Version var1) {
      Iterator var2 = this.ranges.iterator();

      VersionRange var3;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         var3 = (VersionRange)var2.next();
      } while(!var3.containsVersion(var1));

      return true;
   }

   public VersionRange$Bound getLowerBound() {
      return this.lowerBound;
   }

   public VersionRange$Bound getUpperBound() {
      return this.upperBound;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         UnionVersionRange var2 = (UnionVersionRange)var1;
         return this.ranges.equals(var2.ranges);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return 97 * this.ranges.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(128);

      VersionRange var3;
      for(Iterator var2 = this.ranges.iterator(); var2.hasNext(); var1.append(var3)) {
         var3 = (VersionRange)var2.next();
         if (var1.length() > 0) {
            var1.append(", ");
         }
      }

      return var1.toString();
   }
}
