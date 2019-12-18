package org.jline.terminal.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import org.jline.terminal.Attributes;
import org.jline.terminal.spi.Pty;

public abstract class AbstractPty implements Pty {
   private Attributes current;

   public AbstractPty() {
      super();
   }

   public void setAttr(Attributes var1) throws IOException {
      this.current = new Attributes(var1);
      this.doSetAttr(var1);
   }

   public InputStream getSlaveInput() throws IOException {
      InputStream var1 = this.doGetSlaveInput();
      return (InputStream)(Boolean.parseBoolean(System.getProperty("org.jline.terminal.pty.nonBlockingReads", "true")) ? new AbstractPty$PtyInputStream(this, var1) : var1);
   }

   protected abstract void doSetAttr(Attributes var1) throws IOException;

   protected abstract InputStream doGetSlaveInput() throws IOException;

   protected void checkInterrupted() throws InterruptedIOException {
      if (Thread.interrupted()) {
         throw new InterruptedIOException();
      }
   }

   static Attributes access$000(AbstractPty var0) {
      return var0.current;
   }
}
