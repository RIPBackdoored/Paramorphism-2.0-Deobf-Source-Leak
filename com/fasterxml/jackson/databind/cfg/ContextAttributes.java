package com.fasterxml.jackson.databind.cfg;

import java.util.Map;

public abstract class ContextAttributes {
   public ContextAttributes() {
      super();
   }

   public static ContextAttributes getEmpty() {
      return ContextAttributes$Impl.getEmpty();
   }

   public abstract ContextAttributes withSharedAttribute(Object var1, Object var2);

   public abstract ContextAttributes withSharedAttributes(Map var1);

   public abstract ContextAttributes withoutSharedAttribute(Object var1);

   public abstract Object getAttribute(Object var1);

   public abstract ContextAttributes withPerCallAttribute(Object var1, Object var2);
}
