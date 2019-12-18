package com.fasterxml.jackson.core.async;

import java.io.IOException;

public interface ByteArrayFeeder extends NonBlockingInputFeeder {
   void feedInput(byte[] var1, int var2, int var3) throws IOException;
}
