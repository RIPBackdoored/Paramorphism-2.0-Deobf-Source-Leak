package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.type.*;
import java.text.*;
import com.fasterxml.jackson.databind.ser.*;
import java.util.*;
import com.fasterxml.jackson.databind.type.*;
import java.io.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.ser.impl.*;
import com.fasterxml.jackson.databind.cfg.*;

public class ObjectWriter implements Versioned, Serializable
{
    private static final long serialVersionUID = 1L;
    protected static final PrettyPrinter NULL_PRETTY_PRINTER;
    protected final SerializationConfig _config;
    protected final DefaultSerializerProvider _serializerProvider;
    protected final SerializerFactory _serializerFactory;
    protected final JsonFactory _generatorFactory;
    protected final GeneratorSettings _generatorSettings;
    protected final Prefetch _prefetch;
    
    protected ObjectWriter(final ObjectMapper objectMapper, final SerializationConfig config, JavaType withStaticTyping, final PrettyPrinter prettyPrinter) {
        super();
        this._config = config;
        this._serializerProvider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._generatorFactory = objectMapper._jsonFactory;
        this._generatorSettings = ((prettyPrinter == null) ? GeneratorSettings.empty : new GeneratorSettings(prettyPrinter, null, null, null));
        if (withStaticTyping == null || withStaticTyping.hasRawClass(Object.class)) {
            this._prefetch = Prefetch.empty;
        }
        else {
            withStaticTyping = withStaticTyping.withStaticTyping();
            this._prefetch = Prefetch.empty.forRootType(this, withStaticTyping);
        }
    }
    
    protected ObjectWriter(final ObjectMapper objectMapper, final SerializationConfig config) {
        super();
        this._config = config;
        this._serializerProvider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._generatorFactory = objectMapper._jsonFactory;
        this._generatorSettings = GeneratorSettings.empty;
        this._prefetch = Prefetch.empty;
    }
    
    protected ObjectWriter(final ObjectMapper objectMapper, final SerializationConfig config, final FormatSchema formatSchema) {
        super();
        this._config = config;
        this._serializerProvider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._generatorFactory = objectMapper._jsonFactory;
        this._generatorSettings = ((formatSchema == null) ? GeneratorSettings.empty : new GeneratorSettings(null, formatSchema, null, null));
        this._prefetch = Prefetch.empty;
    }
    
    protected ObjectWriter(final ObjectWriter objectWriter, final SerializationConfig config, final GeneratorSettings generatorSettings, final Prefetch prefetch) {
        super();
        this._config = config;
        this._serializerProvider = objectWriter._serializerProvider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._generatorFactory = objectWriter._generatorFactory;
        this._generatorSettings = generatorSettings;
        this._prefetch = prefetch;
    }
    
    protected ObjectWriter(final ObjectWriter objectWriter, final SerializationConfig config) {
        super();
        this._config = config;
        this._serializerProvider = objectWriter._serializerProvider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._generatorFactory = objectWriter._generatorFactory;
        this._generatorSettings = objectWriter._generatorSettings;
        this._prefetch = objectWriter._prefetch;
    }
    
    protected ObjectWriter(final ObjectWriter objectWriter, final JsonFactory generatorFactory) {
        super();
        this._config = ((MapperConfigBase<CFG, SerializationConfig>)objectWriter._config).with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, generatorFactory.requiresPropertyOrdering());
        this._serializerProvider = objectWriter._serializerProvider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._generatorFactory = generatorFactory;
        this._generatorSettings = objectWriter._generatorSettings;
        this._prefetch = objectWriter._prefetch;
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    protected ObjectWriter _new(final ObjectWriter objectWriter, final JsonFactory jsonFactory) {
        return new ObjectWriter(objectWriter, jsonFactory);
    }
    
    protected ObjectWriter _new(final ObjectWriter objectWriter, final SerializationConfig serializationConfig) {
        if (serializationConfig == this._config) {
            return this;
        }
        return new ObjectWriter(objectWriter, serializationConfig);
    }
    
    protected ObjectWriter _new(final GeneratorSettings generatorSettings, final Prefetch prefetch) {
        if (this._generatorSettings == generatorSettings && this._prefetch == prefetch) {
            return this;
        }
        return new ObjectWriter(this, this._config, generatorSettings, prefetch);
    }
    
