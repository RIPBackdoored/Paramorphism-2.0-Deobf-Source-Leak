package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator$Feature;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Instantiatable;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.ConfigOverrides;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.Serializable;
import java.text.DateFormat;

public final class SerializationConfig extends MapperConfigBase implements Serializable {
   private static final long serialVersionUID = 1L;
   protected static final PrettyPrinter DEFAULT_PRETTY_PRINTER = new DefaultPrettyPrinter();
   protected final FilterProvider _filterProvider;
   protected final PrettyPrinter _defaultPrettyPrinter;
   protected final int _serFeatures;
   protected final int _generatorFeatures;
   protected final int _generatorFeaturesToChange;
   protected final int _formatWriteFeatures;
   protected final int _formatWriteFeaturesToChange;

   public SerializationConfig(BaseSettings var1, SubtypeResolver var2, SimpleMixInResolver var3, RootNameLookup var4, ConfigOverrides var5) {
      super(var1, var2, var3, var4, var5);
      this._serFeatures = collectFeatureDefaults(SerializationFeature.class);
      this._filterProvider = null;
      this._defaultPrettyPrinter = DEFAULT_PRETTY_PRINTER;
      this._generatorFeatures = 0;
      this._generatorFeaturesToChange = 0;
      this._formatWriteFeatures = 0;
      this._formatWriteFeaturesToChange = 0;
   }

   protected SerializationConfig(SerializationConfig var1, SimpleMixInResolver var2, RootNameLookup var3, ConfigOverrides var4) {
      super(var1, var2, var3, var4);
      this._serFeatures = var1._serFeatures;
      this._filterProvider = var1._filterProvider;
      this._defaultPrettyPrinter = var1._defaultPrettyPrinter;
      this._generatorFeatures = var1._generatorFeatures;
      this._generatorFeaturesToChange = var1._generatorFeaturesToChange;
      this._formatWriteFeatures = var1._formatWriteFeatures;
      this._formatWriteFeaturesToChange = var1._formatWriteFeaturesToChange;
   }

   private SerializationConfig(SerializationConfig var1, SubtypeResolver var2) {
      super(var1, (SubtypeResolver)var2);
      this._serFeatures = var1._serFeatures;
      this._filterProvider = var1._filterProvider;
      this._defaultPrettyPrinter = var1._defaultPrettyPrinter;
      this._generatorFeatures = var1._generatorFeatures;
      this._generatorFeaturesToChange = var1._generatorFeaturesToChange;
      this._formatWriteFeatures = var1._formatWriteFeatures;
      this._formatWriteFeaturesToChange = var1._formatWriteFeaturesToChange;
   }

