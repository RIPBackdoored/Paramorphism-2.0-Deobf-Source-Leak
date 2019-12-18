package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap$SerializerAndMapResult;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

@JacksonStdImpl
public class MapSerializer extends ContainerSerializer implements ContextualSerializer {
   private static final long serialVersionUID = 1L;
   protected static final JavaType UNSPECIFIED_TYPE = TypeFactory.unknownType();
   public static final Object MARKER_FOR_EMPTY;
   protected final BeanProperty _property;
   protected final boolean _valueTypeIsStatic;
   protected final JavaType _keyType;
   protected final JavaType _valueType;
   protected JsonSerializer _keySerializer;
   protected JsonSerializer _valueSerializer;
   protected final TypeSerializer _valueTypeSerializer;
   protected PropertySerializerMap _dynamicValueSerializers;
   protected final Set _ignoredEntries;
   protected final Object _filterId;
   protected final Object _suppressableValue;
   protected final boolean _suppressNulls;
   protected final boolean _sortKeys;

   protected MapSerializer(Set var1, JavaType var2, JavaType var3, boolean var4, TypeSerializer var5, JsonSerializer var6, JsonSerializer var7) {
      super(Map.class, false);
      this._ignoredEntries = var1 != null && !var1.isEmpty() ? var1 : null;
      this._keyType = var2;
      this._valueType = var3;
      this._valueTypeIsStatic = var4;
      this._valueTypeSerializer = var5;
      this._keySerializer = var6;
      this._valueSerializer = var7;
      this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
      this._property = null;
      this._filterId = null;
      this._sortKeys = false;
      this._suppressableValue = null;
      this._suppressNulls = false;
   }

   protected MapSerializer(MapSerializer var1, BeanProperty var2, JsonSerializer var3, JsonSerializer var4, Set var5) {
      super(Map.class, false);
      this._ignoredEntries = var5 != null && !var5.isEmpty() ? var5 : null;
      this._keyType = var1._keyType;
      this._valueType = var1._valueType;
      this._valueTypeIsStatic = var1._valueTypeIsStatic;
      this._valueTypeSerializer = var1._valueTypeSerializer;
      this._keySerializer = var3;
      this._valueSerializer = var4;
      this._dynamicValueSerializers = var1._dynamicValueSerializers;
      this._property = var2;
      this._filterId = var1._filterId;
      this._sortKeys = var1._sortKeys;
      this._suppressableValue = var1._suppressableValue;
      this._suppressNulls = var1._suppressNulls;
   }

   protected MapSerializer(MapSerializer var1, TypeSerializer var2, Object var3, boolean var4) {
      super(Map.class, false);
      this._ignoredEntries = var1._ignoredEntries;
      this._keyType = var1._keyType;
      this._valueType = var1._valueType;
      this._valueTypeIsStatic = var1._valueTypeIsStatic;
      this._valueTypeSerializer = var2;
      this._keySerializer = var1._keySerializer;
      this._valueSerializer = var1._valueSerializer;
      this._dynamicValueSerializers = var1._dynamicValueSerializers;
      this._property = var1._property;
      this._filterId = var1._filterId;
      this._sortKeys = var1._sortKeys;
      this._suppressableValue = var3;
      this._suppressNulls = var4;
   }

   protected MapSerializer(MapSerializer var1, Object var2, boolean var3) {
      super(Map.class, false);
      this._ignoredEntries = var1._ignoredEntries;
      this._keyType = var1._keyType;
      this._valueType = var1._valueType;
      this._valueTypeIsStatic = var1._valueTypeIsStatic;
      this._valueTypeSerializer = var1._valueTypeSerializer;
      this._keySerializer = var1._keySerializer;
      this._valueSerializer = var1._valueSerializer;
      this._dynamicValueSerializers = var1._dynamicValueSerializers;
      this._property = var1._property;
      this._filterId = var2;
      this._sortKeys = var3;
      this._suppressableValue = var1._suppressableValue;
      this._suppressNulls = var1._suppressNulls;
   }

