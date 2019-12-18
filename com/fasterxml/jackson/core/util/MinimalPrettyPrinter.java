package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.*;
import java.io.*;

public class MinimalPrettyPrinter implements PrettyPrinter, Serializable
{
    private static final long serialVersionUID = 1L;
    protected String _rootValueSeparator;
    protected Separators _separators;
    
    public MinimalPrettyPrinter() {
        this(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR.toString());
    }
    
    public MinimalPrettyPrinter(final String rootValueSeparator) {
        super();
        this._rootValueSeparator = rootValueSeparator;
        this._separators = MinimalPrettyPrinter.DEFAULT_SEPARATORS;
    }
    
    public void setRootValueSeparator(final String rootValueSeparator) {
        this._rootValueSeparator = rootValueSeparator;
    }
    
    public MinimalPrettyPrinter setSeparators(final Separators separators) {
        this._separators = separators;
        return this;
    }
    
    @Override
    public void writeRootValueSeparator(final JsonGenerator jsonGenerator) throws IOException {
        if (this._rootValueSeparator != null) {
            jsonGenerator.writeRaw(this._rootValueSeparator);
        }
    }
    
    @Override
    public void writeStartObject(final JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw('{');
    }
    
    @Override
    public void beforeObjectEntries(final JsonGenerator jsonGenerator) throws IOException {
    }
    
    @Override
    public void writeObjectFieldValueSeparator(final JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw(this._separators.getObjectFieldValueSeparator());
    }
    
    @Override
    public void writeObjectEntrySeparator(final JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw(this._separators.getObjectEntrySeparator());
    }
    
    @Override
    public void writeEndObject(final JsonGenerator jsonGenerator, final int n) throws IOException {
        jsonGenerator.writeRaw('}');
    }
    
    @Override
    public void writeStartArray(final JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw('[');
    }
    
    @Override
    public void beforeArrayValues(final JsonGenerator jsonGenerator) throws IOException {
    }
    
    @Override
    public void writeArrayValueSeparator(final JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw(this._separators.getArrayValueSeparator());
    }
    
    @Override
    public void writeEndArray(final JsonGenerator jsonGenerator, final int n) throws IOException {
        jsonGenerator.writeRaw(']');
    }
}
