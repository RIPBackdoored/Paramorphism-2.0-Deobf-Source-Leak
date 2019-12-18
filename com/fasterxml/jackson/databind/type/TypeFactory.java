package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.LRUMap;
import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

public final class TypeFactory implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final JavaType[] NO_TYPES = new JavaType[0];
   protected static final TypeFactory instance = new TypeFactory();
   protected static final TypeBindings EMPTY_BINDINGS = TypeBindings.emptyBindings();
   private static final Class CLS_STRING = String.class;
   private static final Class CLS_OBJECT = Object.class;
   private static final Class CLS_COMPARABLE = Comparable.class;
   private static final Class CLS_CLASS = Class.class;
   private static final Class CLS_ENUM = Enum.class;
   private static final Class CLS_BOOL;
   private static final Class CLS_INT;
   private static final Class CLS_LONG;
   protected static final SimpleType CORE_TYPE_BOOL;
   protected static final SimpleType CORE_TYPE_INT;
   protected static final SimpleType CORE_TYPE_LONG;
   protected static final SimpleType CORE_TYPE_STRING;
   protected static final SimpleType CORE_TYPE_OBJECT;
   protected static final SimpleType CORE_TYPE_COMPARABLE;
   protected static final SimpleType CORE_TYPE_ENUM;
   protected static final SimpleType CORE_TYPE_CLASS;
   protected final LRUMap _typeCache;
   protected final TypeModifier[] _modifiers;
   protected final TypeParser _parser;
   protected final ClassLoader _classLoader;

   private TypeFactory() {
      this((LRUMap)null);
   }

   protected TypeFactory(LRUMap var1) {
      super();
      if (var1 == null) {
         var1 = new LRUMap(16, 200);
      }

      this._typeCache = var1;
      this._parser = new TypeParser(this);
      this._modifiers = null;
      this._classLoader = null;
   }

   protected TypeFactory(LRUMap var1, TypeParser var2, TypeModifier[] var3, ClassLoader var4) {
      super();
      if (var1 == null) {
         var1 = new LRUMap(16, 200);
      }

      this._typeCache = var1;
      this._parser = var2.withFactory(this);
      this._modifiers = var3;
      this._classLoader = var4;
   }

   public TypeFactory withModifier(TypeModifier var1) {
      LRUMap var2 = this._typeCache;
      TypeModifier[] var3;
      if (var1 == null) {
         var3 = null;
         var2 = null;
      } else if (this._modifiers == null) {
         var3 = new TypeModifier[]{var1};
      } else {
         var3 = (TypeModifier[])ArrayBuilders.insertInListNoDup(this._modifiers, var1);
      }

      return new TypeFactory(var2, this._parser, var3, this._classLoader);
   }

   public TypeFactory withClassLoader(ClassLoader var1) {
      return new TypeFactory(this._typeCache, this._parser, this._modifiers, var1);
   }

   public TypeFactory withCache(LRUMap var1) {
      return new TypeFactory(var1, this._parser, this._modifiers, this._classLoader);
   }

   public static TypeFactory defaultInstance() {
      return instance;
   }

   public void clearCache() {
      this._typeCache.clear();
   }

   public ClassLoader getClassLoader() {
      return this._classLoader;
   }

   public static JavaType unknownType() {
      return defaultInstance()._unknownType();
   }

   public static Class rawClass(Type var0) {
      return var0 instanceof Class ? (Class)var0 : defaultInstance().constructType(var0).getRawClass();
   }

   public Class findClass(String var1) throws ClassNotFoundException {
      if (var1.indexOf(46) < 0) {
         Class var2 = this._findPrimitive(var1);
         if (var2 != null) {
            return var2;
         }
      }

      Throwable var7 = null;
      ClassLoader var3 = this.getClassLoader();
      if (var3 == null) {
         var3 = Thread.currentThread().getContextClassLoader();
      }

      Class var10000;
      if (var3 != null) {
         label32: {
            try {
               var10000 = this.classForName(var1, true, var3);
            } catch (Exception var6) {
               var7 = ClassUtil.getRootCause(var6);
               break label32;
            }

            return var10000;
         }
      }

      try {
         var10000 = this.classForName(var1);
      } catch (Exception var5) {
         if (var7 == null) {
            var7 = ClassUtil.getRootCause(var5);
         }

         ClassUtil.throwIfRTE(var7);
         throw new ClassNotFoundException(var7.getMessage(), var7);
      }

      return var10000;
   }

   protected Class classForName(String var1, boolean var2, ClassLoader var3) throws ClassNotFoundException {
      return Class.forName(var1, true, var3);
   }

   protected Class classForName(String var1) throws ClassNotFoundException {
      return Class.forName(var1);
   }

   protected Class _findPrimitive(String var1) {
      if ("int".equals(var1)) {
         return Integer.TYPE;
      } else if ("long".equals(var1)) {
         return Long.TYPE;
      } else if ("float".equals(var1)) {
         return Float.TYPE;
      } else if ("double".equals(var1)) {
         return Double.TYPE;
      } else if ("boolean".equals(var1)) {
         return Boolean.TYPE;
      } else if ("byte".equals(var1)) {
         return Byte.TYPE;
      } else if ("char".equals(var1)) {
         return Character.TYPE;
      } else if ("short".equals(var1)) {
         return Short.TYPE;
      } else {
         return "void".equals(var1) ? Void.TYPE : null;
      }
   }

   public JavaType constructSpecializedType(JavaType var1, Class var2) {
      Class var3 = var1.getRawClass();
      if (var3 == var2) {
         return var1;
      } else {
         JavaType var4;
         if (var3 == Object.class) {
            var4 = this._fromClass((ClassStack)null, var2, EMPTY_BINDINGS);
         } else {
            if (!var3.isAssignableFrom(var2)) {
               throw new IllegalArgumentException(String.format("Class %s not subtype of %s", var2.getName(), var1));
            }

            if (var1.getBindings().isEmpty()) {
               var4 = this._fromClass((ClassStack)null, var2, EMPTY_BINDINGS);
            } else {
               label70: {
                  if (var1.isContainerType()) {
                     if (var1.isMapLikeType()) {
                        if (var2 == HashMap.class || var2 == LinkedHashMap.class || var2 == EnumMap.class || var2 == TreeMap.class) {
                           var4 = this._fromClass((ClassStack)null, var2, TypeBindings.create(var2, var1.getKeyType(), var1.getContentType()));
                           break label70;
                        }
                     } else if (var1.isCollectionLikeType()) {
                        if (var2 == ArrayList.class || var2 == LinkedList.class || var2 == HashSet.class || var2 == TreeSet.class) {
                           var4 = this._fromClass((ClassStack)null, var2, TypeBindings.create(var2, var1.getContentType()));
                           break label70;
                        }

                        if (var3 == EnumSet.class) {
                           return var1;
                        }
                     }
                  }

                  int var5 = var2.getTypeParameters().length;
                  if (var5 == 0) {
                     var4 = this._fromClass((ClassStack)null, var2, EMPTY_BINDINGS);
                  } else {
                     TypeBindings var6 = this._bindingsForSubtype(var1, var5, var2);
                     var4 = this._fromClass((ClassStack)null, var2, var6);
                  }
               }
            }
         }

         var4 = var4.withHandlersFrom(var1);
         return var4;
      }
   }

   private TypeBindings _bindingsForSubtype(JavaType var1, int var2, Class var3) {
      PlaceholderForType[] var4 = new PlaceholderForType[var2];

      for(int var5 = 0; var5 < var2; ++var5) {
         var4[var5] = new PlaceholderForType(var5);
      }

      TypeBindings var12 = TypeBindings.create(var3, (JavaType[])var4);
      JavaType var6 = this._fromClass((ClassStack)null, var3, var12);
      JavaType var7 = var6.findSuperType(var1.getRawClass());
      if (var7 == null) {
         throw new IllegalArgumentException(String.format("Internal error: unable to locate supertype (%s) from resolved subtype %s", var1.getRawClass().getName(), var3.getName()));
      } else {
         String var8 = this._resolveTypePlaceholders(var1, var7);
         if (var8 != null) {
            throw new IllegalArgumentException("Failed to specialize base type " + var1.toCanonical() + " as " + var3.getName() + ", problem: " + var8);
         } else {
            JavaType[] var9 = new JavaType[var2];

            for(int var10 = 0; var10 < var2; ++var10) {
               JavaType var11 = var4[var10].actualType();
               if (var11 == null) {
                  var11 = unknownType();
               }

               var9[var10] = var11;
            }

            return TypeBindings.create(var3, var9);
         }
      }
   }

   private String _resolveTypePlaceholders(JavaType var1, JavaType var2) throws IllegalArgumentException {
      List var3 = var1.getBindings().getTypeParameters();
      List var4 = var2.getBindings().getTypeParameters();
      int var5 = 0;

      for(int var6 = var3.size(); var5 < var6; ++var5) {
         JavaType var7 = (JavaType)var3.get(var5);
         JavaType var8 = (JavaType)var4.get(var5);
         if (!this._verifyAndResolvePlaceholders(var7, var8) && !var7.hasRawClass(Object.class) && (var5 != 0 || !var1.hasRawClass(Map.class) || !var8.hasRawClass(Object.class))) {
            return String.format("Type parameter #%d/%d differs; can not specialize %s with %s", var5 + 1, var6, var7.toCanonical(), var8.toCanonical());
         }
      }

      return null;
   }

   private boolean _verifyAndResolvePlaceholders(JavaType var1, JavaType var2) {
      if (var2 instanceof PlaceholderForType) {
         ((PlaceholderForType)var2).actualType(var1);
         return true;
      } else if (var1.getRawClass() != var2.getRawClass()) {
         return false;
      } else {
         List var3 = var1.getBindings().getTypeParameters();
         List var4 = var2.getBindings().getTypeParameters();
         int var5 = 0;

         for(int var6 = var3.size(); var5 < var6; ++var5) {
            JavaType var7 = (JavaType)var3.get(var5);
            JavaType var8 = (JavaType)var4.get(var5);
            if (!this._verifyAndResolvePlaceholders(var7, var8)) {
               return false;
            }
         }

         return true;
      }
   }

   public JavaType constructGeneralizedType(JavaType var1, Class var2) {
      Class var3 = var1.getRawClass();
      if (var3 == var2) {
         return var1;
      } else {
         JavaType var4 = var1.findSuperType(var2);
         if (var4 == null) {
            if (!var2.isAssignableFrom(var3)) {
               throw new IllegalArgumentException(String.format("Class %s not a super-type of %s", var2.getName(), var1));
            } else {
               throw new IllegalArgumentException(String.format("Internal error: class %s not included as super-type for %s", var2.getName(), var1));
            }
         } else {
            return var4;
         }
      }
   }

   public JavaType constructFromCanonical(String var1) throws IllegalArgumentException {
      return this._parser.parse(var1);
   }

   public JavaType[] findTypeParameters(JavaType var1, Class var2) {
      JavaType var3 = var1.findSuperType(var2);
      return var3 == null ? NO_TYPES : var3.getBindings().typeParameterArray();
   }

   /** @deprecated */
   @Deprecated
   public JavaType[] findTypeParameters(Class var1, Class var2, TypeBindings var3) {
      return this.findTypeParameters(this.constructType(var1, (TypeBindings)var3), var2);
   }

   /** @deprecated */
   @Deprecated
   public JavaType[] findTypeParameters(Class var1, Class var2) {
      return this.findTypeParameters(this.constructType((Type)var1), var2);
   }

   public JavaType moreSpecificType(JavaType var1, JavaType var2) {
      if (var1 == null) {
         return var2;
      } else if (var2 == null) {
         return var1;
      } else {
         Class var3 = var1.getRawClass();
         Class var4 = var2.getRawClass();
         if (var3 == var4) {
            return var1;
         } else {
            return var3.isAssignableFrom(var4) ? var2 : var1;
         }
      }
   }

   public JavaType constructType(Type var1) {
      return this._fromAny((ClassStack)null, var1, EMPTY_BINDINGS);
   }

   public JavaType constructType(Type var1, TypeBindings var2) {
      return this._fromAny((ClassStack)null, var1, var2);
   }

   public JavaType constructType(TypeReference var1) {
      return this._fromAny((ClassStack)null, var1.getType(), EMPTY_BINDINGS);
   }

   /** @deprecated */
   @Deprecated
   public JavaType constructType(Type var1, Class var2) {
      JavaType var3 = var2 == null ? null : this.constructType((Type)var2);
      return this.constructType(var1, var3);
   }

   /** @deprecated */
   @Deprecated
   public JavaType constructType(Type var1, JavaType var2) {
      TypeBindings var3;
      if (var2 == null) {
         var3 = EMPTY_BINDINGS;
      } else {
         var3 = var2.getBindings();
         if (var1.getClass() != Class.class) {
            while(var3.isEmpty()) {
               var2 = var2.getSuperClass();
               if (var2 == null) {
                  break;
               }

               var3 = var2.getBindings();
            }
         }
      }

      return this._fromAny((ClassStack)null, var1, var3);
   }

   public ArrayType constructArrayType(Class var1) {
      return ArrayType.construct(this._fromAny((ClassStack)null, var1, (TypeBindings)null), (TypeBindings)null);
   }

   public ArrayType constructArrayType(JavaType var1) {
      return ArrayType.construct(var1, (TypeBindings)null);
   }

   public CollectionType constructCollectionType(Class var1, Class var2) {
      return this.constructCollectionType(var1, this._fromClass((ClassStack)null, var2, EMPTY_BINDINGS));
   }

   public CollectionType constructCollectionType(Class var1, JavaType var2) {
      TypeBindings var3 = TypeBindings.createIfNeeded(var1, var2);
      CollectionType var4 = (CollectionType)this._fromClass((ClassStack)null, var1, var3);
      if (var3.isEmpty() && var2 != null) {
         JavaType var5 = var4.findSuperType(Collection.class);
         JavaType var6 = var5.getContentType();
         if (!var6.equals(var2)) {
            throw new IllegalArgumentException(String.format("Non-generic Collection class %s did not resolve to something with element type %s but %s ", ClassUtil.nameOf(var1), var2, var6));
         }
      }

      return var4;
   }

   public CollectionLikeType constructCollectionLikeType(Class var1, Class var2) {
      return this.constructCollectionLikeType(var1, this._fromClass((ClassStack)null, var2, EMPTY_BINDINGS));
   }

   public CollectionLikeType constructCollectionLikeType(Class var1, JavaType var2) {
      JavaType var3 = this._fromClass((ClassStack)null, var1, TypeBindings.createIfNeeded(var1, var2));
      return var3 instanceof CollectionLikeType ? (CollectionLikeType)var3 : CollectionLikeType.upgradeFrom(var3, var2);
   }

   public MapType constructMapType(Class var1, Class var2, Class var3) {
      Object var4;
      Object var5;
      if (var1 == Properties.class) {
         var4 = var5 = CORE_TYPE_STRING;
      } else {
         var4 = this._fromClass((ClassStack)null, var2, EMPTY_BINDINGS);
         var5 = this._fromClass((ClassStack)null, var3, EMPTY_BINDINGS);
      }

      return this.constructMapType(var1, (JavaType)var4, (JavaType)var5);
   }

   public MapType constructMapType(Class var1, JavaType var2, JavaType var3) {
      TypeBindings var4 = TypeBindings.createIfNeeded(var1, new JavaType[]{var2, var3});
      MapType var5 = (MapType)this._fromClass((ClassStack)null, var1, var4);
      if (var4.isEmpty()) {
         JavaType var6 = var5.findSuperType(Map.class);
         JavaType var7 = var6.getKeyType();
         if (!var7.equals(var2)) {
            throw new IllegalArgumentException(String.format("Non-generic Map class %s did not resolve to something with key type %s but %s ", ClassUtil.nameOf(var1), var2, var7));
         }

         JavaType var8 = var6.getContentType();
         if (!var8.equals(var3)) {
            throw new IllegalArgumentException(String.format("Non-generic Map class %s did not resolve to something with value type %s but %s ", ClassUtil.nameOf(var1), var3, var8));
         }
      }

      return var5;
   }

   public MapLikeType constructMapLikeType(Class var1, Class var2, Class var3) {
      return this.constructMapLikeType(var1, this._fromClass((ClassStack)null, var2, EMPTY_BINDINGS), this._fromClass((ClassStack)null, var3, EMPTY_BINDINGS));
   }

   public MapLikeType constructMapLikeType(Class var1, JavaType var2, JavaType var3) {
      JavaType var4 = this._fromClass((ClassStack)null, var1, TypeBindings.createIfNeeded(var1, new JavaType[]{var2, var3}));
      return var4 instanceof MapLikeType ? (MapLikeType)var4 : MapLikeType.upgradeFrom(var4, var2, var3);
   }

   public JavaType constructSimpleType(Class var1, JavaType[] var2) {
      return this._fromClass((ClassStack)null, var1, TypeBindings.create(var1, var2));
   }

   /** @deprecated */
   @Deprecated
   public JavaType constructSimpleType(Class var1, Class var2, JavaType[] var3) {
      return this.constructSimpleType(var1, var3);
   }

   public JavaType constructReferenceType(Class var1, JavaType var2) {
      return ReferenceType.construct(var1, (TypeBindings)null, (JavaType)null, (JavaType[])null, var2);
   }

   /** @deprecated */
   @Deprecated
   public JavaType uncheckedSimpleType(Class var1) {
      return this._constructSimple(var1, EMPTY_BINDINGS, (JavaType)null, (JavaType[])null);
   }

   public JavaType constructParametricType(Class var1, Class... var2) {
      int var3 = var2.length;
      JavaType[] var4 = new JavaType[var3];

      for(int var5 = 0; var5 < var3; ++var5) {
         var4[var5] = this._fromClass((ClassStack)null, var2[var5], EMPTY_BINDINGS);
      }

      return this.constructParametricType(var1, var4);
   }

   public JavaType constructParametricType(Class var1, JavaType... var2) {
      return this._fromClass((ClassStack)null, var1, TypeBindings.create(var1, var2));
   }

   /** @deprecated */
   @Deprecated
   public JavaType constructParametrizedType(Class var1, Class var2, JavaType... var3) {
      return this.constructParametricType(var1, var3);
   }

   /** @deprecated */
   @Deprecated
   public JavaType constructParametrizedType(Class var1, Class var2, Class... var3) {
      return this.constructParametricType(var1, var3);
   }

   public CollectionType constructRawCollectionType(Class var1) {
      return this.constructCollectionType(var1, unknownType());
   }

   public CollectionLikeType constructRawCollectionLikeType(Class var1) {
      return this.constructCollectionLikeType(var1, unknownType());
   }

   public MapType constructRawMapType(Class var1) {
      return this.constructMapType(var1, unknownType(), unknownType());
   }

   public MapLikeType constructRawMapLikeType(Class var1) {
      return this.constructMapLikeType(var1, unknownType(), unknownType());
   }

   private JavaType _mapType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      Object var5;
      Object var6;
      if (var1 == Properties.class) {
         var5 = var6 = CORE_TYPE_STRING;
      } else {
         List var7 = var2.getTypeParameters();
         switch(var7.size()) {
         case 0:
            var5 = var6 = this._unknownType();
            break;
         case 2:
            var5 = (JavaType)var7.get(0);
            var6 = (JavaType)var7.get(1);
            break;
         default:
            throw new IllegalArgumentException("Strange Map type " + var1.getName() + ": cannot determine type parameters");
         }
      }

      return MapType.construct(var1, var2, var3, var4, (JavaType)var5, (JavaType)var6);
   }

   private JavaType _collectionType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      List var5 = var2.getTypeParameters();
      JavaType var6;
      if (var5.isEmpty()) {
         var6 = this._unknownType();
      } else {
         if (var5.size() != 1) {
            throw new IllegalArgumentException("Strange Collection type " + var1.getName() + ": cannot determine type parameters");
         }

         var6 = (JavaType)var5.get(0);
      }

      return CollectionType.construct(var1, var2, var3, var4, var6);
   }

   private JavaType _referenceType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      List var5 = var2.getTypeParameters();
      JavaType var6;
      if (var5.isEmpty()) {
         var6 = this._unknownType();
      } else {
         if (var5.size() != 1) {
            throw new IllegalArgumentException("Strange Reference type " + var1.getName() + ": cannot determine type parameters");
         }

         var6 = (JavaType)var5.get(0);
      }

      return ReferenceType.construct(var1, var2, var3, var4, var6);
   }

   protected JavaType _constructSimple(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      if (var2.isEmpty()) {
         JavaType var5 = this._findWellKnownSimple(var1);
         if (var5 != null) {
            return var5;
         }
      }

      return this._newSimpleType(var1, var2, var3, var4);
   }

   protected JavaType _newSimpleType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      return new SimpleType(var1, var2, var3, var4);
   }

   protected JavaType _unknownType() {
      return CORE_TYPE_OBJECT;
   }

   protected JavaType _findWellKnownSimple(Class var1) {
      if (var1.isPrimitive()) {
         if (var1 == CLS_BOOL) {
            return CORE_TYPE_BOOL;
         }

         if (var1 == CLS_INT) {
            return CORE_TYPE_INT;
         }

         if (var1 == CLS_LONG) {
            return CORE_TYPE_LONG;
         }
      } else {
         if (var1 == CLS_STRING) {
            return CORE_TYPE_STRING;
         }

         if (var1 == CLS_OBJECT) {
            return CORE_TYPE_OBJECT;
         }
      }

      return null;
   }

   protected JavaType _fromAny(ClassStack var1, Type var2, TypeBindings var3) {
      JavaType var4;
      if (var2 instanceof Class) {
         var4 = this._fromClass(var1, (Class)var2, EMPTY_BINDINGS);
      } else if (var2 instanceof ParameterizedType) {
         var4 = this._fromParamType(var1, (ParameterizedType)var2, var3);
      } else {
         if (var2 instanceof JavaType) {
            return (JavaType)var2;
         }

         if (var2 instanceof GenericArrayType) {
            var4 = this._fromArrayType(var1, (GenericArrayType)var2, var3);
         } else if (var2 instanceof TypeVariable) {
            var4 = this._fromVariable(var1, (TypeVariable)var2, var3);
         } else {
            if (!(var2 instanceof WildcardType)) {
               throw new IllegalArgumentException("Unrecognized Type: " + (var2 == null ? "[null]" : var2.toString()));
            }

            var4 = this._fromWildcard(var1, (WildcardType)var2, var3);
         }
      }

      if (this._modifiers != null) {
         TypeBindings var5 = var4.getBindings();
         if (var5 == null) {
            var5 = EMPTY_BINDINGS;
         }

         TypeModifier[] var6 = this._modifiers;
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            TypeModifier var9 = var6[var8];
            JavaType var10 = var9.modifyType(var4, var2, var5, this);
            if (var10 == null) {
               throw new IllegalStateException(String.format("TypeModifier %s (of type %s) return null for type %s", var9, var9.getClass().getName(), var4));
            }

            var4 = var10;
         }
      }

      return var4;
   }

   protected JavaType _fromClass(ClassStack var1, Class var2, TypeBindings var3) {
      JavaType var4 = this._findWellKnownSimple(var2);
      if (var4 != null) {
         return var4;
      } else {
         Object var5;
         if (var3 != null && !var3.isEmpty()) {
            var5 = var3.asKey(var2);
         } else {
            var5 = var2;
         }

         Object var8 = (JavaType)this._typeCache.get(var5);
         if (var8 != null) {
            return (JavaType)var8;
         } else {
            if (var1 == null) {
               var1 = new ClassStack(var2);
            } else {
               ClassStack var6 = var1.find(var2);
               if (var6 != null) {
                  ResolvedRecursiveType var10 = new ResolvedRecursiveType(var2, EMPTY_BINDINGS);
                  var6.addSelfReference(var10);
                  return var10;
               }

               var1 = var1.child(var2);
            }

            if (var2.isArray()) {
               var8 = ArrayType.construct(this._fromAny(var1, var2.getComponentType(), var3), var3);
            } else {
               JavaType[] var7;
               JavaType var9;
               if (var2.isInterface()) {
                  var9 = null;
                  var7 = this._resolveSuperInterfaces(var1, var2, var3);
               } else {
                  var9 = this._resolveSuperClass(var1, var2, var3);
                  var7 = this._resolveSuperInterfaces(var1, var2, var3);
               }

               if (var2 == Properties.class) {
                  var8 = MapType.construct(var2, var3, var9, var7, CORE_TYPE_STRING, CORE_TYPE_STRING);
               } else if (var9 != null) {
                  var8 = var9.refine(var2, var3, var9, var7);
               }

               if (var8 == null) {
                  var8 = this._fromWellKnownClass(var1, var2, var3, var9, var7);
                  if (var8 == null) {
                     var8 = this._fromWellKnownInterface(var1, var2, var3, var9, var7);
                     if (var8 == null) {
                        var8 = this._newSimpleType(var2, var3, var9, var7);
                     }
                  }
               }
            }

            var1.resolveSelfReferences((JavaType)var8);
            if (!((JavaType)var8).hasHandlers()) {
               this._typeCache.putIfAbsent(var5, var8);
            }

            return (JavaType)var8;
         }
      }
   }

   protected JavaType _resolveSuperClass(ClassStack var1, Class var2, TypeBindings var3) {
      Type var4 = ClassUtil.getGenericSuperclass(var2);
      return var4 == null ? null : this._fromAny(var1, var4, var3);
   }

   protected JavaType[] _resolveSuperInterfaces(ClassStack var1, Class var2, TypeBindings var3) {
      Type[] var4 = ClassUtil.getGenericInterfaces(var2);
      if (var4 != null && var4.length != 0) {
         int var5 = var4.length;
         JavaType[] var6 = new JavaType[var5];

         for(int var7 = 0; var7 < var5; ++var7) {
            Type var8 = var4[var7];
            var6[var7] = this._fromAny(var1, var8, var3);
         }

         return var6;
      } else {
         return NO_TYPES;
      }
   }

   protected JavaType _fromWellKnownClass(ClassStack var1, Class var2, TypeBindings var3, JavaType var4, JavaType[] var5) {
      if (var3 == null) {
         var3 = EMPTY_BINDINGS;
      }

      if (var2 == Map.class) {
         return this._mapType(var2, var3, var4, var5);
      } else if (var2 == Collection.class) {
         return this._collectionType(var2, var3, var4, var5);
      } else {
         return var2 == AtomicReference.class ? this._referenceType(var2, var3, var4, var5) : null;
      }
   }

   protected JavaType _fromWellKnownInterface(ClassStack var1, Class var2, TypeBindings var3, JavaType var4, JavaType[] var5) {
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         JavaType var8 = var5[var7].refine(var2, var3, var4, var5);
         if (var8 != null) {
            return var8;
         }
      }

      return null;
   }

   protected JavaType _fromParamType(ClassStack var1, ParameterizedType var2, TypeBindings var3) {
      Class var4 = (Class)var2.getRawType();
      if (var4 == CLS_ENUM) {
         return CORE_TYPE_ENUM;
      } else if (var4 == CLS_COMPARABLE) {
         return CORE_TYPE_COMPARABLE;
      } else if (var4 == CLS_CLASS) {
         return CORE_TYPE_CLASS;
      } else {
         Type[] var5 = var2.getActualTypeArguments();
         int var6 = var5 == null ? 0 : var5.length;
         TypeBindings var7;
         if (var6 == 0) {
            var7 = EMPTY_BINDINGS;
         } else {
            JavaType[] var8 = new JavaType[var6];

            for(int var9 = 0; var9 < var6; ++var9) {
               var8[var9] = this._fromAny(var1, var5[var9], var3);
            }

            var7 = TypeBindings.create(var4, var8);
         }

         return this._fromClass(var1, var4, var7);
      }
   }

   protected JavaType _fromArrayType(ClassStack var1, GenericArrayType var2, TypeBindings var3) {
      JavaType var4 = this._fromAny(var1, var2.getGenericComponentType(), var3);
      return ArrayType.construct(var4, var3);
   }

   protected JavaType _fromVariable(ClassStack var1, TypeVariable var2, TypeBindings var3) {
      String var4 = var2.getName();
      if (var3 == null) {
         throw new Error("No Bindings!");
      } else {
         JavaType var5 = var3.findBoundType(var4);
         if (var5 != null) {
            return var5;
         } else if (var3.hasUnbound(var4)) {
            return CORE_TYPE_OBJECT;
         } else {
            var3 = var3.withUnboundVariable(var4);
            Type[] var6 = var2.getBounds();
            return this._fromAny(var1, var6[0], var3);
         }
      }
   }

   protected JavaType _fromWildcard(ClassStack var1, WildcardType var2, TypeBindings var3) {
      return this._fromAny(var1, var2.getUpperBounds()[0], var3);
   }

   static {
      CLS_BOOL = Boolean.TYPE;
      CLS_INT = Integer.TYPE;
      CLS_LONG = Long.TYPE;
      CORE_TYPE_BOOL = new SimpleType(CLS_BOOL);
      CORE_TYPE_INT = new SimpleType(CLS_INT);
      CORE_TYPE_LONG = new SimpleType(CLS_LONG);
      CORE_TYPE_STRING = new SimpleType(CLS_STRING);
      CORE_TYPE_OBJECT = new SimpleType(CLS_OBJECT);
      CORE_TYPE_COMPARABLE = new SimpleType(CLS_COMPARABLE);
      CORE_TYPE_ENUM = new SimpleType(CLS_ENUM);
      CORE_TYPE_CLASS = new SimpleType(CLS_CLASS);
   }
}
