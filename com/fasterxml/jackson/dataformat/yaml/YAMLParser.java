package com.fasterxml.jackson.dataformat.yaml;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.util.BufferRecycler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.CollectionStartEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.Event$ID;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.NodeEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.resolver.Resolver;

public class YAMLParser extends ParserBase {
   private static final Pattern PATTERN_FLOAT = Pattern.compile("[-+]?([0-9][0-9_]*)?\\.[0-9]*([eE][-+][0-9]+)?");
   protected ObjectCodec _objectCodec;
   protected int _formatFeatures;
   protected final Reader _reader;
   protected final ParserImpl _yamlParser;
   protected final Resolver _yamlResolver = new Resolver();
   protected Event _lastEvent;
   protected String _textValue;
   protected String _cleanedTextValue;
   protected String _currentFieldName;
   protected boolean _currentIsAlias;
   protected String _currentAnchor;

   public YAMLParser(IOContext var1, BufferRecycler var2, int var3, int var4, ObjectCodec var5, Reader var6) {
      super(var1, var3);
      this._objectCodec = var5;
      this._formatFeatures = var4;
      this._reader = var6;
      this._yamlParser = new ParserImpl(new StreamReader(var6));
   }

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public void setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
   }

   public boolean isCurrentAlias() {
      return this._currentIsAlias;
   }

   /** @deprecated */
   @Deprecated
   public String getCurrentAnchor() {
      return this._currentAnchor;
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   protected void _closeInput() throws IOException {
      this._reader.close();
   }

   public int getFormatFeatures() {
      return this._formatFeatures;
   }

   public JsonParser overrideFormatFeatures(int var1, int var2) {
      this._formatFeatures = this._formatFeatures & ~var2 | var1 & var2;
      return this;
   }

   public JsonParser enable(YAMLParser$Feature var1) {
      this._formatFeatures |= var1.getMask();
      return this;
   }

   public JsonParser disable(YAMLParser$Feature var1) {
      this._formatFeatures &= ~var1.getMask();
      return this;
   }

   public JsonParser configure(YAMLParser$Feature var1, boolean var2) {
      if (var2) {
         this.enable(var1);
      } else {
         this.disable(var1);
      }

      return this;
   }

   public boolean isEnabled(YAMLParser$Feature var1) {
      return (this._formatFeatures & var1.getMask()) != 0;
   }

   public JsonLocation getTokenLocation() {
      return this._lastEvent == null ? JsonLocation.NA : this._locationFor(this._lastEvent.getStartMark());
   }

   public JsonLocation getCurrentLocation() {
      return this._lastEvent == null ? JsonLocation.NA : this._locationFor(this._lastEvent.getEndMark());
   }

   protected JsonLocation _locationFor(Mark var1) {
      return var1 == null ? new JsonLocation(this._ioContext.getSourceReference(), -1L, -1, -1) : new JsonLocation(this._ioContext.getSourceReference(), -1L, var1.getLine() + 1, var1.getColumn() + 1);
   }

   public JsonToken nextToken() throws IOException {
      this._currentIsAlias = false;
      this._binaryValue = null;
      this._currentAnchor = null;
      if (this._closed) {
         return null;
      } else {
         while(true) {
            Event var1;
            try {
               var1 = this._yamlParser.getEvent();
            } catch (YAMLException var4) {
               if (var4 instanceof MarkedYAMLException) {
                  throw com.fasterxml.jackson.dataformat.yaml.snakeyaml.error.MarkedYAMLException.from(this, (MarkedYAMLException)var4);
               }

               throw com.fasterxml.jackson.dataformat.yaml.snakeyaml.error.YAMLException.from(this, var4);
            }

            if (var1 == null) {
               return this._currToken = null;
            }

            this._lastEvent = var1;
            if (this._parsingContext.inObject() && this._currToken != JsonToken.FIELD_NAME) {
               if (!var1.is(Event$ID.Scalar)) {
                  if (var1.is(Event$ID.MappingEnd)) {
                     if (!this._parsingContext.inObject()) {
                        this._reportMismatchedEndMarker(125, ']');
                     }

                     this._parsingContext = this._parsingContext.getParent();
                     return this._currToken = JsonToken.END_OBJECT;
                  }

                  this._reportError("Expected a field name (Scalar value in YAML), got this instead: " + var1);
               }

               ScalarEvent var7 = (ScalarEvent)var1;
               String var8 = var7.getValue();
               this._currentFieldName = var8;
               this._parsingContext.setCurrentName(var8);
               this._currentAnchor = var7.getAnchor();
               return this._currToken = JsonToken.FIELD_NAME;
            }

            if (var1.is(Event$ID.Scalar)) {
               JsonToken var6 = this._decodeScalar((ScalarEvent)var1);
               this._currToken = var6;
               return var6;
            }

            Mark var5;
            if (var1.is(Event$ID.MappingStart)) {
               var5 = var1.getStartMark();
               MappingStartEvent var3 = (MappingStartEvent)var1;
               this._currentAnchor = var3.getAnchor();
               this._parsingContext = this._parsingContext.createChildObjectContext(var5.getLine(), var5.getColumn());
               return this._currToken = JsonToken.START_OBJECT;
            }

            if (var1.is(Event$ID.MappingEnd)) {
               this._reportError("Not expecting END_OBJECT but a value");
            }

            if (var1.is(Event$ID.SequenceStart)) {
               var5 = var1.getStartMark();
               this._currentAnchor = ((NodeEvent)var1).getAnchor();
               this._parsingContext = this._parsingContext.createChildArrayContext(var5.getLine(), var5.getColumn());
               return this._currToken = JsonToken.START_ARRAY;
            }

            if (var1.is(Event$ID.SequenceEnd)) {
               if (!this._parsingContext.inArray()) {
                  this._reportMismatchedEndMarker(93, '}');
               }

               this._parsingContext = this._parsingContext.getParent();
               return this._currToken = JsonToken.END_ARRAY;
            }

            if (!var1.is(Event$ID.DocumentEnd) && !var1.is(Event$ID.DocumentStart)) {
               if (var1.is(Event$ID.Alias)) {
                  AliasEvent var2 = (AliasEvent)var1;
                  this._currentIsAlias = true;
                  this._textValue = var2.getAnchor();
                  this._cleanedTextValue = null;
                  return this._currToken = JsonToken.VALUE_STRING;
               }

               if (var1.is(Event$ID.StreamEnd)) {
                  this.close();
                  return this._currToken = null;
               }

               if (var1.is(Event$ID.StreamStart)) {
               }
            }
         }
      }
   }

   protected JsonToken _decodeScalar(ScalarEvent var1) throws IOException {
      String var2 = var1.getValue();
      this._textValue = var2;
      this._cleanedTextValue = null;
      String var3 = var1.getTag();
      int var4 = var2.length();
      if (var3 != null && !var3.equals("!")) {
         if (var3.startsWith("tag:yaml.org,2002:")) {
            var3 = var3.substring("tag:yaml.org,2002:".length());
            if (var3.contains(",")) {
               var3 = var3.split(",")[0];
            }
         }

         if ("binary".equals(var3)) {
            var2 = var2.trim();

            try {
               this._binaryValue = Base64Variants.MIME.decode(var2);
            } catch (IllegalArgumentException var7) {
               this._reportError(var7.getMessage());
            }

            return JsonToken.VALUE_EMBEDDED_OBJECT;
         }

         if ("bool".equals(var3)) {
            Boolean var8 = this._matchYAMLBoolean(var2, var4);
            if (var8 != null) {
               return var8 ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE;
            }
         } else {
            if ("int".equals(var3)) {
               return this._decodeNumberScalar(var2, var4);
            }

            if ("float".equals(var3)) {
               this._numTypesValid = 0;
               return this._cleanYamlFloat(var2);
            }

            if ("null".equals(var3)) {
               return JsonToken.VALUE_NULL;
            }
         }
      } else {
         Tag var5 = this._yamlResolver.resolve(NodeId.scalar, var2, var1.getImplicit().canOmitTagInPlainScalar());
         if (var5 == Tag.STR) {
            return JsonToken.VALUE_STRING;
         }

         if (var5 == Tag.INT) {
            return this._decodeNumberScalar(var2, var4);
         }

         if (var5 == Tag.FLOAT) {
            this._numTypesValid = 0;
            return this._cleanYamlFloat(var2);
         }

         if (var5 != Tag.BOOL) {
            if (var5 == Tag.NULL) {
               return JsonToken.VALUE_NULL;
            }

            return JsonToken.VALUE_STRING;
         }

         Boolean var6 = this._matchYAMLBoolean(var2, var4);
         if (var6 != null) {
            return var6 ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE;
         }
      }

      return JsonToken.VALUE_STRING;
   }

   protected Boolean _matchYAMLBoolean(String var1, int var2) {
      switch(var2) {
      case 1:
         switch(var1.charAt(0)) {
         case 'N':
         case 'n':
            return Boolean.FALSE;
         case 'Y':
         case 'y':
            return Boolean.TRUE;
         default:
            return null;
         }
      case 2:
         if ("no".equalsIgnoreCase(var1)) {
            return Boolean.FALSE;
         }

         if ("on".equalsIgnoreCase(var1)) {
            return Boolean.TRUE;
         }
         break;
      case 3:
         if ("yes".equalsIgnoreCase(var1)) {
            return Boolean.TRUE;
         }

         if ("off".equalsIgnoreCase(var1)) {
            return Boolean.FALSE;
         }
         break;
      case 4:
         if ("true".equalsIgnoreCase(var1)) {
            return Boolean.TRUE;
         }
         break;
      case 5:
         if ("false".equalsIgnoreCase(var1)) {
            return Boolean.FALSE;
         }
      }

      return null;
   }

   protected JsonToken _decodeNumberScalar(String var1, int var2) {
      if ("0".equals(var1)) {
         this._numberNegative = false;
         this._numberInt = 0;
         this._numTypesValid = 1;
         return JsonToken.VALUE_NUMBER_INT;
      } else {
         char var4 = var1.charAt(0);
         int var3;
         if (var4 == '-') {
            this._numberNegative = true;
            if (var2 == 1) {
               return null;
            }

            var3 = 1;
         } else if (var4 == '+') {
            this._numberNegative = false;
            if (var2 == 1) {
               return null;
            }

            var3 = 1;
         } else {
            this._numberNegative = false;
            var3 = 0;
         }

         int var5 = 0;

         do {
            char var6 = var1.charAt(var3);
            if (var6 > '9' || var6 < '0') {
               if (var6 != '_') {
                  if (PATTERN_FLOAT.matcher(var1).matches()) {
                     this._numTypesValid = 0;
                     return this._cleanYamlFloat(this._textValue);
                  }

                  return JsonToken.VALUE_STRING;
               }

               ++var5;
            }

            ++var3;
         } while(var3 != var2);

         this._numTypesValid = 0;
         if (var5 > 0) {
            return this._cleanYamlInt(this._textValue);
         } else {
            this._cleanedTextValue = this._textValue;
            return JsonToken.VALUE_NUMBER_INT;
         }
      }
   }

   protected JsonToken _decodeIntWithUnderscores(String var1, int var2) {
      return JsonToken.VALUE_NUMBER_INT;
   }

   public boolean hasTextCharacters() {
      return false;
   }

   public String getText() throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         return this._textValue;
      } else if (this._currToken == JsonToken.FIELD_NAME) {
         return this._currentFieldName;
      } else if (this._currToken != null) {
         return this._currToken.isScalarValue() ? this._textValue : this._currToken.asString();
      } else {
         return null;
      }
   }

   public String getCurrentName() throws IOException {
      return this._currToken == JsonToken.FIELD_NAME ? this._currentFieldName : super.getCurrentName();
   }

   public char[] getTextCharacters() throws IOException {
      String var1 = this.getText();
      return var1 == null ? null : var1.toCharArray();
   }

   public int getTextLength() throws IOException {
      String var1 = this.getText();
      return var1 == null ? 0 : var1.length();
   }

   public int getTextOffset() throws IOException {
      return 0;
   }

   public int getText(Writer var1) throws IOException {
      String var2 = this.getText();
      if (var2 == null) {
         return 0;
      } else {
         var1.write(var2);
         return var2.length();
      }
   }

   public Object getEmbeddedObject() throws IOException {
      return this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT ? this._binaryValue : null;
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException {
      byte[] var3 = this.getBinaryValue(var1);
      var2.write(var3);
      return var3.length;
   }

   protected void _parseNumericValue(int var1) throws IOException {
      if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
         int var2 = this._textValue.length();
         if (this._numberNegative) {
            --var2;
         }

         if (var2 <= 9) {
            this._numberInt = Integer.parseInt(this._textValue);
            this._numTypesValid = 1;
            return;
         }

         if (var2 <= 18) {
            long var8 = Long.parseLong(this._cleanedTextValue);
            if (var2 == 10) {
               if (this._numberNegative) {
                  if (var8 >= -2147483648L) {
                     this._numberInt = (int)var8;
                     this._numTypesValid = 1;
                     return;
                  }
               } else if (var8 <= 0L) {
                  this._numberInt = (int)var8;
                  this._numTypesValid = 1;
                  return;
               }
            }

            this._numberLong = var8;
            this._numTypesValid = 2;
            return;
         }

         try {
            BigInteger var3 = new BigInteger(this._cleanedTextValue);
            if (var2 == 19 && var3.bitLength() <= 63) {
               this._numberLong = var3.longValue();
               this._numTypesValid = 2;
               return;
            }

            this._numberBigInt = var3;
            this._numTypesValid = 4;
         } catch (NumberFormatException var6) {
            this._wrapError("Malformed numeric value '" + this._textValue + "'", var6);
            break label19;
         }

         return;
      }

      if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
         String var7 = this._cleanedTextValue;

         try {
            if (var1 == 16) {
               this._numberBigDecimal = new BigDecimal(var7);
               this._numTypesValid = 16;
            } else {
               this._numberDouble = Double.parseDouble(var7);
               this._numTypesValid = 8;
            }
         } catch (NumberFormatException var5) {
            this._wrapError("Malformed numeric value '" + this._textValue + "'", var5);
         }

      } else {
         this._reportError("Current token (" + this._currToken + ") not numeric, can not use numeric value accessors");
      }
   }

   protected int _parseIntValue() throws IOException {
      if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
         int var1 = this._cleanedTextValue.length();
         if (this._numberNegative) {
            --var1;
         }

         if (var1 <= 9) {
            this._numTypesValid = 1;
            return this._numberInt = Integer.parseInt(this._cleanedTextValue);
         }
      }

      this._parseNumericValue(1);
      if ((this._numTypesValid & 1) == 0) {
         this.convertNumberToInt();
      }

      return this._numberInt;
   }

   public boolean canReadObjectId() {
      return true;
   }

   public boolean canReadTypeId() {
      return true;
   }

   public String getObjectId() throws IOException, JsonGenerationException {
      return this._currentAnchor;
   }

   public String getTypeId() throws IOException, JsonGenerationException {
      String var1;
      if (this._lastEvent instanceof CollectionStartEvent) {
         var1 = ((CollectionStartEvent)this._lastEvent).getTag();
      } else {
         if (!(this._lastEvent instanceof ScalarEvent)) {
            return null;
         }

         var1 = ((ScalarEvent)this._lastEvent).getTag();
      }

      if (var1 == null) {
         return null;
      } else {
         while(var1.startsWith("!")) {
            var1 = var1.substring(1);
         }

         return var1;
      }
   }

   private JsonToken _cleanYamlInt(String var1) {
      int var2 = var1.length();
      StringBuilder var3 = new StringBuilder(var2);

      for(int var4 = var1.charAt(0) == '+' ? 1 : 0; var4 < var2; ++var4) {
         char var5 = var1.charAt(var4);
         if (var5 != '_') {
            var3.append(var5);
         }
      }

      this._cleanedTextValue = var3.toString();
      return JsonToken.VALUE_NUMBER_INT;
   }

   private JsonToken _cleanYamlFloat(String var1) {
      int var2 = var1.length();
      int var3 = var1.indexOf(95);
      if (var3 >= 0 && var2 != 0) {
         StringBuilder var4 = new StringBuilder(var2);

         for(int var5 = var1.charAt(0) == '+' ? 1 : 0; var5 < var2; ++var5) {
            char var6 = var1.charAt(var5);
            if (var6 != '_') {
               var4.append(var6);
            }
         }

         this._cleanedTextValue = var4.toString();
         return JsonToken.VALUE_NUMBER_FLOAT;
      } else {
         this._cleanedTextValue = var1;
         return JsonToken.VALUE_NUMBER_FLOAT;
      }
   }

   public Object getTypeId() throws IOException {
      return this.getTypeId();
   }

   public Object getObjectId() throws IOException {
      return this.getObjectId();
   }
}
