package org.jline.builtins;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$Signal;
import org.jline.terminal.Terminal$SignalHandler;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.Display;
import org.jline.utils.InfoCmp$Capability;

public class TTop {
   public static final String STAT_UPTIME = "uptime";
   public static final String STAT_TID = "tid";
   public static final String STAT_NAME = "name";
   public static final String STAT_STATE = "state";
   public static final String STAT_BLOCKED_TIME = "blocked_time";
   public static final String STAT_BLOCKED_COUNT = "blocked_count";
   public static final String STAT_WAITED_TIME = "waited_time";
   public static final String STAT_WAITED_COUNT = "waited_count";
   public static final String STAT_LOCK_NAME = "lock_name";
   public static final String STAT_LOCK_OWNER_ID = "lock_owner_id";
   public static final String STAT_LOCK_OWNER_NAME = "lock_owner_name";
   public static final String STAT_USER_TIME = "user_time";
   public static final String STAT_USER_TIME_PERC = "user_time_perc";
   public static final String STAT_CPU_TIME = "cpu_time";
   public static final String STAT_CPU_TIME_PERC = "cpu_time_perc";
   public List sort;
   public long delay;
   public List stats;
   public int nthreads;
   private final Map columns = new LinkedHashMap();
   private final Terminal terminal;
   private final Display display;
   private final BindingReader bindingReader;
   private final KeyMap keys;
   private final Size size = new Size();
   private Comparator comparator;
   private Map previous = new HashMap();
   private Map changes = new HashMap();
   private Map widths = new HashMap();

   public static void ttop(Terminal var0, PrintStream var1, PrintStream var2, String[] var3) throws Exception {
      String[] var4 = new String[]{"ttop -  display and update sorted information about threads", "Usage: ttop [OPTIONS]", "  -? --help                    Show help", "  -o --order=ORDER             Comma separated list of sorting keys", "  -t --stats=STATS             Comma separated list of stats to display", "  -s --seconds=SECONDS         Delay between updates in seconds", "  -m --millis=MILLIS           Delay between updates in milliseconds", "  -n --nthreads=NTHREADS       Only display up to NTHREADS threads"};
      Options var5 = Options.compile(var4).parse((Object[])var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         TTop var6 = new TTop(var0);
         var6.sort = var5.isSet("order") ? Arrays.asList(var5.get("order").split(",")) : null;
         var6.delay = var5.isSet("seconds") ? (long)(var5.getNumber("seconds") * 1000) : var6.delay;
         var6.delay = var5.isSet("millis") ? (long)var5.getNumber("millis") : var6.delay;
         var6.stats = var5.isSet("stats") ? Arrays.asList(var5.get("stats").split(",")) : null;
         var6.nthreads = var5.isSet("nthreads") ? var5.getNumber("nthreads") : var6.nthreads;
         var6.run();
      }
   }

   public TTop(Terminal var1) {
      super();
      this.terminal = var1;
      this.display = new Display(var1, true);
      this.bindingReader = new BindingReader(var1.reader());
      DecimalFormatSymbols var2 = new DecimalFormatSymbols();
      var2.setDecimalSeparator('.');
      DecimalFormat var3 = new DecimalFormat("0.00%", var2);
      this.register("tid", TTop$Align.Right, "TID", TTop::lambda$new$0);
      this.register("name", TTop$Align.Left, "NAME", padcut(40));
      this.register("state", TTop$Align.Left, "STATE", TTop::lambda$new$1);
      this.register("blocked_time", TTop$Align.Right, "T-BLOCKED", TTop::lambda$new$2);
      this.register("blocked_count", TTop$Align.Right, "#-BLOCKED", Object::toString);
      this.register("waited_time", TTop$Align.Right, "T-WAITED", TTop::lambda$new$3);
      this.register("waited_count", TTop$Align.Right, "#-WAITED", Object::toString);
      this.register("lock_name", TTop$Align.Left, "LOCK-NAME", Object::toString);
      this.register("lock_owner_id", TTop$Align.Right, "LOCK-OWNER-ID", TTop::lambda$new$4);
      this.register("lock_owner_name", TTop$Align.Left, "LOCK-OWNER-NAME", TTop::lambda$new$5);
      this.register("user_time", TTop$Align.Right, "T-USR", TTop::lambda$new$6);
      this.register("cpu_time", TTop$Align.Right, "T-CPU", TTop::lambda$new$7);
      TTop$Align var10002 = TTop$Align.Right;
      var3.getClass();
      this.register("user_time_perc", var10002, "%-USR", var3::format);
      var10002 = TTop$Align.Right;
      var3.getClass();
      this.register("cpu_time_perc", var10002, "%-CPU", var3::format);
      this.keys = new KeyMap();
      this.bindKeys(this.keys);
   }

