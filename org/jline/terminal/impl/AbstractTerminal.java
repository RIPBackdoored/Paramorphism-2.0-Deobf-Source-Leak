package org.jline.terminal.impl;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.charset.Charset;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import org.jline.terminal.Attributes;
import org.jline.terminal.Attributes$ControlChar;
import org.jline.terminal.Attributes$InputFlag;
import org.jline.terminal.Attributes$LocalFlag;
import org.jline.terminal.Cursor;
import org.jline.terminal.MouseEvent;
import org.jline.terminal.MouseEvent$Button;
import org.jline.terminal.MouseEvent$Modifier;
import org.jline.terminal.MouseEvent$Type;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$MouseTracking;
import org.jline.terminal.Terminal$Signal;
import org.jline.terminal.Terminal$SignalHandler;
import org.jline.utils.Curses;
import org.jline.utils.InfoCmp;
import org.jline.utils.InfoCmp$Capability;
import org.jline.utils.Log;
import org.jline.utils.Status;

public abstract class AbstractTerminal implements Terminal {
   protected final String name;
   protected final String type;
   protected final Charset encoding;
   protected final Map handlers;
   protected final Set bools;
   protected final Map ints;
   protected final Map strings;
   protected Status status;
   private MouseEvent lastMouseEvent;

   public AbstractTerminal(String var1, String var2) throws IOException {
      this(var1, var2, (Charset)null, Terminal$SignalHandler.SIG_DFL);
   }

   public AbstractTerminal(String var1, String var2, Charset var3, Terminal$SignalHandler var4) throws IOException {
      super();
      this.handlers = new HashMap();
      this.bools = new HashSet();
      this.ints = new HashMap();
      this.strings = new HashMap();
      this.lastMouseEvent = new MouseEvent(MouseEvent$Type.Moved, MouseEvent$Button.NoButton, EnumSet.noneOf(MouseEvent$Modifier.class), 0, 0);
      this.name = var1;
      this.type = var2;
      this.encoding = var3 != null ? var3 : Charset.defaultCharset();
      Terminal$Signal[] var5 = Terminal$Signal.values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Terminal$Signal var8 = var5[var7];
         this.handlers.put(var8, var4);
      }

   }

   public Status getStatus() {
      return this.getStatus(true);
   }

   public Status getStatus(boolean var1) {
      if (this.status == null && var1) {
         this.status = new Status(this);
      }

      return this.status;
   }

   public Terminal$SignalHandler handle(Terminal$Signal var1, Terminal$SignalHandler var2) {
      Objects.requireNonNull(var1);
      Objects.requireNonNull(var2);
      return (Terminal$SignalHandler)this.handlers.put(var1, var2);
   }

   public void raise(Terminal$Signal var1) {
      Objects.requireNonNull(var1);
      Terminal$SignalHandler var2 = (Terminal$SignalHandler)this.handlers.get(var1);
      if (var2 != Terminal$SignalHandler.SIG_DFL && var2 != Terminal$SignalHandler.SIG_IGN) {
         var2.handle(var1);
      }

      if (this.status != null && var1 == Terminal$Signal.WINCH) {
         this.status.resize();
      }

   }

   public void close() throws IOException {
      if (this.status != null) {
         this.status.update((List)null);
         this.flush();
      }

   }

   protected void echoSignal(Terminal$Signal var1) {
      // $FF: Couldn't be decompiled
   }

   public Attributes enterRawMode() {
      Attributes var1 = this.getAttributes();
      Attributes var2 = new Attributes(var1);
      var2.setLocalFlags(EnumSet.of(Attributes$LocalFlag.ICANON, Attributes$LocalFlag.ECHO, Attributes$LocalFlag.IEXTEN), false);
      var2.setInputFlags(EnumSet.of(Attributes$InputFlag.IXON, Attributes$InputFlag.ICRNL, Attributes$InputFlag.INLCR), false);
      var2.setControlChar(Attributes$ControlChar.VMIN, 0);
      var2.setControlChar(Attributes$ControlChar.VTIME, 1);
      this.setAttributes(var2);
      return var1;
   }

   public boolean echo() {
      return this.getAttributes().getLocalFlag(Attributes$LocalFlag.ECHO);
   }

   public boolean echo(boolean var1) {
      Attributes var2 = this.getAttributes();
      boolean var3 = var2.getLocalFlag(Attributes$LocalFlag.ECHO);
      if (var3 != var1) {
         var2.setLocalFlag(Attributes$LocalFlag.ECHO, var1);
         this.setAttributes(var2);
      }

      return var3;
   }

   public String getName() {
      return this.name;
   }

   public String getType() {
      return this.type;
   }

   public String getKind() {
      return this.getClass().getSimpleName();
   }

   public Charset encoding() {
      return this.encoding;
   }

   public void flush() {
      this.writer().flush();
   }

   public boolean puts(InfoCmp$Capability var1, Object... var2) {
      String var3 = this.getStringCapability(var1);
      if (var3 == null) {
         return false;
      } else {
         Curses.tputs(this.writer(), var3, var2);
         return true;
      }
   }

   public boolean getBooleanCapability(InfoCmp$Capability var1) {
      return this.bools.contains(var1);
   }

   public Integer getNumericCapability(InfoCmp$Capability var1) {
      return (Integer)this.ints.get(var1);
   }

   public String getStringCapability(InfoCmp$Capability var1) {
      return (String)this.strings.get(var1);
   }

   protected void parseInfoCmp() {
      String var1 = null;
      if (this.type != null) {
         try {
            var1 = InfoCmp.getInfoCmp(this.type);
         } catch (Exception var3) {
            Log.warn("Unable to retrieve infocmp for type " + this.type, var3);
         }
      }

      if (var1 == null) {
         var1 = InfoCmp.getLoadedInfoCmp("ansi");
      }

      InfoCmp.parseInfoCmp(var1, this.bools, this.ints, this.strings);
   }

   public Cursor getCursorPosition(IntConsumer var1) {
      return null;
   }

   public boolean hasMouseSupport() {
      return MouseSupport.hasMouseSupport(this);
   }

   public boolean trackMouse(Terminal$MouseTracking var1) {
      return MouseSupport.trackMouse(this, var1);
   }

   public MouseEvent readMouseEvent() {
      return this.lastMouseEvent = MouseSupport.readMouse((Terminal)this, this.lastMouseEvent);
   }

   public MouseEvent readMouseEvent(IntSupplier var1) {
      return this.lastMouseEvent = MouseSupport.readMouse(var1, this.lastMouseEvent);
   }

   public boolean hasFocusSupport() {
      return this.type != null && this.type.startsWith("xterm");
   }

   public boolean trackFocus(boolean var1) {
      if (this.hasFocusSupport()) {
         this.writer().write(var1 ? "\u001b[?1004h" : "\u001b[?1004l");
         this.writer().flush();
         return true;
      } else {
         return false;
      }
   }

   protected void checkInterrupted() throws InterruptedIOException {
      if (Thread.interrupted()) {
         throw new InterruptedIOException();
      }
   }

   public boolean canPauseResume() {
      return false;
   }

   public void pause() {
   }

   public void pause(boolean var1) throws InterruptedException {
   }

   public void resume() {
   }

   public boolean paused() {
      return false;
   }
}
