package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.json.*;
import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;
import java.math.*;

public abstract class GeneratorBase extends JsonGenerator
{
    public static final int SURR1_FIRST = 55296;
    public static final int SURR1_LAST = 56319;
    public static final int SURR2_FIRST = 56320;
    public static final int SURR2_LAST = 57343;
    protected static final int DERIVED_FEATURES_MASK;
    protected static final String WRITE_BINARY = "write a binary value";
    protected static final String WRITE_BOOLEAN = "write a boolean value";
    protected static final String WRITE_NULL = "write a null";
    protected static final String WRITE_NUMBER = "write a number";
    protected static final String WRITE_RAW = "write a raw (unencoded) value";
    protected static final String WRITE_STRING = "write a string";
    protected static final int MAX_BIG_DECIMAL_SCALE = 9999;
    protected ObjectCodec _objectCodec;
    protected int _features;
    protected boolean _cfgNumbersAsStrings;
    protected JsonWriteContext _writeContext;
    protected boolean _closed;
    
    protected GeneratorBase(final int features, final ObjectCodec objectCodec) {
        super();
        this._features = features;
        this._objectCodec = objectCodec;
        this._writeContext = JsonWriteContext.createRootContext(Feature.STRICT_DUPLICATE_DETECTION.enabledIn(features) ? DupDetector.rootDetector(this) : null);
        this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(features);
    }
    
    protected GeneratorBase(final int features, final ObjectCodec objectCodec, final JsonWriteContext writeContext) {
        super();
        this._features = features;
        this._objectCodec = objectCodec;
        this._writeContext = writeContext;
        this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(features);
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    @Override
    public Object getCurrentValue() {
        return this._writeContext.getCurrentValue();
    }
    
    @Override
    public void setCurrentValue(final Object currentValue) {
        this._writeContext.setCurrentValue(currentValue);
    }
    
    @Override
    public final boolean isEnabled(final Feature feature) {
        return (this._features & feature.getMask()) != 0x0;
    }
    
    @Override
    public int getFeatureMask() {
        return this._features;
    }
    
    @Override
    public JsonGenerator enable(final Feature feature) {
        final int mask = feature.getMask();
        this._features |= mask;
        if ((mask & GeneratorBase.DERIVED_FEATURES_MASK) != 0x0) {
            if (feature == Feature.WRITE_NUMBERS_AS_STRINGS) {
                this._cfgNumbersAsStrings = true;
            }
            else if (feature == Feature.ESCAPE_NON_ASCII) {
                this.setHighestNonEscapedChar(127);
            }
            else if (feature == Feature.STRICT_DUPLICATE_DETECTION && this._writeContext.getDupDetector() == null) {
                this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector(this));
            }
        }
        return this;
    }
    
    @Override
    public JsonGenerator disable(final Feature feature) {
        final int mask = feature.getMask();
        this._features &= ~mask;
        if ((mask & GeneratorBase.DERIVED_FEATURES_MASK) != 0x0) {
            if (feature == Feature.WRITE_NUMBERS_AS_STRINGS) {
                this._cfgNumbersAsStrings = false;
            }
            else if (feature == Feature.ESCAPE_NON_ASCII) {
                this.setHighestNonEscapedChar(0);
            }
            else if (feature == Feature.STRICT_DUPLICATE_DETECTION) {
                this._writeContext = this._writeContext.withDupDetector(null);
            }
        }
        return this;
    }
    
    @Deprecated
    @Override
    public JsonGenerator setFeatureMask(final int features) {
        final int n = features ^ this._features;
        this._features = features;
        if (n != 0) {
            this._checkStdFeatureChanges(features, n);
        }
        return this;
    }
    
    @Override
    public JsonGenerator overrideStdFeatures(final int n, final int n2) {
        final int features = this._features;
        final int features2 = (features & ~n2) | (n & n2);
        final int n3 = features ^ features2;
        if (n3 != 0) {
            this._checkStdFeatureChanges(this._features = features2, n3);
        }
        return this;
    }
    
