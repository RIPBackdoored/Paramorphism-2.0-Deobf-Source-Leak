package org.jline.style;

import java.lang.reflect.Method;

class StyleBundleInvocationHandler$InvalidStyleBundleMethodException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public StyleBundleInvocationHandler$InvalidStyleBundleMethodException(Method var1, String var2) {
      super(var2 + ": " + var1);
   }
}
