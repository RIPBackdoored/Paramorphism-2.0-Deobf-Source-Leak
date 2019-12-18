package paramorphism-obfuscator;

import java.util.Comparator;
import kotlin.Pair;
import kotlin.comparisons.ComparisonsKt;
import site.hackery.paramorphism.api.resources.Resource;

public final class lw implements Comparator {
   public lw() {
      super();
   }

   public final int compare(Object var1, Object var2) {
      boolean var3 = false;
      Pair var4 = (Pair)var1;
      boolean var5 = false;
      Resource var6 = (Resource)var4.component2();
      Comparable var10000 = (Comparable)var6.getOrder();
      var4 = (Pair)var2;
      Comparable var7 = var10000;
      var5 = false;
      var6 = (Resource)var4.component2();
      Integer var8 = var6.getOrder();
      return ComparisonsKt.compareValues(var7, (Comparable)var8);
   }
}
