package org.jline.builtins;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jline.utils.Colors;
import org.jline.utils.WCWidth;

public class ScreenTerminal {
   private int width;
   private int height;
   private long attr;
   private boolean eol;
   private int cx;
   private int cy;
   private long[][] screen;
   private long[][] screen2;
   private ScreenTerminal$State vt100_parse_state;
   private int vt100_parse_len;
   private int vt100_lastchar;
   private int vt100_parse_func;
   private String vt100_parse_param;
   private boolean vt100_mode_autowrap;
   private boolean vt100_mode_insert;
   private boolean vt100_charset_is_single_shift;
   private boolean vt100_charset_is_graphical;
   private boolean vt100_mode_lfnewline;
   private boolean vt100_mode_origin;
   private boolean vt100_mode_inverse;
   private boolean vt100_mode_cursorkey;
   private boolean vt100_mode_cursor;
   private boolean vt100_mode_alt_screen;
   private boolean vt100_mode_backspace;
   private boolean vt100_mode_column_switch;
   private boolean vt100_keyfilter_escape;
   private int[] vt100_charset_graph;
   private int vt100_charset_g_sel;
   private int[] vt100_charset_g;
   private Map vt100_saved;
   private Map vt100_saved2;
   private int vt100_alternate_saved_cx;
   private int vt100_alternate_saved_cy;
   private int vt100_saved_cx;
   private int vt100_saved_cy;
   private String vt100_out;
   private int scroll_area_y0;
   private int scroll_area_y1;
   private List tab_stops;
   private final List history;
   private AtomicBoolean dirty;

   public ScreenTerminal() {
      this(80, 24);
   }

   public ScreenTerminal(int var1, int var2) {
      super();
      this.vt100_parse_state = ScreenTerminal$State.None;
      this.vt100_charset_graph = new int[]{9674, 8230, 8226, 63, 182, 63, 176, 177, 63, 63, 43, 43, 43, 43, 43, 175, 8212, 8212, 8212, 95, 43, 43, 43, 43, 124, 8804, 8805, 182, 8800, 163, 183, 127};
      this.vt100_charset_g = new int[]{0, 0};
      this.history = new ArrayList();
      this.dirty = new AtomicBoolean(true);
      this.width = var1;
      this.height = var2;
      this.reset_hard();
   }

   private void reset_hard() {
      this.attr = 0L;
      this.vt100_keyfilter_escape = false;
      this.vt100_lastchar = 0;
      this.vt100_parse_len = 0;
      this.vt100_parse_state = ScreenTerminal$State.None;
      this.vt100_parse_func = 0;
      this.vt100_parse_param = "";
      this.vt100_out = "";
      this.reset_screen();
      this.reset_soft();
   }

   private void reset_soft() {
      this.attr = 0L;
      this.scroll_area_y0 = 0;
      this.scroll_area_y1 = this.height;
      this.vt100_charset_is_single_shift = false;
      this.vt100_charset_is_graphical = false;
      this.vt100_charset_g_sel = 0;
      this.vt100_charset_g = new int[]{0, 0};
      this.vt100_mode_insert = false;
      this.vt100_mode_lfnewline = false;
      this.vt100_mode_cursorkey = false;
      this.vt100_mode_column_switch = false;
      this.vt100_mode_inverse = false;
      this.vt100_mode_origin = false;
      this.vt100_mode_autowrap = true;
      this.vt100_mode_cursor = true;
      this.vt100_mode_alt_screen = false;
      this.vt100_mode_backspace = false;
      this.esc_DECSC();
      this.vt100_saved2 = this.vt100_saved;
      this.esc_DECSC();
   }

   private void reset_screen() {
      this.screen = (long[][])((long[][])Array.newInstance(Long.TYPE, new int[]{this.height, this.width}));
      this.screen2 = (long[][])((long[][])Array.newInstance(Long.TYPE, new int[]{this.height, this.width}));

      int var1;
      for(var1 = 0; var1 < this.height; ++var1) {
         Arrays.fill(this.screen[var1], this.attr | 32L);
         Arrays.fill(this.screen2[var1], this.attr | 32L);
      }

      this.scroll_area_y0 = 0;
      this.scroll_area_y1 = this.height;
      this.cx = 0;
      this.cy = 0;
      this.tab_stops = new ArrayList();

      for(var1 = 7; var1 < this.width; var1 += 8) {
         this.tab_stops.add(var1);
      }

   }

   private int utf8_charwidth(int var1) {
      return WCWidth.wcwidth(var1);
   }

   private long[] peek(int var1, int var2, int var3, int var4) {
      int var5 = this.width * var1 + var2;
      int var6 = this.width * (var3 - 1) + var4;
      int var7 = var6 - var5;
      if (var7 < 0) {
         throw new IllegalArgumentException(var5 + " > " + var6);
      } else {
         long[] var8 = new long[var7];

         int var12;
         for(int var9 = var5; var9 < var6; var9 += var12) {
            int var10 = var9 / this.width;
            int var11 = var9 % this.width;
            var12 = Math.min(this.width - var11, var6 - var9);
            System.arraycopy(this.screen[var10], var11, var8, var9 - var5, var12);
         }

         return var8;
      }
   }

   private void poke(int var1, int var2, long[] var3) {
      int var4 = 0;

      int var6;
      for(int var5 = var3.length; var4 < var5; var4 += var6) {
         var6 = Math.min(this.width - var2, var5 - var4);
         System.arraycopy(var3, 0, this.screen[var1++], var2, var6);
         var2 = 0;
      }

      this.setDirty();
   }

   private void fill(int var1, int var2, int var3, int var4, long var5) {
      if (var1 == var3 - 1) {
         if (var2 < var4 - 1) {
            Arrays.fill(this.screen[var1], var2, var4, var5);
            this.setDirty();
         }
      } else if (var1 < var3 - 1) {
         Arrays.fill(this.screen[var1], var2, this.width, var5);

         for(int var7 = var1; var7 < var3 - 1; ++var7) {
            Arrays.fill(this.screen[var7], var5);
         }

         Arrays.fill(this.screen[var3 - 1], 0, var4, var5);
         this.setDirty();
      }

   }

   private void clear(int var1, int var2, int var3, int var4) {
      this.fill(var1, var2, var3, var4, this.attr | 32L);
   }

   private void scroll_area_up(int var1, int var2) {
      this.scroll_area_up(var1, var2, 1);
   }

