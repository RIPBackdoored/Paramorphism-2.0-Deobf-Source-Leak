package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import java.util.Collection;
import java.util.Map;

public class SimpleType extends TypeBase {
   private static final long serialVersionUID = 1L;

   protected SimpleType(Class var1) {
      this(var1, TypeBindings.emptyBindings(), (JavaType)null, (JavaType[])null);
   }

   protected SimpleType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      this(var1, var2, var3, var4, (Object)null, (Object)null, false);
   }

   protected SimpleType(TypeBase var1) {
      super(var1);
   }

   protected SimpleType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4, Object var5, Object var6, boolean var7) {
      super(var1, var2, var3, var4, 0, var5, var6, var7);
   }

   protected SimpleType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4, int var5, Object var6, Object var7, boolean var8) {
      super(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public static SimpleType constructUnsafe(Class var0) {
      return new SimpleType(var0, (TypeBindings)null, (JavaType)null, (JavaType[])null, (Object)null, (Object)null, false);
   }

   /** @deprecated */
   @Deprecated
   public static SimpleType construct(Class var0) {
      if (Map.class.isAssignableFrom(var0)) {
         throw new IllegalArgumentException("Cannot construct SimpleType for a Map (class: " + var0.getName() + ")");
      } else if (Collection.class.isAssignableFrom(var0)) {
         throw new IllegalArgumentException("Cannot construct SimpleType for a Collection (class: " + var0.getName() + ")");
      } else if (var0.isArray()) {
         throw new IllegalArgumentException("Cannot construct SimpleType for an array (class: " + var0.getName() + ")");
      } else {
         TypeBindings var1 = TypeBindings.emptyBindings();
         return new SimpleType(var0, var1, _buildSuperClass(var0.getSuperclass(), var1), (JavaType[])null, (Object)null, (Object)null, false);
      }
   }

   /** @deprecated */
   @Deprecated
   protected JavaType _narrow(Class var1) {
      if (this._class == var1) {
         return this;
      } else if (!this._class.isAssignableFrom(var1)) {
         return new SimpleType(var1, this._bindings, this, this._superInterfaces, this._valueHandler, this._typeHandler, this._asStatic);
      } else {
         Class var2 = var1.getSuperclass();
         if (var2 == this._class) {
            return new SimpleType(var1, this._bindings, this, this._superInterfaces, this._valueHandler, this._typeHandler, this._asStatic);
         } else if (var2 != null && this._class.isAssignableFrom(var2)) {
            JavaType var9 = this._narrow(var2);
            return new SimpleType(var1, this._bindings, var9, (JavaType[])null, this._valueHandler, this._typeHandler, this._asStatic);
         } else {
            Class[] var3 = var1.getInterfaces();
            Class[] var4 = var3;
            int var5 = var3.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Class var7 = var4[var6];
               if (var7 == this._class) {
                  return new SimpleType(var1, this._bindings, (JavaType)null, new JavaType[]{this}, this._valueHandler, this._typeHandler, this._asStatic);
               }

               if (this._class.isAssignableFrom(var7)) {
                  JavaType var8 = this._narrow(var7);
                  return new SimpleType(var1, this._bindings, (JavaType)null, new JavaType[]{var8}, this._valueHandler, this._typeHandler, this._asStatic);
               }
            }

            throw new IllegalArgumentException("Internal error: Cannot resolve sub-type for Class " + var1.getName() + " to " + this._class.getName());
         }
      }
   }

   public JavaType withContentType(JavaType var1) {
      throw new IllegalArgumentException("Simple types have no content types; cannot call withContentType()");
   }

   public SimpleType withTypeHandler(Object var1) {
      return this._typeHandler == var1 ? this : new SimpleType(this._class, this._bindings, this._superClass, this._superInterfaces, this._valueHandler, var1, this._asStatic);
   }

   public JavaType withContentTypeHandler(Object var1) {
      throw new IllegalArgumentException("Simple types have no content types; cannot call withContenTypeHandler()");
   }

   public SimpleType withValueHandler(Object var1) {
      return var1 == this._valueHandler ? this : new SimpleType(this._class, this._bindings, this._superClass, this._superInterfaces, var1, this._typeHandler, this._asStatic);
   }

   public SimpleType withContentValueHandler(Object var1) {
      throw new IllegalArgumentException("Simple types have no content types; cannot call withContenValueHandler()");
   }

   public SimpleType withStaticTyping() {
      return this._asStatic ? this : new SimpleType(this._class, this._bindings, this._superClass, this._superInterfaces, this._valueHandler, this._typeHandler, true);
   }

   public JavaType refine(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      return null;
   }

   protected String buildCanonicalName() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this._class.getName());
      int var2 = this._bindings.size();
      if (var2 > 0) {
         var1.append('<');

         for(int var3 = 0; var3 < var2; ++var3) {
            JavaType var4 = this.containedType(var3);
            if (var3 > 0) {
               var1.append(',');
            }

            var1.append(var4.toCanonical());
         }

         var1.append('>');
      }

      return var1.toString();
   }

   public boolean isContainerType() {
      return false;
   }

   public boolean hasContentType() {
      return false;
   }

   public StringBuilder getErasedSignature(StringBuilder var1) {
      return _classSignature(this._class, var1, true);
   }

   public StringBuilder getGenericSignature(StringBuilder var1) {
      _classSignature(this._class, var1, false);
      int var2 = this._bindings.size();
      if (var2 > 0) {
         var1.append('<');

         for(int var3 = 0; var3 < var2; ++var3) {
            var1 = this.containedType(var3).getGenericSignature(var1);
         }

         var1.append('>');
      }

      var1.append(';');
      return var1;
   }

   private static JavaType _buildSuperClass(Class var0, TypeBindings var1) {
      if (var0 == null) {
         return null;
      } else if (var0 == Object.class) {
         return TypeFactory.unknownType();
      } else {
         JavaType var2 = _buildSuperClass(var0.getSuperclass(), var1);
         return new SimpleType(var0, var1, var2, (JavaType[])null, (Object)null, (Object)null, false);
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(40);
      var1.append("[simple type, class ").append(this.buildCanonicalName()).append(']');
      return var1.toString();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         SimpleType var2 = (SimpleType)var1;
         if (var2._class != this._class) {
            return false;
         } else {
            TypeBindings var3 = this._bindings;
            TypeBindings var4 = var2._bindings;
            return var3.equals(var4);
         }
      }
   }

   public JavaType withStaticTyping() {
      return this.withStaticTyping();
   }

   public JavaType withContentValueHandler(Object var1) {
      return this.withContentValueHandler(var1);
   }

   public JavaType withValueHandler(Object var1) {
      return this.withValueHandler(var1);
   }

   public JavaType withTypeHandler(Object var1) {
      return this.withTypeHandler(var1);
   }
}
