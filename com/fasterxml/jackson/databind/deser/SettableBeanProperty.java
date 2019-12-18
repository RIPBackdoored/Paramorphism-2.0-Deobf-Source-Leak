package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.impl.FailingDeserializer;
import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ConcreteBeanPropertyBase;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.ViewMatcher;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;

public abstract class SettableBeanProperty extends ConcreteBeanPropertyBase implements Serializable {
   protected static final JsonDeserializer MISSING_VALUE_DESERIALIZER = new FailingDeserializer("No _valueDeserializer assigned");
   protected final PropertyName _propName;
   protected final JavaType _type;
   protected final PropertyName _wrapperName;
   protected final transient Annotations _contextAnnotations;
   protected final JsonDeserializer _valueDeserializer;
   protected final TypeDeserializer _valueTypeDeserializer;
   protected final NullValueProvider _nullProvider;
   protected String _managedReferenceName;
   protected ObjectIdInfo _objectIdInfo;
   protected ViewMatcher _viewMatcher;
   protected int _propertyIndex;

   protected SettableBeanProperty(BeanPropertyDefinition var1, JavaType var2, TypeDeserializer var3, Annotations var4) {
      this(var1.getFullName(), var2, var1.getWrapperName(), var3, var4, var1.getMetadata());
   }

   protected SettableBeanProperty(PropertyName var1, JavaType var2, PropertyName var3, TypeDeserializer var4, Annotations var5, PropertyMetadata var6) {
      super(var6);
      this._propertyIndex = -1;
      if (var1 == null) {
         this._propName = PropertyName.NO_NAME;
      } else {
         this._propName = var1.internSimpleName();
      }

      this._type = var2;
      this._wrapperName = var3;
      this._contextAnnotations = var5;
      this._viewMatcher = null;
      if (var4 != null) {
         var4 = var4.forProperty(this);
      }

      this._valueTypeDeserializer = var4;
      this._valueDeserializer = MISSING_VALUE_DESERIALIZER;
      this._nullProvider = MISSING_VALUE_DESERIALIZER;
   }

   protected SettableBeanProperty(PropertyName var1, JavaType var2, PropertyMetadata var3, JsonDeserializer var4) {
      super(var3);
      this._propertyIndex = -1;
      if (var1 == null) {
         this._propName = PropertyName.NO_NAME;
      } else {
         this._propName = var1.internSimpleName();
      }

      this._type = var2;
      this._wrapperName = null;
      this._contextAnnotations = null;
      this._viewMatcher = null;
      this._valueTypeDeserializer = null;
      this._valueDeserializer = var4;
      this._nullProvider = var4;
   }

   protected SettableBeanProperty(SettableBeanProperty var1) {
      super((ConcreteBeanPropertyBase)var1);
      this._propertyIndex = -1;
      this._propName = var1._propName;
      this._type = var1._type;
      this._wrapperName = var1._wrapperName;
      this._contextAnnotations = var1._contextAnnotations;
      this._valueDeserializer = var1._valueDeserializer;
      this._valueTypeDeserializer = var1._valueTypeDeserializer;
      this._managedReferenceName = var1._managedReferenceName;
      this._propertyIndex = var1._propertyIndex;
      this._viewMatcher = var1._viewMatcher;
      this._nullProvider = var1._nullProvider;
   }

   protected SettableBeanProperty(SettableBeanProperty var1, JsonDeserializer var2, NullValueProvider var3) {
      super((ConcreteBeanPropertyBase)var1);
      this._propertyIndex = -1;
      this._propName = var1._propName;
      this._type = var1._type;
      this._wrapperName = var1._wrapperName;
      this._contextAnnotations = var1._contextAnnotations;
      this._valueTypeDeserializer = var1._valueTypeDeserializer;
      this._managedReferenceName = var1._managedReferenceName;
      this._propertyIndex = var1._propertyIndex;
      if (var2 == null) {
         this._valueDeserializer = MISSING_VALUE_DESERIALIZER;
      } else {
         this._valueDeserializer = var2;
      }

      this._viewMatcher = var1._viewMatcher;
      if (var3 == MISSING_VALUE_DESERIALIZER) {
         var3 = this._valueDeserializer;
      }

      this._nullProvider = (NullValueProvider)var3;
   }

   protected SettableBeanProperty(SettableBeanProperty var1, PropertyName var2) {
      super((ConcreteBeanPropertyBase)var1);
      this._propertyIndex = -1;
      this._propName = var2;
      this._type = var1._type;
      this._wrapperName = var1._wrapperName;
      this._contextAnnotations = var1._contextAnnotations;
      this._valueDeserializer = var1._valueDeserializer;
      this._valueTypeDeserializer = var1._valueTypeDeserializer;
      this._managedReferenceName = var1._managedReferenceName;
      this._propertyIndex = var1._propertyIndex;
      this._viewMatcher = var1._viewMatcher;
      this._nullProvider = var1._nullProvider;
   }

   public abstract SettableBeanProperty withValueDeserializer(JsonDeserializer var1);

   public abstract SettableBeanProperty withName(PropertyName var1);

   public SettableBeanProperty withSimpleName(String var1) {
      PropertyName var2 = this._propName == null ? new PropertyName(var1) : this._propName.withSimpleName(var1);
      return var2 == this._propName ? this : this.withName(var2);
   }

   public abstract SettableBeanProperty withNullProvider(NullValueProvider var1);

   public void setManagedReferenceName(String var1) {
      this._managedReferenceName = var1;
   }

   public void setObjectIdInfo(ObjectIdInfo var1) {
      this._objectIdInfo = var1;
   }

