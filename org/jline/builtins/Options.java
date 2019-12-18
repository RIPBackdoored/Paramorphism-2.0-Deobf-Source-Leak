package org.jline.builtins;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Options {
   public static final String NL = System.getProperty("line.separator", "\n");
   private static final String regex = "(?x)\\s*(?:-([^-]))?(?:,?\\s*-(\\w))?(?:,?\\s*--(\\w[\\w-]*)(=\\w+)?)?(?:,?\\s*--(\\w[\\w-]*))?.*?(?:\\(default=(.*)\\))?\\s*";
   private static final int GROUP_SHORT_OPT_1 = 1;
   private static final int GROUP_SHORT_OPT_2 = 2;
   private static final int GROUP_LONG_OPT_1 = 3;
   private static final int GROUP_ARG_1 = 4;
   private static final int GROUP_LONG_OPT_2 = 5;
   private static final int GROUP_DEFAULT = 6;
   private static final Pattern parser = Pattern.compile("(?x)\\s*(?:-([^-]))?(?:,?\\s*-(\\w))?(?:,?\\s*--(\\w[\\w-]*)(=\\w+)?)?(?:,?\\s*--(\\w[\\w-]*))?.*?(?:\\(default=(.*)\\))?\\s*");
   private static final Pattern uname = Pattern.compile("^Usage:\\s+(\\w+)");
   private final Map unmodifiableOptSet;
   private final Map unmodifiableOptArg;
   private final Map optSet = new HashMap();
   private final Map optArg = new HashMap();
   private final Map optName = new HashMap();
   private final Map optAlias = new HashMap();
   private final List xargs = new ArrayList();
   private List args = null;
   private static final String UNKNOWN = "unknown";
   private String usageName = "unknown";
   private int usageIndex = 0;
   private final String[] spec;
   private final String[] gspec;
   private final String defOpts;
   private final String[] defArgs;
   private String error = null;
   private boolean optionsFirst = false;
   private boolean stopOnBadOption = false;

   public static Options compile(String[] var0) {
      return new Options(var0, (String[])null, (Options)null, System::getenv);
   }

   public static Options compile(String[] var0, Function var1) {
      return new Options(var0, (String[])null, (Options)null, var1);
   }

   public static Options compile(String var0) {
      return compile(var0.split("\\n"), System::getenv);
   }

   public static Options compile(String var0, Function var1) {
      return compile(var0.split("\\n"), var1);
   }

   public static Options compile(String[] var0, Options var1) {
      return new Options(var0, (String[])null, var1, System::getenv);
   }

   public static Options compile(String[] var0, String[] var1) {
      return new Options(var0, var1, (Options)null, System::getenv);
   }

   public Options setStopOnBadOption(boolean var1) {
      this.stopOnBadOption = var1;
      return this;
   }

   public Options setOptionsFirst(boolean var1) {
      this.optionsFirst = var1;
      return this;
   }

   public boolean isSet(String var1) {
      if (!this.optSet.containsKey(var1)) {
         throw new IllegalArgumentException("option not defined in spec: " + var1);
      } else {
         return (Boolean)this.optSet.get(var1);
      }
   }

   public Object getObject(String var1) {
      if (!this.optArg.containsKey(var1)) {
         throw new IllegalArgumentException("option not defined with argument: " + var1);
      } else {
         List var2 = this.getObjectList(var1);
         return var2.isEmpty() ? "" : var2.get(var2.size() - 1);
      }
   }

   public List getObjectList(String var1) {
      Object var3 = this.optArg.get(var1);
      if (var3 == null) {
         throw new IllegalArgumentException("option not defined with argument: " + var1);
      } else {
         Object var2;
         if (var3 instanceof String) {
            var2 = new ArrayList();
            if (!"".equals(var3)) {
               ((List)var2).add(var3);
            }
         } else {
            var2 = (List)var3;
         }

         return (List)var2;
      }
   }

   public List getList(String var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.getObjectList(var1).iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();

         try {
            var2.add((String)var4);
         } catch (ClassCastException var6) {
            throw new IllegalArgumentException("option not String: " + var1);
         }
      }

      return var2;
   }

   private void addArg(String var1, Object var2) {
      Object var4 = this.optArg.get(var1);
      Object var3;
      if (var4 instanceof String) {
         var3 = new ArrayList();
         this.optArg.put(var1, var3);
      } else {
         var3 = (List)var4;
      }

      ((List)var3).add(var2);
   }

   public String get(String var1) {
      String var10000;
      try {
         var10000 = (String)this.getObject(var1);
      } catch (ClassCastException var3) {
         throw new IllegalArgumentException("option not String: " + var1);
      }

      return var10000;
   }

   public int getNumber(String var1) {
      String var2 = this.get(var1);

      byte var10000;
      try {
         if (var2 != null) {
            int var5 = Integer.parseInt(var2);
            return var5;
         }

         var10000 = 0;
      } catch (NumberFormatException var4) {
         throw new IllegalArgumentException("option '" + var1 + "' not Number: " + var2);
      }

      return var10000;
   }

   public List argObjects() {
      return this.xargs;
   }

   public List args() {
      if (this.args == null) {
         this.args = new ArrayList();
         Iterator var1 = this.xargs.iterator();

         while(var1.hasNext()) {
            Object var2 = var1.next();
            this.args.add(var2 == null ? "null" : var2.toString());
         }
      }

      return this.args;
   }

   public void usage(PrintStream var1) {
      var1.print(this.usage());
   }

   public String usage() {
      StringBuilder var1 = new StringBuilder();
      int var2 = 0;
      if (this.error != null) {
         var1.append(this.error);
         var1.append(NL);
         var2 = this.usageIndex;
      }

      for(int var3 = var2; var3 < this.spec.length; ++var3) {
         var1.append(this.spec[var3]);
         var1.append(NL);
      }

      return var1.toString();
   }

   public IllegalArgumentException usageError(String var1) {
      this.error = this.usageName + ": " + var1;
      return new IllegalArgumentException(this.error);
   }

   private Options(String[] var1, String[] var2, Options var3, Function var4) {
      super();
      this.gspec = var2;
      if (var2 == null && var3 == null) {
         this.spec = var1;
      } else {
         ArrayList var5 = new ArrayList();
         var5.addAll(Arrays.asList(var1));
         var5.addAll(Arrays.asList(var2 != null ? var2 : var3.gspec));
         this.spec = (String[])var5.toArray(new String[var5.size()]);
      }

      HashMap var9 = new HashMap();
      HashMap var6 = new HashMap();
      this.parseSpec(var9, var6);
      if (var3 != null) {
         Iterator var7 = var3.optSet.entrySet().iterator();

         Entry var8;
         while(var7.hasNext()) {
            var8 = (Entry)var7.next();
            if ((Boolean)var8.getValue()) {
               var9.put(var8.getKey(), true);
            }
         }

         var7 = var3.optArg.entrySet().iterator();

         while(var7.hasNext()) {
            var8 = (Entry)var7.next();
            if (!var8.getValue().equals("")) {
               var6.put(var8.getKey(), var8.getValue());
            }
         }

         var3.reset();
      }

      this.unmodifiableOptSet = Collections.unmodifiableMap(var9);
      this.unmodifiableOptArg = Collections.unmodifiableMap(var6);
      this.defOpts = var4 != null ? (String)var4.apply(this.usageName.toUpperCase() + "_OPTS") : null;
      this.defArgs = this.defOpts != null ? this.defOpts.split("\\s+") : new String[0];
   }

   private void parseSpec(Map var1, Map var2) {
      int var3 = 0;
      String[] var4 = this.spec;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String var7 = var4[var6];
         Matcher var8 = parser.matcher(var7);
         if (var8.matches()) {
            String var9 = var8.group(3);
            String var10 = var9 != null ? var9 : var8.group(1);
            if (var10 != null) {
               if (var1.containsKey(var10)) {
                  throw new IllegalArgumentException("duplicate option in spec: --" + var10);
               }

               var1.put(var10, false);
            }

            String var11 = var8.group(6) != null ? var8.group(6) : "";
            if (var8.group(4) != null) {
               var2.put(var9, var11);
            }

            String var12 = var8.group(5);
            if (var12 != null) {
               this.optAlias.put(var12, var9);
               var1.put(var12, false);
               if (var8.group(4) != null) {
                  var2.put(var12, "");
               }
            }

            for(int var13 = 0; var13 < 2; ++var13) {
               String var14 = var8.group(var13 == 0 ? 1 : 2);
               if (var14 != null) {
                  if (this.optName.containsKey(var14)) {
                     throw new IllegalArgumentException("duplicate option in spec: -" + var14);
                  }

                  this.optName.put(var14, var10);
               }
            }
         }

         if (Objects.equals(this.usageName, "unknown")) {
            Matcher var15 = uname.matcher(var7);
            if (var15.find()) {
               this.usageName = var15.group(1);
               this.usageIndex = var3;
            }
         }

         ++var3;
      }

   }

   private void reset() {
      this.optSet.clear();
      this.optSet.putAll(this.unmodifiableOptSet);
      this.optArg.clear();
      this.optArg.putAll(this.unmodifiableOptArg);
      this.xargs.clear();
      this.args = null;
      this.error = null;
   }

   public Options parse(Object[] var1) {
      return this.parse(var1, false);
   }

   public Options parse(List var1) {
      return this.parse(var1, false);
   }

   public Options parse(Object[] var1, boolean var2) {
      if (null == var1) {
         throw new IllegalArgumentException("argv is null");
      } else {
         return this.parse(Arrays.asList(var1), var2);
      }
   }

   public Options parse(List var1, boolean var2) {
      this.reset();
      ArrayList var3 = new ArrayList();
      var3.addAll(Arrays.asList(this.defArgs));
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         if (var2) {
            var2 = false;
            this.usageName = var5.toString();
         } else {
            var3.add(var5);
         }
      }

      String var16 = null;
      String var17 = null;
      boolean var6 = false;
      Iterator var7 = var3.iterator();

      while(true) {
         while(var7.hasNext()) {
            Object var8 = var7.next();
            String var9 = var8 == null ? "null" : var8.toString();
            if (var6) {
               this.xargs.add(var8);
            } else if (var16 != null) {
               this.addArg(var16, var8);
               var16 = null;
               var17 = null;
            } else if (var9.startsWith("-") && (var9.length() <= 1 || !Character.isDigit(var9.charAt(1))) && !"-".equals(var8)) {
               if (var9.equals("--")) {
                  var6 = true;
               } else {
                  int var10;
                  String var11;
                  String var12;
                  if (var9.startsWith("--")) {
                     var10 = var9.indexOf("=");
                     var11 = var10 == -1 ? null : var9.substring(var10 + 1);
                     var12 = var9.substring(2, var10 == -1 ? var9.length() : var10);
                     ArrayList var20 = new ArrayList();
                     if (this.optSet.containsKey(var12)) {
                        var20.add(var12);
                     } else {
                        Iterator var14 = this.optSet.keySet().iterator();

                        while(var14.hasNext()) {
                           String var15 = (String)var14.next();
                           if (var15.startsWith(var12)) {
                              var20.add(var15);
                           }
                        }
                     }

                     switch(var20.size()) {
                     case 0:
                        if (!this.stopOnBadOption) {
                           throw this.usageError("invalid option '--" + var12 + "'");
                        }

                        var6 = true;
                        this.xargs.add(var8);
                        break;
                     case 1:
                        var12 = (String)var20.get(0);
                        this.optSet.put(var12, true);
                        if (this.optArg.containsKey(var12)) {
                           if (var11 != null) {
                              this.addArg(var12, var11);
                           } else {
                              var16 = var12;
                           }
                        } else if (var11 != null) {
                           throw this.usageError("option '--" + var12 + "' doesn't allow an argument");
                        }
                        break;
                     default:
                        throw this.usageError("option '--" + var12 + "' is ambiguous: " + var20);
                     }
                  } else {
                     for(var10 = 1; var10 < var9.length(); ++var10) {
                        var11 = String.valueOf(var9.charAt(var10));
                        if (this.optName.containsKey(var11)) {
                           var12 = (String)this.optName.get(var11);
                           this.optSet.put(var12, true);
                           if (this.optArg.containsKey(var12)) {
                              int var13 = var10 + 1;
                              if (var13 < var9.length()) {
                                 this.addArg(var12, var9.substring(var13));
                              } else {
                                 var17 = var11;
                                 var16 = var12;
                              }
                              break;
                           }
                        } else {
                           if (!this.stopOnBadOption) {
                              throw this.usageError("invalid option '" + var11 + "'");
                           }

                           this.xargs.add("-" + var11);
                           var6 = true;
                        }
                     }
                  }
               }
            } else {
               if (this.optionsFirst) {
                  var6 = true;
               }

               this.xargs.add(var8);
            }
         }

         if (var16 != null) {
            String var18 = var17 != null ? var17 : "--" + var16;
            throw this.usageError("option '" + var18 + "' requires an argument");
         }

         var7 = this.optAlias.entrySet().iterator();

         while(var7.hasNext()) {
            Entry var19 = (Entry)var7.next();
            if ((Boolean)this.optSet.get(var19.getKey())) {
               this.optSet.put(var19.getValue(), true);
               if (this.optArg.containsKey(var19.getKey())) {
                  this.optArg.put(var19.getValue(), this.optArg.get(var19.getKey()));
               }
            }

            this.optSet.remove(var19.getKey());
            this.optArg.remove(var19.getKey());
         }

         return this;
      }
   }

   public String toString() {
      return "isSet" + this.optSet + "\nArg" + this.optArg + "\nargs" + this.xargs;
   }
}
