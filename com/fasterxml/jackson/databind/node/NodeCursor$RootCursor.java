package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;

public final class NodeCursor$RootCursor extends NodeCursor {
   protected JsonNode _node;
   protected boolean _done = false;

   public NodeCursor$RootCursor(JsonNode var1, NodeCursor var2) {
      super(0, var2);
      this._node = var1;
   }

   public void overrideCurrentName(String var1) {
   }

   public JsonToken nextToken() {
      if (!this._done) {
         this._done = true;
         return this._node.asToken();
      } else {
         this._node = null;
         return null;
      }
   }

   public JsonToken nextValue() {
      return this.nextToken();
   }

   public JsonToken endToken() {
      return null;
   }

   public JsonNode currentNode() {
      return this._node;
   }

   public boolean currentHasChildren() {
      return false;
   }

   public JsonStreamContext getParent() {
      return super.getParent();
   }
}
