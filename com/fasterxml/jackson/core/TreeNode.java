package com.fasterxml.jackson.core;

import java.util.Iterator;

public interface TreeNode {
   JsonToken asToken();

   JsonParser$NumberType numberType();

   int size();

   boolean isValueNode();

   boolean isContainerNode();

   boolean isMissingNode();

   boolean isArray();

   boolean isObject();

   TreeNode get(String var1);

   TreeNode get(int var1);

   TreeNode path(String var1);

   TreeNode path(int var1);

   Iterator fieldNames();

   TreeNode at(JsonPointer var1);

   TreeNode at(String var1) throws IllegalArgumentException;

   JsonParser traverse();

   JsonParser traverse(ObjectCodec var1);
}
