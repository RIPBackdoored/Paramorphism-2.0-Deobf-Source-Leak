package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExternalTypeHandler {
   private final JavaType _beanType;
   private final ExternalTypeHandler$ExtTypedProperty[] _properties;
   private final Map _nameToPropertyIndex;
   private final String[] _typeIds;
   private final TokenBuffer[] _tokens;

   protected ExternalTypeHandler(JavaType var1, ExternalTypeHandler$ExtTypedProperty[] var2, Map var3, String[] var4, TokenBuffer[] var5) {
      super();
      this._beanType = var1;
      this._properties = var2;
      this._nameToPropertyIndex = var3;
      this._typeIds = var4;
      this._tokens = var5;
   }

   protected ExternalTypeHandler(ExternalTypeHandler var1) {
      super();
      this._beanType = var1._beanType;
      this._properties = var1._properties;
      this._nameToPropertyIndex = var1._nameToPropertyIndex;
      int var2 = this._properties.length;
      this._typeIds = new String[var2];
      this._tokens = new TokenBuffer[var2];
   }

   public static ExternalTypeHandler$Builder builder(JavaType var0) {
      return new ExternalTypeHandler$Builder(var0);
   }

   public ExternalTypeHandler start() {
      return new ExternalTypeHandler(this);
   }

   public boolean handleTypePropertyValue(JsonParser var1, DeserializationContext var2, String var3, Object var4) throws IOException {
      Object var5 = this._nameToPropertyIndex.get(var3);
      if (var5 == null) {
         return false;
      } else {
         String var6 = var1.getText();
         if (var5 instanceof List) {
            boolean var7 = false;
            Iterator var8 = ((List)var5).iterator();

            while(var8.hasNext()) {
               Integer var9 = (Integer)var8.next();
               if (this._handleTypePropertyValue(var1, var2, var3, var4, var6, var9)) {
                  var7 = true;
               }
            }

            return var7;
         } else {
            return this._handleTypePropertyValue(var1, var2, var3, var4, var6, (Integer)var5);
         }
      }
   }

   private final boolean _handleTypePropertyValue(JsonParser var1, DeserializationContext var2, String var3, Object var4, String var5, int var6) throws IOException {
      ExternalTypeHandler$ExtTypedProperty var7 = this._properties[var6];
      if (!var7.hasTypePropertyName(var3)) {
         return false;
      } else {
         boolean var8 = var4 != null && this._tokens[var6] != null;
         if (var8) {
            this._deserializeAndSet(var1, var2, var4, var6, var5);
            this._tokens[var6] = null;
         } else {
            this._typeIds[var6] = var5;
         }

         return true;
      }
   }

   public boolean handlePropertyValue(JsonParser var1, DeserializationContext var2, String var3, Object var4) throws IOException {
      Object var5 = this._nameToPropertyIndex.get(var3);
      if (var5 == null) {
         return false;
      } else {
         TokenBuffer var9;
         String var13;
         if (var5 instanceof List) {
            Iterator var10 = ((List)var5).iterator();
            Integer var11 = (Integer)var10.next();
            ExternalTypeHandler$ExtTypedProperty var12 = this._properties[var11];
            if (var12.hasTypePropertyName(var3)) {
               var13 = var1.getText();
               var1.skipChildren();

               for(this._typeIds[var11] = var13; var10.hasNext(); this._typeIds[(Integer)var10.next()] = var13) {
               }
            } else {
               var9 = new TokenBuffer(var1, var2);
               var9.copyCurrentStructure(var1);

               for(this._tokens[var11] = var9; var10.hasNext(); this._tokens[(Integer)var10.next()] = var9) {
               }
            }

            return true;
         } else {
            int var6 = (Integer)var5;
            ExternalTypeHandler$ExtTypedProperty var7 = this._properties[var6];
            boolean var8;
            if (var7.hasTypePropertyName(var3)) {
               this._typeIds[var6] = var1.getText();
               var1.skipChildren();
               var8 = var4 != null && this._tokens[var6] != null;
            } else {
               var9 = new TokenBuffer(var1, var2);
               var9.copyCurrentStructure(var1);
               this._tokens[var6] = var9;
               var8 = var4 != null && this._typeIds[var6] != null;
            }

            if (var8) {
               var13 = this._typeIds[var6];
               this._typeIds[var6] = null;
               this._deserializeAndSet(var1, var2, var4, var6, var13);
               this._tokens[var6] = null;
            }

            return true;
         }
      }
   }

   public Object complete(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      int var4 = 0;

      for(int var5 = this._properties.length; var4 < var5; ++var4) {
         String var6 = this._typeIds[var4];
         if (var6 == null) {
            TokenBuffer var7 = this._tokens[var4];
            if (var7 == null) {
               continue;
            }

            JsonToken var8 = var7.firstToken();
            if (var8.isScalarValue()) {
               JsonParser var9 = var7.asParser(var1);
               var9.nextToken();
               SettableBeanProperty var10 = this._properties[var4].getProperty();
               Object var11 = TypeDeserializer.deserializeIfNatural(var9, var2, var10.getType());
               if (var11 != null) {
                  var10.set(var3, var11);
                  continue;
               }

               if (!this._properties[var4].hasDefaultType()) {
                  var2.reportInputMismatch(var3.getClass(), "Missing external type id property '%s'", this._properties[var4].getTypePropertyName());
               } else {
                  var6 = this._properties[var4].getDefaultTypeId();
               }
            }
         } else if (this._tokens[var4] == null) {
            SettableBeanProperty var12 = this._properties[var4].getProperty();
            if (var12.isRequired() || var2.isEnabled(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY)) {
               var2.reportInputMismatch(var3.getClass(), "Missing property '%s' for external type id '%s'", var12.getName(), this._properties[var4].getTypePropertyName());
            }

            return var3;
         }

         this._deserializeAndSet(var1, var2, var3, var4, var6);
      }

      return var3;
   }

   public Object complete(JsonParser var1, DeserializationContext var2, PropertyValueBuffer var3, PropertyBasedCreator var4) throws IOException {
      int var5 = this._properties.length;
      Object[] var6 = new Object[var5];

      for(int var7 = 0; var7 < var5; ++var7) {
         String var8 = this._typeIds[var7];
         ExternalTypeHandler$ExtTypedProperty var9 = this._properties[var7];
         SettableBeanProperty var10;
         if (var8 == null) {
            if (this._tokens[var7] == null) {
               continue;
            }

            if (!var9.hasDefaultType()) {
               var2.reportInputMismatch(this._beanType, "Missing external type id property '%s'", var9.getTypePropertyName());
            } else {
               var8 = var9.getDefaultTypeId();
            }
         } else if (this._tokens[var7] == null) {
            var10 = var9.getProperty();
            var2.reportInputMismatch(this._beanType, "Missing property '%s' for external type id '%s'", var10.getName(), this._properties[var7].getTypePropertyName());
         }

         var6[var7] = this._deserialize(var1, var2, var7, var8);
         var10 = var9.getProperty();
         if (var10.getCreatorIndex() >= 0) {
            var3.assignParameter(var10, var6[var7]);
            SettableBeanProperty var11 = var9.getTypeProperty();
            if (var11 != null && var11.getCreatorIndex() >= 0) {
               Object var12;
               if (var11.getType().hasRawClass(String.class)) {
                  var12 = var8;
               } else {
                  TokenBuffer var13 = new TokenBuffer(var1, var2);
                  var13.writeString(var8);
                  var12 = var11.getValueDeserializer().deserialize(var13.asParserOnFirstToken(), var2);
                  var13.close();
               }

               var3.assignParameter(var11, var12);
            }
         }
      }

      Object var14 = var4.build(var2, var3);

      for(int var15 = 0; var15 < var5; ++var15) {
         SettableBeanProperty var16 = this._properties[var15].getProperty();
         if (var16.getCreatorIndex() < 0) {
            var16.set(var14, var6[var15]);
         }
      }

      return var14;
   }

   protected final Object _deserialize(JsonParser var1, DeserializationContext var2, int var3, String var4) throws IOException {
      JsonParser var5 = this._tokens[var3].asParser(var1);
      JsonToken var6 = var5.nextToken();
      if (var6 == JsonToken.VALUE_NULL) {
         return null;
      } else {
         TokenBuffer var7 = new TokenBuffer(var1, var2);
         var7.writeStartArray();
         var7.writeString(var4);
         var7.copyCurrentStructure(var5);
         var7.writeEndArray();
         JsonParser var8 = var7.asParser(var1);
         var8.nextToken();
         return this._properties[var3].getProperty().deserialize(var8, var2);
      }
   }

   protected final void _deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3, int var4, String var5) throws IOException {
      JsonParser var6 = this._tokens[var4].asParser(var1);
      JsonToken var7 = var6.nextToken();
      if (var7 == JsonToken.VALUE_NULL) {
         this._properties[var4].getProperty().set(var3, (Object)null);
      } else {
         TokenBuffer var8 = new TokenBuffer(var1, var2);
         var8.writeStartArray();
         var8.writeString(var5);
         var8.copyCurrentStructure(var6);
         var8.writeEndArray();
         JsonParser var9 = var8.asParser(var1);
         var9.nextToken();
         this._properties[var4].getProperty().deserializeAndSet(var9, var2, var3);
      }
   }
}