    protected SequenceWriter _newSequenceWriter(final boolean b, final JsonGenerator jsonGenerator, final boolean b2) throws IOException {
        this._configureGenerator(jsonGenerator);
        return new SequenceWriter(this._serializerProvider(), jsonGenerator, b2, this._prefetch).init(b);
    }
    
    public ObjectWriter with(final SerializationFeature serializationFeature) {
        return this._new(this, this._config.with(serializationFeature));
    }
    
    public ObjectWriter with(final SerializationFeature serializationFeature, final SerializationFeature... array) {
        return this._new(this, this._config.with(serializationFeature, array));
    }
    
    public ObjectWriter withFeatures(final SerializationFeature... array) {
        return this._new(this, this._config.withFeatures(array));
    }
    
    public ObjectWriter without(final SerializationFeature serializationFeature) {
        return this._new(this, this._config.without(serializationFeature));
    }
    
    public ObjectWriter without(final SerializationFeature serializationFeature, final SerializationFeature... array) {
        return this._new(this, this._config.without(serializationFeature, array));
    }
    
    public ObjectWriter withoutFeatures(final SerializationFeature... array) {
        return this._new(this, this._config.withoutFeatures(array));
    }
    
    public ObjectWriter with(final JsonGenerator.Feature feature) {
        return this._new(this, this._config.with(feature));
    }
    
    public ObjectWriter withFeatures(final JsonGenerator.Feature... array) {
        return this._new(this, this._config.withFeatures(array));
    }
    
    public ObjectWriter without(final JsonGenerator.Feature feature) {
        return this._new(this, this._config.without(feature));
    }
    
    public ObjectWriter withoutFeatures(final JsonGenerator.Feature... array) {
        return this._new(this, this._config.withoutFeatures(array));
    }
    
    public ObjectWriter with(final FormatFeature formatFeature) {
        return this._new(this, this._config.with(formatFeature));
    }
    
    public ObjectWriter withFeatures(final FormatFeature... array) {
        return this._new(this, this._config.withFeatures(array));
    }
    
    public ObjectWriter without(final FormatFeature formatFeature) {
        return this._new(this, this._config.without(formatFeature));
    }
    
    public ObjectWriter withoutFeatures(final FormatFeature... array) {
        return this._new(this, this._config.withoutFeatures(array));
    }
    
    public ObjectWriter forType(final JavaType javaType) {
        return this._new(this._generatorSettings, this._prefetch.forRootType(this, javaType));
    }
    
    public ObjectWriter forType(final Class<?> clazz) {
        if (clazz == Object.class) {
            return this.forType((JavaType)null);
        }
        return this.forType(this._config.constructType(clazz));
    }
    
    public ObjectWriter forType(final TypeReference<?> typeReference) {
        return this.forType(this._config.getTypeFactory().constructType(typeReference.getType()));
    }
    
    @Deprecated
    public ObjectWriter withType(final JavaType javaType) {
        return this.forType(javaType);
    }
    
    @Deprecated
    public ObjectWriter withType(final Class<?> clazz) {
        return this.forType(clazz);
    }
    
    @Deprecated
    public ObjectWriter withType(final TypeReference<?> typeReference) {
        return this.forType(typeReference);
    }
    
    public ObjectWriter with(final DateFormat dateFormat) {
        return this._new(this, this._config.with(dateFormat));
    }
    
    public ObjectWriter withDefaultPrettyPrinter() {
        return this.with(this._config.getDefaultPrettyPrinter());
    }
    
    public ObjectWriter with(final FilterProvider filterProvider) {
        if (filterProvider == this._config.getFilterProvider()) {
            return this;
        }
        return this._new(this, this._config.withFilters(filterProvider));
    }
    
    public ObjectWriter with(final PrettyPrinter prettyPrinter) {
        return this._new(this._generatorSettings.with(prettyPrinter), this._prefetch);
    }
    
    public ObjectWriter withRootName(final String s) {
        return this._new(this, ((MapperConfigBase<CFG, SerializationConfig>)this._config).withRootName(s));
    }
    
    public ObjectWriter withRootName(final PropertyName propertyName) {
        return this._new(this, this._config.withRootName(propertyName));
    }
    
