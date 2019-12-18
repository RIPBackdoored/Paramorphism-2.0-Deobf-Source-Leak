package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap$SerializerAndMapResult;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

@JacksonStdImpl
public class BeanPropertyWriter extends PropertyWriter implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final Object MARKER_FOR_EMPTY;
   protected final SerializedString _name;
   protected final PropertyName _wrapperName;
   protected final JavaType _declaredType;
   protected final JavaType _cfgSerializationType;
   protected JavaType _nonTrivialBaseType;
   protected final transient Annotations _contextAnnotations;
   protected final AnnotatedMember _member;
   protected transient Method _accessorMethod;
   protected transient Field _field;
   protected JsonSerializer _serializer;
   protected JsonSerializer _nullSerializer;
   protected TypeSerializer _typeSerializer;
   protected transient PropertySerializerMap _dynamicSerializers;
   protected final boolean _suppressNulls;
   protected final Object _suppressableValue;
   protected final Class[] _includeInViews;
   protected transient HashMap _internalSettings;

   public BeanPropertyWriter(BeanPropertyDefinition var1, AnnotatedMember var2, Annotations var3, JavaType var4, JsonSerializer var5, TypeSerializer var6, JavaType var7, boolean var8, Object var9, Class[] var10) {
      super(var1);
      this._member = var2;
      this._contextAnnotations = var3;
      this._name = new SerializedString(var1.getName());
      this._wrapperName = var1.getWrapperName();
      this._declaredType = var4;
      this._serializer = var5;
      this._dynamicSerializers = var5 == null ? PropertySerializerMap.emptyForProperties() : null;
      this._typeSerializer = var6;
      this._cfgSerializationType = var7;
      if (var2 instanceof AnnotatedField) {
         this._accessorMethod = null;
         this._field = (Field)var2.getMember();
      } else if (var2 instanceof AnnotatedMethod) {
         this._accessorMethod = (Method)var2.getMember();
         this._field = null;
      } else {
         this._accessorMethod = null;
         this._field = null;
      }

      this._suppressNulls = var8;
      this._suppressableValue = var9;
      this._nullSerializer = null;
      this._includeInViews = var10;
   }

   /** @deprecated */
   @Deprecated
   public BeanPropertyWriter(BeanPropertyDefinition var1, AnnotatedMember var2, Annotations var3, JavaType var4, JsonSerializer var5, TypeSerializer var6, JavaType var7, boolean var8, Object var9) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, var9, (Class[])null);
   }

   protected BeanPropertyWriter() {
      super(PropertyMetadata.STD_REQUIRED_OR_OPTIONAL);
      this._member = null;
      this._contextAnnotations = null;
      this._name = null;
      this._wrapperName = null;
      this._includeInViews = null;
      this._declaredType = null;
      this._serializer = null;
      this._dynamicSerializers = null;
      this._typeSerializer = null;
      this._cfgSerializationType = null;
      this._accessorMethod = null;
      this._field = null;
      this._suppressNulls = false;
      this._suppressableValue = null;
      this._nullSerializer = null;
   }

   protected BeanPropertyWriter(BeanPropertyWriter var1) {
      this(var1, var1._name);
   }

   protected BeanPropertyWriter(BeanPropertyWriter var1, PropertyName var2) {
      super((PropertyWriter)var1);
      this._name = new SerializedString(var2.getSimpleName());
      this._wrapperName = var1._wrapperName;
      this._contextAnnotations = var1._contextAnnotations;
      this._declaredType = var1._declaredType;
      this._member = var1._member;
      this._accessorMethod = var1._accessorMethod;
      this._field = var1._field;
      this._serializer = var1._serializer;
      this._nullSerializer = var1._nullSerializer;
      if (var1._internalSettings != null) {
         this._internalSettings = new HashMap(var1._internalSettings);
      }

      this._cfgSerializationType = var1._cfgSerializationType;
      this._dynamicSerializers = var1._dynamicSerializers;
      this._suppressNulls = var1._suppressNulls;
      this._suppressableValue = var1._suppressableValue;
      this._includeInViews = var1._includeInViews;
      this._typeSerializer = var1._typeSerializer;
      this._nonTrivialBaseType = var1._nonTrivialBaseType;
   }

   protected BeanPropertyWriter(BeanPropertyWriter var1, SerializedString var2) {
      super((PropertyWriter)var1);
      this._name = var2;
      this._wrapperName = var1._wrapperName;
      this._member = var1._member;
      this._contextAnnotations = var1._contextAnnotations;
      this._declaredType = var1._declaredType;
      this._accessorMethod = var1._accessorMethod;
      this._field = var1._field;
      this._serializer = var1._serializer;
      this._nullSerializer = var1._nullSerializer;
      if (var1._internalSettings != null) {
         this._internalSettings = new HashMap(var1._internalSettings);
      }

      this._cfgSerializationType = var1._cfgSerializationType;
      this._dynamicSerializers = var1._dynamicSerializers;
      this._suppressNulls = var1._suppressNulls;
      this._suppressableValue = var1._suppressableValue;
      this._includeInViews = var1._includeInViews;
      this._typeSerializer = var1._typeSerializer;
      this._nonTrivialBaseType = var1._nonTrivialBaseType;
   }

   public BeanPropertyWriter rename(NameTransformer var1) {
      String var2 = var1.transform(this._name.getValue());
      return var2.equals(this._name.toString()) ? this : this._new(PropertyName.construct(var2));
   }

   protected BeanPropertyWriter _new(PropertyName var1) {
      return new BeanPropertyWriter(this, var1);
   }

   public void assignTypeSerializer(TypeSerializer var1) {
      this._typeSerializer = var1;
   }

   public void assignSerializer(JsonSerializer var1) {
      if (this._serializer != null && this._serializer != var1) {
         throw new IllegalStateException(String.format("Cannot override _serializer: had a %s, trying to set to %s", ClassUtil.classNameOf(this._serializer), ClassUtil.classNameOf(var1)));
      } else {
         this._serializer = var1;
      }
   }

   public void assignNullSerializer(JsonSerializer var1) {
      if (this._nullSerializer != null && this._nullSerializer != var1) {
         throw new IllegalStateException(String.format("Cannot override _nullSerializer: had a %s, trying to set to %s", ClassUtil.classNameOf(this._nullSerializer), ClassUtil.classNameOf(var1)));
      } else {
         this._nullSerializer = var1;
      }
   }

   public BeanPropertyWriter unwrappingWriter(NameTransformer var1) {
      return new UnwrappingBeanPropertyWriter(this, var1);
   }

   public void setNonTrivialBaseType(JavaType var1) {
      this._nonTrivialBaseType = var1;
   }

   public void fixAccess(SerializationConfig var1) {
      this._member.fixAccess(var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
   }

   Object readResolve() {
      if (this._member instanceof AnnotatedField) {
         this._accessorMethod = null;
         this._field = (Field)this._member.getMember();
      } else if (this._member instanceof AnnotatedMethod) {
         this._accessorMethod = (Method)this._member.getMember();
         this._field = null;
      }

      if (this._serializer == null) {
         this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
      }

      return this;
   }

   public String getName() {
      return this._name.getValue();
   }

   public PropertyName getFullName() {
      return new PropertyName(this._name.getValue());
   }

   public JavaType getType() {
      return this._declaredType;
   }

   public PropertyName getWrapperName() {
      return this._wrapperName;
   }

   public Annotation getAnnotation(Class var1) {
      return this._member == null ? null : this._member.getAnnotation(var1);
   }

   public Annotation getContextAnnotation(Class var1) {
      return this._contextAnnotations == null ? null : this._contextAnnotations.get(var1);
   }

   public AnnotatedMember getMember() {
      return this._member;
   }

   protected void _depositSchemaProperty(ObjectNode var1, JsonNode var2) {
      var1.set(this.getName(), var2);
   }

   public Object getInternalSetting(Object var1) {
      return this._internalSettings == null ? null : this._internalSettings.get(var1);
   }

   public Object setInternalSetting(Object var1, Object var2) {
      if (this._internalSettings == null) {
         this._internalSettings = new HashMap();
      }

      return this._internalSettings.put(var1, var2);
   }

   public Object removeInternalSetting(Object var1) {
      Object var2 = null;
      if (this._internalSettings != null) {
         var2 = this._internalSettings.remove(var1);
         if (this._internalSettings.size() == 0) {
            this._internalSettings = null;
         }
      }

      return var2;
   }

   public SerializableString getSerializedName() {
      return this._name;
   }

   public boolean hasSerializer() {
      return this._serializer != null;
   }

   public boolean hasNullSerializer() {
      return this._nullSerializer != null;
   }

   public TypeSerializer getTypeSerializer() {
      return this._typeSerializer;
   }

   public boolean isUnwrapping() {
      return false;
   }

   public boolean willSuppressNulls() {
      return this._suppressNulls;
   }

   public boolean wouldConflictWithName(PropertyName var1) {
      if (this._wrapperName != null) {
         return this._wrapperName.equals(var1);
      } else {
         return var1.hasSimpleName(this._name.getValue()) && !var1.hasNamespace();
      }
   }

   public JsonSerializer getSerializer() {
      return this._serializer;
   }

   public JavaType getSerializationType() {
      return this._cfgSerializationType;
   }

   /** @deprecated */
   @Deprecated
   public Class getRawSerializationType() {
      return this._cfgSerializationType == null ? null : this._cfgSerializationType.getRawClass();
   }

   /** @deprecated */
   @Deprecated
   public Class getPropertyType() {
      if (this._accessorMethod != null) {
         return this._accessorMethod.getReturnType();
      } else {
         return this._field != null ? this._field.getType() : null;
      }
   }

   /** @deprecated */
   @Deprecated
   public Type getGenericPropertyType() {
      if (this._accessorMethod != null) {
         return this._accessorMethod.getGenericReturnType();
      } else {
         return this._field != null ? this._field.getGenericType() : null;
      }
   }

   public Class[] getViews() {
      return this._includeInViews;
   }

   public void serializeAsField(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception {
      Object var4 = this._accessorMethod == null ? this._field.get(var1) : this._accessorMethod.invoke(var1, (Object[])null);
      if (var4 == null) {
         if (this._nullSerializer != null) {
            var2.writeFieldName((SerializableString)this._name);
            this._nullSerializer.serialize((Object)null, var2, var3);
         }

      } else {
         JsonSerializer var5 = this._serializer;
         if (var5 == null) {
            Class var6 = var4.getClass();
            PropertySerializerMap var7 = this._dynamicSerializers;
            var5 = var7.serializerFor(var6);
            if (var5 == null) {
               var5 = this._findAndAddDynamic(var7, var6, var3);
            }
         }

         if (this._suppressableValue != null) {
            if (MARKER_FOR_EMPTY == this._suppressableValue) {
               if (var5.isEmpty(var3, var4)) {
                  return;
               }
            } else if (this._suppressableValue.equals(var4)) {
               return;
            }
         }

         if (var4 != var1 || !this._handleSelfReference(var1, var2, var3, var5)) {
            var2.writeFieldName((SerializableString)this._name);
            if (this._typeSerializer == null) {
               var5.serialize(var4, var2, var3);
            } else {
               var5.serializeWithType(var4, var2, var3, this._typeSerializer);
            }

         }
      }
   }

   public void serializeAsOmittedField(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception {
      if (!var2.canOmitFields()) {
         var2.writeOmittedField(this._name.getValue());
      }

   }

   public void serializeAsElement(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception {
      Object var4 = this._accessorMethod == null ? this._field.get(var1) : this._accessorMethod.invoke(var1, (Object[])null);
      if (var4 == null) {
         if (this._nullSerializer != null) {
            this._nullSerializer.serialize((Object)null, var2, var3);
         } else {
            var2.writeNull();
         }

      } else {
         JsonSerializer var5 = this._serializer;
         if (var5 == null) {
            Class var6 = var4.getClass();
            PropertySerializerMap var7 = this._dynamicSerializers;
            var5 = var7.serializerFor(var6);
            if (var5 == null) {
               var5 = this._findAndAddDynamic(var7, var6, var3);
            }
         }

         if (this._suppressableValue != null) {
            if (MARKER_FOR_EMPTY == this._suppressableValue) {
               if (var5.isEmpty(var3, var4)) {
                  this.serializeAsPlaceholder(var1, var2, var3);
                  return;
               }
            } else if (this._suppressableValue.equals(var4)) {
               this.serializeAsPlaceholder(var1, var2, var3);
               return;
            }
         }

         if (var4 != var1 || !this._handleSelfReference(var1, var2, var3, var5)) {
            if (this._typeSerializer == null) {
               var5.serialize(var4, var2, var3);
            } else {
               var5.serializeWithType(var4, var2, var3, this._typeSerializer);
            }

         }
      }
   }

   public void serializeAsPlaceholder(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception {
      if (this._nullSerializer != null) {
         this._nullSerializer.serialize((Object)null, var2, var3);
      } else {
         var2.writeNull();
      }

   }

   public void depositSchemaProperty(JsonObjectFormatVisitor var1, SerializerProvider var2) throws JsonMappingException {
      if (var1 != null) {
         if (this.isRequired()) {
            var1.property(this);
         } else {
            var1.optionalProperty(this);
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public void depositSchemaProperty(ObjectNode var1, SerializerProvider var2) throws JsonMappingException {
      JavaType var3 = this.getSerializationType();
      Type var4 = (Type)(var3 == null ? this.getType() : var3.getRawClass());
      JsonSerializer var6 = this.getSerializer();
      if (var6 == null) {
         var6 = var2.findValueSerializer((JavaType)this.getType(), this);
      }

      boolean var7 = !this.isRequired();
      JsonNode var5;
      if (var6 instanceof SchemaAware) {
         var5 = ((SchemaAware)var6).getSchema(var2, var4, var7);
      } else {
         var5 = JsonSchema.getDefaultSchemaNode();
      }

      this._depositSchemaProperty(var1, var5);
   }

   protected JsonSerializer _findAndAddDynamic(PropertySerializerMap var1, Class var2, SerializerProvider var3) throws JsonMappingException {
      PropertySerializerMap$SerializerAndMapResult var4;
      if (this._nonTrivialBaseType != null) {
         JavaType var5 = var3.constructSpecializedType(this._nonTrivialBaseType, var2);
         var4 = var1.findAndAddPrimarySerializer((JavaType)var5, var3, this);
      } else {
         var4 = var1.findAndAddPrimarySerializer((Class)var2, var3, this);
      }

      if (var1 != var4.map) {
         this._dynamicSerializers = var4.map;
      }

      return var4.serializer;
   }

   public final Object get(Object var1) throws Exception {
      return this._accessorMethod == null ? this._field.get(var1) : this._accessorMethod.invoke(var1, (Object[])null);
   }

   protected boolean _handleSelfReference(Object var1, JsonGenerator var2, SerializerProvider var3, JsonSerializer var4) throws JsonMappingException {
      if (var3.isEnabled(SerializationFeature.FAIL_ON_SELF_REFERENCES) && !var4.usesObjectId() && var4 instanceof BeanSerializerBase) {
         var3.reportBadDefinition(this.getType(), "Direct self-reference leading to cycle");
      }

      return false;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(40);
      var1.append("property '").append(this.getName()).append("' (");
      if (this._accessorMethod != null) {
         var1.append("via method ").append(this._accessorMethod.getDeclaringClass().getName()).append("#").append(this._accessorMethod.getName());
      } else if (this._field != null) {
         var1.append("field \"").append(this._field.getDeclaringClass().getName()).append("#").append(this._field.getName());
      } else {
         var1.append("virtual");
      }

      if (this._serializer == null) {
         var1.append(", no static serializer");
      } else {
         var1.append(", static serializer of type " + this._serializer.getClass().getName());
      }

      var1.append(')');
      return var1.toString();
   }

   static {
      MARKER_FOR_EMPTY = JsonInclude$Include.NON_EMPTY;
   }
}
