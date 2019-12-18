package org.jline.style;

class StyleBundleInvocationHandler$InvalidStyleGroupException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public StyleBundleInvocationHandler$InvalidStyleGroupException(Class var1) {
      super(String.format("%s missing or invalid @%s: %s", StyleBundle.class.getSimpleName(), StyleBundle$StyleGroup.class.getSimpleName(), var1.getName()));
   }
}
