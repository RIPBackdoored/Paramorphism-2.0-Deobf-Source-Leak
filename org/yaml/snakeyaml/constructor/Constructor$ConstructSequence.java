package org.yaml.snakeyaml.constructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class Constructor$ConstructSequence implements Construct {
   final Constructor this$0;

   protected Constructor$ConstructSequence(Constructor var1) {
      super();
      this.this$0 = var1;
   }

   public Object construct(Node var1) {
      SequenceNode var2 = (SequenceNode)var1;
      if (Set.class.isAssignableFrom(var1.getType())) {
         if (var1.isTwoStepsConstruction()) {
            throw new YAMLException("Set cannot be recursive.");
         } else {
            return this.this$0.constructSet(var2);
         }
      } else if (Collection.class.isAssignableFrom(var1.getType())) {
         return var1.isTwoStepsConstruction() ? this.this$0.createDefaultList(var2.getValue().size()) : this.this$0.constructSequence(var2);
      } else if (var1.getType().isArray()) {
         return var1.isTwoStepsConstruction() ? this.this$0.createArray(var1.getType(), var2.getValue().size()) : this.this$0.constructArray(var2);
      } else {
         ArrayList var3 = new ArrayList(var2.getValue().size());
         java.lang.reflect.Constructor[] var4 = var1.getType().getDeclaredConstructors();
         int var5 = var4.length;

         int var6;
         for(var6 = 0; var6 < var5; ++var6) {
            java.lang.reflect.Constructor var7 = var4[var6];
            if (var2.getValue().size() == var7.getParameterTypes().length) {
               var3.add(var7);
            }
         }

         if (!var3.isEmpty()) {
            Object var10000;
            Iterator var18;
            if (var3.size() == 1) {
               Object[] var15 = new Object[var2.getValue().size()];
               java.lang.reflect.Constructor var17 = (java.lang.reflect.Constructor)var3.get(0);
               var6 = 0;

               Node var20;
               for(var18 = var2.getValue().iterator(); var18.hasNext(); var15[var6++] = this.this$0.constructObject(var20)) {
                  var20 = (Node)var18.next();
                  Class var21 = var17.getParameterTypes()[var6];
                  var20.setType(var21);
               }

               try {
                  var17.setAccessible(true);
                  var10000 = var17.newInstance(var15);
               } catch (Exception var12) {
                  throw new YAMLException(var12);
               }

               return var10000;
            }

            List var14 = this.this$0.constructSequence(var2);
            Class[] var16 = new Class[var14.size()];
            var6 = 0;

            for(var18 = var14.iterator(); var18.hasNext(); ++var6) {
               Object var8 = var18.next();
               var16[var6] = var8.getClass();
            }

            var18 = var3.iterator();

            while(var18.hasNext()) {
               java.lang.reflect.Constructor var19 = (java.lang.reflect.Constructor)var18.next();
               Class[] var9 = var19.getParameterTypes();
               boolean var10 = true;

               for(int var11 = 0; var11 < var9.length; ++var11) {
                  if (!this.wrapIfPrimitive(var9[var11]).isAssignableFrom(var16[var11])) {
                     var10 = false;
                     break;
                  }
               }

               if (var10) {
                  try {
                     var19.setAccessible(true);
                     var10000 = var19.newInstance(var14.toArray());
                  } catch (Exception var13) {
                     throw new YAMLException(var13);
                  }

                  return var10000;
               }
            }
         }

         throw new YAMLException("No suitable constructor with " + String.valueOf(var2.getValue().size()) + " arguments found for " + var1.getType());
      }
   }

   private final Class wrapIfPrimitive(Class var1) {
      if (!var1.isPrimitive()) {
         return var1;
      } else if (var1 == Integer.TYPE) {
         return Integer.class;
      } else if (var1 == Float.TYPE) {
         return Float.class;
      } else if (var1 == Double.TYPE) {
         return Double.class;
      } else if (var1 == Boolean.TYPE) {
         return Boolean.class;
      } else if (var1 == Long.TYPE) {
         return Long.class;
      } else if (var1 == Character.TYPE) {
         return Character.class;
      } else if (var1 == Short.TYPE) {
         return Short.class;
      } else if (var1 == Byte.TYPE) {
         return Byte.class;
      } else {
         throw new YAMLException("Unexpected primitive " + var1);
      }
   }

   public void construct2ndStep(Node var1, Object var2) {
      SequenceNode var3 = (SequenceNode)var1;
      if (List.class.isAssignableFrom(var1.getType())) {
         List var4 = (List)var2;
         this.this$0.constructSequenceStep2(var3, var4);
      } else {
         if (!var1.getType().isArray()) {
            throw new YAMLException("Immutable objects cannot be recursive.");
         }

         this.this$0.constructArrayStep2(var3, var2);
      }

   }
}
