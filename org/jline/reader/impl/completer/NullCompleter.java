package org.jline.reader.impl.completer;

import java.util.*;
import org.jline.reader.*;

public final class NullCompleter implements Completer
{
    public static final NullCompleter INSTANCE;
    
    public NullCompleter() {
        super();
    }
    
    @Override
    public void complete(final LineReader lineReader, final ParsedLine parsedLine, final List<Candidate> list) {
    }
    
    static {
        INSTANCE = new NullCompleter();
    }
}
