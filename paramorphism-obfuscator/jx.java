package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.naming.Dictionaries;
import site.hackery.paramorphism.api.naming.MappingDictionary;

public final class jx extends Lambda implements Function0 {
   public final jy qh;
   public final String qi;
   public final MappingDictionary qj;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final MappingDictionary a() {
      JsonNode var10000 = jy.a(this.qh).e(jy.b(this.qh) + '.' + this.qi);
      MappingDictionary var7;
      if (var10000 != null) {
         String var6 = var10000.textValue();
         if (var6 != null) {
            String var1 = var6;
            boolean var2 = false;
            boolean var3 = false;
            boolean var5 = false;
            var7 = (MappingDictionary)Dictionaries.INSTANCE.getDictionaryMap().get(var1);
            if (var7 != null) {
               return var7;
            }
         }
      }

      var7 = this.qj;
      return var7;
   }

   public jx(jy var1, String var2, MappingDictionary var3) {
      super(0);
      this.qh = var1;
      this.qi = var2;
      this.qj = var3;
   }
}
