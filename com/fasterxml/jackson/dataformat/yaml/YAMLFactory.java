package com.fasterxml.jackson.dataformat.yaml;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$Feature;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import org.yaml.snakeyaml.DumperOptions$Version;

public class YAMLFactory extends JsonFactory {
   private static final long serialVersionUID = 1L;
   public static final String FORMAT_NAME_YAML = "YAML";
   protected static final int DEFAULT_YAML_PARSER_FEATURE_FLAGS = YAMLParser$Feature.collectDefaults();
   protected static final int DEFAULT_YAML_GENERATOR_FEATURE_FLAGS = YAMLGenerator$Feature.collectDefaults();
   private static final byte UTF8_BOM_1 = -17;
   private static final byte UTF8_BOM_2 = -69;
   private static final byte UTF8_BOM_3 = -65;
   protected int _yamlParserFeatures;
   protected int _yamlGeneratorFeatures;
   protected DumperOptions$Version _version;
   protected final Charset UTF8;

   public YAMLFactory() {
      this((ObjectCodec)null);
   }

   public YAMLFactory(ObjectCodec var1) {
      super(var1);
      this._yamlParserFeatures = DEFAULT_YAML_PARSER_FEATURE_FLAGS;
      this._yamlGeneratorFeatures = DEFAULT_YAML_GENERATOR_FEATURE_FLAGS;
      this.UTF8 = Charset.forName("UTF-8");
      this._yamlParserFeatures = DEFAULT_YAML_PARSER_FEATURE_FLAGS;
      this._yamlGeneratorFeatures = DEFAULT_YAML_GENERATOR_FEATURE_FLAGS;
      this._version = null;
   }

   public YAMLFactory(YAMLFactory var1, ObjectCodec var2) {
      super(var1, var2);
      this._yamlParserFeatures = DEFAULT_YAML_PARSER_FEATURE_FLAGS;
      this._yamlGeneratorFeatures = DEFAULT_YAML_GENERATOR_FEATURE_FLAGS;
      this.UTF8 = Charset.forName("UTF-8");
      this._version = var1._version;
      this._yamlParserFeatures = var1._yamlParserFeatures;
      this._yamlGeneratorFeatures = var1._yamlGeneratorFeatures;
   }

   public YAMLFactory copy() {
      this._checkInvalidCopy(YAMLFactory.class);
      return new YAMLFactory(this, (ObjectCodec)null);
   }

