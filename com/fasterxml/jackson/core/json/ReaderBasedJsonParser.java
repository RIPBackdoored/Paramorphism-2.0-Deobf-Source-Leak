package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.sym.*;
import java.io.*;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.*;

public class ReaderBasedJsonParser extends ParserBase
{
    protected static final int FEAT_MASK_TRAILING_COMMA;
    protected static final int[] _icLatin1;
    protected Reader _reader;
    protected char[] _inputBuffer;
    protected boolean _bufferRecyclable;
    protected ObjectCodec _objectCodec;
    protected final CharsToNameCanonicalizer _symbols;
    protected final int _hashSeed;
    protected boolean _tokenIncomplete;
    protected long _nameStartOffset;
    protected int _nameStartRow;
    protected int _nameStartCol;
    
    public ReaderBasedJsonParser(final IOContext ioContext, final int n, final Reader reader, final ObjectCodec objectCodec, final CharsToNameCanonicalizer symbols, final char[] inputBuffer, final int inputPtr, final int inputEnd, final boolean bufferRecyclable) {
        super(ioContext, n);
        this._reader = reader;
        this._inputBuffer = inputBuffer;
        this._inputPtr = inputPtr;
        this._inputEnd = inputEnd;
        this._objectCodec = objectCodec;
        this._symbols = symbols;
        this._hashSeed = symbols.hashSeed();
        this._bufferRecyclable = bufferRecyclable;
    }
    
