package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import java.io.IOException;
import java.util.Iterator;

@JacksonStdImpl
public class IterableSerializer extends AsArraySerializerBase {
   public IterableSerializer(JavaType var1, boolean var2, TypeSerializer var3) {
      super(Iterable.class, var1, var2, var3, (JsonSerializer)null);
   }

   public IterableSerializer(IterableSerializer var1, BeanProperty var2, TypeSerializer var3, JsonSerializer var4, Boolean var5) {
      super(var1, var2, var3, var4, var5);
   }

   public ContainerSerializer _withValueTypeSerializer(TypeSerializer var1) {
      return new IterableSerializer(this, this._property, var1, this._elementSerializer, this._unwrapSingle);
   }

   public IterableSerializer withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, Boolean var4) {
      return new IterableSerializer(this, var1, var2, var3, var4);
   }

   public boolean isEmpty(SerializerProvider var1, Iterable var2) {
      return !var2.iterator().hasNext();
   }

   public boolean hasSingleElement(Iterable var1) {
      if (var1 != null) {
         Iterator var2 = var1.iterator();
         if (var2.hasNext()) {
            var2.next();
            if (!var2.hasNext()) {
               return true;
            }
         }
      }

      return false;
   }

   public final void serialize(Iterable var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if ((this._unwrapSingle == null && var3.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) || this._unwrapSingle == Boolean.TRUE) && this.hasSingleElement(var1)) {
         this.serializeContents(var1, var2, var3);
      } else {
         var2.writeStartArray();
         this.serializeContents(var1, var2, var3);
         var2.writeEndArray();
      }
   }

   public void serializeContents(Iterable var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      Iterator var4 = var1.iterator();
      if (var4.hasNext()) {
         TypeSerializer var5 = this._valueTypeSerializer;
         JsonSerializer var6 = null;
         Class var7 = null;

         do {
            Object var8 = var4.next();
            if (var8 == null) {
               var3.defaultSerializeNull(var2);
            } else {
               JsonSerializer var9 = this._elementSerializer;
               if (var9 == null) {
                  Class var10 = var8.getClass();
                  if (var10 == var7) {
                     var9 = var6;
                  } else {
                     var9 = var3.findValueSerializer(var10, this._property);
                     var6 = var9;
                     var7 = var10;
                  }
               }

               if (var5 == null) {
                  var9.serialize(var8, var2, var3);
               } else {
                  var9.serializeWithType(var8, var2, var3, var5);
               }
            }
         } while(var4.hasNext());
      }

   }

   public void serializeContents(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serializeContents((Iterable)var1, var2, var3);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Iterable)var1, var2, var3);
   }

   public AsArraySerializerBase withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, Boolean var4) {
      return this.withResolved(var1, var2, var3, var4);
   }

   public boolean hasSingleElement(Object var1) {
      return this.hasSingleElement((Iterable)var1);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (Iterable)var2);
   }
}
