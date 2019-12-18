package org.jline.reader;

public interface CompletingParsedLine extends ParsedLine
{
    CharSequence escape(final CharSequence p0, final boolean p1);
    
    int rawWordCursor();
    
    int rawWordLength();
}
