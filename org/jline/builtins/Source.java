package org.jline.builtins;

import java.io.IOException;
import java.io.InputStream;

public interface Source {
   String getName();

   InputStream read() throws IOException;
}
