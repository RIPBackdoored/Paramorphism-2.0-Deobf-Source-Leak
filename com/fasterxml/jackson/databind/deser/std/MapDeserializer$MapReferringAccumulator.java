package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId$Referring;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class MapDeserializer$MapReferringAccumulator {
   private final Class _valueType;
   private Map _result;
   private List _accumulator = new ArrayList();

   public MapDeserializer$MapReferringAccumulator(Class var1, Map var2) {
      super();
      this._valueType = var1;
      this._result = var2;
   }

   public void put(Object var1, Object var2) {
      if (this._accumulator.isEmpty()) {
         this._result.put(var1, var2);
      } else {
         MapDeserializer$MapReferring var3 = (MapDeserializer$MapReferring)this._accumulator.get(this._accumulator.size() - 1);
         var3.next.put(var1, var2);
      }

   }

   public ReadableObjectId$Referring handleUnresolvedReference(UnresolvedForwardReference var1, Object var2) {
      MapDeserializer$MapReferring var3 = new MapDeserializer$MapReferring(this, var1, this._valueType, var2);
      this._accumulator.add(var3);
      return var3;
   }

   public void resolveForwardReference(Object var1, Object var2) throws IOException {
      Iterator var3 = this._accumulator.iterator();

      MapDeserializer$MapReferring var5;
      for(Map var4 = this._result; var3.hasNext(); var4 = var5.next) {
         var5 = (MapDeserializer$MapReferring)var3.next();
         if (var5.hasId(var1)) {
            var3.remove();
            var4.put(var5.key, var2);
            var4.putAll(var5.next);
            return;
         }
      }

      throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + var1 + "] that wasn't previously seen as unresolved.");
   }
}
