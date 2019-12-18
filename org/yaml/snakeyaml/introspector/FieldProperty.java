package org.yaml.snakeyaml.introspector;

import java.lang.reflect.Field;
import org.yaml.snakeyaml.error.YAMLException;

public class FieldProperty extends GenericProperty {
   private final Field field;

   public FieldProperty(Field var1) {
      super(var1.getName(), var1.getType(), var1.getGenericType());
      this.field = var1;
      var1.setAccessible(true);
   }

   public void set(Object var1, Object var2) throws Exception {
      this.field.set(var1, var2);
   }

   public Object get(Object var1) {
      Object var10000;
      try {
         var10000 = this.field.get(var1);
      } catch (Exception var3) {
         throw new YAMLException("Unable to access field " + this.field.getName() + " on object " + var1 + " : " + var3);
      }

      return var10000;
   }
}
