package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.util.ClassUtil;

public class ObjectIdInfo {
   protected final PropertyName _propertyName;
   protected final Class _generator;
   protected final Class _resolver;
   protected final Class _scope;
   protected final boolean _alwaysAsId;
   private static final ObjectIdInfo EMPTY;

   public ObjectIdInfo(PropertyName var1, Class var2, Class var3, Class var4) {
      this(var1, var2, var3, false, var4);
   }

   protected ObjectIdInfo(PropertyName var1, Class var2, Class var3, boolean var4) {
      this(var1, var2, var3, var4, SimpleObjectIdResolver.class);
   }

   protected ObjectIdInfo(PropertyName var1, Class var2, Class var3, boolean var4, Class var5) {
      super();
      this._propertyName = var1;
      this._scope = var2;
      this._generator = var3;
      this._alwaysAsId = var4;
      if (var5 == null) {
         var5 = SimpleObjectIdResolver.class;
      }

      this._resolver = var5;
   }

   public static ObjectIdInfo empty() {
      return EMPTY;
   }

   public ObjectIdInfo withAlwaysAsId(boolean var1) {
      return this._alwaysAsId == var1 ? this : new ObjectIdInfo(this._propertyName, this._scope, this._generator, var1, this._resolver);
   }

   public PropertyName getPropertyName() {
      return this._propertyName;
   }

   public Class getScope() {
      return this._scope;
   }

   public Class getGeneratorType() {
      return this._generator;
   }

   public Class getResolverType() {
      return this._resolver;
   }

   public boolean getAlwaysAsId() {
      return this._alwaysAsId;
   }

   public String toString() {
      return "ObjectIdInfo: propName=" + this._propertyName + ", scope=" + ClassUtil.nameOf(this._scope) + ", generatorType=" + ClassUtil.nameOf(this._generator) + ", alwaysAsId=" + this._alwaysAsId;
   }

   static {
      EMPTY = new ObjectIdInfo(PropertyName.NO_NAME, Object.class, (Class)null, false, (Class)null);
   }
}
