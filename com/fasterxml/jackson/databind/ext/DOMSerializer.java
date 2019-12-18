package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class DOMSerializer extends StdSerializer {
   protected final DOMImplementationLS _domImpl;

   public DOMSerializer() {
      super(Node.class);

      DOMImplementationRegistry var1;
      try {
         var1 = DOMImplementationRegistry.newInstance();
      } catch (Exception var3) {
         throw new IllegalStateException("Could not instantiate DOMImplementationRegistry: " + var3.getMessage(), var3);
      }

      this._domImpl = (DOMImplementationLS)var1.getDOMImplementation("LS");
   }

   public void serialize(Node var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException {
      if (this._domImpl == null) {
         throw new IllegalStateException("Could not find DOM LS");
      } else {
         LSSerializer var4 = this._domImpl.createLSSerializer();
         var2.writeString(var4.writeToString(var1));
      }
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode("string", true);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      if (var1 != null) {
         var1.expectAnyFormat(var2);
      }

   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Node)var1, var2, var3);
   }
}
