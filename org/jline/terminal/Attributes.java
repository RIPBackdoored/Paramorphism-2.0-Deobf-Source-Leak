package org.jline.terminal;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Attributes {
   final EnumSet iflag = EnumSet.noneOf(Attributes$InputFlag.class);
   final EnumSet oflag = EnumSet.noneOf(Attributes$OutputFlag.class);
   final EnumSet cflag = EnumSet.noneOf(Attributes$ControlFlag.class);
   final EnumSet lflag = EnumSet.noneOf(Attributes$LocalFlag.class);
   final EnumMap cchars = new EnumMap(Attributes$ControlChar.class);

   public Attributes() {
      super();
   }

   public Attributes(Attributes var1) {
      super();
      this.copy(var1);
   }

   public EnumSet getInputFlags() {
      return this.iflag;
   }

   public void setInputFlags(EnumSet var1) {
      this.iflag.clear();
      this.iflag.addAll(var1);
   }

   public boolean getInputFlag(Attributes$InputFlag var1) {
      return this.iflag.contains(var1);
   }

   public void setInputFlags(EnumSet var1, boolean var2) {
      if (var2) {
         this.iflag.addAll(var1);
      } else {
         this.iflag.removeAll(var1);
      }

   }

   public void setInputFlag(Attributes$InputFlag var1, boolean var2) {
      if (var2) {
         this.iflag.add(var1);
      } else {
         this.iflag.remove(var1);
      }

   }

   public EnumSet getOutputFlags() {
      return this.oflag;
   }

   public void setOutputFlags(EnumSet var1) {
      this.oflag.clear();
      this.oflag.addAll(var1);
   }

   public boolean getOutputFlag(Attributes$OutputFlag var1) {
      return this.oflag.contains(var1);
   }

   public void setOutputFlags(EnumSet var1, boolean var2) {
      if (var2) {
         this.oflag.addAll(var1);
      } else {
         this.oflag.removeAll(var1);
      }

   }

   public void setOutputFlag(Attributes$OutputFlag var1, boolean var2) {
      if (var2) {
         this.oflag.add(var1);
      } else {
         this.oflag.remove(var1);
      }

   }

   public EnumSet getControlFlags() {
      return this.cflag;
   }

   public void setControlFlags(EnumSet var1) {
      this.cflag.clear();
      this.cflag.addAll(var1);
   }

   public boolean getControlFlag(Attributes$ControlFlag var1) {
      return this.cflag.contains(var1);
   }

   public void setControlFlags(EnumSet var1, boolean var2) {
      if (var2) {
         this.cflag.addAll(var1);
      } else {
         this.cflag.removeAll(var1);
      }

   }

   public void setControlFlag(Attributes$ControlFlag var1, boolean var2) {
      if (var2) {
         this.cflag.add(var1);
      } else {
         this.cflag.remove(var1);
      }

   }

   public EnumSet getLocalFlags() {
      return this.lflag;
   }

   public void setLocalFlags(EnumSet var1) {
      this.lflag.clear();
      this.lflag.addAll(var1);
   }

   public boolean getLocalFlag(Attributes$LocalFlag var1) {
      return this.lflag.contains(var1);
   }

   public void setLocalFlags(EnumSet var1, boolean var2) {
      if (var2) {
         this.lflag.addAll(var1);
      } else {
         this.lflag.removeAll(var1);
      }

   }

   public void setLocalFlag(Attributes$LocalFlag var1, boolean var2) {
      if (var2) {
         this.lflag.add(var1);
      } else {
         this.lflag.remove(var1);
      }

   }

   public EnumMap getControlChars() {
      return this.cchars;
   }

   public void setControlChars(EnumMap var1) {
      this.cchars.clear();
      this.cchars.putAll(var1);
   }

   public int getControlChar(Attributes$ControlChar var1) {
      Integer var2 = (Integer)this.cchars.get(var1);
      return var2 != null ? var2 : -1;
   }

   public void setControlChar(Attributes$ControlChar var1, int var2) {
      this.cchars.put(var1, var2);
   }

   public void copy(Attributes var1) {
      this.setControlFlags(var1.getControlFlags());
      this.setInputFlags(var1.getInputFlags());
      this.setLocalFlags(var1.getLocalFlags());
      this.setOutputFlags(var1.getOutputFlags());
      this.setControlChars(var1.getControlChars());
   }

   public String toString() {
      return "Attributes[lflags: " + this.append(this.lflag) + ", iflags: " + this.append(this.iflag) + ", oflags: " + this.append(this.oflag) + ", cflags: " + this.append(this.cflag) + ", cchars: " + this.append(EnumSet.allOf(Attributes$ControlChar.class), this::display) + "]";
   }

   private String display(Attributes$ControlChar var1) {
      int var3 = this.getControlChar(var1);
      String var2;
      if (var1 != Attributes$ControlChar.VMIN && var1 != Attributes$ControlChar.VTIME) {
         if (var3 < 0) {
            var2 = "<undef>";
         } else if (var3 < 32) {
            var2 = "^" + (char)(var3 + 65 - 1);
         } else if (var3 == 127) {
            var2 = "^?";
         } else if (var3 >= 128) {
            var2 = String.format("\\u%04x", var3);
         } else {
            var2 = String.valueOf((char)var3);
         }
      } else {
         var2 = Integer.toString(var3);
      }

      return var1.name().toLowerCase().substring(1) + "=" + var2;
   }

   private String append(EnumSet var1) {
      return this.append(var1, Attributes::lambda$append$0);
   }

   private String append(EnumSet var1, Function var2) {
      return (String)var1.stream().map(var2).collect(Collectors.joining(" "));
   }

   private static String lambda$append$0(Enum var0) {
      return var0.name().toLowerCase();
   }
}
