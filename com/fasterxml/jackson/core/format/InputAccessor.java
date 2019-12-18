package com.fasterxml.jackson.core.format;

import java.io.IOException;

public interface InputAccessor {
   boolean hasMoreBytes() throws IOException;

   byte nextByte() throws IOException;

   void reset();
}
