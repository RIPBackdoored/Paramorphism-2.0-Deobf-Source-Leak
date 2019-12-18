package org.jline.builtins;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.reader.ParsedLine;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Attributes;
import org.jline.terminal.MouseEvent;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$MouseTracking;
import org.jline.terminal.Terminal$Signal;
import org.jline.terminal.Terminal$SignalHandler;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.Colors;
import org.jline.utils.Display;
import org.jline.utils.InfoCmp$Capability;
import org.jline.utils.Log;

public class Tmux {
   public static final String OPT_PREFIX = "prefix";
   public static final String CMD_COMMANDS = "commands";
   public static final String CMD_SEND_PREFIX = "send-prefix";
   public static final String CMD_SPLIT_WINDOW = "split-window";
   public static final String CMD_SPLITW = "splitw";
   public static final String CMD_SELECT_PANE = "select-pane";
   public static final String CMD_SELECTP = "selectp";
   public static final String CMD_RESIZE_PANE = "resize-pane";
   public static final String CMD_RESIZEP = "resizep";
   public static final String CMD_DISPLAY_PANES = "display-panes";
   public static final String CMD_DISPLAYP = "displayp";
   public static final String CMD_CLOCK_MODE = "clock-mode";
   public static final String CMD_SET_OPTION = "set-option";
   public static final String CMD_SET = "set";
   public static final String CMD_LIST_KEYS = "list-keys";
   public static final String CMD_LSK = "lsk";
   public static final String CMD_SEND_KEYS = "send-keys";
   public static final String CMD_SEND = "send";
   public static final String CMD_BIND_KEY = "bind-key";
   public static final String CMD_BIND = "bind";
   public static final String CMD_UNBIND_KEY = "unbind-key";
   public static final String CMD_UNBIND = "unbind";
   private static final int[][][] WINDOW_CLOCK_TABLE = new int[][][]{{{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}, {{0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}}, {{1, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {1, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}, {{1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}}, {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}}, {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}, {0, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}, {{0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}}, {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}}, {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}}, {{1, 0, 0, 0, 1}, {1, 1, 0, 1, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}}};
   private final AtomicBoolean dirty = new AtomicBoolean(true);
   private final AtomicBoolean resized = new AtomicBoolean(true);
   private final Terminal terminal;
   private final Display display;
   private final PrintStream err;
   private final String term;
   private final Consumer runner;
   private List panes = new ArrayList();
   private Tmux$VirtualConsole active;
   private int lastActive;
   private final AtomicBoolean running = new AtomicBoolean(true);
   private final Size size = new Size();
   private final AtomicInteger paneId = new AtomicInteger();
   private Tmux$Layout layout;
   private boolean identify;
   private ScheduledExecutorService executor;
   private ScheduledFuture clockFuture;
   private final Map serverOptions = new HashMap();
   private KeyMap keyMap;
   int ACTIVE_COLOR = 3908;
   int INACTIVE_COLOR = 1103;
   int CLOCK_COLOR = 1103;

   public Tmux(Terminal var1, PrintStream var2, Consumer var3) throws IOException {
      super();
      this.terminal = var1;
      this.err = var2;
      this.runner = var3;
      this.display = new Display(var1, true);
      Integer var4 = var1.getNumericCapability(InfoCmp$Capability.max_colors);
      this.term = var4 != null && var4 >= 256 ? "screen-256color" : "screen";
      this.serverOptions.put("prefix", "`");
      this.keyMap = this.createKeyMap((String)this.serverOptions.get("prefix"));
   }

   protected KeyMap createKeyMap(String var1) {
      KeyMap var2 = this.createEmptyKeyMap(var1);
      var2.bind("send-prefix", (CharSequence)(var1 + var1));
      var2.bind("split-window -v", (CharSequence)(var1 + "\""));
      var2.bind("split-window -h", (CharSequence)(var1 + "%"));
      var2.bind("select-pane -U", (CharSequence)(var1 + KeyMap.key(this.terminal, InfoCmp$Capability.key_up)));
      var2.bind("select-pane -D", (CharSequence)(var1 + KeyMap.key(this.terminal, InfoCmp$Capability.key_down)));
      var2.bind("select-pane -L", (CharSequence)(var1 + KeyMap.key(this.terminal, InfoCmp$Capability.key_left)));
      var2.bind("select-pane -R", (CharSequence)(var1 + KeyMap.key(this.terminal, InfoCmp$Capability.key_right)));
      var2.bind("resize-pane -U 5", (CharSequence)(var1 + KeyMap.esc() + KeyMap.key(this.terminal, InfoCmp$Capability.key_up)));
      var2.bind("resize-pane -D 5", (CharSequence)(var1 + KeyMap.esc() + KeyMap.key(this.terminal, InfoCmp$Capability.key_down)));
      var2.bind("resize-pane -L 5", (CharSequence)(var1 + KeyMap.esc() + KeyMap.key(this.terminal, InfoCmp$Capability.key_left)));
      var2.bind("resize-pane -R 5", (CharSequence)(var1 + KeyMap.esc() + KeyMap.key(this.terminal, InfoCmp$Capability.key_right)));
      var2.bind("resize-pane -U", (CharSequence[])(var1 + KeyMap.translate("^[[1;5A"), var1 + KeyMap.alt(KeyMap.translate("^[[A"))));
      var2.bind("resize-pane -D", (CharSequence[])(var1 + KeyMap.translate("^[[1;5B"), var1 + KeyMap.alt(KeyMap.translate("^[[B"))));
      var2.bind("resize-pane -L", (CharSequence[])(var1 + KeyMap.translate("^[[1;5C"), var1 + KeyMap.alt(KeyMap.translate("^[[C"))));
      var2.bind("resize-pane -R", (CharSequence[])(var1 + KeyMap.translate("^[[1;5D"), var1 + KeyMap.alt(KeyMap.translate("^[[D"))));
      var2.bind("display-panes", (CharSequence)(var1 + "q"));
      var2.bind("clock-mode", (CharSequence)(var1 + "t"));
      return var2;
   }

