package com.fasterxml.jackson.databind.util;

import java.io.Serializable;

public final class NameTransformer$NopTransformer extends NameTransformer implements Serializable {
   private static final long serialVersionUID = 1L;

   protected NameTransformer$NopTransformer() {
      super();
   }

   public String transform(String var1) {
      return var1;
   }

   public String reverse(String var1) {
      return var1;
   }
}
