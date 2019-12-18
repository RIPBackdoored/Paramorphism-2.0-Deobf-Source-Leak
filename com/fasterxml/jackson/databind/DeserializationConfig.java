package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$Feature;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.ConfigOverrides;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.util.LinkedNode;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.Serializable;
import java.util.Collection;

public final class DeserializationConfig extends MapperConfigBase implements Serializable {
   private static final long serialVersionUID = 2L;
   protected final LinkedNode _problemHandlers;
   protected final JsonNodeFactory _nodeFactory;
   protected final int _deserFeatures;
   protected final int _parserFeatures;
   protected final int _parserFeaturesToChange;
   protected final int _formatReadFeatures;
   protected final int _formatReadFeaturesToChange;

   public DeserializationConfig(BaseSettings var1, SubtypeResolver var2, SimpleMixInResolver var3, RootNameLookup var4, ConfigOverrides var5) {
      super(var1, var2, var3, var4, var5);
      this._deserFeatures = collectFeatureDefaults(DeserializationFeature.class);
      this._nodeFactory = JsonNodeFactory.instance;
      this._problemHandlers = null;
      this._parserFeatures = 0;
      this._parserFeaturesToChange = 0;
      this._formatReadFeatures = 0;
      this._formatReadFeaturesToChange = 0;
   }

   protected DeserializationConfig(DeserializationConfig var1, SimpleMixInResolver var2, RootNameLookup var3, ConfigOverrides var4) {
      super(var1, var2, var3, var4);
      this._deserFeatures = var1._deserFeatures;
      this._problemHandlers = var1._problemHandlers;
      this._nodeFactory = var1._nodeFactory;
      this._parserFeatures = var1._parserFeatures;
      this._parserFeaturesToChange = var1._parserFeaturesToChange;
      this._formatReadFeatures = var1._formatReadFeatures;
      this._formatReadFeaturesToChange = var1._formatReadFeaturesToChange;
   }

