package org.jline.utils;

import org.jline.terminal.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;

public class Display
{
    protected final Terminal terminal;
    protected final boolean fullScreen;
    protected List<AttributedString> oldLines;
    protected int cursorPos;
    private int columns;
    private int columns1;
    protected int rows;
    protected boolean reset;
    protected boolean delayLineWrap;
    protected final Map<InfoCmp.Capability, Integer> cost;
    protected final boolean canScroll;
    protected final boolean wrapAtEol;
    protected final boolean delayedWrapAtEol;
    protected final boolean cursorDownIsNewLine;
    
    public Display(final Terminal terminal, final boolean fullScreen) {
        super();
        this.oldLines = Collections.emptyList();
        this.cost = new HashMap<InfoCmp.Capability, Integer>();
        this.terminal = terminal;
        this.fullScreen = fullScreen;
        this.canScroll = (this.can(InfoCmp.Capability.insert_line, InfoCmp.Capability.parm_insert_line) && this.can(InfoCmp.Capability.delete_line, InfoCmp.Capability.parm_delete_line));
        this.wrapAtEol = terminal.getBooleanCapability(InfoCmp.Capability.auto_right_margin);
        this.delayedWrapAtEol = (this.wrapAtEol && terminal.getBooleanCapability(InfoCmp.Capability.eat_newline_glitch));
        this.cursorDownIsNewLine = "\n".equals(Curses.tputs(terminal.getStringCapability(InfoCmp.Capability.cursor_down), new Object[0]));
    }
    
    public boolean delayLineWrap() {
        return this.delayLineWrap;
    }
    
    public void setDelayLineWrap(final boolean delayLineWrap) {
        this.delayLineWrap = delayLineWrap;
    }
    
    public void resize(final int rows, final int columns) {
        if (this.rows != rows || this.columns != columns) {
            this.rows = rows;
            this.columns = columns;
            this.columns1 = columns + 1;
            this.oldLines = AttributedString.join(AttributedString.EMPTY, this.oldLines).columnSplitLength(columns, true, this.delayLineWrap());
        }
    }
    
    public void reset() {
        this.oldLines = Collections.emptyList();
    }
    
    public void clear() {
        if (this.fullScreen) {
            this.reset = true;
        }
    }
    