   private void scroll_area_up(int var1, int var2, int var3) {
      var3 = Math.min(var2 - var1, var3);
      if (var1 == 0 && var2 == this.height) {
         int var4;
         for(var4 = 0; var4 < var3; ++var4) {
            this.history.add(this.screen[var4]);
         }

         System.arraycopy(this.screen, var3, this.screen, 0, this.height - var3);

         for(var4 = 1; var4 <= var3; ++var4) {
            this.screen[var2 - var4] = new long[this.width];
            Arrays.fill(this.screen[var2 - 1], this.attr | 32L);
         }
      } else {
         this.poke(var1, 0, this.peek(var1 + var3, 0, var2, this.width));
         this.clear(var2 - var3, 0, var2, this.width);
      }

   }

   private void scroll_area_down(int var1, int var2) {
      this.scroll_area_down(var1, var2, 1);
   }

   private void scroll_area_down(int var1, int var2, int var3) {
      var3 = Math.min(var2 - var1, var3);
      this.poke(var1 + var3, 0, this.peek(var1, 0, var2 - var3, this.width));
      this.clear(var1, 0, var1 + var3, this.width);
   }

   private void scroll_area_set(int var1, int var2) {
      var1 = Math.max(0, Math.min(this.height - 1, var1));
      var2 = Math.max(1, Math.min(this.height, var2));
      if (var2 > var1) {
         this.scroll_area_y0 = var1;
         this.scroll_area_y1 = var2;
      }

   }

   private void scroll_line_right(int var1, int var2) {
      this.scroll_line_right(var1, var2, 1);
   }

   private void scroll_line_right(int var1, int var2, int var3) {
      if (var2 < this.width) {
         var3 = Math.min(this.width - this.cx, var3);
         this.poke(var1, var2 + var3, this.peek(var1, var2, var1 + 1, this.width - var3));
         this.clear(var1, var2, var1 + 1, var2 + var3);
      }

   }

   private void scroll_line_left(int var1, int var2) {
      this.scroll_line_left(var1, var2, 1);
   }

   private void scroll_line_left(int var1, int var2, int var3) {
      if (var2 < this.width) {
         var3 = Math.min(this.width - this.cx, var3);
         this.poke(var1, var2, this.peek(var1, var2 + var3, var1 + 1, this.width));
         this.clear(var1, this.width - var3, var1 + 1, this.width);
      }

   }

   private int[] cursor_line_width(int var1) {
      int var2 = this.utf8_charwidth(var1);
      int var3 = 0;

      for(int var4 = 0; var4 < Math.min(this.cx, this.width); ++var4) {
         int var5 = (int)(this.peek(this.cy, var4, this.cy + 1, var4 + 1)[0] & 4294967295L);
         var2 += this.utf8_charwidth(var5);
         ++var3;
      }

      return new int[]{var2, var3};
   }

   private void cursor_up() {
      this.cursor_up(1);
   }

   private void cursor_up(int var1) {
      this.cy = Math.max(this.scroll_area_y0, this.cy - var1);
      this.setDirty();
   }

   private void cursor_down() {
      this.cursor_down(1);
   }

   private void cursor_down(int var1) {
      this.cy = Math.min(this.scroll_area_y1 - 1, this.cy + var1);
      this.setDirty();
   }

   private void cursor_left() {
      this.cursor_left(1);
   }

   private void cursor_left(int var1) {
      this.eol = false;
      this.cx = Math.max(0, this.cx - var1);
      this.setDirty();
   }

   private void cursor_right() {
      this.cursor_right(1);
   }

   private void cursor_right(int var1) {
      this.eol = this.cx + var1 >= this.width;
      this.cx = Math.min(this.width - 1, this.cx + var1);
      this.setDirty();
   }

   private void cursor_set_x(int var1) {
      this.eol = false;
      this.cx = Math.max(0, var1);
      this.setDirty();
   }

   private void cursor_set_y(int var1) {
      this.cy = Math.max(0, Math.min(this.height - 1, var1));
      this.setDirty();
   }

   private void cursor_set(int var1, int var2) {
      this.cursor_set_x(var2);
      this.cursor_set_y(var1);
   }

   private void ctrl_BS() {
      int var1 = (this.cx - 1) / this.width;
      this.cursor_set(Math.max(this.scroll_area_y0, this.cy + var1), (this.cx - 1) % this.width);
   }

   private void ctrl_HT() {
      this.ctrl_HT(1);
   }

   private void ctrl_HT(int var1) {
      if (var1 <= 0 || this.cx < this.width) {
         if (var1 > 0 || this.cx != 0) {
            int var2 = -1;

            for(int var3 = 0; var3 < this.tab_stops.size(); ++var3) {
               if (this.cx >= (Integer)this.tab_stops.get(var3)) {
                  var2 = var3;
               }
            }

            var2 += var1;
            if (var2 < this.tab_stops.size() && var2 >= 0) {
               this.cursor_set_x((Integer)this.tab_stops.get(var2));
            } else {
               this.cursor_set_x(this.width - 1);
            }

         }
      }
   }

   private void ctrl_LF() {
      if (this.vt100_mode_lfnewline) {
         this.ctrl_CR();
      }

      if (this.cy == this.scroll_area_y1 - 1) {
         this.scroll_area_up(this.scroll_area_y0, this.scroll_area_y1);
      } else {
         this.cursor_down();
      }

   }

   private void ctrl_CR() {
      this.cursor_set_x(0);
   }

   private boolean dumb_write(int var1) {
      if (var1 >= 32) {
         return false;
      } else {
         if (var1 == 8) {
            this.ctrl_BS();
         } else if (var1 == 9) {
            this.ctrl_HT();
         } else if (var1 >= 10 && var1 <= 12) {
            this.ctrl_LF();
         } else if (var1 == 13) {
            this.ctrl_CR();
         }

         return true;
      }
   }

   private void dumb_echo(int var1) {
      if (this.eol) {
         if (this.vt100_mode_autowrap) {
            this.ctrl_CR();
            this.ctrl_LF();
         } else {
            this.cx = this.cursor_line_width(var1)[1] - 1;
         }
      }

      if (this.vt100_mode_insert) {
         this.scroll_line_right(this.cy, this.cx);
      }

      if (this.vt100_charset_is_single_shift) {
         this.vt100_charset_is_single_shift = false;
      } else if (this.vt100_charset_is_graphical && (var1 & '￠') == 96) {
         var1 = this.vt100_charset_graph[var1 - 96];
      }

      this.poke(this.cy, this.cx, new long[]{this.attr | (long)var1});
      this.cursor_right();
   }

   private void vt100_charset_update() {
      this.vt100_charset_is_graphical = this.vt100_charset_g[this.vt100_charset_g_sel] == 2;
   }

   private void vt100_charset_set(int var1) {
      this.vt100_charset_g_sel = var1;
      this.vt100_charset_update();
   }

   private void vt100_charset_select(int var1, int var2) {
      this.vt100_charset_g[var1] = var2;
      this.vt100_charset_update();
   }

