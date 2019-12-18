package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.sym.*;
import java.io.*;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.*;

public class UTF8StreamJsonParser extends ParserBase
{
    static final byte BYTE_LF = 10;
    private static final int[] _icUTF8;
    protected static final int[] _icLatin1;
    protected static final int FEAT_MASK_TRAILING_COMMA;
    protected ObjectCodec _objectCodec;
    protected final ByteQuadsCanonicalizer _symbols;
    protected int[] _quadBuffer;
    protected boolean _tokenIncomplete;
    private int _quad1;
    protected int _nameStartOffset;
    protected int _nameStartRow;
    protected int _nameStartCol;
    protected InputStream _inputStream;
    protected byte[] _inputBuffer;
    protected boolean _bufferRecyclable;
    
    public UTF8StreamJsonParser(final IOContext ioContext, final int n, final InputStream inputStream, final ObjectCodec objectCodec, final ByteQuadsCanonicalizer symbols, final byte[] inputBuffer, final int n2, final int inputEnd, final boolean bufferRecyclable) {
        super(ioContext, n);
        this._quadBuffer = new int[16];
        this._inputStream = inputStream;
        this._objectCodec = objectCodec;
        this._symbols = symbols;
        this._inputBuffer = inputBuffer;
        this._inputPtr = n2;
        this._inputEnd = inputEnd;
        this._currInputRowStart = n2;
        this._currInputProcessed = -n2;
        this._bufferRecyclable = bufferRecyclable;
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
    public int releaseBuffered(final OutputStream outputStream) throws IOException {
        final int n = this._inputEnd - this._inputPtr;
        if (n < 1) {
            return 0;
        }
        outputStream.write(this._inputBuffer, this._inputPtr, n);
        return n;
    }
    
    @Override
    public Object getInputSource() {
        return this._inputStream;
    }
    
    protected final boolean _loadMore() throws IOException {
        final int inputEnd = this._inputEnd;
        this._currInputProcessed += this._inputEnd;
        this._currInputRowStart -= this._inputEnd;
        this._nameStartOffset -= inputEnd;
        if (this._inputStream != null) {
            final int length = this._inputBuffer.length;
            if (length == 0) {
                return false;
            }
            final int read = this._inputStream.read(this._inputBuffer, 0, length);
            if (read > 0) {
                this._inputPtr = 0;
                this._inputEnd = read;
                return true;
            }
            this._closeInput();
            if (read == 0) {
                throw new IOException("InputStream.read() returned 0 characters when trying to read " + this._inputBuffer.length + " bytes");
            }
        }
        return false;
    }
    
    @Override
    protected void _closeInput() throws IOException {
        if (this._inputStream != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(Feature.AUTO_CLOSE_SOURCE)) {
                this._inputStream.close();
            }
            this._inputStream = null;
        }
    }
    
    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
        if (this._bufferRecyclable) {
            final byte[] inputBuffer = this._inputBuffer;
            if (inputBuffer != null) {
                this._inputBuffer = UTF8StreamJsonParser.NO_BYTES;
                this._ioContext.releaseReadIOBuffer(inputBuffer);
            }
        }
    }
    
    @Override
    public String getText() throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            return this._getText2(this._currToken);
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
        }
        return this._textBuffer.contentsAsString();
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
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                return this._finishAndReturnString();
            }
            return this._textBuffer.contentsAsString();
        }
        else {
            if (this._currToken == JsonToken.FIELD_NAME) {
                return this.getCurrentName();
            }
            return super.getValueAsString(null);
        }
    }
    
    @Override
    public String getValueAsString(final String s) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                return this._finishAndReturnString();
            }
            return this._textBuffer.contentsAsString();
        }
        else {
            if (this._currToken == JsonToken.FIELD_NAME) {
                return this.getCurrentName();
            }
            return super.getValueAsString(s);
        }
    }
    
    @Override
    public int getValueAsInt() throws IOException {
        final JsonToken currToken = this._currToken;
        if (currToken == JsonToken.VALUE_NUMBER_INT || currToken == JsonToken.VALUE_NUMBER_FLOAT) {
            if ((this._numTypesValid & 0x1) == 0x0) {
                if (this._numTypesValid == 0) {
                    return this._parseIntValue();
                }
                if ((this._numTypesValid & 0x1) == 0x0) {
                    this.convertNumberToInt();
                }
            }
            return this._numberInt;
        }
        return super.getValueAsInt(0);
    }
    
    @Override
    public int getValueAsInt(final int n) throws IOException {
        final JsonToken currToken = this._currToken;
        if (currToken == JsonToken.VALUE_NUMBER_INT || currToken == JsonToken.VALUE_NUMBER_FLOAT) {
            if ((this._numTypesValid & 0x1) == 0x0) {
                if (this._numTypesValid == 0) {
                    return this._parseIntValue();
                }
                if ((this._numTypesValid & 0x1) == 0x0) {
                    this.convertNumberToInt();
                }
            }
            return this._numberInt;
        }
        return super.getValueAsInt(n);
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
    public int getTextLength() throws IOException {
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
    public int getTextOffset() throws IOException {
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
        if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
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
            final int n4 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (n4 > 32) {
                int n5 = base64Variant.decodeBase64Char(n4);
                if (n5 < 0) {
                    if (n4 == 34) {
                        break;
                    }
                    n5 = this._decodeBase64Escape(base64Variant, n4, 0);
                    if (n5 < 0) {
                        continue;
                    }
                }
                if (n > n2) {
                    n3 += n;
                    outputStream.write(array, 0, n);
                    n = 0;
                }
                final int n6 = n5;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final int n7 = this._inputBuffer[this._inputPtr++] & 0xFF;
                int n8 = base64Variant.decodeBase64Char(n7);
                if (n8 < 0) {
                    n8 = this._decodeBase64Escape(base64Variant, n7, 1);
                }
                final int n9 = n6 << 6 | n8;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final int n10 = this._inputBuffer[this._inputPtr++] & 0xFF;
                int n11 = base64Variant.decodeBase64Char(n10);
                if (n11 < 0) {
                    if (n11 != -2) {
                        if (n10 == 34 && !base64Variant.usesPadding()) {
                            array[n++] = (byte)(n9 >> 4);
                            break;
                        }
                        n11 = this._decodeBase64Escape(base64Variant, n10, 2);
                    }
                    if (n11 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            this._loadMoreGuaranteed();
                        }
                        final int n12 = this._inputBuffer[this._inputPtr++] & 0xFF;
                        if (!base64Variant.usesPaddingChar(n12)) {
                            throw this.reportInvalidBase64Char(base64Variant, n12, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                        }
                        array[n++] = (byte)(n9 >> 4);
                        continue;
                    }
                }
                final int n13 = n9 << 6 | n11;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final int n14 = this._inputBuffer[this._inputPtr++] & 0xFF;
                int n15 = base64Variant.decodeBase64Char(n14);
                if (n15 < 0) {
                    if (n15 != -2) {
                        if (n14 == 34 && !base64Variant.usesPadding()) {
                            final int n16 = n13 >> 2;
                            array[n++] = (byte)(n16 >> 8);
                            array[n++] = (byte)n16;
                            break;
                        }
                        n15 = this._decodeBase64Escape(base64Variant, n14, 3);
                    }
                    if (n15 == -2) {
                        final int n17 = n13 >> 2;
                        array[n++] = (byte)(n17 >> 8);
                        array[n++] = (byte)n17;
                        continue;
                    }
                }
                final int n18 = n13 << 6 | n15;
                array[n++] = (byte)(n18 >> 16);
                array[n++] = (byte)(n18 >> 8);
                array[n++] = (byte)n18;
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
    public JsonToken nextToken() throws IOException {
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
        if (n == 93) {
            this._closeArrayScope();
            return this._currToken = JsonToken.END_ARRAY;
        }
        if (n == 125) {
            this._closeObjectScope();
            return this._currToken = JsonToken.END_OBJECT;
        }
        if (this._parsingContext.expectComma()) {
            if (n != 44) {
                this._reportUnexpectedChar(n, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            n = this._skipWS();
            if ((this._features & UTF8StreamJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (n == 93 || n == 125)) {
                return this._closeScope(n);
            }
        }
        if (!this._parsingContext.inObject()) {
            this._updateLocation();
            return this._nextTokenNotInObject(n);
        }
        this._updateNameLocation();
        this._parsingContext.setCurrentName(this._parseName(n));
        this._currToken = JsonToken.FIELD_NAME;
        final int skipColon = this._skipColon();
        this._updateLocation();
        if (skipColon == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return this._currToken;
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
                nextToken = this._handleUnexpectedValue(skipColon);
                break;
            }
        }
        this._nextToken = nextToken;
        return this._currToken;
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
                this._matchTrue();
                return this._currToken = JsonToken.VALUE_TRUE;
            }
            case 102: {
                this._matchFalse();
                return this._currToken = JsonToken.VALUE_FALSE;
            }
            case 110: {
                this._matchNull();
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
            default: {
                return this._currToken = this._handleUnexpectedValue(n);
            }
        }
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
        if (n == 93) {
            this._closeArrayScope();
            this._currToken = JsonToken.END_ARRAY;
            return false;
        }
        if (n == 125) {
            this._closeObjectScope();
            this._currToken = JsonToken.END_OBJECT;
            return false;
        }
        if (this._parsingContext.expectComma()) {
            if (n != 44) {
                this._reportUnexpectedChar(n, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            n = this._skipWS();
            if ((this._features & UTF8StreamJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (n == 93 || n == 125)) {
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
            final byte[] quotedUTF8 = serializableString.asQuotedUTF8();
            final int length = quotedUTF8.length;
            if (this._inputPtr + length + 4 < this._inputEnd) {
                final int n2 = this._inputPtr + length;
                if (this._inputBuffer[n2] == 34) {
                    int n3 = 0;
                    int i;
                    for (i = this._inputPtr; i != n2; ++i) {
                        if (quotedUTF8[n3] != this._inputBuffer[i]) {
                            return this._isNextTokenNameMaybe(n, serializableString);
                        }
                        ++n3;
                    }
                    this._parsingContext.setCurrentName(serializableString.getValue());
                    this._isNextTokenNameYes(this._skipColonFast(i + 1));
                    return true;
                }
            }
        }
        return this._isNextTokenNameMaybe(n, serializableString);
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
        if (n == 93) {
            this._closeArrayScope();
            this._currToken = JsonToken.END_ARRAY;
            return null;
        }
        if (n == 125) {
            this._closeObjectScope();
            this._currToken = JsonToken.END_OBJECT;
            return null;
        }
        if (this._parsingContext.expectComma()) {
            if (n != 44) {
                this._reportUnexpectedChar(n, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            n = this._skipWS();
            if ((this._features & UTF8StreamJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (n == 93 || n == 125)) {
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
        final String parseName = this._parseName(n);
        this._parsingContext.setCurrentName(parseName);
        this._currToken = JsonToken.FIELD_NAME;
        final int skipColon = this._skipColon();
        this._updateLocation();
        if (skipColon == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return parseName;
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
                nextToken = this._handleUnexpectedValue(skipColon);
                break;
            }
        }
        this._nextToken = nextToken;
        return parseName;
    }
    
    private final int _skipColonFast(int n) throws IOException {
        byte b = this._inputBuffer[n++];
        if (b == 58) {
            final byte b2 = this._inputBuffer[n++];
            if (b2 > 32) {
                if (b2 != 47 && b2 != 35) {
                    this._inputPtr = n;
                    return b2;
                }
            }
            else if (b2 == 32 || b2 == 9) {
                final byte b3 = this._inputBuffer[n++];
                if (b3 > 32 && b3 != 47 && b3 != 35) {
                    this._inputPtr = n;
                    return b3;
                }
            }
            this._inputPtr = n - 1;
            return this._skipColon2(true);
        }
        if (b == 32 || b == 9) {
            b = this._inputBuffer[n++];
        }
        if (b == 58) {
            final byte b4 = this._inputBuffer[n++];
            if (b4 > 32) {
                if (b4 != 47 && b4 != 35) {
                    this._inputPtr = n;
                    return b4;
                }
            }
            else if (b4 == 32 || b4 == 9) {
                final byte b5 = this._inputBuffer[n++];
                if (b5 > 32 && b5 != 47 && b5 != 35) {
                    this._inputPtr = n;
                    return b5;
                }
            }
            this._inputPtr = n - 1;
            return this._skipColon2(true);
        }
        this._inputPtr = n - 1;
        return this._skipColon2(false);
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
                this._matchTrue();
                this._nextToken = JsonToken.VALUE_TRUE;
            }
            case 102: {
                this._matchFalse();
                this._nextToken = JsonToken.VALUE_FALSE;
            }
            case 110: {
                this._matchNull();
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
                this._nextToken = this._handleUnexpectedValue(n);
            }
        }
    }
    
    private final boolean _isNextTokenNameMaybe(int skipColon, final SerializableString serializableString) throws IOException {
        final String parseName = this._parseName(skipColon);
        this._parsingContext.setCurrentName(parseName);
        final boolean equals = parseName.equals(serializableString.getValue());
        this._currToken = JsonToken.FIELD_NAME;
        skipColon = this._skipColon();
        this._updateLocation();
        if (skipColon == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return equals;
        }
        JsonToken nextToken = null;
        switch (skipColon) {
            case 91: {
                nextToken = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                nextToken = JsonToken.START_OBJECT;
                break;
            }
            case 116: {
                this._matchTrue();
                nextToken = JsonToken.VALUE_TRUE;
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
            default: {
                nextToken = this._handleUnexpectedValue(skipColon);
                break;
            }
        }
        this._nextToken = nextToken;
        return equals;
    }
    
    @Override
    public String nextTextValue() throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_STRING) ? this.getText() : null;
        }
        this._nameCopied = false;
        final JsonToken nextToken = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = nextToken) != JsonToken.VALUE_STRING) {
            if (nextToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            }
            else if (nextToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }
            return null;
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
        }
        return this._textBuffer.contentsAsString();
    }
    
    @Override
    public int nextIntValue(final int n) throws IOException {
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
    public long nextLongValue(final long n) throws IOException {
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
    public Boolean nextBooleanValue() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            final JsonToken nextToken = this._nextToken;
            this._nextToken = null;
            if ((this._currToken = nextToken) == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (nextToken == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            if (nextToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            }
            else if (nextToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }
            return null;
        }
        else {
            final JsonToken nextToken2 = this.nextToken();
            if (nextToken2 == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (nextToken2 == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            return null;
        }
    }
    
    protected JsonToken _parsePosNumber(int verifyNoLeadingZeroes) throws IOException {
        final char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        if (verifyNoLeadingZeroes == 48) {
            verifyNoLeadingZeroes = this._verifyNoLeadingZeroes();
        }
        emptyAndGetCurrentSegment[0] = (char)verifyNoLeadingZeroes;
        int n = 1;
        int currentLength = 1;
        while (this._inputPtr < Math.min(this._inputEnd, this._inputPtr + emptyAndGetCurrentSegment.length - 1)) {
            verifyNoLeadingZeroes = (this._inputBuffer[this._inputPtr++] & 0xFF);
            if (verifyNoLeadingZeroes >= 48 && verifyNoLeadingZeroes <= 57) {
                ++n;
                emptyAndGetCurrentSegment[currentLength++] = (char)verifyNoLeadingZeroes;
            }
            else {
                if (verifyNoLeadingZeroes == 46 || verifyNoLeadingZeroes == 101 || verifyNoLeadingZeroes == 69) {
                    return this._parseFloat(emptyAndGetCurrentSegment, currentLength, verifyNoLeadingZeroes, false, n);
                }
                --this._inputPtr;
                this._textBuffer.setCurrentLength(currentLength);
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(verifyNoLeadingZeroes);
                }
                return this.resetInt(false, n);
            }
        }
        return this._parseNumber2(emptyAndGetCurrentSegment, currentLength, false, n);
    }
    
    protected JsonToken _parseNegNumber() throws IOException {
        final char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int currentLength = 0;
        emptyAndGetCurrentSegment[currentLength++] = '-';
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        int verifyNoLeadingZeroes = this._inputBuffer[this._inputPtr++] & 0xFF;
        if (verifyNoLeadingZeroes <= 48) {
            if (verifyNoLeadingZeroes != 48) {
                return this._handleInvalidNumberStart(verifyNoLeadingZeroes, true);
            }
            verifyNoLeadingZeroes = this._verifyNoLeadingZeroes();
        }
        else if (verifyNoLeadingZeroes > 57) {
            return this._handleInvalidNumberStart(verifyNoLeadingZeroes, true);
        }
        emptyAndGetCurrentSegment[currentLength++] = (char)verifyNoLeadingZeroes;
        int n = 1;
        while (this._inputPtr < Math.min(this._inputEnd, this._inputPtr + emptyAndGetCurrentSegment.length - currentLength)) {
            final int n2 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (n2 >= 48 && n2 <= 57) {
                ++n;
                emptyAndGetCurrentSegment[currentLength++] = (char)n2;
            }
            else {
                if (n2 == 46 || n2 == 101 || n2 == 69) {
                    return this._parseFloat(emptyAndGetCurrentSegment, currentLength, n2, true, n);
                }
                --this._inputPtr;
                this._textBuffer.setCurrentLength(currentLength);
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(n2);
                }
                return this.resetInt(true, n);
            }
        }
        return this._parseNumber2(emptyAndGetCurrentSegment, currentLength, true, n);
    }
    
    private final JsonToken _parseNumber2(char[] finishCurrentSegment, int n, final boolean b, int n2) throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int n3 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (n3 > 57 || n3 < 48) {
                if (n3 == 46 || n3 == 101 || n3 == 69) {
                    return this._parseFloat(finishCurrentSegment, n, n3, b, n2);
                }
                --this._inputPtr;
                this._textBuffer.setCurrentLength(n);
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(this._inputBuffer[this._inputPtr++] & 0xFF);
                }
                return this.resetInt(b, n2);
            }
            else {
                if (n >= finishCurrentSegment.length) {
                    finishCurrentSegment = this._textBuffer.finishCurrentSegment();
                    n = 0;
                }
                finishCurrentSegment[n++] = (char)n3;
                ++n2;
            }
        }
        this._textBuffer.setCurrentLength(n);
        return this.resetInt(b, n2);
    }
    
    private final int _verifyNoLeadingZeroes() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return 48;
        }
        int n = this._inputBuffer[this._inputPtr] & 0xFF;
        if (n < 48 || n > 57) {
            return 48;
        }
        if (!this.isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        ++this._inputPtr;
        if (n == 48) {
            while (this._inputPtr < this._inputEnd || this._loadMore()) {
                n = (this._inputBuffer[this._inputPtr] & 0xFF);
                if (n < 48 || n > 57) {
                    return 48;
                }
                ++this._inputPtr;
                if (n != 48) {
                    break;
                }
            }
        }
        return n;
    }
    
    private final JsonToken _parseFloat(char[] array, int currentLength, int n, final boolean b, final int n2) throws IOException {
        int n3 = 0;
        boolean b2 = false;
        Label_0139: {
            if (n == 46) {
                if (currentLength >= array.length) {
                    array = this._textBuffer.finishCurrentSegment();
                    currentLength = 0;
                }
                array[currentLength++] = (char)n;
                while (true) {
                    while (this._inputPtr < this._inputEnd || this._loadMore()) {
                        n = (this._inputBuffer[this._inputPtr++] & 0xFF);
                        if (n >= 48) {
                            if (n <= 57) {
                                ++n3;
                                if (currentLength >= array.length) {
                                    array = this._textBuffer.finishCurrentSegment();
                                    currentLength = 0;
                                }
                                array[currentLength++] = (char)n;
                                continue;
                            }
                        }
                        if (n3 == 0) {
                            this.reportUnexpectedNumberChar(n, "Decimal point not followed by a digit");
                        }
                        break Label_0139;
                    }
                    b2 = true;
                    continue;
                }
            }
        }
        int n4 = 0;
        if (n == 101 || n == 69) {
            if (currentLength >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentLength = 0;
            }
            array[currentLength++] = (char)n;
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            n = (this._inputBuffer[this._inputPtr++] & 0xFF);
            if (n == 45 || n == 43) {
                if (currentLength >= array.length) {
                    array = this._textBuffer.finishCurrentSegment();
                    currentLength = 0;
                }
                array[currentLength++] = (char)n;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                n = (this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            while (n >= 48 && n <= 57) {
                ++n4;
                if (currentLength >= array.length) {
                    array = this._textBuffer.finishCurrentSegment();
                    currentLength = 0;
                }
                array[currentLength++] = (char)n;
                if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                    b2 = true;
                    break;
                }
                n = (this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            if (n4 == 0) {
                this.reportUnexpectedNumberChar(n, "Exponent indicator not followed by a digit");
            }
        }
        if (!b2) {
            --this._inputPtr;
            if (this._parsingContext.inRoot()) {
                this._verifyRootSpace(n);
            }
        }
        this._textBuffer.setCurrentLength(currentLength);
        return this.resetFloat(b, n2, n3, n4);
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
    
    protected final String _parseName(int n) throws IOException {
        if (n != 34) {
            return this._handleOddName(n);
        }
        if (this._inputPtr + 13 > this._inputEnd) {
            return this.slowParseName();
        }
        final byte[] inputBuffer = this._inputBuffer;
        final int[] icLatin1 = UTF8StreamJsonParser._icLatin1;
        final int n2 = inputBuffer[this._inputPtr++] & 0xFF;
        if (icLatin1[n2] == 0) {
            n = (inputBuffer[this._inputPtr++] & 0xFF);
            if (icLatin1[n] == 0) {
                final int n3 = n2 << 8 | n;
                n = (inputBuffer[this._inputPtr++] & 0xFF);
                if (icLatin1[n] == 0) {
                    final int n4 = n3 << 8 | n;
                    n = (inputBuffer[this._inputPtr++] & 0xFF);
                    if (icLatin1[n] == 0) {
                        final int quad1 = n4 << 8 | n;
                        n = (inputBuffer[this._inputPtr++] & 0xFF);
                        if (icLatin1[n] == 0) {
                            this._quad1 = quad1;
                            return this.parseMediumName(n);
                        }
                        if (n == 34) {
                            return this.findName(quad1, 4);
                        }
                        return this.parseName(quad1, n, 4);
                    }
                    else {
                        if (n == 34) {
                            return this.findName(n4, 3);
                        }
                        return this.parseName(n4, n, 3);
                    }
                }
                else {
                    if (n == 34) {
                        return this.findName(n3, 2);
                    }
                    return this.parseName(n3, n, 2);
                }
            }
            else {
                if (n == 34) {
                    return this.findName(n2, 1);
                }
                return this.parseName(n2, n, 1);
            }
        }
        else {
            if (n2 == 34) {
                return "";
            }
            return this.parseName(0, n2, 0);
        }
    }
    
    protected final String parseMediumName(int n) throws IOException {
        final byte[] inputBuffer = this._inputBuffer;
        final int[] icLatin1 = UTF8StreamJsonParser._icLatin1;
        final int n2 = inputBuffer[this._inputPtr++] & 0xFF;
        if (icLatin1[n2] != 0) {
            if (n2 == 34) {
                return this.findName(this._quad1, n, 1);
            }
            return this.parseName(this._quad1, n, n2, 1);
        }
        else {
            n = (n << 8 | n2);
            final int n3 = inputBuffer[this._inputPtr++] & 0xFF;
            if (icLatin1[n3] != 0) {
                if (n3 == 34) {
                    return this.findName(this._quad1, n, 2);
                }
                return this.parseName(this._quad1, n, n3, 2);
            }
            else {
                n = (n << 8 | n3);
                final int n4 = inputBuffer[this._inputPtr++] & 0xFF;
                if (icLatin1[n4] != 0) {
                    if (n4 == 34) {
                        return this.findName(this._quad1, n, 3);
                    }
                    return this.parseName(this._quad1, n, n4, 3);
                }
                else {
                    n = (n << 8 | n4);
                    final int n5 = inputBuffer[this._inputPtr++] & 0xFF;
                    if (icLatin1[n5] == 0) {
                        return this.parseMediumName2(n5, n);
                    }
                    if (n5 == 34) {
                        return this.findName(this._quad1, n, 4);
                    }
                    return this.parseName(this._quad1, n, n5, 4);
                }
            }
        }
    }
    
    protected final String parseMediumName2(int n, final int n2) throws IOException {
        final byte[] inputBuffer = this._inputBuffer;
        final int[] icLatin1 = UTF8StreamJsonParser._icLatin1;
        final int n3 = inputBuffer[this._inputPtr++] & 0xFF;
        if (icLatin1[n3] != 0) {
            if (n3 == 34) {
                return this.findName(this._quad1, n2, n, 1);
            }
            return this.parseName(this._quad1, n2, n, n3, 1);
        }
        else {
            n = (n << 8 | n3);
            final int n4 = inputBuffer[this._inputPtr++] & 0xFF;
            if (icLatin1[n4] != 0) {
                if (n4 == 34) {
                    return this.findName(this._quad1, n2, n, 2);
                }
                return this.parseName(this._quad1, n2, n, n4, 2);
            }
            else {
                n = (n << 8 | n4);
                final int n5 = inputBuffer[this._inputPtr++] & 0xFF;
                if (icLatin1[n5] != 0) {
                    if (n5 == 34) {
                        return this.findName(this._quad1, n2, n, 3);
                    }
                    return this.parseName(this._quad1, n2, n, n5, 3);
                }
                else {
                    n = (n << 8 | n5);
                    final int n6 = inputBuffer[this._inputPtr++] & 0xFF;
                    if (icLatin1[n6] == 0) {
                        return this.parseLongName(n6, n2, n);
                    }
                    if (n6 == 34) {
                        return this.findName(this._quad1, n2, n, 4);
                    }
                    return this.parseName(this._quad1, n2, n, n6, 4);
                }
            }
        }
    }
    
    protected final String parseLongName(int n, final int n2, final int n3) throws IOException {
        this._quadBuffer[0] = this._quad1;
        this._quadBuffer[1] = n2;
        this._quadBuffer[2] = n3;
        final byte[] inputBuffer = this._inputBuffer;
        final int[] icLatin1 = UTF8StreamJsonParser._icLatin1;
        int n4 = 3;
        while (this._inputPtr + 4 <= this._inputEnd) {
            final int n5 = inputBuffer[this._inputPtr++] & 0xFF;
            if (icLatin1[n5] != 0) {
                if (n5 == 34) {
                    return this.findName(this._quadBuffer, n4, n, 1);
                }
                return this.parseEscapedName(this._quadBuffer, n4, n, n5, 1);
            }
            else {
                n = (n << 8 | n5);
                final int n6 = inputBuffer[this._inputPtr++] & 0xFF;
                if (icLatin1[n6] != 0) {
                    if (n6 == 34) {
                        return this.findName(this._quadBuffer, n4, n, 2);
                    }
                    return this.parseEscapedName(this._quadBuffer, n4, n, n6, 2);
                }
                else {
                    n = (n << 8 | n6);
                    final int n7 = inputBuffer[this._inputPtr++] & 0xFF;
                    if (icLatin1[n7] != 0) {
                        if (n7 == 34) {
                            return this.findName(this._quadBuffer, n4, n, 3);
                        }
                        return this.parseEscapedName(this._quadBuffer, n4, n, n7, 3);
                    }
                    else {
                        n = (n << 8 | n7);
                        final int n8 = inputBuffer[this._inputPtr++] & 0xFF;
                        if (icLatin1[n8] != 0) {
                            if (n8 == 34) {
                                return this.findName(this._quadBuffer, n4, n, 4);
                            }
                            return this.parseEscapedName(this._quadBuffer, n4, n, n8, 4);
                        }
                        else {
                            if (n4 >= this._quadBuffer.length) {
                                this._quadBuffer = ParserBase.growArrayBy(this._quadBuffer, n4);
                            }
                            this._quadBuffer[n4++] = n;
                            n = n8;
                        }
                    }
                }
            }
        }
        return this.parseEscapedName(this._quadBuffer, n4, 0, n, 0);
    }
    
    protected String slowParseName() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(": was expecting closing '\"' for name", JsonToken.FIELD_NAME);
        }
        final int n = this._inputBuffer[this._inputPtr++] & 0xFF;
        if (n == 34) {
            return "";
        }
        return this.parseEscapedName(this._quadBuffer, 0, 0, n, 0);
    }
    
    private final String parseName(final int n, final int n2, final int n3) throws IOException {
        return this.parseEscapedName(this._quadBuffer, 0, n, n2, n3);
    }
    
    private final String parseName(final int n, final int n2, final int n3, final int n4) throws IOException {
        this._quadBuffer[0] = n;
        return this.parseEscapedName(this._quadBuffer, 1, n2, n3, n4);
    }
    
    private final String parseName(final int n, final int n2, final int n3, final int n4, final int n5) throws IOException {
        this._quadBuffer[0] = n;
        this._quadBuffer[1] = n2;
        return this.parseEscapedName(this._quadBuffer, 2, n3, n4, n5);
    }
    
    protected final String parseEscapedName(int[] array, int n, int n2, int decodeEscaped, int n3) throws IOException {
        final int[] icLatin1 = UTF8StreamJsonParser._icLatin1;
        while (true) {
            if (icLatin1[decodeEscaped] != 0) {
                if (decodeEscaped == 34) {
                    break;
                }
                if (decodeEscaped != 92) {
                    this._throwUnquotedSpace(decodeEscaped, "name");
                }
                else {
                    decodeEscaped = this._decodeEscaped();
                }
                if (decodeEscaped > 127) {
                    if (n3 >= 4) {
                        if (n >= array.length) {
                            array = (this._quadBuffer = ParserBase.growArrayBy(array, array.length));
                        }
                        array[n++] = n2;
                        n2 = 0;
                        n3 = 0;
                    }
                    if (decodeEscaped < 2048) {
                        n2 = (n2 << 8 | (0xC0 | decodeEscaped >> 6));
                        ++n3;
                    }
                    else {
                        n2 = (n2 << 8 | (0xE0 | decodeEscaped >> 12));
                        if (++n3 >= 4) {
                            if (n >= array.length) {
                                array = (this._quadBuffer = ParserBase.growArrayBy(array, array.length));
                            }
                            array[n++] = n2;
                            n2 = 0;
                            n3 = 0;
                        }
                        n2 = (n2 << 8 | (0x80 | (decodeEscaped >> 6 & 0x3F)));
                        ++n3;
                    }
                    decodeEscaped = (0x80 | (decodeEscaped & 0x3F));
                }
            }
            if (n3 < 4) {
                ++n3;
                n2 = (n2 << 8 | decodeEscaped);
            }
            else {
                if (n >= array.length) {
                    array = (this._quadBuffer = ParserBase.growArrayBy(array, array.length));
                }
                array[n++] = n2;
                n2 = decodeEscaped;
                n3 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            decodeEscaped = (this._inputBuffer[this._inputPtr++] & 0xFF);
        }
        if (n3 > 0) {
            if (n >= array.length) {
                array = (this._quadBuffer = ParserBase.growArrayBy(array, array.length));
            }
            array[n++] = _padLastQuad(n2, n3);
        }
        String s = this._symbols.findName(array, n);
        if (s == null) {
            s = this.addName(array, n, n3);
        }
        return s;
    }
    
    protected String _handleOddName(int n) throws IOException {
        if (n == 39 && this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
            return this._parseAposName();
        }
        if (!this.isEnabled(Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            this._reportUnexpectedChar((char)this._decodeCharForError(n), "was expecting double-quote to start field name");
        }
        final int[] inputCodeUtf8JsNames = CharTypes.getInputCodeUtf8JsNames();
        if (inputCodeUtf8JsNames[n] != 0) {
            this._reportUnexpectedChar(n, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int[] quadBuffer = this._quadBuffer;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        while (true) {
            if (n4 < 4) {
                ++n4;
                n3 = (n3 << 8 | n);
            }
            else {
                if (n2 >= quadBuffer.length) {
                    quadBuffer = (this._quadBuffer = ParserBase.growArrayBy(quadBuffer, quadBuffer.length));
                }
                quadBuffer[n2++] = n3;
                n3 = n;
                n4 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            n = (this._inputBuffer[this._inputPtr] & 0xFF);
            if (inputCodeUtf8JsNames[n] != 0) {
                break;
            }
            ++this._inputPtr;
        }
        if (n4 > 0) {
            if (n2 >= quadBuffer.length) {
                quadBuffer = (this._quadBuffer = ParserBase.growArrayBy(quadBuffer, quadBuffer.length));
            }
            quadBuffer[n2++] = n3;
        }
        String s = this._symbols.findName(quadBuffer, n2);
        if (s == null) {
            s = this.addName(quadBuffer, n2, n4);
        }
        return s;
    }
    
    protected String _parseAposName() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(": was expecting closing ''' for field name", JsonToken.FIELD_NAME);
        }
        int i = this._inputBuffer[this._inputPtr++] & 0xFF;
        if (i == 39) {
            return "";
        }
        int[] quadBuffer = this._quadBuffer;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        final int[] icLatin1 = UTF8StreamJsonParser._icLatin1;
        while (i != 39) {
            if (icLatin1[i] != 0 && i != 34) {
                if (i != 92) {
                    this._throwUnquotedSpace(i, "name");
                }
                else {
                    i = this._decodeEscaped();
                }
                if (i > 127) {
                    if (n3 >= 4) {
                        if (n >= quadBuffer.length) {
                            quadBuffer = (this._quadBuffer = ParserBase.growArrayBy(quadBuffer, quadBuffer.length));
                        }
                        quadBuffer[n++] = n2;
                        n2 = 0;
                        n3 = 0;
                    }
                    if (i < 2048) {
                        n2 = (n2 << 8 | (0xC0 | i >> 6));
                        ++n3;
                    }
                    else {
                        int n4 = n2 << 8 | (0xE0 | i >> 12);
                        if (++n3 >= 4) {
                            if (n >= quadBuffer.length) {
                                quadBuffer = (this._quadBuffer = ParserBase.growArrayBy(quadBuffer, quadBuffer.length));
                            }
                            quadBuffer[n++] = n4;
                            n4 = 0;
                            n3 = 0;
                        }
                        n2 = (n4 << 8 | (0x80 | (i >> 6 & 0x3F)));
                        ++n3;
                    }
                    i = (0x80 | (i & 0x3F));
                }
            }
            if (n3 < 4) {
                ++n3;
                n2 = (n2 << 8 | i);
            }
            else {
                if (n >= quadBuffer.length) {
                    quadBuffer = (this._quadBuffer = ParserBase.growArrayBy(quadBuffer, quadBuffer.length));
                }
                quadBuffer[n++] = n2;
                n2 = i;
                n3 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            i = (this._inputBuffer[this._inputPtr++] & 0xFF);
        }
        if (n3 > 0) {
            if (n >= quadBuffer.length) {
                quadBuffer = (this._quadBuffer = ParserBase.growArrayBy(quadBuffer, quadBuffer.length));
            }
            quadBuffer[n++] = _padLastQuad(n2, n3);
        }
        String s = this._symbols.findName(quadBuffer, n);
        if (s == null) {
            s = this.addName(quadBuffer, n, n3);
        }
        return s;
    }
    
    private final String findName(int padLastQuad, final int n) throws JsonParseException {
        padLastQuad = _padLastQuad(padLastQuad, n);
        final String name = this._symbols.findName(padLastQuad);
        if (name != null) {
            return name;
        }
        this._quadBuffer[0] = padLastQuad;
        return this.addName(this._quadBuffer, 1, n);
    }
    
    private final String findName(final int n, int padLastQuad, final int n2) throws JsonParseException {
        padLastQuad = _padLastQuad(padLastQuad, n2);
        final String name = this._symbols.findName(n, padLastQuad);
        if (name != null) {
            return name;
        }
        this._quadBuffer[0] = n;
        this._quadBuffer[1] = padLastQuad;
        return this.addName(this._quadBuffer, 2, n2);
    }
    
    private final String findName(final int n, final int n2, int padLastQuad, final int n3) throws JsonParseException {
        padLastQuad = _padLastQuad(padLastQuad, n3);
        final String name = this._symbols.findName(n, n2, padLastQuad);
        if (name != null) {
            return name;
        }
        final int[] quadBuffer = this._quadBuffer;
        quadBuffer[0] = n;
        quadBuffer[1] = n2;
        quadBuffer[2] = _padLastQuad(padLastQuad, n3);
        return this.addName(quadBuffer, 3, n3);
    }
    
    private final String findName(int[] array, int n, final int n2, final int n3) throws JsonParseException {
        if (n >= array.length) {
            array = (this._quadBuffer = ParserBase.growArrayBy(array, array.length));
        }
        array[n++] = _padLastQuad(n2, n3);
        final String name = this._symbols.findName(array, n);
        if (name == null) {
            return this.addName(array, n, n3);
        }
        return name;
    }
    
    private final String addName(final int[] array, final int n, final int n2) throws JsonParseException {
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
    
    private static final int _padLastQuad(final int n, final int n2) {
        return (n2 == 4) ? n : (n | -1 << (n2 << 3));
    }
    
    protected void _loadMoreGuaranteed() throws IOException {
        if (!this._loadMore()) {
            this._reportInvalidEOF();
        }
    }
    
    @Override
    protected void _finishString() throws IOException {
        int i = this._inputPtr;
        if (i >= this._inputEnd) {
            this._loadMoreGuaranteed();
            i = this._inputPtr;
        }
        int currentLength = 0;
        final char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] icUTF8 = UTF8StreamJsonParser._icUTF8;
        final int min = Math.min(this._inputEnd, i + emptyAndGetCurrentSegment.length);
        final byte[] inputBuffer = this._inputBuffer;
        while (i < min) {
            final int n = inputBuffer[i] & 0xFF;
            if (icUTF8[n] != 0) {
                if (n == 34) {
                    this._inputPtr = i + 1;
                    this._textBuffer.setCurrentLength(currentLength);
                    return;
                }
                break;
            }
            else {
                ++i;
                emptyAndGetCurrentSegment[currentLength++] = (char)n;
            }
        }
        this._inputPtr = i;
        this._finishString2(emptyAndGetCurrentSegment, currentLength);
    }
    
    protected String _finishAndReturnString() throws IOException {
        int i = this._inputPtr;
        if (i >= this._inputEnd) {
            this._loadMoreGuaranteed();
            i = this._inputPtr;
        }
        int currentAndReturn = 0;
        final char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] icUTF8 = UTF8StreamJsonParser._icUTF8;
        final int min = Math.min(this._inputEnd, i + emptyAndGetCurrentSegment.length);
        final byte[] inputBuffer = this._inputBuffer;
        while (i < min) {
            final int n = inputBuffer[i] & 0xFF;
            if (icUTF8[n] != 0) {
                if (n == 34) {
                    this._inputPtr = i + 1;
                    return this._textBuffer.setCurrentAndReturn(currentAndReturn);
                }
                break;
            }
            else {
                ++i;
                emptyAndGetCurrentSegment[currentAndReturn++] = (char)n;
            }
        }
        this._inputPtr = i;
        this._finishString2(emptyAndGetCurrentSegment, currentAndReturn);
        return this._textBuffer.contentsAsString();
    }
    
    private final void _finishString2(char[] array, int currentLength) throws IOException {
        final int[] icUTF8 = UTF8StreamJsonParser._icUTF8;
        final byte[] inputBuffer = this._inputBuffer;
    Block_5:
        while (true) {
            int i = this._inputPtr;
            if (i >= this._inputEnd) {
                this._loadMoreGuaranteed();
                i = this._inputPtr;
            }
            if (currentLength >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentLength = 0;
            }
            while (i < Math.min(this._inputEnd, i + (array.length - currentLength))) {
                int n = inputBuffer[i++] & 0xFF;
                if (icUTF8[n] != 0) {
                    this._inputPtr = i;
                    if (n == 34) {
                        break Block_5;
                    }
                    switch (icUTF8[n]) {
                        case 1: {
                            n = this._decodeEscaped();
                            break;
                        }
                        case 2: {
                            n = this._decodeUtf8_2(n);
                            break;
                        }
                        case 3: {
                            if (this._inputEnd - this._inputPtr >= 2) {
                                n = this._decodeUtf8_3fast(n);
                                break;
                            }
                            n = this._decodeUtf8_3(n);
                            break;
                        }
                        case 4: {
                            final int decodeUtf8_4 = this._decodeUtf8_4(n);
                            array[currentLength++] = (char)(0xD800 | decodeUtf8_4 >> 10);
                            if (currentLength >= array.length) {
                                array = this._textBuffer.finishCurrentSegment();
                                currentLength = 0;
                            }
                            n = (0xDC00 | (decodeUtf8_4 & 0x3FF));
                            break;
                        }
                        default: {
                            if (n < 32) {
                                this._throwUnquotedSpace(n, "string value");
                                break;
                            }
                            this._reportInvalidChar(n);
                            break;
                        }
                    }
                    if (currentLength >= array.length) {
                        array = this._textBuffer.finishCurrentSegment();
                        currentLength = 0;
                    }
                    array[currentLength++] = (char)n;
                    continue Block_5;
                }
                else {
                    array[currentLength++] = (char)n;
                }
            }
            this._inputPtr = i;
        }
        this._textBuffer.setCurrentLength(currentLength);
    }
    
    protected void _skipString() throws IOException {
        this._tokenIncomplete = false;
        final int[] icUTF8 = UTF8StreamJsonParser._icUTF8;
        final byte[] inputBuffer = this._inputBuffer;
    Block_4:
        while (true) {
            int i = this._inputPtr;
            int n = this._inputEnd;
            if (i >= n) {
                this._loadMoreGuaranteed();
                i = this._inputPtr;
                n = this._inputEnd;
            }
            while (i < n) {
                final int n2 = inputBuffer[i++] & 0xFF;
                if (icUTF8[n2] != 0) {
                    this._inputPtr = i;
                    if (n2 == 34) {
                        break Block_4;
                    }
                    switch (icUTF8[n2]) {
                        case 1: {
                            this._decodeEscaped();
                            break;
                        }
                        case 2: {
                            this._skipUtf8_2();
                            break;
                        }
                        case 3: {
                            this._skipUtf8_3();
                            break;
                        }
                        case 4: {
                            this._skipUtf8_4(n2);
                            break;
                        }
                        default: {
                            if (n2 < 32) {
                                this._throwUnquotedSpace(n2, "string value");
                                break;
                            }
                            this._reportInvalidChar(n2);
                            break;
                        }
                    }
                    continue Block_4;
                }
            }
            this._inputPtr = i;
        }
    }
    
    protected JsonToken _handleUnexpectedValue(final int n) throws IOException {
        switch (n) {
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
            }
            case 125: {
                this._reportUnexpectedChar(n, "expected a value");
            }
            case 39: {
                if (this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
                    return this._handleApos();
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
                return this._handleInvalidNumberStart(this._inputBuffer[this._inputPtr++] & 0xFF, false);
            }
        }
        if (Character.isJavaIdentifierStart(n)) {
            this._reportInvalidToken("" + (char)n, "('true', 'false' or 'null')");
        }
        this._reportUnexpectedChar(n, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
        return null;
    }
    
    protected JsonToken _handleApos() throws IOException {
        int currentLength = 0;
        char[] array = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] icUTF8 = UTF8StreamJsonParser._icUTF8;
        final byte[] inputBuffer = this._inputBuffer;
    Block_7:
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            if (currentLength >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentLength = 0;
            }
            int inputEnd = this._inputEnd;
            final int n = this._inputPtr + (array.length - currentLength);
            if (n < inputEnd) {
                inputEnd = n;
            }
            while (this._inputPtr < inputEnd) {
                int n2 = inputBuffer[this._inputPtr++] & 0xFF;
                if (n2 != 39 && icUTF8[n2] == 0) {
                    array[currentLength++] = (char)n2;
                }
                else {
                    if (n2 == 39) {
                        break Block_7;
                    }
                    switch (icUTF8[n2]) {
                        case 1: {
                            n2 = this._decodeEscaped();
                            break;
                        }
                        case 2: {
                            n2 = this._decodeUtf8_2(n2);
                            break;
                        }
                        case 3: {
                            if (this._inputEnd - this._inputPtr >= 2) {
                                n2 = this._decodeUtf8_3fast(n2);
                                break;
                            }
                            n2 = this._decodeUtf8_3(n2);
                            break;
                        }
                        case 4: {
                            final int decodeUtf8_4 = this._decodeUtf8_4(n2);
                            array[currentLength++] = (char)(0xD800 | decodeUtf8_4 >> 10);
                            if (currentLength >= array.length) {
                                array = this._textBuffer.finishCurrentSegment();
                                currentLength = 0;
                            }
                            n2 = (0xDC00 | (decodeUtf8_4 & 0x3FF));
                            break;
                        }
                        default: {
                            if (n2 < 32) {
                                this._throwUnquotedSpace(n2, "string value");
                            }
                            this._reportInvalidChar(n2);
                            break;
                        }
                    }
                    if (currentLength >= array.length) {
                        array = this._textBuffer.finishCurrentSegment();
                        currentLength = 0;
                    }
                    array[currentLength++] = (char)n2;
                    break;
                }
            }
        }
        this._textBuffer.setCurrentLength(currentLength);
        return JsonToken.VALUE_STRING;
    }
    
    protected JsonToken _handleInvalidNumberStart(int i, final boolean b) throws IOException {
        while (i == 73) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_FLOAT);
            }
            i = this._inputBuffer[this._inputPtr++];
            String s;
            if (i == 78) {
                s = (b ? "-INF" : "+INF");
            }
            else {
                if (i != 110) {
                    break;
                }
                s = (b ? "-Infinity" : "+Infinity");
            }
            this._matchToken(s, 3);
            if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                return this.resetAsNaN(s, b ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
            }
            this._reportError("Non-standard token '%s': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow", s);
        }
        this.reportUnexpectedNumberChar(i, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }
    
    protected final void _matchTrue() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 3 < this._inputEnd) {
            final byte[] inputBuffer = this._inputBuffer;
            if (inputBuffer[inputPtr++] == 114 && inputBuffer[inputPtr++] == 117 && inputBuffer[inputPtr++] == 101) {
                final int n = inputBuffer[inputPtr] & 0xFF;
                if (n < 48 || n == 93 || n == 125) {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken2("true", 1);
    }
    
    protected final void _matchFalse() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 4 < this._inputEnd) {
            final byte[] inputBuffer = this._inputBuffer;
            if (inputBuffer[inputPtr++] == 97 && inputBuffer[inputPtr++] == 108 && inputBuffer[inputPtr++] == 115 && inputBuffer[inputPtr++] == 101) {
                final int n = inputBuffer[inputPtr] & 0xFF;
                if (n < 48 || n == 93 || n == 125) {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken2("false", 1);
    }
    
    protected final void _matchNull() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 3 < this._inputEnd) {
            final byte[] inputBuffer = this._inputBuffer;
            if (inputBuffer[inputPtr++] == 117 && inputBuffer[inputPtr++] == 108 && inputBuffer[inputPtr++] == 108) {
                final int n = inputBuffer[inputPtr] & 0xFF;
                if (n < 48 || n == 93 || n == 125) {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken2("null", 1);
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
        final int n2 = this._inputBuffer[this._inputPtr] & 0xFF;
        if (n2 >= 48 && n2 != 93 && n2 != 125) {
            this._checkMatchEnd(s, n, n2);
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
        final int n2 = this._inputBuffer[this._inputPtr] & 0xFF;
        if (n2 >= 48 && n2 != 93 && n2 != 125) {
            this._checkMatchEnd(s, n, n2);
        }
    }
    
    private final void _checkMatchEnd(final String s, final int n, final int n2) throws IOException {
        if (Character.isJavaIdentifierPart((char)this._decodeCharForError(n2))) {
            this._reportInvalidToken(s.substring(0, n));
        }
    }
    
    private final int _skipWS() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            final int n = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (n > 32) {
                if (n == 47 || n == 35) {
                    --this._inputPtr;
                    return this._skipWS2();
                }
                return n;
            }
            else {
                if (n == 32) {
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
        return this._skipWS2();
    }
    
    private final int _skipWS2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int n = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (n > 32) {
                if (n == 47) {
                    this._skipComment();
                }
                else {
                    if (n == 35 && this._skipYAMLComment()) {
                        continue;
                    }
                    return n;
                }
            }
            else {
                if (n == 32) {
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
        throw this._constructError("Unexpected end-of-input within/between " + this._parsingContext.typeDesc() + " entries");
    }
    
    private final int _skipWSOrEnd() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return this._eofAsNextChar();
        }
        final int n = this._inputBuffer[this._inputPtr++] & 0xFF;
        if (n <= 32) {
            if (n != 32) {
                if (n == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (n == 13) {
                    this._skipCR();
                }
                else if (n != 9) {
                    this._throwInvalidSpace(n);
                }
            }
            while (this._inputPtr < this._inputEnd) {
                final int n2 = this._inputBuffer[this._inputPtr++] & 0xFF;
                if (n2 > 32) {
                    if (n2 == 47 || n2 == 35) {
                        --this._inputPtr;
                        return this._skipWSOrEnd2();
                    }
                    return n2;
                }
                else {
                    if (n2 == 32) {
                        continue;
                    }
                    if (n2 == 10) {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                    }
                    else if (n2 == 13) {
                        this._skipCR();
                    }
                    else {
                        if (n2 == 9) {
                            continue;
                        }
                        this._throwInvalidSpace(n2);
                    }
                }
            }
            return this._skipWSOrEnd2();
        }
        if (n == 47 || n == 35) {
            --this._inputPtr;
            return this._skipWSOrEnd2();
        }
        return n;
    }
    
    private final int _skipWSOrEnd2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int n = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (n > 32) {
                if (n == 47) {
                    this._skipComment();
                }
                else {
                    if (n == 35 && this._skipYAMLComment()) {
                        continue;
                    }
                    return n;
                }
            }
            else {
                if (n == 32) {
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
        return this._eofAsNextChar();
    }
    
    private final int _skipColon() throws IOException {
        if (this._inputPtr + 4 >= this._inputEnd) {
            return this._skipColon2(false);
        }
        byte b = this._inputBuffer[this._inputPtr];
        if (b == 58) {
            final byte b2 = this._inputBuffer[++this._inputPtr];
            if (b2 <= 32) {
                if (b2 == 32 || b2 == 9) {
                    final byte b3 = this._inputBuffer[++this._inputPtr];
                    if (b3 > 32) {
                        if (b3 == 47 || b3 == 35) {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return b3;
                    }
                }
                return this._skipColon2(true);
            }
            if (b2 == 47 || b2 == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return b2;
        }
        else {
            if (b == 32 || b == 9) {
                b = this._inputBuffer[++this._inputPtr];
            }
            if (b != 58) {
                return this._skipColon2(false);
            }
            final byte b4 = this._inputBuffer[++this._inputPtr];
            if (b4 <= 32) {
                if (b4 == 32 || b4 == 9) {
                    final byte b5 = this._inputBuffer[++this._inputPtr];
                    if (b5 > 32) {
                        if (b5 == 47 || b5 == 35) {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return b5;
                    }
                }
                return this._skipColon2(true);
            }
            if (b4 == 47 || b4 == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return b4;
        }
    }
    
    private final int _skipColon2(boolean b) throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int n = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (n > 32) {
                if (n == 47) {
                    this._skipComment();
                }
                else {
                    if (n == 35 && this._skipYAMLComment()) {
                        continue;
                    }
                    if (b) {
                        return n;
                    }
                    if (n != 58) {
                        this._reportUnexpectedChar(n, "was expecting a colon to separate field name and value");
                    }
                    b = true;
                }
            }
            else {
                if (n == 32) {
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
        this._reportInvalidEOF(" within/between " + this._parsingContext.typeDesc() + " entries", null);
        return -1;
    }
    
    private final void _skipComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_COMMENTS)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in a comment", null);
        }
        final int n = this._inputBuffer[this._inputPtr++] & 0xFF;
        if (n == 47) {
            this._skipLine();
        }
        else if (n == 42) {
            this._skipCComment();
        }
        else {
            this._reportUnexpectedChar(n, "was expecting either '*' or '/' for a comment");
        }
    }
    
    private final void _skipCComment() throws IOException {
        final int[] inputCodeComment = CharTypes.getInputCodeComment();
    Label_0216:
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int n = this._inputBuffer[this._inputPtr++] & 0xFF;
            final int n2 = inputCodeComment[n];
            if (n2 != 0) {
                switch (n2) {
                    case 42: {
                        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                            break Label_0216;
                        }
                        if (this._inputBuffer[this._inputPtr] == 47) {
                            ++this._inputPtr;
                            return;
                        }
                        continue;
                    }
                    case 10: {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                        continue;
                    }
                    case 13: {
                        this._skipCR();
                        continue;
                    }
                    case 2: {
                        this._skipUtf8_2();
                        continue;
                    }
                    case 3: {
                        this._skipUtf8_3();
                        continue;
                    }
                    case 4: {
                        this._skipUtf8_4(n);
                        continue;
                    }
                    default: {
                        this._reportInvalidChar(n);
                        continue;
                    }
                }
            }
        }
        this._reportInvalidEOF(" in a comment", null);
    }
    
    private final boolean _skipYAMLComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        this._skipLine();
        return true;
    }
    
    private final void _skipLine() throws IOException {
        final int[] inputCodeComment = CharTypes.getInputCodeComment();
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int n = this._inputBuffer[this._inputPtr++] & 0xFF;
            final int n2 = inputCodeComment[n];
            if (n2 != 0) {
                switch (n2) {
                    case 10: {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                    }
                    case 13: {
                        this._skipCR();
                    }
                    case 42: {
                        continue;
                    }
                    case 2: {
                        this._skipUtf8_2();
                        continue;
                    }
                    case 3: {
                        this._skipUtf8_3();
                        continue;
                    }
                    case 4: {
                        this._skipUtf8_4(n);
                        continue;
                    }
                    default: {
                        if (n2 < 0) {
                            this._reportInvalidChar(n);
                            continue;
                        }
                        continue;
                    }
                }
            }
        }
    }
    
    @Override
    protected char _decodeEscaped() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
        }
        final byte b = this._inputBuffer[this._inputPtr++];
        switch (b) {
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
                return (char)b;
            }
            case 117: {
                int n = 0;
                for (int i = 0; i < 4; ++i) {
                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                        this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
                    }
                    final byte b2 = this._inputBuffer[this._inputPtr++];
                    final int charToHex = CharTypes.charToHex(b2);
                    if (charToHex < 0) {
                        this._reportUnexpectedChar(b2, "expected a hex-digit for character escape sequence");
                    }
                    n = (n << 4 | charToHex);
                }
                return (char)n;
            }
            default: {
                return this._handleUnrecognizedCharacterEscape((char)this._decodeCharForError(b));
            }
        }
    }
    
    protected int _decodeCharForError(final int n) throws IOException {
        int n2 = n & 0xFF;
        if (n2 > 127) {
            int n3;
            if ((n2 & 0xE0) == 0xC0) {
                n2 &= 0x1F;
                n3 = 1;
            }
            else if ((n2 & 0xF0) == 0xE0) {
                n2 &= 0xF;
                n3 = 2;
            }
            else if ((n2 & 0xF8) == 0xF0) {
                n2 &= 0x7;
                n3 = 3;
            }
            else {
                this._reportInvalidInitial(n2 & 0xFF);
                n3 = 1;
            }
            final int nextByte = this.nextByte();
            if ((nextByte & 0xC0) != 0x80) {
                this._reportInvalidOther(nextByte & 0xFF);
            }
            n2 = (n2 << 6 | (nextByte & 0x3F));
            if (n3 > 1) {
                final int nextByte2 = this.nextByte();
                if ((nextByte2 & 0xC0) != 0x80) {
                    this._reportInvalidOther(nextByte2 & 0xFF);
                }
                n2 = (n2 << 6 | (nextByte2 & 0x3F));
                if (n3 > 2) {
                    final int nextByte3 = this.nextByte();
                    if ((nextByte3 & 0xC0) != 0x80) {
                        this._reportInvalidOther(nextByte3 & 0xFF);
                    }
                    n2 = (n2 << 6 | (nextByte3 & 0x3F));
                }
            }
        }
        return n2;
    }
    
    private final int _decodeUtf8_2(final int n) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b = this._inputBuffer[this._inputPtr++];
        if ((b & 0xC0) != 0x80) {
            this._reportInvalidOther(b & 0xFF, this._inputPtr);
        }
        return (n & 0x1F) << 6 | (b & 0x3F);
    }
    
    private final int _decodeUtf8_3(int n) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        n &= 0xF;
        final byte b = this._inputBuffer[this._inputPtr++];
        if ((b & 0xC0) != 0x80) {
            this._reportInvalidOther(b & 0xFF, this._inputPtr);
        }
        final int n2 = n << 6 | (b & 0x3F);
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b2 = this._inputBuffer[this._inputPtr++];
        if ((b2 & 0xC0) != 0x80) {
            this._reportInvalidOther(b2 & 0xFF, this._inputPtr);
        }
        return n2 << 6 | (b2 & 0x3F);
    }
    
    private final int _decodeUtf8_3fast(int n) throws IOException {
        n &= 0xF;
        final byte b = this._inputBuffer[this._inputPtr++];
        if ((b & 0xC0) != 0x80) {
            this._reportInvalidOther(b & 0xFF, this._inputPtr);
        }
        final int n2 = n << 6 | (b & 0x3F);
        final byte b2 = this._inputBuffer[this._inputPtr++];
        if ((b2 & 0xC0) != 0x80) {
            this._reportInvalidOther(b2 & 0xFF, this._inputPtr);
        }
        return n2 << 6 | (b2 & 0x3F);
    }
    
    private final int _decodeUtf8_4(int n) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b = this._inputBuffer[this._inputPtr++];
        if ((b & 0xC0) != 0x80) {
            this._reportInvalidOther(b & 0xFF, this._inputPtr);
        }
        n = ((n & 0x7) << 6 | (b & 0x3F));
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b2 = this._inputBuffer[this._inputPtr++];
        if ((b2 & 0xC0) != 0x80) {
            this._reportInvalidOther(b2 & 0xFF, this._inputPtr);
        }
        n = (n << 6 | (b2 & 0x3F));
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b3 = this._inputBuffer[this._inputPtr++];
        if ((b3 & 0xC0) != 0x80) {
            this._reportInvalidOther(b3 & 0xFF, this._inputPtr);
        }
        return (n << 6 | (b3 & 0x3F)) - 65536;
    }
    
    private final void _skipUtf8_2() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b = this._inputBuffer[this._inputPtr++];
        if ((b & 0xC0) != 0x80) {
            this._reportInvalidOther(b & 0xFF, this._inputPtr);
        }
    }
    
    private final void _skipUtf8_3() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b = this._inputBuffer[this._inputPtr++];
        if ((b & 0xC0) != 0x80) {
            this._reportInvalidOther(b & 0xFF, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b2 = this._inputBuffer[this._inputPtr++];
        if ((b2 & 0xC0) != 0x80) {
            this._reportInvalidOther(b2 & 0xFF, this._inputPtr);
        }
    }
    
    private final void _skipUtf8_4(final int n) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b = this._inputBuffer[this._inputPtr++];
        if ((b & 0xC0) != 0x80) {
            this._reportInvalidOther(b & 0xFF, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b2 = this._inputBuffer[this._inputPtr++];
        if ((b2 & 0xC0) != 0x80) {
            this._reportInvalidOther(b2 & 0xFF, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final byte b3 = this._inputBuffer[this._inputPtr++];
        if ((b3 & 0xC0) != 0x80) {
            this._reportInvalidOther(b3 & 0xFF, this._inputPtr);
        }
    }
    
    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this._loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
            ++this._inputPtr;
        }
        ++this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }
    
    private int nextByte() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        return this._inputBuffer[this._inputPtr++] & 0xFF;
    }
    
    protected void _reportInvalidToken(final String s, final int inputPtr) throws IOException {
        this._inputPtr = inputPtr;
        this._reportInvalidToken(s, "'null', 'true', 'false' or NaN");
    }
    
    protected void _reportInvalidToken(final String s) throws IOException {
        this._reportInvalidToken(s, "'null', 'true', 'false' or NaN");
    }
    
    protected void _reportInvalidToken(final String s, final String s2) throws IOException {
        final StringBuilder sb = new StringBuilder(s);
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final char c = (char)this._decodeCharForError(this._inputBuffer[this._inputPtr++]);
            if (!Character.isJavaIdentifierPart(c)) {
                break;
            }
            sb.append(c);
            if (sb.length() >= 256) {
                sb.append("...");
                break;
            }
        }
        this._reportError("Unrecognized token '%s': was expecting %s", sb, s2);
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
    
    protected void _reportInvalidOther(final int n) throws JsonParseException {
        this._reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(n));
    }
    
    protected void _reportInvalidOther(final int n, final int inputPtr) throws JsonParseException {
        this._inputPtr = inputPtr;
        this._reportInvalidOther(n);
    }
    
    protected final byte[] _decodeBase64(final Base64Variant base64Variant) throws IOException {
        final ByteArrayBuilder getByteArrayBuilder = this._getByteArrayBuilder();
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            final int n = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (n > 32) {
                int n2 = base64Variant.decodeBase64Char(n);
                if (n2 < 0) {
                    if (n == 34) {
                        return getByteArrayBuilder.toByteArray();
                    }
                    n2 = this._decodeBase64Escape(base64Variant, n, 0);
                    if (n2 < 0) {
                        continue;
                    }
                }
                final int n3 = n2;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final int n4 = this._inputBuffer[this._inputPtr++] & 0xFF;
                int n5 = base64Variant.decodeBase64Char(n4);
                if (n5 < 0) {
                    n5 = this._decodeBase64Escape(base64Variant, n4, 1);
                }
                final int n6 = n3 << 6 | n5;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final int n7 = this._inputBuffer[this._inputPtr++] & 0xFF;
                int n8 = base64Variant.decodeBase64Char(n7);
                if (n8 < 0) {
                    if (n8 != -2) {
                        if (n7 == 34 && !base64Variant.usesPadding()) {
                            getByteArrayBuilder.append(n6 >> 4);
                            return getByteArrayBuilder.toByteArray();
                        }
                        n8 = this._decodeBase64Escape(base64Variant, n7, 2);
                    }
                    if (n8 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            this._loadMoreGuaranteed();
                        }
                        final int n9 = this._inputBuffer[this._inputPtr++] & 0xFF;
                        if (!base64Variant.usesPaddingChar(n9)) {
                            throw this.reportInvalidBase64Char(base64Variant, n9, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                        }
                        getByteArrayBuilder.append(n6 >> 4);
                        continue;
                    }
                }
                final int n10 = n6 << 6 | n8;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                final int n11 = this._inputBuffer[this._inputPtr++] & 0xFF;
                int n12 = base64Variant.decodeBase64Char(n11);
                if (n12 < 0) {
                    if (n12 != -2) {
                        if (n11 == 34 && !base64Variant.usesPadding()) {
                            getByteArrayBuilder.appendTwoBytes(n10 >> 2);
                            return getByteArrayBuilder.toByteArray();
                        }
                        n12 = this._decodeBase64Escape(base64Variant, n11, 3);
                    }
                    if (n12 == -2) {
                        getByteArrayBuilder.appendTwoBytes(n10 >> 2);
                        continue;
                    }
                }
                getByteArrayBuilder.appendThreeBytes(n10 << 6 | n12);
            }
        }
    }
    
    @Override
    public JsonLocation getTokenLocation() {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return new JsonLocation(this._getSourceReference(), this._currInputProcessed + (this._nameStartOffset - 1), -1L, this._nameStartRow, this._nameStartCol);
        }
        return new JsonLocation(this._getSourceReference(), this._tokenInputTotal - 1L, -1L, this._tokenInputRow, this._tokenInputCol);
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        return new JsonLocation(this._getSourceReference(), this._currInputProcessed + this._inputPtr, -1L, this._currInputRow, this._inputPtr - this._currInputRowStart + 1);
    }
    
    private final void _updateLocation() {
        this._tokenInputRow = this._currInputRow;
        final int inputPtr = this._inputPtr;
        this._tokenInputTotal = this._currInputProcessed + inputPtr;
        this._tokenInputCol = inputPtr - this._currInputRowStart;
    }
    
    private final void _updateNameLocation() {
        this._nameStartRow = this._currInputRow;
        final int inputPtr = this._inputPtr;
        this._nameStartOffset = inputPtr;
        this._nameStartCol = inputPtr - this._currInputRowStart;
    }
    
    private final JsonToken _closeScope(final int n) throws JsonParseException {
        if (n == 125) {
            this._closeObjectScope();
            return this._currToken = JsonToken.END_OBJECT;
        }
        this._closeArrayScope();
        return this._currToken = JsonToken.END_ARRAY;
    }
    
    private final void _closeArrayScope() throws JsonParseException {
        this._updateLocation();
        if (!this._parsingContext.inArray()) {
            this._reportMismatchedEndMarker(93, '}');
        }
        this._parsingContext = this._parsingContext.clearAndGetParent();
    }
    
    private final void _closeObjectScope() throws JsonParseException {
        this._updateLocation();
        if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(125, ']');
        }
        this._parsingContext = this._parsingContext.clearAndGetParent();
    }
    
    static {
        _icUTF8 = CharTypes.getInputCodeUtf8();
        _icLatin1 = CharTypes.getInputCodeLatin1();
        FEAT_MASK_TRAILING_COMMA = Feature.ALLOW_TRAILING_COMMA.getMask();
    }
}