   public KeyMap getKeys() {
      return this.keys;
   }

   public void run() throws IOException, InterruptedException {
      // $FF: Couldn't be decompiled
   }

   private void handle(Terminal$Signal var1) {
      int var2 = this.size.getColumns();
      this.size.copy(this.terminal.getSize());

      try {
         if (this.size.getColumns() < var2) {
            this.display.clear();
         }

         this.display();
      } catch (IOException var4) {
      }

   }

   private List infos() {
      long var1 = ManagementFactory.getRuntimeMXBean().getUptime();
      Long var3 = (Long)((Map)this.previous.computeIfAbsent(-1L, TTop::lambda$infos$8)).put("uptime", var1);
      long var4 = var3 != null ? var1 - var3 : 0L;
      ThreadMXBean var6 = ManagementFactory.getThreadMXBean();
      ThreadInfo[] var7 = var6.dumpAllThreads(false, false);
      ArrayList var8 = new ArrayList();
      ThreadInfo[] var9 = var7;
      int var10 = var7.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         ThreadInfo var12 = var9[var11];
         HashMap var13 = new HashMap();
         var13.put("tid", var12.getThreadId());
         var13.put("name", var12.getThreadName());
         var13.put("state", var12.getThreadState());
         if (var6.isThreadContentionMonitoringEnabled()) {
            var13.put("blocked_time", var12.getBlockedTime());
            var13.put("blocked_count", var12.getBlockedCount());
            var13.put("waited_time", var12.getWaitedTime());
            var13.put("waited_count", var12.getWaitedCount());
         }

         var13.put("lock_name", var12.getLockName());
         var13.put("lock_owner_id", var12.getLockOwnerId());
         var13.put("lock_owner_name", var12.getLockOwnerName());
         if (var6.isThreadCpuTimeSupported() && var6.isThreadCpuTimeEnabled()) {
            long var14 = var12.getThreadId();
            long var18 = var6.getThreadCpuTime(var14);
            long var16 = (Long)((Map)this.previous.computeIfAbsent(var14, TTop::lambda$infos$9)).getOrDefault("cpu_time", var18);
            var13.put("cpu_time", var18);
            var13.put("cpu_time_perc", var4 != 0L ? (double)(var18 - var16) / ((double)var4 * 1000000.0D) : 0.0D);
            var18 = var6.getThreadUserTime(var14);
            var16 = (Long)((Map)this.previous.computeIfAbsent(var14, TTop::lambda$infos$10)).getOrDefault("user_time", var18);
            var13.put("user_time", var18);
            var13.put("user_time_perc", var4 != 0L ? (double)(var18 - var16) / ((double)var4 * 1000000.0D) : 0.0D);
         }

         var8.add(var13);
      }

