package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.util.*;

abstract class NodeCursor extends JsonStreamContext
{
    protected final NodeCursor _parent;
    protected String _currentName;
    protected Object _currentValue;
    
    public NodeCursor(final int type, final NodeCursor parent) {
        super();
        this._type = type;
        this._index = -1;
        this._parent = parent;
    }
    
    @Override
    public final NodeCursor getParent() {
        return this._parent;
    }
    
    @Override
    public final String getCurrentName() {
        return this._currentName;
    }
    
    public void overrideCurrentName(final String currentName) {
        this._currentName = currentName;
    }
    
    @Override
    public Object getCurrentValue() {
        return this._currentValue;
    }
    
    @Override
    public void setCurrentValue(final Object currentValue) {
        this._currentValue = currentValue;
    }
    
    public abstract JsonToken nextToken();
    
    public abstract JsonToken nextValue();
    
    public abstract JsonToken endToken();
    
    public abstract JsonNode currentNode();
    
    public abstract boolean currentHasChildren();
    
    public final NodeCursor iterateChildren() {
        final JsonNode currentNode = this.currentNode();
        if (currentNode == null) {
            throw new IllegalStateException("No current node");
        }
        if (currentNode.isArray()) {
            return new ArrayCursor(currentNode, this);
        }
        if (currentNode.isObject()) {
            return new ObjectCursor(currentNode, this);
        }
        throw new IllegalStateException("Current node of type " + currentNode.getClass().getName());
    }
    
    @Override
    public JsonStreamContext getParent() {
        return this.getParent();
    }
    
    protected static final class RootCursor extends NodeCursor
    {
        protected JsonNode _node;
        protected boolean _done;
        
        public RootCursor(final JsonNode node, final NodeCursor nodeCursor) {
            super(0, nodeCursor);
            this._done = false;
            this._node = node;
        }
        
        @Override
        public void overrideCurrentName(final String s) {
        }
        
        @Override
        public JsonToken nextToken() {
            if (!this._done) {
                this._done = true;
                return this._node.asToken();
            }
            this._node = null;
            return null;
        }
        
        @Override
        public JsonToken nextValue() {
            return this.nextToken();
        }
        
        @Override
        public JsonToken endToken() {
            return null;
        }
        
        @Override
        public JsonNode currentNode() {
            return this._node;
        }
        
        @Override
        public boolean currentHasChildren() {
            return false;
        }
        
        @Override
        public JsonStreamContext getParent() {
            return super.getParent();
        }
    }
    
    protected static final class ArrayCursor extends NodeCursor
    {
        protected Iterator<JsonNode> _contents;
        protected JsonNode _currentNode;
        
        public ArrayCursor(final JsonNode jsonNode, final NodeCursor nodeCursor) {
            super(1, nodeCursor);
            this._contents = jsonNode.elements();
        }
        
        @Override
        public JsonToken nextToken() {
            if (!this._contents.hasNext()) {
                this._currentNode = null;
                return null;
            }
            this._currentNode = this._contents.next();
            return this._currentNode.asToken();
        }
        
        @Override
        public JsonToken nextValue() {
            return this.nextToken();
        }
        
        @Override
        public JsonToken endToken() {
            return JsonToken.END_ARRAY;
        }
        
        @Override
        public JsonNode currentNode() {
            return this._currentNode;
        }
        
        @Override
        public boolean currentHasChildren() {
            return ((ContainerNode)this.currentNode()).size() > 0;
        }
        
        @Override
        public JsonStreamContext getParent() {
            return super.getParent();
        }
    }
    
    protected static final class ObjectCursor extends NodeCursor
    {
        protected Iterator<Map.Entry<String, JsonNode>> _contents;
        protected Map.Entry<String, JsonNode> _current;
        protected boolean _needEntry;
        
        public ObjectCursor(final JsonNode jsonNode, final NodeCursor nodeCursor) {
            super(2, nodeCursor);
            this._contents = ((ObjectNode)jsonNode).fields();
            this._needEntry = true;
        }
        
        @Override
        public JsonToken nextToken() {
            if (!this._needEntry) {
                this._needEntry = true;
                return this._current.getValue().asToken();
            }
            if (!this._contents.hasNext()) {
                this._currentName = null;
                this._current = null;
                return null;
            }
            this._needEntry = false;
            this._current = this._contents.next();
            this._currentName = ((this._current == null) ? null : this._current.getKey());
            return JsonToken.FIELD_NAME;
        }
        
        @Override
        public JsonToken nextValue() {
            JsonToken jsonToken = this.nextToken();
            if (jsonToken == JsonToken.FIELD_NAME) {
                jsonToken = this.nextToken();
            }
            return jsonToken;
        }
        
        @Override
        public JsonToken endToken() {
            return JsonToken.END_OBJECT;
        }
        
        @Override
        public JsonNode currentNode() {
            return (this._current == null) ? null : this._current.getValue();
        }
        
        @Override
        public boolean currentHasChildren() {
            return ((ContainerNode)this.currentNode()).size() > 0;
        }
        
        @Override
        public JsonStreamContext getParent() {
            return super.getParent();
        }
    }
}
