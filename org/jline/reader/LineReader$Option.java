package org.jline.reader;

public final class LineReader$Option extends Enum {
   public static final LineReader$Option COMPLETE_IN_WORD = new LineReader$Option("COMPLETE_IN_WORD", 0);
   public static final LineReader$Option DISABLE_EVENT_EXPANSION = new LineReader$Option("DISABLE_EVENT_EXPANSION", 1);
   public static final LineReader$Option HISTORY_VERIFY = new LineReader$Option("HISTORY_VERIFY", 2);
   public static final LineReader$Option HISTORY_IGNORE_SPACE = new LineReader$Option("HISTORY_IGNORE_SPACE", 3, true);
   public static final LineReader$Option HISTORY_IGNORE_DUPS = new LineReader$Option("HISTORY_IGNORE_DUPS", 4, true);
   public static final LineReader$Option HISTORY_REDUCE_BLANKS = new LineReader$Option("HISTORY_REDUCE_BLANKS", 5, true);
   public static final LineReader$Option HISTORY_BEEP = new LineReader$Option("HISTORY_BEEP", 6, true);
   public static final LineReader$Option HISTORY_INCREMENTAL = new LineReader$Option("HISTORY_INCREMENTAL", 7, true);
   public static final LineReader$Option HISTORY_TIMESTAMPED = new LineReader$Option("HISTORY_TIMESTAMPED", 8, true);
   public static final LineReader$Option AUTO_GROUP = new LineReader$Option("AUTO_GROUP", 9, true);
   public static final LineReader$Option AUTO_MENU = new LineReader$Option("AUTO_MENU", 10, true);
   public static final LineReader$Option AUTO_LIST = new LineReader$Option("AUTO_LIST", 11, true);
   public static final LineReader$Option RECOGNIZE_EXACT = new LineReader$Option("RECOGNIZE_EXACT", 12);
   public static final LineReader$Option GROUP = new LineReader$Option("GROUP", 13, true);
   public static final LineReader$Option CASE_INSENSITIVE = new LineReader$Option("CASE_INSENSITIVE", 14);
   public static final LineReader$Option LIST_AMBIGUOUS = new LineReader$Option("LIST_AMBIGUOUS", 15);
   public static final LineReader$Option LIST_PACKED = new LineReader$Option("LIST_PACKED", 16);
   public static final LineReader$Option LIST_ROWS_FIRST = new LineReader$Option("LIST_ROWS_FIRST", 17);
   public static final LineReader$Option GLOB_COMPLETE = new LineReader$Option("GLOB_COMPLETE", 18);
   public static final LineReader$Option MENU_COMPLETE = new LineReader$Option("MENU_COMPLETE", 19);
   public static final LineReader$Option AUTO_FRESH_LINE = new LineReader$Option("AUTO_FRESH_LINE", 20);
   public static final LineReader$Option DELAY_LINE_WRAP = new LineReader$Option("DELAY_LINE_WRAP", 21);
   public static final LineReader$Option AUTO_PARAM_SLASH = new LineReader$Option("AUTO_PARAM_SLASH", 22, true);
   public static final LineReader$Option AUTO_REMOVE_SLASH = new LineReader$Option("AUTO_REMOVE_SLASH", 23, true);
   public static final LineReader$Option INSERT_TAB = new LineReader$Option("INSERT_TAB", 24);
   public static final LineReader$Option MOUSE = new LineReader$Option("MOUSE", 25);
   public static final LineReader$Option DISABLE_HIGHLIGHTER = new LineReader$Option("DISABLE_HIGHLIGHTER", 26);
   public static final LineReader$Option BRACKETED_PASTE = new LineReader$Option("BRACKETED_PASTE", 27, true);
   public static final LineReader$Option ERASE_LINE_ON_FINISH = new LineReader$Option("ERASE_LINE_ON_FINISH", 28);
   public static final LineReader$Option CASE_INSENSITIVE_SEARCH = new LineReader$Option("CASE_INSENSITIVE_SEARCH", 29);
   private final boolean def;
   private static final LineReader$Option[] $VALUES = new LineReader$Option[]{COMPLETE_IN_WORD, DISABLE_EVENT_EXPANSION, HISTORY_VERIFY, HISTORY_IGNORE_SPACE, HISTORY_IGNORE_DUPS, HISTORY_REDUCE_BLANKS, HISTORY_BEEP, HISTORY_INCREMENTAL, HISTORY_TIMESTAMPED, AUTO_GROUP, AUTO_MENU, AUTO_LIST, RECOGNIZE_EXACT, GROUP, CASE_INSENSITIVE, LIST_AMBIGUOUS, LIST_PACKED, LIST_ROWS_FIRST, GLOB_COMPLETE, MENU_COMPLETE, AUTO_FRESH_LINE, DELAY_LINE_WRAP, AUTO_PARAM_SLASH, AUTO_REMOVE_SLASH, INSERT_TAB, MOUSE, DISABLE_HIGHLIGHTER, BRACKETED_PASTE, ERASE_LINE_ON_FINISH, CASE_INSENSITIVE_SEARCH};

   public static LineReader$Option[] values() {
      return (LineReader$Option[])$VALUES.clone();
   }

   public static LineReader$Option valueOf(String var0) {
      return (LineReader$Option)Enum.valueOf(LineReader$Option.class, var0);
   }

   private LineReader$Option(String var1, int var2) {
      this(var1, var2, false);
   }

   private LineReader$Option(String var1, int var2, boolean var3) {
      super(var1, var2);
      this.def = var3;
   }

   public boolean isDef() {
      return this.def;
   }
}
