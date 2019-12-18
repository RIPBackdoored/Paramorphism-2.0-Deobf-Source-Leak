package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnwrappedPropertyHandler {
   protected final List _properties;

   public UnwrappedPropertyHandler() {
      super();
      this._properties = new ArrayList();
   }

   protected UnwrappedPropertyHandler(List var1) {
      super();
      this._properties = var1;
   }

   public void addProperty(SettableBeanProperty var1) {
      this._properties.add(var1);
   }

   public UnwrappedPropertyHandler renameAll(NameTransformer var1) {
      ArrayList var2 = new ArrayList(this._properties.size());

      SettableBeanProperty var4;
      for(Iterator var3 = this._properties.iterator(); var3.hasNext(); var2.add(var4)) {
         var4 = (SettableBeanProperty)var3.next();
         String var5 = var1.transform(var4.getName());
         var4 = var4.withSimpleName(var5);
         JsonDeserializer var6 = var4.getValueDeserializer();
         if (var6 != null) {
            JsonDeserializer var7 = var6.unwrappingDeserializer(var1);
            if (var7 != var6) {
               var4 = var4.withValueDeserializer(var7);
            }
         }
      }

      return new UnwrappedPropertyHandler(var2);
   }

   public Object processUnwrapped(JsonParser var1, DeserializationContext var2, Object var3, TokenBuffer var4) throws IOException {
      int var5 = 0;

      for(int var6 = this._properties.size(); var5 < var6; ++var5) {
         SettableBeanProperty var7 = (SettableBeanProperty)this._properties.get(var5);
         JsonParser var8 = var4.asParser();
         var8.nextToken();
         var7.deserializeAndSet(var8, var2, var3);
      }

      return var3;
   }
}
