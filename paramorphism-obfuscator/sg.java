package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.access.PublicAccess;
import paramorphism-obfuscator.access.StaticAccess;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class sg extends Lambda implements Function1 {
   public static final sg bgw = new sg();

   public Object invoke(Object var1) {
      this.a((ClassInfoList)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassInfoList var1) {
      String var2 = StringsKt.repeat((CharSequence)"/", 65528) + "a";
      var1.addClassInfo(new ClassInfo(xi.a(PublicAccess.bln.a((AccessWrapper)StaticAccess.blo), var2, 0, (String)null, (Function1)si.bgy, 12, (Object)null), Integer.MIN_VALUE));
   }

   public sg() {
      super(1);
   }
}
