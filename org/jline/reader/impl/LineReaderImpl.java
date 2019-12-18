package org.jline.reader.impl;

import org.jline.keymap.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.*;
import org.jline.reader.impl.history.*;
import java.io.*;
import java.time.*;
import java.util.regex.*;
import java.util.stream.*;
import org.jline.reader.*;
import org.jline.utils.*;
import java.util.*;
import java.util.function.*;
import org.jline.terminal.*;

public class LineReaderImpl implements LineReader, Flushable
{
    public static final char NULL_MASK = '\0';
    public static final int TAB_WIDTH = 4;
    public static final String DEFAULT_WORDCHARS = "*?_-.[]~=/&;!#$%^(){}<>";
    public static final String DEFAULT_REMOVE_SUFFIX_CHARS = " \t\n;&|";
    public static final String DEFAULT_COMMENT_BEGIN = "#";
    public static final String DEFAULT_SEARCH_TERMINATORS = "\u001b\n";
    public static final String DEFAULT_BELL_STYLE = "";
    public static final int DEFAULT_LIST_MAX = 100;
    public static final int DEFAULT_ERRORS = 2;
    public static final long DEFAULT_BLINK_MATCHING_PAREN = 500L;
    public static final long DEFAULT_AMBIGUOUS_BINDING = 1000L;
    public static final String DEFAULT_SECONDARY_PROMPT_PATTERN = "%M> ";
    public static final String DEFAULT_OTHERS_GROUP_NAME = "others";
    public static final String DEFAULT_ORIGINAL_GROUP_NAME = "original";
    public static final String DEFAULT_COMPLETION_STYLE_STARTING = "36";
    public static final String DEFAULT_COMPLETION_STYLE_DESCRIPTION = "90";
    public static final String DEFAULT_COMPLETION_STYLE_GROUP = "35;1";
    public static final String DEFAULT_COMPLETION_STYLE_SELECTION = "7";
    private static final int MIN_ROWS = 3;
    public static final String BRACKETED_PASTE_ON = "\u001b[?2004h";
    public static final String BRACKETED_PASTE_OFF = "\u001b[?2004l";
    public static final String BRACKETED_PASTE_BEGIN = "\u001b[200~";
    public static final String BRACKETED_PASTE_END = "\u001b[201~";
    public static final String FOCUS_IN_SEQ = "\u001b[I";
    public static final String FOCUS_OUT_SEQ = "\u001b[O";
    protected final Terminal terminal;
    protected final String appName;
    protected final Map<String, KeyMap<Binding>> keyMaps;
    protected final Map<String, Object> variables;
    protected History history;
    protected Completer completer;
    protected Highlighter highlighter;
    protected Parser parser;
    protected Expander expander;
    protected final Map<Option, Boolean> options;
    protected final Buffer buf;
    protected final Size size;
    protected AttributedString prompt;
    protected AttributedString rightPrompt;
    protected MaskingCallback maskingCallback;
    protected Map<Integer, String> modifiedHistory;
    protected Buffer historyBuffer;
    protected CharSequence searchBuffer;
    protected StringBuffer searchTerm;
    protected boolean searchFailing;
    protected boolean searchBackward;
    protected int searchIndex;
    protected final BindingReader bindingReader;
    protected int findChar;
    protected int findDir;
    protected int findTailAdd;
    private int searchDir;
    private String searchString;
    protected int regionMark;
    protected RegionType regionActive;
    private boolean forceChar;
    private boolean forceLine;
    protected String yankBuffer;
    protected ViMoveMode viMoveMode;
    protected KillRing killRing;
    protected UndoTree<Buffer> undo;
    protected boolean isUndo;
    protected final ReentrantLock lock;
    protected State state;
    protected final AtomicBoolean startedReading;
    protected boolean reading;
    protected Supplier<AttributedString> post;
    protected Map<String, Widget> builtinWidgets;
    protected Map<String, Widget> widgets;
    protected int count;
    protected int mult;
    protected int universal;
    protected int repeatCount;
    protected boolean isArgDigit;
    protected ParsedLine parsedLine;
    protected boolean skipRedisplay;
    protected Display display;
    protected boolean overTyping;
    protected String keyMap;
    protected int smallTerminalOffset;
    protected boolean nextCommandFromHistory;
    protected int nextHistoryId;
    private static final String DESC_PREFIX = "(";
    private static final String DESC_SUFFIX = ")";
    private static final int MARGIN_BETWEEN_DISPLAY_AND_DESC = 1;
    private static final int MARGIN_BETWEEN_COLUMNS = 3;
    
    public LineReaderImpl(final Terminal terminal) throws IOException {
        this(terminal, null, null);
    }
    
    public LineReaderImpl(final Terminal terminal, final String s) throws IOException {
        this(terminal, s, null);
    }
    
    public LineReaderImpl(final Terminal terminal, String appName, final Map<String, Object> variables) {
        super();
        this.history = new DefaultHistory();
        this.completer = null;
        this.highlighter = new DefaultHighlighter();
        this.parser = new DefaultParser();
        this.expander = new DefaultExpander();
        this.options = new HashMap<Option, Boolean>();
        this.buf = new BufferImpl();
        this.size = new Size();
        this.modifiedHistory = new HashMap<Integer, String>();
        this.historyBuffer = null;
        this.searchTerm = null;
        this.searchIndex = -1;
        this.yankBuffer = "";
        this.viMoveMode = ViMoveMode.NORMAL;
        this.killRing = new KillRing();
        this.undo = new UndoTree<Buffer>(this::setBuffer);
        this.lock = new ReentrantLock();
        this.state = State.DONE;
        this.startedReading = new AtomicBoolean();
        this.universal = 4;
        this.overTyping = false;
        this.smallTerminalOffset = 0;
        this.nextCommandFromHistory = false;
        this.nextHistoryId = -1;
        Objects.requireNonNull(terminal, "terminal can not be null");
        this.terminal = terminal;
        if (appName == null) {
            appName = "JLine";
        }
        this.appName = appName;
        if (variables != null) {
            this.variables = variables;
        }
        else {
            this.variables = new HashMap<String, Object>();
        }
        this.keyMaps = this.defaultKeyMaps();
        this.builtinWidgets = this.builtinWidgets();
        this.widgets = new HashMap<String, Widget>(this.builtinWidgets);
        this.bindingReader = new BindingReader(terminal.reader());
    }
    
    @Override
    public Terminal getTerminal() {
        return this.terminal;
    }
    
    @Override
    public String getAppName() {
        return this.appName;
    }
    
    @Override
    public Map<String, KeyMap<Binding>> getKeyMaps() {
        return this.keyMaps;
    }
    
    @Override
    public KeyMap<Binding> getKeys() {
        return this.keyMaps.get(this.keyMap);
    }
    
    @Override
    public Map<String, Widget> getWidgets() {
        return this.widgets;
    }
    
