package org.jline.builtins.telnet;

import java.io.*;
import java.util.logging.*;

class IACHandler
{
    private int[] buffer;
    private boolean DO_ECHO;
    private boolean DO_SUPGA;
    private boolean DO_NAWS;
    private boolean DO_TTYPE;
    private boolean DO_LINEMODE;
    private boolean DO_NEWENV;
    private boolean WAIT_DO_REPLY_SUPGA;
    private boolean WAIT_DO_REPLY_ECHO;
    private boolean WAIT_DO_REPLY_NAWS;
    private boolean WAIT_DO_REPLY_TTYPE;
    private boolean WAIT_DO_REPLY_LINEMODE;
    private boolean WAIT_LM_MODE_ACK;
    private boolean WAIT_LM_DO_REPLY_FORWARDMASK;
    private boolean WAIT_DO_REPLY_NEWENV;
    private boolean WAIT_NE_SEND_REPLY;
    private boolean WAIT_WILL_REPLY_SUPGA;
    private boolean WAIT_WILL_REPLY_ECHO;
    private boolean WAIT_WILL_REPLY_NAWS;
    private boolean WAIT_WILL_REPLY_TTYPE;
    final TelnetIO this$0;
    
    IACHandler(final TelnetIO this$0) {
        this.this$0 = this$0;
        super();
        this.buffer = new int[2];
        this.DO_ECHO = false;
        this.DO_SUPGA = false;
        this.DO_NAWS = false;
        this.DO_TTYPE = false;
        this.DO_LINEMODE = false;
        this.DO_NEWENV = false;
        this.WAIT_DO_REPLY_SUPGA = false;
        this.WAIT_DO_REPLY_ECHO = false;
        this.WAIT_DO_REPLY_NAWS = false;
        this.WAIT_DO_REPLY_TTYPE = false;
        this.WAIT_DO_REPLY_LINEMODE = false;
        this.WAIT_LM_MODE_ACK = false;
        this.WAIT_LM_DO_REPLY_FORWARDMASK = false;
        this.WAIT_DO_REPLY_NEWENV = false;
        this.WAIT_NE_SEND_REPLY = false;
        this.WAIT_WILL_REPLY_SUPGA = false;
        this.WAIT_WILL_REPLY_ECHO = false;
        this.WAIT_WILL_REPLY_NAWS = false;
        this.WAIT_WILL_REPLY_TTYPE = false;
    }
    
    public void doCharacterModeInit() throws IOException {
        this.sendCommand(251, 1, true);
        this.sendCommand(254, 1, true);
        this.sendCommand(253, 31, true);
        this.sendCommand(251, 3, true);
        this.sendCommand(253, 3, true);
        this.sendCommand(253, 24, true);
        this.sendCommand(253, 39, true);
    }
    
    public void doLineModeInit() throws IOException {
        this.sendCommand(253, 31, true);
        this.sendCommand(251, 3, true);
        this.sendCommand(253, 3, true);
        this.sendCommand(253, 24, true);
        this.sendCommand(253, 34, true);
        this.sendCommand(253, 39, true);
    }
    
    public void handleC(final int n) throws IOException {
        this.buffer[0] = n;
        if (!this.parseTWO(this.buffer)) {
            this.buffer[1] = TelnetIO.access$000(this.this$0);
            this.parse(this.buffer);
        }
        this.buffer[0] = 0;
        this.buffer[1] = 0;
    }
    
    private boolean parseTWO(final int[] array) {
        switch (array[0]) {
            case 255: {
                break;
            }
            case 246: {
                TelnetIO.access$100(this.this$0);
                break;
            }
            case 241:
            case 244:
            case 245:
            case 247:
            case 248: {
                break;
            }
            case 243: {
                TelnetIO.access$200(this.this$0);
                break;
            }
            default: {
                return false;
            }
        }
        return true;
    }
    
