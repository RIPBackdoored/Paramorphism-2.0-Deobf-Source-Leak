package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InjectableValues$Std extends InjectableValues implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final Map _values;

   public InjectableValues$Std() {
      this(new HashMap());
   }

   public InjectableValues$Std(Map var1) {
      super();
      this._values = var1;
   }

   public InjectableValues$Std addValue(String var1, Object var2) {
      this._values.put(var1, var2);
      return this;
   }

   public InjectableValues$Std addValue(Class var1, Object var2) {
      this._values.put(var1.getName(), var2);
      return this;
   }

   public Object findInjectableValue(Object var1, DeserializationContext var2, BeanProperty var3, Object var4) throws JsonMappingException {
      if (!(var1 instanceof String)) {
         var2.reportBadDefinition(ClassUtil.classOf(var1), String.format("Unrecognized inject value id type (%s), expecting String", ClassUtil.classNameOf(var1)));
      }

      String var5 = (String)var1;
      Object var6 = this._values.get(var5);
      if (var6 == null && !this._values.containsKey(var5)) {
         throw new IllegalArgumentException("No injectable id with value '" + var5 + "' found (for property '" + var3.getName() + "')");
      } else {
         return var6;
      }
   }
}
