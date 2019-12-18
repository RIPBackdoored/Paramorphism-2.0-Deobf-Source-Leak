package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public abstract class DOMDeserializer extends FromStringDeserializer {
   private static final long serialVersionUID = 1L;
   private static final DocumentBuilderFactory DEFAULT_PARSER_FACTORY;

   protected DOMDeserializer(Class var1) {
      super(var1);
   }

   public abstract Object _deserialize(String var1, DeserializationContext var2);

   protected final Document parse(String var1) throws IllegalArgumentException {
      Document var10000;
      try {
         var10000 = this.documentBuilder().parse(new InputSource(new StringReader(var1)));
      } catch (Exception var3) {
         throw new IllegalArgumentException("Failed to parse JSON String as XML: " + var3.getMessage(), var3);
      }

      return var10000;
   }

   protected DocumentBuilder documentBuilder() throws ParserConfigurationException {
      return DEFAULT_PARSER_FACTORY.newDocumentBuilder();
   }

   static {
      DocumentBuilderFactory var0 = DocumentBuilderFactory.newInstance();
      var0.setNamespaceAware(true);
      var0.setExpandEntityReferences(false);

      try {
         var0.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
      } catch (ParserConfigurationException var2) {
      } catch (Error var3) {
      }

      DEFAULT_PARSER_FACTORY = var0;
   }
}