   public void setViews(Class[] var1) {
      if (var1 == null) {
         this._viewMatcher = null;
      } else {
         this._viewMatcher = ViewMatcher.construct(var1);
      }

   }

   public void assignIndex(int var1) {
      if (this._propertyIndex != -1) {
         throw new IllegalStateException("Property '" + this.getName() + "' already had index (" + this._propertyIndex + "), trying to assign " + var1);
      } else {
         this._propertyIndex = var1;
      }
   }

   public void fixAccess(DeserializationConfig var1) {
   }

   public void markAsIgnorable() {
   }

   public boolean isIgnorable() {
      return false;
   }

   public final String getName() {
      return this._propName.getSimpleName();
   }

   public PropertyName getFullName() {
      return this._propName;
   }

   public JavaType getType() {
      return this._type;
   }

   public PropertyName getWrapperName() {
      return this._wrapperName;
   }

   public abstract AnnotatedMember getMember();

   public abstract Annotation getAnnotation(Class var1);

   public Annotation getContextAnnotation(Class var1) {
      return this._contextAnnotations.get(var1);
   }

   public void depositSchemaProperty(JsonObjectFormatVisitor var1, SerializerProvider var2) throws JsonMappingException {
      if (this.isRequired()) {
         var1.property(this);
      } else {
         var1.optionalProperty(this);
      }

   }

   protected Class getDeclaringClass() {
      return this.getMember().getDeclaringClass();
   }

   public String getManagedReferenceName() {
      return this._managedReferenceName;
   }

   public ObjectIdInfo getObjectIdInfo() {
      return this._objectIdInfo;
   }

   public boolean hasValueDeserializer() {
      return this._valueDeserializer != null && this._valueDeserializer != MISSING_VALUE_DESERIALIZER;
   }

   public boolean hasValueTypeDeserializer() {
      return this._valueTypeDeserializer != null;
   }

   public JsonDeserializer getValueDeserializer() {
      JsonDeserializer var1 = this._valueDeserializer;
      return var1 == MISSING_VALUE_DESERIALIZER ? null : var1;
   }

   public TypeDeserializer getValueTypeDeserializer() {
      return this._valueTypeDeserializer;
   }

   public NullValueProvider getNullValueProvider() {
      return this._nullProvider;
   }

   public boolean visibleInView(Class var1) {
      return this._viewMatcher == null || this._viewMatcher.isVisibleForView(var1);
   }

   public boolean hasViews() {
      return this._viewMatcher != null;
   }

   public int getPropertyIndex() {
      return this._propertyIndex;
   }

   public int getCreatorIndex() {
      throw new IllegalStateException(String.format("Internal error: no creator index for property '%s' (of type %s)", this.getName(), this.getClass().getName()));
   }

   public Object getInjectableValueId() {
      return null;
   }

   public abstract void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException;

   public abstract Object deserializeSetAndReturn(JsonParser var1, DeserializationContext var2, Object var3) throws IOException;

   public abstract void set(Object var1, Object var2) throws IOException;

   public abstract Object setAndReturn(Object var1, Object var2) throws IOException;

   public final Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (var1.hasToken(JsonToken.VALUE_NULL)) {
         return this._nullProvider.getNullValue(var2);
      } else if (this._valueTypeDeserializer != null) {
         return this._valueDeserializer.deserializeWithType(var1, var2, this._valueTypeDeserializer);
      } else {
         Object var3 = this._valueDeserializer.deserialize(var1, var2);
         if (var3 == null) {
            var3 = this._nullProvider.getNullValue(var2);
         }

         return var3;
      }
   }

   public final Object deserializeWith(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      if (var1.hasToken(JsonToken.VALUE_NULL)) {
         return NullsConstantProvider.isSkipper(this._nullProvider) ? var3 : this._nullProvider.getNullValue(var2);
      } else {
         if (this._valueTypeDeserializer != null) {
            var2.reportBadDefinition(this.getType(), String.format("Cannot merge polymorphic property '%s'", this.getName()));
         }

         Object var4 = this._valueDeserializer.deserialize(var1, var2, var3);
         if (var4 == null) {
            if (NullsConstantProvider.isSkipper(this._nullProvider)) {
               return var3;
            }

            var4 = this._nullProvider.getNullValue(var2);
         }

         return var4;
      }
   }

   protected void _throwAsIOE(JsonParser var1, Exception var2, Object var3) throws IOException {
      if (var2 instanceof IllegalArgumentException) {
         String var4 = ClassUtil.classNameOf(var3);
         StringBuilder var5 = (new StringBuilder("Problem deserializing property '")).append(this.getName()).append("' (expected type: ").append(this.getType()).append("; actual type: ").append(var4).append(")");
         String var6 = var2.getMessage();
         if (var6 != null) {
            var5.append(", problem: ").append(var6);
         } else {
            var5.append(" (no error message provided)");
         }

         throw JsonMappingException.from((JsonParser)var1, var5.toString(), var2);
      } else {
         this._throwAsIOE(var1, var2);
      }
   }

   protected IOException _throwAsIOE(JsonParser var1, Exception var2) throws IOException {
      ClassUtil.throwIfIOE(var2);
      ClassUtil.throwIfRTE(var2);
      Throwable var3 = ClassUtil.getRootCause(var2);
      throw JsonMappingException.from(var1, var3.getMessage(), var3);
   }

   /** @deprecated */
   @Deprecated
   protected IOException _throwAsIOE(Exception var1) throws IOException {
      return this._throwAsIOE((JsonParser)null, var1);
   }

   protected void _throwAsIOE(Exception var1, Object var2) throws IOException {
      this._throwAsIOE((JsonParser)null, var1, var2);
   }

   public String toString() {
      return "[property '" + this.getName() + "']";
   }
}