   protected KeyMap createEmptyKeyMap(String var1) {
      KeyMap var2 = new KeyMap();
      var2.setUnicode(Tmux$Binding.SelfInsert);
      var2.setNomatch(Tmux$Binding.SelfInsert);

      for(int var3 = 0; var3 < 255; ++var3) {
         var2.bind(Tmux$Binding.Discard, (CharSequence)(var1 + (char)var3));
      }

      var2.bind(Tmux$Binding.Mouse, (CharSequence)KeyMap.key(this.terminal, InfoCmp$Capability.key_mouse));
      return var2;
   }

   public void run() throws IOException {
      Terminal$SignalHandler var1 = this.terminal.handle(Terminal$Signal.WINCH, this::resize);
      Terminal$SignalHandler var2 = this.terminal.handle(Terminal$Signal.INT, this::interrupt);
      Terminal$SignalHandler var3 = this.terminal.handle(Terminal$Signal.TSTP, this::suspend);
      Attributes var4 = this.terminal.enterRawMode();
      this.terminal.puts(InfoCmp$Capability.enter_ca_mode);
      this.terminal.puts(InfoCmp$Capability.keypad_xmit);
      this.terminal.trackMouse(Terminal$MouseTracking.Any);
      this.terminal.flush();
      this.executor = Executors.newSingleThreadScheduledExecutor();
      boolean var9 = false;

      try {
         var9 = true;
         this.size.copy(this.terminal.getSize());
         this.layout = new Tmux$Layout();
         this.layout.sx = this.size.getColumns();
         this.layout.sy = this.size.getRows();
         this.layout.type = Tmux$Layout$Type.WindowPane;
         this.active = new Tmux$VirtualConsole(this.paneId.incrementAndGet(), this.term, 0, 0, this.size.getColumns(), this.size.getRows() - 1, this::setDirty, this::close, this.layout);
         Tmux$VirtualConsole.access$002(this.active, this.lastActive++);
         this.active.getConsole().setAttributes(this.terminal.getAttributes());
         this.panes.add(this.active);
         this.runner.accept(this.active.getConsole());
         (new Thread(this::inputLoop, "Mux input loop")).start();
         this.redrawLoop();
         var9 = false;
      } catch (RuntimeException var10) {
         throw var10;
      } finally {
         if (var9) {
            this.executor.shutdown();
            this.terminal.trackMouse(Terminal$MouseTracking.Off);
            this.terminal.puts(InfoCmp$Capability.keypad_local);
            this.terminal.puts(InfoCmp$Capability.exit_ca_mode);
            this.terminal.flush();
            this.terminal.setAttributes(var4);
            this.terminal.handle(Terminal$Signal.WINCH, var1);
            this.terminal.handle(Terminal$Signal.INT, var2);
            this.terminal.handle(Terminal$Signal.TSTP, var3);
         }
      }

      this.executor.shutdown();
      this.terminal.trackMouse(Terminal$MouseTracking.Off);
      this.terminal.puts(InfoCmp$Capability.keypad_local);
      this.terminal.puts(InfoCmp$Capability.exit_ca_mode);
      this.terminal.flush();
      this.terminal.setAttributes(var4);
      this.terminal.handle(Terminal$Signal.WINCH, var1);
      this.terminal.handle(Terminal$Signal.INT, var2);
      this.terminal.handle(Terminal$Signal.TSTP, var3);
   }

   private void redrawLoop() {
      while(this.running.get()) {
         try {
            synchronized(this.dirty) {
               while(this.running.get() && !this.dirty.compareAndSet(true, false)) {
                  this.dirty.wait();
               }
            }
         } catch (InterruptedException var4) {
            var4.printStackTrace();
         }

         this.handleResize();
         this.redraw();
      }

   }

   private void setDirty() {
      synchronized(this.dirty) {
         this.dirty.set(true);
         this.dirty.notifyAll();
      }

   }

