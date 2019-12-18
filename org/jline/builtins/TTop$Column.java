package org.jline.builtins;

import java.util.function.*;

private static class Column
{
    final String name;
    final Align align;
    final String header;
    final Function<Object, String> format;
    
    Column(final String name, final Align align, final String header, final Function<Object, String> format) {
        super();
        this.name = name;
        this.align = align;
        this.header = header;
        this.format = format;
    }
}
