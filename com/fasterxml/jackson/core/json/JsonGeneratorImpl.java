package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator$Feature;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;

public abstract class JsonGeneratorImpl extends GeneratorBase {
   protected static final int[] sOutputEscapes = CharTypes.get7BitOutputEscapes();
   protected final IOContext _ioContext;
   protected int[] _outputEscapes;
   protected int _maximumNonEscapedChar;
   protected CharacterEscapes _characterEscapes;
   protected SerializableString _rootValueSeparator;
   protected boolean _cfgUnqNames;

   public JsonGeneratorImpl(IOContext var1, int var2, ObjectCodec var3) {
      super(var2, var3);
      this._outputEscapes = sOutputEscapes;
      this._rootValueSeparator = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
      this._ioContext = var1;
      if (JsonGenerator$Feature.ESCAPE_NON_ASCII.enabledIn(var2)) {
         this._maximumNonEscapedChar = 127;
      }

      this._cfgUnqNames = !JsonGenerator$Feature.QUOTE_FIELD_NAMES.enabledIn(var2);
   }

   public Version version() {
      return VersionUtil.versionFor(this.getClass());
   }

   public JsonGenerator enable(JsonGenerator$Feature var1) {
      super.enable(var1);
      if (var1 == JsonGenerator$Feature.QUOTE_FIELD_NAMES) {
         this._cfgUnqNames = false;
      }

      return this;
   }

   public JsonGenerator disable(JsonGenerator$Feature var1) {
      super.disable(var1);
      if (var1 == JsonGenerator$Feature.QUOTE_FIELD_NAMES) {
         this._cfgUnqNames = true;
      }

      return this;
   }

   protected void _checkStdFeatureChanges(int var1, int var2) {
      super._checkStdFeatureChanges(var1, var2);
      this._cfgUnqNames = !JsonGenerator$Feature.QUOTE_FIELD_NAMES.enabledIn(var1);
   }

   public JsonGenerator setHighestNonEscapedChar(int var1) {
      this._maximumNonEscapedChar = var1 < 0 ? 0 : var1;
      return this;
   }

   public int getHighestEscapedChar() {
      return this._maximumNonEscapedChar;
   }

   public JsonGenerator setCharacterEscapes(CharacterEscapes var1) {
      this._characterEscapes = var1;
      if (var1 == null) {
         this._outputEscapes = sOutputEscapes;
      } else {
         this._outputEscapes = var1.getEscapeCodesForAscii();
      }

      return this;
   }

   public CharacterEscapes getCharacterEscapes() {
      return this._characterEscapes;
   }

   public JsonGenerator setRootValueSeparator(SerializableString var1) {
      this._rootValueSeparator = var1;
      return this;
   }

   public final void writeStringField(String var1, String var2) throws IOException {
      this.writeFieldName(var1);
      this.writeString(var2);
   }

   protected void _verifyPrettyValueWrite(String var1, int var2) throws IOException {
      switch(var2) {
      case 0:
         if (this._writeContext.inArray()) {
            this._cfgPrettyPrinter.beforeArrayValues(this);
         } else if (this._writeContext.inObject()) {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
         }
         break;
      case 1:
         this._cfgPrettyPrinter.writeArrayValueSeparator(this);
         break;
      case 2:
         this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
         break;
      case 3:
         this._cfgPrettyPrinter.writeRootValueSeparator(this);
         break;
      case 4:
      default:
         this._throwInternal();
         break;
      case 5:
         this._reportCantWriteValueExpectName(var1);
      }

   }

   protected void _reportCantWriteValueExpectName(String var1) throws IOException {
      this._reportError(String.format("Can not %s, expecting field name (context: %s)", var1, this._writeContext.typeDesc()));
   }
}
