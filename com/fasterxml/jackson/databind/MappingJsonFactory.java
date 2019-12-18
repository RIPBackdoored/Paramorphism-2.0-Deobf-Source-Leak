package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import java.io.IOException;

public class MappingJsonFactory extends JsonFactory {
   private static final long serialVersionUID = -1L;

   public MappingJsonFactory() {
      this((ObjectMapper)null);
   }

   public MappingJsonFactory(ObjectMapper var1) {
      super(var1);
      if (var1 == null) {
         this.setCodec(new ObjectMapper(this));
      }

   }

   public MappingJsonFactory(JsonFactory var1, ObjectMapper var2) {
      super(var1, var2);
      if (var2 == null) {
         this.setCodec(new ObjectMapper(this));
      }

   }

   public final ObjectMapper getCodec() {
      return (ObjectMapper)this._objectCodec;
   }

   public JsonFactory copy() {
      this._checkInvalidCopy(MappingJsonFactory.class);
      return new MappingJsonFactory(this, (ObjectMapper)null);
   }

   public String getFormatName() {
      return "JSON";
   }

   public MatchStrength hasFormat(InputAccessor var1) throws IOException {
      return this.getClass() == MappingJsonFactory.class ? this.hasJSONFormat(var1) : null;
   }

   public ObjectCodec getCodec() {
      return this.getCodec();
   }
}
