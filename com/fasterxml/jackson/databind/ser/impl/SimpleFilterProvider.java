package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SimpleFilterProvider extends FilterProvider implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final Map _filtersById;
   protected PropertyFilter _defaultFilter;
   protected boolean _cfgFailOnUnknownId;

   public SimpleFilterProvider() {
      this(new HashMap());
   }

   public SimpleFilterProvider(Map var1) {
      super();
      this._cfgFailOnUnknownId = true;
      Iterator var2 = var1.values().iterator();

      Object var3;
      do {
         if (!var2.hasNext()) {
            this._filtersById = var1;
            return;
         }

         var3 = var2.next();
      } while(var3 instanceof PropertyFilter);

      this._filtersById = _convert(var1);
   }

   private static final Map _convert(Map var0) {
      HashMap var1 = new HashMap();
      Iterator var2 = var0.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         Object var4 = var3.getValue();
         if (var4 instanceof PropertyFilter) {
            var1.put(var3.getKey(), (PropertyFilter)var4);
         } else {
            if (!(var4 instanceof BeanPropertyFilter)) {
               throw new IllegalArgumentException("Unrecognized filter type (" + var4.getClass().getName() + ")");
            }

            var1.put(var3.getKey(), _convert((BeanPropertyFilter)var4));
         }
      }

      return var1;
   }

   private static final PropertyFilter _convert(BeanPropertyFilter var0) {
      return SimpleBeanPropertyFilter.from(var0);
   }

   /** @deprecated */
   @Deprecated
   public SimpleFilterProvider setDefaultFilter(BeanPropertyFilter var1) {
      this._defaultFilter = SimpleBeanPropertyFilter.from(var1);
      return this;
   }

   public SimpleFilterProvider setDefaultFilter(PropertyFilter var1) {
      this._defaultFilter = var1;
      return this;
   }

   public SimpleFilterProvider setDefaultFilter(SimpleBeanPropertyFilter var1) {
      this._defaultFilter = var1;
      return this;
   }

   public PropertyFilter getDefaultFilter() {
      return this._defaultFilter;
   }

   public SimpleFilterProvider setFailOnUnknownId(boolean var1) {
      this._cfgFailOnUnknownId = var1;
      return this;
   }

   public boolean willFailOnUnknownId() {
      return this._cfgFailOnUnknownId;
   }

   /** @deprecated */
   @Deprecated
   public SimpleFilterProvider addFilter(String var1, BeanPropertyFilter var2) {
      this._filtersById.put(var1, _convert(var2));
      return this;
   }

   public SimpleFilterProvider addFilter(String var1, PropertyFilter var2) {
      this._filtersById.put(var1, var2);
      return this;
   }

   public SimpleFilterProvider addFilter(String var1, SimpleBeanPropertyFilter var2) {
      this._filtersById.put(var1, var2);
      return this;
   }

   public PropertyFilter removeFilter(String var1) {
      return (PropertyFilter)this._filtersById.remove(var1);
   }

   /** @deprecated */
   @Deprecated
   public BeanPropertyFilter findFilter(Object var1) {
      throw new UnsupportedOperationException("Access to deprecated filters not supported");
   }

   public PropertyFilter findPropertyFilter(Object var1, Object var2) {
      PropertyFilter var3 = (PropertyFilter)this._filtersById.get(var1);
      if (var3 == null) {
         var3 = this._defaultFilter;
         if (var3 == null && this._cfgFailOnUnknownId) {
            throw new IllegalArgumentException("No filter configured with id '" + var1 + "' (type " + var1.getClass().getName() + ")");
         }
      }

      return var3;
   }
}
