package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.util.*;
import java.util.*;

@JacksonStdImpl
public class UntypedObjectDeserializer extends StdDeserializer<Object> implements ResolvableDeserializer, ContextualDeserializer
{
    private static final long serialVersionUID = 1L;
    protected static final Object[] NO_OBJECTS;
    protected JsonDeserializer<Object> _mapDeserializer;
    protected JsonDeserializer<Object> _listDeserializer;
    protected JsonDeserializer<Object> _stringDeserializer;
    protected JsonDeserializer<Object> _numberDeserializer;
    protected JavaType _listType;
    protected JavaType _mapType;
    protected final boolean _nonMerging;
    
    @Deprecated
    public UntypedObjectDeserializer() {
        this(null, null);
    }
    
    public UntypedObjectDeserializer(final JavaType listType, final JavaType mapType) {
        super(Object.class);
        this._listType = listType;
        this._mapType = mapType;
        this._nonMerging = false;
    }
    
    public UntypedObjectDeserializer(final UntypedObjectDeserializer untypedObjectDeserializer, final JsonDeserializer<?> mapDeserializer, final JsonDeserializer<?> listDeserializer, final JsonDeserializer<?> stringDeserializer, final JsonDeserializer<?> numberDeserializer) {
        super(Object.class);
        this._mapDeserializer = (JsonDeserializer<Object>)mapDeserializer;
        this._listDeserializer = (JsonDeserializer<Object>)listDeserializer;
        this._stringDeserializer = (JsonDeserializer<Object>)stringDeserializer;
        this._numberDeserializer = (JsonDeserializer<Object>)numberDeserializer;
        this._listType = untypedObjectDeserializer._listType;
        this._mapType = untypedObjectDeserializer._mapType;
        this._nonMerging = untypedObjectDeserializer._nonMerging;
    }
    
    protected UntypedObjectDeserializer(final UntypedObjectDeserializer untypedObjectDeserializer, final boolean nonMerging) {
        super(Object.class);
        this._mapDeserializer = untypedObjectDeserializer._mapDeserializer;
        this._listDeserializer = untypedObjectDeserializer._listDeserializer;
        this._stringDeserializer = untypedObjectDeserializer._stringDeserializer;
        this._numberDeserializer = untypedObjectDeserializer._numberDeserializer;
        this._listType = untypedObjectDeserializer._listType;
        this._mapType = untypedObjectDeserializer._mapType;
        this._nonMerging = nonMerging;
    }
    
    @Override
    public void resolve(final DeserializationContext deserializationContext) throws JsonMappingException {
        final JavaType constructType = deserializationContext.constructType(Object.class);
        final JavaType constructType2 = deserializationContext.constructType(String.class);
        final TypeFactory typeFactory = deserializationContext.getTypeFactory();
        if (this._listType == null) {
            this._listDeserializer = this._clearIfStdImpl(this._findCustomDeser(deserializationContext, typeFactory.constructCollectionType(List.class, constructType)));
        }
        else {
            this._listDeserializer = this._findCustomDeser(deserializationContext, this._listType);
        }
        if (this._mapType == null) {
            this._mapDeserializer = this._clearIfStdImpl(this._findCustomDeser(deserializationContext, typeFactory.constructMapType(Map.class, constructType2, constructType)));
        }
        else {
            this._mapDeserializer = this._findCustomDeser(deserializationContext, this._mapType);
        }
        this._stringDeserializer = this._clearIfStdImpl(this._findCustomDeser(deserializationContext, constructType2));
        this._numberDeserializer = this._clearIfStdImpl(this._findCustomDeser(deserializationContext, typeFactory.constructType(Number.class)));
        final JavaType unknownType = TypeFactory.unknownType();
        this._mapDeserializer = (JsonDeserializer<Object>)deserializationContext.handleSecondaryContextualization(this._mapDeserializer, null, unknownType);
        this._listDeserializer = (JsonDeserializer<Object>)deserializationContext.handleSecondaryContextualization(this._listDeserializer, null, unknownType);
        this._stringDeserializer = (JsonDeserializer<Object>)deserializationContext.handleSecondaryContextualization(this._stringDeserializer, null, unknownType);
        this._numberDeserializer = (JsonDeserializer<Object>)deserializationContext.handleSecondaryContextualization(this._numberDeserializer, null, unknownType);
    }
    
    protected JsonDeserializer<Object> _findCustomDeser(final DeserializationContext deserializationContext, final JavaType javaType) throws JsonMappingException {
        return deserializationContext.findNonContextualValueDeserializer(javaType);
    }
    
