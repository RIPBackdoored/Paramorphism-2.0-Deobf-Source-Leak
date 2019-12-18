package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ElementMask$DefaultImpls;

public final class ip extends Lambda implements Function0 {
   public final ju nl;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final String[] a() {
      JsonNode var10000 = ju.a(this.nl).e(this.nl.a() + ".include");
      String[] var8;
      if (var10000 != null) {
         Iterator var4 = var10000.elements();
         if (var4 != null) {
            Sequence var5 = SequencesKt.asSequence(var4);
            if (var5 != null) {
               var5 = SequencesKt.map(var5, (Function1)im.ni);
               if (var5 != null) {
                  List var6 = SequencesKt.toList(var5);
                  if (var6 != null) {
                     Collection var1 = (Collection)var6;
                     boolean var2 = false;
                     if (var1 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.util.Collection<T>");
                     }

                     Object[] var7 = var1.toArray(new String[0]);
                     if (var7 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                     }

                     var8 = (String[])var7;
                     if (var8 != null) {
                        return var8;
                     }
                  }
               }
            }
         }
      }

      var8 = ElementMask$DefaultImpls.getInclude(this.nl);
      return var8;
   }

   public ip(ju var1) {
      super(0);
      this.nl = var1;
   }
}
