package com.fasterxml.jackson.core.async;

import java.nio.*;
import java.io.*;

public interface ByteBufferFeeder extends NonBlockingInputFeeder
{
    void feedInput(final ByteBuffer p0) throws IOException;
}
