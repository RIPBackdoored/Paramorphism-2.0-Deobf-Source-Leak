package org.jline.reader.impl.completer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReader$Option;
import org.jline.reader.ParsedLine;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

/** @deprecated */
@Deprecated
public class FileNameCompleter implements Completer {
   static final boolean $assertionsDisabled = !FileNameCompleter.class.desiredAssertionStatus();

   public FileNameCompleter() {
      super();
   }

   public void complete(LineReader var1, ParsedLine var2, List var3) {
      if (!$assertionsDisabled && var2 == null) {
         throw new AssertionError();
      } else if (!$assertionsDisabled && var3 == null) {
         throw new AssertionError();
      } else {
         String var4 = var2.word().substring(0, var2.wordCursor());
         String var7 = this.getUserDir().getFileSystem().getSeparator();
         int var8 = var4.lastIndexOf(var7);
         Path var5;
         String var6;
         if (var8 >= 0) {
            var6 = var4.substring(0, var8 + 1);
            if (var6.startsWith("~")) {
               if (var6.startsWith("~" + var7)) {
                  var5 = this.getUserHome().resolve(var6.substring(2));
               } else {
                  var5 = this.getUserHome().getParent().resolve(var6.substring(1));
               }
            } else {
               var5 = this.getUserDir().resolve(var6);
            }
         } else {
            var6 = "";
            var5 = this.getUserDir();
         }

         try {
            Files.newDirectoryStream(var5, this::accept).forEach(this::lambda$complete$0);
         } catch (IOException var10) {
         }

      }
   }

   protected boolean accept(Path var1) {
      boolean var10000;
      try {
         var10000 = !Files.isHidden(var1);
      } catch (IOException var3) {
         return false;
      }

      return var10000;
   }

   protected Path getUserDir() {
      return Paths.get(System.getProperty("user.dir"));
   }

   protected Path getUserHome() {
      return Paths.get(System.getProperty("user.home"));
   }

   protected String getDisplay(Terminal var1, Path var2) {
      String var3 = var2.getFileName().toString();
      AttributedStringBuilder var4;
      if (Files.isDirectory(var2, new LinkOption[0])) {
         var4 = new AttributedStringBuilder();
         var4.styled((AttributedStyle)AttributedStyle.BOLD.foreground(1), (CharSequence)var3);
         var4.append((CharSequence)"/");
         var3 = var4.toAnsi(var1);
      } else if (Files.isSymbolicLink(var2)) {
         var4 = new AttributedStringBuilder();
         var4.styled((AttributedStyle)AttributedStyle.BOLD.foreground(1), (CharSequence)var3);
         var4.append((CharSequence)"@");
         var3 = var4.toAnsi(var1);
      }

      return var3;
   }

   private void lambda$complete$0(String var1, List var2, LineReader var3, String var4, Path var5) {
      String var6 = var1 + var5.getFileName().toString();
      if (Files.isDirectory(var5, new LinkOption[0])) {
         var2.add(new Candidate(var6 + (var3.isSet(LineReader$Option.AUTO_PARAM_SLASH) ? var4 : ""), this.getDisplay(var3.getTerminal(), var5), (String)null, (String)null, var3.isSet(LineReader$Option.AUTO_REMOVE_SLASH) ? var4 : null, (String)null, false));
      } else {
         var2.add(new Candidate(var6, this.getDisplay(var3.getTerminal(), var5), (String)null, (String)null, (String)null, (String)null, true));
      }

   }
}