    public ReaderBasedJsonParser(final IOContext ioContext, final int n, final Reader reader, final ObjectCodec objectCodec, final CharsToNameCanonicalizer symbols) {
        super(ioContext, n);
        this._reader = reader;
        this._inputBuffer = ioContext.allocTokenBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._objectCodec = objectCodec;
        this._symbols = symbols;
        this._hashSeed = symbols.hashSeed();
        this._bufferRecyclable = true;
    }
    
    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }
    
    @Override
    public void setCodec(final ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }
    
    @Override
    public int releaseBuffered(final Writer writer) throws IOException {
        final int n = this._inputEnd - this._inputPtr;
        if (n < 1) {
            return 0;
        }
        writer.write(this._inputBuffer, this._inputPtr, n);
        return n;
    }
    
    @Override
    public Object getInputSource() {
        return this._reader;
    }
    
    @Deprecated
    protected char getNextChar(final String s) throws IOException {
        return this.getNextChar(s, null);
    }
    
    protected char getNextChar(final String s, final JsonToken jsonToken) throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(s, jsonToken);
        }
        return this._inputBuffer[this._inputPtr++];
    }
    
    @Override
    protected void _closeInput() throws IOException {
        if (this._reader != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(Feature.AUTO_CLOSE_SOURCE)) {
                this._reader.close();
            }
            this._reader = null;
        }
    }
    
    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
        if (this._bufferRecyclable) {
            final char[] inputBuffer = this._inputBuffer;
            if (inputBuffer != null) {
                this._inputBuffer = null;
                this._ioContext.releaseTokenBuffer(inputBuffer);
            }
        }
    }
    
    protected void _loadMoreGuaranteed() throws IOException {
        if (!this._loadMore()) {
            this._reportInvalidEOF();
        }
    }
    
    protected boolean _loadMore() throws IOException {
        final int inputEnd = this._inputEnd;
        this._currInputProcessed += inputEnd;
        this._currInputRowStart -= inputEnd;
        this._nameStartOffset -= inputEnd;
        if (this._reader != null) {
            final int read = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
            if (read > 0) {
                this._inputPtr = 0;
                this._inputEnd = read;
                return true;
            }
            this._closeInput();
            if (read == 0) {
                throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
            }
        }
        return false;
    }
    
    @Override
    public final String getText() throws IOException {
        final JsonToken currToken = this._currToken;
        if (currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        return this._getText2(currToken);
    }
    
    @Override
    public int getText(final Writer writer) throws IOException {
        final JsonToken currToken = this._currToken;
        if (currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
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
        final char[] charArray = currToken.asCharArray();
        writer.write(charArray);
        return charArray.length;
    }
    
    @Override
    public final String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return super.getValueAsString(null);
    }
    
    @Override
    public final String getValueAsString(final String s) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return super.getValueAsString(s);
    }
    
    protected final String _getText2(final JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        switch (jsonToken.id()) {
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
    public final char[] getTextCharacters() throws IOException {
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
            case 6: {
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    this._finishString();
                    return this._textBuffer.getTextBuffer();
                }
                return this._textBuffer.getTextBuffer();
            }
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
    public final int getTextLength() throws IOException {
        if (this._currToken == null) {
            return 0;
        }
        switch (this._currToken.id()) {
            case 5: {
                return this._parsingContext.getCurrentName().length();
            }
            case 6: {
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    this._finishString();
                    return this._textBuffer.size();
                }
                return this._textBuffer.size();
            }
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
    public final int getTextOffset() throws IOException {
        if (this._currToken != null) {
            switch (this._currToken.id()) {
                case 5: {
                    return 0;
                }
                case 6: {
                    if (this._tokenIncomplete) {
                        this._tokenIncomplete = false;
                        this._finishString();
                        return this._textBuffer.getTextOffset();
                    }
                    return this._textBuffer.getTextOffset();
                }
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
        if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT && this._binaryValue != null) {
            return this._binaryValue;
        }
        if (this._currToken != JsonToken.VALUE_STRING) {
            this._reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
        }
        if (this._tokenIncomplete) {
            try {
                this._binaryValue = this._decodeBase64(base64Variant);
            }
            catch (IllegalArgumentException ex) {
                throw this._constructError("Failed to decode VALUE_STRING as base64 (" + base64Variant + "): " + ex.getMessage());
            }
            this._tokenIncomplete = false;
        }
        else if (this._binaryValue == null) {
            final ByteArrayBuilder getByteArrayBuilder = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), getByteArrayBuilder, base64Variant);
            this._binaryValue = getByteArrayBuilder.toByteArray();
        }
        return this._binaryValue;
    }
    
    @Override
    public int readBinaryValue(final Base64Variant base64Variant, final OutputStream outputStream) throws IOException {
        if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
            final byte[] binaryValue = this.getBinaryValue(base64Variant);
            outputStream.write(binaryValue);
            return binaryValue.length;
        }
        final byte[] allocBase64Buffer = this._ioContext.allocBase64Buffer();
        try {
            return this._readBinary(base64Variant, outputStream, allocBase64Buffer);
        }
        finally {
            this._ioContext.releaseBase64Buffer(allocBase64Buffer);
        }
    }
    
    protected int _readBinary(final Base64Variant base64Variant, final OutputStream outputStream, final byte[] array) throws IOException {
        int n = 0;
        final int n2 = array.length - 3;
        int n3 = 0;
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            final char c = this._inputBuffer[this._inputPtr++];
            if (c > ' ') {
                int n4 = base64Variant.decodeBase64Char(c);
                if (n4 < 0) {
                    if (c == '\"') {
                        break;
                    }
                    n4 = this._decodeBase64Escape(base64Variant, c, 0);
                    if (n4 < 0) {
                        continue;
                    }
                }
                if (n > n2) {
                    n3 += n;
                    outputStream.write(array, 0, n);
                    n = 0;
                }
                final int n5 = n4;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final char c2 = this._inputBuffer[this._inputPtr++];
                int n6 = base64Variant.decodeBase64Char(c2);
                if (n6 < 0) {
                    n6 = this._decodeBase64Escape(base64Variant, c2, 1);
                }
                final int n7 = n5 << 6 | n6;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final char c3 = this._inputBuffer[this._inputPtr++];
                int n8 = base64Variant.decodeBase64Char(c3);
                if (n8 < 0) {
                    if (n8 != -2) {
                        if (c3 == '\"' && !base64Variant.usesPadding()) {
                            array[n++] = (byte)(n7 >> 4);
                            break;
                        }
                        n8 = this._decodeBase64Escape(base64Variant, c3, 2);
                    }
                    if (n8 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            this._loadMoreGuaranteed();
                        }
                        final char c4 = this._inputBuffer[this._inputPtr++];
                        if (!base64Variant.usesPaddingChar(c4)) {
                            throw this.reportInvalidBase64Char(base64Variant, c4, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                        }
                        array[n++] = (byte)(n7 >> 4);
                        continue;
                    }
                }
                final int n9 = n7 << 6 | n8;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final char c5 = this._inputBuffer[this._inputPtr++];
                int n10 = base64Variant.decodeBase64Char(c5);
                if (n10 < 0) {
                    if (n10 != -2) {
                        if (c5 == '\"' && !base64Variant.usesPadding()) {
                            final int n11 = n9 >> 2;
                            array[n++] = (byte)(n11 >> 8);
                            array[n++] = (byte)n11;
                            break;
                        }
                        n10 = this._decodeBase64Escape(base64Variant, c5, 3);
                    }
                    if (n10 == -2) {
                        final int n12 = n9 >> 2;
                        array[n++] = (byte)(n12 >> 8);
                        array[n++] = (byte)n12;
                        continue;
                    }
                }
                final int n13 = n9 << 6 | n10;
                array[n++] = (byte)(n13 >> 16);
                array[n++] = (byte)(n13 >> 8);
                array[n++] = (byte)n13;
            }
        }
        this._tokenIncomplete = false;
        if (n > 0) {
            n3 += n;
            outputStream.write(array, 0, n);
        }
        return n3;
    }
    
    @Override
    public final JsonToken nextToken() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._nextAfterName();
        }
        this._numTypesValid = 0;
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int n = this._skipWSOrEnd();
        if (n < 0) {
            this.close();
            return this._currToken = null;
        }
        this._binaryValue = null;
        if (n == 93 || n == 125) {
            this._closeScope(n);
            return this._currToken;
        }
        if (this._parsingContext.expectComma()) {
            n = this._skipComma(n);
            if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (n == 93 || n == 125)) {
                this._closeScope(n);
                return this._currToken;
            }
        }
        final boolean inObject = this._parsingContext.inObject();
        if (inObject) {
            this._updateNameLocation();
            this._parsingContext.setCurrentName((n == 34) ? this._parseName() : this._handleOddName(n));
            this._currToken = JsonToken.FIELD_NAME;
            n = this._skipColon();
        }
        this._updateLocation();
        JsonToken jsonToken = null;
        switch (n) {
            case 34: {
                this._tokenIncomplete = true;
                jsonToken = JsonToken.VALUE_STRING;
                break;
            }
            case 91: {
                if (!inObject) {
                    this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                }
                jsonToken = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                if (!inObject) {
                    this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                }
                jsonToken = JsonToken.START_OBJECT;
                break;
            }
            case 125: {
                this._reportUnexpectedChar(n, "expected a value");
            }
            case 116: {
                this._matchTrue();
                jsonToken = JsonToken.VALUE_TRUE;
                break;
            }
            case 102: {
                this._matchFalse();
                jsonToken = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchNull();
                jsonToken = JsonToken.VALUE_NULL;
                break;
            }
            case 45: {
                jsonToken = this._parseNegNumber();
                break;
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                jsonToken = this._parsePosNumber(n);
                break;
            }
            default: {
                jsonToken = this._handleOddValue(n);
                break;
            }
        }
        if (inObject) {
            this._nextToken = jsonToken;
            return this._currToken;
        }
        return this._currToken = jsonToken;
    }
    
    private final JsonToken _nextAfterName() {
        this._nameCopied = false;
        final JsonToken nextToken = this._nextToken;
        this._nextToken = null;
        if (nextToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (nextToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return this._currToken = nextToken;
    }
    
    @Override
    public void finishToken() throws IOException {
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
        }
    }
    
    @Override
    public boolean nextFieldName(final SerializableString serializableString) throws IOException {
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nextAfterName();
            return false;
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int n = this._skipWSOrEnd();
        if (n < 0) {
            this.close();
            this._currToken = null;
            return false;
        }
        this._binaryValue = null;
        if (n == 93 || n == 125) {
            this._closeScope(n);
            return false;
        }
        if (this._parsingContext.expectComma()) {
            n = this._skipComma(n);
            if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (n == 93 || n == 125)) {
                this._closeScope(n);
                return false;
            }
        }
        if (!this._parsingContext.inObject()) {
            this._updateLocation();
            this._nextTokenNotInObject(n);
            return false;
        }
        this._updateNameLocation();
        if (n == 34) {
            final char[] quotedChars = serializableString.asQuotedChars();
            final int length = quotedChars.length;
            if (this._inputPtr + length + 4 < this._inputEnd) {
                final int n2 = this._inputPtr + length;
                if (this._inputBuffer[n2] == '\"') {
                    int n3 = 0;
                    int i;
                    for (i = this._inputPtr; i != n2; ++i) {
                        if (quotedChars[n3] != this._inputBuffer[i]) {
                            return this._isNextTokenNameMaybe(n, serializableString.getValue());
                        }
                        ++n3;
                    }
                    this._parsingContext.setCurrentName(serializableString.getValue());
                    this._isNextTokenNameYes(this._skipColonFast(i + 1));
                    return true;
                }
            }
        }
        return this._isNextTokenNameMaybe(n, serializableString.getValue());
    }
    
    @Override
    public String nextFieldName() throws IOException {
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nextAfterName();
            return null;
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int n = this._skipWSOrEnd();
        if (n < 0) {
            this.close();
            this._currToken = null;
            return null;
        }
        this._binaryValue = null;
        if (n == 93 || n == 125) {
            this._closeScope(n);
            return null;
        }
        if (this._parsingContext.expectComma()) {
            n = this._skipComma(n);
            if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (n == 93 || n == 125)) {
                this._closeScope(n);
                return null;
            }
        }
        if (!this._parsingContext.inObject()) {
            this._updateLocation();
            this._nextTokenNotInObject(n);
            return null;
        }
        this._updateNameLocation();
        final String currentName = (n == 34) ? this._parseName() : this._handleOddName(n);
        this._parsingContext.setCurrentName(currentName);
        this._currToken = JsonToken.FIELD_NAME;
        final int skipColon = this._skipColon();
        this._updateLocation();
        if (skipColon == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return currentName;
        }
        JsonToken nextToken = null;
        switch (skipColon) {
            case 45: {
                nextToken = this._parseNegNumber();
                break;
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                nextToken = this._parsePosNumber(skipColon);
                break;
            }
            case 102: {
                this._matchFalse();
                nextToken = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchNull();
                nextToken = JsonToken.VALUE_NULL;
                break;
            }
            case 116: {
                this._matchTrue();
                nextToken = JsonToken.VALUE_TRUE;
                break;
            }
            case 91: {
                nextToken = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                nextToken = JsonToken.START_OBJECT;
                break;
            }
            default: {
                nextToken = this._handleOddValue(skipColon);
                break;
            }
        }
        this._nextToken = nextToken;
        return currentName;
    }
    
    private final void _isNextTokenNameYes(final int n) throws IOException {
        this._currToken = JsonToken.FIELD_NAME;
        this._updateLocation();
        switch (n) {
            case 34: {
                this._tokenIncomplete = true;
                this._nextToken = JsonToken.VALUE_STRING;
            }
            case 91: {
                this._nextToken = JsonToken.START_ARRAY;
            }
            case 123: {
                this._nextToken = JsonToken.START_OBJECT;
            }
            case 116: {
                this._matchToken("true", 1);
                this._nextToken = JsonToken.VALUE_TRUE;
            }
            case 102: {
                this._matchToken("false", 1);
                this._nextToken = JsonToken.VALUE_FALSE;
            }
            case 110: {
                this._matchToken("null", 1);
                this._nextToken = JsonToken.VALUE_NULL;
            }
            case 45: {
                this._nextToken = this._parseNegNumber();
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                this._nextToken = this._parsePosNumber(n);
            }
            default: {
                this._nextToken = this._handleOddValue(n);
            }
        }
    }
    
    protected boolean _isNextTokenNameMaybe(int skipColon, final String s) throws IOException {
        final String currentName = (skipColon == 34) ? this._parseName() : this._handleOddName(skipColon);
        this._parsingContext.setCurrentName(currentName);
        this._currToken = JsonToken.FIELD_NAME;
        skipColon = this._skipColon();
        this._updateLocation();
        if (skipColon == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return s.equals(currentName);
        }
        JsonToken nextToken = null;
        switch (skipColon) {
            case 45: {
                nextToken = this._parseNegNumber();
                break;
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                nextToken = this._parsePosNumber(skipColon);
                break;
            }
            case 102: {
                this._matchFalse();
                nextToken = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchNull();
                nextToken = JsonToken.VALUE_NULL;
                break;
            }
            case 116: {
                this._matchTrue();
                nextToken = JsonToken.VALUE_TRUE;
                break;
            }
            case 91: {
                nextToken = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                nextToken = JsonToken.START_OBJECT;
                break;
            }
            default: {
                nextToken = this._handleOddValue(skipColon);
                break;
            }
        }
        this._nextToken = nextToken;
        return s.equals(currentName);
    }
    
    private final JsonToken _nextTokenNotInObject(final int n) throws IOException {
        if (n == 34) {
            this._tokenIncomplete = true;
            return this._currToken = JsonToken.VALUE_STRING;
        }
        switch (n) {
            case 91: {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return this._currToken = JsonToken.START_ARRAY;
            }
            case 123: {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                return this._currToken = JsonToken.START_OBJECT;
            }
            case 116: {
                this._matchToken("true", 1);
                return this._currToken = JsonToken.VALUE_TRUE;
            }
            case 102: {
                this._matchToken("false", 1);
                return this._currToken = JsonToken.VALUE_FALSE;
            }
            case 110: {
                this._matchToken("null", 1);
                return this._currToken = JsonToken.VALUE_NULL;
            }
            case 45: {
                return this._currToken = this._parseNegNumber();
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                return this._currToken = this._parsePosNumber(n);
            }
            case 44:
            case 93: {
                if (this.isEnabled(Feature.ALLOW_MISSING_VALUES)) {
                    --this._inputPtr;
                    return this._currToken = JsonToken.VALUE_NULL;
                }
                break;
            }
        }
        return this._currToken = this._handleOddValue(n);
    }
    
    @Override
    public final String nextTextValue() throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_STRING) ? this.getText() : null;
        }
        this._nameCopied = false;
        final JsonToken nextToken = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = nextToken) == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        if (nextToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (nextToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return null;
    }
    
    @Override
    public final int nextIntValue(final int n) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_NUMBER_INT) ? this.getIntValue() : n;
        }
        this._nameCopied = false;
        final JsonToken nextToken = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = nextToken) == JsonToken.VALUE_NUMBER_INT) {
            return this.getIntValue();
        }
        if (nextToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (nextToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return n;
    }
    
    @Override
    public final long nextLongValue(final long n) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_NUMBER_INT) ? this.getLongValue() : n;
        }
        this._nameCopied = false;
        final JsonToken nextToken = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = nextToken) == JsonToken.VALUE_NUMBER_INT) {
            return this.getLongValue();
        }
        if (nextToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (nextToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return n;
    }
    
    @Override
    public final Boolean nextBooleanValue() throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            final JsonToken nextToken = this.nextToken();
            if (nextToken != null) {
                final int id = nextToken.id();
                if (id == 9) {
                    return Boolean.TRUE;
                }
                if (id == 10) {
                    return Boolean.FALSE;
                }
            }
            return null;
        }
        this._nameCopied = false;
        final JsonToken nextToken2 = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = nextToken2) == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (nextToken2 == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        if (nextToken2 == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (nextToken2 == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return null;
    }
    
    protected final JsonToken _parsePosNumber(int n) throws IOException {
        int i = this._inputPtr;
        final int inputPtr = i - 1;
        final int inputEnd = this._inputEnd;
        if (n == 48) {
            return this._parseNumber2(false, inputPtr);
        }
        int n2 = 1;
        while (i < inputEnd) {
            n = this._inputBuffer[i++];
            if (n >= 48 && n <= 57) {
                ++n2;
            }
            else {
                if (n == 46 || n == 101 || n == 69) {
                    this._inputPtr = i;
                    return this._parseFloat(n, inputPtr, i, false, n2);
                }
                --i;
                this._inputPtr = i;
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(n);
                }
                this._textBuffer.resetWithShared(this._inputBuffer, inputPtr, i - inputPtr);
                return this.resetInt(false, n2);
            }
        }
        this._inputPtr = inputPtr;
        return this._parseNumber2(false, inputPtr);
    }
    
    private final JsonToken _parseFloat(int n, final int inputPtr, int i, final boolean b, final int n2) throws IOException {
        final int inputEnd = this._inputEnd;
        int n3 = 0;
        Label_0073: {
            if (n == 46) {
                while (i < inputEnd) {
                    n = this._inputBuffer[i++];
                    if (n >= 48 && n <= 57) {
                        ++n3;
                    }
                    else {
                        if (n3 == 0) {
                            this.reportUnexpectedNumberChar(n, "Decimal point not followed by a digit");
                        }
                        break Label_0073;
                    }
                }
                return this._parseNumber2(b, inputPtr);
            }
        }
        int n4 = 0;
        if (n == 101 || n == 69) {
            if (i >= inputEnd) {
                this._inputPtr = inputPtr;
                return this._parseNumber2(b, inputPtr);
            }
            n = this._inputBuffer[i++];
            if (n == 45 || n == 43) {
                if (i >= inputEnd) {
                    this._inputPtr = inputPtr;
                    return this._parseNumber2(b, inputPtr);
                }
                n = this._inputBuffer[i++];
            }
            while (n <= 57 && n >= 48) {
                ++n4;
                if (i >= inputEnd) {
                    this._inputPtr = inputPtr;
                    return this._parseNumber2(b, inputPtr);
                }
                n = this._inputBuffer[i++];
            }
            if (n4 == 0) {
                this.reportUnexpectedNumberChar(n, "Exponent indicator not followed by a digit");
            }
        }
        --i;
        this._inputPtr = i;
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace(n);
        }
        this._textBuffer.resetWithShared(this._inputBuffer, inputPtr, i - inputPtr);
        return this.resetFloat(b, n2, n3, n4);
    }
    
    protected final JsonToken _parseNegNumber() throws IOException {
        int i = this._inputPtr;
        final int n = i - 1;
        final int inputEnd = this._inputEnd;
        if (i >= inputEnd) {
            return this._parseNumber2(true, n);
        }
        final char c = this._inputBuffer[i++];
        if (c > '9' || c < '0') {
            this._inputPtr = i;
            return this._handleInvalidNumberStart(c, true);
        }
        if (c == '0') {
            return this._parseNumber2(true, n);
        }
        int n2 = 1;
        while (i < inputEnd) {
            final char c2 = this._inputBuffer[i++];
            if (c2 >= '0' && c2 <= '9') {
                ++n2;
            }
            else {
                if (c2 == '.' || c2 == 'e' || c2 == 'E') {
                    this._inputPtr = i;
                    return this._parseFloat(c2, n, i, true, n2);
                }
                --i;
                this._inputPtr = i;
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(c2);
                }
                this._textBuffer.resetWithShared(this._inputBuffer, n, i - n);
                return this.resetInt(true, n2);
            }
        }
        return this._parseNumber2(true, n);
    }
    
    private final JsonToken _parseNumber2(final boolean b, final int n) throws IOException {
        this._inputPtr = (b ? (n + 1) : n);
        char[] array = this._textBuffer.emptyAndGetCurrentSegment();
        int currentLength = 0;
        if (b) {
            array[currentLength++] = '-';
        }
        int n2 = 0;
        char verifyNoLeadingZeroes = (this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : this.getNextChar("No digit following minus sign", JsonToken.VALUE_NUMBER_INT);
        if (verifyNoLeadingZeroes == '0') {
            verifyNoLeadingZeroes = this._verifyNoLeadingZeroes();
        }
        boolean b2 = false;
        while (verifyNoLeadingZeroes >= '0' && verifyNoLeadingZeroes <= '9') {
            ++n2;
            if (currentLength >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentLength = 0;
            }
            array[currentLength++] = verifyNoLeadingZeroes;
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                verifyNoLeadingZeroes = '\0';
                b2 = true;
                break;
            }
            verifyNoLeadingZeroes = this._inputBuffer[this._inputPtr++];
        }
        if (n2 == 0) {
            return this._handleInvalidNumberStart(verifyNoLeadingZeroes, b);
        }
        int n3 = 0;
        Label_0348: {
            if (verifyNoLeadingZeroes == '.') {
                if (currentLength >= array.length) {
                    array = this._textBuffer.finishCurrentSegment();
                    currentLength = 0;
                }
                array[currentLength++] = verifyNoLeadingZeroes;
                while (true) {
                    while (this._inputPtr < this._inputEnd || this._loadMore()) {
                        verifyNoLeadingZeroes = this._inputBuffer[this._inputPtr++];
                        if (verifyNoLeadingZeroes >= '0') {
                            if (verifyNoLeadingZeroes <= '9') {
                                ++n3;
                                if (currentLength >= array.length) {
                                    array = this._textBuffer.finishCurrentSegment();
                                    currentLength = 0;
                                }
                                array[currentLength++] = verifyNoLeadingZeroes;
                                continue;
                            }
                        }
                        if (n3 == 0) {
                            this.reportUnexpectedNumberChar(verifyNoLeadingZeroes, "Decimal point not followed by a digit");
                        }
                        break Label_0348;
                    }
                    b2 = true;
                    continue;
                }
            }
        }
        int n4 = 0;
        if (verifyNoLeadingZeroes == 'e' || verifyNoLeadingZeroes == 'E') {
            if (currentLength >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentLength = 0;
            }
            array[currentLength++] = verifyNoLeadingZeroes;
            verifyNoLeadingZeroes = ((this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : this.getNextChar("expected a digit for number exponent"));
            if (verifyNoLeadingZeroes == '-' || verifyNoLeadingZeroes == '+') {
                if (currentLength >= array.length) {
                    array = this._textBuffer.finishCurrentSegment();
                    currentLength = 0;
                }
                array[currentLength++] = verifyNoLeadingZeroes;
                verifyNoLeadingZeroes = ((this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : this.getNextChar("expected a digit for number exponent"));
            }
            while (verifyNoLeadingZeroes <= '9' && verifyNoLeadingZeroes >= '0') {
                ++n4;
                if (currentLength >= array.length) {
                    array = this._textBuffer.finishCurrentSegment();
                    currentLength = 0;
                }
                array[currentLength++] = verifyNoLeadingZeroes;
                if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                    b2 = true;
                    break;
                }
                verifyNoLeadingZeroes = this._inputBuffer[this._inputPtr++];
            }
            if (n4 == 0) {
                this.reportUnexpectedNumberChar(verifyNoLeadingZeroes, "Exponent indicator not followed by a digit");
            }
        }
        if (!b2) {
            --this._inputPtr;
            if (this._parsingContext.inRoot()) {
                this._verifyRootSpace(verifyNoLeadingZeroes);
            }
        }
        this._textBuffer.setCurrentLength(currentLength);
        return this.reset(b, n2, n3, n4);
    }
    
    private final char _verifyNoLeadingZeroes() throws IOException {
        if (this._inputPtr < this._inputEnd) {
            final char c = this._inputBuffer[this._inputPtr];
            if (c < '0' || c > '9') {
                return '0';
            }
        }
        return this._verifyNLZ2();
    }
    
    private char _verifyNLZ2() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return '0';
        }
        char c = this._inputBuffer[this._inputPtr];
        if (c < '0' || c > '9') {
            return '0';
        }
        if (!this.isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        ++this._inputPtr;
        if (c == '0') {
            while (this._inputPtr < this._inputEnd || this._loadMore()) {
                c = this._inputBuffer[this._inputPtr];
                if (c < '0' || c > '9') {
                    return '0';
                }
                ++this._inputPtr;
                if (c != '0') {
                    break;
                }
            }
        }
        return c;
    }
    
    protected JsonToken _handleInvalidNumberStart(int n, final boolean b) throws IOException {
        if (n == 73) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
            }
            n = this._inputBuffer[this._inputPtr++];
            if (n == 78) {
                final String s = b ? "-INF" : "+INF";
                this._matchToken(s, 3);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN(s, b ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token '" + s + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
            else if (n == 110) {
                final String s2 = b ? "-Infinity" : "+Infinity";
                this._matchToken(s2, 3);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN(s2, b ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token '" + s2 + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
        }
        this.reportUnexpectedNumberChar(n, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }
    
    private final void _verifyRootSpace(final int n) throws IOException {
        ++this._inputPtr;
        switch (n) {
            case 9:
            case 32: {}
            case 13: {
                this._skipCR();
            }
            case 10: {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
            }
            default: {
                this._reportMissingRootWS(n);
            }
        }
    }
    
    protected final String _parseName() throws IOException {
        int i = this._inputPtr;
        int hashSeed = this._hashSeed;
        final int[] icLatin1 = ReaderBasedJsonParser._icLatin1;
        while (i < this._inputEnd) {
            final char c = this._inputBuffer[i];
            if (c < icLatin1.length && icLatin1[c] != 0) {
                if (c == '\"') {
                    final int inputPtr = this._inputPtr;
                    this._inputPtr = i + 1;
                    return this._symbols.findSymbol(this._inputBuffer, inputPtr, i - inputPtr, hashSeed);
                }
                break;
            }
            else {
                hashSeed = hashSeed * 33 + c;
                ++i;
            }
        }
        final int inputPtr2 = this._inputPtr;
        this._inputPtr = i;
        return this._parseName2(inputPtr2, hashSeed, 34);
    }
    
    private String _parseName2(final int n, int n2, final int n3) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, n, this._inputPtr - n);
        char[] array = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            final int n4;
            int decodeEscaped = n4 = this._inputBuffer[this._inputPtr++];
            if (n4 <= 92) {
                if (n4 == 92) {
                    decodeEscaped = this._decodeEscaped();
                }
                else if (n4 <= n3) {
                    if (n4 == n3) {
                        break;
                    }
                    if (n4 < 32) {
                        this._throwUnquotedSpace(n4, "name");
                    }
                }
            }
            n2 = n2 * 33 + decodeEscaped;
            array[currentSegmentSize++] = (char)decodeEscaped;
            if (currentSegmentSize >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
        }
        this._textBuffer.setCurrentLength(currentSegmentSize);
        final TextBuffer textBuffer = this._textBuffer;
        return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), n2);
    }
    
    protected String _handleOddName(final int n) throws IOException {
        if (n == 39 && this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
            return this._parseAposName();
        }
        if (!this.isEnabled(Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            this._reportUnexpectedChar(n, "was expecting double-quote to start field name");
        }
        final int[] inputCodeLatin1JsNames = CharTypes.getInputCodeLatin1JsNames();
        final int length = inputCodeLatin1JsNames.length;
        boolean javaIdentifierPart;
        if (n < length) {
            javaIdentifierPart = (inputCodeLatin1JsNames[n] == 0);
        }
        else {
            javaIdentifierPart = Character.isJavaIdentifierPart((char)n);
        }
        if (!javaIdentifierPart) {
            this._reportUnexpectedChar(n, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int inputPtr = this._inputPtr;
        int hashSeed = this._hashSeed;
        final int inputEnd = this._inputEnd;
        if (inputPtr < inputEnd) {
            do {
                final char c = this._inputBuffer[inputPtr];
                if (c < length) {
                    if (inputCodeLatin1JsNames[c] != 0) {
                        final int n2 = this._inputPtr - 1;
                        this._inputPtr = inputPtr;
                        return this._symbols.findSymbol(this._inputBuffer, n2, inputPtr - n2, hashSeed);
                    }
                }
                else if (!Character.isJavaIdentifierPart(c)) {
                    final int n3 = this._inputPtr - 1;
                    this._inputPtr = inputPtr;
                    return this._symbols.findSymbol(this._inputBuffer, n3, inputPtr - n3, hashSeed);
                }
                hashSeed = hashSeed * 33 + c;
            } while (++inputPtr < inputEnd);
        }
        final int n4 = this._inputPtr - 1;
        this._inputPtr = inputPtr;
        return this._handleOddName2(n4, hashSeed, inputCodeLatin1JsNames);
    }
    
    protected String _parseAposName() throws IOException {
        int inputPtr = this._inputPtr;
        int hashSeed = this._hashSeed;
        final int inputEnd = this._inputEnd;
        if (inputPtr < inputEnd) {
            final int[] icLatin1 = ReaderBasedJsonParser._icLatin1;
            final int length = icLatin1.length;
            do {
                final char c = this._inputBuffer[inputPtr];
                if (c == '\'') {
                    final int inputPtr2 = this._inputPtr;
                    this._inputPtr = inputPtr + 1;
                    return this._symbols.findSymbol(this._inputBuffer, inputPtr2, inputPtr - inputPtr2, hashSeed);
                }
                if (c < length && icLatin1[c] != 0) {
                    break;
                }
                hashSeed = hashSeed * 33 + c;
            } while (++inputPtr < inputEnd);
        }
        final int inputPtr3 = this._inputPtr;
        this._inputPtr = inputPtr;
        return this._parseName2(inputPtr3, hashSeed, 39);
    }
    
    protected JsonToken _handleOddValue(final int n) throws IOException {
        switch (n) {
            case 39: {
                if (this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
                    return this._handleApos();
                }
                break;
            }
            case 93: {
                if (!this._parsingContext.inArray()) {
                    break;
                }
            }
            case 44: {
                if (this.isEnabled(Feature.ALLOW_MISSING_VALUES)) {
                    --this._inputPtr;
                    return JsonToken.VALUE_NULL;
                }
                break;
            }
            case 78: {
                this._matchToken("NaN", 1);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN("NaN", Double.NaN);
                }
                this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                break;
            }
            case 73: {
                this._matchToken("Infinity", 1);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                break;
            }
            case 43: {
                if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                    this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
                }
                return this._handleInvalidNumberStart(this._inputBuffer[this._inputPtr++], false);
            }
        }
        if (Character.isJavaIdentifierStart(n)) {
            this._reportInvalidToken("" + (char)n, "('true', 'false' or 'null')");
        }
        this._reportUnexpectedChar(n, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
        return null;
    }
    
    protected JsonToken _handleApos() throws IOException {
        char[] array = this._textBuffer.emptyAndGetCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            final int n;
            int decodeEscaped = n = this._inputBuffer[this._inputPtr++];
            if (n <= 92) {
                if (n == 92) {
                    decodeEscaped = this._decodeEscaped();
                }
                else if (n <= 39) {
                    if (n == 39) {
                        break;
                    }
                    if (n < 32) {
                        this._throwUnquotedSpace(n, "string value");
                    }
                }
            }
            if (currentSegmentSize >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            array[currentSegmentSize++] = (char)decodeEscaped;
        }
        this._textBuffer.setCurrentLength(currentSegmentSize);
        return JsonToken.VALUE_STRING;
    }
    
    private String _handleOddName2(final int n, int n2, final int[] array) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, n, this._inputPtr - n);
        char[] array2 = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        final int length = array.length;
        while (true) {
            while (this._inputPtr < this._inputEnd || this._loadMore()) {
                final int n4;
                final int n3 = n4 = this._inputBuffer[this._inputPtr];
                if (n4 <= length) {
                    if (array[n4] != 0) {
                        break;
                    }
                }
                else if (!Character.isJavaIdentifierPart((char)n3)) {
                    break;
                }
                ++this._inputPtr;
                n2 = n2 * 33 + n4;
                array2[currentSegmentSize++] = (char)n3;
                if (currentSegmentSize < array2.length) {
                    continue;
                }
                array2 = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
                continue;
                this._textBuffer.setCurrentLength(currentSegmentSize);
                final TextBuffer textBuffer = this._textBuffer;
                return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), n2);
            }
            continue;
        }
    }
    
    @Override
    protected final void _finishString() throws IOException {
        int inputPtr = this._inputPtr;
        final int inputEnd = this._inputEnd;
        if (inputPtr < inputEnd) {
            final int[] icLatin1 = ReaderBasedJsonParser._icLatin1;
            final int length = icLatin1.length;
            do {
                final char c = this._inputBuffer[inputPtr];
                if (c < length && icLatin1[c] != 0) {
                    if (c == '\"') {
                        this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, inputPtr - this._inputPtr);
                        this._inputPtr = inputPtr + 1;
                        return;
                    }
                    break;
                }
            } while (++inputPtr < inputEnd);
        }
        this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, inputPtr - this._inputPtr);
        this._inputPtr = inputPtr;
        this._finishString2();
    }
    
    protected void _finishString2() throws IOException {
        char[] array = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        final int[] icLatin1 = ReaderBasedJsonParser._icLatin1;
        final int length = icLatin1.length;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            final int n;
            int decodeEscaped = n = this._inputBuffer[this._inputPtr++];
            if (n < length && icLatin1[n] != 0) {
                if (n == 34) {
                    break;
                }
                if (n == 92) {
                    decodeEscaped = this._decodeEscaped();
                }
                else if (n < 32) {
                    this._throwUnquotedSpace(n, "string value");
                }
            }
            if (currentSegmentSize >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            array[currentSegmentSize++] = (char)decodeEscaped;
        }
        this._textBuffer.setCurrentLength(currentSegmentSize);
    }
    
    protected final void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int n = this._inputPtr;
        int n2 = this._inputEnd;
        final char[] inputBuffer = this._inputBuffer;
        while (true) {
            if (n >= n2) {
                this._inputPtr = n;
                if (!this._loadMore()) {
                    this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
                }
                n = this._inputPtr;
                n2 = this._inputEnd;
            }
            final char c = inputBuffer[n++];
            if (c <= '\\') {
                if (c == '\\') {
                    this._inputPtr = n;
                    this._decodeEscaped();
                    n = this._inputPtr;
                    n2 = this._inputEnd;
                }
                else {
                    if (c > '\"') {
                        continue;
                    }
                    if (c == '\"') {
                        break;
                    }
                    if (c >= ' ') {
                        continue;
                    }
                    this._inputPtr = n;
                    this._throwUnquotedSpace(c, "string value");
                }
            }
        }
        this._inputPtr = n;
    }
    
    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this._loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
            ++this._inputPtr;
        }
        ++this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }
    
    private final int _skipColon() throws IOException {
        if (this._inputPtr + 4 >= this._inputEnd) {
            return this._skipColon2(false);
        }
        char c = this._inputBuffer[this._inputPtr];
        if (c == ':') {
            final char c2 = this._inputBuffer[++this._inputPtr];
            if (c2 <= ' ') {
                if (c2 == ' ' || c2 == '\t') {
                    final char c3 = this._inputBuffer[++this._inputPtr];
                    if (c3 > ' ') {
                        if (c3 == '/' || c3 == '#') {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return c3;
                    }
                }
                return this._skipColon2(true);
            }
            if (c2 == '/' || c2 == '#') {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return c2;
        }
        else {
            if (c == ' ' || c == '\t') {
                c = this._inputBuffer[++this._inputPtr];
            }
            if (c != ':') {
                return this._skipColon2(false);
            }
            final char c4 = this._inputBuffer[++this._inputPtr];
            if (c4 <= ' ') {
                if (c4 == ' ' || c4 == '\t') {
                    final char c5 = this._inputBuffer[++this._inputPtr];
                    if (c5 > ' ') {
                        if (c5 == '/' || c5 == '#') {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return c5;
                    }
                }
                return this._skipColon2(true);
            }
            if (c4 == '/' || c4 == '#') {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return c4;
        }
    }
    
    private final int _skipColon2(boolean b) throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final char c = this._inputBuffer[this._inputPtr++];
            if (c > ' ') {
                if (c == '/') {
                    this._skipComment();
                }
                else {
                    if (c == '#' && this._skipYAMLComment()) {
                        continue;
                    }
                    if (b) {
                        return c;
                    }
                    if (c != ':') {
                        this._reportUnexpectedChar(c, "was expecting a colon to separate field name and value");
                    }
                    b = true;
                }
            }
            else {
                if (c >= ' ') {
                    continue;
                }
                if (c == '\n') {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (c == '\r') {
                    this._skipCR();
                }
                else {
                    if (c == '\t') {
                        continue;
                    }
                    this._throwInvalidSpace(c);
                }
            }
        }
        this._reportInvalidEOF(" within/between " + this._parsingContext.typeDesc() + " entries", null);
        return -1;
    }
    
    private final int _skipColonFast(int n) throws IOException {
        char c = this._inputBuffer[n++];
        if (c == ':') {
            final char c2 = this._inputBuffer[n++];
            if (c2 > ' ') {
                if (c2 != '/' && c2 != '#') {
                    this._inputPtr = n;
                    return c2;
                }
            }
            else if (c2 == ' ' || c2 == '\t') {
                final char c3 = this._inputBuffer[n++];
                if (c3 > ' ' && c3 != '/' && c3 != '#') {
                    this._inputPtr = n;
                    return c3;
                }
            }
            this._inputPtr = n - 1;
            return this._skipColon2(true);
        }
        if (c == ' ' || c == '\t') {
            c = this._inputBuffer[n++];
        }
        final boolean b = c == ':';
        if (b) {
            final char c4 = this._inputBuffer[n++];
            if (c4 > ' ') {
                if (c4 != '/' && c4 != '#') {
                    this._inputPtr = n;
                    return c4;
                }
            }
            else if (c4 == ' ' || c4 == '\t') {
                final char c5 = this._inputBuffer[n++];
                if (c5 > ' ' && c5 != '/' && c5 != '#') {
                    this._inputPtr = n;
                    return c5;
                }
            }
        }
        this._inputPtr = n - 1;
        return this._skipColon2(b);
    }
    
    private final int _skipComma(int n) throws IOException {
        if (n != 44) {
            this._reportUnexpectedChar(n, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
        }
        while (this._inputPtr < this._inputEnd) {
            n = this._inputBuffer[this._inputPtr++];
            if (n > 32) {
                if (n == 47 || n == 35) {
                    --this._inputPtr;
                    return this._skipAfterComma2();
                }
                return n;
            }
            else {
                if (n >= 32) {
                    continue;
                }
                if (n == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (n == 13) {
                    this._skipCR();
                }
                else {
                    if (n == 9) {
                        continue;
                    }
                    this._throwInvalidSpace(n);
                }
            }
        }
        return this._skipAfterComma2();
    }
    
    private final int _skipAfterComma2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final char c = this._inputBuffer[this._inputPtr++];
            if (c > ' ') {
                if (c == '/') {
                    this._skipComment();
                }
                else {
                    if (c == '#' && this._skipYAMLComment()) {
                        continue;
                    }
                    return c;
                }
            }
            else {
                if (c >= ' ') {
                    continue;
                }
                if (c == '\n') {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (c == '\r') {
                    this._skipCR();
                }
                else {
                    if (c == '\t') {
                        continue;
                    }
                    this._throwInvalidSpace(c);
                }
            }
        }
        throw this._constructError("Unexpected end-of-input within/between " + this._parsingContext.typeDesc() + " entries");
    }
    
    private final int _skipWSOrEnd() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return this._eofAsNextChar();
        }
        final char c = this._inputBuffer[this._inputPtr++];
        if (c <= ' ') {
            if (c != ' ') {
                if (c == '\n') {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (c == '\r') {
                    this._skipCR();
                }
                else if (c != '\t') {
                    this._throwInvalidSpace(c);
                }
            }
            while (this._inputPtr < this._inputEnd) {
                final char c2 = this._inputBuffer[this._inputPtr++];
                if (c2 > ' ') {
                    if (c2 == '/' || c2 == '#') {
                        --this._inputPtr;
                        return this._skipWSOrEnd2();
                    }
                    return c2;
                }
                else {
                    if (c2 == ' ') {
                        continue;
                    }
                    if (c2 == '\n') {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                    }
                    else if (c2 == '\r') {
                        this._skipCR();
                    }
                    else {
                        if (c2 == '\t') {
                            continue;
                        }
                        this._throwInvalidSpace(c2);
                    }
                }
            }
            return this._skipWSOrEnd2();
        }
        if (c == '/' || c == '#') {
            --this._inputPtr;
            return this._skipWSOrEnd2();
        }
        return c;
    }
    
    private int _skipWSOrEnd2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final char c = this._inputBuffer[this._inputPtr++];
            if (c > ' ') {
                if (c == '/') {
                    this._skipComment();
                }
                else {
                    if (c == '#' && this._skipYAMLComment()) {
                        continue;
                    }
                    return c;
                }
            }
            else {
                if (c == ' ') {
                    continue;
                }
                if (c == '\n') {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (c == '\r') {
                    this._skipCR();
                }
                else {
                    if (c == '\t') {
                        continue;
                    }
                    this._throwInvalidSpace(c);
                }
            }
        }
        return this._eofAsNextChar();
    }
    
    private void _skipComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_COMMENTS)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in a comment", null);
        }
        final char c = this._inputBuffer[this._inputPtr++];
        if (c == '/') {
            this._skipLine();
        }
        else if (c == '*') {
            this._skipCComment();
        }
        else {
            this._reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
        }
    }
    
    private void _skipCComment() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final char c = this._inputBuffer[this._inputPtr++];
            if (c <= '*') {
                if (c == '*') {
                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                        break;
                    }
                    if (this._inputBuffer[this._inputPtr] == '/') {
                        ++this._inputPtr;
                        return;
                    }
                    continue;
                }
                else {
                    if (c >= ' ') {
                        continue;
                    }
                    if (c == '\n') {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                    }
                    else if (c == '\r') {
                        this._skipCR();
                    }
                    else {
                        if (c == '\t') {
                            continue;
                        }
                        this._throwInvalidSpace(c);
                    }
                }
            }
        }
        this._reportInvalidEOF(" in a comment", null);
    }
    
    private boolean _skipYAMLComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        this._skipLine();
        return true;
    }
    
    private void _skipLine() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final char c = this._inputBuffer[this._inputPtr++];
            if (c < ' ') {
                if (c == '\n') {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                    break;
                }
                if (c == '\r') {
                    this._skipCR();
                    break;
                }
                if (c == '\t') {
                    continue;
                }
                this._throwInvalidSpace(c);
            }
        }
    }
    
    @Override
    protected char _decodeEscaped() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
        }
        final char c = this._inputBuffer[this._inputPtr++];
        switch (c) {
            case 98: {
                return '\b';
            }
            case 116: {
                return '\t';
            }
            case 110: {
                return '\n';
            }
            case 102: {
                return '\f';
            }
            case 114: {
                return '\r';
            }
            case 34:
            case 47:
            case 92: {
                return c;
            }
            case 117: {
                int n = 0;
                for (int i = 0; i < 4; ++i) {
                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                        this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
                    }
                    final char c2 = this._inputBuffer[this._inputPtr++];
                    final int charToHex = CharTypes.charToHex(c2);
                    if (charToHex < 0) {
                        this._reportUnexpectedChar(c2, "expected a hex-digit for character escape sequence");
                    }
                    n = (n << 4 | charToHex);
                }
                return (char)n;
            }
            default: {
                return this._handleUnrecognizedCharacterEscape(c);
            }
        }
    }
    
    private final void _matchTrue() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 3 < this._inputEnd) {
            final char[] inputBuffer = this._inputBuffer;
            if (inputBuffer[inputPtr] == 'r' && inputBuffer[++inputPtr] == 'u' && inputBuffer[++inputPtr] == 'e') {
                final char c = inputBuffer[++inputPtr];
                if (c < '0' || c == ']' || c == '}') {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken("true", 1);
    }
    
    private final void _matchFalse() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 4 < this._inputEnd) {
            final char[] inputBuffer = this._inputBuffer;
            if (inputBuffer[inputPtr] == 'a' && inputBuffer[++inputPtr] == 'l' && inputBuffer[++inputPtr] == 's' && inputBuffer[++inputPtr] == 'e') {
                final char c = inputBuffer[++inputPtr];
                if (c < '0' || c == ']' || c == '}') {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken("false", 1);
    }
    
    private final void _matchNull() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 3 < this._inputEnd) {
            final char[] inputBuffer = this._inputBuffer;
            if (inputBuffer[inputPtr] == 'u' && inputBuffer[++inputPtr] == 'l' && inputBuffer[++inputPtr] == 'l') {
                final char c = inputBuffer[++inputPtr];
                if (c < '0' || c == ']' || c == '}') {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken("null", 1);
    }
    
    protected final void _matchToken(final String s, int n) throws IOException {
        final int length = s.length();
        if (this._inputPtr + length >= this._inputEnd) {
            this._matchToken2(s, n);
            return;
        }
        do {
            if (this._inputBuffer[this._inputPtr] != s.charAt(n)) {
                this._reportInvalidToken(s.substring(0, n));
            }
            ++this._inputPtr;
        } while (++n < length);
        final char c = this._inputBuffer[this._inputPtr];
        if (c >= '0' && c != ']' && c != '}') {
            this._checkMatchEnd(s, n, c);
        }
    }
    
    private final void _matchToken2(final String s, int n) throws IOException {
        do {
            if ((this._inputPtr >= this._inputEnd && !this._loadMore()) || this._inputBuffer[this._inputPtr] != s.charAt(n)) {
                this._reportInvalidToken(s.substring(0, n));
            }
            ++this._inputPtr;
        } while (++n < s.length());
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return;
        }
        final char c = this._inputBuffer[this._inputPtr];
        if (c >= '0' && c != ']' && c != '}') {
            this._checkMatchEnd(s, n, c);
        }
    }
    
    private final void _checkMatchEnd(final String s, final int n, final int n2) throws IOException {
        if (Character.isJavaIdentifierPart((char)n2)) {
            this._reportInvalidToken(s.substring(0, n));
        }
    }
    
    protected byte[] _decodeBase64(final Base64Variant base64Variant) throws IOException {
        final ByteArrayBuilder getByteArrayBuilder = this._getByteArrayBuilder();
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            final char c = this._inputBuffer[this._inputPtr++];
            if (c > ' ') {
                int n = base64Variant.decodeBase64Char(c);
                if (n < 0) {
                    if (c == '\"') {
                        return getByteArrayBuilder.toByteArray();
                    }
                    n = this._decodeBase64Escape(base64Variant, c, 0);
                    if (n < 0) {
                        continue;
                    }
                }
                final int n2 = n;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final char c2 = this._inputBuffer[this._inputPtr++];
                int n3 = base64Variant.decodeBase64Char(c2);
                if (n3 < 0) {
                    n3 = this._decodeBase64Escape(base64Variant, c2, 1);
                }
                final int n4 = n2 << 6 | n3;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final char c3 = this._inputBuffer[this._inputPtr++];
                int n5 = base64Variant.decodeBase64Char(c3);
                if (n5 < 0) {
                    if (n5 != -2) {
                        if (c3 == '\"' && !base64Variant.usesPadding()) {
                            getByteArrayBuilder.append(n4 >> 4);
                            return getByteArrayBuilder.toByteArray();
                        }
                        n5 = this._decodeBase64Escape(base64Variant, c3, 2);
                    }
                    if (n5 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            this._loadMoreGuaranteed();
                        }
                        final char c4 = this._inputBuffer[this._inputPtr++];
                        if (!base64Variant.usesPaddingChar(c4)) {
                            throw this.reportInvalidBase64Char(base64Variant, c4, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                        }
                        getByteArrayBuilder.append(n4 >> 4);
                        continue;
                    }
                }
                final int n6 = n4 << 6 | n5;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final char c5 = this._inputBuffer[this._inputPtr++];
                int n7 = base64Variant.decodeBase64Char(c5);
                if (n7 < 0) {
                    if (n7 != -2) {
                        if (c5 == '\"' && !base64Variant.usesPadding()) {
                            getByteArrayBuilder.appendTwoBytes(n6 >> 2);
                            return getByteArrayBuilder.toByteArray();
                        }
                        n7 = this._decodeBase64Escape(base64Variant, c5, 3);
                    }
                    if (n7 == -2) {
                        getByteArrayBuilder.appendTwoBytes(n6 >> 2);
                        continue;
                    }
                }
                getByteArrayBuilder.appendThreeBytes(n6 << 6 | n7);
            }
        }
    }
    
    @Override
    public JsonLocation getTokenLocation() {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return new JsonLocation(this._getSourceReference(), -1L, this._currInputProcessed + (this._nameStartOffset - 1L), this._nameStartRow, this._nameStartCol);
        }
        return new JsonLocation(this._getSourceReference(), -1L, this._tokenInputTotal - 1L, this._tokenInputRow, this._tokenInputCol);
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        return new JsonLocation(this._getSourceReference(), -1L, this._currInputProcessed + this._inputPtr, this._currInputRow, this._inputPtr - this._currInputRowStart + 1);
    }
    
    private final void _updateLocation() {
        final int inputPtr = this._inputPtr;
        this._tokenInputTotal = this._currInputProcessed + inputPtr;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = inputPtr - this._currInputRowStart;
    }
    
    private final void _updateNameLocation() {
        final int inputPtr = this._inputPtr;
        this._nameStartOffset = inputPtr;
        this._nameStartRow = this._currInputRow;
        this._nameStartCol = inputPtr - this._currInputRowStart;
    }
    
    protected void _reportInvalidToken(final String s) throws IOException {
        this._reportInvalidToken(s, "'null', 'true', 'false' or NaN");
    }
    
    protected void _reportInvalidToken(final String s, final String s2) throws IOException {
        final StringBuilder sb = new StringBuilder(s);
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final char c = this._inputBuffer[this._inputPtr];
            if (!Character.isJavaIdentifierPart(c)) {
                break;
            }
            ++this._inputPtr;
            sb.append(c);
            if (sb.length() >= 256) {
                sb.append("...");
                break;
            }
        }
        this._reportError("Unrecognized token '%s': was expecting %s", sb, s2);
    }
    
    private void _closeScope(final int n) throws JsonParseException {
        if (n == 93) {
            this._updateLocation();
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(n, '}');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_ARRAY;
        }
        if (n == 125) {
            this._updateLocation();
            if (!this._parsingContext.inObject()) {
                this._reportMismatchedEndMarker(n, ']');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_OBJECT;
        }
    }
    
    static {
        FEAT_MASK_TRAILING_COMMA = Feature.ALLOW_TRAILING_COMMA.getMask();
        _icLatin1 = CharTypes.getInputCodeLatin1();
    }
}
