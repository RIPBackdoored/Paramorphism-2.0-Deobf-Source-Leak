package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.util.RequestPayload;

public class JsonParseException extends JsonProcessingException {
   private static final long serialVersionUID = 2L;
   protected transient JsonParser _processor;
   protected RequestPayload _requestPayload;

   /** @deprecated */
   @Deprecated
   public JsonParseException(String var1, JsonLocation var2) {
      super(var1, var2);
   }

   /** @deprecated */
   @Deprecated
   public JsonParseException(String var1, JsonLocation var2, Throwable var3) {
      super(var1, var2, var3);
   }

   public JsonParseException(JsonParser var1, String var2) {
      super(var2, var1 == null ? null : var1.getCurrentLocation());
      this._processor = var1;
   }

   public JsonParseException(JsonParser var1, String var2, Throwable var3) {
      super(var2, var1 == null ? null : var1.getCurrentLocation(), var3);
      this._processor = var1;
   }

   public JsonParseException(JsonParser var1, String var2, JsonLocation var3) {
      super(var2, var3);
      this._processor = var1;
   }

   public JsonParseException(JsonParser var1, String var2, JsonLocation var3, Throwable var4) {
      super(var2, var3, var4);
      this._processor = var1;
   }

   public JsonParseException withParser(JsonParser var1) {
      this._processor = var1;
      return this;
   }

   public JsonParseException withRequestPayload(RequestPayload var1) {
      this._requestPayload = var1;
      return this;
   }

   public JsonParser getProcessor() {
      return this._processor;
   }

   public RequestPayload getRequestPayload() {
      return this._requestPayload;
   }

   public String getRequestPayloadAsString() {
      return this._requestPayload != null ? this._requestPayload.toString() : null;
   }

   public String getMessage() {
      String var1 = super.getMessage();
      if (this._requestPayload != null) {
         var1 = var1 + "\nRequest payload : " + this._requestPayload.toString();
      }

      return var1;
   }

   public Object getProcessor() {
      return this.getProcessor();
   }
}
