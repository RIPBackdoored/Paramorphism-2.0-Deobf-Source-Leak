package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator$IdKey;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class ReadableObjectId {
   protected Object _item;
   protected final ObjectIdGenerator$IdKey _key;
   protected LinkedList _referringProperties;
   protected ObjectIdResolver _resolver;

   public ReadableObjectId(ObjectIdGenerator$IdKey var1) {
      super();
      this._key = var1;
   }

   public void setResolver(ObjectIdResolver var1) {
      this._resolver = var1;
   }

   public ObjectIdGenerator$IdKey getKey() {
      return this._key;
   }

   public void appendReferring(ReadableObjectId$Referring var1) {
      if (this._referringProperties == null) {
         this._referringProperties = new LinkedList();
      }

      this._referringProperties.add(var1);
   }

   public void bindItem(Object var1) throws IOException {
      this._resolver.bindItem(this._key, var1);
      this._item = var1;
      Object var2 = this._key.key;
      if (this._referringProperties != null) {
         Iterator var3 = this._referringProperties.iterator();
         this._referringProperties = null;

         while(var3.hasNext()) {
            ((ReadableObjectId$Referring)var3.next()).handleResolvedForwardReference(var2, var1);
         }
      }

   }

   public Object resolve() {
      return this._item = this._resolver.resolveId(this._key);
   }

   public boolean hasReferringProperties() {
      return this._referringProperties != null && !this._referringProperties.isEmpty();
   }

   public Iterator referringProperties() {
      return this._referringProperties == null ? Collections.emptyList().iterator() : this._referringProperties.iterator();
   }

   public boolean tryToResolveUnresolved(DeserializationContext var1) {
      return false;
   }

   public ObjectIdResolver getResolver() {
      return this._resolver;
   }

   public String toString() {
      return String.valueOf(this._key);
   }
}
