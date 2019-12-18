package com.fasterxml.jackson.core.filter;

import java.io.*;
import com.fasterxml.jackson.core.*;

public class TokenFilterContext extends JsonStreamContext
{
    protected final TokenFilterContext _parent;
    protected TokenFilterContext _child;
    protected String _currentName;
    protected TokenFilter _filter;
    protected boolean _startHandled;
    protected boolean _needToHandleName;
    
    protected TokenFilterContext(final int type, final TokenFilterContext parent, final TokenFilter filter, final boolean startHandled) {
        super();
        this._type = type;
        this._parent = parent;
        this._filter = filter;
        this._index = -1;
        this._startHandled = startHandled;
        this._needToHandleName = false;
    }
    
    protected TokenFilterContext reset(final int type, final TokenFilter filter, final boolean startHandled) {
        this._type = type;
        this._filter = filter;
        this._index = -1;
        this._currentName = null;
        this._startHandled = startHandled;
        this._needToHandleName = false;
        return this;
    }
    
    public static TokenFilterContext createRootContext(final TokenFilter tokenFilter) {
        return new TokenFilterContext(0, null, tokenFilter, true);
    }
    
    public TokenFilterContext createChildArrayContext(final TokenFilter tokenFilter, final boolean b) {
        final TokenFilterContext child = this._child;
        if (child == null) {
            return this._child = new TokenFilterContext(1, this, tokenFilter, b);
        }
        return child.reset(1, tokenFilter, b);
    }
    
    public TokenFilterContext createChildObjectContext(final TokenFilter tokenFilter, final boolean b) {
        final TokenFilterContext child = this._child;
        if (child == null) {
            return this._child = new TokenFilterContext(2, this, tokenFilter, b);
        }
        return child.reset(2, tokenFilter, b);
    }
    
    public TokenFilter setFieldName(final String currentName) throws JsonProcessingException {
        this._currentName = currentName;
        this._needToHandleName = true;
        return this._filter;
    }
    
    public TokenFilter checkValue(final TokenFilter tokenFilter) {
        if (this._type == 2) {
            return tokenFilter;
        }
        final int n = ++this._index;
        if (this._type == 1) {
            return tokenFilter.includeElement(n);
        }
        return tokenFilter.includeRootValue(n);
    }
    
    public void writePath(final JsonGenerator jsonGenerator) throws IOException {
        if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._parent != null) {
            this._parent._writePath(jsonGenerator);
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                jsonGenerator.writeFieldName(this._currentName);
            }
        }
        else {
            this._startHandled = true;
            if (this._type == 2) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName(this._currentName);
            }
            else if (this._type == 1) {
                jsonGenerator.writeStartArray();
            }
        }
    }
    
    public void writeImmediatePath(final JsonGenerator jsonGenerator) throws IOException {
        if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                jsonGenerator.writeFieldName(this._currentName);
            }
        }
        else {
            this._startHandled = true;
            if (this._type == 2) {
                jsonGenerator.writeStartObject();
                if (this._needToHandleName) {
                    jsonGenerator.writeFieldName(this._currentName);
                }
            }
            else if (this._type == 1) {
                jsonGenerator.writeStartArray();
            }
        }
    }
    
    private void _writePath(final JsonGenerator jsonGenerator) throws IOException {
        if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._parent != null) {
            this._parent._writePath(jsonGenerator);
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                this._needToHandleName = false;
                jsonGenerator.writeFieldName(this._currentName);
            }
        }
        else {
            this._startHandled = true;
            if (this._type == 2) {
                jsonGenerator.writeStartObject();
                if (this._needToHandleName) {
                    this._needToHandleName = false;
                    jsonGenerator.writeFieldName(this._currentName);
                }
            }
            else if (this._type == 1) {
                jsonGenerator.writeStartArray();
            }
        }
    }
    
    public TokenFilterContext closeArray(final JsonGenerator jsonGenerator) throws IOException {
        if (this._startHandled) {
            jsonGenerator.writeEndArray();
        }
        if (this._filter != null && this._filter != TokenFilter.INCLUDE_ALL) {
            this._filter.filterFinishArray();
        }
        return this._parent;
    }
    
    public TokenFilterContext closeObject(final JsonGenerator jsonGenerator) throws IOException {
        if (this._startHandled) {
            jsonGenerator.writeEndObject();
        }
        if (this._filter != null && this._filter != TokenFilter.INCLUDE_ALL) {
            this._filter.filterFinishObject();
        }
        return this._parent;
    }
    
    public void skipParentChecks() {
        this._filter = null;
        for (TokenFilterContext tokenFilterContext = this._parent; tokenFilterContext != null; tokenFilterContext = tokenFilterContext._parent) {
            this._parent._filter = null;
        }
    }
    
    @Override
    public Object getCurrentValue() {
        return null;
    }
    
    @Override
    public void setCurrentValue(final Object o) {
    }
    
    @Override
    public final TokenFilterContext getParent() {
        return this._parent;
    }
    
    @Override
    public final String getCurrentName() {
        return this._currentName;
    }
    
    @Override
    public boolean hasCurrentName() {
        return this._currentName != null;
    }
    
    public TokenFilter getFilter() {
        return this._filter;
    }
    
    public boolean isStartHandled() {
        return this._startHandled;
    }
    
    public JsonToken nextTokenToRead() {
        if (!this._startHandled) {
            this._startHandled = true;
            if (this._type == 2) {
                return JsonToken.START_OBJECT;
            }
            return JsonToken.START_ARRAY;
        }
        else {
            if (this._needToHandleName && this._type == 2) {
                this._needToHandleName = false;
                return JsonToken.FIELD_NAME;
            }
            return null;
        }
    }
    
    public TokenFilterContext findChildOf(final TokenFilterContext tokenFilterContext) {
        if (this._parent == tokenFilterContext) {
            return this;
        }
        TokenFilterContext parent2;
        for (TokenFilterContext parent = this._parent; parent != null; parent = parent2) {
            parent2 = parent._parent;
            if (parent2 == tokenFilterContext) {
                return parent;
            }
        }
        return null;
    }
    
    protected void appendDesc(final StringBuilder sb) {
        if (this._parent != null) {
            this._parent.appendDesc(sb);
        }
        if (this._type == 2) {
            sb.append('{');
            if (this._currentName != null) {
                sb.append('\"');
                sb.append(this._currentName);
                sb.append('\"');
            }
            else {
                sb.append('?');
            }
            sb.append('}');
        }
        else if (this._type == 1) {
            sb.append('[');
            sb.append(this.getCurrentIndex());
            sb.append(']');
        }
        else {
            sb.append("/");
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        this.appendDesc(sb);
        return sb.toString();
    }
    
    @Override
    public JsonStreamContext getParent() {
        return this.getParent();
    }
}
