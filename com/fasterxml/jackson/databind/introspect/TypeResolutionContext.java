package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import java.lang.reflect.Type;

public interface TypeResolutionContext {
   JavaType resolveType(Type var1);
}
