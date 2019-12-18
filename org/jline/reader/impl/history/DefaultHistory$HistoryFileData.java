package org.jline.reader.impl.history;

class DefaultHistory$HistoryFileData {
   private int lastLoaded;
   private int entriesInFile;
   final DefaultHistory this$0;

   public DefaultHistory$HistoryFileData(DefaultHistory var1) {
      super();
      this.this$0 = var1;
      this.lastLoaded = 0;
      this.entriesInFile = 0;
   }

   public DefaultHistory$HistoryFileData(DefaultHistory var1, int var2, int var3) {
      super();
      this.this$0 = var1;
      this.lastLoaded = 0;
      this.entriesInFile = 0;
      this.lastLoaded = var2;
      this.entriesInFile = var3;
   }

   public int getLastLoaded() {
      return this.lastLoaded;
   }

   public void setLastLoaded(int var1) {
      this.lastLoaded = var1;
   }

   public void decLastLoaded() {
      --this.lastLoaded;
      if (this.lastLoaded < 0) {
         this.lastLoaded = 0;
      }

   }

   public int getEntriesInFile() {
      return this.entriesInFile;
   }

   public void setEntriesInFile(int var1) {
      this.entriesInFile = var1;
   }

   public void incEntriesInFile(int var1) {
      this.entriesInFile += var1;
   }
}
