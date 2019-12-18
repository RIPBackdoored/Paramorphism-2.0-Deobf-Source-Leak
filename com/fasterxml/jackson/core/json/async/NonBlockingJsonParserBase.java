package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.sym.*;
import com.fasterxml.jackson.core.io.*;
import java.io.*;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.json.*;
import com.fasterxml.jackson.core.*;

public abstract class NonBlockingJsonParserBase extends ParserBase
{
    protected static final int MAJOR_INITIAL = 0;
    protected static final int MAJOR_ROOT = 1;
    protected static final int MAJOR_OBJECT_FIELD_FIRST = 2;
    protected static final int MAJOR_OBJECT_FIELD_NEXT = 3;
    protected static final int MAJOR_OBJECT_VALUE = 4;
    protected static final int MAJOR_ARRAY_ELEMENT_FIRST = 5;
    protected static final int MAJOR_ARRAY_ELEMENT_NEXT = 6;
    protected static final int MAJOR_CLOSED = 7;
    protected static final int MINOR_ROOT_BOM = 1;
    protected static final int MINOR_ROOT_NEED_SEPARATOR = 2;
    protected static final int MINOR_ROOT_GOT_SEPARATOR = 3;
    protected static final int MINOR_FIELD_LEADING_WS = 4;
    protected static final int MINOR_FIELD_LEADING_COMMA = 5;
    protected static final int MINOR_FIELD_NAME = 7;
    protected static final int MINOR_FIELD_NAME_ESCAPE = 8;
    protected static final int MINOR_FIELD_APOS_NAME = 9;
    protected static final int MINOR_FIELD_UNQUOTED_NAME = 10;
    protected static final int MINOR_VALUE_LEADING_WS = 12;
    protected static final int MINOR_VALUE_EXPECTING_COMMA = 13;
    protected static final int MINOR_VALUE_EXPECTING_COLON = 14;
    protected static final int MINOR_VALUE_WS_AFTER_COMMA = 15;
    protected static final int MINOR_VALUE_TOKEN_NULL = 16;
    protected static final int MINOR_VALUE_TOKEN_TRUE = 17;
    protected static final int MINOR_VALUE_TOKEN_FALSE = 18;
    protected static final int MINOR_VALUE_TOKEN_NON_STD = 19;
    protected static final int MINOR_NUMBER_MINUS = 23;
    protected static final int MINOR_NUMBER_ZERO = 24;
    protected static final int MINOR_NUMBER_MINUSZERO = 25;
    protected static final int MINOR_NUMBER_INTEGER_DIGITS = 26;
    protected static final int MINOR_NUMBER_FRACTION_DIGITS = 30;
    protected static final int MINOR_NUMBER_EXPONENT_MARKER = 31;
    protected static final int MINOR_NUMBER_EXPONENT_DIGITS = 32;
    protected static final int MINOR_VALUE_STRING = 40;
    protected static final int MINOR_VALUE_STRING_ESCAPE = 41;
    protected static final int MINOR_VALUE_STRING_UTF8_2 = 42;
    protected static final int MINOR_VALUE_STRING_UTF8_3 = 43;
    protected static final int MINOR_VALUE_STRING_UTF8_4 = 44;
    protected static final int MINOR_VALUE_APOS_STRING = 45;
    protected static final int MINOR_VALUE_TOKEN_ERROR = 50;
    protected static final int MINOR_COMMENT_LEADING_SLASH = 51;
    protected static final int MINOR_COMMENT_CLOSING_ASTERISK = 52;
    protected static final int MINOR_COMMENT_C = 53;
    protected static final int MINOR_COMMENT_CPP = 54;
    protected static final int MINOR_COMMENT_YAML = 55;
    protected final ByteQuadsCanonicalizer _symbols;
    protected int[] _quadBuffer;
    protected int _quadLength;
    protected int _quad1;
    protected int _pending32;
    protected int _pendingBytes;
    protected int _quoted32;
    protected int _quotedDigits;
    protected int _majorState;
    protected int _majorStateAfterValue;
    protected int _minorState;
    protected int _minorStateAfterSplit;
    protected boolean _endOfInput;
    protected static final int NON_STD_TOKEN_NAN = 0;
    protected static final int NON_STD_TOKEN_INFINITY = 1;
    protected static final int NON_STD_TOKEN_PLUS_INFINITY = 2;
    protected static final int NON_STD_TOKEN_MINUS_INFINITY = 3;
    protected static final String[] NON_STD_TOKENS;
    protected static final double[] NON_STD_TOKEN_VALUES;
    protected int _nonStdTokenType;
    protected int _currBufferStart;
    protected int _currInputRowAlt;
    
