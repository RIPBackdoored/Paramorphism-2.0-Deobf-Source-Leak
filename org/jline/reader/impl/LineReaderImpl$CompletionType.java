package org.jline.reader.impl;

public final class LineReaderImpl$CompletionType extends Enum {
   public static final LineReaderImpl$CompletionType Expand = new LineReaderImpl$CompletionType("Expand", 0);
   public static final LineReaderImpl$CompletionType ExpandComplete = new LineReaderImpl$CompletionType("ExpandComplete", 1);
   public static final LineReaderImpl$CompletionType Complete = new LineReaderImpl$CompletionType("Complete", 2);
   public static final LineReaderImpl$CompletionType List = new LineReaderImpl$CompletionType("List", 3);
   private static final LineReaderImpl$CompletionType[] $VALUES = new LineReaderImpl$CompletionType[]{Expand, ExpandComplete, Complete, List};

   public static LineReaderImpl$CompletionType[] values() {
      return (LineReaderImpl$CompletionType[])$VALUES.clone();
   }

   public static LineReaderImpl$CompletionType valueOf(String var0) {
      return (LineReaderImpl$CompletionType)Enum.valueOf(LineReaderImpl$CompletionType.class, var0);
   }

   private LineReaderImpl$CompletionType(String var1, int var2) {
      super(var1, var2);
   }
}
