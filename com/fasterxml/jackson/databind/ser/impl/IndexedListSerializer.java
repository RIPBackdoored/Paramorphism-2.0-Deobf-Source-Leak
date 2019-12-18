package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
import java.io.IOException;
import java.util.List;

@JacksonStdImpl
public final class IndexedListSerializer extends AsArraySerializerBase {
   private static final long serialVersionUID = 1L;

   public IndexedListSerializer(JavaType var1, boolean var2, TypeSerializer var3, JsonSerializer var4) {
      super(List.class, var1, var2, var3, var4);
   }

   public IndexedListSerializer(IndexedListSerializer var1, BeanProperty var2, TypeSerializer var3, JsonSerializer var4, Boolean var5) {
      super(var1, var2, var3, var4, var5);
   }

   public IndexedListSerializer withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, Boolean var4) {
      return new IndexedListSerializer(this, var1, var2, var3, var4);
   }

   public boolean isEmpty(SerializerProvider var1, List var2) {
      return var2.isEmpty();
   }

   public boolean hasSingleElement(List var1) {
      return var1.size() == 1;
   }

   public ContainerSerializer _withValueTypeSerializer(TypeSerializer var1) {
      return new IndexedListSerializer(this, this._property, var1, this._elementSerializer, this._unwrapSingle);
   }

   public final void serialize(List var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      int var4 = var1.size();
      if (var4 != 1 || (this._unwrapSingle != null || !var3.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) && this._unwrapSingle != Boolean.TRUE) {
         var2.writeStartArray(var4);
         this.serializeContents(var1, var2, var3);
         var2.writeEndArray();
      } else {
         this.serializeContents(var1, var2, var3);
      }
   }

   public void serializeContents(List var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (this._elementSerializer != null) {
         this.serializeContentsUsing(var1, var2, var3, this._elementSerializer);
      } else if (this._valueTypeSerializer != null) {
         this.serializeTypedContents(var1, var2, var3);
      } else {
         int var4 = var1.size();
         if (var4 != 0) {
            int var5 = 0;

            try {
               for(PropertySerializerMap var6 = this._dynamicSerializers; var5 < var4; ++var5) {
                  Object var7 = var1.get(var5);
                  if (var7 == null) {
                     var3.defaultSerializeNull(var2);
                  } else {
                     Class var8 = var7.getClass();
                     JsonSerializer var9 = var6.serializerFor(var8);
                     if (var9 == null) {
                        if (this._elementType.hasGenericTypes()) {
                           var9 = this._findAndAddDynamic(var6, var3.constructSpecializedType(this._elementType, var8), var3);
                        } else {
                           var9 = this._findAndAddDynamic(var6, var8, var3);
                        }

                        var6 = this._dynamicSerializers;
                     }

                     var9.serialize(var7, var2, var3);
                  }
               }
            } catch (Exception var10) {
               this.wrapAndThrow(var3, var10, var1, var5);
            }

         }
      }
   }

   public void serializeContentsUsing(List var1, JsonGenerator var2, SerializerProvider var3, JsonSerializer var4) throws IOException {
      int var5 = var1.size();
      if (var5 != 0) {
         TypeSerializer var6 = this._valueTypeSerializer;

         for(int var7 = 0; var7 < var5; ++var7) {
            Object var8 = var1.get(var7);

            try {
               if (var8 == null) {
                  var3.defaultSerializeNull(var2);
               } else if (var6 == null) {
                  var4.serialize(var8, var2, var3);
               } else {
                  var4.serializeWithType(var8, var2, var3, var6);
               }
            } catch (Exception var10) {
               this.wrapAndThrow(var3, var10, var1, var7);
            }
         }

      }
   }

   public void serializeTypedContents(List var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      int var4 = var1.size();
      if (var4 != 0) {
         int var5 = 0;

         try {
            TypeSerializer var6 = this._valueTypeSerializer;

            for(PropertySerializerMap var7 = this._dynamicSerializers; var5 < var4; ++var5) {
               Object var8 = var1.get(var5);
               if (var8 == null) {
                  var3.defaultSerializeNull(var2);
               } else {
                  Class var9 = var8.getClass();
                  JsonSerializer var10 = var7.serializerFor(var9);
                  if (var10 == null) {
                     if (this._elementType.hasGenericTypes()) {
                        var10 = this._findAndAddDynamic(var7, var3.constructSpecializedType(this._elementType, var9), var3);
                     } else {
                        var10 = this._findAndAddDynamic(var7, var9, var3);
                     }

                     var7 = this._dynamicSerializers;
                  }

                  var10.serializeWithType(var8, var2, var3, var6);
               }
            }
         } catch (Exception var11) {
            this.wrapAndThrow(var3, var11, var1, var5);
         }

      }
   }

   public void serializeContents(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serializeContents((List)var1, var2, var3);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((List)var1, var2, var3);
   }

   public AsArraySerializerBase withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, Boolean var4) {
      return this.withResolved(var1, var2, var3, var4);
   }

   public boolean hasSingleElement(Object var1) {
      return this.hasSingleElement((List)var1);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (List)var2);
   }
}
