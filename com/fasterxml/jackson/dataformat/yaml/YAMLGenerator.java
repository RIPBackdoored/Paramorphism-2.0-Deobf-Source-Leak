package com.fasterxml.jackson.dataformat.yaml;

import com.fasterxml.jackson.core.base.*;
import java.util.regex.*;
import com.fasterxml.jackson.core.io.*;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.emitter.*;
import org.yaml.snakeyaml.error.*;
import java.io.*;
import java.util.*;
import java.math.*;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.nodes.*;
import com.fasterxml.jackson.core.*;

public class YAMLGenerator extends GeneratorBase
{
    protected static final long MIN_INT_AS_LONG = -2147483648L;
    protected static final long MAX_INT_AS_LONG = 0L;
    protected static final Pattern PLAIN_NUMBER_P;
    protected static final String TAG_BINARY;
    protected final IOContext _ioContext;
    protected int _formatFeatures;
    protected Writer _writer;
    protected DumperOptions _outputOptions;
    private static final Character STYLE_NAME;
    private static final Character STYLE_SCALAR;
    private static final Character STYLE_QUOTED;
    private static final Character STYLE_LITERAL;
    private static final Character STYLE_BASE64;
    private static final Character STYLE_PLAIN;
    protected Emitter _emitter;
    protected String _objectId;
    protected String _typeId;
    private static final ImplicitTuple NO_TAGS;
    private static final ImplicitTuple EXPLICIT_TAGS;
    
    public YAMLGenerator(final IOContext ioContext, final int n, final int formatFeatures, final ObjectCodec objectCodec, final Writer writer, final DumperOptions.Version version) throws IOException {
        super(n, objectCodec);
        this._ioContext = ioContext;
        this._formatFeatures = formatFeatures;
        this._writer = writer;
        this._outputOptions = this.buildDumperOptions(n, formatFeatures, version);
        (this._emitter = new Emitter(this._writer, this._outputOptions)).emit(new StreamStartEvent(null, null));
        this._emitter.emit(new DocumentStartEvent(null, null, Feature.WRITE_DOC_START_MARKER.enabledIn(formatFeatures), version, Collections.emptyMap()));
    }
    