   private void inputLoop() {
      boolean var27 = false;

      label567: {
         try {
            var27 = true;
            BindingReader var1 = new BindingReader(this.terminal.reader());
            boolean var2 = true;

            while(true) {
               while(this.running.get()) {
                  Object var3;
                  if (var2) {
                     var3 = var1.readBinding(this.keyMap);
                  } else if (var1.peekCharacter(100L) >= 0) {
                     var3 = var1.readBinding(this.keyMap, (KeyMap)null, false);
                  } else {
                     var3 = null;
                  }

                  if (var3 == Tmux$Binding.SelfInsert) {
                     if (Tmux$VirtualConsole.access$100(this.active)) {
                        Tmux$VirtualConsole.access$102(this.active, false);
                        if (this.clockFuture != null && this.panes.stream().noneMatch(Tmux::lambda$inputLoop$0)) {
                           this.clockFuture.cancel(false);
                           this.clockFuture = null;
                        }

                        this.setDirty();
                     } else {
                        this.active.getMasterInputOutput().write(var1.getLastBinding().getBytes());
                        var2 = false;
                     }
                  } else {
                     if (var2) {
                        var2 = false;
                     } else {
                        this.active.getMasterInputOutput().flush();
                        var2 = true;
                     }

                     if (var3 == Tmux$Binding.Mouse) {
                        MouseEvent var63 = this.terminal.readMouseEvent();
                     } else if (var3 instanceof String || var3 instanceof String[]) {
                        ByteArrayOutputStream var4 = new ByteArrayOutputStream();
                        ByteArrayOutputStream var5 = new ByteArrayOutputStream();

                        try {
                           PrintStream var6 = new PrintStream(var4);
                           Throwable var7 = null;
                           boolean var39 = false;

                           try {
                              var39 = true;
                              PrintStream var8 = new PrintStream(var5);
                              Throwable var9 = null;
                              boolean var51 = false;

                              try {
                                 var51 = true;
                                 if (var3 instanceof String) {
                                    this.execute(var6, var8, (String)var3);
                                    var51 = false;
                                 } else {
                                    this.execute(var6, var8, Arrays.asList((String[])((String[])var3)));
                                    var51 = false;
                                 }
                              } catch (Throwable var56) {
                                 var9 = var56;
                                 throw var56;
                              } finally {
                                 if (var51) {
                                    if (var8 != null) {
                                       if (var9 != null) {
                                          try {
                                             var8.close();
                                          } catch (Throwable var53) {
                                             var9.addSuppressed(var53);
                                          }
                                       } else {
                                          var8.close();
                                       }
                                    }

                                 }
                              }

                              if (var8 != null) {
                                 if (var9 != null) {
                                    try {
                                       var8.close();
                                       var39 = false;
                                    } catch (Throwable var55) {
                                       var9.addSuppressed(var55);
                                       var39 = false;
                                    }
                                 } else {
                                    var8.close();
                                    var39 = false;
                                 }
                              } else {
                                 var39 = false;
                              }
                           } catch (Throwable var58) {
                              var7 = var58;
                              throw var58;
                           } finally {
                              if (var39) {
                                 if (var6 != null) {
                                    if (var7 != null) {
                                       try {
                                          var6.close();
                                       } catch (Throwable var52) {
                                          var7.addSuppressed(var52);
                                       }
                                    } else {
                                       var6.close();
                                    }
                                 }

                              }
                           }

                           if (var6 != null) {
                              if (var7 != null) {
                                 try {
                                    var6.close();
                                 } catch (Throwable var54) {
                                    var7.addSuppressed(var54);
                                 }
                              } else {
                                 var6.close();
                              }
                           }
                        } catch (Exception var60) {
                        }
                     }
                  }
               }

               var27 = false;
               break label567;
            }
         } catch (IOException var61) {
            if (this.running.get()) {
               Log.info("Error in tmux input loop", var61);
               var27 = false;
            } else {
               var27 = false;
            }
         } finally {
            if (var27) {
               this.running.set(false);
               this.setDirty();
            }
         }

         this.running.set(false);
         this.setDirty();
         return;
      }

      this.running.set(false);
      this.setDirty();
   }

   private synchronized void close(Tmux$VirtualConsole var1) {
      int var2 = this.panes.indexOf(var1);
      if (var2 >= 0) {
         this.panes.remove(var2);
         if (this.panes.isEmpty()) {
            this.running.set(false);
            this.setDirty();
         } else {
            Tmux$VirtualConsole.access$200(var1).remove();
            if (this.active == var1) {
               this.active = (Tmux$VirtualConsole)this.panes.stream().sorted(Comparator.comparingInt(Tmux::lambda$close$1).reversed()).findFirst().get();
            }

            for(this.layout = Tmux$VirtualConsole.access$200(this.active); this.layout.parent != null; this.layout = this.layout.parent) {
            }

            this.layout.fixOffsets();
            this.layout.fixPanes(this.size.getColumns(), this.size.getRows());
            this.resize(Terminal$Signal.WINCH);
         }
      }

   }

   private void resize(Terminal$Signal var1) {
      this.resized.set(true);
      this.setDirty();
   }

   private void interrupt(Terminal$Signal var1) {
      this.active.getConsole().raise(var1);
   }

   private void suspend(Terminal$Signal var1) {
      this.active.getConsole().raise(var1);
   }

   private void handleResize() {
      if (this.resized.compareAndSet(true, false)) {
         this.size.copy(this.terminal.getSize());
      }

      this.layout.resize(this.size.getColumns(), this.size.getRows() - 1);
      this.panes.forEach(this::lambda$handleResize$2);
   }

   public void execute(PrintStream var1, PrintStream var2, String var3) throws Exception {
      ParsedLine var4 = (new DefaultParser()).parse(var3.trim(), 0);
      this.execute(var1, var2, var4.words());
   }

