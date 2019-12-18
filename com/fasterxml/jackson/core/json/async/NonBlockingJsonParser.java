package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.JsonParser$Feature;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.core.async.NonBlockingInputFeeder;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;
import java.io.OutputStream;

public class NonBlockingJsonParser extends NonBlockingJsonParserBase implements ByteArrayFeeder {
   private static final int[] _icUTF8 = CharTypes.getInputCodeUtf8();
   protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
   protected byte[] _inputBuffer;
   protected int _origBufferLen;

   public NonBlockingJsonParser(IOContext var1, int var2, ByteQuadsCanonicalizer var3) {
      super(var1, var2, var3);
      this._inputBuffer = NO_BYTES;
   }

   public ByteArrayFeeder getNonBlockingInputFeeder() {
      return this;
   }

   public final boolean needMoreInput() {
      return this._inputPtr >= this._inputEnd && !this._endOfInput;
   }

   public void feedInput(byte[] var1, int var2, int var3) throws IOException {
      if (this._inputPtr < this._inputEnd) {
         this._reportError("Still have %d undecoded bytes, should not call 'feedInput'", this._inputEnd - this._inputPtr);
      }

      if (var3 < var2) {
         this._reportError("Input end (%d) may not be before start (%d)", var3, var2);
      }

      if (this._endOfInput) {
         this._reportError("Already closed, can not feed more input");
      }

      this._currInputProcessed += (long)this._origBufferLen;
      this._currInputRowStart = var2 - (this._inputEnd - this._currInputRowStart);
      this._inputBuffer = var1;
      this._inputPtr = var2;
      this._inputEnd = var3;
      this._origBufferLen = var3 - var2;
   }

   public void endOfInput() {
      this._endOfInput = true;
   }

   public int releaseBuffered(OutputStream var1) throws IOException {
      int var2 = this._inputEnd - this._inputPtr;
      if (var2 > 0) {
         var1.write(this._inputBuffer, this._inputPtr, var2);
      }

      return var2;
   }

   protected char _decodeEscaped() throws IOException {
      VersionUtil.throwInternal();
      return ' ';
   }

