package org.jline.style;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

class StyleBundleInvocationHandler implements InvocationHandler {
   private static final Logger log = Logger.getLogger(StyleBundleInvocationHandler.class.getName());
   private final Class type;
   private final StyleResolver resolver;

   public StyleBundleInvocationHandler(Class var1, StyleResolver var2) {
      super();
      this.type = (Class)Objects.requireNonNull(var1);
      this.resolver = (StyleResolver)Objects.requireNonNull(var2);
   }

   private static void validate(Method var0) {
      if (var0.getParameterCount() != 1) {
         throw new StyleBundleInvocationHandler$InvalidStyleBundleMethodException(var0, "Invalid parameters");
      } else if (var0.getReturnType() != AttributedString.class) {
         throw new StyleBundleInvocationHandler$InvalidStyleBundleMethodException(var0, "Invalid return-type");
      }
   }

   @Nullable
   private static String emptyToNull(@Nullable String var0) {
      return var0 != null && !var0.isEmpty() ? var0 : null;
   }

   @Nullable
   private static String getStyleGroup(Class var0) {
      StyleBundle$StyleGroup var1 = (StyleBundle$StyleGroup)var0.getAnnotation(StyleBundle$StyleGroup.class);
      return var1 != null ? emptyToNull(var1.value().trim()) : null;
   }

   private static String getStyleName(Method var0) {
      StyleBundle$StyleName var1 = (StyleBundle$StyleName)var0.getAnnotation(StyleBundle$StyleName.class);
      return var1 != null ? emptyToNull(var1.value().trim()) : var0.getName();
   }

   @Nullable
   private static String getDefaultStyle(Method var0) {
      StyleBundle$DefaultStyle var1 = (StyleBundle$DefaultStyle)var0.getAnnotation(StyleBundle$DefaultStyle.class);
      return var1 != null ? emptyToNull(var1.value()) : null;
   }

   static StyleBundle create(StyleResolver var0, Class var1) {
      Objects.requireNonNull(var0);
      Objects.requireNonNull(var1);
      if (log.isLoggable(Level.FINEST)) {
         log.finest(String.format("Using style-group: %s for type: %s", var0.getGroup(), var1.getName()));
      }

      StyleBundleInvocationHandler var2 = new StyleBundleInvocationHandler(var1, var0);
      return (StyleBundle)Proxy.newProxyInstance(var1.getClassLoader(), new Class[]{var1}, var2);
   }

   static StyleBundle create(StyleSource var0, Class var1) {
      Objects.requireNonNull(var1);
      String var2 = getStyleGroup(var1);
      if (var2 == null) {
         throw new StyleBundleInvocationHandler$InvalidStyleGroupException(var1);
      } else {
         return create(new StyleResolver(var0, var2), var1);
      }
   }

   public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
      if (var2.getDeclaringClass() == Object.class) {
         return var2.invoke(this, var3);
      } else {
         validate(var2);
         String var4 = getStyleName(var2);
         String var5 = this.resolver.getSource().get(this.resolver.getGroup(), var4);
         if (log.isLoggable(Level.FINEST)) {
            log.finest(String.format("Sourced-style: %s -> %s", var4, var5));
         }

         if (var5 == null) {
            var5 = getDefaultStyle(var2);
            if (var5 == null) {
               throw new StyleBundleInvocationHandler$StyleBundleMethodMissingDefaultStyleException(var2);
            }
         }

         String var6 = String.valueOf(var3[0]);
         if (log.isLoggable(Level.FINEST)) {
            log.finest(String.format("Applying style: %s -> %s to: %s", var4, var5, var6));
         }

         AttributedStyle var7 = this.resolver.resolve(var5);
         return new AttributedString(var6, var7);
      }
   }

   public String toString() {
      return this.type.getName();
   }
}