   public synchronized void execute(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String var4 = (String)var3.get(0);
      List var5 = var3.subList(1, var3.size());
      byte var7 = -1;
      switch(var4.hashCode()) {
      case -1830406552:
         if (var4.equals("unbind-key")) {
            var7 = 12;
         }
         break;
      case -1625275273:
         if (var4.equals("send-prefix")) {
            var7 = 0;
         }
         break;
      case -1485481469:
         if (var4.equals("split-window")) {
            var7 = 1;
         }
         break;
      case -1270525501:
         if (var4.equals("list-keys")) {
            var7 = 14;
         }
         break;
      case -911558431:
         if (var4.equals("resize-pane")) {
            var7 = 5;
         }
         break;
      case -895858531:
         if (var4.equals("splitw")) {
            var7 = 2;
         }
         break;
      case -840745386:
         if (var4.equals("unbind")) {
            var7 = 13;
         }
         break;
      case -19729127:
         if (var4.equals("send-keys")) {
            var7 = 16;
         }
         break;
      case 107460:
         if (var4.equals("lsk")) {
            var7 = 15;
         }
         break;
      case 113762:
         if (var4.equals("set")) {
            var7 = 19;
         }
         break;
      case 3023933:
         if (var4.equals("bind")) {
            var7 = 11;
         }
         break;
      case 3526536:
         if (var4.equals("send")) {
            var7 = 17;
         }
         break;
      case 169151897:
         if (var4.equals("select-pane")) {
            var7 = 3;
         }
         break;
      case 285081582:
         if (var4.equals("displayp")) {
            var7 = 8;
         }
         break;
      case 938332367:
         if (var4.equals("bind-key")) {
            var7 = 10;
         }
         break;
      case 1043650562:
         if (var4.equals("clock-mode")) {
            var7 = 9;
         }
         break;
      case 1097202236:
         if (var4.equals("resizep")) {
            var7 = 6;
         }
         break;
      case 1393433920:
         if (var4.equals("display-panes")) {
            var7 = 7;
         }
         break;
      case 1581835328:
         if (var4.equals("set-option")) {
            var7 = 18;
         }
         break;
      case 1978100468:
         if (var4.equals("selectp")) {
            var7 = 4;
         }
      }

      switch(var7) {
      case 0:
         this.sendPrefix(var1, var2, var5);
         break;
      case 1:
      case 2:
         this.splitWindow(var1, var2, var5);
         break;
      case 3:
      case 4:
         this.selectPane(var1, var2, var5);
         break;
      case 5:
      case 6:
         this.resizePane(var1, var2, var5);
         break;
      case 7:
      case 8:
         this.displayPanes(var1, var2, var5);
         break;
      case 9:
         this.clockMode(var1, var2, var5);
         break;
      case 10:
      case 11:
         this.bindKey(var1, var2, var5);
         break;
      case 12:
      case 13:
         this.unbindKey(var1, var2, var5);
         break;
      case 14:
      case 15:
         this.listKeys(var1, var2, var5);
         break;
      case 16:
      case 17:
         this.sendKeys(var1, var2, var5);
         break;
      case 18:
      case 19:
         this.setOption(var1, var2, var5);
      }

   }

