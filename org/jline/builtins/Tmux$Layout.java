package org.jline.builtins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Tmux$Layout {
   static final Pattern PATTERN = Pattern.compile("([0-9]+)x([0-9]+),([0-9]+),([0-9]+)([^0-9]\\S*)?");
   private static final int PANE_MINIMUM = 3;
   Tmux$Layout$Type type;
   Tmux$Layout parent;
   int sx;
   int sy;
   int xoff;
   int yoff;
   List cells = new ArrayList();

   Tmux$Layout() {
      super();
   }

   public static Tmux$Layout parse(String var0) {
      if (var0.length() < 6) {
         throw new IllegalArgumentException("Bad syntax");
      } else {
         String var1 = var0.substring(0, 4);
         if (var0.charAt(4) != ',') {
            throw new IllegalArgumentException("Bad syntax");
         } else {
            var0 = var0.substring(5);
            if (Integer.parseInt(var1, 16) != checksum(var0)) {
               throw new IllegalArgumentException("Bad checksum");
            } else {
               return parseCell((Tmux$Layout)null, var0);
            }
         }
      }
   }

   public String dump() {
      StringBuilder var1 = new StringBuilder(64);
      var1.append("0000,");
      this.doDump(var1);
      int var2 = checksum(var1, 5);
      var1.setCharAt(0, toHexChar(var2 >> 12 & 15));
      var1.setCharAt(1, toHexChar(var2 >> 8 & 15));
      var1.setCharAt(2, toHexChar(var2 >> 4 & 15));
      var1.setCharAt(3, toHexChar(var2 & 15));
      return var1.toString();
   }

   private static char toHexChar(int var0) {
      return var0 < 10 ? (char)(var0 + 48) : (char)(var0 - 10 + 97);
   }

   private void doDump(StringBuilder var1) {
      // $FF: Couldn't be decompiled
   }

   public void resize(Tmux$Layout$Type var1, int var2, boolean var3) {
      Tmux$Layout var4 = this;

      Tmux$Layout var5;
      for(var5 = this.parent; var5 != null && var5.type != var1; var5 = var5.parent) {
         var4 = var5;
      }

      if (var5 != null) {
         if (var4.nextSibling() == null) {
            var4 = var4.prevSibling();
         }

         int var7 = var2;

         while(var7 != 0) {
            int var6;
            if (var2 > 0) {
               var6 = var4.resizePaneGrow(var1, var7, var3);
               var7 -= var6;
            } else {
               var6 = var4.resizePaneShrink(var1, var7);
               var7 += var6;
            }

            if (var6 == 0) {
               break;
            }
         }

         this.fixOffsets();
         this.fixPanes();
      }
   }

   int resizePaneGrow(Tmux$Layout$Type var1, int var2, boolean var3) {
      int var4 = 0;

      Tmux$Layout var6;
      for(var6 = this.nextSibling(); var6 != null; var6 = var6.nextSibling()) {
         var4 = var6.resizeCheck(var1);
         if (var4 > 0) {
            break;
         }
      }

      if (var3 && var6 == null) {
         for(var6 = this.prevSibling(); var6 != null; var6 = var6.prevSibling()) {
            var4 = var6.resizeCheck(var1);
            if (var4 > 0) {
               break;
            }
         }
      }

      if (var6 == null) {
         return 0;
      } else {
         if (var4 > var2) {
            var4 = var2;
         }

         this.resizeAdjust(var1, var4);
         var6.resizeAdjust(var1, -var4);
         return var4;
      }
   }

   int resizePaneShrink(Tmux$Layout$Type var1, int var2) {
      boolean var3 = false;
      Tmux$Layout var4 = this;

      int var6;
      do {
         var6 = var4.resizeCheck(var1);
         if (var6 > 0) {
            break;
         }

         var4 = var4.prevSibling();
      } while(var4 != null);

      if (var4 == null) {
         return 0;
      } else {
         Tmux$Layout var5 = this.nextSibling();
         if (var5 == null) {
            return 0;
         } else {
            if (var6 > -var2) {
               var6 = -var2;
            }

            var5.resizeAdjust(var1, var6);
            var4.resizeAdjust(var1, -var6);
            return var6;
         }
      }
   }

   Tmux$Layout prevSibling() {
      int var1 = this.parent.cells.indexOf(this);
      return var1 > 0 ? (Tmux$Layout)this.parent.cells.get(var1 - 1) : null;
   }

   Tmux$Layout nextSibling() {
      int var1 = this.parent.cells.indexOf(this);
      return var1 < this.parent.cells.size() - 1 ? (Tmux$Layout)this.parent.cells.get(var1 + 1) : null;
   }

   public void resizeTo(Tmux$Layout$Type var1, int var2) {
      Tmux$Layout var3 = this;

      Tmux$Layout var4;
      for(var4 = this.parent; var4 != null && var4.type != var1; var4 = var4.parent) {
         var3 = var4;
      }

      if (var4 != null) {
         int var5 = var1 == Tmux$Layout$Type.LeftRight ? var3.sx : var3.sy;
         int var6 = var3.nextSibling() == null ? var5 - var2 : var2 - var5;
         var3.resize(var1, var6, true);
      }
   }

   public void resize(int var1, int var2) {
      int var3 = var1 - this.sx;
      int var4 = this.resizeCheck(Tmux$Layout$Type.LeftRight);
      if (var3 < 0 && var3 < -var4) {
         var3 = -var4;
      }

      if (var4 == 0) {
         if (var1 <= this.sx) {
            var3 = 0;
         } else {
            var3 = var1 - this.sx;
         }
      }

      if (var3 != 0) {
         this.resizeAdjust(Tmux$Layout$Type.LeftRight, var3);
      }

      int var5 = var2 - this.sy;
      int var6 = this.resizeCheck(Tmux$Layout$Type.TopBottom);
      if (var5 < 0 && var5 < -var6) {
         var5 = -var6;
      }

      if (var6 == 0) {
         if (var2 <= this.sy) {
            var5 = 0;
         } else {
            var5 = var2 - this.sy;
         }
      }

      if (var5 != 0) {
         this.resizeAdjust(Tmux$Layout$Type.TopBottom, var5);
      }

      this.fixOffsets();
      this.fixPanes(var1, var2);
   }

   public void remove() {
      if (this.parent == null) {
         throw new IllegalStateException();
      } else {
         int var1 = this.parent.cells.indexOf(this);
         Tmux$Layout var2 = (Tmux$Layout)this.parent.cells.get(var1 == 0 ? 1 : var1 - 1);
         var2.resizeAdjust(this.parent.type, this.parent.type == Tmux$Layout$Type.LeftRight ? this.sx + 1 : this.sy + 1);
         this.parent.cells.remove(this);
         if (var2.parent.cells.size() == 1) {
            if (var2.parent.parent == null) {
               var2.parent = null;
            } else {
               var2.parent.parent.cells.set(var2.parent.parent.cells.indexOf(var2.parent), var2);
               var2.parent = var2.parent.parent;
            }
         }

      }
   }

   private int resizeCheck(Tmux$Layout$Type var1) {
      if (this.type == Tmux$Layout$Type.WindowPane) {
         int var2 = 3;
         int var3;
         if (var1 == Tmux$Layout$Type.LeftRight) {
            var3 = this.sx;
         } else {
            var3 = this.sy;
            ++var2;
         }

         if (var3 > var2) {
            var3 -= var2;
         } else {
            var3 = 0;
         }

         return var3;
      } else {
         return this.type == var1 ? this.cells.stream().mapToInt(Tmux$Layout::lambda$resizeCheck$0).sum() : this.cells.stream().mapToInt(Tmux$Layout::lambda$resizeCheck$1).min().orElse(0);
      }
   }

   private void resizeAdjust(Tmux$Layout$Type var1, int var2) {
      if (var1 == Tmux$Layout$Type.LeftRight) {
         this.sx += var2;
      } else {
         this.sy += var2;
      }

      if (this.type != Tmux$Layout$Type.WindowPane) {
         Iterator var3;
         Tmux$Layout var4;
         if (this.type != var1) {
            var3 = this.cells.iterator();

            while(var3.hasNext()) {
               var4 = (Tmux$Layout)var3.next();
               var4.resizeAdjust(var1, var2);
            }

         } else {
            while(var2 != 0) {
               var3 = this.cells.iterator();

               while(var3.hasNext()) {
                  var4 = (Tmux$Layout)var3.next();
                  if (var2 == 0) {
                     break;
                  }

                  if (var2 > 0) {
                     var4.resizeAdjust(var1, 1);
                     --var2;
                  } else if (var4.resizeCheck(var1) > 0) {
                     var4.resizeAdjust(var1, -1);
                     ++var2;
                  }
               }
            }

         }
      }
   }

   public void fixOffsets() {
      int var1;
      Iterator var2;
      Tmux$Layout var3;
      if (this.type == Tmux$Layout$Type.LeftRight) {
         var1 = this.xoff;

         for(var2 = this.cells.iterator(); var2.hasNext(); var1 += var3.sx + 1) {
            var3 = (Tmux$Layout)var2.next();
            var3.xoff = var1;
            var3.yoff = this.yoff;
            var3.fixOffsets();
         }
      } else if (this.type == Tmux$Layout$Type.TopBottom) {
         var1 = this.yoff;

         for(var2 = this.cells.iterator(); var2.hasNext(); var1 += var3.sy + 1) {
            var3 = (Tmux$Layout)var2.next();
            var3.xoff = this.xoff;
            var3.yoff = var1;
            var3.fixOffsets();
         }
      }

   }

   public void fixPanes() {
   }

   public void fixPanes(int var1, int var2) {
   }

   public int countCells() {
      // $FF: Couldn't be decompiled
   }

   public Tmux$Layout split(Tmux$Layout$Type var1, int var2, boolean var3) {
      if (var1 == Tmux$Layout$Type.WindowPane) {
         throw new IllegalStateException();
      } else if ((var1 == Tmux$Layout$Type.LeftRight ? this.sx : this.sy) < 7) {
         return null;
      } else if (this.parent == null) {
         throw new IllegalStateException();
      } else {
         int var4 = var1 == Tmux$Layout$Type.LeftRight ? this.sx : this.sy;
         int var5 = var2 < 0 ? (var4 + 1) / 2 - 1 : (var3 ? var4 - var2 - 1 : var2);
         if (var5 < 3) {
            var5 = 3;
         } else if (var5 > var4 - 2) {
            var5 = var4 - 2;
         }

         int var6 = var4 - 1 - var5;
         Tmux$Layout var7;
         if (this.parent.type != var1) {
            var7 = new Tmux$Layout();
            var7.type = var1;
            var7.parent = this.parent;
            var7.sx = this.sx;
            var7.sy = this.sy;
            var7.xoff = this.xoff;
            var7.yoff = this.yoff;
            this.parent.cells.set(this.parent.cells.indexOf(this), var7);
            var7.cells.add(this);
            this.parent = var7;
         }

         var7 = new Tmux$Layout();
         var7.type = Tmux$Layout$Type.WindowPane;
         var7.parent = this.parent;
         this.parent.cells.add(this.parent.cells.indexOf(this) + (var3 ? 0 : 1), var7);
         int var8 = this.sx;
         int var9 = this.sy;
         int var10 = this.xoff;
         int var11 = this.yoff;
         Tmux$Layout var12;
         Tmux$Layout var13;
         if (var3) {
            var12 = var7;
            var13 = this;
         } else {
            var12 = this;
            var13 = var7;
         }

         if (var1 == Tmux$Layout$Type.LeftRight) {
            var12.setSize(var6, var9, var10, var11);
            var13.setSize(var5, var9, var10 + var6 + 1, var11);
         } else {
            var12.setSize(var8, var6, var10, var11);
            var13.setSize(var8, var5, var10, var11 + var6 + 1);
         }

         return var7;
      }
   }

   private void setSize(int var1, int var2, int var3, int var4) {
      this.sx = var1;
      this.sy = var2;
      this.xoff = var3;
      this.yoff = var4;
   }

   private static int checksum(CharSequence var0) {
      return checksum(var0, 0);
   }

   private static int checksum(CharSequence var0, int var1) {
      int var2 = 0;

      for(int var3 = var1; var3 < var0.length(); ++var3) {
         var2 = (var2 >> 1) + ((var2 & 1) << 15);
         var2 += var0.charAt(var3);
      }

      return var2;
   }

   private static Tmux$Layout parseCell(Tmux$Layout var0, String var1) {
      Matcher var2 = PATTERN.matcher(var1);
      if (var2.matches()) {
         Tmux$Layout var3 = new Tmux$Layout();
         var3.type = Tmux$Layout$Type.WindowPane;
         var3.parent = var0;
         var3.sx = Integer.parseInt(var2.group(1));
         var3.sy = Integer.parseInt(var2.group(2));
         var3.xoff = Integer.parseInt(var2.group(3));
         var3.yoff = Integer.parseInt(var2.group(4));
         if (var0 != null) {
            var0.cells.add(var3);
         }

         var1 = var2.group(5);
         if (var1 != null && !var1.isEmpty()) {
            int var4;
            if (var1.charAt(0) == ',') {
               for(var4 = 1; var4 < var1.length() && Character.isDigit(var1.charAt(var4)); ++var4) {
               }

               if (var4 == var1.length()) {
                  return var3;
               }

               if (var1.charAt(var4) == ',') {
                  var1 = var1.substring(var4);
               }
            }

            switch(var1.charAt(0)) {
            case ',':
               parseCell(var0, var1.substring(1));
               return var3;
            case '[':
               var3.type = Tmux$Layout$Type.TopBottom;
               var4 = Tmux.access$500(var1, '[', ']');
               parseCell(var3, var1.substring(1, var4));
               var1 = var1.substring(var4 + 1);
               if (!var1.isEmpty() && var1.charAt(0) == ',') {
                  parseCell(var0, var1.substring(1));
               }

               return var3;
            case '{':
               var3.type = Tmux$Layout$Type.LeftRight;
               var4 = Tmux.access$500(var1, '{', '}');
               parseCell(var3, var1.substring(1, var4));
               var1 = var1.substring(var4 + 1);
               if (!var1.isEmpty() && var1.charAt(0) == ',') {
                  parseCell(var0, var1.substring(1));
               }

               return var3;
            default:
               throw new IllegalArgumentException("Unexpected '" + var1.charAt(0) + "'");
            }
         } else {
            return var3;
         }
      } else {
         throw new IllegalArgumentException("Bad syntax");
      }
   }

   private static int lambda$resizeCheck$1(Tmux$Layout$Type var0, Tmux$Layout var1) {
      return var1.resizeCheck(var0);
   }

   private static int lambda$resizeCheck$0(Tmux$Layout$Type var0, Tmux$Layout var1) {
      return var1.resizeCheck(var0);
   }
}