    public NonBlockingJsonParserBase(final IOContext ioContext, final int n, final ByteQuadsCanonicalizer symbols) {
        super(ioContext, n);
        this._quadBuffer = new int[8];
        this._endOfInput = false;
        this._currBufferStart = 0;
        this._currInputRowAlt = 1;
        this._symbols = symbols;
        this._currToken = null;
        this._majorState = 0;
        this._majorStateAfterValue = 1;
    }
    
    @Override
    public ObjectCodec getCodec() {
        return null;
    }
    
    @Override
    public void setCodec(final ObjectCodec objectCodec) {
        throw new UnsupportedOperationException("Can not use ObjectMapper with non-blocking parser");
    }
    
    @Override
    public boolean canParseAsync() {
        return true;
    }
    
    protected ByteQuadsCanonicalizer symbolTableForTests() {
        return this._symbols;
    }
    
    @Override
    public abstract int releaseBuffered(final OutputStream p0) throws IOException;
    
    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
    }
    
    @Override
    public Object getInputSource() {
        return null;
    }
    
    @Override
    protected void _closeInput() throws IOException {
        this._currBufferStart = 0;
        this._inputEnd = 0;
    }
    
    @Override
    public boolean hasTextCharacters() {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.hasTextAsCharacters();
        }
        return this._currToken == JsonToken.FIELD_NAME && this._nameCopied;
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        return new JsonLocation(this._getSourceReference(), this._currInputProcessed + (this._inputPtr - this._currBufferStart), -1L, Math.max(this._currInputRow, this._currInputRowAlt), this._inputPtr - this._currInputRowStart + 1);
    }
    
    @Override
    public JsonLocation getTokenLocation() {
        return new JsonLocation(this._getSourceReference(), this._tokenInputTotal, -1L, this._tokenInputRow, this._tokenInputCol);
    }
    
    @Override
    public String getText() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.contentsAsString();
        }
        return this._getText2(this._currToken);
    }
    
    protected final String _getText2(final JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        switch (jsonToken.id()) {
            case -1: {
                return null;
            }
            case 5: {
                return this._parsingContext.getCurrentName();
            }
            case 6:
            case 7:
            case 8: {
                return this._textBuffer.contentsAsString();
            }
            default: {
                return jsonToken.asString();
            }
        }
    }
    
    @Override
    public int getText(final Writer writer) throws IOException {
        final JsonToken currToken = this._currToken;
        if (currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.contentsToWriter(writer);
        }
        if (currToken == JsonToken.FIELD_NAME) {
            final String currentName = this._parsingContext.getCurrentName();
            writer.write(currentName);
            return currentName.length();
        }
        if (currToken == null) {
            return 0;
        }
        if (currToken.isNumeric()) {
            return this._textBuffer.contentsToWriter(writer);
        }
        if (currToken == JsonToken.NOT_AVAILABLE) {
            this._reportError("Current token not available: can not call this method");
        }
        final char[] charArray = currToken.asCharArray();
        writer.write(charArray);
        return charArray.length;
    }
    
    @Override
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return super.getValueAsString(null);
    }
    
    @Override
    public String getValueAsString(final String s) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return super.getValueAsString(s);
    }
    
    @Override
    public char[] getTextCharacters() throws IOException {
        if (this._currToken == null) {
            return null;
        }
        switch (this._currToken.id()) {
            case 5: {
                if (!this._nameCopied) {
                    final String currentName = this._parsingContext.getCurrentName();
                    final int length = currentName.length();
                    if (this._nameCopyBuffer == null) {
                        this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(length);
                    }
                    else if (this._nameCopyBuffer.length < length) {
                        this._nameCopyBuffer = new char[length];
                    }
                    currentName.getChars(0, length, this._nameCopyBuffer, 0);
                    this._nameCopied = true;
                }
                return this._nameCopyBuffer;
            }
            case 6:
            case 7:
            case 8: {
                return this._textBuffer.getTextBuffer();
            }
            default: {
                return this._currToken.asCharArray();
            }
        }
    }
    
    @Override
    public int getTextLength() throws IOException {
        if (this._currToken == null) {
            return 0;
        }
        switch (this._currToken.id()) {
            case 5: {
                return this._parsingContext.getCurrentName().length();
            }
            case 6:
            case 7:
            case 8: {
                return this._textBuffer.size();
            }
            default: {
                return this._currToken.asCharArray().length;
            }
        }
    }
    
    @Override
    public int getTextOffset() throws IOException {
        if (this._currToken != null) {
            switch (this._currToken.id()) {
                case 5: {
                    return 0;
                }
                case 6:
                case 7:
                case 8: {
                    return this._textBuffer.getTextOffset();
                }
            }
        }
        return 0;
    }
    
    @Override
    public byte[] getBinaryValue(final Base64Variant base64Variant) throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            this._reportError("Current token (%s) not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary", this._currToken);
        }
        if (this._binaryValue == null) {
            final ByteArrayBuilder getByteArrayBuilder = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), getByteArrayBuilder, base64Variant);
            this._binaryValue = getByteArrayBuilder.toByteArray();
        }
        return this._binaryValue;
    }
    
    @Override
    public int readBinaryValue(final Base64Variant base64Variant, final OutputStream outputStream) throws IOException {
        final byte[] binaryValue = this.getBinaryValue(base64Variant);
        outputStream.write(binaryValue);
        return binaryValue.length;
    }
    
    @Override
    public Object getEmbeddedObject() throws IOException {
        if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
            return this._binaryValue;
        }
        return null;
    }
    
    protected final JsonToken _startArrayScope() throws IOException {
        this._parsingContext = this._parsingContext.createChildArrayContext(-1, -1);
        this._majorState = 5;
        this._majorStateAfterValue = 6;
        return this._currToken = JsonToken.START_ARRAY;
    }
    
    protected final JsonToken _startObjectScope() throws IOException {
        this._parsingContext = this._parsingContext.createChildObjectContext(-1, -1);
        this._majorState = 2;
        this._majorStateAfterValue = 3;
        return this._currToken = JsonToken.START_OBJECT;
    }
    
    protected final JsonToken _closeArrayScope() throws IOException {
        if (!this._parsingContext.inArray()) {
            this._reportMismatchedEndMarker(93, '}');
        }
        final JsonReadContext parent = this._parsingContext.getParent();
        this._parsingContext = parent;
        int n;
        if (parent.inObject()) {
            n = 3;
        }
        else if (parent.inArray()) {
            n = 6;
        }
        else {
            n = 1;
        }
        this._majorState = n;
        this._majorStateAfterValue = n;
        return this._currToken = JsonToken.END_ARRAY;
    }
    
    protected final JsonToken _closeObjectScope() throws IOException {
        if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(125, ']');
        }
        final JsonReadContext parent = this._parsingContext.getParent();
        this._parsingContext = parent;
        int n;
        if (parent.inObject()) {
            n = 3;
        }
        else if (parent.inArray()) {
            n = 6;
        }
        else {
            n = 1;
        }
        this._majorState = n;
        this._majorStateAfterValue = n;
        return this._currToken = JsonToken.END_OBJECT;
    }
    
    protected final String _findName(int padLastQuad, final int n) throws JsonParseException {
        padLastQuad = _padLastQuad(padLastQuad, n);
        final String name = this._symbols.findName(padLastQuad);
        if (name != null) {
            return name;
        }
        this._quadBuffer[0] = padLastQuad;
        return this._addName(this._quadBuffer, 1, n);
    }
    
    protected final String _findName(final int n, int padLastQuad, final int n2) throws JsonParseException {
        padLastQuad = _padLastQuad(padLastQuad, n2);
        final String name = this._symbols.findName(n, padLastQuad);
        if (name != null) {
            return name;
        }
        this._quadBuffer[0] = n;
        this._quadBuffer[1] = padLastQuad;
        return this._addName(this._quadBuffer, 2, n2);
    }
    
    protected final String _findName(final int n, final int n2, int padLastQuad, final int n3) throws JsonParseException {
        padLastQuad = _padLastQuad(padLastQuad, n3);
        final String name = this._symbols.findName(n, n2, padLastQuad);
        if (name != null) {
            return name;
        }
        final int[] quadBuffer = this._quadBuffer;
        quadBuffer[0] = n;
        quadBuffer[1] = n2;
        quadBuffer[2] = _padLastQuad(padLastQuad, n3);
        return this._addName(quadBuffer, 3, n3);
    }
    
    protected final String _addName(final int[] array, final int n, final int n2) throws JsonParseException {
        final int n3 = (n << 2) - 4 + n2;
        int n4;
        if (n2 < 4) {
            n4 = array[n - 1];
            array[n - 1] = n4 << (4 - n2 << 3);
        }
        else {
            n4 = 0;
        }
        char[] array2 = this._textBuffer.emptyAndGetCurrentSegment();
        int n5 = 0;
        int i = 0;
        while (i < n3) {
            int n6 = array[i >> 2] >> (3 - (i & 0x3) << 3) & 0xFF;
            ++i;
            if (n6 > 127) {
                int n7;
                int n8;
                if ((n6 & 0xE0) == 0xC0) {
                    n7 = (n6 & 0x1F);
                    n8 = 1;
                }
                else if ((n6 & 0xF0) == 0xE0) {
                    n7 = (n6 & 0xF);
                    n8 = 2;
                }
                else if ((n6 & 0xF8) == 0xF0) {
                    n7 = (n6 & 0x7);
                    n8 = 3;
                }
                else {
                    this._reportInvalidInitial(n6);
                    n7 = (n8 = 1);
                }
                if (i + n8 > n3) {
                    this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
                }
                final int n9 = array[i >> 2] >> (3 - (i & 0x3) << 3);
                ++i;
                if ((n9 & 0xC0) != 0x80) {
                    this._reportInvalidOther(n9);
                }
                n6 = (n7 << 6 | (n9 & 0x3F));
                if (n8 > 1) {
                    final int n10 = array[i >> 2] >> (3 - (i & 0x3) << 3);
                    ++i;
                    if ((n10 & 0xC0) != 0x80) {
                        this._reportInvalidOther(n10);
                    }
                    n6 = (n6 << 6 | (n10 & 0x3F));
                    if (n8 > 2) {
                        final int n11 = array[i >> 2] >> (3 - (i & 0x3) << 3);
                        ++i;
                        if ((n11 & 0xC0) != 0x80) {
                            this._reportInvalidOther(n11 & 0xFF);
                        }
                        n6 = (n6 << 6 | (n11 & 0x3F));
                    }
                }
                if (n8 > 2) {
                    final int n12 = n6 - 65536;
                    if (n5 >= array2.length) {
                        array2 = this._textBuffer.expandCurrentSegment();
                    }
                    array2[n5++] = (char)(55296 + (n12 >> 10));
                    n6 = (0xDC00 | (n12 & 0x3FF));
                }
            }
            if (n5 >= array2.length) {
                array2 = this._textBuffer.expandCurrentSegment();
            }
            array2[n5++] = (char)n6;
        }
        final String s = new String(array2, 0, n5);
        if (n2 < 4) {
            array[n - 1] = n4;
        }
        return this._symbols.addName(s, array, n);
    }
    
    protected static final int _padLastQuad(final int n, final int n2) {
        return (n2 == 4) ? n : (n | -1 << (n2 << 3));
    }
    
    protected final JsonToken _eofAsNextToken() throws IOException {
        this._majorState = 7;
        if (!this._parsingContext.inRoot()) {
            this._handleEOF();
        }
        this.close();
        return this._currToken = null;
    }
    
    protected final JsonToken _fieldComplete(final String currentName) throws IOException {
        this._majorState = 4;
        this._parsingContext.setCurrentName(currentName);
        return this._currToken = JsonToken.FIELD_NAME;
    }
    
    protected final JsonToken _valueComplete(final JsonToken currToken) throws IOException {
        this._majorState = this._majorStateAfterValue;
        return this._currToken = currToken;
    }
    
    protected final JsonToken _valueCompleteInt(final int numberInt, final String s) throws IOException {
        this._textBuffer.resetWithString(s);
        this._intLength = s.length();
        this._numTypesValid = 1;
        this._numberInt = numberInt;
        this._majorState = this._majorStateAfterValue;
        return this._currToken = JsonToken.VALUE_NUMBER_INT;
    }
    
    protected final JsonToken _valueNonStdNumberComplete(final int n) throws IOException {
        final String s = NonBlockingJsonParserBase.NON_STD_TOKENS[n];
        this._textBuffer.resetWithString(s);
        if (!this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
            this._reportError("Non-standard token '%s': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow", s);
        }
        this._intLength = 0;
        this._numTypesValid = 8;
        this._numberDouble = NonBlockingJsonParserBase.NON_STD_TOKEN_VALUES[n];
        this._majorState = this._majorStateAfterValue;
        return this._currToken = JsonToken.VALUE_NUMBER_FLOAT;
    }
    
    protected final String _nonStdToken(final int n) {
        return NonBlockingJsonParserBase.NON_STD_TOKENS[n];
    }
    
    protected final void _updateTokenLocation() {
        this._tokenInputRow = Math.max(this._currInputRow, this._currInputRowAlt);
        final int inputPtr = this._inputPtr;
        this._tokenInputCol = inputPtr - this._currInputRowStart;
        this._tokenInputTotal = this._currInputProcessed + (inputPtr - this._currBufferStart);
    }
    
    protected void _reportInvalidChar(final int n) throws JsonParseException {
        if (n < 32) {
            this._throwInvalidSpace(n);
        }
        this._reportInvalidInitial(n);
    }
    
    protected void _reportInvalidInitial(final int n) throws JsonParseException {
        this._reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(n));
    }
    
    protected void _reportInvalidOther(final int n, final int inputPtr) throws JsonParseException {
        this._inputPtr = inputPtr;
        this._reportInvalidOther(n);
    }
    
    protected void _reportInvalidOther(final int n) throws JsonParseException {
        this._reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(n));
    }
    
    static {
        NON_STD_TOKENS = new String[] { "NaN", "Infinity", "+Infinity", "-Infinity" };
        NON_STD_TOKEN_VALUES = new double[] { Double.NaN, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY };
    }
}
