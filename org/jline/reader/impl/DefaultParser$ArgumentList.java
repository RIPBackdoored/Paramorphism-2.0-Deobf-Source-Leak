package org.jline.reader.impl;

import org.jline.reader.*;
import java.util.*;
import java.util.function.*;

public class ArgumentList implements ParsedLine, CompletingParsedLine
{
    private final String line;
    private final List<String> words;
    private final int wordIndex;
    private final int wordCursor;
    private final int cursor;
    private final String openingQuote;
    private final int rawWordCursor;
    private final int rawWordLength;
    final DefaultParser this$0;
    
    @Deprecated
    public ArgumentList(final DefaultParser defaultParser, final String s, final List<String> list, final int n, final int n2, final int n3) {
        this(s, list, n, n2, n3, null, n2, list.get(n).length());
    }
    
    public ArgumentList(final DefaultParser this$0, final String line, final List<String> list, final int wordIndex, final int wordCursor, final int cursor, final String openingQuote, final int rawWordCursor, final int rawWordLength) {
        this.this$0 = this$0;
        super();
        this.line = line;
        this.words = Collections.unmodifiableList((List<? extends String>)Objects.requireNonNull((List<? extends T>)list));
        this.wordIndex = wordIndex;
        this.wordCursor = wordCursor;
        this.cursor = cursor;
        this.openingQuote = openingQuote;
        this.rawWordCursor = rawWordCursor;
        this.rawWordLength = rawWordLength;
    }
    
    @Override
    public int wordIndex() {
        return this.wordIndex;
    }
    
    @Override
    public String word() {
        if (this.wordIndex < 0 || this.wordIndex >= this.words.size()) {
            return "";
        }
        return this.words.get(this.wordIndex);
    }
    
    @Override
    public int wordCursor() {
        return this.wordCursor;
    }
    
    @Override
    public List<String> words() {
        return this.words;
    }
    
    @Override
    public int cursor() {
        return this.cursor;
    }
    
    @Override
    public String line() {
        return this.line;
    }
    
    @Override
    public CharSequence escape(final CharSequence charSequence, final boolean b) {
        final StringBuilder sb = new StringBuilder(charSequence);
        String openingQuote = this.openingQuote;
        boolean b2 = false;
        if (this.openingQuote == null) {
            for (int i = 0; i < sb.length(); ++i) {
                if (this.this$0.isQuoteChar(sb, i)) {
                    b2 = true;
                    break;
                }
            }
        }
        if (DefaultParser.access$200(this.this$0) != null) {
            Predicate predicate;
            if (this.openingQuote != null) {
                predicate = this::lambda$escape$0;
            }
            else if (b2) {
                predicate = this::lambda$escape$1;
            }
            else {
                predicate = this::lambda$escape$2;
            }
            for (int j = 0; j < sb.length(); ++j) {
                if (predicate.test(j)) {
                    sb.insert(j++, DefaultParser.access$200(this.this$0)[0]);
                }
            }
        }
        else if (this.openingQuote == null && !b2) {
            for (int k = 0; k < sb.length(); ++k) {
                if (this.this$0.isDelimiterChar(sb, k)) {
                    openingQuote = "'";
                    break;
                }
            }
        }
        if (openingQuote != null) {
            sb.insert(0, openingQuote);
            if (b) {
                sb.append(openingQuote);
            }
        }
        return sb;
    }
    
    @Override
    public int rawWordCursor() {
        return this.rawWordCursor;
    }
    
    @Override
    public int rawWordLength() {
        return this.rawWordLength;
    }
    
    private boolean lambda$escape$2(final StringBuilder sb, final Integer n) {
        return this.this$0.isDelimiterChar(sb, n) || DefaultParser.access$300(this.this$0, sb.charAt(n)) || DefaultParser.access$400(this.this$0, sb.charAt(n));
    }
    
    private boolean lambda$escape$1(final StringBuilder sb, final Integer n) {
        return DefaultParser.access$300(this.this$0, sb.charAt(n));
    }
    
    private boolean lambda$escape$0(final StringBuilder sb, final Integer n) {
        return DefaultParser.access$300(this.this$0, sb.charAt(n)) || String.valueOf(sb.charAt(n)).equals(this.openingQuote);
    }
}
