package org.jline.builtins;

public final class Nano$Operation extends Enum {
   public static final Nano$Operation DO_LOWER_CASE = new Nano$Operation("DO_LOWER_CASE", 0);
   public static final Nano$Operation QUIT = new Nano$Operation("QUIT", 1);
   public static final Nano$Operation WRITE = new Nano$Operation("WRITE", 2);
   public static final Nano$Operation READ = new Nano$Operation("READ", 3);
   public static final Nano$Operation GOTO = new Nano$Operation("GOTO", 4);
   public static final Nano$Operation FIND = new Nano$Operation("FIND", 5);
   public static final Nano$Operation WRAP = new Nano$Operation("WRAP", 6);
   public static final Nano$Operation NUMBERS = new Nano$Operation("NUMBERS", 7);
   public static final Nano$Operation SMOOTH_SCROLLING = new Nano$Operation("SMOOTH_SCROLLING", 8);
   public static final Nano$Operation MOUSE_SUPPORT = new Nano$Operation("MOUSE_SUPPORT", 9);
   public static final Nano$Operation ONE_MORE_LINE = new Nano$Operation("ONE_MORE_LINE", 10);
   public static final Nano$Operation CLEAR_SCREEN = new Nano$Operation("CLEAR_SCREEN", 11);
   public static final Nano$Operation UP = new Nano$Operation("UP", 12);
   public static final Nano$Operation DOWN = new Nano$Operation("DOWN", 13);
   public static final Nano$Operation LEFT = new Nano$Operation("LEFT", 14);
   public static final Nano$Operation RIGHT = new Nano$Operation("RIGHT", 15);
   public static final Nano$Operation INSERT = new Nano$Operation("INSERT", 16);
   public static final Nano$Operation BACKSPACE = new Nano$Operation("BACKSPACE", 17);
   public static final Nano$Operation NEXT_BUFFER = new Nano$Operation("NEXT_BUFFER", 18);
   public static final Nano$Operation PREV_BUFFER = new Nano$Operation("PREV_BUFFER", 19);
   public static final Nano$Operation HELP = new Nano$Operation("HELP", 20);
   public static final Nano$Operation NEXT_PAGE = new Nano$Operation("NEXT_PAGE", 21);
   public static final Nano$Operation PREV_PAGE = new Nano$Operation("PREV_PAGE", 22);
   public static final Nano$Operation SCROLL_UP = new Nano$Operation("SCROLL_UP", 23);
   public static final Nano$Operation SCROLL_DOWN = new Nano$Operation("SCROLL_DOWN", 24);
   public static final Nano$Operation NEXT_WORD = new Nano$Operation("NEXT_WORD", 25);
   public static final Nano$Operation PREV_WORD = new Nano$Operation("PREV_WORD", 26);
   public static final Nano$Operation BEGINNING_OF_LINE = new Nano$Operation("BEGINNING_OF_LINE", 27);
   public static final Nano$Operation END_OF_LINE = new Nano$Operation("END_OF_LINE", 28);
   public static final Nano$Operation FIRST_LINE = new Nano$Operation("FIRST_LINE", 29);
   public static final Nano$Operation LAST_LINE = new Nano$Operation("LAST_LINE", 30);
   public static final Nano$Operation CUR_POS = new Nano$Operation("CUR_POS", 31);
   public static final Nano$Operation CASE_SENSITIVE = new Nano$Operation("CASE_SENSITIVE", 32);
   public static final Nano$Operation BACKWARDS = new Nano$Operation("BACKWARDS", 33);
   public static final Nano$Operation REGEXP = new Nano$Operation("REGEXP", 34);
   public static final Nano$Operation ACCEPT = new Nano$Operation("ACCEPT", 35);
   public static final Nano$Operation CANCEL = new Nano$Operation("CANCEL", 36);
   public static final Nano$Operation SEARCH = new Nano$Operation("SEARCH", 37);
   public static final Nano$Operation MAC_FORMAT = new Nano$Operation("MAC_FORMAT", 38);
   public static final Nano$Operation DOS_FORMAT = new Nano$Operation("DOS_FORMAT", 39);
   public static final Nano$Operation APPEND_MODE = new Nano$Operation("APPEND_MODE", 40);
   public static final Nano$Operation PREPEND_MODE = new Nano$Operation("PREPEND_MODE", 41);
   public static final Nano$Operation BACKUP = new Nano$Operation("BACKUP", 42);
   public static final Nano$Operation TO_FILES = new Nano$Operation("TO_FILES", 43);
   public static final Nano$Operation YES = new Nano$Operation("YES", 44);
   public static final Nano$Operation NO = new Nano$Operation("NO", 45);
   public static final Nano$Operation NEW_BUFFER = new Nano$Operation("NEW_BUFFER", 46);
   public static final Nano$Operation EXECUTE = new Nano$Operation("EXECUTE", 47);
   public static final Nano$Operation NEXT_SEARCH = new Nano$Operation("NEXT_SEARCH", 48);
   public static final Nano$Operation MATCHING = new Nano$Operation("MATCHING", 49);
   public static final Nano$Operation VERBATIM = new Nano$Operation("VERBATIM", 50);
   public static final Nano$Operation DELETE = new Nano$Operation("DELETE", 51);
   public static final Nano$Operation JUSTIFY_PARAGRAPH = new Nano$Operation("JUSTIFY_PARAGRAPH", 52);
   public static final Nano$Operation TO_SPELL = new Nano$Operation("TO_SPELL", 53);
   public static final Nano$Operation CUT = new Nano$Operation("CUT", 54);
   public static final Nano$Operation REPLACE = new Nano$Operation("REPLACE", 55);
   public static final Nano$Operation MARK = new Nano$Operation("MARK", 56);
   public static final Nano$Operation COPY = new Nano$Operation("COPY", 57);
   public static final Nano$Operation INDENT = new Nano$Operation("INDENT", 58);
   public static final Nano$Operation UNINDENT = new Nano$Operation("UNINDENT", 59);
   public static final Nano$Operation BEGINNING_OF_PARAGRAPH = new Nano$Operation("BEGINNING_OF_PARAGRAPH", 60);
   public static final Nano$Operation END_OF_PARAGRAPH = new Nano$Operation("END_OF_PARAGRAPH", 61);
   public static final Nano$Operation CUT_TO_END = new Nano$Operation("CUT_TO_END", 62);
   public static final Nano$Operation JUSTIFY_FILE = new Nano$Operation("JUSTIFY_FILE", 63);
   public static final Nano$Operation COUNT = new Nano$Operation("COUNT", 64);
   public static final Nano$Operation CONSTANT_CURSOR = new Nano$Operation("CONSTANT_CURSOR", 65);
   public static final Nano$Operation WHITESPACE = new Nano$Operation("WHITESPACE", 66);
   public static final Nano$Operation HIGHLIGHT = new Nano$Operation("HIGHLIGHT", 67);
   public static final Nano$Operation SMART_HOME_KEY = new Nano$Operation("SMART_HOME_KEY", 68);
   public static final Nano$Operation AUTO_INDENT = new Nano$Operation("AUTO_INDENT", 69);
   public static final Nano$Operation CUT_TO_END_TOGGLE = new Nano$Operation("CUT_TO_END_TOGGLE", 70);
   public static final Nano$Operation TABS_TO_SPACE = new Nano$Operation("TABS_TO_SPACE", 71);
   public static final Nano$Operation UNCUT = new Nano$Operation("UNCUT", 72);
   public static final Nano$Operation MOUSE_EVENT = new Nano$Operation("MOUSE_EVENT", 73);
   private static final Nano$Operation[] $VALUES = new Nano$Operation[]{DO_LOWER_CASE, QUIT, WRITE, READ, GOTO, FIND, WRAP, NUMBERS, SMOOTH_SCROLLING, MOUSE_SUPPORT, ONE_MORE_LINE, CLEAR_SCREEN, UP, DOWN, LEFT, RIGHT, INSERT, BACKSPACE, NEXT_BUFFER, PREV_BUFFER, HELP, NEXT_PAGE, PREV_PAGE, SCROLL_UP, SCROLL_DOWN, NEXT_WORD, PREV_WORD, BEGINNING_OF_LINE, END_OF_LINE, FIRST_LINE, LAST_LINE, CUR_POS, CASE_SENSITIVE, BACKWARDS, REGEXP, ACCEPT, CANCEL, SEARCH, MAC_FORMAT, DOS_FORMAT, APPEND_MODE, PREPEND_MODE, BACKUP, TO_FILES, YES, NO, NEW_BUFFER, EXECUTE, NEXT_SEARCH, MATCHING, VERBATIM, DELETE, JUSTIFY_PARAGRAPH, TO_SPELL, CUT, REPLACE, MARK, COPY, INDENT, UNINDENT, BEGINNING_OF_PARAGRAPH, END_OF_PARAGRAPH, CUT_TO_END, JUSTIFY_FILE, COUNT, CONSTANT_CURSOR, WHITESPACE, HIGHLIGHT, SMART_HOME_KEY, AUTO_INDENT, CUT_TO_END_TOGGLE, TABS_TO_SPACE, UNCUT, MOUSE_EVENT};

   public static Nano$Operation[] values() {
      return (Nano$Operation[])$VALUES.clone();
   }

   public static Nano$Operation valueOf(String var0) {
      return (Nano$Operation)Enum.valueOf(Nano$Operation.class, var0);
   }

   private Nano$Operation(String var1, int var2) {
      super(var1, var2);
   }
}
