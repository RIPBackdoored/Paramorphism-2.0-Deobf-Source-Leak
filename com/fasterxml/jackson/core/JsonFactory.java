package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.DataOutputAsStream;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.InputDecorator;
import com.fasterxml.jackson.core.io.OutputDecorator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import com.fasterxml.jackson.core.json.async.NonBlockingJsonParser;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.BufferRecyclers;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import java.io.CharArrayReader;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;

public class JsonFactory implements Versioned, Serializable {
   private static final long serialVersionUID = 1L;
   public static final String FORMAT_NAME_JSON = "JSON";
   protected static final int DEFAULT_FACTORY_FEATURE_FLAGS = JsonFactory$Feature.collectDefaults();
   protected static final int DEFAULT_PARSER_FEATURE_FLAGS = JsonParser$Feature.collectDefaults();
   protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator$Feature.collectDefaults();
   private static final SerializableString DEFAULT_ROOT_VALUE_SEPARATOR;
   protected final transient CharsToNameCanonicalizer _rootCharSymbols;
   protected final transient ByteQuadsCanonicalizer _byteSymbolCanonicalizer;
   protected ObjectCodec _objectCodec;
   protected int _factoryFeatures;
   protected int _parserFeatures;
   protected int _generatorFeatures;
   protected CharacterEscapes _characterEscapes;
   protected InputDecorator _inputDecorator;
   protected OutputDecorator _outputDecorator;
   protected SerializableString _rootValueSeparator;

   public JsonFactory() {
      this((ObjectCodec)null);
   }

   public JsonFactory(ObjectCodec var1) {
      super();
      this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
      this._byteSymbolCanonicalizer = ByteQuadsCanonicalizer.createRoot();
      this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
      this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
      this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
      this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
      this._objectCodec = var1;
   }

   protected JsonFactory(JsonFactory var1, ObjectCodec var2) {
      super();
      this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
      this._byteSymbolCanonicalizer = ByteQuadsCanonicalizer.createRoot();
      this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
      this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
      this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
      this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
      this._objectCodec = var2;
      this._factoryFeatures = var1._factoryFeatures;
      this._parserFeatures = var1._parserFeatures;
      this._generatorFeatures = var1._generatorFeatures;
      this._characterEscapes = var1._characterEscapes;
      this._inputDecorator = var1._inputDecorator;
      this._outputDecorator = var1._outputDecorator;
      this._rootValueSeparator = var1._rootValueSeparator;
   }

   public JsonFactory copy() {
      this._checkInvalidCopy(JsonFactory.class);
      return new JsonFactory(this, (ObjectCodec)null);
   }

   protected void _checkInvalidCopy(Class var1) {
      if (this.getClass() != var1) {
         throw new IllegalStateException("Failed copy(): " + this.getClass().getName() + " (version: " + this.version() + ") does not override copy(); it has to");
      }
   }

   protected Object readResolve() {
      return new JsonFactory(this, this._objectCodec);
   }

   public boolean requiresPropertyOrdering() {
      return false;
   }

   public boolean canHandleBinaryNatively() {
      return false;
   }

   public boolean canUseCharArrays() {
      return true;
   }

   public boolean canParseAsync() {
      return this._isJSONFactory();
   }

   public Class getFormatReadFeatureType() {
      return null;
   }

   public Class getFormatWriteFeatureType() {
      return null;
   }

   public boolean canUseSchema(FormatSchema var1) {
      if (var1 == null) {
         return false;
      } else {
         String var2 = this.getFormatName();
         return var2 != null && var2.equals(var1.getSchemaType());
      }
   }

   public String getFormatName() {
      return this.getClass() == JsonFactory.class ? "JSON" : null;
   }

   public MatchStrength hasFormat(InputAccessor var1) throws IOException {
      return this.getClass() == JsonFactory.class ? this.hasJSONFormat(var1) : null;
   }

   public boolean requiresCustomCodec() {
      return false;
   }

