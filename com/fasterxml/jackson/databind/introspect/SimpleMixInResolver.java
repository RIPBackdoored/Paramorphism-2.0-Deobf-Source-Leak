package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SimpleMixInResolver implements ClassIntrospector$MixInResolver, Serializable {
   private static final long serialVersionUID = 1L;
   protected final ClassIntrospector$MixInResolver _overrides;
   protected Map _localMixIns;

   public SimpleMixInResolver(ClassIntrospector$MixInResolver var1) {
      super();
      this._overrides = var1;
   }

   protected SimpleMixInResolver(ClassIntrospector$MixInResolver var1, Map var2) {
      super();
      this._overrides = var1;
      this._localMixIns = var2;
   }

   public SimpleMixInResolver withOverrides(ClassIntrospector$MixInResolver var1) {
      return new SimpleMixInResolver(var1, this._localMixIns);
   }

   public SimpleMixInResolver withoutLocalDefinitions() {
      return new SimpleMixInResolver(this._overrides, (Map)null);
   }

   public void setLocalDefinitions(Map var1) {
      if (var1 != null && !var1.isEmpty()) {
         HashMap var2 = new HashMap(var1.size());
         Iterator var3 = var1.entrySet().iterator();

         while(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            var2.put(new ClassKey((Class)var4.getKey()), var4.getValue());
         }

         this._localMixIns = var2;
      } else {
         this._localMixIns = null;
      }

   }

   public void addLocalDefinition(Class var1, Class var2) {
      if (this._localMixIns == null) {
         this._localMixIns = new HashMap();
      }

      this._localMixIns.put(new ClassKey(var1), var2);
   }

   public SimpleMixInResolver copy() {
      ClassIntrospector$MixInResolver var1 = this._overrides == null ? null : this._overrides.copy();
      HashMap var2 = this._localMixIns == null ? null : new HashMap(this._localMixIns);
      return new SimpleMixInResolver(var1, var2);
   }

   public Class findMixInClassFor(Class var1) {
      Class var2 = this._overrides == null ? null : this._overrides.findMixInClassFor(var1);
      if (var2 == null && this._localMixIns != null) {
         var2 = (Class)this._localMixIns.get(new ClassKey(var1));
      }

      return var2;
   }

   public int localSize() {
      return this._localMixIns == null ? 0 : this._localMixIns.size();
   }

   public ClassIntrospector$MixInResolver copy() {
      return this.copy();
   }
}
