package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

class JavaUtilCollectionsDeserializers$JavaUtilCollectionsConverter implements Converter {
   private final JavaType _inputType;
   private final int _kind;

   private JavaUtilCollectionsDeserializers$JavaUtilCollectionsConverter(int var1, JavaType var2) {
      super();
      this._inputType = var2;
      this._kind = var1;
   }

   public Object convert(Object var1) {
      if (var1 == null) {
         return null;
      } else {
         switch(this._kind) {
         case 1:
            Set var5 = (Set)var1;
            this._checkSingleton(var5.size());
            return Collections.singleton(var5.iterator().next());
         case 2:
            List var4 = (List)var1;
            this._checkSingleton(var4.size());
            return Collections.singletonList(var4.get(0));
         case 3:
            Map var2 = (Map)var1;
            this._checkSingleton(var2.size());
            Entry var3 = (Entry)var2.entrySet().iterator().next();
            return Collections.singletonMap(var3.getKey(), var3.getValue());
         case 4:
            return Collections.unmodifiableSet((Set)var1);
         case 5:
            return Collections.unmodifiableList((List)var1);
         case 6:
            return Collections.unmodifiableMap((Map)var1);
         case 7:
         default:
            return var1;
         }
      }
   }

   public JavaType getInputType(TypeFactory var1) {
      return this._inputType;
   }

   public JavaType getOutputType(TypeFactory var1) {
      return this._inputType;
   }

   private void _checkSingleton(int var1) {
      if (var1 != 1) {
         throw new IllegalArgumentException("Can not deserialize Singleton container from " + var1 + " entries");
      }
   }

   JavaUtilCollectionsDeserializers$JavaUtilCollectionsConverter(int var1, JavaType var2, JavaUtilCollectionsDeserializers$1 var3) {
      this(var1, var2);
   }
}
