package org.jline.reader;

import java.util.*;

public interface Completer
{
    void complete(final LineReader p0, final ParsedLine p1, final List<Candidate> p2);
}
