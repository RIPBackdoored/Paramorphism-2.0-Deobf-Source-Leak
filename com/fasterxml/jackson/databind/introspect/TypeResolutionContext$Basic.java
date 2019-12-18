package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.reflect.Type;

public class TypeResolutionContext$Basic implements TypeResolutionContext {
   private final TypeFactory _typeFactory;
   private final TypeBindings _bindings;

   public TypeResolutionContext$Basic(TypeFactory var1, TypeBindings var2) {
      super();
      this._typeFactory = var1;
      this._bindings = var2;
   }

   public JavaType resolveType(Type var1) {
      return this._typeFactory.constructType(var1, this._bindings);
   }
}
