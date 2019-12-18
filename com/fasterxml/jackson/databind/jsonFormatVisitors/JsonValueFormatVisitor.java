package com.fasterxml.jackson.databind.jsonFormatVisitors;

import java.util.Set;

public interface JsonValueFormatVisitor {
   void format(JsonValueFormat var1);

   void enumTypes(Set var1);
}
