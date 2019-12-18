package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Iterator;

public abstract class ObjectCodec extends TreeCodec implements Versioned {
   protected ObjectCodec() {
      super();
   }

   public abstract Version version();

   public abstract Object readValue(JsonParser var1, Class var2) throws IOException;

   public abstract Object readValue(JsonParser var1, TypeReference var2) throws IOException;

   public abstract Object readValue(JsonParser var1, ResolvedType var2) throws IOException;

   public abstract Iterator readValues(JsonParser var1, Class var2) throws IOException;

   public abstract Iterator readValues(JsonParser var1, TypeReference var2) throws IOException;

   public abstract Iterator readValues(JsonParser var1, ResolvedType var2) throws IOException;

   public abstract void writeValue(JsonGenerator var1, Object var2) throws IOException;

   public abstract TreeNode readTree(JsonParser var1) throws IOException;

   public abstract void writeTree(JsonGenerator var1, TreeNode var2) throws IOException;

   public abstract TreeNode createObjectNode();

   public abstract TreeNode createArrayNode();

   public abstract JsonParser treeAsTokens(TreeNode var1);

   public abstract Object treeToValue(TreeNode var1, Class var2) throws JsonProcessingException;

   /** @deprecated */
   @Deprecated
   public JsonFactory getJsonFactory() {
      return this.getFactory();
   }

   public JsonFactory getFactory() {
      return this.getJsonFactory();
   }
}
