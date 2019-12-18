package com.fasterxml.jackson.databind;

import java.io.*;

public abstract class KeyDeserializer
{
    public KeyDeserializer() {
        super();
    }
    
    public abstract Object deserializeKey(final String p0, final DeserializationContext p1) throws IOException;
    
    public abstract static class None extends KeyDeserializer
    {
        public None() {
            super();
        }
    }
}
