package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;

public final class JsonReadContext extends JsonStreamContext {
   protected final JsonReadContext _parent;
   protected DupDetector _dups;
   protected JsonReadContext _child;
   protected String _currentName;
   protected Object _currentValue;
   protected int _lineNr;
   protected int _columnNr;

   public JsonReadContext(JsonReadContext var1, DupDetector var2, int var3, int var4, int var5) {
      super();
      this._parent = var1;
      this._dups = var2;
      this._type = var3;
      this._lineNr = var4;
      this._columnNr = var5;
      this._index = -1;
   }

   protected void reset(int var1, int var2, int var3) {
      this._type = var1;
      this._index = -1;
      this._lineNr = var2;
      this._columnNr = var3;
      this._currentName = null;
      this._currentValue = null;
      if (this._dups != null) {
         this._dups.reset();
      }

   }

   public JsonReadContext withDupDetector(DupDetector var1) {
      this._dups = var1;
      return this;
   }

   public Object getCurrentValue() {
      return this._currentValue;
   }

   public void setCurrentValue(Object var1) {
      this._currentValue = var1;
   }

   public static JsonReadContext createRootContext(int var0, int var1, DupDetector var2) {
      return new JsonReadContext((JsonReadContext)null, var2, 0, var0, var1);
   }

   public static JsonReadContext createRootContext(DupDetector var0) {
      return new JsonReadContext((JsonReadContext)null, var0, 0, 1, 0);
   }

   public JsonReadContext createChildArrayContext(int var1, int var2) {
      JsonReadContext var3 = this._child;
      if (var3 == null) {
         this._child = var3 = new JsonReadContext(this, this._dups == null ? null : this._dups.child(), 1, var1, var2);
      } else {
         var3.reset(1, var1, var2);
      }

      return var3;
   }

   public JsonReadContext createChildObjectContext(int var1, int var2) {
      JsonReadContext var3 = this._child;
      if (var3 == null) {
         this._child = var3 = new JsonReadContext(this, this._dups == null ? null : this._dups.child(), 2, var1, var2);
         return var3;
      } else {
         var3.reset(2, var1, var2);
         return var3;
      }
   }

   public String getCurrentName() {
      return this._currentName;
   }

   public boolean hasCurrentName() {
      return this._currentName != null;
   }

   public JsonReadContext getParent() {
      return this._parent;
   }

   public JsonLocation getStartLocation(Object var1) {
      long var2 = -1L;
      return new JsonLocation(var1, var2, this._lineNr, this._columnNr);
   }

   public JsonReadContext clearAndGetParent() {
      this._currentValue = null;
      return this._parent;
   }

   public DupDetector getDupDetector() {
      return this._dups;
   }

   public boolean expectComma() {
      int var1 = ++this._index;
      return this._type != 0 && var1 > 0;
   }

   public void setCurrentName(String var1) throws JsonProcessingException {
      this._currentName = var1;
      if (this._dups != null) {
         this._checkDup(this._dups, var1);
      }

   }

   private void _checkDup(DupDetector var1, String var2) throws JsonProcessingException {
      if (var1.isDup(var2)) {
         Object var3 = var1.getSource();
         throw new JsonParseException(var3 instanceof JsonParser ? (JsonParser)var3 : null, "Duplicate field '" + var2 + "'");
      }
   }

   public JsonStreamContext getParent() {
      return this.getParent();
   }
}
