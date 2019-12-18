package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TypeParser implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final TypeFactory _factory;

   public TypeParser(TypeFactory var1) {
      super();
      this._factory = var1;
   }

   public TypeParser withFactory(TypeFactory var1) {
      return var1 == this._factory ? this : new TypeParser(var1);
   }

   public JavaType parse(String var1) throws IllegalArgumentException {
      TypeParser$MyTokenizer var2 = new TypeParser$MyTokenizer(var1.trim());
      JavaType var3 = this.parseType(var2);
      if (var2.hasMoreTokens()) {
         throw this._problem(var2, "Unexpected tokens after complete type");
      } else {
         return var3;
      }
   }

   protected JavaType parseType(TypeParser$MyTokenizer var1) throws IllegalArgumentException {
      if (!var1.hasMoreTokens()) {
         throw this._problem(var1, "Unexpected end-of-string");
      } else {
         Class var2 = this.findClass(var1.nextToken(), var1);
         if (var1.hasMoreTokens()) {
            String var3 = var1.nextToken();
            if ("<".equals(var3)) {
               List var4 = this.parseTypes(var1);
               TypeBindings var5 = TypeBindings.create(var2, var4);
               return this._factory._fromClass((ClassStack)null, var2, var5);
            }

            var1.pushBack(var3);
         }

         return this._factory._fromClass((ClassStack)null, var2, TypeBindings.emptyBindings());
      }
   }

   protected List parseTypes(TypeParser$MyTokenizer var1) throws IllegalArgumentException {
      ArrayList var2 = new ArrayList();

      while(var1.hasMoreTokens()) {
         var2.add(this.parseType(var1));
         if (!var1.hasMoreTokens()) {
            break;
         }

         String var3 = var1.nextToken();
         if (">".equals(var3)) {
            return var2;
         }

         if (!",".equals(var3)) {
            throw this._problem(var1, "Unexpected token '" + var3 + "', expected ',' or '>')");
         }
      }

      throw this._problem(var1, "Unexpected end-of-string");
   }

   protected Class findClass(String var1, TypeParser$MyTokenizer var2) {
      Class var10000;
      try {
         var10000 = this._factory.findClass(var1);
      } catch (Exception var4) {
         ClassUtil.throwIfRTE(var4);
         throw this._problem(var2, "Cannot locate class '" + var1 + "', problem: " + var4.getMessage());
      }

      return var10000;
   }

   protected IllegalArgumentException _problem(TypeParser$MyTokenizer var1, String var2) {
      return new IllegalArgumentException(String.format("Failed to parse type '%s' (remaining: '%s'): %s", var1.getAllInput(), var1.getRemainingInput(), var2));
   }
}
