package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.util.*;
import java.math.*;
import com.fasterxml.jackson.core.*;
import java.io.*;

public class FilteringParserDelegate extends JsonParserDelegate
{
    protected TokenFilter rootFilter;
    protected boolean _allowMultipleMatches;
    protected boolean _includePath;
    @Deprecated
    protected boolean _includeImmediateParent;
    protected JsonToken _currToken;
    protected JsonToken _lastClearedToken;
    protected TokenFilterContext _headContext;
    protected TokenFilterContext _exposedContext;
    protected TokenFilter _itemFilter;
    protected int _matchCount;
    
    public FilteringParserDelegate(final JsonParser jsonParser, final TokenFilter tokenFilter, final boolean includePath, final boolean allowMultipleMatches) {
        super(jsonParser);
        this.rootFilter = tokenFilter;
        this._itemFilter = tokenFilter;
        this._headContext = TokenFilterContext.createRootContext(tokenFilter);
        this._includePath = includePath;
        this._allowMultipleMatches = allowMultipleMatches;
    }
    
    public TokenFilter getFilter() {
        return this.rootFilter;
    }
    
    public int getMatchCount() {
        return this._matchCount;
    }
    
    @Override
    public JsonToken getCurrentToken() {
        return this._currToken;
    }
    
    @Override
    public JsonToken currentToken() {
        return this._currToken;
    }
    
    @Override
    public final int getCurrentTokenId() {
        final JsonToken currToken = this._currToken;
        return (currToken == null) ? 0 : currToken.id();
    }
    
    @Override
    public final int currentTokenId() {
        final JsonToken currToken = this._currToken;
        return (currToken == null) ? 0 : currToken.id();
    }
    
    @Override
    public boolean hasCurrentToken() {
        return this._currToken != null;
    }
    
    @Override
    public boolean hasTokenId(final int n) {
        final JsonToken currToken = this._currToken;
        if (currToken == null) {
            return 0 == n;
        }
        return currToken.id() == n;
    }
    
    @Override
    public final boolean hasToken(final JsonToken jsonToken) {
        return this._currToken == jsonToken;
    }
    
    @Override
    public boolean isExpectedStartArrayToken() {
        return this._currToken == JsonToken.START_ARRAY;
    }
    
    @Override
    public boolean isExpectedStartObjectToken() {
        return this._currToken == JsonToken.START_OBJECT;
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        return this.delegate.getCurrentLocation();
    }
    
    @Override
    public JsonStreamContext getParsingContext() {
        return this._filterContext();
    }
    
