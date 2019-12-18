package org.jline.utils;

public class AttributedStyle {
   public static final int BLACK = 0;
   public static final int RED = 1;
   public static final int GREEN = 2;
   public static final int YELLOW = 3;
   public static final int BLUE = 4;
   public static final int MAGENTA = 5;
   public static final int CYAN = 6;
   public static final int WHITE = 7;
   public static final int BRIGHT = 8;
   static final int F_BOLD = 1;
   static final int F_FAINT = 2;
   static final int F_ITALIC = 4;
   static final int F_UNDERLINE = 8;
   static final int F_BLINK = 16;
   static final int F_INVERSE = 32;
   static final int F_CONCEAL = 64;
   static final int F_CROSSED_OUT = 128;
   static final int F_FOREGROUND = 256;
   static final int F_BACKGROUND = 512;
   static final int F_HIDDEN = 1024;
   static final int MASK = 2047;
   static final int FG_COLOR_EXP = 16;
   static final int BG_COLOR_EXP = 24;
   static final int FG_COLOR = 16711680;
   static final int BG_COLOR = -16777216;
   public static final AttributedStyle DEFAULT = new AttributedStyle();
   public static final AttributedStyle BOLD;
   public static final AttributedStyle BOLD_OFF;
   public static final AttributedStyle INVERSE;
   public static final AttributedStyle INVERSE_OFF;
   public static final AttributedStyle HIDDEN;
   public static final AttributedStyle HIDDEN_OFF;
   final int style;
   final int mask;

   public AttributedStyle() {
      this(0, 0);
   }

   public AttributedStyle(AttributedStyle var1) {
      this(var1.style, var1.mask);
   }

   public AttributedStyle(int var1, int var2) {
      super();
      this.style = var1;
      this.mask = var2 & 2047 | ((var1 & 256) != 0 ? 16711680 : 0) | ((var1 & 512) != 0 ? -16777216 : 0);
   }

   public AttributedStyle bold() {
      return new AttributedStyle(this.style | 1, this.mask | 1);
   }

   public AttributedStyle boldOff() {
      return new AttributedStyle(this.style & -2, this.mask | 1);
   }

   public AttributedStyle boldDefault() {
      return new AttributedStyle(this.style & -2, this.mask & -2);
   }

   public AttributedStyle faint() {
      return new AttributedStyle(this.style | 2, this.mask | 2);
   }

   public AttributedStyle faintOff() {
      return new AttributedStyle(this.style & -3, this.mask | 2);
   }

   public AttributedStyle faintDefault() {
      return new AttributedStyle(this.style & -3, this.mask & -3);
   }

   public AttributedStyle italic() {
      return new AttributedStyle(this.style | 4, this.mask | 4);
   }

   public AttributedStyle italicOff() {
      return new AttributedStyle(this.style & -5, this.mask | 4);
   }

   public AttributedStyle italicDefault() {
      return new AttributedStyle(this.style & -5, this.mask & -5);
   }

   public AttributedStyle underline() {
      return new AttributedStyle(this.style | 8, this.mask | 8);
   }

   public AttributedStyle underlineOff() {
      return new AttributedStyle(this.style & -9, this.mask | 8);
   }

   public AttributedStyle underlineDefault() {
      return new AttributedStyle(this.style & -9, this.mask & -9);
   }

   public AttributedStyle blink() {
      return new AttributedStyle(this.style | 16, this.mask | 16);
   }

   public AttributedStyle blinkOff() {
      return new AttributedStyle(this.style & -17, this.mask | 16);
   }

   public AttributedStyle blinkDefault() {
      return new AttributedStyle(this.style & -17, this.mask & -17);
   }

   public AttributedStyle inverse() {
      return new AttributedStyle(this.style | 32, this.mask | 32);
   }

   public AttributedStyle inverseNeg() {
      int var1 = (this.style & 32) != 0 ? this.style & -33 : this.style | 32;
      return new AttributedStyle(var1, this.mask | 32);
   }

   public AttributedStyle inverseOff() {
      return new AttributedStyle(this.style & -33, this.mask | 32);
   }

   public AttributedStyle inverseDefault() {
      return new AttributedStyle(this.style & -33, this.mask & -33);
   }

   public AttributedStyle conceal() {
      return new AttributedStyle(this.style | 64, this.mask | 64);
   }

   public AttributedStyle concealOff() {
      return new AttributedStyle(this.style & -65, this.mask | 64);
   }

   public AttributedStyle concealDefault() {
      return new AttributedStyle(this.style & -65, this.mask & -65);
   }

   public AttributedStyle crossedOut() {
      return new AttributedStyle(this.style | 128, this.mask | 128);
   }

   public AttributedStyle crossedOutOff() {
      return new AttributedStyle(this.style & -129, this.mask | 128);
   }

   public AttributedStyle crossedOutDefault() {
      return new AttributedStyle(this.style & -129, this.mask & -129);
   }

   public AttributedStyle foreground(int var1) {
      return new AttributedStyle(this.style & -16711681 | 256 | var1 << 16 & 16711680, this.mask | 256);
   }

   public AttributedStyle foregroundOff() {
      return new AttributedStyle(this.style & -16711681 & -257, this.mask | 256);
   }

   public AttributedStyle foregroundDefault() {
      return new AttributedStyle(this.style & -16711681 & -257, this.mask & -16711937);
   }

   public AttributedStyle background(int var1) {
      return new AttributedStyle(this.style & 16777215 | 512 | var1 << 24 & -16777216, this.mask | 512);
   }

   public AttributedStyle backgroundOff() {
      return new AttributedStyle(this.style & 16777215 & -513, this.mask | 512);
   }

   public AttributedStyle backgroundDefault() {
      return new AttributedStyle(this.style & 16777215 & -513, this.mask & 16776703);
   }

   public AttributedStyle hidden() {
      return new AttributedStyle(this.style | 1024, this.mask | 1024);
   }

   public AttributedStyle hiddenOff() {
      return new AttributedStyle(this.style & -1025, this.mask | 1024);
   }

   public AttributedStyle hiddenDefault() {
      return new AttributedStyle(this.style & -1025, this.mask & -1025);
   }

   public int getStyle() {
      return this.style;
   }

   public int getMask() {
      return this.mask;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         AttributedStyle var2 = (AttributedStyle)var1;
         if (this.style != var2.style) {
            return false;
         } else {
            return this.mask == var2.mask;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.style;
      var1 = 31 * var1 + this.mask;
      return var1;
   }

   static {
      BOLD = DEFAULT.bold();
      BOLD_OFF = DEFAULT.boldOff();
      INVERSE = DEFAULT.inverse();
      INVERSE_OFF = DEFAULT.inverseOff();
      HIDDEN = DEFAULT.hidden();
      HIDDEN_OFF = DEFAULT.hiddenOff();
   }
}
