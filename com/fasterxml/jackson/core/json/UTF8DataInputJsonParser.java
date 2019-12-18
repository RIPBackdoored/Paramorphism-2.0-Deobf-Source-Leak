package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser$Feature;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;

public class UTF8DataInputJsonParser extends ParserBase {
   static final byte BYTE_LF = 10;
   private static final int[] _icUTF8 = CharTypes.getInputCodeUtf8();
   protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
   protected ObjectCodec _objectCodec;
   protected final ByteQuadsCanonicalizer _symbols;
   protected int[] _quadBuffer = new int[16];
   protected boolean _tokenIncomplete;
   private int _quad1;
   protected DataInput _inputData;
   protected int _nextByte = -1;

   public UTF8DataInputJsonParser(IOContext var1, int var2, DataInput var3, ObjectCodec var4, ByteQuadsCanonicalizer var5, int var6) {
      super(var1, var2);
      this._objectCodec = var4;
      this._symbols = var5;
      this._inputData = var3;
      this._nextByte = var6;
   }

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public void setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
   }

   public int releaseBuffered(OutputStream var1) throws IOException {
      return 0;
   }

   public Object getInputSource() {
      return this._inputData;
   }

   protected void _closeInput() throws IOException {
   }

   protected void _releaseBuffers() throws IOException {
      super._releaseBuffers();
      this._symbols.release();
   }

   public String getText() throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
         } else {
            return this._textBuffer.contentsAsString();
         }
      } else {
         return this._getText2(this._currToken);
      }
   }

   public int getText(Writer var1) throws IOException {
      JsonToken var2 = this._currToken;
      if (var2 == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.contentsToWriter(var1);
      } else if (var2 == JsonToken.FIELD_NAME) {
         String var4 = this._parsingContext.getCurrentName();
         var1.write(var4);
         return var4.length();
      } else if (var2 != null) {
         if (var2.isNumeric()) {
            return this._textBuffer.contentsToWriter(var1);
         } else {
            char[] var3 = var2.asCharArray();
            var1.write(var3);
            return var3.length;
         }
      } else {
         return 0;
      }
   }

   public String getValueAsString() throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
         } else {
            return this._textBuffer.contentsAsString();
         }
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this.getCurrentName() : super.getValueAsString((String)null);
      }
   }

   public String getValueAsString(String var1) throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
         } else {
            return this._textBuffer.contentsAsString();
         }
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this.getCurrentName() : super.getValueAsString(var1);
      }
   }

   public int getValueAsInt() throws IOException {
      JsonToken var1 = this._currToken;
      if (var1 != JsonToken.VALUE_NUMBER_INT && var1 != JsonToken.VALUE_NUMBER_FLOAT) {
         return super.getValueAsInt(0);
      } else {
         if ((this._numTypesValid & 1) == 0) {
            if (this._numTypesValid == 0) {
               return this._parseIntValue();
            }

            if ((this._numTypesValid & 1) == 0) {
               this.convertNumberToInt();
            }
         }

         return this._numberInt;
      }
   }

   public int getValueAsInt(int var1) throws IOException {
      JsonToken var2 = this._currToken;
      if (var2 != JsonToken.VALUE_NUMBER_INT && var2 != JsonToken.VALUE_NUMBER_FLOAT) {
         return super.getValueAsInt(var1);
      } else {
         if ((this._numTypesValid & 1) == 0) {
            if (this._numTypesValid == 0) {
               return this._parseIntValue();
            }

            if ((this._numTypesValid & 1) == 0) {
               this.convertNumberToInt();
            }
         }

         return this._numberInt;
      }
   }

   protected final String _getText2(JsonToken var1) {
      if (var1 == null) {
         return null;
      } else {
         switch(var1.id()) {
         case 5:
            return this._parsingContext.getCurrentName();
         case 6:
         case 7:
         case 8:
            return this._textBuffer.contentsAsString();
         default:
            return var1.asString();
         }
      }
   }

   public char[] getTextCharacters() throws IOException {
      if (this._currToken != null) {
         switch(this._currToken.id()) {
         case 5:
            if (!this._nameCopied) {
               String var1 = this._parsingContext.getCurrentName();
               int var2 = var1.length();
               if (this._nameCopyBuffer == null) {
                  this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(var2);
               } else if (this._nameCopyBuffer.length < var2) {
                  this._nameCopyBuffer = new char[var2];
               }

               var1.getChars(0, var2, this._nameCopyBuffer, 0);
               this._nameCopied = true;
            }

            return this._nameCopyBuffer;
         case 6:
            if (this._tokenIncomplete) {
               this._tokenIncomplete = false;
               this._finishString();
            }
         case 7:
         case 8:
            return this._textBuffer.getTextBuffer();
         default:
            return this._currToken.asCharArray();
         }
      } else {
         return null;
      }
   }

   public int getTextLength() throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.size();
      } else if (this._currToken == JsonToken.FIELD_NAME) {
         return this._parsingContext.getCurrentName().length();
      } else if (this._currToken != null) {
         return this._currToken.isNumeric() ? this._textBuffer.size() : this._currToken.asCharArray().length;
      } else {
         return 0;
      }
   }

   public int getTextOffset() throws IOException {
      if (this._currToken != null) {
         switch(this._currToken.id()) {
         case 5:
            return 0;
         case 6:
            if (this._tokenIncomplete) {
               this._tokenIncomplete = false;
               this._finishString();
            }
         case 7:
         case 8:
            return this._textBuffer.getTextOffset();
         }
      }

      return 0;
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException {
      if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
         this._reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
      }

      if (this._tokenIncomplete) {
         try {
            this._binaryValue = this._decodeBase64(var1);
         } catch (IllegalArgumentException var3) {
            throw this._constructError("Failed to decode VALUE_STRING as base64 (" + var1 + "): " + var3.getMessage());
         }

         this._tokenIncomplete = false;
      } else if (this._binaryValue == null) {
         ByteArrayBuilder var2 = this._getByteArrayBuilder();
         this._decodeBase64(this.getText(), var2, var1);
         this._binaryValue = var2.toByteArray();
      }

      return this._binaryValue;
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException {
      byte[] var3;
      if (this._tokenIncomplete && this._currToken == JsonToken.VALUE_STRING) {
         var3 = this._ioContext.allocBase64Buffer();
         boolean var7 = false;

         int var4;
         try {
            var7 = true;
            var4 = this._readBinary(var1, var2, var3);
            var7 = false;
         } finally {
            if (var7) {
               this._ioContext.releaseBase64Buffer(var3);
            }
         }

         this._ioContext.releaseBase64Buffer(var3);
         return var4;
      } else {
         var3 = this.getBinaryValue(var1);
         var2.write(var3);
         return var3.length;
      }
   }

   protected int _readBinary(Base64Variant var1, OutputStream var2, byte[] var3) throws IOException {
      int var4 = 0;
      int var5 = var3.length - 3;
      int var6 = 0;

      while(true) {
         int var7;
         do {
            var7 = this._inputData.readUnsignedByte();
         } while(var7 <= 32);

         int var8 = var1.decodeBase64Char(var7);
         if (var8 < 0) {
            if (var7 == 34) {
               break;
            }

            var8 = this._decodeBase64Escape(var1, var7, 0);
            if (var8 < 0) {
               continue;
            }
         }

         if (var4 > var5) {
            var6 += var4;
            var2.write(var3, 0, var4);
            var4 = 0;
         }

         int var9 = var8;
         var7 = this._inputData.readUnsignedByte();
         var8 = var1.decodeBase64Char(var7);
         if (var8 < 0) {
            var8 = this._decodeBase64Escape(var1, var7, 1);
         }

         var9 = var9 << 6 | var8;
         var7 = this._inputData.readUnsignedByte();
         var8 = var1.decodeBase64Char(var7);
         if (var8 < 0) {
            if (var8 != -2) {
               if (var7 == 34 && !var1.usesPadding()) {
                  var9 >>= 4;
                  var3[var4++] = (byte)var9;
                  break;
               }

               var8 = this._decodeBase64Escape(var1, var7, 2);
            }

            if (var8 == -2) {
               var7 = this._inputData.readUnsignedByte();
               if (!var1.usesPaddingChar(var7)) {
                  throw this.reportInvalidBase64Char(var1, var7, 3, "expected padding character '" + var1.getPaddingChar() + "'");
               }

               var9 >>= 4;
               var3[var4++] = (byte)var9;
               continue;
            }
         }

         var9 = var9 << 6 | var8;
         var7 = this._inputData.readUnsignedByte();
         var8 = var1.decodeBase64Char(var7);
         if (var8 < 0) {
            if (var8 != -2) {
               if (var7 == 34 && !var1.usesPadding()) {
                  var9 >>= 2;
                  var3[var4++] = (byte)(var9 >> 8);
                  var3[var4++] = (byte)var9;
                  break;
               }

               var8 = this._decodeBase64Escape(var1, var7, 3);
            }

            if (var8 == -2) {
               var9 >>= 2;
               var3[var4++] = (byte)(var9 >> 8);
               var3[var4++] = (byte)var9;
               continue;
            }
         }

         var9 = var9 << 6 | var8;
         var3[var4++] = (byte)(var9 >> 16);
         var3[var4++] = (byte)(var9 >> 8);
         var3[var4++] = (byte)var9;
      }

      this._tokenIncomplete = false;
      if (var4 > 0) {
         var6 += var4;
         var2.write(var3, 0, var4);
      }

      return var6;
   }

   public JsonToken nextToken() throws IOException {
      if (this._closed) {
         return null;
      } else if (this._currToken == JsonToken.FIELD_NAME) {
         return this._nextAfterName();
      } else {
         this._numTypesValid = 0;
         if (this._tokenIncomplete) {
            this._skipString();
         }

         int var1 = this._skipWSOrEnd();
         if (var1 < 0) {
            this.close();
            return this._currToken = null;
         } else {
            this._binaryValue = null;
            this._tokenInputRow = this._currInputRow;
            if (var1 != 93 && var1 != 125) {
               if (this._parsingContext.expectComma()) {
                  if (var1 != 44) {
                     this._reportUnexpectedChar(var1, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
                  }

                  var1 = this._skipWS();
                  if (JsonParser$Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) && (var1 == 93 || var1 == 125)) {
                     this._closeScope(var1);
                     return this._currToken;
                  }
               }

               if (!this._parsingContext.inObject()) {
                  return this._nextTokenNotInObject(var1);
               } else {
                  String var2 = this._parseName(var1);
                  this._parsingContext.setCurrentName(var2);
                  this._currToken = JsonToken.FIELD_NAME;
                  var1 = this._skipColon();
                  if (var1 == 34) {
                     this._tokenIncomplete = true;
                     this._nextToken = JsonToken.VALUE_STRING;
                     return this._currToken;
                  } else {
                     JsonToken var3;
                     switch(var1) {
                     case 45:
                        var3 = this._parseNegNumber();
                        break;
                     case 48:
                     case 49:
                     case 50:
                     case 51:
                     case 52:
                     case 53:
                     case 54:
                     case 55:
                     case 56:
                     case 57:
                        var3 = this._parsePosNumber(var1);
                        break;
                     case 91:
                        var3 = JsonToken.START_ARRAY;
                        break;
                     case 102:
                        this._matchToken("false", 1);
                        var3 = JsonToken.VALUE_FALSE;
                        break;
                     case 110:
                        this._matchToken("null", 1);
                        var3 = JsonToken.VALUE_NULL;
                        break;
                     case 116:
                        this._matchToken("true", 1);
                        var3 = JsonToken.VALUE_TRUE;
                        break;
                     case 123:
                        var3 = JsonToken.START_OBJECT;
                        break;
                     default:
                        var3 = this._handleUnexpectedValue(var1);
                     }

                     this._nextToken = var3;
                     return this._currToken;
                  }
               }
            } else {
               this._closeScope(var1);
               return this._currToken;
            }
         }
      }
   }

   private final JsonToken _nextTokenNotInObject(int var1) throws IOException {
      if (var1 == 34) {
         this._tokenIncomplete = true;
         return this._currToken = JsonToken.VALUE_STRING;
      } else {
         switch(var1) {
         case 45:
            return this._currToken = this._parseNegNumber();
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
            return this._currToken = this._parsePosNumber(var1);
         case 91:
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            return this._currToken = JsonToken.START_ARRAY;
         case 102:
            this._matchToken("false", 1);
            return this._currToken = JsonToken.VALUE_FALSE;
         case 110:
            this._matchToken("null", 1);
            return this._currToken = JsonToken.VALUE_NULL;
         case 116:
            this._matchToken("true", 1);
            return this._currToken = JsonToken.VALUE_TRUE;
         case 123:
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return this._currToken = JsonToken.START_OBJECT;
         default:
            return this._currToken = this._handleUnexpectedValue(var1);
         }
      }
   }

   private final JsonToken _nextAfterName() {
      this._nameCopied = false;
      JsonToken var1 = this._nextToken;
      this._nextToken = null;
      if (var1 == JsonToken.START_ARRAY) {
         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
      } else if (var1 == JsonToken.START_OBJECT) {
         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
      }

      return this._currToken = var1;
   }

   public void finishToken() throws IOException {
      if (this._tokenIncomplete) {
         this._tokenIncomplete = false;
         this._finishString();
      }

   }

   public String nextFieldName() throws IOException {
      this._numTypesValid = 0;
      if (this._currToken == JsonToken.FIELD_NAME) {
         this._nextAfterName();
         return null;
      } else {
         if (this._tokenIncomplete) {
            this._skipString();
         }

         int var1 = this._skipWS();
         this._binaryValue = null;
         this._tokenInputRow = this._currInputRow;
         if (var1 != 93 && var1 != 125) {
            if (this._parsingContext.expectComma()) {
               if (var1 != 44) {
                  this._reportUnexpectedChar(var1, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
               }

               var1 = this._skipWS();
               if (JsonParser$Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) && (var1 == 93 || var1 == 125)) {
                  this._closeScope(var1);
                  return null;
               }
            }

            if (!this._parsingContext.inObject()) {
               this._nextTokenNotInObject(var1);
               return null;
            } else {
               String var2 = this._parseName(var1);
               this._parsingContext.setCurrentName(var2);
               this._currToken = JsonToken.FIELD_NAME;
               var1 = this._skipColon();
               if (var1 == 34) {
                  this._tokenIncomplete = true;
                  this._nextToken = JsonToken.VALUE_STRING;
                  return var2;
               } else {
                  JsonToken var3;
                  switch(var1) {
                  case 45:
                     var3 = this._parseNegNumber();
                     break;
                  case 48:
                  case 49:
                  case 50:
                  case 51:
                  case 52:
                  case 53:
                  case 54:
                  case 55:
                  case 56:
                  case 57:
                     var3 = this._parsePosNumber(var1);
                     break;
                  case 91:
                     var3 = JsonToken.START_ARRAY;
                     break;
                  case 102:
                     this._matchToken("false", 1);
                     var3 = JsonToken.VALUE_FALSE;
                     break;
                  case 110:
                     this._matchToken("null", 1);
                     var3 = JsonToken.VALUE_NULL;
                     break;
                  case 116:
                     this._matchToken("true", 1);
                     var3 = JsonToken.VALUE_TRUE;
                     break;
                  case 123:
                     var3 = JsonToken.START_OBJECT;
                     break;
                  default:
                     var3 = this._handleUnexpectedValue(var1);
                  }

                  this._nextToken = var3;
                  return var2;
               }
            }
         } else {
            this._closeScope(var1);
            return null;
         }
      }
   }

   public String nextTextValue() throws IOException {
      if (this._currToken == JsonToken.FIELD_NAME) {
         this._nameCopied = false;
         JsonToken var1 = this._nextToken;
         this._nextToken = null;
         this._currToken = var1;
         if (var1 == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
               this._tokenIncomplete = false;
               return this._finishAndReturnString();
            } else {
               return this._textBuffer.contentsAsString();
            }
         } else {
            if (var1 == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (var1 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return null;
         }
      } else {
         return this.nextToken() == JsonToken.VALUE_STRING ? this.getText() : null;
      }
   }

   public int nextIntValue(int var1) throws IOException {
      if (this._currToken == JsonToken.FIELD_NAME) {
         this._nameCopied = false;
         JsonToken var2 = this._nextToken;
         this._nextToken = null;
         this._currToken = var2;
         if (var2 == JsonToken.VALUE_NUMBER_INT) {
            return this.getIntValue();
         } else {
            if (var2 == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (var2 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return var1;
         }
      } else {
         return this.nextToken() == JsonToken.VALUE_NUMBER_INT ? this.getIntValue() : var1;
      }
   }

   public long nextLongValue(long var1) throws IOException {
      if (this._currToken == JsonToken.FIELD_NAME) {
         this._nameCopied = false;
         JsonToken var3 = this._nextToken;
         this._nextToken = null;
         this._currToken = var3;
         if (var3 == JsonToken.VALUE_NUMBER_INT) {
            return this.getLongValue();
         } else {
            if (var3 == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (var3 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return var1;
         }
      } else {
         return this.nextToken() == JsonToken.VALUE_NUMBER_INT ? this.getLongValue() : var1;
      }
   }

   public Boolean nextBooleanValue() throws IOException {
      JsonToken var1;
      if (this._currToken == JsonToken.FIELD_NAME) {
         this._nameCopied = false;
         var1 = this._nextToken;
         this._nextToken = null;
         this._currToken = var1;
         if (var1 == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
         } else if (var1 == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
         } else {
            if (var1 == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (var1 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return null;
         }
      } else {
         var1 = this.nextToken();
         if (var1 == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
         } else {
            return var1 == JsonToken.VALUE_FALSE ? Boolean.FALSE : null;
         }
      }
   }

   protected JsonToken _parsePosNumber(int var1) throws IOException {
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      int var3;
      if (var1 == 48) {
         var1 = this._handleLeadingZeroes();
         if (var1 <= 57 && var1 >= 48) {
            var3 = 0;
         } else {
            var2[0] = '0';
            var3 = 1;
         }
      } else {
         var2[0] = (char)var1;
         var1 = this._inputData.readUnsignedByte();
         var3 = 1;
      }

      int var4;
      for(var4 = var3; var1 <= 57 && var1 >= 48; var1 = this._inputData.readUnsignedByte()) {
         ++var4;
         var2[var3++] = (char)var1;
      }

      if (var1 != 46 && var1 != 101 && var1 != 69) {
         this._textBuffer.setCurrentLength(var3);
         if (this._parsingContext.inRoot()) {
            this._verifyRootSpace();
         } else {
            this._nextByte = var1;
         }

         return this.resetInt(false, var4);
      } else {
         return this._parseFloat(var2, var3, var1, false, var4);
      }
   }

   protected JsonToken _parseNegNumber() throws IOException {
      char[] var1 = this._textBuffer.emptyAndGetCurrentSegment();
      byte var2 = 0;
      int var5 = var2 + 1;
      var1[var2] = '-';
      int var3 = this._inputData.readUnsignedByte();
      var1[var5++] = (char)var3;
      if (var3 <= 48) {
         if (var3 != 48) {
            return this._handleInvalidNumberStart(var3, true);
         }

         var3 = this._handleLeadingZeroes();
      } else {
         if (var3 > 57) {
            return this._handleInvalidNumberStart(var3, true);
         }

         var3 = this._inputData.readUnsignedByte();
      }

      int var4;
      for(var4 = 1; var3 <= 57 && var3 >= 48; var3 = this._inputData.readUnsignedByte()) {
         ++var4;
         var1[var5++] = (char)var3;
      }

      if (var3 != 46 && var3 != 101 && var3 != 69) {
         this._textBuffer.setCurrentLength(var5);
         this._nextByte = var3;
         if (this._parsingContext.inRoot()) {
            this._verifyRootSpace();
         }

         return this.resetInt(true, var4);
      } else {
         return this._parseFloat(var1, var5, var3, true, var4);
      }
   }

   private final int _handleLeadingZeroes() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      if (var1 >= 48 && var1 <= 57) {
         if (!this.isEnabled(JsonParser$Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
         }

         while(var1 == 48) {
            var1 = this._inputData.readUnsignedByte();
         }

         return var1;
      } else {
         return var1;
      }
   }

   private final JsonToken _parseFloat(char[] var1, int var2, int var3, boolean var4, int var5) throws IOException {
      int var6 = 0;
      if (var3 == 46) {
         var1[var2++] = (char)var3;

         while(true) {
            var3 = this._inputData.readUnsignedByte();
            if (var3 < 48 || var3 > 57) {
               if (var6 == 0) {
                  this.reportUnexpectedNumberChar(var3, "Decimal point not followed by a digit");
               }
               break;
            }

            ++var6;
            if (var2 >= var1.length) {
               var1 = this._textBuffer.finishCurrentSegment();
               var2 = 0;
            }

            var1[var2++] = (char)var3;
         }
      }

      int var7 = 0;
      if (var3 == 101 || var3 == 69) {
         if (var2 >= var1.length) {
            var1 = this._textBuffer.finishCurrentSegment();
            var2 = 0;
         }

         var1[var2++] = (char)var3;
         var3 = this._inputData.readUnsignedByte();
         if (var3 == 45 || var3 == 43) {
            if (var2 >= var1.length) {
               var1 = this._textBuffer.finishCurrentSegment();
               var2 = 0;
            }

            var1[var2++] = (char)var3;
            var3 = this._inputData.readUnsignedByte();
         }

         while(var3 <= 57 && var3 >= 48) {
            ++var7;
            if (var2 >= var1.length) {
               var1 = this._textBuffer.finishCurrentSegment();
               var2 = 0;
            }

            var1[var2++] = (char)var3;
            var3 = this._inputData.readUnsignedByte();
         }

         if (var7 == 0) {
            this.reportUnexpectedNumberChar(var3, "Exponent indicator not followed by a digit");
         }
      }

      this._nextByte = var3;
      if (this._parsingContext.inRoot()) {
         this._verifyRootSpace();
      }

      this._textBuffer.setCurrentLength(var2);
      return this.resetFloat(var4, var5, var6, var7);
   }

   private final void _verifyRootSpace() throws IOException {
      int var1 = this._nextByte;
      if (var1 > 32) {
         this._reportMissingRootWS(var1);
      } else {
         this._nextByte = -1;
         if (var1 == 13 || var1 == 10) {
            ++this._currInputRow;
         }

      }
   }

   protected final String _parseName(int var1) throws IOException {
      if (var1 != 34) {
         return this._handleOddName(var1);
      } else {
         int[] var2 = _icLatin1;
         int var3 = this._inputData.readUnsignedByte();
         if (var2[var3] == 0) {
            var1 = this._inputData.readUnsignedByte();
            if (var2[var1] == 0) {
               var3 = var3 << 8 | var1;
               var1 = this._inputData.readUnsignedByte();
               if (var2[var1] == 0) {
                  var3 = var3 << 8 | var1;
                  var1 = this._inputData.readUnsignedByte();
                  if (var2[var1] == 0) {
                     var3 = var3 << 8 | var1;
                     var1 = this._inputData.readUnsignedByte();
                     if (var2[var1] == 0) {
                        this._quad1 = var3;
                        return this._parseMediumName(var1);
                     } else {
                        return var1 == 34 ? this.findName(var3, 4) : this.parseName(var3, var1, 4);
                     }
                  } else {
                     return var1 == 34 ? this.findName(var3, 3) : this.parseName(var3, var1, 3);
                  }
               } else {
                  return var1 == 34 ? this.findName(var3, 2) : this.parseName(var3, var1, 2);
               }
            } else {
               return var1 == 34 ? this.findName(var3, 1) : this.parseName(var3, var1, 1);
            }
         } else {
            return var3 == 34 ? "" : this.parseName(0, var3, 0);
         }
      }
   }

   private final String _parseMediumName(int var1) throws IOException {
      int[] var2 = _icLatin1;
      int var3 = this._inputData.readUnsignedByte();
      if (var2[var3] != 0) {
         return var3 == 34 ? this.findName(this._quad1, var1, 1) : this.parseName(this._quad1, var1, var3, 1);
      } else {
         var1 = var1 << 8 | var3;
         var3 = this._inputData.readUnsignedByte();
         if (var2[var3] != 0) {
            return var3 == 34 ? this.findName(this._quad1, var1, 2) : this.parseName(this._quad1, var1, var3, 2);
         } else {
            var1 = var1 << 8 | var3;
            var3 = this._inputData.readUnsignedByte();
            if (var2[var3] != 0) {
               return var3 == 34 ? this.findName(this._quad1, var1, 3) : this.parseName(this._quad1, var1, var3, 3);
            } else {
               var1 = var1 << 8 | var3;
               var3 = this._inputData.readUnsignedByte();
               if (var2[var3] != 0) {
                  return var3 == 34 ? this.findName(this._quad1, var1, 4) : this.parseName(this._quad1, var1, var3, 4);
               } else {
                  return this._parseMediumName2(var3, var1);
               }
            }
         }
      }
   }

   private final String _parseMediumName2(int var1, int var2) throws IOException {
      int[] var3 = _icLatin1;
      int var4 = this._inputData.readUnsignedByte();
      if (var3[var4] != 0) {
         return var4 == 34 ? this.findName(this._quad1, var2, var1, 1) : this.parseName(this._quad1, var2, var1, var4, 1);
      } else {
         var1 = var1 << 8 | var4;
         var4 = this._inputData.readUnsignedByte();
         if (var3[var4] != 0) {
            return var4 == 34 ? this.findName(this._quad1, var2, var1, 2) : this.parseName(this._quad1, var2, var1, var4, 2);
         } else {
            var1 = var1 << 8 | var4;
            var4 = this._inputData.readUnsignedByte();
            if (var3[var4] != 0) {
               return var4 == 34 ? this.findName(this._quad1, var2, var1, 3) : this.parseName(this._quad1, var2, var1, var4, 3);
            } else {
               var1 = var1 << 8 | var4;
               var4 = this._inputData.readUnsignedByte();
               if (var3[var4] != 0) {
                  return var4 == 34 ? this.findName(this._quad1, var2, var1, 4) : this.parseName(this._quad1, var2, var1, var4, 4);
               } else {
                  return this._parseLongName(var4, var2, var1);
               }
            }
         }
      }
   }

   private final String _parseLongName(int var1, int var2, int var3) throws IOException {
      this._quadBuffer[0] = this._quad1;
      this._quadBuffer[1] = var2;
      this._quadBuffer[2] = var3;
      int[] var4 = _icLatin1;
      int var5 = 3;

      while(true) {
         int var6 = this._inputData.readUnsignedByte();
         if (var4[var6] != 0) {
            return var6 == 34 ? this.findName(this._quadBuffer, var5, var1, 1) : this.parseEscapedName(this._quadBuffer, var5, var1, var6, 1);
         }

         var1 = var1 << 8 | var6;
         var6 = this._inputData.readUnsignedByte();
         if (var4[var6] != 0) {
            if (var6 == 34) {
               return this.findName(this._quadBuffer, var5, var1, 2);
            }

            return this.parseEscapedName(this._quadBuffer, var5, var1, var6, 2);
         }

         var1 = var1 << 8 | var6;
         var6 = this._inputData.readUnsignedByte();
         if (var4[var6] != 0) {
            if (var6 == 34) {
               return this.findName(this._quadBuffer, var5, var1, 3);
            }

            return this.parseEscapedName(this._quadBuffer, var5, var1, var6, 3);
         }

         var1 = var1 << 8 | var6;
         var6 = this._inputData.readUnsignedByte();
         if (var4[var6] != 0) {
            if (var6 == 34) {
               return this.findName(this._quadBuffer, var5, var1, 4);
            }

            return this.parseEscapedName(this._quadBuffer, var5, var1, var6, 4);
         }

         if (var5 >= this._quadBuffer.length) {
            this._quadBuffer = _growArrayBy(this._quadBuffer, var5);
         }

         this._quadBuffer[var5++] = var1;
         var1 = var6;
      }
   }

   private final String parseName(int var1, int var2, int var3) throws IOException {
      return this.parseEscapedName(this._quadBuffer, 0, var1, var2, var3);
   }

   private final String parseName(int var1, int var2, int var3, int var4) throws IOException {
      this._quadBuffer[0] = var1;
      return this.parseEscapedName(this._quadBuffer, 1, var2, var3, var4);
   }

   private final String parseName(int var1, int var2, int var3, int var4, int var5) throws IOException {
      this._quadBuffer[0] = var1;
      this._quadBuffer[1] = var2;
      return this.parseEscapedName(this._quadBuffer, 2, var3, var4, var5);
   }

   protected final String parseEscapedName(int[] var1, int var2, int var3, int var4, int var5) throws IOException {
      int[] var6 = _icLatin1;

      while(true) {
         if (var6[var4] != 0) {
            if (var4 == 34) {
               if (var5 > 0) {
                  if (var2 >= var1.length) {
                     this._quadBuffer = var1 = _growArrayBy(var1, var1.length);
                  }

                  var1[var2++] = pad(var3, var5);
               }

               String var7 = this._symbols.findName(var1, var2);
               if (var7 == null) {
                  var7 = this.addName(var1, var2, var5);
               }

               return var7;
            }

            if (var4 != 92) {
               this._throwUnquotedSpace(var4, "name");
            } else {
               var4 = this._decodeEscaped();
            }

            if (var4 > 127) {
               if (var5 >= 4) {
                  if (var2 >= var1.length) {
                     this._quadBuffer = var1 = _growArrayBy(var1, var1.length);
                  }

                  var1[var2++] = var3;
                  var3 = 0;
                  var5 = 0;
               }

               if (var4 < 2048) {
                  var3 = var3 << 8 | 192 | var4 >> 6;
                  ++var5;
               } else {
                  var3 = var3 << 8 | 224 | var4 >> 12;
                  ++var5;
                  if (var5 >= 4) {
                     if (var2 >= var1.length) {
                        this._quadBuffer = var1 = _growArrayBy(var1, var1.length);
                     }

                     var1[var2++] = var3;
                     var3 = 0;
                     var5 = 0;
                  }

                  var3 = var3 << 8 | 128 | var4 >> 6 & 63;
                  ++var5;
               }

               var4 = 128 | var4 & 63;
            }
         }

         if (var5 < 4) {
            ++var5;
            var3 = var3 << 8 | var4;
         } else {
            if (var2 >= var1.length) {
               this._quadBuffer = var1 = _growArrayBy(var1, var1.length);
            }

            var1[var2++] = var3;
            var3 = var4;
            var5 = 1;
         }

         var4 = this._inputData.readUnsignedByte();
      }
   }

   protected String _handleOddName(int var1) throws IOException {
      if (var1 == 39 && this.isEnabled(JsonParser$Feature.ALLOW_SINGLE_QUOTES)) {
         return this._parseAposName();
      } else {
         if (!this.isEnabled(JsonParser$Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            char var2 = (char)this._decodeCharForError(var1);
            this._reportUnexpectedChar(var2, "was expecting double-quote to start field name");
         }

         int[] var8 = CharTypes.getInputCodeUtf8JsNames();
         if (var8[var1] != 0) {
            this._reportUnexpectedChar(var1, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
         }

         int[] var3 = this._quadBuffer;
         int var4 = 0;
         int var5 = 0;
         int var6 = 0;

         do {
            if (var6 < 4) {
               ++var6;
               var5 = var5 << 8 | var1;
            } else {
               if (var4 >= var3.length) {
                  this._quadBuffer = var3 = _growArrayBy(var3, var3.length);
               }

               var3[var4++] = var5;
               var5 = var1;
               var6 = 1;
            }

            var1 = this._inputData.readUnsignedByte();
         } while(var8[var1] == 0);

         this._nextByte = var1;
         if (var6 > 0) {
            if (var4 >= var3.length) {
               this._quadBuffer = var3 = _growArrayBy(var3, var3.length);
            }

            var3[var4++] = var5;
         }

         String var7 = this._symbols.findName(var3, var4);
         if (var7 == null) {
            var7 = this.addName(var3, var4, var6);
         }

         return var7;
      }
   }

   protected String _parseAposName() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      if (var1 == 39) {
         return "";
      } else {
         int[] var2 = this._quadBuffer;
         int var3 = 0;
         int var4 = 0;
         int var5 = 0;

         for(int[] var6 = _icLatin1; var1 != 39; var1 = this._inputData.readUnsignedByte()) {
            if (var1 != 34 && var6[var1] != 0) {
               if (var1 != 92) {
                  this._throwUnquotedSpace(var1, "name");
               } else {
                  var1 = this._decodeEscaped();
               }

               if (var1 > 127) {
                  if (var5 >= 4) {
                     if (var3 >= var2.length) {
                        this._quadBuffer = var2 = _growArrayBy(var2, var2.length);
                     }

                     var2[var3++] = var4;
                     var4 = 0;
                     var5 = 0;
                  }

                  if (var1 < 2048) {
                     var4 = var4 << 8 | 192 | var1 >> 6;
                     ++var5;
                  } else {
                     var4 = var4 << 8 | 224 | var1 >> 12;
                     ++var5;
                     if (var5 >= 4) {
                        if (var3 >= var2.length) {
                           this._quadBuffer = var2 = _growArrayBy(var2, var2.length);
                        }

                        var2[var3++] = var4;
                        var4 = 0;
                        var5 = 0;
                     }

                     var4 = var4 << 8 | 128 | var1 >> 6 & 63;
                     ++var5;
                  }

                  var1 = 128 | var1 & 63;
               }
            }

            if (var5 < 4) {
               ++var5;
               var4 = var4 << 8 | var1;
            } else {
               if (var3 >= var2.length) {
                  this._quadBuffer = var2 = _growArrayBy(var2, var2.length);
               }

               var2[var3++] = var4;
               var4 = var1;
               var5 = 1;
            }
         }

         if (var5 > 0) {
            if (var3 >= var2.length) {
               this._quadBuffer = var2 = _growArrayBy(var2, var2.length);
            }

            var2[var3++] = pad(var4, var5);
         }

         String var7 = this._symbols.findName(var2, var3);
         if (var7 == null) {
            var7 = this.addName(var2, var3, var5);
         }

         return var7;
      }
   }

   private final String findName(int var1, int var2) throws JsonParseException {
      var1 = pad(var1, var2);
      String var3 = this._symbols.findName(var1);
      if (var3 != null) {
         return var3;
      } else {
         this._quadBuffer[0] = var1;
         return this.addName(this._quadBuffer, 1, var2);
      }
   }

   private final String findName(int var1, int var2, int var3) throws JsonParseException {
      var2 = pad(var2, var3);
      String var4 = this._symbols.findName(var1, var2);
      if (var4 != null) {
         return var4;
      } else {
         this._quadBuffer[0] = var1;
         this._quadBuffer[1] = var2;
         return this.addName(this._quadBuffer, 2, var3);
      }
   }

   private final String findName(int var1, int var2, int var3, int var4) throws JsonParseException {
      var3 = pad(var3, var4);
      String var5 = this._symbols.findName(var1, var2, var3);
      if (var5 != null) {
         return var5;
      } else {
         int[] var6 = this._quadBuffer;
         var6[0] = var1;
         var6[1] = var2;
         var6[2] = pad(var3, var4);
         return this.addName(var6, 3, var4);
      }
   }

   private final String findName(int[] var1, int var2, int var3, int var4) throws JsonParseException {
      if (var2 >= var1.length) {
         this._quadBuffer = var1 = _growArrayBy(var1, var1.length);
      }

      var1[var2++] = pad(var3, var4);
      String var5 = this._symbols.findName(var1, var2);
      return var5 == null ? this.addName(var1, var2, var4) : var5;
   }

   private final String addName(int[] var1, int var2, int var3) throws JsonParseException {
      int var4 = (var2 << 2) - 4 + var3;
      int var5;
      if (var3 < 4) {
         var5 = var1[var2 - 1];
         var1[var2 - 1] = var5 << (4 - var3 << 3);
      } else {
         var5 = 0;
      }

      char[] var6 = this._textBuffer.emptyAndGetCurrentSegment();
      int var7 = 0;

      int var9;
      for(int var8 = 0; var8 < var4; var6[var7++] = (char)var9) {
         var9 = var1[var8 >> 2];
         int var10 = var8 & 3;
         var9 = var9 >> (3 - var10 << 3) & 255;
         ++var8;
         if (var9 > 127) {
            byte var11;
            if ((var9 & 224) == 192) {
               var9 &= 31;
               var11 = 1;
            } else if ((var9 & 240) == 224) {
               var9 &= 15;
               var11 = 2;
            } else if ((var9 & 248) == 240) {
               var9 &= 7;
               var11 = 3;
            } else {
               this._reportInvalidInitial(var9);
               var9 = 1;
               var11 = 1;
            }

            if (var8 + var11 > var4) {
               this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }

            int var12 = var1[var8 >> 2];
            var10 = var8 & 3;
            var12 >>= 3 - var10 << 3;
            ++var8;
            if ((var12 & 192) != 128) {
               this._reportInvalidOther(var12);
            }

            var9 = var9 << 6 | var12 & 63;
            if (var11 > 1) {
               var12 = var1[var8 >> 2];
               var10 = var8 & 3;
               var12 >>= 3 - var10 << 3;
               ++var8;
               if ((var12 & 192) != 128) {
                  this._reportInvalidOther(var12);
               }

               var9 = var9 << 6 | var12 & 63;
               if (var11 > 2) {
                  var12 = var1[var8 >> 2];
                  var10 = var8 & 3;
                  var12 >>= 3 - var10 << 3;
                  ++var8;
                  if ((var12 & 192) != 128) {
                     this._reportInvalidOther(var12 & 255);
                  }

                  var9 = var9 << 6 | var12 & 63;
               }
            }

            if (var11 > 2) {
               var9 -= 65536;
               if (var7 >= var6.length) {
                  var6 = this._textBuffer.expandCurrentSegment();
               }

               var6[var7++] = (char)('\ud800' + (var9 >> 10));
               var9 = '\udc00' | var9 & 1023;
            }
         }

         if (var7 >= var6.length) {
            var6 = this._textBuffer.expandCurrentSegment();
         }
      }

      String var13 = new String(var6, 0, var7);
      if (var3 < 4) {
         var1[var2 - 1] = var5;
      }

      return this._symbols.addName(var13, var1, var2);
   }

   protected void _finishString() throws IOException {
      int var1 = 0;
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var3 = _icUTF8;
      int var4 = var2.length;

      do {
         int var5 = this._inputData.readUnsignedByte();
         if (var3[var5] != 0) {
            if (var5 == 34) {
               this._textBuffer.setCurrentLength(var1);
               return;
            } else {
               this._finishString2(var2, var1, var5);
               return;
            }
         }

         var2[var1++] = (char)var5;
      } while(var1 < var4);

      this._finishString2(var2, var1, this._inputData.readUnsignedByte());
   }

   private String _finishAndReturnString() throws IOException {
      int var1 = 0;
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var3 = _icUTF8;
      int var4 = var2.length;

      do {
         int var5 = this._inputData.readUnsignedByte();
         if (var3[var5] != 0) {
            if (var5 == 34) {
               return this._textBuffer.setCurrentAndReturn(var1);
            } else {
               this._finishString2(var2, var1, var5);
               return this._textBuffer.contentsAsString();
            }
         }

         var2[var1++] = (char)var5;
      } while(var1 < var4);

      this._finishString2(var2, var1, this._inputData.readUnsignedByte());
      return this._textBuffer.contentsAsString();
   }

   private final void _finishString2(char[] var1, int var2, int var3) throws IOException {
      int[] var4 = _icUTF8;
      int var5 = var1.length;

      while(true) {
         while(var4[var3] != 0) {
            if (var3 == 34) {
               this._textBuffer.setCurrentLength(var2);
               return;
            }

            switch(var4[var3]) {
            case 1:
               var3 = this._decodeEscaped();
               break;
            case 2:
               var3 = this._decodeUtf8_2(var3);
               break;
            case 3:
               var3 = this._decodeUtf8_3(var3);
               break;
            case 4:
               var3 = this._decodeUtf8_4(var3);
               var1[var2++] = (char)('\ud800' | var3 >> 10);
               if (var2 >= var1.length) {
                  var1 = this._textBuffer.finishCurrentSegment();
                  var2 = 0;
                  var5 = var1.length;
               }

               var3 = '\udc00' | var3 & 1023;
               break;
            default:
               if (var3 < 32) {
                  this._throwUnquotedSpace(var3, "string value");
               } else {
                  this._reportInvalidChar(var3);
               }
            }

            if (var2 >= var1.length) {
               var1 = this._textBuffer.finishCurrentSegment();
               var2 = 0;
               var5 = var1.length;
            }

            var1[var2++] = (char)var3;
            var3 = this._inputData.readUnsignedByte();
         }

         if (var2 >= var5) {
            var1 = this._textBuffer.finishCurrentSegment();
            var2 = 0;
            var5 = var1.length;
         }

         var1[var2++] = (char)var3;
         var3 = this._inputData.readUnsignedByte();
      }
   }

   protected void _skipString() throws IOException {
      this._tokenIncomplete = false;
      int[] var1 = _icUTF8;

      while(true) {
         int var2;
         do {
            var2 = this._inputData.readUnsignedByte();
         } while(var1[var2] == 0);

         if (var2 == 34) {
            return;
         }

         switch(var1[var2]) {
         case 1:
            this._decodeEscaped();
            break;
         case 2:
            this._skipUtf8_2();
            break;
         case 3:
            this._skipUtf8_3();
            break;
         case 4:
            this._skipUtf8_4();
            break;
         default:
            if (var2 < 32) {
               this._throwUnquotedSpace(var2, "string value");
            } else {
               this._reportInvalidChar(var2);
            }
         }
      }
   }

   protected JsonToken _handleUnexpectedValue(int var1) throws IOException {
      switch(var1) {
      case 43:
         return this._handleInvalidNumberStart(this._inputData.readUnsignedByte(), false);
      case 73:
         this._matchToken("Infinity", 1);
         if (this.isEnabled(JsonParser$Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
            return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
         }

         this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
         break;
      case 78:
         this._matchToken("NaN", 1);
         if (this.isEnabled(JsonParser$Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
            return this.resetAsNaN("NaN", Double.NaN);
         }

         this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
         break;
      case 93:
         if (!this._parsingContext.inArray()) {
            break;
         }
      case 44:
         if (this.isEnabled(JsonParser$Feature.ALLOW_MISSING_VALUES)) {
            this._nextByte = var1;
            return JsonToken.VALUE_NULL;
         }
      case 125:
         this._reportUnexpectedChar(var1, "expected a value");
      case 39:
         if (this.isEnabled(JsonParser$Feature.ALLOW_SINGLE_QUOTES)) {
            return this._handleApos();
         }
      }

      if (Character.isJavaIdentifierStart(var1)) {
         this._reportInvalidToken(var1, "" + (char)var1, "('true', 'false' or 'null')");
      }

      this._reportUnexpectedChar(var1, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
      return null;
   }

   protected JsonToken _handleApos() throws IOException {
      boolean var1 = false;
      int var2 = 0;
      char[] var3 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var4 = _icUTF8;

      while(true) {
         while(true) {
            int var5 = var3.length;
            if (var2 >= var3.length) {
               var3 = this._textBuffer.finishCurrentSegment();
               var2 = 0;
               var5 = var3.length;
            }

            while(true) {
               int var6 = this._inputData.readUnsignedByte();
               if (var6 == 39) {
                  this._textBuffer.setCurrentLength(var2);
                  return JsonToken.VALUE_STRING;
               }

               if (var4[var6] != 0) {
                  switch(var4[var6]) {
                  case 1:
                     var6 = this._decodeEscaped();
                     break;
                  case 2:
                     var6 = this._decodeUtf8_2(var6);
                     break;
                  case 3:
                     var6 = this._decodeUtf8_3(var6);
                     break;
                  case 4:
                     var6 = this._decodeUtf8_4(var6);
                     var3[var2++] = (char)('\ud800' | var6 >> 10);
                     if (var2 >= var3.length) {
                        var3 = this._textBuffer.finishCurrentSegment();
                        var2 = 0;
                     }

                     var6 = '\udc00' | var6 & 1023;
                     break;
                  default:
                     if (var6 < 32) {
                        this._throwUnquotedSpace(var6, "string value");
                     }

                     this._reportInvalidChar(var6);
                  }

                  if (var2 >= var3.length) {
                     var3 = this._textBuffer.finishCurrentSegment();
                     var2 = 0;
                  }

                  var3[var2++] = (char)var6;
                  break;
               }

               var3[var2++] = (char)var6;
               if (var2 >= var5) {
                  break;
               }
            }
         }
      }
   }

   protected JsonToken _handleInvalidNumberStart(int var1, boolean var2) throws IOException {
      while(var1 == 73) {
         var1 = this._inputData.readUnsignedByte();
         String var3;
         if (var1 == 78) {
            var3 = var2 ? "-INF" : "+INF";
         } else {
            if (var1 != 110) {
               break;
            }

            var3 = var2 ? "-Infinity" : "+Infinity";
         }

         this._matchToken(var3, 3);
         if (this.isEnabled(JsonParser$Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
            return this.resetAsNaN(var3, var2 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
         }

         this._reportError("Non-standard token '" + var3 + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
      }

      this.reportUnexpectedNumberChar(var1, "expected digit (0-9) to follow minus sign, for valid numeric value");
      return null;
   }

   protected final void _matchToken(String var1, int var2) throws IOException {
      int var3 = var1.length();

      int var4;
      do {
         var4 = this._inputData.readUnsignedByte();
         if (var4 != var1.charAt(var2)) {
            this._reportInvalidToken(var4, var1.substring(0, var2));
         }

         ++var2;
      } while(var2 < var3);

      var4 = this._inputData.readUnsignedByte();
      if (var4 >= 48 && var4 != 93 && var4 != 125) {
         this._checkMatchEnd(var1, var2, var4);
      }

      this._nextByte = var4;
   }

   private final void _checkMatchEnd(String var1, int var2, int var3) throws IOException {
      char var4 = (char)this._decodeCharForError(var3);
      if (Character.isJavaIdentifierPart(var4)) {
         this._reportInvalidToken(var4, var1.substring(0, var2));
      }

   }

   private final int _skipWS() throws IOException {
      int var1 = this._nextByte;
      if (var1 < 0) {
         var1 = this._inputData.readUnsignedByte();
      } else {
         this._nextByte = -1;
      }

      for(; var1 <= 32; var1 = this._inputData.readUnsignedByte()) {
         if (var1 == 13 || var1 == 10) {
            ++this._currInputRow;
         }
      }

      if (var1 != 47 && var1 != 35) {
         return var1;
      } else {
         return this._skipWSComment(var1);
      }
   }

   private final int _skipWSOrEnd() throws IOException {
      int var1 = this._nextByte;
      if (var1 < 0) {
         try {
            var1 = this._inputData.readUnsignedByte();
         } catch (EOFException var4) {
            return this._eofAsNextChar();
         }
      } else {
         this._nextByte = -1;
      }

      while(var1 <= 32) {
         if (var1 == 13 || var1 == 10) {
            ++this._currInputRow;
         }

         try {
            var1 = this._inputData.readUnsignedByte();
         } catch (EOFException var3) {
            return this._eofAsNextChar();
         }
      }

      if (var1 != 47 && var1 != 35) {
         return var1;
      } else {
         return this._skipWSComment(var1);
      }
   }

   private final int _skipWSComment(int var1) throws IOException {
      while(true) {
         if (var1 > 32) {
            if (var1 == 47) {
               this._skipComment();
            } else {
               if (var1 != 35) {
                  return var1;
               }

               if (!this._skipYAMLComment()) {
                  return var1;
               }
            }
         } else if (var1 == 13 || var1 == 10) {
            ++this._currInputRow;
         }

         var1 = this._inputData.readUnsignedByte();
      }
   }

   private final int _skipColon() throws IOException {
      int var1 = this._nextByte;
      if (var1 < 0) {
         var1 = this._inputData.readUnsignedByte();
      } else {
         this._nextByte = -1;
      }

      if (var1 == 58) {
         var1 = this._inputData.readUnsignedByte();
         if (var1 > 32) {
            return var1 != 47 && var1 != 35 ? var1 : this._skipColon2(var1, true);
         } else {
            if (var1 == 32 || var1 == 9) {
               var1 = this._inputData.readUnsignedByte();
               if (var1 > 32) {
                  if (var1 != 47 && var1 != 35) {
                     return var1;
                  }

                  return this._skipColon2(var1, true);
               }
            }

            return this._skipColon2(var1, true);
         }
      } else {
         if (var1 == 32 || var1 == 9) {
            var1 = this._inputData.readUnsignedByte();
         }

         if (var1 == 58) {
            var1 = this._inputData.readUnsignedByte();
            if (var1 > 32) {
               return var1 != 47 && var1 != 35 ? var1 : this._skipColon2(var1, true);
            } else {
               if (var1 == 32 || var1 == 9) {
                  var1 = this._inputData.readUnsignedByte();
                  if (var1 > 32) {
                     if (var1 != 47 && var1 != 35) {
                        return var1;
                     }

                     return this._skipColon2(var1, true);
                  }
               }

               return this._skipColon2(var1, true);
            }
         } else {
            return this._skipColon2(var1, false);
         }
      }
   }

   private final int _skipColon2(int var1, boolean var2) throws IOException {
      while(true) {
         if (var1 > 32) {
            if (var1 == 47) {
               this._skipComment();
            } else if (var1 != 35 || !this._skipYAMLComment()) {
               if (var2) {
                  return var1;
               }

               if (var1 != 58) {
                  this._reportUnexpectedChar(var1, "was expecting a colon to separate field name and value");
               }

               var2 = true;
            }
         } else if (var1 == 13 || var1 == 10) {
            ++this._currInputRow;
         }

         var1 = this._inputData.readUnsignedByte();
      }
   }

   private final void _skipComment() throws IOException {
      if (!this.isEnabled(JsonParser$Feature.ALLOW_COMMENTS)) {
         this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
      }

      int var1 = this._inputData.readUnsignedByte();
      if (var1 == 47) {
         this._skipLine();
      } else if (var1 == 42) {
         this._skipCComment();
      } else {
         this._reportUnexpectedChar(var1, "was expecting either '*' or '/' for a comment");
      }

   }

   private final void _skipCComment() throws IOException {
      int[] var1 = CharTypes.getInputCodeComment();
      int var2 = this._inputData.readUnsignedByte();

      label24:
      do {
         while(true) {
            int var3 = var1[var2];
            if (var3 != 0) {
               switch(var3) {
               case 2:
                  this._skipUtf8_2();
                  break;
               case 3:
                  this._skipUtf8_3();
                  break;
               case 4:
                  this._skipUtf8_4();
                  break;
               case 10:
               case 13:
                  ++this._currInputRow;
                  break;
               case 42:
                  var2 = this._inputData.readUnsignedByte();
                  continue label24;
               default:
                  this._reportInvalidChar(var2);
               }
            }

            var2 = this._inputData.readUnsignedByte();
         }
      } while(var2 != 47);

   }

   private final boolean _skipYAMLComment() throws IOException {
      if (!this.isEnabled(JsonParser$Feature.ALLOW_YAML_COMMENTS)) {
         return false;
      } else {
         this._skipLine();
         return true;
      }
   }

   private final void _skipLine() throws IOException {
      int[] var1 = CharTypes.getInputCodeComment();

      while(true) {
         int var2;
         int var3;
         do {
            var2 = this._inputData.readUnsignedByte();
            var3 = var1[var2];
         } while(var3 == 0);

         switch(var3) {
         case 2:
            this._skipUtf8_2();
            break;
         case 3:
            this._skipUtf8_3();
            break;
         case 4:
            this._skipUtf8_4();
            break;
         case 10:
         case 13:
            ++this._currInputRow;
            return;
         case 42:
            break;
         default:
            if (var3 < 0) {
               this._reportInvalidChar(var2);
            }
         }
      }
   }

   protected char _decodeEscaped() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      switch(var1) {
      case 34:
      case 47:
      case 92:
         return (char)var1;
      case 98:
         return '\b';
      case 102:
         return '\f';
      case 110:
         return '\n';
      case 114:
         return '\r';
      case 116:
         return '\t';
      case 117:
         int var2 = 0;

         for(int var3 = 0; var3 < 4; ++var3) {
            int var4 = this._inputData.readUnsignedByte();
            int var5 = CharTypes.charToHex(var4);
            if (var5 < 0) {
               this._reportUnexpectedChar(var4, "expected a hex-digit for character escape sequence");
            }

            var2 = var2 << 4 | var5;
         }

         return (char)var2;
      default:
         return this._handleUnrecognizedCharacterEscape((char)this._decodeCharForError(var1));
      }
   }

   protected int _decodeCharForError(int var1) throws IOException {
      int var2 = var1 & 255;
      if (var2 > 127) {
         byte var3;
         if ((var2 & 224) == 192) {
            var2 &= 31;
            var3 = 1;
         } else if ((var2 & 240) == 224) {
            var2 &= 15;
            var3 = 2;
         } else if ((var2 & 248) == 240) {
            var2 &= 7;
            var3 = 3;
         } else {
            this._reportInvalidInitial(var2 & 255);
            var3 = 1;
         }

         int var4 = this._inputData.readUnsignedByte();
         if ((var4 & 192) != 128) {
            this._reportInvalidOther(var4 & 255);
         }

         var2 = var2 << 6 | var4 & 63;
         if (var3 > 1) {
            var4 = this._inputData.readUnsignedByte();
            if ((var4 & 192) != 128) {
               this._reportInvalidOther(var4 & 255);
            }

            var2 = var2 << 6 | var4 & 63;
            if (var3 > 2) {
               var4 = this._inputData.readUnsignedByte();
               if ((var4 & 192) != 128) {
                  this._reportInvalidOther(var4 & 255);
               }

               var2 = var2 << 6 | var4 & 63;
            }
         }
      }

      return var2;
   }

   private final int _decodeUtf8_2(int var1) throws IOException {
      int var2 = this._inputData.readUnsignedByte();
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255);
      }

      return (var1 & 31) << 6 | var2 & 63;
   }

   private final int _decodeUtf8_3(int var1) throws IOException {
      var1 &= 15;
      int var2 = this._inputData.readUnsignedByte();
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255);
      }

      int var3 = var1 << 6 | var2 & 63;
      var2 = this._inputData.readUnsignedByte();
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255);
      }

      var3 = var3 << 6 | var2 & 63;
      return var3;
   }

   private final int _decodeUtf8_4(int var1) throws IOException {
      int var2 = this._inputData.readUnsignedByte();
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255);
      }

      var1 = (var1 & 7) << 6 | var2 & 63;
      var2 = this._inputData.readUnsignedByte();
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255);
      }

      var1 = var1 << 6 | var2 & 63;
      var2 = this._inputData.readUnsignedByte();
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255);
      }

      return (var1 << 6 | var2 & 63) - 65536;
   }

   private final void _skipUtf8_2() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

   }

   private final void _skipUtf8_3() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

      var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

   }

   private final void _skipUtf8_4() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

      var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

      var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

   }

   protected void _reportInvalidToken(int var1, String var2) throws IOException {
      this._reportInvalidToken(var1, var2, "'null', 'true', 'false' or NaN");
   }

   protected void _reportInvalidToken(int var1, String var2, String var3) throws IOException {
      StringBuilder var4 = new StringBuilder(var2);

      while(true) {
         char var5 = (char)this._decodeCharForError(var1);
         if (!Character.isJavaIdentifierPart(var5)) {
            this._reportError("Unrecognized token '" + var4.toString() + "': was expecting " + var3);
            return;
         }

         var4.append(var5);
         var1 = this._inputData.readUnsignedByte();
      }
   }

   protected void _reportInvalidChar(int var1) throws JsonParseException {
      if (var1 < 32) {
         this._throwInvalidSpace(var1);
      }

      this._reportInvalidInitial(var1);
   }

   protected void _reportInvalidInitial(int var1) throws JsonParseException {
      this._reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(var1));
   }

   private void _reportInvalidOther(int var1) throws JsonParseException {
      this._reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(var1));
   }

   private static int[] _growArrayBy(int[] var0, int var1) {
      return var0 == null ? new int[var1] : Arrays.copyOf(var0, var0.length + var1);
   }

   protected final byte[] _decodeBase64(Base64Variant var1) throws IOException {
      ByteArrayBuilder var2 = this._getByteArrayBuilder();

      while(true) {
         while(true) {
            int var3;
            int var4;
            do {
               do {
                  var3 = this._inputData.readUnsignedByte();
               } while(var3 <= 32);

               var4 = var1.decodeBase64Char(var3);
               if (var4 >= 0) {
                  break;
               }

               if (var3 == 34) {
                  return var2.toByteArray();
               }

               var4 = this._decodeBase64Escape(var1, var3, 0);
            } while(var4 < 0);

            int var5 = var4;
            var3 = this._inputData.readUnsignedByte();
            var4 = var1.decodeBase64Char(var3);
            if (var4 < 0) {
               var4 = this._decodeBase64Escape(var1, var3, 1);
            }

            var5 = var5 << 6 | var4;
            var3 = this._inputData.readUnsignedByte();
            var4 = var1.decodeBase64Char(var3);
            if (var4 < 0) {
               if (var4 != -2) {
                  if (var3 == 34 && !var1.usesPadding()) {
                     var5 >>= 4;
                     var2.append(var5);
                     return var2.toByteArray();
                  }

                  var4 = this._decodeBase64Escape(var1, var3, 2);
               }

               if (var4 == -2) {
                  var3 = this._inputData.readUnsignedByte();
                  if (!var1.usesPaddingChar(var3)) {
                     throw this.reportInvalidBase64Char(var1, var3, 3, "expected padding character '" + var1.getPaddingChar() + "'");
                  }

                  var5 >>= 4;
                  var2.append(var5);
                  continue;
               }
            }

            var5 = var5 << 6 | var4;
            var3 = this._inputData.readUnsignedByte();
            var4 = var1.decodeBase64Char(var3);
            if (var4 < 0) {
               if (var4 != -2) {
                  if (var3 == 34 && !var1.usesPadding()) {
                     var5 >>= 2;
                     var2.appendTwoBytes(var5);
                     return var2.toByteArray();
                  }

                  var4 = this._decodeBase64Escape(var1, var3, 3);
               }

               if (var4 == -2) {
                  var5 >>= 2;
                  var2.appendTwoBytes(var5);
                  continue;
               }
            }

            var5 = var5 << 6 | var4;
            var2.appendThreeBytes(var5);
         }
      }
   }

   public JsonLocation getTokenLocation() {
      return new JsonLocation(this._getSourceReference(), -1L, -1L, this._tokenInputRow, -1);
   }

   public JsonLocation getCurrentLocation() {
      return new JsonLocation(this._getSourceReference(), -1L, -1L, this._currInputRow, -1);
   }

   private void _closeScope(int var1) throws JsonParseException {
      if (var1 == 93) {
         if (!this._parsingContext.inArray()) {
            this._reportMismatchedEndMarker(var1, '}');
         }

         this._parsingContext = this._parsingContext.clearAndGetParent();
         this._currToken = JsonToken.END_ARRAY;
      }

      if (var1 == 125) {
         if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(var1, ']');
         }

         this._parsingContext = this._parsingContext.clearAndGetParent();
         this._currToken = JsonToken.END_OBJECT;
      }

   }

   private static final int pad(int var0, int var1) {
      return var1 == 4 ? var0 : var0 | -1 << (var1 << 3);
   }
}
