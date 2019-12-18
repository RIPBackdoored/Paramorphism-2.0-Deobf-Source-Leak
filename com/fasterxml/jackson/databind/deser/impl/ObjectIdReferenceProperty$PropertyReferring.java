package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import java.io.IOException;

public final class ObjectIdReferenceProperty$PropertyReferring extends ReadableObjectId$Referring {
   private final ObjectIdReferenceProperty _parent;
   public final Object _pojo;

   public ObjectIdReferenceProperty$PropertyReferring(ObjectIdReferenceProperty var1, UnresolvedForwardReference var2, Class var3, Object var4) {
      super(var2, var3);
      this._parent = var1;
      this._pojo = var4;
   }

   public void handleResolvedForwardReference(Object var1, Object var2) throws IOException {
      if (!this.hasId(var1)) {
         throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + var1 + "] that wasn't previously seen as unresolved.");
      } else {
         this._parent.set(this._pojo, var2);
      }
   }
}
