package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import java.util.Map.Entry;

public final class NodeCursor$ObjectCursor extends NodeCursor {
   protected Iterator _contents;
   protected Entry _current;
   protected boolean _needEntry;

   public NodeCursor$ObjectCursor(JsonNode var1, NodeCursor var2) {
      super(2, var2);
      this._contents = ((ObjectNode)var1).fields();
      this._needEntry = true;
   }

   public JsonToken nextToken() {
      if (this._needEntry) {
         if (!this._contents.hasNext()) {
            this._currentName = null;
            this._current = null;
            return null;
         } else {
            this._needEntry = false;
            this._current = (Entry)this._contents.next();
            this._currentName = this._current == null ? null : (String)this._current.getKey();
            return JsonToken.FIELD_NAME;
         }
      } else {
         this._needEntry = true;
         return ((JsonNode)this._current.getValue()).asToken();
      }
   }

   public JsonToken nextValue() {
      JsonToken var1 = this.nextToken();
      if (var1 == JsonToken.FIELD_NAME) {
         var1 = this.nextToken();
      }

      return var1;
   }

   public JsonToken endToken() {
      return JsonToken.END_OBJECT;
   }

   public JsonNode currentNode() {
      return this._current == null ? null : (JsonNode)this._current.getValue();
   }

   public boolean currentHasChildren() {
      return ((ContainerNode)this.currentNode()).size() > 0;
   }

   public JsonStreamContext getParent() {
      return super.getParent();
   }
}