    protected DumperOptions buildDumperOptions(final int n, final int n2, final DumperOptions.Version version) {
        final DumperOptions dumperOptions = new DumperOptions();
        if (Feature.CANONICAL_OUTPUT.enabledIn(this._formatFeatures)) {
            dumperOptions.setCanonical(true);
        }
        else {
            dumperOptions.setCanonical(false);
            dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        }
        dumperOptions.setSplitLines(Feature.SPLIT_LINES.enabledIn(this._formatFeatures));
        if (Feature.INDENT_ARRAYS.enabledIn(this._formatFeatures)) {
            dumperOptions.setIndicatorIndent(1);
            dumperOptions.setIndent(2);
        }
        if (Feature.USE_PLATFORM_LINE_BREAKS.enabledIn(this._formatFeatures)) {
            dumperOptions.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
        }
        return dumperOptions;
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    @Override
    public YAMLGenerator useDefaultPrettyPrinter() {
        return this;
    }
    
    @Override
    public YAMLGenerator setPrettyPrinter(final PrettyPrinter prettyPrinter) {
        return this;
    }
    
    @Override
    public Object getOutputTarget() {
        return this._writer;
    }
    
    @Override
    public int getOutputBuffered() {
        return -1;
    }
    
    @Override
    public int getFormatFeatures() {
        return this._formatFeatures;
    }
    
    @Override
    public JsonGenerator overrideFormatFeatures(final int n, final int n2) {
        this._formatFeatures = ((this._formatFeatures & ~n2) | (n & n2));
        return this;
    }
    
    @Override
    public boolean canUseSchema(final FormatSchema formatSchema) {
        return false;
    }
    
    @Override
    public boolean canWriteFormattedNumbers() {
        return true;
    }
    
    public YAMLGenerator enable(final Feature feature) {
        this._formatFeatures |= feature.getMask();
        return this;
    }
    
    public YAMLGenerator disable(final Feature feature) {
        this._formatFeatures &= ~feature.getMask();
        return this;
    }
    
    public final boolean isEnabled(final Feature feature) {
        return (this._formatFeatures & feature.getMask()) != 0x0;
    }
    
    public YAMLGenerator configure(final Feature feature, final boolean b) {
        if (b) {
            this.enable(feature);
        }
        else {
            this.disable(feature);
        }
        return this;
    }
    
    @Override
    public final void writeFieldName(final String s) throws IOException {
        if (this._writeContext.writeFieldName(s) == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        this._writeFieldName(s);
    }
    
    @Override
    public final void writeFieldName(final SerializableString serializableString) throws IOException {
        if (this._writeContext.writeFieldName(serializableString.getValue()) == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        this._writeFieldName(serializableString.getValue());
    }
    
    @Override
    public final void writeStringField(final String s, final String s2) throws IOException {
        if (this._writeContext.writeFieldName(s) == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        this._writeFieldName(s);
        this.writeString(s2);
    }
    
    private final void _writeFieldName(final String s) throws IOException {
        this._writeScalar(s, "string", YAMLGenerator.STYLE_NAME);
    }
    
    @Override
    public final void flush() throws IOException {
        this._writer.flush();
    }
    
    @Override
    public void close() throws IOException {
        if (!this.isClosed()) {
            this._emitter.emit(new DocumentEndEvent(null, null, false));
            this._emitter.emit(new StreamEndEvent(null, null));
            super.close();
            this._writer.close();
        }
    }
    
    @Override
    public final void writeStartArray() throws IOException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        final Boolean styleBoolean = this._outputOptions.getDefaultFlowStyle().getStyleBoolean();
        final String typeId = this._typeId;
        final boolean implicit = typeId == null;
        final String objectId = this._objectId;
        if (objectId != null) {
            this._objectId = null;
        }
        this._emitter.emit(new SequenceStartEvent(objectId, typeId, implicit, null, null, styleBoolean));
    }
    
    @Override
    public final void writeEndArray() throws IOException {
        if (!this._writeContext.inArray()) {
            this._reportError("Current context not Array but " + this._writeContext.typeDesc());
        }
        this._typeId = null;
        this._writeContext = this._writeContext.getParent();
        this._emitter.emit(new SequenceEndEvent(null, null));
    }
    
    @Override
    public final void writeStartObject() throws IOException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        final Boolean styleBoolean = this._outputOptions.getDefaultFlowStyle().getStyleBoolean();
        final String typeId = this._typeId;
        final boolean implicit = typeId == null;
        final String objectId = this._objectId;
        if (objectId != null) {
            this._objectId = null;
        }
        this._emitter.emit(new MappingStartEvent(objectId, typeId, implicit, null, null, styleBoolean));
    }
    
    @Override
    public final void writeEndObject() throws IOException {
        if (!this._writeContext.inObject()) {
            this._reportError("Current context not Object but " + this._writeContext.typeDesc());
        }
        this._typeId = null;
        this._writeContext = this._writeContext.getParent();
        this._emitter.emit(new MappingEndEvent(null, null));
    }
    
    @Override
    public void writeString(final String s) throws IOException, JsonGenerationException {
        if (s == null) {
            this.writeNull();
            return;
        }
        this._verifyValueWrite("write String value");
        Character c = YAMLGenerator.STYLE_QUOTED;
        if (Feature.MINIMIZE_QUOTES.enabledIn(this._formatFeatures) && !this.isBooleanContent(s)) {
            if (Feature.ALWAYS_QUOTE_NUMBERS_AS_STRINGS.enabledIn(this._formatFeatures) && YAMLGenerator.PLAIN_NUMBER_P.matcher(s).matches()) {
                c = YAMLGenerator.STYLE_QUOTED;
            }
            else if (s.indexOf(10) >= 0) {
                c = YAMLGenerator.STYLE_LITERAL;
            }
            else {
                c = YAMLGenerator.STYLE_PLAIN;
            }
        }
        else if (Feature.LITERAL_BLOCK_STYLE.enabledIn(this._formatFeatures) && s.indexOf(10) >= 0) {
            c = YAMLGenerator.STYLE_LITERAL;
        }
        this._writeScalar(s, "string", c);
    }
    
    private boolean isBooleanContent(final String s) {
        return s.equals("true") || s.equals("false");
    }
    
    @Override
    public void writeString(final char[] array, final int n, final int n2) throws IOException {
        this.writeString(new String(array, n, n2));
    }
    
    @Override
    public final void writeString(final SerializableString serializableString) throws IOException {
        this.writeString(serializableString.toString());
    }
    
    @Override
    public void writeRawUTF8String(final byte[] array, final int n, final int n2) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public final void writeUTF8String(final byte[] array, final int n, final int n2) throws IOException {
        this.writeString(new String(array, n, n2, "UTF-8"));
    }
    
    @Override
    public void writeRaw(final String s) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeRaw(final String s, final int n, final int n2) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeRaw(final char[] array, final int n, final int n2) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeRaw(final char c) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeRawValue(final String s) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeRawValue(final String s, final int n, final int n2) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeRawValue(final char[] array, final int n, final int n2) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeBinary(final Base64Variant base64Variant, byte[] copyOfRange, final int n, final int n2) throws IOException {
        if (copyOfRange == null) {
            this.writeNull();
            return;
        }
        this._verifyValueWrite("write Binary value");
        if (n > 0 || n + n2 != copyOfRange.length) {
            copyOfRange = Arrays.copyOfRange(copyOfRange, n, n + n2);
        }
        this._writeScalarBinary(base64Variant, copyOfRange);
    }
    
    @Override
    public void writeBoolean(final boolean b) throws IOException {
        this._verifyValueWrite("write boolean value");
        this._writeScalar(b ? "true" : "false", "bool", YAMLGenerator.STYLE_SCALAR);
    }
    
    @Override
    public void writeNumber(final int n) throws IOException {
        this._verifyValueWrite("write number");
        this._writeScalar(String.valueOf(n), "int", YAMLGenerator.STYLE_SCALAR);
    }
    
    @Override
    public void writeNumber(final long n) throws IOException {
        if (n <= 0L && n >= -2147483648L) {
            this.writeNumber((int)n);
            return;
        }
        this._verifyValueWrite("write number");
        this._writeScalar(String.valueOf(n), "long", YAMLGenerator.STYLE_SCALAR);
    }
    
    @Override
    public void writeNumber(final BigInteger bigInteger) throws IOException {
        if (bigInteger == null) {
            this.writeNull();
            return;
        }
        this._verifyValueWrite("write number");
        this._writeScalar(String.valueOf(bigInteger.toString()), "java.math.BigInteger", YAMLGenerator.STYLE_SCALAR);
    }
    
    @Override
    public void writeNumber(final double n) throws IOException {
        this._verifyValueWrite("write number");
        this._writeScalar(String.valueOf(n), "double", YAMLGenerator.STYLE_SCALAR);
    }
    
    @Override
    public void writeNumber(final float n) throws IOException {
        this._verifyValueWrite("write number");
        this._writeScalar(String.valueOf(n), "float", YAMLGenerator.STYLE_SCALAR);
    }
    
    @Override
    public void writeNumber(final BigDecimal bigDecimal) throws IOException {
        if (bigDecimal == null) {
            this.writeNull();
            return;
        }
        this._verifyValueWrite("write number");
        this._writeScalar(this.isEnabled(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN) ? bigDecimal.toPlainString() : bigDecimal.toString(), "java.math.BigDecimal", YAMLGenerator.STYLE_SCALAR);
    }
    
    @Override
    public void writeNumber(final String s) throws IOException, JsonGenerationException, UnsupportedOperationException {
        if (s == null) {
            this.writeNull();
            return;
        }
        this._verifyValueWrite("write number");
        this._writeScalar(s, "number", YAMLGenerator.STYLE_SCALAR);
    }
    
    @Override
    public void writeNull() throws IOException {
        this._verifyValueWrite("write null value");
        this._writeScalar("null", "object", YAMLGenerator.STYLE_SCALAR);
    }
    
    @Override
    public boolean canWriteObjectId() {
        return Feature.USE_NATIVE_OBJECT_ID.enabledIn(this._formatFeatures);
    }
    
    @Override
    public boolean canWriteTypeId() {
        return Feature.USE_NATIVE_TYPE_ID.enabledIn(this._formatFeatures);
    }
    
    @Override
    public void writeTypeId(final Object o) throws IOException {
        this._typeId = String.valueOf(o);
    }
    
    @Override
    public void writeObjectRef(final Object o) throws IOException {
        this._verifyValueWrite("write Object reference");
        this._emitter.emit(new AliasEvent(String.valueOf(o), null, null));
    }
    
    @Override
    public void writeObjectId(final Object o) throws IOException {
        this._objectId = String.valueOf(o);
    }
    
    @Override
    protected final void _verifyValueWrite(final String s) throws IOException {
        if (this._writeContext.writeValue() == 5) {
            this._reportError("Can not " + s + ", expecting field name");
        }
    }
    
    @Override
    protected void _releaseBuffers() {
    }
    
    protected void _writeScalar(final String s, final String s2, final Character c) throws IOException {
        this._emitter.emit(this._scalarEvent(s, c));
    }
    
    private void _writeScalarBinary(Base64Variant mime, final byte[] array) throws IOException {
        if (mime == Base64Variants.getDefaultVariant()) {
            mime = Base64Variants.MIME;
        }
        this._emitter.emit(new ScalarEvent(null, YAMLGenerator.TAG_BINARY, YAMLGenerator.EXPLICIT_TAGS, mime.encode(array), null, null, YAMLGenerator.STYLE_BASE64));
    }
    
    protected ScalarEvent _scalarEvent(final String value, final Character style) {
        final String typeId = this._typeId;
        if (typeId != null) {
            this._typeId = null;
        }
        final String objectId = this._objectId;
        if (objectId != null) {
            this._objectId = null;
        }
        return new ScalarEvent(objectId, typeId, YAMLGenerator.NO_TAGS, value, null, null, style);
    }
    
    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        return this.useDefaultPrettyPrinter();
    }
    
    @Override
    public JsonGenerator setPrettyPrinter(final PrettyPrinter prettyPrinter) {
        return this.setPrettyPrinter(prettyPrinter);
    }
    
    static {
        PLAIN_NUMBER_P = Pattern.compile("[0-9]*(\\.[0-9]*)?");
        TAG_BINARY = Tag.BINARY.toString();
        STYLE_NAME = null;
        STYLE_SCALAR = null;
        STYLE_QUOTED = '\"';
        STYLE_LITERAL = '|';
        STYLE_BASE64 = YAMLGenerator.STYLE_LITERAL;
        STYLE_PLAIN = null;
        NO_TAGS = new ImplicitTuple(true, true);
        EXPLICIT_TAGS = new ImplicitTuple(false, false);
    }
    
    public enum Feature implements FormatFeature
    {
        WRITE_DOC_START_MARKER(true), 
        USE_NATIVE_OBJECT_ID(true), 
        USE_NATIVE_TYPE_ID(true), 
        CANONICAL_OUTPUT(false), 
        SPLIT_LINES(true), 
        MINIMIZE_QUOTES(false), 
        ALWAYS_QUOTE_NUMBERS_AS_STRINGS(false), 
        LITERAL_BLOCK_STYLE(false), 
        INDENT_ARRAYS(false), 
        USE_PLATFORM_LINE_BREAKS(false);
        
        protected final boolean _defaultState;
        protected final int _mask;
        private static final Feature[] $VALUES;
        
        public static Feature[] values() {
            return Feature.$VALUES.clone();
        }
        
        public static Feature valueOf(final String s) {
            return Enum.valueOf(Feature.class, s);
        }
        
        public static int collectDefaults() {
            int n = 0;
            for (final Feature feature : values()) {
                if (feature.enabledByDefault()) {
                    n |= feature.getMask();
                }
            }
            return n;
        }
        
        private Feature(final boolean defaultState) {
            this._defaultState = defaultState;
            this._mask = 1 << this.ordinal();
        }
        
        @Override
        public boolean enabledByDefault() {
            return this._defaultState;
        }
        
        @Override
        public boolean enabledIn(final int n) {
            return (n & this._mask) != 0x0;
        }
        
        @Override
        public int getMask() {
            return this._mask;
        }
        
        static {
            $VALUES = new Feature[] { Feature.WRITE_DOC_START_MARKER, Feature.USE_NATIVE_OBJECT_ID, Feature.USE_NATIVE_TYPE_ID, Feature.CANONICAL_OUTPUT, Feature.SPLIT_LINES, Feature.MINIMIZE_QUOTES, Feature.ALWAYS_QUOTE_NUMBERS_AS_STRINGS, Feature.LITERAL_BLOCK_STYLE, Feature.INDENT_ARRAYS, Feature.USE_PLATFORM_LINE_BREAKS };
        }
    }
}
