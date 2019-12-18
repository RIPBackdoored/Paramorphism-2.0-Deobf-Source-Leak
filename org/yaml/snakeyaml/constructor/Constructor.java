package org.yaml.snakeyaml.constructor;

import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;

public class Constructor extends SafeConstructor {
   private final Map typeTags;
   protected final Map typeDefinitions;

   public Constructor() {
      this(Object.class);
   }

   public Constructor(Class var1) {
      this(new TypeDescription(checkRoot(var1)));
   }

   private static Class checkRoot(Class var0) {
      if (var0 == null) {
         throw new NullPointerException("Root class must be provided.");
      } else {
         return var0;
      }
   }

   public Constructor(TypeDescription var1) {
      super();
      if (var1 == null) {
         throw new NullPointerException("Root type must be provided.");
      } else {
         this.yamlConstructors.put((Object)null, new Constructor$ConstructYamlObject(this));
         if (!Object.class.equals(var1.getType())) {
            this.rootTag = new Tag(var1.getType());
         }

         this.typeTags = new HashMap();
         this.typeDefinitions = new HashMap();
         this.yamlClassConstructors.put(NodeId.scalar, new Constructor$ConstructScalar(this));
         this.yamlClassConstructors.put(NodeId.mapping, new Constructor$ConstructMapping(this));
         this.yamlClassConstructors.put(NodeId.sequence, new Constructor$ConstructSequence(this));
         this.addTypeDescription(var1);
      }
   }

   public Constructor(String var1) throws ClassNotFoundException {
      this(Class.forName(check(var1)));
   }

   private static final String check(String var0) {
      if (var0 == null) {
         throw new NullPointerException("Root type must be provided.");
      } else if (var0.trim().length() == 0) {
         throw new YAMLException("Root type must be provided.");
      } else {
         return var0;
      }
   }

   public TypeDescription addTypeDescription(TypeDescription var1) {
      if (var1 == null) {
         throw new NullPointerException("TypeDescription is required.");
      } else {
         Tag var2 = var1.getTag();
         this.typeTags.put(var2, var1.getType());
         return (TypeDescription)this.typeDefinitions.put(var1.getType(), var1);
      }
   }

   protected Class getClassForNode(Node var1) {
      Class var2 = (Class)this.typeTags.get(var1.getTag());
      if (var2 == null) {
         String var3 = var1.getTag().getClassName();

         Class var4;
         try {
            var4 = this.getClassForName(var3);
         } catch (ClassNotFoundException var6) {
            throw new YAMLException("Class not found: " + var3);
         }

         this.typeTags.put(var1.getTag(), var4);
         return var4;
      } else {
         return var2;
      }
   }

   protected Class getClassForName(String var1) throws ClassNotFoundException {
      Class var10000;
      try {
         var10000 = Class.forName(var1, true, Thread.currentThread().getContextClassLoader());
      } catch (ClassNotFoundException var3) {
         return Class.forName(var1);
      }

      return var10000;
   }
}