   protected Object readResolve() {
      return new YAMLFactory(this, this._objectCodec);
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   public boolean canUseCharArrays() {
      return false;
   }

   public String getFormatName() {
      return "YAML";
   }

   public MatchStrength hasFormat(InputAccessor var1) throws IOException {
      if (!var1.hasMoreBytes()) {
         return MatchStrength.INCONCLUSIVE;
      } else {
         byte var2 = var1.nextByte();
         if (var2 == -17) {
            if (!var1.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            if (var1.nextByte() != -69) {
               return MatchStrength.NO_MATCH;
            }

            if (!var1.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            if (var1.nextByte() != -65) {
               return MatchStrength.NO_MATCH;
            }

            if (!var1.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            var2 = var1.nextByte();
         }

         return var2 == 45 && var1.hasMoreBytes() && var1.nextByte() == 45 && var1.hasMoreBytes() && var1.nextByte() == 45 ? MatchStrength.FULL_MATCH : MatchStrength.INCONCLUSIVE;
      }
   }

   public final YAMLFactory configure(YAMLParser$Feature var1, boolean var2) {
      if (var2) {
         this.enable(var1);
      } else {
         this.disable(var1);
      }

      return this;
   }

   public YAMLFactory enable(YAMLParser$Feature var1) {
      this._yamlParserFeatures |= var1.getMask();
      return this;
   }

   public YAMLFactory disable(YAMLParser$Feature var1) {
      this._yamlParserFeatures &= ~var1.getMask();
      return this;
   }

   public final boolean isEnabled(YAMLParser$Feature var1) {
      return (this._yamlParserFeatures & var1.getMask()) != 0;
   }

   public final YAMLFactory configure(YAMLGenerator$Feature var1, boolean var2) {
      if (var2) {
         this.enable(var1);
      } else {
         this.disable(var1);
      }

      return this;
   }

   public YAMLFactory enable(YAMLGenerator$Feature var1) {
      this._yamlGeneratorFeatures |= var1.getMask();
      return this;
   }

   public YAMLFactory disable(YAMLGenerator$Feature var1) {
      this._yamlGeneratorFeatures &= ~var1.getMask();
      return this;
   }

   public final boolean isEnabled(YAMLGenerator$Feature var1) {
      return (this._yamlGeneratorFeatures & var1.getMask()) != 0;
   }

   public YAMLParser createParser(String var1) throws IOException {
      return this.createParser((Reader)(new StringReader(var1)));
   }

   public YAMLParser createParser(File var1) throws IOException {
      IOContext var2 = this._createContext(var1, true);
      return this._createParser(this._decorate(new FileInputStream(var1), var2), var2);
   }

   public YAMLParser createParser(URL var1) throws IOException {
      IOContext var2 = this._createContext(var1, true);
      return this._createParser(this._decorate(this._optimizedStreamFromURL(var1), var2), var2);
   }

   public YAMLParser createParser(InputStream var1) throws IOException {
      IOContext var2 = this._createContext(var1, false);
      return this._createParser(this._decorate(var1, var2), var2);
   }

   public YAMLParser createParser(Reader var1) throws IOException {
      IOContext var2 = this._createContext(var1, false);
      return this._createParser(this._decorate(var1, var2), var2);
   }

   public YAMLParser createParser(char[] var1) throws IOException {
      return this.createParser((Reader)(new CharArrayReader(var1, 0, var1.length)));
   }

   public YAMLParser createParser(char[] var1, int var2, int var3) throws IOException {
      return this.createParser((Reader)(new CharArrayReader(var1, var2, var3)));
   }

   public YAMLParser createParser(byte[] var1) throws IOException {
      IOContext var2 = this._createContext(var1, true);
      if (this._inputDecorator != null) {
         InputStream var3 = this._inputDecorator.decorate(var2, var1, 0, var1.length);
         if (var3 != null) {
            return this._createParser(var3, var2);
         }
      }

      return this._createParser(var1, 0, var1.length, var2);
   }

   public YAMLParser createParser(byte[] var1, int var2, int var3) throws IOException {
      IOContext var4 = this._createContext(var1, true);
      if (this._inputDecorator != null) {
         InputStream var5 = this._inputDecorator.decorate(var4, var1, var2, var3);
         if (var5 != null) {
            return this._createParser(var5, var4);
         }
      }

      return this._createParser(var1, var2, var3, var4);
   }

   public YAMLGenerator createGenerator(OutputStream var1, JsonEncoding var2) throws IOException {
      IOContext var3 = this._createContext(var1, false);
      var3.setEncoding(var2);
      return this._createGenerator(this._createWriter(this._decorate(var1, var3), var2, var3), var3);
   }

   public YAMLGenerator createGenerator(OutputStream var1) throws IOException {
      IOContext var2 = this._createContext(var1, false);
      return this._createGenerator(this._createWriter(this._decorate(var1, var2), JsonEncoding.UTF8, var2), var2);
   }

   public YAMLGenerator createGenerator(Writer var1) throws IOException {
      IOContext var2 = this._createContext(var1, false);
      return this._createGenerator(this._decorate(var1, var2), var2);
   }

   public JsonGenerator createGenerator(File var1, JsonEncoding var2) throws IOException {
      FileOutputStream var3 = new FileOutputStream(var1);
      IOContext var4 = this._createContext(var1, true);
      var4.setEncoding(var2);
      return this._createGenerator(this._createWriter(this._decorate(var3, var4), var2, var4), var4);
   }

   protected YAMLParser _createParser(InputStream var1, IOContext var2) throws IOException {
      return new YAMLParser(var2, this._getBufferRecycler(), this._parserFeatures, this._yamlParserFeatures, this._objectCodec, this._createReader(var1, (JsonEncoding)null, var2));
   }

   protected YAMLParser _createParser(Reader var1, IOContext var2) throws IOException {
      return new YAMLParser(var2, this._getBufferRecycler(), this._parserFeatures, this._yamlParserFeatures, this._objectCodec, var1);
   }

   protected YAMLParser _createParser(char[] var1, int var2, int var3, IOContext var4, boolean var5) throws IOException {
      return new YAMLParser(var4, this._getBufferRecycler(), this._parserFeatures, this._yamlParserFeatures, this._objectCodec, new CharArrayReader(var1, var2, var3));
   }

   protected YAMLParser _createParser(byte[] var1, int var2, int var3, IOContext var4) throws IOException {
      return new YAMLParser(var4, this._getBufferRecycler(), this._parserFeatures, this._yamlParserFeatures, this._objectCodec, this._createReader(var1, var2, var3, (JsonEncoding)null, var4));
   }

   protected YAMLGenerator _createGenerator(Writer var1, IOContext var2) throws IOException {
      int var3 = this._yamlGeneratorFeatures;
      YAMLGenerator var4 = new YAMLGenerator(var2, this._generatorFeatures, var3, this._objectCodec, var1, this._version);
      return var4;
   }

   protected YAMLGenerator _createUTF8Generator(OutputStream var1, IOContext var2) throws IOException {
      throw new IllegalStateException();
   }

   protected Writer _createWriter(OutputStream var1, JsonEncoding var2, IOContext var3) throws IOException {
      return (Writer)(var2 == JsonEncoding.UTF8 ? new UTF8Writer(var1) : new OutputStreamWriter(var1, var2.getJavaName()));
   }

   protected Reader _createReader(InputStream var1, JsonEncoding var2, IOContext var3) throws IOException {
      if (var2 == null) {
         var2 = JsonEncoding.UTF8;
      }

      if (var2 != JsonEncoding.UTF8) {
         return new InputStreamReader(var1, var2.getJavaName());
      } else {
         boolean var4 = var3.isResourceManaged() || this.isEnabled((JsonParser$Feature)JsonParser$Feature.AUTO_CLOSE_SOURCE);
         return new UTF8Reader(var1, var4);
      }
   }

   protected Reader _createReader(byte[] var1, int var2, int var3, JsonEncoding var4, IOContext var5) throws IOException {
      if (var4 == null) {
         var4 = JsonEncoding.UTF8;
      }

      if (var4 != null && var4 != JsonEncoding.UTF8) {
         ByteArrayInputStream var6 = new ByteArrayInputStream(var1, var2, var3);
         return new InputStreamReader(var6, var4.getJavaName());
      } else {
         return new UTF8Reader(var1, var2, var3, true);
      }
   }

   protected JsonGenerator _createUTF8Generator(OutputStream var1, IOContext var2) throws IOException {
      return this._createUTF8Generator(var1, var2);
   }

   protected JsonGenerator _createGenerator(Writer var1, IOContext var2) throws IOException {
      return this._createGenerator(var1, var2);
   }

   protected JsonParser _createParser(byte[] var1, int var2, int var3, IOContext var4) throws IOException {
      return this._createParser(var1, var2, var3, var4);
   }

   protected JsonParser _createParser(char[] var1, int var2, int var3, IOContext var4, boolean var5) throws IOException {
      return this._createParser(var1, var2, var3, var4, var5);
   }

   protected JsonParser _createParser(Reader var1, IOContext var2) throws IOException {
      return this._createParser(var1, var2);
   }

   protected JsonParser _createParser(InputStream var1, IOContext var2) throws IOException {
      return this._createParser(var1, var2);
   }

   public JsonGenerator createGenerator(Writer var1) throws IOException {
      return this.createGenerator(var1);
   }

   public JsonGenerator createGenerator(OutputStream var1) throws IOException {
      return this.createGenerator(var1);
   }

   public JsonGenerator createGenerator(OutputStream var1, JsonEncoding var2) throws IOException {
      return this.createGenerator(var1, var2);
   }

   public JsonParser createParser(char[] var1, int var2, int var3) throws IOException {
      return this.createParser(var1, var2, var3);
   }

   public JsonParser createParser(char[] var1) throws IOException {
      return this.createParser(var1);
   }

   public JsonParser createParser(String var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   public JsonParser createParser(byte[] var1, int var2, int var3) throws IOException, JsonParseException {
      return this.createParser(var1, var2, var3);
   }

   public JsonParser createParser(byte[] var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   public JsonParser createParser(Reader var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   public JsonParser createParser(InputStream var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   public JsonParser createParser(URL var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   public JsonParser createParser(File var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   public JsonFactory copy() {
      return this.copy();
   }
}
