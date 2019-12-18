package org.jline.terminal.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import org.jline.terminal.Attributes;
import org.jline.terminal.Attributes$ControlChar;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal$SignalHandler;
import org.jline.utils.NonBlocking;
import org.jline.utils.NonBlockingInputStream;
import org.jline.utils.NonBlockingReader;

public class DumbTerminal extends AbstractTerminal {
   private final NonBlockingInputStream input;
   private final OutputStream output;
   private final NonBlockingReader reader;
   private final PrintWriter writer;
   private final Attributes attributes;
   private final Size size;

   public DumbTerminal(InputStream var1, OutputStream var2) throws IOException {
      this("dumb", "dumb", var1, var2, (Charset)null);
   }

   public DumbTerminal(String var1, String var2, InputStream var3, OutputStream var4, Charset var5) throws IOException {
      this(var1, var2, var3, var4, var5, Terminal$SignalHandler.SIG_DFL);
   }

   public DumbTerminal(String var1, String var2, InputStream var3, OutputStream var4, Charset var5, Terminal$SignalHandler var6) throws IOException {
      super(var1, var2, var5, var6);
      NonBlockingInputStream var7 = NonBlocking.nonBlocking(this.getName(), var3);
      this.input = new DumbTerminal$1(this, var7);
      this.output = var4;
      this.reader = NonBlocking.nonBlocking(this.getName(), this.input, this.encoding());
      this.writer = new PrintWriter(new OutputStreamWriter(this.output, this.encoding()));
      this.attributes = new Attributes();
      this.attributes.setControlChar(Attributes$ControlChar.VERASE, 127);
      this.attributes.setControlChar(Attributes$ControlChar.VWERASE, 23);
      this.attributes.setControlChar(Attributes$ControlChar.VKILL, 21);
      this.attributes.setControlChar(Attributes$ControlChar.VLNEXT, 22);
      this.size = new Size();
      this.parseInfoCmp();
   }

   public NonBlockingReader reader() {
      return this.reader;
   }

   public PrintWriter writer() {
      return this.writer;
   }

   public InputStream input() {
      return this.input;
   }

   public OutputStream output() {
      return this.output;
   }

   public Attributes getAttributes() {
      Attributes var1 = new Attributes();
      var1.copy(this.attributes);
      return var1;
   }

   public void setAttributes(Attributes var1) {
      this.attributes.copy(var1);
   }

   public Size getSize() {
      Size var1 = new Size();
      var1.copy(this.size);
      return var1;
   }

   public void setSize(Size var1) {
      this.size.copy(var1);
   }

   static Attributes access$000(DumbTerminal var0) {
      return var0.attributes;
   }
}
