package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.*;
import java.lang.reflect.*;

public abstract class TypeModifier
{
    public TypeModifier() {
        super();
    }
    
    public abstract JavaType modifyType(final JavaType p0, final Type p1, final TypeBindings p2, final TypeFactory p3);
}
