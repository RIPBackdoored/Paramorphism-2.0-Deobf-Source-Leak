package org.jline.builtins;

import org.jline.terminal.*;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;
import org.jline.utils.*;
import java.time.format.*;
import java.time.temporal.*;
import java.time.*;
import java.util.function.*;
import org.jline.keymap.*;
import java.util.*;
import org.jline.reader.*;

public class Commands
{
    public Commands() {
        super();
    }
    
    public static void tmux(final Terminal terminal, final PrintStream printStream, final PrintStream printStream2, final Supplier<Object> supplier, final Consumer<Object> consumer, final Consumer<Terminal> consumer2, final String[] array) throws Exception {
        final Options parse = Options.compile(new String[] { "tmux -  terminal multiplexer", "Usage: tmux [command]", "  -? --help                    Show help" }).parse(array);
        if (parse.isSet("help")) {
            throw new Options.HelpException(parse.usage());
        }
        if (array.length == 0) {
            if (supplier.get() != null) {
                printStream2.println("tmux: can't run tmux inside itself");
            }
            else {
                final Tmux tmux = new Tmux(terminal, printStream2, consumer2);
                consumer.accept(tmux);
                try {
                    tmux.run();
                }
                finally {
                    consumer.accept(null);
                }
            }
        }
        else {
            final Tmux value = supplier.get();
            if (value != null) {
                value.execute(printStream, printStream2, Arrays.asList(array));
            }
            else {
                printStream2.println("tmux: no instance running");
            }
        }
    }
    
    public static void nano(final Terminal terminal, final PrintStream printStream, final PrintStream printStream2, final Path path, final String[] array) throws Exception {
        final Options parse = Options.compile(new String[] { "nano -  edit files", "Usage: nano [FILES]", "  -? --help                    Show help" }).parse(array);
        if (parse.isSet("help")) {
            throw new Options.HelpException(parse.usage());
        }
        final Nano nano = new Nano(terminal, path);
        nano.open(parse.args());
        nano.run();
    }
    
    public static void less(final Terminal terminal, final InputStream inputStream, final PrintStream printStream, final PrintStream printStream2, final Path path, final String[] array) throws Exception {
        final Options parse = Options.compile(new String[] { "less -  file pager", "Usage: less [OPTIONS] [FILES]", "  -? --help                    Show help", "  -e --quit-at-eof             Exit on second EOF", "  -E --QUIT-AT-EOF             Exit on EOF", "  -q --quiet --silent          Silent mode", "  -Q --QUIET --SILENT          Completely silent", "  -S --chop-long-lines         Do not fold long lines", "  -i --ignore-case             Search ignores lowercase case", "  -I --IGNORE-CASE             Search ignores all case", "  -x --tabs                    Set tab stops", "  -N --LINE-NUMBERS            Display line number for each line" }).parse(array);
        if (parse.isSet("help")) {
            throw new Options.HelpException(parse.usage());
        }
        final Less less = new Less(terminal);
        less.quitAtFirstEof = parse.isSet("QUIT-AT-EOF");
        less.quitAtSecondEof = parse.isSet("quit-at-eof");
        less.quiet = parse.isSet("quiet");
        less.veryQuiet = parse.isSet("QUIET");
        less.chopLongLines = parse.isSet("chop-long-lines");
        less.ignoreCaseAlways = parse.isSet("IGNORE-CASE");
        less.ignoreCaseCond = parse.isSet("ignore-case");
        if (parse.isSet("tabs")) {
            less.tabs = parse.getNumber("tabs");
        }
        less.printLineNumbers = parse.isSet("LINE-NUMBERS");
        final ArrayList<Source.StdInSource> list = new ArrayList<Source.StdInSource>();
        if (parse.args().isEmpty()) {
            parse.args().add("-");
        }
        for (final String s : parse.args()) {
            if ("-".equals(s)) {
                list.add(new Source.StdInSource(inputStream));
            }
            else {
                list.add((Source.StdInSource)new Source.URLSource(path.resolve(s).toUri().toURL(), s));
            }
        }
        less.run((List<Source>)list);
    }
    
