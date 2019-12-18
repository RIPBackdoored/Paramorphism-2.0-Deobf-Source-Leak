package com.fasterxml.jackson.core;

import java.io.IOException;

public class JsonProcessingException extends IOException {
   static final long serialVersionUID = 123L;
   protected JsonLocation _location;

   protected JsonProcessingException(String var1, JsonLocation var2, Throwable var3) {
      super(var1);
      if (var3 != null) {
         this.initCause(var3);
      }

      this._location = var2;
   }

   protected JsonProcessingException(String var1) {
      super(var1);
   }

   protected JsonProcessingException(String var1, JsonLocation var2) {
      this(var1, var2, (Throwable)null);
   }

   protected JsonProcessingException(String var1, Throwable var2) {
      this(var1, (JsonLocation)null, var2);
   }

   protected JsonProcessingException(Throwable var1) {
      this((String)null, (JsonLocation)null, var1);
   }

   public JsonLocation getLocation() {
      return this._location;
   }

   public void clearLocation() {
      this._location = null;
   }

   public String getOriginalMessage() {
      return super.getMessage();
   }

   public Object getProcessor() {
      return null;
   }

   protected String getMessageSuffix() {
      return null;
   }

   public String getMessage() {
      String var1 = super.getMessage();
      if (var1 == null) {
         var1 = "N/A";
      }

      JsonLocation var2 = this.getLocation();
      String var3 = this.getMessageSuffix();
      if (var2 != null || var3 != null) {
         StringBuilder var4 = new StringBuilder(100);
         var4.append(var1);
         if (var3 != null) {
            var4.append(var3);
         }

         if (var2 != null) {
            var4.append('\n');
            var4.append(" at ");
            var4.append(var2.toString());
         }

         var1 = var4.toString();
      }

      return var1;
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.getMessage();
   }
}
