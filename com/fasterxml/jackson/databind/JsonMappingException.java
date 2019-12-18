package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JsonMappingException extends JsonProcessingException {
   private static final long serialVersionUID = 1L;
   static final int MAX_REFS_TO_LIST = 1000;
   protected LinkedList _path;
   protected transient Closeable _processor;

   /** @deprecated */
   @Deprecated
   public JsonMappingException(String var1) {
      super(var1);
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException(String var1, Throwable var2) {
      super(var1, var2);
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException(String var1, JsonLocation var2) {
      super(var1, var2);
   }

   /** @deprecated */
   @Deprecated
   public JsonMappingException(String var1, JsonLocation var2, Throwable var3) {
      super(var1, var2, var3);
   }

   public JsonMappingException(Closeable var1, String var2) {
      super(var2);
      this._processor = var1;
      if (var1 instanceof JsonParser) {
         this._location = ((JsonParser)var1).getTokenLocation();
      }

   }

   public JsonMappingException(Closeable var1, String var2, Throwable var3) {
      super(var2, var3);
      this._processor = var1;
      if (var1 instanceof JsonParser) {
         this._location = ((JsonParser)var1).getTokenLocation();
      }

   }

   public JsonMappingException(Closeable var1, String var2, JsonLocation var3) {
      super(var2, var3);
      this._processor = var1;
   }

   public static JsonMappingException from(JsonParser var0, String var1) {
      return new JsonMappingException(var0, var1);
   }

   public static JsonMappingException from(JsonParser var0, String var1, Throwable var2) {
      return new JsonMappingException(var0, var1, var2);
   }

   public static JsonMappingException from(JsonGenerator var0, String var1) {
      return new JsonMappingException(var0, var1, (Throwable)null);
   }

   public static JsonMappingException from(JsonGenerator var0, String var1, Throwable var2) {
      return new JsonMappingException(var0, var1, var2);
   }

   public static JsonMappingException from(DeserializationContext var0, String var1) {
      return new JsonMappingException(var0.getParser(), var1);
   }

   public static JsonMappingException from(DeserializationContext var0, String var1, Throwable var2) {
      return new JsonMappingException(var0.getParser(), var1, var2);
   }

   public static JsonMappingException from(SerializerProvider var0, String var1) {
      return new JsonMappingException(var0.getGenerator(), var1);
   }

   public static JsonMappingException from(SerializerProvider var0, String var1, Throwable var2) {
      return new JsonMappingException(var0.getGenerator(), var1, var2);
   }

   public static JsonMappingException fromUnexpectedIOE(IOException var0) {
      return new JsonMappingException((Closeable)null, String.format("Unexpected IOException (of type %s): %s", var0.getClass().getName(), var0.getMessage()));
   }

   public static JsonMappingException wrapWithPath(Throwable var0, Object var1, String var2) {
      return wrapWithPath(var0, new JsonMappingException$Reference(var1, var2));
   }

   public static JsonMappingException wrapWithPath(Throwable var0, Object var1, int var2) {
      return wrapWithPath(var0, new JsonMappingException$Reference(var1, var2));
   }

   public static JsonMappingException wrapWithPath(Throwable var0, JsonMappingException$Reference var1) {
      JsonMappingException var2;
      if (var0 instanceof JsonMappingException) {
         var2 = (JsonMappingException)var0;
      } else {
         String var3 = var0.getMessage();
         if (var3 == null || var3.length() == 0) {
            var3 = "(was " + var0.getClass().getName() + ")";
         }

         Closeable var4 = null;
         if (var0 instanceof JsonProcessingException) {
            Object var5 = ((JsonProcessingException)var0).getProcessor();
            if (var5 instanceof Closeable) {
               var4 = (Closeable)var5;
            }
         }

         var2 = new JsonMappingException(var4, var3, var0);
      }

      var2.prependPath(var1);
      return var2;
   }

   public List getPath() {
      return this._path == null ? Collections.emptyList() : Collections.unmodifiableList(this._path);
   }

   public String getPathReference() {
      return this.getPathReference(new StringBuilder()).toString();
   }

   public StringBuilder getPathReference(StringBuilder var1) {
      this._appendPathDesc(var1);
      return var1;
   }

   public void prependPath(Object var1, String var2) {
      JsonMappingException$Reference var3 = new JsonMappingException$Reference(var1, var2);
      this.prependPath(var3);
   }

   public void prependPath(Object var1, int var2) {
      JsonMappingException$Reference var3 = new JsonMappingException$Reference(var1, var2);
      this.prependPath(var3);
   }

   public void prependPath(JsonMappingException$Reference var1) {
      if (this._path == null) {
         this._path = new LinkedList();
      }

      if (this._path.size() < 1000) {
         this._path.addFirst(var1);
      }

   }

   @JsonIgnore
   public Object getProcessor() {
      return this._processor;
   }

   public String getLocalizedMessage() {
      return this._buildMessage();
   }

   public String getMessage() {
      return this._buildMessage();
   }

   protected String _buildMessage() {
      String var1 = super.getMessage();
      if (this._path == null) {
         return var1;
      } else {
         StringBuilder var2 = var1 == null ? new StringBuilder() : new StringBuilder(var1);
         var2.append(" (through reference chain: ");
         var2 = this.getPathReference(var2);
         var2.append(')');
         return var2.toString();
      }
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.getMessage();
   }

   protected void _appendPathDesc(StringBuilder var1) {
      if (this._path != null) {
         Iterator var2 = this._path.iterator();

         while(var2.hasNext()) {
            var1.append(((JsonMappingException$Reference)var2.next()).toString());
            if (var2.hasNext()) {
               var1.append("->");
            }
         }

      }
   }
}
