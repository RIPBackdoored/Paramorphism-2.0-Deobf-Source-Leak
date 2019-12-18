package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize$Typing;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

public abstract class ReferenceTypeSerializer extends StdSerializer implements ContextualSerializer {
   private static final long serialVersionUID = 1L;
   public static final Object MARKER_FOR_EMPTY;
   protected final JavaType _referredType;
   protected final BeanProperty _property;
   protected final TypeSerializer _valueTypeSerializer;
   protected final JsonSerializer _valueSerializer;
   protected final NameTransformer _unwrapper;
   protected transient PropertySerializerMap _dynamicSerializers;
   protected final Object _suppressableValue;
   protected final boolean _suppressNulls;

   public ReferenceTypeSerializer(ReferenceType var1, boolean var2, TypeSerializer var3, JsonSerializer var4) {
      super((JavaType)var1);
      this._referredType = var1.getReferencedType();
      this._property = null;
      this._valueTypeSerializer = var3;
      this._valueSerializer = var4;
      this._unwrapper = null;
      this._suppressableValue = null;
      this._suppressNulls = false;
      this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
   }

   protected ReferenceTypeSerializer(ReferenceTypeSerializer var1, BeanProperty var2, TypeSerializer var3, JsonSerializer var4, NameTransformer var5, Object var6, boolean var7) {
      super((StdSerializer)var1);
      this._referredType = var1._referredType;
      this._dynamicSerializers = var1._dynamicSerializers;
      this._property = var2;
      this._valueTypeSerializer = var3;
      this._valueSerializer = var4;
      this._unwrapper = var5;
      this._suppressableValue = var6;
      this._suppressNulls = var7;
   }

   public JsonSerializer unwrappingSerializer(NameTransformer var1) {
      JsonSerializer var2 = this._valueSerializer;
      if (var2 != null) {
         var2 = var2.unwrappingSerializer(var1);
      }

      NameTransformer var3 = this._unwrapper == null ? var1 : NameTransformer.chainedTransformer(var1, this._unwrapper);
      return this._valueSerializer == var2 && this._unwrapper == var3 ? this : this.withResolved(this._property, this._valueTypeSerializer, var2, var3);
   }

   protected abstract ReferenceTypeSerializer withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, NameTransformer var4);

   public abstract ReferenceTypeSerializer withContentInclusion(Object var1, boolean var2);

   protected abstract boolean _isValuePresent(Object var1);

   protected abstract Object _getReferenced(Object var1);

   protected abstract Object _getReferencedIfPresent(Object var1);

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   protected boolean _useStatic(SerializerProvider var1, BeanProperty var2, JavaType var3) {
      if (var3.isJavaLangObject()) {
         return false;
      } else if (var3.isFinal()) {
         return true;
      } else if (var3.useStaticType()) {
         return true;
      } else {
         AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
         if (var4 != null && var2 != null) {
            AnnotatedMember var5 = var2.getMember();
            if (var5 != null) {
               JsonSerialize$Typing var6 = var4.findSerializationTyping(var2.getMember());
               if (var6 == JsonSerialize$Typing.STATIC) {
                  return true;
               }

               if (var6 == JsonSerialize$Typing.DYNAMIC) {
                  return false;
               }
            }
         }

         return var1.isEnabled(MapperFeature.USE_STATIC_TYPING);
      }
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      if (!this._isValuePresent(var2)) {
         return true;
      } else {
         Object var3 = this._getReferenced(var2);
         if (var3 == null) {
            return this._suppressNulls;
         } else if (this._suppressableValue == null) {
            return false;
         } else {
            JsonSerializer var4 = this._valueSerializer;
            if (var4 == null) {
               try {
                  var4 = this._findCachedSerializer(var1, var3.getClass());
               } catch (JsonMappingException var6) {
                  throw new RuntimeJsonMappingException(var6);
               }
            }

            return this._suppressableValue == MARKER_FOR_EMPTY ? var4.isEmpty(var1, var3) : this._suppressableValue.equals(var3);
         }
      }
   }

   public boolean isUnwrappingSerializer() {
      return this._unwrapper != null;
   }

   public JavaType getReferredType() {
      return this._referredType;
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      Object var4 = this._getReferencedIfPresent(var1);
      if (var4 == null) {
         if (this._unwrapper == null) {
            var3.defaultSerializeNull(var2);
         }

      } else {
         JsonSerializer var5 = this._valueSerializer;
         if (var5 == null) {
            var5 = this._findCachedSerializer(var3, var4.getClass());
         }

         if (this._valueTypeSerializer != null) {
            var5.serializeWithType(var4, var2, var3, this._valueTypeSerializer);
         } else {
            var5.serialize(var4, var2, var3);
         }

      }
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      Object var5 = this._getReferencedIfPresent(var1);
      if (var5 == null) {
         if (this._unwrapper == null) {
            var3.defaultSerializeNull(var2);
         }

      } else {
         JsonSerializer var6 = this._valueSerializer;
         if (var6 == null) {
            var6 = this._findCachedSerializer(var3, var5.getClass());
         }

         var6.serializeWithType(var5, var2, var3, var4);
      }
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      JsonSerializer var3 = this._valueSerializer;
      if (var3 == null) {
         var3 = this._findSerializer(var1.getProvider(), this._referredType, this._property);
         if (this._unwrapper != null) {
            var3 = var3.unwrappingSerializer(this._unwrapper);
         }
      }

      var3.acceptJsonFormatVisitor(var1, this._referredType);
   }

   private final JsonSerializer _findCachedSerializer(SerializerProvider var1, Class var2) throws JsonMappingException {
      JsonSerializer var3 = this._dynamicSerializers.serializerFor(var2);
      if (var3 == null) {
         if (this._referredType.hasGenericTypes()) {
            JavaType var4 = var1.constructSpecializedType(this._referredType, var2);
            var3 = var1.findValueSerializer(var4, this._property);
         } else {
            var3 = var1.findValueSerializer(var2, this._property);
         }

         if (this._unwrapper != null) {
            var3 = var3.unwrappingSerializer(this._unwrapper);
         }

         this._dynamicSerializers = this._dynamicSerializers.newWith(var2, var3);
      }

      return var3;
   }

   private final JsonSerializer _findSerializer(SerializerProvider var1, JavaType var2, BeanProperty var3) throws JsonMappingException {
      return var1.findValueSerializer(var2, var3);
   }

   static {
      MARKER_FOR_EMPTY = JsonInclude$Include.NON_EMPTY;
   }
}
