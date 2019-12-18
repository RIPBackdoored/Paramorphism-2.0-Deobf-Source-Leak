package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;

public final class NodeCursor$ArrayCursor extends NodeCursor {
   protected Iterator _contents;
   protected JsonNode _currentNode;

   public NodeCursor$ArrayCursor(JsonNode var1, NodeCursor var2) {
      super(1, var2);
      this._contents = var1.elements();
   }

   public JsonToken nextToken() {
      if (!this._contents.hasNext()) {
         this._currentNode = null;
         return null;
      } else {
         this._currentNode = (JsonNode)this._contents.next();
         return this._currentNode.asToken();
      }
   }

   public JsonToken nextValue() {
      return this.nextToken();
   }

   public JsonToken endToken() {
      return JsonToken.END_ARRAY;
   }

   public JsonNode currentNode() {
      return this._currentNode;
   }

   public boolean currentHasChildren() {
      return ((ContainerNode)this.currentNode()).size() > 0;
   }

   public JsonStreamContext getParent() {
      return super.getParent();
   }
}