    @Override
    public Map<String, Widget> getBuiltinWidgets() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends Widget>)this.builtinWidgets);
    }
    
    @Override
    public Buffer getBuffer() {
        return this.buf;
    }
    
    @Override
    public void runMacro(final String s) {
        this.bindingReader.runMacro(s);
    }
    
    @Override
    public MouseEvent readMouseEvent() {
        return this.terminal.readMouseEvent(this.bindingReader::readCharacter);
    }
    
    public void setCompleter(final Completer completer) {
        this.completer = completer;
    }
    
    public Completer getCompleter() {
        return this.completer;
    }
    
    public void setHistory(final History history) {
        Objects.requireNonNull(history);
        this.history = history;
    }
    
    @Override
    public History getHistory() {
        return this.history;
    }
    
    public void setHighlighter(final Highlighter highlighter) {
        this.highlighter = highlighter;
    }
    
    @Override
    public Highlighter getHighlighter() {
        return this.highlighter;
    }
    
    @Override
    public Parser getParser() {
        return this.parser;
    }
    
    public void setParser(final Parser parser) {
        this.parser = parser;
    }
    
    @Override
    public Expander getExpander() {
        return this.expander;
    }
    
    public void setExpander(final Expander expander) {
        this.expander = expander;
    }
    
    @Override
    public String readLine() throws UserInterruptException, EndOfFileException {
        return this.readLine(null, null, (MaskingCallback)null, null);
    }
    
    @Override
    public String readLine(final Character c) throws UserInterruptException, EndOfFileException {
        return this.readLine(null, null, c, null);
    }
    
    @Override
    public String readLine(final String s) throws UserInterruptException, EndOfFileException {
        return this.readLine(s, null, (MaskingCallback)null, null);
    }
    
    @Override
    public String readLine(final String s, final Character c) throws UserInterruptException, EndOfFileException {
        return this.readLine(s, null, c, null);
    }
    
    @Override
    public String readLine(final String s, final Character c, final String s2) throws UserInterruptException, EndOfFileException {
        return this.readLine(s, null, c, s2);
    }
    
    @Override
    public String readLine(final String s, final String s2, final Character c, final String s3) throws UserInterruptException, EndOfFileException {
        return this.readLine(s, s2, (c != null) ? new SimpleMaskingCallback(c) : null, s3);
    }
    
    @Override
    public String readLine(final String prompt, final String rightPrompt, final MaskingCallback maskingCallback, final String s) throws UserInterruptException, EndOfFileException {
        if (!this.startedReading.compareAndSet(false, true)) {
            throw new IllegalStateException();
        }
        final Thread currentThread = Thread.currentThread();
        Terminal.SignalHandler handle = null;
        Terminal.SignalHandler handle2 = null;
        Terminal.SignalHandler handle3 = null;
        Attributes enterRawMode = null;
        final boolean b = "dumb".equals(this.terminal.getType()) || "dumb-color".equals(this.terminal.getType());
        try {
            this.maskingCallback = maskingCallback;
            this.repeatCount = 0;
            this.mult = 1;
            this.regionActive = RegionType.NONE;
            this.regionMark = -1;
            this.smallTerminalOffset = 0;
            this.state = State.NORMAL;
            this.modifiedHistory.clear();
            this.setPrompt(prompt);
            this.setRightPrompt(rightPrompt);
            this.buf.clear();
            if (s != null) {
                this.buf.write(s);
            }
            if (this.nextCommandFromHistory && this.nextHistoryId > 0) {
                if (this.history.size() > this.nextHistoryId) {
                    this.history.moveTo(this.nextHistoryId);
                }
                else {
                    this.history.moveTo(this.history.last());
                }
                this.buf.write(this.history.current());
            }
            else {
                this.nextHistoryId = -1;
            }
            this.nextCommandFromHistory = false;
            this.undo.clear();
            this.parsedLine = null;
            this.keyMap = "main";
            if (this.history != null) {
                this.history.attach(this);
            }
            try {
                this.lock.lock();
                this.reading = true;
                handle = this.terminal.handle(Terminal.Signal.INT, LineReaderImpl::lambda$readLine$0);
                handle2 = this.terminal.handle(Terminal.Signal.WINCH, this::handleSignal);
                handle3 = this.terminal.handle(Terminal.Signal.CONT, this::handleSignal);
                enterRawMode = this.terminal.enterRawMode();
                this.size.copy(this.terminal.getBufferSize());
                this.display = new Display(this.terminal, false);
                if (this.size.getRows() == 0 || this.size.getColumns() == 0) {
                    this.display.resize(1, 0);
                }
                else {
                    this.display.resize(this.size.getRows(), this.size.getColumns());
                }
                if (this.isSet(Option.DELAY_LINE_WRAP)) {
                    this.display.setDelayLineWrap(true);
                }
                if (!b) {
                    this.terminal.puts(InfoCmp.Capability.keypad_xmit, new Object[0]);
                    if (this.isSet(Option.AUTO_FRESH_LINE)) {
                        this.callWidget("fresh-line");
                    }
                    if (this.isSet(Option.MOUSE)) {
                        this.terminal.trackMouse(Terminal.MouseTracking.Normal);
                    }
                    if (this.isSet(Option.BRACKETED_PASTE)) {
                        this.terminal.writer().write("\u001b[?2004h");
                    }
                }
                else {
                    final Attributes attributes = new Attributes(enterRawMode);
                    attributes.setInputFlag(Attributes.InputFlag.IGNCR, true);
                    this.terminal.setAttributes(attributes);
                }
                this.callWidget("callback-init");
                this.undo.newState(this.buf.copy());
                this.redrawLine();
                this.redisplay();
            }
            finally {
                this.lock.unlock();
            }
            while (true) {
                KeyMap<Binding> keyMap = null;
                if (this.isInViCmdMode() && this.regionActive != RegionType.NONE) {
                    keyMap = this.keyMaps.get("visual");
                }
                final Binding binding = this.readBinding(this.getKeys(), keyMap);
                if (binding == null) {
                    throw new EndOfFileException();
                }
                Log.trace("Binding: ", binding);
                if (this.buf.length() == 0 && this.getLastBinding().charAt(0) == enterRawMode.getControlChar(Attributes.ControlChar.VEOF)) {
                    throw new EndOfFileException();
                }
                this.isArgDigit = false;
                this.count = ((this.repeatCount == 0) ? 1 : this.repeatCount) * this.mult;
                this.isUndo = false;
                if (this.regionActive == RegionType.PASTE) {
                    this.regionActive = RegionType.NONE;
                }
                try {
                    this.lock.lock();
                    final Buffer copy = this.buf.copy();
                    if (!this.getWidget(binding).apply()) {
                        this.beep();
                    }
                    if (!this.isUndo && !copy.toString().equals(this.buf.toString())) {
                        this.undo.newState(this.buf.copy());
                    }
                    switch (this.state) {
                        case DONE: {
                            return this.finishBuffer();
                        }
                        case EOF: {
                            throw new EndOfFileException();
                        }
                        case INTERRUPT: {
                            throw new UserInterruptException(this.buf.toString());
                        }
                        default: {
                            if (!this.isArgDigit) {
                                this.repeatCount = 0;
                                this.mult = 1;
                            }
                            if (b) {
                                continue;
                            }
                            this.redisplay();
                            continue;
                        }
                    }
                }
                finally {
                    this.lock.unlock();
                }
            }
        }
        catch (IOError ioError) {
            if (ioError.getCause() instanceof InterruptedIOException) {
                throw new UserInterruptException(this.buf.toString());
            }
            throw ioError;
        }
        finally {
            try {
                this.lock.lock();
                this.reading = false;
                this.cleanup();
                if (enterRawMode != null) {
                    this.terminal.setAttributes(enterRawMode);
                }
                if (handle != null) {
                    this.terminal.handle(Terminal.Signal.INT, handle);
                }
                if (handle2 != null) {
                    this.terminal.handle(Terminal.Signal.WINCH, handle2);
                }
                if (handle3 != null) {
                    this.terminal.handle(Terminal.Signal.CONT, handle3);
                }
            }
            finally {
                this.lock.unlock();
            }
            this.startedReading.set(false);
        }
    }
    
    @Override
    public void printAbove(final String s) {
        try {
            this.lock.lock();
            final boolean reading = this.reading;
            if (reading) {
                this.display.update(Collections.emptyList(), 0);
            }
            if (s.endsWith("\n")) {
                this.terminal.writer().print(s);
            }
            else {
                this.terminal.writer().println(s);
            }
            if (reading) {
                this.redisplay(false);
            }
            this.terminal.flush();
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public void printAbove(final AttributedString attributedString) {
        this.printAbove(attributedString.toAnsi(this.terminal));
    }
    
    @Override
    public boolean isReading() {
        try {
            this.lock.lock();
            return this.reading;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    protected boolean freshLine() {
        final boolean booleanCapability = this.terminal.getBooleanCapability(InfoCmp.Capability.auto_right_margin);
        final boolean b = booleanCapability && this.terminal.getBooleanCapability(InfoCmp.Capability.eat_newline_glitch);
        final AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        attributedStringBuilder.style(AttributedStyle.DEFAULT.foreground(8));
        attributedStringBuilder.append((CharSequence)"~");
        attributedStringBuilder.style(AttributedStyle.DEFAULT);
        if (!booleanCapability || b) {
            for (int i = 0; i < this.size.getColumns() - 1; ++i) {
                attributedStringBuilder.append((CharSequence)" ");
            }
            attributedStringBuilder.append((CharSequence)KeyMap.key(this.terminal, InfoCmp.Capability.carriage_return));
            attributedStringBuilder.append((CharSequence)" ");
            attributedStringBuilder.append((CharSequence)KeyMap.key(this.terminal, InfoCmp.Capability.carriage_return));
        }
        else {
            final String stringCapability = this.terminal.getStringCapability(InfoCmp.Capability.clr_eol);
            if (stringCapability != null) {
                Curses.tputs(attributedStringBuilder, stringCapability, new Object[0]);
            }
            for (int j = 0; j < this.size.getColumns() - 2; ++j) {
                attributedStringBuilder.append((CharSequence)" ");
            }
            attributedStringBuilder.append((CharSequence)KeyMap.key(this.terminal, InfoCmp.Capability.carriage_return));
            attributedStringBuilder.append((CharSequence)" ");
            attributedStringBuilder.append((CharSequence)KeyMap.key(this.terminal, InfoCmp.Capability.carriage_return));
        }
        attributedStringBuilder.print(this.terminal);
        return true;
    }
    
    @Override
    public void callWidget(final String s) {
        try {
            this.lock.lock();
            if (!this.reading) {
                throw new IllegalStateException("Widgets can only be called during a `readLine` call");
            }
            try {
                Widget widget;
                if (s.startsWith(".")) {
                    widget = this.builtinWidgets.get(s.substring(1));
                }
                else {
                    widget = this.widgets.get(s);
                }
                if (widget != null) {
                    widget.apply();
                }
            }
            catch (Throwable t) {
                Log.debug("Error executing widget '", s, "'", t);
            }
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public boolean redrawLine() {
        this.display.reset();
        return true;
    }
    
    public void putString(final CharSequence charSequence) {
        this.buf.write(charSequence, this.overTyping);
    }
    
    @Override
    public void flush() {
        this.terminal.flush();
    }
    
    public boolean isKeyMap(final String s) {
        return this.keyMap.equals(s);
    }
    
    public int readCharacter() {
        if (this.lock.isHeldByCurrentThread()) {
            try {
                this.lock.unlock();
                return this.bindingReader.readCharacter();
            }
            finally {
                this.lock.lock();
            }
        }
        return this.bindingReader.readCharacter();
    }
    
    public int peekCharacter(final long n) {
        return this.bindingReader.peekCharacter(n);
    }
    
    protected <T> T doReadBinding(final KeyMap<T> keyMap, final KeyMap<T> keyMap2) {
        if (this.lock.isHeldByCurrentThread()) {
            try {
                this.lock.unlock();
                return this.bindingReader.readBinding(keyMap, keyMap2);
            }
            finally {
                this.lock.lock();
            }
        }
        return this.bindingReader.readBinding(keyMap, keyMap2);
    }
    
    public Binding readBinding(final KeyMap<Binding> keyMap) {
        return this.readBinding(keyMap, null);
    }
    
    public Binding readBinding(final KeyMap<Binding> keyMap, final KeyMap<Binding> keyMap2) {
        final Binding binding = this.doReadBinding(keyMap, keyMap2);
        if (binding instanceof Reference) {
            final String name = ((Reference)binding).name();
            if (!"yank-pop".equals(name) && !"yank".equals(name)) {
                this.killRing.resetLastYank();
            }
            if (!"kill-line".equals(name) && !"kill-whole-line".equals(name) && !"backward-kill-word".equals(name) && !"kill-word".equals(name)) {
                this.killRing.resetLastKill();
            }
        }
        return binding;
    }
    
    @Override
    public ParsedLine getParsedLine() {
        return this.parsedLine;
    }
    
    public String getLastBinding() {
        return this.bindingReader.getLastBinding();
    }
    
    @Override
    public String getSearchTerm() {
        return (this.searchTerm != null) ? this.searchTerm.toString() : null;
    }
    
    @Override
    public RegionType getRegionActive() {
        return this.regionActive;
    }
    
    @Override
    public int getRegionMark() {
        return this.regionMark;
    }
    
    @Override
    public boolean setKeyMap(final String keyMap) {
        if (this.keyMaps.get(keyMap) == null) {
            return false;
        }
        this.keyMap = keyMap;
        if (this.reading) {
            this.callWidget("callback-keymap");
        }
        return true;
    }
    
    @Override
    public String getKeyMap() {
        return this.keyMap;
    }
    
    @Override
    public LineReader variable(final String s, final Object o) {
        this.variables.put(s, o);
        return this;
    }
    
    @Override
    public Map<String, Object> getVariables() {
        return this.variables;
    }
    
    @Override
    public Object getVariable(final String s) {
        return this.variables.get(s);
    }
    
    @Override
    public void setVariable(final String s, final Object o) {
        this.variables.put(s, o);
    }
    
    @Override
    public LineReader option(final Option option, final boolean b) {
        this.options.put(option, b);
        return this;
    }
    
    @Override
    public boolean isSet(final Option option) {
        final Boolean b = this.options.get(option);
        return (b != null) ? b : option.isDef();
    }
    
    @Override
    public void setOpt(final Option option) {
        this.options.put(option, Boolean.TRUE);
    }
    
    @Override
    public void unsetOpt(final Option option) {
        this.options.put(option, Boolean.FALSE);
    }
    
    protected String finishBuffer() {
        String s;
        String string = s = this.buf.toString();
        if (!this.isSet(Option.DISABLE_EVENT_EXPANSION)) {
            final StringBuilder sb = new StringBuilder();
            int n = 0;
            for (int i = 0; i < string.length(); ++i) {
                final char char1 = string.charAt(i);
                if (n != 0) {
                    n = 0;
                    if (char1 != '\n') {
                        sb.append(char1);
                    }
                }
                else if (this.parser.isEscapeChar(char1)) {
                    n = 1;
                }
                else {
                    sb.append(char1);
                }
            }
            string = sb.toString();
        }
        if (this.maskingCallback != null) {
            s = this.maskingCallback.history(s);
        }
        if (s != null && s.length() > 0) {
            this.history.add(Instant.now(), s);
        }
        return string;
    }
    
    protected void handleSignal(final Terminal.Signal signal) {
        if (signal == Terminal.Signal.WINCH) {
            this.size.copy(this.terminal.getBufferSize());
            this.display.resize(this.size.getRows(), this.size.getColumns());
            this.redisplay();
        }
        else if (signal == Terminal.Signal.CONT) {
            this.terminal.enterRawMode();
            this.size.copy(this.terminal.getBufferSize());
            this.display.resize(this.size.getRows(), this.size.getColumns());
            this.terminal.puts(InfoCmp.Capability.keypad_xmit, new Object[0]);
            this.redrawLine();
            this.redisplay();
        }
    }
    
    protected Widget getWidget(final Object o) {
        Widget widget;
        if (o instanceof Widget) {
            widget = (Widget)o;
        }
        else if (o instanceof Macro) {
            widget = this::lambda$getWidget$1;
        }
        else if (o instanceof Reference) {
            final String name = ((Reference)o).name();
            widget = this.widgets.get(name);
            if (widget == null) {
                widget = this::lambda$getWidget$3;
            }
        }
        else {
            widget = this::lambda$getWidget$5;
        }
        return widget;
    }
    
    public void setPrompt(final String s) {
        this.prompt = ((s == null) ? AttributedString.EMPTY : this.expandPromptPattern(s, 0, "", 0));
    }
    
    public void setRightPrompt(final String s) {
        this.rightPrompt = ((s == null) ? AttributedString.EMPTY : this.expandPromptPattern(s, 0, "", 0));
    }
    
    protected void setBuffer(final Buffer buffer) {
        this.buf.copyFrom(buffer);
    }
    
    protected void setBuffer(final String s) {
        this.buf.clear();
        this.buf.write(s);
    }
    
    protected String viDeleteChangeYankToRemap(final String s) {
        switch (s) {
            case "abort":
            case "backward-char":
            case "forward-char":
            case "end-of-line":
            case "vi-match-bracket":
            case "vi-digit-or-beginning-of-line":
            case "neg-argument":
            case "digit-argument":
            case "vi-backward-char":
            case "vi-backward-word":
            case "vi-forward-char":
            case "vi-forward-word":
            case "vi-forward-word-end":
            case "vi-first-non-blank":
            case "vi-goto-column":
            case "vi-delete":
            case "vi-yank":
            case "vi-change-to":
            case "vi-find-next-char":
            case "vi-find-next-char-skip":
            case "vi-find-prev-char":
            case "vi-find-prev-char-skip":
            case "vi-repeat-find":
            case "vi-rev-repeat-find": {
                return s;
            }
            default: {
                return "vi-cmd-mode";
            }
        }
    }
    
    protected int switchCase(final int n) {
        if (Character.isUpperCase(n)) {
            return Character.toLowerCase(n);
        }
        if (Character.isLowerCase(n)) {
            return Character.toUpperCase(n);
        }
        return n;
    }
    
    protected boolean isInViMoveOperation() {
        return this.viMoveMode != ViMoveMode.NORMAL;
    }
    
    protected boolean isInViChangeOperation() {
        return this.viMoveMode == ViMoveMode.CHANGE;
    }
    
    protected boolean isInViCmdMode() {
        return "vicmd".equals(this.keyMap);
    }
    
    protected boolean viForwardChar() {
        if (this.count < 0) {
            return this.callNeg(this::viBackwardChar);
        }
        int findeol = this.findeol();
        if (this.isInViCmdMode() && !this.isInViMoveOperation()) {
            --findeol;
        }
        if (this.buf.cursor() >= findeol) {
            return false;
        }
        while (this.count-- > 0 && this.buf.cursor() < findeol) {
            this.buf.move(1);
        }
        return true;
    }
    
    protected boolean viBackwardChar() {
        if (this.count < 0) {
            return this.callNeg(this::viForwardChar);
        }
        if (this.buf.cursor() == this.findbol()) {
            return false;
        }
        while (this.count-- > 0 && this.buf.cursor() > 0) {
            this.buf.move(-1);
            if (this.buf.currChar() == 10) {
                this.buf.move(1);
                break;
            }
        }
        return true;
    }
    
    protected boolean forwardWord() {
        if (this.count < 0) {
            return this.callNeg(this::backwardWord);
        }
        while (this.count-- > 0) {
            while (this.buf.cursor() < this.buf.length() && this.isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
            if (this.isInViChangeOperation() && this.count == 0) {
                break;
            }
            while (this.buf.cursor() < this.buf.length() && !this.isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
        }
        return true;
    }
    
    protected boolean viForwardWord() {
        if (this.count < 0) {
            return this.callNeg(this::backwardWord);
        }
        while (this.count-- > 0) {
            if (this.isViAlphaNum(this.buf.currChar())) {
                while (this.buf.cursor() < this.buf.length() && this.isViAlphaNum(this.buf.currChar())) {
                    this.buf.move(1);
                }
            }
            else {
                while (this.buf.cursor() < this.buf.length() && !this.isViAlphaNum(this.buf.currChar()) && !this.isWhitespace(this.buf.currChar())) {
                    this.buf.move(1);
                }
            }
            if (this.isInViChangeOperation() && this.count == 0) {
                return true;
            }
            for (int n = (this.buf.currChar() == 10) ? 1 : 0; this.buf.cursor() < this.buf.length() && n < 2 && this.isWhitespace(this.buf.currChar()); n += ((this.buf.currChar() == 10) ? 1 : 0)) {
                this.buf.move(1);
            }
        }
        return true;
    }
    
    protected boolean viForwardBlankWord() {
        if (this.count < 0) {
            return this.callNeg(this::viBackwardBlankWord);
        }
        while (this.count-- > 0) {
            while (this.buf.cursor() < this.buf.length() && !this.isWhitespace(this.buf.currChar())) {
                this.buf.move(1);
            }
            if (this.isInViChangeOperation() && this.count == 0) {
                return true;
            }
            for (int n = (this.buf.currChar() == 10) ? 1 : 0; this.buf.cursor() < this.buf.length() && n < 2 && this.isWhitespace(this.buf.currChar()); n += ((this.buf.currChar() == 10) ? 1 : 0)) {
                this.buf.move(1);
            }
        }
        return true;
    }
    
    protected boolean emacsForwardWord() {
        if (this.count < 0) {
            return this.callNeg(this::emacsBackwardWord);
        }
        while (this.count-- > 0) {
            while (this.buf.cursor() < this.buf.length() && !this.isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
            if (this.isInViChangeOperation() && this.count == 0) {
                return true;
            }
            while (this.buf.cursor() < this.buf.length() && this.isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
        }
        return true;
    }
    
    protected boolean viForwardBlankWordEnd() {
        if (this.count < 0) {
            return false;
        }
        while (this.count-- > 0) {
            while (this.buf.cursor() < this.buf.length()) {
                this.buf.move(1);
                if (!this.isWhitespace(this.buf.currChar())) {
                    break;
                }
            }
            while (this.buf.cursor() < this.buf.length()) {
                this.buf.move(1);
                if (this.isWhitespace(this.buf.currChar())) {
                    break;
                }
            }
        }
        return true;
    }
    
    protected boolean viForwardWordEnd() {
        if (this.count < 0) {
            return this.callNeg(this::backwardWord);
        }
        while (this.count-- > 0) {
            while (this.buf.cursor() < this.buf.length() && this.isWhitespace(this.buf.nextChar())) {
                this.buf.move(1);
            }
            if (this.buf.cursor() < this.buf.length()) {
                if (this.isViAlphaNum(this.buf.nextChar())) {
                    this.buf.move(1);
                    while (this.buf.cursor() < this.buf.length() && this.isViAlphaNum(this.buf.nextChar())) {
                        this.buf.move(1);
                    }
                }
                else {
                    this.buf.move(1);
                    while (this.buf.cursor() < this.buf.length() && !this.isViAlphaNum(this.buf.nextChar()) && !this.isWhitespace(this.buf.nextChar())) {
                        this.buf.move(1);
                    }
                }
            }
        }
        if (this.buf.cursor() < this.buf.length() && this.isInViMoveOperation()) {
            this.buf.move(1);
        }
        return true;
    }
    
    protected boolean backwardWord() {
        if (this.count < 0) {
            return this.callNeg(this::forwardWord);
        }
        while (this.count-- > 0) {
            while (this.buf.cursor() > 0 && !this.isWord(this.buf.atChar(this.buf.cursor() - 1))) {
                this.buf.move(-1);
            }
            while (this.buf.cursor() > 0 && this.isWord(this.buf.atChar(this.buf.cursor() - 1))) {
                this.buf.move(-1);
            }
        }
        return true;
    }
    
    protected boolean viBackwardWord() {
        if (this.count < 0) {
            return this.callNeg(this::backwardWord);
        }
        while (this.count-- > 0) {
            int n = 0;
            while (this.buf.cursor() > 0) {
                this.buf.move(-1);
                if (!this.isWhitespace(this.buf.currChar())) {
                    break;
                }
                n += ((this.buf.currChar() == 10) ? 1 : 0);
                if (n == 2) {
                    this.buf.move(1);
                    break;
                }
            }
            if (this.buf.cursor() > 0) {
                if (this.isViAlphaNum(this.buf.currChar())) {
                    while (this.buf.cursor() > 0) {
                        if (!this.isViAlphaNum(this.buf.prevChar())) {
                            break;
                        }
                        this.buf.move(-1);
                    }
                }
                else {
                    while (this.buf.cursor() > 0 && !this.isViAlphaNum(this.buf.prevChar())) {
                        if (this.isWhitespace(this.buf.prevChar())) {
                            break;
                        }
                        this.buf.move(-1);
                    }
                }
            }
        }
        return true;
    }
    
    protected boolean viBackwardBlankWord() {
        if (this.count < 0) {
            return this.callNeg(this::viForwardBlankWord);
        }
        while (this.count-- > 0) {
            while (this.buf.cursor() > 0) {
                this.buf.move(-1);
                if (!this.isWhitespace(this.buf.currChar())) {
                    break;
                }
            }
            while (this.buf.cursor() > 0) {
                this.buf.move(-1);
                if (this.isWhitespace(this.buf.currChar())) {
                    break;
                }
            }
        }
        return true;
    }
    
    protected boolean viBackwardWordEnd() {
        if (this.count < 0) {
            return this.callNeg(this::viForwardWordEnd);
        }
        while (this.count-- > 0 && this.buf.cursor() > 1) {
            int n;
            if (this.isViAlphaNum(this.buf.currChar())) {
                n = 1;
            }
            else if (!this.isWhitespace(this.buf.currChar())) {
                n = 2;
            }
            else {
                n = 0;
            }
            while (this.buf.cursor() > 0) {
                boolean b = n != 1 && this.isWhitespace(this.buf.currChar());
                if (n != 0) {
                    b |= this.isViAlphaNum(this.buf.currChar());
                }
                if (b == (n == 2)) {
                    break;
                }
                this.buf.move(-1);
            }
            while (this.buf.cursor() > 0 && this.isWhitespace(this.buf.currChar())) {
                this.buf.move(-1);
            }
        }
        return true;
    }
    
    protected boolean viBackwardBlankWordEnd() {
        if (this.count < 0) {
            return this.callNeg(this::viForwardBlankWordEnd);
        }
        while (this.count-- > 0) {
            while (this.buf.cursor() > 0 && !this.isWhitespace(this.buf.currChar())) {
                this.buf.move(-1);
            }
            while (this.buf.cursor() > 0 && this.isWhitespace(this.buf.currChar())) {
                this.buf.move(-1);
            }
        }
        return true;
    }
    
    protected boolean emacsBackwardWord() {
        if (this.count < 0) {
            return this.callNeg(this::emacsForwardWord);
        }
        while (this.count-- > 0) {
            while (this.buf.cursor() > 0) {
                this.buf.move(-1);
                if (this.isWord(this.buf.currChar())) {
                    break;
                }
            }
            while (this.buf.cursor() > 0) {
                this.buf.move(-1);
                if (!this.isWord(this.buf.currChar())) {
                    break;
                }
            }
        }
        return true;
    }
    
    protected boolean backwardDeleteWord() {
        if (this.count < 0) {
            return this.callNeg(this::deleteWord);
        }
        int cursor = this.buf.cursor();
        while (this.count-- > 0) {
            while (cursor > 0 && !this.isWord(this.buf.atChar(cursor - 1))) {
                --cursor;
            }
            while (cursor > 0 && this.isWord(this.buf.atChar(cursor - 1))) {
                --cursor;
            }
        }
        this.buf.backspace(this.buf.cursor() - cursor);
        return true;
    }
    
    protected boolean viBackwardKillWord() {
        if (this.count < 0) {
            return false;
        }
        final int findbol = this.findbol();
        int cursor = this.buf.cursor();
        while (this.count-- > 0) {
            while (cursor > findbol && this.isWhitespace(this.buf.atChar(cursor - 1))) {
                --cursor;
            }
            if (cursor > findbol) {
                if (this.isViAlphaNum(this.buf.atChar(cursor - 1))) {
                    while (cursor > findbol && this.isViAlphaNum(this.buf.atChar(cursor - 1))) {
                        --cursor;
                    }
                }
                else {
                    while (cursor > findbol && !this.isViAlphaNum(this.buf.atChar(cursor - 1)) && !this.isWhitespace(this.buf.atChar(cursor - 1))) {
                        --cursor;
                    }
                }
            }
        }
        this.killRing.addBackwards(this.buf.substring(cursor, this.buf.cursor()));
        this.buf.backspace(this.buf.cursor() - cursor);
        return true;
    }
    
    protected boolean backwardKillWord() {
        if (this.count < 0) {
            return this.callNeg(this::killWord);
        }
        int cursor = this.buf.cursor();
        while (this.count-- > 0) {
            while (cursor > 0 && !this.isWord(this.buf.atChar(cursor - 1))) {
                --cursor;
            }
            while (cursor > 0 && this.isWord(this.buf.atChar(cursor - 1))) {
                --cursor;
            }
        }
        this.killRing.addBackwards(this.buf.substring(cursor, this.buf.cursor()));
        this.buf.backspace(this.buf.cursor() - cursor);
        return true;
    }
    
    protected boolean copyPrevWord() {
        if (this.count <= 0) {
            return false;
        }
        int i = this.buf.cursor();
        do {
            final int n = i;
            while (i > 0 && !this.isWord(this.buf.atChar(i - 1))) {
                --i;
            }
            while (i > 0 && this.isWord(this.buf.atChar(i - 1))) {
                --i;
            }
            if (--this.count == 0) {
                this.buf.write(this.buf.substring(i, n));
                return true;
            }
        } while (i != 0);
        return false;
    }
    
    protected boolean upCaseWord() {
        int abs = Math.abs(this.count);
        final int cursor = this.buf.cursor();
        while (abs-- > 0) {
            while (this.buf.cursor() < this.buf.length() && !this.isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
            while (this.buf.cursor() < this.buf.length() && this.isWord(this.buf.currChar())) {
                this.buf.currChar(Character.toUpperCase(this.buf.currChar()));
                this.buf.move(1);
            }
        }
        if (this.count < 0) {
            this.buf.cursor(cursor);
        }
        return true;
    }
    
    protected boolean downCaseWord() {
        int abs = Math.abs(this.count);
        final int cursor = this.buf.cursor();
        while (abs-- > 0) {
            while (this.buf.cursor() < this.buf.length() && !this.isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
            while (this.buf.cursor() < this.buf.length() && this.isWord(this.buf.currChar())) {
                this.buf.currChar(Character.toLowerCase(this.buf.currChar()));
                this.buf.move(1);
            }
        }
        if (this.count < 0) {
            this.buf.cursor(cursor);
        }
        return true;
    }
    
    protected boolean capitalizeWord() {
        int abs = Math.abs(this.count);
        final int cursor = this.buf.cursor();
        while (abs-- > 0) {
            int n = 1;
            while (this.buf.cursor() < this.buf.length() && !this.isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
            while (this.buf.cursor() < this.buf.length() && this.isWord(this.buf.currChar()) && !this.isAlpha(this.buf.currChar())) {
                this.buf.move(1);
            }
            while (this.buf.cursor() < this.buf.length() && this.isWord(this.buf.currChar())) {
                this.buf.currChar((n != 0) ? Character.toUpperCase(this.buf.currChar()) : Character.toLowerCase(this.buf.currChar()));
                this.buf.move(1);
                n = 0;
            }
        }
        if (this.count < 0) {
            this.buf.cursor(cursor);
        }
        return true;
    }
    
    protected boolean deleteWord() {
        if (this.count < 0) {
            return this.callNeg(this::backwardDeleteWord);
        }
        int cursor = this.buf.cursor();
        while (this.count-- > 0) {
            while (cursor < this.buf.length() && !this.isWord(this.buf.atChar(cursor))) {
                ++cursor;
            }
            while (cursor < this.buf.length() && this.isWord(this.buf.atChar(cursor))) {
                ++cursor;
            }
        }
        this.buf.delete(cursor - this.buf.cursor());
        return true;
    }
    
    protected boolean killWord() {
        if (this.count < 0) {
            return this.callNeg(this::backwardKillWord);
        }
        int cursor = this.buf.cursor();
        while (this.count-- > 0) {
            while (cursor < this.buf.length() && !this.isWord(this.buf.atChar(cursor))) {
                ++cursor;
            }
            while (cursor < this.buf.length() && this.isWord(this.buf.atChar(cursor))) {
                ++cursor;
            }
        }
        this.killRing.add(this.buf.substring(this.buf.cursor(), cursor));
        this.buf.delete(cursor - this.buf.cursor());
        return true;
    }
    
    protected boolean transposeWords() {
        int n = this.buf.cursor() - 1;
        int cursor = this.buf.cursor();
        while (this.buf.atChar(n) != 0 && this.buf.atChar(n) != 10) {
            --n;
        }
        ++n;
        while (this.buf.atChar(cursor) != 0 && this.buf.atChar(cursor) != 10) {
            ++cursor;
        }
        if (cursor - n < 2) {
            return false;
        }
        int n2 = 0;
        int n3 = 0;
        if (!this.isDelimiter(this.buf.atChar(n))) {
            ++n2;
            n3 = 1;
        }
        for (int i = n; i < cursor; ++i) {
            if (this.isDelimiter(this.buf.atChar(i))) {
                n3 = 0;
            }
            else {
                if (n3 == 0) {
                    ++n2;
                }
                n3 = 1;
            }
        }
        if (n2 < 2) {
            return false;
        }
        final boolean b = this.count < 0;
        for (int j = Math.max(this.count, -this.count); j > 0; --j) {
            int cursor2;
            for (cursor2 = this.buf.cursor(); cursor2 > n && !this.isDelimiter(this.buf.atChar(cursor2 - 1)); --cursor2) {}
            int n4 = cursor2;
            while (n4 < cursor && !this.isDelimiter(this.buf.atChar(++n4))) {}
            int n5;
            int n6;
            if (b) {
                for (n5 = cursor2 - 1; n5 > n && this.isDelimiter(this.buf.atChar(n5 - 1)); --n5) {}
                if (n5 < n) {
                    n6 = n4;
                    while (this.isDelimiter(this.buf.atChar(++n6))) {}
                    n5 = n6;
                    while (n5 < cursor && !this.isDelimiter(this.buf.atChar(++n5))) {}
                }
                else {
                    for (n6 = n5; n6 > n && !this.isDelimiter(this.buf.atChar(n6 - 1)); --n6) {}
                }
            }
            else {
                n6 = n4;
                while (n6 < cursor && this.isDelimiter(this.buf.atChar(++n6))) {}
                if (n6 == cursor) {
                    for (n5 = cursor2; this.isDelimiter(this.buf.atChar(n5 - 1)); --n5) {}
                    for (n6 = n5; n6 > n && !this.isDelimiter(this.buf.atChar(n6 - 1)); --n6) {}
                }
                else {
                    n5 = n6;
                    while (n5 < cursor && !this.isDelimiter(this.buf.atChar(++n5))) {}
                }
            }
            if (cursor2 < n6) {
                final String string = this.buf.substring(0, cursor2) + this.buf.substring(n6, n5) + this.buf.substring(n4, n6) + this.buf.substring(cursor2, n4) + this.buf.substring(n5);
                this.buf.clear();
                this.buf.write(string);
                this.buf.cursor(b ? n4 : n5);
            }
            else {
                final String string2 = this.buf.substring(0, n6) + this.buf.substring(cursor2, n4) + this.buf.substring(n5, cursor2) + this.buf.substring(n6, n5) + this.buf.substring(n4);
                this.buf.clear();
                this.buf.write(string2);
                this.buf.cursor(b ? n5 : n4);
            }
        }
        return true;
    }
    
    private int findbol() {
        int cursor;
        for (cursor = this.buf.cursor(); cursor > 0 && this.buf.atChar(cursor - 1) != 10; --cursor) {}
        return cursor;
    }
    
    private int findeol() {
        int cursor;
        for (cursor = this.buf.cursor(); cursor < this.buf.length() && this.buf.atChar(cursor) != 10; ++cursor) {}
        return cursor;
    }
    
    protected boolean insertComment() {
        return this.doInsertComment(false);
    }
    
    protected boolean viInsertComment() {
        return this.doInsertComment(true);
    }
    
    protected boolean doInsertComment(final boolean b) {
        final String string = this.getString("comment-begin", "#");
        this.beginningOfLine();
        this.putString(string);
        if (b) {
            this.setKeyMap("viins");
        }
        return this.acceptLine();
    }
    
    protected boolean viFindNextChar() {
        final int vigetkey = this.vigetkey();
        this.findChar = vigetkey;
        if (vigetkey > 0) {
            this.findDir = 1;
            this.findTailAdd = 0;
            return this.vifindchar(false);
        }
        return false;
    }
    
    protected boolean viFindPrevChar() {
        final int vigetkey = this.vigetkey();
        this.findChar = vigetkey;
        if (vigetkey > 0) {
            this.findDir = -1;
            this.findTailAdd = 0;
            return this.vifindchar(false);
        }
        return false;
    }
    
    protected boolean viFindNextCharSkip() {
        final int vigetkey = this.vigetkey();
        this.findChar = vigetkey;
        if (vigetkey > 0) {
            this.findDir = 1;
            this.findTailAdd = -1;
            return this.vifindchar(false);
        }
        return false;
    }
    
    protected boolean viFindPrevCharSkip() {
        final int vigetkey = this.vigetkey();
        this.findChar = vigetkey;
        if (vigetkey > 0) {
            this.findDir = -1;
            this.findTailAdd = 1;
            return this.vifindchar(false);
        }
        return false;
    }
    
    protected boolean viRepeatFind() {
        return this.vifindchar(true);
    }
    
    protected boolean viRevRepeatFind() {
        if (this.count < 0) {
            return this.callNeg(this::lambda$viRevRepeatFind$6);
        }
        this.findTailAdd = -this.findTailAdd;
        this.findDir = -this.findDir;
        final boolean vifindchar = this.vifindchar(true);
        this.findTailAdd = -this.findTailAdd;
        this.findDir = -this.findDir;
        return vifindchar;
    }
    
    private int vigetkey() {
        final int character = this.readCharacter();
        final KeyMap<Binding> keyMap = this.keyMaps.get("main");
        if (keyMap != null) {
            final Binding binding = keyMap.getBound(new String(Character.toChars(character)));
            if (binding instanceof Reference && "abort".equals(((Reference)binding).name())) {
                return -1;
            }
        }
        return character;
    }
    
    private boolean vifindchar(final boolean b) {
        if (this.findDir == 0) {
            return false;
        }
        if (this.count < 0) {
            return this.callNeg(this::viRevRepeatFind);
        }
        if (b && this.findTailAdd != 0) {
            if (this.findDir > 0) {
                if (this.buf.cursor() < this.buf.length() && this.buf.nextChar() == this.findChar) {
                    this.buf.move(1);
                }
            }
            else if (this.buf.cursor() > 0 && this.buf.prevChar() == this.findChar) {
                this.buf.move(-1);
            }
        }
        final int cursor = this.buf.cursor();
        while (this.count-- > 0) {
            do {
                this.buf.move(this.findDir);
            } while (this.buf.cursor() > 0 && this.buf.cursor() < this.buf.length() && this.buf.currChar() != this.findChar && this.buf.currChar() != 10);
            if (this.buf.cursor() <= 0 || this.buf.cursor() >= this.buf.length() || this.buf.currChar() == 10) {
                this.buf.cursor(cursor);
                return false;
            }
        }
        if (this.findTailAdd != 0) {
            this.buf.move(this.findTailAdd);
        }
        if (this.findDir == 1 && this.isInViMoveOperation()) {
            this.buf.move(1);
        }
        return true;
    }
    
    private boolean callNeg(final Widget widget) {
        this.count = -this.count;
        final boolean apply = widget.apply();
        this.count = -this.count;
        return apply;
    }
    
    protected boolean viHistorySearchForward() {
        this.searchDir = 1;
        this.searchIndex = 0;
        return this.getViSearchString() && this.viRepeatSearch();
    }
    
    protected boolean viHistorySearchBackward() {
        this.searchDir = -1;
        this.searchIndex = this.history.size() - 1;
        return this.getViSearchString() && this.viRepeatSearch();
    }
    
    protected boolean viRepeatSearch() {
        if (this.searchDir == 0) {
            return false;
        }
        final int searchIndex = (this.searchDir < 0) ? this.searchBackwards(this.searchString, this.searchIndex, false) : this.searchForwards(this.searchString, this.searchIndex, false);
        if (searchIndex == -1 || searchIndex == this.history.index()) {
            return false;
        }
        this.searchIndex = searchIndex;
        this.buf.clear();
        this.history.moveTo(this.searchIndex);
        this.buf.write(this.history.get(this.searchIndex));
        if ("vicmd".equals(this.keyMap)) {
            this.buf.move(-1);
        }
        return true;
    }
    
    protected boolean viRevRepeatSearch() {
        this.searchDir = -this.searchDir;
        final boolean viRepeatSearch = this.viRepeatSearch();
        this.searchDir = -this.searchDir;
        return viRepeatSearch;
    }
    
    private boolean getViSearchString() {
        if (this.searchDir == 0) {
            return false;
        }
        final String s = (this.searchDir < 0) ? "?" : "/";
        final BufferImpl bufferImpl = new BufferImpl();
        KeyMap<Binding> keyMap = this.keyMaps.get("main");
        if (keyMap == null) {
            keyMap = this.keyMaps.get(".safe");
        }
        while (true) {
            this.post = LineReaderImpl::lambda$getViSearchString$7;
            this.redisplay();
            final Binding binding = this.doReadBinding(keyMap, null);
            if (binding instanceof Reference) {
                final String name = ((Reference)binding).name();
                switch (name) {
                    case "abort": {
                        this.post = null;
                        return false;
                    }
                    case "accept-line":
                    case "vi-cmd-mode": {
                        this.searchString = bufferImpl.toString();
                        this.post = null;
                        return true;
                    }
                    case "magic-space": {
                        bufferImpl.write(32);
                        continue;
                    }
                    case "redisplay": {
                        this.redisplay();
                        continue;
                    }
                    case "clear-screen": {
                        this.clearScreen();
                        continue;
                    }
                    case "self-insert": {
                        bufferImpl.write(this.getLastBinding());
                        continue;
                    }
                    case "self-insert-unmeta": {
                        if (this.getLastBinding().charAt(0) == '\u001b') {
                            String substring = this.getLastBinding().substring(1);
                            if ("\r".equals(substring)) {
                                substring = "\n";
                            }
                            bufferImpl.write(substring);
                            continue;
                        }
                        continue;
                    }
                    case "backward-delete-char":
                    case "vi-backward-delete-char": {
                        if (bufferImpl.length() > 0) {
                            bufferImpl.backspace();
                            continue;
                        }
                        continue;
                    }
                    case "backward-kill-word":
                    case "vi-backward-kill-word": {
                        if (bufferImpl.length() > 0 && !this.isWhitespace(bufferImpl.prevChar())) {
                            bufferImpl.backspace();
                        }
                        if (bufferImpl.length() > 0 && this.isWhitespace(bufferImpl.prevChar())) {
                            bufferImpl.backspace();
                            continue;
                        }
                        continue;
                    }
                    case "quoted-insert":
                    case "vi-quoted-insert": {
                        final int character = this.readCharacter();
                        if (character >= 0) {
                            bufferImpl.write(character);
                            continue;
                        }
                        this.beep();
                        continue;
                    }
                    default: {
                        this.beep();
                        continue;
                    }
                }
            }
        }
    }
    
    protected boolean insertCloseCurly() {
        return this.insertClose("}");
    }
    
    protected boolean insertCloseParen() {
        return this.insertClose(")");
    }
    
    protected boolean insertCloseSquare() {
        return this.insertClose("]");
    }
    
    protected boolean insertClose(final String s) {
        this.putString(s);
        final long long1 = this.getLong("blink-matching-paren", 500L);
        if (long1 <= 0L) {
            return true;
        }
        final int cursor = this.buf.cursor();
        this.buf.move(-1);
        this.doViMatchBracket();
        this.redisplay();
        this.peekCharacter(long1);
        this.buf.cursor(cursor);
        return true;
    }
    
    protected boolean viMatchBracket() {
        return this.doViMatchBracket();
    }
    
    protected boolean undefinedKey() {
        return false;
    }
    
    protected boolean doViMatchBracket() {
        int cursor = this.buf.cursor();
        if (cursor == this.buf.length()) {
            return false;
        }
        final int bracketType = this.getBracketType(this.buf.atChar(cursor));
        final int n = (bracketType < 0) ? -1 : 1;
        int i = 1;
        if (bracketType == 0) {
            return false;
        }
        while (i > 0) {
            cursor += n;
            if (cursor < 0 || cursor >= this.buf.length()) {
                return false;
            }
            final int bracketType2 = this.getBracketType(this.buf.atChar(cursor));
            if (bracketType2 == bracketType) {
                ++i;
            }
            else {
                if (bracketType2 != -bracketType) {
                    continue;
                }
                --i;
            }
        }
        if (n > 0 && this.isInViMoveOperation()) {
            ++cursor;
        }
        this.buf.cursor(cursor);
        return true;
    }
    
    protected int getBracketType(final int n) {
        switch (n) {
            case 91: {
                return 1;
            }
            case 93: {
                return -1;
            }
            case 123: {
                return 2;
            }
            case 125: {
                return -2;
            }
            case 40: {
                return 3;
            }
            case 41: {
                return -3;
            }
            default: {
                return 0;
            }
        }
    }
    
    protected boolean transposeChars() {
        int n = this.buf.cursor() - 1;
        int cursor = this.buf.cursor();
        while (this.buf.atChar(n) != 0 && this.buf.atChar(n) != 10) {
            --n;
        }
        ++n;
        while (this.buf.atChar(cursor) != 0 && this.buf.atChar(cursor) != 10) {
            ++cursor;
        }
        if (cursor - n < 2) {
            return false;
        }
        final boolean b = this.count < 0;
        for (int i = Math.max(this.count, -this.count); i > 0; --i) {
            while (this.buf.cursor() <= n) {
                this.buf.move(1);
            }
            while (this.buf.cursor() >= cursor) {
                this.buf.move(-1);
            }
            final int currChar = this.buf.currChar();
            this.buf.currChar(this.buf.prevChar());
            this.buf.move(-1);
            this.buf.currChar(currChar);
            this.buf.move(b ? 0 : 2);
        }
        return true;
    }
    
    protected boolean undo() {
        this.isUndo = true;
        if (this.undo.canUndo()) {
            this.undo.undo();
            return true;
        }
        return false;
    }
    
    protected boolean redo() {
        this.isUndo = true;
        if (this.undo.canRedo()) {
            this.undo.redo();
            return true;
        }
        return false;
    }
    
    protected boolean sendBreak() {
        if (this.searchTerm == null) {
            this.buf.clear();
            this.println();
            this.redrawLine();
            return false;
        }
        return true;
    }
    
    protected boolean backwardChar() {
        return this.buf.move(-this.count) != 0;
    }
    
    protected boolean forwardChar() {
        return this.buf.move(this.count) != 0;
    }
    
    protected boolean viDigitOrBeginningOfLine() {
        if (this.repeatCount > 0) {
            return this.digitArgument();
        }
        return this.beginningOfLine();
    }
    
    protected boolean universalArgument() {
        this.mult *= this.universal;
        return this.isArgDigit = true;
    }
    
    protected boolean argumentBase() {
        if (this.repeatCount > 0 && this.repeatCount < 32) {
            this.universal = this.repeatCount;
            return this.isArgDigit = true;
        }
        return false;
    }
    
    protected boolean negArgument() {
        this.mult *= -1;
        return this.isArgDigit = true;
    }
    
    protected boolean digitArgument() {
        final String lastBinding = this.getLastBinding();
        this.repeatCount = this.repeatCount * 10 + lastBinding.charAt(lastBinding.length() - 1) - 48;
        return this.isArgDigit = true;
    }
    
    protected boolean viDelete() {
        final int cursor = this.buf.cursor();
        final Binding binding = this.readBinding(this.getKeys());
        if (binding instanceof Reference) {
            final String viDeleteChangeYankToRemap = this.viDeleteChangeYankToRemap(((Reference)binding).name());
            if ("vi-delete".equals(viDeleteChangeYankToRemap)) {
                this.killWholeLine();
            }
            else {
                this.viMoveMode = ViMoveMode.DELETE;
                final Widget widget = this.widgets.get(viDeleteChangeYankToRemap);
                if (widget != null && !widget.apply()) {
                    this.viMoveMode = ViMoveMode.NORMAL;
                    return false;
                }
                this.viMoveMode = ViMoveMode.NORMAL;
            }
            return this.viDeleteTo(cursor, this.buf.cursor());
        }
        this.pushBackBinding();
        return false;
    }
    
    protected boolean viYankTo() {
        final int cursor = this.buf.cursor();
        final Binding binding = this.readBinding(this.getKeys());
        if (!(binding instanceof Reference)) {
            this.pushBackBinding();
            return false;
        }
        final String viDeleteChangeYankToRemap = this.viDeleteChangeYankToRemap(((Reference)binding).name());
        if ("vi-yank".equals(viDeleteChangeYankToRemap)) {
            this.yankBuffer = this.buf.toString();
            return true;
        }
        this.viMoveMode = ViMoveMode.YANK;
        final Widget widget = this.widgets.get(viDeleteChangeYankToRemap);
        if (widget != null && !widget.apply()) {
            return false;
        }
        this.viMoveMode = ViMoveMode.NORMAL;
        return this.viYankTo(cursor, this.buf.cursor());
    }
    
    protected boolean viYankWholeLine() {
        final int cursor = this.buf.cursor();
        while (this.buf.move(-1) == -1 && this.buf.prevChar() != 10) {}
        final int cursor2 = this.buf.cursor();
        for (int i = 0; i < this.repeatCount; ++i) {
            while (this.buf.move(1) == 1 && this.buf.prevChar() != 10) {}
        }
        this.yankBuffer = this.buf.substring(cursor2, this.buf.cursor());
        if (!this.yankBuffer.endsWith("\n")) {
            this.yankBuffer += "\n";
        }
        this.buf.cursor(cursor);
        return true;
    }
    
    protected boolean viChange() {
        final int cursor = this.buf.cursor();
        final Binding binding = this.readBinding(this.getKeys());
        if (binding instanceof Reference) {
            final String viDeleteChangeYankToRemap = this.viDeleteChangeYankToRemap(((Reference)binding).name());
            if ("vi-change-to".equals(viDeleteChangeYankToRemap)) {
                this.killWholeLine();
            }
            else {
                this.viMoveMode = ViMoveMode.CHANGE;
                final Widget widget = this.widgets.get(viDeleteChangeYankToRemap);
                if (widget != null && !widget.apply()) {
                    this.viMoveMode = ViMoveMode.NORMAL;
                    return false;
                }
                this.viMoveMode = ViMoveMode.NORMAL;
            }
            final boolean viChange = this.viChange(cursor, this.buf.cursor());
            this.setKeyMap("viins");
            return viChange;
        }
        this.pushBackBinding();
        return false;
    }
    
    protected void cleanup() {
        if (this.isSet(Option.ERASE_LINE_ON_FINISH)) {
            final Buffer copy = this.buf.copy();
            final AttributedString prompt = this.prompt;
            this.buf.clear();
            this.prompt = new AttributedString("");
            this.doCleanup(false);
            this.prompt = prompt;
            this.buf.copyFrom(copy);
        }
        else {
            this.doCleanup(true);
        }
    }
    
    protected void doCleanup(final boolean b) {
        this.buf.cursor(this.buf.length());
        this.post = null;
        if (this.size.getColumns() > 0 || this.size.getRows() > 0) {
            this.redisplay(false);
            if (b) {
                this.println();
            }
            this.terminal.puts(InfoCmp.Capability.keypad_local, new Object[0]);
            this.terminal.trackMouse(Terminal.MouseTracking.Off);
            if (this.isSet(Option.BRACKETED_PASTE)) {
                this.terminal.writer().write("\u001b[?2004l");
            }
            this.flush();
        }
        this.history.moveToEnd();
    }
    
    protected boolean historyIncrementalSearchForward() {
        return this.doSearchHistory(false);
    }
    
    protected boolean historyIncrementalSearchBackward() {
        return this.doSearchHistory(true);
    }
    
    protected boolean doSearchHistory(final boolean searchBackward) {
        if (this.history.isEmpty()) {
            return false;
        }
        final KeyMap<Binding> keyMap = new KeyMap<Binding>();
        this.getString("search-terminators", "\u001b\n").codePoints().forEach(this::lambda$doSearchHistory$8);
        final Buffer copy = this.buf.copy();
        this.searchIndex = -1;
        this.searchTerm = new StringBuffer();
        this.searchBackward = searchBackward;
        this.searchFailing = false;
        this.post = this::lambda$doSearchHistory$9;
        this.redisplay();
        try {
            while (true) {
                final int searchIndex = this.searchIndex;
                final Binding binding = this.readBinding(this.getKeys(), keyMap);
                final String s = (binding instanceof Reference) ? ((Reference)binding).name() : "";
                boolean b = false;
                final String s2 = s;
                switch (s2) {
                    case "abort": {
                        this.beep();
                        this.buf.copyFrom(copy);
                        return true;
                    }
                    case "history-incremental-search-backward": {
                        this.searchBackward = true;
                        b = true;
                        break;
                    }
                    case "history-incremental-search-forward": {
                        this.searchBackward = false;
                        b = true;
                        break;
                    }
                    case "backward-delete-char": {
                        if (this.searchTerm.length() > 0) {
                            this.searchTerm.deleteCharAt(this.searchTerm.length() - 1);
                            break;
                        }
                        break;
                    }
                    case "self-insert": {
                        this.searchTerm.append(this.getLastBinding());
                        break;
                    }
                    default: {
                        if (this.searchIndex != -1) {
                            this.history.moveTo(this.searchIndex);
                        }
                        this.pushBackBinding();
                        return true;
                    }
                }
                final String doGetSearchPattern = this.doGetSearchPattern();
                if (doGetSearchPattern.length() == 0) {
                    this.buf.copyFrom(copy);
                    this.searchFailing = false;
                }
                else {
                    final Pattern compile = Pattern.compile(doGetSearchPattern, this.isSet(Option.CASE_INSENSITIVE_SEARCH) ? 66 : 64);
                    Pair pair;
                    if (this.searchBackward) {
                        pair = this.matches(compile, this.buf.toString(), this.searchIndex).stream().filter(this::lambda$doSearchHistory$10).max(Comparator.comparing((Function<? super Pair, ? extends Comparable>)Pair::getV)).orElse(null);
                        if (pair == null) {
                            pair = StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<?>)this.history.reverseIterator((this.searchIndex < 0) ? this.history.last() : (this.searchIndex - 1)), 16), false).flatMap((Function<? super Object, ? extends Stream<? extends Pair>>)this::lambda$doSearchHistory$11).findFirst().orElse(null);
                        }
                    }
                    else {
                        pair = this.matches(compile, this.buf.toString(), this.searchIndex).stream().filter(this::lambda$doSearchHistory$12).min(Comparator.comparing((Function<? super Pair, ? extends Comparable>)Pair::getV)).orElse(null);
                        if (pair == null) {
                            pair = StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<?>)this.history.iterator(((this.searchIndex < 0) ? this.history.last() : this.searchIndex) + 1), 16), false).flatMap((Function<? super Object, ? extends Stream<? extends Pair>>)this::lambda$doSearchHistory$13).findFirst().orElse(null);
                            if (pair == null && this.searchIndex >= 0) {
                                pair = this.matches(compile, copy.toString(), -1).stream().min(Comparator.comparing((Function<? super Pair, ? extends Comparable>)Pair::getV)).orElse(null);
                            }
                        }
                    }
                    if (pair != null) {
                        this.searchIndex = (int)pair.u;
                        this.buf.clear();
                        if (this.searchIndex >= 0) {
                            this.buf.write(this.history.get(this.searchIndex));
                        }
                        else {
                            this.buf.write(copy.toString());
                        }
                        this.buf.cursor((int)pair.v);
                        this.searchFailing = false;
                    }
                    else {
                        this.searchFailing = true;
                        this.beep();
                    }
                }
                this.redisplay();
            }
        }
        catch (IOError ioError) {
            if (!(ioError.getCause() instanceof InterruptedException)) {
                throw ioError;
            }
            return true;
        }
        finally {
            this.searchTerm = null;
            this.searchIndex = -1;
            this.post = null;
        }
    }
    
    private List<Pair<Integer, Integer>> matches(final Pattern pattern, final String s, final int n) {
        final ArrayList<Pair<Integer, Integer>> list = new ArrayList<Pair<Integer, Integer>>();
        final Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            list.add(new Pair<Integer, Integer>(n, matcher.start()));
        }
        return list;
    }
    
    private String doGetSearchPattern() {
        final StringBuilder sb = new StringBuilder();
        int n = 0;
        for (int i = 0; i < this.searchTerm.length(); ++i) {
            final char char1 = this.searchTerm.charAt(i);
            if (Character.isLowerCase(char1)) {
                if (n != 0) {
                    sb.append("\\E");
                    n = 0;
                }
                sb.append("[").append(Character.toLowerCase(char1)).append(Character.toUpperCase(char1)).append("]");
            }
            else {
                if (n == 0) {
                    sb.append("\\Q");
                    n = 1;
                }
                sb.append(char1);
            }
        }
        if (n != 0) {
            sb.append("\\E");
        }
        return sb.toString();
    }
    
    private void pushBackBinding() {
        this.pushBackBinding(false);
    }
    
    private void pushBackBinding(final boolean skipRedisplay) {
        final String lastBinding = this.getLastBinding();
        if (lastBinding != null) {
            this.bindingReader.runMacro(lastBinding);
            this.skipRedisplay = skipRedisplay;
        }
    }
    
    protected boolean historySearchForward() {
        if (this.historyBuffer == null || this.buf.length() == 0 || !this.buf.toString().equals(this.history.current())) {
            this.historyBuffer = this.buf.copy();
            this.searchBuffer = this.getFirstWord();
        }
        final int n = this.history.index() + 1;
        if (n < this.history.last() + 1) {
            final int searchForwards = this.searchForwards(this.searchBuffer.toString(), n, true);
            if (searchForwards == -1) {
                this.history.moveToEnd();
                if (this.buf.toString().equals(this.historyBuffer.toString())) {
                    return false;
                }
                this.setBuffer(this.historyBuffer.toString());
                this.historyBuffer = null;
            }
            else {
                if (!this.history.moveTo(searchForwards)) {
                    this.history.moveToEnd();
                    this.setBuffer(this.historyBuffer.toString());
                    return false;
                }
                this.setBuffer(this.history.current());
            }
        }
        else {
            this.history.moveToEnd();
            if (this.buf.toString().equals(this.historyBuffer.toString())) {
                return false;
            }
            this.setBuffer(this.historyBuffer.toString());
            this.historyBuffer = null;
        }
        return true;
    }
    
    private CharSequence getFirstWord() {
        String string;
        int n;
        for (string = this.buf.toString(), n = 0; n < string.length() && !Character.isWhitespace(string.charAt(n)); ++n) {}
        return string.substring(0, n);
    }
    
    protected boolean historySearchBackward() {
        if (this.historyBuffer == null || this.buf.length() == 0 || !this.buf.toString().equals(this.history.current())) {
            this.historyBuffer = this.buf.copy();
            this.searchBuffer = this.getFirstWord();
        }
        final int searchBackwards = this.searchBackwards(this.searchBuffer.toString(), this.history.index(), true);
        if (searchBackwards == -1) {
            return false;
        }
        if (this.history.moveTo(searchBackwards)) {
            this.setBuffer(this.history.current());
            return true;
        }
        return false;
    }
    
    public int searchBackwards(final String s, final int n) {
        return this.searchBackwards(s, n, false);
    }
    
    public int searchBackwards(final String s) {
        return this.searchBackwards(s, this.history.index(), false);
    }
    
    public int searchBackwards(String lowerCase, final int n, final boolean b) {
        final boolean set = this.isSet(Option.CASE_INSENSITIVE_SEARCH);
        if (set) {
            lowerCase = lowerCase.toLowerCase();
        }
        final ListIterator<History.Entry> iterator = this.history.iterator(n);
        while (iterator.hasPrevious()) {
            final History.Entry entry = iterator.previous();
            String s = entry.line();
            if (set) {
                s = s.toLowerCase();
            }
            final int index = s.indexOf(lowerCase);
            if ((b && index == 0) || (!b && index >= 0)) {
                return entry.index();
            }
        }
        return -1;
    }
    
    public int searchForwards(String lowerCase, int last, final boolean b) {
        final boolean set = this.isSet(Option.CASE_INSENSITIVE_SEARCH);
        if (set) {
            lowerCase = lowerCase.toLowerCase();
        }
        if (last > this.history.last()) {
            last = this.history.last();
        }
        final ListIterator<History.Entry> iterator = this.history.iterator(last);
        if (this.searchIndex != -1 && iterator.hasNext()) {
            iterator.next();
        }
        while (iterator.hasNext()) {
            final History.Entry entry = iterator.next();
            String s = entry.line();
            if (set) {
                s = s.toLowerCase();
            }
            final int index = s.indexOf(lowerCase);
            if ((b && index == 0) || (!b && index >= 0)) {
                return entry.index();
            }
        }
        return -1;
    }
    
    public int searchForwards(final String s, final int n) {
        return this.searchForwards(s, n, false);
    }
    
    public int searchForwards(final String s) {
        return this.searchForwards(s, this.history.index());
    }
    
    protected boolean quit() {
        this.getBuffer().clear();
        return this.acceptLine();
    }
    
    protected boolean acceptAndHold() {
        this.nextCommandFromHistory = false;
        this.acceptLine();
        if (!this.buf.toString().isEmpty()) {
            this.nextHistoryId = 0;
            this.nextCommandFromHistory = true;
        }
        return this.nextCommandFromHistory;
    }
    
    protected boolean acceptLineAndDownHistory() {
        this.nextCommandFromHistory = false;
        this.acceptLine();
        if (this.nextHistoryId < 0) {
            this.nextHistoryId = this.history.index();
        }
        if (this.history.size() > this.nextHistoryId + 1) {
            ++this.nextHistoryId;
            this.nextCommandFromHistory = true;
        }
        return this.nextCommandFromHistory;
    }
    
    protected boolean acceptAndInferNextHistory() {
        this.nextCommandFromHistory = false;
        this.acceptLine();
        if (!this.buf.toString().isEmpty()) {
            this.nextHistoryId = this.searchBackwards(this.buf.toString(), this.history.last());
            if (this.nextHistoryId >= 0 && this.history.size() > this.nextHistoryId + 1) {
                ++this.nextHistoryId;
                this.nextCommandFromHistory = true;
            }
        }
        return this.nextCommandFromHistory;
    }
    
    protected boolean acceptLine() {
        this.parsedLine = null;
        if (!this.isSet(Option.DISABLE_EVENT_EXPANSION)) {
            try {
                final String string = this.buf.toString();
                final String expandHistory = this.expander.expandHistory(this.history, string);
                if (!expandHistory.equals(string)) {
                    this.buf.clear();
                    this.buf.write(expandHistory);
                    if (this.isSet(Option.HISTORY_VERIFY)) {
                        return true;
                    }
                }
            }
            catch (IllegalArgumentException ex) {}
        }
        try {
            this.parsedLine = this.parser.parse(this.buf.toString(), this.buf.cursor(), Parser.ParseContext.ACCEPT_LINE);
        }
        catch (EOFError eofError) {
            this.buf.write("\n");
            return true;
        }
        catch (SyntaxError syntaxError) {}
        this.callWidget("callback-finish");
        this.state = State.DONE;
        return true;
    }
    
    protected boolean selfInsert() {
        for (int i = this.count; i > 0; --i) {
            this.putString(this.getLastBinding());
        }
        return true;
    }
    
    protected boolean selfInsertUnmeta() {
        if (this.getLastBinding().charAt(0) == '\u001b') {
            String substring = this.getLastBinding().substring(1);
            if ("\r".equals(substring)) {
                substring = "\n";
            }
            for (int i = this.count; i > 0; --i) {
                this.putString(substring);
            }
            return true;
        }
        return false;
    }
    
    protected boolean overwriteMode() {
        this.overTyping = !this.overTyping;
        return true;
    }
    
    protected boolean beginningOfBufferOrHistory() {
        if (this.findbol() != 0) {
            this.buf.cursor(0);
            return true;
        }
        return this.beginningOfHistory();
    }
    
    protected boolean beginningOfHistory() {
        if (this.history.moveToFirst()) {
            this.setBuffer(this.history.current());
            return true;
        }
        return false;
    }
    
    protected boolean endOfBufferOrHistory() {
        if (this.findeol() != this.buf.length()) {
            this.buf.cursor(this.buf.length());
            return true;
        }
        return this.endOfHistory();
    }
    
    protected boolean endOfHistory() {
        if (this.history.moveToLast()) {
            this.setBuffer(this.history.current());
            return true;
        }
        return false;
    }
    
    protected boolean beginningOfLineHist() {
        if (this.count < 0) {
            return this.callNeg(this::endOfLineHist);
        }
        while (this.count-- > 0) {
            final int findbol = this.findbol();
            if (findbol != this.buf.cursor()) {
                this.buf.cursor(findbol);
            }
            else {
                this.moveHistory(false);
                this.buf.cursor(0);
            }
        }
        return true;
    }
    
    protected boolean endOfLineHist() {
        if (this.count < 0) {
            return this.callNeg(this::beginningOfLineHist);
        }
        while (this.count-- > 0) {
            final int findeol = this.findeol();
            if (findeol != this.buf.cursor()) {
                this.buf.cursor(findeol);
            }
            else {
                this.moveHistory(true);
            }
        }
        return true;
    }
    
    protected boolean upHistory() {
        while (this.count-- > 0) {
            if (!this.moveHistory(false)) {
                return !this.isSet(Option.HISTORY_BEEP);
            }
        }
        return true;
    }
    
    protected boolean downHistory() {
        while (this.count-- > 0) {
            if (!this.moveHistory(true)) {
                return !this.isSet(Option.HISTORY_BEEP);
            }
        }
        return true;
    }
    
    protected boolean viUpLineOrHistory() {
        return this.upLine() || (this.upHistory() && this.viFirstNonBlank());
    }
    
    protected boolean viDownLineOrHistory() {
        return this.downLine() || (this.downHistory() && this.viFirstNonBlank());
    }
    
    protected boolean upLine() {
        return this.buf.up();
    }
    
    protected boolean downLine() {
        return this.buf.down();
    }
    
    protected boolean upLineOrHistory() {
        return this.upLine() || this.upHistory();
    }
    
    protected boolean upLineOrSearch() {
        return this.upLine() || this.historySearchBackward();
    }
    
    protected boolean downLineOrHistory() {
        return this.downLine() || this.downHistory();
    }
    
    protected boolean downLineOrSearch() {
        return this.downLine() || this.historySearchForward();
    }
    
    protected boolean viCmdMode() {
        if (this.state == State.NORMAL) {
            this.buf.move(-1);
        }
        return this.setKeyMap("vicmd");
    }
    
    protected boolean viInsert() {
        return this.setKeyMap("viins");
    }
    
    protected boolean viAddNext() {
        this.buf.move(1);
        return this.setKeyMap("viins");
    }
    
    protected boolean viAddEol() {
        return this.endOfLine() && this.setKeyMap("viins");
    }
    
    protected boolean emacsEditingMode() {
        return this.setKeyMap("emacs");
    }
    
    protected boolean viChangeWholeLine() {
        return this.viFirstNonBlank() && this.viChangeEol();
    }
    
    protected boolean viChangeEol() {
        return this.viChange(this.buf.cursor(), this.buf.length()) && this.setKeyMap("viins");
    }
    
    protected boolean viKillEol() {
        final int findeol = this.findeol();
        if (this.buf.cursor() == findeol) {
            return false;
        }
        this.killRing.add(this.buf.substring(this.buf.cursor(), findeol));
        this.buf.delete(findeol - this.buf.cursor());
        return true;
    }
    
    protected boolean quotedInsert() {
        final int character = this.readCharacter();
        while (this.count-- > 0) {
            this.putString(new String(Character.toChars(character)));
        }
        return true;
    }
    
    protected boolean viJoin() {
        if (this.buf.down()) {
            while (this.buf.move(-1) == -1 && this.buf.prevChar() != 10) {}
            this.buf.backspace();
            this.buf.write(32);
            this.buf.move(-1);
            return true;
        }
        return false;
    }
    
    protected boolean viKillWholeLine() {
        return this.killWholeLine() && this.setKeyMap("viins");
    }
    
    protected boolean viInsertBol() {
        return this.beginningOfLine() && this.setKeyMap("viins");
    }
    
    protected boolean backwardDeleteChar() {
        if (this.count < 0) {
            return this.callNeg(this::deleteChar);
        }
        if (this.buf.cursor() == 0) {
            return false;
        }
        this.buf.backspace(this.count);
        return true;
    }
    
    protected boolean viFirstNonBlank() {
        this.beginningOfLine();
        while (this.buf.cursor() < this.buf.length() && this.isWhitespace(this.buf.currChar())) {
            this.buf.move(1);
        }
        return true;
    }
    
    protected boolean viBeginningOfLine() {
        this.buf.cursor(this.findbol());
        return true;
    }
    
    protected boolean viEndOfLine() {
        if (this.count < 0) {
            return false;
        }
        while (this.count-- > 0) {
            this.buf.cursor(this.findeol() + 1);
        }
        this.buf.move(-1);
        return true;
    }
    
    protected boolean beginningOfLine() {
        while (this.count-- > 0) {
            while (this.buf.move(-1) == -1 && this.buf.prevChar() != 10) {}
        }
        return true;
    }
    
    protected boolean endOfLine() {
        while (this.count-- > 0) {
            while (this.buf.move(1) == 1 && this.buf.currChar() != 10) {}
        }
        return true;
    }
    
    protected boolean deleteChar() {
        if (this.count < 0) {
            return this.callNeg(this::backwardDeleteChar);
        }
        if (this.buf.cursor() == this.buf.length()) {
            return false;
        }
        this.buf.delete(this.count);
        return true;
    }
    
    protected boolean viBackwardDeleteChar() {
        for (int i = 0; i < this.count; ++i) {
            if (!this.buf.backspace()) {
                return false;
            }
        }
        return true;
    }
    
    protected boolean viDeleteChar() {
        for (int i = 0; i < this.count; ++i) {
            if (!this.buf.delete()) {
                return false;
            }
        }
        return true;
    }
    
    protected boolean viSwapCase() {
        for (int i = 0; i < this.count; ++i) {
            if (this.buf.cursor() >= this.buf.length()) {
                return false;
            }
            this.buf.currChar(this.switchCase(this.buf.atChar(this.buf.cursor())));
            this.buf.move(1);
        }
        return true;
    }
    
    protected boolean viReplaceChars() {
        final int character = this.readCharacter();
        if (character < 0 || character == 27 || character == 3) {
            return true;
        }
        for (int i = 0; i < this.count; ++i) {
            if (!this.buf.currChar((char)character)) {
                return false;
            }
            if (i < this.count - 1) {
                this.buf.move(1);
            }
        }
        return true;
    }
    
    protected boolean viChange(final int n, final int n2) {
        return this.doViDeleteOrChange(n, n2, true);
    }
    
    protected boolean viDeleteTo(final int n, final int n2) {
        return this.doViDeleteOrChange(n, n2, false);
    }
    
    protected boolean doViDeleteOrChange(int n, int n2, final boolean b) {
        if (n == n2) {
            return true;
        }
        if (n2 < n) {
            final int n3 = n2;
            n2 = n;
            n = n3;
        }
        this.buf.cursor(n);
        this.buf.delete(n2 - n);
        if (!b && n > 0 && n == this.buf.length()) {
            this.buf.move(-1);
        }
        return true;
    }
    
    protected boolean viYankTo(int n, int n2) {
        final int n3 = n;
        if (n2 < n) {
            final int n4 = n2;
            n2 = n;
            n = n4;
        }
        if (n == n2) {
            this.yankBuffer = "";
            return true;
        }
        this.yankBuffer = this.buf.substring(n, n2);
        this.buf.cursor(n3);
        return true;
    }
    
    protected boolean viOpenLineAbove() {
        while (this.buf.move(-1) == -1 && this.buf.prevChar() != 10) {}
        this.buf.write(10);
        this.buf.move(-1);
        return this.setKeyMap("viins");
    }
    
    protected boolean viOpenLineBelow() {
        while (this.buf.move(1) == 1 && this.buf.currChar() != 10) {}
        this.buf.write(10);
        return this.setKeyMap("viins");
    }
    
    protected boolean viPutAfter() {
        if (this.yankBuffer.indexOf(10) >= 0) {
            while (this.buf.move(1) == 1 && this.buf.currChar() != 10) {}
            this.buf.move(1);
            this.putString(this.yankBuffer);
            this.buf.move(-this.yankBuffer.length());
        }
        else if (this.yankBuffer.length() != 0) {
            if (this.buf.cursor() < this.buf.length()) {
                this.buf.move(1);
            }
            for (int i = 0; i < this.count; ++i) {
                this.putString(this.yankBuffer);
            }
            this.buf.move(-1);
        }
        return true;
    }
    
    protected boolean viPutBefore() {
        if (this.yankBuffer.indexOf(10) >= 0) {
            while (this.buf.move(-1) == -1 && this.buf.prevChar() != 10) {}
            this.putString(this.yankBuffer);
            this.buf.move(-this.yankBuffer.length());
        }
        else if (this.yankBuffer.length() != 0) {
            if (this.buf.cursor() > 0) {
                this.buf.move(-1);
            }
            for (int i = 0; i < this.count; ++i) {
                this.putString(this.yankBuffer);
            }
            this.buf.move(-1);
        }
        return true;
    }
    
    protected boolean doLowercaseVersion() {
        this.bindingReader.runMacro(this.getLastBinding().toLowerCase());
        return true;
    }
    
    protected boolean setMarkCommand() {
        if (this.count < 0) {
            this.regionActive = RegionType.NONE;
            return true;
        }
        this.regionMark = this.buf.cursor();
        this.regionActive = RegionType.CHAR;
        return true;
    }
    
    protected boolean exchangePointAndMark() {
        if (this.count == 0) {
            this.regionActive = RegionType.CHAR;
            return true;
        }
        final int regionMark = this.regionMark;
        this.regionMark = this.buf.cursor();
        this.buf.cursor(regionMark);
        if (this.buf.cursor() > this.buf.length()) {
            this.buf.cursor(this.buf.length());
        }
        if (this.count > 0) {
            this.regionActive = RegionType.CHAR;
        }
        return true;
    }
    
    protected boolean visualMode() {
        if (this.isInViMoveOperation()) {
            this.isArgDigit = true;
            this.forceLine = false;
            return this.forceChar = true;
        }
        if (this.regionActive == RegionType.NONE) {
            this.regionMark = this.buf.cursor();
            this.regionActive = RegionType.CHAR;
        }
        else if (this.regionActive == RegionType.CHAR) {
            this.regionActive = RegionType.NONE;
        }
        else if (this.regionActive == RegionType.LINE) {
            this.regionActive = RegionType.CHAR;
        }
        return true;
    }
    
    protected boolean visualLineMode() {
        if (this.isInViMoveOperation()) {
            this.isArgDigit = true;
            this.forceLine = true;
            this.forceChar = false;
            return true;
        }
        if (this.regionActive == RegionType.NONE) {
            this.regionMark = this.buf.cursor();
            this.regionActive = RegionType.LINE;
        }
        else if (this.regionActive == RegionType.CHAR) {
            this.regionActive = RegionType.LINE;
        }
        else if (this.regionActive == RegionType.LINE) {
            this.regionActive = RegionType.NONE;
        }
        return true;
    }
    
    protected boolean deactivateRegion() {
        this.regionActive = RegionType.NONE;
        return true;
    }
    
    protected boolean whatCursorPosition() {
        this.post = this::lambda$whatCursorPosition$14;
        return true;
    }
    
    protected Map<String, Widget> builtinWidgets() {
        final HashMap<String, Widget> hashMap = new HashMap<String, Widget>();
        this.addBuiltinWidget(hashMap, "accept-and-infer-next-history", this::acceptAndInferNextHistory);
        this.addBuiltinWidget(hashMap, "accept-and-hold", this::acceptAndHold);
        this.addBuiltinWidget(hashMap, "accept-line", this::acceptLine);
        this.addBuiltinWidget(hashMap, "accept-line-and-down-history", this::acceptLineAndDownHistory);
        this.addBuiltinWidget(hashMap, "argument-base", this::argumentBase);
        this.addBuiltinWidget(hashMap, "backward-char", this::backwardChar);
        this.addBuiltinWidget(hashMap, "backward-delete-char", this::backwardDeleteChar);
        this.addBuiltinWidget(hashMap, "backward-delete-word", this::backwardDeleteWord);
        this.addBuiltinWidget(hashMap, "backward-kill-line", this::backwardKillLine);
        this.addBuiltinWidget(hashMap, "backward-kill-word", this::backwardKillWord);
        this.addBuiltinWidget(hashMap, "backward-word", this::backwardWord);
        this.addBuiltinWidget(hashMap, "beep", this::beep);
        this.addBuiltinWidget(hashMap, "beginning-of-buffer-or-history", this::beginningOfBufferOrHistory);
        this.addBuiltinWidget(hashMap, "beginning-of-history", this::beginningOfHistory);
        this.addBuiltinWidget(hashMap, "beginning-of-line", this::beginningOfLine);
        this.addBuiltinWidget(hashMap, "beginning-of-line-hist", this::beginningOfLineHist);
        this.addBuiltinWidget(hashMap, "capitalize-word", this::capitalizeWord);
        this.addBuiltinWidget(hashMap, "clear", this::clear);
        this.addBuiltinWidget(hashMap, "clear-screen", this::clearScreen);
        this.addBuiltinWidget(hashMap, "complete-prefix", this::completePrefix);
        this.addBuiltinWidget(hashMap, "complete-word", this::completeWord);
        this.addBuiltinWidget(hashMap, "copy-prev-word", this::copyPrevWord);
        this.addBuiltinWidget(hashMap, "copy-region-as-kill", this::copyRegionAsKill);
        this.addBuiltinWidget(hashMap, "delete-char", this::deleteChar);
        this.addBuiltinWidget(hashMap, "delete-char-or-list", this::deleteCharOrList);
        this.addBuiltinWidget(hashMap, "delete-word", this::deleteWord);
        this.addBuiltinWidget(hashMap, "digit-argument", this::digitArgument);
        this.addBuiltinWidget(hashMap, "do-lowercase-version", this::doLowercaseVersion);
        this.addBuiltinWidget(hashMap, "down-case-word", this::downCaseWord);
        this.addBuiltinWidget(hashMap, "down-line", this::downLine);
        this.addBuiltinWidget(hashMap, "down-line-or-history", this::downLineOrHistory);
        this.addBuiltinWidget(hashMap, "down-line-or-search", this::downLineOrSearch);
        this.addBuiltinWidget(hashMap, "down-history", this::downHistory);
        this.addBuiltinWidget(hashMap, "emacs-editing-mode", this::emacsEditingMode);
        this.addBuiltinWidget(hashMap, "emacs-backward-word", this::emacsBackwardWord);
        this.addBuiltinWidget(hashMap, "emacs-forward-word", this::emacsForwardWord);
        this.addBuiltinWidget(hashMap, "end-of-buffer-or-history", this::endOfBufferOrHistory);
        this.addBuiltinWidget(hashMap, "end-of-history", this::endOfHistory);
        this.addBuiltinWidget(hashMap, "end-of-line", this::endOfLine);
        this.addBuiltinWidget(hashMap, "end-of-line-hist", this::endOfLineHist);
        this.addBuiltinWidget(hashMap, "exchange-point-and-mark", this::exchangePointAndMark);
        this.addBuiltinWidget(hashMap, "expand-history", this::expandHistory);
        this.addBuiltinWidget(hashMap, "expand-or-complete", this::expandOrComplete);
        this.addBuiltinWidget(hashMap, "expand-or-complete-prefix", this::expandOrCompletePrefix);
        this.addBuiltinWidget(hashMap, "expand-word", this::expandWord);
        this.addBuiltinWidget(hashMap, "fresh-line", this::freshLine);
        this.addBuiltinWidget(hashMap, "forward-char", this::forwardChar);
        this.addBuiltinWidget(hashMap, "forward-word", this::forwardWord);
        this.addBuiltinWidget(hashMap, "history-incremental-search-backward", this::historyIncrementalSearchBackward);
        this.addBuiltinWidget(hashMap, "history-incremental-search-forward", this::historyIncrementalSearchForward);
        this.addBuiltinWidget(hashMap, "history-search-backward", this::historySearchBackward);
        this.addBuiltinWidget(hashMap, "history-search-forward", this::historySearchForward);
        this.addBuiltinWidget(hashMap, "insert-close-curly", this::insertCloseCurly);
        this.addBuiltinWidget(hashMap, "insert-close-paren", this::insertCloseParen);
        this.addBuiltinWidget(hashMap, "insert-close-square", this::insertCloseSquare);
        this.addBuiltinWidget(hashMap, "insert-comment", this::insertComment);
        this.addBuiltinWidget(hashMap, "kill-buffer", this::killBuffer);
        this.addBuiltinWidget(hashMap, "kill-line", this::killLine);
        this.addBuiltinWidget(hashMap, "kill-region", this::killRegion);
        this.addBuiltinWidget(hashMap, "kill-whole-line", this::killWholeLine);
        this.addBuiltinWidget(hashMap, "kill-word", this::killWord);
        this.addBuiltinWidget(hashMap, "list-choices", this::listChoices);
        this.addBuiltinWidget(hashMap, "menu-complete", this::menuComplete);
        this.addBuiltinWidget(hashMap, "menu-expand-or-complete", this::menuExpandOrComplete);
        this.addBuiltinWidget(hashMap, "neg-argument", this::negArgument);
        this.addBuiltinWidget(hashMap, "overwrite-mode", this::overwriteMode);
        this.addBuiltinWidget(hashMap, "quoted-insert", this::quotedInsert);
        this.addBuiltinWidget(hashMap, "redisplay", this::redisplay);
        this.addBuiltinWidget(hashMap, "redraw-line", this::redrawLine);
        this.addBuiltinWidget(hashMap, "redo", this::redo);
        this.addBuiltinWidget(hashMap, "self-insert", this::selfInsert);
        this.addBuiltinWidget(hashMap, "self-insert-unmeta", this::selfInsertUnmeta);
        this.addBuiltinWidget(hashMap, "abort", this::sendBreak);
        this.addBuiltinWidget(hashMap, "set-mark-command", this::setMarkCommand);
        this.addBuiltinWidget(hashMap, "transpose-chars", this::transposeChars);
        this.addBuiltinWidget(hashMap, "transpose-words", this::transposeWords);
        this.addBuiltinWidget(hashMap, "undefined-key", this::undefinedKey);
        this.addBuiltinWidget(hashMap, "universal-argument", this::universalArgument);
        this.addBuiltinWidget(hashMap, "undo", this::undo);
        this.addBuiltinWidget(hashMap, "up-case-word", this::upCaseWord);
        this.addBuiltinWidget(hashMap, "up-history", this::upHistory);
        this.addBuiltinWidget(hashMap, "up-line", this::upLine);
        this.addBuiltinWidget(hashMap, "up-line-or-history", this::upLineOrHistory);
        this.addBuiltinWidget(hashMap, "up-line-or-search", this::upLineOrSearch);
        this.addBuiltinWidget(hashMap, "vi-add-eol", this::viAddEol);
        this.addBuiltinWidget(hashMap, "vi-add-next", this::viAddNext);
        this.addBuiltinWidget(hashMap, "vi-backward-char", this::viBackwardChar);
        this.addBuiltinWidget(hashMap, "vi-backward-delete-char", this::viBackwardDeleteChar);
        this.addBuiltinWidget(hashMap, "vi-backward-blank-word", this::viBackwardBlankWord);
        this.addBuiltinWidget(hashMap, "vi-backward-blank-word-end", this::viBackwardBlankWordEnd);
        this.addBuiltinWidget(hashMap, "vi-backward-kill-word", this::viBackwardKillWord);
        this.addBuiltinWidget(hashMap, "vi-backward-word", this::viBackwardWord);
        this.addBuiltinWidget(hashMap, "vi-backward-word-end", this::viBackwardWordEnd);
        this.addBuiltinWidget(hashMap, "vi-beginning-of-line", this::viBeginningOfLine);
        this.addBuiltinWidget(hashMap, "vi-cmd-mode", this::viCmdMode);
        this.addBuiltinWidget(hashMap, "vi-digit-or-beginning-of-line", this::viDigitOrBeginningOfLine);
        this.addBuiltinWidget(hashMap, "vi-down-line-or-history", this::viDownLineOrHistory);
        this.addBuiltinWidget(hashMap, "vi-change-to", this::viChange);
        this.addBuiltinWidget(hashMap, "vi-change-eol", this::viChangeEol);
        this.addBuiltinWidget(hashMap, "vi-change-whole-line", this::viChangeWholeLine);
        this.addBuiltinWidget(hashMap, "vi-delete-char", this::viDeleteChar);
        this.addBuiltinWidget(hashMap, "vi-delete", this::viDelete);
        this.addBuiltinWidget(hashMap, "vi-end-of-line", this::viEndOfLine);
        this.addBuiltinWidget(hashMap, "vi-kill-eol", this::viKillEol);
        this.addBuiltinWidget(hashMap, "vi-first-non-blank", this::viFirstNonBlank);
        this.addBuiltinWidget(hashMap, "vi-find-next-char", this::viFindNextChar);
        this.addBuiltinWidget(hashMap, "vi-find-next-char-skip", this::viFindNextCharSkip);
        this.addBuiltinWidget(hashMap, "vi-find-prev-char", this::viFindPrevChar);
        this.addBuiltinWidget(hashMap, "vi-find-prev-char-skip", this::viFindPrevCharSkip);
        this.addBuiltinWidget(hashMap, "vi-forward-blank-word", this::viForwardBlankWord);
        this.addBuiltinWidget(hashMap, "vi-forward-blank-word-end", this::viForwardBlankWordEnd);
        this.addBuiltinWidget(hashMap, "vi-forward-char", this::viForwardChar);
        this.addBuiltinWidget(hashMap, "vi-forward-word", this::viForwardWord);
        this.addBuiltinWidget(hashMap, "vi-forward-word", this::viForwardWord);
        this.addBuiltinWidget(hashMap, "vi-forward-word-end", this::viForwardWordEnd);
        this.addBuiltinWidget(hashMap, "vi-history-search-backward", this::viHistorySearchBackward);
        this.addBuiltinWidget(hashMap, "vi-history-search-forward", this::viHistorySearchForward);
        this.addBuiltinWidget(hashMap, "vi-insert", this::viInsert);
        this.addBuiltinWidget(hashMap, "vi-insert-bol", this::viInsertBol);
        this.addBuiltinWidget(hashMap, "vi-insert-comment", this::viInsertComment);
        this.addBuiltinWidget(hashMap, "vi-join", this::viJoin);
        this.addBuiltinWidget(hashMap, "vi-kill-line", this::viKillWholeLine);
        this.addBuiltinWidget(hashMap, "vi-match-bracket", this::viMatchBracket);
        this.addBuiltinWidget(hashMap, "vi-open-line-above", this::viOpenLineAbove);
        this.addBuiltinWidget(hashMap, "vi-open-line-below", this::viOpenLineBelow);
        this.addBuiltinWidget(hashMap, "vi-put-after", this::viPutAfter);
        this.addBuiltinWidget(hashMap, "vi-put-before", this::viPutBefore);
        this.addBuiltinWidget(hashMap, "vi-repeat-find", this::viRepeatFind);
        this.addBuiltinWidget(hashMap, "vi-repeat-search", this::viRepeatSearch);
        this.addBuiltinWidget(hashMap, "vi-replace-chars", this::viReplaceChars);
        this.addBuiltinWidget(hashMap, "vi-rev-repeat-find", this::viRevRepeatFind);
        this.addBuiltinWidget(hashMap, "vi-rev-repeat-search", this::viRevRepeatSearch);
        this.addBuiltinWidget(hashMap, "vi-swap-case", this::viSwapCase);
        this.addBuiltinWidget(hashMap, "vi-up-line-or-history", this::viUpLineOrHistory);
        this.addBuiltinWidget(hashMap, "vi-yank", this::viYankTo);
        this.addBuiltinWidget(hashMap, "vi-yank-whole-line", this::viYankWholeLine);
        this.addBuiltinWidget(hashMap, "visual-line-mode", this::visualLineMode);
        this.addBuiltinWidget(hashMap, "visual-mode", this::visualMode);
        this.addBuiltinWidget(hashMap, "what-cursor-position", this::whatCursorPosition);
        this.addBuiltinWidget(hashMap, "yank", this::yank);
        this.addBuiltinWidget(hashMap, "yank-pop", this::yankPop);
        this.addBuiltinWidget(hashMap, "mouse", this::mouse);
        this.addBuiltinWidget(hashMap, "begin-paste", this::beginPaste);
        this.addBuiltinWidget(hashMap, "terminal-focus-in", this::focusIn);
        this.addBuiltinWidget(hashMap, "terminal-focus-out", this::focusOut);
        return hashMap;
    }
    
    private void addBuiltinWidget(final Map<String, Widget> map, final String s, final Widget widget) {
        map.put(s, this.namedWidget(s, widget));
    }
    
    private Widget namedWidget(final String s, final Widget widget) {
        return new Widget(this, s, widget) {
            final String val$name;
            final Widget val$widget;
            final LineReaderImpl this$0;
            
            LineReaderImpl$1(final LineReaderImpl this$0, final String val$name, final Widget val$widget) {
                this.this$0 = this$0;
                this.val$name = val$name;
                this.val$widget = val$widget;
                super();
            }
            
            @Override
            public String toString() {
                return this.val$name;
            }
            
            @Override
            public boolean apply() {
                return this.val$widget.apply();
            }
        };
    }
    
    public boolean redisplay() {
        this.redisplay(true);
        return true;
    }
    
    protected void redisplay(final boolean b) {
        try {
            this.lock.lock();
            if (this.skipRedisplay) {
                this.skipRedisplay = false;
                return;
            }
            final Status status = Status.getStatus(this.terminal, false);
            if (status != null) {
                status.redraw();
            }
            if (this.size.getRows() > 0 && this.size.getRows() < 3) {
                final AttributedStringBuilder tabs = new AttributedStringBuilder().tabs(4);
                tabs.append(this.prompt);
                this.concat(this.getHighlightedBuffer(this.buf.toString()).columnSplitLength(0), tabs);
                AttributedString attributedString = tabs.toAttributedString();
                tabs.setLength(0);
                tabs.append(this.prompt);
                String s = this.buf.upToCursor();
                if (this.maskingCallback != null) {
                    s = this.maskingCallback.display(s);
                }
                this.concat(new AttributedString(s).columnSplitLength(0), tabs);
                final AttributedString attributedString2 = tabs.toAttributedString();
                final int wcwidth = WCWidth.wcwidth(8230);
                final int columns = this.size.getColumns();
                final int i = attributedString2.columnLength();
                final int n = columns / 2 + 1;
                while (i <= this.smallTerminalOffset + wcwidth) {
                    this.smallTerminalOffset -= n;
                }
                while (i >= this.smallTerminalOffset + columns - wcwidth) {
                    this.smallTerminalOffset += n;
                }
                if (this.smallTerminalOffset > 0) {
                    tabs.setLength(0);
                    tabs.append((CharSequence)"\u2026");
                    tabs.append(attributedString.columnSubSequence(this.smallTerminalOffset + wcwidth, 0));
                    attributedString = tabs.toAttributedString();
                }
                if (attributedString.columnLength() >= this.smallTerminalOffset + columns) {
                    tabs.setLength(0);
                    tabs.append(attributedString.columnSubSequence(0, columns - wcwidth));
                    tabs.append((CharSequence)"\u2026");
                    attributedString = tabs.toAttributedString();
                }
                this.display.update(Collections.singletonList(attributedString), i - this.smallTerminalOffset, b);
                return;
            }
            final ArrayList<AttributedString> list = new ArrayList<AttributedString>();
            final AttributedString displayedBufferWithPrompts = this.getDisplayedBufferWithPrompts(list);
            List<AttributedString> columnSplitLength;
            if (this.size.getColumns() <= 0) {
                columnSplitLength = new ArrayList<AttributedString>();
                columnSplitLength.add(displayedBufferWithPrompts);
            }
            else {
                columnSplitLength = displayedBufferWithPrompts.columnSplitLength(this.size.getColumns(), true, this.display.delayLineWrap());
            }
            List<AttributedString> columnSplitLength2;
            if (this.rightPrompt.length() == 0 || this.size.getColumns() <= 0) {
                columnSplitLength2 = new ArrayList<AttributedString>();
            }
            else {
                columnSplitLength2 = this.rightPrompt.columnSplitLength(this.size.getColumns());
            }
            while (columnSplitLength.size() < columnSplitLength2.size()) {
                columnSplitLength.add(new AttributedString(""));
            }
            for (int j = 0; j < columnSplitLength2.size(); ++j) {
                columnSplitLength.set(j, this.addRightPrompt(columnSplitLength2.get(j), columnSplitLength.get(j)));
            }
            int cursorPos = -1;
            if (this.size.getColumns() > 0) {
                final AttributedStringBuilder tabs2 = new AttributedStringBuilder().tabs(4);
                tabs2.append(this.prompt);
                String s2 = this.buf.upToCursor();
                if (this.maskingCallback != null) {
                    s2 = this.maskingCallback.display(s2);
                }
                tabs2.append(this.insertSecondaryPrompts(new AttributedString(s2), list, false));
                final List<AttributedString> columnSplitLength3 = tabs2.columnSplitLength(this.size.getColumns(), false, this.display.delayLineWrap());
                if (!columnSplitLength3.isEmpty()) {
                    cursorPos = this.size.cursorPos(columnSplitLength3.size() - 1, columnSplitLength3.get(columnSplitLength3.size() - 1).columnLength());
                }
            }
            this.display.update(columnSplitLength, cursorPos, b);
        }
        finally {
            this.lock.unlock();
        }
    }
    
    private void concat(final List<AttributedString> list, final AttributedStringBuilder attributedStringBuilder) {
        if (list.size() > 1) {
            for (int i = 0; i < list.size() - 1; ++i) {
                attributedStringBuilder.append(list.get(i));
                attributedStringBuilder.style(attributedStringBuilder.style().inverse());
                attributedStringBuilder.append((CharSequence)"\\n");
                attributedStringBuilder.style(attributedStringBuilder.style().inverseOff());
            }
        }
        attributedStringBuilder.append(list.get(list.size() - 1));
    }
    
    public AttributedString getDisplayedBufferWithPrompts(final List<AttributedString> list) {
        final AttributedString insertSecondaryPrompts = this.insertSecondaryPrompts(this.getHighlightedBuffer(this.buf.toString()), list);
        final AttributedStringBuilder tabs = new AttributedStringBuilder().tabs(4);
        tabs.append(this.prompt);
        tabs.append(insertSecondaryPrompts);
        if (this.post != null) {
            tabs.append((CharSequence)"\n");
            tabs.append(this.post.get());
        }
        return tabs.toAttributedString();
    }
    
    private AttributedString getHighlightedBuffer(String display) {
        if (this.maskingCallback != null) {
            display = this.maskingCallback.display(display);
        }
        if (this.highlighter != null && !this.isSet(Option.DISABLE_HIGHLIGHTER)) {
            return this.highlighter.highlight(this, display);
        }
        return new AttributedString(display);
    }
    
    private AttributedString expandPromptPattern(String string, int n, final String s, final int n2) {
        final ArrayList<AttributedString> list = new ArrayList<AttributedString>();
        int n3 = 0;
        int size = -1;
        StringBuilder sb = null;
        StringBuilder sb2 = new StringBuilder();
        string += "%{";
        final int length = string.length();
        int char1 = -1;
        int length2 = -1;
        int n4 = 0;
        int i = 0;
        while (i < length) {
            final char char2 = string.charAt(i++);
            if (char2 == '%' && i < length) {
                int n5 = 0;
                int n6 = 0;
            Label_0566:
                while (true) {
                    char c = string.charAt(i++);
                    switch (c) {
                        case 123:
                        case 125: {
                            final String string2 = sb2.toString();
                            AttributedString fromAnsi;
                            if (n3 == 0) {
                                fromAnsi = AttributedString.fromAnsi(string2);
                                n4 += fromAnsi.columnLength();
                            }
                            else {
                                fromAnsi = new AttributedString(string2, AttributedStyle.HIDDEN);
                            }
                            if (size == list.size()) {
                                sb = sb2;
                                if (i < length) {
                                    sb2 = new StringBuilder();
                                }
                            }
                            else {
                                sb2.setLength(0);
                            }
                            list.add(fromAnsi);
                            n3 = ((c == '{') ? 1 : 0);
                            break Label_0566;
                        }
                        case 37: {
                            sb2.append(c);
                            break Label_0566;
                        }
                        case 78: {
                            sb2.append(this.getInt("line-offset", 0) + n2);
                            break Label_0566;
                        }
                        case 77: {
                            if (s != null) {
                                sb2.append(s);
                                break Label_0566;
                            }
                            break Label_0566;
                        }
                        case 80: {
                            if (n6 != 0 && n5 >= 0) {
                                n = n5;
                            }
                            if (i < length) {
                                char1 = string.charAt(i++);
                            }
                            length2 = sb2.length();
                            size = list.size();
                            break Label_0566;
                        }
                        case 45:
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57: {
                            boolean b = false;
                            if (c == '-') {
                                b = true;
                                c = string.charAt(i++);
                            }
                            n6 = 1;
                            n5 = 0;
                            while (c >= '0' && c <= '9') {
                                n5 = ((n5 < 0) ? 0 : (10 * n5)) + (c - '0');
                                c = string.charAt(i++);
                            }
                            if (b) {
                                n5 = -n5;
                            }
                            --i;
                            continue;
                        }
                        default: {
                            break Label_0566;
                        }
                    }
                }
            }
            else {
                sb2.append(char2);
            }
        }
        if (n > n4) {
            int n7 = (n - n4) / WCWidth.wcwidth(char1);
            final StringBuilder sb3 = sb;
            while (--n7 >= 0) {
                sb3.insert(length2, (char)char1);
            }
            list.set(size, AttributedString.fromAnsi(sb3.toString()));
        }
        return AttributedString.join(null, list);
    }
    
    private AttributedString insertSecondaryPrompts(final AttributedString attributedString, final List<AttributedString> list) {
        return this.insertSecondaryPrompts(attributedString, list, true);
    }
    
    private AttributedString insertSecondaryPrompts(final AttributedString attributedString, final List<AttributedString> list, final boolean b) {
        Objects.requireNonNull(list);
        final List<AttributedString> columnSplitLength = attributedString.columnSplitLength(0);
        final AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        final String string = this.getString("secondary-prompt-pattern", "%M> ");
        final boolean contains = string.contains("%M");
        final AttributedStringBuilder attributedStringBuilder2 = new AttributedStringBuilder();
        int n = 0;
        final ArrayList<String> list2 = new ArrayList<String>();
        if (b && string.contains("%P")) {
            n = this.prompt.columnLength();
            for (int i = 0; i < columnSplitLength.size() - 1; ++i) {
                attributedStringBuilder2.append(columnSplitLength.get(i)).append((CharSequence)"\n");
                String missing = "";
                if (contains) {
                    try {
                        this.parser.parse(attributedStringBuilder2.toString(), attributedStringBuilder2.length(), Parser.ParseContext.SECONDARY_PROMPT);
                    }
                    catch (EOFError eofError) {
                        missing = eofError.getMissing();
                    }
                    catch (SyntaxError syntaxError) {}
                }
                list2.add(missing);
                n = Math.max(n, this.expandPromptPattern(string, 0, missing, i + 1).columnLength());
            }
            attributedStringBuilder2.setLength(0);
        }
        int j;
        for (j = 0; j < columnSplitLength.size() - 1; ++j) {
            attributedStringBuilder.append(columnSplitLength.get(j)).append((CharSequence)"\n");
            attributedStringBuilder2.append(columnSplitLength.get(j)).append((CharSequence)"\n");
            AttributedString expandPromptPattern;
            if (b) {
                String missing2 = "";
                if (contains) {
                    if (list2.isEmpty()) {
                        try {
                            this.parser.parse(attributedStringBuilder2.toString(), attributedStringBuilder2.length(), Parser.ParseContext.SECONDARY_PROMPT);
                        }
                        catch (EOFError eofError2) {
                            missing2 = eofError2.getMissing();
                        }
                        catch (SyntaxError syntaxError2) {}
                    }
                    else {
                        missing2 = list2.get(j);
                    }
                }
                expandPromptPattern = this.expandPromptPattern(string, n, missing2, j + 1);
            }
            else {
                expandPromptPattern = list.get(j);
            }
            list.add(expandPromptPattern);
            attributedStringBuilder.append(expandPromptPattern);
        }
        attributedStringBuilder.append(columnSplitLength.get(j));
        attributedStringBuilder2.append(columnSplitLength.get(j));
        return attributedStringBuilder.toAttributedString();
    }
    
    private AttributedString addRightPrompt(final AttributedString attributedString, AttributedString attributedString2) {
        final int columnLength = attributedString.columnLength();
        final int n = (attributedString2.length() > 0 && attributedString2.charAt(attributedString2.length() - 1) == '\n') ? 1 : 0;
        final int n2 = this.size.getColumns() - columnLength - (attributedString2.columnLength() + n);
        if (n2 >= 3) {
            final AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder(this.size.getColumns());
            attributedStringBuilder.append(attributedString2, 0, (n != 0) ? (attributedString2.length() - 1) : attributedString2.length());
            for (int i = 0; i < n2; ++i) {
                attributedStringBuilder.append(' ');
            }
            attributedStringBuilder.append(attributedString);
            if (n != 0) {
                attributedStringBuilder.append('\n');
            }
            attributedString2 = attributedStringBuilder.toAttributedString();
        }
        return attributedString2;
    }
    
    protected boolean insertTab() {
        return this.isSet(Option.INSERT_TAB) && this.getLastBinding().equals("\t") && this.buf.toString().matches("(^|[\\s\\S]*\n)[\r\n\t ]*");
    }
    
    protected boolean expandHistory() {
        final String string = this.buf.toString();
        final String expandHistory = this.expander.expandHistory(this.history, string);
        if (!expandHistory.equals(string)) {
            this.buf.clear();
            this.buf.write(expandHistory);
            return true;
        }
        return false;
    }
    
    protected boolean expandWord() {
        if (this.insertTab()) {
            return this.selfInsert();
        }
        return this.doComplete(CompletionType.Expand, this.isSet(Option.MENU_COMPLETE), false);
    }
    
    protected boolean expandOrComplete() {
        if (this.insertTab()) {
            return this.selfInsert();
        }
        return this.doComplete(CompletionType.ExpandComplete, this.isSet(Option.MENU_COMPLETE), false);
    }
    
    protected boolean expandOrCompletePrefix() {
        if (this.insertTab()) {
            return this.selfInsert();
        }
        return this.doComplete(CompletionType.ExpandComplete, this.isSet(Option.MENU_COMPLETE), true);
    }
    
    protected boolean completeWord() {
        if (this.insertTab()) {
            return this.selfInsert();
        }
        return this.doComplete(CompletionType.Complete, this.isSet(Option.MENU_COMPLETE), false);
    }
    
    protected boolean menuComplete() {
        if (this.insertTab()) {
            return this.selfInsert();
        }
        return this.doComplete(CompletionType.Complete, true, false);
    }
    
    protected boolean menuExpandOrComplete() {
        if (this.insertTab()) {
            return this.selfInsert();
        }
        return this.doComplete(CompletionType.ExpandComplete, true, false);
    }
    
    protected boolean completePrefix() {
        if (this.insertTab()) {
            return this.selfInsert();
        }
        return this.doComplete(CompletionType.Complete, this.isSet(Option.MENU_COMPLETE), true);
    }
    
    protected boolean listChoices() {
        return this.doComplete(CompletionType.List, this.isSet(Option.MENU_COMPLETE), false);
    }
    
    protected boolean deleteCharOrList() {
        if (this.buf.cursor() != this.buf.length() || this.buf.length() == 0) {
            return this.deleteChar();
        }
        return this.doComplete(CompletionType.List, this.isSet(Option.MENU_COMPLETE), false);
    }
    
    protected boolean doComplete(CompletionType complete, final boolean b, final boolean b2) {
        if (this.getBoolean("disable-completion", false)) {
            return true;
        }
        if (!this.isSet(Option.DISABLE_EVENT_EXPANSION)) {
            try {
                if (this.expandHistory()) {
                    return true;
                }
            }
            catch (Exception ex) {
                Log.info("Error while expanding history", ex);
                return false;
            }
        }
        CompletingParsedLine wrap;
        try {
            wrap = this.wrap(this.parser.parse(this.buf.toString(), this.buf.cursor(), Parser.ParseContext.COMPLETE));
        }
        catch (Exception ex2) {
            Log.info("Error while parsing line", ex2);
            return false;
        }
        final ArrayList<Candidate> list = new ArrayList<Candidate>();
        try {
            if (this.completer != null) {
                this.completer.complete(this, wrap, list);
            }
        }
        catch (Exception ex3) {
            Log.info("Error while finding completion candidates", ex3);
            return false;
        }
        if (complete == CompletionType.ExpandComplete || complete == CompletionType.Expand) {
            final String expandVar = this.expander.expandVar(wrap.word());
            if (!wrap.word().equals(expandVar)) {
                if (b2) {
                    this.buf.backspace(wrap.wordCursor());
                }
                else {
                    this.buf.move(wrap.word().length() - wrap.wordCursor());
                    this.buf.backspace(wrap.word().length());
                }
                this.buf.write(expandVar);
                return true;
            }
            if (complete == CompletionType.Expand) {
                return false;
            }
            complete = CompletionType.Complete;
        }
        final boolean set = this.isSet(Option.CASE_INSENSITIVE);
        final int int1 = this.getInt("errors", 2);
        final HashMap<String, List<Candidate>> hashMap = new HashMap<String, List<Candidate>>();
        for (final Candidate candidate : list) {
            ((List<Candidate>)hashMap.computeIfAbsent(AttributedString.fromAnsi(candidate.value()).toString(), (Function<? super String, ?>)LineReaderImpl::lambda$doComplete$15)).add(candidate);
        }
        List<Function<Map, Map>> list2;
        Predicate predicate;
        if (b2) {
            final String word = wrap.word();
            final String substring = (set ? word.toLowerCase() : word).substring(0, wrap.wordCursor());
            list2 = Arrays.asList(this.simpleMatcher(LineReaderImpl::lambda$doComplete$16), this.simpleMatcher(LineReaderImpl::lambda$doComplete$17), this.typoMatcher(substring, int1, set));
            predicate = LineReaderImpl::lambda$doComplete$18;
        }
        else if (this.isSet(Option.COMPLETE_IN_WORD)) {
            final String word2 = wrap.word();
            final String s = set ? word2.toLowerCase() : word2;
            final String substring2 = s.substring(0, wrap.wordCursor());
            final String substring3 = s.substring(wrap.wordCursor());
            list2 = Arrays.asList(this.simpleMatcher((Predicate<String>)LineReaderImpl::lambda$doComplete$19), this.simpleMatcher((Predicate<String>)LineReaderImpl::lambda$doComplete$20), this.typoMatcher(s, int1, set));
            predicate = LineReaderImpl::lambda$doComplete$21;
        }
        else {
            final String word3 = wrap.word();
            final String s2 = set ? word3.toLowerCase() : word3;
            list2 = Arrays.asList(this.simpleMatcher((Predicate<String>)LineReaderImpl::lambda$doComplete$22), this.simpleMatcher((Predicate<String>)LineReaderImpl::lambda$doComplete$23), this.typoMatcher(s2, int1, set));
            predicate = LineReaderImpl::lambda$doComplete$24;
        }
        Object emptyMap = Collections.emptyMap();
        final Iterator<Function<Map, Map>> iterator2 = list2.iterator();
        while (iterator2.hasNext()) {
            emptyMap = iterator2.next().apply(hashMap);
            if (!((Map)emptyMap).isEmpty()) {
                break;
            }
        }
        if (((Map)emptyMap).isEmpty()) {
            return false;
        }
        if (complete == CompletionType.List) {
            final List<? super Object> list3 = ((Map<String, Object>)emptyMap).entrySet().stream().flatMap((Function<? super Object, ? extends Stream<?>>)LineReaderImpl::lambda$doComplete$25).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
            this.doList((List<Candidate>)list3, wrap.word(), false, wrap::escape);
            return !list3.isEmpty();
        }
        Candidate candidate2 = null;
        if (((Map)emptyMap).size() == 1) {
            candidate2 = ((Map<String, Object>)emptyMap).values().stream().flatMap((Function<? super Object, ? extends Stream<? extends Candidate>>)Collection::stream).findFirst().orElse(null);
        }
        else if (this.isSet(Option.RECOGNIZE_EXACT)) {
            candidate2 = ((Map<String, Object>)emptyMap).values().stream().flatMap((Function<? super Object, ? extends Stream<? extends Candidate>>)Collection::stream).filter(Candidate::complete).filter(LineReaderImpl::lambda$doComplete$26).findFirst().orElse(null);
        }
        if (candidate2 != null && !candidate2.value().isEmpty()) {
            if (b2) {
                this.buf.backspace(wrap.rawWordCursor());
            }
            else {
                this.buf.move(wrap.rawWordLength() - wrap.rawWordCursor());
                this.buf.backspace(wrap.rawWordLength());
            }
            this.buf.write(wrap.escape(candidate2.value(), candidate2.complete()));
            if (candidate2.complete()) {
                if (this.buf.currChar() != 32) {
                    this.buf.write(" ");
                }
                else {
                    this.buf.move(1);
                }
            }
            if (candidate2.suffix() != null) {
                this.redisplay();
                final Binding binding = this.readBinding(this.getKeys());
                if (binding != null) {
                    final String string = this.getString("REMOVE_SUFFIX_CHARS", " \t\n;&|");
                    final String s3 = (binding instanceof Reference) ? ((Reference)binding).name() : null;
                    if (("self-insert".equals(s3) && string.indexOf(this.getLastBinding().charAt(0)) >= 0) || "accept-line".equals(s3)) {
                        this.buf.backspace(candidate2.suffix().length());
                        if (this.getLastBinding().charAt(0) != ' ') {
                            this.buf.write(32);
                        }
                    }
                    this.pushBackBinding(true);
                }
            }
            return true;
        }
        final List<? super Object> list4 = ((Map<String, Object>)emptyMap).entrySet().stream().flatMap((Function<? super Object, ? extends Stream<?>>)LineReaderImpl::lambda$doComplete$27).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        if (b) {
            this.buf.move(wrap.word().length() - wrap.wordCursor());
            this.buf.backspace(wrap.word().length());
            this.doMenu((List<Candidate>)list4, wrap.word(), wrap::escape);
            return true;
        }
        String s4;
        if (b2) {
            s4 = wrap.word().substring(0, wrap.wordCursor());
        }
        else {
            s4 = wrap.word();
            this.buf.move(wrap.rawWordLength() - wrap.rawWordCursor());
        }
        String s5 = null;
        for (final String s6 : ((Map<String, Object>)emptyMap).keySet()) {
            s5 = ((s5 == null) ? s6 : this.getCommonStart(s5, s6, set));
        }
        if (s5.startsWith(s4) && !s5.equals(s4)) {
            this.buf.backspace(wrap.rawWordLength());
            this.buf.write(wrap.escape(s5, false));
            s4 = s5;
            if (((!this.isSet(Option.AUTO_LIST) && this.isSet(Option.AUTO_MENU)) || (this.isSet(Option.AUTO_LIST) && this.isSet(Option.LIST_AMBIGUOUS))) && !this.nextBindingIsComplete()) {
                return true;
            }
        }
        if (this.isSet(Option.AUTO_LIST) && !this.doList((List<Candidate>)list4, s4, true, wrap::escape)) {
            return true;
        }
        if (this.isSet(Option.AUTO_MENU)) {
            this.buf.backspace(s4.length());
            this.doMenu((List<Candidate>)list4, wrap.word(), wrap::escape);
        }
        return true;
    }
    
    private CompletingParsedLine wrap(final ParsedLine parsedLine) {
        if (parsedLine instanceof CompletingParsedLine) {
            return (CompletingParsedLine)parsedLine;
        }
        return new CompletingParsedLine(this, parsedLine) {
            final ParsedLine val$line;
            final LineReaderImpl this$0;
            
            LineReaderImpl$2(final LineReaderImpl this$0, final ParsedLine val$line) {
                this.this$0 = this$0;
                this.val$line = val$line;
                super();
            }
            
            @Override
            public String word() {
                return this.val$line.word();
            }
            
            @Override
            public int wordCursor() {
                return this.val$line.wordCursor();
            }
            
            @Override
            public int wordIndex() {
                return this.val$line.wordIndex();
            }
            
            @Override
            public List<String> words() {
                return this.val$line.words();
            }
            
            @Override
            public String line() {
                return this.val$line.line();
            }
            
            @Override
            public int cursor() {
                return this.val$line.cursor();
            }
            
            @Override
            public CharSequence escape(final CharSequence charSequence, final boolean b) {
                return charSequence;
            }
            
            @Override
            public int rawWordCursor() {
                return this.wordCursor();
            }
            
            @Override
            public int rawWordLength() {
                return this.word().length();
            }
        };
    }
    
    protected Comparator<Candidate> getCandidateComparator(final boolean b, final String s) {
        return Comparator.comparing((Function<? super Candidate, ?>)Candidate::value, Comparator.comparingInt(this::lambda$getCandidateComparator$28)).thenComparing((Function<? super Candidate, ?>)Candidate::value, Comparator.comparingInt(String::length)).thenComparing(Comparator.naturalOrder());
    }
    
    protected String getOthersGroupName() {
        return this.getString("OTHERS_GROUP_NAME", "others");
    }
    
    protected String getOriginalGroupName() {
        return this.getString("ORIGINAL_GROUP_NAME", "original");
    }
    
    protected Comparator<String> getGroupComparator() {
        return Comparator.comparingInt((ToIntFunction<? super String>)this::lambda$getGroupComparator$29).thenComparing((Function<? super String, ?>)String::toLowerCase, Comparator.naturalOrder());
    }
    
    private void mergeCandidates(final List<Candidate> list) {
        final HashMap<String, List<?>> hashMap = (HashMap<String, List<?>>)new HashMap<String, Object>();
        for (final Candidate candidate : list) {
            if (candidate.key() != null) {
                hashMap.computeIfAbsent(candidate.key(), (Function<? super String, ? extends List<Candidate>>)LineReaderImpl::lambda$mergeCandidates$30).add(candidate);
            }
        }
        if (!hashMap.isEmpty()) {
            for (final List<Candidate> list2 : hashMap.values()) {
                if (list2.size() >= 1) {
                    list.removeAll(list2);
                    list2.sort(Comparator.comparing((Function<? super Object, ? extends Comparable>)Candidate::value));
                    final Candidate candidate2 = list2.get(0);
                    list.add(new Candidate(candidate2.value(), list2.stream().map((Function<? super Object, ?>)Candidate::displ).collect((Collector<? super Object, ?, String>)Collectors.joining(" ")), candidate2.group(), candidate2.descr(), candidate2.suffix(), null, candidate2.complete()));
                }
            }
        }
    }
    
    private Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>> simpleMatcher(final Predicate<String> predicate) {
        return LineReaderImpl::lambda$simpleMatcher$32;
    }
    
    private Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>> typoMatcher(final String s, final int n, final boolean b) {
        return this::lambda$typoMatcher$35;
    }
    
    private int distance(final String s, final String s2) {
        if (s.length() < s2.length()) {
            return Math.min(Levenshtein.distance(s, s2.substring(0, Math.min(s2.length(), s.length()))), Levenshtein.distance(s, s2));
        }
        return Levenshtein.distance(s, s2);
    }
    
    protected boolean nextBindingIsComplete() {
        this.redisplay();
        final Binding binding = this.readBinding(this.getKeys(), this.keyMaps.get("menu"));
        if (binding instanceof Reference && "menu-complete".equals(((Reference)binding).name())) {
            return true;
        }
        this.pushBackBinding();
        return false;
    }
    
    protected boolean doMenu(final List<Candidate> list, final String s, final BiFunction<CharSequence, Boolean, CharSequence> biFunction) {
        final ArrayList<Candidate> list2 = new ArrayList<Candidate>();
        list.sort(this.getCandidateComparator(this.isSet(Option.CASE_INSENSITIVE), s));
        this.mergeCandidates(list);
        this.computePost(list, null, list2, s);
        final MenuSupport post = new MenuSupport(list, s, biFunction);
        this.post = post;
        this.redisplay();
        Binding binding;
        while ((binding = this.readBinding(this.getKeys(), this.keyMaps.get("menu"))) != null) {
            final String s3;
            final String s2 = s3 = ((binding instanceof Reference) ? ((Reference)binding).name() : "");
            switch (s3) {
                case "menu-complete": {
                    post.next();
                    break;
                }
                case "reverse-menu-complete": {
                    post.previous();
                    break;
                }
                case "up-line-or-history":
                case "up-line-or-search": {
                    post.up();
                    break;
                }
                case "down-line-or-history":
                case "down-line-or-search": {
                    post.down();
                    break;
                }
                case "forward-char": {
                    post.right();
                    break;
                }
                case "backward-char": {
                    post.left();
                    break;
                }
                case "clear-screen": {
                    this.clearScreen();
                    break;
                }
                default: {
                    final Candidate completion = post.completion();
                    if (completion.suffix() != null) {
                        final String string = this.getString("REMOVE_SUFFIX_CHARS", " \t\n;&|");
                        if (("self-insert".equals(s2) && string.indexOf(this.getLastBinding().charAt(0)) >= 0) || "backward-delete-char".equals(s2)) {
                            this.buf.backspace(completion.suffix().length());
                        }
                    }
                    if (completion.complete() && this.getLastBinding().charAt(0) != ' ' && ("self-insert".equals(s2) || this.getLastBinding().charAt(0) != ' ')) {
                        this.buf.write(32);
                    }
                    if (!"accept-line".equals(s2) && (!"self-insert".equals(s2) || completion.suffix() == null || !completion.suffix().startsWith(this.getLastBinding()))) {
                        this.pushBackBinding(true);
                    }
                    this.post = null;
                    return true;
                }
            }
            this.redisplay();
        }
        return false;
    }
    
    protected boolean doList(final List<Candidate> list, final String s, final boolean b, final BiFunction<CharSequence, Boolean, CharSequence> biFunction) {
        this.mergeCandidates(list);
        final int size = this.insertSecondaryPrompts(AttributedStringBuilder.append(this.prompt, this.buf.toString()), new ArrayList<AttributedString>()).columnSplitLength(this.size.getColumns(), false, this.display.delayLineWrap()).size();
        final PostResult computePost = this.computePost(list, null, null, s);
        final int lines = computePost.lines;
        final int int1 = this.getInt("list-max", 100);
        if ((int1 > 0 && list.size() >= int1) || lines >= this.size.getRows() - size) {
            this.post = this::lambda$doList$36;
            this.redisplay(true);
            final int character = this.readCharacter();
            if (character != 121 && character != 89 && character != 9) {
                this.post = null;
                return false;
            }
        }
        final int set = this.isSet(Option.CASE_INSENSITIVE) ? 1 : 0;
        final StringBuilder sb = new StringBuilder();
        while (true) {
            final String string = s + sb.toString();
            List<? super Object> list2;
            if (sb.length() > 0) {
                list2 = list.stream().filter(LineReaderImpl::lambda$doList$37).sorted((Comparator<? super Object>)this.getCandidateComparator((boolean)(set != 0), string)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
            }
            else {
                list2 = list.stream().sorted((Comparator<? super Object>)this.getCandidateComparator((boolean)(set != 0), string)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
            }
            this.post = this::lambda$doList$38;
            if (!b) {
                return false;
            }
            this.redisplay();
            final Binding binding = this.doReadBinding(this.getKeys(), null);
            if (binding instanceof Reference) {
                final String name = ((Reference)binding).name();
                if ("backward-delete-char".equals(name) || "vi-backward-delete-char".equals(name)) {
                    if (sb.length() == 0) {
                        this.pushBackBinding();
                        this.post = null;
                        return false;
                    }
                    sb.setLength(sb.length() - 1);
                    this.buf.backspace();
                }
                else if ("self-insert".equals(name)) {
                    sb.append(this.getLastBinding());
                    this.buf.write(this.getLastBinding());
                    if (list2.isEmpty()) {
                        this.post = null;
                        return false;
                    }
                    continue;
                }
                else {
                    if ("\t".equals(this.getLastBinding())) {
                        if (list2.size() == 1 || sb.length() > 0) {
                            this.post = null;
                            this.pushBackBinding();
                        }
                        else if (this.isSet(Option.AUTO_MENU)) {
                            this.buf.backspace(biFunction.apply(string, false).length());
                            this.doMenu((List<Candidate>)list2, string, biFunction);
                        }
                        return false;
                    }
                    this.pushBackBinding();
                    this.post = null;
                    return false;
                }
            }
            else {
                if (binding == null) {
                    this.post = null;
                    return false;
                }
                continue;
            }
        }
    }
    
    protected PostResult computePost(final List<Candidate> list, final Candidate candidate, final List<Candidate> list2, final String s) {
        return this.computePost(list, candidate, list2, s, this.display::wcwidth, this.size.getColumns(), this.isSet(Option.AUTO_GROUP), this.isSet(Option.GROUP), this.isSet(Option.LIST_ROWS_FIRST));
    }
    
    protected PostResult computePost(final List<Candidate> list, final Candidate candidate, final List<Candidate> list2, final String s, final Function<String, Integer> function, final int n, final boolean b, final boolean b2, final boolean b3) {
        final ArrayList<ArrayList<String>> list3 = new ArrayList<ArrayList<String>>();
        if (b2) {
            final Comparator<String> groupComparator = this.getGroupComparator();
            final Cloneable cloneable = (groupComparator != null) ? new TreeMap<Object, Object>(groupComparator) : new LinkedHashMap<Object, Object>();
            for (final Candidate candidate2 : list) {
                final String group = candidate2.group();
                ((Map<String, Map<String, Candidate>>)cloneable).computeIfAbsent((group != null) ? group : "", (Function<? super String, ? extends Map<String, Candidate>>)LineReaderImpl::lambda$computePost$39).put(candidate2.value(), candidate2);
            }
            for (final Map.Entry<String, Map<String, Candidate>> entry : ((Map<String, Map<String, Candidate>>)cloneable).entrySet()) {
                String othersGroupName = entry.getKey();
                if (othersGroupName.isEmpty() && ((Map)cloneable).size() > 1) {
                    othersGroupName = this.getOthersGroupName();
                }
                if (!othersGroupName.isEmpty() && b) {
                    list3.add((ArrayList<String>)othersGroupName);
                }
                list3.add((String)new ArrayList<String>(entry.getValue().values()));
                if (list2 != null) {
                    list2.addAll(entry.getValue().values());
                }
            }
        }
        else {
            final LinkedHashSet<String> set = new LinkedHashSet<String>();
            final TreeMap<String, Candidate> treeMap = new TreeMap<String, Candidate>();
            for (final Candidate candidate3 : list) {
                final String group2 = candidate3.group();
                if (group2 != null) {
                    set.add(group2);
                }
                treeMap.put(candidate3.value(), candidate3);
            }
            if (b) {
                list3.addAll((Collection<?>)set);
            }
            list3.add(new ArrayList<String>(treeMap.values()));
            if (list2 != null) {
                list2.addAll(treeMap.values());
            }
        }
        return this.toColumns((List<Object>)list3, candidate, s, function, n, b3);
    }
    
    protected PostResult toColumns(final List<Object> list, final Candidate candidate, final String s, final Function<String, Integer> function, final int n, final boolean b) {
        final int[] array = new int[2];
        int n2 = 0;
        for (final String next : list) {
            if (next instanceof String) {
                n2 = Math.max(n2, function.apply(next));
            }
            else {
                if (!(next instanceof List)) {
                    continue;
                }
                for (final Candidate candidate2 : (List<Candidate>)next) {
                    int intValue = function.apply(candidate2.displ());
                    if (candidate2.descr() != null) {
                        intValue = ++intValue + "(".length() + function.apply(candidate2.descr()) + ")".length();
                    }
                    n2 = Math.max(n2, intValue);
                }
            }
        }
        final AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        final Iterator<String> iterator3 = list.iterator();
        while (iterator3.hasNext()) {
            this.toColumns(iterator3.next(), n, n2, attributedStringBuilder, candidate, s, b, array);
        }
        if (attributedStringBuilder.length() > 0 && attributedStringBuilder.charAt(attributedStringBuilder.length() - 1) == '\n') {
            attributedStringBuilder.setLength(attributedStringBuilder.length() - 1);
        }
        return new PostResult(attributedStringBuilder.toAttributedString(), array[0], array[1]);
    }
    
    protected void toColumns(final Object o, final int n, int min, final AttributedStringBuilder attributedStringBuilder, final Candidate candidate, final String s, final boolean b, final int[] array) {
        if (min <= 0 || n <= 0) {
            return;
        }
        if (o instanceof String) {
            attributedStringBuilder.style(this.getCompletionStyleGroup()).append((CharSequence)o).style(AttributedStyle.DEFAULT).append((CharSequence)"\n");
            final int n2 = 0;
            ++array[n2];
        }
        else if (o instanceof List) {
            final List list = (List)o;
            int n3;
            for (min = Math.min(n, min), n3 = n / min; n3 > 1 && n3 * min + (n3 - 1) * 3 >= n; --n3) {}
            final int n4 = (list.size() + n3 - 1) / n3;
            final int n5 = (list.size() + n4 - 1) / n4;
            IntBinaryOperator intBinaryOperator;
            if (b) {
                intBinaryOperator = LineReaderImpl::lambda$toColumns$40;
            }
            else {
                intBinaryOperator = LineReaderImpl::lambda$toColumns$41;
            }
            for (int i = 0; i < n4; ++i) {
                for (int j = 0; j < n5; ++j) {
                    final int applyAsInt = intBinaryOperator.applyAsInt(i, j);
                    if (applyAsInt < list.size()) {
                        final Candidate candidate2 = list.get(applyAsInt);
                        final boolean b2 = j < n5 - 1 && intBinaryOperator.applyAsInt(i, j + 1) < list.size();
                        final AttributedString fromAnsi = AttributedString.fromAnsi(candidate2.displ());
                        AttributedString attributedString = AttributedString.fromAnsi(candidate2.descr());
                        final int columnLength = fromAnsi.columnLength();
                        int n6 = 0;
                        if (attributedString != null) {
                            final int n7 = min - (columnLength + 1 + "(".length() + ")".length());
                            int n8 = attributedString.columnLength();
                            if (n8 > n7) {
                                attributedString = AttributedStringBuilder.append(attributedString.columnSubSequence(0, n7 - WCWidth.wcwidth(8230)), "\u2026");
                                n8 = attributedString.columnLength();
                            }
                            attributedString = AttributedStringBuilder.append("(", attributedString, ")");
                            n6 = n8 + ("(".length() + ")".length());
                        }
                        if (candidate2 == candidate) {
                            array[1] = i;
                            attributedStringBuilder.style(this.getCompletionStyleSelection());
                            if (fromAnsi.toString().regionMatches(this.isSet(Option.CASE_INSENSITIVE), 0, s, 0, s.length())) {
                                attributedStringBuilder.append((CharSequence)fromAnsi.toString(), 0, s.length());
                                attributedStringBuilder.append((CharSequence)fromAnsi.toString(), s.length(), fromAnsi.length());
                            }
                            else {
                                attributedStringBuilder.append((CharSequence)fromAnsi.toString());
                            }
                            for (int k = 0; k < min - columnLength - n6; ++k) {
                                attributedStringBuilder.append(' ');
                            }
                            if (attributedString != null) {
                                attributedStringBuilder.append(attributedString);
                            }
                            attributedStringBuilder.style(AttributedStyle.DEFAULT);
                        }
                        else {
                            if (fromAnsi.toString().regionMatches(this.isSet(Option.CASE_INSENSITIVE), 0, s, 0, s.length())) {
                                attributedStringBuilder.style(this.getCompletionStyleStarting());
                                attributedStringBuilder.append(fromAnsi, 0, s.length());
                                attributedStringBuilder.style(AttributedStyle.DEFAULT);
                                attributedStringBuilder.append(fromAnsi, s.length(), fromAnsi.length());
                            }
                            else {
                                attributedStringBuilder.append(fromAnsi);
                            }
                            if (attributedString != null || b2) {
                                for (int l = 0; l < min - columnLength - n6; ++l) {
                                    attributedStringBuilder.append(' ');
                                }
                            }
                            if (attributedString != null) {
                                attributedStringBuilder.style(this.getCompletionStyleDescription());
                                attributedStringBuilder.append(attributedString);
                                attributedStringBuilder.style(AttributedStyle.DEFAULT);
                            }
                        }
                        if (b2) {
                            for (int n9 = 0; n9 < 3; ++n9) {
                                attributedStringBuilder.append(' ');
                            }
                        }
                    }
                }
                attributedStringBuilder.append('\n');
            }
            final int n10 = 0;
            array[n10] += n4;
        }
    }
    
    private AttributedStyle getCompletionStyleStarting() {
        return this.getCompletionStyle("COMPLETION_STYLE_STARTING", "36");
    }
    
    protected AttributedStyle getCompletionStyleDescription() {
        return this.getCompletionStyle("COMPLETION_STYLE_DESCRIPTION", "90");
    }
    
    protected AttributedStyle getCompletionStyleGroup() {
        return this.getCompletionStyle("COMPLETION_STYLE_GROUP", "35;1");
    }
    
    protected AttributedStyle getCompletionStyleSelection() {
        return this.getCompletionStyle("COMPLETION_STYLE_SELECTION", "7");
    }
    
    protected AttributedStyle getCompletionStyle(final String s, final String s2) {
        return this.buildStyle(this.getString(s, s2));
    }
    
    protected AttributedStyle buildStyle(final String s) {
        return AttributedString.fromAnsi("\u001b[" + s + "m ").styleAt(0);
    }
    
    private String getCommonStart(final String s, final String s2, final boolean b) {
        int[] array;
        int[] array2;
        int i;
        int n;
        int n2;
        for (array = s.codePoints().toArray(), array2 = s2.codePoints().toArray(), i = 0; i < Math.min(array.length, array2.length); ++i) {
            n = array[i];
            n2 = array2[i];
            if (n != n2 && b) {
                n = Character.toUpperCase(n);
                n2 = Character.toUpperCase(n2);
                if (n != n2) {
                    n = Character.toLowerCase(n);
                    n2 = Character.toLowerCase(n2);
                }
            }
            if (n != n2) {
                break;
            }
        }
        return new String(array, 0, i);
    }
    
    protected boolean moveHistory(final boolean b, final int n) {
        boolean moveHistory = true;
        for (int n2 = 0; n2 < n && (moveHistory = this.moveHistory(b)); ++n2) {}
        return moveHistory;
    }
    
    protected boolean moveHistory(final boolean b) {
        if (!this.buf.toString().equals(this.history.current())) {
            this.modifiedHistory.put(this.history.index(), this.buf.toString());
        }
        if (b && !this.history.next()) {
            return false;
        }
        if (!b && !this.history.previous()) {
            return false;
        }
        this.setBuffer(this.modifiedHistory.containsKey(this.history.index()) ? this.modifiedHistory.get(this.history.index()) : this.history.current());
        return true;
    }
    
    void print(final String s) {
        this.terminal.writer().write(s);
    }
    
    void println(final String s) {
        this.print(s);
        this.println();
    }
    
    void println() {
        this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
        this.print("\n");
        this.redrawLine();
    }
    
    protected boolean killBuffer() {
        this.killRing.add(this.buf.toString());
        this.buf.clear();
        return true;
    }
    
    protected boolean killWholeLine() {
        if (this.buf.length() == 0) {
            return false;
        }
        int cursor;
        int cursor2;
        if (this.count < 0) {
            for (cursor = this.buf.cursor(); this.buf.atChar(cursor) != 0 && this.buf.atChar(cursor) != 10; ++cursor) {}
            cursor2 = cursor;
            for (int i = -this.count; i > 0; --i) {
                while (cursor2 > 0 && this.buf.atChar(cursor2 - 1) != 10) {
                    --cursor2;
                }
                --cursor2;
            }
        }
        else {
            for (cursor2 = this.buf.cursor(); cursor2 > 0 && this.buf.atChar(cursor2 - 1) != 10; --cursor2) {}
            cursor = cursor2;
            while (this.count-- > 0) {
                while (cursor < this.buf.length() && this.buf.atChar(cursor) != 10) {
                    ++cursor;
                }
                if (cursor < this.buf.length()) {
                    ++cursor;
                }
            }
        }
        final String substring = this.buf.substring(cursor2, cursor);
        this.buf.cursor(cursor2);
        this.buf.delete(cursor - cursor2);
        this.killRing.add(substring);
        return true;
    }
    
    public boolean killLine() {
        if (this.count < 0) {
            return this.callNeg(this::backwardKillLine);
        }
        if (this.buf.cursor() == this.buf.length()) {
            return false;
        }
        int cursor;
        final int n = cursor = this.buf.cursor();
        while (this.count-- > 0) {
            if (this.buf.atChar(cursor) == 10) {
                ++cursor;
            }
            else {
                while (this.buf.atChar(cursor) != 0 && this.buf.atChar(cursor) != 10) {
                    ++cursor;
                }
            }
        }
        final int n2 = cursor - n;
        final String substring = this.buf.substring(n, n + n2);
        this.buf.delete(n2);
        this.killRing.add(substring);
        return true;
    }
    
    public boolean backwardKillLine() {
        if (this.count < 0) {
            return this.callNeg(this::killLine);
        }
        if (this.buf.cursor() == 0) {
            return false;
        }
        int cursor;
        int n;
        for (n = (cursor = this.buf.cursor()); this.count-- > 0 && cursor != 0; --cursor) {
            if (this.buf.atChar(cursor - 1) != 10) {
                while (cursor > 0 && this.buf.atChar(cursor - 1) != 0 && this.buf.atChar(cursor - 1) != 10) {
                    --cursor;
                }
            }
        }
        final int n2 = n - cursor;
        final String substring = this.buf.substring(n - cursor, n);
        this.buf.cursor(cursor);
        this.buf.delete(n2);
        this.killRing.add(substring);
        return true;
    }
    
    public boolean killRegion() {
        return this.doCopyKillRegion(true);
    }
    
    public boolean copyRegionAsKill() {
        return this.doCopyKillRegion(false);
    }
    
    private boolean doCopyKillRegion(final boolean b) {
        if (this.regionMark > this.buf.length()) {
            this.regionMark = this.buf.length();
        }
        if (this.regionActive == RegionType.LINE) {
            int regionMark = this.regionMark;
            int cursor = this.buf.cursor();
            if (regionMark < cursor) {
                while (regionMark > 0 && this.buf.atChar(regionMark - 1) != 10) {
                    --regionMark;
                }
                while (cursor < this.buf.length() - 1 && this.buf.atChar(cursor + 1) != 10) {
                    ++cursor;
                }
                if (this.isInViCmdMode()) {
                    ++cursor;
                }
                this.killRing.add(this.buf.substring(regionMark, cursor));
                if (b) {
                    this.buf.backspace(cursor - regionMark);
                }
            }
            else {
                while (cursor > 0 && this.buf.atChar(cursor - 1) != 10) {
                    --cursor;
                }
                while (regionMark < this.buf.length() && this.buf.atChar(regionMark) != 10) {
                    ++regionMark;
                }
                if (this.isInViCmdMode()) {
                    ++regionMark;
                }
                this.killRing.addBackwards(this.buf.substring(cursor, regionMark));
                if (b) {
                    this.buf.cursor(cursor);
                    this.buf.delete(regionMark - cursor);
                }
            }
        }
        else if (this.regionMark > this.buf.cursor()) {
            if (this.isInViCmdMode()) {
                ++this.regionMark;
            }
            this.killRing.add(this.buf.substring(this.buf.cursor(), this.regionMark));
            if (b) {
                this.buf.delete(this.regionMark - this.buf.cursor());
            }
        }
        else {
            if (this.isInViCmdMode()) {
                this.buf.move(1);
            }
            this.killRing.add(this.buf.substring(this.regionMark, this.buf.cursor()));
            if (b) {
                this.buf.backspace(this.buf.cursor() - this.regionMark);
            }
        }
        if (b) {
            this.regionActive = RegionType.NONE;
        }
        return true;
    }
    
    public boolean yank() {
        final String yank = this.killRing.yank();
        if (yank == null) {
            return false;
        }
        this.putString(yank);
        return true;
    }
    
    public boolean yankPop() {
        if (!this.killRing.lastYank()) {
            return false;
        }
        final String yank = this.killRing.yank();
        if (yank == null) {
            return false;
        }
        this.buf.backspace(yank.length());
        final String yankPop = this.killRing.yankPop();
        if (yankPop == null) {
            return false;
        }
        this.putString(yankPop);
        return true;
    }
    
    public boolean mouse() {
        final MouseEvent mouseEvent = this.readMouseEvent();
        if (mouseEvent.getType() == MouseEvent.Type.Released && mouseEvent.getButton() == MouseEvent.Button.Button1) {
            final StringBuilder sb = new StringBuilder();
            final Cursor cursorPosition = this.terminal.getCursorPosition(LineReaderImpl::lambda$mouse$42);
            this.bindingReader.runMacro(sb.toString());
            final ArrayList list = new ArrayList<Object>();
            this.getDisplayedBufferWithPrompts(list);
            final AttributedStringBuilder tabs = new AttributedStringBuilder().tabs(4);
            tabs.append(this.prompt);
            tabs.append(this.insertSecondaryPrompts(new AttributedString(this.buf.upToCursor()), list, false));
            final int n = tabs.columnSplitLength(this.size.getColumns(), false, this.display.delayLineWrap()).size() - 1;
            final int max = Math.max(0, Math.min(n + mouseEvent.getY() - cursorPosition.getY(), list.size()));
            this.buf.moveXY(mouseEvent.getX() - cursorPosition.getX() - (((max == 0) ? this.prompt.columnLength() : ((AttributedString)list.get(max - 1)).columnLength()) - ((n == 0) ? this.prompt.columnLength() : list.get(n - 1).columnLength())), mouseEvent.getY() - cursorPosition.getY());
        }
        return true;
    }
    
    public boolean beginPaste() {
        final Object o = new Object();
        final Object o2 = new Object();
        final KeyMap<Object> keyMap = new KeyMap<Object>();
        keyMap.setUnicode(o);
        keyMap.setNomatch(o);
        keyMap.setAmbiguousTimeout(0L);
        keyMap.bind(o2, "\u001b[201~");
        final StringBuilder sb = new StringBuilder();
        while (this.doReadBinding(keyMap, null) != o2) {
            String lastBinding = this.getLastBinding();
            if ("\r".equals(lastBinding)) {
                lastBinding = "\n";
            }
            sb.append(lastBinding);
        }
        this.regionActive = RegionType.PASTE;
        this.regionMark = this.getBuffer().cursor();
        this.getBuffer().write(sb);
        return true;
    }
    
    public boolean focusIn() {
        return false;
    }
    
    public boolean focusOut() {
        return false;
    }
    
    public boolean clear() {
        this.display.update(Collections.emptyList(), 0);
        return true;
    }
    
    public boolean clearScreen() {
        if (this.terminal.puts(InfoCmp.Capability.clear_screen, new Object[0])) {
            if ("windows-conemu".equals(this.terminal.getType()) && !Boolean.getBoolean("org.jline.terminal.conemu.disable-activate")) {
                this.terminal.writer().write("\u001b[9999E");
            }
            final Status status = Status.getStatus(this.terminal, false);
            if (status != null) {
                status.reset();
            }
            this.redrawLine();
        }
        else {
            this.println();
        }
        return true;
    }
    
    public boolean beep() {
        BellType bellType = BellType.AUDIBLE;
        final String lowerCase = this.getString("bell-style", "").toLowerCase();
        switch (lowerCase) {
            case "none":
            case "off": {
                bellType = BellType.NONE;
                break;
            }
            case "audible": {
                bellType = BellType.AUDIBLE;
                break;
            }
            case "visible": {
                bellType = BellType.VISIBLE;
                break;
            }
            case "on": {
                bellType = (this.getBoolean("prefer-visible-bell", false) ? BellType.VISIBLE : BellType.AUDIBLE);
                break;
            }
        }
        if (bellType == BellType.VISIBLE) {
            if (this.terminal.puts(InfoCmp.Capability.flash_screen, new Object[0]) || this.terminal.puts(InfoCmp.Capability.bell, new Object[0])) {
                this.flush();
            }
        }
        else if (bellType == BellType.AUDIBLE && this.terminal.puts(InfoCmp.Capability.bell, new Object[0])) {
            this.flush();
        }
        return true;
    }
    
    protected boolean isDelimiter(final int n) {
        return !Character.isLetterOrDigit(n);
    }
    
    protected boolean isWhitespace(final int n) {
        return Character.isWhitespace(n);
    }
    
    protected boolean isViAlphaNum(final int n) {
        return n == 95 || Character.isLetterOrDigit(n);
    }
    
    protected boolean isAlpha(final int n) {
        return Character.isLetter(n);
    }
    
    protected boolean isWord(final int n) {
        final String string = this.getString("WORDCHARS", "*?_-.[]~=/&;!#$%^(){}<>");
        return Character.isLetterOrDigit(n) || (n < 128 && string.indexOf((char)n) >= 0);
    }
    
    String getString(final String s, final String s2) {
        return ReaderUtils.getString(this, s, s2);
    }
    
    boolean getBoolean(final String s, final boolean b) {
        return ReaderUtils.getBoolean(this, s, b);
    }
    
    int getInt(final String s, final int n) {
        return ReaderUtils.getInt(this, s, n);
    }
    
    long getLong(final String s, final long n) {
        return ReaderUtils.getLong(this, s, n);
    }
    
    @Override
    public Map<String, KeyMap<Binding>> defaultKeyMaps() {
        final HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("emacs", this.emacs());
        hashMap.put("vicmd", this.viCmd());
        hashMap.put("viins", this.viInsertion());
        hashMap.put("menu", this.menu());
        hashMap.put("viopp", this.viOpp());
        hashMap.put("visual", this.visual());
        hashMap.put(".safe", this.safe());
        if (this.getBoolean("bind-tty-special-chars", true)) {
            final Attributes attributes = this.terminal.getAttributes();
            this.bindConsoleChars(hashMap.get("emacs"), attributes);
            this.bindConsoleChars(hashMap.get("viins"), attributes);
        }
        for (final KeyMap<Binding> keyMap : hashMap.values()) {
            keyMap.setUnicode(new Reference("self-insert"));
            keyMap.setAmbiguousTimeout(this.getLong("ambiguous-binding", 1000L));
        }
        hashMap.put("main", hashMap.get("emacs"));
        return (Map<String, KeyMap<Binding>>)hashMap;
    }
    
    public KeyMap<Binding> emacs() {
        final KeyMap<Binding> keyMap = new KeyMap<Binding>();
        this.bindKeys(keyMap);
        this.bind(keyMap, "set-mark-command", KeyMap.ctrl('@'));
        this.bind(keyMap, "beginning-of-line", KeyMap.ctrl('A'));
        this.bind(keyMap, "backward-char", KeyMap.ctrl('B'));
        this.bind(keyMap, "delete-char-or-list", KeyMap.ctrl('D'));
        this.bind(keyMap, "end-of-line", KeyMap.ctrl('E'));
        this.bind(keyMap, "forward-char", KeyMap.ctrl('F'));
        this.bind(keyMap, "abort", KeyMap.ctrl('G'));
        this.bind(keyMap, "backward-delete-char", KeyMap.ctrl('H'));
        this.bind(keyMap, "expand-or-complete", KeyMap.ctrl('I'));
        this.bind(keyMap, "accept-line", KeyMap.ctrl('J'));
        this.bind(keyMap, "kill-line", KeyMap.ctrl('K'));
        this.bind(keyMap, "clear-screen", KeyMap.ctrl('L'));
        this.bind(keyMap, "accept-line", KeyMap.ctrl('M'));
        this.bind(keyMap, "down-line-or-history", KeyMap.ctrl('N'));
        this.bind(keyMap, "accept-line-and-down-history", KeyMap.ctrl('O'));
        this.bind(keyMap, "up-line-or-history", KeyMap.ctrl('P'));
        this.bind(keyMap, "history-incremental-search-backward", KeyMap.ctrl('R'));
        this.bind(keyMap, "history-incremental-search-forward", KeyMap.ctrl('S'));
        this.bind(keyMap, "transpose-chars", KeyMap.ctrl('T'));
        this.bind(keyMap, "kill-whole-line", KeyMap.ctrl('U'));
        this.bind(keyMap, "quoted-insert", KeyMap.ctrl('V'));
        this.bind(keyMap, "backward-kill-word", KeyMap.ctrl('W'));
        this.bind(keyMap, "yank", KeyMap.ctrl('Y'));
        this.bind(keyMap, "character-search", KeyMap.ctrl(']'));
        this.bind(keyMap, "undo", KeyMap.ctrl('_'));
        this.bind(keyMap, "self-insert", KeyMap.range(" -~"));
        this.bind(keyMap, "insert-close-paren", ")");
        this.bind(keyMap, "insert-close-square", "]");
        this.bind(keyMap, "insert-close-curly", "}");
        this.bind(keyMap, "backward-delete-char", KeyMap.del());
        this.bind(keyMap, "vi-match-bracket", KeyMap.translate("^X^B"));
        this.bind(keyMap, "abort", KeyMap.translate("^X^G"));
        this.bind(keyMap, "vi-find-next-char", KeyMap.translate("^X^F"));
        this.bind(keyMap, "vi-join", KeyMap.translate("^X^J"));
        this.bind(keyMap, "kill-buffer", KeyMap.translate("^X^K"));
        this.bind(keyMap, "infer-next-history", KeyMap.translate("^X^N"));
        this.bind(keyMap, "overwrite-mode", KeyMap.translate("^X^O"));
        this.bind(keyMap, "redo", KeyMap.translate("^X^R"));
        this.bind(keyMap, "undo", KeyMap.translate("^X^U"));
        this.bind(keyMap, "vi-cmd-mode", KeyMap.translate("^X^V"));
        this.bind(keyMap, "exchange-point-and-mark", KeyMap.translate("^X^X"));
        this.bind(keyMap, "do-lowercase-version", KeyMap.translate("^XA-^XZ"));
        this.bind(keyMap, "what-cursor-position", KeyMap.translate("^X="));
        this.bind(keyMap, "kill-line", KeyMap.translate("^X^?"));
        this.bind(keyMap, "abort", KeyMap.alt(KeyMap.ctrl('G')));
        this.bind(keyMap, "backward-kill-word", KeyMap.alt(KeyMap.ctrl('H')));
        this.bind(keyMap, "self-insert-unmeta", KeyMap.alt(KeyMap.ctrl('M')));
        this.bind(keyMap, "complete-word", KeyMap.alt(KeyMap.esc()));
        this.bind(keyMap, "character-search-backward", KeyMap.alt(KeyMap.ctrl(']')));
        this.bind(keyMap, "copy-prev-word", KeyMap.alt(KeyMap.ctrl('_')));
        this.bind(keyMap, "set-mark-command", KeyMap.alt(' '));
        this.bind(keyMap, "neg-argument", KeyMap.alt('-'));
        this.bind(keyMap, "digit-argument", KeyMap.range("\\E0-\\E9"));
        this.bind(keyMap, "beginning-of-history", KeyMap.alt('<'));
        this.bind(keyMap, "list-choices", KeyMap.alt('='));
        this.bind(keyMap, "end-of-history", KeyMap.alt('>'));
        this.bind(keyMap, "list-choices", KeyMap.alt('?'));
        this.bind(keyMap, "do-lowercase-version", KeyMap.range("^[A-^[Z"));
        this.bind(keyMap, "accept-and-hold", KeyMap.alt('a'));
        this.bind(keyMap, "backward-word", KeyMap.alt('b'));
        this.bind(keyMap, "capitalize-word", KeyMap.alt('c'));
        this.bind(keyMap, "kill-word", KeyMap.alt('d'));
        this.bind(keyMap, "kill-word", KeyMap.translate("^[[3;5~"));
        this.bind(keyMap, "forward-word", KeyMap.alt('f'));
        this.bind(keyMap, "down-case-word", KeyMap.alt('l'));
        this.bind(keyMap, "history-search-forward", KeyMap.alt('n'));
        this.bind(keyMap, "history-search-backward", KeyMap.alt('p'));
        this.bind(keyMap, "transpose-words", KeyMap.alt('t'));
        this.bind(keyMap, "up-case-word", KeyMap.alt('u'));
        this.bind(keyMap, "yank-pop", KeyMap.alt('y'));
        this.bind(keyMap, "backward-kill-word", KeyMap.alt(KeyMap.del()));
        this.bindArrowKeys(keyMap);
        this.bind(keyMap, "forward-word", KeyMap.translate("^[[1;5C"));
        this.bind(keyMap, "backward-word", KeyMap.translate("^[[1;5D"));
        this.bind(keyMap, "forward-word", KeyMap.alt(this.key(InfoCmp.Capability.key_right)));
        this.bind(keyMap, "backward-word", KeyMap.alt(this.key(InfoCmp.Capability.key_left)));
        this.bind(keyMap, "forward-word", KeyMap.alt(KeyMap.translate("^[[C")));
        this.bind(keyMap, "backward-word", KeyMap.alt(KeyMap.translate("^[[D")));
        return keyMap;
    }
    
    public KeyMap<Binding> viInsertion() {
        final KeyMap<Binding> keyMap = new KeyMap<Binding>();
        this.bindKeys(keyMap);
        this.bind(keyMap, "self-insert", KeyMap.range("^@-^_"));
        this.bind(keyMap, "list-choices", KeyMap.ctrl('D'));
        this.bind(keyMap, "abort", KeyMap.ctrl('G'));
        this.bind(keyMap, "backward-delete-char", KeyMap.ctrl('H'));
        this.bind(keyMap, "expand-or-complete", KeyMap.ctrl('I'));
        this.bind(keyMap, "accept-line", KeyMap.ctrl('J'));
        this.bind(keyMap, "clear-screen", KeyMap.ctrl('L'));
        this.bind(keyMap, "accept-line", KeyMap.ctrl('M'));
        this.bind(keyMap, "menu-complete", KeyMap.ctrl('N'));
        this.bind(keyMap, "reverse-menu-complete", KeyMap.ctrl('P'));
        this.bind(keyMap, "history-incremental-search-backward", KeyMap.ctrl('R'));
        this.bind(keyMap, "history-incremental-search-forward", KeyMap.ctrl('S'));
        this.bind(keyMap, "transpose-chars", KeyMap.ctrl('T'));
        this.bind(keyMap, "kill-whole-line", KeyMap.ctrl('U'));
        this.bind(keyMap, "quoted-insert", KeyMap.ctrl('V'));
        this.bind(keyMap, "backward-kill-word", KeyMap.ctrl('W'));
        this.bind(keyMap, "yank", KeyMap.ctrl('Y'));
        this.bind(keyMap, "vi-cmd-mode", KeyMap.ctrl('['));
        this.bind(keyMap, "undo", KeyMap.ctrl('_'));
        this.bind(keyMap, "history-incremental-search-backward", KeyMap.ctrl('X') + "r");
        this.bind(keyMap, "history-incremental-search-forward", KeyMap.ctrl('X') + "s");
        this.bind(keyMap, "self-insert", KeyMap.range(" -~"));
        this.bind(keyMap, "insert-close-paren", ")");
        this.bind(keyMap, "insert-close-square", "]");
        this.bind(keyMap, "insert-close-curly", "}");
        this.bind(keyMap, "backward-delete-char", KeyMap.del());
        this.bindArrowKeys(keyMap);
        return keyMap;
    }
    
    public KeyMap<Binding> viCmd() {
        final KeyMap<Binding> keyMap = new KeyMap<Binding>();
        this.bind(keyMap, "list-choices", KeyMap.ctrl('D'));
        this.bind(keyMap, "emacs-editing-mode", KeyMap.ctrl('E'));
        this.bind(keyMap, "abort", KeyMap.ctrl('G'));
        this.bind(keyMap, "vi-backward-char", KeyMap.ctrl('H'));
        this.bind(keyMap, "accept-line", KeyMap.ctrl('J'));
        this.bind(keyMap, "kill-line", KeyMap.ctrl('K'));
        this.bind(keyMap, "clear-screen", KeyMap.ctrl('L'));
        this.bind(keyMap, "accept-line", KeyMap.ctrl('M'));
        this.bind(keyMap, "vi-down-line-or-history", KeyMap.ctrl('N'));
        this.bind(keyMap, "vi-up-line-or-history", KeyMap.ctrl('P'));
        this.bind(keyMap, "quoted-insert", KeyMap.ctrl('Q'));
        this.bind(keyMap, "history-incremental-search-backward", KeyMap.ctrl('R'));
        this.bind(keyMap, "history-incremental-search-forward", KeyMap.ctrl('S'));
        this.bind(keyMap, "transpose-chars", KeyMap.ctrl('T'));
        this.bind(keyMap, "kill-whole-line", KeyMap.ctrl('U'));
        this.bind(keyMap, "quoted-insert", KeyMap.ctrl('V'));
        this.bind(keyMap, "backward-kill-word", KeyMap.ctrl('W'));
        this.bind(keyMap, "yank", KeyMap.ctrl('Y'));
        this.bind(keyMap, "history-incremental-search-backward", KeyMap.ctrl('X') + "r");
        this.bind(keyMap, "history-incremental-search-forward", KeyMap.ctrl('X') + "s");
        this.bind(keyMap, "abort", KeyMap.alt(KeyMap.ctrl('G')));
        this.bind(keyMap, "backward-kill-word", KeyMap.alt(KeyMap.ctrl('H')));
        this.bind(keyMap, "self-insert-unmeta", KeyMap.alt(KeyMap.ctrl('M')));
        this.bind(keyMap, "complete-word", KeyMap.alt(KeyMap.esc()));
        this.bind(keyMap, "character-search-backward", KeyMap.alt(KeyMap.ctrl(']')));
        this.bind(keyMap, "set-mark-command", KeyMap.alt(' '));
        this.bind(keyMap, "digit-argument", KeyMap.alt('-'));
        this.bind(keyMap, "beginning-of-history", KeyMap.alt('<'));
        this.bind(keyMap, "list-choices", KeyMap.alt('='));
        this.bind(keyMap, "end-of-history", KeyMap.alt('>'));
        this.bind(keyMap, "list-choices", KeyMap.alt('?'));
        this.bind(keyMap, "do-lowercase-version", KeyMap.range("^[A-^[Z"));
        this.bind(keyMap, "backward-word", KeyMap.alt('b'));
        this.bind(keyMap, "capitalize-word", KeyMap.alt('c'));
        this.bind(keyMap, "kill-word", KeyMap.alt('d'));
        this.bind(keyMap, "forward-word", KeyMap.alt('f'));
        this.bind(keyMap, "down-case-word", KeyMap.alt('l'));
        this.bind(keyMap, "history-search-forward", KeyMap.alt('n'));
        this.bind(keyMap, "history-search-backward", KeyMap.alt('p'));
        this.bind(keyMap, "transpose-words", KeyMap.alt('t'));
        this.bind(keyMap, "up-case-word", KeyMap.alt('u'));
        this.bind(keyMap, "yank-pop", KeyMap.alt('y'));
        this.bind(keyMap, "backward-kill-word", KeyMap.alt(KeyMap.del()));
        this.bind(keyMap, "forward-char", " ");
        this.bind(keyMap, "vi-insert-comment", "#");
        this.bind(keyMap, "end-of-line", "$");
        this.bind(keyMap, "vi-match-bracket", "%");
        this.bind(keyMap, "vi-down-line-or-history", "+");
        this.bind(keyMap, "vi-rev-repeat-find", ",");
        this.bind(keyMap, "vi-up-line-or-history", "-");
        this.bind(keyMap, "vi-repeat-change", ".");
        this.bind(keyMap, "vi-history-search-backward", "/");
        this.bind(keyMap, "vi-digit-or-beginning-of-line", "0");
        this.bind(keyMap, "digit-argument", KeyMap.range("1-9"));
        this.bind(keyMap, "vi-repeat-find", ";");
        this.bind(keyMap, "list-choices", "=");
        this.bind(keyMap, "vi-history-search-forward", "?");
        this.bind(keyMap, "vi-add-eol", "A");
        this.bind(keyMap, "vi-backward-blank-word", "B");
        this.bind(keyMap, "vi-change-eol", "C");
        this.bind(keyMap, "vi-kill-eol", "D");
        this.bind(keyMap, "vi-forward-blank-word-end", "E");
        this.bind(keyMap, "vi-find-prev-char", "F");
        this.bind(keyMap, "vi-fetch-history", "G");
        this.bind(keyMap, "vi-insert-bol", "I");
        this.bind(keyMap, "vi-join", "J");
        this.bind(keyMap, "vi-rev-repeat-search", "N");
        this.bind(keyMap, "vi-open-line-above", "O");
        this.bind(keyMap, "vi-put-before", "P");
        this.bind(keyMap, "vi-replace", "R");
        this.bind(keyMap, "vi-kill-line", "S");
        this.bind(keyMap, "vi-find-prev-char-skip", "T");
        this.bind(keyMap, "redo", "U");
        this.bind(keyMap, "visual-line-mode", "V");
        this.bind(keyMap, "vi-forward-blank-word", "W");
        this.bind(keyMap, "vi-backward-delete-char", "X");
        this.bind(keyMap, "vi-yank-whole-line", "Y");
        this.bind(keyMap, "vi-first-non-blank", "^");
        this.bind(keyMap, "vi-add-next", "a");
        this.bind(keyMap, "vi-backward-word", "b");
        this.bind(keyMap, "vi-change-to", "c");
        this.bind(keyMap, "vi-delete", "d");
        this.bind(keyMap, "vi-forward-word-end", "e");
        this.bind(keyMap, "vi-find-next-char", "f");
        this.bind(keyMap, "what-cursor-position", "ga");
        this.bind(keyMap, "vi-backward-blank-word-end", "gE");
        this.bind(keyMap, "vi-backward-word-end", "ge");
        this.bind(keyMap, "vi-backward-char", "h");
        this.bind(keyMap, "vi-insert", "i");
        this.bind(keyMap, "down-line-or-history", "j");
        this.bind(keyMap, "up-line-or-history", "k");
        this.bind(keyMap, "vi-forward-char", "l");
        this.bind(keyMap, "vi-repeat-search", "n");
        this.bind(keyMap, "vi-open-line-below", "o");
        this.bind(keyMap, "vi-put-after", "p");
        this.bind(keyMap, "vi-replace-chars", "r");
        this.bind(keyMap, "vi-substitute", "s");
        this.bind(keyMap, "vi-find-next-char-skip", "t");
        this.bind(keyMap, "undo", "u");
        this.bind(keyMap, "visual-mode", "v");
        this.bind(keyMap, "vi-forward-word", "w");
        this.bind(keyMap, "vi-delete-char", "x");
        this.bind(keyMap, "vi-yank", "y");
        this.bind(keyMap, "vi-goto-column", "|");
        this.bind(keyMap, "vi-swap-case", "~");
        this.bind(keyMap, "vi-backward-char", KeyMap.del());
        this.bindArrowKeys(keyMap);
        return keyMap;
    }
    
    public KeyMap<Binding> menu() {
        final KeyMap<Binding> keyMap = new KeyMap<Binding>();
        this.bind(keyMap, "menu-complete", "\t");
        this.bind(keyMap, "reverse-menu-complete", this.key(InfoCmp.Capability.back_tab));
        this.bind(keyMap, "accept-line", "\r", "\n");
        this.bindArrowKeys(keyMap);
        return keyMap;
    }
    
    public KeyMap<Binding> safe() {
        final KeyMap<Binding> keyMap = new KeyMap<Binding>();
        this.bind(keyMap, "self-insert", KeyMap.range("^@-^?"));
        this.bind(keyMap, "accept-line", "\r", "\n");
        this.bind(keyMap, "abort", KeyMap.ctrl('G'));
        return keyMap;
    }
    
    public KeyMap<Binding> visual() {
        final KeyMap<Binding> keyMap = new KeyMap<Binding>();
        this.bind(keyMap, "up-line", this.key(InfoCmp.Capability.key_up), "k");
        this.bind(keyMap, "down-line", this.key(InfoCmp.Capability.key_down), "j");
        this.bind(keyMap, this::deactivateRegion, KeyMap.esc());
        this.bind(keyMap, "exchange-point-and-mark", "o");
        this.bind(keyMap, "put-replace-selection", "p");
        this.bind(keyMap, "vi-delete", "x");
        this.bind(keyMap, "vi-oper-swap-case", "~");
        return keyMap;
    }
    
    public KeyMap<Binding> viOpp() {
        final KeyMap<Binding> keyMap = new KeyMap<Binding>();
        this.bind(keyMap, "up-line", this.key(InfoCmp.Capability.key_up), "k");
        this.bind(keyMap, "down-line", this.key(InfoCmp.Capability.key_down), "j");
        this.bind(keyMap, "vi-cmd-mode", KeyMap.esc());
        return keyMap;
    }
    
    private void bind(final KeyMap<Binding> keyMap, final String s, final Iterable<? extends CharSequence> iterable) {
        keyMap.bind(new Reference(s), iterable);
    }
    
    private void bind(final KeyMap<Binding> keyMap, final String s, final CharSequence... array) {
        keyMap.bind(new Reference(s), array);
    }
    
    private void bind(final KeyMap<Binding> keyMap, final Widget widget, final CharSequence... array) {
        keyMap.bind(widget, array);
    }
    
    private String key(final InfoCmp.Capability capability) {
        return KeyMap.key(this.terminal, capability);
    }
    
    private void bindKeys(final KeyMap<Binding> keyMap) {
        Stream.of(InfoCmp.Capability.values()).filter(LineReaderImpl::lambda$bindKeys$43).map((Function<? super InfoCmp.Capability, ?>)this::key).forEach(this::lambda$bindKeys$44);
    }
    
    private void bindArrowKeys(final KeyMap<Binding> keyMap) {
        this.bind(keyMap, "up-line-or-search", this.key(InfoCmp.Capability.key_up));
        this.bind(keyMap, "down-line-or-search", this.key(InfoCmp.Capability.key_down));
        this.bind(keyMap, "backward-char", this.key(InfoCmp.Capability.key_left));
        this.bind(keyMap, "forward-char", this.key(InfoCmp.Capability.key_right));
        this.bind(keyMap, "beginning-of-line", this.key(InfoCmp.Capability.key_home));
        this.bind(keyMap, "end-of-line", this.key(InfoCmp.Capability.key_end));
        this.bind(keyMap, "delete-char", this.key(InfoCmp.Capability.key_dc));
        this.bind(keyMap, "kill-whole-line", this.key(InfoCmp.Capability.key_dl));
        this.bind(keyMap, "overwrite-mode", this.key(InfoCmp.Capability.key_ic));
        this.bind(keyMap, "mouse", this.key(InfoCmp.Capability.key_mouse));
        this.bind(keyMap, "begin-paste", "\u001b[200~");
        this.bind(keyMap, "terminal-focus-in", "\u001b[I");
        this.bind(keyMap, "terminal-focus-out", "\u001b[O");
    }
    
    private void bindConsoleChars(final KeyMap<Binding> keyMap, final Attributes attributes) {
        if (attributes != null) {
            this.rebind(keyMap, "backward-delete-char", KeyMap.del(), (char)attributes.getControlChar(Attributes.ControlChar.VERASE));
            this.rebind(keyMap, "backward-kill-word", KeyMap.ctrl('W'), (char)attributes.getControlChar(Attributes.ControlChar.VWERASE));
            this.rebind(keyMap, "kill-whole-line", KeyMap.ctrl('U'), (char)attributes.getControlChar(Attributes.ControlChar.VKILL));
            this.rebind(keyMap, "quoted-insert", KeyMap.ctrl('V'), (char)attributes.getControlChar(Attributes.ControlChar.VLNEXT));
        }
    }
    
    private void rebind(final KeyMap<Binding> keyMap, final String s, final String s2, final char c) {
        if (c > '\0' && c < '\u0080') {
            final Reference reference = new Reference(s);
            this.bind(keyMap, "self-insert", s2);
            keyMap.bind(reference, Character.toString(c));
        }
    }
    
    private void lambda$bindKeys$44(final KeyMap keyMap, final Widget widget, final String s) {
        this.bind(keyMap, widget, s);
    }
    
    private static boolean lambda$bindKeys$43(final InfoCmp.Capability capability) {
        return capability.name().startsWith("key_");
    }
    
    private static void lambda$mouse$42(final StringBuilder sb, final int n) {
        sb.append((char)n);
    }
    
    private static int lambda$toColumns$41(final int n, final int n2, final int n3) {
        return n3 * n + n2;
    }
    
    private static int lambda$toColumns$40(final int n, final int n2, final int n3) {
        return n2 * n + n3;
    }
    
    private static Map lambda$computePost$39(final String s) {
        return new LinkedHashMap();
    }
    
    private AttributedString lambda$doList$38(final List list, final String s, final PostResult postResult) {
        final int size = this.insertSecondaryPrompts(AttributedStringBuilder.append(this.prompt, this.buf.toString()), new ArrayList<AttributedString>()).columnSplitLength(this.size.getColumns(), false, this.display.delayLineWrap()).size();
        final PostResult computePost = this.computePost(list, null, null, s);
        if (computePost.lines >= this.size.getRows() - size) {
            this.post = null;
            final int cursor = this.buf.cursor();
            this.buf.cursor(this.buf.length());
            this.redisplay(false);
            this.buf.cursor(cursor);
            this.println();
            final List<AttributedString> columnSplitLength = postResult.post.columnSplitLength(this.size.getColumns(), false, this.display.delayLineWrap());
            final Display display = new Display(this.terminal, false);
            display.resize(this.size.getRows(), this.size.getColumns());
            display.update(columnSplitLength, -1);
            this.redrawLine();
            return new AttributedString("");
        }
        return computePost.post;
    }
    
    private static boolean lambda$doList$37(final boolean b, final String s, final Candidate candidate) {
        return b ? candidate.value().toLowerCase().startsWith(s.toLowerCase()) : candidate.value().startsWith(s);
    }
    
    private AttributedString lambda$doList$36(final List list, final int n) {
        return new AttributedString(this.getAppName() + ": do you wish to see to see all " + list.size() + " possibilities (" + n + " lines)?");
    }
    
    private Map lambda$typoMatcher$35(final String s, final boolean b, final int n, final Map map) {
        final Map map2 = (Map)map.entrySet().stream().filter(this::lambda$null$33).collect(Collectors.toMap((Function<? super Object, ?>)Map.Entry::getKey, (Function<? super Object, ?>)Map.Entry::getValue));
        if (map2.size() > 1) {
            map2.computeIfAbsent(s, LineReaderImpl::lambda$null$34).add(new Candidate(s, s, this.getOriginalGroupName(), null, null, null, false));
        }
        return map2;
    }
    
    private static List lambda$null$34(final String s) {
        return new ArrayList();
    }
    
    private boolean lambda$null$33(final String s, final boolean b, final int n, final Map.Entry entry) {
        return this.distance(s, b ? entry.getKey() : entry.getKey().toLowerCase()) < n;
    }
    
    private static Map lambda$simpleMatcher$32(final Predicate predicate, final Map map) {
        return (Map)map.entrySet().stream().filter(LineReaderImpl::lambda$null$31).collect(Collectors.toMap((Function<? super Object, ?>)Map.Entry::getKey, (Function<? super Object, ?>)Map.Entry::getValue));
    }
    
    private static boolean lambda$null$31(final Predicate predicate, final Map.Entry entry) {
        return predicate.test(entry.getKey());
    }
    
    private static List lambda$mergeCandidates$30(final String s) {
        return new ArrayList();
    }
    
    private int lambda$getGroupComparator$29(final String s) {
        return this.getOthersGroupName().equals(s) ? 1 : (this.getOriginalGroupName().equals(s) ? -1 : 0);
    }
    
    private int lambda$getCandidateComparator$28(final String s, final boolean b, final String s2) {
        return this.distance(s, b ? s2.toLowerCase() : s2);
    }
    
    private static Stream lambda$doComplete$27(final Map.Entry entry) {
        return entry.getValue().stream();
    }
    
    private static boolean lambda$doComplete$26(final Predicate predicate, final Candidate candidate) {
        return predicate.test(candidate.value());
    }
    
    private static Stream lambda$doComplete$25(final Map.Entry entry) {
        return entry.getValue().stream();
    }
    
    private static boolean lambda$doComplete$24(final boolean b, final String s, final String s2) {
        return b ? s2.equalsIgnoreCase(s) : s2.equals(s);
    }
    
    private static boolean lambda$doComplete$23(final boolean b, final String s, final String s2) {
        return (b ? s2.toLowerCase() : s2).contains(s);
    }
    
    private static boolean lambda$doComplete$22(final boolean b, final String s, final String s2) {
        return (b ? s2.toLowerCase() : s2).startsWith(s);
    }
    
    private static boolean lambda$doComplete$21(final boolean b, final String s, final String s2) {
        return b ? s2.equalsIgnoreCase(s) : s2.equals(s);
    }
    
    private static boolean lambda$doComplete$20(final Pattern pattern, final boolean b, final String s) {
        return pattern.matcher(b ? s.toLowerCase() : s).matches();
    }
    
    private static boolean lambda$doComplete$19(final Pattern pattern, final boolean b, final String s) {
        return pattern.matcher(b ? s.toLowerCase() : s).matches();
    }
    
    private static boolean lambda$doComplete$18(final boolean b, final String s, final String s2) {
        return b ? s2.equalsIgnoreCase(s) : s2.equals(s);
    }
    
    private static boolean lambda$doComplete$17(final boolean b, final String s, final String s2) {
        return (b ? s2.toLowerCase() : s2).contains(s);
    }
    
    private static boolean lambda$doComplete$16(final boolean b, final String s, final String s2) {
        return (b ? s2.toLowerCase() : s2).startsWith(s);
    }
    
    private static List lambda$doComplete$15(final String s) {
        return new ArrayList();
    }
    
    private AttributedString lambda$whatCursorPosition$14() {
        final AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        if (this.buf.cursor() < this.buf.length()) {
            final int currChar = this.buf.currChar();
            attributedStringBuilder.append((CharSequence)"Char: ");
            if (currChar == 32) {
                attributedStringBuilder.append((CharSequence)"SPC");
            }
            else if (currChar == 10) {
                attributedStringBuilder.append((CharSequence)"LFD");
            }
            else if (currChar < 32) {
                attributedStringBuilder.append('^');
                attributedStringBuilder.append((char)(currChar + 65 - 1));
            }
            else if (currChar == 127) {
                attributedStringBuilder.append((CharSequence)"^?");
            }
            else {
                attributedStringBuilder.append((char)currChar);
            }
            attributedStringBuilder.append((CharSequence)" (");
            attributedStringBuilder.append((CharSequence)"0").append((CharSequence)Integer.toOctalString(currChar)).append((CharSequence)" ");
            attributedStringBuilder.append((CharSequence)Integer.toString(currChar)).append((CharSequence)" ");
            attributedStringBuilder.append((CharSequence)"0x").append((CharSequence)Integer.toHexString(currChar)).append((CharSequence)" ");
            attributedStringBuilder.append((CharSequence)")");
        }
        else {
            attributedStringBuilder.append((CharSequence)"EOF");
        }
        attributedStringBuilder.append((CharSequence)"   ");
        attributedStringBuilder.append((CharSequence)"point ");
        attributedStringBuilder.append((CharSequence)Integer.toString(this.buf.cursor() + 1));
        attributedStringBuilder.append((CharSequence)" of ");
        attributedStringBuilder.append((CharSequence)Integer.toString(this.buf.length() + 1));
        attributedStringBuilder.append((CharSequence)" (");
        attributedStringBuilder.append((CharSequence)Integer.toString((this.buf.length() == 0) ? 100 : (100 * this.buf.cursor() / this.buf.length())));
        attributedStringBuilder.append((CharSequence)"%)");
        attributedStringBuilder.append((CharSequence)"   ");
        attributedStringBuilder.append((CharSequence)"column ");
        attributedStringBuilder.append((CharSequence)Integer.toString(this.buf.cursor() - this.findbol()));
        return attributedStringBuilder.toAttributedString();
    }
    
    private Stream lambda$doSearchHistory$13(final Pattern pattern, final History.Entry entry) {
        return this.matches(pattern, entry.line(), entry.index()).stream();
    }
    
    private boolean lambda$doSearchHistory$12(final boolean b, final Pair pair) {
        return b ? ((int)pair.v > this.buf.cursor()) : ((int)pair.v >= this.buf.cursor());
    }
    
    private Stream lambda$doSearchHistory$11(final Pattern pattern, final History.Entry entry) {
        return this.matches(pattern, entry.line(), entry.index()).stream();
    }
    
    private boolean lambda$doSearchHistory$10(final boolean b, final Pair pair) {
        return b ? ((int)pair.v < this.buf.cursor()) : ((int)pair.v <= this.buf.cursor());
    }
    
    private AttributedString lambda$doSearchHistory$9() {
        return new AttributedString((this.searchFailing ? "failing " : "") + (this.searchBackward ? "bck-i-search" : "fwd-i-search") + ": " + (Object)this.searchTerm + "_");
    }
    
    private void lambda$doSearchHistory$8(final KeyMap keyMap, final int n) {
        this.bind(keyMap, "accept-line", new String(Character.toChars(n)));
    }
    
    private static AttributedString lambda$getViSearchString$7(final String s, final Buffer buffer) {
        return new AttributedString(s + buffer.toString() + "_");
    }
    
    private boolean lambda$viRevRepeatFind$6() {
        return this.vifindchar(true);
    }
    
    private boolean lambda$getWidget$5() {
        this.post = LineReaderImpl::lambda$null$4;
        return false;
    }
    
    private static AttributedString lambda$null$4() {
        return new AttributedString("Unsupported widget");
    }
    
    private boolean lambda$getWidget$3(final String s) {
        this.post = LineReaderImpl::lambda$null$2;
        return false;
    }
    
    private static AttributedString lambda$null$2(final String s) {
        return new AttributedString("No such widget `" + s + "'");
    }
    
    private boolean lambda$getWidget$1(final String s) {
        this.bindingReader.runMacro(s);
        return true;
    }
    
    private static void lambda$readLine$0(final Thread thread, final Terminal.Signal signal) {
        thread.interrupt();
    }
    
    static AttributedString access$000(final LineReaderImpl lineReaderImpl, final AttributedString attributedString, final List list) {
        return lineReaderImpl.insertSecondaryPrompts(attributedString, list);
    }
    
    protected enum State
    {
        NORMAL, 
        DONE, 
        EOF, 
        INTERRUPT;
        
        private static final State[] $VALUES;
        
        public static State[] values() {
            return State.$VALUES.clone();
        }
        
        public static State valueOf(final String s) {
            return Enum.valueOf(State.class, s);
        }
        
        static {
            $VALUES = new State[] { State.NORMAL, State.DONE, State.EOF, State.INTERRUPT };
        }
    }
    
    protected enum ViMoveMode
    {
        NORMAL, 
        YANK, 
        DELETE, 
        CHANGE;
        
        private static final ViMoveMode[] $VALUES;
        
        public static ViMoveMode[] values() {
            return ViMoveMode.$VALUES.clone();
        }
        
        public static ViMoveMode valueOf(final String s) {
            return Enum.valueOf(ViMoveMode.class, s);
        }
        
        static {
            $VALUES = new ViMoveMode[] { ViMoveMode.NORMAL, ViMoveMode.YANK, ViMoveMode.DELETE, ViMoveMode.CHANGE };
        }
    }
    
    protected enum BellType
    {
        NONE, 
        AUDIBLE, 
        VISIBLE;
        
        private static final BellType[] $VALUES;
        
        public static BellType[] values() {
            return BellType.$VALUES.clone();
        }
        
        public static BellType valueOf(final String s) {
            return Enum.valueOf(BellType.class, s);
        }
        
        static {
            $VALUES = new BellType[] { BellType.NONE, BellType.AUDIBLE, BellType.VISIBLE };
        }
    }
    
    static class Pair<U, V>
    {
        final U u;
        final V v;
        
        public Pair(final U u, final V v) {
            super();
            this.u = u;
            this.v = v;
        }
        
        public U getU() {
            return this.u;
        }
        
        public V getV() {
            return this.v;
        }
    }
    
    protected enum CompletionType
    {
        Expand, 
        ExpandComplete, 
        Complete, 
        List;
        
        private static final CompletionType[] $VALUES;
        
        public static CompletionType[] values() {
            return CompletionType.$VALUES.clone();
        }
        
        public static CompletionType valueOf(final String s) {
            return Enum.valueOf(CompletionType.class, s);
        }
        
        static {
            $VALUES = new CompletionType[] { CompletionType.Expand, CompletionType.ExpandComplete, CompletionType.Complete, CompletionType.List };
        }
    }
    
    private class MenuSupport implements Supplier<AttributedString>
    {
        final List<Candidate> possible;
        final BiFunction<CharSequence, Boolean, CharSequence> escaper;
        int selection;
        int topLine;
        String word;
        AttributedString computed;
        int lines;
        int columns;
        String completed;
        final LineReaderImpl this$0;
        
        public MenuSupport(final LineReaderImpl this$0, final List<Candidate> list, final String completed, final BiFunction<CharSequence, Boolean, CharSequence> escaper) {
            this.this$0 = this$0;
            super();
            this.possible = new ArrayList<Candidate>();
            this.escaper = escaper;
            this.selection = -1;
            this.topLine = 0;
            this.word = "";
            this.completed = completed;
            this$0.computePost(list, null, this.possible, completed);
            this.next();
        }
        
        public Candidate completion() {
            return this.possible.get(this.selection);
        }
        
        public void next() {
            this.selection = (this.selection + 1) % this.possible.size();
            this.update();
        }
        
        public void previous() {
            this.selection = (this.selection + this.possible.size() - 1) % this.possible.size();
            this.update();
        }
        
        private void major(final int n) {
            final int n2 = this.this$0.isSet(Option.LIST_ROWS_FIRST) ? this.columns : this.lines;
            int selection = this.selection + n * n2;
            if (selection < 0) {
                selection = this.possible.size() - this.possible.size() % n2 + (selection + n2) % n2;
                if (selection >= this.possible.size()) {
                    selection -= n2;
                }
            }
            else if (selection >= this.possible.size()) {
                selection %= n2;
            }
            this.selection = selection;
            this.update();
        }
        
        private void minor(final int n) {
            int n2 = this.this$0.isSet(Option.LIST_ROWS_FIRST) ? this.columns : this.lines;
            final int n3 = this.selection % n2;
            final int size = this.possible.size();
            if (this.selection - n3 + n2 > size) {
                n2 = size % n2;
            }
            this.selection = this.selection - n3 + (n2 + n3 + n) % n2;
            this.update();
        }
        
        public void up() {
            if (this.this$0.isSet(Option.LIST_ROWS_FIRST)) {
                this.major(-1);
            }
            else {
                this.minor(-1);
            }
        }
        
        public void down() {
            if (this.this$0.isSet(Option.LIST_ROWS_FIRST)) {
                this.major(1);
            }
            else {
                this.minor(1);
            }
        }
        
        public void left() {
            if (this.this$0.isSet(Option.LIST_ROWS_FIRST)) {
                this.minor(-1);
            }
            else {
                this.major(-1);
            }
        }
        
        public void right() {
            if (this.this$0.isSet(Option.LIST_ROWS_FIRST)) {
                this.minor(1);
            }
            else {
                this.major(1);
            }
        }
        
        private void update() {
            this.this$0.buf.backspace(this.word.length());
            this.word = this.escaper.apply(this.completion().value(), true).toString();
            this.this$0.buf.write(this.word);
            final PostResult computePost = this.this$0.computePost(this.possible, this.completion(), null, this.completed);
            final int size = LineReaderImpl.access$000(this.this$0, AttributedStringBuilder.append(this.this$0.prompt, this.this$0.buf.toString()), new ArrayList()).columnSplitLength(this.this$0.size.getColumns(), false, this.this$0.display.delayLineWrap()).size();
            if (computePost.lines > this.this$0.size.getRows() - size) {
                final int n = this.this$0.size.getRows() - size - 1;
                if (computePost.selectedLine >= 0) {
                    if (computePost.selectedLine < this.topLine) {
                        this.topLine = computePost.selectedLine;
                    }
                    else if (computePost.selectedLine >= this.topLine + n) {
                        this.topLine = computePost.selectedLine - n + 1;
                    }
                }
                AttributedString attributedString = computePost.post;
                if (attributedString.length() > 0 && attributedString.charAt(attributedString.length() - 1) != '\n') {
                    attributedString = new AttributedStringBuilder(attributedString.length() + 1).append(attributedString).append((CharSequence)"\n").toAttributedString();
                }
                final List<AttributedString> columnSplitLength = attributedString.columnSplitLength(this.this$0.size.getColumns(), true, this.this$0.display.delayLineWrap());
                final ArrayList list = new ArrayList<AttributedString>(columnSplitLength.subList(this.topLine, this.topLine + n));
                list.add(new AttributedStringBuilder().style(AttributedStyle.DEFAULT.foreground(6)).append((CharSequence)"rows ").append((CharSequence)Integer.toString(this.topLine + 1)).append((CharSequence)" to ").append((CharSequence)Integer.toString(this.topLine + n)).append((CharSequence)" of ").append((CharSequence)Integer.toString(columnSplitLength.size())).append((CharSequence)"\n").style(AttributedStyle.DEFAULT).toAttributedString());
                this.computed = AttributedString.join(AttributedString.EMPTY, (Iterable<AttributedString>)list);
            }
            else {
                this.computed = computePost.post;
            }
            this.lines = computePost.lines;
            this.columns = (this.possible.size() + this.lines - 1) / this.lines;
        }
        
        @Override
        public AttributedString get() {
            return this.computed;
        }
        
        @Override
        public Object get() {
            return this.get();
        }
    }
    
    protected static class PostResult
    {
        final AttributedString post;
        final int lines;
        final int selectedLine;
        
        public PostResult(final AttributedString post, final int lines, final int selectedLine) {
            super();
            this.post = post;
            this.lines = lines;
            this.selectedLine = selectedLine;
        }
    }
}
