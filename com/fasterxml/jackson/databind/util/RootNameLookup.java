package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;

public class RootNameLookup implements Serializable {
   private static final long serialVersionUID = 1L;
   protected transient LRUMap _rootNames = new LRUMap(20, 200);

   public RootNameLookup() {
      super();
   }

   public PropertyName findRootName(JavaType var1, MapperConfig var2) {
      return this.findRootName(var1.getRawClass(), var2);
   }

   public PropertyName findRootName(Class var1, MapperConfig var2) {
      ClassKey var3 = new ClassKey(var1);
      PropertyName var4 = (PropertyName)this._rootNames.get(var3);
      if (var4 != null) {
         return var4;
      } else {
         BeanDescription var5 = var2.introspectClassAnnotations(var1);
         AnnotationIntrospector var6 = var2.getAnnotationIntrospector();
         AnnotatedClass var7 = var5.getClassInfo();
         var4 = var6.findRootName(var7);
         if (var4 == null || !var4.hasSimpleName()) {
            var4 = PropertyName.construct(var1.getSimpleName());
         }

         this._rootNames.put(var3, var4);
         return var4;
      }
   }

   protected Object readResolve() {
      return new RootNameLookup();
   }
}
