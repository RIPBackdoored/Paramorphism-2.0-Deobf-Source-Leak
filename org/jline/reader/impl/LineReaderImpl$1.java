package org.jline.reader.impl;

import org.jline.reader.Widget;

class LineReaderImpl$1 implements Widget {
   final String val$name;
   final Widget val$widget;
   final LineReaderImpl this$0;

   LineReaderImpl$1(LineReaderImpl var1, String var2, Widget var3) {
      super();
      this.this$0 = var1;
      this.val$name = var2;
      this.val$widget = var3;
   }

   public String toString() {
      return this.val$name;
   }

   public boolean apply() {
      return this.val$widget.apply();
   }
}