    protected JsonDeserializer<Object> _clearIfStdImpl(final JsonDeserializer<Object> jsonDeserializer) {
        return ClassUtil.isJacksonStdImpl(jsonDeserializer) ? null : jsonDeserializer;
    }
    
    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext deserializationContext, final BeanProperty beanProperty) throws JsonMappingException {
        final boolean b = beanProperty == null && Boolean.FALSE.equals(deserializationContext.getConfig().getDefaultMergeable(Object.class));
        if (this._stringDeserializer == null && this._numberDeserializer == null && this._mapDeserializer == null && this._listDeserializer == null && this.getClass() == UntypedObjectDeserializer.class) {
            return Vanilla.instance(b);
        }
        if (b != this._nonMerging) {
            return new UntypedObjectDeserializer(this, b);
        }
        return this;
    }
    
    @Override
    public boolean isCachable() {
        return true;
    }
    
    @Override
    public Boolean supportsUpdate(final DeserializationConfig deserializationConfig) {
        return null;
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        switch (jsonParser.getCurrentTokenId()) {
            case 1:
            case 2:
            case 5: {
                if (this._mapDeserializer != null) {
                    return this._mapDeserializer.deserialize(jsonParser, deserializationContext);
                }
                return this.mapObject(jsonParser, deserializationContext);
            }
            case 3: {
                if (deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                    return this.mapArrayToArray(jsonParser, deserializationContext);
                }
                if (this._listDeserializer != null) {
                    return this._listDeserializer.deserialize(jsonParser, deserializationContext);
                }
                return this.mapArray(jsonParser, deserializationContext);
            }
            case 12: {
                return jsonParser.getEmbeddedObject();
            }
            case 6: {
                if (this._stringDeserializer != null) {
                    return this._stringDeserializer.deserialize(jsonParser, deserializationContext);
                }
                return jsonParser.getText();
            }
            case 7: {
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jsonParser, deserializationContext);
                }
                if (deserializationContext.hasSomeOfFeatures(UntypedObjectDeserializer.F_MASK_INT_COERCIONS)) {
                    return this._coerceIntegral(jsonParser, deserializationContext);
                }
                return jsonParser.getNumberValue();
            }
            case 8: {
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jsonParser, deserializationContext);
                }
                if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jsonParser.getDecimalValue();
                }
                return jsonParser.getNumberValue();
            }
            case 9: {
                return Boolean.TRUE;
            }
            case 10: {
                return Boolean.FALSE;
            }
            case 11: {
                return null;
            }
            default: {
                return deserializationContext.handleUnexpectedToken(Object.class, jsonParser);
            }
        }
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jsonParser, final DeserializationContext deserializationContext, final TypeDeserializer typeDeserializer) throws IOException {
        switch (jsonParser.getCurrentTokenId()) {
            case 1:
            case 3:
            case 5: {
                return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
            }
            case 12: {
                return jsonParser.getEmbeddedObject();
            }
            case 6: {
                if (this._stringDeserializer != null) {
                    return this._stringDeserializer.deserialize(jsonParser, deserializationContext);
                }
                return jsonParser.getText();
            }
            case 7: {
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jsonParser, deserializationContext);
                }
                if (deserializationContext.hasSomeOfFeatures(UntypedObjectDeserializer.F_MASK_INT_COERCIONS)) {
                    return this._coerceIntegral(jsonParser, deserializationContext);
                }
                return jsonParser.getNumberValue();
            }
            case 8: {
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jsonParser, deserializationContext);
                }
                if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jsonParser.getDecimalValue();
                }
                return jsonParser.getNumberValue();
            }
            case 9: {
                return Boolean.TRUE;
            }
            case 10: {
                return Boolean.FALSE;
            }
            case 11: {
                return null;
            }
            default: {
                return deserializationContext.handleUnexpectedToken(Object.class, jsonParser);
            }
        }
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object o) throws IOException {
        if (this._nonMerging) {
            return this.deserialize(jsonParser, deserializationContext);
        }
        switch (jsonParser.getCurrentTokenId()) {
            case 1:
            case 2:
            case 5: {
                if (this._mapDeserializer != null) {
                    return this._mapDeserializer.deserialize(jsonParser, deserializationContext, o);
                }
                if (o instanceof Map) {
                    return this.mapObject(jsonParser, deserializationContext, (Map<Object, Object>)o);
                }
                return this.mapObject(jsonParser, deserializationContext);
            }
            case 3: {
                if (this._listDeserializer != null) {
                    return this._listDeserializer.deserialize(jsonParser, deserializationContext, o);
                }
                if (o instanceof Collection) {
                    return this.mapArray(jsonParser, deserializationContext, (Collection<Object>)o);
                }
                if (deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                    return this.mapArrayToArray(jsonParser, deserializationContext);
                }
                return this.mapArray(jsonParser, deserializationContext);
            }
            case 12: {
                return jsonParser.getEmbeddedObject();
            }
            case 6: {
                if (this._stringDeserializer != null) {
                    return this._stringDeserializer.deserialize(jsonParser, deserializationContext, o);
                }
                return jsonParser.getText();
            }
            case 7: {
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jsonParser, deserializationContext, o);
                }
                if (deserializationContext.hasSomeOfFeatures(UntypedObjectDeserializer.F_MASK_INT_COERCIONS)) {
                    return this._coerceIntegral(jsonParser, deserializationContext);
                }
                return jsonParser.getNumberValue();
            }
            case 8: {
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jsonParser, deserializationContext, o);
                }
                if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jsonParser.getDecimalValue();
                }
                return jsonParser.getNumberValue();
            }
            case 9: {
                return Boolean.TRUE;
            }
            case 10: {
                return Boolean.FALSE;
            }
            case 11: {
                return null;
            }
            default: {
                return this.deserialize(jsonParser, deserializationContext);
            }
        }
    }
    
    protected Object mapArray(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            return new ArrayList(2);
        }
        final Object deserialize = this.deserialize(jsonParser, deserializationContext);
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            final ArrayList<Object> list = new ArrayList<Object>(2);
            list.add(deserialize);
            return list;
        }
        final Object deserialize2 = this.deserialize(jsonParser, deserializationContext);
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            final ArrayList<Object> list2 = new ArrayList<Object>(2);
            list2.add(deserialize);
            list2.add(deserialize2);
            return list2;
        }
        final ObjectBuffer leaseObjectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] array = leaseObjectBuffer.resetAndStart();
        int n = 0;
        array[n++] = deserialize;
        array[n++] = deserialize2;
        int n2 = n;
        do {
            final Object deserialize3 = this.deserialize(jsonParser, deserializationContext);
            ++n2;
            if (n >= array.length) {
                array = leaseObjectBuffer.appendCompletedChunk(array);
                n = 0;
            }
            array[n++] = deserialize3;
        } while (jsonParser.nextToken() != JsonToken.END_ARRAY);
        final ArrayList list3 = new ArrayList<Object>(n2);
        leaseObjectBuffer.completeAndClearBuffer(array, n, (List<Object>)list3);
        return list3;
    }
    
    protected Object mapArray(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Collection<Object> collection) throws IOException {
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            collection.add(this.deserialize(jsonParser, deserializationContext));
        }
        return collection;
    }
    
    protected Object mapObject(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final JsonToken currentToken = jsonParser.getCurrentToken();
        String s;
        if (currentToken == JsonToken.START_OBJECT) {
            s = jsonParser.nextFieldName();
        }
        else if (currentToken == JsonToken.FIELD_NAME) {
            s = jsonParser.getCurrentName();
        }
        else {
            if (currentToken != JsonToken.END_OBJECT) {
                return deserializationContext.handleUnexpectedToken(this.handledType(), jsonParser);
            }
            s = null;
        }
        if (s == null) {
            return new LinkedHashMap(2);
        }
        jsonParser.nextToken();
        final Object deserialize = this.deserialize(jsonParser, deserializationContext);
        final String nextFieldName = jsonParser.nextFieldName();
        if (nextFieldName == null) {
            final LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>(2);
            linkedHashMap.put(s, deserialize);
            return linkedHashMap;
        }
        jsonParser.nextToken();
        final Object deserialize2 = this.deserialize(jsonParser, deserializationContext);
        String s2 = jsonParser.nextFieldName();
        if (s2 == null) {
            final LinkedHashMap<String, Object> linkedHashMap2 = new LinkedHashMap<String, Object>(4);
            linkedHashMap2.put(s, deserialize);
            linkedHashMap2.put(nextFieldName, deserialize2);
            return linkedHashMap2;
        }
        final LinkedHashMap<String, Object> linkedHashMap3 = new LinkedHashMap<String, Object>();
        linkedHashMap3.put(s, deserialize);
        linkedHashMap3.put(nextFieldName, deserialize2);
        do {
            jsonParser.nextToken();
            linkedHashMap3.put(s2, this.deserialize(jsonParser, deserializationContext));
        } while ((s2 = jsonParser.nextFieldName()) != null);
        return linkedHashMap3;
    }
    
    protected Object[] mapArrayToArray(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            return UntypedObjectDeserializer.NO_OBJECTS;
        }
        final ObjectBuffer leaseObjectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] array = leaseObjectBuffer.resetAndStart();
        int n = 0;
        do {
            final Object deserialize = this.deserialize(jsonParser, deserializationContext);
            if (n >= array.length) {
                array = leaseObjectBuffer.appendCompletedChunk(array);
                n = 0;
            }
            array[n++] = deserialize;
        } while (jsonParser.nextToken() != JsonToken.END_ARRAY);
        return leaseObjectBuffer.completeAndClearBuffer(array, n);
    }
    
    protected Object mapObject(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Map<Object, Object> map) throws IOException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
        }
        if (jsonToken == JsonToken.END_OBJECT) {
            return map;
        }
        String s = jsonParser.getCurrentName();
        do {
            jsonParser.nextToken();
            final Object value = map.get(s);
            Object o;
            if (value != null) {
                o = this.deserialize(jsonParser, deserializationContext, value);
            }
            else {
                o = this.deserialize(jsonParser, deserializationContext);
            }
            if (o != value) {
                map.put(s, o);
            }
        } while ((s = jsonParser.nextFieldName()) != null);
        return map;
    }
    
    static {
        NO_OBJECTS = new Object[0];
    }
    
    @JacksonStdImpl
    public static class Vanilla extends StdDeserializer<Object>
    {
        private static final long serialVersionUID = 1L;
        public static final Vanilla std;
        protected final boolean _nonMerging;
        
        public Vanilla() {
            this(false);
        }
        
        protected Vanilla(final boolean nonMerging) {
            super(Object.class);
            this._nonMerging = nonMerging;
        }
        
        public static Vanilla instance(final boolean b) {
            if (b) {
                return new Vanilla(true);
            }
            return Vanilla.std;
        }
        
        @Override
        public Boolean supportsUpdate(final DeserializationConfig deserializationConfig) {
            return this._nonMerging ? Boolean.FALSE : null;
        }
        
        @Override
        public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
            switch (jsonParser.getCurrentTokenId()) {
                case 1: {
                    if (jsonParser.nextToken() == JsonToken.END_OBJECT) {
                        return new LinkedHashMap(2);
                    }
                    return this.mapObject(jsonParser, deserializationContext);
                }
                case 5: {
                    return this.mapObject(jsonParser, deserializationContext);
                }
                case 3: {
                    if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                        if (deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                            return UntypedObjectDeserializer.NO_OBJECTS;
                        }
                        return new ArrayList(2);
                    }
                    else {
                        if (deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                            return this.mapArrayToArray(jsonParser, deserializationContext);
                        }
                        return this.mapArray(jsonParser, deserializationContext);
                    }
                    break;
                }
                case 12: {
                    return jsonParser.getEmbeddedObject();
                }
                case 6: {
                    return jsonParser.getText();
                }
                case 7: {
                    if (deserializationContext.hasSomeOfFeatures(Vanilla.F_MASK_INT_COERCIONS)) {
                        return this._coerceIntegral(jsonParser, deserializationContext);
                    }
                    return jsonParser.getNumberValue();
                }
                case 8: {
                    if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                        return jsonParser.getDecimalValue();
                    }
                    return jsonParser.getNumberValue();
                }
                case 9: {
                    return Boolean.TRUE;
                }
                case 10: {
                    return Boolean.FALSE;
                }
                case 2: {
                    return new LinkedHashMap(2);
                }
                case 11: {
                    return null;
                }
                default: {
                    return deserializationContext.handleUnexpectedToken(Object.class, jsonParser);
                }
            }
        }
        
        @Override
        public Object deserializeWithType(final JsonParser jsonParser, final DeserializationContext deserializationContext, final TypeDeserializer typeDeserializer) throws IOException {
            switch (jsonParser.getCurrentTokenId()) {
                case 1:
                case 3:
                case 5: {
                    return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
                }
                case 6: {
                    return jsonParser.getText();
                }
                case 7: {
                    if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                        return jsonParser.getBigIntegerValue();
                    }
                    return jsonParser.getNumberValue();
                }
                case 8: {
                    if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                        return jsonParser.getDecimalValue();
                    }
                    return jsonParser.getNumberValue();
                }
                case 9: {
                    return Boolean.TRUE;
                }
                case 10: {
                    return Boolean.FALSE;
                }
                case 12: {
                    return jsonParser.getEmbeddedObject();
                }
                case 11: {
                    return null;
                }
                default: {
                    return deserializationContext.handleUnexpectedToken(Object.class, jsonParser);
                }
            }
        }
        
        @Override
        public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object o) throws IOException {
            if (this._nonMerging) {
                return this.deserialize(jsonParser, deserializationContext);
            }
            switch (jsonParser.getCurrentTokenId()) {
                case 2:
                case 4: {
                    return o;
                }
                case 1: {
                    if (jsonParser.nextToken() == JsonToken.END_OBJECT) {
                        return o;
                    }
                }
                case 5: {
                    if (o instanceof Map) {
                        final Map map = (Map)o;
                        String s = jsonParser.getCurrentName();
                        do {
                            jsonParser.nextToken();
                            final Object value = map.get(s);
                            Object o2;
                            if (value != null) {
                                o2 = this.deserialize(jsonParser, deserializationContext, value);
                            }
                            else {
                                o2 = this.deserialize(jsonParser, deserializationContext);
                            }
                            if (o2 != value) {
                                map.put(s, o2);
                            }
                        } while ((s = jsonParser.nextFieldName()) != null);
                        return o;
                    }
                    break;
                }
                case 3: {
                    if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                        return o;
                    }
                    if (o instanceof Collection) {
                        final Collection collection = (Collection)o;
                        do {
                            collection.add(this.deserialize(jsonParser, deserializationContext));
                        } while (jsonParser.nextToken() != JsonToken.END_ARRAY);
                        return o;
                    }
                    break;
                }
            }
            return this.deserialize(jsonParser, deserializationContext);
        }
        
        protected Object mapArray(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
            final Object deserialize = this.deserialize(jsonParser, deserializationContext);
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                final ArrayList<Object> list = new ArrayList<Object>(2);
                list.add(deserialize);
                return list;
            }
            final Object deserialize2 = this.deserialize(jsonParser, deserializationContext);
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                final ArrayList<Object> list2 = new ArrayList<Object>(2);
                list2.add(deserialize);
                list2.add(deserialize2);
                return list2;
            }
            final ObjectBuffer leaseObjectBuffer = deserializationContext.leaseObjectBuffer();
            Object[] array = leaseObjectBuffer.resetAndStart();
            int n = 0;
            array[n++] = deserialize;
            array[n++] = deserialize2;
            int n2 = n;
            do {
                final Object deserialize3 = this.deserialize(jsonParser, deserializationContext);
                ++n2;
                if (n >= array.length) {
                    array = leaseObjectBuffer.appendCompletedChunk(array);
                    n = 0;
                }
                array[n++] = deserialize3;
            } while (jsonParser.nextToken() != JsonToken.END_ARRAY);
            final ArrayList list3 = new ArrayList<Object>(n2);
            leaseObjectBuffer.completeAndClearBuffer(array, n, (List<Object>)list3);
            return list3;
        }
        
        protected Object[] mapArrayToArray(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
            final ObjectBuffer leaseObjectBuffer = deserializationContext.leaseObjectBuffer();
            Object[] array = leaseObjectBuffer.resetAndStart();
            int n = 0;
            do {
                final Object deserialize = this.deserialize(jsonParser, deserializationContext);
                if (n >= array.length) {
                    array = leaseObjectBuffer.appendCompletedChunk(array);
                    n = 0;
                }
                array[n++] = deserialize;
            } while (jsonParser.nextToken() != JsonToken.END_ARRAY);
            return leaseObjectBuffer.completeAndClearBuffer(array, n);
        }
        
        protected Object mapObject(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
            final String text = jsonParser.getText();
            jsonParser.nextToken();
            final Object deserialize = this.deserialize(jsonParser, deserializationContext);
            final String nextFieldName = jsonParser.nextFieldName();
            if (nextFieldName == null) {
                final LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>(2);
                linkedHashMap.put(text, deserialize);
                return linkedHashMap;
            }
            jsonParser.nextToken();
            final Object deserialize2 = this.deserialize(jsonParser, deserializationContext);
            String s = jsonParser.nextFieldName();
            if (s == null) {
                final LinkedHashMap<String, Object> linkedHashMap2 = new LinkedHashMap<String, Object>(4);
                linkedHashMap2.put(text, deserialize);
                linkedHashMap2.put(nextFieldName, deserialize2);
                return linkedHashMap2;
            }
            final LinkedHashMap<String, Object> linkedHashMap3 = new LinkedHashMap<String, Object>();
            linkedHashMap3.put(text, deserialize);
            linkedHashMap3.put(nextFieldName, deserialize2);
            do {
                jsonParser.nextToken();
                linkedHashMap3.put(s, this.deserialize(jsonParser, deserializationContext));
            } while ((s = jsonParser.nextFieldName()) != null);
            return linkedHashMap3;
        }
        
        static {
            std = new Vanilla();
        }
    }
}
