package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import java.util.Collections;
import java.util.List;

public class BeanSerializerBuilder {
   private static final BeanPropertyWriter[] NO_PROPERTIES = new BeanPropertyWriter[0];
   protected final BeanDescription _beanDesc;
   protected SerializationConfig _config;
   protected List _properties = Collections.emptyList();
   protected BeanPropertyWriter[] _filteredProperties;
   protected AnyGetterWriter _anyGetter;
   protected Object _filterId;
   protected AnnotatedMember _typeId;
   protected ObjectIdWriter _objectIdWriter;

   public BeanSerializerBuilder(BeanDescription var1) {
      super();
      this._beanDesc = var1;
   }

   protected BeanSerializerBuilder(BeanSerializerBuilder var1) {
      super();
      this._beanDesc = var1._beanDesc;
      this._properties = var1._properties;
      this._filteredProperties = var1._filteredProperties;
      this._anyGetter = var1._anyGetter;
      this._filterId = var1._filterId;
   }

   protected void setConfig(SerializationConfig var1) {
      this._config = var1;
   }

   public void setProperties(List var1) {
      this._properties = var1;
   }

   public void setFilteredProperties(BeanPropertyWriter[] var1) {
      if (var1 != null && var1.length != this._properties.size()) {
         throw new IllegalArgumentException(String.format("Trying to set %d filtered properties; must match length of non-filtered `properties` (%d)", var1.length, this._properties.size()));
      } else {
         this._filteredProperties = var1;
      }
   }

   public void setAnyGetter(AnyGetterWriter var1) {
      this._anyGetter = var1;
   }

   public void setFilterId(Object var1) {
      this._filterId = var1;
   }

   public void setTypeId(AnnotatedMember var1) {
      if (this._typeId != null) {
         throw new IllegalArgumentException("Multiple type ids specified with " + this._typeId + " and " + var1);
      } else {
         this._typeId = var1;
      }
   }

   public void setObjectIdWriter(ObjectIdWriter var1) {
      this._objectIdWriter = var1;
   }

   public AnnotatedClass getClassInfo() {
      return this._beanDesc.getClassInfo();
   }

   public BeanDescription getBeanDescription() {
      return this._beanDesc;
   }

   public List getProperties() {
      return this._properties;
   }

   public boolean hasProperties() {
      return this._properties != null && this._properties.size() > 0;
   }

   public BeanPropertyWriter[] getFilteredProperties() {
      return this._filteredProperties;
   }

   public AnyGetterWriter getAnyGetter() {
      return this._anyGetter;
   }

   public Object getFilterId() {
      return this._filterId;
   }

   public AnnotatedMember getTypeId() {
      return this._typeId;
   }

   public ObjectIdWriter getObjectIdWriter() {
      return this._objectIdWriter;
   }

   public JsonSerializer build() {
      BeanPropertyWriter[] var1;
      if (this._properties != null && !this._properties.isEmpty()) {
         var1 = (BeanPropertyWriter[])this._properties.toArray(new BeanPropertyWriter[this._properties.size()]);
         if (this._config.isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
            int var2 = 0;

            for(int var3 = var1.length; var2 < var3; ++var2) {
               var1[var2].fixAccess(this._config);
            }
         }
      } else {
         if (this._anyGetter == null && this._objectIdWriter == null) {
            return null;
         }

         var1 = NO_PROPERTIES;
      }

      if (this._filteredProperties != null && this._filteredProperties.length != this._properties.size()) {
         throw new IllegalStateException(String.format("Mismatch between `properties` size (%d), `filteredProperties` (%s): should have as many (or `null` for latter)", this._properties.size(), this._filteredProperties.length));
      } else {
         if (this._anyGetter != null) {
            this._anyGetter.fixAccess(this._config);
         }

         if (this._typeId != null && this._config.isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
            this._typeId.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
         }

         return new BeanSerializer(this._beanDesc.getType(), this, var1, this._filteredProperties);
      }
   }

   public BeanSerializer createDummy() {
      return BeanSerializer.createDummy(this._beanDesc.getType());
   }
}
