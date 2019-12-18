package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.SerializerCache;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
import com.fasterxml.jackson.databind.ser.impl.TypeWrappedSerializer;
import com.fasterxml.jackson.databind.ser.impl.UnknownSerializer;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public abstract class SerializerProvider extends DatabindContext {
   protected static final boolean CACHE_UNKNOWN_MAPPINGS = false;
   public static final JsonSerializer DEFAULT_NULL_KEY_SERIALIZER = new FailingSerializer("Null key for a Map not allowed in JSON (use a converting NullKeySerializer?)");
   protected static final JsonSerializer DEFAULT_UNKNOWN_SERIALIZER = new UnknownSerializer();
   protected final SerializationConfig _config;
   protected final Class _serializationView;
   protected final SerializerFactory _serializerFactory;
   protected final SerializerCache _serializerCache;
   protected transient ContextAttributes _attributes;
   protected JsonSerializer _unknownTypeSerializer;
   protected JsonSerializer _keySerializer;
   protected JsonSerializer _nullValueSerializer;
   protected JsonSerializer _nullKeySerializer;
   protected final ReadOnlyClassToSerializerMap _knownSerializers;
   protected DateFormat _dateFormat;
   protected final boolean _stdNullValueSerializer;

   public SerializerProvider() {
      super();
      this._unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;
      this._nullValueSerializer = NullSerializer.instance;
      this._nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;
      this._config = null;
      this._serializerFactory = null;
      this._serializerCache = new SerializerCache();
      this._knownSerializers = null;
      this._serializationView = null;
      this._attributes = null;
      this._stdNullValueSerializer = true;
   }

   protected SerializerProvider(SerializerProvider var1, SerializationConfig var2, SerializerFactory var3) {
      super();
      this._unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;
      this._nullValueSerializer = NullSerializer.instance;
      this._nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;
      this._serializerFactory = var3;
      this._config = var2;
      this._serializerCache = var1._serializerCache;
      this._unknownTypeSerializer = var1._unknownTypeSerializer;
      this._keySerializer = var1._keySerializer;
      this._nullValueSerializer = var1._nullValueSerializer;
      this._nullKeySerializer = var1._nullKeySerializer;
      this._stdNullValueSerializer = this._nullValueSerializer == DEFAULT_NULL_KEY_SERIALIZER;
      this._serializationView = var2.getActiveView();
      this._attributes = var2.getAttributes();
      this._knownSerializers = this._serializerCache.getReadOnlyLookupMap();
   }

   protected SerializerProvider(SerializerProvider var1) {
      super();
      this._unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;
      this._nullValueSerializer = NullSerializer.instance;
      this._nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;
      this._config = null;
      this._serializationView = null;
      this._serializerFactory = null;
      this._knownSerializers = null;
      this._serializerCache = new SerializerCache();
      this._unknownTypeSerializer = var1._unknownTypeSerializer;
      this._keySerializer = var1._keySerializer;
      this._nullValueSerializer = var1._nullValueSerializer;
      this._nullKeySerializer = var1._nullKeySerializer;
      this._stdNullValueSerializer = var1._stdNullValueSerializer;
   }

   public void setDefaultKeySerializer(JsonSerializer var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null JsonSerializer");
      } else {
         this._keySerializer = var1;
      }
   }

   public void setNullValueSerializer(JsonSerializer var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null JsonSerializer");
      } else {
         this._nullValueSerializer = var1;
      }
   }

   public void setNullKeySerializer(JsonSerializer var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null JsonSerializer");
      } else {
         this._nullKeySerializer = var1;
      }
   }

   public final SerializationConfig getConfig() {
      return this._config;
   }

   public final AnnotationIntrospector getAnnotationIntrospector() {
      return this._config.getAnnotationIntrospector();
   }

   public final TypeFactory getTypeFactory() {
      return this._config.getTypeFactory();
   }

   public final Class getActiveView() {
      return this._serializationView;
   }

   /** @deprecated */
   @Deprecated
   public final Class getSerializationView() {
      return this._serializationView;
   }

   public final boolean canOverrideAccessModifiers() {
      return this._config.canOverrideAccessModifiers();
   }

   public final boolean isEnabled(MapperFeature var1) {
      return this._config.isEnabled(var1);
   }

   public final JsonFormat$Value getDefaultPropertyFormat(Class var1) {
      return this._config.getDefaultPropertyFormat(var1);
   }

   public final JsonInclude$Value getDefaultPropertyInclusion(Class var1) {
      return this._config.getDefaultPropertyInclusion();
   }

   public Locale getLocale() {
      return this._config.getLocale();
   }

   public TimeZone getTimeZone() {
      return this._config.getTimeZone();
   }

   public Object getAttribute(Object var1) {
      return this._attributes.getAttribute(var1);
   }

   public SerializerProvider setAttribute(Object var1, Object var2) {
      this._attributes = this._attributes.withPerCallAttribute(var1, var2);
      return this;
   }

   public final boolean isEnabled(SerializationFeature var1) {
      return this._config.isEnabled(var1);
   }

   public final boolean hasSerializationFeatures(int var1) {
      return this._config.hasSerializationFeatures(var1);
   }

   public final FilterProvider getFilterProvider() {
      return this._config.getFilterProvider();
   }

   public JsonGenerator getGenerator() {
      return null;
   }

   public abstract WritableObjectId findObjectId(Object var1, ObjectIdGenerator var2);

   public JsonSerializer findValueSerializer(Class var1, BeanProperty var2) throws JsonMappingException {
      JsonSerializer var3 = this._knownSerializers.untypedValueSerializer(var1);
      if (var3 == null) {
         var3 = this._serializerCache.untypedValueSerializer(var1);
         if (var3 == null) {
            var3 = this._serializerCache.untypedValueSerializer(this._config.constructType(var1));
            if (var3 == null) {
               var3 = this._createAndCacheUntypedSerializer(var1);
               if (var3 == null) {
                  var3 = this.getUnknownTypeSerializer(var1);
                  return var3;
               }
            }
         }
      }

      return this.handleSecondaryContextualization(var3, var2);
   }

   public JsonSerializer findValueSerializer(JavaType var1, BeanProperty var2) throws JsonMappingException {
      if (var1 == null) {
         this.reportMappingProblem("Null passed for `valueType` of `findValueSerializer()`");
      }

      JsonSerializer var3 = this._knownSerializers.untypedValueSerializer(var1);
      if (var3 == null) {
         var3 = this._serializerCache.untypedValueSerializer(var1);
         if (var3 == null) {
            var3 = this._createAndCacheUntypedSerializer(var1);
            if (var3 == null) {
               var3 = this.getUnknownTypeSerializer(var1.getRawClass());
               return var3;
            }
         }
      }

      return this.handleSecondaryContextualization(var3, var2);
   }

   public JsonSerializer findValueSerializer(Class var1) throws JsonMappingException {
      JsonSerializer var2 = this._knownSerializers.untypedValueSerializer(var1);
      if (var2 == null) {
         var2 = this._serializerCache.untypedValueSerializer(var1);
         if (var2 == null) {
            var2 = this._serializerCache.untypedValueSerializer(this._config.constructType(var1));
            if (var2 == null) {
               var2 = this._createAndCacheUntypedSerializer(var1);
               if (var2 == null) {
                  var2 = this.getUnknownTypeSerializer(var1);
               }
            }
         }
      }

      return var2;
   }

   public JsonSerializer findValueSerializer(JavaType var1) throws JsonMappingException {
      JsonSerializer var2 = this._knownSerializers.untypedValueSerializer(var1);
      if (var2 == null) {
         var2 = this._serializerCache.untypedValueSerializer(var1);
         if (var2 == null) {
            var2 = this._createAndCacheUntypedSerializer(var1);
            if (var2 == null) {
               var2 = this.getUnknownTypeSerializer(var1.getRawClass());
            }
         }
      }

      return var2;
   }

   public JsonSerializer findPrimaryPropertySerializer(JavaType var1, BeanProperty var2) throws JsonMappingException {
      JsonSerializer var3 = this._knownSerializers.untypedValueSerializer(var1);
      if (var3 == null) {
         var3 = this._serializerCache.untypedValueSerializer(var1);
         if (var3 == null) {
            var3 = this._createAndCacheUntypedSerializer(var1);
            if (var3 == null) {
               var3 = this.getUnknownTypeSerializer(var1.getRawClass());
               return var3;
            }
         }
      }

      return this.handlePrimaryContextualization(var3, var2);
   }

   public JsonSerializer findPrimaryPropertySerializer(Class var1, BeanProperty var2) throws JsonMappingException {
      JsonSerializer var3 = this._knownSerializers.untypedValueSerializer(var1);
      if (var3 == null) {
         var3 = this._serializerCache.untypedValueSerializer(var1);
         if (var3 == null) {
            var3 = this._serializerCache.untypedValueSerializer(this._config.constructType(var1));
            if (var3 == null) {
               var3 = this._createAndCacheUntypedSerializer(var1);
               if (var3 == null) {
                  var3 = this.getUnknownTypeSerializer(var1);
                  return var3;
               }
            }
         }
      }

      return this.handlePrimaryContextualization(var3, var2);
   }

   public JsonSerializer findTypedValueSerializer(Class var1, boolean var2, BeanProperty var3) throws JsonMappingException {
      JsonSerializer var4 = this._knownSerializers.typedValueSerializer(var1);
      if (var4 != null) {
         return var4;
      } else {
         var4 = this._serializerCache.typedValueSerializer(var1);
         if (var4 != null) {
            return var4;
         } else {
            Object var6 = this.findValueSerializer(var1, var3);
            TypeSerializer var5 = this._serializerFactory.createTypeSerializer(this._config, this._config.constructType(var1));
            if (var5 != null) {
               var5 = var5.forProperty(var3);
               var6 = new TypeWrappedSerializer(var5, (JsonSerializer)var6);
            }

            if (var2) {
               this._serializerCache.addTypedSerializer((Class)var1, (JsonSerializer)var6);
            }

            return (JsonSerializer)var6;
         }
      }
   }

   public JsonSerializer findTypedValueSerializer(JavaType var1, boolean var2, BeanProperty var3) throws JsonMappingException {
      JsonSerializer var4 = this._knownSerializers.typedValueSerializer(var1);
      if (var4 != null) {
         return var4;
      } else {
         var4 = this._serializerCache.typedValueSerializer(var1);
         if (var4 != null) {
            return var4;
         } else {
            Object var6 = this.findValueSerializer(var1, var3);
            TypeSerializer var5 = this._serializerFactory.createTypeSerializer(this._config, var1);
            if (var5 != null) {
               var5 = var5.forProperty(var3);
               var6 = new TypeWrappedSerializer(var5, (JsonSerializer)var6);
            }

            if (var2) {
               this._serializerCache.addTypedSerializer((JavaType)var1, (JsonSerializer)var6);
            }

            return (JsonSerializer)var6;
         }
      }
   }

   public TypeSerializer findTypeSerializer(JavaType var1) throws JsonMappingException {
      return this._serializerFactory.createTypeSerializer(this._config, var1);
   }

   public JsonSerializer findKeySerializer(JavaType var1, BeanProperty var2) throws JsonMappingException {
      JsonSerializer var3 = this._serializerFactory.createKeySerializer(this._config, var1, this._keySerializer);
      return this._handleContextualResolvable(var3, var2);
   }

   public JsonSerializer findKeySerializer(Class var1, BeanProperty var2) throws JsonMappingException {
      return this.findKeySerializer(this._config.constructType(var1), var2);
   }

   public JsonSerializer getDefaultNullKeySerializer() {
      return this._nullKeySerializer;
   }

   public JsonSerializer getDefaultNullValueSerializer() {
      return this._nullValueSerializer;
   }

   public JsonSerializer findNullKeySerializer(JavaType var1, BeanProperty var2) throws JsonMappingException {
      return this._nullKeySerializer;
   }

   public JsonSerializer findNullValueSerializer(BeanProperty var1) throws JsonMappingException {
      return this._nullValueSerializer;
   }

   public JsonSerializer getUnknownTypeSerializer(Class var1) {
      return (JsonSerializer)(var1 == Object.class ? this._unknownTypeSerializer : new UnknownSerializer(var1));
   }

   public boolean isUnknownTypeSerializer(JsonSerializer var1) {
      if (var1 != this._unknownTypeSerializer && var1 != null) {
         return this.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS) && var1.getClass() == UnknownSerializer.class;
      } else {
         return true;
      }
   }

   public abstract JsonSerializer serializerInstance(Annotated var1, Object var2) throws JsonMappingException;

   public abstract Object includeFilterInstance(BeanPropertyDefinition var1, Class var2) throws JsonMappingException;

   public abstract boolean includeFilterSuppressNulls(Object var1) throws JsonMappingException;

   public JsonSerializer handlePrimaryContextualization(JsonSerializer var1, BeanProperty var2) throws JsonMappingException {
      if (var1 != null && var1 instanceof ContextualSerializer) {
         var1 = ((ContextualSerializer)var1).createContextual(this, var2);
      }

      return var1;
   }

   public JsonSerializer handleSecondaryContextualization(JsonSerializer var1, BeanProperty var2) throws JsonMappingException {
      if (var1 != null && var1 instanceof ContextualSerializer) {
         var1 = ((ContextualSerializer)var1).createContextual(this, var2);
      }

      return var1;
   }

   public final void defaultSerializeValue(Object var1, JsonGenerator var2) throws IOException {
      if (var1 == null) {
         if (this._stdNullValueSerializer) {
            var2.writeNull();
         } else {
            this._nullValueSerializer.serialize((Object)null, var2, this);
         }
      } else {
         Class var3 = var1.getClass();
         this.findTypedValueSerializer((Class)var3, true, (BeanProperty)null).serialize(var1, var2, this);
      }

   }

   public final void defaultSerializeField(String var1, Object var2, JsonGenerator var3) throws IOException {
      var3.writeFieldName(var1);
      if (var2 == null) {
         if (this._stdNullValueSerializer) {
            var3.writeNull();
         } else {
            this._nullValueSerializer.serialize((Object)null, var3, this);
         }
      } else {
         Class var4 = var2.getClass();
         this.findTypedValueSerializer((Class)var4, true, (BeanProperty)null).serialize(var2, var3, this);
      }

   }

   public final void defaultSerializeDateValue(long var1, JsonGenerator var3) throws IOException {
      if (this.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
         var3.writeNumber(var1);
      } else {
         var3.writeString(this._dateFormat().format(new Date(var1)));
      }

   }

   public final void defaultSerializeDateValue(Date var1, JsonGenerator var2) throws IOException {
      if (this.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
         var2.writeNumber(var1.getTime());
      } else {
         var2.writeString(this._dateFormat().format(var1));
      }

   }

   public void defaultSerializeDateKey(long var1, JsonGenerator var3) throws IOException {
      if (this.isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
         var3.writeFieldName(String.valueOf(var1));
      } else {
         var3.writeFieldName(this._dateFormat().format(new Date(var1)));
      }

   }

   public void defaultSerializeDateKey(Date var1, JsonGenerator var2) throws IOException {
      if (this.isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
         var2.writeFieldName(String.valueOf(var1.getTime()));
      } else {
         var2.writeFieldName(this._dateFormat().format(var1));
      }

   }

   public final void defaultSerializeNull(JsonGenerator var1) throws IOException {
      if (this._stdNullValueSerializer) {
         var1.writeNull();
      } else {
         this._nullValueSerializer.serialize((Object)null, var1, this);
      }

   }

   public void reportMappingProblem(String var1, Object... var2) throws JsonMappingException {
      throw this.mappingException(var1, var2);
   }

   public Object reportBadTypeDefinition(BeanDescription var1, String var2, Object... var3) throws JsonMappingException {
      String var4 = "N/A";
      if (var1 != null) {
         var4 = ClassUtil.nameOf(var1.getBeanClass());
      }

      var2 = String.format("Invalid type definition for type %s: %s", var4, this._format(var2, var3));
      throw InvalidDefinitionException.from((JsonGenerator)this.getGenerator(), var2, var1, (BeanPropertyDefinition)null);
   }

   public Object reportBadPropertyDefinition(BeanDescription var1, BeanPropertyDefinition var2, String var3, Object... var4) throws JsonMappingException {
      var3 = this._format(var3, var4);
      String var5 = "N/A";
      if (var2 != null) {
         var5 = this._quotedString(var2.getName());
      }

      String var6 = "N/A";
      if (var1 != null) {
         var6 = ClassUtil.nameOf(var1.getBeanClass());
      }

      var3 = String.format("Invalid definition for property %s (of type %s): %s", var5, var6, var3);
      throw InvalidDefinitionException.from(this.getGenerator(), var3, var1, var2);
   }

   public Object reportBadDefinition(JavaType var1, String var2) throws JsonMappingException {
      throw InvalidDefinitionException.from(this.getGenerator(), var2, var1);
   }

   public Object reportBadDefinition(JavaType var1, String var2, Throwable var3) throws JsonMappingException {
      InvalidDefinitionException var4 = InvalidDefinitionException.from(this.getGenerator(), var2, var1);
      var4.initCause(var3);
      throw var4;
   }

   public Object reportBadDefinition(Class var1, String var2, Throwable var3) throws JsonMappingException {
      InvalidDefinitionException var4 = InvalidDefinitionException.from(this.getGenerator(), var2, this.constructType(var1));
      var4.initCause(var3);
      throw var4;
   }

   public void reportMappingProblem(Throwable var1, String var2, Object... var3) throws JsonMappingException {
      var2 = this._format(var2, var3);
      throw JsonMappingException.from(this.getGenerator(), var2, var1);
   }

   public JsonMappingException invalidTypeIdException(JavaType var1, String var2, String var3) {
      String var4 = String.format("Could not resolve type id '%s' as a subtype of %s", var2, var1);
      return InvalidTypeIdException.from((JsonParser)null, this._colonConcat(var4, var3), var1, var2);
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException mappingException(String var1, Object... var2) {
      return JsonMappingException.from(this.getGenerator(), this._format(var1, var2));
   }

   /** @deprecated */
   @Deprecated
   protected JsonMappingException mappingException(Throwable var1, String var2, Object... var3) {
      return JsonMappingException.from(this.getGenerator(), this._format(var2, var3), var1);
   }

   protected void _reportIncompatibleRootType(Object var1, JavaType var2) throws IOException {
      if (var2.isPrimitive()) {
         Class var3 = ClassUtil.wrapperType(var2.getRawClass());
         if (var3.isAssignableFrom(var1.getClass())) {
            return;
         }
      }

      this.reportBadDefinition(var2, String.format("Incompatible types: declared root type (%s) vs %s", var2, ClassUtil.classNameOf(var1)));
   }

   protected JsonSerializer _findExplicitUntypedSerializer(Class var1) throws JsonMappingException {
      JsonSerializer var2 = this._knownSerializers.untypedValueSerializer(var1);
      if (var2 == null) {
         var2 = this._serializerCache.untypedValueSerializer(var1);
         if (var2 == null) {
            var2 = this._createAndCacheUntypedSerializer(var1);
         }
      }

      return this.isUnknownTypeSerializer(var2) ? null : var2;
   }

   protected JsonSerializer _createAndCacheUntypedSerializer(Class var1) throws JsonMappingException {
      JavaType var2 = this._config.constructType(var1);

      JsonSerializer var3;
      try {
         var3 = this._createUntypedSerializer(var2);
      } catch (IllegalArgumentException var5) {
         var3 = null;
         this.reportMappingProblem(var5, var5.getMessage());
      }

      if (var3 != null) {
         this._serializerCache.addAndResolveNonTypedSerializer(var1, var2, var3, this);
      }

      return var3;
   }

   protected JsonSerializer _createAndCacheUntypedSerializer(JavaType var1) throws JsonMappingException {
      JsonSerializer var2;
      try {
         var2 = this._createUntypedSerializer(var1);
      } catch (IllegalArgumentException var4) {
         var2 = null;
         this.reportMappingProblem(var4, var4.getMessage());
      }

      if (var2 != null) {
         this._serializerCache.addAndResolveNonTypedSerializer(var1, var2, this);
      }

      return var2;
   }

   protected JsonSerializer _createUntypedSerializer(JavaType var1) throws JsonMappingException {
      JsonSerializer var10000;
      synchronized(this._serializerCache) {
         var10000 = this._serializerFactory.createSerializer(this, var1);
      }

      return var10000;
   }

   protected JsonSerializer _handleContextualResolvable(JsonSerializer var1, BeanProperty var2) throws JsonMappingException {
      if (var1 instanceof ResolvableSerializer) {
         ((ResolvableSerializer)var1).resolve(this);
      }

      return this.handleSecondaryContextualization(var1, var2);
   }

   protected JsonSerializer _handleResolvable(JsonSerializer var1) throws JsonMappingException {
      if (var1 instanceof ResolvableSerializer) {
         ((ResolvableSerializer)var1).resolve(this);
      }

      return var1;
   }

   protected final DateFormat _dateFormat() {
      if (this._dateFormat != null) {
         return this._dateFormat;
      } else {
         DateFormat var1 = this._config.getDateFormat();
         this._dateFormat = var1 = (DateFormat)var1.clone();
         return var1;
      }
   }

   public DatabindContext setAttribute(Object var1, Object var2) {
      return this.setAttribute(var1, var2);
   }

   public MapperConfig getConfig() {
      return this.getConfig();
   }
}