    public void updateAnsi(final List<String> list, final int n) {
        this.update((List<AttributedString>)list.stream().map((Function<? super Object, ?>)AttributedString::fromAnsi).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()), n);
    }
    
    public void update(final List<AttributedString> list, final int n) {
        this.update(list, n, true);
    }
    
    public void update(List<AttributedString> oldLines, int n, final boolean b) {
        if (this.reset) {
            this.terminal.puts(InfoCmp.Capability.clear_screen, new Object[0]);
            this.oldLines.clear();
            this.cursorPos = 0;
            this.reset = false;
        }
        final Integer numericCapability = this.terminal.getNumericCapability(InfoCmp.Capability.max_colors);
        if (numericCapability == null || numericCapability < 8) {
            oldLines = oldLines.stream().map((Function<? super Object, ?>)Display::lambda$update$0).collect((Collector<? super Object, ?, List<AttributedString>>)Collectors.toList());
        }
        if ((this.fullScreen || oldLines.size() >= this.rows) && oldLines.size() == this.oldLines.size() && this.canScroll) {
            int n2 = 0;
            int n3 = 0;
            int size;
            for (size = oldLines.size(); n2 < size && Objects.equals(oldLines.get(n2), this.oldLines.get(n2)); ++n2) {}
            while (n3 < size - n2 - 1 && Objects.equals(oldLines.get(oldLines.size() - n3 - 1), this.oldLines.get(this.oldLines.size() - n3 - 1))) {
                ++n3;
            }
            final int[] longestCommon = longestCommon(oldLines.subList(n2, oldLines.size() - n3), this.oldLines.subList(n2, this.oldLines.size() - n3));
            if (longestCommon != null) {
                final int n4 = longestCommon[0];
                final int n5 = longestCommon[1];
                final int n6 = longestCommon[2];
                if (n6 > 1 && n4 < n5) {
                    this.moveVisualCursorTo((n2 + n4) * this.columns1);
                    final int n7 = n5 - n4;
                    this.deleteLines(n7);
                    for (int i = 0; i < n7; ++i) {
                        this.oldLines.remove(n2 + n4);
                    }
                    if (n3 > 0) {
                        this.moveVisualCursorTo((n2 + n4 + n6) * this.columns1);
                        this.insertLines(n7);
                        for (int j = 0; j < n7; ++j) {
                            this.oldLines.add(n2 + n4 + n6, new AttributedString(""));
                        }
                    }
                }
                else if (n6 > 1 && n4 > n5) {
                    final int n8 = n4 - n5;
                    if (n3 > 0) {
                        this.moveVisualCursorTo((n2 + n5 + n6) * this.columns1);
                        this.deleteLines(n8);
                        for (int k = 0; k < n8; ++k) {
                            this.oldLines.remove(n2 + n5 + n6);
                        }
                    }
                    this.moveVisualCursorTo((n2 + n5) * this.columns1);
                    this.insertLines(n8);
                    for (int l = 0; l < n8; ++l) {
                        this.oldLines.add(n2 + n5, new AttributedString(""));
                    }
                }
            }
        }
        int n9 = 0;
        int n10 = 0;
        final int max = Math.max(this.oldLines.size(), oldLines.size());
        int n11 = 0;
        while (n9 < max) {
            AttributedString attributedString = (n9 < this.oldLines.size()) ? this.oldLines.get(n9) : AttributedString.NEWLINE;
            AttributedString attributedString2 = (n9 < oldLines.size()) ? oldLines.get(n9) : AttributedString.NEWLINE;
            final int cursorPos;
            n10 = (cursorPos = n9 * this.columns1);
            int length = attributedString.length();
            int length2 = attributedString2.length();
            final boolean b2 = length > 0 && attributedString.charAt(length - 1) == '\n';
            final boolean b3 = length2 > 0 && attributedString2.charAt(length2 - 1) == '\n';
            if (b2) {
                --length;
                attributedString = attributedString.substring(0, length);
            }
            if (b3) {
                --length2;
                attributedString2 = attributedString2.substring(0, length2);
            }
            if (n11 != 0 && n9 == (this.cursorPos + 1) / this.columns1 && n9 < oldLines.size()) {
                ++this.cursorPos;
                if (length2 == 0 || attributedString2.isHidden(0)) {
                    this.rawPrint(new AttributedString(" \b"));
                }
                else {
                    final AttributedString columnSubSequence = attributedString2.columnSubSequence(0, 1);
                    this.rawPrint(columnSubSequence);
                    ++this.cursorPos;
                    final int length3 = columnSubSequence.length();
                    attributedString2 = attributedString2.substring(length3, length2);
                    if (length >= length3) {
                        attributedString = attributedString.substring(length3, length);
                    }
                    n10 = this.cursorPos;
                }
            }
            final List<DiffHelper.Diff> diff = DiffHelper.diff(attributedString, attributedString2);
            int n12 = 1;
            int n13 = 0;
            for (int n14 = 0; n14 < diff.size(); ++n14) {
                final DiffHelper.Diff diff2 = diff.get(n14);
                final int columnLength = diff2.text.columnLength();
                switch (diff2.operation) {
                    case EQUAL: {
                        if (n12 == 0) {
                            this.cursorPos = this.moveVisualCursorTo(n10);
                            this.rawPrint(diff2.text);
                            this.cursorPos += columnLength;
                            n10 = this.cursorPos;
                            break;
                        }
                        n10 += columnLength;
                        break;
                    }
                    case INSERT: {
                        if (n14 <= diff.size() - 2 && diff.get(n14 + 1).operation == DiffHelper.Operation.EQUAL) {
                            this.cursorPos = this.moveVisualCursorTo(n10);
                            if (this.insertChars(columnLength)) {
                                this.rawPrint(diff2.text);
                                this.cursorPos += columnLength;
                                n10 = this.cursorPos;
                                break;
                            }
                        }
                        else if (n14 <= diff.size() - 2 && diff.get(n14 + 1).operation == DiffHelper.Operation.DELETE && columnLength == diff.get(n14 + 1).text.columnLength()) {
                            this.moveVisualCursorTo(n10);
                            this.rawPrint(diff2.text);
                            this.cursorPos += columnLength;
                            n10 = this.cursorPos;
                            ++n14;
                            break;
                        }
                        this.moveVisualCursorTo(n10);
                        this.rawPrint(diff2.text);
                        this.cursorPos += columnLength;
                        n10 = this.cursorPos;
                        n12 = 0;
                        break;
                    }
                    case DELETE: {
                        if (n13 != 0) {
                            break;
                        }
                        if (n10 - cursorPos >= this.columns) {
                            break;
                        }
                        if (n14 <= diff.size() - 2 && diff.get(n14 + 1).operation == DiffHelper.Operation.EQUAL && n10 + diff.get(n14 + 1).text.columnLength() < this.columns) {
                            this.moveVisualCursorTo(n10);
                            if (this.deleteChars(columnLength)) {
                                break;
                            }
                        }
                        final int n15 = Math.max(attributedString.columnLength(), attributedString2.columnLength()) - (n10 - cursorPos);
                        this.moveVisualCursorTo(n10);
                        if (!this.terminal.puts(InfoCmp.Capability.clr_eol, new Object[0])) {
                            this.rawPrint(' ', n15);
                            this.cursorPos += n15;
                        }
                        n13 = 1;
                        n12 = 0;
                        break;
                    }
                }
            }
            ++n9;
            final boolean b4 = !b3 && n9 < oldLines.size();
            if (n + 1 == n9 * this.columns1 && (b4 || !this.delayLineWrap)) {
                ++n;
            }
            final boolean b5 = (this.cursorPos - cursorPos) % this.columns1 == this.columns;
            n11 = 0;
            if (this.delayedWrapAtEol) {
                final boolean b6 = !b2 && n9 < this.oldLines.size();
                if (b4 == b6 || (b6 && n13 != 0)) {
                    continue;
                }
                this.moveVisualCursorTo(n9 * this.columns1 - 1, oldLines);
                if (b4) {
                    n11 = 1;
                }
                else {
                    this.terminal.puts(InfoCmp.Capability.clr_eol, new Object[0]);
                }
            }
            else {
                if (!b5) {
                    continue;
                }
                if (this.wrapAtEol) {
                    this.terminal.writer().write(" \b");
                    ++this.cursorPos;
                }
                else {
                    this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
                    this.cursorPos = cursorPos;
                }
                n10 = this.cursorPos;
            }
        }
        final int cursorPos2 = this.cursorPos;
        if (this.cursorPos != n) {
            this.moveVisualCursorTo((n < 0) ? n10 : n, oldLines);
        }
        this.oldLines = oldLines;
        if (b) {
            this.terminal.flush();
        }
    }
    
    protected boolean deleteLines(final int n) {
        return this.perform(InfoCmp.Capability.delete_line, InfoCmp.Capability.parm_delete_line, n);
    }
    
    protected boolean insertLines(final int n) {
        return this.perform(InfoCmp.Capability.insert_line, InfoCmp.Capability.parm_insert_line, n);
    }
    
    protected boolean insertChars(final int n) {
        return this.perform(InfoCmp.Capability.insert_character, InfoCmp.Capability.parm_ich, n);
    }
    
    protected boolean deleteChars(final int n) {
        return this.perform(InfoCmp.Capability.delete_character, InfoCmp.Capability.parm_dch, n);
    }
    
    protected boolean can(final InfoCmp.Capability capability, final InfoCmp.Capability capability2) {
        return this.terminal.getStringCapability(capability) != null || this.terminal.getStringCapability(capability2) != null;
    }
    
    protected boolean perform(final InfoCmp.Capability capability, final InfoCmp.Capability capability2, final int n) {
        final boolean b = this.terminal.getStringCapability(capability2) != null;
        final boolean b2 = this.terminal.getStringCapability(capability) != null;
        if (b && (!b2 || this.cost(capability) * n > this.cost(capability2))) {
            this.terminal.puts(capability2, n);
            return true;
        }
        if (b2) {
            for (int i = 0; i < n; ++i) {
                this.terminal.puts(capability, new Object[0]);
            }
            return true;
        }
        return false;
    }
    
    private int cost(final InfoCmp.Capability capability) {
        return this.cost.computeIfAbsent(capability, this::computeCost);
    }
    
    private int computeCost(final InfoCmp.Capability capability) {
        final String tputs = Curses.tputs(this.terminal.getStringCapability(capability), 0);
        return (tputs != null) ? tputs.length() : 0;
    }
    
    private static int[] longestCommon(final List<AttributedString> list, final List<AttributedString> list2) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < list.size(); ++i) {
            for (int j = 0; j < list2.size(); ++j) {
                int n4 = 0;
                while (Objects.equals(list.get(i + n4), list2.get(j + n4))) {
                    ++n4;
                    if (i + n4 >= list.size() || j + n4 >= list2.size()) {
                        break;
                    }
                }
                if (n4 > n3) {
                    n3 = n4;
                    n = i;
                    n2 = j;
                }
            }
        }
        return (int[])((n3 != 0) ? new int[] { n, n2, n3 } : null);
    }
    
    protected void moveVisualCursorTo(final int n, final List<AttributedString> list) {
        if (this.cursorPos != n) {
            final int n2 = (n % this.columns1 == this.columns) ? 1 : 0;
            this.moveVisualCursorTo(n - n2);
            if (n2 != 0) {
                final int n3 = n / this.columns1;
                final AttributedString attributedString = (n3 >= list.size()) ? AttributedString.EMPTY : list.get(n3).columnSubSequence(this.columns - 1, this.columns);
                if (attributedString.length() == 0) {
                    this.rawPrint(32);
                }
                else {
                    this.rawPrint(attributedString);
                }
                ++this.cursorPos;
            }
        }
    }
    
    protected int moveVisualCursorTo(final int cursorPos) {
        final int cursorPos2 = this.cursorPos;
        if (cursorPos2 == cursorPos) {
            return cursorPos;
        }
        final int columns1 = this.columns1;
        final int n = cursorPos2 / columns1;
        int n2 = cursorPos2 % columns1;
        final int n3 = cursorPos / columns1;
        final int n4 = cursorPos % columns1;
        if (n2 == this.columns) {
            this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
            n2 = 0;
        }
        if (n > n3) {
            this.perform(InfoCmp.Capability.cursor_up, InfoCmp.Capability.parm_up_cursor, n - n3);
        }
        else if (n < n3) {
            if (this.fullScreen) {
                if (!this.terminal.puts(InfoCmp.Capability.parm_down_cursor, n3 - n)) {
                    for (int i = n; i < n3; ++i) {
                        this.terminal.puts(InfoCmp.Capability.cursor_down, new Object[0]);
                    }
                    if (this.cursorDownIsNewLine) {
                        n2 = 0;
                    }
                }
            }
            else {
                this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
                this.rawPrint('\n', n3 - n);
                n2 = 0;
            }
        }
        if (n2 != 0 && n4 == 0) {
            this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
        }
        else if (n2 < n4) {
            this.perform(InfoCmp.Capability.cursor_right, InfoCmp.Capability.parm_right_cursor, n4 - n2);
        }
        else if (n2 > n4) {
            this.perform(InfoCmp.Capability.cursor_left, InfoCmp.Capability.parm_left_cursor, n2 - n4);
        }
        return this.cursorPos = cursorPos;
    }
    
    void rawPrint(final char c, final int n) {
        for (int i = 0; i < n; ++i) {
            this.rawPrint(c);
        }
    }
    
    void rawPrint(final int n) {
        this.terminal.writer().write(n);
    }
    
    void rawPrint(final AttributedString attributedString) {
        attributedString.print(this.terminal);
    }
    
    public int wcwidth(final String s) {
        return AttributedString.fromAnsi(s).columnLength();
    }
    
    private static AttributedString lambda$update$0(final AttributedString attributedString) {
        return new AttributedString(attributedString.toString());
    }
}
