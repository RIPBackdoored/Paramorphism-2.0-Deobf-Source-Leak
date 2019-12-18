package org.yaml.snakeyaml.introspector;

public abstract class Property implements Comparable {
   private final String name;
   private final Class type;

   public Property(String var1, Class var2) {
      super();
      this.name = var1;
      this.type = var2;
   }

   public Class getType() {
      return this.type;
   }

   public abstract Class[] getActualTypeArguments();

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.getName() + " of " + this.getType();
   }

   public int compareTo(Property var1) {
      return this.name.compareTo(var1.name);
   }

   public boolean isWritable() {
      return true;
   }

   public boolean isReadable() {
      return true;
   }

   public abstract void set(Object var1, Object var2) throws Exception;

   public abstract Object get(Object var1);

   public int hashCode() {
      return this.name.hashCode() + this.type.hashCode();
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof Property)) {
         return false;
      } else {
         Property var2 = (Property)var1;
         return this.name.equals(var2.getName()) && this.type.equals(var2.getType());
      }
   }

   public int compareTo(Object var1) {
      return this.compareTo((Property)var1);
   }
}