    private void parse(final int[] array) throws IOException {
        Label_0515: {
            switch (array[0]) {
                case 251: {
                    if (this.supported(array[1]) && this.isEnabled(array[1])) {
                        break;
                    }
                    if (this.waitDOreply(array[1]) && this.supported(array[1])) {
                        this.enable(array[1]);
                        this.setWait(253, array[1], false);
                        break;
                    }
                    if (this.supported(array[1])) {
                        this.sendCommand(253, array[1], false);
                        this.enable(array[1]);
                        break;
                    }
                    this.sendCommand(254, array[1], false);
                    break;
                }
                case 252: {
                    if (this.waitDOreply(array[1]) && this.supported(array[1])) {
                        this.setWait(253, array[1], false);
                        break;
                    }
                    if (this.supported(array[1]) && this.isEnabled(array[1])) {
                        this.enable(array[1]);
                        break;
                    }
                    break;
                }
                case 253: {
                    if (this.supported(array[1]) && this.isEnabled(array[1])) {
                        break;
                    }
                    if (this.waitWILLreply(array[1]) && this.supported(array[1])) {
                        this.enable(array[1]);
                        this.setWait(251, array[1], false);
                        break;
                    }
                    if (this.supported(array[1])) {
                        this.sendCommand(251, array[1], false);
                        this.enable(array[1]);
                        break;
                    }
                    this.sendCommand(252, array[1], false);
                    break;
                }
                case 254: {
                    if (this.waitWILLreply(array[1]) && this.supported(array[1])) {
                        this.setWait(251, array[1], false);
                        break;
                    }
                    if (this.supported(array[1]) && this.isEnabled(array[1])) {
                        this.enable(array[1]);
                        break;
                    }
                    break;
                }
                case 250: {
                    if (!this.supported(array[1]) || !this.isEnabled(array[1])) {
                        break;
                    }
                    switch (array[1]) {
                        case 31: {
                            this.handleNAWS();
                            break Label_0515;
                        }
                        case 24: {
                            this.handleTTYPE();
                            break Label_0515;
                        }
                        case 34: {
                            this.handleLINEMODE();
                            break Label_0515;
                        }
                        case 39: {
                            this.handleNEWENV();
                            break Label_0515;
                        }
                        default: {
                            break Label_0515;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    private void handleNAWS() throws IOException {
        int n = TelnetIO.access$300(this.this$0);
        if (n == 255) {
            n = TelnetIO.access$300(this.this$0);
        }
        int n2 = TelnetIO.access$300(this.this$0);
        if (n2 == 255) {
            n2 = TelnetIO.access$300(this.this$0);
        }
        this.skipToSE();
        TelnetIO.access$400(this.this$0, n, n2);
    }
    
    private void handleTTYPE() throws IOException {
        TelnetIO.access$000(this.this$0);
        final String iacseTerminatedString = this.readIACSETerminatedString(40);
        TelnetIO.access$500().log(Level.FINE, "Reported terminal name " + iacseTerminatedString);
        TelnetIO.access$600(this.this$0).setNegotiatedTerminalType(iacseTerminatedString);
    }
    
    public void handleLINEMODE() throws IOException {
        final int access$000 = TelnetIO.access$000(this.this$0);
        switch (access$000) {
            case 1: {
                this.handleLMMode();
                break;
            }
            case 3: {
                this.handleLMSLC();
                break;
            }
            case 251:
            case 252: {
                this.handleLMForwardMask(access$000);
                break;
            }
            default: {
                this.skipToSE();
                break;
            }
        }
    }
    
    public void handleLMMode() throws IOException {
        if (this.WAIT_LM_MODE_ACK) {
            final int access$000 = TelnetIO.access$000(this.this$0);
            if (access$000 != 7) {
                TelnetIO.access$500().log(Level.FINE, "Client violates linemodeack sent: " + access$000);
            }
            this.WAIT_LM_MODE_ACK = false;
        }
        this.skipToSE();
    }
    
    public void handleLMSLC() throws IOException {
        final int[] array = new int[3];
        if (!this.readTriple(array)) {
            return;
        }
        if (array[0] == 0 && array[1] == 3 && array[2] == 0) {
            this.skipToSE();
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 250);
            TelnetIO.access$700(this.this$0, 34);
            TelnetIO.access$700(this.this$0, 3);
            for (int i = 1; i < 12; ++i) {
                TelnetIO.access$700(this.this$0, i);
                TelnetIO.access$700(this.this$0, 3);
                TelnetIO.access$700(this.this$0, 0);
            }
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 240);
            this.this$0.flush();
        }
        else {
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 250);
            TelnetIO.access$700(this.this$0, 34);
            TelnetIO.access$700(this.this$0, 3);
            TelnetIO.access$700(this.this$0, array[0]);
            TelnetIO.access$700(this.this$0, array[1] | 0x80);
            TelnetIO.access$700(this.this$0, array[2]);
            while (this.readTriple(array)) {
                TelnetIO.access$700(this.this$0, array[0]);
                TelnetIO.access$700(this.this$0, array[1] | 0x80);
                TelnetIO.access$700(this.this$0, array[2]);
            }
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 240);
            this.this$0.flush();
        }
    }
    
    public void handleLMForwardMask(final int n) throws IOException {
        switch (n) {
            case 252: {
                if (this.WAIT_LM_DO_REPLY_FORWARDMASK) {
                    this.WAIT_LM_DO_REPLY_FORWARDMASK = false;
                    break;
                }
                break;
            }
        }
        this.skipToSE();
    }
    
    public void handleNEWENV() throws IOException {
        TelnetIO.access$500().log(Level.FINE, "handleNEWENV()");
        switch (TelnetIO.access$000(this.this$0)) {
            case 0: {
                this.handleNEIs();
                break;
            }
            case 2: {
                this.handleNEInfo();
                break;
            }
            default: {
                this.skipToSE();
                break;
            }
        }
    }
    
    private int readNEVariableName(final StringBuffer sb) throws IOException {
        TelnetIO.access$500().log(Level.FINE, "readNEVariableName()");
        while (true) {
            final int access$000 = TelnetIO.access$000(this.this$0);
            if (access$000 == -1) {
                return -2;
            }
            if (access$000 == 255) {
                final int access$2 = TelnetIO.access$000(this.this$0);
                if (access$2 == 255) {
                    sb.append((char)access$2);
                }
                else {
                    if (access$2 == 240) {
                        return -3;
                    }
                    return -2;
                }
            }
            else if (access$000 == 2) {
                final int access$3 = TelnetIO.access$000(this.this$0);
                if (access$3 != 2 && access$3 != 0 && access$3 != 3 && access$3 != 1) {
                    return -2;
                }
                sb.append((char)access$3);
            }
            else {
                if (access$000 == 0 || access$000 == 3) {
                    return -1;
                }
                if (access$000 == 1) {
                    return 1;
                }
                if (sb.length() >= 50) {
                    return -2;
                }
                sb.append((char)access$000);
            }
        }
    }
    
    private int readNEVariableValue(final StringBuffer sb) throws IOException {
        TelnetIO.access$500().log(Level.FINE, "readNEVariableValue()");
        final int access$000 = TelnetIO.access$000(this.this$0);
        if (access$000 == -1) {
            return -2;
        }
        if (access$000 == 255) {
            final int access$2 = TelnetIO.access$000(this.this$0);
            if (access$2 == 255) {
                return 0;
            }
            if (access$2 == 240) {
                return -3;
            }
            return -2;
        }
        else {
            if (access$000 == 0 || access$000 == 3) {
                return 0;
            }
            if (access$000 == 2) {
                final int access$3 = TelnetIO.access$000(this.this$0);
                if (access$3 != 2 && access$3 != 0 && access$3 != 3 && access$3 != 1) {
                    return -2;
                }
                sb.append((char)access$3);
            }
            else {
                sb.append((char)access$000);
            }
            while (true) {
                final int access$4 = TelnetIO.access$000(this.this$0);
                if (access$4 == -1) {
                    return -2;
                }
                if (access$4 == 255) {
                    final int access$5 = TelnetIO.access$000(this.this$0);
                    if (access$5 == 255) {
                        sb.append((char)access$5);
                    }
                    else {
                        if (access$5 == 240) {
                            return -3;
                        }
                        return -2;
                    }
                }
                else if (access$4 == 2) {
                    final int access$6 = TelnetIO.access$000(this.this$0);
                    if (access$6 != 2 && access$6 != 0 && access$6 != 3 && access$6 != 1) {
                        return -2;
                    }
                    sb.append((char)access$6);
                }
                else {
                    if (access$4 == 0 || access$4 == 3) {
                        return 2;
                    }
                    if (sb.length() > 1000) {
                        return -2;
                    }
                    sb.append((char)access$4);
                }
            }
        }
    }
    
    public void readNEVariables() throws IOException {
        TelnetIO.access$500().log(Level.FINE, "readNEVariables()");
        final StringBuffer sb = new StringBuffer(50);
        final int access$000 = TelnetIO.access$000(this.this$0);
        if (access$000 == 255) {
            this.skipToSE();
            TelnetIO.access$500().log(Level.FINE, "readNEVariables()::INVALID VARIABLE");
            return;
        }
        final boolean b = true;
        if (access$000 == 0 || access$000 == 3) {
            do {
                switch (this.readNEVariableName(sb)) {
                    case -2: {
                        TelnetIO.access$500().log(Level.FINE, "readNEVariables()::NE_IN_ERROR");
                    }
                    case -3: {
                        TelnetIO.access$500().log(Level.FINE, "readNEVariables()::NE_IN_END");
                    }
                    default: {
                        continue;
                    }
                    case 1: {
                        TelnetIO.access$500().log(Level.FINE, "readNEVariables()::NE_VAR_DEFINED");
                        final String string = sb.toString();
                        sb.delete(0, sb.length());
                        switch (this.readNEVariableValue(sb)) {
                            case -2: {
                                TelnetIO.access$500().log(Level.FINE, "readNEVariables()::NE_IN_ERROR");
                                return;
                            }
                            case -3: {
                                TelnetIO.access$500().log(Level.FINE, "readNEVariables()::NE_IN_END");
                                return;
                            }
                            case 0: {
                                TelnetIO.access$500().log(Level.FINE, "readNEVariables()::NE_VAR_DEFINED_EMPTY");
                                continue;
                            }
                            case 2: {
                                TelnetIO.access$500().log(Level.FINE, "readNEVariables()::NE_VAR_OK:VAR=" + string + " VAL=" + sb.toString());
                                TelnetIO.access$600(this.this$0).getEnvironment().put(string, sb.toString());
                                sb.delete(0, sb.length());
                                continue;
                            }
                        }
                        continue;
                    }
                    case -1: {
                        TelnetIO.access$500().log(Level.FINE, "readNEVariables()::NE_VAR_UNDEFINED");
                        continue;
                    }
                }
            } while (b);
        }
    }
    
    public void handleNEIs() throws IOException {
        TelnetIO.access$500().log(Level.FINE, "handleNEIs()");
        if (this.isEnabled(39)) {
            this.readNEVariables();
        }
    }
    
    public void handleNEInfo() throws IOException {
        TelnetIO.access$500().log(Level.FINE, "handleNEInfo()");
        if (this.isEnabled(39)) {
            this.readNEVariables();
        }
    }
    
    public void getTTYPE() throws IOException {
        if (this.isEnabled(24)) {
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 250);
            TelnetIO.access$700(this.this$0, 24);
            TelnetIO.access$700(this.this$0, 1);
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 240);
            this.this$0.flush();
        }
    }
    
