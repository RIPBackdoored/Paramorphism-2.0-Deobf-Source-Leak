package org.jline.builtins;

import java.util.Map;
import java.util.Set;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

public interface Completers$CompletionEnvironment {
   Map getCompletions();

   Set getCommands();

   String resolveCommand(String var1);

   String commandName(String var1);

   Object evaluate(LineReader var1, ParsedLine var2, String var3) throws Exception;
}
