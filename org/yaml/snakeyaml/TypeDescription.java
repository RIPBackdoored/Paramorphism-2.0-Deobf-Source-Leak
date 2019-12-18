package org.yaml.snakeyaml;

import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.nodes.Tag;

public final class TypeDescription {
   private final Class type;
   private Tag tag;
   private Map listProperties;
   private Map keyProperties;
   private Map valueProperties;

   public TypeDescription(Class var1, Tag var2) {
      super();
      this.type = var1;
      this.tag = var2;
      this.listProperties = new HashMap();
      this.keyProperties = new HashMap();
      this.valueProperties = new HashMap();
   }

   public TypeDescription(Class var1, String var2) {
      this(var1, new Tag(var2));
   }

   public TypeDescription(Class var1) {
      this(var1, (Tag)null);
   }

   public Tag getTag() {
      return this.tag;
   }

   public void setTag(Tag var1) {
      this.tag = var1;
   }

   public void setTag(String var1) {
      this.setTag(new Tag(var1));
   }

   public Class getType() {
      return this.type;
   }

   public void putListPropertyType(String var1, Class var2) {
      this.listProperties.put(var1, var2);
   }

   public Class getListPropertyType(String var1) {
      return (Class)this.listProperties.get(var1);
   }

   public void putMapPropertyType(String var1, Class var2, Class var3) {
      this.keyProperties.put(var1, var2);
      this.valueProperties.put(var1, var3);
   }

   public Class getMapKeyType(String var1) {
      return (Class)this.keyProperties.get(var1);
   }

   public Class getMapValueType(String var1) {
      return (Class)this.valueProperties.get(var1);
   }

   public String toString() {
      return "TypeDescription for " + this.getType() + " (tag='" + this.getTag() + "')";
   }
}
