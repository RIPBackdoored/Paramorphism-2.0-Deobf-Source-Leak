package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$Feature;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.filter.FilteringParserDelegate;
import com.fasterxml.jackson.core.filter.JsonPointerBasedFilter;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.fasterxml.jackson.databind.deser.DataFormatReaders$Match;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.DataInput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectReader extends ObjectCodec implements Versioned, Serializable {
   private static final long serialVersionUID = 2L;
   private static final JavaType JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);
   protected final DeserializationConfig _config;
   protected final DefaultDeserializationContext _context;
   protected final JsonFactory _parserFactory;
   protected final boolean _unwrapRoot;
   private final TokenFilter _filter;
   protected final JavaType _valueType;
   protected final JsonDeserializer _rootDeserializer;
   protected final Object _valueToUpdate;
   protected final FormatSchema _schema;
   protected final InjectableValues _injectableValues;
   protected final DataFormatReaders _dataFormatReaders;
   protected final ConcurrentHashMap _rootDeserializers;

   protected ObjectReader(ObjectMapper var1, DeserializationConfig var2) {
      this(var1, var2, (JavaType)null, (Object)null, (FormatSchema)null, (InjectableValues)null);
   }

   protected ObjectReader(ObjectMapper var1, DeserializationConfig var2, JavaType var3, Object var4, FormatSchema var5, InjectableValues var6) {
      super();
      this._config = var2;
      this._context = var1._deserializationContext;
      this._rootDeserializers = var1._rootDeserializers;
      this._parserFactory = var1._jsonFactory;
      this._valueType = var3;
      this._valueToUpdate = var4;
      this._schema = var5;
      this._injectableValues = var6;
      this._unwrapRoot = var2.useRootWrapping();
      this._rootDeserializer = this._prefetchRootDeserializer(var3);
      this._dataFormatReaders = null;
      this._filter = null;
   }

   protected ObjectReader(ObjectReader var1, DeserializationConfig var2, JavaType var3, JsonDeserializer var4, Object var5, FormatSchema var6, InjectableValues var7, DataFormatReaders var8) {
      super();
      this._config = var2;
      this._context = var1._context;
      this._rootDeserializers = var1._rootDeserializers;
      this._parserFactory = var1._parserFactory;
      this._valueType = var3;
      this._rootDeserializer = var4;
      this._valueToUpdate = var5;
      this._schema = var6;
      this._injectableValues = var7;
      this._unwrapRoot = var2.useRootWrapping();
      this._dataFormatReaders = var8;
      this._filter = var1._filter;
   }

   protected ObjectReader(ObjectReader var1, DeserializationConfig var2) {
      super();
      this._config = var2;
      this._context = var1._context;
      this._rootDeserializers = var1._rootDeserializers;
      this._parserFactory = var1._parserFactory;
      this._valueType = var1._valueType;
      this._rootDeserializer = var1._rootDeserializer;
      this._valueToUpdate = var1._valueToUpdate;
      this._schema = var1._schema;
      this._injectableValues = var1._injectableValues;
      this._unwrapRoot = var2.useRootWrapping();
      this._dataFormatReaders = var1._dataFormatReaders;
      this._filter = var1._filter;
   }

   protected ObjectReader(ObjectReader var1, JsonFactory var2) {
      super();
      this._config = (DeserializationConfig)var1._config.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, var2.requiresPropertyOrdering());
      this._context = var1._context;
      this._rootDeserializers = var1._rootDeserializers;
      this._parserFactory = var2;
      this._valueType = var1._valueType;
      this._rootDeserializer = var1._rootDeserializer;
      this._valueToUpdate = var1._valueToUpdate;
      this._schema = var1._schema;
      this._injectableValues = var1._injectableValues;
      this._unwrapRoot = var1._unwrapRoot;
      this._dataFormatReaders = var1._dataFormatReaders;
      this._filter = var1._filter;
   }

   protected ObjectReader(ObjectReader var1, TokenFilter var2) {
      super();
      this._config = var1._config;
      this._context = var1._context;
      this._rootDeserializers = var1._rootDeserializers;
      this._parserFactory = var1._parserFactory;
      this._valueType = var1._valueType;
      this._rootDeserializer = var1._rootDeserializer;
      this._valueToUpdate = var1._valueToUpdate;
      this._schema = var1._schema;
      this._injectableValues = var1._injectableValues;
      this._unwrapRoot = var1._unwrapRoot;
      this._dataFormatReaders = var1._dataFormatReaders;
      this._filter = var2;
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   protected ObjectReader _new(ObjectReader var1, JsonFactory var2) {
      return new ObjectReader(var1, var2);
   }

   protected ObjectReader _new(ObjectReader var1, DeserializationConfig var2) {
      return new ObjectReader(var1, var2);
   }

   protected ObjectReader _new(ObjectReader var1, DeserializationConfig var2, JavaType var3, JsonDeserializer var4, Object var5, FormatSchema var6, InjectableValues var7, DataFormatReaders var8) {
      return new ObjectReader(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   protected MappingIterator _newIterator(JsonParser var1, DeserializationContext var2, JsonDeserializer var3, boolean var4) {
      return new MappingIterator(this._valueType, var1, var2, var3, var4, this._valueToUpdate);
   }

   protected JsonToken _initForReading(DeserializationContext var1, JsonParser var2) throws IOException {
      if (this._schema != null) {
         var2.setSchema(this._schema);
      }

      this._config.initialize(var2);
      JsonToken var3 = var2.getCurrentToken();
      if (var3 == null) {
         var3 = var2.nextToken();
         if (var3 == null) {
            var1.reportInputMismatch(this._valueType, "No content to map due to end-of-input");
         }
      }

      return var3;
   }

   protected void _initForMultiRead(DeserializationContext var1, JsonParser var2) throws IOException {
      if (this._schema != null) {
         var2.setSchema(this._schema);
      }

      this._config.initialize(var2);
   }

   public ObjectReader with(DeserializationFeature var1) {
      return this._with(this._config.with(var1));
   }

   public ObjectReader with(DeserializationFeature var1, DeserializationFeature... var2) {
      return this._with(this._config.with(var1, var2));
   }

   public ObjectReader withFeatures(DeserializationFeature... var1) {
      return this._with(this._config.withFeatures(var1));
   }

   public ObjectReader without(DeserializationFeature var1) {
      return this._with(this._config.without(var1));
   }

   public ObjectReader without(DeserializationFeature var1, DeserializationFeature... var2) {
      return this._with(this._config.without(var1, var2));
   }

   public ObjectReader withoutFeatures(DeserializationFeature... var1) {
      return this._with(this._config.withoutFeatures(var1));
   }

   public ObjectReader with(JsonParser$Feature var1) {
      return this._with(this._config.with(var1));
   }

   public ObjectReader withFeatures(JsonParser$Feature... var1) {
      return this._with(this._config.withFeatures(var1));
   }

   public ObjectReader without(JsonParser$Feature var1) {
      return this._with(this._config.without(var1));
   }

   public ObjectReader withoutFeatures(JsonParser$Feature... var1) {
      return this._with(this._config.withoutFeatures(var1));
   }

   public ObjectReader with(FormatFeature var1) {
      return this._with(this._config.with(var1));
   }

   public ObjectReader withFeatures(FormatFeature... var1) {
      return this._with(this._config.withFeatures(var1));
   }

   public ObjectReader without(FormatFeature var1) {
      return this._with(this._config.without(var1));
   }

   public ObjectReader withoutFeatures(FormatFeature... var1) {
      return this._with(this._config.withoutFeatures(var1));
   }

   public ObjectReader at(String var1) {
      return new ObjectReader(this, new JsonPointerBasedFilter(var1));
   }

   public ObjectReader at(JsonPointer var1) {
      return new ObjectReader(this, new JsonPointerBasedFilter(var1));
   }

   public ObjectReader with(DeserializationConfig var1) {
      return this._with(var1);
   }

   public ObjectReader with(InjectableValues var1) {
      return this._injectableValues == var1 ? this : this._new(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, this._schema, var1, this._dataFormatReaders);
   }

   public ObjectReader with(JsonNodeFactory var1) {
      return this._with(this._config.with(var1));
   }

   public ObjectReader with(JsonFactory var1) {
      if (var1 == this._parserFactory) {
         return this;
      } else {
         ObjectReader var2 = this._new(this, var1);
         if (var1.getCodec() == null) {
            var1.setCodec(var2);
         }

         return var2;
      }
   }

   public ObjectReader withRootName(String var1) {
      return this._with((DeserializationConfig)this._config.withRootName((String)var1));
   }

   public ObjectReader withRootName(PropertyName var1) {
      return this._with(this._config.withRootName(var1));
   }

   public ObjectReader withoutRootName() {
      return this._with(this._config.withRootName(PropertyName.NO_NAME));
   }

   public ObjectReader with(FormatSchema var1) {
      if (this._schema == var1) {
         return this;
      } else {
         this._verifySchemaType(var1);
         return this._new(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, var1, this._injectableValues, this._dataFormatReaders);
      }
   }

   public ObjectReader forType(JavaType var1) {
      if (var1 != null && var1.equals(this._valueType)) {
         return this;
      } else {
         JsonDeserializer var2 = this._prefetchRootDeserializer(var1);
         DataFormatReaders var3 = this._dataFormatReaders;
         if (var3 != null) {
            var3 = var3.withType(var1);
         }

         return this._new(this, this._config, var1, var2, this._valueToUpdate, this._schema, this._injectableValues, var3);
      }
   }

   public ObjectReader forType(Class var1) {
      return this.forType(this._config.constructType(var1));
   }

   public ObjectReader forType(TypeReference var1) {
      return this.forType(this._config.getTypeFactory().constructType(var1.getType()));
   }

   /** @deprecated */
   @Deprecated
   public ObjectReader withType(JavaType var1) {
      return this.forType(var1);
   }

   /** @deprecated */
   @Deprecated
   public ObjectReader withType(Class var1) {
      return this.forType(this._config.constructType(var1));
   }

   /** @deprecated */
   @Deprecated
   public ObjectReader withType(Type var1) {
      return this.forType(this._config.getTypeFactory().constructType(var1));
   }

   /** @deprecated */
   @Deprecated
   public ObjectReader withType(TypeReference var1) {
      return this.forType(this._config.getTypeFactory().constructType(var1.getType()));
   }

   public ObjectReader withValueToUpdate(Object var1) {
      if (var1 == this._valueToUpdate) {
         return this;
      } else if (var1 == null) {
         return this._new(this, this._config, this._valueType, this._rootDeserializer, (Object)null, this._schema, this._injectableValues, this._dataFormatReaders);
      } else {
         JavaType var2;
         if (this._valueType == null) {
            var2 = this._config.constructType(var1.getClass());
         } else {
            var2 = this._valueType;
         }

         return this._new(this, this._config, var2, this._rootDeserializer, var1, this._schema, this._injectableValues, this._dataFormatReaders);
      }
   }

   public ObjectReader withView(Class var1) {
      return this._with(this._config.withView(var1));
   }

   public ObjectReader with(Locale var1) {
      return this._with((DeserializationConfig)this._config.with((Locale)var1));
   }

   public ObjectReader with(TimeZone var1) {
      return this._with((DeserializationConfig)this._config.with((TimeZone)var1));
   }

   public ObjectReader withHandler(DeserializationProblemHandler var1) {
      return this._with(this._config.withHandler(var1));
   }

   public ObjectReader with(Base64Variant var1) {
      return this._with((DeserializationConfig)this._config.with((Base64Variant)var1));
   }

   public ObjectReader withFormatDetection(ObjectReader... var1) {
      return this.withFormatDetection(new DataFormatReaders(var1));
   }

   public ObjectReader withFormatDetection(DataFormatReaders var1) {
      return this._new(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, this._schema, this._injectableValues, var1);
   }

   public ObjectReader with(ContextAttributes var1) {
      return this._with(this._config.with(var1));
   }

   public ObjectReader withAttributes(Map var1) {
      return this._with((DeserializationConfig)this._config.withAttributes(var1));
   }

   public ObjectReader withAttribute(Object var1, Object var2) {
      return this._with((DeserializationConfig)this._config.withAttribute(var1, var2));
   }

   public ObjectReader withoutAttribute(Object var1) {
      return this._with((DeserializationConfig)this._config.withoutAttribute(var1));
   }

   protected ObjectReader _with(DeserializationConfig var1) {
      if (var1 == this._config) {
         return this;
      } else {
         ObjectReader var2 = this._new(this, var1);
         if (this._dataFormatReaders != null) {
            var2 = var2.withFormatDetection(this._dataFormatReaders.with(var1));
         }

         return var2;
      }
   }

   public boolean isEnabled(DeserializationFeature var1) {
      return this._config.isEnabled(var1);
   }

   public boolean isEnabled(MapperFeature var1) {
      return this._config.isEnabled(var1);
   }

   public boolean isEnabled(JsonParser$Feature var1) {
      return this._parserFactory.isEnabled(var1);
   }

   public DeserializationConfig getConfig() {
      return this._config;
   }

   public JsonFactory getFactory() {
      return this._parserFactory;
   }

   public TypeFactory getTypeFactory() {
      return this._config.getTypeFactory();
   }

   public ContextAttributes getAttributes() {
      return this._config.getAttributes();
   }

   public InjectableValues getInjectableValues() {
      return this._injectableValues;
   }

   public Object readValue(JsonParser var1) throws IOException {
      return this._bind(var1, this._valueToUpdate);
   }

   public Object readValue(JsonParser var1, Class var2) throws IOException {
      return this.forType(var2).readValue(var1);
   }

   public Object readValue(JsonParser var1, TypeReference var2) throws IOException {
      return this.forType(var2).readValue(var1);
   }

   public Object readValue(JsonParser var1, ResolvedType var2) throws IOException {
      return this.forType((JavaType)var2).readValue(var1);
   }

   public Object readValue(JsonParser var1, JavaType var2) throws IOException {
      return this.forType(var2).readValue(var1);
   }

   public Iterator readValues(JsonParser var1, Class var2) throws IOException {
      return this.forType(var2).readValues(var1);
   }

   public Iterator readValues(JsonParser var1, TypeReference var2) throws IOException {
      return this.forType(var2).readValues(var1);
   }

   public Iterator readValues(JsonParser var1, ResolvedType var2) throws IOException {
      return this.readValues(var1, (JavaType)var2);
   }

   public Iterator readValues(JsonParser var1, JavaType var2) throws IOException {
      return this.forType(var2).readValues(var1);
   }

   public JsonNode createArrayNode() {
      return this._config.getNodeFactory().arrayNode();
   }

   public JsonNode createObjectNode() {
      return this._config.getNodeFactory().objectNode();
   }

   public JsonParser treeAsTokens(TreeNode var1) {
      ObjectReader var2 = this.withValueToUpdate((Object)null);
      return new TreeTraversingParser((JsonNode)var1, var2);
   }

   public TreeNode readTree(JsonParser var1) throws IOException {
      return this._bindAsTree(var1);
   }

   public void writeTree(JsonGenerator var1, TreeNode var2) {
      throw new UnsupportedOperationException();
   }

   public Object readValue(InputStream var1) throws IOException {
      return this._dataFormatReaders != null ? this._detectBindAndClose(this._dataFormatReaders.findFormat(var1), false) : this._bindAndClose(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public Object readValue(Reader var1) throws IOException {
      if (this._dataFormatReaders != null) {
         this._reportUndetectableSource(var1);
      }

      return this._bindAndClose(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public Object readValue(String var1) throws IOException {
      if (this._dataFormatReaders != null) {
         this._reportUndetectableSource(var1);
      }

      return this._bindAndClose(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public Object readValue(byte[] var1) throws IOException {
      return this._dataFormatReaders != null ? this._detectBindAndClose(var1, 0, var1.length) : this._bindAndClose(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public Object readValue(byte[] var1, int var2, int var3) throws IOException {
      return this._dataFormatReaders != null ? this._detectBindAndClose(var1, var2, var3) : this._bindAndClose(this._considerFilter(this._parserFactory.createParser(var1, var2, var3), false));
   }

   public Object readValue(File var1) throws IOException {
      return this._dataFormatReaders != null ? this._detectBindAndClose(this._dataFormatReaders.findFormat(this._inputStream(var1)), true) : this._bindAndClose(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public Object readValue(URL var1) throws IOException {
      return this._dataFormatReaders != null ? this._detectBindAndClose(this._dataFormatReaders.findFormat(this._inputStream(var1)), true) : this._bindAndClose(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public Object readValue(JsonNode var1) throws IOException {
      if (this._dataFormatReaders != null) {
         this._reportUndetectableSource(var1);
      }

      return this._bindAndClose(this._considerFilter(this.treeAsTokens(var1), false));
   }

   public Object readValue(DataInput var1) throws IOException {
      if (this._dataFormatReaders != null) {
         this._reportUndetectableSource(var1);
      }

      return this._bindAndClose(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public JsonNode readTree(InputStream var1) throws IOException {
      return this._dataFormatReaders != null ? this._detectBindAndCloseAsTree(var1) : this._bindAndCloseAsTree(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public JsonNode readTree(Reader var1) throws IOException {
      if (this._dataFormatReaders != null) {
         this._reportUndetectableSource(var1);
      }

      return this._bindAndCloseAsTree(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public JsonNode readTree(String var1) throws IOException {
      if (this._dataFormatReaders != null) {
         this._reportUndetectableSource(var1);
      }

      return this._bindAndCloseAsTree(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public JsonNode readTree(DataInput var1) throws IOException {
      if (this._dataFormatReaders != null) {
         this._reportUndetectableSource(var1);
      }

      return this._bindAndCloseAsTree(this._considerFilter(this._parserFactory.createParser(var1), false));
   }

   public MappingIterator readValues(JsonParser var1) throws IOException {
      DefaultDeserializationContext var2 = this.createDeserializationContext(var1);
      return this._newIterator(var1, var2, this._findRootDeserializer(var2), false);
   }

   public MappingIterator readValues(InputStream var1) throws IOException {
      return this._dataFormatReaders != null ? this._detectBindAndReadValues(this._dataFormatReaders.findFormat(var1), false) : this._bindAndReadValues(this._considerFilter(this._parserFactory.createParser(var1), true));
   }

   public MappingIterator readValues(Reader var1) throws IOException {
      if (this._dataFormatReaders != null) {
         this._reportUndetectableSource(var1);
      }

      JsonParser var2 = this._considerFilter(this._parserFactory.createParser(var1), true);
      DefaultDeserializationContext var3 = this.createDeserializationContext(var2);
      this._initForMultiRead(var3, var2);
      var2.nextToken();
      return this._newIterator(var2, var3, this._findRootDeserializer(var3), true);
   }

   public MappingIterator readValues(String var1) throws IOException {
      if (this._dataFormatReaders != null) {
         this._reportUndetectableSource(var1);
      }

      JsonParser var2 = this._considerFilter(this._parserFactory.createParser(var1), true);
      DefaultDeserializationContext var3 = this.createDeserializationContext(var2);
      this._initForMultiRead(var3, var2);
      var2.nextToken();
      return this._newIterator(var2, var3, this._findRootDeserializer(var3), true);
   }

   public MappingIterator readValues(byte[] var1, int var2, int var3) throws IOException {
      return this._dataFormatReaders != null ? this._detectBindAndReadValues(this._dataFormatReaders.findFormat(var1, var2, var3), false) : this._bindAndReadValues(this._considerFilter(this._parserFactory.createParser(var1, var2, var3), true));
   }

   public final MappingIterator readValues(byte[] var1) throws IOException {
      return this.readValues(var1, 0, var1.length);
   }

   public MappingIterator readValues(File var1) throws IOException {
      return this._dataFormatReaders != null ? this._detectBindAndReadValues(this._dataFormatReaders.findFormat(this._inputStream(var1)), false) : this._bindAndReadValues(this._considerFilter(this._parserFactory.createParser(var1), true));
   }

   public MappingIterator readValues(URL var1) throws IOException {
      return this._dataFormatReaders != null ? this._detectBindAndReadValues(this._dataFormatReaders.findFormat(this._inputStream(var1)), true) : this._bindAndReadValues(this._considerFilter(this._parserFactory.createParser(var1), true));
   }

   public MappingIterator readValues(DataInput var1) throws IOException {
      if (this._dataFormatReaders != null) {
         this._reportUndetectableSource(var1);
      }

      return this._bindAndReadValues(this._considerFilter(this._parserFactory.createParser(var1), true));
   }

   public Object treeToValue(TreeNode var1, Class var2) throws JsonProcessingException {
      Object var10000;
      try {
         var10000 = this.readValue(this.treeAsTokens(var1), var2);
      } catch (JsonProcessingException var4) {
         throw var4;
      } catch (IOException var5) {
         throw JsonMappingException.fromUnexpectedIOE(var5);
      }

      return var10000;
   }

   public void writeValue(JsonGenerator var1, Object var2) throws IOException {
      throw new UnsupportedOperationException("Not implemented for ObjectReader");
   }

   protected Object _bind(JsonParser var1, Object var2) throws IOException {
      DefaultDeserializationContext var4 = this.createDeserializationContext(var1);
      JsonToken var5 = this._initForReading(var4, var1);
      Object var3;
      if (var5 == JsonToken.VALUE_NULL) {
         if (var2 == null) {
            var3 = this._findRootDeserializer(var4).getNullValue(var4);
         } else {
            var3 = var2;
         }
      } else if (var5 != JsonToken.END_ARRAY && var5 != JsonToken.END_OBJECT) {
         JsonDeserializer var6 = this._findRootDeserializer(var4);
         if (this._unwrapRoot) {
            var3 = this._unwrapAndDeserialize(var1, var4, this._valueType, var6);
         } else if (var2 == null) {
            var3 = var6.deserialize(var1, var4);
         } else {
            var3 = var6.deserialize(var1, var4, var2);
         }
      } else {
         var3 = var2;
      }

      var1.clearCurrentToken();
      if (this._config.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
         this._verifyNoTrailingTokens(var1, var4, this._valueType);
      }

      return var3;
   }

   protected Object _bindAndClose(JsonParser var1) throws IOException {
      JsonParser var2 = var1;
      Throwable var3 = null;
      boolean var15 = false;

      Object var20;
      try {
         var15 = true;
         DefaultDeserializationContext var5 = this.createDeserializationContext(var2);
         JsonToken var6 = this._initForReading(var5, var2);
         Object var4;
         if (var6 == JsonToken.VALUE_NULL) {
            if (this._valueToUpdate == null) {
               var4 = this._findRootDeserializer(var5).getNullValue(var5);
            } else {
               var4 = this._valueToUpdate;
            }
         } else if (var6 != JsonToken.END_ARRAY && var6 != JsonToken.END_OBJECT) {
            JsonDeserializer var7 = this._findRootDeserializer(var5);
            if (this._unwrapRoot) {
               var4 = this._unwrapAndDeserialize(var2, var5, this._valueType, var7);
            } else if (this._valueToUpdate == null) {
               var4 = var7.deserialize(var2, var5);
            } else {
               var7.deserialize(var2, var5, this._valueToUpdate);
               var4 = this._valueToUpdate;
            }
         } else {
            var4 = this._valueToUpdate;
         }

         if (this._config.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
            this._verifyNoTrailingTokens(var2, var5, this._valueType);
         }

         var20 = var4;
         var15 = false;
      } catch (Throwable var18) {
         var3 = var18;
         throw var18;
      } finally {
         if (var15) {
            if (var1 != null) {
               if (var3 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var16) {
                     var3.addSuppressed(var16);
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
            } catch (Throwable var17) {
               var3.addSuppressed(var17);
            }
         } else {
            var1.close();
         }
      }

      return var20;
   }

   protected final JsonNode _bindAndCloseAsTree(JsonParser var1) throws IOException {
      JsonParser var2 = var1;
      Throwable var3 = null;
      boolean var12 = false;

      JsonNode var4;
      try {
         var12 = true;
         var4 = this._bindAsTree(var2);
         var12 = false;
      } catch (Throwable var15) {
         var3 = var15;
         throw var15;
      } finally {
         if (var12) {
            if (var1 != null) {
               if (var3 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var13) {
                     var3.addSuppressed(var13);
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
            } catch (Throwable var14) {
               var3.addSuppressed(var14);
            }
         } else {
            var1.close();
         }
      }

      return var4;
   }

   protected final JsonNode _bindAsTree(JsonParser var1) throws IOException {
      this._config.initialize(var1);
      if (this._schema != null) {
         var1.setSchema(this._schema);
      }

      JsonToken var2 = var1.getCurrentToken();
      if (var2 == null) {
         var2 = var1.nextToken();
         if (var2 == null) {
            return null;
         }
      }

      DefaultDeserializationContext var3 = this.createDeserializationContext(var1);
      if (var2 == JsonToken.VALUE_NULL) {
         return var3.getNodeFactory().nullNode();
      } else {
         JsonDeserializer var4 = this._findTreeDeserializer(var3);
         Object var5;
         if (this._unwrapRoot) {
            var5 = this._unwrapAndDeserialize(var1, var3, JSON_NODE_TYPE, var4);
         } else {
            var5 = var4.deserialize(var1, var3);
            if (this._config.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
               this._verifyNoTrailingTokens(var1, var3, JSON_NODE_TYPE);
            }
         }

         return (JsonNode)var5;
      }
   }

   protected MappingIterator _bindAndReadValues(JsonParser var1) throws IOException {
      DefaultDeserializationContext var2 = this.createDeserializationContext(var1);
      this._initForMultiRead(var2, var1);
      var1.nextToken();
      return this._newIterator(var1, var2, this._findRootDeserializer(var2), true);
   }

   protected Object _unwrapAndDeserialize(JsonParser var1, DeserializationContext var2, JavaType var3, JsonDeserializer var4) throws IOException {
      PropertyName var5 = this._config.findRootName(var3);
      String var6 = var5.getSimpleName();
      if (var1.getCurrentToken() != JsonToken.START_OBJECT) {
         var2.reportWrongTokenException(var3, JsonToken.START_OBJECT, "Current token not START_OBJECT (needed to unwrap root name '%s'), but %s", var6, var1.getCurrentToken());
      }

      if (var1.nextToken() != JsonToken.FIELD_NAME) {
         var2.reportWrongTokenException(var3, JsonToken.FIELD_NAME, "Current token not FIELD_NAME (to contain expected root name '%s'), but %s", var6, var1.getCurrentToken());
      }

      String var7 = var1.getCurrentName();
      if (!var6.equals(var7)) {
         var2.reportInputMismatch(var3, "Root name '%s' does not match expected ('%s') for type %s", var7, var6, var3);
      }

      var1.nextToken();
      Object var8;
      if (this._valueToUpdate == null) {
         var8 = var4.deserialize(var1, var2);
      } else {
         var4.deserialize(var1, var2, this._valueToUpdate);
         var8 = this._valueToUpdate;
      }

      if (var1.nextToken() != JsonToken.END_OBJECT) {
         var2.reportWrongTokenException(var3, JsonToken.END_OBJECT, "Current token not END_OBJECT (to match wrapper object with root name '%s'), but %s", var6, var1.getCurrentToken());
      }

      if (this._config.isEnabled(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)) {
         this._verifyNoTrailingTokens(var1, var2, this._valueType);
      }

      return var8;
   }

   protected JsonParser _considerFilter(JsonParser var1, boolean var2) {
      return (JsonParser)(this._filter != null && !FilteringParserDelegate.class.isInstance(var1) ? new FilteringParserDelegate(var1, this._filter, false, var2) : var1);
   }

   protected final void _verifyNoTrailingTokens(JsonParser var1, DeserializationContext var2, JavaType var3) throws IOException {
      JsonToken var4 = var1.nextToken();
      if (var4 != null) {
         Class var5 = ClassUtil.rawClass(var3);
         if (var5 == null && this._valueToUpdate != null) {
            var5 = this._valueToUpdate.getClass();
         }

         var2.reportTrailingTokens(var5, var1, var4);
      }

   }

   protected Object _detectBindAndClose(byte[] var1, int var2, int var3) throws IOException {
      DataFormatReaders$Match var4 = this._dataFormatReaders.findFormat(var1, var2, var3);
      if (!var4.hasMatch()) {
         this._reportUnkownFormat(this._dataFormatReaders, var4);
      }

      JsonParser var5 = var4.createParserWithMatch();
      return var4.getReader()._bindAndClose(var5);
   }

   protected Object _detectBindAndClose(DataFormatReaders$Match var1, boolean var2) throws IOException {
      if (!var1.hasMatch()) {
         this._reportUnkownFormat(this._dataFormatReaders, var1);
      }

      JsonParser var3 = var1.createParserWithMatch();
      if (var2) {
         var3.enable(JsonParser$Feature.AUTO_CLOSE_SOURCE);
      }

      return var1.getReader()._bindAndClose(var3);
   }

   protected MappingIterator _detectBindAndReadValues(DataFormatReaders$Match var1, boolean var2) throws IOException {
      if (!var1.hasMatch()) {
         this._reportUnkownFormat(this._dataFormatReaders, var1);
      }

      JsonParser var3 = var1.createParserWithMatch();
      if (var2) {
         var3.enable(JsonParser$Feature.AUTO_CLOSE_SOURCE);
      }

      return var1.getReader()._bindAndReadValues(var3);
   }

   protected JsonNode _detectBindAndCloseAsTree(InputStream var1) throws IOException {
      DataFormatReaders$Match var2 = this._dataFormatReaders.findFormat(var1);
      if (!var2.hasMatch()) {
         this._reportUnkownFormat(this._dataFormatReaders, var2);
      }

      JsonParser var3 = var2.createParserWithMatch();
      var3.enable(JsonParser$Feature.AUTO_CLOSE_SOURCE);
      return var2.getReader()._bindAndCloseAsTree(var3);
   }

   protected void _reportUnkownFormat(DataFormatReaders var1, DataFormatReaders$Match var2) throws JsonProcessingException {
      throw new JsonParseException((JsonParser)null, "Cannot detect format from input, does not look like any of detectable formats " + var1.toString());
   }

   protected void _verifySchemaType(FormatSchema var1) {
      if (var1 != null && !this._parserFactory.canUseSchema(var1)) {
         throw new IllegalArgumentException("Cannot use FormatSchema of type " + var1.getClass().getName() + " for format " + this._parserFactory.getFormatName());
      }
   }

   protected DefaultDeserializationContext createDeserializationContext(JsonParser var1) {
      return this._context.createInstance(this._config, var1, this._injectableValues);
   }

   protected InputStream _inputStream(URL var1) throws IOException {
      return var1.openStream();
   }

   protected InputStream _inputStream(File var1) throws IOException {
      return new FileInputStream(var1);
   }

   protected void _reportUndetectableSource(Object var1) throws JsonProcessingException {
      throw new JsonParseException((JsonParser)null, "Cannot use source of type " + var1.getClass().getName() + " with format auto-detection: must be byte- not char-based");
   }

   protected JsonDeserializer _findRootDeserializer(DeserializationContext var1) throws JsonMappingException {
      if (this._rootDeserializer != null) {
         return this._rootDeserializer;
      } else {
         JavaType var2 = this._valueType;
         if (var2 == null) {
            var1.reportBadDefinition((JavaType)null, "No value type configured for ObjectReader");
         }

         JsonDeserializer var3 = (JsonDeserializer)this._rootDeserializers.get(var2);
         if (var3 != null) {
            return var3;
         } else {
            var3 = var1.findRootValueDeserializer(var2);
            if (var3 == null) {
               var1.reportBadDefinition(var2, "Cannot find a deserializer for type " + var2);
            }

            this._rootDeserializers.put(var2, var3);
            return var3;
         }
      }
   }

   protected JsonDeserializer _findTreeDeserializer(DeserializationContext var1) throws JsonMappingException {
      JsonDeserializer var2 = (JsonDeserializer)this._rootDeserializers.get(JSON_NODE_TYPE);
      if (var2 == null) {
         var2 = var1.findRootValueDeserializer(JSON_NODE_TYPE);
         if (var2 == null) {
            var1.reportBadDefinition(JSON_NODE_TYPE, "Cannot find a deserializer for type " + JSON_NODE_TYPE);
         }

         this._rootDeserializers.put(JSON_NODE_TYPE, var2);
      }

      return var2;
   }

   protected JsonDeserializer _prefetchRootDeserializer(JavaType var1) {
      if (var1 != null && this._config.isEnabled(DeserializationFeature.EAGER_DESERIALIZER_FETCH)) {
         JsonDeserializer var2 = (JsonDeserializer)this._rootDeserializers.get(var1);
         if (var2 == null) {
            JsonDeserializer var10000;
            try {
               DefaultDeserializationContext var3 = this.createDeserializationContext((JsonParser)null);
               var2 = var3.findRootValueDeserializer(var1);
               if (var2 != null) {
                  this._rootDeserializers.put(var1, var2);
               }

               var10000 = var2;
            } catch (JsonProcessingException var4) {
               return var2;
            }

            return var10000;
         } else {
            return var2;
         }
      } else {
         return null;
      }
   }

   public TreeNode createArrayNode() {
      return this.createArrayNode();
   }

   public TreeNode createObjectNode() {
      return this.createObjectNode();
   }
}