   public MapSerializer _withValueTypeSerializer(TypeSerializer var1) {
      if (this._valueTypeSerializer == var1) {
         return this;
      } else {
         this._ensureOverride("_withValueTypeSerializer");
         return new MapSerializer(this, var1, this._suppressableValue, this._suppressNulls);
      }
   }

   public MapSerializer withResolved(BeanProperty var1, JsonSerializer var2, JsonSerializer var3, Set var4, boolean var5) {
      this._ensureOverride("withResolved");
      MapSerializer var6 = new MapSerializer(this, var1, var2, var3, var4);
      if (var5 != var6._sortKeys) {
         var6 = new MapSerializer(var6, this._filterId, var5);
      }

      return var6;
   }

   public MapSerializer withFilterId(Object var1) {
      if (this._filterId == var1) {
         return this;
      } else {
         this._ensureOverride("withFilterId");
         return new MapSerializer(this, var1, this._sortKeys);
      }
   }

   public MapSerializer withContentInclusion(Object var1, boolean var2) {
      if (var1 == this._suppressableValue && var2 == this._suppressNulls) {
         return this;
      } else {
         this._ensureOverride("withContentInclusion");
         return new MapSerializer(this, this._valueTypeSerializer, var1, var2);
      }
   }

   public static MapSerializer construct(Set var0, JavaType var1, boolean var2, TypeSerializer var3, JsonSerializer var4, JsonSerializer var5, Object var6) {
      JavaType var7;
      JavaType var8;
      if (var1 == null) {
         var7 = var8 = UNSPECIFIED_TYPE;
      } else {
         var7 = var1.getKeyType();
         var8 = var1.getContentType();
      }

      if (!var2) {
         var2 = var8 != null && var8.isFinal();
      } else if (var8.getRawClass() == Object.class) {
         var2 = false;
      }

      MapSerializer var9 = new MapSerializer(var0, var7, var8, var2, var3, var4, var5);
      if (var6 != null) {
         var9 = var9.withFilterId(var6);
      }

      return var9;
   }

   protected void _ensureOverride(String var1) {
      ClassUtil.verifyMustOverride(MapSerializer.class, this, var1);
   }

   /** @deprecated */
   @Deprecated
   protected void _ensureOverride() {
      this._ensureOverride("N/A");
   }

   /** @deprecated */
   @Deprecated
   protected MapSerializer(MapSerializer var1, TypeSerializer var2, Object var3) {
      this(var1, var2, var3, false);
   }

   /** @deprecated */
   @Deprecated
   public MapSerializer withContentInclusion(Object var1) {
      return new MapSerializer(this, this._valueTypeSerializer, var1, this._suppressNulls);
   }

