package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators$PropertyGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class AbstractDeserializer extends JsonDeserializer implements ContextualDeserializer, Serializable {
   private static final long serialVersionUID = 1L;
   protected final JavaType _baseType;
   protected final ObjectIdReader _objectIdReader;
   protected final Map _backRefProperties;
   protected transient Map _properties;
   protected final boolean _acceptString;
   protected final boolean _acceptBoolean;
   protected final boolean _acceptInt;
   protected final boolean _acceptDouble;

   public AbstractDeserializer(BeanDeserializerBuilder var1, BeanDescription var2, Map var3, Map var4) {
      super();
      this._baseType = var2.getType();
      this._objectIdReader = var1.getObjectIdReader();
      this._backRefProperties = var3;
      this._properties = var4;
      Class var5 = this._baseType.getRawClass();
      this._acceptString = var5.isAssignableFrom(String.class);
      this._acceptBoolean = var5 == Boolean.TYPE || var5.isAssignableFrom(Boolean.class);
      this._acceptInt = var5 == Integer.TYPE || var5.isAssignableFrom(Integer.class);
      this._acceptDouble = var5 == Double.TYPE || var5.isAssignableFrom(Double.class);
   }

   /** @deprecated */
   @Deprecated
   public AbstractDeserializer(BeanDeserializerBuilder var1, BeanDescription var2, Map var3) {
      this(var1, var2, var3, (Map)null);
   }

   protected AbstractDeserializer(BeanDescription var1) {
      super();
      this._baseType = var1.getType();
      this._objectIdReader = null;
      this._backRefProperties = null;
      Class var2 = this._baseType.getRawClass();
      this._acceptString = var2.isAssignableFrom(String.class);
      this._acceptBoolean = var2 == Boolean.TYPE || var2.isAssignableFrom(Boolean.class);
      this._acceptInt = var2 == Integer.TYPE || var2.isAssignableFrom(Integer.class);
      this._acceptDouble = var2 == Double.TYPE || var2.isAssignableFrom(Double.class);
   }

   protected AbstractDeserializer(AbstractDeserializer var1, ObjectIdReader var2, Map var3) {
      super();
      this._baseType = var1._baseType;
      this._backRefProperties = var1._backRefProperties;
      this._acceptString = var1._acceptString;
      this._acceptBoolean = var1._acceptBoolean;
      this._acceptInt = var1._acceptInt;
      this._acceptDouble = var1._acceptDouble;
      this._objectIdReader = var2;
      this._properties = var3;
   }

   public static AbstractDeserializer constructForNonPOJO(BeanDescription var0) {
      return new AbstractDeserializer(var0);
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
      if (var2 != null && var3 != null) {
         AnnotatedMember var4 = var2.getMember();
         if (var4 != null) {
            ObjectIdInfo var5 = var3.findObjectIdInfo(var4);
            if (var5 != null) {
               SettableBeanProperty var8 = null;
               ObjectIdResolver var9 = var1.objectIdResolverInstance(var4, var5);
               var5 = var3.findObjectReferenceInfo(var4, var5);
               Class var10 = var5.getGeneratorType();
               JavaType var6;
               Object var7;
               if (var10 == ObjectIdGenerators$PropertyGenerator.class) {
                  PropertyName var11 = var5.getPropertyName();
                  var8 = this._properties == null ? null : (SettableBeanProperty)this._properties.get(var11.getSimpleName());
                  if (var8 == null) {
                     var1.reportBadDefinition(this._baseType, String.format("Invalid Object Id definition for %s: cannot find property with name '%s'", this.handledType().getName(), var11));
                  }

                  var6 = var8.getType();
                  var7 = new PropertyBasedObjectIdGenerator(var5.getScope());
               } else {
                  var9 = var1.objectIdResolverInstance(var4, var5);
                  JavaType var13 = var1.constructType(var10);
                  var6 = var1.getTypeFactory().findTypeParameters(var13, ObjectIdGenerator.class)[0];
                  var7 = var1.objectIdGeneratorInstance(var4, var5);
               }

               JsonDeserializer var14 = var1.findRootValueDeserializer(var6);
               ObjectIdReader var12 = ObjectIdReader.construct(var6, var5.getPropertyName(), (ObjectIdGenerator)var7, var14, var8, var9);
               return new AbstractDeserializer(this, var12, (Map)null);
            }
         }
      }

      return this._properties == null ? this : new AbstractDeserializer(this, this._objectIdReader, (Map)null);
   }

   public Class handledType() {
      return this._baseType.getRawClass();
   }

   public boolean isCachable() {
      return true;
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return null;
   }

   public ObjectIdReader getObjectIdReader() {
      return this._objectIdReader;
   }

   public SettableBeanProperty findBackReference(String var1) {
      return this._backRefProperties == null ? null : (SettableBeanProperty)this._backRefProperties.get(var1);
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      if (this._objectIdReader != null) {
         JsonToken var4 = var1.getCurrentToken();
         if (var4 != null) {
            if (var4.isScalarValue()) {
               return this._deserializeFromObjectId(var1, var2);
            }

            if (var4 == JsonToken.START_OBJECT) {
               var4 = var1.nextToken();
            }

            if (var4 == JsonToken.FIELD_NAME && this._objectIdReader.maySerializeAsObject() && this._objectIdReader.isValidReferencePropertyName(var1.getCurrentName(), var1)) {
               return this._deserializeFromObjectId(var1, var2);
            }
         }
      }

      Object var5 = this._deserializeIfNatural(var1, var2);
      return var5 != null ? var5 : var3.deserializeTypedFromObject(var1, var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      ValueInstantiator$Base var3 = new ValueInstantiator$Base(this._baseType);
      return var2.handleMissingInstantiator(this._baseType.getRawClass(), var3, var1, "abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information");
   }

   protected Object _deserializeIfNatural(JsonParser var1, DeserializationContext var2) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 6:
         if (this._acceptString) {
            return var1.getText();
         }
         break;
      case 7:
         if (this._acceptInt) {
            return var1.getIntValue();
         }
         break;
      case 8:
         if (this._acceptDouble) {
            return var1.getDoubleValue();
         }
         break;
      case 9:
         if (this._acceptBoolean) {
            return Boolean.TRUE;
         }
         break;
      case 10:
         if (this._acceptBoolean) {
            return Boolean.FALSE;
         }
      }

      return null;
   }

   protected Object _deserializeFromObjectId(JsonParser var1, DeserializationContext var2) throws IOException {
      Object var3 = this._objectIdReader.readObjectReference(var1, var2);
      ReadableObjectId var4 = var2.findObjectId(var3, this._objectIdReader.generator, this._objectIdReader.resolver);
      Object var5 = var4.resolve();
      if (var5 == null) {
         throw new UnresolvedForwardReference(var1, "Could not resolve Object Id [" + var3 + "] -- unresolved forward-reference?", var1.getCurrentLocation(), var4);
      } else {
         return var5;
      }
   }
}
