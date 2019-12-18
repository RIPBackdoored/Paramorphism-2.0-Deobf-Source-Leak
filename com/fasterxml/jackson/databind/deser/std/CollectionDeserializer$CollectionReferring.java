package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId$Referring;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class CollectionDeserializer$CollectionReferring extends ReadableObjectId$Referring {
   private final CollectionDeserializer$CollectionReferringAccumulator _parent;
   public final List next = new ArrayList();

   CollectionDeserializer$CollectionReferring(CollectionDeserializer$CollectionReferringAccumulator var1, UnresolvedForwardReference var2, Class var3) {
      super(var2, var3);
      this._parent = var1;
   }

   public void handleResolvedForwardReference(Object var1, Object var2) throws IOException {
      this._parent.resolveForwardReference(var1, var2);
   }
}