   /** @deprecated */
   @Deprecated
   public static MapSerializer construct(String[] var0, JavaType var1, boolean var2, TypeSerializer var3, JsonSerializer var4, JsonSerializer var5, Object var6) {
      HashSet var7 = ArrayBuilders.arrayToSet(var0);
      return construct((Set)var7, var1, var2, var3, var4, var5, var6);
   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   public JavaType getContentType() {
      return this._valueType;
   }

   public JsonSerializer getContentSerializer() {
      return this._valueSerializer;
   }

   public boolean isEmpty(SerializerProvider var1, Map var2) {
      if (var2.isEmpty()) {
         return true;
      } else {
         Object var3 = this._suppressableValue;
         if (var3 == null && !this._suppressNulls) {
            return false;
         } else {
            JsonSerializer var4 = this._valueSerializer;
            boolean var5 = MARKER_FOR_EMPTY == var3;
            Iterator var6;
            Object var7;
            if (var4 != null) {
               var6 = var2.values().iterator();

               label71:
               do {
                  label65:
                  do {
                     do {
                        if (!var6.hasNext()) {
                           return true;
                        }

                        var7 = var6.next();
                        if (var7 != null) {
                           if (!var5) {
                              continue label71;
                           }
                           continue label65;
                        }
                     } while(this._suppressNulls);

                     return false;
                  } while(var4.isEmpty(var1, var7));

                  return false;
               } while(var3 != null && var3.equals(var2));

               return false;
            } else {
               var6 = var2.values().iterator();

               label96:
               do {
                  label90:
                  do {
                     do {
                        if (!var6.hasNext()) {
                           return true;
                        }

                        var7 = var6.next();
                        if (var7 != null) {
                           try {
                              var4 = this._findSerializer(var1, var7);
                           } catch (JsonMappingException var9) {
                              return false;
                           }

                           if (!var5) {
                              continue label96;
                           }
                           continue label90;
                        }
                     } while(this._suppressNulls);

                     return false;
                  } while(var4.isEmpty(var1, var7));

                  return false;
               } while(var3 != null && var3.equals(var2));

               return false;
            }
         }
      }
   }

   public boolean hasSingleElement(Map var1) {
      return var1.size() == 1;
   }

   public JsonSerializer getKeySerializer() {
      return this._keySerializer;
   }

   public void serialize(Map var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeStartObject(var1);
      if (!var1.isEmpty()) {
         if (this._sortKeys || var3.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
            var1 = this._orderEntries(var1, var2, var3);
         }

         PropertyFilter var4;
         if (this._filterId != null && (var4 = this.findPropertyFilter(var3, this._filterId, var1)) != null) {
            this.serializeFilteredFields(var1, var2, var3, var4, this._suppressableValue);
         } else if (this._suppressableValue == null && !this._suppressNulls) {
            if (this._valueSerializer != null) {
               this.serializeFieldsUsing(var1, var2, var3, this._valueSerializer);
            } else {
               this.serializeFields(var1, var2, var3);
            }
         } else {
            this.serializeOptionalFields(var1, var2, var3, this._suppressableValue);
         }
      }

      var2.writeEndObject();
   }

   public void serializeWithType(Map var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      var2.setCurrentValue(var1);
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, JsonToken.START_OBJECT));
      if (!var1.isEmpty()) {
         if (this._sortKeys || var3.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
            var1 = this._orderEntries(var1, var2, var3);
         }

         PropertyFilter var6;
         if (this._filterId != null && (var6 = this.findPropertyFilter(var3, this._filterId, var1)) != null) {
            this.serializeFilteredFields(var1, var2, var3, var6, this._suppressableValue);
         } else if (this._suppressableValue == null && !this._suppressNulls) {
            if (this._valueSerializer != null) {
               this.serializeFieldsUsing(var1, var2, var3, this._valueSerializer);
            } else {
               this.serializeFields(var1, var2, var3);
            }
         } else {
            this.serializeOptionalFields(var1, var2, var3, this._suppressableValue);
         }
      }

