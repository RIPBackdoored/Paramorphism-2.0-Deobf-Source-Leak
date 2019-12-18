package org.jline.builtins;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

public class Completers$Completer implements Completer {
   private final Completers$CompletionEnvironment environment;

   public Completers$Completer(Completers$CompletionEnvironment var1) {
      super();
      this.environment = var1;
   }

   public void complete(LineReader var1, ParsedLine var2, List var3) {
      if (var2.wordIndex() == 0) {
         this.completeCommand(var3);
      } else {
         this.tryCompleteArguments(var1, var2, var3);
      }

   }

   protected void tryCompleteArguments(LineReader var1, ParsedLine var2, List var3) {
      String var4 = (String)var2.words().get(0);
      String var5 = this.environment.resolveCommand(var4);
      Map var6 = this.environment.getCompletions();
      if (var6 != null) {
         List var7 = (List)var6.get(var5);
         if (var7 != null) {
            this.completeCommandArguments(var1, var2, var3, var7);
         }
      }

   }

   protected void completeCommandArguments(LineReader var1, ParsedLine var2, List var3, List var4) {
      Iterator var5 = var4.iterator();

      while(true) {
         while(var5.hasNext()) {
            Completers$CompletionData var6 = (Completers$CompletionData)var5.next();
            boolean var7 = var2.word().startsWith("-");
            String var8 = var2.wordIndex() >= 2 && ((String)var2.words().get(var2.wordIndex() - 1)).startsWith("-") ? (String)var2.words().get(var2.wordIndex() - 1) : null;
            String var9 = UUID.randomUUID().toString();
            boolean var10 = true;
            Object var11;
            if (var6.condition != null) {
               var11 = Boolean.FALSE;

               try {
                  var11 = this.environment.evaluate(var1, var2, var6.condition);
               } catch (Throwable var17) {
                  var17.getCause();
               }

               var10 = this.isTrue(var11);
            }

            if (var10 && var7 && var6.options != null) {
               Iterator var21 = var6.options.iterator();

               while(var21.hasNext()) {
                  String var19 = (String)var21.next();
                  var3.add(new Candidate(var19, var19, "options", var6.description, (String)null, var9, true));
               }
            } else {
               Iterator var12;
               Object var13;
               if (!var7 && var8 != null && var6.argument != null && var6.options != null && var6.options.contains(var8)) {
                  var11 = null;

                  try {
                     var11 = this.environment.evaluate(var1, var2, var6.argument);
                  } catch (Throwable var16) {
                  }

                  if (var11 instanceof Candidate) {
                     var3.add((Candidate)var11);
                  } else if (var11 instanceof String) {
                     var3.add(new Candidate((String)var11, (String)var11, (String)null, (String)null, (String)null, (String)null, true));
                  } else if (var11 instanceof Collection) {
                     var12 = ((Collection)var11).iterator();

                     while(var12.hasNext()) {
                        var13 = var12.next();
                        if (var13 instanceof Candidate) {
                           var3.add((Candidate)var13);
                        } else if (var13 instanceof String) {
                           var3.add(new Candidate((String)var13, (String)var13, (String)null, (String)null, (String)null, (String)null, true));
                        }
                     }
                  } else if (var11 != null && var11.getClass().isArray()) {
                     int var18 = 0;

                     for(int var20 = Array.getLength(var11); var18 < var20; ++var18) {
                        Object var14 = Array.get(var11, var18);
                        if (var14 instanceof Candidate) {
                           var3.add((Candidate)var14);
                        } else if (var14 instanceof String) {
                           var3.add(new Candidate((String)var14, (String)var14, (String)null, (String)null, (String)null, (String)null, true));
                        }
                     }
                  }
               } else if (!var7 && var6.argument != null) {
                  var11 = null;

                  try {
                     var11 = this.environment.evaluate(var1, var2, var6.argument);
                  } catch (Throwable var15) {
                  }

                  if (var11 instanceof Candidate) {
                     var3.add((Candidate)var11);
                  } else if (var11 instanceof String) {
                     var3.add(new Candidate((String)var11, (String)var11, (String)null, var6.description, (String)null, (String)null, true));
                  } else if (var11 instanceof Collection) {
                     var12 = ((Collection)var11).iterator();

                     while(var12.hasNext()) {
                        var13 = var12.next();
                        if (var13 instanceof Candidate) {
                           var3.add((Candidate)var13);
                        } else if (var13 instanceof String) {
                           var3.add(new Candidate((String)var13, (String)var13, (String)null, var6.description, (String)null, (String)null, true));
                        }
                     }
                  }
               }
            }
         }

         return;
      }
   }

   protected void completeCommand(List var1) {
      Set var2 = this.environment.getCommands();
      Iterator var3 = var2.iterator();

      while(true) {
         String var4;
         String var5;
         boolean var6;
         do {
            if (!var3.hasNext()) {
               return;
            }

            var4 = (String)var3.next();
            var5 = this.environment.commandName(var4);
            var6 = var4.equals(this.environment.resolveCommand(var5));
         } while(var5.startsWith("_"));

         String var7 = null;
         Map var8 = this.environment.getCompletions();
         if (var8 != null) {
            List var9 = (List)var8.get(var4);
            if (var9 != null) {
               Iterator var10 = var9.iterator();

               while(var10.hasNext()) {
                  Completers$CompletionData var11 = (Completers$CompletionData)var10.next();
                  if (var11.description != null && var11.options == null && var11.argument == null && var11.condition == null) {
                     var7 = var11.description;
                  }
               }
            }
         }

         String var12 = UUID.randomUUID().toString();
         if (var7 != null) {
            var1.add(new Candidate(var4, var4, (String)null, var7, (String)null, var12, true));
            if (var6) {
               var1.add(new Candidate(var5, var5, (String)null, var7, (String)null, var12, true));
            }
         } else {
            var1.add(new Candidate(var4, var4, (String)null, (String)null, (String)null, var12, true));
            if (var6) {
               var1.add(new Candidate(var5, var5, (String)null, (String)null, (String)null, var12, true));
            }
         }
      }
   }

   private boolean isTrue(Object var1) {
      if (var1 == null) {
         return false;
      } else if (var1 instanceof Boolean) {
         return (Boolean)var1;
      } else if (var1 instanceof Number && 0 == ((Number)var1).intValue()) {
         return false;
      } else {
         return !"".equals(var1) && !"0".equals(var1);
      }
   }
}