   private void vt100_setmode(String var1, boolean var2) {
      String[] var3 = this.vt100_parse_params(var1, new String[0]);
      String[] var4 = var3;
      int var5 = var3.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String var7 = var4[var6];
         byte var9 = -1;
         switch(var7.hashCode()) {
         case 52:
            if (var7.equals("4")) {
               var9 = 0;
            }
            break;
         case 1598:
            if (var7.equals("20")) {
               var9 = 1;
            }
            break;
         case 2002:
            if (var7.equals("?1")) {
               var9 = 2;
            }
            break;
         case 2004:
            if (var7.equals("?3")) {
               var9 = 3;
            }
            break;
         case 2006:
            if (var7.equals("?5")) {
               var9 = 4;
            }
            break;
         case 2007:
            if (var7.equals("?6")) {
               var9 = 5;
            }
            break;
         case 2008:
            if (var7.equals("?7")) {
               var9 = 6;
            }
            break;
         case 62146:
            if (var7.equals("?25")) {
               var9 = 7;
            }
            break;
         case 62203:
            if (var7.equals("?40")) {
               var9 = 8;
            }
            break;
         case 62272:
            if (var7.equals("?67")) {
               var9 = 10;
            }
            break;
         case 59689379:
            if (var7.equals("?1049")) {
               var9 = 9;
            }
         }

         switch(var9) {
         case 0:
            this.vt100_mode_insert = var2;
            break;
         case 1:
            this.vt100_mode_lfnewline = var2;
            break;
         case 2:
            this.vt100_mode_cursorkey = var2;
            break;
         case 3:
            if (this.vt100_mode_column_switch) {
               if (var2) {
                  this.width = 132;
               } else {
                  this.width = 80;
               }

               this.reset_screen();
            }
            break;
         case 4:
            this.vt100_mode_inverse = var2;
            break;
         case 5:
            this.vt100_mode_origin = var2;
            if (var2) {
               this.cursor_set(this.scroll_area_y0, 0);
            } else {
               this.cursor_set(0, 0);
            }
            break;
         case 6:
            this.vt100_mode_autowrap = var2;
            break;
         case 7:
            this.vt100_mode_cursor = var2;
            break;
         case 8:
            this.vt100_mode_column_switch = var2;
            break;
         case 9:
            if (var2 && !this.vt100_mode_alt_screen || !var2 && this.vt100_mode_alt_screen) {
               long[][] var10 = this.screen;
               this.screen = this.screen2;
               this.screen2 = var10;
               Map var11 = this.vt100_saved;
               this.vt100_saved = this.vt100_saved2;
               this.vt100_saved2 = var11;
               int var12 = this.vt100_alternate_saved_cx;
               this.vt100_alternate_saved_cx = this.cx;
               this.cx = Math.min(var12, this.width - 1);
               var12 = this.vt100_alternate_saved_cy;
               this.vt100_alternate_saved_cy = this.cy;
               this.cy = Math.min(var12, this.height - 1);
            }

            this.vt100_mode_alt_screen = var2;
            break;
         case 10:
            this.vt100_mode_backspace = var2;
         }
      }

   }

   private void ctrl_SO() {
      this.vt100_charset_set(1);
   }

   private void ctrl_SI() {
      this.vt100_charset_set(0);
   }

   private void esc_CSI() {
      this.vt100_parse_reset(ScreenTerminal$State.Csi);
   }

   private void esc_DECALN() {
      this.fill(0, 0, this.height, this.width, 16711749L);
   }

   private void esc_G0_0() {
      this.vt100_charset_select(0, 0);
   }

   private void esc_G0_1() {
      this.vt100_charset_select(0, 1);
   }

   private void esc_G0_2() {
      this.vt100_charset_select(0, 2);
   }

   private void esc_G0_3() {
      this.vt100_charset_select(0, 3);
   }

   private void esc_G0_4() {
      this.vt100_charset_select(0, 4);
   }

   private void esc_G1_0() {
      this.vt100_charset_select(1, 0);
   }

   private void esc_G1_1() {
      this.vt100_charset_select(1, 1);
   }

   private void esc_G1_2() {
      this.vt100_charset_select(1, 2);
   }

   private void esc_G1_3() {
      this.vt100_charset_select(1, 3);
   }

   private void esc_G1_4() {
      this.vt100_charset_select(1, 4);
   }

   private void esc_DECSC() {
      this.vt100_saved = new HashMap();
      this.vt100_saved.put("cx", this.cx);
      this.vt100_saved.put("cy", this.cy);
      this.vt100_saved.put("attr", this.attr);
      this.vt100_saved.put("vt100_charset_g_sel", this.vt100_charset_g_sel);
      this.vt100_saved.put("vt100_charset_g", this.vt100_charset_g);
      this.vt100_saved.put("vt100_mode_autowrap", this.vt100_mode_autowrap);
      this.vt100_saved.put("vt100_mode_origin", this.vt100_mode_origin);
   }

   private void esc_DECRC() {
      this.cx = (Integer)this.vt100_saved.get("cx");
      this.cy = (Integer)this.vt100_saved.get("cy");
      this.attr = (Long)this.vt100_saved.get("attr");
      this.vt100_charset_g_sel = (Integer)this.vt100_saved.get("vt100_charset_g_sel");
      this.vt100_charset_g = (int[])((int[])this.vt100_saved.get("vt100_charset_g"));
      this.vt100_charset_update();
      this.vt100_mode_autowrap = (Boolean)this.vt100_saved.get("vt100_mode_autowrap");
      this.vt100_mode_origin = (Boolean)this.vt100_saved.get("vt100_mode_origin");
   }

   private void esc_IND() {
      this.ctrl_LF();
   }

   private void esc_NEL() {
      this.ctrl_CR();
      this.ctrl_LF();
   }

   private void esc_HTS() {
      this.csi_CTC("0");
   }

   private void esc_RI() {
      if (this.cy == this.scroll_area_y0) {
         this.scroll_area_down(this.scroll_area_y0, this.scroll_area_y1);
      } else {
         this.cursor_up();
      }

   }

   private void esc_SS2() {
      this.vt100_charset_is_single_shift = true;
   }

   private void esc_SS3() {
      this.vt100_charset_is_single_shift = true;
   }

   private void esc_DCS() {
      this.vt100_parse_reset(ScreenTerminal$State.Str);
   }

   private void esc_SOS() {
      this.vt100_parse_reset(ScreenTerminal$State.Str);
   }

   private void esc_DECID() {
      this.csi_DA("0");
   }

   private void esc_ST() {
   }

   private void esc_OSC() {
      this.vt100_parse_reset(ScreenTerminal$State.Str);
   }

   private void esc_PM() {
      this.vt100_parse_reset(ScreenTerminal$State.Str);
   }

   private void esc_APC() {
      this.vt100_parse_reset(ScreenTerminal$State.Str);
   }

   private void esc_RIS() {
      this.reset_hard();
   }

   private void csi_ICH(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.scroll_line_right(this.cy, this.cx, var2[0]);
   }

   private void csi_CUU(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.cursor_up(Math.max(1, var2[0]));
   }

   private void csi_CUD(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.cursor_down(Math.max(1, var2[0]));
   }

   private void csi_CUF(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.cursor_right(Math.max(1, var2[0]));
   }

   private void csi_CUB(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.cursor_left(Math.max(1, var2[0]));
   }

   private void csi_CNL(String var1) {
      this.csi_CUD(var1);
      this.ctrl_CR();
   }

   private void csi_CPL(String var1) {
      this.csi_CUU(var1);
      this.ctrl_CR();
   }

   private void csi_CHA(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.cursor_set_x(var2[0] - 1);
   }

   private void csi_CUP(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1, 1});
      if (this.vt100_mode_origin) {
         this.cursor_set(this.scroll_area_y0 + var2[0] - 1, var2[1] - 1);
      } else {
         this.cursor_set(var2[0] - 1, var2[1] - 1);
      }

   }

   private void csi_CHT(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.ctrl_HT(Math.max(1, var2[0]));
   }

   private void csi_ED(String var1) {
      String[] var2 = this.vt100_parse_params(var1, new String[]{"0"});
      if ("0".equals(var2[0])) {
         this.clear(this.cy, this.cx, this.height, this.width);
      } else if ("1".equals(var2[0])) {
         this.clear(0, 0, this.cy + 1, this.cx + 1);
      } else if ("2".equals(var2[0])) {
         this.clear(0, 0, this.height, this.width);
      }

   }

   private void csi_EL(String var1) {
      String[] var2 = this.vt100_parse_params(var1, new String[]{"0"});
      if ("0".equals(var2[0])) {
         this.clear(this.cy, this.cx, this.cy + 1, this.width);
      } else if ("1".equals(var2[0])) {
         this.clear(this.cy, 0, this.cy + 1, this.cx + 1);
      } else if ("2".equals(var2[0])) {
         this.clear(this.cy, 0, this.cy + 1, this.width);
      }

   }

   private void csi_IL(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      if (this.cy >= this.scroll_area_y0 && this.cy < this.scroll_area_y1) {
         this.scroll_area_down(this.cy, this.scroll_area_y1, Math.max(1, var2[0]));
      }

   }

   private void csi_DL(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      if (this.cy >= this.scroll_area_y0 && this.cy < this.scroll_area_y1) {
         this.scroll_area_up(this.cy, this.scroll_area_y1, Math.max(1, var2[0]));
      }

   }

   private void csi_DCH(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.scroll_line_left(this.cy, this.cx, Math.max(1, var2[0]));
   }

   private void csi_SU(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.scroll_area_up(this.scroll_area_y0, this.scroll_area_y1, Math.max(1, var2[0]));
   }

   private void csi_SD(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.scroll_area_down(this.scroll_area_y0, this.scroll_area_y1, Math.max(1, var2[0]));
   }

   private void csi_CTC(String var1) {
      String[] var2 = this.vt100_parse_params(var1, new String[]{"0"});
      String[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         if ("0".equals(var6)) {
            if (this.tab_stops.indexOf(this.cx) < 0) {
               this.tab_stops.add(this.cx);
               Collections.sort(this.tab_stops);
            }
         } else if ("2".equals(var6)) {
            this.tab_stops.remove(this.cx);
         } else if ("5".equals(var6)) {
            this.tab_stops = new ArrayList();
         }
      }

   }

   private void csi_ECH(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      int var3 = Math.min(this.width - this.cx, Math.max(1, var2[0]));
      this.clear(this.cy, this.cx, this.cy + 1, this.cx + var3);
   }

   private void csi_CBT(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.ctrl_HT(1 - Math.max(1, var2[0]));
   }

   private void csi_HPA(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.cursor_set_x(var2[0] - 1);
   }

   private void csi_HPR(String var1) {
      this.csi_CUF(var1);
   }

   private void csi_REP(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      if (this.vt100_lastchar >= 32) {
         int var3 = Math.min(2000, Math.max(1, var2[0]));

         while(var3-- > 0) {
            this.dumb_echo(this.vt100_lastchar);
         }

         this.vt100_lastchar = 0;
      }
   }

   private void csi_DA(String var1) {
      String[] var2 = this.vt100_parse_params(var1, new String[]{"0"});
      if ("0".equals(var2[0])) {
         this.vt100_out = "\u001b[?1;2c";
      } else if (">0".equals(var2[0]) || ">".equals(var2[0])) {
         this.vt100_out = "\u001b[>0;184;0c";
      }

   }

   private void csi_VPA(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1});
      this.cursor_set_y(var2[0] - 1);
   }

   private void csi_VPR(String var1) {
      this.csi_CUD(var1);
   }

   private void csi_HVP(String var1) {
      this.csi_CUP(var1);
   }

   private void csi_TBC(String var1) {
      String[] var2 = this.vt100_parse_params(var1, new String[]{"0"});
      if ("0".equals(var2[0])) {
         this.csi_CTC("2");
      } else if ("3".equals(var2[0])) {
         this.csi_CTC("5");
      }

   }

   private void csi_SM(String var1) {
      this.vt100_setmode(var1, true);
   }

   private void csi_RM(String var1) {
      this.vt100_setmode(var1, false);
   }

   private void csi_SGR(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{0});

      for(int var3 = 0; var3 < var2.length; ++var3) {
         int var4 = var2[var3];
         if (var4 == 0) {
            this.attr = 0L;
         } else if (var4 == 1) {
            this.attr |= 576460752303423488L;
         } else if (var4 == 4) {
            this.attr |= 72057594037927936L;
         } else if (var4 == 7) {
            this.attr |= 144115188075855872L;
         } else if (var4 == 8) {
            this.attr |= 288230376151711744L;
         } else if (var4 == 21) {
            this.attr &= -576460756598390784L;
         } else if (var4 == 24) {
            this.attr &= -72057598332895232L;
         } else if (var4 == 27) {
            this.attr &= -144115192370823168L;
         } else if (var4 == 28) {
            this.attr &= -288230380446679040L;
         } else if (var4 >= 30 && var4 <= 37) {
            this.attr = this.attr & -1224961510753697792L | 1152921504606846976L | this.col24(var4 - 30) << 44;
         } else if (var4 == 38) {
            ++var3;
            var4 = var3 < var2.length ? var2[var3] : 0;
            if (var4 == 5) {
               ++var3;
               var4 = var3 < var2.length ? var2[var3] : 0;
               this.attr = this.attr & -1224961510753697792L | 1152921504606846976L | this.col24(var4) << 44;
            }
         } else if (var4 == 39) {
            this.attr &= -1224961510753697792L;
         } else if (var4 >= 40 && var4 <= 47) {
            this.attr = this.attr & -2305860601399738368L | 2305843009213693952L | this.col24(var4 - 40) << 32;
         } else if (var4 == 48) {
            ++var3;
            var4 = var3 < var2.length ? var2[var3] : 0;
            if (var4 == 5) {
               ++var3;
               var4 = var3 < var2.length ? var2[var3] : 0;
               this.attr = this.attr & -2305860601399738368L | 2305843009213693952L | this.col24(var4) << 32;
            }
         } else if (var4 == 49) {
            this.attr &= -2377883015360544768L;
         } else if (var4 >= 90 && var4 <= 97) {
            this.attr = this.attr & -1224961510753697792L | 1152921504606846976L | this.col24(var4 - 90 + 8) << 44;
         } else if (var4 >= 100 && var4 <= 107) {
            this.attr = this.attr & -2305860601399738368L | 2305843009213693952L | this.col24(var4 - 100 + 8) << 32;
         }
      }

   }

   private long col24(int var1) {
      int var2 = Colors.rgbColor(var1);
      int var3 = var2 >> 16 & 255;
      int var4 = var2 >> 8 & 255;
      int var5 = var2 >> 0 & 255;
      return (long)(var3 >> 4 << 8 | var4 >> 4 << 4 | var5 >> 4 << 0);
   }

   private void csi_DSR(String var1) {
      String[] var2 = this.vt100_parse_params(var1, new String[]{"0"});
      if ("5".equals(var2[0])) {
         this.vt100_out = "\u001b[0n";
      } else if ("6".equals(var2[0])) {
         this.vt100_out = "\u001b[" + (this.cy + 1) + ";" + (this.cx + 1) + "R";
      } else if ("7".equals(var2[0])) {
         this.vt100_out = "gogo-term";
      } else if ("8".equals(var2[0])) {
         this.vt100_out = "1.0-SNAPSHOT";
      } else if ("?6".equals(var2[0])) {
         this.vt100_out = "\u001b[" + (this.cy + 1) + ";" + (this.cx + 1) + ";0R";
      } else if ("?15".equals(var2[0])) {
         this.vt100_out = "\u001b[?13n";
      } else if ("?25".equals(var2[0])) {
         this.vt100_out = "\u001b[?20n";
      } else if ("?26".equals(var2[0])) {
         this.vt100_out = "\u001b[?27;1n";
      } else if ("?53".equals(var2[0])) {
         this.vt100_out = "\u001b[?53n";
      }

   }

   private void csi_DECSTBM(String var1) {
      int[] var2 = this.vt100_parse_params(var1, new int[]{1, this.height});
      this.scroll_area_set(var2[0] - 1, var2[1]);
      if (this.vt100_mode_origin) {
         this.cursor_set(this.scroll_area_y0, 0);
      } else {
         this.cursor_set(0, 0);
      }

   }

   private void csi_SCP(String var1) {
      this.vt100_saved_cx = this.cx;
      this.vt100_saved_cy = this.cy;
   }

   private void csi_RCP(String var1) {
      this.cx = this.vt100_saved_cx;
      this.cy = this.vt100_saved_cy;
   }

   private void csi_DECREQTPARM(String var1) {
      String[] var2 = this.vt100_parse_params(var1, new String[0]);
      if ("0".equals(var2[0])) {
         this.vt100_out = "\u001b[2;1;1;112;112;1;0x";
      } else if ("1".equals(var2[0])) {
         this.vt100_out = "\u001b[3;1;1;112;112;1;0x";
      }

   }

   private void csi_DECSTR(String var1) {
      this.reset_soft();
   }

   private String[] vt100_parse_params(String var1, String[] var2) {
      String var3 = "";
      if (var1.length() > 0 && var1.charAt(0) >= '<' && var1.charAt(0) <= '?') {
         var3 = "" + var1.charAt(0);
         var1 = var1.substring(1);
      }

      String[] var4 = var1.split(";");
      int var5 = Math.max(var4.length, var2.length);
      String[] var6 = new String[var5];

      for(int var7 = 0; var7 < var5; ++var7) {
         String var8 = null;
         if (var7 < var4.length && var4[var7].length() > 0) {
            var8 = var3 + var4[var7];
         }

         if (var8 == null && var7 < var2.length) {
            var8 = var2[var7];
         }

         if (var8 == null) {
            var8 = "";
         }

         var6[var7] = var8;
      }

      return var6;
   }

   private int[] vt100_parse_params(String var1, int[] var2) {
      String var3 = "";
      var1 = var1 == null ? "" : var1;
      if (var1.length() > 0 && var1.charAt(0) >= '<' && var1.charAt(0) <= '?') {
         var3 = var1.substring(0, 1);
         var1 = var1.substring(1);
      }

      String[] var4 = var1.split(";");
      int var5 = Math.max(var4.length, var2.length);
      int[] var6 = new int[var5];

      for(int var7 = 0; var7 < var5; ++var7) {
         Integer var8 = null;
         if (var7 < var4.length) {
            String var9 = var3 + var4[var7];

            try {
               var8 = Integer.parseInt(var9);
            } catch (NumberFormatException var11) {
            }
         }

         if (var8 == null && var7 < var2.length) {
            var8 = var2[var7];
         }

         if (var8 == null) {
            var8 = 0;
         }

         var6[var7] = var8;
      }

      return var6;
   }

   private void vt100_parse_reset() {
      this.vt100_parse_reset(ScreenTerminal$State.None);
   }

   private void vt100_parse_reset(ScreenTerminal$State var1) {
      this.vt100_parse_state = var1;
      this.vt100_parse_len = 0;
      this.vt100_parse_func = 0;
      this.vt100_parse_param = "";
   }

   private void vt100_parse_process() {
      if (this.vt100_parse_state == ScreenTerminal$State.Esc) {
         switch(this.vt100_parse_func) {
         case 54:
         case 66:
         case 67:
         case 73:
         case 74:
         case 75:
         case 76:
         case 81:
         case 82:
         case 83:
         case 84:
         case 85:
         case 86:
         case 87:
         case 90:
         case 96:
         case 97:
         case 98:
         case 100:
         case 108:
         case 110:
         case 111:
         case 124:
         case 125:
         case 126:
         default:
            break;
         case 55:
            this.esc_DECSC();
            break;
         case 56:
            this.esc_DECRC();
            break;
         case 68:
            this.esc_IND();
            break;
         case 69:
            this.esc_NEL();
            break;
         case 70:
            this.esc_NEL();
            break;
         case 72:
            this.esc_HTS();
            break;
         case 77:
            this.esc_RI();
            break;
         case 78:
            this.esc_SS2();
            break;
         case 79:
            this.esc_SS3();
            break;
         case 80:
            this.esc_DCS();
            break;
         case 88:
            this.esc_SOS();
            break;
         case 91:
            this.esc_CSI();
            break;
         case 92:
            this.esc_ST();
            break;
         case 93:
            this.esc_OSC();
            break;
         case 94:
            this.esc_PM();
            break;
         case 95:
            this.esc_APC();
            break;
         case 99:
            this.esc_RIS();
            break;
         case 9016:
            this.esc_DECALN();
            break;
         case 10288:
            this.esc_G0_2();
            break;
         case 10289:
            this.esc_G0_3();
            break;
         case 10290:
            this.esc_G0_4();
            break;
         case 10305:
            this.esc_G0_0();
            break;
         case 10306:
            this.esc_G0_1();
            break;
         case 10544:
            this.esc_G1_2();
            break;
         case 10545:
            this.esc_G1_3();
            break;
         case 10546:
            this.esc_G1_4();
            break;
         case 10561:
            this.esc_G1_0();
            break;
         case 10562:
            this.esc_G1_1();
         }

         if (this.vt100_parse_state == ScreenTerminal$State.Esc) {
            this.vt100_parse_reset();
         }
      } else {
         switch(this.vt100_parse_func) {
         case 64:
            this.csi_ICH(this.vt100_parse_param);
            break;
         case 65:
            this.csi_CUU(this.vt100_parse_param);
            break;
         case 66:
            this.csi_CUD(this.vt100_parse_param);
            break;
         case 67:
            this.csi_CUF(this.vt100_parse_param);
            break;
         case 68:
            this.csi_CUB(this.vt100_parse_param);
            break;
         case 69:
            this.csi_CNL(this.vt100_parse_param);
            break;
         case 70:
            this.csi_CPL(this.vt100_parse_param);
            break;
         case 71:
            this.csi_CHA(this.vt100_parse_param);
            break;
         case 72:
            this.csi_CUP(this.vt100_parse_param);
            break;
         case 73:
            this.csi_CHT(this.vt100_parse_param);
            break;
         case 74:
            this.csi_ED(this.vt100_parse_param);
            break;
         case 75:
            this.csi_EL(this.vt100_parse_param);
            break;
         case 76:
            this.csi_IL(this.vt100_parse_param);
            break;
         case 77:
            this.csi_DL(this.vt100_parse_param);
         case 78:
         case 79:
         case 81:
         case 82:
         case 85:
         case 86:
         case 89:
         case 91:
         case 92:
         case 93:
         case 94:
         case 105:
         case 106:
         case 107:
         case 111:
         case 8256:
         case 8257:
         case 8258:
         case 8259:
         case 8260:
         case 8261:
         case 8262:
         case 8263:
         case 8264:
         case 8265:
         case 8266:
         case 8267:
         case 8268:
         case 8269:
         case 8270:
         case 8271:
         case 8272:
         case 8273:
         case 8274:
         case 8275:
         case 8276:
         case 8277:
         case 8278:
         case 8279:
         case 8280:
         case 8281:
         case 8282:
         case 8283:
         case 8284:
         case 8285:
         case 8286:
         case 8287:
         case 8288:
         case 8289:
         case 8290:
         case 8291:
         case 8292:
         case 8293:
         case 8294:
         case 8295:
         case 8296:
         case 8297:
         case 8298:
         case 8299:
         case 9330:
         case 9335:
         default:
            break;
         case 80:
            this.csi_DCH(this.vt100_parse_param);
            break;
         case 83:
            this.csi_SU(this.vt100_parse_param);
            break;
         case 84:
            this.csi_SD(this.vt100_parse_param);
            break;
         case 87:
            this.csi_CTC(this.vt100_parse_param);
            break;
         case 88:
            this.csi_ECH(this.vt100_parse_param);
            break;
         case 90:
            this.csi_CBT(this.vt100_parse_param);
            break;
         case 96:
            this.csi_HPA(this.vt100_parse_param);
            break;
         case 97:
            this.csi_HPR(this.vt100_parse_param);
            break;
         case 98:
            this.csi_REP(this.vt100_parse_param);
            break;
         case 99:
            this.csi_DA(this.vt100_parse_param);
            break;
         case 100:
            this.csi_VPA(this.vt100_parse_param);
            break;
         case 101:
            this.csi_VPR(this.vt100_parse_param);
            break;
         case 102:
            this.csi_HVP(this.vt100_parse_param);
            break;
         case 103:
            this.csi_TBC(this.vt100_parse_param);
            break;
         case 104:
            this.csi_SM(this.vt100_parse_param);
            break;
         case 108:
            this.csi_RM(this.vt100_parse_param);
            break;
         case 109:
            this.csi_SGR(this.vt100_parse_param);
            break;
         case 110:
            this.csi_DSR(this.vt100_parse_param);
            break;
         case 114:
            this.csi_DECSTBM(this.vt100_parse_param);
            break;
         case 115:
            this.csi_SCP(this.vt100_parse_param);
            break;
         case 117:
            this.csi_RCP(this.vt100_parse_param);
            break;
         case 120:
            this.csi_DECREQTPARM(this.vt100_parse_param);
            break;
         case 8560:
            this.csi_DECSTR(this.vt100_parse_param);
         }

         if (this.vt100_parse_state == ScreenTerminal$State.Csi) {
            this.vt100_parse_reset();
         }
      }

   }

   private boolean vt100_write(int var1) {
      if (var1 < 32) {
         if (var1 == 27) {
            this.vt100_parse_reset(ScreenTerminal$State.Esc);
            return true;
         }

         if (var1 == 14) {
            this.ctrl_SO();
         } else if (var1 == 15) {
            this.ctrl_SI();
         }
      } else if ((var1 & '￠') == 128) {
         this.vt100_parse_reset(ScreenTerminal$State.Esc);
         this.vt100_parse_func = (char)(var1 - 64);
         this.vt100_parse_process();
         return true;
      }

      if (this.vt100_parse_state != ScreenTerminal$State.None) {
         if (this.vt100_parse_state == ScreenTerminal$State.Str) {
            if (var1 >= 32) {
               return true;
            }

            this.vt100_parse_reset();
         } else if (var1 < 32) {
            if (var1 == 24 || var1 == 26) {
               this.vt100_parse_reset();
               return true;
            }
         } else {
            ++this.vt100_parse_len;
            if (this.vt100_parse_len <= 32) {
               int var2 = var1 & 240;
               if (var2 == 32) {
                  this.vt100_parse_func <<= 8;
                  this.vt100_parse_func += (char)var1;
               } else if (var2 == 48 && this.vt100_parse_state == ScreenTerminal$State.Csi) {
                  this.vt100_parse_param = this.vt100_parse_param + new String(new char[]{(char)var1});
               } else {
                  this.vt100_parse_func <<= 8;
                  this.vt100_parse_func += (char)var1;
                  this.vt100_parse_process();
               }

               return true;
            }

            this.vt100_parse_reset();
         }
      }

      this.vt100_lastchar = var1;
      return false;
   }

   public boolean isDirty() {
      return this.dirty.compareAndSet(true, false);
   }

   public synchronized void waitDirty() throws InterruptedException {
      while(!this.dirty.compareAndSet(true, false)) {
         this.wait();
      }

   }

   protected synchronized void setDirty() {
      this.dirty.set(true);
      this.notifyAll();
   }

   public synchronized boolean setSize(int var1, int var2) {
      if (var1 >= 2 && var1 <= 256 && var2 >= 2 && var2 <= 256) {
         int var3;
         for(var3 = 0; var3 < this.height; ++var3) {
            if (this.screen[var3].length < var1) {
               this.screen[var3] = Arrays.copyOf(this.screen[var3], var1);
            }

            if (this.screen2[var3].length < var1) {
               this.screen2[var3] = Arrays.copyOf(this.screen2[var3], var1);
            }
         }

         if (this.cx >= var1) {
            this.cx = var1 - 1;
         }

         int var4;
         if (var2 < this.height) {
            var3 = this.height - var2;
            var4 = this.height - 1 - this.cy;
            if (var4 > 0) {
               if (var4 > var3) {
                  var4 = var3;
               }

               this.screen = (long[][])Arrays.copyOfRange(this.screen, 0, this.height - var4);
            }

            var3 -= var4;

            for(int var5 = 0; var5 < var3; ++var5) {
               this.history.add(this.screen[var5]);
            }

            this.screen = (long[][])Arrays.copyOfRange(this.screen, var3, this.screen.length);
            this.cy -= var3;
         } else if (var2 > this.height) {
            var3 = var2 - this.height;
            var4 = this.history.size();
            if (var4 > var3) {
               var4 = var3;
            }

            long[][] var7 = new long[var2][];
            int var6;
            if (var4 > 0) {
               for(var6 = 0; var6 < var4; ++var6) {
                  var7[var6] = (long[])this.history.remove(this.history.size() - var4 + var6);
               }

               this.cy += var4;
            }

            System.arraycopy(this.screen, 0, var7, var4, this.screen.length);

            for(var6 = var4 + this.screen.length; var6 < var7.length; ++var6) {
               var7[var6] = new long[var1];
               Arrays.fill(var7[var6], this.attr | 32L);
            }

            this.screen = var7;
         }

         this.screen2 = (long[][])((long[][])Array.newInstance(Long.TYPE, new int[]{var2, var1}));

         for(var3 = 0; var3 < var2; ++var3) {
            Arrays.fill(this.screen2[var3], this.attr | 32L);
         }

         this.scroll_area_y0 = Math.min(var2, this.scroll_area_y0);
         this.scroll_area_y1 = this.scroll_area_y1 == this.height ? var2 : Math.min(var2, this.scroll_area_y1);
         this.cx = Math.min(var1 - 1, this.cx);
         this.cy = Math.min(var2 - 1, this.cy);
         this.width = var1;
         this.height = var2;
         this.setDirty();
         return true;
      } else {
         return false;
      }
   }

   public synchronized String read() {
      String var1 = this.vt100_out;
      this.vt100_out = "";
      return var1;
   }

   public synchronized String pipe(String var1) {
      String var2 = "";
      char[] var3 = var1.toCharArray();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char var6 = var3[var5];
         if (this.vt100_keyfilter_escape) {
            this.vt100_keyfilter_escape = false;
            if (this.vt100_mode_cursorkey) {
               switch(var6) {
               case '1':
                  var2 = var2 + "\u001b[5~";
                  break;
               case '2':
                  var2 = var2 + "\u001b[6~";
                  break;
               case '3':
                  var2 = var2 + "\u001b[2~";
                  break;
               case '4':
                  var2 = var2 + "\u001b[3~";
               case '5':
               case '6':
               case '7':
               case '8':
               case '9':
               case ':':
               case ';':
               case '<':
               case '=':
               case '>':
               case '?':
               case '@':
               case 'E':
               case 'G':
               case 'I':
               case 'J':
               case 'K':
               case 'L':
               case 'M':
               case 'N':
               case 'O':
               case 'P':
               case 'Q':
               case 'R':
               case 'S':
               case 'T':
               case 'U':
               case 'V':
               case 'W':
               case 'X':
               case 'Y':
               case 'Z':
               case '[':
               case '\\':
               case ']':
               case '^':
               case '_':
               case '`':
               case 'm':
               case 'n':
               case 'o':
               case 'p':
               case 'q':
               case 'r':
               case 's':
               case 't':
               case 'u':
               case 'v':
               case 'w':
               case 'x':
               case 'y':
               case 'z':
               case '{':
               case '|':
               case '}':
               default:
                  break;
               case 'A':
                  var2 = var2 + "\u001bOA";
                  break;
               case 'B':
                  var2 = var2 + "\u001bOB";
                  break;
               case 'C':
                  var2 = var2 + "\u001bOC";
                  break;
               case 'D':
                  var2 = var2 + "\u001bOD";
                  break;
               case 'F':
                  var2 = var2 + "\u001bOF";
                  break;
               case 'H':
                  var2 = var2 + "\u001bOH";
                  break;
               case 'a':
                  var2 = var2 + "\u001bOP";
                  break;
               case 'b':
                  var2 = var2 + "\u001bOQ";
                  break;
               case 'c':
                  var2 = var2 + "\u001bOR";
                  break;
               case 'd':
                  var2 = var2 + "\u001bOS";
                  break;
               case 'e':
                  var2 = var2 + "\u001b[15~";
                  break;
               case 'f':
                  var2 = var2 + "\u001b[17~";
                  break;
               case 'g':
                  var2 = var2 + "\u001b[18~";
                  break;
               case 'h':
                  var2 = var2 + "\u001b[19~";
                  break;
               case 'i':
                  var2 = var2 + "\u001b[20~";
                  break;
               case 'j':
                  var2 = var2 + "\u001b[21~";
                  break;
               case 'k':
                  var2 = var2 + "\u001b[23~";
                  break;
               case 'l':
                  var2 = var2 + "\u001b[24~";
                  break;
               case '~':
                  var2 = var2 + "~";
               }
            } else {
               switch(var6) {
               case '1':
                  var2 = var2 + "\u001b[5~";
                  break;
               case '2':
                  var2 = var2 + "\u001b[6~";
                  break;
               case '3':
                  var2 = var2 + "\u001b[2~";
                  break;
               case '4':
                  var2 = var2 + "\u001b[3~";
               case '5':
               case '6':
               case '7':
               case '8':
               case '9':
               case ':':
               case ';':
               case '<':
               case '=':
               case '>':
               case '?':
               case '@':
               case 'E':
               case 'G':
               case 'I':
               case 'J':
               case 'K':
               case 'L':
               case 'M':
               case 'N':
               case 'O':
               case 'P':
               case 'Q':
               case 'R':
               case 'S':
               case 'T':
               case 'U':
               case 'V':
               case 'W':
               case 'X':
               case 'Y':
               case 'Z':
               case '[':
               case '\\':
               case ']':
               case '^':
               case '_':
               case '`':
               case 'm':
               case 'n':
               case 'o':
               case 'p':
               case 'q':
               case 'r':
               case 's':
               case 't':
               case 'u':
               case 'v':
               case 'w':
               case 'x':
               case 'y':
               case 'z':
               case '{':
               case '|':
               case '}':
               default:
                  break;
               case 'A':
                  var2 = var2 + "\u001b[A";
                  break;
               case 'B':
                  var2 = var2 + "\u001b[B";
                  break;
               case 'C':
                  var2 = var2 + "\u001b[C";
                  break;
               case 'D':
                  var2 = var2 + "\u001b[D";
                  break;
               case 'F':
                  var2 = var2 + "\u001b[F";
                  break;
               case 'H':
                  var2 = var2 + "\u001b[H";
                  break;
               case 'a':
                  var2 = var2 + "\u001bOP";
                  break;
               case 'b':
                  var2 = var2 + "\u001bOQ";
                  break;
               case 'c':
                  var2 = var2 + "\u001bOR";
                  break;
               case 'd':
                  var2 = var2 + "\u001bOS";
                  break;
               case 'e':
                  var2 = var2 + "\u001b[15~";
                  break;
               case 'f':
                  var2 = var2 + "\u001b[17~";
                  break;
               case 'g':
                  var2 = var2 + "\u001b[18~";
                  break;
               case 'h':
                  var2 = var2 + "\u001b[19~";
                  break;
               case 'i':
                  var2 = var2 + "\u001b[20~";
                  break;
               case 'j':
                  var2 = var2 + "\u001b[21~";
                  break;
               case 'k':
                  var2 = var2 + "\u001b[23~";
                  break;
               case 'l':
                  var2 = var2 + "\u001b[24~";
                  break;
               case '~':
                  var2 = var2 + "~";
               }
            }
         } else if (var6 == '~') {
            this.vt100_keyfilter_escape = true;
         } else if (var6 == 127) {
            if (this.vt100_mode_backspace) {
               var2 = var2 + '\b';
            } else {
               var2 = var2 + '\u007f';
            }
         } else {
            var2 = var2 + var6;
            if (this.vt100_mode_lfnewline && var6 == '\r') {
               var2 = var2 + '\n';
            }
         }
      }

      return var2;
   }

   public synchronized boolean write(CharSequence var1) {
      var1.codePoints().forEachOrdered(this::lambda$write$0);
      return true;
   }

   public synchronized void dump(long[] var1, int var2, int var3, int var4, int var5, int[] var6) {
      int var7 = Math.min(this.cx, this.width - 1);
      int var8 = this.cy;

      for(int var9 = 0; var9 < Math.min(this.height, var4 - var2); ++var9) {
         System.arraycopy(this.screen[var9], 0, var1, (var9 + var2) * var5 + var3, this.width);
      }

      if (var6 != null) {
         var6[0] = var7 + var3;
         var6[1] = var8 + var2;
      }

   }

   public synchronized String dump(long var1, boolean var3) throws InterruptedException {
      if (!this.dirty.get() && var1 > 0L) {
         this.wait(var1);
      }

      if (!this.dirty.compareAndSet(true, false) && !var3) {
         return null;
      } else {
         StringBuilder var4 = new StringBuilder();
         int var5 = -1;
         int var6 = Math.min(this.cx, this.width - 1);
         int var7 = this.cy;
         var4.append("<div><pre class='term'>");

         for(int var8 = 0; var8 < this.height; ++var8) {
            int var9 = 0;

            for(int var10 = 0; var10 < this.width; ++var10) {
               long var11 = this.screen[var8][var10];
               int var13 = (int)(var11 & -1L);
               int var14 = (int)(var11 >> 32);
               if (var7 == var8 && var6 == var10 && this.vt100_mode_cursor) {
                  var14 = var14 & '\ufff0' | 12;
               }

               if (var14 != var5) {
                  if (var5 != -1) {
                     var4.append("</span>");
                  }

                  int var15 = var14 & 255;
                  int var16 = (var14 & '\uff00') >> 8;
                  boolean var17 = (var14 & 131072) != 0;
                  boolean var18 = this.vt100_mode_inverse;
                  if (var17 && !var18 || var18 && !var17) {
                     int var19 = var16;
                     var16 = var15;
                     var15 = var19;
                  }

                  if ((var14 & 262144) != 0) {
                     var16 = 12;
                  }

                  String var21;
                  if ((var14 & 65536) != 0) {
                     var21 = " ul";
                  } else {
                     var21 = "";
                  }

                  String var20;
                  if ((var14 & 524288) != 0) {
                     var20 = " b";
                  } else {
                     var20 = "";
                  }

                  var4.append("<span class='f").append(var16).append(" b").append(var15).append(var21).append(var20).append("'>");
                  var5 = var14;
               }

               switch(var13) {
               case 38:
                  var4.append("&amp;");
                  break;
               case 60:
                  var4.append("&lt;");
                  break;
               case 62:
                  var4.append("&gt;");
                  break;
               default:
                  var9 += this.utf8_charwidth(var13);
                  if (var9 <= this.width) {
                     var4.append((char)var13);
                  }
               }
            }

            var4.append("\n");
         }

         var4.append("</span></pre></div>");
         return var4.toString();
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();

      for(int var2 = 0; var2 < this.height; ++var2) {
         for(int var3 = 0; var3 < this.width; ++var3) {
            var1.appendCodePoint((int)(this.screen[var2][var3] & 4294967295L));
         }

         var1.append("\n");
      }

      return var1.toString();
   }

   private void lambda$write$0(int var1) {
      if (!this.vt100_write(var1) && !this.dumb_write(var1) && var1 <= 65535) {
         this.dumb_echo(var1);
      }

   }
}