   protected void setOption(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"set-option - ", "Usage: set-option [-agosquw] option [value]", "  -? --help                    Show help", "  -u --unset                   Unset the option"};
      Options var5 = Options.compile(var4).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         int var6 = var5.args().size();
         if (var6 >= 1 && var6 <= 2) {
            String var7 = (String)var5.args().get(0);
            String var8 = var6 > 1 ? (String)var5.args().get(1) : null;
            if (!var7.startsWith("@")) {
               byte var10 = -1;
               switch(var7.hashCode()) {
               case -980110702:
                  if (var7.equals("prefix")) {
                     var10 = 0;
                  }
               }

               switch(var10) {
               case 0:
                  if (var8 == null) {
                     throw new IllegalArgumentException("Missing argument");
                  }

                  String var11 = KeyMap.translate(var8);
                  String var12 = (String)this.serverOptions.put("prefix", var11);
                  KeyMap var13 = this.createEmptyKeyMap(var11);
                  Iterator var14 = this.keyMap.getBoundKeys().entrySet().iterator();

                  while(var14.hasNext()) {
                     Entry var15 = (Entry)var14.next();
                     if (var15.getValue() instanceof String) {
                        if (((String)var15.getKey()).equals(var12 + var12)) {
                           var13.bind(var15.getValue(), (CharSequence)(var11 + var11));
                        } else if (((String)var15.getKey()).startsWith(var12)) {
                           var13.bind(var15.getValue(), (CharSequence)(var11 + ((String)var15.getKey()).substring(var12.length())));
                        } else {
                           var13.bind(var15.getValue(), (CharSequence)var15.getKey());
                        }
                     }
                  }

                  this.keyMap = var13;
               }
            }

         } else {
            throw new Options$HelpException(var5.usage());
         }
      }
   }

   protected void bindKey(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"bind-key - ", "Usage: bind-key key command [arguments]", "  -? --help                    Show help"};
      Options var5 = Options.compile(var4).setOptionsFirst(true).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         List var6 = var5.args();
         if (var6.size() < 2) {
            throw new Options$HelpException(var5.usage());
         } else {
            String var7 = (String)this.serverOptions.get("prefix");
            String var8 = var7 + KeyMap.translate((String)var6.remove(0));
            this.keyMap.unbind((CharSequence)var8.substring(0, 2));
            this.keyMap.bind(var6.toArray(new String[var6.size()]), (CharSequence)var8);
         }
      }
   }

   protected void unbindKey(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"unbind-key - ", "Usage: unbind-key key", "  -? --help                    Show help"};
      Options var5 = Options.compile(var4).setOptionsFirst(true).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         List var6 = var5.args();
         if (var6.size() != 1) {
            throw new Options$HelpException(var5.usage());
         } else {
            String var7 = (String)this.serverOptions.get("prefix");
            String var8 = var7 + KeyMap.translate((String)var6.remove(0));
            this.keyMap.unbind((CharSequence)var8);
            this.keyMap.bind(Tmux$Binding.Discard, (CharSequence)var8);
         }
      }
   }

   protected void listKeys(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"list-keys - ", "Usage: list-keys ", "  -? --help                    Show help"};
      Options var5 = Options.compile(var4).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         String var6 = (String)this.serverOptions.get("prefix");
         Stream var10000 = this.keyMap.getBoundKeys().entrySet().stream().filter(Tmux::lambda$listKeys$3).map(Tmux::lambda$listKeys$4).sorted();
         var1.getClass();
         var10000.forEach(var1::println);
      }
   }

   protected void sendKeys(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"send-keys - ", "Usage: send-keys [-lXRM] [-N repeat-count] [-t target-pane] key...", "  -? --help                    Show help", "  -l --literal                Send key literally", "  -N --number=repeat-count     Specifies a repeat count"};
      Options var5 = Options.compile(var4).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         int var6 = 0;

         for(int var7 = var5.getNumber("number"); var6 < var7; ++var6) {
            Iterator var8 = var5.args().iterator();

            while(var8.hasNext()) {
               String var9 = (String)var8.next();
               String var10 = var5.isSet("literal") ? var9 : KeyMap.translate(var9);
               this.active.getMasterInputOutput().write(var10.getBytes());
            }
         }

      }
   }

   protected void clockMode(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"clock-mode - ", "Usage: clock-mode", "  -? --help                    Show help"};
      Options var5 = Options.compile(var4).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         Tmux$VirtualConsole.access$102(this.active, true);
         if (this.clockFuture == null) {
            long var6 = Instant.now().until(Instant.now().truncatedTo(ChronoUnit.MINUTES).plusSeconds(60L), ChronoUnit.MILLIS);
            long var8 = TimeUnit.MILLISECONDS.convert(1L, TimeUnit.SECONDS);
            this.clockFuture = this.executor.scheduleWithFixedDelay(this::setDirty, var6, var8, TimeUnit.MILLISECONDS);
         }

         this.setDirty();
      }
   }

   protected void displayPanes(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"display-panes - ", "Usage: display-panes", "  -? --help                    Show help"};
      Options var5 = Options.compile(var4).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         this.identify = true;
         this.setDirty();
         this.executor.schedule(this::lambda$displayPanes$5, 1L, TimeUnit.SECONDS);
      }
   }

   protected void resizePane(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"resize-pane - ", "Usage: resize-pane [-UDLR] [-x width] [-y height] [-t target-pane] [adjustment]", "  -? --help                    Show help", "  -U                           Resize pane upward", "  -D                           Select pane downward", "  -L                           Select pane to the left", "  -R                           Select pane to the right", "  -x --width=width             Set the width of the pane", "  -y --height=height           Set the height of the pane"};
      Options var5 = Options.compile(var4).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         int var6;
         if (var5.args().size() == 0) {
            var6 = 1;
         } else {
            if (var5.args().size() != 1) {
               throw new Options$HelpException(var5.usage());
            }

            var6 = Integer.parseInt((String)var5.args().get(0));
         }

         int var7;
         if (var5.isSet("width")) {
            var7 = var5.getNumber("width");
            this.active.layout().resizeTo(Tmux$Layout$Type.LeftRight, var7);
         }

         if (var5.isSet("height")) {
            var7 = var5.getNumber("height");
            this.active.layout().resizeTo(Tmux$Layout$Type.TopBottom, var7);
         }

         if (var5.isSet("L")) {
            this.active.layout().resize(Tmux$Layout$Type.LeftRight, -var6, true);
         } else if (var5.isSet("R")) {
            this.active.layout().resize(Tmux$Layout$Type.LeftRight, var6, true);
         } else if (var5.isSet("U")) {
            this.active.layout().resize(Tmux$Layout$Type.TopBottom, -var6, true);
         } else if (var5.isSet("D")) {
            this.active.layout().resize(Tmux$Layout$Type.TopBottom, var6, true);
         }

         this.setDirty();
      }
   }

   protected void selectPane(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"select-pane - ", "Usage: select-pane [-UDLR] [-t target-pane]", "  -? --help                    Show help", "  -U                           Select pane up", "  -D                           Select pane down", "  -L                           Select pane left", "  -R                           Select pane right"};
      Options var5 = Options.compile(var4).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         Tmux$VirtualConsole var6 = this.active;
         if (var5.isSet("L")) {
            this.active = (Tmux$VirtualConsole)this.panes.stream().filter(this::lambda$selectPane$6).filter(this::lambda$selectPane$7).sorted(Comparator.comparingInt(this::lambda$selectPane$8).reversed().thenComparingInt(Tmux::lambda$selectPane$9)).findFirst().orElse(this.active);
         } else if (var5.isSet("R")) {
            this.active = (Tmux$VirtualConsole)this.panes.stream().filter(this::lambda$selectPane$10).filter(this::lambda$selectPane$11).sorted(Comparator.comparingInt(this::lambda$selectPane$12).thenComparingInt(Tmux::lambda$selectPane$13)).findFirst().orElse(this.active);
         } else if (var5.isSet("U")) {
            this.active = (Tmux$VirtualConsole)this.panes.stream().filter(this::lambda$selectPane$14).filter(this::lambda$selectPane$15).sorted(Comparator.comparingInt(this::lambda$selectPane$16).reversed().thenComparingInt(Tmux::lambda$selectPane$17)).findFirst().orElse(this.active);
         } else if (var5.isSet("D")) {
            this.active = (Tmux$VirtualConsole)this.panes.stream().filter(this::lambda$selectPane$18).filter(this::lambda$selectPane$19).sorted(Comparator.comparingInt(this::lambda$selectPane$20).thenComparingInt(Tmux::lambda$selectPane$21)).findFirst().orElse(this.active);
         }

         if (var6 != this.active) {
            this.setDirty();
            Tmux$VirtualConsole.access$002(this.active, this.lastActive++);
         }

      }
   }

   protected void sendPrefix(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"send-prefix - ", "Usage: send-prefix [-2] [-t target-pane]", "  -? --help                    Show help"};
      Options var5 = Options.compile(var4).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         this.active.getMasterInputOutput().write(((String)this.serverOptions.get("prefix")).getBytes());
      }
   }

   protected void splitWindow(PrintStream var1, PrintStream var2, List var3) throws Exception {
      String[] var4 = new String[]{"split-window - ", "Usage: split-window [-bdfhvP] [-c start-directory] [-F format] [-p percentage|-l size] [-t target-pane] [command]", "  -? --help                    Show help", "  -h --horizontal              Horizontal split", "  -v --vertical                Vertical split", "  -l --size=size               Size", "  -p --perc=percentage         Percentage", "  -b --before                  Insert the new pane before the active one", "  -f                           Split the full window instead of the active pane", "  -d                           Do not make the new pane the active one"};
      Options var5 = Options.compile(var4).parse(var3);
      if (var5.isSet("help")) {
         throw new Options$HelpException(var5.usage());
      } else {
         Tmux$Layout$Type var6 = var5.isSet("horizontal") ? Tmux$Layout$Type.LeftRight : Tmux$Layout$Type.TopBottom;
         Tmux$Layout var7;
         if (this.layout.type == Tmux$Layout$Type.WindowPane) {
            var7 = new Tmux$Layout();
            var7.sx = this.layout.sx;
            var7.sy = this.layout.sy;
            var7.type = var6;
            var7.cells.add(this.layout);
            this.layout.parent = var7;
            this.layout = var7;
         }

         var7 = this.active.layout();
         if (var5.isSet("f")) {
            while(var7.parent != this.layout) {
               var7 = var7.parent;
            }
         }

         int var8 = -1;
         if (var5.isSet("size")) {
            var8 = var5.getNumber("size");
         } else if (var5.isSet("perc")) {
            int var9 = var5.getNumber("perc");
            if (var6 == Tmux$Layout$Type.TopBottom) {
               var8 = var7.sy * var9 / 100;
            } else {
               var8 = var7.sx * var9 / 100;
            }
         }

         Tmux$Layout var11 = var7.split(var6, var8, var5.isSet("before"));
         if (var11 == null) {
            var2.println("create pane failed: pane too small");
         } else {
            Tmux$VirtualConsole var10 = new Tmux$VirtualConsole(this.paneId.incrementAndGet(), this.term, var11.xoff, var11.yoff, var11.sx, var11.sy, this::setDirty, this::close, var11);
            this.panes.add(var10);
            var10.getConsole().setAttributes(this.terminal.getAttributes());
            if (!var5.isSet("d")) {
               this.active = var10;
               Tmux$VirtualConsole.access$002(this.active, this.lastActive++);
            }

            this.runner.accept(var10.getConsole());
            this.setDirty();
         }
      }
   }

   protected void layoutResize() {
   }

   protected synchronized void redraw() {
      long[] var1 = new long[this.size.getRows() * this.size.getColumns()];
      Arrays.fill(var1, 32L);
      int[] var2 = new int[2];

      Tmux$VirtualConsole var4;
      for(Iterator var3 = this.panes.iterator(); var3.hasNext(); this.drawBorder(var1, this.size, var4, 0L)) {
         var4 = (Tmux$VirtualConsole)var3.next();
         String var5;
         if (Tmux$VirtualConsole.access$100(var4)) {
            var5 = DateFormat.getTimeInstance(3).format(new Date());
            this.print(var1, var4, var5, this.CLOCK_COLOR);
         } else {
            var4.dump(var1, var4.top(), var4.left(), this.size.getRows(), this.size.getColumns(), var4 == this.active ? var2 : null);
         }

         if (this.identify) {
            var5 = Integer.toString(Tmux$VirtualConsole.access$300(var4));
            this.print(var1, var4, var5, var4 == this.active ? this.ACTIVE_COLOR : this.INACTIVE_COLOR);
         }
      }

      this.drawBorder(var1, this.size, this.active, 1155173304420532224L);
      Arrays.fill(var1, (this.size.getRows() - 1) * this.size.getColumns(), this.size.getRows() * this.size.getColumns(), 2305843558969507872L);
      ArrayList var28 = new ArrayList();
      int var29 = 0;
      int var30 = 0;
      boolean var6 = false;
      boolean var7 = false;
      boolean var8 = false;
      boolean var9 = false;
      boolean var10 = false;
      boolean var11 = false;

      for(int var12 = 0; var12 < this.size.getRows(); ++var12) {
         AttributedStringBuilder var13 = new AttributedStringBuilder(this.size.getColumns());

         for(int var14 = 0; var14 < this.size.getColumns(); ++var14) {
            long var15 = var1[var12 * this.size.getColumns() + var14];
            int var17 = (int)(var15 & 4294967295L);
            int var18 = (int)(var15 >> 32);
            int var19 = var18 & 4095;
            int var20 = (var18 & 16773120) >> 12;
            boolean var21 = (var18 & 16777216) != 0;
            boolean var22 = (var18 & 33554432) != 0;
            boolean var23 = (var18 & 67108864) != 0;
            boolean var24 = (var18 & 134217728) != 0;
            boolean var25 = (var18 & 268435456) != 0;
            boolean var26 = (var18 & 536870912) != 0;
            int var27;
            if (var26 && var11 && var19 != var29 || var11 != var26) {
               if (!var26) {
                  var13.style(var13.style().backgroundDefault());
               } else {
                  var27 = Colors.roundRgbColor((var19 & 3840) >> 4, var19 & 240, (var19 & 15) << 4, 256);
                  var13.style(var13.style().background(var27));
               }

               var29 = var19;
               var11 = var26;
            }

            if (var25 && var10 && var20 != var30 || var10 != var25) {
               if (!var25) {
                  var13.style(var13.style().foregroundDefault());
               } else {
                  var27 = Colors.roundRgbColor((var20 & 3840) >> 4, var20 & 240, (var20 & 15) << 4, 256);
                  var13.style(var13.style().foreground(var27));
               }

               var30 = var20;
               var10 = var25;
            }

            if (var23 != var9) {
               var13.style(var23 ? var13.style().conceal() : var13.style().concealOff());
               var9 = var23;
            }

            if (var22 != var6) {
               var13.style(var22 ? var13.style().inverse() : var13.style().inverseOff());
               var6 = var22;
            }

            if (var21 != var7) {
               var13.style(var21 ? var13.style().underline() : var13.style().underlineOff());
               var7 = var21;
            }

            if (var24 != var8) {
               var13.style(var24 ? var13.style().bold() : var13.style().boldOff());
               var8 = var24;
            }

            var13.append((char)var17);
         }

         var28.add(var13.toAttributedString());
      }

      this.display.resize(this.size.getRows(), this.size.getColumns());
      this.display.update(var28, this.size.cursorPos(var2[1], var2[0]));
   }

   private void print(long[] var1, Tmux$VirtualConsole var2, String var3, int var4) {
      long var5;
      int var7;
      int var8;
      int var9;
      if (var2.height() > 5) {
         var5 = (long)var4 << 32 | 2305843009213693952L;
         var7 = (var2.height() - 5) / 2;
         var8 = (var2.width() - var3.length() * 6) / 2;

         for(var9 = 0; var9 < var3.length(); ++var9) {
            char var10 = var3.charAt(var9);
            int var11;
            switch(var10) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
               var11 = var10 - 48;
               break;
            case ':':
               var11 = 10;
               break;
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'N':
            case 'O':
            default:
               var11 = -1;
               break;
            case 'A':
               var11 = 11;
               break;
            case 'M':
               var11 = 13;
               break;
            case 'P':
               var11 = 12;
            }

            if (var11 >= 0) {
               int[][] var12 = WINDOW_CLOCK_TABLE[var11];

               for(int var13 = 0; var13 < var12.length; ++var13) {
                  for(int var14 = 0; var14 < var12[var13].length; ++var14) {
                     if (var12[var13][var14] != 0) {
                        int var15 = (Tmux$VirtualConsole.access$400(var2) + var7 + var13) * this.size.getColumns() + var2.left() + var8 + var14 + 6 * var9;
                        var1[var15] = var5 | 32L;
                     }
                  }
               }
            }
         }
      } else {
         var5 = (long)var4 << 44 | 1152921504606846976L;
         var7 = (var2.height() + 1) / 2;
         var8 = (var2.width() - var3.length()) / 2;
         var9 = (Tmux$VirtualConsole.access$400(var2) + var7) * this.size.getColumns() + var2.left() + var8;

         for(int var16 = 0; var16 < var3.length(); ++var16) {
            var1[var9 + var16] = var5 | (long)var3.charAt(var16);
         }
      }

   }

   private void drawBorder(long[] var1, Size var2, Tmux$VirtualConsole var3, long var4) {
      int var6;
      int var7;
      int var8;
      for(var6 = var3.left(); var6 < var3.right(); ++var6) {
         var7 = var3.top() - 1;
         var8 = var3.bottom();
         this.drawBorderChar(var1, var2, var6, var7, var4, 9472);
         this.drawBorderChar(var1, var2, var6, var8, var4, 9472);
      }

      for(var6 = var3.top(); var6 < var3.bottom(); ++var6) {
         var7 = var3.left() - 1;
         var8 = var3.right();
         this.drawBorderChar(var1, var2, var7, var6, var4, 9474);
         this.drawBorderChar(var1, var2, var8, var6, var4, 9474);
      }

      this.drawBorderChar(var1, var2, var3.left() - 1, var3.top() - 1, var4, 9484);
      this.drawBorderChar(var1, var2, var3.right(), var3.top() - 1, var4, 9488);
      this.drawBorderChar(var1, var2, var3.left() - 1, var3.bottom(), var4, 9492);
      this.drawBorderChar(var1, var2, var3.right(), var3.bottom(), var4, 9496);
   }

   private void drawBorderChar(long[] var1, Size var2, int var3, int var4, long var5, int var7) {
      if (var3 >= 0 && var3 < var2.getColumns() && var4 >= 0 && var4 < var2.getRows() - 1) {
         int var8 = (int)(var1[var4 * var2.getColumns() + var3] & 4294967295L);
         var7 = this.addBorder(var7, var8);
         var1[var4 * var2.getColumns() + var3] = var5 | (long)var7;
      }

   }

   private int addBorder(int var1, int var2) {
      if (var2 == 32) {
         return var1;
      } else if (var2 == 9532) {
         return 9532;
      } else {
         switch(var1) {
         case 9472:
            return this.addBorder(9588, this.addBorder(9590, var2));
         case 9474:
            return this.addBorder(9591, this.addBorder(9589, var2));
         case 9484:
            return this.addBorder(9590, this.addBorder(9591, var2));
         case 9488:
            return this.addBorder(9588, this.addBorder(9591, var2));
         case 9492:
            return this.addBorder(9590, this.addBorder(9589, var2));
         case 9496:
            return this.addBorder(9588, this.addBorder(9589, var2));
         case 9500:
            return this.addBorder(9590, this.addBorder(9474, var2));
         case 9508:
            return this.addBorder(9588, this.addBorder(9474, var2));
         case 9516:
            return this.addBorder(9591, this.addBorder(9472, var2));
         case 9524:
            return this.addBorder(9589, this.addBorder(9472, var2));
         case 9588:
            switch(var2) {
            case 9472:
               return 9472;
            case 9474:
               return 9508;
            case 9484:
               return 9516;
            case 9488:
               return 9488;
            case 9492:
               return 9524;
            case 9496:
               return 9496;
            case 9500:
               return 9532;
            case 9508:
               return 9508;
            case 9516:
               return 9516;
            case 9524:
               return 9524;
            default:
               throw new IllegalArgumentException();
            }
         case 9589:
            switch(var2) {
            case 9472:
               return 9524;
            case 9474:
               return 9474;
            case 9484:
               return 9500;
            case 9488:
               return 9508;
            case 9492:
               return 9492;
            case 9496:
               return 9496;
            case 9500:
               return 9500;
            case 9508:
               return 9508;
            case 9516:
               return 9532;
            case 9524:
               return 9524;
            default:
               throw new IllegalArgumentException();
            }
         case 9590:
            switch(var2) {
            case 9472:
               return 9472;
            case 9474:
               return 9500;
            case 9484:
               return 9484;
            case 9488:
               return 9516;
            case 9492:
               return 9492;
            case 9496:
               return 9524;
            case 9500:
               return 9500;
            case 9508:
               return 9532;
            case 9516:
               return 9516;
            case 9524:
               return 9524;
            default:
               throw new IllegalArgumentException();
            }
         case 9591:
            switch(var2) {
            case 9472:
               return 9516;
            case 9474:
               return 9474;
            case 9484:
               return 9484;
            case 9488:
               return 9488;
            case 9492:
               return 9500;
            case 9496:
               return 9508;
            case 9500:
               return 9500;
            case 9508:
               return 9508;
            case 9516:
               return 9516;
            case 9524:
               return 9532;
            default:
               throw new IllegalArgumentException();
            }
         default:
            throw new IllegalArgumentException();
         }
      }
   }

   private static int findMatch(String var0, char var1, char var2) {
      if (var0.charAt(0) != var1) {
         throw new IllegalArgumentException();
      } else {
         int var3 = 0;

         int var4;
         for(var4 = 0; var4 < var0.length(); ++var4) {
            char var5 = var0.charAt(var4);
            if (var5 == var1) {
               ++var3;
            } else if (var5 == var2) {
               --var3;
               if (var3 == 0) {
                  return var4;
               }
            }
         }

         if (var3 > 0) {
            throw new IllegalArgumentException("No matching '" + var2 + "'");
         } else {
            return var4;
         }
      }
   }

   private static int lambda$selectPane$21(Tmux$VirtualConsole var0) {
      return -Tmux$VirtualConsole.access$000(var0);
   }

   private int lambda$selectPane$20(Tmux$VirtualConsole var1) {
      return var1.top() > this.active.top() ? var1.top() : var1.top() + this.size.getRows();
   }

   private boolean lambda$selectPane$19(Tmux$VirtualConsole var1) {
      return var1 != this.active;
   }

   private boolean lambda$selectPane$18(Tmux$VirtualConsole var1) {
      return var1.right() > this.active.left() && var1.left() < this.active.right();
   }

   private static int lambda$selectPane$17(Tmux$VirtualConsole var0) {
      return -Tmux$VirtualConsole.access$000(var0);
   }

   private int lambda$selectPane$16(Tmux$VirtualConsole var1) {
      return var1.top() > this.active.top() ? var1.top() : var1.top() + this.size.getRows();
   }

   private boolean lambda$selectPane$15(Tmux$VirtualConsole var1) {
      return var1 != this.active;
   }

   private boolean lambda$selectPane$14(Tmux$VirtualConsole var1) {
      return var1.right() > this.active.left() && var1.left() < this.active.right();
   }

   private static int lambda$selectPane$13(Tmux$VirtualConsole var0) {
      return -Tmux$VirtualConsole.access$000(var0);
   }

   private int lambda$selectPane$12(Tmux$VirtualConsole var1) {
      return var1.left() > this.active.left() ? var1.left() : var1.left() + this.size.getColumns();
   }

   private boolean lambda$selectPane$11(Tmux$VirtualConsole var1) {
      return var1 != this.active;
   }

   private boolean lambda$selectPane$10(Tmux$VirtualConsole var1) {
      return var1.bottom() > this.active.top() && var1.top() < this.active.bottom();
   }

   private static int lambda$selectPane$9(Tmux$VirtualConsole var0) {
      return -Tmux$VirtualConsole.access$000(var0);
   }

   private int lambda$selectPane$8(Tmux$VirtualConsole var1) {
      return var1.left() > this.active.left() ? var1.left() : var1.left() + this.size.getColumns();
   }

   private boolean lambda$selectPane$7(Tmux$VirtualConsole var1) {
      return var1 != this.active;
   }

   private boolean lambda$selectPane$6(Tmux$VirtualConsole var1) {
      return var1.bottom() > this.active.top() && var1.top() < this.active.bottom();
   }

   private void lambda$displayPanes$5() {
      this.identify = false;
      this.setDirty();
   }

   private static String lambda$listKeys$4(String var0, Entry var1) {
      String var2 = (String)var1.getKey();
      String var3 = (String)var1.getValue();
      StringBuilder var4 = new StringBuilder();
      var4.append("bind-key -T ");
      if (var2.startsWith(var0)) {
         var4.append("prefix ");
         var2 = var2.substring(var0.length());
      } else {
         var4.append("root   ");
      }

      var4.append(KeyMap.display(var2));

      while(var4.length() < 32) {
         var4.append(" ");
      }

      var4.append(var3);
      return var4.toString();
   }

   private static boolean lambda$listKeys$3(Entry var0) {
      return var0.getValue() instanceof String;
   }

   private void lambda$handleResize$2(Tmux$VirtualConsole var1) {
      if (var1.width() != Tmux$VirtualConsole.access$200(var1).sx || var1.height() != Tmux$VirtualConsole.access$200(var1).sy || var1.left() != Tmux$VirtualConsole.access$200(var1).xoff || var1.top() != Tmux$VirtualConsole.access$200(var1).yoff) {
         var1.resize(Tmux$VirtualConsole.access$200(var1).xoff, Tmux$VirtualConsole.access$200(var1).yoff, Tmux$VirtualConsole.access$200(var1).sx, Tmux$VirtualConsole.access$200(var1).sy);
         this.display.clear();
      }

   }

   private static int lambda$close$1(Tmux$VirtualConsole var0) {
      return Tmux$VirtualConsole.access$000(var0);
   }

   private static boolean lambda$inputLoop$0(Tmux$VirtualConsole var0) {
      return Tmux$VirtualConsole.access$100(var0);
   }

   static int access$500(String var0, char var1, char var2) {
      return findMatch(var0, var1, var2);
   }
}
