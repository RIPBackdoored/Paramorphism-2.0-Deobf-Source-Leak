package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.ClassUtil$Ctor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class AnnotatedCreatorCollector extends CollectorBase {
   private final TypeResolutionContext _typeContext;
   private AnnotatedConstructor _defaultConstructor;

   AnnotatedCreatorCollector(AnnotationIntrospector var1, TypeResolutionContext var2) {
      super(var1);
      this._typeContext = var2;
   }

   public static AnnotatedClass$Creators collectCreators(AnnotationIntrospector var0, TypeResolutionContext var1, JavaType var2, Class var3) {
      return (new AnnotatedCreatorCollector(var0, var1)).collect(var2, var3);
   }

   AnnotatedClass$Creators collect(JavaType var1, Class var2) {
      List var3 = this._findPotentialConstructors(var1, var2);
      List var4 = this._findPotentialFactories(var1, var2);
      if (this._intr != null) {
         if (this._defaultConstructor != null && this._intr.hasIgnoreMarker(this._defaultConstructor)) {
            this._defaultConstructor = null;
         }

         int var5 = var3.size();

         while(true) {
            --var5;
            if (var5 < 0) {
               var5 = var4.size();

               while(true) {
                  --var5;
                  if (var5 < 0) {
                     return new AnnotatedClass$Creators(this._defaultConstructor, var3, var4);
                  }

                  if (this._intr.hasIgnoreMarker((AnnotatedMember)var4.get(var5))) {
                     var4.remove(var5);
                  }
               }
            }

            if (this._intr.hasIgnoreMarker((AnnotatedMember)var3.get(var5))) {
               var3.remove(var5);
            }
         }
      } else {
         return new AnnotatedClass$Creators(this._defaultConstructor, var3, var4);
      }
   }

   private List _findPotentialConstructors(JavaType var1, Class var2) {
      ClassUtil$Ctor var3 = null;
      ArrayList var4 = null;
      int var7;
      if (!var1.isEnumType()) {
         ClassUtil$Ctor[] var5 = ClassUtil.getConstructors(var1.getRawClass());
         ClassUtil$Ctor[] var6 = var5;
         var7 = var5.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            ClassUtil$Ctor var9 = var6[var8];
            if (isIncludableConstructor(var9.getConstructor())) {
               if (var9.getParamCount() == 0) {
                  var3 = var9;
               } else {
                  if (var4 == null) {
                     var4 = new ArrayList();
                  }

                  var4.add(var9);
               }
            }
         }
      }

      Object var14;
      int var15;
      if (var4 == null) {
         var14 = Collections.emptyList();
         if (var3 == null) {
            return (List)var14;
         }

         var15 = 0;
      } else {
         var15 = var4.size();
         var14 = new ArrayList(var15);

         for(var7 = 0; var7 < var15; ++var7) {
            ((List)var14).add((Object)null);
         }
      }

      if (var2 != null) {
         MemberKey[] var16 = null;
         ClassUtil$Ctor[] var17 = ClassUtil.getConstructors(var2);
         int var19 = var17.length;

         for(int var10 = 0; var10 < var19; ++var10) {
            ClassUtil$Ctor var11 = var17[var10];
            if (var11.getParamCount() == 0) {
               if (var3 != null) {
                  this._defaultConstructor = this.constructDefaultConstructor(var3, var11);
                  var3 = null;
               }
            } else if (var4 != null) {
               if (var16 == null) {
                  var16 = new MemberKey[var15];

                  for(int var12 = 0; var12 < var15; ++var12) {
                     var16[var12] = new MemberKey(((ClassUtil$Ctor)var4.get(var12)).getConstructor());
                  }
               }

               MemberKey var20 = new MemberKey(var11.getConstructor());

               for(int var13 = 0; var13 < var15; ++var13) {
                  if (var20.equals(var16[var13])) {
                     ((List)var14).set(var13, this.constructNonDefaultConstructor((ClassUtil$Ctor)var4.get(var13), var11));
                     break;
                  }
               }
            }
         }
      }

      if (var3 != null) {
         this._defaultConstructor = this.constructDefaultConstructor(var3, (ClassUtil$Ctor)null);
      }

      for(var7 = 0; var7 < var15; ++var7) {
         AnnotatedConstructor var18 = (AnnotatedConstructor)((List)var14).get(var7);
         if (var18 == null) {
            ((List)var14).set(var7, this.constructNonDefaultConstructor((ClassUtil$Ctor)var4.get(var7), (ClassUtil$Ctor)null));
         }
      }

      return (List)var14;
   }

   private List _findPotentialFactories(JavaType var1, Class var2) {
      ArrayList var3 = null;
      Method[] var4 = ClassUtil.getClassMethods(var1.getRawClass());
      int var5 = var4.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         Method var7 = var4[var6];
         if (Modifier.isStatic(var7.getModifiers())) {
            if (var3 == null) {
               var3 = new ArrayList();
            }

            var3.add(var7);
         }
      }

      if (var3 == null) {
         return Collections.emptyList();
      } else {
         int var13 = var3.size();
         ArrayList var14 = new ArrayList(var13);

         for(var6 = 0; var6 < var13; ++var6) {
            var14.add((Object)null);
         }

         if (var2 != null) {
            MemberKey[] var15 = null;
            Method[] var16 = ClassUtil.getDeclaredMethods(var2);
            int var8 = var16.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               Method var10 = var16[var9];
               if (Modifier.isStatic(var10.getModifiers())) {
                  if (var15 == null) {
                     var15 = new MemberKey[var13];

                     for(int var11 = 0; var11 < var13; ++var11) {
                        var15[var11] = new MemberKey((Method)var3.get(var11));
                     }
                  }

                  MemberKey var18 = new MemberKey(var10);

                  for(int var12 = 0; var12 < var13; ++var12) {
                     if (var18.equals(var15[var12])) {
                        var14.set(var12, this.constructFactoryCreator((Method)var3.get(var12), var10));
                        break;
                     }
                  }
               }
            }
         }

         for(var6 = 0; var6 < var13; ++var6) {
            AnnotatedMethod var17 = (AnnotatedMethod)var14.get(var6);
            if (var17 == null) {
               var14.set(var6, this.constructFactoryCreator((Method)var3.get(var6), (Method)null));
            }
         }

         return var14;
      }
   }

   protected AnnotatedConstructor constructDefaultConstructor(ClassUtil$Ctor var1, ClassUtil$Ctor var2) {
      return this._intr == null ? new AnnotatedConstructor(this._typeContext, var1.getConstructor(), _emptyAnnotationMap(), NO_ANNOTATION_MAPS) : new AnnotatedConstructor(this._typeContext, var1.getConstructor(), this.collectAnnotations(var1, var2), this.collectAnnotations(var1.getConstructor().getParameterAnnotations(), var2 == null ? (Annotation[][])null : var2.getConstructor().getParameterAnnotations()));
   }

   protected AnnotatedConstructor constructNonDefaultConstructor(ClassUtil$Ctor var1, ClassUtil$Ctor var2) {
      int var3 = var1.getParamCount();
      if (this._intr == null) {
         return new AnnotatedConstructor(this._typeContext, var1.getConstructor(), _emptyAnnotationMap(), _emptyAnnotationMaps(var3));
      } else if (var3 == 0) {
         return new AnnotatedConstructor(this._typeContext, var1.getConstructor(), this.collectAnnotations(var1, var2), NO_ANNOTATION_MAPS);
      } else {
         Annotation[][] var5 = var1.getParameterAnnotations();
         AnnotationMap[] var4;
         if (var3 != var5.length) {
            var4 = null;
            Class var6 = var1.getDeclaringClass();
            Annotation[][] var7;
            if (var6.isEnum() && var3 == var5.length + 2) {
               var7 = var5;
               var5 = new Annotation[var5.length + 2][];
               System.arraycopy(var7, 0, var5, 2, var7.length);
               var4 = this.collectAnnotations(var5, (Annotation[][])null);
            } else if (var6.isMemberClass() && var3 == var5.length + 1) {
               var7 = var5;
               var5 = new Annotation[var5.length + 1][];
               System.arraycopy(var7, 0, var5, 1, var7.length);
               var5[0] = NO_ANNOTATIONS;
               var4 = this.collectAnnotations(var5, (Annotation[][])null);
            }

            if (var4 == null) {
               throw new IllegalStateException(String.format("Internal error: constructor for %s has mismatch: %d parameters; %d sets of annotations", var1.getDeclaringClass().getName(), var3, var5.length));
            }
         } else {
            var4 = this.collectAnnotations(var5, var2 == null ? (Annotation[][])null : var2.getParameterAnnotations());
         }

         return new AnnotatedConstructor(this._typeContext, var1.getConstructor(), this.collectAnnotations(var1, var2), var4);
      }
   }

   protected AnnotatedMethod constructFactoryCreator(Method var1, Method var2) {
      int var3 = var1.getParameterTypes().length;
      if (this._intr == null) {
         return new AnnotatedMethod(this._typeContext, var1, _emptyAnnotationMap(), _emptyAnnotationMaps(var3));
      } else {
         return var3 == 0 ? new AnnotatedMethod(this._typeContext, var1, this.collectAnnotations((AnnotatedElement)var1, (AnnotatedElement)var2), NO_ANNOTATION_MAPS) : new AnnotatedMethod(this._typeContext, var1, this.collectAnnotations((AnnotatedElement)var1, (AnnotatedElement)var2), this.collectAnnotations(var1.getParameterAnnotations(), var2 == null ? (Annotation[][])null : var2.getParameterAnnotations()));
      }
   }

   private AnnotationMap[] collectAnnotations(Annotation[][] var1, Annotation[][] var2) {
      int var3 = var1.length;
      AnnotationMap[] var4 = new AnnotationMap[var3];

      for(int var5 = 0; var5 < var3; ++var5) {
         AnnotationCollector var6 = this.collectAnnotations((AnnotationCollector)AnnotationCollector.emptyCollector(), (Annotation[])var1[var5]);
         if (var2 != null) {
            var6 = this.collectAnnotations((AnnotationCollector)var6, (Annotation[])var2[var5]);
         }

         var4[var5] = var6.asAnnotationMap();
      }

      return var4;
   }

   private AnnotationMap collectAnnotations(ClassUtil$Ctor var1, ClassUtil$Ctor var2) {
      AnnotationCollector var3 = this.collectAnnotations(var1.getConstructor().getDeclaredAnnotations());
      if (var2 != null) {
         var3 = this.collectAnnotations((AnnotationCollector)var3, (Annotation[])var2.getConstructor().getDeclaredAnnotations());
      }

      return var3.asAnnotationMap();
   }

   private final AnnotationMap collectAnnotations(AnnotatedElement var1, AnnotatedElement var2) {
      AnnotationCollector var3 = this.collectAnnotations(var1.getDeclaredAnnotations());
      if (var2 != null) {
         var3 = this.collectAnnotations((AnnotationCollector)var3, (Annotation[])var2.getDeclaredAnnotations());
      }

      return var3.asAnnotationMap();
   }

   private static boolean isIncludableConstructor(Constructor var0) {
      return !var0.isSynthetic();
   }
}
