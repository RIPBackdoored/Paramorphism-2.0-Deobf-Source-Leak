package paramorphism-obfuscator;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import kotlin.TypeCastException;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassSet;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class ObtainRuntimeClass {
   @NotNull
   public static final ClassSet a() {
      URL var10000 = Object.class.getResource("Object.class");
      String var1;
      if (var10000 == null) {
         var1 = "Error obtaining resource for runtime class 'java/lang/Object'";
         boolean var8 = false;
         throw (Throwable)(new IllegalStateException(var1.toString()));
      } else {
         URL var0 = var10000;
         String var10 = var0.getProtocol();
         String var7;
         if (var10 != null) {
            label80: {
               var1 = var10;
               ClassSet var11;
               switch(var1.hashCode()) {
               case 104987:
                  if (!var1.equals("jar")) {
                     break label80;
                  }

                  String var3 = var0.getFile();
                  int var4 = StringsKt.indexOf$default((CharSequence)var0.getFile(), ':', 0, false, 6, (Object)null) + 1;
                  int var5 = StringsKt.indexOf$default((CharSequence)var0.getFile(), '!', 0, false, 6, (Object)null);
                  boolean var6 = false;
                  if (var3 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }

                  var7 = URLDecoder.decode(var3.substring(var4, var5), "UTF-8");
                  var11 = (ClassSet)(new sy((ResourceSet)(new tc(new File(var7))), 1));
                  break;
               case 105516:
                  if (var1.equals("jrt")) {
                     FileSystem var2 = FileSystems.getFileSystem(URI.create("jrt:/"));
                     var11 = (ClassSet)(new sy((ResourceSet)(new tl(var2)), 1));
                     break;
                  }
               default:
                  break label80;
               }

               return var11;
            }
         }

         var7 = "Could not locate the runtime classes.";
         boolean var9 = false;
         throw (Throwable)(new IllegalStateException(var7.toString()));
      }
   }
}
