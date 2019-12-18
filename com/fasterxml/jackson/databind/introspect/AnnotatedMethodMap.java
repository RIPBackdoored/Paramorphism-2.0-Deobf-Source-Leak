package com.fasterxml.jackson.databind.introspect;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public final class AnnotatedMethodMap implements Iterable {
   protected Map _methods;

   public AnnotatedMethodMap() {
      super();
   }

   public AnnotatedMethodMap(Map var1) {
      super();
      this._methods = var1;
   }

   public int size() {
      return this._methods == null ? 0 : this._methods.size();
   }

   public AnnotatedMethod find(String var1, Class[] var2) {
      return this._methods == null ? null : (AnnotatedMethod)this._methods.get(new MemberKey(var1, var2));
   }

   public AnnotatedMethod find(Method var1) {
      return this._methods == null ? null : (AnnotatedMethod)this._methods.get(new MemberKey(var1));
   }

   public Iterator iterator() {
      return this._methods == null ? Collections.emptyIterator() : this._methods.values().iterator();
   }
}
