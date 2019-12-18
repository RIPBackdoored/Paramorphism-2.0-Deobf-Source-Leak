package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.Module$SetupContext;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SimpleModule extends Module implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final String _name;
   protected final Version _version;
   protected SimpleSerializers _serializers;
   protected SimpleDeserializers _deserializers;
   protected SimpleSerializers _keySerializers;
   protected SimpleKeyDeserializers _keyDeserializers;
   protected SimpleAbstractTypeResolver _abstractTypes;
   protected SimpleValueInstantiators _valueInstantiators;
   protected BeanDeserializerModifier _deserializerModifier;
   protected BeanSerializerModifier _serializerModifier;
   protected HashMap _mixins;
   protected LinkedHashSet _subtypes;
   protected PropertyNamingStrategy _namingStrategy;

   public SimpleModule() {
      super();
      this._serializers = null;
      this._deserializers = null;
      this._keySerializers = null;
      this._keyDeserializers = null;
      this._abstractTypes = null;
      this._valueInstantiators = null;
      this._deserializerModifier = null;
      this._serializerModifier = null;
      this._mixins = null;
      this._subtypes = null;
      this._namingStrategy = null;
      this._name = this.getClass() == SimpleModule.class ? "SimpleModule-" + System.identityHashCode(this) : this.getClass().getName();
      this._version = Version.unknownVersion();
   }

   public SimpleModule(String var1) {
      this(var1, Version.unknownVersion());
   }

   public SimpleModule(Version var1) {
      super();
      this._serializers = null;
      this._deserializers = null;
      this._keySerializers = null;
      this._keyDeserializers = null;
      this._abstractTypes = null;
      this._valueInstantiators = null;
      this._deserializerModifier = null;
      this._serializerModifier = null;
      this._mixins = null;
      this._subtypes = null;
      this._namingStrategy = null;
      this._name = var1.getArtifactId();
      this._version = var1;
   }

   public SimpleModule(String var1, Version var2) {
      super();
      this._serializers = null;
      this._deserializers = null;
      this._keySerializers = null;
      this._keyDeserializers = null;
      this._abstractTypes = null;
      this._valueInstantiators = null;
      this._deserializerModifier = null;
      this._serializerModifier = null;
      this._mixins = null;
      this._subtypes = null;
      this._namingStrategy = null;
      this._name = var1;
      this._version = var2;
   }

   public SimpleModule(String var1, Version var2, Map var3) {
      this(var1, var2, var3, (List)null);
   }

   public SimpleModule(String var1, Version var2, List var3) {
      this(var1, var2, (Map)null, var3);
   }

   public SimpleModule(String var1, Version var2, Map var3, List var4) {
      super();
      this._serializers = null;
      this._deserializers = null;
      this._keySerializers = null;
      this._keyDeserializers = null;
      this._abstractTypes = null;
      this._valueInstantiators = null;
      this._deserializerModifier = null;
      this._serializerModifier = null;
      this._mixins = null;
      this._subtypes = null;
      this._namingStrategy = null;
      this._name = var1;
      this._version = var2;
      if (var3 != null) {
         this._deserializers = new SimpleDeserializers(var3);
      }

      if (var4 != null) {
         this._serializers = new SimpleSerializers(var4);
      }

   }

   public Object getTypeId() {
      return this.getClass() == SimpleModule.class ? null : super.getTypeId();
   }

   public void setSerializers(SimpleSerializers var1) {
      this._serializers = var1;
   }

   public void setDeserializers(SimpleDeserializers var1) {
      this._deserializers = var1;
   }

   public void setKeySerializers(SimpleSerializers var1) {
      this._keySerializers = var1;
   }

   public void setKeyDeserializers(SimpleKeyDeserializers var1) {
      this._keyDeserializers = var1;
   }

   public void setAbstractTypes(SimpleAbstractTypeResolver var1) {
      this._abstractTypes = var1;
   }

   public void setValueInstantiators(SimpleValueInstantiators var1) {
      this._valueInstantiators = var1;
   }

   public SimpleModule setDeserializerModifier(BeanDeserializerModifier var1) {
      this._deserializerModifier = var1;
      return this;
   }

   public SimpleModule setSerializerModifier(BeanSerializerModifier var1) {
      this._serializerModifier = var1;
      return this;
   }

   protected SimpleModule setNamingStrategy(PropertyNamingStrategy var1) {
      this._namingStrategy = var1;
      return this;
   }

   public SimpleModule addSerializer(JsonSerializer var1) {
      this._checkNotNull(var1, "serializer");
      if (this._serializers == null) {
         this._serializers = new SimpleSerializers();
      }

      this._serializers.addSerializer(var1);
      return this;
   }

   public SimpleModule addSerializer(Class var1, JsonSerializer var2) {
      this._checkNotNull(var1, "type to register serializer for");
      this._checkNotNull(var2, "serializer");
      if (this._serializers == null) {
         this._serializers = new SimpleSerializers();
      }

      this._serializers.addSerializer(var1, var2);
      return this;
   }

   public SimpleModule addKeySerializer(Class var1, JsonSerializer var2) {
      this._checkNotNull(var1, "type to register key serializer for");
      this._checkNotNull(var2, "key serializer");
      if (this._keySerializers == null) {
         this._keySerializers = new SimpleSerializers();
      }

      this._keySerializers.addSerializer(var1, var2);
      return this;
   }

   public SimpleModule addDeserializer(Class var1, JsonDeserializer var2) {
      this._checkNotNull(var1, "type to register deserializer for");
      this._checkNotNull(var2, "deserializer");
      if (this._deserializers == null) {
         this._deserializers = new SimpleDeserializers();
      }

      this._deserializers.addDeserializer(var1, var2);
      return this;
   }

   public SimpleModule addKeyDeserializer(Class var1, KeyDeserializer var2) {
      this._checkNotNull(var1, "type to register key deserializer for");
      this._checkNotNull(var2, "key deserializer");
      if (this._keyDeserializers == null) {
         this._keyDeserializers = new SimpleKeyDeserializers();
      }

      this._keyDeserializers.addDeserializer(var1, var2);
      return this;
   }

   public SimpleModule addAbstractTypeMapping(Class var1, Class var2) {
      this._checkNotNull(var1, "abstract type to map");
      this._checkNotNull(var2, "concrete type to map to");
      if (this._abstractTypes == null) {
         this._abstractTypes = new SimpleAbstractTypeResolver();
      }

      this._abstractTypes = this._abstractTypes.addMapping(var1, var2);
      return this;
   }

   public SimpleModule registerSubtypes(Class... var1) {
      if (this._subtypes == null) {
         this._subtypes = new LinkedHashSet();
      }

      Class[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class var5 = var2[var4];
         this._checkNotNull(var5, "subtype to register");
         this._subtypes.add(new NamedType(var5));
      }

      return this;
   }

   public SimpleModule registerSubtypes(NamedType... var1) {
      if (this._subtypes == null) {
         this._subtypes = new LinkedHashSet();
      }

      NamedType[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         NamedType var5 = var2[var4];
         this._checkNotNull(var5, "subtype to register");
         this._subtypes.add(var5);
      }

      return this;
   }

   public SimpleModule registerSubtypes(Collection var1) {
      if (this._subtypes == null) {
         this._subtypes = new LinkedHashSet();
      }

      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Class var3 = (Class)var2.next();
         this._checkNotNull(var3, "subtype to register");
         this._subtypes.add(new NamedType(var3));
      }

      return this;
   }

   public SimpleModule addValueInstantiator(Class var1, ValueInstantiator var2) {
      this._checkNotNull(var1, "class to register value instantiator for");
      this._checkNotNull(var2, "value instantiator");
      if (this._valueInstantiators == null) {
         this._valueInstantiators = new SimpleValueInstantiators();
      }

      this._valueInstantiators = this._valueInstantiators.addValueInstantiator(var1, var2);
      return this;
   }

   public SimpleModule setMixInAnnotation(Class var1, Class var2) {
      this._checkNotNull(var1, "target type");
      this._checkNotNull(var2, "mixin class");
      if (this._mixins == null) {
         this._mixins = new HashMap();
      }

      this._mixins.put(var1, var2);
      return this;
   }

   public String getModuleName() {
      return this._name;
   }

   public void setupModule(Module$SetupContext var1) {
      if (this._serializers != null) {
         var1.addSerializers(this._serializers);
      }

      if (this._deserializers != null) {
         var1.addDeserializers(this._deserializers);
      }

      if (this._keySerializers != null) {
         var1.addKeySerializers(this._keySerializers);
      }

      if (this._keyDeserializers != null) {
         var1.addKeyDeserializers(this._keyDeserializers);
      }

      if (this._abstractTypes != null) {
         var1.addAbstractTypeResolver(this._abstractTypes);
      }

      if (this._valueInstantiators != null) {
         var1.addValueInstantiators(this._valueInstantiators);
      }

      if (this._deserializerModifier != null) {
         var1.addBeanDeserializerModifier(this._deserializerModifier);
      }

      if (this._serializerModifier != null) {
         var1.addBeanSerializerModifier(this._serializerModifier);
      }

      if (this._subtypes != null && this._subtypes.size() > 0) {
         var1.registerSubtypes((NamedType[])this._subtypes.toArray(new NamedType[this._subtypes.size()]));
      }

      if (this._namingStrategy != null) {
         var1.setNamingStrategy(this._namingStrategy);
      }

      if (this._mixins != null) {
         Iterator var2 = this._mixins.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            var1.setMixInAnnotations((Class)var3.getKey(), (Class)var3.getValue());
         }
      }

   }

   public Version version() {
      return this._version;
   }

   protected void _checkNotNull(Object var1, String var2) {
      if (var1 == null) {
         throw new IllegalArgumentException(String.format("Cannot pass `null` as %s", var2));
      }
   }
}