      var4.writeTypeSuffix(var2, var5);
   }

   public void serializeFields(Map var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (this._valueTypeSerializer != null) {
         this.serializeTypedFields(var1, var2, var3, (Object)null);
      } else {
         JsonSerializer var4 = this._keySerializer;
         Set var5 = this._ignoredEntries;
         Object var6 = null;

         try {
            Iterator var7 = var1.entrySet().iterator();

            while(true) {
               Object var9;
               while(true) {
                  if (!var7.hasNext()) {
                     return;
                  }

                  Entry var8 = (Entry)var7.next();
                  var9 = var8.getValue();
                  var6 = var8.getKey();
                  if (var6 == null) {
                     var3.findNullKeySerializer(this._keyType, this._property).serialize((Object)null, var2, var3);
                     break;
                  }

                  if (var5 == null || !var5.contains(var6)) {
                     var4.serialize(var6, var2, var3);
                     break;
                  }
               }

               if (var9 == null) {
                  var3.defaultSerializeNull(var2);
               } else {
                  JsonSerializer var10 = this._valueSerializer;
                  if (var10 == null) {
                     var10 = this._findSerializer(var3, var9);
                  }

                  var10.serialize(var9, var2, var3);
               }
            }
         } catch (Exception var11) {
            this.wrapAndThrow(var3, var11, var1, String.valueOf(var6));
         }
      }
   }

   public void serializeOptionalFields(Map var1, JsonGenerator var2, SerializerProvider var3, Object var4) throws IOException {
      if (this._valueTypeSerializer != null) {
         this.serializeTypedFields(var1, var2, var3, var4);
      } else {
         Set var5 = this._ignoredEntries;
         boolean var6 = MARKER_FOR_EMPTY == var4;
         Iterator var7 = var1.entrySet().iterator();

         while(true) {
            Object var9;
            JsonSerializer var10;
            Object var11;
            JsonSerializer var12;
            while(true) {
               Entry var8;
               while(true) {
                  if (!var7.hasNext()) {
                     return;
                  }

                  var8 = (Entry)var7.next();
                  var9 = var8.getKey();
                  if (var9 == null) {
                     var10 = var3.findNullKeySerializer(this._keyType, this._property);
                     break;
                  }

                  if (var5 == null || !var5.contains(var9)) {
                     var10 = this._keySerializer;
                     break;
                  }
               }

               var11 = var8.getValue();
               if (var11 == null) {
                  if (!this._suppressNulls) {
                     var12 = var3.getDefaultNullValueSerializer();
                     break;
                  }
               } else {
                  var12 = this._valueSerializer;
                  if (var12 == null) {
                     var12 = this._findSerializer(var3, var11);
                  }

                  if (var6) {
                     if (var12.isEmpty(var3, var11)) {
                        continue;
                     }
                     break;
                  } else if (var4 == null || !var4.equals(var11)) {
                     break;
                  }
               }
            }

            try {
               var10.serialize(var9, var2, var3);
               var12.serialize(var11, var2, var3);
            } catch (Exception var14) {
               this.wrapAndThrow(var3, var14, var1, String.valueOf(var9));
            }
         }
      }
   }

   public void serializeFieldsUsing(Map var1, JsonGenerator var2, SerializerProvider var3, JsonSerializer var4) throws IOException {
      JsonSerializer var5 = this._keySerializer;
      Set var6 = this._ignoredEntries;
      TypeSerializer var7 = this._valueTypeSerializer;
      Iterator var8 = var1.entrySet().iterator();

      while(true) {
         Entry var9;
         Object var10;
         do {
            if (!var8.hasNext()) {
               return;
            }

            var9 = (Entry)var8.next();
            var10 = var9.getKey();
         } while(var6 != null && var6.contains(var10));

         if (var10 == null) {
            var3.findNullKeySerializer(this._keyType, this._property).serialize((Object)null, var2, var3);
         } else {
            var5.serialize(var10, var2, var3);
         }

         Object var11 = var9.getValue();
         if (var11 == null) {
            var3.defaultSerializeNull(var2);
         } else {
            try {
               if (var7 == null) {
                  var4.serialize(var11, var2, var3);
               } else {
                  var4.serializeWithType(var11, var2, var3, var7);
               }
            } catch (Exception var13) {
               this.wrapAndThrow(var3, var13, var1, String.valueOf(var10));
            }
         }
      }
   }

   public void serializeFilteredFields(Map var1, JsonGenerator var2, SerializerProvider var3, PropertyFilter var4, Object var5) throws IOException {
      Set var6 = this._ignoredEntries;
      MapProperty var7 = new MapProperty(this._valueTypeSerializer, this._property);
      boolean var8 = MARKER_FOR_EMPTY == var5;
      Iterator var9 = var1.entrySet().iterator();

      while(true) {
         Object var11;
         JsonSerializer var12;
         Object var13;
         JsonSerializer var14;
         while(true) {
            Entry var10;
            do {
               if (!var9.hasNext()) {
                  return;
               }

               var10 = (Entry)var9.next();
               var11 = var10.getKey();
            } while(var6 != null && var6.contains(var11));

            if (var11 == null) {
               var12 = var3.findNullKeySerializer(this._keyType, this._property);
            } else {
               var12 = this._keySerializer;
            }

            var13 = var10.getValue();
            if (var13 == null) {
               if (!this._suppressNulls) {
                  var14 = var3.getDefaultNullValueSerializer();
                  break;
               }
            } else {
               var14 = this._valueSerializer;
               if (var14 == null) {
                  var14 = this._findSerializer(var3, var13);
               }

               if (var8) {
                  if (var14.isEmpty(var3, var13)) {
                     continue;
                  }
                  break;
               } else if (var5 == null || !var5.equals(var13)) {
                  break;
               }
            }
         }

         var7.reset(var11, var13, var12, var14);

         try {
            var4.serializeAsField(var1, var2, var3, var7);
         } catch (Exception var16) {
            this.wrapAndThrow(var3, var16, var1, String.valueOf(var11));
         }
      }
   }

   public void serializeTypedFields(Map var1, JsonGenerator var2, SerializerProvider var3, Object var4) throws IOException {
      Set var5 = this._ignoredEntries;
      boolean var6 = MARKER_FOR_EMPTY == var4;
      Iterator var7 = var1.entrySet().iterator();

      while(true) {
         Object var9;
         JsonSerializer var10;
         Object var11;
         JsonSerializer var12;
         while(true) {
            Entry var8;
            while(true) {
               if (!var7.hasNext()) {
                  return;
               }

               var8 = (Entry)var7.next();
               var9 = var8.getKey();
               if (var9 == null) {
                  var10 = var3.findNullKeySerializer(this._keyType, this._property);
                  break;
               }

               if (var5 == null || !var5.contains(var9)) {
                  var10 = this._keySerializer;
                  break;
               }
            }

            var11 = var8.getValue();
            if (var11 == null) {
               if (!this._suppressNulls) {
                  var12 = var3.getDefaultNullValueSerializer();
                  break;
               }
            } else {
               var12 = this._valueSerializer;
               if (var12 == null) {
                  var12 = this._findSerializer(var3, var11);
               }

               if (var6) {
                  if (var12.isEmpty(var3, var11)) {
                     continue;
                  }
                  break;
               } else if (var4 == null || !var4.equals(var11)) {
                  break;
               }
            }
         }

         var10.serialize(var9, var2, var3);

         try {
            var12.serializeWithType(var11, var2, var3, this._valueTypeSerializer);
         } catch (Exception var14) {
            this.wrapAndThrow(var3, var14, var1, String.valueOf(var9));
         }
      }
   }

   public void serializeFilteredAnyProperties(SerializerProvider var1, JsonGenerator var2, Object var3, Map var4, PropertyFilter var5, Object var6) throws IOException {
      Set var7 = this._ignoredEntries;
      MapProperty var8 = new MapProperty(this._valueTypeSerializer, this._property);
      boolean var9 = MARKER_FOR_EMPTY == var6;
      Iterator var10 = var4.entrySet().iterator();

      while(true) {
         Object var12;
         JsonSerializer var13;
         Object var14;
         JsonSerializer var15;
         while(true) {
            Entry var11;
            do {
               if (!var10.hasNext()) {
                  return;
               }

               var11 = (Entry)var10.next();
               var12 = var11.getKey();
            } while(var7 != null && var7.contains(var12));

            if (var12 == null) {
               var13 = var1.findNullKeySerializer(this._keyType, this._property);
            } else {
               var13 = this._keySerializer;
            }

            var14 = var11.getValue();
            if (var14 == null) {
               if (!this._suppressNulls) {
                  var15 = var1.getDefaultNullValueSerializer();
                  break;
               }
            } else {
               var15 = this._valueSerializer;
               if (var15 == null) {
                  var15 = this._findSerializer(var1, var14);
               }

               if (var9) {
                  if (var15.isEmpty(var1, var14)) {
                     continue;
                  }
                  break;
               } else if (var6 == null || !var6.equals(var14)) {
                  break;
               }
            }
         }

         var8.reset(var12, var14, var13, var15);

         try {
            var5.serializeAsField(var3, var2, var1, var8);
         } catch (Exception var17) {
            this.wrapAndThrow(var1, var17, var4, String.valueOf(var12));
         }
      }
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode("object", true);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      JsonMapFormatVisitor var3 = var1.expectMapFormat(var2);
      if (var3 != null) {
         var3.keyFormat(this._keySerializer, this._keyType);
         JsonSerializer var4 = this._valueSerializer;
         if (var4 == null) {
            var4 = this._findAndAddDynamic(this._dynamicValueSerializers, this._valueType, var1.getProvider());
         }

         var3.valueFormat(var4, this._valueType);
      }

   }

   protected final JsonSerializer _findAndAddDynamic(PropertySerializerMap var1, Class var2, SerializerProvider var3) throws JsonMappingException {
      PropertySerializerMap$SerializerAndMapResult var4 = var1.findAndAddSecondarySerializer(var2, var3, this._property);
      if (var1 != var4.map) {
         this._dynamicValueSerializers = var4.map;
      }

      return var4.serializer;
   }

   protected final JsonSerializer _findAndAddDynamic(PropertySerializerMap var1, JavaType var2, SerializerProvider var3) throws JsonMappingException {
      PropertySerializerMap$SerializerAndMapResult var4 = var1.findAndAddSecondarySerializer(var2, var3, this._property);
      if (var1 != var4.map) {
         this._dynamicValueSerializers = var4.map;
      }

      return var4.serializer;
   }

   protected Map _orderEntries(Map var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (var1 instanceof SortedMap) {
         return var1;
      } else if (this._hasNullKey(var1)) {
         TreeMap var4 = new TreeMap();
         Iterator var5 = var1.entrySet().iterator();

         while(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            Object var7 = var6.getKey();
            if (var7 == null) {
               this._writeNullKeyedEntry(var2, var3, var6.getValue());
            } else {
               var4.put(var7, var6.getValue());
            }
         }

         return var4;
      } else {
         return new TreeMap(var1);
      }
   }

   protected boolean _hasNullKey(Map var1) {
      return var1 instanceof HashMap && var1.containsKey((Object)null);
   }

   protected void _writeNullKeyedEntry(JsonGenerator var1, SerializerProvider var2, Object var3) throws IOException {
      JsonSerializer var4 = var2.findNullKeySerializer(this._keyType, this._property);
      JsonSerializer var5;
      if (var3 == null) {
         if (this._suppressNulls) {
            return;
         }

         var5 = var2.getDefaultNullValueSerializer();
      } else {
         var5 = this._valueSerializer;
         if (var5 == null) {
            var5 = this._findSerializer(var2, var3);
         }

         if (this._suppressableValue == MARKER_FOR_EMPTY) {
            if (var5.isEmpty(var2, var3)) {
               return;
            }
         } else if (this._suppressableValue != null && this._suppressableValue.equals(var3)) {
            return;
         }
      }

      try {
         var4.serialize((Object)null, var1, var2);
         var5.serialize(var3, var1, var2);
      } catch (Exception var7) {
         this.wrapAndThrow(var2, var7, var3, "");
      }

   }

   private final JsonSerializer _findSerializer(SerializerProvider var1, Object var2) throws JsonMappingException {
      Class var3 = var2.getClass();
      JsonSerializer var4 = this._dynamicValueSerializers.serializerFor(var3);
      if (var4 != null) {
         return var4;
      } else {
         return this._valueType.hasGenericTypes() ? this._findAndAddDynamic(this._dynamicValueSerializers, var1.constructSpecializedType(this._valueType, var3), var1) : this._findAndAddDynamic(this._dynamicValueSerializers, var3, var1);
      }
   }

   public ContainerSerializer _withValueTypeSerializer(TypeSerializer var1) {
      return this._withValueTypeSerializer(var1);
   }

   public boolean hasSingleElement(Object var1) {
      return this.hasSingleElement((Map)var1);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Map)var1, var2, var3);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (Map)var2);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((Map)var1, var2, var3, var4);
   }

   public JsonSerializer withFilterId(Object var1) {
      return this.withFilterId(var1);
   }

   static {
      MARKER_FOR_EMPTY = JsonInclude$Include.NON_EMPTY;
   }
}
