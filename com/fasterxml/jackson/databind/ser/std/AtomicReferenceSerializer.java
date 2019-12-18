package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceSerializer extends ReferenceTypeSerializer {
   private static final long serialVersionUID = 1L;

   public AtomicReferenceSerializer(ReferenceType var1, boolean var2, TypeSerializer var3, JsonSerializer var4) {
      super(var1, var2, var3, var4);
   }

   protected AtomicReferenceSerializer(AtomicReferenceSerializer var1, BeanProperty var2, TypeSerializer var3, JsonSerializer var4, NameTransformer var5, Object var6, boolean var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   protected ReferenceTypeSerializer withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, NameTransformer var4) {
      return new AtomicReferenceSerializer(this, var1, var2, var3, var4, this._suppressableValue, this._suppressNulls);
   }

   public ReferenceTypeSerializer withContentInclusion(Object var1, boolean var2) {
      return new AtomicReferenceSerializer(this, this._property, this._valueTypeSerializer, this._valueSerializer, this._unwrapper, var1, var2);
   }

   protected boolean _isValuePresent(AtomicReference var1) {
      return var1.get() != null;
   }

   protected Object _getReferenced(AtomicReference var1) {
      return var1.get();
   }

   protected Object _getReferencedIfPresent(AtomicReference var1) {
      return var1.get();
   }

   protected Object _getReferencedIfPresent(Object var1) {
      return this._getReferencedIfPresent((AtomicReference)var1);
   }

   protected Object _getReferenced(Object var1) {
      return this._getReferenced((AtomicReference)var1);
   }

   protected boolean _isValuePresent(Object var1) {
      return this._isValuePresent((AtomicReference)var1);
   }
}
