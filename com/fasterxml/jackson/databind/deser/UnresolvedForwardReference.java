package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnresolvedForwardReference extends JsonMappingException {
   private static final long serialVersionUID = 1L;
   private ReadableObjectId _roid;
   private List _unresolvedIds;

   public UnresolvedForwardReference(JsonParser var1, String var2, JsonLocation var3, ReadableObjectId var4) {
      super((Closeable)var1, (String)var2, (JsonLocation)var3);
      this._roid = var4;
   }

   public UnresolvedForwardReference(JsonParser var1, String var2) {
      super((Closeable)var1, (String)var2);
      this._unresolvedIds = new ArrayList();
   }

   /** @deprecated */
   @Deprecated
   public UnresolvedForwardReference(String var1, JsonLocation var2, ReadableObjectId var3) {
      super(var1, var2);
      this._roid = var3;
   }

   /** @deprecated */
   @Deprecated
   public UnresolvedForwardReference(String var1) {
      super(var1);
      this._unresolvedIds = new ArrayList();
   }

   public ReadableObjectId getRoid() {
      return this._roid;
   }

   public Object getUnresolvedId() {
      return this._roid.getKey().key;
   }

   public void addUnresolvedId(Object var1, Class var2, JsonLocation var3) {
      this._unresolvedIds.add(new UnresolvedId(var1, var2, var3));
   }

   public List getUnresolvedIds() {
      return this._unresolvedIds;
   }

   public String getMessage() {
      String var1 = super.getMessage();
      if (this._unresolvedIds == null) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder(var1);
         Iterator var3 = this._unresolvedIds.iterator();

         while(var3.hasNext()) {
            UnresolvedId var4 = (UnresolvedId)var3.next();
            var2.append(var4.toString());
            if (var3.hasNext()) {
               var2.append(", ");
            }
         }

         var2.append('.');
         return var2.toString();
      }
   }
}