   protected MatchStrength hasJSONFormat(InputAccessor var1) throws IOException {
      return ByteSourceJsonBootstrapper.hasJSONFormat(var1);
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   public final JsonFactory configure(JsonFactory$Feature var1, boolean var2) {
      return var2 ? this.enable(var1) : this.disable(var1);
   }

   public JsonFactory enable(JsonFactory$Feature var1) {
      this._factoryFeatures |= var1.getMask();
      return this;
   }

   public JsonFactory disable(JsonFactory$Feature var1) {
      this._factoryFeatures &= ~var1.getMask();
      return this;
   }

   public final boolean isEnabled(JsonFactory$Feature var1) {
      return (this._factoryFeatures & var1.getMask()) != 0;
   }

   public final JsonFactory configure(JsonParser$Feature var1, boolean var2) {
      return var2 ? this.enable(var1) : this.disable(var1);
   }

   public JsonFactory enable(JsonParser$Feature var1) {
      this._parserFeatures |= var1.getMask();
      return this;
   }

   public JsonFactory disable(JsonParser$Feature var1) {
      this._parserFeatures &= ~var1.getMask();
      return this;
   }

   public final boolean isEnabled(JsonParser$Feature var1) {
      return (this._parserFeatures & var1.getMask()) != 0;
   }

   public InputDecorator getInputDecorator() {
      return this._inputDecorator;
   }

   public JsonFactory setInputDecorator(InputDecorator var1) {
      this._inputDecorator = var1;
      return this;
   }

   public final JsonFactory configure(JsonGenerator$Feature var1, boolean var2) {
      return var2 ? this.enable(var1) : this.disable(var1);
   }

   public JsonFactory enable(JsonGenerator$Feature var1) {
      this._generatorFeatures |= var1.getMask();
      return this;
   }

   public JsonFactory disable(JsonGenerator$Feature var1) {
      this._generatorFeatures &= ~var1.getMask();
      return this;
   }

   public final boolean isEnabled(JsonGenerator$Feature var1) {
      return (this._generatorFeatures & var1.getMask()) != 0;
   }

   public CharacterEscapes getCharacterEscapes() {
      return this._characterEscapes;
   }

   public JsonFactory setCharacterEscapes(CharacterEscapes var1) {
      this._characterEscapes = var1;
      return this;
   }

   public OutputDecorator getOutputDecorator() {
      return this._outputDecorator;
   }

   public JsonFactory setOutputDecorator(OutputDecorator var1) {
      this._outputDecorator = var1;
      return this;
   }

   public JsonFactory setRootValueSeparator(String var1) {
      this._rootValueSeparator = var1 == null ? null : new SerializedString(var1);
      return this;
   }

   public String getRootValueSeparator() {
      return this._rootValueSeparator == null ? null : this._rootValueSeparator.getValue();
   }

   public JsonFactory setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
      return this;
   }

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public JsonParser createParser(File var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, true);
      FileInputStream var3 = new FileInputStream(var1);
      return this._createParser(this._decorate((InputStream)var3, var2), var2);
   }

   public JsonParser createParser(URL var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, true);
      InputStream var3 = this._optimizedStreamFromURL(var1);
      return this._createParser(this._decorate(var3, var2), var2);
   }

   public JsonParser createParser(InputStream var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, false);
      return this._createParser(this._decorate(var1, var2), var2);
   }

   public JsonParser createParser(Reader var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, false);
      return this._createParser(this._decorate(var1, var2), var2);
   }

   public JsonParser createParser(byte[] var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, true);
      if (this._inputDecorator != null) {
         InputStream var3 = this._inputDecorator.decorate(var2, var1, 0, var1.length);
         if (var3 != null) {
            return this._createParser(var3, var2);
         }
      }

      return this._createParser(var1, 0, var1.length, var2);
   }

   public JsonParser createParser(byte[] var1, int var2, int var3) throws IOException, JsonParseException {
      IOContext var4 = this._createContext(var1, true);
      if (this._inputDecorator != null) {
         InputStream var5 = this._inputDecorator.decorate(var4, var1, var2, var3);
         if (var5 != null) {
            return this._createParser(var5, var4);
         }
      }

      return this._createParser(var1, var2, var3, var4);
   }

   public JsonParser createParser(String var1) throws IOException, JsonParseException {
      int var2 = var1.length();
      if (this._inputDecorator == null && var2 <= 32768 && this.canUseCharArrays()) {
         IOContext var3 = this._createContext(var1, true);
         char[] var4 = var3.allocTokenBuffer(var2);
         var1.getChars(0, var2, var4, 0);
         return this._createParser(var4, 0, var2, var3, true);
      } else {
         return this.createParser((Reader)(new StringReader(var1)));
      }
   }

   public JsonParser createParser(char[] var1) throws IOException {
      return this.createParser((char[])var1, 0, var1.length);
   }

   public JsonParser createParser(char[] var1, int var2, int var3) throws IOException {
      return this._inputDecorator != null ? this.createParser((Reader)(new CharArrayReader(var1, var2, var3))) : this._createParser(var1, var2, var3, this._createContext(var1, true), false);
   }

   public JsonParser createParser(DataInput var1) throws IOException {
      IOContext var2 = this._createContext(var1, false);
      return this._createParser(this._decorate(var1, var2), var2);
   }

   public JsonParser createNonBlockingByteArrayParser() throws IOException {
      this._requireJSONFactory("Non-blocking source not (yet?) support for this format (%s)");
      IOContext var1 = this._createContext((Object)null, false);
      ByteQuadsCanonicalizer var2 = this._byteSymbolCanonicalizer.makeChild(this._factoryFeatures);
      return new NonBlockingJsonParser(var1, this._parserFeatures, var2);
   }

   /** @deprecated */
   @Deprecated
   public JsonParser createJsonParser(File var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   /** @deprecated */
   @Deprecated
   public JsonParser createJsonParser(URL var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   /** @deprecated */
   @Deprecated
   public JsonParser createJsonParser(InputStream var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   /** @deprecated */
   @Deprecated
   public JsonParser createJsonParser(Reader var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   /** @deprecated */
   @Deprecated
   public JsonParser createJsonParser(byte[] var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   /** @deprecated */
   @Deprecated
   public JsonParser createJsonParser(byte[] var1, int var2, int var3) throws IOException, JsonParseException {
      return this.createParser(var1, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public JsonParser createJsonParser(String var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   public JsonGenerator createGenerator(OutputStream var1, JsonEncoding var2) throws IOException {
      IOContext var3 = this._createContext(var1, false);
      var3.setEncoding(var2);
      if (var2 == JsonEncoding.UTF8) {
         return this._createUTF8Generator(this._decorate(var1, var3), var3);
      } else {
         Writer var4 = this._createWriter(var1, var2, var3);
         return this._createGenerator(this._decorate(var4, var3), var3);
      }
   }

   public JsonGenerator createGenerator(OutputStream var1) throws IOException {
      return this.createGenerator(var1, JsonEncoding.UTF8);
   }

   public JsonGenerator createGenerator(Writer var1) throws IOException {
      IOContext var2 = this._createContext(var1, false);
      return this._createGenerator(this._decorate(var1, var2), var2);
   }

   public JsonGenerator createGenerator(File var1, JsonEncoding var2) throws IOException {
      FileOutputStream var3 = new FileOutputStream(var1);
      IOContext var4 = this._createContext(var3, true);
      var4.setEncoding(var2);
      if (var2 == JsonEncoding.UTF8) {
         return this._createUTF8Generator(this._decorate((OutputStream)var3, var4), var4);
      } else {
         Writer var5 = this._createWriter(var3, var2, var4);
         return this._createGenerator(this._decorate(var5, var4), var4);
      }
   }

   public JsonGenerator createGenerator(DataOutput var1, JsonEncoding var2) throws IOException {
      return this.createGenerator(this._createDataOutputWrapper(var1), var2);
   }

   public JsonGenerator createGenerator(DataOutput var1) throws IOException {
      return this.createGenerator(this._createDataOutputWrapper(var1), JsonEncoding.UTF8);
   }

   /** @deprecated */
   @Deprecated
   public JsonGenerator createJsonGenerator(OutputStream var1, JsonEncoding var2) throws IOException {
      return this.createGenerator(var1, var2);
   }

   /** @deprecated */
   @Deprecated
   public JsonGenerator createJsonGenerator(Writer var1) throws IOException {
      return this.createGenerator(var1);
   }

   /** @deprecated */
   @Deprecated
   public JsonGenerator createJsonGenerator(OutputStream var1) throws IOException {
      return this.createGenerator(var1, JsonEncoding.UTF8);
   }

   protected JsonParser _createParser(InputStream var1, IOContext var2) throws IOException {
      return (new ByteSourceJsonBootstrapper(var2, var1)).constructParser(this._parserFeatures, this._objectCodec, this._byteSymbolCanonicalizer, this._rootCharSymbols, this._factoryFeatures);
   }

   protected JsonParser _createParser(Reader var1, IOContext var2) throws IOException {
      return new ReaderBasedJsonParser(var2, this._parserFeatures, var1, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures));
   }

   protected JsonParser _createParser(char[] var1, int var2, int var3, IOContext var4, boolean var5) throws IOException {
      return new ReaderBasedJsonParser(var4, this._parserFeatures, (Reader)null, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures), var1, var2, var2 + var3, var5);
   }

   protected JsonParser _createParser(byte[] var1, int var2, int var3, IOContext var4) throws IOException {
      return (new ByteSourceJsonBootstrapper(var4, var1, var2, var3)).constructParser(this._parserFeatures, this._objectCodec, this._byteSymbolCanonicalizer, this._rootCharSymbols, this._factoryFeatures);
   }

   protected JsonParser _createParser(DataInput var1, IOContext var2) throws IOException {
      this._requireJSONFactory("InputData source not (yet?) support for this format (%s)");
      int var3 = ByteSourceJsonBootstrapper.skipUTF8BOM(var1);
      ByteQuadsCanonicalizer var4 = this._byteSymbolCanonicalizer.makeChild(this._factoryFeatures);
      return new UTF8DataInputJsonParser(var2, this._parserFeatures, var1, this._objectCodec, var4, var3);
   }

   protected JsonGenerator _createGenerator(Writer var1, IOContext var2) throws IOException {
      WriterBasedJsonGenerator var3 = new WriterBasedJsonGenerator(var2, this._generatorFeatures, this._objectCodec, var1);
      if (this._characterEscapes != null) {
         var3.setCharacterEscapes(this._characterEscapes);
      }

      SerializableString var4 = this._rootValueSeparator;
      if (var4 != DEFAULT_ROOT_VALUE_SEPARATOR) {
         var3.setRootValueSeparator(var4);
      }

      return var3;
   }

   protected JsonGenerator _createUTF8Generator(OutputStream var1, IOContext var2) throws IOException {
      UTF8JsonGenerator var3 = new UTF8JsonGenerator(var2, this._generatorFeatures, this._objectCodec, var1);
      if (this._characterEscapes != null) {
         var3.setCharacterEscapes(this._characterEscapes);
      }

      SerializableString var4 = this._rootValueSeparator;
      if (var4 != DEFAULT_ROOT_VALUE_SEPARATOR) {
         var3.setRootValueSeparator(var4);
      }

      return var3;
   }

   protected Writer _createWriter(OutputStream var1, JsonEncoding var2, IOContext var3) throws IOException {
      return (Writer)(var2 == JsonEncoding.UTF8 ? new UTF8Writer(var3, var1) : new OutputStreamWriter(var1, var2.getJavaName()));
   }

   protected final InputStream _decorate(InputStream var1, IOContext var2) throws IOException {
      if (this._inputDecorator != null) {
         InputStream var3 = this._inputDecorator.decorate(var2, var1);
         if (var3 != null) {
            return var3;
         }
      }

      return var1;
   }

   protected final Reader _decorate(Reader var1, IOContext var2) throws IOException {
      if (this._inputDecorator != null) {
         Reader var3 = this._inputDecorator.decorate(var2, var1);
         if (var3 != null) {
            return var3;
         }
      }

      return var1;
   }

   protected final DataInput _decorate(DataInput var1, IOContext var2) throws IOException {
      if (this._inputDecorator != null) {
         DataInput var3 = this._inputDecorator.decorate(var2, var1);
         if (var3 != null) {
            return var3;
         }
      }

      return var1;
   }

   protected final OutputStream _decorate(OutputStream var1, IOContext var2) throws IOException {
      if (this._outputDecorator != null) {
         OutputStream var3 = this._outputDecorator.decorate(var2, var1);
         if (var3 != null) {
            return var3;
         }
      }

      return var1;
   }

   protected final Writer _decorate(Writer var1, IOContext var2) throws IOException {
      if (this._outputDecorator != null) {
         Writer var3 = this._outputDecorator.decorate(var2, var1);
         if (var3 != null) {
            return var3;
         }
      }

      return var1;
   }

   public BufferRecycler _getBufferRecycler() {
      return JsonFactory$Feature.USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING.enabledIn(this._factoryFeatures) ? BufferRecyclers.getBufferRecycler() : new BufferRecycler();
   }

   protected IOContext _createContext(Object var1, boolean var2) {
      return new IOContext(this._getBufferRecycler(), var1, var2);
   }

   protected OutputStream _createDataOutputWrapper(DataOutput var1) {
      return new DataOutputAsStream(var1);
   }

   protected InputStream _optimizedStreamFromURL(URL var1) throws IOException {
      if ("file".equals(var1.getProtocol())) {
         String var2 = var1.getHost();
         if (var2 == null || var2.length() == 0) {
            String var3 = var1.getPath();
            if (var3.indexOf(37) < 0) {
               return new FileInputStream(var1.getPath());
            }
         }
      }

      return var1.openStream();
   }

   private final void _requireJSONFactory(String var1) {
      if (!this._isJSONFactory()) {
         throw new UnsupportedOperationException(String.format(var1, this.getFormatName()));
      }
   }

   private final boolean _isJSONFactory() {
      return this.getFormatName() == "JSON";
   }

   static {
      DEFAULT_ROOT_VALUE_SEPARATOR = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
   }
}
