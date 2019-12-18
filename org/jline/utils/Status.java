package org.jline.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.impl.AbstractTerminal;

public class Status {
   protected final AbstractTerminal terminal;
   protected final boolean supported;
   protected List oldLines = Collections.emptyList();
   protected int rows;
   protected int columns;
   protected boolean force;

   public static Status getStatus(Terminal var0) {
      return getStatus(var0, true);
   }

   public static Status getStatus(Terminal var0, boolean var1) {
      return var0 instanceof AbstractTerminal ? ((AbstractTerminal)var0).getStatus(var1) : null;
   }

   public Status(AbstractTerminal var1) {
      super();
      this.terminal = (AbstractTerminal)Objects.requireNonNull(var1, "terminal can not be null");
      this.supported = var1.getStringCapability(InfoCmp$Capability.change_scroll_region) != null && var1.getStringCapability(InfoCmp$Capability.save_cursor) != null && var1.getStringCapability(InfoCmp$Capability.restore_cursor) != null && var1.getStringCapability(InfoCmp$Capability.cursor_address) != null;
      if (this.supported) {
         this.resize();
      }

   }

   public void resize() {
      Size var1 = this.terminal.getSize();
      this.rows = var1.getRows();
      this.columns = var1.getColumns();
      this.force = true;
   }

   public void reset() {
      this.force = true;
   }

   public void redraw() {
      this.update(this.oldLines);
   }

   public void update(List var1) {
      if (var1 == null) {
         var1 = Collections.emptyList();
      }

      if (this.supported && (!this.oldLines.equals(var1) || this.force)) {
         int var2 = var1.size() - this.oldLines.size();
         int var3;
         if (var2 > 0) {
            for(var3 = 0; var3 < var2; ++var3) {
               this.terminal.puts(InfoCmp$Capability.cursor_down);
            }

            for(var3 = 0; var3 < var2; ++var3) {
               this.terminal.puts(InfoCmp$Capability.cursor_up);
            }
         }

         this.terminal.puts(InfoCmp$Capability.save_cursor);
         this.terminal.puts(InfoCmp$Capability.cursor_address, this.rows - var1.size(), 0);
         this.terminal.puts(InfoCmp$Capability.clr_eos);

         for(var3 = 0; var3 < var1.size(); ++var3) {
            this.terminal.puts(InfoCmp$Capability.cursor_address, this.rows - var1.size() + var3, 0);
            ((AttributedString)var1.get(var3)).columnSubSequence(0, this.columns).print(this.terminal);
         }

         this.terminal.puts(InfoCmp$Capability.change_scroll_region, 0, this.rows - 1 - var1.size());
         this.terminal.puts(InfoCmp$Capability.restore_cursor);
         this.terminal.flush();
         this.oldLines = new ArrayList(var1);
         this.force = false;
      }
   }
}
