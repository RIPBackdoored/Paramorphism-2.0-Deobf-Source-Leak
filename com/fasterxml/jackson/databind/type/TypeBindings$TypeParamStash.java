package com.fasterxml.jackson.databind.type;

import java.lang.reflect.TypeVariable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class TypeBindings$TypeParamStash {
   private static final TypeVariable[] VARS_ABSTRACT_LIST = AbstractList.class.getTypeParameters();
   private static final TypeVariable[] VARS_COLLECTION = Collection.class.getTypeParameters();
   private static final TypeVariable[] VARS_ITERABLE = Iterable.class.getTypeParameters();
   private static final TypeVariable[] VARS_LIST = List.class.getTypeParameters();
   private static final TypeVariable[] VARS_ARRAY_LIST = ArrayList.class.getTypeParameters();
   private static final TypeVariable[] VARS_MAP = Map.class.getTypeParameters();
   private static final TypeVariable[] VARS_HASH_MAP = HashMap.class.getTypeParameters();
   private static final TypeVariable[] VARS_LINKED_HASH_MAP = LinkedHashMap.class.getTypeParameters();

   TypeBindings$TypeParamStash() {
      super();
   }

   public static TypeVariable[] paramsFor1(Class var0) {
      if (var0 == Collection.class) {
         return VARS_COLLECTION;
      } else if (var0 == List.class) {
         return VARS_LIST;
      } else if (var0 == ArrayList.class) {
         return VARS_ARRAY_LIST;
      } else if (var0 == AbstractList.class) {
         return VARS_ABSTRACT_LIST;
      } else {
         return var0 == Iterable.class ? VARS_ITERABLE : var0.getTypeParameters();
      }
   }

   public static TypeVariable[] paramsFor2(Class var0) {
      if (var0 == Map.class) {
         return VARS_MAP;
      } else if (var0 == HashMap.class) {
         return VARS_HASH_MAP;
      } else {
         return var0 == LinkedHashMap.class ? VARS_LINKED_HASH_MAP : var0.getTypeParameters();
      }
   }
}
