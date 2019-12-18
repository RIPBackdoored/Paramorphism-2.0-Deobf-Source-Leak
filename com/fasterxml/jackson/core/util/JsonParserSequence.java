package com.fasterxml.jackson.core.util;

import java.util.*;
import java.io.*;
import com.fasterxml.jackson.core.*;

public class JsonParserSequence extends JsonParserDelegate
{
    protected final JsonParser[] _parsers;
    protected final boolean _checkForExistingToken;
    protected int _nextParserIndex;
    protected boolean _hasToken;
    
    @Deprecated
    protected JsonParserSequence(final JsonParser[] array) {
        this(false, array);
    }
    
    protected JsonParserSequence(final boolean checkForExistingToken, final JsonParser[] parsers) {
        super(parsers[0]);
        this._checkForExistingToken = checkForExistingToken;
        this._hasToken = (checkForExistingToken && this.delegate.hasCurrentToken());
        this._parsers = parsers;
        this._nextParserIndex = 1;
    }
    
    public static JsonParserSequence createFlattened(final boolean b, final JsonParser jsonParser, final JsonParser jsonParser2) {
        if (!(jsonParser instanceof JsonParserSequence) && !(jsonParser2 instanceof JsonParserSequence)) {
            return new JsonParserSequence(b, new JsonParser[] { jsonParser, jsonParser2 });
        }
        final ArrayList<JsonParser> list = new ArrayList<JsonParser>();
        if (jsonParser instanceof JsonParserSequence) {
            ((JsonParserSequence)jsonParser).addFlattenedActiveParsers(list);
        }
        else {
            list.add(jsonParser);
        }
        if (jsonParser2 instanceof JsonParserSequence) {
            ((JsonParserSequence)jsonParser2).addFlattenedActiveParsers(list);
        }
        else {
            list.add(jsonParser2);
        }
        return new JsonParserSequence(b, list.toArray(new JsonParser[list.size()]));
    }
    
    @Deprecated
    public static JsonParserSequence createFlattened(final JsonParser jsonParser, final JsonParser jsonParser2) {
        return createFlattened(false, jsonParser, jsonParser2);
    }
    
    protected void addFlattenedActiveParsers(final List<JsonParser> list) {
        for (int i = this._nextParserIndex - 1; i < this._parsers.length; ++i) {
            final JsonParser jsonParser = this._parsers[i];
            if (jsonParser instanceof JsonParserSequence) {
                ((JsonParserSequence)jsonParser).addFlattenedActiveParsers(list);
            }
            else {
                list.add(jsonParser);
            }
        }
    }
    
    @Override
    public void close() throws IOException {
        do {
            this.delegate.close();
        } while (this.switchToNext());
    }
    
    @Override
    public JsonToken nextToken() throws IOException {
        if (this.delegate == null) {
            return null;
        }
        if (this._hasToken) {
            this._hasToken = false;
            return this.delegate.currentToken();
        }
        final JsonToken nextToken = this.delegate.nextToken();
        if (nextToken == null) {
            return this.switchAndReturnNext();
        }
        return nextToken;
    }
    
    @Override
    public JsonParser skipChildren() throws IOException {
        if (this.delegate.currentToken() != JsonToken.START_OBJECT && this.delegate.currentToken() != JsonToken.START_ARRAY) {
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
    
    public int containedParsersCount() {
        return this._parsers.length;
    }
    
    protected boolean switchToNext() {
        if (this._nextParserIndex < this._parsers.length) {
            this.delegate = this._parsers[this._nextParserIndex++];
            return true;
        }
        return false;
    }
    
    protected JsonToken switchAndReturnNext() throws IOException {
        while (this._nextParserIndex < this._parsers.length) {
            this.delegate = this._parsers[this._nextParserIndex++];
            if (this._checkForExistingToken && this.delegate.hasCurrentToken()) {
                return this.delegate.getCurrentToken();
            }
            final JsonToken nextToken = this.delegate.nextToken();
            if (nextToken != null) {
                return nextToken;
            }
        }
        return null;
    }
}