   public JsonToken nextToken() throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         if (this._closed) {
            return null;
         } else if (this._endOfInput) {
            return this._currToken == JsonToken.NOT_AVAILABLE ? this._finishTokenWithEOF() : this._eofAsNextToken();
         } else {
            return JsonToken.NOT_AVAILABLE;
         }
      } else if (this._currToken == JsonToken.NOT_AVAILABLE) {
         return this._finishToken();
      } else {
         this._numTypesValid = 0;
         this._tokenInputTotal = this._currInputProcessed + (long)this._inputPtr;
         this._binaryValue = null;
         int var1 = this._inputBuffer[this._inputPtr++] & 255;
         switch(this._majorState) {
         case 0:
            return this._startDocument(var1);
         case 1:
            return this._startValue(var1);
         case 2:
            return this._startFieldName(var1);
         case 3:
            return this._startFieldNameAfterComma(var1);
         case 4:
            return this._startValueExpectColon(var1);
         case 5:
            return this._startValue(var1);
         case 6:
            return this._startValueExpectComma(var1);
         default:
            VersionUtil.throwInternal();
            return null;
         }
      }
   }

   protected final JsonToken _finishToken() throws IOException {
      switch(this._minorState) {
      case 1:
         return this._finishBOM(this._pending32);
      case 2:
      case 3:
      case 6:
      case 11:
      case 20:
      case 21:
      case 22:
      case 27:
      case 28:
      case 29:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 46:
      case 47:
      case 48:
      case 49:
      default:
         VersionUtil.throwInternal();
         return null;
      case 4:
         return this._startFieldName(this._inputBuffer[this._inputPtr++] & 255);
      case 5:
         return this._startFieldNameAfterComma(this._inputBuffer[this._inputPtr++] & 255);
      case 7:
         return this._parseEscapedName(this._quadLength, this._pending32, this._pendingBytes);
      case 8:
         return this._finishFieldWithEscape();
      case 9:
         return this._finishAposName(this._quadLength, this._pending32, this._pendingBytes);
      case 10:
         return this._finishUnquotedName(this._quadLength, this._pending32, this._pendingBytes);
      case 12:
         return this._startValue(this._inputBuffer[this._inputPtr++] & 255);
      case 13:
         return this._startValueExpectComma(this._inputBuffer[this._inputPtr++] & 255);
      case 14:
         return this._startValueExpectColon(this._inputBuffer[this._inputPtr++] & 255);
      case 15:
         return this._startValueAfterComma(this._inputBuffer[this._inputPtr++] & 255);
      case 16:
         return this._finishKeywordToken("null", this._pending32, JsonToken.VALUE_NULL);
      case 17:
         return this._finishKeywordToken("true", this._pending32, JsonToken.VALUE_TRUE);
      case 18:
         return this._finishKeywordToken("false", this._pending32, JsonToken.VALUE_FALSE);
      case 19:
         return this._finishNonStdToken(this._nonStdTokenType, this._pending32);
      case 23:
         return this._finishNumberMinus(this._inputBuffer[this._inputPtr++] & 255);
      case 24:
         return this._finishNumberLeadingZeroes();
      case 25:
         return this._finishNumberLeadingNegZeroes();
      case 26:
         return this._finishNumberIntegralPart(this._textBuffer.getBufferWithoutReset(), this._textBuffer.getCurrentSegmentSize());
      case 30:
         return this._finishFloatFraction();
      case 31:
         return this._finishFloatExponent(true, this._inputBuffer[this._inputPtr++] & 255);
      case 32:
         return this._finishFloatExponent(false, this._inputBuffer[this._inputPtr++] & 255);
      case 40:
         return this._finishRegularString();
      case 41:
         int var1 = this._decodeSplitEscaped(this._quoted32, this._quotedDigits);
         if (var1 < 0) {
            return JsonToken.NOT_AVAILABLE;
         } else {
            this._textBuffer.append((char)var1);
            if (this._minorStateAfterSplit == 45) {
               return this._finishAposString();
            }

            return this._finishRegularString();
         }
      case 42:
         this._textBuffer.append((char)this._decodeUTF8_2(this._pending32, this._inputBuffer[this._inputPtr++]));
         if (this._minorStateAfterSplit == 45) {
            return this._finishAposString();
         }

         return this._finishRegularString();
      case 43:
         if (!this._decodeSplitUTF8_3(this._pending32, this._pendingBytes, this._inputBuffer[this._inputPtr++])) {
            return JsonToken.NOT_AVAILABLE;
         } else {
            if (this._minorStateAfterSplit == 45) {
               return this._finishAposString();
            }

            return this._finishRegularString();
         }
      case 44:
         if (!this._decodeSplitUTF8_4(this._pending32, this._pendingBytes, this._inputBuffer[this._inputPtr++])) {
            return JsonToken.NOT_AVAILABLE;
         } else {
            if (this._minorStateAfterSplit == 45) {
               return this._finishAposString();
            }

            return this._finishRegularString();
         }
      case 45:
         return this._finishAposString();
      case 50:
         return this._finishErrorToken();
      case 51:
         return this._startSlashComment(this._pending32);
      case 52:
         return this._finishCComment(this._pending32, true);
      case 53:
         return this._finishCComment(this._pending32, false);
      case 54:
         return this._finishCppComment(this._pending32);
      case 55:
         return this._finishHashComment(this._pending32);
      }
   }

   protected final JsonToken _finishTokenWithEOF() throws IOException {
      JsonToken var1 = this._currToken;
      switch(this._minorState) {
      case 3:
         return this._eofAsNextToken();
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 13:
      case 14:
      case 15:
      case 20:
      case 21:
      case 22:
      case 23:
      case 27:
      case 28:
      case 29:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 51:
      default:
         this._reportInvalidEOF(": was expecting rest of token (internal state: " + this._minorState + ")", this._currToken);
         return var1;
      case 12:
         return this._eofAsNextToken();
      case 16:
         return this._finishKeywordTokenWithEOF("null", this._pending32, JsonToken.VALUE_NULL);
      case 17:
         return this._finishKeywordTokenWithEOF("true", this._pending32, JsonToken.VALUE_TRUE);
      case 18:
         return this._finishKeywordTokenWithEOF("false", this._pending32, JsonToken.VALUE_FALSE);
      case 19:
         return this._finishNonStdTokenWithEOF(this._nonStdTokenType, this._pending32);
      case 24:
      case 25:
         return this._valueCompleteInt(0, "0");
      case 26:
         int var2 = this._textBuffer.getCurrentSegmentSize();
         if (this._numberNegative) {
            --var2;
         }

         this._intLength = var2;
         return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
      case 30:
         this._expLength = 0;
      case 32:
         return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
      case 31:
         this._reportInvalidEOF(": was expecting fraction after exponent marker", JsonToken.VALUE_NUMBER_FLOAT);
      case 52:
      case 53:
         this._reportInvalidEOF(": was expecting closing '*/' for comment", JsonToken.NOT_AVAILABLE);
      case 54:
      case 55:
         return this._eofAsNextToken();
      case 50:
         return this._finishErrorTokenWithEOF();
      }
   }

   private final JsonToken _startDocument(int var1) throws IOException {
      var1 &= 255;
      if (var1 == 239 && this._minorState != 1) {
         return this._finishBOM(1);
      } else {
         while(var1 <= 32) {
            if (var1 != 32) {
               if (var1 == 10) {
                  ++this._currInputRow;
                  this._currInputRowStart = this._inputPtr;
               } else if (var1 == 13) {
                  ++this._currInputRowAlt;
                  this._currInputRowStart = this._inputPtr;
               } else if (var1 != 9) {
                  this._throwInvalidSpace(var1);
               }
            }

            if (this._inputPtr >= this._inputEnd) {
               this._minorState = 3;
               if (this._closed) {
                  return null;
               }

               if (this._endOfInput) {
                  return this._eofAsNextToken();
               }

               return JsonToken.NOT_AVAILABLE;
            }

            var1 = this._inputBuffer[this._inputPtr++] & 255;
         }

         return this._startValue(var1);
      }
   }

   private final JsonToken _finishBOM(int var1) throws IOException {
      for(; this._inputPtr < this._inputEnd; ++var1) {
         int var2 = this._inputBuffer[this._inputPtr++] & 255;
         switch(var1) {
         case 1:
            if (var2 != 187) {
               this._reportError("Unexpected byte 0x%02x following 0xEF; should get 0xBB as second byte UTF-8 BOM", var2);
            }
            break;
         case 2:
            if (var2 != 191) {
               this._reportError("Unexpected byte 0x%02x following 0xEF 0xBB; should get 0xBF as third byte of UTF-8 BOM", var2);
            }
            break;
         case 3:
            this._currInputProcessed -= 3L;
            return this._startDocument(var2);
         }
      }

      this._pending32 = var1;
      this._minorState = 1;
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   private final JsonToken _startFieldName(int var1) throws IOException {
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         if (var1 <= 0) {
            this._minorState = 4;
            return this._currToken;
         }
      }

      this._updateTokenLocation();
      if (var1 != 34) {
         return var1 == 125 ? this._closeObjectScope() : this._handleOddName(var1);
      } else {
         if (this._inputPtr + 13 <= this._inputEnd) {
            String var2 = this._fastParseName();
            if (var2 != null) {
               return this._fieldComplete(var2);
            }
         }

         return this._parseEscapedName(0, 0, 0);
      }
   }

   private final JsonToken _startFieldNameAfterComma(int var1) throws IOException {
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         if (var1 <= 0) {
            this._minorState = 5;
            return this._currToken;
         }
      }

      if (var1 != 44) {
         if (var1 == 125) {
            return this._closeObjectScope();
         }

         if (var1 == 35) {
            return this._finishHashComment(5);
         }

         if (var1 == 47) {
            return this._startSlashComment(5);
         }

         this._reportUnexpectedChar(var1, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
      }

      int var2 = this._inputPtr;
      if (var2 >= this._inputEnd) {
         this._minorState = 4;
         return this._currToken = JsonToken.NOT_AVAILABLE;
      } else {
         var1 = this._inputBuffer[var2];
         this._inputPtr = var2 + 1;
         if (var1 <= 32) {
            var1 = this._skipWS(var1);
            if (var1 <= 0) {
               this._minorState = 4;
               return this._currToken;
            }
         }

         this._updateTokenLocation();
         if (var1 != 34) {
            return var1 == 125 && JsonParser$Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) ? this._closeObjectScope() : this._handleOddName(var1);
         } else {
            if (this._inputPtr + 13 <= this._inputEnd) {
               String var3 = this._fastParseName();
               if (var3 != null) {
                  return this._fieldComplete(var3);
               }
            }

            return this._parseEscapedName(0, 0, 0);
         }
      }
   }

   private final JsonToken _startValue(int var1) throws IOException {
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         if (var1 <= 0) {
            this._minorState = 12;
            return this._currToken;
         }
      }

      this._updateTokenLocation();
      if (var1 == 34) {
         return this._startString();
      } else {
         switch(var1) {
         case 35:
            return this._finishHashComment(12);
         case 45:
            return this._startNegativeNumber();
         case 47:
            return this._startSlashComment(12);
         case 48:
            return this._startNumberLeadingZero();
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
            return this._startPositiveNumber(var1);
         case 91:
            return this._startArrayScope();
         case 93:
            return this._closeArrayScope();
         case 102:
            return this._startFalseToken();
         case 110:
            return this._startNullToken();
         case 116:
            return this._startTrueToken();
         case 123:
            return this._startObjectScope();
         case 125:
            return this._closeObjectScope();
         default:
            return this._startUnexpectedValue(false, var1);
         }
      }
   }

   private final JsonToken _startValueExpectComma(int var1) throws IOException {
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         if (var1 <= 0) {
            this._minorState = 13;
            return this._currToken;
         }
      }

      if (var1 != 44) {
         if (var1 == 93) {
            return this._closeArrayScope();
         }

         if (var1 == 125) {
            return this._closeObjectScope();
         }

         if (var1 == 47) {
            return this._startSlashComment(13);
         }

         if (var1 == 35) {
            return this._finishHashComment(13);
         }

         this._reportUnexpectedChar(var1, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
      }

      int var2 = this._inputPtr;
      if (var2 >= this._inputEnd) {
         this._minorState = 15;
         return this._currToken = JsonToken.NOT_AVAILABLE;
      } else {
         var1 = this._inputBuffer[var2];
         this._inputPtr = var2 + 1;
         if (var1 <= 32) {
            var1 = this._skipWS(var1);
            if (var1 <= 0) {
               this._minorState = 15;
               return this._currToken;
            }
         }

         this._updateTokenLocation();
         if (var1 == 34) {
            return this._startString();
         } else {
            switch(var1) {
            case 35:
               return this._finishHashComment(15);
            case 45:
               return this._startNegativeNumber();
            case 47:
               return this._startSlashComment(15);
            case 48:
               return this._startNumberLeadingZero();
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
               return this._startPositiveNumber(var1);
            case 91:
               return this._startArrayScope();
            case 93:
               if (this.isEnabled(JsonParser$Feature.ALLOW_TRAILING_COMMA)) {
                  return this._closeArrayScope();
               }
               break;
            case 102:
               return this._startFalseToken();
            case 110:
               return this._startNullToken();
            case 116:
               return this._startTrueToken();
            case 123:
               return this._startObjectScope();
            case 125:
               if (this.isEnabled(JsonParser$Feature.ALLOW_TRAILING_COMMA)) {
                  return this._closeObjectScope();
               }
            }

            return this._startUnexpectedValue(true, var1);
         }
      }
   }

   private final JsonToken _startValueExpectColon(int var1) throws IOException {
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         if (var1 <= 0) {
            this._minorState = 14;
            return this._currToken;
         }
      }

      if (var1 != 58) {
         if (var1 == 47) {
            return this._startSlashComment(14);
         }

         if (var1 == 35) {
            return this._finishHashComment(14);
         }

         this._reportUnexpectedChar(var1, "was expecting a colon to separate field name and value");
      }

      int var2 = this._inputPtr;
      if (var2 >= this._inputEnd) {
         this._minorState = 12;
         return this._currToken = JsonToken.NOT_AVAILABLE;
      } else {
         var1 = this._inputBuffer[var2];
         this._inputPtr = var2 + 1;
         if (var1 <= 32) {
            var1 = this._skipWS(var1);
            if (var1 <= 0) {
               this._minorState = 12;
               return this._currToken;
            }
         }

         this._updateTokenLocation();
         if (var1 == 34) {
            return this._startString();
         } else {
            switch(var1) {
            case 35:
               return this._finishHashComment(12);
            case 45:
               return this._startNegativeNumber();
            case 47:
               return this._startSlashComment(12);
            case 48:
               return this._startNumberLeadingZero();
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
               return this._startPositiveNumber(var1);
            case 91:
               return this._startArrayScope();
            case 102:
               return this._startFalseToken();
            case 110:
               return this._startNullToken();
            case 116:
               return this._startTrueToken();
            case 123:
               return this._startObjectScope();
            default:
               return this._startUnexpectedValue(false, var1);
            }
         }
      }
   }

   private final JsonToken _startValueAfterComma(int var1) throws IOException {
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         if (var1 <= 0) {
            this._minorState = 15;
            return this._currToken;
         }
      }

      this._updateTokenLocation();
      if (var1 == 34) {
         return this._startString();
      } else {
         switch(var1) {
         case 35:
            return this._finishHashComment(15);
         case 45:
            return this._startNegativeNumber();
         case 47:
            return this._startSlashComment(15);
         case 48:
            return this._startNumberLeadingZero();
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
            return this._startPositiveNumber(var1);
         case 91:
            return this._startArrayScope();
         case 93:
            if (this.isEnabled(JsonParser$Feature.ALLOW_TRAILING_COMMA)) {
               return this._closeArrayScope();
            }
            break;
         case 102:
            return this._startFalseToken();
         case 110:
            return this._startNullToken();
         case 116:
            return this._startTrueToken();
         case 123:
            return this._startObjectScope();
         case 125:
            if (this.isEnabled(JsonParser$Feature.ALLOW_TRAILING_COMMA)) {
               return this._closeObjectScope();
            }
         }

         return this._startUnexpectedValue(true, var1);
      }
   }

   protected JsonToken _startUnexpectedValue(boolean var1, int var2) throws IOException {
      switch(var2) {
      case 39:
         if (this.isEnabled(JsonParser$Feature.ALLOW_SINGLE_QUOTES)) {
            return this._startAposString();
         }
         break;
      case 43:
         return this._finishNonStdToken(2, 1);
      case 73:
         return this._finishNonStdToken(1, 1);
      case 78:
         return this._finishNonStdToken(0, 1);
      case 93:
         if (!this._parsingContext.inArray()) {
            break;
         }
      case 44:
         if (this.isEnabled(JsonParser$Feature.ALLOW_MISSING_VALUES)) {
            --this._inputPtr;
            return this._valueComplete(JsonToken.VALUE_NULL);
         }
      case 125:
      }

      this._reportUnexpectedChar(var2, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
      return null;
   }

   private final int _skipWS(int var1) throws IOException {
      do {
         if (var1 != 32) {
            if (var1 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var1 == 13) {
               ++this._currInputRowAlt;
               this._currInputRowStart = this._inputPtr;
            } else if (var1 != 9) {
               this._throwInvalidSpace(var1);
            }
         }

         if (this._inputPtr >= this._inputEnd) {
            this._currToken = JsonToken.NOT_AVAILABLE;
            return 0;
         }

         var1 = this._inputBuffer[this._inputPtr++] & 255;
      } while(var1 <= 32);

      return var1;
   }

   private final JsonToken _startSlashComment(int var1) throws IOException {
      if (!JsonParser$Feature.ALLOW_COMMENTS.enabledIn(this._features)) {
         this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
      }

      if (this._inputPtr >= this._inputEnd) {
         this._pending32 = var1;
         this._minorState = 51;
         return this._currToken = JsonToken.NOT_AVAILABLE;
      } else {
         byte var2 = this._inputBuffer[this._inputPtr++];
         if (var2 == 42) {
            return this._finishCComment(var1, false);
         } else if (var2 == 47) {
            return this._finishCppComment(var1);
         } else {
            this._reportUnexpectedChar(var2 & 255, "was expecting either '*' or '/' for a comment");
            return null;
         }
      }
   }

   private final JsonToken _finishHashComment(int var1) throws IOException {
      if (!JsonParser$Feature.ALLOW_YAML_COMMENTS.enabledIn(this._features)) {
         this._reportUnexpectedChar(35, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_YAML_COMMENTS' not enabled for parser)");
      }

      while(true) {
         int var2;
         do {
            if (this._inputPtr >= this._inputEnd) {
               this._minorState = 55;
               this._pending32 = var1;
               return this._currToken = JsonToken.NOT_AVAILABLE;
            }

            var2 = this._inputBuffer[this._inputPtr++] & 255;
         } while(var2 >= 32);

         if (var2 == 10) {
            ++this._currInputRow;
            this._currInputRowStart = this._inputPtr;
            break;
         }

         if (var2 == 13) {
            ++this._currInputRowAlt;
            this._currInputRowStart = this._inputPtr;
            break;
         }

         if (var2 != 9) {
            this._throwInvalidSpace(var2);
         }
      }

      return this._startAfterComment(var1);
   }

   private final JsonToken _finishCppComment(int var1) throws IOException {
      while(this._inputPtr < this._inputEnd) {
         int var2 = this._inputBuffer[this._inputPtr++] & 255;
         if (var2 < 32) {
            if (var2 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else {
               if (var2 != 13) {
                  if (var2 != 9) {
                     this._throwInvalidSpace(var2);
                  }
                  continue;
               }

               ++this._currInputRowAlt;
               this._currInputRowStart = this._inputPtr;
            }

            return this._startAfterComment(var1);
         }
      }

      this._minorState = 54;
      this._pending32 = var1;
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   private final JsonToken _finishCComment(int var1, boolean var2) throws IOException {
      while(this._inputPtr < this._inputEnd) {
         int var3 = this._inputBuffer[this._inputPtr++] & 255;
         if (var3 < 32) {
            if (var3 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var3 == 13) {
               ++this._currInputRowAlt;
               this._currInputRowStart = this._inputPtr;
            } else if (var3 != 9) {
               this._throwInvalidSpace(var3);
            }
         } else {
            if (var3 == 42) {
               var2 = true;
               continue;
            }

            if (var3 == 47 && var2) {
               return this._startAfterComment(var1);
            }
         }

         var2 = false;
      }

      this._minorState = var2 ? 52 : 53;
      this._pending32 = var1;
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   private final JsonToken _startAfterComment(int var1) throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._minorState = var1;
         return this._currToken = JsonToken.NOT_AVAILABLE;
      } else {
         int var2 = this._inputBuffer[this._inputPtr++] & 255;
         switch(var1) {
         case 4:
            return this._startFieldName(var2);
         case 5:
            return this._startFieldNameAfterComma(var2);
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         default:
            VersionUtil.throwInternal();
            return null;
         case 12:
            return this._startValue(var2);
         case 13:
            return this._startValueExpectComma(var2);
         case 14:
            return this._startValueExpectColon(var2);
         case 15:
            return this._startValueAfterComma(var2);
         }
      }
   }

   protected JsonToken _startFalseToken() throws IOException {
      int var1 = this._inputPtr;
      if (var1 + 4 < this._inputEnd) {
         byte[] var2 = this._inputBuffer;
         if (var2[var1++] == 97 && var2[var1++] == 108 && var2[var1++] == 115 && var2[var1++] == 101) {
            int var3 = var2[var1] & 255;
            if (var3 < 48 || var3 == 93 || var3 == 125) {
               this._inputPtr = var1;
               return this._valueComplete(JsonToken.VALUE_FALSE);
            }
         }
      }

      this._minorState = 18;
      return this._finishKeywordToken("false", 1, JsonToken.VALUE_FALSE);
   }

   protected JsonToken _startTrueToken() throws IOException {
      int var1 = this._inputPtr;
      if (var1 + 3 < this._inputEnd) {
         byte[] var2 = this._inputBuffer;
         if (var2[var1++] == 114 && var2[var1++] == 117 && var2[var1++] == 101) {
            int var3 = var2[var1] & 255;
            if (var3 < 48 || var3 == 93 || var3 == 125) {
               this._inputPtr = var1;
               return this._valueComplete(JsonToken.VALUE_TRUE);
            }
         }
      }

      this._minorState = 17;
      return this._finishKeywordToken("true", 1, JsonToken.VALUE_TRUE);
   }

   protected JsonToken _startNullToken() throws IOException {
      int var1 = this._inputPtr;
      if (var1 + 3 < this._inputEnd) {
         byte[] var2 = this._inputBuffer;
         if (var2[var1++] == 117 && var2[var1++] == 108 && var2[var1++] == 108) {
            int var3 = var2[var1] & 255;
            if (var3 < 48 || var3 == 93 || var3 == 125) {
               this._inputPtr = var1;
               return this._valueComplete(JsonToken.VALUE_NULL);
            }
         }
      }

      this._minorState = 16;
      return this._finishKeywordToken("null", 1, JsonToken.VALUE_NULL);
   }

   protected JsonToken _finishKeywordToken(String var1, int var2, JsonToken var3) throws IOException {
      int var4 = var1.length();

      while(this._inputPtr < this._inputEnd) {
         byte var5 = this._inputBuffer[this._inputPtr];
         if (var2 == var4) {
            if (var5 < 48 || var5 == 93 || var5 == 125) {
               return this._valueComplete(var3);
            }
         } else if (var5 == var1.charAt(var2)) {
            ++var2;
            ++this._inputPtr;
            continue;
         }

         this._minorState = 50;
         this._textBuffer.resetWithCopy((String)var1, 0, var2);
         return this._finishErrorToken();
      }

      this._pending32 = var2;
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   protected JsonToken _finishKeywordTokenWithEOF(String var1, int var2, JsonToken var3) throws IOException {
      if (var2 == var1.length()) {
         return this._currToken = var3;
      } else {
         this._textBuffer.resetWithCopy((String)var1, 0, var2);
         return this._finishErrorTokenWithEOF();
      }
   }

   protected JsonToken _finishNonStdToken(int var1, int var2) throws IOException {
      String var3 = this._nonStdToken(var1);
      int var4 = var3.length();

      while(this._inputPtr < this._inputEnd) {
         byte var5 = this._inputBuffer[this._inputPtr];
         if (var2 == var4) {
            if (var5 < 48 || var5 == 93 || var5 == 125) {
               return this._valueNonStdNumberComplete(var1);
            }
         } else if (var5 == var3.charAt(var2)) {
            ++var2;
            ++this._inputPtr;
            continue;
         }

         this._minorState = 50;
         this._textBuffer.resetWithCopy((String)var3, 0, var2);
         return this._finishErrorToken();
      }

      this._nonStdTokenType = var1;
      this._pending32 = var2;
      this._minorState = 19;
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   protected JsonToken _finishNonStdTokenWithEOF(int var1, int var2) throws IOException {
      String var3 = this._nonStdToken(var1);
      if (var2 == var3.length()) {
         return this._valueNonStdNumberComplete(var1);
      } else {
         this._textBuffer.resetWithCopy((String)var3, 0, var2);
         return this._finishErrorTokenWithEOF();
      }
   }

   protected JsonToken _finishErrorToken() throws IOException {
      while(true) {
         if (this._inputPtr < this._inputEnd) {
            byte var1 = this._inputBuffer[this._inputPtr++];
            char var2 = (char)var1;
            if (Character.isJavaIdentifierPart(var2)) {
               this._textBuffer.append(var2);
               if (this._textBuffer.size() < 256) {
                  continue;
               }
            }

            return this._reportErrorToken(this._textBuffer.contentsAsString());
         }

         return this._currToken = JsonToken.NOT_AVAILABLE;
      }
   }

   protected JsonToken _finishErrorTokenWithEOF() throws IOException {
      return this._reportErrorToken(this._textBuffer.contentsAsString());
   }

   protected JsonToken _reportErrorToken(String var1) throws IOException {
      this._reportError("Unrecognized token '%s': was expecting %s", this._textBuffer.contentsAsString(), "'null', 'true' or 'false'");
      return JsonToken.NOT_AVAILABLE;
   }

   protected JsonToken _startPositiveNumber(int var1) throws IOException {
      this._numberNegative = false;
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      var2[0] = (char)var1;
      if (this._inputPtr >= this._inputEnd) {
         this._minorState = 26;
         this._textBuffer.setCurrentLength(1);
         return this._currToken = JsonToken.NOT_AVAILABLE;
      } else {
         int var3 = 1;
         var1 = this._inputBuffer[this._inputPtr] & 255;

         while(true) {
            if (var1 < 48) {
               if (var1 == 46) {
                  this._intLength = var3;
                  ++this._inputPtr;
                  return this._startFloat(var2, var3, var1);
               }
               break;
            }

            if (var1 > 57) {
               if (var1 != 101 && var1 != 69) {
                  break;
               }

               this._intLength = var3;
               ++this._inputPtr;
               return this._startFloat(var2, var3, var1);
            }

            if (var3 >= var2.length) {
               var2 = this._textBuffer.expandCurrentSegment();
            }

            var2[var3++] = (char)var1;
            if (++this._inputPtr >= this._inputEnd) {
               this._minorState = 26;
               this._textBuffer.setCurrentLength(var3);
               return this._currToken = JsonToken.NOT_AVAILABLE;
            }

            var1 = this._inputBuffer[this._inputPtr] & 255;
         }

         this._intLength = var3;
         this._textBuffer.setCurrentLength(var3);
         return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
      }
   }

   protected JsonToken _startNegativeNumber() throws IOException {
      this._numberNegative = true;
      if (this._inputPtr >= this._inputEnd) {
         this._minorState = 23;
         return this._currToken = JsonToken.NOT_AVAILABLE;
      } else {
         int var1 = this._inputBuffer[this._inputPtr++] & 255;
         if (var1 <= 48) {
            if (var1 == 48) {
               return this._finishNumberLeadingNegZeroes();
            }

            this.reportUnexpectedNumberChar(var1, "expected digit (0-9) to follow minus sign, for valid numeric value");
         } else if (var1 > 57) {
            if (var1 == 73) {
               return this._finishNonStdToken(3, 2);
            }

            this.reportUnexpectedNumberChar(var1, "expected digit (0-9) to follow minus sign, for valid numeric value");
         }

         char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
         var2[0] = '-';
         var2[1] = (char)var1;
         if (this._inputPtr >= this._inputEnd) {
            this._minorState = 26;
            this._textBuffer.setCurrentLength(2);
            this._intLength = 1;
            return this._currToken = JsonToken.NOT_AVAILABLE;
         } else {
            var1 = this._inputBuffer[this._inputPtr];
            int var3 = 2;

            while(true) {
               if (var1 < 48) {
                  if (var1 == 46) {
                     this._intLength = var3 - 1;
                     ++this._inputPtr;
                     return this._startFloat(var2, var3, var1);
                  }
               } else {
                  if (var1 <= 57) {
                     if (var3 >= var2.length) {
                        var2 = this._textBuffer.expandCurrentSegment();
                     }

                     var2[var3++] = (char)var1;
                     if (++this._inputPtr >= this._inputEnd) {
                        this._minorState = 26;
                        this._textBuffer.setCurrentLength(var3);
                        return this._currToken = JsonToken.NOT_AVAILABLE;
                     }

                     var1 = this._inputBuffer[this._inputPtr] & 255;
                     continue;
                  }

                  if (var1 == 101 || var1 == 69) {
                     this._intLength = var3 - 1;
                     ++this._inputPtr;
                     return this._startFloat(var2, var3, var1);
                  }
               }

               this._intLength = var3 - 1;
               this._textBuffer.setCurrentLength(var3);
               return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
            }
         }
      }
   }

   protected JsonToken _startNumberLeadingZero() throws IOException {
      int var1 = this._inputPtr;
      if (var1 >= this._inputEnd) {
         this._minorState = 24;
         return this._currToken = JsonToken.NOT_AVAILABLE;
      } else {
         int var2 = this._inputBuffer[var1++] & 255;
         char[] var3;
         if (var2 < 48) {
            if (var2 == 46) {
               this._inputPtr = var1;
               this._intLength = 1;
               var3 = this._textBuffer.emptyAndGetCurrentSegment();
               var3[0] = '0';
               return this._startFloat(var3, 1, var2);
            }
         } else {
            if (var2 <= 57) {
               return this._finishNumberLeadingZeroes();
            }

            if (var2 == 101 || var2 == 69) {
               this._inputPtr = var1;
               this._intLength = 1;
               var3 = this._textBuffer.emptyAndGetCurrentSegment();
               var3[0] = '0';
               return this._startFloat(var3, 1, var2);
            }

            if (var2 != 93 && var2 != 125) {
               this.reportUnexpectedNumberChar(var2, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
         }

         return this._valueCompleteInt(0, "0");
      }
   }

   protected JsonToken _finishNumberMinus(int var1) throws IOException {
      if (var1 <= 48) {
         if (var1 == 48) {
            return this._finishNumberLeadingNegZeroes();
         }

         this.reportUnexpectedNumberChar(var1, "expected digit (0-9) to follow minus sign, for valid numeric value");
      } else if (var1 > 57) {
         if (var1 == 73) {
            return this._finishNonStdToken(3, 2);
         }

         this.reportUnexpectedNumberChar(var1, "expected digit (0-9) to follow minus sign, for valid numeric value");
      }

      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      var2[0] = '-';
      var2[1] = (char)var1;
      this._intLength = 1;
      return this._finishNumberIntegralPart(var2, 2);
   }

   protected JsonToken _finishNumberLeadingZeroes() throws IOException {
      while(this._inputPtr < this._inputEnd) {
         int var1 = this._inputBuffer[this._inputPtr++] & 255;
         char[] var2;
         if (var1 < 48) {
            if (var1 == 46) {
               var2 = this._textBuffer.emptyAndGetCurrentSegment();
               var2[0] = '0';
               this._intLength = 1;
               return this._startFloat(var2, 1, var1);
            }
         } else {
            if (var1 <= 57) {
               if (!this.isEnabled(JsonParser$Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
                  this.reportInvalidNumber("Leading zeroes not allowed");
               }

               if (var1 == 48) {
                  continue;
               }

               var2 = this._textBuffer.emptyAndGetCurrentSegment();
               var2[0] = (char)var1;
               this._intLength = 1;
               return this._finishNumberIntegralPart(var2, 1);
            }

            if (var1 == 101 || var1 == 69) {
               var2 = this._textBuffer.emptyAndGetCurrentSegment();
               var2[0] = '0';
               this._intLength = 1;
               return this._startFloat(var2, 1, var1);
            }

            if (var1 != 93 && var1 != 125) {
               this.reportUnexpectedNumberChar(var1, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
         }

         --this._inputPtr;
         return this._valueCompleteInt(0, "0");
      }

      this._minorState = 24;
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   protected JsonToken _finishNumberLeadingNegZeroes() throws IOException {
      while(this._inputPtr < this._inputEnd) {
         int var1 = this._inputBuffer[this._inputPtr++] & 255;
         char[] var2;
         if (var1 < 48) {
            if (var1 == 46) {
               var2 = this._textBuffer.emptyAndGetCurrentSegment();
               var2[0] = '-';
               var2[1] = '0';
               this._intLength = 1;
               return this._startFloat(var2, 2, var1);
            }
         } else {
            if (var1 <= 57) {
               if (!this.isEnabled(JsonParser$Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
                  this.reportInvalidNumber("Leading zeroes not allowed");
               }

               if (var1 == 48) {
                  continue;
               }

               var2 = this._textBuffer.emptyAndGetCurrentSegment();
               var2[0] = '-';
               var2[1] = (char)var1;
               this._intLength = 1;
               return this._finishNumberIntegralPart(var2, 2);
            }

            if (var1 == 101 || var1 == 69) {
               var2 = this._textBuffer.emptyAndGetCurrentSegment();
               var2[0] = '-';
               var2[1] = '0';
               this._intLength = 1;
               return this._startFloat(var2, 2, var1);
            }

            if (var1 != 93 && var1 != 125) {
               this.reportUnexpectedNumberChar(var1, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
         }

         --this._inputPtr;
         return this._valueCompleteInt(0, "0");
      }

      this._minorState = 25;
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   protected JsonToken _finishNumberIntegralPart(char[] var1, int var2) throws IOException {
      int var3 = this._numberNegative ? -1 : 0;

      while(this._inputPtr < this._inputEnd) {
         int var4 = this._inputBuffer[this._inputPtr] & 255;
         if (var4 < 48) {
            if (var4 == 46) {
               this._intLength = var2 + var3;
               ++this._inputPtr;
               return this._startFloat(var1, var2, var4);
            }
         } else {
            if (var4 <= 57) {
               ++this._inputPtr;
               if (var2 >= var1.length) {
                  var1 = this._textBuffer.expandCurrentSegment();
               }

               var1[var2++] = (char)var4;
               continue;
            }

            if (var4 == 101 || var4 == 69) {
               this._intLength = var2 + var3;
               ++this._inputPtr;
               return this._startFloat(var1, var2, var4);
            }
         }

         this._intLength = var2 + var3;
         this._textBuffer.setCurrentLength(var2);
         return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
      }

      this._minorState = 26;
      this._textBuffer.setCurrentLength(var2);
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   protected JsonToken _startFloat(char[] var1, int var2, int var3) throws IOException {
      int var4 = 0;
      byte var6;
      if (var3 == 46) {
         if (var2 >= var1.length) {
            var1 = this._textBuffer.expandCurrentSegment();
         }

         var1[var2++] = '.';

         while(true) {
            if (this._inputPtr >= this._inputEnd) {
               this._textBuffer.setCurrentLength(var2);
               this._minorState = 30;
               this._fractLength = var4;
               return this._currToken = JsonToken.NOT_AVAILABLE;
            }

            var6 = this._inputBuffer[this._inputPtr++];
            if (var6 < 48 || var6 > 57) {
               var3 = var6 & 255;
               if (var4 == 0) {
                  this.reportUnexpectedNumberChar(var3, "Decimal point not followed by a digit");
               }
               break;
            }

            if (var2 >= var1.length) {
               var1 = this._textBuffer.expandCurrentSegment();
            }

            var1[var2++] = (char)var6;
            ++var4;
         }
      }

      this._fractLength = var4;
      int var5 = 0;
      if (var3 == 101 || var3 == 69) {
         if (var2 >= var1.length) {
            var1 = this._textBuffer.expandCurrentSegment();
         }

         var1[var2++] = (char)var3;
         if (this._inputPtr >= this._inputEnd) {
            this._textBuffer.setCurrentLength(var2);
            this._minorState = 31;
            this._expLength = 0;
            return this._currToken = JsonToken.NOT_AVAILABLE;
         }

         var6 = this._inputBuffer[this._inputPtr++];
         if (var6 == 45 || var6 == 43) {
            if (var2 >= var1.length) {
               var1 = this._textBuffer.expandCurrentSegment();
            }

            var1[var2++] = (char)var6;
            if (this._inputPtr >= this._inputEnd) {
               this._textBuffer.setCurrentLength(var2);
               this._minorState = 32;
               this._expLength = 0;
               return this._currToken = JsonToken.NOT_AVAILABLE;
            }

            var6 = this._inputBuffer[this._inputPtr++];
         }

         while(var6 >= 48 && var6 <= 57) {
            ++var5;
            if (var2 >= var1.length) {
               var1 = this._textBuffer.expandCurrentSegment();
            }

            var1[var2++] = (char)var6;
            if (this._inputPtr >= this._inputEnd) {
               this._textBuffer.setCurrentLength(var2);
               this._minorState = 32;
               this._expLength = var5;
               return this._currToken = JsonToken.NOT_AVAILABLE;
            }

            var6 = this._inputBuffer[this._inputPtr++];
         }

         var3 = var6 & 255;
         if (var5 == 0) {
            this.reportUnexpectedNumberChar(var3, "Exponent indicator not followed by a digit");
         }
      }

      --this._inputPtr;
      this._textBuffer.setCurrentLength(var2);
      this._expLength = var5;
      return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
   }

   protected JsonToken _finishFloatFraction() throws IOException {
      int var1 = this._fractLength;
      char[] var2 = this._textBuffer.getBufferWithoutReset();
      int var3 = this._textBuffer.getCurrentSegmentSize();

      byte var4;
      while((var4 = this._inputBuffer[this._inputPtr++]) >= 48 && var4 <= 57) {
         ++var1;
         if (var3 >= var2.length) {
            var2 = this._textBuffer.expandCurrentSegment();
         }

         var2[var3++] = (char)var4;
         if (this._inputPtr >= this._inputEnd) {
            this._textBuffer.setCurrentLength(var3);
            this._fractLength = var1;
            return JsonToken.NOT_AVAILABLE;
         }
      }

      if (var1 == 0) {
         this.reportUnexpectedNumberChar(var4, "Decimal point not followed by a digit");
      }

      this._fractLength = var1;
      this._textBuffer.setCurrentLength(var3);
      if (var4 != 101 && var4 != 69) {
         --this._inputPtr;
         this._textBuffer.setCurrentLength(var3);
         this._expLength = 0;
         return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
      } else {
         this._textBuffer.append((char)var4);
         this._expLength = 0;
         if (this._inputPtr >= this._inputEnd) {
            this._minorState = 31;
            return JsonToken.NOT_AVAILABLE;
         } else {
            this._minorState = 32;
            return this._finishFloatExponent(true, this._inputBuffer[this._inputPtr++] & 255);
         }
      }
   }

   protected JsonToken _finishFloatExponent(boolean var1, int var2) throws IOException {
      if (var1) {
         this._minorState = 32;
         if (var2 == 45 || var2 == 43) {
            this._textBuffer.append((char)var2);
            if (this._inputPtr >= this._inputEnd) {
               this._minorState = 32;
               this._expLength = 0;
               return JsonToken.NOT_AVAILABLE;
            }

            var2 = this._inputBuffer[this._inputPtr++];
         }
      }

      char[] var3 = this._textBuffer.getBufferWithoutReset();
      int var4 = this._textBuffer.getCurrentSegmentSize();

      int var5;
      for(var5 = this._expLength; var2 >= 48 && var2 <= 57; var2 = this._inputBuffer[this._inputPtr++]) {
         ++var5;
         if (var4 >= var3.length) {
            var3 = this._textBuffer.expandCurrentSegment();
         }

         var3[var4++] = (char)var2;
         if (this._inputPtr >= this._inputEnd) {
            this._textBuffer.setCurrentLength(var4);
            this._expLength = var5;
            return JsonToken.NOT_AVAILABLE;
         }
      }

      var2 &= 255;
      if (var5 == 0) {
         this.reportUnexpectedNumberChar(var2, "Exponent indicator not followed by a digit");
      }

      --this._inputPtr;
      this._textBuffer.setCurrentLength(var4);
      this._expLength = var5;
      return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
   }

   private final String _fastParseName() throws IOException {
      byte[] var1 = this._inputBuffer;
      int[] var2 = _icLatin1;
      int var3 = this._inputPtr;
      int var4 = var1[var3++] & 255;
      if (var2[var4] == 0) {
         int var5 = var1[var3++] & 255;
         if (var2[var5] == 0) {
            int var6 = var4 << 8 | var5;
            var5 = var1[var3++] & 255;
            if (var2[var5] == 0) {
               var6 = var6 << 8 | var5;
               var5 = var1[var3++] & 255;
               if (var2[var5] == 0) {
                  var6 = var6 << 8 | var5;
                  var5 = var1[var3++] & 255;
                  if (var2[var5] == 0) {
                     this._quad1 = var6;
                     return this._parseMediumName(var3, var5);
                  } else if (var5 == 34) {
                     this._inputPtr = var3;
                     return this._findName(var6, 4);
                  } else {
                     return null;
                  }
               } else if (var5 == 34) {
                  this._inputPtr = var3;
                  return this._findName(var6, 3);
               } else {
                  return null;
               }
            } else if (var5 == 34) {
               this._inputPtr = var3;
               return this._findName(var6, 2);
            } else {
               return null;
            }
         } else if (var5 == 34) {
            this._inputPtr = var3;
            return this._findName(var4, 1);
         } else {
            return null;
         }
      } else if (var4 == 34) {
         this._inputPtr = var3;
         return "";
      } else {
         return null;
      }
   }

   private final String _parseMediumName(int var1, int var2) throws IOException {
      byte[] var3 = this._inputBuffer;
      int[] var4 = _icLatin1;
      int var5 = var3[var1++] & 255;
      if (var4[var5] == 0) {
         var2 = var2 << 8 | var5;
         var5 = var3[var1++] & 255;
         if (var4[var5] == 0) {
            var2 = var2 << 8 | var5;
            var5 = var3[var1++] & 255;
            if (var4[var5] == 0) {
               var2 = var2 << 8 | var5;
               var5 = var3[var1++] & 255;
               if (var4[var5] == 0) {
                  return this._parseMediumName2(var1, var5, var2);
               } else if (var5 == 34) {
                  this._inputPtr = var1;
                  return this._findName(this._quad1, var2, 4);
               } else {
                  return null;
               }
            } else if (var5 == 34) {
               this._inputPtr = var1;
               return this._findName(this._quad1, var2, 3);
            } else {
               return null;
            }
         } else if (var5 == 34) {
            this._inputPtr = var1;
            return this._findName(this._quad1, var2, 2);
         } else {
            return null;
         }
      } else if (var5 == 34) {
         this._inputPtr = var1;
         return this._findName(this._quad1, var2, 1);
      } else {
         return null;
      }
   }

   private final String _parseMediumName2(int var1, int var2, int var3) throws IOException {
      byte[] var4 = this._inputBuffer;
      int[] var5 = _icLatin1;
      int var6 = var4[var1++] & 255;
      if (var5[var6] != 0) {
         if (var6 == 34) {
            this._inputPtr = var1;
            return this._findName(this._quad1, var3, var2, 1);
         } else {
            return null;
         }
      } else {
         var2 = var2 << 8 | var6;
         var6 = var4[var1++] & 255;
         if (var5[var6] != 0) {
            if (var6 == 34) {
               this._inputPtr = var1;
               return this._findName(this._quad1, var3, var2, 2);
            } else {
               return null;
            }
         } else {
            var2 = var2 << 8 | var6;
            var6 = var4[var1++] & 255;
            if (var5[var6] != 0) {
               if (var6 == 34) {
                  this._inputPtr = var1;
                  return this._findName(this._quad1, var3, var2, 3);
               } else {
                  return null;
               }
            } else {
               var2 = var2 << 8 | var6;
               var6 = var4[var1++] & 255;
               if (var6 == 34) {
                  this._inputPtr = var1;
                  return this._findName(this._quad1, var3, var2, 4);
               } else {
                  return null;
               }
            }
         }
      }
   }

   private final JsonToken _parseEscapedName(int var1, int var2, int var3) throws IOException {
      int[] var4 = this._quadBuffer;
      int[] var5 = _icLatin1;

      while(this._inputPtr < this._inputEnd) {
         int var6 = this._inputBuffer[this._inputPtr++] & 255;
         if (var5[var6] == 0) {
            if (var3 < 4) {
               ++var3;
               var2 = var2 << 8 | var6;
            } else {
               if (var1 >= var4.length) {
                  this._quadBuffer = var4 = growArrayBy(var4, var4.length);
               }

               var4[var1++] = var2;
               var2 = var6;
               var3 = 1;
            }
         } else {
            if (var6 == 34) {
               if (var3 > 0) {
                  if (var1 >= var4.length) {
                     this._quadBuffer = var4 = growArrayBy(var4, var4.length);
                  }

                  var4[var1++] = _padLastQuad(var2, var3);
               } else if (var1 == 0) {
                  return this._fieldComplete("");
               }

               String var7 = this._symbols.findName(var4, var1);
               if (var7 == null) {
                  var7 = this._addName(var4, var1, var3);
               }

               return this._fieldComplete(var7);
            }

            if (var6 != 92) {
               this._throwUnquotedSpace(var6, "name");
            } else {
               var6 = this._decodeCharEscape();
               if (var6 < 0) {
                  this._minorState = 8;
                  this._minorStateAfterSplit = 7;
                  this._quadLength = var1;
                  this._pending32 = var2;
                  this._pendingBytes = var3;
                  return this._currToken = JsonToken.NOT_AVAILABLE;
               }
            }

            if (var1 >= var4.length) {
               this._quadBuffer = var4 = growArrayBy(var4, var4.length);
            }

            if (var6 > 127) {
               if (var3 >= 4) {
                  var4[var1++] = var2;
                  var2 = 0;
                  var3 = 0;
               }

               if (var6 < 2048) {
                  var2 = var2 << 8 | 192 | var6 >> 6;
                  ++var3;
               } else {
                  var2 = var2 << 8 | 224 | var6 >> 12;
                  ++var3;
                  if (var3 >= 4) {
                     var4[var1++] = var2;
                     var2 = 0;
                     var3 = 0;
                  }

                  var2 = var2 << 8 | 128 | var6 >> 6 & 63;
                  ++var3;
               }

               var6 = 128 | var6 & 63;
            }

            if (var3 < 4) {
               ++var3;
               var2 = var2 << 8 | var6;
            } else {
               var4[var1++] = var2;
               var2 = var6;
               var3 = 1;
            }
         }
      }

      this._quadLength = var1;
      this._pending32 = var2;
      this._pendingBytes = var3;
      this._minorState = 7;
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   private JsonToken _handleOddName(int var1) throws IOException {
      switch(var1) {
      case 35:
         if (JsonParser$Feature.ALLOW_YAML_COMMENTS.enabledIn(this._features)) {
            return this._finishHashComment(4);
         }
         break;
      case 39:
         if (this.isEnabled(JsonParser$Feature.ALLOW_SINGLE_QUOTES)) {
            return this._finishAposName(0, 0, 0);
         }
         break;
      case 47:
         return this._startSlashComment(4);
      case 93:
         return this._closeArrayScope();
      }

      if (!this.isEnabled(JsonParser$Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
         char var2 = (char)var1;
         this._reportUnexpectedChar(var2, "was expecting double-quote to start field name");
      }

      int[] var3 = CharTypes.getInputCodeUtf8JsNames();
      if (var3[var1] != 0) {
         this._reportUnexpectedChar(var1, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
      }

      return this._finishUnquotedName(0, var1, 1);
   }

   private JsonToken _finishUnquotedName(int var1, int var2, int var3) throws IOException {
      int[] var4 = this._quadBuffer;
      int[] var5 = CharTypes.getInputCodeUtf8JsNames();

      while(this._inputPtr < this._inputEnd) {
         int var6 = this._inputBuffer[this._inputPtr] & 255;
         if (var5[var6] != 0) {
            if (var3 > 0) {
               if (var1 >= var4.length) {
                  this._quadBuffer = var4 = growArrayBy(var4, var4.length);
               }

               var4[var1++] = var2;
            }

            String var7 = this._symbols.findName(var4, var1);
            if (var7 == null) {
               var7 = this._addName(var4, var1, var3);
            }

            return this._fieldComplete(var7);
         }

         ++this._inputPtr;
         if (var3 < 4) {
            ++var3;
            var2 = var2 << 8 | var6;
         } else {
            if (var1 >= var4.length) {
               this._quadBuffer = var4 = growArrayBy(var4, var4.length);
            }

            var4[var1++] = var2;
            var2 = var6;
            var3 = 1;
         }
      }

      this._quadLength = var1;
      this._pending32 = var2;
      this._pendingBytes = var3;
      this._minorState = 10;
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   private JsonToken _finishAposName(int var1, int var2, int var3) throws IOException {
      int[] var4 = this._quadBuffer;
      int[] var5 = _icLatin1;

      while(this._inputPtr < this._inputEnd) {
         int var6 = this._inputBuffer[this._inputPtr++] & 255;
         if (var6 == 39) {
            if (var3 > 0) {
               if (var1 >= var4.length) {
                  this._quadBuffer = var4 = growArrayBy(var4, var4.length);
               }

               var4[var1++] = _padLastQuad(var2, var3);
            } else if (var1 == 0) {
               return this._fieldComplete("");
            }

            String var7 = this._symbols.findName(var4, var1);
            if (var7 == null) {
               var7 = this._addName(var4, var1, var3);
            }

            return this._fieldComplete(var7);
         }

         if (var6 != 34 && var5[var6] != 0) {
            if (var6 != 92) {
               this._throwUnquotedSpace(var6, "name");
            } else {
               var6 = this._decodeCharEscape();
               if (var6 < 0) {
                  this._minorState = 8;
                  this._minorStateAfterSplit = 9;
                  this._quadLength = var1;
                  this._pending32 = var2;
                  this._pendingBytes = var3;
                  return this._currToken = JsonToken.NOT_AVAILABLE;
               }
            }

            if (var6 > 127) {
               if (var3 >= 4) {
                  if (var1 >= var4.length) {
                     this._quadBuffer = var4 = growArrayBy(var4, var4.length);
                  }

                  var4[var1++] = var2;
                  var2 = 0;
                  var3 = 0;
               }

               if (var6 < 2048) {
                  var2 = var2 << 8 | 192 | var6 >> 6;
                  ++var3;
               } else {
                  var2 = var2 << 8 | 224 | var6 >> 12;
                  ++var3;
                  if (var3 >= 4) {
                     if (var1 >= var4.length) {
                        this._quadBuffer = var4 = growArrayBy(var4, var4.length);
                     }

                     var4[var1++] = var2;
                     var2 = 0;
                     var3 = 0;
                  }

                  var2 = var2 << 8 | 128 | var6 >> 6 & 63;
                  ++var3;
               }

               var6 = 128 | var6 & 63;
            }
         }

         if (var3 < 4) {
            ++var3;
            var2 = var2 << 8 | var6;
         } else {
            if (var1 >= var4.length) {
               this._quadBuffer = var4 = growArrayBy(var4, var4.length);
            }

            var4[var1++] = var2;
            var2 = var6;
            var3 = 1;
         }
      }

      this._quadLength = var1;
      this._pending32 = var2;
      this._pendingBytes = var3;
      this._minorState = 9;
      return this._currToken = JsonToken.NOT_AVAILABLE;
   }

   protected final JsonToken _finishFieldWithEscape() throws IOException {
      int var1 = this._decodeSplitEscaped(this._quoted32, this._quotedDigits);
      if (var1 < 0) {
         this._minorState = 8;
         return JsonToken.NOT_AVAILABLE;
      } else {
         if (this._quadLength >= this._quadBuffer.length) {
            this._quadBuffer = growArrayBy(this._quadBuffer, 32);
         }

         int var2 = this._pending32;
         int var3 = this._pendingBytes;
         if (var1 > 127) {
            if (var3 >= 4) {
               this._quadBuffer[this._quadLength++] = var2;
               var2 = 0;
               var3 = 0;
            }

            if (var1 < 2048) {
               var2 = var2 << 8 | 192 | var1 >> 6;
               ++var3;
            } else {
               var2 = var2 << 8 | 224 | var1 >> 12;
               ++var3;
               if (var3 >= 4) {
                  this._quadBuffer[this._quadLength++] = var2;
                  var2 = 0;
                  var3 = 0;
               }

               var2 = var2 << 8 | 128 | var1 >> 6 & 63;
               ++var3;
            }

            var1 = 128 | var1 & 63;
         }

         if (var3 < 4) {
            ++var3;
            var2 = var2 << 8 | var1;
         } else {
            this._quadBuffer[this._quadLength++] = var2;
            var2 = var1;
            var3 = 1;
         }

         return this._minorStateAfterSplit == 9 ? this._finishAposName(this._quadLength, var2, var3) : this._parseEscapedName(this._quadLength, var2, var3);
      }
   }

   private int _decodeSplitEscaped(int var1, int var2) throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._quoted32 = var1;
         this._quotedDigits = var2;
         return -1;
      } else {
         byte var3 = this._inputBuffer[this._inputPtr++];
         if (var2 == -1) {
            switch(var3) {
            case 34:
            case 47:
            case 92:
               return var3;
            case 98:
               return 8;
            case 102:
               return 12;
            case 110:
               return 10;
            case 114:
               return 13;
            case 116:
               return 9;
            case 117:
               if (this._inputPtr >= this._inputEnd) {
                  this._quotedDigits = 0;
                  this._quoted32 = 0;
                  return -1;
               }

               var3 = this._inputBuffer[this._inputPtr++];
               var2 = 0;
               break;
            default:
               char var6 = (char)var3;
               return this._handleUnrecognizedCharacterEscape(var6);
            }
         }

         int var5 = var3 & 255;

         while(true) {
            int var4 = CharTypes.charToHex(var5);
            if (var4 < 0) {
               this._reportUnexpectedChar(var5, "expected a hex-digit for character escape sequence");
            }

            var1 = var1 << 4 | var4;
            ++var2;
            if (var2 == 4) {
               return var1;
            }

            if (this._inputPtr >= this._inputEnd) {
               this._quotedDigits = var2;
               this._quoted32 = var1;
               return -1;
            }

            var5 = this._inputBuffer[this._inputPtr++] & 255;
         }
      }
   }

   protected JsonToken _startString() throws IOException {
      int var1 = this._inputPtr;
      int var2 = 0;
      char[] var3 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var4 = _icUTF8;
      int var5 = Math.min(this._inputEnd, var1 + var3.length);

      int var7;
      for(byte[] var6 = this._inputBuffer; var1 < var5; var3[var2++] = (char)var7) {
         var7 = var6[var1] & 255;
         if (var4[var7] != 0) {
            if (var7 == 34) {
               this._inputPtr = var1 + 1;
               this._textBuffer.setCurrentLength(var2);
               return this._valueComplete(JsonToken.VALUE_STRING);
            }
            break;
         }

         ++var1;
      }

      this._textBuffer.setCurrentLength(var2);
      this._inputPtr = var1;
      return this._finishRegularString();
   }

   private final JsonToken _finishRegularString() throws IOException {
      int[] var2 = _icUTF8;
      byte[] var3 = this._inputBuffer;
      char[] var4 = this._textBuffer.getBufferWithoutReset();
      int var5 = this._textBuffer.getCurrentSegmentSize();
      int var6 = this._inputPtr;
      int var7 = this._inputEnd - 5;

      while(true) {
         while(var6 < this._inputEnd) {
            if (var5 >= var4.length) {
               var4 = this._textBuffer.finishCurrentSegment();
               var5 = 0;
            }

            int var1;
            for(int var8 = Math.min(this._inputEnd, var6 + (var4.length - var5)); var6 < var8; var4[var5++] = (char)var1) {
               var1 = var3[var6++] & 255;
               if (var2[var1] != 0) {
                  if (var1 == 34) {
                     this._inputPtr = var6;
                     this._textBuffer.setCurrentLength(var5);
                     return this._valueComplete(JsonToken.VALUE_STRING);
                  }

                  if (var6 >= var7) {
                     this._inputPtr = var6;
                     this._textBuffer.setCurrentLength(var5);
                     if (!this._decodeSplitMultiByte(var1, var2[var1], var6 < this._inputEnd)) {
                        this._minorStateAfterSplit = 40;
                        return this._currToken = JsonToken.NOT_AVAILABLE;
                     }

                     var4 = this._textBuffer.getBufferWithoutReset();
                     var5 = this._textBuffer.getCurrentSegmentSize();
                     var6 = this._inputPtr;
                  } else {
                     switch(var2[var1]) {
                     case 1:
                        this._inputPtr = var6;
                        var1 = this._decodeFastCharEscape();
                        var6 = this._inputPtr;
                        break;
                     case 2:
                        var1 = this._decodeUTF8_2(var1, this._inputBuffer[var6++]);
                        break;
                     case 3:
                        var1 = this._decodeUTF8_3(var1, this._inputBuffer[var6++], this._inputBuffer[var6++]);
                        break;
                     case 4:
                        var1 = this._decodeUTF8_4(var1, this._inputBuffer[var6++], this._inputBuffer[var6++], this._inputBuffer[var6++]);
                        var4[var5++] = (char)('\ud800' | var1 >> 10);
                        if (var5 >= var4.length) {
                           var4 = this._textBuffer.finishCurrentSegment();
                           var5 = 0;
                        }

                        var1 = '\udc00' | var1 & 1023;
                        break;
                     default:
                        if (var1 < 32) {
                           this._throwUnquotedSpace(var1, "string value");
                        } else {
                           this._reportInvalidChar(var1);
                        }
                     }

                     if (var5 >= var4.length) {
                        var4 = this._textBuffer.finishCurrentSegment();
                        var5 = 0;
                     }

                     var4[var5++] = (char)var1;
                  }
                  break;
               }
            }
         }

         this._inputPtr = var6;
         this._minorState = 40;
         this._textBuffer.setCurrentLength(var5);
         return this._currToken = JsonToken.NOT_AVAILABLE;
      }
   }

   protected JsonToken _startAposString() throws IOException {
      int var1 = this._inputPtr;
      int var2 = 0;
      char[] var3 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var4 = _icUTF8;
      int var5 = Math.min(this._inputEnd, var1 + var3.length);

      int var7;
      for(byte[] var6 = this._inputBuffer; var1 < var5; var3[var2++] = (char)var7) {
         var7 = var6[var1] & 255;
         if (var7 == 39) {
            this._inputPtr = var1 + 1;
            this._textBuffer.setCurrentLength(var2);
            return this._valueComplete(JsonToken.VALUE_STRING);
         }

         if (var4[var7] != 0) {
            break;
         }

         ++var1;
      }

      this._textBuffer.setCurrentLength(var2);
      this._inputPtr = var1;
      return this._finishAposString();
   }

   private final JsonToken _finishAposString() throws IOException {
      int[] var2 = _icUTF8;
      byte[] var3 = this._inputBuffer;
      char[] var4 = this._textBuffer.getBufferWithoutReset();
      int var5 = this._textBuffer.getCurrentSegmentSize();
      int var6 = this._inputPtr;
      int var7 = this._inputEnd - 5;

      while(true) {
         while(var6 < this._inputEnd) {
            if (var5 >= var4.length) {
               var4 = this._textBuffer.finishCurrentSegment();
               var5 = 0;
            }

            int var1;
            for(int var8 = Math.min(this._inputEnd, var6 + (var4.length - var5)); var6 < var8; var4[var5++] = (char)var1) {
               var1 = var3[var6++] & 255;
               if (var2[var1] != 0 && var1 != 34) {
                  if (var6 >= var7) {
                     this._inputPtr = var6;
                     this._textBuffer.setCurrentLength(var5);
                     if (!this._decodeSplitMultiByte(var1, var2[var1], var6 < this._inputEnd)) {
                        this._minorStateAfterSplit = 45;
                        return this._currToken = JsonToken.NOT_AVAILABLE;
                     }

                     var4 = this._textBuffer.getBufferWithoutReset();
                     var5 = this._textBuffer.getCurrentSegmentSize();
                     var6 = this._inputPtr;
                  } else {
                     switch(var2[var1]) {
                     case 1:
                        this._inputPtr = var6;
                        var1 = this._decodeFastCharEscape();
                        var6 = this._inputPtr;
                        break;
                     case 2:
                        var1 = this._decodeUTF8_2(var1, this._inputBuffer[var6++]);
                        break;
                     case 3:
                        var1 = this._decodeUTF8_3(var1, this._inputBuffer[var6++], this._inputBuffer[var6++]);
                        break;
                     case 4:
                        var1 = this._decodeUTF8_4(var1, this._inputBuffer[var6++], this._inputBuffer[var6++], this._inputBuffer[var6++]);
                        var4[var5++] = (char)('\ud800' | var1 >> 10);
                        if (var5 >= var4.length) {
                           var4 = this._textBuffer.finishCurrentSegment();
                           var5 = 0;
                        }

                        var1 = '\udc00' | var1 & 1023;
                        break;
                     default:
                        if (var1 < 32) {
                           this._throwUnquotedSpace(var1, "string value");
                        } else {
                           this._reportInvalidChar(var1);
                        }
                     }

                     if (var5 >= var4.length) {
                        var4 = this._textBuffer.finishCurrentSegment();
                        var5 = 0;
                     }

                     var4[var5++] = (char)var1;
                  }
                  break;
               }

               if (var1 == 39) {
                  this._inputPtr = var6;
                  this._textBuffer.setCurrentLength(var5);
                  return this._valueComplete(JsonToken.VALUE_STRING);
               }
            }
         }

         this._inputPtr = var6;
         this._minorState = 45;
         this._textBuffer.setCurrentLength(var5);
         return this._currToken = JsonToken.NOT_AVAILABLE;
      }
   }

   private final boolean _decodeSplitMultiByte(int var1, int var2, boolean var3) throws IOException {
      switch(var2) {
      case 1:
         var1 = this._decodeSplitEscaped(0, -1);
         if (var1 < 0) {
            this._minorState = 41;
            return false;
         }

         this._textBuffer.append((char)var1);
         return true;
      case 2:
         if (var3) {
            var1 = this._decodeUTF8_2(var1, this._inputBuffer[this._inputPtr++]);
            this._textBuffer.append((char)var1);
            return true;
         }

         this._minorState = 42;
         this._pending32 = var1;
         return false;
      case 3:
         var1 &= 15;
         if (var3) {
            return this._decodeSplitUTF8_3(var1, 1, this._inputBuffer[this._inputPtr++]);
         }

         this._minorState = 43;
         this._pending32 = var1;
         this._pendingBytes = 1;
         return false;
      case 4:
         var1 &= 7;
         if (var3) {
            return this._decodeSplitUTF8_4(var1, 1, this._inputBuffer[this._inputPtr++]);
         }

         this._pending32 = var1;
         this._pendingBytes = 1;
         this._minorState = 44;
         return false;
      default:
         if (var1 < 32) {
            this._throwUnquotedSpace(var1, "string value");
         } else {
            this._reportInvalidChar(var1);
         }

         this._textBuffer.append((char)var1);
         return true;
      }
   }

   private final boolean _decodeSplitUTF8_3(int var1, int var2, int var3) throws IOException {
      if (var2 == 1) {
         if ((var3 & 192) != 128) {
            this._reportInvalidOther(var3 & 255, this._inputPtr);
         }

         var1 = var1 << 6 | var3 & 63;
         if (this._inputPtr >= this._inputEnd) {
            this._minorState = 43;
            this._pending32 = var1;
            this._pendingBytes = 2;
            return false;
         }

         var3 = this._inputBuffer[this._inputPtr++];
      }

      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      this._textBuffer.append((char)(var1 << 6 | var3 & 63));
      return true;
   }

   private final boolean _decodeSplitUTF8_4(int var1, int var2, int var3) throws IOException {
      if (var2 == 1) {
         if ((var3 & 192) != 128) {
            this._reportInvalidOther(var3 & 255, this._inputPtr);
         }

         var1 = var1 << 6 | var3 & 63;
         if (this._inputPtr >= this._inputEnd) {
            this._minorState = 44;
            this._pending32 = var1;
            this._pendingBytes = 2;
            return false;
         }

         var2 = 2;
         var3 = this._inputBuffer[this._inputPtr++];
      }

      if (var2 == 2) {
         if ((var3 & 192) != 128) {
            this._reportInvalidOther(var3 & 255, this._inputPtr);
         }

         var1 = var1 << 6 | var3 & 63;
         if (this._inputPtr >= this._inputEnd) {
            this._minorState = 44;
            this._pending32 = var1;
            this._pendingBytes = 3;
            return false;
         }

         var3 = this._inputBuffer[this._inputPtr++];
      }

      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      int var4 = (var1 << 6 | var3 & 63) - 65536;
      this._textBuffer.append((char)('\ud800' | var4 >> 10));
      var4 = '\udc00' | var4 & 1023;
      this._textBuffer.append((char)var4);
      return true;
   }

   private final int _decodeCharEscape() throws IOException {
      int var1 = this._inputEnd - this._inputPtr;
      return var1 < 5 ? this._decodeSplitEscaped(0, -1) : this._decodeFastCharEscape();
   }

   private final int _decodeFastCharEscape() throws IOException {
      byte var1 = this._inputBuffer[this._inputPtr++];
      switch(var1) {
      case 34:
      case 47:
      case 92:
         return (char)var1;
      case 98:
         return 8;
      case 102:
         return 12;
      case 110:
         return 10;
      case 114:
         return 13;
      case 116:
         return 9;
      case 117:
         byte var2 = this._inputBuffer[this._inputPtr++];
         int var3 = CharTypes.charToHex(var2);
         int var4 = var3;
         if (var3 >= 0) {
            var2 = this._inputBuffer[this._inputPtr++];
            var3 = CharTypes.charToHex(var2);
            if (var3 >= 0) {
               var4 = var4 << 4 | var3;
               var2 = this._inputBuffer[this._inputPtr++];
               var3 = CharTypes.charToHex(var2);
               if (var3 >= 0) {
                  var4 = var4 << 4 | var3;
                  var2 = this._inputBuffer[this._inputPtr++];
                  var3 = CharTypes.charToHex(var2);
                  if (var3 >= 0) {
                     return var4 << 4 | var3;
                  }
               }
            }
         }

         this._reportUnexpectedChar(var2 & 255, "expected a hex-digit for character escape sequence");
         return -1;
      default:
         char var5 = (char)var1;
         return this._handleUnrecognizedCharacterEscape(var5);
      }
   }

   private final int _decodeUTF8_2(int var1, int var2) throws IOException {
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255, this._inputPtr);
      }

      return (var1 & 31) << 6 | var2 & 63;
   }

   private final int _decodeUTF8_3(int var1, int var2, int var3) throws IOException {
      var1 &= 15;
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255, this._inputPtr);
      }

      var1 = var1 << 6 | var2 & 63;
      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      return var1 << 6 | var3 & 63;
   }

   private final int _decodeUTF8_4(int var1, int var2, int var3, int var4) throws IOException {
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255, this._inputPtr);
      }

      var1 = (var1 & 7) << 6 | var2 & 63;
      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      var1 = var1 << 6 | var3 & 63;
      if ((var4 & 192) != 128) {
         this._reportInvalidOther(var4 & 255, this._inputPtr);
      }

      return (var1 << 6 | var4 & 63) - 65536;
   }

   public NonBlockingInputFeeder getNonBlockingInputFeeder() {
      return this.getNonBlockingInputFeeder();
   }
}