    public ObjectWriter withoutRootName() {
        return this._new(this, this._config.withRootName(PropertyName.NO_NAME));
    }
    
    public ObjectWriter with(final FormatSchema formatSchema) {
        this._verifySchemaType(formatSchema);
        return this._new(this._generatorSettings.with(formatSchema), this._prefetch);
    }
    
    @Deprecated
    public ObjectWriter withSchema(final FormatSchema formatSchema) {
        return this.with(formatSchema);
    }
    
    public ObjectWriter withView(final Class<?> clazz) {
        return this._new(this, this._config.withView(clazz));
    }
    
    public ObjectWriter with(final Locale locale) {
        return this._new(this, ((MapperConfigBase<CFG, SerializationConfig>)this._config).with(locale));
    }
    
    public ObjectWriter with(final TimeZone timeZone) {
        return this._new(this, ((MapperConfigBase<CFG, SerializationConfig>)this._config).with(timeZone));
    }
    
    public ObjectWriter with(final Base64Variant base64Variant) {
        return this._new(this, ((MapperConfigBase<CFG, SerializationConfig>)this._config).with(base64Variant));
    }
    
    public ObjectWriter with(final CharacterEscapes characterEscapes) {
        return this._new(this._generatorSettings.with(characterEscapes), this._prefetch);
    }
    
    public ObjectWriter with(final JsonFactory jsonFactory) {
        return (jsonFactory == this._generatorFactory) ? this : this._new(this, jsonFactory);
    }
    
    public ObjectWriter with(final ContextAttributes contextAttributes) {
        return this._new(this, this._config.with(contextAttributes));
    }
    
    public ObjectWriter withAttributes(final Map<?, ?> map) {
        return this._new(this, ((MapperConfigBase<CFG, SerializationConfig>)this._config).withAttributes(map));
    }
    
    public ObjectWriter withAttribute(final Object o, final Object o2) {
        return this._new(this, ((MapperConfigBase<CFG, SerializationConfig>)this._config).withAttribute(o, o2));
    }
    
    public ObjectWriter withoutAttribute(final Object o) {
        return this._new(this, ((MapperConfigBase<CFG, SerializationConfig>)this._config).withoutAttribute(o));
    }
    
    public ObjectWriter withRootValueSeparator(final String s) {
        return this._new(this._generatorSettings.withRootValueSeparator(s), this._prefetch);
    }
    
    public ObjectWriter withRootValueSeparator(final SerializableString serializableString) {
        return this._new(this._generatorSettings.withRootValueSeparator(serializableString), this._prefetch);
    }
    
    public SequenceWriter writeValues(final File file) throws IOException {
        return this._newSequenceWriter(false, this._generatorFactory.createGenerator(file, JsonEncoding.UTF8), true);
    }
    
    public SequenceWriter writeValues(final JsonGenerator jsonGenerator) throws IOException {
        this._configureGenerator(jsonGenerator);
        return this._newSequenceWriter(false, jsonGenerator, false);
    }
    
    public SequenceWriter writeValues(final Writer writer) throws IOException {
        return this._newSequenceWriter(false, this._generatorFactory.createGenerator(writer), true);
    }
    
    public SequenceWriter writeValues(final OutputStream outputStream) throws IOException {
        return this._newSequenceWriter(false, this._generatorFactory.createGenerator(outputStream, JsonEncoding.UTF8), true);
    }
    
    public SequenceWriter writeValues(final DataOutput dataOutput) throws IOException {
        return this._newSequenceWriter(false, this._generatorFactory.createGenerator(dataOutput), true);
    }
    
    public SequenceWriter writeValuesAsArray(final File file) throws IOException {
        return this._newSequenceWriter(true, this._generatorFactory.createGenerator(file, JsonEncoding.UTF8), true);
    }
    
    public SequenceWriter writeValuesAsArray(final JsonGenerator jsonGenerator) throws IOException {
        return this._newSequenceWriter(true, jsonGenerator, false);
    }
    
    public SequenceWriter writeValuesAsArray(final Writer writer) throws IOException {
        return this._newSequenceWriter(true, this._generatorFactory.createGenerator(writer), true);
    }
    
    public SequenceWriter writeValuesAsArray(final OutputStream outputStream) throws IOException {
        return this._newSequenceWriter(true, this._generatorFactory.createGenerator(outputStream, JsonEncoding.UTF8), true);
    }
    