    protected void _checkStdFeatureChanges(final int n, final int n2) {
        if ((n2 & GeneratorBase.DERIVED_FEATURES_MASK) == 0x0) {
            return;
        }
        this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(n);
        if (Feature.ESCAPE_NON_ASCII.enabledIn(n2)) {
            if (Feature.ESCAPE_NON_ASCII.enabledIn(n)) {
                this.setHighestNonEscapedChar(127);
            }
            else {
                this.setHighestNonEscapedChar(0);
            }
        }
        if (Feature.STRICT_DUPLICATE_DETECTION.enabledIn(n2)) {
            if (Feature.STRICT_DUPLICATE_DETECTION.enabledIn(n)) {
                if (this._writeContext.getDupDetector() == null) {
                    this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector(this));
                }
            }
            else {
                this._writeContext = this._writeContext.withDupDetector(null);
            }
        }
    }
    
    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        if (this.getPrettyPrinter() != null) {
            return this;
        }
        return this.setPrettyPrinter(this._constructDefaultPrettyPrinter());
    }
    
    @Override
    public JsonGenerator setCodec(final ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        return this;
    }
    
    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }
    
    @Override
    public JsonStreamContext getOutputContext() {
        return this._writeContext;
    }
    
    @Override
    public void writeStartObject(final Object o) throws IOException {
        this.writeStartObject();
        if (this._writeContext != null && o != null) {
            this._writeContext.setCurrentValue(o);
        }
        this.setCurrentValue(o);
    }
    
    @Override
    public void writeFieldName(final SerializableString serializableString) throws IOException {
        this.writeFieldName(serializableString.getValue());
    }
    
    @Override
    public void writeString(final SerializableString serializableString) throws IOException {
        this.writeString(serializableString.getValue());
    }
    
    @Override
    public void writeRawValue(final String s) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(s);
    }
    
    @Override
    public void writeRawValue(final String s, final int n, final int n2) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(s, n, n2);
    }
    
    @Override
    public void writeRawValue(final char[] array, final int n, final int n2) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(array, n, n2);
    }
    
    @Override
    public void writeRawValue(final SerializableString serializableString) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(serializableString);
    }
    
    @Override
    public int writeBinary(final Base64Variant base64Variant, final InputStream inputStream, final int n) throws IOException {
        this._reportUnsupportedOperation();
        return 0;
    }
    
    @Override
    public void writeObject(final Object o) throws IOException {
        if (o == null) {
            this.writeNull();
        }
        else {
            if (this._objectCodec != null) {
                this._objectCodec.writeValue(this, o);
                return;
            }
            this._writeSimpleObject(o);
        }
    }
    
    @Override
    public void writeTree(final TreeNode treeNode) throws IOException {
        if (treeNode == null) {
            this.writeNull();
        }
        else {
            if (this._objectCodec == null) {
                throw new IllegalStateException("No ObjectCodec defined");
            }
            this._objectCodec.writeValue(this, treeNode);
        }
    }
    
    @Override
    public abstract void flush() throws IOException;
    
    @Override
    public void close() throws IOException {
        this._closed = true;
    }
    
    @Override
    public boolean isClosed() {
        return this._closed;
    }
    
    protected abstract void _releaseBuffers();
    
    protected abstract void _verifyValueWrite(final String p0) throws IOException;
    
    protected PrettyPrinter _constructDefaultPrettyPrinter() {
        return new DefaultPrettyPrinter();
    }
    
    protected String _asString(final BigDecimal bigDecimal) throws IOException {
        if (Feature.WRITE_BIGDECIMAL_AS_PLAIN.enabledIn(this._features)) {
            final int scale = bigDecimal.scale();
            if (scale < -9999 || scale > 9999) {
                this._reportError(String.format("Attempt to write plain `java.math.BigDecimal` (see JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN) with illegal scale (%d): needs to be between [-%d, %d]", scale, 9999, 9999));
            }
            return bigDecimal.toPlainString();
        }
        return bigDecimal.toString();
    }
    
    protected final int _decodeSurrogate(final int n, final int n2) throws IOException {
        if (n2 < 56320 || n2 > 57343) {
            this._reportError("Incomplete surrogate pair: first char 0x" + Integer.toHexString(n) + ", second 0x" + Integer.toHexString(n2));
        }
        return 65536 + (n - 55296 << 10) + (n2 - 56320);
    }
    
    static {
        DERIVED_FEATURES_MASK = (Feature.WRITE_NUMBERS_AS_STRINGS.getMask() | Feature.ESCAPE_NON_ASCII.getMask() | Feature.STRICT_DUPLICATE_DETECTION.getMask());
    }
}
