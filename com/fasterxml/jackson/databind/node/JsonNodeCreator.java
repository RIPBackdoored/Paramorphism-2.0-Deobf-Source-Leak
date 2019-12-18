package com.fasterxml.jackson.databind.node;

import java.math.*;
import com.fasterxml.jackson.databind.util.*;

public interface JsonNodeCreator
{
    ValueNode booleanNode(final boolean p0);
    
    ValueNode nullNode();
    
    ValueNode numberNode(final byte p0);
    
    ValueNode numberNode(final Byte p0);
    
    ValueNode numberNode(final short p0);
    
    ValueNode numberNode(final Short p0);
    
    ValueNode numberNode(final int p0);
    
    ValueNode numberNode(final Integer p0);
    
    ValueNode numberNode(final long p0);
    
    ValueNode numberNode(final Long p0);
    
    ValueNode numberNode(final BigInteger p0);
    
    ValueNode numberNode(final float p0);
    
    ValueNode numberNode(final Float p0);
    
    ValueNode numberNode(final double p0);
    
    ValueNode numberNode(final Double p0);
    
    ValueNode numberNode(final BigDecimal p0);
    
    ValueNode textNode(final String p0);
    
    ValueNode binaryNode(final byte[] p0);
    
    ValueNode binaryNode(final byte[] p0, final int p1, final int p2);
    
    ValueNode pojoNode(final Object p0);
    
    ValueNode rawValueNode(final RawValue p0);
    
    ArrayNode arrayNode();
    
    ArrayNode arrayNode(final int p0);
    
    ObjectNode objectNode();
}
