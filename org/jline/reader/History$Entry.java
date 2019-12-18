package org.jline.reader;

import java.time.Instant;

public interface History$Entry {
   int index();

   Instant time();

   String line();
}
