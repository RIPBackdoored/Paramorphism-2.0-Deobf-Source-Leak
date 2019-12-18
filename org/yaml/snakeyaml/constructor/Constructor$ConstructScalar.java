package org.yaml.snakeyaml.constructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

public class Constructor$ConstructScalar extends AbstractConstruct {
   final Constructor this$0;

   protected Constructor$ConstructScalar(Constructor var1) {
      super();
      this.this$0 = var1;
   }

   public Object construct(Node var1) {
      ScalarNode var2 = (ScalarNode)var1;
      Class var3 = var2.getType();
      Object var4;
      if (!var3.isPrimitive() && var3 != String.class && !Number.class.isAssignableFrom(var3) && var3 != Boolean.class && !Date.class.isAssignableFrom(var3) && var3 != Character.class && var3 != BigInteger.class && var3 != BigDecimal.class && !Enum.class.isAssignableFrom(var3) && !Tag.BINARY.equals(var2.getTag()) && !Calendar.class.isAssignableFrom(var3) && var3 != UUID.class) {
         java.lang.reflect.Constructor[] var5 = var3.getDeclaredConstructors();
         int var6 = 0;
         java.lang.reflect.Constructor var7 = null;
         java.lang.reflect.Constructor[] var8 = var5;
         int var9 = var5.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            java.lang.reflect.Constructor var11 = var8[var10];
            if (var11.getParameterTypes().length == 1) {
               ++var6;
               var7 = var11;
            }
         }

         if (var7 == null) {
            throw new YAMLException("No single argument constructor found for " + var3);
         }

         Object var14;
         if (var6 == 1) {
            var14 = this.constructStandardJavaInstance(var7.getParameterTypes()[0], var2);
         } else {
            var14 = this.this$0.constructScalar(var2);

            try {
               var7 = var3.getDeclaredConstructor(String.class);
            } catch (Exception var13) {
               throw new YAMLException("Can't construct a java object for scalar " + var2.getTag() + "; No String constructor found. Exception=" + var13.getMessage(), var13);
            }
         }

         try {
            var7.setAccessible(true);
            var4 = var7.newInstance(var14);
         } catch (Exception var12) {
            throw new ConstructorException((String)null, (Mark)null, "Can't construct a java object for scalar " + var2.getTag() + "; exception=" + var12.getMessage(), var2.getStartMark(), var12);
         }
      } else {
         var4 = this.constructStandardJavaInstance(var3, var2);
      }

      return var4;
   }

   private Object constructStandardJavaInstance(Class var1, ScalarNode var2) {
      Object var3;
      Construct var4;
      if (var1 == String.class) {
         var4 = (Construct)this.this$0.yamlConstructors.get(Tag.STR);
         var3 = var4.construct(var2);
      } else if (var1 != Boolean.class && var1 != Boolean.TYPE) {
         if (var1 != Character.class && var1 != Character.TYPE) {
            if (Date.class.isAssignableFrom(var1)) {
               var4 = (Construct)this.this$0.yamlConstructors.get(Tag.TIMESTAMP);
               Date var10 = (Date)var4.construct(var2);
               if (var1 == Date.class) {
                  var3 = var10;
               } else {
                  try {
                     java.lang.reflect.Constructor var6 = var1.getConstructor(Long.TYPE);
                     var3 = var6.newInstance(var10.getTime());
                  } catch (RuntimeException var8) {
                     throw var8;
                  } catch (Exception var9) {
                     throw new YAMLException("Cannot construct: '" + var1 + "'");
                  }
               }
            } else if (var1 != Float.class && var1 != Double.class && var1 != Float.TYPE && var1 != Double.TYPE && var1 != BigDecimal.class) {
               if (var1 != Byte.class && var1 != Short.class && var1 != Integer.class && var1 != Long.class && var1 != BigInteger.class && var1 != Byte.TYPE && var1 != Short.TYPE && var1 != Integer.TYPE && var1 != Long.TYPE) {
                  if (Enum.class.isAssignableFrom(var1)) {
                     String var11 = var2.getValue();

                     try {
                        var3 = Enum.valueOf(var1, var11);
                     } catch (Exception var7) {
                        throw new YAMLException("Unable to find enum value '" + var11 + "' for enum class: " + var1.getName());
                     }
                  } else if (Calendar.class.isAssignableFrom(var1)) {
                     SafeConstructor$ConstructYamlTimestamp var12 = new SafeConstructor$ConstructYamlTimestamp();
                     var12.construct(var2);
                     var3 = var12.getCalendar();
                  } else if (Number.class.isAssignableFrom(var1)) {
                     SafeConstructor$ConstructYamlNumber var13 = new SafeConstructor$ConstructYamlNumber(this.this$0);
                     var3 = var13.construct(var2);
                  } else if (UUID.class == var1) {
                     var3 = UUID.fromString(var2.getValue());
                  } else {
                     if (!this.this$0.yamlConstructors.containsKey(var2.getTag())) {
                        throw new YAMLException("Unsupported class: " + var1);
                     }

                     var3 = ((Construct)this.this$0.yamlConstructors.get(var2.getTag())).construct(var2);
                  }
               } else {
                  var4 = (Construct)this.this$0.yamlConstructors.get(Tag.INT);
                  var3 = var4.construct(var2);
                  if (var1 != Byte.class && var1 != Byte.TYPE) {
                     if (var1 != Short.class && var1 != Short.TYPE) {
                        if (var1 != Integer.class && var1 != Integer.TYPE) {
                           if (var1 != Long.class && var1 != Long.TYPE) {
                              var3 = new BigInteger(var3.toString());
                           } else {
                              var3 = Long.valueOf(var3.toString());
                           }
                        } else {
                           var3 = Integer.parseInt(var3.toString());
                        }
                     } else {
                        var3 = Short.valueOf(var3.toString());
                     }
                  } else {
                     var3 = Byte.valueOf(var3.toString());
                  }
               }
            } else if (var1 == BigDecimal.class) {
               var3 = new BigDecimal(var2.getValue());
            } else {
               var4 = (Construct)this.this$0.yamlConstructors.get(Tag.FLOAT);
               var3 = var4.construct(var2);
               if (var1 == Float.class || var1 == Float.TYPE) {
                  var3 = new Float((Double)var3);
               }
            }
         } else {
            var4 = (Construct)this.this$0.yamlConstructors.get(Tag.STR);
            String var5 = (String)var4.construct(var2);
            if (var5.length() == 0) {
               var3 = null;
            } else {
               if (var5.length() != 1) {
                  throw new YAMLException("Invalid node Character: '" + var5 + "'; length: " + var5.length());
               }

               var3 = var5.charAt(0);
            }
         }
      } else {
         var4 = (Construct)this.this$0.yamlConstructors.get(Tag.BOOL);
         var3 = var4.construct(var2);
      }

      return var3;
   }
}
