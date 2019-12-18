package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.util.InternCache;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;

public class PropertyName implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String _USE_DEFAULT = "";
   private static final String _NO_NAME = "";
   public static final PropertyName USE_DEFAULT = new PropertyName("", (String)null);
   public static final PropertyName NO_NAME = new PropertyName(new String(""), (String)null);
   protected final String _simpleName;
   protected final String _namespace;
   protected SerializableString _encodedSimple;

   public PropertyName(String var1) {
      this(var1, (String)null);
   }

   public PropertyName(String var1, String var2) {
      super();
      this._simpleName = ClassUtil.nonNullString(var1);
      this._namespace = var2;
   }

   protected Object readResolve() {
      return this._namespace != null || this._simpleName != null && !"".equals(this._simpleName) ? this : USE_DEFAULT;
   }

   public static PropertyName construct(String var0) {
      return var0 != null && var0.length() != 0 ? new PropertyName(InternCache.instance.intern(var0), (String)null) : USE_DEFAULT;
   }

   public static PropertyName construct(String var0, String var1) {
      if (var0 == null) {
         var0 = "";
      }

      return var1 == null && var0.length() == 0 ? USE_DEFAULT : new PropertyName(InternCache.instance.intern(var0), var1);
   }

   public PropertyName internSimpleName() {
      if (this._simpleName.length() == 0) {
         return this;
      } else {
         String var1 = InternCache.instance.intern(this._simpleName);
         return var1 == this._simpleName ? this : new PropertyName(var1, this._namespace);
      }
   }

   public PropertyName withSimpleName(String var1) {
      if (var1 == null) {
         var1 = "";
      }

      return var1.equals(this._simpleName) ? this : new PropertyName(var1, this._namespace);
   }

   public PropertyName withNamespace(String var1) {
      if (var1 == null) {
         if (this._namespace == null) {
            return this;
         }
      } else if (var1.equals(this._namespace)) {
         return this;
      }

      return new PropertyName(this._simpleName, var1);
   }

   public String getSimpleName() {
      return this._simpleName;
   }

   public SerializableString simpleAsEncoded(MapperConfig var1) {
      Object var2 = this._encodedSimple;
      if (var2 == null) {
         if (var1 == null) {
            var2 = new SerializedString(this._simpleName);
         } else {
            var2 = var1.compileString(this._simpleName);
         }

         this._encodedSimple = (SerializableString)var2;
      }

      return (SerializableString)var2;
   }

   public String getNamespace() {
      return this._namespace;
   }

   public boolean hasSimpleName() {
      return this._simpleName.length() > 0;
   }

   public boolean hasSimpleName(String var1) {
      return this._simpleName.equals(var1);
   }

   public boolean hasNamespace() {
      return this._namespace != null;
   }

   public boolean isEmpty() {
      return this._namespace == null && this._simpleName.isEmpty();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         PropertyName var2 = (PropertyName)var1;
         if (this._simpleName == null) {
            if (var2._simpleName != null) {
               return false;
            }
         } else if (!this._simpleName.equals(var2._simpleName)) {
            return false;
         }

         if (this._namespace == null) {
            return null == var2._namespace;
         } else {
            return this._namespace.equals(var2._namespace);
         }
      }
   }

   public int hashCode() {
      return this._namespace == null ? this._simpleName.hashCode() : this._namespace.hashCode() ^ this._simpleName.hashCode();
   }

   public String toString() {
      return this._namespace == null ? this._simpleName : "{" + this._namespace + "}" + this._simpleName;
   }
}
