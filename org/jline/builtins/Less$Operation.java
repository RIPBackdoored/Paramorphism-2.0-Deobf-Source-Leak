package org.jline.builtins;

public final class Less$Operation extends Enum {
   public static final Less$Operation HELP = new Less$Operation("HELP", 0);
   public static final Less$Operation EXIT = new Less$Operation("EXIT", 1);
   public static final Less$Operation FORWARD_ONE_LINE = new Less$Operation("FORWARD_ONE_LINE", 2);
   public static final Less$Operation BACKWARD_ONE_LINE = new Less$Operation("BACKWARD_ONE_LINE", 3);
   public static final Less$Operation FORWARD_ONE_WINDOW_OR_LINES = new Less$Operation("FORWARD_ONE_WINDOW_OR_LINES", 4);
   public static final Less$Operation BACKWARD_ONE_WINDOW_OR_LINES = new Less$Operation("BACKWARD_ONE_WINDOW_OR_LINES", 5);
   public static final Less$Operation FORWARD_ONE_WINDOW_AND_SET = new Less$Operation("FORWARD_ONE_WINDOW_AND_SET", 6);
   public static final Less$Operation BACKWARD_ONE_WINDOW_AND_SET = new Less$Operation("BACKWARD_ONE_WINDOW_AND_SET", 7);
   public static final Less$Operation FORWARD_ONE_WINDOW_NO_STOP = new Less$Operation("FORWARD_ONE_WINDOW_NO_STOP", 8);
   public static final Less$Operation FORWARD_HALF_WINDOW_AND_SET = new Less$Operation("FORWARD_HALF_WINDOW_AND_SET", 9);
   public static final Less$Operation BACKWARD_HALF_WINDOW_AND_SET = new Less$Operation("BACKWARD_HALF_WINDOW_AND_SET", 10);
   public static final Less$Operation LEFT_ONE_HALF_SCREEN = new Less$Operation("LEFT_ONE_HALF_SCREEN", 11);
   public static final Less$Operation RIGHT_ONE_HALF_SCREEN = new Less$Operation("RIGHT_ONE_HALF_SCREEN", 12);
   public static final Less$Operation FORWARD_FOREVER = new Less$Operation("FORWARD_FOREVER", 13);
   public static final Less$Operation REPAINT = new Less$Operation("REPAINT", 14);
   public static final Less$Operation REPAINT_AND_DISCARD = new Less$Operation("REPAINT_AND_DISCARD", 15);
   public static final Less$Operation REPEAT_SEARCH_FORWARD = new Less$Operation("REPEAT_SEARCH_FORWARD", 16);
   public static final Less$Operation REPEAT_SEARCH_BACKWARD = new Less$Operation("REPEAT_SEARCH_BACKWARD", 17);
   public static final Less$Operation REPEAT_SEARCH_FORWARD_SPAN_FILES = new Less$Operation("REPEAT_SEARCH_FORWARD_SPAN_FILES", 18);
   public static final Less$Operation REPEAT_SEARCH_BACKWARD_SPAN_FILES = new Less$Operation("REPEAT_SEARCH_BACKWARD_SPAN_FILES", 19);
   public static final Less$Operation UNDO_SEARCH = new Less$Operation("UNDO_SEARCH", 20);
   public static final Less$Operation GO_TO_FIRST_LINE_OR_N = new Less$Operation("GO_TO_FIRST_LINE_OR_N", 21);
   public static final Less$Operation GO_TO_LAST_LINE_OR_N = new Less$Operation("GO_TO_LAST_LINE_OR_N", 22);
   public static final Less$Operation GO_TO_PERCENT_OR_N = new Less$Operation("GO_TO_PERCENT_OR_N", 23);
   public static final Less$Operation GO_TO_NEXT_TAG = new Less$Operation("GO_TO_NEXT_TAG", 24);
   public static final Less$Operation GO_TO_PREVIOUS_TAG = new Less$Operation("GO_TO_PREVIOUS_TAG", 25);
   public static final Less$Operation FIND_CLOSE_BRACKET = new Less$Operation("FIND_CLOSE_BRACKET", 26);
   public static final Less$Operation FIND_OPEN_BRACKET = new Less$Operation("FIND_OPEN_BRACKET", 27);
   public static final Less$Operation OPT_PRINT_LINES = new Less$Operation("OPT_PRINT_LINES", 28);
   public static final Less$Operation OPT_CHOP_LONG_LINES = new Less$Operation("OPT_CHOP_LONG_LINES", 29);
   public static final Less$Operation OPT_QUIT_AT_FIRST_EOF = new Less$Operation("OPT_QUIT_AT_FIRST_EOF", 30);
   public static final Less$Operation OPT_QUIT_AT_SECOND_EOF = new Less$Operation("OPT_QUIT_AT_SECOND_EOF", 31);
   public static final Less$Operation OPT_QUIET = new Less$Operation("OPT_QUIET", 32);
   public static final Less$Operation OPT_VERY_QUIET = new Less$Operation("OPT_VERY_QUIET", 33);
   public static final Less$Operation OPT_IGNORE_CASE_COND = new Less$Operation("OPT_IGNORE_CASE_COND", 34);
   public static final Less$Operation OPT_IGNORE_CASE_ALWAYS = new Less$Operation("OPT_IGNORE_CASE_ALWAYS", 35);
   public static final Less$Operation NEXT_FILE = new Less$Operation("NEXT_FILE", 36);
   public static final Less$Operation PREV_FILE = new Less$Operation("PREV_FILE", 37);
   public static final Less$Operation CHAR = new Less$Operation("CHAR", 38);
   private static final Less$Operation[] $VALUES = new Less$Operation[]{HELP, EXIT, FORWARD_ONE_LINE, BACKWARD_ONE_LINE, FORWARD_ONE_WINDOW_OR_LINES, BACKWARD_ONE_WINDOW_OR_LINES, FORWARD_ONE_WINDOW_AND_SET, BACKWARD_ONE_WINDOW_AND_SET, FORWARD_ONE_WINDOW_NO_STOP, FORWARD_HALF_WINDOW_AND_SET, BACKWARD_HALF_WINDOW_AND_SET, LEFT_ONE_HALF_SCREEN, RIGHT_ONE_HALF_SCREEN, FORWARD_FOREVER, REPAINT, REPAINT_AND_DISCARD, REPEAT_SEARCH_FORWARD, REPEAT_SEARCH_BACKWARD, REPEAT_SEARCH_FORWARD_SPAN_FILES, REPEAT_SEARCH_BACKWARD_SPAN_FILES, UNDO_SEARCH, GO_TO_FIRST_LINE_OR_N, GO_TO_LAST_LINE_OR_N, GO_TO_PERCENT_OR_N, GO_TO_NEXT_TAG, GO_TO_PREVIOUS_TAG, FIND_CLOSE_BRACKET, FIND_OPEN_BRACKET, OPT_PRINT_LINES, OPT_CHOP_LONG_LINES, OPT_QUIT_AT_FIRST_EOF, OPT_QUIT_AT_SECOND_EOF, OPT_QUIET, OPT_VERY_QUIET, OPT_IGNORE_CASE_COND, OPT_IGNORE_CASE_ALWAYS, NEXT_FILE, PREV_FILE, CHAR};

   public static Less$Operation[] values() {
      return (Less$Operation[])$VALUES.clone();
   }

   public static Less$Operation valueOf(String var0) {
      return (Less$Operation)Enum.valueOf(Less$Operation.class, var0);
   }

   private Less$Operation(String var1, int var2) {
      super(var1, var2);
   }
}
