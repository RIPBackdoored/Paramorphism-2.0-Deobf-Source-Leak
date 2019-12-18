package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.json.JsonReadContext;

public class TokenBufferReadContext extends JsonStreamContext {
   protected final JsonStreamContext _parent;
   protected final JsonLocation _startLocation;
   protected String _currentName;
   protected Object _currentValue;

   protected TokenBufferReadContext(JsonStreamContext var1, Object var2) {
      super(var1);
      this._parent = var1.getParent();
      this._currentName = var1.getCurrentName();
      this._currentValue = var1.getCurrentValue();
      if (var1 instanceof JsonReadContext) {
         JsonReadContext var3 = (JsonReadContext)var1;
         this._startLocation = var3.getStartLocation(var2);
      } else {
         this._startLocation = JsonLocation.NA;
      }

   }

   protected TokenBufferReadContext(JsonStreamContext var1, JsonLocation var2) {
      super(var1);
      this._parent = var1.getParent();
      this._currentName = var1.getCurrentName();
      this._currentValue = var1.getCurrentValue();
      this._startLocation = var2;
   }

   protected TokenBufferReadContext() {
      super(0, -1);
      this._parent = null;
      this._startLocation = JsonLocation.NA;
   }

   protected TokenBufferReadContext(TokenBufferReadContext var1, int var2, int var3) {
      super(var2, var3);
      this._parent = var1;
      this._startLocation = var1._startLocation;
   }

   public Object getCurrentValue() {
      return this._currentValue;
   }

   public void setCurrentValue(Object var1) {
      this._currentValue = var1;
   }

   public static TokenBufferReadContext createRootContext(JsonStreamContext var0) {
      return var0 == null ? new TokenBufferReadContext() : new TokenBufferReadContext(var0, (JsonLocation)null);
   }

   public TokenBufferReadContext createChildArrayContext() {
      return new TokenBufferReadContext(this, 1, -1);
   }

   public TokenBufferReadContext createChildObjectContext() {
      return new TokenBufferReadContext(this, 2, -1);
   }

   public TokenBufferReadContext parentOrCopy() {
      if (this._parent instanceof TokenBufferReadContext) {
         return (TokenBufferReadContext)this._parent;
      } else {
         return this._parent == null ? new TokenBufferReadContext() : new TokenBufferReadContext(this._parent, this._startLocation);
      }
   }

   public String getCurrentName() {
      return this._currentName;
   }

   public boolean hasCurrentName() {
      return this._currentName != null;
   }

   public JsonStreamContext getParent() {
      return this._parent;
   }

   public void setCurrentName(String var1) throws JsonProcessingException {
      this._currentName = var1;
   }
}
