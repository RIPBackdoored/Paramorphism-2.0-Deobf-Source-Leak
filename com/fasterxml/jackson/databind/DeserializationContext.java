package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.DeserializerCache;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.impl.TypeWrappedDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.LinkedNode;
import com.fasterxml.jackson.databind.util.Named;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

public abstract class DeserializationContext extends DatabindContext implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final DeserializerCache _cache;
   protected final DeserializerFactory _factory;
   protected final DeserializationConfig _config;
   protected final int _featureFlags;
   protected final Class _view;
   protected transient JsonParser _parser;
   protected final InjectableValues _injectableValues;
   protected transient ArrayBuilders _arrayBuilders;
   protected transient ObjectBuffer _objectBuffer;
   protected transient DateFormat _dateFormat;
   protected transient ContextAttributes _attributes;
   protected LinkedNode _currentType;

   protected DeserializationContext(DeserializerFactory var1) {
      this((DeserializerFactory)var1, (DeserializerCache)null);
   }

   protected DeserializationContext(DeserializerFactory var1, DeserializerCache var2) {
      super();
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null DeserializerFactory");
      } else {
         this._factory = var1;
         if (var2 == null) {
            var2 = new DeserializerCache();
         }

         this._cache = var2;
         this._featureFlags = 0;
         this._config = null;
         this._injectableValues = null;
         this._view = null;
         this._attributes = null;
      }
   }

   protected DeserializationContext(DeserializationContext var1, DeserializerFactory var2) {
      super();
      this._cache = var1._cache;
      this._factory = var2;
      this._config = var1._config;
      this._featureFlags = var1._featureFlags;
      this._view = var1._view;
      this._parser = var1._parser;
      this._injectableValues = var1._injectableValues;
      this._attributes = var1._attributes;
   }

   protected DeserializationContext(DeserializationContext var1, DeserializationConfig var2, JsonParser var3, InjectableValues var4) {
      super();
      this._cache = var1._cache;
      this._factory = var1._factory;
      this._config = var2;
      this._featureFlags = var2.getDeserializationFeatures();
      this._view = var2.getActiveView();
      this._parser = var3;
      this._injectableValues = var4;
      this._attributes = var2.getAttributes();
   }

   protected DeserializationContext(DeserializationContext var1) {
      super();
      this._cache = new DeserializerCache();
      this._factory = var1._factory;
      this._config = var1._config;
      this._featureFlags = var1._featureFlags;
      this._view = var1._view;
      this._injectableValues = null;
   }

   public DeserializationConfig getConfig() {
      return this._config;
   }

   public final Class getActiveView() {
      return this._view;
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

   public final AnnotationIntrospector getAnnotationIntrospector() {
      return this._config.getAnnotationIntrospector();
   }

   public final TypeFactory getTypeFactory() {
      return this._config.getTypeFactory();
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

   public DeserializationContext setAttribute(Object var1, Object var2) {
      this._attributes = this._attributes.withPerCallAttribute(var1, var2);
      return this;
   }

   public JavaType getContextualType() {
      return this._currentType == null ? null : (JavaType)this._currentType.value();
   }

   public DeserializerFactory getFactory() {
      return this._factory;
   }

   public final boolean isEnabled(DeserializationFeature var1) {
      return (this._featureFlags & var1.getMask()) != 0;
   }

   public final int getDeserializationFeatures() {
      return this._featureFlags;
   }

   public final boolean hasDeserializationFeatures(int var1) {
      return (this._featureFlags & var1) == var1;
   }

   public final boolean hasSomeOfFeatures(int var1) {
      return (this._featureFlags & var1) != 0;
   }

   public final JsonParser getParser() {
      return this._parser;
   }

   public final Object findInjectableValue(Object var1, BeanProperty var2, Object var3) throws JsonMappingException {
      if (this._injectableValues == null) {
         this.reportBadDefinition(ClassUtil.classOf(var1), String.format("No 'injectableValues' configured, cannot inject value with id [%s]", var1));
      }

      return this._injectableValues.findInjectableValue(var1, this, var2, var3);
   }

   public final Base64Variant getBase64Variant() {
      return this._config.getBase64Variant();
   }

   public final JsonNodeFactory getNodeFactory() {
      return this._config.getNodeFactory();
   }

   public boolean hasValueDeserializerFor(JavaType var1, AtomicReference var2) {
      try {
         boolean var10000 = this._cache.hasValueDeserializerFor(this, this._factory, var1);
         return var10000;
      } catch (JsonMappingException var4) {
         if (var2 != null) {
            var2.set(var4);
         }
      } catch (RuntimeException var5) {
         if (var2 == null) {
            throw var5;
         }

         var2.set(var5);
      }

      return false;
   }

   public final JsonDeserializer findContextualValueDeserializer(JavaType var1, BeanProperty var2) throws JsonMappingException {
      JsonDeserializer var3 = this._cache.findValueDeserializer(this, this._factory, var1);
      if (var3 != null) {
         var3 = this.handleSecondaryContextualization(var3, var2, var1);
      }

      return var3;
   }

   public final JsonDeserializer findNonContextualValueDeserializer(JavaType var1) throws JsonMappingException {
      return this._cache.findValueDeserializer(this, this._factory, var1);
   }

   public final JsonDeserializer findRootValueDeserializer(JavaType var1) throws JsonMappingException {
      JsonDeserializer var2 = this._cache.findValueDeserializer(this, this._factory, var1);
      if (var2 == null) {
         return null;
      } else {
         var2 = this.handleSecondaryContextualization(var2, (BeanProperty)null, var1);
         TypeDeserializer var3 = this._factory.findTypeDeserializer(this._config, var1);
         if (var3 != null) {
            var3 = var3.forProperty((BeanProperty)null);
            return new TypeWrappedDeserializer(var3, var2);
         } else {
            return var2;
         }
      }
   }

   public final KeyDeserializer findKeyDeserializer(JavaType var1, BeanProperty var2) throws JsonMappingException {
      KeyDeserializer var3 = this._cache.findKeyDeserializer(this, this._factory, var1);
      if (var3 instanceof ContextualKeyDeserializer) {
         var3 = ((ContextualKeyDeserializer)var3).createContextual(this, var2);
      }

      return var3;
   }

   public abstract ReadableObjectId findObjectId(Object var1, ObjectIdGenerator var2, ObjectIdResolver var3);

   public abstract void checkUnresolvedObjectId() throws UnresolvedForwardReference;

   public final JavaType constructType(Class var1) {
      return var1 == null ? null : this._config.constructType(var1);
   }

   public Class findClass(String var1) throws ClassNotFoundException {
      return this.getTypeFactory().findClass(var1);
   }

   public final ObjectBuffer leaseObjectBuffer() {
      ObjectBuffer var1 = this._objectBuffer;
      if (var1 == null) {
         var1 = new ObjectBuffer();
      } else {
         this._objectBuffer = null;
      }

      return var1;
   }

   public final void returnObjectBuffer(ObjectBuffer var1) {
      if (this._objectBuffer == null || var1.initialCapacity() >= this._objectBuffer.initialCapacity()) {
         this._objectBuffer = var1;
      }

   }

   public final ArrayBuilders getArrayBuilders() {
      if (this._arrayBuilders == null) {
         this._arrayBuilders = new ArrayBuilders();
      }

      return this._arrayBuilders;
   }

   public abstract JsonDeserializer deserializerInstance(Annotated var1, Object var2) throws JsonMappingException;

   public abstract KeyDeserializer keyDeserializerInstance(Annotated var1, Object var2) throws JsonMappingException;

   public JsonDeserializer handlePrimaryContextualization(JsonDeserializer var1, BeanProperty var2, JavaType var3) throws JsonMappingException {
      if (var1 instanceof ContextualDeserializer) {
         this._currentType = new LinkedNode(var3, this._currentType);
         boolean var6 = false;

         try {
            var6 = true;
            var1 = ((ContextualDeserializer)var1).createContextual(this, var2);
            var6 = false;
         } finally {
            if (var6) {
               this._currentType = this._currentType.next();
            }
         }

         this._currentType = this._currentType.next();
      }

      return var1;
   }

   public JsonDeserializer handleSecondaryContextualization(JsonDeserializer var1, BeanProperty var2, JavaType var3) throws JsonMappingException {
      if (var1 instanceof ContextualDeserializer) {
         this._currentType = new LinkedNode(var3, this._currentType);
         boolean var6 = false;

         try {
            var6 = true;
            var1 = ((ContextualDeserializer)var1).createContextual(this, var2);
            var6 = false;
         } finally {
            if (var6) {
               this._currentType = this._currentType.next();
            }
         }

         this._currentType = this._currentType.next();
      }

      return var1;
   }

   public Date parseDate(String var1) throws IllegalArgumentException {
      Date var10000;
      try {
         DateFormat var2 = this.getDateFormat();
         var10000 = var2.parse(var1);
      } catch (ParseException var3) {
         throw new IllegalArgumentException(String.format("Failed to parse Date value '%s': %s", var1, var3.getMessage()));
      }

      return var10000;
   }

   public Calendar constructCalendar(Date var1) {
      Calendar var2 = Calendar.getInstance(this.getTimeZone());
      var2.setTime(var1);
      return var2;
   }

   public Object readValue(JsonParser var1, Class var2) throws IOException {
      return this.readValue(var1, this.getTypeFactory().constructType((Type)var2));
   }

   public Object readValue(JsonParser var1, JavaType var2) throws IOException {
      JsonDeserializer var3 = this.findRootValueDeserializer(var2);
      if (var3 == null) {
         this.reportBadDefinition(var2, "Could not find JsonDeserializer for type " + var2);
      }

      return var3.deserialize(var1, this);
   }

   public Object readPropertyValue(JsonParser var1, BeanProperty var2, Class var3) throws IOException {
      return this.readPropertyValue(var1, var2, this.getTypeFactory().constructType((Type)var3));
   }

   public Object readPropertyValue(JsonParser var1, BeanProperty var2, JavaType var3) throws IOException {
      JsonDeserializer var4 = this.findContextualValueDeserializer(var3, var2);
      return var4 == null ? this.reportBadDefinition(var3, String.format("Could not find JsonDeserializer for type %s (via property %s)", var3, ClassUtil.nameOf((Named)var2))) : var4.deserialize(var1, this);
   }

   public boolean handleUnknownProperty(JsonParser var1, JsonDeserializer var2, Object var3, String var4) throws IOException {
      for(LinkedNode var5 = this._config.getProblemHandlers(); var5 != null; var5 = var5.next()) {
         if (((DeserializationProblemHandler)var5.value()).handleUnknownProperty(this, var1, var2, var3, var4)) {
            return true;
         }
      }

      if (!this.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
         var1.skipChildren();
         return true;
      } else {
         Collection var6 = var2 == null ? null : var2.getKnownPropertyNames();
         throw UnrecognizedPropertyException.from(this._parser, var3, var4, var6);
      }
   }

   public Object handleWeirdKey(Class var1, String var2, String var3, Object... var4) throws IOException {
      var3 = this._format(var3, var4);

      for(LinkedNode var5 = this._config.getProblemHandlers(); var5 != null; var5 = var5.next()) {
         Object var6 = ((DeserializationProblemHandler)var5.value()).handleWeirdKey(this, var1, var2, var3);
         if (var6 != DeserializationProblemHandler.NOT_HANDLED) {
            if (var6 != null && !var1.isInstance(var6)) {
               throw this.weirdStringException(var2, var1, String.format("DeserializationProblemHandler.handleWeirdStringValue() for type %s returned value of type %s", var1, var6.getClass()));
            }

            return var6;
         }
      }

      throw this.weirdKeyException(var1, var2, var3);
   }

   public Object handleWeirdStringValue(Class var1, String var2, String var3, Object... var4) throws IOException {
      var3 = this._format(var3, var4);

      for(LinkedNode var5 = this._config.getProblemHandlers(); var5 != null; var5 = var5.next()) {
         Object var6 = ((DeserializationProblemHandler)var5.value()).handleWeirdStringValue(this, var1, var2, var3);
         if (var6 != DeserializationProblemHandler.NOT_HANDLED) {
            if (this._isCompatible(var1, var6)) {
               return var6;
            }

            throw this.weirdStringException(var2, var1, String.format("DeserializationProblemHandler.handleWeirdStringValue() for type %s returned value of type %s", var1, var6.getClass()));
         }
      }

      throw this.weirdStringException(var2, var1, var3);
   }

   public Object handleWeirdNumberValue(Class var1, Number var2, String var3, Object... var4) throws IOException {
      var3 = this._format(var3, var4);

      for(LinkedNode var5 = this._config.getProblemHandlers(); var5 != null; var5 = var5.next()) {
         Object var6 = ((DeserializationProblemHandler)var5.value()).handleWeirdNumberValue(this, var1, var2, var3);
         if (var6 != DeserializationProblemHandler.NOT_HANDLED) {
            if (this._isCompatible(var1, var6)) {
               return var6;
            }

            throw this.weirdNumberException(var2, var1, this._format("DeserializationProblemHandler.handleWeirdNumberValue() for type %s returned value of type %s", new Object[]{var1, var6.getClass()}));
         }
      }

      throw this.weirdNumberException(var2, var1, var3);
   }

   public Object handleWeirdNativeValue(JavaType var1, Object var2, JsonParser var3) throws IOException {
      LinkedNode var4 = this._config.getProblemHandlers();

      Class var5;
      for(var5 = var1.getRawClass(); var4 != null; var4 = var4.next()) {
         Object var6 = ((DeserializationProblemHandler)var4.value()).handleWeirdNativeValue(this, var1, var2, var3);
         if (var6 != DeserializationProblemHandler.NOT_HANDLED) {
            if (var6 != null && !var5.isInstance(var6)) {
               throw JsonMappingException.from(var3, this._format("DeserializationProblemHandler.handleWeirdNativeValue() for type %s returned value of type %s", new Object[]{var1, var6.getClass()}));
            }

            return var6;
         }
      }

      throw this.weirdNativeValueException(var2, var5);
   }

   public Object handleMissingInstantiator(Class var1, ValueInstantiator var2, JsonParser var3, String var4, Object... var5) throws IOException {
      if (var3 == null) {
         var3 = this.getParser();
      }

      var4 = this._format(var4, var5);

      for(LinkedNode var6 = this._config.getProblemHandlers(); var6 != null; var6 = var6.next()) {
         Object var7 = ((DeserializationProblemHandler)var6.value()).handleMissingInstantiator(this, var1, var2, var3, var4);
         if (var7 != DeserializationProblemHandler.NOT_HANDLED) {
            if (this._isCompatible(var1, var7)) {
               return var7;
            }

            this.reportBadDefinition(this.constructType(var1), String.format("DeserializationProblemHandler.handleMissingInstantiator() for type %s returned value of type %s", var1, ClassUtil.classNameOf(var7)));
         }
      }

      if (var2 != null && !var2.canInstantiate()) {
         var4 = String.format("Cannot construct instance of %s (no Creators, like default construct, exist): %s", ClassUtil.nameOf(var1), var4);
         return this.reportBadDefinition(this.constructType(var1), var4);
      } else {
         var4 = String.format("Cannot construct instance of %s (although at least one Creator exists): %s", ClassUtil.nameOf(var1), var4);
         return this.reportInputMismatch(var1, var4);
      }
   }

   public Object handleInstantiationProblem(Class var1, Object var2, Throwable var3) throws IOException {
      for(LinkedNode var4 = this._config.getProblemHandlers(); var4 != null; var4 = var4.next()) {
         Object var5 = ((DeserializationProblemHandler)var4.value()).handleInstantiationProblem(this, var1, var2, var3);
         if (var5 != DeserializationProblemHandler.NOT_HANDLED) {
            if (this._isCompatible(var1, var5)) {
               return var5;
            }

            this.reportBadDefinition(this.constructType(var1), String.format("DeserializationProblemHandler.handleInstantiationProblem() for type %s returned value of type %s", var1, ClassUtil.classNameOf(var5)));
         }
      }

      ClassUtil.throwIfIOE(var3);
      throw this.instantiationException(var1, var3);
   }

   public Object handleUnexpectedToken(Class var1, JsonParser var2) throws IOException {
      return this.handleUnexpectedToken(var1, var2.getCurrentToken(), var2, (String)null);
   }

   public Object handleUnexpectedToken(Class var1, JsonToken var2, JsonParser var3, String var4, Object... var5) throws IOException {
      var4 = this._format(var4, var5);

      for(LinkedNode var6 = this._config.getProblemHandlers(); var6 != null; var6 = var6.next()) {
         Object var7 = ((DeserializationProblemHandler)var6.value()).handleUnexpectedToken(this, var1, var2, var3, var4);
         if (var7 != DeserializationProblemHandler.NOT_HANDLED) {
            if (this._isCompatible(var1, var7)) {
               return var7;
            }

            this.reportBadDefinition(this.constructType(var1), String.format("DeserializationProblemHandler.handleUnexpectedToken() for type %s returned value of type %s", ClassUtil.nameOf(var1), ClassUtil.classNameOf(var7)));
         }
      }

      if (var4 == null) {
         if (var2 == null) {
            var4 = String.format("Unexpected end-of-input when binding data into %s", ClassUtil.nameOf(var1));
         } else {
            var4 = String.format("Cannot deserialize instance of %s out of %s token", ClassUtil.nameOf(var1), var2);
         }
      }

      this.reportInputMismatch(var1, var4);
      return null;
   }

   public JavaType handleUnknownTypeId(JavaType var1, String var2, TypeIdResolver var3, String var4) throws IOException {
      for(LinkedNode var5 = this._config.getProblemHandlers(); var5 != null; var5 = var5.next()) {
         JavaType var6 = ((DeserializationProblemHandler)var5.value()).handleUnknownTypeId(this, var1, var2, var3, var4);
         if (var6 != null) {
            if (var6.hasRawClass(Void.class)) {
               return null;
            }

            if (var6.isTypeOrSubTypeOf(var1.getRawClass())) {
               return var6;
            }

            throw this.invalidTypeIdException(var1, var2, "problem handler tried to resolve into non-subtype: " + var6);
         }
      }

      if (!this.isEnabled(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)) {
         return null;
      } else {
         throw this.invalidTypeIdException(var1, var2, var4);
      }
   }

   public JavaType handleMissingTypeId(JavaType var1, TypeIdResolver var2, String var3) throws IOException {
      for(LinkedNode var4 = this._config.getProblemHandlers(); var4 != null; var4 = var4.next()) {
         JavaType var5 = ((DeserializationProblemHandler)var4.value()).handleMissingTypeId(this, var1, var2, var3);
         if (var5 != null) {
            if (var5.hasRawClass(Void.class)) {
               return null;
            }

            if (var5.isTypeOrSubTypeOf(var1.getRawClass())) {
               return var5;
            }

            throw this.invalidTypeIdException(var1, (String)null, "problem handler tried to resolve into non-subtype: " + var5);
         }
      }

      throw this.missingTypeIdException(var1, var3);
   }

   protected boolean _isCompatible(Class var1, Object var2) {
      if (var2 != null && !var1.isInstance(var2)) {
         return var1.isPrimitive() && ClassUtil.wrapperType(var1).isInstance(var2);
      } else {
         return true;
      }
   }

   public void reportWrongTokenException(JsonDeserializer var1, JsonToken var2, String var3, Object... var4) throws JsonMappingException {
      var3 = this._format(var3, var4);
      throw this.wrongTokenException(this.getParser(), var1.handledType(), var2, var3);
   }

   public void reportWrongTokenException(JavaType var1, JsonToken var2, String var3, Object... var4) throws JsonMappingException {
      var3 = this._format(var3, var4);
      throw this.wrongTokenException(this.getParser(), var1, var2, var3);
   }

   public void reportWrongTokenException(Class var1, JsonToken var2, String var3, Object... var4) throws JsonMappingException {
      var3 = this._format(var3, var4);
      throw this.wrongTokenException(this.getParser(), var1, var2, var3);
   }

   public Object reportUnresolvedObjectId(ObjectIdReader var1, Object var2) throws JsonMappingException {
      String var3 = String.format("No Object Id found for an instance of %s, to assign to property '%s'", ClassUtil.classNameOf(var2), var1.propertyName);
      return this.reportInputMismatch((BeanProperty)var1.idProperty, var3);
   }

   public Object reportInputMismatch(BeanProperty var1, String var2, Object... var3) throws JsonMappingException {
      var2 = this._format(var2, var3);
      JavaType var4 = var1 == null ? null : var1.getType();
      throw MismatchedInputException.from(this.getParser(), var4, var2);
   }

   public Object reportInputMismatch(JsonDeserializer var1, String var2, Object... var3) throws JsonMappingException {
      var2 = this._format(var2, var3);
      throw MismatchedInputException.from(this.getParser(), var1.handledType(), var2);
   }

   public Object reportInputMismatch(Class var1, String var2, Object... var3) throws JsonMappingException {
      var2 = this._format(var2, var3);
      throw MismatchedInputException.from(this.getParser(), var1, var2);
   }

   public Object reportInputMismatch(JavaType var1, String var2, Object... var3) throws JsonMappingException {
      var2 = this._format(var2, var3);
      throw MismatchedInputException.from(this.getParser(), var1, var2);
   }

   public Object reportTrailingTokens(Class var1, JsonParser var2, JsonToken var3) throws JsonMappingException {
      throw MismatchedInputException.from(var2, var1, String.format("Trailing token (of type %s) found after value (bound as %s): not allowed as per `DeserializationFeature.FAIL_ON_TRAILING_TOKENS`", var3, ClassUtil.nameOf(var1)));
   }

   /** @deprecated */
   @Deprecated
   public void reportWrongTokenException(JsonParser var1, JsonToken var2, String var3, Object... var4) throws JsonMappingException {
      var3 = this._format(var3, var4);
      throw this.wrongTokenException(var1, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public void reportUnknownProperty(Object var1, String var2, JsonDeserializer var3) throws JsonMappingException {
      if (this.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
         Collection var4 = var3 == null ? null : var3.getKnownPropertyNames();
         throw UnrecognizedPropertyException.from(this._parser, var1, var2, var4);
      }
   }

   /** @deprecated */
   @Deprecated
   public void reportMissingContent(String var1, Object... var2) throws JsonMappingException {
      throw MismatchedInputException.from(this.getParser(), (JavaType)null, "No content to map due to end-of-input");
   }

   public Object reportBadTypeDefinition(BeanDescription var1, String var2, Object... var3) throws JsonMappingException {
      var2 = this._format(var2, var3);
      String var4 = ClassUtil.nameOf(var1.getBeanClass());
      var2 = String.format("Invalid type definition for type %s: %s", var4, var2);
      throw InvalidDefinitionException.from((JsonParser)this._parser, var2, var1, (BeanPropertyDefinition)null);
   }

   public Object reportBadPropertyDefinition(BeanDescription var1, BeanPropertyDefinition var2, String var3, Object... var4) throws JsonMappingException {
      var3 = this._format(var3, var4);
      String var5 = ClassUtil.nameOf((Named)var2);
      String var6 = ClassUtil.nameOf(var1.getBeanClass());
      var3 = String.format("Invalid definition for property %s (of type %s): %s", var5, var6, var3);
      throw InvalidDefinitionException.from(this._parser, var3, var1, var2);
   }

   public Object reportBadDefinition(JavaType var1, String var2) throws JsonMappingException {
      throw InvalidDefinitionException.from(this._parser, var2, var1);
   }

   public Object reportBadMerge(JsonDeserializer var1) throws JsonMappingException {
      if (this.isEnabled(MapperFeature.IGNORE_MERGE_FOR_UNMERGEABLE)) {
         return null;
      } else {
         JavaType var2 = this.constructType(var1.handledType());
         String var3 = String.format("Invalid configuration: values of type %s cannot be merged", var2);
         throw InvalidDefinitionException.from(this.getParser(), var3, var2);
      }
   }

   public JsonMappingException wrongTokenException(JsonParser var1, JavaType var2, JsonToken var3, String var4) {
      String var5 = String.format("Unexpected token (%s), expected %s", var1.getCurrentToken(), var3);
      var5 = this._colonConcat(var5, var4);
      return MismatchedInputException.from(var1, var2, var5);
   }

   public JsonMappingException wrongTokenException(JsonParser var1, Class var2, JsonToken var3, String var4) {
      String var5 = String.format("Unexpected token (%s), expected %s", var1.getCurrentToken(), var3);
      var5 = this._colonConcat(var5, var4);
      return MismatchedInputException.from(var1, var2, var5);
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException wrongTokenException(JsonParser var1, JsonToken var2, String var3) {
      return this.wrongTokenException(var1, (JavaType)null, var2, var3);
   }

   public JsonMappingException weirdKeyException(Class var1, String var2, String var3) {
      return InvalidFormatException.from(this._parser, String.format("Cannot deserialize Map key of type %s from String %s: %s", ClassUtil.nameOf(var1), this._quotedString(var2), var3), var2, var1);
   }

   public JsonMappingException weirdStringException(String var1, Class var2, String var3) {
      return InvalidFormatException.from(this._parser, String.format("Cannot deserialize value of type %s from String %s: %s", ClassUtil.nameOf(var2), this._quotedString(var1), var3), var1, var2);
   }

   public JsonMappingException weirdNumberException(Number var1, Class var2, String var3) {
      return InvalidFormatException.from(this._parser, String.format("Cannot deserialize value of type %s from number %s: %s", ClassUtil.nameOf(var2), String.valueOf(var1), var3), var1, var2);
   }

   public JsonMappingException weirdNativeValueException(Object var1, Class var2) {
      return InvalidFormatException.from(this._parser, String.format("Cannot deserialize value of type %s from native value (`JsonToken.VALUE_EMBEDDED_OBJECT`) of type %s: incompatible types", ClassUtil.nameOf(var2), ClassUtil.classNameOf(var1)), var1, var2);
   }

   public JsonMappingException instantiationException(Class var1, Throwable var2) {
      JavaType var3 = this.constructType(var1);
      String var4 = String.format("Cannot construct instance of %s, problem: %s", ClassUtil.nameOf(var1), var2.getMessage());
      InvalidDefinitionException var5 = InvalidDefinitionException.from(this._parser, var4, var3);
      var5.initCause(var2);
      return var5;
   }

   public JsonMappingException instantiationException(Class var1, String var2) {
      JavaType var3 = this.constructType(var1);
      String var4 = String.format("Cannot construct instance of %s: %s", ClassUtil.nameOf(var1), var2);
      return InvalidDefinitionException.from(this._parser, var4, var3);
   }

   public JsonMappingException invalidTypeIdException(JavaType var1, String var2, String var3) {
      String var4 = String.format("Could not resolve type id '%s' as a subtype of %s", var2, var1);
      return InvalidTypeIdException.from(this._parser, this._colonConcat(var4, var3), var1, var2);
   }

   public JsonMappingException missingTypeIdException(JavaType var1, String var2) {
      String var3 = String.format("Missing type id when trying to resolve subtype of %s", var1);
      return InvalidTypeIdException.from(this._parser, this._colonConcat(var3, var2), var1, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException unknownTypeException(JavaType var1, String var2, String var3) {
      String var4 = String.format("Could not resolve type id '%s' into a subtype of %s", var2, var1);
      var4 = this._colonConcat(var4, var3);
      return MismatchedInputException.from(this._parser, var1, var4);
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException endOfInputException(Class var1) {
      return MismatchedInputException.from(this._parser, var1, "Unexpected end-of-input when trying to deserialize a " + var1.getName());
   }

   /** @deprecated */
   @Deprecated
   public void reportMappingException(String var1, Object... var2) throws JsonMappingException {
      throw JsonMappingException.from(this.getParser(), this._format(var1, var2));
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException mappingException(String var1) {
      return JsonMappingException.from(this.getParser(), var1);
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException mappingException(String var1, Object... var2) {
      return JsonMappingException.from(this.getParser(), this._format(var1, var2));
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException mappingException(Class var1) {
      return this.mappingException(var1, this._parser.getCurrentToken());
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException mappingException(Class var1, JsonToken var2) {
      return JsonMappingException.from(this._parser, String.format("Cannot deserialize instance of %s out of %s token", ClassUtil.nameOf(var1), var2));
   }

   protected DateFormat getDateFormat() {
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
