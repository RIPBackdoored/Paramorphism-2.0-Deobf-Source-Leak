package org.jline.utils;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

public class NonBlocking {
   public NonBlocking() {
      super();
   }

   public static NonBlockingPumpReader nonBlockingPumpReader() {
      return new NonBlockingPumpReader();
   }

   public static NonBlockingPumpReader nonBlockingPumpReader(int var0) {
      return new NonBlockingPumpReader(var0);
   }

   public static NonBlockingPumpInputStream nonBlockingPumpInputStream() {
      return new NonBlockingPumpInputStream();
   }

   public static NonBlockingPumpInputStream nonBlockingPumpInputStream(int var0) {
      return new NonBlockingPumpInputStream(var0);
   }

   public static NonBlockingInputStream nonBlockingStream(NonBlockingReader var0, Charset var1) {
      return new NonBlocking$NonBlockingReaderInputStream(var0, var1, (NonBlocking$1)null);
   }

   public static NonBlockingInputStream nonBlocking(String var0, InputStream var1) {
      return (NonBlockingInputStream)(var1 instanceof NonBlockingInputStream ? (NonBlockingInputStream)var1 : new NonBlockingInputStreamImpl(var0, var1));
   }

   public static NonBlockingReader nonBlocking(String var0, Reader var1) {
      return (NonBlockingReader)(var1 instanceof NonBlockingReader ? (NonBlockingReader)var1 : new NonBlockingReaderImpl(var0, var1));
   }

   public static NonBlockingReader nonBlocking(String var0, InputStream var1, Charset var2) {
      return new NonBlocking$NonBlockingInputStreamReader(nonBlocking(var0, var1), var2);
   }
}
