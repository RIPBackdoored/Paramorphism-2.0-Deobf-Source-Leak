package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId$Referring;
import java.io.IOException;

class BeanDeserializer$BeanReferring extends ReadableObjectId$Referring {
   private final DeserializationContext _context;
   private final SettableBeanProperty _prop;
   private Object _bean;

   BeanDeserializer$BeanReferring(DeserializationContext var1, UnresolvedForwardReference var2, JavaType var3, PropertyValueBuffer var4, SettableBeanProperty var5) {
      super(var2, var3);
      this._context = var1;
      this._prop = var5;
   }

   public void setBean(Object var1) {
      this._bean = var1;
   }

   public void handleResolvedForwardReference(Object var1, Object var2) throws IOException {
      if (this._bean == null) {
         this._context.reportInputMismatch((BeanProperty)this._prop, "Cannot resolve ObjectId forward reference using property '%s' (of type %s): Bean not yet resolved", this._prop.getName(), this._prop.getDeclaringClass().getName());
      }

      this._prop.set(this._bean, var2);
   }
}