    @Override
    public String getCurrentName() throws IOException {
        final JsonStreamContext filterContext = this._filterContext();
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            final JsonStreamContext parent = filterContext.getParent();
            return (parent == null) ? null : parent.getCurrentName();
        }
        return filterContext.getCurrentName();
    }
    
    @Override
    public void clearCurrentToken() {
        if (this._currToken != null) {
            this._lastClearedToken = this._currToken;
            this._currToken = null;
        }
    }
    
    @Override
    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }
    
    @Override
    public void overrideCurrentName(final String s) {
        throw new UnsupportedOperationException("Can not currently override name during filtering read");
    }
    
    @Override
    public JsonToken nextToken() throws IOException {
        if (!this._allowMultipleMatches && this._currToken != null && this._exposedContext == null && this._currToken.isScalarValue() && !this._headContext.isStartHandled() && !this._includePath && this._itemFilter == TokenFilter.INCLUDE_ALL) {
            return this._currToken = null;
        }
        TokenFilterContext exposedContext = this._exposedContext;
        if (exposedContext != null) {
            while (true) {
                final JsonToken nextTokenToRead = exposedContext.nextTokenToRead();
                if (nextTokenToRead != null) {
                    return this._currToken = nextTokenToRead;
                }
                if (exposedContext == this._headContext) {
                    this._exposedContext = null;
                    if (exposedContext.inArray()) {
                        return this._currToken = this.delegate.getCurrentToken();
                    }
                    break;
                }
                else {
                    exposedContext = this._headContext.findChildOf(exposedContext);
                    if ((this._exposedContext = exposedContext) == null) {
                        throw this._constructError("Unexpected problem: chain of filtered context broken");
                    }
                    continue;
                }
            }
        }
        JsonToken jsonToken = this.delegate.nextToken();
        if (jsonToken == null) {
            return this._currToken = jsonToken;
        }
        switch (jsonToken.id()) {
            case 3: {
                final TokenFilter itemFilter = this._itemFilter;
                if (itemFilter == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildArrayContext(itemFilter, true);
                    return this._currToken = jsonToken;
                }
                if (itemFilter == null) {
                    this.delegate.skipChildren();
                    break;
                }
                TokenFilter itemFilter2 = this._headContext.checkValue(itemFilter);
                if (itemFilter2 == null) {
                    this.delegate.skipChildren();
                    break;
                }
                if (itemFilter2 != TokenFilter.INCLUDE_ALL) {
                    itemFilter2 = itemFilter2.filterStartArray();
                }
                if ((this._itemFilter = itemFilter2) == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildArrayContext(itemFilter2, true);
                    return this._currToken = jsonToken;
                }
                this._headContext = this._headContext.createChildArrayContext(itemFilter2, false);
                if (!this._includePath) {
                    break;
                }
                final JsonToken nextTokenWithBuffering = this._nextTokenWithBuffering(this._headContext);
                if (nextTokenWithBuffering != null) {
                    return this._currToken = nextTokenWithBuffering;
                }
                break;
            }
            case 1: {
                final TokenFilter itemFilter3 = this._itemFilter;
                if (itemFilter3 == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildObjectContext(itemFilter3, true);
                    return this._currToken = jsonToken;
                }
                if (itemFilter3 == null) {
                    this.delegate.skipChildren();
                    break;
                }
                TokenFilter itemFilter4 = this._headContext.checkValue(itemFilter3);
                if (itemFilter4 == null) {
                    this.delegate.skipChildren();
                    break;
                }
                if (itemFilter4 != TokenFilter.INCLUDE_ALL) {
                    itemFilter4 = itemFilter4.filterStartObject();
                }
                if ((this._itemFilter = itemFilter4) == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildObjectContext(itemFilter4, true);
                    return this._currToken = jsonToken;
                }
                this._headContext = this._headContext.createChildObjectContext(itemFilter4, false);
                if (!this._includePath) {
                    break;
                }
                final JsonToken nextTokenWithBuffering2 = this._nextTokenWithBuffering(this._headContext);
                if (nextTokenWithBuffering2 != null) {
                    return this._currToken = nextTokenWithBuffering2;
                }
                break;
            }
            case 2:
            case 4: {
                final boolean startHandled = this._headContext.isStartHandled();
                final TokenFilter filter = this._headContext.getFilter();
                if (filter != null && filter != TokenFilter.INCLUDE_ALL) {
                    filter.filterFinishArray();
                }
                this._headContext = this._headContext.getParent();
                this._itemFilter = this._headContext.getFilter();
                if (startHandled) {
                    return this._currToken = jsonToken;
                }
                break;
            }
            case 5: {
                final String currentName = this.delegate.getCurrentName();
                final TokenFilter setFieldName = this._headContext.setFieldName(currentName);
                if (setFieldName == TokenFilter.INCLUDE_ALL) {
                    this._itemFilter = setFieldName;
                    if (!this._includePath && this._includeImmediateParent && !this._headContext.isStartHandled()) {
                        jsonToken = this._headContext.nextTokenToRead();
                        this._exposedContext = this._headContext;
                    }
                    return this._currToken = jsonToken;
                }
                if (setFieldName == null) {
                    this.delegate.nextToken();
                    this.delegate.skipChildren();
                    break;
                }
                final TokenFilter includeProperty = setFieldName.includeProperty(currentName);
                if (includeProperty == null) {
                    this.delegate.nextToken();
                    this.delegate.skipChildren();
                    break;
                }
                if ((this._itemFilter = includeProperty) == TokenFilter.INCLUDE_ALL) {
                    if (this._verifyAllowedMatches()) {
                        if (this._includePath) {
                            return this._currToken = jsonToken;
                        }
                    }
                    else {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                    }
                }
                if (!this._includePath) {
                    break;
                }
                final JsonToken nextTokenWithBuffering3 = this._nextTokenWithBuffering(this._headContext);
                if (nextTokenWithBuffering3 != null) {
                    return this._currToken = nextTokenWithBuffering3;
                }
                break;
            }
            default: {
                final TokenFilter itemFilter5 = this._itemFilter;
                if (itemFilter5 == TokenFilter.INCLUDE_ALL) {
                    return this._currToken = jsonToken;
                }
                if (itemFilter5 == null) {
                    break;
                }
                final TokenFilter checkValue = this._headContext.checkValue(itemFilter5);
                if ((checkValue == TokenFilter.INCLUDE_ALL || (checkValue != null && checkValue.includeValue(this.delegate))) && this._verifyAllowedMatches()) {
                    return this._currToken = jsonToken;
                }
                break;
            }
        }
        return this._nextToken2();
    }
    
    protected final JsonToken _nextToken2() throws IOException {
        while (true) {
            final JsonToken nextToken = this.delegate.nextToken();
            if (nextToken == null) {
                return this._currToken = nextToken;
            }
            switch (nextToken.id()) {
                case 3: {
                    final TokenFilter itemFilter = this._itemFilter;
                    if (itemFilter == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext(itemFilter, true);
                        return this._currToken = nextToken;
                    }
                    if (itemFilter == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    TokenFilter itemFilter2 = this._headContext.checkValue(itemFilter);
                    if (itemFilter2 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (itemFilter2 != TokenFilter.INCLUDE_ALL) {
                        itemFilter2 = itemFilter2.filterStartArray();
                    }
                    if ((this._itemFilter = itemFilter2) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext(itemFilter2, true);
                        return this._currToken = nextToken;
                    }
                    this._headContext = this._headContext.createChildArrayContext(itemFilter2, false);
                    if (!this._includePath) {
                        continue;
                    }
                    final JsonToken nextTokenWithBuffering = this._nextTokenWithBuffering(this._headContext);
                    if (nextTokenWithBuffering != null) {
                        return this._currToken = nextTokenWithBuffering;
                    }
                    continue;
                }
                case 1: {
                    final TokenFilter itemFilter3 = this._itemFilter;
                    if (itemFilter3 == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(itemFilter3, true);
                        return this._currToken = nextToken;
                    }
                    if (itemFilter3 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    TokenFilter itemFilter4 = this._headContext.checkValue(itemFilter3);
                    if (itemFilter4 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (itemFilter4 != TokenFilter.INCLUDE_ALL) {
                        itemFilter4 = itemFilter4.filterStartObject();
                    }
                    if ((this._itemFilter = itemFilter4) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(itemFilter4, true);
                        return this._currToken = nextToken;
                    }
                    this._headContext = this._headContext.createChildObjectContext(itemFilter4, false);
                    if (!this._includePath) {
                        continue;
                    }
                    final JsonToken nextTokenWithBuffering2 = this._nextTokenWithBuffering(this._headContext);
                    if (nextTokenWithBuffering2 != null) {
                        return this._currToken = nextTokenWithBuffering2;
                    }
                    continue;
                }
                case 2:
                case 4: {
                    final boolean startHandled = this._headContext.isStartHandled();
                    final TokenFilter filter = this._headContext.getFilter();
                    if (filter != null && filter != TokenFilter.INCLUDE_ALL) {
                        filter.filterFinishArray();
                    }
                    this._headContext = this._headContext.getParent();
                    this._itemFilter = this._headContext.getFilter();
                    if (startHandled) {
                        return this._currToken = nextToken;
                    }
                    continue;
                }
                case 5: {
                    final String currentName = this.delegate.getCurrentName();
                    final TokenFilter setFieldName = this._headContext.setFieldName(currentName);
                    if (setFieldName == TokenFilter.INCLUDE_ALL) {
                        this._itemFilter = setFieldName;
                        return this._currToken = nextToken;
                    }
                    if (setFieldName == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    final TokenFilter includeProperty = setFieldName.includeProperty(currentName);
                    if (includeProperty == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    if ((this._itemFilter = includeProperty) != TokenFilter.INCLUDE_ALL) {
                        if (!this._includePath) {
                            continue;
                        }
                        final JsonToken nextTokenWithBuffering3 = this._nextTokenWithBuffering(this._headContext);
                        if (nextTokenWithBuffering3 != null) {
                            return this._currToken = nextTokenWithBuffering3;
                        }
                        continue;
                        continue;
                    }
                    if (this._verifyAllowedMatches() && this._includePath) {
                        return this._currToken = nextToken;
                    }
                    continue;
                }
                default: {
                    final TokenFilter itemFilter5 = this._itemFilter;
                    if (itemFilter5 == TokenFilter.INCLUDE_ALL) {
                        return this._currToken = nextToken;
                    }
                    if (itemFilter5 == null) {
                        continue;
                    }
                    final TokenFilter checkValue = this._headContext.checkValue(itemFilter5);
                    if ((checkValue == TokenFilter.INCLUDE_ALL || (checkValue != null && checkValue.includeValue(this.delegate))) && this._verifyAllowedMatches()) {
                        return this._currToken = nextToken;
                    }
                    continue;
                    continue;
                }
            }
        }
    }
    
    protected final JsonToken _nextTokenWithBuffering(final TokenFilterContext tokenFilterContext) throws IOException {
        while (true) {
            final JsonToken nextToken = this.delegate.nextToken();
            if (nextToken == null) {
                return nextToken;
            }
            switch (nextToken.id()) {
                case 3: {
                    TokenFilter itemFilter = this._headContext.checkValue(this._itemFilter);
                    if (itemFilter == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (itemFilter != TokenFilter.INCLUDE_ALL) {
                        itemFilter = itemFilter.filterStartArray();
                    }
                    if ((this._itemFilter = itemFilter) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext(itemFilter, true);
                        return this._nextBuffered(tokenFilterContext);
                    }
                    this._headContext = this._headContext.createChildArrayContext(itemFilter, false);
                    continue;
                }
                case 1: {
                    final TokenFilter itemFilter2 = this._itemFilter;
                    if (itemFilter2 == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(itemFilter2, true);
                        return nextToken;
                    }
                    if (itemFilter2 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    TokenFilter itemFilter3 = this._headContext.checkValue(itemFilter2);
                    if (itemFilter3 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (itemFilter3 != TokenFilter.INCLUDE_ALL) {
                        itemFilter3 = itemFilter3.filterStartObject();
                    }
                    if ((this._itemFilter = itemFilter3) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(itemFilter3, true);
                        return this._nextBuffered(tokenFilterContext);
                    }
                    this._headContext = this._headContext.createChildObjectContext(itemFilter3, false);
                    continue;
                }
                case 2:
                case 4: {
                    final TokenFilter filter = this._headContext.getFilter();
                    if (filter != null && filter != TokenFilter.INCLUDE_ALL) {
                        filter.filterFinishArray();
                    }
                    final boolean b = this._headContext == tokenFilterContext && this._headContext.isStartHandled();
                    this._headContext = this._headContext.getParent();
                    this._itemFilter = this._headContext.getFilter();
                    if (b) {
                        return nextToken;
                    }
                    continue;
                }
                case 5: {
                    final String currentName = this.delegate.getCurrentName();
                    final TokenFilter setFieldName = this._headContext.setFieldName(currentName);
                    if (setFieldName == TokenFilter.INCLUDE_ALL) {
                        this._itemFilter = setFieldName;
                        return this._nextBuffered(tokenFilterContext);
                    }
                    if (setFieldName == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    final TokenFilter includeProperty = setFieldName.includeProperty(currentName);
                    if (includeProperty == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    if ((this._itemFilter = includeProperty) != TokenFilter.INCLUDE_ALL) {
                        continue;
                    }
                    if (this._verifyAllowedMatches()) {
                        return this._nextBuffered(tokenFilterContext);
                    }
                    this._itemFilter = this._headContext.setFieldName(currentName);
                    continue;
                }
                default: {
                    final TokenFilter itemFilter4 = this._itemFilter;
                    if (itemFilter4 == TokenFilter.INCLUDE_ALL) {
                        return this._nextBuffered(tokenFilterContext);
                    }
                    if (itemFilter4 == null) {
                        continue;
                    }
                    final TokenFilter checkValue = this._headContext.checkValue(itemFilter4);
                    if ((checkValue == TokenFilter.INCLUDE_ALL || (checkValue != null && checkValue.includeValue(this.delegate))) && this._verifyAllowedMatches()) {
                        return this._nextBuffered(tokenFilterContext);
                    }
                    continue;
                }
            }
        }
    }
    
    private JsonToken _nextBuffered(final TokenFilterContext exposedContext) throws IOException {
        this._exposedContext = exposedContext;
        TokenFilterContext child = exposedContext;
        final JsonToken nextTokenToRead = child.nextTokenToRead();
        if (nextTokenToRead != null) {
            return nextTokenToRead;
        }
        while (child != this._headContext) {
            child = this._exposedContext.findChildOf(child);
            if ((this._exposedContext = child) == null) {
                throw this._constructError("Unexpected problem: chain of filtered context broken");
            }
            final JsonToken nextTokenToRead2 = this._exposedContext.nextTokenToRead();
            if (nextTokenToRead2 != null) {
                return nextTokenToRead2;
            }
        }
        throw this._constructError("Internal error: failed to locate expected buffered tokens");
    }
    
    private final boolean _verifyAllowedMatches() throws IOException {
        if (this._matchCount == 0 || this._allowMultipleMatches) {
            ++this._matchCount;
            return true;
        }
        return false;
    }
    
    @Override
    public JsonToken nextValue() throws IOException {
        JsonToken jsonToken = this.nextToken();
        if (jsonToken == JsonToken.FIELD_NAME) {
            jsonToken = this.nextToken();
        }
        return jsonToken;
    }
    
    @Override
    public JsonParser skipChildren() throws IOException {
        if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
            return this;
        }
        int n = 1;
        while (true) {
            final JsonToken nextToken = this.nextToken();
            if (nextToken == null) {
                return this;
            }
            if (nextToken.isStructStart()) {
                ++n;
            }
            else {
                if (nextToken.isStructEnd() && --n == 0) {
                    return this;
                }
                continue;
            }
        }
    }
    
    @Override
    public String getText() throws IOException {
        return this.delegate.getText();
    }
    
    @Override
    public boolean hasTextCharacters() {
        return this.delegate.hasTextCharacters();
    }
    
    @Override
    public char[] getTextCharacters() throws IOException {
        return this.delegate.getTextCharacters();
    }
    
    @Override
    public int getTextLength() throws IOException {
        return this.delegate.getTextLength();
    }
    
    @Override
    public int getTextOffset() throws IOException {
        return this.delegate.getTextOffset();
    }
    
    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        return this.delegate.getBigIntegerValue();
    }
    
    @Override
    public boolean getBooleanValue() throws IOException {
        return this.delegate.getBooleanValue();
    }
    
    @Override
    public byte getByteValue() throws IOException {
        return this.delegate.getByteValue();
    }
    
    @Override
    public short getShortValue() throws IOException {
        return this.delegate.getShortValue();
    }
    
    @Override
    public BigDecimal getDecimalValue() throws IOException {
        return this.delegate.getDecimalValue();
    }
    
    @Override
    public double getDoubleValue() throws IOException {
        return this.delegate.getDoubleValue();
    }
    
    @Override
    public float getFloatValue() throws IOException {
        return this.delegate.getFloatValue();
    }
    
    @Override
    public int getIntValue() throws IOException {
        return this.delegate.getIntValue();
    }
    
    @Override
    public long getLongValue() throws IOException {
        return this.delegate.getLongValue();
    }
    
    @Override
    public NumberType getNumberType() throws IOException {
        return this.delegate.getNumberType();
    }
    
    @Override
    public Number getNumberValue() throws IOException {
        return this.delegate.getNumberValue();
    }
    
    @Override
    public int getValueAsInt() throws IOException {
        return this.delegate.getValueAsInt();
    }
    
    @Override
    public int getValueAsInt(final int n) throws IOException {
        return this.delegate.getValueAsInt(n);
    }
    
    @Override
    public long getValueAsLong() throws IOException {
        return this.delegate.getValueAsLong();
    }
    
    @Override
    public long getValueAsLong(final long n) throws IOException {
        return this.delegate.getValueAsLong(n);
    }
    
    @Override
    public double getValueAsDouble() throws IOException {
        return this.delegate.getValueAsDouble();
    }
    
    @Override
    public double getValueAsDouble(final double n) throws IOException {
        return this.delegate.getValueAsDouble(n);
    }
    
    @Override
    public boolean getValueAsBoolean() throws IOException {
        return this.delegate.getValueAsBoolean();
    }
    
    @Override
    public boolean getValueAsBoolean(final boolean b) throws IOException {
        return this.delegate.getValueAsBoolean(b);
    }
    
    @Override
    public String getValueAsString() throws IOException {
        return this.delegate.getValueAsString();
    }
    
    @Override
    public String getValueAsString(final String s) throws IOException {
        return this.delegate.getValueAsString(s);
    }
    
    @Override
    public Object getEmbeddedObject() throws IOException {
        return this.delegate.getEmbeddedObject();
    }
    
    @Override
    public byte[] getBinaryValue(final Base64Variant base64Variant) throws IOException {
        return this.delegate.getBinaryValue(base64Variant);
    }
    
    @Override
    public int readBinaryValue(final Base64Variant base64Variant, final OutputStream outputStream) throws IOException {
        return this.delegate.readBinaryValue(base64Variant, outputStream);
    }
    
    @Override
    public JsonLocation getTokenLocation() {
        return this.delegate.getTokenLocation();
    }
    
    protected JsonStreamContext _filterContext() {
        if (this._exposedContext != null) {
            return this._exposedContext;
        }
        return this._headContext;
    }
}
