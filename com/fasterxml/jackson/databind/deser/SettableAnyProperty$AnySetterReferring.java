package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId$Referring;
import java.io.IOException;

class SettableAnyProperty$AnySetterReferring extends ReadableObjectId$Referring {
   private final SettableAnyProperty _parent;
   private final Object _pojo;
   private final String _propName;

   public SettableAnyProperty$AnySetterReferring(SettableAnyProperty var1, UnresolvedForwardReference var2, Class var3, Object var4, String var5) {
      super(var2, var3);
      this._parent = var1;
      this._pojo = var4;
      this._propName = var5;
   }

   public void handleResolvedForwardReference(Object var1, Object var2) throws IOException {
      if (!this.hasId(var1)) {
         throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + var1.toString() + "] that wasn't previously registered.");
      } else {
         this._parent.set(this._pojo, this._propName, var2);
      }
   }
}
