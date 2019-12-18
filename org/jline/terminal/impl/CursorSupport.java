package org.jline.terminal.impl;

import java.util.function.*;
import org.jline.terminal.*;
import org.jline.utils.*;
import java.io.*;
import java.util.regex.*;

public class CursorSupport
{
    public CursorSupport() {
        super();
    }
    
    public static Cursor getCursorPosition(final Terminal terminal, final IntConsumer intConsumer) {
        try {
            final String stringCapability = terminal.getStringCapability(InfoCmp.Capability.user6);
            final String stringCapability2 = terminal.getStringCapability(InfoCmp.Capability.user7);
            if (stringCapability == null || stringCapability2 == null) {
                return null;
            }
            boolean b = false;
            final StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < stringCapability.length()) {
                final char char1;
                switch (char1 = stringCapability.charAt(i++)) {
                    case '\\': {
                        switch (stringCapability.charAt(i++)) {
                            case 'E':
                            case 'e': {
                                sb.append("\\x1b");
                                continue;
                            }
                            default: {
                                throw new IllegalArgumentException();
                            }
                        }
                        break;
                    }
                    case '%': {
                        switch (stringCapability.charAt(i++)) {
                            case '%': {
                                sb.append('%');
                                continue;
                            }
                            case 'i': {
                                b = true;
                                continue;
                            }
                            case 'd': {
                                sb.append("([0-9]+)");
                                continue;
                            }
                            default: {
                                throw new IllegalArgumentException();
                            }
                        }
                        break;
                    }
                    default: {
                        switch (char1) {
                            case 91: {
                                sb.append('\\');
                                break;
                            }
                        }
                        sb.append(char1);
                        continue;
                    }
                }
            }
            final Pattern compile = Pattern.compile(sb.toString());
            Curses.tputs(terminal.writer(), stringCapability2, new Object[0]);
            terminal.flush();
            final StringBuilder sb2 = new StringBuilder();
            int n = 0;
            while (true) {
                final int read = terminal.reader().read();
                if (read < 0) {
                    return null;
                }
                sb2.append((char)read);
                final Matcher matcher = compile.matcher(sb2.substring(n));
                if (matcher.matches()) {
                    int int1 = Integer.parseInt(matcher.group(1));
                    int int2 = Integer.parseInt(matcher.group(2));
                    if (b) {
                        --int2;
                        --int1;
                    }
                    if (intConsumer != null) {
                        for (int j = 0; j < n; ++j) {
                            intConsumer.accept(sb2.charAt(j));
                        }
                    }
                    return new Cursor(int2, int1);
                }
                if (matcher.hitEnd()) {
                    continue;
                }
                ++n;
            }
        }
        catch (IOException ex) {
            throw new IOError(ex);
        }
    }
}
