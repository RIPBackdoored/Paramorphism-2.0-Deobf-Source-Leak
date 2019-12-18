package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import java.util.HashSet;

public class DupDetector {
   protected final Object _source;
   protected String _firstName;
   protected String _secondName;
   protected HashSet _seen;

   private DupDetector(Object var1) {
      super();
      this._source = var1;
   }

   public static DupDetector rootDetector(JsonParser var0) {
      return new DupDetector(var0);
   }

   public static DupDetector rootDetector(JsonGenerator var0) {
      return new DupDetector(var0);
   }

   public DupDetector child() {
      return new DupDetector(this._source);
   }

   public void reset() {
      this._firstName = null;
      this._secondName = null;
      this._seen = null;
   }

   public JsonLocation findLocation() {
      return this._source instanceof JsonParser ? ((JsonParser)this._source).getCurrentLocation() : null;
   }

   public Object getSource() {
      return this._source;
   }

   public boolean isDup(String var1) throws JsonParseException {
      if (this._firstName == null) {
         this._firstName = var1;
         return false;
      } else if (var1.equals(this._firstName)) {
         return true;
      } else if (this._secondName == null) {
         this._secondName = var1;
         return false;
      } else if (var1.equals(this._secondName)) {
         return true;
      } else {
         if (this._seen == null) {
            this._seen = new HashSet(16);
            this._seen.add(this._firstName);
            this._seen.add(this._secondName);
         }

         return !this._seen.add(var1);
      }
   }
}
