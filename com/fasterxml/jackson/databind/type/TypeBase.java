package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class TypeBase extends JavaType implements JsonSerializable {
   private static final long serialVersionUID = 1L;
   private static final TypeBindings NO_BINDINGS = TypeBindings.emptyBindings();
   private static final JavaType[] NO_TYPES = new JavaType[0];
   protected final JavaType _superClass;
   protected final JavaType[] _superInterfaces;
   protected final TypeBindings _bindings;
   transient String _canonicalName;

   protected TypeBase(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4, int var5, Object var6, Object var7, boolean var8) {
      super(var1, var5, var6, var7, var8);
      this._bindings = var2 == null ? NO_BINDINGS : var2;
      this._superClass = var3;
      this._superInterfaces = var4;
   }

   protected TypeBase(TypeBase var1) {
      super(var1);
      this._superClass = var1._superClass;
      this._superInterfaces = var1._superInterfaces;
      this._bindings = var1._bindings;
   }

   public String toCanonical() {
      String var1 = this._canonicalName;
      if (var1 == null) {
         var1 = this.buildCanonicalName();
      }

      return var1;
   }

   protected String buildCanonicalName() {
      return this._class.getName();
   }

   public abstract StringBuilder getGenericSignature(StringBuilder var1);

   public abstract StringBuilder getErasedSignature(StringBuilder var1);

   public TypeBindings getBindings() {
      return this._bindings;
   }

   public int containedTypeCount() {
      return this._bindings.size();
   }

   public JavaType containedType(int var1) {
      return this._bindings.getBoundType(var1);
   }

   /** @deprecated */
   @Deprecated
   public String containedTypeName(int var1) {
      return this._bindings.getBoundName(var1);
   }

   public JavaType getSuperClass() {
      return this._superClass;
   }

   public List getInterfaces() {
      if (this._superInterfaces == null) {
         return Collections.emptyList();
      } else {
         switch(this._superInterfaces.length) {
         case 0:
            return Collections.emptyList();
         case 1:
            return Collections.singletonList(this._superInterfaces[0]);
         default:
            return Arrays.asList(this._superInterfaces);
         }
      }
   }

   public final JavaType findSuperType(Class var1) {
      if (var1 == this._class) {
         return this;
      } else {
         if (var1.isInterface() && this._superInterfaces != null) {
            int var2 = 0;

            for(int var3 = this._superInterfaces.length; var2 < var3; ++var2) {
               JavaType var4 = this._superInterfaces[var2].findSuperType(var1);
               if (var4 != null) {
                  return var4;
               }
            }
         }

         if (this._superClass != null) {
            JavaType var5 = this._superClass.findSuperType(var1);
            if (var5 != null) {
               return var5;
            }
         }

         return null;
      }
   }

   public JavaType[] findTypeParameters(Class var1) {
      JavaType var2 = this.findSuperType(var1);
      return var2 == null ? NO_TYPES : var2.getBindings().typeParameterArray();
   }

   public void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException {
      WritableTypeId var4 = new WritableTypeId(this, JsonToken.VALUE_STRING);
      var3.writeTypePrefix(var1, var4);
      this.serialize(var1, var2);
      var3.writeTypeSuffix(var1, var4);
   }

   public void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      var1.writeString(this.toCanonical());
   }

   protected static StringBuilder _classSignature(Class var0, StringBuilder var1, boolean var2) {
      if (var0.isPrimitive()) {
         if (var0 == Boolean.TYPE) {
            var1.append('Z');
         } else if (var0 == Byte.TYPE) {
            var1.append('B');
         } else if (var0 == Short.TYPE) {
            var1.append('S');
         } else if (var0 == Character.TYPE) {
            var1.append('C');
         } else if (var0 == Integer.TYPE) {
            var1.append('I');
         } else if (var0 == Long.TYPE) {
            var1.append('J');
         } else if (var0 == Float.TYPE) {
            var1.append('F');
         } else if (var0 == Double.TYPE) {
            var1.append('D');
         } else {
            if (var0 != Void.TYPE) {
               throw new IllegalStateException("Unrecognized primitive type: " + var0.getName());
            }

            var1.append('V');
         }
      } else {
         var1.append('L');
         String var3 = var0.getName();
         int var4 = 0;

         for(int var5 = var3.length(); var4 < var5; ++var4) {
            char var6 = var3.charAt(var4);
            if (var6 == '.') {
               var6 = '/';
            }

            var1.append(var6);
         }

         if (var2) {
            var1.append(';');
         }
      }

      return var1;
   }

   protected static JavaType _bogusSuperClass(Class var0) {
      Class var1 = var0.getSuperclass();
      return var1 == null ? null : TypeFactory.unknownType();
   }

   public ResolvedType containedType(int var1) {
      return this.containedType(var1);
   }
}