   private DeserializationConfig(DeserializationConfig var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2);
      this._deserFeatures = var3;
      this._nodeFactory = var1._nodeFactory;
      this._problemHandlers = var1._problemHandlers;
      this._parserFeatures = var4;
      this._parserFeaturesToChange = var5;
      this._formatReadFeatures = var6;
      this._formatReadFeaturesToChange = var7;
   }

   private DeserializationConfig(DeserializationConfig var1, SubtypeResolver var2) {
      super(var1, (SubtypeResolver)var2);
      this._deserFeatures = var1._deserFeatures;
      this._nodeFactory = var1._nodeFactory;
      this._problemHandlers = var1._problemHandlers;
      this._parserFeatures = var1._parserFeatures;
      this._parserFeaturesToChange = var1._parserFeaturesToChange;
      this._formatReadFeatures = var1._formatReadFeatures;
      this._formatReadFeaturesToChange = var1._formatReadFeaturesToChange;
   }

   private DeserializationConfig(DeserializationConfig var1, BaseSettings var2) {
      super(var1, (BaseSettings)var2);
      this._deserFeatures = var1._deserFeatures;
      this._nodeFactory = var1._nodeFactory;
      this._problemHandlers = var1._problemHandlers;
      this._parserFeatures = var1._parserFeatures;
      this._parserFeaturesToChange = var1._parserFeaturesToChange;
      this._formatReadFeatures = var1._formatReadFeatures;
      this._formatReadFeaturesToChange = var1._formatReadFeaturesToChange;
   }

   private DeserializationConfig(DeserializationConfig var1, JsonNodeFactory var2) {
      super(var1);
      this._deserFeatures = var1._deserFeatures;
      this._problemHandlers = var1._problemHandlers;
      this._nodeFactory = var2;
      this._parserFeatures = var1._parserFeatures;
      this._parserFeaturesToChange = var1._parserFeaturesToChange;
      this._formatReadFeatures = var1._formatReadFeatures;
      this._formatReadFeaturesToChange = var1._formatReadFeaturesToChange;
   }

   private DeserializationConfig(DeserializationConfig var1, LinkedNode var2) {
      super(var1);
      this._deserFeatures = var1._deserFeatures;
      this._problemHandlers = var2;
      this._nodeFactory = var1._nodeFactory;
      this._parserFeatures = var1._parserFeatures;
      this._parserFeaturesToChange = var1._parserFeaturesToChange;
      this._formatReadFeatures = var1._formatReadFeatures;
      this._formatReadFeaturesToChange = var1._formatReadFeaturesToChange;
   }

   private DeserializationConfig(DeserializationConfig var1, PropertyName var2) {
      super(var1, (PropertyName)var2);
      this._deserFeatures = var1._deserFeatures;
      this._problemHandlers = var1._problemHandlers;
      this._nodeFactory = var1._nodeFactory;
      this._parserFeatures = var1._parserFeatures;
      this._parserFeaturesToChange = var1._parserFeaturesToChange;
      this._formatReadFeatures = var1._formatReadFeatures;
      this._formatReadFeaturesToChange = var1._formatReadFeaturesToChange;
   }

   private DeserializationConfig(DeserializationConfig var1, Class var2) {
      super(var1, (Class)var2);
      this._deserFeatures = var1._deserFeatures;
      this._problemHandlers = var1._problemHandlers;
      this._nodeFactory = var1._nodeFactory;
      this._parserFeatures = var1._parserFeatures;
      this._parserFeaturesToChange = var1._parserFeaturesToChange;
      this._formatReadFeatures = var1._formatReadFeatures;
      this._formatReadFeaturesToChange = var1._formatReadFeaturesToChange;
   }

   protected DeserializationConfig(DeserializationConfig var1, ContextAttributes var2) {
      super(var1, (ContextAttributes)var2);
      this._deserFeatures = var1._deserFeatures;
      this._problemHandlers = var1._problemHandlers;
      this._nodeFactory = var1._nodeFactory;
      this._parserFeatures = var1._parserFeatures;
      this._parserFeaturesToChange = var1._parserFeaturesToChange;
      this._formatReadFeatures = var1._formatReadFeatures;
      this._formatReadFeaturesToChange = var1._formatReadFeaturesToChange;
   }

   protected DeserializationConfig(DeserializationConfig var1, SimpleMixInResolver var2) {
      super(var1, (SimpleMixInResolver)var2);
      this._deserFeatures = var1._deserFeatures;
      this._problemHandlers = var1._problemHandlers;
      this._nodeFactory = var1._nodeFactory;
      this._parserFeatures = var1._parserFeatures;
      this._parserFeaturesToChange = var1._parserFeaturesToChange;
      this._formatReadFeatures = var1._formatReadFeatures;
      this._formatReadFeaturesToChange = var1._formatReadFeaturesToChange;
   }

   protected BaseSettings getBaseSettings() {
      return this._base;
   }

   protected final DeserializationConfig _withBase(BaseSettings var1) {
      return this._base == var1 ? this : new DeserializationConfig(this, var1);
   }

   protected final DeserializationConfig _withMapperFeatures(int var1) {
      return new DeserializationConfig(this, var1, this._deserFeatures, this._parserFeatures, this._parserFeaturesToChange, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig with(SubtypeResolver var1) {
      return this._subtypeResolver == var1 ? this : new DeserializationConfig(this, var1);
   }

   public DeserializationConfig withRootName(PropertyName var1) {
      if (var1 == null) {
         if (this._rootName == null) {
            return this;
         }
      } else if (var1.equals(this._rootName)) {
         return this;
      }

      return new DeserializationConfig(this, var1);
   }

   public DeserializationConfig withView(Class var1) {
      return this._view == var1 ? this : new DeserializationConfig(this, var1);
   }

   public DeserializationConfig with(ContextAttributes var1) {
      return var1 == this._attributes ? this : new DeserializationConfig(this, var1);
   }

   public DeserializationConfig with(DeserializationFeature var1) {
      int var2 = this._deserFeatures | var1.getMask();
      return var2 == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, var2, this._parserFeatures, this._parserFeaturesToChange, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig with(DeserializationFeature var1, DeserializationFeature... var2) {
      int var3 = this._deserFeatures | var1.getMask();
      DeserializationFeature[] var4 = var2;
      int var5 = var2.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         DeserializationFeature var7 = var4[var6];
         var3 |= var7.getMask();
      }

      return var3 == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, var3, this._parserFeatures, this._parserFeaturesToChange, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig withFeatures(DeserializationFeature... var1) {
      int var2 = this._deserFeatures;
      DeserializationFeature[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         DeserializationFeature var6 = var3[var5];
         var2 |= var6.getMask();
      }

      return var2 == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, var2, this._parserFeatures, this._parserFeaturesToChange, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig without(DeserializationFeature var1) {
      int var2 = this._deserFeatures & ~var1.getMask();
      return var2 == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, var2, this._parserFeatures, this._parserFeaturesToChange, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig without(DeserializationFeature var1, DeserializationFeature... var2) {
      int var3 = this._deserFeatures & ~var1.getMask();
      DeserializationFeature[] var4 = var2;
      int var5 = var2.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         DeserializationFeature var7 = var4[var6];
         var3 &= ~var7.getMask();
      }

      return var3 == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, var3, this._parserFeatures, this._parserFeaturesToChange, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig withoutFeatures(DeserializationFeature... var1) {
      int var2 = this._deserFeatures;
      DeserializationFeature[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         DeserializationFeature var6 = var3[var5];
         var2 &= ~var6.getMask();
      }

      return var2 == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, var2, this._parserFeatures, this._parserFeaturesToChange, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig with(JsonParser$Feature var1) {
      int var2 = this._parserFeatures | var1.getMask();
      int var3 = this._parserFeaturesToChange | var1.getMask();
      return this._parserFeatures == var2 && this._parserFeaturesToChange == var3 ? this : new DeserializationConfig(this, this._mapperFeatures, this._deserFeatures, var2, var3, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig withFeatures(JsonParser$Feature... var1) {
      int var2 = this._parserFeatures;
      int var3 = this._parserFeaturesToChange;
      JsonParser$Feature[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         JsonParser$Feature var7 = var4[var6];
         int var8 = var7.getMask();
         var2 |= var8;
         var3 |= var8;
      }

      return this._parserFeatures == var2 && this._parserFeaturesToChange == var3 ? this : new DeserializationConfig(this, this._mapperFeatures, this._deserFeatures, var2, var3, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig without(JsonParser$Feature var1) {
      int var2 = this._parserFeatures & ~var1.getMask();
      int var3 = this._parserFeaturesToChange | var1.getMask();
      return this._parserFeatures == var2 && this._parserFeaturesToChange == var3 ? this : new DeserializationConfig(this, this._mapperFeatures, this._deserFeatures, var2, var3, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig withoutFeatures(JsonParser$Feature... var1) {
      int var2 = this._parserFeatures;
      int var3 = this._parserFeaturesToChange;
      JsonParser$Feature[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         JsonParser$Feature var7 = var4[var6];
         int var8 = var7.getMask();
         var2 &= ~var8;
         var3 |= var8;
      }

      return this._parserFeatures == var2 && this._parserFeaturesToChange == var3 ? this : new DeserializationConfig(this, this._mapperFeatures, this._deserFeatures, var2, var3, this._formatReadFeatures, this._formatReadFeaturesToChange);
   }

   public DeserializationConfig with(FormatFeature var1) {
      int var2 = this._formatReadFeatures | var1.getMask();
      int var3 = this._formatReadFeaturesToChange | var1.getMask();
      return this._formatReadFeatures == var2 && this._formatReadFeaturesToChange == var3 ? this : new DeserializationConfig(this, this._mapperFeatures, this._deserFeatures, this._parserFeatures, this._parserFeaturesToChange, var2, var3);
   }

   public DeserializationConfig withFeatures(FormatFeature... var1) {
      int var2 = this._formatReadFeatures;
      int var3 = this._formatReadFeaturesToChange;
      FormatFeature[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         FormatFeature var7 = var4[var6];
         int var8 = var7.getMask();
         var2 |= var8;
         var3 |= var8;
      }

      return this._formatReadFeatures == var2 && this._formatReadFeaturesToChange == var3 ? this : new DeserializationConfig(this, this._mapperFeatures, this._deserFeatures, this._parserFeatures, this._parserFeaturesToChange, var2, var3);
   }

   public DeserializationConfig without(FormatFeature var1) {
      int var2 = this._formatReadFeatures & ~var1.getMask();
      int var3 = this._formatReadFeaturesToChange | var1.getMask();
      return this._formatReadFeatures == var2 && this._formatReadFeaturesToChange == var3 ? this : new DeserializationConfig(this, this._mapperFeatures, this._deserFeatures, this._parserFeatures, this._parserFeaturesToChange, var2, var3);
   }

   public DeserializationConfig withoutFeatures(FormatFeature... var1) {
      int var2 = this._formatReadFeatures;
      int var3 = this._formatReadFeaturesToChange;
      FormatFeature[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         FormatFeature var7 = var4[var6];
         int var8 = var7.getMask();
         var2 &= ~var8;
         var3 |= var8;
      }

      return this._formatReadFeatures == var2 && this._formatReadFeaturesToChange == var3 ? this : new DeserializationConfig(this, this._mapperFeatures, this._deserFeatures, this._parserFeatures, this._parserFeaturesToChange, var2, var3);
   }

   public DeserializationConfig with(JsonNodeFactory var1) {
      return this._nodeFactory == var1 ? this : new DeserializationConfig(this, var1);
   }

   public DeserializationConfig withHandler(DeserializationProblemHandler var1) {
      return LinkedNode.contains(this._problemHandlers, var1) ? this : new DeserializationConfig(this, new LinkedNode(var1, this._problemHandlers));
   }

   public DeserializationConfig withNoProblemHandlers() {
      return this._problemHandlers == null ? this : new DeserializationConfig(this, (LinkedNode)null);
   }

   public void initialize(JsonParser var1) {
      if (this._parserFeaturesToChange != 0) {
         var1.overrideStdFeatures(this._parserFeatures, this._parserFeaturesToChange);
      }

      if (this._formatReadFeaturesToChange != 0) {
         var1.overrideFormatFeatures(this._formatReadFeatures, this._formatReadFeaturesToChange);
      }

   }

   public boolean useRootWrapping() {
      if (this._rootName != null) {
         return !this._rootName.isEmpty();
      } else {
         return this.isEnabled(DeserializationFeature.UNWRAP_ROOT_VALUE);
      }
   }

   public final boolean isEnabled(DeserializationFeature var1) {
      return (this._deserFeatures & var1.getMask()) != 0;
   }

   public final boolean isEnabled(JsonParser$Feature var1, JsonFactory var2) {
      int var3 = var1.getMask();
      if ((this._parserFeaturesToChange & var3) != 0) {
         return (this._parserFeatures & var1.getMask()) != 0;
      } else {
         return var2.isEnabled(var1);
      }
   }

   public final boolean hasDeserializationFeatures(int var1) {
      return (this._deserFeatures & var1) == var1;
   }

   public final boolean hasSomeOfFeatures(int var1) {
      return (this._deserFeatures & var1) != 0;
   }

   public final int getDeserializationFeatures() {
      return this._deserFeatures;
   }

   public final boolean requiresFullValue() {
      return DeserializationFeature.FAIL_ON_TRAILING_TOKENS.enabledIn(this._deserFeatures);
   }

   public LinkedNode getProblemHandlers() {
      return this._problemHandlers;
   }

   public final JsonNodeFactory getNodeFactory() {
      return this._nodeFactory;
   }

   public BeanDescription introspect(JavaType var1) {
      return this.getClassIntrospector().forDeserialization(this, var1, this);
   }

   public BeanDescription introspectForCreation(JavaType var1) {
      return this.getClassIntrospector().forCreation(this, var1, this);
   }

   public BeanDescription introspectForBuilder(JavaType var1) {
      return this.getClassIntrospector().forDeserializationWithBuilder(this, var1, this);
   }

   public TypeDeserializer findTypeDeserializer(JavaType var1) throws JsonMappingException {
      BeanDescription var2 = this.introspectClassAnnotations(var1.getRawClass());
      AnnotatedClass var3 = var2.getClassInfo();
      TypeResolverBuilder var4 = this.getAnnotationIntrospector().findTypeResolver(this, var3, var1);
      Collection var5 = null;
      if (var4 == null) {
         var4 = this.getDefaultTyper(var1);
         if (var4 == null) {
            return null;
         }
      } else {
         var5 = this.getSubtypeResolver().collectAndResolveSubtypesByTypeId(this, var3);
      }

      return var4.buildTypeDeserializer(this, var1, var5);
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
