package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public class JsonMappingException$Reference implements Serializable {
   private static final long serialVersionUID = 2L;
   protected transient Object _from;
   protected String _fieldName;
   protected int _index = -1;
   protected String _desc;

   protected JsonMappingException$Reference() {
      super();
   }

   public JsonMappingException$Reference(Object var1) {
      super();
      this._from = var1;
   }

   public JsonMappingException$Reference(Object var1, String var2) {
      super();
      this._from = var1;
      if (var2 == null) {
         throw new NullPointerException("Cannot pass null fieldName");
      } else {
         this._fieldName = var2;
      }
   }

   public JsonMappingException$Reference(Object var1, int var2) {
      super();
      this._from = var1;
      this._index = var2;
   }

   void setFieldName(String var1) {
      this._fieldName = var1;
   }

   void setIndex(int var1) {
      this._index = var1;
   }

   void setDescription(String var1) {
      this._desc = var1;
   }

   @JsonIgnore
   public Object getFrom() {
      return this._from;
   }

   public String getFieldName() {
      return this._fieldName;
   }

   public int getIndex() {
      return this._index;
   }

   public String getDescription() {
      if (this._desc == null) {
         StringBuilder var1 = new StringBuilder();
         if (this._from == null) {
            var1.append("UNKNOWN");
         } else {
            Class var2 = this._from instanceof Class ? (Class)this._from : this._from.getClass();

            int var3;
            for(var3 = 0; var2.isArray(); ++var3) {
               var2 = var2.getComponentType();
            }

            var1.append(var2.getName());

            while(true) {
               --var3;
               if (var3 < 0) {
                  break;
               }

               var1.append("[]");
            }
         }

         var1.append('[');
         if (this._fieldName != null) {
            var1.append('"');
            var1.append(this._fieldName);
            var1.append('"');
         } else if (this._index >= 0) {
            var1.append(this._index);
         } else {
            var1.append('?');
         }

         var1.append(']');
         this._desc = var1.toString();
      }

      return this._desc;
   }

   public String toString() {
      return this.getDescription();
   }

   Object writeReplace() {
      this.getDescription();
      return this;
   }
}