    public SequenceWriter writeValuesAsArray(final DataOutput dataOutput) throws IOException {
        return this._newSequenceWriter(true, this._generatorFactory.createGenerator(dataOutput), true);
    }
    
    public boolean isEnabled(final SerializationFeature serializationFeature) {
        return this._config.isEnabled(serializationFeature);
    }
    
    public boolean isEnabled(final MapperFeature mapperFeature) {
        return this._config.isEnabled(mapperFeature);
    }
    
    @Deprecated
    public boolean isEnabled(final JsonParser.Feature feature) {
        return this._generatorFactory.isEnabled(feature);
    }
    
    public boolean isEnabled(final JsonGenerator.Feature feature) {
        return this._generatorFactory.isEnabled(feature);
    }
    
    public SerializationConfig getConfig() {
        return this._config;
    }
    
    public JsonFactory getFactory() {
        return this._generatorFactory;
    }
    
    public TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }
    
    public boolean hasPrefetchedSerializer() {
        return this._prefetch.hasSerializer();
    }
    
    public ContextAttributes getAttributes() {
        return this._config.getAttributes();
    }
    
    public void writeValue(final JsonGenerator jsonGenerator, final Object o) throws IOException {
        this._configureGenerator(jsonGenerator);
        if (this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && o instanceof Closeable) {
            final Closeable closeable = (Closeable)o;
            try {
                this._prefetch.serialize(jsonGenerator, o, this._serializerProvider());
                if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                    jsonGenerator.flush();
                }
            }
            catch (Exception ex) {
                ClassUtil.closeOnFailAndThrowAsIOE(null, closeable, ex);
                return;
            }
            closeable.close();
        }
        else {
            this._prefetch.serialize(jsonGenerator, o, this._serializerProvider());
            if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                jsonGenerator.flush();
            }
        }
    }
    
    public void writeValue(final File file, final Object o) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._generatorFactory.createGenerator(file, JsonEncoding.UTF8), o);
    }
    
    public void writeValue(final OutputStream outputStream, final Object o) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._generatorFactory.createGenerator(outputStream, JsonEncoding.UTF8), o);
    }
    
    public void writeValue(final Writer writer, final Object o) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._generatorFactory.createGenerator(writer), o);
    }
    
    public void writeValue(final DataOutput dataOutput, final Object o) throws IOException {
        this._configAndWriteValue(this._generatorFactory.createGenerator(dataOutput), o);
    }
    
    public String writeValueAsString(final Object o) throws JsonProcessingException {
        final SegmentedStringWriter segmentedStringWriter = new SegmentedStringWriter(this._generatorFactory._getBufferRecycler());
        try {
            this._configAndWriteValue(this._generatorFactory.createGenerator(segmentedStringWriter), o);
        }
        catch (JsonProcessingException ex) {
            throw ex;
        }
        catch (IOException ex2) {
            throw JsonMappingException.fromUnexpectedIOE(ex2);
        }
        return segmentedStringWriter.getAndClear();
    }
    
    public byte[] writeValueAsBytes(final Object o) throws JsonProcessingException {
        final ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(this._generatorFactory._getBufferRecycler());
        try {
            this._configAndWriteValue(this._generatorFactory.createGenerator(byteArrayBuilder, JsonEncoding.UTF8), o);
        }
        catch (JsonProcessingException ex) {
            throw ex;
        }
        catch (IOException ex2) {
            throw JsonMappingException.fromUnexpectedIOE(ex2);
        }
        final byte[] byteArray = byteArrayBuilder.toByteArray();
        byteArrayBuilder.release();
        return byteArray;
    }
    
    public void acceptJsonFormatVisitor(final JavaType javaType, final JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        if (javaType == null) {
            throw new IllegalArgumentException("type must be provided");
        }
        this._serializerProvider().acceptJsonFormatVisitor(javaType, jsonFormatVisitorWrapper);
    }
    
    public void acceptJsonFormatVisitor(final Class<?> clazz, final JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        this.acceptJsonFormatVisitor(this._config.constructType(clazz), jsonFormatVisitorWrapper);
    }
    
    public boolean canSerialize(final Class<?> clazz) {
        return this._serializerProvider().hasSerializerFor(clazz, null);
    }
    
    public boolean canSerialize(final Class<?> clazz, final AtomicReference<Throwable> atomicReference) {
        return this._serializerProvider().hasSerializerFor(clazz, atomicReference);
    }
    
    protected DefaultSerializerProvider _serializerProvider() {
        return this._serializerProvider.createInstance(this._config, this._serializerFactory);
    }
    
    protected void _verifySchemaType(final FormatSchema formatSchema) {
        if (formatSchema != null && !this._generatorFactory.canUseSchema(formatSchema)) {
            throw new IllegalArgumentException("Cannot use FormatSchema of type " + formatSchema.getClass().getName() + " for format " + this._generatorFactory.getFormatName());
        }
    }
    
    protected final void _configAndWriteValue(final JsonGenerator jsonGenerator, final Object o) throws IOException {
        this._configureGenerator(jsonGenerator);
        if (this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && o instanceof Closeable) {
            this._writeCloseable(jsonGenerator, o);
            return;
        }
        try {
            this._prefetch.serialize(jsonGenerator, o, this._serializerProvider());
        }
        catch (Exception ex) {
            ClassUtil.closeOnFailAndThrowAsIOE(jsonGenerator, ex);
            return;
        }
        jsonGenerator.close();
    }
    
    private final void _writeCloseable(final JsonGenerator jsonGenerator, final Object o) throws IOException {
        Closeable closeable = (Closeable)o;
        try {
            this._prefetch.serialize(jsonGenerator, o, this._serializerProvider());
            final Closeable closeable2 = closeable;
            closeable = null;
            closeable2.close();
        }
        catch (Exception ex) {
            ClassUtil.closeOnFailAndThrowAsIOE(jsonGenerator, closeable, ex);
            return;
        }
        jsonGenerator.close();
    }
    
    protected final void _configureGenerator(final JsonGenerator jsonGenerator) {
        this._config.initialize(jsonGenerator);
        this._generatorSettings.initialize(jsonGenerator);
    }
    
    static {
        NULL_PRETTY_PRINTER = new MinimalPrettyPrinter();
    }
    
    public static final class GeneratorSettings implements Serializable
    {
        private static final long serialVersionUID = 1L;
        public static final GeneratorSettings empty;
        public final PrettyPrinter prettyPrinter;
        public final FormatSchema schema;
        public final CharacterEscapes characterEscapes;
        public final SerializableString rootValueSeparator;
        
        public GeneratorSettings(final PrettyPrinter prettyPrinter, final FormatSchema schema, final CharacterEscapes characterEscapes, final SerializableString rootValueSeparator) {
            super();
            this.prettyPrinter = prettyPrinter;
            this.schema = schema;
            this.characterEscapes = characterEscapes;
            this.rootValueSeparator = rootValueSeparator;
        }
        
        public GeneratorSettings with(PrettyPrinter null_PRETTY_PRINTER) {
            if (null_PRETTY_PRINTER == null) {
                null_PRETTY_PRINTER = ObjectWriter.NULL_PRETTY_PRINTER;
            }
            return (null_PRETTY_PRINTER == this.prettyPrinter) ? this : new GeneratorSettings(null_PRETTY_PRINTER, this.schema, this.characterEscapes, this.rootValueSeparator);
        }
        
        public GeneratorSettings with(final FormatSchema formatSchema) {
            return (this.schema == formatSchema) ? this : new GeneratorSettings(this.prettyPrinter, formatSchema, this.characterEscapes, this.rootValueSeparator);
        }
        
        public GeneratorSettings with(final CharacterEscapes characterEscapes) {
            return (this.characterEscapes == characterEscapes) ? this : new GeneratorSettings(this.prettyPrinter, this.schema, characterEscapes, this.rootValueSeparator);
        }
        
        public GeneratorSettings withRootValueSeparator(final String s) {
            if (s == null) {
                if (this.rootValueSeparator == null) {
                    return this;
                }
                return new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, null);
            }
            else {
                if (s.equals(this._rootValueSeparatorAsString())) {
                    return this;
                }
                return new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, new SerializedString(s));
            }
        }
        
        public GeneratorSettings withRootValueSeparator(final SerializableString serializableString) {
            if (serializableString == null) {
                if (this.rootValueSeparator == null) {
                    return this;
                }
                return new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, null);
            }
            else {
                if (serializableString.equals(this.rootValueSeparator)) {
                    return this;
                }
                return new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, serializableString);
            }
        }
        
        private final String _rootValueSeparatorAsString() {
            return (this.rootValueSeparator == null) ? null : this.rootValueSeparator.getValue();
        }
        
        public void initialize(final JsonGenerator jsonGenerator) {
            PrettyPrinter prettyPrinter = this.prettyPrinter;
            if (this.prettyPrinter != null) {
                if (prettyPrinter == ObjectWriter.NULL_PRETTY_PRINTER) {
                    jsonGenerator.setPrettyPrinter(null);
                }
                else {
                    if (prettyPrinter instanceof Instantiatable) {
                        prettyPrinter = ((Instantiatable<PrettyPrinter>)prettyPrinter).createInstance();
                    }
                    jsonGenerator.setPrettyPrinter(prettyPrinter);
                }
            }
            if (this.characterEscapes != null) {
                jsonGenerator.setCharacterEscapes(this.characterEscapes);
            }
            if (this.schema != null) {
                jsonGenerator.setSchema(this.schema);
            }
            if (this.rootValueSeparator != null) {
                jsonGenerator.setRootValueSeparator(this.rootValueSeparator);
            }
        }
        
        static {
            empty = new GeneratorSettings(null, null, null, null);
        }
    }
    
    public static final class Prefetch implements Serializable
    {
        private static final long serialVersionUID = 1L;
        public static final Prefetch empty;
        private final JavaType rootType;
        private final JsonSerializer<Object> valueSerializer;
        private final TypeSerializer typeSerializer;
        
        private Prefetch(final JavaType rootType, final JsonSerializer<Object> valueSerializer, final TypeSerializer typeSerializer) {
            super();
            this.rootType = rootType;
            this.valueSerializer = valueSerializer;
            this.typeSerializer = typeSerializer;
        }
        
        public Prefetch forRootType(final ObjectWriter objectWriter, final JavaType javaType) {
            if (javaType == null || javaType.isJavaLangObject()) {
                if (this.rootType == null || this.valueSerializer == null) {
                    return this;
                }
                return new Prefetch(null, null, this.typeSerializer);
            }
            else {
                if (javaType.equals(this.rootType)) {
                    return this;
                }
                if (objectWriter.isEnabled(SerializationFeature.EAGER_SERIALIZER_FETCH)) {
                    final DefaultSerializerProvider serializerProvider = objectWriter._serializerProvider();
                    try {
                        final JsonSerializer<Object> typedValueSerializer = serializerProvider.findTypedValueSerializer(javaType, true, null);
                        if (typedValueSerializer instanceof TypeWrappedSerializer) {
                            return new Prefetch(javaType, null, ((TypeWrappedSerializer)typedValueSerializer).typeSerializer());
                        }
                        return new Prefetch(javaType, typedValueSerializer, null);
                    }
                    catch (JsonProcessingException ex) {}
                }
                return new Prefetch(javaType, null, this.typeSerializer);
            }
        }
        
        public final JsonSerializer<Object> getValueSerializer() {
            return this.valueSerializer;
        }
        
        public final TypeSerializer getTypeSerializer() {
            return this.typeSerializer;
        }
        
        public boolean hasSerializer() {
            return this.valueSerializer != null || this.typeSerializer != null;
        }
        
        public void serialize(final JsonGenerator jsonGenerator, final Object o, final DefaultSerializerProvider defaultSerializerProvider) throws IOException {
            if (this.typeSerializer != null) {
                defaultSerializerProvider.serializePolymorphic(jsonGenerator, o, this.rootType, this.valueSerializer, this.typeSerializer);
            }
            else if (this.valueSerializer != null) {
                defaultSerializerProvider.serializeValue(jsonGenerator, o, this.rootType, this.valueSerializer);
            }
            else if (this.rootType != null) {
                defaultSerializerProvider.serializeValue(jsonGenerator, o, this.rootType);
            }
            else {
                defaultSerializerProvider.serializeValue(jsonGenerator, o);
            }
        }
        
        static {
            empty = new Prefetch(null, null, null);
        }
    }
}