      return var8;
   }

   private void align(AttributedStringBuilder var1, String var2, int var3, TTop$Align var4) {
      int var5;
      if (var4 == TTop$Align.Left) {
         var1.append((CharSequence)var2);

         for(var5 = 0; var5 < var3 - var2.length(); ++var5) {
            var1.append(' ');
         }
      } else {
         for(var5 = 0; var5 < var3 - var2.length(); ++var5) {
            var1.append(' ');
         }

         var1.append((CharSequence)var2);
      }

   }

   private void display() throws IOException {
      long var1 = System.currentTimeMillis();
      this.display.resize(this.size.getRows(), this.size.getColumns());
      ArrayList var3 = new ArrayList();
      AttributedStringBuilder var4 = new AttributedStringBuilder(this.size.getColumns());
      var4.style(var4.style().bold());
      var4.append((CharSequence)"ttop");
      var4.style(var4.style().boldOff());
      var4.append((CharSequence)" - ");
      var4.append((CharSequence)String.format("%8tT", new Date()));
      var4.append((CharSequence)".");
      OperatingSystemMXBean var5 = ManagementFactory.getOperatingSystemMXBean();
      String var6 = "OS: " + var5.getName() + " " + var5.getVersion() + ", " + var5.getArch() + ", " + var5.getAvailableProcessors() + " cpus.";
      if (var4.length() + 1 + var6.length() < this.size.getColumns()) {
         var4.append((CharSequence)" ");
      } else {
         var3.add(var4.toAttributedString());
         var4.setLength(0);
      }

      var4.append((CharSequence)var6);
      ClassLoadingMXBean var7 = ManagementFactory.getClassLoadingMXBean();
      String var8 = "Classes: " + var7.getLoadedClassCount() + " loaded, " + var7.getUnloadedClassCount() + " unloaded, " + var7.getTotalLoadedClassCount() + " loaded total.";
      if (var4.length() + 1 + var8.length() < this.size.getColumns()) {
         var4.append((CharSequence)" ");
      } else {
         var3.add(var4.toAttributedString());
         var4.setLength(0);
      }

      var4.append((CharSequence)var8);
      ThreadMXBean var9 = ManagementFactory.getThreadMXBean();
      String var10 = "Threads: " + var9.getThreadCount() + ", peak: " + var9.getPeakThreadCount() + ", started: " + var9.getTotalStartedThreadCount() + ".";
      if (var4.length() + 1 + var10.length() < this.size.getColumns()) {
         var4.append((CharSequence)" ");
      } else {
         var3.add(var4.toAttributedString());
         var4.setLength(0);
      }

      var4.append((CharSequence)var10);
      MemoryMXBean var11 = ManagementFactory.getMemoryMXBean();
      String var12 = "Memory: heap: " + memory(var11.getHeapMemoryUsage().getUsed(), var11.getHeapMemoryUsage().getMax()) + ", non heap: " + memory(var11.getNonHeapMemoryUsage().getUsed(), var11.getNonHeapMemoryUsage().getMax()) + ".";
      if (var4.length() + 1 + var12.length() < this.size.getColumns()) {
         var4.append((CharSequence)" ");
      } else {
         var3.add(var4.toAttributedString());
         var4.setLength(0);
      }

      var4.append((CharSequence)var12);
      StringBuilder var13 = new StringBuilder();
      var13.append("GC: ");
      boolean var14 = true;
      Iterator var15 = ManagementFactory.getGarbageCollectorMXBeans().iterator();

      while(var15.hasNext()) {
         GarbageCollectorMXBean var16 = (GarbageCollectorMXBean)var15.next();
         if (var14) {
            var14 = false;
         } else {
            var13.append(", ");
         }

         long var17 = var16.getCollectionCount();
         long var19 = var16.getCollectionTime();
         var13.append(var16.getName()).append(": ").append(Long.toString(var17)).append(" col. / ").append(String.format("%d", var19 / 1000L)).append(".").append(String.format("%03d", var19 % 1000L)).append(" s");
      }

      var13.append(".");
      if (var4.length() + 1 + var13.length() < this.size.getColumns()) {
         var4.append((CharSequence)" ");
      } else {
         var3.add(var4.toAttributedString());
         var4.setLength(0);
      }

      var4.append((CharSequence)var13);
      var3.add(var4.toAttributedString());
      var4.setLength(0);
      var3.add(var4.toAttributedString());
      List var32 = this.infos();
      Collections.sort(var32, this.comparator);
      int var33 = Math.min(this.size.getRows() - var3.size() - 2, this.nthreads > 0 ? this.nthreads : var32.size());
      List var34 = (List)var32.subList(0, var33).stream().map(this::lambda$display$12).collect(Collectors.toList());
      Iterator var18 = this.stats.iterator();

      while(var18.hasNext()) {
         String var36 = (String)var18.next();
         int var20 = var34.stream().mapToInt(TTop::lambda$display$13).max().orElse(0);
         this.widths.put(var36, Math.max(((TTop$Column)this.columns.get(var36)).header.length(), Math.max(var20, (Integer)this.widths.getOrDefault(var36, 0))));
      }

      Object var35;
      int var37;
      if (this.widths.values().stream().mapToInt(Integer::intValue).sum() + this.stats.size() - 1 < this.size.getColumns()) {
         var35 = this.stats;
      } else {
         var35 = new ArrayList();
         var37 = 0;
         Iterator var38 = this.stats.iterator();

         while(var38.hasNext()) {
            String var21 = (String)var38.next();
            int var22 = var37;
            if (var37 > 0) {
               var22 = var37 + 1;
            }

            var22 += (Integer)this.widths.get(var21);
            if (var22 >= this.size.getColumns()) {
               break;
            }

            var37 = var22;
            ((List)var35).add(var21);
         }
      }

      Iterator var39 = ((List)var35).iterator();

      while(var39.hasNext()) {
         String var40 = (String)var39.next();
         if (var4.length() > 0) {
            var4.append((CharSequence)" ");
         }

         TTop$Column var42 = (TTop$Column)this.columns.get(var40);
         this.align(var4, var42.header, (Integer)this.widths.get(var40), var42.align);
      }

      var3.add(var4.toAttributedString());
      var4.setLength(0);

      for(var37 = 0; var37 < var33; ++var37) {
         Map var41 = (Map)var32.get(var37);
         long var43 = (Long)var41.get("tid");
         Iterator var23 = ((List)var35).iterator();

         while(var23.hasNext()) {
            String var24 = (String)var23.next();
            if (var4.length() > 0) {
               var4.append((CharSequence)" ");
            }

            Object var27 = var41.get(var24);
            Object var28 = ((Map)this.previous.computeIfAbsent(var43, TTop::lambda$display$14)).put(var24, var27);
            long var25;
            if (var28 != null && !var28.equals(var27)) {
               ((Map)this.changes.computeIfAbsent(var43, TTop::lambda$display$15)).put(var24, var1);
               var25 = var1;
            } else {
               var25 = (Long)((Map)this.changes.computeIfAbsent(var43, TTop::lambda$display$16)).getOrDefault(var24, 0L);
            }

            long var29 = this.delay * 24L;
            if (var1 - var25 < var29) {
               int var31 = (int)((var1 - var25) / (var29 / 24L));
               var4.style(var4.style().foreground(255 - var31).background(9));
            }

            this.align(var4, (String)((Map)var34.get(var37)).get(var24), (Integer)this.widths.get(var24), ((TTop$Column)this.columns.get(var24)).align);
            var4.style(var4.style().backgroundOff().foregroundOff());
         }

         var3.add(var4.toAttributedString());
         var4.setLength(0);
      }

      this.display.update(var3, 0);
   }

   private Comparator buildComparator(List var1) {
      if (var1 == null || var1.isEmpty()) {
         var1 = Collections.singletonList("tid");
      }

      Comparator var2 = null;
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         String var5;
         boolean var6;
         if (var4.startsWith("+")) {
            var5 = var4.substring(1);
            var6 = true;
         } else if (var4.startsWith("-")) {
            var5 = var4.substring(1);
            var6 = false;
         } else {
            var5 = var4;
            var6 = true;
         }

         if (!this.columns.containsKey(var5)) {
            throw new IllegalArgumentException("Unsupported sort key: " + var5);
         }

         Comparator var7 = Comparator.comparing(TTop::lambda$buildComparator$17);
         if (var6) {
            var7 = var7.reversed();
         }

         if (var2 != null) {
            var2 = var2.thenComparing(var7);
         } else {
            var2 = var7;
         }
      }

      return var2;
   }

   private void register(String var1, TTop$Align var2, String var3, Function var4) {
      this.columns.put(var1, new TTop$Column(var1, var2, var3, var4));
   }

   private static String nanos(long var0) {
      return millis(var0 / 1000000L);
   }

   private static String millis(long var0) {
      long var2 = var0 / 1000L;
      var0 %= 1000L;
      long var4 = var2 / 60L;
      var2 %= 60L;
      long var6 = var4 / 60L;
      var4 %= 60L;
      if (var6 > 0L) {
         return String.format("%d:%02d:%02d.%03d", var6, var4, var2, var0);
      } else {
         return var4 > 0L ? String.format("%d:%02d.%03d", var4, var2, var0) : String.format("%d.%03d", var2, var0);
      }
   }

   private static Function padcut(int var0) {
      return TTop::lambda$padcut$18;
   }

   private static String padcut(String var0, int var1) {
      StringBuilder var2;
      if (var0.length() > var1) {
         var2 = new StringBuilder(var1);
         var2.append(var0, 0, var1 - 3);
         var2.append("...");
         return var2.toString();
      } else {
         var2 = new StringBuilder(var1);
         var2.append(var0);

         while(var2.length() < var1) {
            var2.append(' ');
         }

         return var2.toString();
      }
   }

   private static String memory(long var0, long var2) {
      if (var2 <= 0L) {
         return humanReadableByteCount(var0, false);
      } else {
         String var4 = humanReadableByteCount(var2, false);
         String var5 = humanReadableByteCount(var0, false);
         StringBuilder var6 = new StringBuilder(var4.length() * 2 + 3);

         for(int var7 = var5.length(); var7 < var4.length(); ++var7) {
            var6.append(' ');
         }

         var6.append(var5).append(" / ").append(var4);
         return var6.toString();
      }
   }

   private static String humanReadableByteCount(long var0, boolean var2) {
      int var3 = var2 ? 1000 : 1024;
      if (var0 < 1024L) {
         return var0 + " B";
      } else {
         int var4 = (int)(Math.log((double)var0) / Math.log(1024.0D));
         String var5 = (var2 ? "kMGTPE" : "KMGTPE").charAt(var4 - 1) + (var2 ? "" : "i");
         return String.format("%.1f %sB", (double)var0 / Math.pow((double)var3, (double)var4), var5);
      }
   }

   private void checkInterrupted() throws InterruptedException {
      Thread.yield();
      if (Thread.currentThread().isInterrupted()) {
         throw new InterruptedException();
      }
   }

   private void bindKeys(KeyMap var1) {
      var1.bind(TTop$Operation.HELP, (CharSequence[])("h", "?"));
      var1.bind(TTop$Operation.EXIT, (CharSequence[])("q", ":q", "Q", ":Q", "ZZ"));
      var1.bind(TTop$Operation.INCREASE_DELAY, (CharSequence)"+");
      var1.bind(TTop$Operation.DECREASE_DELAY, (CharSequence)"-");
      var1.bind(TTop$Operation.CLEAR, (CharSequence)KeyMap.ctrl('L'));
      var1.bind(TTop$Operation.REVERSE, (CharSequence)"r");
   }

   private static String lambda$padcut$18(int var0, Object var1) {
      return padcut(var1.toString(), var0);
   }

   private static Comparable lambda$buildComparator$17(String var0, Map var1) {
      return (Comparable)var1.get(var0);
   }

   private static Map lambda$display$16(Long var0) {
      return new HashMap();
   }

   private static Map lambda$display$15(Long var0) {
      return new HashMap();
   }

   private static Map lambda$display$14(Long var0) {
      return new HashMap();
   }

   private static int lambda$display$13(String var0, Map var1) {
      return ((String)var1.get(var0)).length();
   }

   private Map lambda$display$12(Map var1) {
      return (Map)this.stats.stream().collect(Collectors.toMap(Function.identity(), this::lambda$null$11));
   }

   private String lambda$null$11(Map var1, String var2) {
      return (String)((TTop$Column)this.columns.get(var2)).format.apply(var1.get(var2));
   }

   private static Map lambda$infos$10(Long var0) {
      return new HashMap();
   }

   private static Map lambda$infos$9(Long var0) {
      return new HashMap();
   }

   private static Map lambda$infos$8(Long var0) {
      return new HashMap();
   }

   private static String lambda$new$7(Object var0) {
      return nanos((Long)var0);
   }

   private static String lambda$new$6(Object var0) {
      return nanos((Long)var0);
   }

   private static String lambda$new$5(Object var0) {
      return var0 != null ? var0.toString() : "";
   }

   private static String lambda$new$4(Object var0) {
      return (Long)var0 >= 0L ? var0.toString() : "";
   }

   private static String lambda$new$3(Object var0) {
      return millis((Long)var0);
   }

   private static String lambda$new$2(Object var0) {
      return millis((Long)var0);
   }

   private static String lambda$new$1(Object var0) {
      return var0.toString().toLowerCase();
   }

   private static String lambda$new$0(Object var0) {
      return String.format("%3d", (Long)var0);
   }
}
