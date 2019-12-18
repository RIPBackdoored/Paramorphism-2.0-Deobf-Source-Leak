package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Shape;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException$Reference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.AnyGetterWriter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public abstract class BeanSerializerBase extends StdSerializer implements ContextualSerializer, ResolvableSerializer, JsonFormatVisitable, SchemaAware {
   protected static final PropertyName NAME_FOR_OBJECT_REF = new PropertyName("#object-ref");
   protected static final BeanPropertyWriter[] NO_PROPS = new BeanPropertyWriter[0];
   protected final JavaType _beanType;
   protected final BeanPropertyWriter[] _props;
   protected final BeanPropertyWriter[] _filteredProps;
   protected final AnyGetterWriter _anyGetterWriter;
   protected final Object _propertyFilterId;
   protected final AnnotatedMember _typeId;
   protected final ObjectIdWriter _objectIdWriter;
   protected final JsonFormat$Shape _serializationShape;

   protected BeanSerializerBase(JavaType var1, BeanSerializerBuilder var2, BeanPropertyWriter[] var3, BeanPropertyWriter[] var4) {
      super(var1);
      this._beanType = var1;
      this._props = var3;
      this._filteredProps = var4;
      if (var2 == null) {
         this._typeId = null;
         this._anyGetterWriter = null;
         this._propertyFilterId = null;
         this._objectIdWriter = null;
         this._serializationShape = null;
      } else {
         this._typeId = var2.getTypeId();
         this._anyGetterWriter = var2.getAnyGetter();
         this._propertyFilterId = var2.getFilterId();
         this._objectIdWriter = var2.getObjectIdWriter();
         JsonFormat$Value var5 = var2.getBeanDescription().findExpectedFormat((JsonFormat$Value)null);
         this._serializationShape = var5 == null ? null : var5.getShape();
      }

   }

   public BeanSerializerBase(BeanSerializerBase var1, BeanPropertyWriter[] var2, BeanPropertyWriter[] var3) {
      super(var1._handledType);
      this._beanType = var1._beanType;
      this._props = var2;
      this._filteredProps = var3;
      this._typeId = var1._typeId;
      this._anyGetterWriter = var1._anyGetterWriter;
      this._objectIdWriter = var1._objectIdWriter;
      this._propertyFilterId = var1._propertyFilterId;
      this._serializationShape = var1._serializationShape;
   }

   protected BeanSerializerBase(BeanSerializerBase var1, ObjectIdWriter var2) {
      this(var1, var2, var1._propertyFilterId);
   }

   protected BeanSerializerBase(BeanSerializerBase var1, ObjectIdWriter var2, Object var3) {
      super(var1._handledType);
      this._beanType = var1._beanType;
      this._props = var1._props;
      this._filteredProps = var1._filteredProps;
      this._typeId = var1._typeId;
      this._anyGetterWriter = var1._anyGetterWriter;
      this._objectIdWriter = var2;
      this._propertyFilterId = var3;
      this._serializationShape = var1._serializationShape;
   }

   /** @deprecated */
   @Deprecated
   protected BeanSerializerBase(BeanSerializerBase var1, String[] var2) {
      this(var1, (Set)ArrayBuilders.arrayToSet(var2));
   }

   protected BeanSerializerBase(BeanSerializerBase var1, Set var2) {
      super(var1._handledType);
      this._beanType = var1._beanType;
      BeanPropertyWriter[] var3 = var1._props;
      BeanPropertyWriter[] var4 = var1._filteredProps;
      int var5 = var3.length;
      ArrayList var6 = new ArrayList(var5);
      ArrayList var7 = var4 == null ? null : new ArrayList(var5);

      for(int var8 = 0; var8 < var5; ++var8) {
         BeanPropertyWriter var9 = var3[var8];
         if (var2 == null || !var2.contains(var9.getName())) {
            var6.add(var9);
            if (var4 != null) {
               var7.add(var4[var8]);
            }
         }
      }

      this._props = (BeanPropertyWriter[])var6.toArray(new BeanPropertyWriter[var6.size()]);
      this._filteredProps = var7 == null ? null : (BeanPropertyWriter[])var7.toArray(new BeanPropertyWriter[var7.size()]);
      this._typeId = var1._typeId;
      this._anyGetterWriter = var1._anyGetterWriter;
      this._objectIdWriter = var1._objectIdWriter;
      this._propertyFilterId = var1._propertyFilterId;
      this._serializationShape = var1._serializationShape;
   }

   public abstract BeanSerializerBase withObjectIdWriter(ObjectIdWriter var1);

   protected abstract BeanSerializerBase withIgnorals(Set var1);

   /** @deprecated */
   @Deprecated
   protected BeanSerializerBase withIgnorals(String[] var1) {
      return this.withIgnorals((Set)ArrayBuilders.arrayToSet(var1));
   }

   protected abstract BeanSerializerBase asArraySerializer();

   public abstract BeanSerializerBase withFilterId(Object var1);

   protected BeanSerializerBase(BeanSerializerBase var1) {
      this(var1, var1._props, var1._filteredProps);
   }

   protected BeanSerializerBase(BeanSerializerBase var1, NameTransformer var2) {
      this(var1, rename(var1._props, var2), rename(var1._filteredProps, var2));
   }

   private static final BeanPropertyWriter[] rename(BeanPropertyWriter[] var0, NameTransformer var1) {
      if (var0 != null && var0.length != 0 && var1 != null && var1 != NameTransformer.NOP) {
         int var2 = var0.length;
         BeanPropertyWriter[] var3 = new BeanPropertyWriter[var2];

         for(int var4 = 0; var4 < var2; ++var4) {
            BeanPropertyWriter var5 = var0[var4];
            if (var5 != null) {
               var3[var4] = var5.rename(var1);
            }
         }

         return var3;
      } else {
         return var0;
      }
   }

   public void resolve(SerializerProvider var1) throws JsonMappingException {
      int var2 = this._filteredProps == null ? 0 : this._filteredProps.length;
      int var3 = 0;

      for(int var4 = this._props.length; var3 < var4; ++var3) {
         BeanPropertyWriter var5 = this._props[var3];
         BeanPropertyWriter var7;
         if (!var5.willSuppressNulls() && !var5.hasNullSerializer()) {
            JsonSerializer var6 = var1.findNullValueSerializer(var5);
            if (var6 != null) {
               var5.assignNullSerializer(var6);
               if (var3 < var2) {
                  var7 = this._filteredProps[var3];
                  if (var7 != null) {
                     var7.assignNullSerializer(var6);
                  }
               }
            }
         }

         if (!var5.hasSerializer()) {
            Object var10 = this.findConvertingSerializer(var1, var5);
            if (var10 == null) {
               JavaType var11 = var5.getSerializationType();
               if (var11 == null) {
                  var11 = var5.getType();
                  if (!var11.isFinal()) {
                     if (var11.isContainerType() || var11.containedTypeCount() > 0) {
                        var5.setNonTrivialBaseType(var11);
                     }
                     continue;
                  }
               }

               var10 = var1.findValueSerializer((JavaType)var11, var5);
               if (var11.isContainerType()) {
                  TypeSerializer var8 = (TypeSerializer)var11.getContentType().getTypeHandler();
                  if (var8 != null && var10 instanceof ContainerSerializer) {
                     ContainerSerializer var9 = ((ContainerSerializer)var10).withValueTypeSerializer(var8);
                     var10 = var9;
                  }
               }
            }

            if (var3 < var2) {
               var7 = this._filteredProps[var3];
               if (var7 != null) {
                  var7.assignSerializer((JsonSerializer)var10);
                  continue;
               }
            }

            var5.assignSerializer((JsonSerializer)var10);
         }
      }

      if (this._anyGetterWriter != null) {
         this._anyGetterWriter.resolve(var1);
      }

   }

   protected JsonSerializer findConvertingSerializer(SerializerProvider var1, BeanPropertyWriter var2) throws JsonMappingException {
      AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
      if (var3 != null) {
         AnnotatedMember var4 = var2.getMember();
         if (var4 != null) {
            Object var5 = var3.findSerializationConverter(var4);
            if (var5 != null) {
               Converter var6 = var1.converterInstance(var2.getMember(), var5);
               JavaType var7 = var6.getOutputType(var1.getTypeFactory());
               JsonSerializer var8 = var7.isJavaLangObject() ? null : var1.findValueSerializer((JavaType)var7, var2);
               return new StdDelegatingSerializer(var6, var7, var8);
            }
         }
      }

      return null;
   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   public Iterator properties() {
      return Arrays.asList(this._props).iterator();
   }

   public boolean usesObjectId() {
      return this._objectIdWriter != null;
   }

   public abstract void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException;

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      if (this._objectIdWriter != null) {
         var2.setCurrentValue(var1);
         this._serializeWithObjectId(var1, var2, var3, var4);
      } else {
         var2.setCurrentValue(var1);
         WritableTypeId var5 = this._typeIdDef(var4, var1, JsonToken.START_OBJECT);
         var4.writeTypePrefix(var2, var5);
         if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(var1, var2, var3);
         } else {
            this.serializeFields(var1, var2, var3);
         }

         var4.writeTypeSuffix(var2, var5);
      }
   }

   protected final void _serializeWithObjectId(Object var1, JsonGenerator var2, SerializerProvider var3, boolean var4) throws IOException {
      ObjectIdWriter var5 = this._objectIdWriter;
      WritableObjectId var6 = var3.findObjectId(var1, var5.generator);
      if (!var6.writeAsId(var2, var3, var5)) {
         Object var7 = var6.generateId(var1);
         if (var5.alwaysAsId) {
            var5.serializer.serialize(var7, var2, var3);
         } else {
            if (var4) {
               var2.writeStartObject(var1);
            }

            var6.writeAsField(var2, var3, var5);
            if (this._propertyFilterId != null) {
               this.serializeFieldsFiltered(var1, var2, var3);
            } else {
               this.serializeFields(var1, var2, var3);
            }

            if (var4) {
               var2.writeEndObject();
            }

         }
      }
   }

   protected final void _serializeWithObjectId(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      ObjectIdWriter var5 = this._objectIdWriter;
      WritableObjectId var6 = var3.findObjectId(var1, var5.generator);
      if (!var6.writeAsId(var2, var3, var5)) {
         Object var7 = var6.generateId(var1);
         if (var5.alwaysAsId) {
            var5.serializer.serialize(var7, var2, var3);
         } else {
            this._serializeObjectId(var1, var2, var3, var4, var6);
         }
      }
   }

   protected void _serializeObjectId(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4, WritableObjectId var5) throws IOException {
      ObjectIdWriter var6 = this._objectIdWriter;
      WritableTypeId var7 = this._typeIdDef(var4, var1, JsonToken.START_OBJECT);
      var4.writeTypePrefix(var2, var7);
      var5.writeAsField(var2, var3, var6);
      if (this._propertyFilterId != null) {
         this.serializeFieldsFiltered(var1, var2, var3);
      } else {
         this.serializeFields(var1, var2, var3);
      }

      var4.writeTypeSuffix(var2, var7);
   }

   protected final WritableTypeId _typeIdDef(TypeSerializer var1, Object var2, JsonToken var3) {
      if (this._typeId == null) {
         return var1.typeId(var2, var3);
      } else {
         Object var4 = this._typeId.getValue(var2);
         if (var4 == null) {
            var4 = "";
         }

         return var1.typeId(var2, var3, var4);
      }
   }

   /** @deprecated */
   @Deprecated
   protected final String _customTypeId(Object var1) {
      Object var2 = this._typeId.getValue(var1);
      if (var2 == null) {
         return "";
      } else {
         return var2 instanceof String ? (String)var2 : var2.toString();
      }
   }

   protected void serializeFields(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      BeanPropertyWriter[] var4;
      if (this._filteredProps != null && var3.getActiveView() != null) {
         var4 = this._filteredProps;
      } else {
         var4 = this._props;
      }

      int var5 = 0;

      try {
         for(int var6 = var4.length; var5 < var6; ++var5) {
            BeanPropertyWriter var12 = var4[var5];
            if (var12 != null) {
               var12.serializeAsField(var1, var2, var3);
            }
         }

         if (this._anyGetterWriter != null) {
            this._anyGetterWriter.getAndSerialize(var1, var2, var3);
         }
      } catch (Exception var9) {
         String var11 = var5 == var4.length ? "[anySetter]" : var4[var5].getName();
         this.wrapAndThrow(var3, var9, var1, var11);
      } catch (StackOverflowError var10) {
         JsonMappingException var7 = new JsonMappingException(var2, "Infinite recursion (StackOverflowError)", var10);
         String var8 = var5 == var4.length ? "[anySetter]" : var4[var5].getName();
         var7.prependPath(new JsonMappingException$Reference(var1, var8));
         throw var7;
      }

   }

   protected void serializeFieldsFiltered(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException {
      BeanPropertyWriter[] var4;
      if (this._filteredProps != null && var3.getActiveView() != null) {
         var4 = this._filteredProps;
      } else {
         var4 = this._props;
      }

      PropertyFilter var5 = this.findPropertyFilter(var3, this._propertyFilterId, var1);
      if (var5 == null) {
         this.serializeFields(var1, var2, var3);
      } else {
         int var6 = 0;

         try {
            for(int var7 = var4.length; var6 < var7; ++var6) {
               BeanPropertyWriter var13 = var4[var6];
               if (var13 != null) {
                  var5.serializeAsField(var1, var2, var3, var13);
               }
            }

            if (this._anyGetterWriter != null) {
               this._anyGetterWriter.getAndFilter(var1, var2, var3, var5);
            }
         } catch (Exception var10) {
            String var12 = var6 == var4.length ? "[anySetter]" : var4[var6].getName();
            this.wrapAndThrow(var3, var10, var1, var12);
         } catch (StackOverflowError var11) {
            JsonMappingException var8 = new JsonMappingException(var2, "Infinite recursion (StackOverflowError)", var11);
            String var9 = var6 == var4.length ? "[anySetter]" : var4[var6].getName();
            var8.prependPath(new JsonMappingException$Reference(var1, var9));
            throw var8;
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public JsonNode getSchema(SerializerProvider var1, Type var2) throws JsonMappingException {
      ObjectNode var3 = this.createSchemaNode("object", true);
      JsonSerializableSchema var4 = (JsonSerializableSchema)this._handledType.getAnnotation(JsonSerializableSchema.class);
      if (var4 != null) {
         String var5 = var4.id();
         if (var5 != null && var5.length() > 0) {
            var3.put("id", var5);
         }
      }

      ObjectNode var9 = var3.objectNode();
      PropertyFilter var6;
      if (this._propertyFilterId != null) {
         var6 = this.findPropertyFilter(var1, this._propertyFilterId, (Object)null);
      } else {
         var6 = null;
      }

      for(int var7 = 0; var7 < this._props.length; ++var7) {
         BeanPropertyWriter var8 = this._props[var7];
         if (var6 == null) {
            var8.depositSchemaProperty(var9, var1);
         } else {
            var6.depositSchemaProperty(var8, (ObjectNode)var9, var1);
         }
      }

      var3.set("properties", var9);
      return var3;
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      if (var1 != null) {
         JsonObjectFormatVisitor var3 = var1.expectObjectFormat(var2);
         if (var3 != null) {
            SerializerProvider var4 = var1.getProvider();
            int var7;
            if (this._propertyFilterId != null) {
               PropertyFilter var5 = this.findPropertyFilter(var1.getProvider(), this._propertyFilterId, (Object)null);
               int var6 = 0;

               for(var7 = this._props.length; var6 < var7; ++var6) {
                  var5.depositSchemaProperty(this._props[var6], (JsonObjectFormatVisitor)var3, var4);
               }
            } else {
               Class var10 = this._filteredProps != null && var4 != null ? var4.getActiveView() : null;
               BeanPropertyWriter[] var11;
               if (var10 != null) {
                  var11 = this._filteredProps;
               } else {
                  var11 = this._props;
               }

               var7 = 0;

               for(int var8 = var11.length; var7 < var8; ++var7) {
                  BeanPropertyWriter var9 = var11[var7];
                  if (var9 != null) {
                     var9.depositSchemaProperty(var3, var4);
                  }
               }
            }

         }
      }
   }

   public JsonSerializer withFilterId(Object var1) {
      return this.withFilterId(var1);
   }
}
