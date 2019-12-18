package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class BeanPropertyMap implements Iterable, Serializable {
   private static final long serialVersionUID = 2L;
   protected final boolean _caseInsensitive;
   private int _hashMask;
   private int _size;
   private int _spillCount;
   private Object[] _hashArea;
   private final SettableBeanProperty[] _propsInOrder;
   private final Map _aliasDefs;
   private final Map _aliasMapping;

   public BeanPropertyMap(boolean var1, Collection var2, Map var3) {
      super();
      this._caseInsensitive = var1;
      this._propsInOrder = (SettableBeanProperty[])var2.toArray(new SettableBeanProperty[var2.size()]);
      this._aliasDefs = var3;
      this._aliasMapping = this._buildAliasMapping(var3);
      this.init(var2);
   }

   private BeanPropertyMap(BeanPropertyMap var1, SettableBeanProperty var2, int var3, int var4) {
      super();
      this._caseInsensitive = var1._caseInsensitive;
      this._hashMask = var1._hashMask;
      this._size = var1._size;
      this._spillCount = var1._spillCount;
      this._aliasDefs = var1._aliasDefs;
      this._aliasMapping = var1._aliasMapping;
      this._hashArea = Arrays.copyOf(var1._hashArea, var1._hashArea.length);
      this._propsInOrder = (SettableBeanProperty[])Arrays.copyOf(var1._propsInOrder, var1._propsInOrder.length);
      this._hashArea[var3] = var2;
      this._propsInOrder[var4] = var2;
   }

   private BeanPropertyMap(BeanPropertyMap var1, SettableBeanProperty var2, String var3, int var4) {
      super();
      this._caseInsensitive = var1._caseInsensitive;
      this._hashMask = var1._hashMask;
      this._size = var1._size;
      this._spillCount = var1._spillCount;
      this._aliasDefs = var1._aliasDefs;
      this._aliasMapping = var1._aliasMapping;
      this._hashArea = Arrays.copyOf(var1._hashArea, var1._hashArea.length);
      int var5 = var1._propsInOrder.length;
      this._propsInOrder = (SettableBeanProperty[])Arrays.copyOf(var1._propsInOrder, var5 + 1);
      this._propsInOrder[var5] = var2;
      int var6 = this._hashMask + 1;
      int var7 = var4 << 1;
      if (this._hashArea[var7] != null) {
         var7 = var6 + (var4 >> 1) << 1;
         if (this._hashArea[var7] != null) {
            var7 = (var6 + (var6 >> 1) << 1) + this._spillCount;
            this._spillCount += 2;
            if (var7 >= this._hashArea.length) {
               this._hashArea = Arrays.copyOf(this._hashArea, this._hashArea.length + 4);
            }
         }
      }

      this._hashArea[var7] = var3;
      this._hashArea[var7 + 1] = var2;
   }

   /** @deprecated */
   @Deprecated
   public BeanPropertyMap(boolean var1, Collection var2) {
      this(var1, var2, Collections.emptyMap());
   }

   protected BeanPropertyMap(BeanPropertyMap var1, boolean var2) {
      super();
      this._caseInsensitive = var2;
      this._aliasDefs = var1._aliasDefs;
      this._aliasMapping = var1._aliasMapping;
      this._propsInOrder = (SettableBeanProperty[])Arrays.copyOf(var1._propsInOrder, var1._propsInOrder.length);
      this.init(Arrays.asList(this._propsInOrder));
   }

   public BeanPropertyMap withCaseInsensitivity(boolean var1) {
      return this._caseInsensitive == var1 ? this : new BeanPropertyMap(this, var1);
   }

   protected void init(Collection var1) {
      this._size = var1.size();
      int var2 = findSize(this._size);
      this._hashMask = var2 - 1;
      int var3 = (var2 + (var2 >> 1)) * 2;
      Object[] var4 = new Object[var3];
      int var5 = 0;
      Iterator var6 = var1.iterator();

      while(var6.hasNext()) {
         SettableBeanProperty var7 = (SettableBeanProperty)var6.next();
         if (var7 != null) {
            String var8 = this.getPropertyName(var7);
            int var9 = this._hashCode(var8);
            int var10 = var9 << 1;
            if (var4[var10] != null) {
               var10 = var2 + (var9 >> 1) << 1;
               if (var4[var10] != null) {
                  var10 = (var2 + (var2 >> 1) << 1) + var5;
                  var5 += 2;
                  if (var10 >= var4.length) {
                     var4 = Arrays.copyOf(var4, var4.length + 4);
                  }
               }
            }

            var4[var10] = var8;
            var4[var10 + 1] = var7;
         }
      }

      this._hashArea = var4;
      this._spillCount = var5;
   }

   private static final int findSize(int var0) {
      if (var0 <= 5) {
         return 8;
      } else if (var0 <= 12) {
         return 16;
      } else {
         int var1 = var0 + (var0 >> 2);

         int var2;
         for(var2 = 32; var2 < var1; var2 += var2) {
         }

         return var2;
      }
   }

   public static BeanPropertyMap construct(Collection var0, boolean var1, Map var2) {
      return new BeanPropertyMap(var1, var0, var2);
   }

   /** @deprecated */
   @Deprecated
   public static BeanPropertyMap construct(Collection var0, boolean var1) {
      return construct(var0, var1, Collections.emptyMap());
   }

   public BeanPropertyMap withProperty(SettableBeanProperty var1) {
      String var2 = this.getPropertyName(var1);
      int var3 = 1;

      for(int var4 = this._hashArea.length; var3 < var4; var3 += 2) {
         SettableBeanProperty var5 = (SettableBeanProperty)this._hashArea[var3];
         if (var5 != null && var5.getName().equals(var2)) {
            return new BeanPropertyMap(this, var1, var3, this._findFromOrdered(var5));
         }
      }

      var3 = this._hashCode(var2);
      return new BeanPropertyMap(this, var1, var2, var3);
   }

   public BeanPropertyMap assignIndexes() {
      int var1 = 0;
      int var2 = 1;

      for(int var3 = this._hashArea.length; var2 < var3; var2 += 2) {
         SettableBeanProperty var4 = (SettableBeanProperty)this._hashArea[var2];
         if (var4 != null) {
            var4.assignIndex(var1++);
         }
      }

      return this;
   }

   public BeanPropertyMap renameAll(NameTransformer var1) {
      if (var1 != null && var1 != NameTransformer.NOP) {
         int var2 = this._propsInOrder.length;
         ArrayList var3 = new ArrayList(var2);

         for(int var4 = 0; var4 < var2; ++var4) {
            SettableBeanProperty var5 = this._propsInOrder[var4];
            if (var5 == null) {
               var3.add(var5);
            } else {
               var3.add(this._rename(var5, var1));
            }
         }

         return new BeanPropertyMap(this._caseInsensitive, var3, this._aliasDefs);
      } else {
         return this;
      }
   }

   public BeanPropertyMap withoutProperties(Collection var1) {
      if (var1.isEmpty()) {
         return this;
      } else {
         int var2 = this._propsInOrder.length;
         ArrayList var3 = new ArrayList(var2);

         for(int var4 = 0; var4 < var2; ++var4) {
            SettableBeanProperty var5 = this._propsInOrder[var4];
            if (var5 != null && !var1.contains(var5.getName())) {
               var3.add(var5);
            }
         }

         return new BeanPropertyMap(this._caseInsensitive, var3, this._aliasDefs);
      }
   }

   /** @deprecated */
   @Deprecated
   public void replace(SettableBeanProperty var1) {
      String var2 = this.getPropertyName(var1);
      int var3 = this._findIndexInHash(var2);
      if (var3 < 0) {
         throw new NoSuchElementException("No entry '" + var2 + "' found, can't replace");
      } else {
         SettableBeanProperty var4 = (SettableBeanProperty)this._hashArea[var3];
         this._hashArea[var3] = var1;
         this._propsInOrder[this._findFromOrdered(var4)] = var1;
      }
   }

   public void replace(SettableBeanProperty var1, SettableBeanProperty var2) {
      int var3 = 1;

      for(int var4 = this._hashArea.length; var3 <= var4; var3 += 2) {
         if (this._hashArea[var3] == var1) {
            this._hashArea[var3] = var2;
            this._propsInOrder[this._findFromOrdered(var1)] = var2;
            return;
         }
      }

      throw new NoSuchElementException("No entry '" + var1.getName() + "' found, can't replace");
   }

   public void remove(SettableBeanProperty var1) {
      ArrayList var2 = new ArrayList(this._size);
      String var3 = this.getPropertyName(var1);
      boolean var4 = false;
      int var5 = 1;

      for(int var6 = this._hashArea.length; var5 < var6; var5 += 2) {
         SettableBeanProperty var7 = (SettableBeanProperty)this._hashArea[var5];
         if (var7 != null) {
            if (!var4) {
               var4 = var3.equals(this._hashArea[var5 - 1]);
               if (var4) {
                  this._propsInOrder[this._findFromOrdered(var7)] = null;
                  continue;
               }
            }

            var2.add(var7);
         }
      }

      if (!var4) {
         throw new NoSuchElementException("No entry '" + var1.getName() + "' found, can't remove");
      } else {
         this.init(var2);
      }
   }

   public int size() {
      return this._size;
   }

   public boolean isCaseInsensitive() {
      return this._caseInsensitive;
   }

   public boolean hasAliases() {
      return !this._aliasDefs.isEmpty();
   }

   public Iterator iterator() {
      return this._properties().iterator();
   }

   private List _properties() {
      ArrayList var1 = new ArrayList(this._size);
      int var2 = 1;

      for(int var3 = this._hashArea.length; var2 < var3; var2 += 2) {
         SettableBeanProperty var4 = (SettableBeanProperty)this._hashArea[var2];
         if (var4 != null) {
            var1.add(var4);
         }
      }

      return var1;
   }

   public SettableBeanProperty[] getPropertiesInInsertionOrder() {
      return this._propsInOrder;
   }

   protected final String getPropertyName(SettableBeanProperty var1) {
      return this._caseInsensitive ? var1.getName().toLowerCase() : var1.getName();
   }

   public SettableBeanProperty find(int var1) {
      int var2 = 1;

      for(int var3 = this._hashArea.length; var2 < var3; var2 += 2) {
         SettableBeanProperty var4 = (SettableBeanProperty)this._hashArea[var2];
         if (var4 != null && var1 == var4.getPropertyIndex()) {
            return var4;
         }
      }

      return null;
   }

   public SettableBeanProperty find(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null property name");
      } else {
         if (this._caseInsensitive) {
            var1 = var1.toLowerCase();
         }

         int var2 = var1.hashCode() & this._hashMask;
         int var3 = var2 << 1;
         Object var4 = this._hashArea[var3];
         return var4 != var1 && !var1.equals(var4) ? this._find2(var1, var2, var4) : (SettableBeanProperty)this._hashArea[var3 + 1];
      }
   }

   private final SettableBeanProperty _find2(String var1, int var2, Object var3) {
      if (var3 == null) {
         return this._findWithAlias((String)this._aliasMapping.get(var1));
      } else {
         int var4 = this._hashMask + 1;
         int var5 = var4 + (var2 >> 1) << 1;
         var3 = this._hashArea[var5];
         if (var1.equals(var3)) {
            return (SettableBeanProperty)this._hashArea[var5 + 1];
         } else {
            if (var3 != null) {
               int var6 = var4 + (var4 >> 1) << 1;

               for(int var7 = var6 + this._spillCount; var6 < var7; var6 += 2) {
                  var3 = this._hashArea[var6];
                  if (var3 == var1 || var1.equals(var3)) {
                     return (SettableBeanProperty)this._hashArea[var6 + 1];
                  }
               }
            }

            return this._findWithAlias((String)this._aliasMapping.get(var1));
         }
      }
   }

   private SettableBeanProperty _findWithAlias(String var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = this._hashCode(var1);
         int var3 = var2 << 1;
         Object var4 = this._hashArea[var3];
         if (var1.equals(var4)) {
            return (SettableBeanProperty)this._hashArea[var3 + 1];
         } else {
            return var4 == null ? null : this._find2ViaAlias(var1, var2, var4);
         }
      }
   }

   private SettableBeanProperty _find2ViaAlias(String var1, int var2, Object var3) {
      int var4 = this._hashMask + 1;
      int var5 = var4 + (var2 >> 1) << 1;
      var3 = this._hashArea[var5];
      if (var1.equals(var3)) {
         return (SettableBeanProperty)this._hashArea[var5 + 1];
      } else {
         if (var3 != null) {
            int var6 = var4 + (var4 >> 1) << 1;

            for(int var7 = var6 + this._spillCount; var6 < var7; var6 += 2) {
               var3 = this._hashArea[var6];
               if (var3 == var1 || var1.equals(var3)) {
                  return (SettableBeanProperty)this._hashArea[var6 + 1];
               }
            }
         }

         return null;
      }
   }

   public boolean findDeserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3, String var4) throws IOException {
      SettableBeanProperty var5 = this.find(var4);
      if (var5 == null) {
         return false;
      } else {
         try {
            var5.deserializeAndSet(var1, var2, var3);
         } catch (Exception var7) {
            this.wrapAndThrow(var7, var3, var4, var2);
         }

         return true;
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Properties=[");
      int var2 = 0;
      Iterator var3 = this.iterator();

      while(var3.hasNext()) {
         SettableBeanProperty var4 = (SettableBeanProperty)var3.next();
         if (var2++ > 0) {
            var1.append(", ");
         }

         var1.append(var4.getName());
         var1.append('(');
         var1.append(var4.getType());
         var1.append(')');
      }

      var1.append(']');
      if (!this._aliasDefs.isEmpty()) {
         var1.append("(aliases: ");
         var1.append(this._aliasDefs);
         var1.append(")");
      }

      return var1.toString();
   }

   protected SettableBeanProperty _rename(SettableBeanProperty var1, NameTransformer var2) {
      if (var1 == null) {
         return var1;
      } else {
         String var3 = var2.transform(var1.getName());
         var1 = var1.withSimpleName(var3);
         JsonDeserializer var4 = var1.getValueDeserializer();
         if (var4 != null) {
            JsonDeserializer var5 = var4.unwrappingDeserializer(var2);
            if (var5 != var4) {
               var1 = var1.withValueDeserializer(var5);
            }
         }

         return var1;
      }
   }

   protected void wrapAndThrow(Throwable var1, Object var2, String var3, DeserializationContext var4) throws IOException {
      while(var1 instanceof InvocationTargetException && var1.getCause() != null) {
         var1 = var1.getCause();
      }

      ClassUtil.throwIfError(var1);
      boolean var5 = var4 == null || var4.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
      if (var1 instanceof IOException) {
         if (!var5 || !(var1 instanceof JsonProcessingException)) {
            throw (IOException)var1;
         }
      } else if (!var5) {
         ClassUtil.throwIfRTE(var1);
      }

      throw JsonMappingException.wrapWithPath(var1, var2, var3);
   }

   private final int _findIndexInHash(String var1) {
      int var2 = this._hashCode(var1);
      int var3 = var2 << 1;
      if (var1.equals(this._hashArea[var3])) {
         return var3 + 1;
      } else {
         int var4 = this._hashMask + 1;
         var3 = var4 + (var2 >> 1) << 1;
         if (var1.equals(this._hashArea[var3])) {
            return var3 + 1;
         } else {
            int var5 = var4 + (var4 >> 1) << 1;

            for(int var6 = var5 + this._spillCount; var5 < var6; var5 += 2) {
               if (var1.equals(this._hashArea[var5])) {
                  return var5 + 1;
               }
            }

            return -1;
         }
      }
   }

   private final int _findFromOrdered(SettableBeanProperty var1) {
      int var2 = 0;

      for(int var3 = this._propsInOrder.length; var2 < var3; ++var2) {
         if (this._propsInOrder[var2] == var1) {
            return var2;
         }
      }

      throw new IllegalStateException("Illegal state: property '" + var1.getName() + "' missing from _propsInOrder");
   }

   private final int _hashCode(String var1) {
      return var1.hashCode() & this._hashMask;
   }

   private Map _buildAliasMapping(Map var1) {
      if (var1 != null && !var1.isEmpty()) {
         HashMap var2 = new HashMap();
         Iterator var3 = var1.entrySet().iterator();

         while(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            String var5 = (String)var4.getKey();
            if (this._caseInsensitive) {
               var5 = var5.toLowerCase();
            }

            String var8;
            for(Iterator var6 = ((List)var4.getValue()).iterator(); var6.hasNext(); var2.put(var8, var5)) {
               PropertyName var7 = (PropertyName)var6.next();
               var8 = var7.getSimpleName();
               if (this._caseInsensitive) {
                  var8 = var8.toLowerCase();
               }
            }
         }

         return var2;
      } else {
         return Collections.emptyMap();
      }
   }
}