   private SerializationConfig(SerializationConfig var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2);
      this._serFeatures = var3;
      this._filterProvider = var1._filterProvider;
      this._defaultPrettyPrinter = var1._defaultPrettyPrinter;
      this._generatorFeatures = var4;
      this._generatorFeaturesToChange = var5;
      this._formatWriteFeatures = var6;
      this._formatWriteFeaturesToChange = var7;
   }

   private SerializationConfig(SerializationConfig var1, BaseSettings var2) {
      super(var1, (BaseSettings)var2);
      this._serFeatures = var1._serFeatures;
      this._filterProvider = var1._filterProvider;
      this._defaultPrettyPrinter = var1._defaultPrettyPrinter;
      this._generatorFeatures = var1._generatorFeatures;
      this._generatorFeaturesToChange = var1._generatorFeaturesToChange;
      this._formatWriteFeatures = var1._formatWriteFeatures;
      this._formatWriteFeaturesToChange = var1._formatWriteFeaturesToChange;
   }

   private SerializationConfig(SerializationConfig var1, FilterProvider var2) {
      super(var1);
      this._serFeatures = var1._serFeatures;
      this._filterProvider = var2;
      this._defaultPrettyPrinter = var1._defaultPrettyPrinter;
      this._generatorFeatures = var1._generatorFeatures;
      this._generatorFeaturesToChange = var1._generatorFeaturesToChange;
      this._formatWriteFeatures = var1._formatWriteFeatures;
      this._formatWriteFeaturesToChange = var1._formatWriteFeaturesToChange;
   }

   private SerializationConfig(SerializationConfig var1, Class var2) {
      super(var1, (Class)var2);
      this._serFeatures = var1._serFeatures;
      this._filterProvider = var1._filterProvider;
      this._defaultPrettyPrinter = var1._defaultPrettyPrinter;
      this._generatorFeatures = var1._generatorFeatures;
      this._generatorFeaturesToChange = var1._generatorFeaturesToChange;
      this._formatWriteFeatures = var1._formatWriteFeatures;
      this._formatWriteFeaturesToChange = var1._formatWriteFeaturesToChange;
   }

   private SerializationConfig(SerializationConfig var1, PropertyName var2) {
      super(var1, (PropertyName)var2);
      this._serFeatures = var1._serFeatures;
      this._filterProvider = var1._filterProvider;
      this._defaultPrettyPrinter = var1._defaultPrettyPrinter;
      this._generatorFeatures = var1._generatorFeatures;
      this._generatorFeaturesToChange = var1._generatorFeaturesToChange;
      this._formatWriteFeatures = var1._formatWriteFeatures;
      this._formatWriteFeaturesToChange = var1._formatWriteFeaturesToChange;
   }

   protected SerializationConfig(SerializationConfig var1, ContextAttributes var2) {
      super(var1, (ContextAttributes)var2);
      this._serFeatures = var1._serFeatures;
      this._filterProvider = var1._filterProvider;
      this._defaultPrettyPrinter = var1._defaultPrettyPrinter;
      this._generatorFeatures = var1._generatorFeatures;
      this._generatorFeaturesToChange = var1._generatorFeaturesToChange;
      this._formatWriteFeatures = var1._formatWriteFeatures;
      this._formatWriteFeaturesToChange = var1._formatWriteFeaturesToChange;
   }

   protected SerializationConfig(SerializationConfig var1, SimpleMixInResolver var2) {
      super(var1, (SimpleMixInResolver)var2);
      this._serFeatures = var1._serFeatures;
      this._filterProvider = var1._filterProvider;
      this._defaultPrettyPrinter = var1._defaultPrettyPrinter;
      this._generatorFeatures = var1._generatorFeatures;
      this._generatorFeaturesToChange = var1._generatorFeaturesToChange;
      this._formatWriteFeatures = var1._formatWriteFeatures;
      this._formatWriteFeaturesToChange = var1._formatWriteFeaturesToChange;
   }

   protected SerializationConfig(SerializationConfig var1, PrettyPrinter var2) {
      super(var1);
      this._serFeatures = var1._serFeatures;
      this._filterProvider = var1._filterProvider;
      this._defaultPrettyPrinter = var2;
      this._generatorFeatures = var1._generatorFeatures;
      this._generatorFeaturesToChange = var1._generatorFeaturesToChange;
      this._formatWriteFeatures = var1._formatWriteFeatures;
      this._formatWriteFeaturesToChange = var1._formatWriteFeaturesToChange;
   }

   protected final SerializationConfig _withBase(BaseSettings var1) {
      return this._base == var1 ? this : new SerializationConfig(this, var1);
   }

   protected final SerializationConfig _withMapperFeatures(int var1) {
      return new SerializationConfig(this, var1, this._serFeatures, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig withRootName(PropertyName var1) {
      if (var1 == null) {
         if (this._rootName == null) {
            return this;
         }
      } else if (var1.equals(this._rootName)) {
         return this;
      }

      return new SerializationConfig(this, var1);
   }

   public SerializationConfig with(SubtypeResolver var1) {
      return var1 == this._subtypeResolver ? this : new SerializationConfig(this, var1);
   }

   public SerializationConfig withView(Class var1) {
      return this._view == var1 ? this : new SerializationConfig(this, var1);
   }

   public SerializationConfig with(ContextAttributes var1) {
      return var1 == this._attributes ? this : new SerializationConfig(this, var1);
   }

   public SerializationConfig with(DateFormat var1) {
      SerializationConfig var2 = (SerializationConfig)super.with(var1);
      return var1 == null ? var2.with(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) : var2.without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
   }

   public SerializationConfig with(SerializationFeature var1) {
      int var2 = this._serFeatures | var1.getMask();
      return var2 == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, var2, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig with(SerializationFeature var1, SerializationFeature... var2) {
      int var3 = this._serFeatures | var1.getMask();
      SerializationFeature[] var4 = var2;
      int var5 = var2.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         SerializationFeature var7 = var4[var6];
         var3 |= var7.getMask();
      }

      return var3 == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, var3, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig withFeatures(SerializationFeature... var1) {
      int var2 = this._serFeatures;
      SerializationFeature[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         SerializationFeature var6 = var3[var5];
         var2 |= var6.getMask();
      }

      return var2 == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, var2, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig without(SerializationFeature var1) {
      int var2 = this._serFeatures & ~var1.getMask();
      return var2 == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, var2, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig without(SerializationFeature var1, SerializationFeature... var2) {
      int var3 = this._serFeatures & ~var1.getMask();
      SerializationFeature[] var4 = var2;
      int var5 = var2.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         SerializationFeature var7 = var4[var6];
         var3 &= ~var7.getMask();
      }

      return var3 == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, var3, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig withoutFeatures(SerializationFeature... var1) {
      int var2 = this._serFeatures;
      SerializationFeature[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         SerializationFeature var6 = var3[var5];
         var2 &= ~var6.getMask();
      }

      return var2 == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, var2, this._generatorFeatures, this._generatorFeaturesToChange, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig with(JsonGenerator$Feature var1) {
      int var2 = this._generatorFeatures | var1.getMask();
      int var3 = this._generatorFeaturesToChange | var1.getMask();
      return this._generatorFeatures == var2 && this._generatorFeaturesToChange == var3 ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, var2, var3, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig withFeatures(JsonGenerator$Feature... var1) {
      int var2 = this._generatorFeatures;
      int var3 = this._generatorFeaturesToChange;
      JsonGenerator$Feature[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         JsonGenerator$Feature var7 = var4[var6];
         int var8 = var7.getMask();
         var2 |= var8;
         var3 |= var8;
      }

      return this._generatorFeatures == var2 && this._generatorFeaturesToChange == var3 ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, var2, var3, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig without(JsonGenerator$Feature var1) {
      int var2 = this._generatorFeatures & ~var1.getMask();
      int var3 = this._generatorFeaturesToChange | var1.getMask();
      return this._generatorFeatures == var2 && this._generatorFeaturesToChange == var3 ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, var2, var3, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig withoutFeatures(JsonGenerator$Feature... var1) {
      int var2 = this._generatorFeatures;
      int var3 = this._generatorFeaturesToChange;
      JsonGenerator$Feature[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         JsonGenerator$Feature var7 = var4[var6];
         int var8 = var7.getMask();
         var2 &= ~var8;
         var3 |= var8;
      }

      return this._generatorFeatures == var2 && this._generatorFeaturesToChange == var3 ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, var2, var3, this._formatWriteFeatures, this._formatWriteFeaturesToChange);
   }

   public SerializationConfig with(FormatFeature var1) {
      int var2 = this._formatWriteFeatures | var1.getMask();
      int var3 = this._formatWriteFeaturesToChange | var1.getMask();
      return this._formatWriteFeatures == var2 && this._formatWriteFeaturesToChange == var3 ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, this._generatorFeatures, this._generatorFeaturesToChange, var2, var3);
   }

   public SerializationConfig withFeatures(FormatFeature... var1) {
      int var2 = this._formatWriteFeatures;
      int var3 = this._formatWriteFeaturesToChange;
      FormatFeature[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         FormatFeature var7 = var4[var6];
         int var8 = var7.getMask();
         var2 |= var8;
         var3 |= var8;
      }

      return this._formatWriteFeatures == var2 && this._formatWriteFeaturesToChange == var3 ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, this._generatorFeatures, this._generatorFeaturesToChange, var2, var3);
   }

   public SerializationConfig without(FormatFeature var1) {
      int var2 = this._formatWriteFeatures & ~var1.getMask();
      int var3 = this._formatWriteFeaturesToChange | var1.getMask();
      return this._formatWriteFeatures == var2 && this._formatWriteFeaturesToChange == var3 ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, this._generatorFeatures, this._generatorFeaturesToChange, var2, var3);
   }

   public SerializationConfig withoutFeatures(FormatFeature... var1) {
      int var2 = this._formatWriteFeatures;
      int var3 = this._formatWriteFeaturesToChange;
      FormatFeature[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         FormatFeature var7 = var4[var6];
         int var8 = var7.getMask();
         var2 &= ~var8;
         var3 |= var8;
      }

      return this._formatWriteFeatures == var2 && this._formatWriteFeaturesToChange == var3 ? this : new SerializationConfig(this, this._mapperFeatures, this._serFeatures, this._generatorFeatures, this._generatorFeaturesToChange, var2, var3);
   }

   public SerializationConfig withFilters(FilterProvider var1) {
      return var1 == this._filterProvider ? this : new SerializationConfig(this, var1);
   }

   /** @deprecated */
   @Deprecated
   public SerializationConfig withPropertyInclusion(JsonInclude$Value var1) {
      this._configOverrides.setDefaultInclusion(var1);
      return this;
   }

   public SerializationConfig withDefaultPrettyPrinter(PrettyPrinter var1) {
      return this._defaultPrettyPrinter == var1 ? this : new SerializationConfig(this, var1);
   }

   public PrettyPrinter constructDefaultPrettyPrinter() {
      PrettyPrinter var1 = this._defaultPrettyPrinter;
      if (var1 instanceof Instantiatable) {
         var1 = (PrettyPrinter)((Instantiatable)var1).createInstance();
      }

      return var1;
   }

   public void initialize(JsonGenerator var1) {
      if (SerializationFeature.INDENT_OUTPUT.enabledIn(this._serFeatures) && var1.getPrettyPrinter() == null) {
         PrettyPrinter var2 = this.constructDefaultPrettyPrinter();
         if (var2 != null) {
            var1.setPrettyPrinter(var2);
         }
      }

      boolean var6 = SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN.enabledIn(this._serFeatures);
      int var3 = this._generatorFeaturesToChange;
      if (var3 != 0 || var6) {
         int var4 = this._generatorFeatures;
         if (var6) {
            int var5 = JsonGenerator$Feature.WRITE_BIGDECIMAL_AS_PLAIN.getMask();
            var4 |= var5;
            var3 |= var5;
         }

         var1.overrideStdFeatures(var4, var3);
      }

      if (this._formatWriteFeaturesToChange != 0) {
         var1.overrideFormatFeatures(this._formatWriteFeatures, this._formatWriteFeaturesToChange);
      }

   }

   /** @deprecated */
   @Deprecated
   public JsonInclude$Include getSerializationInclusion() {
      JsonInclude$Include var1 = this.getDefaultPropertyInclusion().getValueInclusion();
      return var1 == JsonInclude$Include.USE_DEFAULTS ? JsonInclude$Include.ALWAYS : var1;
   }

   public boolean useRootWrapping() {
      if (this._rootName != null) {
         return !this._rootName.isEmpty();
      } else {
         return this.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
      }
   }

   public final boolean isEnabled(SerializationFeature var1) {
      return (this._serFeatures & var1.getMask()) != 0;
   }

   public final boolean isEnabled(JsonGenerator$Feature var1, JsonFactory var2) {
      int var3 = var1.getMask();
      if ((this._generatorFeaturesToChange & var3) != 0) {
         return (this._generatorFeatures & var1.getMask()) != 0;
      } else {
         return var2.isEnabled(var1);
      }
   }

   public final boolean hasSerializationFeatures(int var1) {
      return (this._serFeatures & var1) == var1;
   }

   public final int getSerializationFeatures() {
      return this._serFeatures;
   }

   public FilterProvider getFilterProvider() {
      return this._filterProvider;
   }

   public PrettyPrinter getDefaultPrettyPrinter() {
      return this._defaultPrettyPrinter;
   }

   public BeanDescription introspect(JavaType var1) {
      return this.getClassIntrospector().forSerialization(this, var1, this);
   }

   public MapperConfigBase withView(Class var1) {
      return this.withView(var1);
   }

   public MapperConfigBase with(SubtypeResolver var1) {
      return this.with(var1);
   }

   public MapperConfigBase withRootName(PropertyName var1) {
      return this.withRootName(var1);
   }

   public MapperConfigBase with(DateFormat var1) {
      return this.with(var1);
   }

   public MapperConfigBase with(ContextAttributes var1) {
      return this.with(var1);
   }

   protected MapperConfigBase _withMapperFeatures(int var1) {
      return this._withMapperFeatures(var1);
   }

   protected MapperConfigBase _withBase(BaseSettings var1) {
      return this._withBase(var1);
   }
}
