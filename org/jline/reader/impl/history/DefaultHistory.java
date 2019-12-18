package org.jline.reader.impl.history;

import org.jline.reader.*;
import org.jline.utils.*;
import java.time.*;
import java.nio.file.attribute.*;
import org.jline.reader.impl.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DefaultHistory implements History
{
    public static final int DEFAULT_HISTORY_SIZE = 500;
    public static final int DEFAULT_HISTORY_FILE_SIZE = 10000;
    private final LinkedList<Entry> items;
    private LineReader reader;
    private Map<String, HistoryFileData> historyFiles;
    private int offset;
    private int index;
    
    public DefaultHistory() {
        super();
        this.items = new LinkedList<Entry>();
        this.historyFiles = new HashMap<String, HistoryFileData>();
        this.offset = 0;
        this.index = 0;
    }
    
    public DefaultHistory(final LineReader lineReader) {
        super();
        this.items = new LinkedList<Entry>();
        this.historyFiles = new HashMap<String, HistoryFileData>();
        this.offset = 0;
        this.index = 0;
        this.attach(lineReader);
    }
    
    private Path getPath() {
        final File file = (this.reader != null) ? this.reader.getVariables().get("history-file") : null;
        if (file instanceof Path) {
            return (Path)file;
        }
        if (file instanceof File) {
            return file.toPath();
        }
        if (file != null) {
            return Paths.get(file.toString(), new String[0]);
        }
        return null;
    }
    
    @Override
    public void attach(final LineReader reader) {
        if (this.reader != reader) {
            this.reader = reader;
            try {
                this.load();
            }
            catch (IllegalArgumentException | IOException ex) {
                final Object o;
                Log.warn("Failed to load history", o);
            }
        }
    }
    
    @Override
    public void load() throws IOException {
        final Path path = this.getPath();
        if (path != null) {
            try {
                if (Files.exists(path, new LinkOption[0])) {
                    Log.trace("Loading history from: ", path);
                    try (final BufferedReader bufferedReader = Files.newBufferedReader(path)) {
                        this.internalClear();
                        bufferedReader.lines().forEach(this::lambda$load$0);
                        this.setHistoryFileData(path, new HistoryFileData(this.items.size(), this.items.size()));
                        this.maybeResize();
                    }
                }
            }
            catch (IllegalArgumentException | IOException ex3) {
                final IOException ex2;
                final IOException ex = ex2;
                Log.debug("Failed to load history; clearing", ex);
                this.internalClear();
                throw ex;
            }
        }
    }
    
    @Override
    public void read(final Path path, final boolean b) throws IOException {
        final Path path2 = (path != null) ? path : this.getPath();
        if (path2 != null) {
            try {
                if (Files.exists(path2, new LinkOption[0])) {
                    Log.trace("Reading history from: ", path2);
                    try (final BufferedReader bufferedReader = Files.newBufferedReader(path2)) {
                        bufferedReader.lines().forEach(this::lambda$read$1);
                        this.setHistoryFileData(path2, new HistoryFileData(this.items.size(), this.items.size()));
                        this.maybeResize();
                    }
                }
            }
            catch (IllegalArgumentException | IOException ex3) {
                final IOException ex2;
                final IOException ex = ex2;
                Log.debug("Failed to read history; clearing", ex);
                this.internalClear();
                throw ex;
            }
        }
    }
    
    private String doHistoryFileDataKey(final Path path) {
        return (path != null) ? path.toAbsolutePath().toString() : null;
    }
    
    private HistoryFileData getHistoryFileData(final Path path) {
        final String doHistoryFileDataKey = this.doHistoryFileDataKey(path);
        if (!this.historyFiles.containsKey(doHistoryFileDataKey)) {
            this.historyFiles.put(doHistoryFileDataKey, new HistoryFileData());
        }
        return this.historyFiles.get(doHistoryFileDataKey);
    }
    
    private void setHistoryFileData(final Path path, final HistoryFileData historyFileData) {
        this.historyFiles.put(this.doHistoryFileDataKey(path), historyFileData);
    }
    
    private boolean isLineReaderHistory(final Path path) throws IOException {
        final Path path2 = this.getPath();
        if (path2 == null) {
            return path == null;
        }
        return Files.isSameFile(path2, path);
    }
    
    private void setLastLoaded(final Path path, final int lastLoaded) {
        this.getHistoryFileData(path).setLastLoaded(lastLoaded);
    }
    
    private void setEntriesInFile(final Path path, final int entriesInFile) {
        this.getHistoryFileData(path).setEntriesInFile(entriesInFile);
    }
    
    private void incEntriesInFile(final Path path, final int n) {
        this.getHistoryFileData(path).incEntriesInFile(n);
    }
    
    private int getLastLoaded(final Path path) {
        return this.getHistoryFileData(path).getLastLoaded();
    }
    
    private int getEntriesInFile(final Path path) {
        return this.getHistoryFileData(path).getEntriesInFile();
    }
    
    protected void addHistoryLine(final Path path, final String s) {
        this.addHistoryLine(path, s, false);
    }
    
    protected void addHistoryLine(final Path path, final String s, final boolean b) {
        if (this.reader.isSet(LineReader.Option.HISTORY_TIMESTAMPED)) {
            final int index = s.indexOf(58);
            final String string = "Bad history file syntax! The history file `" + path + "` may be an older history: please remove it or use a different history file.";
            if (index < 0) {
                throw new IllegalArgumentException(string);
            }
            Instant ofEpochMilli;
            try {
                ofEpochMilli = Instant.ofEpochMilli(Long.parseLong(s.substring(0, index)));
            }
            catch (DateTimeException | NumberFormatException ex) {
                throw new IllegalArgumentException(string);
            }
            this.internalAdd(ofEpochMilli, unescape(s.substring(index + 1)), b);
        }
        else {
            this.internalAdd(Instant.now(), unescape(s), b);
        }
    }
    
    @Override
    public void purge() throws IOException {
        this.internalClear();
        final Path path = this.getPath();
        if (path != null) {
            Log.trace("Purging history from: ", path);
            Files.deleteIfExists(path);
        }
    }
    
    @Override
    public void write(final Path path, final boolean b) throws IOException {
        final Path path2 = (path != null) ? path : this.getPath();
        if (path2 != null && Files.exists(path2, new LinkOption[0])) {
            path2.toFile().delete();
        }
        this.internalWrite(path2, b ? this.getLastLoaded(path2) : 0);
    }
    
    @Override
    public void append(final Path path, final boolean b) throws IOException {
        this.internalWrite((path != null) ? path : this.getPath(), b ? this.getLastLoaded(path) : 0);
    }
    
    @Override
    public void save() throws IOException {
        this.internalWrite(this.getPath(), this.getLastLoaded(this.getPath()));
    }
    
    private void internalWrite(final Path path, final int n) throws IOException {
        if (path != null) {
            Log.trace("Saving history to: ", path);
            Files.createDirectories(path.toAbsolutePath().getParent(), (FileAttribute<?>[])new FileAttribute[0]);
            try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(path.toAbsolutePath(), StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
                for (final Entry entry : this.items.subList(n, this.items.size())) {
                    if (this.isPersistable(entry)) {
                        bufferedWriter.append((CharSequence)this.format(entry));
                    }
                }
            }
            this.incEntriesInFile(path, this.items.size() - n);
            final int int1 = ReaderUtils.getInt(this.reader, "history-file-size", 10000);
            if (this.getEntriesInFile(path) > int1 + int1 / 4) {
                this.trimHistory(path, int1);
            }
        }
        this.setLastLoaded(path, this.items.size());
    }
    
    protected void trimHistory(final Path path, final int n) throws IOException {
        Log.trace("Trimming history path: ", path);
        final LinkedList<Entry> list = new LinkedList<Entry>();
        try (final BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            bufferedReader.lines().forEach(this::lambda$trimHistory$2);
        }
        doTrimHistory(list, n);
        final Path tempFile = Files.createTempFile(path.toAbsolutePath().getParent(), path.getFileName().toString(), ".tmp", (FileAttribute<?>[])new FileAttribute[0]);
        try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(tempFile, StandardOpenOption.WRITE)) {
            final Iterator<Object> iterator = list.iterator();
            while (iterator.hasNext()) {
                bufferedWriter.append((CharSequence)this.format(iterator.next()));
            }
        }
        Files.move(tempFile, path, StandardCopyOption.REPLACE_EXISTING);
        if (this.isLineReaderHistory(path)) {
            this.internalClear();
            this.offset = list.get(0).index();
            this.items.addAll(list);
            this.setHistoryFileData(path, new HistoryFileData(this.items.size(), this.items.size()));
        }
        else {
            this.setEntriesInFile(path, list.size());
        }
        this.maybeResize();
    }
    
    protected EntryImpl createEntry(final int n, final Instant instant, final String s) {
        return new EntryImpl(n, instant, s);
    }
    
    private void internalClear() {
        this.offset = 0;
        this.index = 0;
        this.historyFiles = new HashMap<String, HistoryFileData>();
        this.items.clear();
    }
    
    static void doTrimHistory(final List<Entry> list, final int n) {
        for (int i = 0; i < list.size(); ++i) {
            final int n2 = list.size() - i - 1;
            final String trim = list.get(n2).line().trim();
            final ListIterator<Entry> listIterator = list.listIterator(n2);
            while (listIterator.hasPrevious()) {
                if (trim.equals(listIterator.previous().line().trim())) {
                    listIterator.remove();
                }
            }
        }
        while (list.size() > n) {
            list.remove(0);
        }
    }
    
    @Override
    public int size() {
        return this.items.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }
    
    @Override
    public int index() {
        return this.offset + this.index;
    }
    
    @Override
    public int first() {
        return this.offset;
    }
    
    @Override
    public int last() {
        return this.offset + this.items.size() - 1;
    }
    
    private String format(final Entry entry) {
        if (this.reader.isSet(LineReader.Option.HISTORY_TIMESTAMPED)) {
            return Long.toString(entry.time().toEpochMilli()) + ":" + escape(entry.line()) + "\n";
        }
        return escape(entry.line()) + "\n";
    }
    
    @Override
    public String get(final int n) {
        return this.items.get(n - this.offset).line();
    }
    
    @Override
    public void add(final Instant instant, String trim) {
        Objects.requireNonNull(instant);
        Objects.requireNonNull(trim);
        if (ReaderUtils.getBoolean(this.reader, "disable-history", false)) {
            return;
        }
        if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_IGNORE_SPACE) && trim.startsWith(" ")) {
            return;
        }
        if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_REDUCE_BLANKS)) {
            trim = trim.trim();
        }
        if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_IGNORE_DUPS) && !this.items.isEmpty() && trim.equals(this.items.getLast().line())) {
            return;
        }
        if (this.matchPatterns(ReaderUtils.getString(this.reader, "history-ignore", ""), trim)) {
            return;
        }
        this.internalAdd(instant, trim);
        if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_INCREMENTAL)) {
            try {
                this.save();
            }
            catch (IOException ex) {
                Log.warn("Failed to save history", ex);
            }
        }
    }
    
    protected boolean matchPatterns(final String s, final String s2) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '\\') {
                sb.append(s.charAt(++i));
            }
            else if (char1 == ':') {
                sb.append('|');
            }
            else if (char1 == '*') {
                sb.append('.').append('*');
            }
        }
        return s2.matches(sb.toString());
    }
    
    protected void internalAdd(final Instant instant, final String s) {
        this.internalAdd(instant, s, false);
    }
    
    protected void internalAdd(final Instant instant, final String s, final boolean b) {
        final EntryImpl entryImpl = new EntryImpl(this.offset + this.items.size(), instant, s);
        if (b) {
            final Iterator<Entry> iterator = (Iterator<Entry>)this.items.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().line().trim().equals(s.trim())) {
                    return;
                }
            }
        }
        this.items.add(entryImpl);
        this.maybeResize();
    }
    
    private void maybeResize() {
        while (this.size() > ReaderUtils.getInt(this.reader, "history-size", 500)) {
            this.items.removeFirst();
            final Iterator<HistoryFileData> iterator = this.historyFiles.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().decLastLoaded();
            }
            ++this.offset;
        }
        this.index = this.size();
    }
    
    @Override
    public ListIterator<Entry> iterator(final int n) {
        return this.items.listIterator(n - this.offset);
    }
    
    @Override
    public Spliterator<Entry> spliterator() {
        return this.items.spliterator();
    }
    
    @Override
    public boolean moveToLast() {
        final int n = this.size() - 1;
        if (n >= 0 && n != this.index) {
            this.index = this.size() - 1;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean moveTo(int index) {
        index -= this.offset;
        if (index >= 0 && index < this.size()) {
            this.index = index;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean moveToFirst() {
        if (this.size() > 0 && this.index != 0) {
            this.index = 0;
            return true;
        }
        return false;
    }
    
    @Override
    public void moveToEnd() {
        this.index = this.size();
    }
    
    @Override
    public String current() {
        if (this.index >= this.size()) {
            return "";
        }
        return this.items.get(this.index).line();
    }
    
    @Override
    public boolean previous() {
        if (this.index <= 0) {
            return false;
        }
        --this.index;
        return true;
    }
    
    @Override
    public boolean next() {
        if (this.index >= this.size()) {
            return false;
        }
        ++this.index;
        return true;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final ListIterator<Entry> iterator = this.iterator();
        while (iterator.hasNext()) {
            sb.append(((Entry)iterator.next()).toString()).append("\n");
        }
        return sb.toString();
    }
    
    private static String escape(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            switch (char1) {
                case 10: {
                    sb.append('\\');
                    sb.append('n');
                    break;
                }
                case 13: {
                    sb.append('\\');
                    sb.append('r');
                    break;
                }
                case 92: {
                    sb.append('\\');
                    sb.append('\\');
                    break;
                }
                default: {
                    sb.append(char1);
                    break;
                }
            }
        }
        return sb.toString();
    }
    
    static String unescape(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            switch (char1) {
                case 92: {
                    final char char2 = s.charAt(++i);
                    if (char2 == 'n') {
                        sb.append('\n');
                        break;
                    }
                    if (char2 == 'r') {
                        sb.append('\r');
                        break;
                    }
                    sb.append(char2);
                    break;
                }
                default: {
                    sb.append(char1);
                    break;
                }
            }
        }
        return sb.toString();
    }
    
    private void lambda$trimHistory$2(final LinkedList list, final String s) {
        final int index = s.indexOf(58);
        list.add(this.createEntry(list.size(), Instant.ofEpochMilli(Long.parseLong(s.substring(0, index))), unescape(s.substring(index + 1))));
    }
    
    private void lambda$read$1(final Path path, final boolean b, final String s) {
        this.addHistoryLine(path, s, b);
    }
    
    private void lambda$load$0(final Path path, final String s) {
        this.addHistoryLine(path, s);
    }
    
    protected static class EntryImpl implements Entry
    {
        private final int index;
        private final Instant time;
        private final String line;
        
        public EntryImpl(final int index, final Instant time, final String line) {
            super();
            this.index = index;
            this.time = time;
            this.line = line;
        }
        
        @Override
        public int index() {
            return this.index;
        }
        
        @Override
        public Instant time() {
            return this.time;
        }
        
        @Override
        public String line() {
            return this.line;
        }
        
        @Override
        public String toString() {
            return String.format("%d: %s", this.index, this.line);
        }
    }
    
    private class HistoryFileData
    {
        private int lastLoaded;
        private int entriesInFile;
        final DefaultHistory this$0;
        
        public HistoryFileData(final DefaultHistory this$0) {
            this.this$0 = this$0;
            super();
            this.lastLoaded = 0;
            this.entriesInFile = 0;
        }
        
        public HistoryFileData(final DefaultHistory this$0, final int lastLoaded, final int entriesInFile) {
            this.this$0 = this$0;
            super();
            this.lastLoaded = 0;
            this.entriesInFile = 0;
            this.lastLoaded = lastLoaded;
            this.entriesInFile = entriesInFile;
        }
        
        public int getLastLoaded() {
            return this.lastLoaded;
        }
        
        public void setLastLoaded(final int lastLoaded) {
            this.lastLoaded = lastLoaded;
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
        
        public void setEntriesInFile(final int entriesInFile) {
            this.entriesInFile = entriesInFile;
        }
        
        public void incEntriesInFile(final int n) {
            this.entriesInFile += n;
        }
    }
}
