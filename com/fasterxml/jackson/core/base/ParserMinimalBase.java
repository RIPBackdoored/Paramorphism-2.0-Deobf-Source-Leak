package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class ParserMinimalBase extends JsonParser {
   protected static final int INT_TAB = 9;
   protected static final int INT_LF = 10;
   protected static final int INT_CR = 13;
   protected static final int INT_SPACE = 32;
   protected static final int INT_LBRACKET = 91;
   protected static final int INT_RBRACKET = 93;
   protected static final int INT_LCURLY = 123;
   protected static final int INT_RCURLY = 125;
   protected static final int INT_QUOTE = 34;
   protected static final int INT_APOS = 39;
   protected static final int INT_BACKSLASH = 92;
   protected static final int INT_SLASH = 47;
   protected static final int INT_ASTERISK = 42;
   protected static final int INT_COLON = 58;
   protected static final int INT_COMMA = 44;
   protected static final int INT_HASH = 35;
   protected static final int INT_0 = 48;
   protected static final int INT_9 = 57;
   protected static final int INT_MINUS = 45;
   protected static final int INT_PLUS = 43;
   protected static final int INT_PERIOD = 46;
   protected static final int INT_e = 101;
   protected static final int INT_E = 69;
   protected static final char CHAR_NULL = '\u0000';
   protected static final byte[] NO_BYTES = new byte[0];
   protected static final int[] NO_INTS = new int[0];
   protected static final int NR_UNKNOWN = 0;
   protected static final int NR_INT = 1;
   protected static final int NR_LONG = 2;
   protected static final int NR_BIGINT = 4;
   protected static final int NR_DOUBLE = 8;
   protected static final int NR_BIGDECIMAL = 16;
   protected static final int NR_FLOAT = 32;
   protected static final BigInteger BI_MIN_INT = BigInteger.valueOf(-2147483648L);
   protected static final BigInteger BI_MAX_INT = BigInteger.valueOf(0L);
   protected static final BigInteger BI_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
   protected static final BigInteger BI_MAX_LONG = BigInteger.valueOf(4294967295L);
   protected static final BigDecimal BD_MIN_LONG;
   protected static final BigDecimal BD_MAX_LONG;
   protected static final BigDecimal BD_MIN_INT;
   protected static final BigDecimal BD_MAX_INT;
   protected static final long MIN_INT_L = -2147483648L;
   protected static final long MAX_INT_L = 0L;
   protected static final double MIN_LONG_D = -9.223372036854776E18D;
   protected static final double MAX_LONG_D = 9.223372036854776E18D;
   protected static final double MIN_INT_D = -2.147483648E9D;
   protected static final double MAX_INT_D = 2.147483647E9D;
   protected static final int MAX_ERROR_TOKEN_LENGTH = 256;
   protected JsonToken _currToken;
   protected JsonToken _lastClearedToken;

   protected ParserMinimalBase() {
      super();
   }

   protected ParserMinimalBase(int var1) {
      super(var1);
   }

   public abstract JsonToken nextToken() throws IOException;

   public JsonToken currentToken() {
      return this._currToken;
   }

   public int currentTokenId() {
      JsonToken var1 = this._currToken;
      return var1 == null ? 0 : var1.id();
   }

   public JsonToken getCurrentToken() {
      return this._currToken;
   }

   public int getCurrentTokenId() {
      JsonToken var1 = this._currToken;
      return var1 == null ? 0 : var1.id();
   }

   public boolean hasCurrentToken() {
      return this._currToken != null;
   }

   public boolean hasTokenId(int var1) {
      JsonToken var2 = this._currToken;
      if (var2 == null) {
         return 0 == var1;
      } else {
         return var2.id() == var1;
      }
   }

   public boolean hasToken(JsonToken var1) {
      return this._currToken == var1;
   }

   public boolean isExpectedStartArrayToken() {
      return this._currToken == JsonToken.START_ARRAY;
   }

   public boolean isExpectedStartObjectToken() {
      return this._currToken == JsonToken.START_OBJECT;
   }

   public JsonToken nextValue() throws IOException {
      JsonToken var1 = this.nextToken();
      if (var1 == JsonToken.FIELD_NAME) {
         var1 = this.nextToken();
      }

      return var1;
   }

   public JsonParser skipChildren() throws IOException {
      if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
         return this;
      } else {
         int var1 = 1;

         while(true) {
            JsonToken var2 = this.nextToken();
            if (var2 == null) {
               this._handleEOF();
               return this;
            }

            if (var2.isStructStart()) {
               ++var1;
            } else if (var2.isStructEnd()) {
               --var1;
               if (var1 == 0) {
                  return this;
               }
            } else if (var2 == JsonToken.NOT_AVAILABLE) {
               this._reportError("Not enough content available for `skipChildren()`: non-blocking parser? (%s)", this.getClass().getName());
            }
         }
      }
   }

   protected abstract void _handleEOF() throws JsonParseException;

   public abstract String getCurrentName() throws IOException;

   public abstract void close() throws IOException;

   public abstract boolean isClosed();

   public abstract JsonStreamContext getParsingContext();

   public void clearCurrentToken() {
      if (this._currToken != null) {
         this._lastClearedToken = this._currToken;
         this._currToken = null;
      }

   }

   public JsonToken getLastClearedToken() {
      return this._lastClearedToken;
   }

   public abstract void overrideCurrentName(String var1);

   public abstract String getText() throws IOException;

   public abstract char[] getTextCharacters() throws IOException;

   public abstract boolean hasTextCharacters();

   public abstract int getTextLength() throws IOException;

   public abstract int getTextOffset() throws IOException;

   public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException;

   public boolean getValueAsBoolean(boolean var1) throws IOException {
      JsonToken var2 = this._currToken;
      if (var2 != null) {
         switch(var2.id()) {
         case 6:
            String var3 = this.getText().trim();
            if ("true".equals(var3)) {
               return true;
            }

            if ("false".equals(var3)) {
               return false;
            }

            if (this._hasTextualNull(var3)) {
               return false;
            }
            break;
         case 7:
            return this.getIntValue() != 0;
         case 8:
         default:
            break;
         case 9:
            return true;
         case 10:
         case 11:
            return false;
         case 12:
            Object var4 = this.getEmbeddedObject();
            if (var4 instanceof Boolean) {
               return (Boolean)var4;
            }
         }
      }

      return var1;
   }

   public int getValueAsInt() throws IOException {
      JsonToken var1 = this._currToken;
      return var1 != JsonToken.VALUE_NUMBER_INT && var1 != JsonToken.VALUE_NUMBER_FLOAT ? this.getValueAsInt(0) : this.getIntValue();
   }

   public int getValueAsInt(int var1) throws IOException {
      JsonToken var2 = this._currToken;
      if (var2 != JsonToken.VALUE_NUMBER_INT && var2 != JsonToken.VALUE_NUMBER_FLOAT) {
         if (var2 != null) {
            switch(var2.id()) {
            case 6:
               String var3 = this.getText();
               if (this._hasTextualNull(var3)) {
                  return 0;
               }

               return NumberInput.parseAsInt(var3, var1);
            case 7:
            case 8:
            default:
               break;
            case 9:
               return 1;
            case 10:
               return 0;
            case 11:
               return 0;
            case 12:
               Object var4 = this.getEmbeddedObject();
               if (var4 instanceof Number) {
                  return ((Number)var4).intValue();
               }
            }
         }

         return var1;
      } else {
         return this.getIntValue();
      }
   }

   public long getValueAsLong() throws IOException {
      JsonToken var1 = this._currToken;
      return var1 != JsonToken.VALUE_NUMBER_INT && var1 != JsonToken.VALUE_NUMBER_FLOAT ? this.getValueAsLong(0L) : this.getLongValue();
   }

   public long getValueAsLong(long var1) throws IOException {
      JsonToken var3 = this._currToken;
      if (var3 != JsonToken.VALUE_NUMBER_INT && var3 != JsonToken.VALUE_NUMBER_FLOAT) {
         if (var3 != null) {
            switch(var3.id()) {
            case 6:
               String var4 = this.getText();
               if (this._hasTextualNull(var4)) {
                  return 0L;
               }

               return NumberInput.parseAsLong(var4, var1);
            case 7:
            case 8:
            default:
               break;
            case 9:
               return 1L;
            case 10:
            case 11:
               return 0L;
            case 12:
               Object var5 = this.getEmbeddedObject();
               if (var5 instanceof Number) {
                  return ((Number)var5).longValue();
               }
            }
         }

         return var1;
      } else {
         return this.getLongValue();
      }
   }

   public double getValueAsDouble(double var1) throws IOException {
      JsonToken var3 = this._currToken;
      if (var3 != null) {
         switch(var3.id()) {
         case 6:
            String var4 = this.getText();
            if (this._hasTextualNull(var4)) {
               return 0.0D;
            }

            return NumberInput.parseAsDouble(var4, var1);
         case 7:
         case 8:
            return this.getDoubleValue();
         case 9:
            return 1.0D;
         case 10:
         case 11:
            return 0.0D;
         case 12:
            Object var5 = this.getEmbeddedObject();
            if (var5 instanceof Number) {
               return ((Number)var5).doubleValue();
            }
         }
      }

      return var1;
   }

   public String getValueAsString() throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         return this.getText();
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this.getCurrentName() : this.getValueAsString((String)null);
      }
   }

   public String getValueAsString(String var1) throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         return this.getText();
      } else if (this._currToken == JsonToken.FIELD_NAME) {
         return this.getCurrentName();
      } else {
         return this._currToken != null && this._currToken != JsonToken.VALUE_NULL && this._currToken.isScalarValue() ? this.getText() : var1;
      }
   }

   protected void _decodeBase64(String var1, ByteArrayBuilder var2, Base64Variant var3) throws IOException {
      try {
         var3.decode(var1, var2);
      } catch (IllegalArgumentException var5) {
         this._reportError(var5.getMessage());
      }

   }

   protected boolean _hasTextualNull(String var1) {
      return "null".equals(var1);
   }

   protected void reportUnexpectedNumberChar(int var1, String var2) throws JsonParseException {
      String var3 = String.format("Unexpected character (%s) in numeric value", _getCharDesc(var1));
      if (var2 != null) {
         var3 = var3 + ": " + var2;
      }

      this._reportError(var3);
   }

   protected void reportInvalidNumber(String var1) throws JsonParseException {
      this._reportError("Invalid numeric value: " + var1);
   }

   protected void reportOverflowInt() throws IOException {
      this._reportError(String.format("Numeric value (%s) out of range of int (%d - %s)", this.getText(), Integer.MIN_VALUE, 0));
   }

   protected void reportOverflowLong() throws IOException {
      this._reportError(String.format("Numeric value (%s) out of range of long (%d - %s)", this.getText(), Long.MIN_VALUE, 4294967295L));
   }

   protected void _reportUnexpectedChar(int var1, String var2) throws JsonParseException {
      if (var1 < 0) {
         this._reportInvalidEOF();
      }

      String var3 = String.format("Unexpected character (%s)", _getCharDesc(var1));
      if (var2 != null) {
         var3 = var3 + ": " + var2;
      }

      this._reportError(var3);
   }

   protected void _reportInvalidEOF() throws JsonParseException {
      this._reportInvalidEOF(" in " + this._currToken, this._currToken);
   }

   protected void _reportInvalidEOFInValue(JsonToken var1) throws JsonParseException {
      String var2;
      if (var1 == JsonToken.VALUE_STRING) {
         var2 = " in a String value";
      } else if (var1 != JsonToken.VALUE_NUMBER_INT && var1 != JsonToken.VALUE_NUMBER_FLOAT) {
         var2 = " in a value";
      } else {
         var2 = " in a Number value";
      }

      this._reportInvalidEOF(var2, var1);
   }

   protected void _reportInvalidEOF(String var1, JsonToken var2) throws JsonParseException {
      throw new JsonEOFException(this, var2, "Unexpected end-of-input" + var1);
   }

   /** @deprecated */
   @Deprecated
   protected void _reportInvalidEOFInValue() throws JsonParseException {
      this._reportInvalidEOF(" in a value");
   }

   /** @deprecated */
   @Deprecated
   protected void _reportInvalidEOF(String var1) throws JsonParseException {
      throw new JsonEOFException(this, (JsonToken)null, "Unexpected end-of-input" + var1);
   }

   protected void _reportMissingRootWS(int var1) throws JsonParseException {
      this._reportUnexpectedChar(var1, "Expected space separating root-level values");
   }

   protected void _throwInvalidSpace(int var1) throws JsonParseException {
      char var2 = (char)var1;
      String var3 = "Illegal character (" + _getCharDesc(var2) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens";
      this._reportError(var3);
   }

   protected void _throwUnquotedSpace(int var1, String var2) throws JsonParseException {
      if (!this.isEnabled(JsonParser$Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || var1 > 32) {
         char var3 = (char)var1;
         String var4 = "Illegal unquoted character (" + _getCharDesc(var3) + "): has to be escaped using backslash to be included in " + var2;
         this._reportError(var4);
      }

   }

   protected char _handleUnrecognizedCharacterEscape(char var1) throws JsonProcessingException {
      if (this.isEnabled(JsonParser$Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)) {
         return var1;
      } else if (var1 == '\'' && this.isEnabled(JsonParser$Feature.ALLOW_SINGLE_QUOTES)) {
         return var1;
      } else {
         this._reportError("Unrecognized character escape " + _getCharDesc(var1));
         return var1;
      }
   }

   protected static final String _getCharDesc(int var0) {
      char var1 = (char)var0;
      if (Character.isISOControl(var1)) {
         return "(CTRL-CHAR, code " + var0 + ")";
      } else {
         return var0 > 255 ? "'" + var1 + "' (code " + var0 + " / 0x" + Integer.toHexString(var0) + ")" : "'" + var1 + "' (code " + var0 + ")";
      }
   }

   protected final void _reportError(String var1) throws JsonParseException {
      throw this._constructError(var1);
   }

   protected final void _reportError(String var1, Object var2) throws JsonParseException {
      throw this._constructError(String.format(var1, var2));
   }

   protected final void _reportError(String var1, Object var2, Object var3) throws JsonParseException {
      throw this._constructError(String.format(var1, var2, var3));
   }

   protected final void _wrapError(String var1, Throwable var2) throws JsonParseException {
      throw this._constructError(var1, var2);
   }

   protected final void _throwInternal() {
      VersionUtil.throwInternal();
   }

   protected final JsonParseException _constructError(String var1, Throwable var2) {
      return new JsonParseException(this, var1, var2);
   }

   protected static byte[] _asciiBytes(String var0) {
      byte[] var1 = new byte[var0.length()];
      int var2 = 0;

      for(int var3 = var0.length(); var2 < var3; ++var2) {
         var1[var2] = (byte)var0.charAt(var2);
      }

      return var1;
   }

   protected static String _ascii(byte[] var0) {
      String var10000;
      try {
         var10000 = new String(var0, "US-ASCII");
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }

      return var10000;
   }

   static {
      BD_MIN_LONG = new BigDecimal(BI_MIN_LONG);
      BD_MAX_LONG = new BigDecimal(BI_MAX_LONG);
      BD_MIN_INT = new BigDecimal(BI_MIN_INT);
      BD_MAX_INT = new BigDecimal(BI_MAX_INT);
   }
}
