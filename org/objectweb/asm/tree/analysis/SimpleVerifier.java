package org.objectweb.asm.tree.analysis;

import java.util.Iterator;
import java.util.List;
import org.objectweb.asm.Type;

public class SimpleVerifier extends BasicVerifier {
   private final Type currentClass;
   private final Type currentSuperClass;
   private final List currentClassInterfaces;
   private final boolean isInterface;
   private ClassLoader loader;

   public SimpleVerifier() {
      this((Type)null, (Type)null, false);
   }

   public SimpleVerifier(Type var1, Type var2, boolean var3) {
      this(var1, var2, (List)null, var3);
   }

   public SimpleVerifier(Type var1, Type var2, List var3, boolean var4) {
      this(458752, var1, var2, var3, var4);
      if (this.getClass() != SimpleVerifier.class) {
         throw new IllegalStateException();
      }
   }

   protected SimpleVerifier(int var1, Type var2, Type var3, List var4, boolean var5) {
      super(var1);
      this.loader = this.getClass().getClassLoader();
      this.currentClass = var2;
      this.currentSuperClass = var3;
      this.currentClassInterfaces = var4;
      this.isInterface = var5;
   }

   public void setClassLoader(ClassLoader var1) {
      this.loader = var1;
   }

   public BasicValue newValue(Type var1) {
      if (var1 == null) {
         return BasicValue.UNINITIALIZED_VALUE;
      } else {
         boolean var2 = var1.getSort() == 9;
         if (var2) {
            switch(var1.getElementType().getSort()) {
            case 1:
            case 2:
            case 3:
            case 4:
               return new BasicValue(var1);
            }
         }

         BasicValue var3 = super.newValue(var1);
         if (BasicValue.REFERENCE_VALUE.equals(var3)) {
            if (var2) {
               var3 = this.newValue(var1.getElementType());
               StringBuilder var4 = new StringBuilder();

               for(int var5 = 0; var5 < var1.getDimensions(); ++var5) {
                  var4.append('[');
               }

               var4.append(var3.getType().getDescriptor());
               var3 = new BasicValue(Type.getType(var4.toString()));
            } else {
               var3 = new BasicValue(var1);
            }
         }

         return var3;
      }
   }

   protected boolean isArrayValue(BasicValue var1) {
      Type var2 = var1.getType();
      return var2 != null && (var2.getSort() == 9 || var2.equals(NULL_TYPE));
   }

   protected BasicValue getElementValue(BasicValue var1) throws AnalyzerException {
      Type var2 = var1.getType();
      if (var2 != null) {
         if (var2.getSort() == 9) {
            return this.newValue(Type.getType(var2.getDescriptor().substring(1)));
         }

         if (var2.equals(NULL_TYPE)) {
            return var1;
         }
      }

      throw new AssertionError();
   }

   protected boolean isSubTypeOf(BasicValue var1, BasicValue var2) {
      Type var3 = var2.getType();
      Type var4 = var1.getType();
      switch(var3.getSort()) {
      case 5:
      case 6:
      case 7:
      case 8:
         return var4.equals(var3);
      case 9:
      case 10:
         if (var4.equals(NULL_TYPE)) {
            return true;
         } else if (var4.getSort() != 10 && var4.getSort() != 9) {
            return false;
         } else if (this.isAssignableFrom(var3, var4)) {
            return true;
         } else {
            if (this.getClass(var3).isInterface()) {
               return Object.class.isAssignableFrom(this.getClass(var4));
            }

            return false;
         }
      default:
         throw new AssertionError();
      }
   }

   public BasicValue merge(BasicValue var1, BasicValue var2) {
      if (var1.equals(var2)) {
         return var1;
      } else {
         Type var3 = var1.getType();
         Type var4 = var2.getType();
         if (var3 == null || var3.getSort() != 10 && var3.getSort() != 9 || var4 == null || var4.getSort() != 10 && var4.getSort() != 9) {
            return BasicValue.UNINITIALIZED_VALUE;
         } else if (var3.equals(NULL_TYPE)) {
            return var2;
         } else if (var4.equals(NULL_TYPE)) {
            return var1;
         } else if (this.isAssignableFrom(var3, var4)) {
            return var1;
         } else if (this.isAssignableFrom(var4, var3)) {
            return var2;
         } else {
            int var5 = 0;
            if (var3.getSort() == 9 && var4.getSort() == 9 && var3.getDimensions() == var4.getDimensions() && var3.getElementType().getSort() == 10 && var4.getElementType().getSort() == 10) {
               var5 = var3.getDimensions();
               var3 = var3.getElementType();
               var4 = var4.getElementType();
            }

            while(var3 != null && !this.isInterface(var3)) {
               var3 = this.getSuperClass(var3);
               if (this.isAssignableFrom(var3, var4)) {
                  return this.newArrayValue(var3, var5);
               }
            }

            return this.newArrayValue(Type.getObjectType("java/lang/Object"), var5);
         }
      }
   }

   private BasicValue newArrayValue(Type var1, int var2) {
      if (var2 == 0) {
         return this.newValue(var1);
      } else {
         StringBuilder var3 = new StringBuilder();

         for(int var4 = 0; var4 < var2; ++var4) {
            var3.append('[');
         }

         var3.append(var1.getDescriptor());
         return this.newValue(Type.getType(var3.toString()));
      }
   }

   protected boolean isInterface(Type var1) {
      return this.currentClass != null && this.currentClass.equals(var1) ? this.isInterface : this.getClass(var1).isInterface();
   }

   protected Type getSuperClass(Type var1) {
      if (this.currentClass != null && this.currentClass.equals(var1)) {
         return this.currentSuperClass;
      } else {
         Class var2 = this.getClass(var1).getSuperclass();
         return var2 == null ? null : Type.getType(var2);
      }
   }

   protected boolean isAssignableFrom(Type var1, Type var2) {
      if (var1.equals(var2)) {
         return true;
      } else if (this.currentClass != null && this.currentClass.equals(var1)) {
         if (this.getSuperClass(var2) == null) {
            return false;
         } else if (!this.isInterface) {
            return this.isAssignableFrom(var1, this.getSuperClass(var2));
         } else {
            return var2.getSort() == 10 || var2.getSort() == 9;
         }
      } else if (this.currentClass != null && this.currentClass.equals(var2)) {
         if (this.isAssignableFrom(var1, this.currentSuperClass)) {
            return true;
         } else {
            if (this.currentClassInterfaces != null) {
               Iterator var3 = this.currentClassInterfaces.iterator();

               while(var3.hasNext()) {
                  Type var4 = (Type)var3.next();
                  if (this.isAssignableFrom(var1, var4)) {
                     return true;
                  }
               }
            }

            return false;
         }
      } else {
         return this.getClass(var1).isAssignableFrom(this.getClass(var2));
      }
   }

   protected Class getClass(Type var1) {
      Class var10000;
      try {
         if (var1.getSort() == 9) {
            var10000 = Class.forName(var1.getDescriptor().replace('/', '.'), false, this.loader);
            return var10000;
         }

         var10000 = Class.forName(var1.getClassName(), false, this.loader);
      } catch (ClassNotFoundException var3) {
         throw new TypeNotPresentException(var3.toString(), var3);
      }

      return var10000;
   }

   public Value merge(Value var1, Value var2) {
      return this.merge((BasicValue)var1, (BasicValue)var2);
   }

   public Value newValue(Type var1) {
      return this.newValue(var1);
   }
}
