package org.yaml.snakeyaml.introspector;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.yaml.snakeyaml.error.YAMLException;

public class PropertyUtils {
   private final Map propertiesCache = new HashMap();
   private final Map readableProperties = new HashMap();
   private BeanAccess beanAccess;
   private boolean allowReadOnlyProperties;
   private boolean skipMissingProperties;

   public PropertyUtils() {
      super();
      this.beanAccess = BeanAccess.DEFAULT;
      this.allowReadOnlyProperties = false;
      this.skipMissingProperties = false;
   }

   protected Map getPropertiesMap(Class var1, BeanAccess var2) throws IntrospectionException {
      // $FF: Couldn't be decompiled
   }

   public Set getProperties(Class var1) throws IntrospectionException {
      return this.getProperties(var1, this.beanAccess);
   }

   public Set getProperties(Class var1, BeanAccess var2) throws IntrospectionException {
      if (this.readableProperties.containsKey(var1)) {
         return (Set)this.readableProperties.get(var1);
      } else {
         Set var3 = this.createPropertySet(var1, var2);
         this.readableProperties.put(var1, var3);
         return var3;
      }
   }

   protected Set createPropertySet(Class var1, BeanAccess var2) throws IntrospectionException {
      TreeSet var3 = new TreeSet();
      Collection var4 = this.getPropertiesMap(var1, var2).values();
      Iterator var5 = var4.iterator();

      while(true) {
         Property var6;
         do {
            do {
               if (!var5.hasNext()) {
                  return var3;
               }

               var6 = (Property)var5.next();
            } while(!var6.isReadable());
         } while(!this.allowReadOnlyProperties && !var6.isWritable());

         var3.add(var6);
      }
   }

   public Property getProperty(Class var1, String var2) throws IntrospectionException {
      return this.getProperty(var1, var2, this.beanAccess);
   }

   public Property getProperty(Class var1, String var2, BeanAccess var3) throws IntrospectionException {
      Map var4 = this.getPropertiesMap(var1, var3);
      Object var5 = (Property)var4.get(var2);
      if (var5 == null && this.skipMissingProperties) {
         var5 = new MissingProperty(var2);
      }

      if (var5 != null && ((Property)var5).isWritable()) {
         return (Property)var5;
      } else {
         throw new YAMLException("Unable to find property '" + var2 + "' on class: " + var1.getName());
      }
   }

   public void setBeanAccess(BeanAccess var1) {
      if (this.beanAccess != var1) {
         this.beanAccess = var1;
         this.propertiesCache.clear();
         this.readableProperties.clear();
      }

   }

   public void setAllowReadOnlyProperties(boolean var1) {
      if (this.allowReadOnlyProperties != var1) {
         this.allowReadOnlyProperties = var1;
         this.readableProperties.clear();
      }

   }

   public void setSkipMissingProperties(boolean var1) {
      if (this.skipMissingProperties != var1) {
         this.skipMissingProperties = var1;
         this.readableProperties.clear();
      }

   }
}
