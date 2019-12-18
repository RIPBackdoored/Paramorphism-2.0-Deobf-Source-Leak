package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonAutoDetect$Value;
import com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility;
import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.annotation.JsonSetter$Value;
import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.annotation.JsonTypeInfo$Id;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonFactory$Feature;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator$Feature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.ConfigOverrides;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MutableConfigOverride;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext$Impl;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector$MixInResolver;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker$Std;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.POJONode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider$Impl;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.AccessController;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class ObjectMapper extends ObjectCodec implements Versioned, Serializable {
   private static final long serialVersionUID = 2L;
   private static final JavaType JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);
   protected static final AnnotationIntrospector DEFAULT_ANNOTATION_INTROSPECTOR = new JacksonAnnotationIntrospector();
   protected static final BaseSettings DEFAULT_BASE;
   protected final JsonFactory _jsonFactory;
   protected TypeFactory _typeFactory;
   protected InjectableValues _injectableValues;
   protected SubtypeResolver _subtypeResolver;
   protected final ConfigOverrides _configOverrides;
   protected SimpleMixInResolver _mixIns;
   protected SerializationConfig _serializationConfig;
   protected DefaultSerializerProvider _serializerProvider;
   protected SerializerFactory _serializerFactory;
   protected DeserializationConfig _deserializationConfig;
   protected DefaultDeserializationContext _deserializationContext;
   protected Set _registeredModuleTypes;
   protected final ConcurrentHashMap _rootDeserializers;

   public ObjectMapper() {
      this((JsonFactory)null, (DefaultSerializerProvider)null, (DefaultDeserializationContext)null);
   }

   public ObjectMapper(JsonFactory var1) {
      this(var1, (DefaultSerializerProvider)null, (DefaultDeserializationContext)null);
   }

   protected ObjectMapper(ObjectMapper var1) {
      super();
      this._rootDeserializers = new ConcurrentHashMap(64, 0.6F, 2);
      this._jsonFactory = var1._jsonFactory.copy();
      this._jsonFactory.setCodec(this);
      this._subtypeResolver = var1._subtypeResolver;
      this._typeFactory = var1._typeFactory;
      this._injectableValues = var1._injectableValues;
      this._configOverrides = var1._configOverrides.copy();
      this._mixIns = var1._mixIns.copy();
      RootNameLookup var2 = new RootNameLookup();
      this._serializationConfig = new SerializationConfig(var1._serializationConfig, this._mixIns, var2, this._configOverrides);
      this._deserializationConfig = new DeserializationConfig(var1._deserializationConfig, this._mixIns, var2, this._configOverrides);
      this._serializerProvider = var1._serializerProvider.copy();
      this._deserializationContext = var1._deserializationContext.copy();
      this._serializerFactory = var1._serializerFactory;
      Set var3 = var1._registeredModuleTypes;
      if (var3 == null) {
         this._registeredModuleTypes = null;
      } else {
         this._registeredModuleTypes = new LinkedHashSet(var3);
      }

   }

   public ObjectMapper(JsonFactory var1, DefaultSerializerProvider var2, DefaultDeserializationContext var3) {
      super();
      this._rootDeserializers = new ConcurrentHashMap(64, 0.6F, 2);
      if (var1 == null) {
         this._jsonFactory = new MappingJsonFactory(this);
      } else {
         this._jsonFactory = var1;
         if (var1.getCodec() == null) {
            this._jsonFactory.setCodec(this);
         }
      }

      this._subtypeResolver = new StdSubtypeResolver();
      RootNameLookup var4 = new RootNameLookup();
      this._typeFactory = TypeFactory.defaultInstance();
      SimpleMixInResolver var5 = new SimpleMixInResolver((ClassIntrospector$MixInResolver)null);
      this._mixIns = var5;
      BaseSettings var6 = DEFAULT_BASE.withClassIntrospector(this.defaultClassIntrospector());
      this._configOverrides = new ConfigOverrides();
      this._serializationConfig = new SerializationConfig(var6, this._subtypeResolver, var5, var4, this._configOverrides);
      this._deserializationConfig = new DeserializationConfig(var6, this._subtypeResolver, var5, var4, this._configOverrides);
      boolean var7 = this._jsonFactory.requiresPropertyOrdering();
      if (var7 ^ this._serializationConfig.isEnabled(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)) {
         this.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, var7);
      }

      this._serializerProvider = (DefaultSerializerProvider)(var2 == null ? new DefaultSerializerProvider$Impl() : var2);
      this._deserializationContext = (DefaultDeserializationContext)(var3 == null ? new DefaultDeserializationContext$Impl(BeanDeserializerFactory.instance) : var3);
      this._serializerFactory = BeanSerializerFactory.instance;
   }

   protected ClassIntrospector defaultClassIntrospector() {
      return new BasicClassIntrospector();
   }

   public ObjectMapper copy() {
      this._checkInvalidCopy(ObjectMapper.class);
      return new ObjectMapper(this);
   }

   protected void _checkInvalidCopy(Class var1) {
      if (this.getClass() != var1) {
         throw new IllegalStateException("Failed copy(): " + this.getClass().getName() + " (version: " + this.version() + ") does not override copy(); it has to");
      }
   }

   protected ObjectReader _newReader(DeserializationConfig var1) {
      return new ObjectReader(this, var1);
   }

   protected ObjectReader _newReader(DeserializationConfig var1, JavaType var2, Object var3, FormatSchema var4, InjectableValues var5) {
      return new ObjectReader(this, var1, var2, var3, var4, var5);
   }

   protected ObjectWriter _newWriter(SerializationConfig var1) {
      return new ObjectWriter(this, var1);
   }

   protected ObjectWriter _newWriter(SerializationConfig var1, FormatSchema var2) {
      return new ObjectWriter(this, var1, var2);
   }

   protected ObjectWriter _newWriter(SerializationConfig var1, JavaType var2, PrettyPrinter var3) {
      return new ObjectWriter(this, var1, var2, var3);
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   public ObjectMapper registerModule(Module var1) {
      if (this.isEnabled(MapperFeature.IGNORE_DUPLICATE_MODULE_REGISTRATIONS)) {
         Object var2 = var1.getTypeId();
         if (var2 != null) {
            if (this._registeredModuleTypes == null) {
               this._registeredModuleTypes = new LinkedHashSet();
            }

            if (!this._registeredModuleTypes.add(var2)) {
               return this;
            }
         }
      }

      String var4 = var1.getModuleName();
      if (var4 == null) {
         throw new IllegalArgumentException("Module without defined name");
      } else {
         Version var3 = var1.version();
         if (var3 == null) {
            throw new IllegalArgumentException("Module without defined version");
         } else {
            var1.setupModule(new ObjectMapper$1(this));
            return this;
         }
      }
   }

   public ObjectMapper registerModules(Module... var1) {
      Module[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module var5 = var2[var4];
         this.registerModule(var5);
      }

      return this;
   }

   public ObjectMapper registerModules(Iterable var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Module var3 = (Module)var2.next();
         this.registerModule(var3);
      }

      return this;
   }

   public Set getRegisteredModuleIds() {
      return Collections.unmodifiableSet(this._registeredModuleTypes);
   }

   public static List findModules() {
      return findModules((ClassLoader)null);
   }

   public static List findModules(ClassLoader var0) {
      ArrayList var1 = new ArrayList();
      ServiceLoader var2 = secureGetServiceLoader(Module.class, var0);
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Module var4 = (Module)var3.next();
         var1.add(var4);
      }

      return var1;
   }

   private static ServiceLoader secureGetServiceLoader(Class var0, ClassLoader var1) {
      SecurityManager var2 = System.getSecurityManager();
      if (var2 == null) {
         return var1 == null ? ServiceLoader.load(var0) : ServiceLoader.load(var0, var1);
      } else {
         return (ServiceLoader)AccessController.doPrivileged(new ObjectMapper$2(var1, var0));
      }
   }

   public ObjectMapper findAndRegisterModules() {
      return this.registerModules((Iterable)findModules());
   }

   public SerializationConfig getSerializationConfig() {
      return this._serializationConfig;
   }

   public DeserializationConfig getDeserializationConfig() {
      return this._deserializationConfig;
   }

   public DeserializationContext getDeserializationContext() {
      return this._deserializationContext;
   }

   public ObjectMapper setSerializerFactory(SerializerFactory var1) {
      this._serializerFactory = var1;
      return this;
   }

   public SerializerFactory getSerializerFactory() {
      return this._serializerFactory;
   }

   public ObjectMapper setSerializerProvider(DefaultSerializerProvider var1) {
      this._serializerProvider = var1;
      return this;
   }

   public SerializerProvider getSerializerProvider() {
      return this._serializerProvider;
   }

   public SerializerProvider getSerializerProviderInstance() {
      return this._serializerProvider(this._serializationConfig);
   }

   public ObjectMapper setMixIns(Map var1) {
      this._mixIns.setLocalDefinitions(var1);
      return this;
   }

   public ObjectMapper addMixIn(Class var1, Class var2) {
      this._mixIns.addLocalDefinition(var1, var2);
      return this;
   }

   public ObjectMapper setMixInResolver(ClassIntrospector$MixInResolver var1) {
      SimpleMixInResolver var2 = this._mixIns.withOverrides(var1);
      if (var2 != this._mixIns) {
         this._mixIns = var2;
         this._deserializationConfig = new DeserializationConfig(this._deserializationConfig, var2);
         this._serializationConfig = new SerializationConfig(this._serializationConfig, var2);
      }

      return this;
   }

   public Class findMixInClassFor(Class var1) {
      return this._mixIns.findMixInClassFor(var1);
   }

   public int mixInCount() {
      return this._mixIns.localSize();
   }

   /** @deprecated */
   @Deprecated
   public void setMixInAnnotations(Map var1) {
      this.setMixIns(var1);
   }

   /** @deprecated */
   @Deprecated
   public final void addMixInAnnotations(Class var1, Class var2) {
      this.addMixIn(var1, var2);
   }

   public VisibilityChecker getVisibilityChecker() {
      return this._serializationConfig.getDefaultVisibilityChecker();
   }

   public ObjectMapper setVisibility(VisibilityChecker var1) {
      this._configOverrides.setDefaultVisibility(var1);
      return this;
   }

   public ObjectMapper setVisibility(PropertyAccessor var1, JsonAutoDetect$Visibility var2) {
      VisibilityChecker var3 = this._configOverrides.getDefaultVisibility();
      var3 = var3.withVisibility(var1, var2);
      this._configOverrides.setDefaultVisibility(var3);
      return this;
   }

   public SubtypeResolver getSubtypeResolver() {
      return this._subtypeResolver;
   }

   public ObjectMapper setSubtypeResolver(SubtypeResolver var1) {
      this._subtypeResolver = var1;
      this._deserializationConfig = this._deserializationConfig.with(var1);
      this._serializationConfig = this._serializationConfig.with(var1);
      return this;
   }

   public ObjectMapper setAnnotationIntrospector(AnnotationIntrospector var1) {
      this._serializationConfig = (SerializationConfig)this._serializationConfig.with((AnnotationIntrospector)var1);
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((AnnotationIntrospector)var1);
      return this;
   }

   public ObjectMapper setAnnotationIntrospectors(AnnotationIntrospector var1, AnnotationIntrospector var2) {
      this._serializationConfig = (SerializationConfig)this._serializationConfig.with((AnnotationIntrospector)var1);
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((AnnotationIntrospector)var2);
      return this;
   }

   public ObjectMapper setPropertyNamingStrategy(PropertyNamingStrategy var1) {
      this._serializationConfig = (SerializationConfig)this._serializationConfig.with((PropertyNamingStrategy)var1);
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((PropertyNamingStrategy)var1);
      return this;
   }

   public PropertyNamingStrategy getPropertyNamingStrategy() {
      return this._serializationConfig.getPropertyNamingStrategy();
   }

   public ObjectMapper setDefaultPrettyPrinter(PrettyPrinter var1) {
      this._serializationConfig = this._serializationConfig.withDefaultPrettyPrinter(var1);
      return this;
   }

   /** @deprecated */
   @Deprecated
   public void setVisibilityChecker(VisibilityChecker var1) {
      this.setVisibility(var1);
   }

   public ObjectMapper setSerializationInclusion(JsonInclude$Include var1) {
      this.setPropertyInclusion(JsonInclude$Value.construct(var1, var1));
      return this;
   }

   /** @deprecated */
   @Deprecated
   public ObjectMapper setPropertyInclusion(JsonInclude$Value var1) {
      return this.setDefaultPropertyInclusion(var1);
   }

   public ObjectMapper setDefaultPropertyInclusion(JsonInclude$Value var1) {
      this._configOverrides.setDefaultInclusion(var1);
      return this;
   }

   public ObjectMapper setDefaultPropertyInclusion(JsonInclude$Include var1) {
      this._configOverrides.setDefaultInclusion(JsonInclude$Value.construct(var1, var1));
      return this;
   }

   public ObjectMapper setDefaultSetterInfo(JsonSetter$Value var1) {
      this._configOverrides.setDefaultSetterInfo(var1);
      return this;
   }

   public ObjectMapper setDefaultVisibility(JsonAutoDetect$Value var1) {
      this._configOverrides.setDefaultVisibility(VisibilityChecker$Std.construct(var1));
      return this;
   }

   public ObjectMapper setDefaultMergeable(Boolean var1) {
      this._configOverrides.setDefaultMergeable(var1);
      return this;
   }

   public ObjectMapper enableDefaultTyping() {
      return this.enableDefaultTyping(ObjectMapper$DefaultTyping.OBJECT_AND_NON_CONCRETE);
   }

   public ObjectMapper enableDefaultTyping(ObjectMapper$DefaultTyping var1) {
      return this.enableDefaultTyping(var1, JsonTypeInfo$As.WRAPPER_ARRAY);
   }

   public ObjectMapper enableDefaultTyping(ObjectMapper$DefaultTyping var1, JsonTypeInfo$As var2) {
      if (var2 == JsonTypeInfo$As.EXTERNAL_PROPERTY) {
         throw new IllegalArgumentException("Cannot use includeAs of " + var2);
      } else {
         ObjectMapper$DefaultTypeResolverBuilder var3 = new ObjectMapper$DefaultTypeResolverBuilder(var1);
         TypeResolverBuilder var4 = var3.init(JsonTypeInfo$Id.CLASS, (TypeIdResolver)null);
         var4 = var4.inclusion(var2);
         return this.setDefaultTyping(var4);
      }
   }

   public ObjectMapper enableDefaultTypingAsProperty(ObjectMapper$DefaultTyping var1, String var2) {
      ObjectMapper$DefaultTypeResolverBuilder var3 = new ObjectMapper$DefaultTypeResolverBuilder(var1);
      TypeResolverBuilder var4 = var3.init(JsonTypeInfo$Id.CLASS, (TypeIdResolver)null);
      var4 = var4.inclusion(JsonTypeInfo$As.PROPERTY);
      var4 = var4.typeProperty(var2);
      return this.setDefaultTyping(var4);
   }

   public ObjectMapper disableDefaultTyping() {
      return this.setDefaultTyping((TypeResolverBuilder)null);
   }

   public ObjectMapper setDefaultTyping(TypeResolverBuilder var1) {
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((TypeResolverBuilder)var1);
      this._serializationConfig = (SerializationConfig)this._serializationConfig.with((TypeResolverBuilder)var1);
      return this;
   }

   public void registerSubtypes(Class... var1) {
      this.getSubtypeResolver().registerSubtypes(var1);
   }

   public void registerSubtypes(NamedType... var1) {
      this.getSubtypeResolver().registerSubtypes(var1);
   }

   public void registerSubtypes(Collection var1) {
      this.getSubtypeResolver().registerSubtypes(var1);
   }

   public MutableConfigOverride configOverride(Class var1) {
      return this._configOverrides.findOrCreateOverride(var1);
   }

   public TypeFactory getTypeFactory() {
      return this._typeFactory;
   }

   public ObjectMapper setTypeFactory(TypeFactory var1) {
      this._typeFactory = var1;
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((TypeFactory)var1);
      this._serializationConfig = (SerializationConfig)this._serializationConfig.with((TypeFactory)var1);
      return this;
   }

   public JavaType constructType(Type var1) {
      return this._typeFactory.constructType(var1);
   }

   public JsonNodeFactory getNodeFactory() {
      return this._deserializationConfig.getNodeFactory();
   }

   public ObjectMapper setNodeFactory(JsonNodeFactory var1) {
      this._deserializationConfig = this._deserializationConfig.with(var1);
      return this;
   }

   public ObjectMapper addHandler(DeserializationProblemHandler var1) {
      this._deserializationConfig = this._deserializationConfig.withHandler(var1);
      return this;
   }

   public ObjectMapper clearProblemHandlers() {
      this._deserializationConfig = this._deserializationConfig.withNoProblemHandlers();
      return this;
   }

   public ObjectMapper setConfig(DeserializationConfig var1) {
      this._deserializationConfig = var1;
      return this;
   }

   /** @deprecated */
   @Deprecated
   public void setFilters(FilterProvider var1) {
      this._serializationConfig = this._serializationConfig.withFilters(var1);
   }

   public ObjectMapper setFilterProvider(FilterProvider var1) {
      this._serializationConfig = this._serializationConfig.withFilters(var1);
      return this;
   }

   public ObjectMapper setBase64Variant(Base64Variant var1) {
      this._serializationConfig = (SerializationConfig)this._serializationConfig.with((Base64Variant)var1);
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((Base64Variant)var1);
      return this;
   }

   public ObjectMapper setConfig(SerializationConfig var1) {
      this._serializationConfig = var1;
      return this;
   }

   public JsonFactory getFactory() {
      return this._jsonFactory;
   }

   /** @deprecated */
   @Deprecated
   public JsonFactory getJsonFactory() {
      return this.getFactory();
   }

   public ObjectMapper setDateFormat(DateFormat var1) {
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((DateFormat)var1);
      this._serializationConfig = this._serializationConfig.with(var1);
      return this;
   }

   public DateFormat getDateFormat() {
      return this._serializationConfig.getDateFormat();
   }

   public Object setHandlerInstantiator(HandlerInstantiator var1) {
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((HandlerInstantiator)var1);
      this._serializationConfig = (SerializationConfig)this._serializationConfig.with((HandlerInstantiator)var1);
      return this;
   }

   public ObjectMapper setInjectableValues(InjectableValues var1) {
      this._injectableValues = var1;
      return this;
   }

   public InjectableValues getInjectableValues() {
      return this._injectableValues;
   }

   public ObjectMapper setLocale(Locale var1) {
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((Locale)var1);
      this._serializationConfig = (SerializationConfig)this._serializationConfig.with((Locale)var1);
      return this;
   }

   public ObjectMapper setTimeZone(TimeZone var1) {
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((TimeZone)var1);
      this._serializationConfig = (SerializationConfig)this._serializationConfig.with((TimeZone)var1);
      return this;
   }

   public boolean isEnabled(MapperFeature var1) {
      return this._serializationConfig.isEnabled(var1);
   }

   public ObjectMapper configure(MapperFeature var1, boolean var2) {
      this._serializationConfig = var2 ? (SerializationConfig)this._serializationConfig.with((MapperFeature[])(new MapperFeature[]{var1})) : (SerializationConfig)this._serializationConfig.without((MapperFeature[])(new MapperFeature[]{var1}));
      this._deserializationConfig = var2 ? (DeserializationConfig)this._deserializationConfig.with((MapperFeature[])(new MapperFeature[]{var1})) : (DeserializationConfig)this._deserializationConfig.without((MapperFeature[])(new MapperFeature[]{var1}));
      return this;
   }

   public ObjectMapper enable(MapperFeature... var1) {
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.with((MapperFeature[])var1);
      this._serializationConfig = (SerializationConfig)this._serializationConfig.with((MapperFeature[])var1);
      return this;
   }

   public ObjectMapper disable(MapperFeature... var1) {
      this._deserializationConfig = (DeserializationConfig)this._deserializationConfig.without((MapperFeature[])var1);
      this._serializationConfig = (SerializationConfig)this._serializationConfig.without((MapperFeature[])var1);
      return this;
   }

   public boolean isEnabled(SerializationFeature var1) {
      return this._serializationConfig.isEnabled(var1);
   }

   public ObjectMapper configure(SerializationFeature var1, boolean var2) {
      this._serializationConfig = var2 ? this._serializationConfig.with(var1) : this._serializationConfig.without(var1);
      return this;
   }

   public ObjectMapper enable(SerializationFeature var1) {
      this._serializationConfig = this._serializationConfig.with(var1);
      return this;
   }

   public ObjectMapper enable(SerializationFeature var1, SerializationFeature... var2) {
      this._serializationConfig = this._serializationConfig.with(var1, var2);
      return this;
   }

   public ObjectMapper disable(SerializationFeature var1) {
      this._serializationConfig = this._serializationConfig.without(var1);
      return this;
   }

   public ObjectMapper disable(SerializationFeature var1, SerializationFeature... var2) {
      this._serializationConfig = this._serializationConfig.without(var1, var2);
      return this;
   }

   public boolean isEnabled(DeserializationFeature var1) {
      return this._deserializationConfig.isEnabled(var1);
   }

   public ObjectMapper configure(DeserializationFeature var1, boolean var2) {
      this._deserializationConfig = var2 ? this._deserializationConfig.with(var1) : this._deserializationConfig.without(var1);
      return this;
   }

   public ObjectMapper enable(DeserializationFeature var1) {
      this._deserializationConfig = this._deserializationConfig.with(var1);
      return this;
   }

   public ObjectMapper enable(DeserializationFeature var1, DeserializationFeature... var2) {
      this._deserializationConfig = this._deserializationConfig.with(var1, var2);
      return this;
   }

   public ObjectMapper disable(DeserializationFeature var1) {
      this._deserializationConfig = this._deserializationConfig.without(var1);
      return this;
   }

   public ObjectMapper disable(DeserializationFeature var1, DeserializationFeature... var2) {
      this._deserializationConfig = this._deserializationConfig.without(var1, var2);
      return this;
   }

   public boolean isEnabled(JsonParser$Feature var1) {
      return this._deserializationConfig.isEnabled(var1, this._jsonFactory);
   }

   public ObjectMapper configure(JsonParser$Feature var1, boolean var2) {
      this._jsonFactory.configure(var1, var2);
      return this;
   }

   public ObjectMapper enable(JsonParser$Feature... var1) {
      JsonParser$Feature[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         JsonParser$Feature var5 = var2[var4];
         this._jsonFactory.enable(var5);
      }

      return this;
   }

   public ObjectMapper disable(JsonParser$Feature... var1) {
      JsonParser$Feature[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         JsonParser$Feature var5 = var2[var4];
         this._jsonFactory.disable(var5);
      }

      return this;
   }

   public boolean isEnabled(JsonGenerator$Feature var1) {
      return this._serializationConfig.isEnabled(var1, this._jsonFactory);
   }

   public ObjectMapper configure(JsonGenerator$Feature var1, boolean var2) {
      this._jsonFactory.configure(var1, var2);
      return this;
   }

   public ObjectMapper enable(JsonGenerator$Feature... var1) {
      JsonGenerator$Feature[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         JsonGenerator$Feature var5 = var2[var4];
         this._jsonFactory.enable(var5);
      }

      return this;
   }

   public ObjectMapper disable(JsonGenerator$Feature... var1) {
      JsonGenerator$Feature[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         JsonGenerator$Feature var5 = var2[var4];
         this._jsonFactory.disable(var5);
      }

      return this;
   }

   public boolean isEnabled(JsonFactory$Feature var1) {
      return this._jsonFactory.isEnabled(var1);
   }

   public Object readValue(JsonParser var1, Class var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readValue(this.getDeserializationConfig(), var1, this._typeFactory.constructType((Type)var2));
   }

   public Object readValue(JsonParser var1, TypeReference var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readValue(this.getDeserializationConfig(), var1, this._typeFactory.constructType(var2));
   }

   public final Object readValue(JsonParser var1, ResolvedType var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readValue(this.getDeserializationConfig(), var1, (JavaType)var2);
   }

   public Object readValue(JsonParser var1, JavaType var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readValue(this.getDeserializationConfig(), var1, var2);
   }

   public TreeNode readTree(JsonParser var1) throws IOException, JsonProcessingException {
      DeserializationConfig var2 = this.getDeserializationConfig();
      JsonToken var3 = var1.getCurrentToken();
      if (var3 == null) {
         var3 = var1.nextToken();
         if (var3 == null) {
            return null;
         }
      }

      Object var4 = (JsonNode)this._readValue(var2, var1, JSON_NODE_TYPE);
      if (var4 == null) {
         var4 = this.getNodeFactory().nullNode();
      }

      return (TreeNode)var4;
   }

   public MappingIterator readValues(JsonParser var1, ResolvedType var2) throws IOException, JsonProcessingException {
      return this.readValues(var1, (JavaType)var2);
   }

   public MappingIterator readValues(JsonParser var1, JavaType var2) throws IOException, JsonProcessingException {
      DeserializationConfig var3 = this.getDeserializationConfig();
      DefaultDeserializationContext var4 = this.createDeserializationContext(var1, var3);
      JsonDeserializer var5 = this._findRootDeserializer(var4, var2);
      return new MappingIterator(var2, var1, var4, var5, false, (Object)null);
   }

   public MappingIterator readValues(JsonParser var1, Class var2) throws IOException, JsonProcessingException {
      return this.readValues(var1, this._typeFactory.constructType((Type)var2));
   }

   public MappingIterator readValues(JsonParser var1, TypeReference var2) throws IOException, JsonProcessingException {
      return this.readValues(var1, this._typeFactory.constructType(var2));
   }

   public JsonNode readTree(InputStream var1) throws IOException {
      return this._readTreeAndClose(this._jsonFactory.createParser(var1));
   }

   public JsonNode readTree(Reader var1) throws IOException {
      return this._readTreeAndClose(this._jsonFactory.createParser(var1));
   }

   public JsonNode readTree(String var1) throws IOException {
      return this._readTreeAndClose(this._jsonFactory.createParser(var1));
   }

   public JsonNode readTree(byte[] var1) throws IOException {
      return this._readTreeAndClose(this._jsonFactory.createParser(var1));
   }

   public JsonNode readTree(File var1) throws IOException, JsonProcessingException {
      return this._readTreeAndClose(this._jsonFactory.createParser(var1));
   }

   public JsonNode readTree(URL var1) throws IOException {
      return this._readTreeAndClose(this._jsonFactory.createParser(var1));
   }

   public void writeValue(JsonGenerator var1, Object var2) throws IOException, JsonGenerationException, JsonMappingException {
      SerializationConfig var3 = this.getSerializationConfig();
      if (var3.isEnabled(SerializationFeature.INDENT_OUTPUT) && var1.getPrettyPrinter() == null) {
         var1.setPrettyPrinter(var3.constructDefaultPrettyPrinter());
      }

      if (var3.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && var2 instanceof Closeable) {
         this._writeCloseableValue(var1, var2, var3);
      } else {
         this._serializerProvider(var3).serializeValue(var1, var2);
         if (var3.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            var1.flush();
         }
      }

   }

   public void writeTree(JsonGenerator var1, TreeNode var2) throws IOException, JsonProcessingException {
      SerializationConfig var3 = this.getSerializationConfig();
      this._serializerProvider(var3).serializeValue(var1, var2);
      if (var3.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
         var1.flush();
      }

   }

   public void writeTree(JsonGenerator var1, JsonNode var2) throws IOException, JsonProcessingException {
      SerializationConfig var3 = this.getSerializationConfig();
      this._serializerProvider(var3).serializeValue(var1, var2);
      if (var3.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
         var1.flush();
      }

   }

   public ObjectNode createObjectNode() {
      return this._deserializationConfig.getNodeFactory().objectNode();
   }

   public ArrayNode createArrayNode() {
      return this._deserializationConfig.getNodeFactory().arrayNode();
   }

   public JsonParser treeAsTokens(TreeNode var1) {
      return new TreeTraversingParser((JsonNode)var1, this);
   }

   public Object treeToValue(TreeNode var1, Class var2) throws JsonProcessingException {
      Object var10000;
      try {
         if (var2 != Object.class && var2.isAssignableFrom(var1.getClass())) {
            TreeNode var6 = var1;
            return var6;
         }

         if (var1.asToken() == JsonToken.VALUE_EMBEDDED_OBJECT && var1 instanceof POJONode) {
            Object var3 = ((POJONode)var1).getPojo();
            if (var3 == null || var2.isInstance(var3)) {
               var10000 = var3;
               return var10000;
            }
         }

         var10000 = this.readValue(this.treeAsTokens(var1), var2);
      } catch (JsonProcessingException var4) {
         throw var4;
      } catch (IOException var5) {
         throw new IllegalArgumentException(var5.getMessage(), var5);
      }

      return var10000;
   }

   public JsonNode valueToTree(Object var1) throws IllegalArgumentException {
      if (var1 == null) {
         return null;
      } else {
         TokenBuffer var2 = new TokenBuffer(this, false);
         if (this.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
            var2 = var2.forceUseOfBigDecimal(true);
         }

         JsonNode var3;
         try {
            this.writeValue((JsonGenerator)var2, var1);
            JsonParser var4 = var2.asParser();
            var3 = (JsonNode)this.readTree(var4);
            var4.close();
         } catch (IOException var5) {
            throw new IllegalArgumentException(var5.getMessage(), var5);
         }

         return var3;
      }
   }

   public boolean canSerialize(Class var1) {
      return this._serializerProvider(this.getSerializationConfig()).hasSerializerFor(var1, (AtomicReference)null);
   }

   public boolean canSerialize(Class var1, AtomicReference var2) {
      return this._serializerProvider(this.getSerializationConfig()).hasSerializerFor(var1, var2);
   }

   public boolean canDeserialize(JavaType var1) {
      return this.createDeserializationContext((JsonParser)null, this.getDeserializationConfig()).hasValueDeserializerFor(var1, (AtomicReference)null);
   }

   public boolean canDeserialize(JavaType var1, AtomicReference var2) {
      return this.createDeserializationContext((JsonParser)null, this.getDeserializationConfig()).hasValueDeserializerFor(var1, var2);
   }

   public Object readValue(File var1, Class var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType((Type)var2));
   }

   public Object readValue(File var1, TypeReference var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType(var2));
   }

   public Object readValue(File var1, JavaType var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), var2);
   }

   public Object readValue(URL var1, Class var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType((Type)var2));
   }

   public Object readValue(URL var1, TypeReference var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType(var2));
   }

   public Object readValue(URL var1, JavaType var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), var2);
   }

   public Object readValue(String var1, Class var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType((Type)var2));
   }

   public Object readValue(String var1, TypeReference var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType(var2));
   }

   public Object readValue(String var1, JavaType var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), var2);
   }

   public Object readValue(Reader var1, Class var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType((Type)var2));
   }

   public Object readValue(Reader var1, TypeReference var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType(var2));
   }

   public Object readValue(Reader var1, JavaType var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), var2);
   }

   public Object readValue(InputStream var1, Class var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType((Type)var2));
   }

   public Object readValue(InputStream var1, TypeReference var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType(var2));
   }

   public Object readValue(InputStream var1, JavaType var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), var2);
   }

   public Object readValue(byte[] var1, Class var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType((Type)var2));
   }

   public Object readValue(byte[] var1, int var2, int var3, Class var4) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1, var2, var3), this._typeFactory.constructType((Type)var4));
   }

   public Object readValue(byte[] var1, TypeReference var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType(var2));
   }

   public Object readValue(byte[] var1, int var2, int var3, TypeReference var4) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1, var2, var3), this._typeFactory.constructType(var4));
   }

   public Object readValue(byte[] var1, JavaType var2) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), var2);
   }

   public Object readValue(byte[] var1, int var2, int var3, JavaType var4) throws IOException, JsonParseException, JsonMappingException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1, var2, var3), var4);
   }

   public Object readValue(DataInput var1, Class var2) throws IOException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), this._typeFactory.constructType((Type)var2));
   }

   public Object readValue(DataInput var1, JavaType var2) throws IOException {
      return this._readMapAndClose(this._jsonFactory.createParser(var1), var2);
   }

   public void writeValue(File var1, Object var2) throws IOException, JsonGenerationException, JsonMappingException {
      this._configAndWriteValue(this._jsonFactory.createGenerator(var1, JsonEncoding.UTF8), var2);
   }

   public void writeValue(OutputStream var1, Object var2) throws IOException, JsonGenerationException, JsonMappingException {
      this._configAndWriteValue(this._jsonFactory.createGenerator(var1, JsonEncoding.UTF8), var2);
   }

   public void writeValue(DataOutput var1, Object var2) throws IOException {
      this._configAndWriteValue(this._jsonFactory.createGenerator(var1, JsonEncoding.UTF8), var2);
   }

   public void writeValue(Writer var1, Object var2) throws IOException, JsonGenerationException, JsonMappingException {
      this._configAndWriteValue(this._jsonFactory.createGenerator(var1), var2);
   }

   public String writeValueAsString(Object var1) throws JsonProcessingException {
      SegmentedStringWriter var2 = new SegmentedStringWriter(this._jsonFactory._getBufferRecycler());

      try {
         this._configAndWriteValue(this._jsonFactory.createGenerator((Writer)var2), var1);
      } catch (JsonProcessingException var4) {
         throw var4;
      } catch (IOException var5) {
         throw JsonMappingException.fromUnexpectedIOE(var5);
      }

      return var2.getAndClear();
   }

   public byte[] writeValueAsBytes(Object var1) throws JsonProcessingException {
      ByteArrayBuilder var2 = new ByteArrayBuilder(this._jsonFactory._getBufferRecycler());

      try {
         this._configAndWriteValue(this._jsonFactory.createGenerator((OutputStream)var2, JsonEncoding.UTF8), var1);
      } catch (JsonProcessingException var4) {
         throw var4;
      } catch (IOException var5) {
         throw JsonMappingException.fromUnexpectedIOE(var5);
      }

      byte[] var3 = var2.toByteArray();
      var2.release();
      return var3;
   }

   public ObjectWriter writer() {
      return this._newWriter(this.getSerializationConfig());
   }

   public ObjectWriter writer(SerializationFeature var1) {
      return this._newWriter(this.getSerializationConfig().with(var1));
   }

   public ObjectWriter writer(SerializationFeature var1, SerializationFeature... var2) {
      return this._newWriter(this.getSerializationConfig().with(var1, var2));
   }

   public ObjectWriter writer(DateFormat var1) {
      return this._newWriter(this.getSerializationConfig().with(var1));
   }

   public ObjectWriter writerWithView(Class var1) {
      return this._newWriter(this.getSerializationConfig().withView(var1));
   }

   public ObjectWriter writerFor(Class var1) {
      return this._newWriter(this.getSerializationConfig(), var1 == null ? null : this._typeFactory.constructType((Type)var1), (PrettyPrinter)null);
   }

   public ObjectWriter writerFor(TypeReference var1) {
      return this._newWriter(this.getSerializationConfig(), var1 == null ? null : this._typeFactory.constructType(var1), (PrettyPrinter)null);
   }

   public ObjectWriter writerFor(JavaType var1) {
      return this._newWriter(this.getSerializationConfig(), var1, (PrettyPrinter)null);
   }

   public ObjectWriter writer(PrettyPrinter var1) {
      if (var1 == null) {
         var1 = ObjectWriter.NULL_PRETTY_PRINTER;
      }

      return this._newWriter(this.getSerializationConfig(), (JavaType)null, var1);
   }

   public ObjectWriter writerWithDefaultPrettyPrinter() {
      SerializationConfig var1 = this.getSerializationConfig();
      return this._newWriter(var1, (JavaType)null, var1.getDefaultPrettyPrinter());
   }

   public ObjectWriter writer(FilterProvider var1) {
      return this._newWriter(this.getSerializationConfig().withFilters(var1));
   }

   public ObjectWriter writer(FormatSchema var1) {
      this._verifySchemaType(var1);
      return this._newWriter(this.getSerializationConfig(), var1);
   }

   public ObjectWriter writer(Base64Variant var1) {
      return this._newWriter((SerializationConfig)this.getSerializationConfig().with((Base64Variant)var1));
   }

   public ObjectWriter writer(CharacterEscapes var1) {
      return this._newWriter(this.getSerializationConfig()).with(var1);
   }

   public ObjectWriter writer(ContextAttributes var1) {
      return this._newWriter(this.getSerializationConfig().with(var1));
   }

   /** @deprecated */
   @Deprecated
   public ObjectWriter writerWithType(Class var1) {
      return this._newWriter(this.getSerializationConfig(), var1 == null ? null : this._typeFactory.constructType((Type)var1), (PrettyPrinter)null);
   }

   /** @deprecated */
   @Deprecated
   public ObjectWriter writerWithType(TypeReference var1) {
      return this._newWriter(this.getSerializationConfig(), var1 == null ? null : this._typeFactory.constructType(var1), (PrettyPrinter)null);
   }

   /** @deprecated */
   @Deprecated
   public ObjectWriter writerWithType(JavaType var1) {
      return this._newWriter(this.getSerializationConfig(), var1, (PrettyPrinter)null);
   }

   public ObjectReader reader() {
      return this._newReader(this.getDeserializationConfig()).with(this._injectableValues);
   }

   public ObjectReader reader(DeserializationFeature var1) {
      return this._newReader(this.getDeserializationConfig().with(var1));
   }

   public ObjectReader reader(DeserializationFeature var1, DeserializationFeature... var2) {
      return this._newReader(this.getDeserializationConfig().with(var1, var2));
   }

   public ObjectReader readerForUpdating(Object var1) {
      JavaType var2 = this._typeFactory.constructType((Type)var1.getClass());
      return this._newReader(this.getDeserializationConfig(), var2, var1, (FormatSchema)null, this._injectableValues);
   }

   public ObjectReader readerFor(JavaType var1) {
      return this._newReader(this.getDeserializationConfig(), var1, (Object)null, (FormatSchema)null, this._injectableValues);
   }

   public ObjectReader readerFor(Class var1) {
      return this._newReader(this.getDeserializationConfig(), this._typeFactory.constructType((Type)var1), (Object)null, (FormatSchema)null, this._injectableValues);
   }

   public ObjectReader readerFor(TypeReference var1) {
      return this._newReader(this.getDeserializationConfig(), this._typeFactory.constructType(var1), (Object)null, (FormatSchema)null, this._injectableValues);
   }

   public ObjectReader reader(JsonNodeFactory var1) {
      return this._newReader(this.getDeserializationConfig()).with(var1);
   }

   public ObjectReader reader(FormatSchema var1) {
      this._verifySchemaType(var1);
      return this._newReader(this.getDeserializationConfig(), (JavaType)null, (Object)null, var1, this._injectableValues);
   }

   public ObjectReader reader(InjectableValues var1) {
      return this._newReader(this.getDeserializationConfig(), (JavaType)null, (Object)null, (FormatSchema)null, var1);
   }

   public ObjectReader readerWithView(Class var1) {
      return this._newReader(this.getDeserializationConfig().withView(var1));
   }

   public ObjectReader reader(Base64Variant var1) {
      return this._newReader((DeserializationConfig)this.getDeserializationConfig().with((Base64Variant)var1));
   }

   public ObjectReader reader(ContextAttributes var1) {
      return this._newReader(this.getDeserializationConfig().with(var1));
   }

   /** @deprecated */
   @Deprecated
   public ObjectReader reader(JavaType var1) {
      return this._newReader(this.getDeserializationConfig(), var1, (Object)null, (FormatSchema)null, this._injectableValues);
   }

   /** @deprecated */
   @Deprecated
   public ObjectReader reader(Class var1) {
      return this._newReader(this.getDeserializationConfig(), this._typeFactory.constructType((Type)var1), (Object)null, (FormatSchema)null, this._injectableValues);
   }

   /** @deprecated */
   @Deprecated
   public ObjectReader reader(TypeReference var1) {
      return this._newReader(this.getDeserializationConfig(), this._typeFactory.constructType(var1), (Object)null, (FormatSchema)null, this._injectableValues);
   }

   public Object convertValue(Object var1, Class var2) throws IllegalArgumentException {
      return this._convert(var1, this._typeFactory.constructType((Type)var2));
   }

   public Object convertValue(Object var1, TypeReference var2) throws IllegalArgumentException {
      return this._convert(var1, this._typeFactory.constructType(var2));
   }

   public Object convertValue(Object var1, JavaType var2) throws IllegalArgumentException {
      return this._convert(var1, var2);
   }

   protected Object _convert(Object var1, JavaType var2) throws IllegalArgumentException {
      if (var1 != null) {
         Class var3 = var2.getRawClass();
         if (var3 != Object.class && !var2.hasGenericTypes() && var3.isAssignableFrom(var1.getClass())) {
            return var1;
         }
      }

      TokenBuffer var12 = new TokenBuffer(this, false);
      if (this.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
         var12 = var12.forceUseOfBigDecimal(true);
      }

      Object var10000;
      try {
         SerializationConfig var4 = this.getSerializationConfig().without(SerializationFeature.WRAP_ROOT_VALUE);
         this._serializerProvider(var4).serializeValue(var12, var1);
         JsonParser var5 = var12.asParser();
         DeserializationConfig var7 = this.getDeserializationConfig();
         JsonToken var8 = this._initForReading(var5, var2);
         Object var6;
         DefaultDeserializationContext var9;
         if (var8 == JsonToken.VALUE_NULL) {
            var9 = this.createDeserializationContext(var5, var7);
            var6 = this._findRootDeserializer(var9, var2).getNullValue(var9);
         } else if (var8 != JsonToken.END_ARRAY && var8 != JsonToken.END_OBJECT) {
            var9 = this.createDeserializationContext(var5, var7);
            JsonDeserializer var10 = this._findRootDeserializer(var9, var2);
            var6 = var10.deserialize(var5, var9);
         } else {
            var6 = null;
         }

         var5.close();
         var10000 = var6;
      } catch (IOException var11) {
         throw new IllegalArgumentException(var11.getMessage(), var11);
      }

      return var10000;
   }

   public Object updateValue(Object var1, Object var2) throws JsonMappingException {
      Object var3 = var1;
      if (var1 != null && var2 != null) {
         TokenBuffer var4 = new TokenBuffer(this, false);
         if (this.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
            var4 = var4.forceUseOfBigDecimal(true);
         }

         try {
            SerializationConfig var5 = this.getSerializationConfig().without(SerializationFeature.WRAP_ROOT_VALUE);
            this._serializerProvider(var5).serializeValue(var4, var2);
            JsonParser var6 = var4.asParser();
            var3 = this.readerForUpdating(var1).readValue(var6);
            var6.close();
         } catch (IOException var7) {
            if (var7 instanceof JsonMappingException) {
               throw (JsonMappingException)var7;
            }

            throw JsonMappingException.fromUnexpectedIOE(var7);
         }
      }

      return var3;
   }

   /** @deprecated */
   @Deprecated
   public JsonSchema generateJsonSchema(Class var1) throws JsonMappingException {
      return this._serializerProvider(this.getSerializationConfig()).generateJsonSchema(var1);
   }

   public void acceptJsonFormatVisitor(Class var1, JsonFormatVisitorWrapper var2) throws JsonMappingException {
      this.acceptJsonFormatVisitor(this._typeFactory.constructType((Type)var1), var2);
   }

   public void acceptJsonFormatVisitor(JavaType var1, JsonFormatVisitorWrapper var2) throws JsonMappingException {
      if (var1 == null) {
         throw new IllegalArgumentException("type must be provided");
      } else {
         this._serializerProvider(this.getSerializationConfig()).acceptJsonFormatVisitor(var1, var2);
      }
   }

   protected DefaultSerializerProvider _serializerProvider(SerializationConfig var1) {
      return this._serializerProvider.createInstance(var1, this._serializerFactory);
   }

   protected final void _configAndWriteValue(JsonGenerator var1, Object var2) throws IOException {
      SerializationConfig var3 = this.getSerializationConfig();
      var3.initialize(var1);
      if (var3.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && var2 instanceof Closeable) {
         this._configAndWriteCloseable(var1, var2, var3);
      } else {
         try {
            this._serializerProvider(var3).serializeValue(var1, var2);
         } catch (Exception var5) {
            ClassUtil.closeOnFailAndThrowAsIOE(var1, var5);
            return;
         }

         var1.close();
      }
   }

   private final void _configAndWriteCloseable(JsonGenerator var1, Object var2, SerializationConfig var3) throws IOException {
      Closeable var4 = (Closeable)var2;

      try {
         this._serializerProvider(var3).serializeValue(var1, var2);
         Closeable var5 = var4;
         var4 = null;
         var5.close();
      } catch (Exception var6) {
         ClassUtil.closeOnFailAndThrowAsIOE(var1, var4, var6);
         return;
      }

      var1.close();
   }

   private final void _writeCloseableValue(JsonGenerator var1, Object var2, SerializationConfig var3) throws IOException {
      Closeable var4 = (Closeable)var2;

      try {
         this._serializerProvider(var3).serializeValue(var1, var2);
         if (var3.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            var1.flush();
         }
      } catch (Exception var6) {
         ClassUtil.closeOnFailAndThrowAsIOE((JsonGenerator)null, var4, var6);
         return;
      }

      var4.close();
   }

   protected Object _readValue(DeserializationConfig var1, JsonParser var2, JavaType var3) throws IOException {
      JsonToken var5 = this._initForReading(var2, var3);
      DefaultDeserializationContext var6 = this.createDeserializationContext(var2, var1);
      Object var4;
      if (var5 == JsonToken.VALUE_NULL) {
         var4 = this._findRootDeserializer(var6, var3).getNullValue(var6);
      } else if (var5 != JsonToken.END_ARRAY && var5 != JsonToken.END_OBJECT) {
         JsonDeserializer var7 = this._findRootDeserializer(var6, var3);
         if (var1.useRootWrapping()) {
            var4 = this._unwrapAndDeserialize(var2, var6, var1, var3, var7);
         } else {
            var4 = var7.deserialize(var2, var6);
         }
      } else {
         var4 = null;
      }

      var2.clearCurrentToken();
      if (var1.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
         this._verifyNoTrailingTokens(var2, var6, var3);
      }

      return var4;
   }

   protected Object _readMapAndClose(JsonParser var1, JavaType var2) throws IOException {
      JsonParser var3 = var1;
      Throwable var4 = null;
      boolean var17 = false;

      Object var22;
      try {
         var17 = true;
         JsonToken var6 = this._initForReading(var3, var2);
         DeserializationConfig var7 = this.getDeserializationConfig();
         DefaultDeserializationContext var8 = this.createDeserializationContext(var3, var7);
         Object var5;
         if (var6 == JsonToken.VALUE_NULL) {
            var5 = this._findRootDeserializer(var8, var2).getNullValue(var8);
         } else if (var6 != JsonToken.END_ARRAY && var6 != JsonToken.END_OBJECT) {
            JsonDeserializer var9 = this._findRootDeserializer(var8, var2);
            if (var7.useRootWrapping()) {
               var5 = this._unwrapAndDeserialize(var3, var8, var7, var2, var9);
            } else {
               var5 = var9.deserialize(var3, var8);
            }

            var8.checkUnresolvedObjectId();
         } else {
            var5 = null;
         }

         if (var7.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
            this._verifyNoTrailingTokens(var3, var8, var2);
         }

         var22 = var5;
         var17 = false;
      } catch (Throwable var20) {
         var4 = var20;
         throw var20;
      } finally {
         if (var17) {
            if (var1 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var18) {
                     var4.addSuppressed(var18);
                  }
               } else {
                  var1.close();
               }
            }

         }
      }

      if (var1 != null) {
         if (var4 != null) {
            try {
               var3.close();
            } catch (Throwable var19) {
               var4.addSuppressed(var19);
            }
         } else {
            var1.close();
         }
      }

      return var22;
   }

   protected JsonNode _readTreeAndClose(JsonParser var1) throws IOException {
      JsonParser var2 = var1;
      Throwable var3 = null;
      boolean var20 = false;

      NullNode var27;
      label175: {
         JsonNode var10;
         label176: {
            DefaultDeserializationContext var7;
            try {
               label180: {
                  var20 = true;
                  JavaType var4 = JSON_NODE_TYPE;
                  DeserializationConfig var5 = this.getDeserializationConfig();
                  var5.initialize(var2);
                  JsonToken var6 = var2.getCurrentToken();
                  if (var6 == null) {
                     var6 = var2.nextToken();
                     if (var6 == null) {
                        var7 = null;
                        var20 = false;
                        break label180;
                     }
                  }

                  if (var6 == JsonToken.VALUE_NULL) {
                     var27 = var5.getNodeFactory().nullNode();
                     var20 = false;
                     break label175;
                  }

                  var7 = this.createDeserializationContext(var2, var5);
                  JsonDeserializer var8 = this._findRootDeserializer(var7, var4);
                  Object var9;
                  if (var5.useRootWrapping()) {
                     var9 = this._unwrapAndDeserialize(var2, var7, var5, var4, var8);
                  } else {
                     var9 = var8.deserialize(var2, var7);
                     if (var5.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
                        this._verifyNoTrailingTokens(var2, var7, var4);
                     }
                  }

                  var10 = (JsonNode)var9;
                  var20 = false;
                  break label176;
               }
            } catch (Throwable var25) {
               var3 = var25;
               throw var25;
            } finally {
               if (var20) {
                  if (var1 != null) {
                     if (var3 != null) {
                        try {
                           var2.close();
                        } catch (Throwable var21) {
                           var3.addSuppressed(var21);
                        }
                     } else {
                        var1.close();
                     }
                  }

               }
            }

            if (var1 != null) {
               if (var3 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var22) {
                     var3.addSuppressed(var22);
                  }
               } else {
                  var1.close();
               }
            }

            return var7;
         }

         if (var1 != null) {
            if (var3 != null) {
               try {
                  var2.close();
               } catch (Throwable var24) {
                  var3.addSuppressed(var24);
               }
            } else {
               var1.close();
            }
         }

         return var10;
      }

      if (var1 != null) {
         if (var3 != null) {
            try {
               var2.close();
            } catch (Throwable var23) {
               var3.addSuppressed(var23);
            }
         } else {
            var1.close();
         }
      }

      return var27;
   }

   protected Object _unwrapAndDeserialize(JsonParser var1, DeserializationContext var2, DeserializationConfig var3, JavaType var4, JsonDeserializer var5) throws IOException {
      PropertyName var6 = var3.findRootName(var4);
      String var7 = var6.getSimpleName();
      if (var1.getCurrentToken() != JsonToken.START_OBJECT) {
         var2.reportWrongTokenException(var4, JsonToken.START_OBJECT, "Current token not START_OBJECT (needed to unwrap root name '%s'), but %s", var7, var1.getCurrentToken());
      }

      if (var1.nextToken() != JsonToken.FIELD_NAME) {
         var2.reportWrongTokenException(var4, JsonToken.FIELD_NAME, "Current token not FIELD_NAME (to contain expected root name '%s'), but %s", var7, var1.getCurrentToken());
      }

      String var8 = var1.getCurrentName();
      if (!var7.equals(var8)) {
         var2.reportInputMismatch(var4, "Root name '%s' does not match expected ('%s') for type %s", var8, var7, var4);
      }

      var1.nextToken();
      Object var9 = var5.deserialize(var1, var2);
      if (var1.nextToken() != JsonToken.END_OBJECT) {
         var2.reportWrongTokenException(var4, JsonToken.END_OBJECT, "Current token not END_OBJECT (to match wrapper object with root name '%s'), but %s", var7, var1.getCurrentToken());
      }

      if (var3.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
         this._verifyNoTrailingTokens(var1, var2, var4);
      }

      return var9;
   }

   protected DefaultDeserializationContext createDeserializationContext(JsonParser var1, DeserializationConfig var2) {
      return this._deserializationContext.createInstance(var2, var1, this._injectableValues);
   }

   protected JsonToken _initForReading(JsonParser var1, JavaType var2) throws IOException {
      this._deserializationConfig.initialize(var1);
      JsonToken var3 = var1.getCurrentToken();
      if (var3 == null) {
         var3 = var1.nextToken();
         if (var3 == null) {
            throw MismatchedInputException.from(var1, var2, "No content to map due to end-of-input");
         }
      }

      return var3;
   }

   /** @deprecated */
   @Deprecated
   protected JsonToken _initForReading(JsonParser var1) throws IOException {
      return this._initForReading(var1, (JavaType)null);
   }

   protected final void _verifyNoTrailingTokens(JsonParser var1, DeserializationContext var2, JavaType var3) throws IOException {
      JsonToken var4 = var1.nextToken();
      if (var4 != null) {
         Class var5 = ClassUtil.rawClass(var3);
         var2.reportTrailingTokens(var5, var1, var4);
      }

   }

   protected JsonDeserializer _findRootDeserializer(DeserializationContext var1, JavaType var2) throws JsonMappingException {
      JsonDeserializer var3 = (JsonDeserializer)this._rootDeserializers.get(var2);
      if (var3 != null) {
         return var3;
      } else {
         var3 = var1.findRootValueDeserializer(var2);
         if (var3 == null) {
            return (JsonDeserializer)var1.reportBadDefinition(var2, "Cannot find a deserializer for type " + var2);
         } else {
            this._rootDeserializers.put(var2, var3);
            return var3;
         }
      }
   }

   protected void _verifySchemaType(FormatSchema var1) {
      if (var1 != null && !this._jsonFactory.canUseSchema(var1)) {
         throw new IllegalArgumentException("Cannot use FormatSchema of type " + var1.getClass().getName() + " for format " + this._jsonFactory.getFormatName());
      }
   }

   public TreeNode createArrayNode() {
      return this.createArrayNode();
   }

   public TreeNode createObjectNode() {
      return this.createObjectNode();
   }

   public Iterator readValues(JsonParser var1, ResolvedType var2) throws IOException {
      return this.readValues(var1, var2);
   }

   public Iterator readValues(JsonParser var1, TypeReference var2) throws IOException {
      return this.readValues(var1, var2);
   }

   public Iterator readValues(JsonParser var1, Class var2) throws IOException {
      return this.readValues(var1, var2);
   }

   static {
      DEFAULT_BASE = new BaseSettings((ClassIntrospector)null, DEFAULT_ANNOTATION_INTROSPECTOR, (PropertyNamingStrategy)null, TypeFactory.defaultInstance(), (TypeResolverBuilder)null, StdDateFormat.instance, (HandlerInstantiator)null, Locale.getDefault(), (TimeZone)null, Base64Variants.getDefaultVariant());
   }
}
