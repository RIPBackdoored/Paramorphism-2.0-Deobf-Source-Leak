package paramorphism-obfuscator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import site.hackery.paramorphism.api.resources.BufferResource;
import site.hackery.paramorphism.api.resources.ClassInfo;
import site.hackery.paramorphism.api.resources.Resource;

public final class no extends Lambda implements Function1 {
   public final kg wx;

   public Object invoke(Object var1) {
      this.a((mg)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull mg var1) {
      Resource var2 = this.wx.d().get("plugin.yml");
      if (var2 != null) {
         ObjectMapper var3 = new ObjectMapper((JsonFactory)(new YAMLFactory()));
         JsonNode var10000 = var3.readTree(var2.getData());
         if (!(var10000 instanceof ObjectNode)) {
            var10000 = null;
         }

         ObjectNode var4 = (ObjectNode)var10000;
         if (var4 != null) {
            var10000 = var4.get("main");
            String var5 = var10000 != null ? var10000.textValue() : null;
            if (var5 != null) {
               String var9;
               label64: {
                  ClassInfo var7 = this.wx.b().get(StringsKt.replace$default(var5, '.', '/', false, 4, (Object)null));
                  if (var7 != null) {
                     ClassNode var8 = var7.getNode();
                     if (var8 != null) {
                        var9 = var8.name;
                        break label64;
                     }
                  }

                  var9 = null;
               }

               String var6 = var9;
               if (var6 != null) {
                  var4.set("main", (JsonNode)(new TextNode(StringsKt.replace$default(var6, '/', '.', false, 4, (Object)null))));
               }
            }

            this.wx.d().put("plugin.yml", (Resource)(new BufferResource(var3.writeValueAsBytes(var4), 0, 2, (DefaultConstructorMarker)null)));
         }
      }

   }

   public no(kg var1) {
      super(1);
      this.wx = var1;
   }
}