    public static void history(final LineReader lineReader, final PrintStream printStream, final PrintStream printStream2, final String[] array) throws Exception {
        final Options parse = Options.compile(new String[] { "history -  list history of commands", "Usage: history [-dnrfEi] [-m match] [first] [last]", "       history -ARWI [filename]", "       history --clear", "       history --save", "  -? --help                       Displays command help", "     --clear                      Clear history", "     --save                       Save history", "  -m match                        If option -m is present the first argument is taken as a pattern", "                                  and only the history events matching the pattern will be shown", "  -d                              Print timestamps for each event", "  -f                              Print full time date stamps in the US format", "  -E                              Print full time date stamps in the European format", "  -i                              Print full time date stamps in ISO8601 format", "  -n                              Suppresses command numbers", "  -r                              Reverses the order of the commands", "  -A                              Appends the history out to the given file", "  -R                              Reads the history from the given file", "  -W                              Writes the history out to the given file", "  -I                              If added to -R, only the events that are not contained within the internal list are added", "                                  If added to -W or -A, only the events that are new since the last incremental operation", "                                  to the file are added", "  [first] [last]                  These optional arguments are numbers. A negative number is", "                                  used as an offset to the current history event number" }).parse(array);
        if (parse.isSet("help")) {
            throw new Options.HelpException(parse.usage());
        }
        final History history = lineReader.getHistory();
        boolean b = true;
        final boolean set = parse.isSet("I");
        if (parse.isSet("clear")) {
            history.purge();
        }
        else if (parse.isSet("save")) {
            history.save();
        }
        else if (parse.isSet("A")) {
            history.append((parse.args().size() > 0) ? Paths.get(parse.args().get(0), new String[0]) : null, set);
        }
        else if (parse.isSet("R")) {
            history.read((parse.args().size() > 0) ? Paths.get(parse.args().get(0), new String[0]) : null, set);
        }
        else if (parse.isSet("W")) {
            history.write((parse.args().size() > 0) ? Paths.get(parse.args().get(0), new String[0]) : null, set);
        }
        else {
            b = false;
        }
        if (b) {
            return;
        }
        int n = 0;
        Pattern compile = null;
        if (parse.isSet("m")) {
            if (parse.args().size() == 0) {
                throw new IllegalArgumentException();
            }
            compile = Pattern.compile(parse.args().get(n++).toString());
        }
        final int n2 = (parse.args().size() > n) ? parseInteger(parse.args().get(n++)) : -17;
        final int n3 = (parse.args().size() > n) ? parseInteger(parse.args().get(n++)) : -1;
        final int historyId = historyId(n2, history.size() - 1);
        final int historyId2 = historyId(n3, history.size() - 1);
        if (historyId > historyId2) {
            throw new IllegalArgumentException();
        }
        final int n4 = historyId2 - historyId + 1;
        int n5 = 0;
        final Highlighter highlighter = lineReader.getHighlighter();
        Iterator<History.Entry> iterator;
        if (parse.isSet("r")) {
            iterator = history.reverseIterator(historyId2);
        }
        else {
            iterator = history.iterator(historyId);
        }
        while (iterator.hasNext() && n5 < n4) {
            final History.Entry entry = iterator.next();
            ++n5;
            if (compile != null && !compile.matcher(entry.line()).matches()) {
                continue;
            }
            final AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
            if (!parse.isSet("n")) {
                attributedStringBuilder.append((CharSequence)"  ");
                attributedStringBuilder.styled(AttributedStyle::bold, String.format("%3d", entry.index()));
            }
            if (parse.isSet("d") || parse.isSet("f") || parse.isSet("E") || parse.isSet("i")) {
                attributedStringBuilder.append((CharSequence)"  ");
                if (parse.isSet("d")) {
                    DateTimeFormatter.ISO_LOCAL_TIME.formatTo(LocalTime.from(entry.time().atZone(ZoneId.systemDefault())).truncatedTo(ChronoUnit.SECONDS), attributedStringBuilder);
                }
                else {
                    final LocalDateTime from = LocalDateTime.from((TemporalAccessor)entry.time().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.MINUTES));
                    String s = "yyyy-MM-dd hh:mm";
                    if (parse.isSet("f")) {
                        s = "MM/dd/yy hh:mm";
                    }
                    else if (parse.isSet("E")) {
                        s = "dd.MM.yyyy hh:mm";
                    }
                    DateTimeFormatter.ofPattern(s).formatTo(from, attributedStringBuilder);
                }
            }
            attributedStringBuilder.append((CharSequence)"  ");
            attributedStringBuilder.append(highlighter.highlight(lineReader, entry.line()));
            printStream.println(attributedStringBuilder.toAnsi(lineReader.getTerminal()));
        }
    }
    
    private static int historyId(final int n, final int n2) {
        int n3 = n;
        if (n < 0) {
            n3 = n2 + n + 1;
        }
        if (n3 < 0) {
            n3 = 0;
        }
        else if (n3 > n2) {
            n3 = n2;
        }
        return n3;
    }
    
    private static int parseInteger(final String s) throws IllegalArgumentException {
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException();
        }
    }
    
    public static void complete(final LineReader lineReader, final PrintStream printStream, final PrintStream printStream2, final Map<String, List<Completers.CompletionData>> map, final String[] array) throws Options.HelpException {
        final Options parse = Options.compile(new String[] { "complete -  edit command specific tab-completions", "Usage: complete", "  -? --help                       Displays command help", "  -c --command=COMMAND            Command to add completion to", "  -d --description=DESCRIPTION    Description of this completions", "  -e --erase                      Erase the completions", "  -s --short-option=SHORT_OPTION  Posix-style option to complete", "  -l --long-option=LONG_OPTION    GNU-style option to complete", "  -a --argument=ARGUMENTS         A list of possible arguments", "  -n --condition=CONDITION        The completion should only be used if the", "                                  specified command has a zero exit status" }).parse(array);
        if (parse.isSet("help")) {
            throw new Options.HelpException(parse.usage());
        }
        final String value = parse.get("command");
        if (parse.isSet("erase")) {
            map.remove(value);
            return;
        }
        final List<Completers.CompletionData> list = map.computeIfAbsent(value, (Function<? super String, ? extends List<Completers.CompletionData>>)Commands::lambda$complete$0);
        List<String> list2 = null;
        if (parse.isSet("short-option")) {
            for (final String s : parse.getList("short-option")) {
                if (list2 == null) {
                    list2 = new ArrayList<String>();
                }
                list2.add("-" + s);
            }
        }
        if (parse.isSet("long-option")) {
            for (final String s2 : parse.getList("long-option")) {
                if (list2 == null) {
                    list2 = new ArrayList<String>();
                }
                list2.add("--" + s2);
            }
        }
        list.add(new Completers.CompletionData(list2, parse.isSet("description") ? parse.get("description") : null, parse.isSet("argument") ? parse.get("argument") : null, parse.isSet("condition") ? parse.get("condition") : null));
    }
    
    public static void widget(final LineReader lineReader, final PrintStream printStream, final PrintStream printStream2, final Function<String, Widget> function, final String[] array) throws Exception {
        final Options parse = Options.compile(new String[] { "widget -  manipulate widgets", "Usage: widget [options] -N new-widget [function-name]", "       widget [options] -D widget ...", "       widget [options] -A old-widget new-widget", "       widget [options] -U string ...", "       widget [options] -l", "  -? --help                       Displays command help", "  -A                              Create alias to widget", "  -N                              Create new widget", "  -D                              Delete widgets", "  -U                              Push characters to the stack", "  -l                              List user-defined widgets", "  -a                              With -l, list all widgets" }).parse(array);
        if (parse.isSet("help")) {
            throw new Options.HelpException(parse.usage());
        }
        if (((parse.isSet("N") + parse.isSet("D") + parse.isSet("U") + parse.isSet("l") + parse.isSet("A")) ? 1 : 0) > 1) {
            printStream2.println("widget: incompatible operation selection options");
            return;
        }
        if (parse.isSet("l")) {
            final TreeSet set = new TreeSet((Collection<? extends E>)lineReader.getWidgets().keySet());
            if (!parse.isSet("a")) {
                set.removeAll(lineReader.getBuiltinWidgets().keySet());
            }
            set.forEach(printStream::println);
        }
        else if (parse.isSet("N")) {
            if (parse.args().size() < 1) {
                printStream2.println("widget: not enough arguments for -N");
                return;
            }
            if (parse.args().size() > 2) {
                printStream2.println("widget: too many arguments for -N");
                return;
            }
            final String s = parse.args().get(0);
            lineReader.getWidgets().put(s, function.apply((parse.args().size() == 2) ? parse.args().get(1) : s));
        }
        else if (parse.isSet("D")) {
            final Iterator<String> iterator = parse.args().iterator();
            while (iterator.hasNext()) {
                lineReader.getWidgets().remove(iterator.next());
            }
        }
        else if (parse.isSet("A")) {
            if (parse.args().size() < 2) {
                printStream2.println("widget: not enough arguments for -A");
                return;
            }
            if (parse.args().size() > 2) {
                printStream2.println("widget: too many arguments for -A");
                return;
            }
            final Widget widget = lineReader.getWidgets().get(parse.args().get(0));
            if (widget == null) {
                printStream2.println("widget: no such widget `" + parse.args().get(0) + "'");
                return;
            }
            lineReader.getWidgets().put(parse.args().get(1), widget);
        }
        else if (parse.isSet("U")) {
            final Iterator<String> iterator2 = parse.args().iterator();
            while (iterator2.hasNext()) {
                lineReader.runMacro(KeyMap.translate(iterator2.next()));
            }
        }
        else if (parse.args().size() == 1) {
            lineReader.callWidget(parse.args().get(0));
        }
    }
    
    public static void keymap(final LineReader lineReader, final PrintStream printStream, final PrintStream printStream2, final String[] array) throws Options.HelpException {
        final Options parse = Options.compile(new String[] { "keymap -  manipulate keymaps", "Usage: keymap [options] -l [-L] [keymap ...]", "       keymap [options] -d", "       keymap [options] -D keymap ...", "       keymap [options] -A old-keymap new-keymap", "       keymap [options] -N new-keymap [old-keymap]", "       keymap [options] -m", "       keymap [options] -r in-string ...", "       keymap [options] -s in-string out-string ...", "       keymap [options] in-string command ...", "       keymap [options] [in-string]", "  -? --help                       Displays command help", "  -A                              Create alias to keymap", "  -D                              Delete named keymaps", "  -L                              Output in form of keymap commands", "  -M (default=main)               Specify keymap to select", "  -N                              Create new keymap", "  -R                              Interpret in-strings as ranges", "  -a                              Select vicmd keymap", "  -d                              Delete existing keymaps and reset to default state", "  -e                              Select emacs keymap and bind it to main", "  -l                              List existing keymap names", "  -p                              List bindings which have given key sequence as a a prefix", "  -r                              Unbind specified in-strings ", "  -s                              Bind each in-string to each out-string ", "  -v                              Select viins keymap and bind it to main" }).parse(array);
        if (parse.isSet("help")) {
            throw new Options.HelpException(parse.usage());
        }
        final Map<String, KeyMap<Binding>> keyMaps = lineReader.getKeyMaps();
        if (((parse.isSet("N") + parse.isSet("d") + parse.isSet("D") + parse.isSet("l") + parse.isSet("r") + parse.isSet("s") + parse.isSet("A")) ? 1 : 0) > 1) {
            printStream2.println("keymap: incompatible operation selection options");
            return;
        }
        if (parse.isSet("l")) {
            parse.isSet("L");
            if (parse.args().size() > 0) {
                for (final String s : parse.args()) {
                    if (keyMaps.get(s) == null) {
                        printStream2.println("keymap: no such keymap: `" + s + "'");
                    }
                    else {
                        printStream.println(s);
                    }
                }
            }
            else {
                keyMaps.keySet().forEach(printStream::println);
            }
        }
        else if (parse.isSet("N")) {
            if (parse.isSet("e") || parse.isSet("v") || parse.isSet("a") || parse.isSet("M")) {
                printStream2.println("keymap: keymap can not be selected with -N");
                return;
            }
            if (parse.args().size() < 1) {
                printStream2.println("keymap: not enough arguments for -N");
                return;
            }
            if (parse.args().size() > 2) {
                printStream2.println("keymap: too many arguments for -N");
                return;
            }
            Object o = null;
            if (parse.args().size() == 2) {
                o = keyMaps.get(parse.args().get(1));
                if (o == null) {
                    printStream2.println("keymap: no such keymap `" + parse.args().get(1) + "'");
                    return;
                }
            }
            final KeyMap<Binding> keyMap = new KeyMap<Binding>();
            if (o != null) {
                for (final Map.Entry<String, Object> entry : ((KeyMap<Object>)o).getBoundKeys().entrySet()) {
                    keyMap.bind(entry.getValue(), entry.getKey());
                }
            }
            keyMaps.put(parse.args().get(0), keyMap);
        }
        else if (parse.isSet("A")) {
            if (parse.isSet("e") || parse.isSet("v") || parse.isSet("a") || parse.isSet("M")) {
                printStream2.println("keymap: keymap can not be selected with -N");
                return;
            }
            if (parse.args().size() < 2) {
                printStream2.println("keymap: not enough arguments for -A");
                return;
            }
            if (parse.args().size() > 2) {
                printStream2.println("keymap: too many arguments for -A");
                return;
            }
            final KeyMap<Binding> keyMap2 = keyMaps.get(parse.args().get(0));
            if (keyMap2 == null) {
                printStream2.println("keymap: no such keymap `" + parse.args().get(0) + "'");
                return;
            }
            keyMaps.put(parse.args().get(1), keyMap2);
        }
        else if (parse.isSet("d")) {
            if (parse.isSet("e") || parse.isSet("v") || parse.isSet("a") || parse.isSet("M")) {
                printStream2.println("keymap: keymap can not be selected with -N");
                return;
            }
            if (parse.args().size() > 0) {
                printStream2.println("keymap: too many arguments for -d");
                return;
            }
            keyMaps.clear();
            keyMaps.putAll(lineReader.defaultKeyMaps());
        }
        else if (parse.isSet("D")) {
            if (parse.isSet("e") || parse.isSet("v") || parse.isSet("a") || parse.isSet("M")) {
                printStream2.println("keymap: keymap can not be selected with -N");
                return;
            }
            if (parse.args().size() < 1) {
                printStream2.println("keymap: not enough arguments for -A");
                return;
            }
            for (final String s2 : parse.args()) {
                if (keyMaps.remove(s2) == null) {
                    printStream2.println("keymap: no such keymap `" + s2 + "'");
                }
            }
        }
        else if (parse.isSet("r")) {
            String s3 = "main";
            if (((parse.isSet("a") + parse.isSet("e") + parse.isSet("v") + parse.isSet("M")) ? 1 : 0) > 1) {
                printStream2.println("keymap: incompatible keymap selection options");
                return;
            }
            if (parse.isSet("a")) {
                s3 = "vicmd";
            }
            else if (parse.isSet("e")) {
                s3 = "emacs";
            }
            else if (parse.isSet("v")) {
                s3 = "viins";
            }
            else if (parse.isSet("M")) {
                if (parse.args().isEmpty()) {
                    printStream2.println("keymap: argument expected: -M");
                    return;
                }
                s3 = parse.args().remove(0);
            }
            final KeyMap<Binding> keyMap3 = keyMaps.get(s3);
            if (keyMap3 == null) {
                printStream2.println("keymap: no such keymap `" + s3 + "'");
                return;
            }
            final boolean set = parse.isSet("R");
            final boolean set2 = parse.isSet("p");
            final HashSet<String> set3 = new HashSet<String>();
            final Map<String, Binding> boundKeys = keyMap3.getBoundKeys();
            for (final String s4 : parse.args()) {
                if (set) {
                    final Collection<String> range = KeyMap.range(parse.args().get(0));
                    if (range == null) {
                        printStream2.println("keymap: malformed key range `" + parse.args().get(0) + "'");
                        return;
                    }
                    set3.addAll((Collection<?>)range);
                }
                else {
                    final String translate = KeyMap.translate(s4);
                    for (final String s5 : boundKeys.keySet()) {
                        if ((set2 && s5.startsWith(translate) && s5.length() > translate.length()) || (!set2 && s5.equals(translate))) {
                            set3.add(s5);
                        }
                    }
                }
            }
            final Iterator<Object> iterator6 = set3.iterator();
            while (iterator6.hasNext()) {
                keyMap3.unbind(iterator6.next());
            }
            if (parse.isSet("e") || parse.isSet("v")) {
                keyMaps.put("main", keyMap3);
            }
        }
        else if (parse.isSet("s") || parse.args().size() > 1) {
            String s6 = "main";
            if (((parse.isSet("a") + parse.isSet("e") + parse.isSet("v") + parse.isSet("M")) ? 1 : 0) > 1) {
                printStream2.println("keymap: incompatible keymap selection options");
                return;
            }
            if (parse.isSet("a")) {
                s6 = "vicmd";
            }
            else if (parse.isSet("e")) {
                s6 = "emacs";
            }
            else if (parse.isSet("v")) {
                s6 = "viins";
            }
            else if (parse.isSet("M")) {
                if (parse.args().isEmpty()) {
                    printStream2.println("keymap: argument expected: -M");
                    return;
                }
                s6 = parse.args().remove(0);
            }
            final KeyMap<Binding> keyMap4 = keyMaps.get(s6);
            if (keyMap4 == null) {
                printStream2.println("keymap: no such keymap `" + s6 + "'");
                return;
            }
            final boolean set4 = parse.isSet("R");
            if (parse.args().size() % 2 == 1) {
                printStream2.println("keymap: even number of arguments required");
                return;
            }
            for (int i = 0; i < parse.args().size(); i += 2) {
                final Binding binding = parse.isSet("s") ? new Macro(KeyMap.translate(parse.args().get(i + 1))) : new Reference(parse.args().get(i + 1));
                if (set4) {
                    final Collection<String> range2 = KeyMap.range(parse.args().get(i));
                    if (range2 == null) {
                        printStream2.println("keymap: malformed key range `" + parse.args().get(i) + "'");
                        return;
                    }
                    keyMap4.bind(binding, range2);
                }
                else {
                    keyMap4.bind(binding, KeyMap.translate(parse.args().get(i)));
                }
            }
            if (parse.isSet("e") || parse.isSet("v")) {
                keyMaps.put("main", keyMap4);
            }
        }
        else {
            String s7 = "main";
            if (((parse.isSet("a") + parse.isSet("e") + parse.isSet("v") + parse.isSet("M")) ? 1 : 0) > 1) {
                printStream2.println("keymap: incompatible keymap selection options");
                return;
            }
            if (parse.isSet("a")) {
                s7 = "vicmd";
            }
            else if (parse.isSet("e")) {
                s7 = "emacs";
            }
            else if (parse.isSet("v")) {
                s7 = "viins";
            }
            else if (parse.isSet("M")) {
                if (parse.args().isEmpty()) {
                    printStream2.println("keymap: argument expected: -M");
                    return;
                }
                s7 = parse.args().remove(0);
            }
            final KeyMap<Binding> keyMap5 = keyMaps.get(s7);
            if (keyMap5 == null) {
                printStream2.println("keymap: no such keymap `" + s7 + "'");
                return;
            }
            final boolean set5 = parse.isSet("p");
            final boolean set6 = parse.isSet("L");
            if (set5 && parse.args().isEmpty()) {
                printStream2.println("keymap: option -p requires a prefix string");
                return;
            }
            if (parse.args().size() > 0 || (!parse.isSet("e") && !parse.isSet("v"))) {
                final Map<String, Binding> boundKeys2 = keyMap5.getBoundKeys();
                final String s8 = (parse.args().size() > 0) ? KeyMap.translate(parse.args().get(0)) : null;
                Object o2 = null;
                String s9 = null;
                final Iterator<Map.Entry<String, Binding>> iterator7 = boundKeys2.entrySet().iterator();
                while (iterator7.hasNext()) {
                    final Map.Entry<String, Binding> entry2 = iterator7.next();
                    final String s10 = entry2.getKey();
                    if (s8 == null || (set5 && s10.startsWith(s8) && !s10.equals(s8)) || (!set5 && s10.equals(s8))) {
                        if (o2 != null || !iterator7.hasNext()) {
                            if (s10.equals(((s9.length() > 1) ? s9.substring(0, s9.length() - 1) : "") + (char)(s9.charAt(s9.length() - 1) + '\u0001')) && entry2.getValue().equals(((Map.Entry<String, Object>)o2).getValue())) {
                                s9 = s10;
                            }
                            else {
                                final StringBuilder sb = new StringBuilder();
                                if (set6) {
                                    sb.append("keymap -M ");
                                    sb.append(s7);
                                    sb.append(" ");
                                }
                                if (((Map.Entry<String, Object>)o2).getKey().equals(s9)) {
                                    sb.append(KeyMap.display(s9));
                                    sb.append(" ");
                                    displayValue(sb, ((Map.Entry<String, Object>)o2).getValue());
                                    printStream.println(sb.toString());
                                }
                                else {
                                    if (set6) {
                                        sb.append("-R ");
                                    }
                                    sb.append(KeyMap.display(((Map.Entry<String, Object>)o2).getKey()));
                                    sb.append("-");
                                    sb.append(KeyMap.display(s9));
                                    sb.append(" ");
                                    displayValue(sb, ((Map.Entry<String, Object>)o2).getValue());
                                    printStream.println(sb.toString());
                                }
                                o2 = entry2;
                                s9 = s10;
                            }
                        }
                        else {
                            o2 = entry2;
                            s9 = s10;
                        }
                    }
                }
            }
            if (parse.isSet("e") || parse.isSet("v")) {
                keyMaps.put("main", keyMap5);
            }
        }
    }
    
    public static void setopt(final LineReader lineReader, final PrintStream printStream, final PrintStream printStream2, final String[] array) throws Options.HelpException {
        final Options parse = Options.compile(new String[] { "setopt -  set options", "Usage: setopt [-m] option ...", "       setopt", "  -? --help                       Displays command help", "  -m                              Use pattern matching" }).parse(array);
        if (parse.isSet("help")) {
            throw new Options.HelpException(parse.usage());
        }
        if (parse.args().isEmpty()) {
            for (final LineReader.Option option : LineReader.Option.values()) {
                if (lineReader.isSet(option) != option.isDef()) {
                    printStream.println((option.isDef() ? "no-" : "") + option.toString().toLowerCase().replace('_', '-'));
                }
            }
        }
        else {
            doSetOpts(lineReader, printStream, printStream2, parse.args(), parse.isSet("m"), true);
        }
    }
    
    public static void unsetopt(final LineReader lineReader, final PrintStream printStream, final PrintStream printStream2, final String[] array) throws Options.HelpException {
        final Options parse = Options.compile(new String[] { "unsetopt -  unset options", "Usage: unsetopt [-m] option ...", "       unsetopt", "  -? --help                       Displays command help", "  -m                              Use pattern matching" }).parse(array);
        if (parse.isSet("help")) {
            throw new Options.HelpException(parse.usage());
        }
        if (parse.args().isEmpty()) {
            for (final LineReader.Option option : LineReader.Option.values()) {
                if (lineReader.isSet(option) == option.isDef()) {
                    printStream.println((option.isDef() ? "no-" : "") + option.toString().toLowerCase().replace('_', '-'));
                }
            }
        }
        else {
            doSetOpts(lineReader, printStream, printStream2, parse.args(), parse.isSet("m"), false);
        }
    }
    
    private static void doSetOpts(final LineReader lineReader, final PrintStream printStream, final PrintStream printStream2, final List<String> list, final boolean b, final boolean b2) {
        for (final String s : list) {
            String s2 = s.toLowerCase().replaceAll("[-_]", "");
            if (b) {
                s2 = s2.replaceAll("\\*", "[a-z]*").replaceAll("\\?", "[a-z]");
            }
            boolean b3 = false;
            for (final LineReader.Option option : LineReader.Option.values()) {
                final String replaceAll = option.name().toLowerCase().replaceAll("[-_]", "");
                Label_0290: {
                    Label_0189: {
                        if (b) {
                            if (!replaceAll.matches(s2)) {
                                break Label_0189;
                            }
                        }
                        else if (!replaceAll.equals(s2)) {
                            break Label_0189;
                        }
                        if (b2) {
                            lineReader.setOpt(option);
                        }
                        else {
                            lineReader.unsetOpt(option);
                        }
                        b3 = true;
                        if (!b) {
                            break;
                        }
                        break Label_0290;
                    }
                    if (b) {
                        if (!("no" + replaceAll).matches(s2)) {
                            break Label_0290;
                        }
                    }
                    else if (!("no" + replaceAll).equals(s2)) {
                        break Label_0290;
                    }
                    if (b2) {
                        lineReader.unsetOpt(option);
                    }
                    else {
                        lineReader.setOpt(option);
                    }
                    if (!b) {
                        b3 = true;
                        break;
                    }
                    break;
                }
            }
            if (!b3) {
                printStream2.println("No matching option: " + s);
            }
        }
    }
    
    private static void displayValue(final StringBuilder sb, final Object o) {
        if (o == null) {
            sb.append("undefined-key");
        }
        else if (o instanceof Macro) {
            sb.append(KeyMap.display(((Macro)o).getSequence()));
        }
        else if (o instanceof Reference) {
            sb.append(((Reference)o).name());
        }
        else {
            sb.append(o.toString());
        }
    }
    
    private static List lambda$complete$0(final String s) {
        return new ArrayList();
    }
}