    public void negotiateLineMode() throws IOException {
        if (this.isEnabled(34)) {
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 250);
            TelnetIO.access$700(this.this$0, 34);
            TelnetIO.access$700(this.this$0, 1);
            TelnetIO.access$700(this.this$0, 3);
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 240);
            this.WAIT_LM_MODE_ACK = true;
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 250);
            TelnetIO.access$700(this.this$0, 34);
            TelnetIO.access$700(this.this$0, 254);
            TelnetIO.access$700(this.this$0, 2);
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 240);
            this.WAIT_LM_DO_REPLY_FORWARDMASK = true;
            this.this$0.flush();
        }
    }
    
    private void negotiateEnvironment() throws IOException {
        if (this.isEnabled(39)) {
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 250);
            TelnetIO.access$700(this.this$0, 39);
            TelnetIO.access$700(this.this$0, 1);
            TelnetIO.access$700(this.this$0, 0);
            TelnetIO.access$700(this.this$0, 3);
            TelnetIO.access$700(this.this$0, 255);
            TelnetIO.access$700(this.this$0, 240);
            this.WAIT_NE_SEND_REPLY = true;
            this.this$0.flush();
        }
    }
    
    private void skipToSE() throws IOException {
        while (TelnetIO.access$000(this.this$0) != 240) {}
    }
    
    private boolean readTriple(final int[] array) throws IOException {
        array[0] = TelnetIO.access$000(this.this$0);
        array[1] = TelnetIO.access$000(this.this$0);
        if (array[0] == 255 && array[1] == 240) {
            return false;
        }
        array[2] = TelnetIO.access$000(this.this$0);
        return true;
    }
    
    private String readIACSETerminatedString(final int n) throws IOException {
        int n2 = 0;
        final char[] array = new char[n];
        int i = 1;
        do {
            int n3 = TelnetIO.access$000(this.this$0);
            switch (n3) {
                case 255: {
                    n3 = TelnetIO.access$000(this.this$0);
                    if (n3 == 240) {
                        i = 0;
                        break;
                    }
                    break;
                }
                case -1: {
                    return new String("default");
                }
            }
            if (i != 0) {
                final char c = (char)n3;
                if (c == '\n' || c == '\r' || n2 == n) {
                    i = 0;
                }
                else {
                    array[n2++] = c;
                }
            }
        } while (i != 0);
        return new String(array, 0, n2);
    }
    
    private boolean supported(final int n) {
        switch (n) {
            case 1:
            case 3:
            case 24:
            case 31:
            case 39: {
                return true;
            }
            case 34: {
                return TelnetIO.access$600(this.this$0).isLineMode();
            }
            default: {
                return false;
            }
        }
    }
    
    private void sendCommand(final int n, final int n2, final boolean b) throws IOException {
        TelnetIO.access$700(this.this$0, 255);
        TelnetIO.access$700(this.this$0, n);
        TelnetIO.access$700(this.this$0, n2);
        if (n == 253 && b) {
            this.setWait(253, n2, true);
        }
        if (n == 251 && b) {
            this.setWait(251, n2, true);
        }
        this.this$0.flush();
    }
    
    private void enable(final int n) throws IOException {
        switch (n) {
            case 3: {
                if (this.DO_SUPGA) {
                    this.DO_SUPGA = false;
                    break;
                }
                this.DO_SUPGA = true;
                break;
            }
            case 1: {
                if (this.DO_ECHO) {
                    this.DO_ECHO = false;
                    break;
                }
                this.DO_ECHO = true;
                break;
            }
            case 31: {
                if (this.DO_NAWS) {
                    this.DO_NAWS = false;
                    break;
                }
                this.DO_NAWS = true;
                break;
            }
            case 24: {
                if (this.DO_TTYPE) {
                    this.DO_TTYPE = false;
                    break;
                }
                this.DO_TTYPE = true;
                this.getTTYPE();
                break;
            }
            case 34: {
                if (this.DO_LINEMODE) {
                    this.DO_LINEMODE = false;
                    TelnetIO.access$600(this.this$0).setLineMode(false);
                    break;
                }
                this.DO_LINEMODE = true;
                this.negotiateLineMode();
                break;
            }
            case 39: {
                if (this.DO_NEWENV) {
                    this.DO_NEWENV = false;
                    break;
                }
                this.DO_NEWENV = true;
                this.negotiateEnvironment();
                break;
            }
        }
    }
    
    private boolean isEnabled(final int n) {
        switch (n) {
            case 3: {
                return this.DO_SUPGA;
            }
            case 1: {
                return this.DO_ECHO;
            }
            case 31: {
                return this.DO_NAWS;
            }
            case 24: {
                return this.DO_TTYPE;
            }
            case 34: {
                return this.DO_LINEMODE;
            }
            case 39: {
                return this.DO_NEWENV;
            }
            default: {
                return false;
            }
        }
    }
    
    private boolean waitWILLreply(final int n) {
        switch (n) {
            case 3: {
                return this.WAIT_WILL_REPLY_SUPGA;
            }
            case 1: {
                return this.WAIT_WILL_REPLY_ECHO;
            }
            case 31: {
                return this.WAIT_WILL_REPLY_NAWS;
            }
            case 24: {
                return this.WAIT_WILL_REPLY_TTYPE;
            }
            default: {
                return false;
            }
        }
    }
    
    private boolean waitDOreply(final int n) {
        switch (n) {
            case 3: {
                return this.WAIT_DO_REPLY_SUPGA;
            }
            case 1: {
                return this.WAIT_DO_REPLY_ECHO;
            }
            case 31: {
                return this.WAIT_DO_REPLY_NAWS;
            }
            case 24: {
                return this.WAIT_DO_REPLY_TTYPE;
            }
            case 34: {
                return this.WAIT_DO_REPLY_LINEMODE;
            }
            case 39: {
                return this.WAIT_DO_REPLY_NEWENV;
            }
            default: {
                return false;
            }
        }
    }
    
    private void setWait(final int n, final int n2, final boolean b) {
        Label_0209: {
            switch (n) {
                case 253: {
                    switch (n2) {
                        case 3: {
                            this.WAIT_DO_REPLY_SUPGA = b;
                            break;
                        }
                        case 1: {
                            this.WAIT_DO_REPLY_ECHO = b;
                            break;
                        }
                        case 31: {
                            this.WAIT_DO_REPLY_NAWS = b;
                            break;
                        }
                        case 24: {
                            this.WAIT_DO_REPLY_TTYPE = b;
                            break;
                        }
                        case 34: {
                            this.WAIT_DO_REPLY_LINEMODE = b;
                            break;
                        }
                        case 39: {
                            this.WAIT_DO_REPLY_NEWENV = b;
                            break;
                        }
                    }
                    break;
                }
                case 251: {
                    switch (n2) {
                        case 3: {
                            this.WAIT_WILL_REPLY_SUPGA = b;
                            break Label_0209;
                        }
                        case 1: {
                            this.WAIT_WILL_REPLY_ECHO = b;
                            break Label_0209;
                        }
                        case 31: {
                            this.WAIT_WILL_REPLY_NAWS = b;
                            break Label_0209;
                        }
                        case 24: {
                            this.WAIT_WILL_REPLY_TTYPE = b;
                            break Label_0209;
                        }
                    }
                    break;
                }
            }
        }
    }
}
