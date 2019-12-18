package com.fasterxml.jackson.databind.jsonschema;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;

@Deprecated
public class JsonSchema
{
    private final ObjectNode schema;
    
    @JsonCreator
    public JsonSchema(final ObjectNode schema) {
        super();
        this.schema = schema;
    }
    
    @JsonValue
    public ObjectNode getSchemaNode() {
        return this.schema;
    }
    
    @Override
    public String toString() {
        return this.schema.toString();
    }
    
    @Override
    public int hashCode() {
        return this.schema.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof JsonSchema)) {
            return false;
        }
        final JsonSchema jsonSchema = (JsonSchema)o;
        if (this.schema == null) {
            return jsonSchema.schema == null;
        }
        return this.schema.equals(jsonSchema.schema);
    }
    
    public static JsonNode getDefaultSchemaNode() {
        final ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("type", "any");
        return objectNode;
    }
}
