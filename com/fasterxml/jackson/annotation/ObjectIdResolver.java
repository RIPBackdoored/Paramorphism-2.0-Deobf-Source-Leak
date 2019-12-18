package com.fasterxml.jackson.annotation;

public interface ObjectIdResolver {
   void bindItem(ObjectIdGenerator$IdKey var1, Object var2);

   Object resolveId(ObjectIdGenerator$IdKey var1);

   ObjectIdResolver newForDeserialization(Object var1);

   boolean canUseFor(ObjectIdResolver var1);
}
