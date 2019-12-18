package com.fasterxml.jackson.databind.cfg;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ContextAttributes$Impl extends ContextAttributes implements Serializable {
   private static final long serialVersionUID = 1L;
   protected static final ContextAttributes$Impl EMPTY = new ContextAttributes$Impl(Collections.emptyMap());
   protected static final Object NULL_SURROGATE = new Object();
   protected final Map _shared;
   protected transient Map _nonShared;

   protected ContextAttributes$Impl(Map var1) {
      super();
      this._shared = var1;
      this._nonShared = null;
   }

   protected ContextAttributes$Impl(Map var1, Map var2) {
      super();
      this._shared = var1;
      this._nonShared = var2;
   }

   public static ContextAttributes getEmpty() {
      return EMPTY;
   }

   public ContextAttributes withSharedAttribute(Object var1, Object var2) {
      Object var3;
      if (this == EMPTY) {
         var3 = new HashMap(8);
      } else {
         var3 = this._copy(this._shared);
      }

      ((Map)var3).put(var1, var2);
      return new ContextAttributes$Impl((Map)var3);
   }

   public ContextAttributes withSharedAttributes(Map var1) {
      return new ContextAttributes$Impl(var1);
   }

   public ContextAttributes withoutSharedAttribute(Object var1) {
      if (this._shared.isEmpty()) {
         return this;
      } else if (this._shared.containsKey(var1)) {
         if (this._shared.size() == 1) {
            return EMPTY;
         } else {
            Map var2 = this._copy(this._shared);
            var2.remove(var1);
            return new ContextAttributes$Impl(var2);
         }
      } else {
         return this;
      }
   }

   public Object getAttribute(Object var1) {
      if (this._nonShared != null) {
         Object var2 = this._nonShared.get(var1);
         if (var2 != null) {
            if (var2 == NULL_SURROGATE) {
               return null;
            }

            return var2;
         }
      }

      return this._shared.get(var1);
   }

   public ContextAttributes withPerCallAttribute(Object var1, Object var2) {
      if (var2 == null) {
         if (!this._shared.containsKey(var1)) {
            if (this._nonShared != null && this._nonShared.containsKey(var1)) {
               this._nonShared.remove(var1);
               return this;
            }

            return this;
         }

         var2 = NULL_SURROGATE;
      }

      if (this._nonShared == null) {
         return this.nonSharedInstance(var1, var2);
      } else {
         this._nonShared.put(var1, var2);
         return this;
      }
   }

   protected ContextAttributes nonSharedInstance(Object var1, Object var2) {
      HashMap var3 = new HashMap();
      if (var2 == null) {
         var2 = NULL_SURROGATE;
      }

      var3.put(var1, var2);
      return new ContextAttributes$Impl(this._shared, var3);
   }

   private Map _copy(Map var1) {
      return new HashMap(var1);
   }
}
