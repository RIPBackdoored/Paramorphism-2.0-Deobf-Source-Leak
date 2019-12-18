package org.jline.utils;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class InfoCmp {
   private static final Map CAPS = new HashMap();

   private InfoCmp() {
      super();
   }

   public static Map getCapabilitiesByName() {
      LinkedHashMap var0 = new LinkedHashMap();

      LinkedHashMap var5;
      try {
         InputStream var1 = InfoCmp.class.getResourceAsStream("capabilities.txt");
         Throwable var2 = null;
         boolean var20 = false;

         try {
            var20 = true;
            BufferedReader var3 = new BufferedReader(new InputStreamReader(var1, StandardCharsets.UTF_8));
            Throwable var4 = null;
            boolean var30 = false;

            try {
               var30 = true;
               var3.lines().map(String::trim).filter(InfoCmp::lambda$getCapabilitiesByName$0).filter(InfoCmp::lambda$getCapabilitiesByName$1).forEach(InfoCmp::lambda$getCapabilitiesByName$2);
               var5 = var0;
               var30 = false;
            } catch (Throwable var35) {
               var4 = var35;
               throw var35;
            } finally {
               if (var30) {
                  if (var3 != null) {
                     if (var4 != null) {
                        try {
                           var3.close();
                        } catch (Throwable var32) {
                           var4.addSuppressed(var32);
                        }
                     } else {
                        var3.close();
                     }
                  }

               }
            }

            if (var3 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                     var20 = false;
                  } catch (Throwable var34) {
                     var4.addSuppressed(var34);
                     var20 = false;
                  }
               } else {
                  var3.close();
                  var20 = false;
               }
            } else {
               var20 = false;
            }
         } catch (Throwable var37) {
            var2 = var37;
            throw var37;
         } finally {
            if (var20) {
               if (var1 != null) {
                  if (var2 != null) {
                     try {
                        var1.close();
                     } catch (Throwable var31) {
                        var2.addSuppressed(var31);
                     }
                  } else {
                     var1.close();
                  }
               }

            }
         }

         if (var1 != null) {
            if (var2 != null) {
               try {
                  var1.close();
               } catch (Throwable var33) {
                  var2.addSuppressed(var33);
               }
            } else {
               var1.close();
            }
         }
      } catch (IOException var39) {
         throw new IOError(var39);
      }

      return var5;
   }

   public static void setDefaultInfoCmp(String var0, String var1) {
      CAPS.putIfAbsent(var0, var1);
   }

   public static void setDefaultInfoCmp(String var0, Supplier var1) {
      CAPS.putIfAbsent(var0, var1);
   }

   public static String getInfoCmp(String var0) throws IOException, InterruptedException {
      String var1 = getLoadedInfoCmp(var0);
      if (var1 == null) {
         Process var2 = (new ProcessBuilder(new String[]{OSUtils.INFOCMP_COMMAND, var0})).start();
         var1 = ExecHelper.waitAndCapture(var2);
         CAPS.put(var0, var1);
      }

      return var1;
   }

   public static String getLoadedInfoCmp(String var0) {
      Object var1 = CAPS.get(var0);
      if (var1 instanceof Supplier) {
         var1 = ((Supplier)var1).get();
      }

      return (String)var1;
   }

   public static void parseInfoCmp(String var0, Set var1, Map var2, Map var3) {
      Map var4 = getCapabilitiesByName();
      String[] var5 = var0.split("\n");

      for(int var6 = 1; var6 < var5.length; ++var6) {
         Matcher var7 = Pattern.compile("\\s*(([^,]|\\\\,)+)\\s*[,$]").matcher(var5[var6]);

         while(var7.find()) {
            String var8 = var7.group(1);
            int var9;
            String var10;
            String var11;
            if (var8.contains("#")) {
               var9 = var8.indexOf(35);
               var10 = var8.substring(0, var9);
               var11 = var8.substring(var9 + 1);
               int var12;
               if (var11.startsWith("0x")) {
                  var12 = Integer.parseInt(var11.substring(2), 16);
               } else {
                  var12 = Integer.parseInt(var11);
               }

               InfoCmp$Capability var13 = (InfoCmp$Capability)var4.get(var10);
               if (var13 != null) {
                  var2.put(var13, var12);
               }
            } else if (var8.contains("=")) {
               var9 = var8.indexOf(61);
               var10 = var8.substring(0, var9);
               var11 = var8.substring(var9 + 1);
               InfoCmp$Capability var15 = (InfoCmp$Capability)var4.get(var10);
               if (var15 != null) {
                  var3.put(var15, var11);
               }
            } else {
               InfoCmp$Capability var14 = (InfoCmp$Capability)var4.get(var8);
               if (var14 != null) {
                  var1.add(var14);
               }
            }
         }
      }

   }

   static String loadDefaultInfoCmp(String var0) {
      String var5;
      try {
         InputStream var1 = InfoCmp.class.getResourceAsStream(var0 + ".caps");
         Throwable var2 = null;
         boolean var20 = false;

         try {
            var20 = true;
            BufferedReader var3 = new BufferedReader(new InputStreamReader(var1, StandardCharsets.UTF_8));
            Throwable var4 = null;
            boolean var30 = false;

            try {
               var30 = true;
               var5 = (String)var3.lines().collect(Collectors.joining("\n", "", "\n"));
               var30 = false;
            } catch (Throwable var35) {
               var4 = var35;
               throw var35;
            } finally {
               if (var30) {
                  if (var3 != null) {
                     if (var4 != null) {
                        try {
                           var3.close();
                        } catch (Throwable var32) {
                           var4.addSuppressed(var32);
                        }
                     } else {
                        var3.close();
                     }
                  }

               }
            }

            if (var3 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                     var20 = false;
                  } catch (Throwable var34) {
                     var4.addSuppressed(var34);
                     var20 = false;
                  }
               } else {
                  var3.close();
                  var20 = false;
               }
            } else {
               var20 = false;
            }
         } catch (Throwable var37) {
            var2 = var37;
            throw var37;
         } finally {
            if (var20) {
               if (var1 != null) {
                  if (var2 != null) {
                     try {
                        var1.close();
                     } catch (Throwable var31) {
                        var2.addSuppressed(var31);
                     }
                  } else {
                     var1.close();
                  }
               }

            }
         }

         if (var1 != null) {
            if (var2 != null) {
               try {
                  var1.close();
               } catch (Throwable var33) {
                  var2.addSuppressed(var33);
               }
            } else {
               var1.close();
            }
         }
      } catch (IOException var39) {
         throw new IOError(var39);
      }

      return var5;
   }

   private static String lambda$static$3(String var0) {
      return loadDefaultInfoCmp(var0);
   }

   private static void lambda$getCapabilitiesByName$2(Map var0, String var1) {
      String[] var2 = var1.split(", ");
      InfoCmp$Capability var3 = (InfoCmp$Capability)Enum.valueOf(InfoCmp$Capability.class, var2[0]);
      var0.put(var2[0], var3);
      var0.put(var2[1], var3);
   }

   private static boolean lambda$getCapabilitiesByName$1(String var0) {
      return !var0.isEmpty();
   }

   private static boolean lambda$getCapabilitiesByName$0(String var0) {
      return !var0.startsWith("#");
   }

   static {
      Iterator var0 = Arrays.asList("dumb", "ansi", "xterm", "xterm-256color", "windows", "windows-256color", "windows-conemu", "windows-vtp", "screen", "screen-256color").iterator();

      while(var0.hasNext()) {
         String var1 = (String)var0.next();
         setDefaultInfoCmp(var1, InfoCmp::lambda$static$3);
      }

   }
}
