package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExternalTypeHandler$Builder {
   private final JavaType _beanType;
   private final List _properties = new ArrayList();
   private final Map _nameToPropertyIndex = new HashMap();

   protected ExternalTypeHandler$Builder(JavaType var1) {
      super();
      this._beanType = var1;
   }

   public void addExternal(SettableBeanProperty var1, TypeDeserializer var2) {
      Integer var3 = this._properties.size();
      this._properties.add(new ExternalTypeHandler$ExtTypedProperty(var1, var2));
      this._addPropertyIndex(var1.getName(), var3);
      this._addPropertyIndex(var2.getPropertyName(), var3);
   }

   private void _addPropertyIndex(String var1, Integer var2) {
      Object var3 = this._nameToPropertyIndex.get(var1);
      if (var3 == null) {
         this._nameToPropertyIndex.put(var1, var2);
      } else if (var3 instanceof List) {
         List var4 = (List)var3;
         var4.add(var2);
      } else {
         LinkedList var5 = new LinkedList();
         var5.add(var3);
         var5.add(var2);
         this._nameToPropertyIndex.put(var1, var5);
      }

   }

   public ExternalTypeHandler build(BeanPropertyMap var1) {
      int var2 = this._properties.size();
      ExternalTypeHandler$ExtTypedProperty[] var3 = new ExternalTypeHandler$ExtTypedProperty[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         ExternalTypeHandler$ExtTypedProperty var5 = (ExternalTypeHandler$ExtTypedProperty)this._properties.get(var4);
         String var6 = var5.getTypePropertyName();
         SettableBeanProperty var7 = var1.find(var6);
         if (var7 != null) {
            var5.linkTypeProperty(var7);
         }

         var3[var4] = var5;
      }

      return new ExternalTypeHandler(this._beanType, var3, this._nameToPropertyIndex, (String[])null, (TokenBuffer[])null);
   }
}
