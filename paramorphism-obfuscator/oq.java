package paramorphism-obfuscator;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;

public final class oq {
   public static final boolean a(@NotNull ClassNode var0, @NotNull String var1, @NotNull String var2) {
      if ((var0.access & 8192) != 0) {
         return true;
      } else if (!Intrinsics.areEqual((Object)var1, (Object)"<init>") && !Intrinsics.areEqual((Object)var1, (Object)"<clinit>")) {
         if (Intrinsics.areEqual((Object)var1, (Object)"main") && Intrinsics.areEqual((Object)var2, (Object)"([Ljava/lang/String;)V")) {
            return true;
         } else if (!Intrinsics.areEqual((Object)var1, (Object)"premain") && !Intrinsics.areEqual((Object)var1, (Object)"agentmain") || !Intrinsics.areEqual((Object)var2, (Object)"(Ljava/lang/String;Ljava/lang/instrument/Instrumentation;)V") && !Intrinsics.areEqual((Object)var2, (Object)"(Ljava/lang/String;)V")) {
            if (Intrinsics.areEqual((Object)var1, (Object)"getHandlerList")) {
               return true;
            } else {
               if ((var0.access & 16384) != 0) {
                  if (Intrinsics.areEqual((Object)var1, (Object)"values") && Intrinsics.areEqual((Object)var2, (Object)("()[L" + var0.name + ';'))) {
                     return true;
                  }

                  if (Intrinsics.areEqual((Object)var1, (Object)"valueOf") && Intrinsics.areEqual((Object)var2, (Object)("(Ljava/lang/String;)L" + var0.name + ';'))) {
                     return true;
                  }
               }

               return false;
            }
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public static final boolean b(@NotNull ClassNode var0, @NotNull String var1, @NotNull String var2) {
      return (var0.access & 16384) != 0 && Intrinsics.areEqual((Object)var1, (Object)"$VALUES") && Intrinsics.areEqual((Object)var2, (Object)("[L" + var0.name + ';'));
   }
}
