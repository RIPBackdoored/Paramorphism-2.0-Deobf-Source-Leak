package org.jline.builtins.telnet;

import org.jline.builtins.*;
import java.io.*;
import org.jline.terminal.*;
import java.util.*;

public class Telnet
{
    public static final String[] functions;
    private static final int defaultPort = 2019;
    private final Terminal terminal;
    private final ShellProvider provider;
    private PortListener portListener;
    private int port;
    private String ip;
    
    public Telnet(final Terminal terminal, final ShellProvider provider) {
        super();
        this.terminal = terminal;
        this.provider = provider;
    }
    
    public void telnetd(final String[] array) throws Exception {
        final Options parse = Options.compile(new String[] { "telnetd - start simple telnet server", "Usage: telnetd [-i ip] [-p port] start | stop | status", "  -i --ip=INTERFACE        listen interface (default=127.0.0.1)", "  -p --port=PORT           listen port (default=2019)", "  -? --help                show help" }).parse(array, true);
        final List<String> args = parse.args();
        if (parse.isSet("help") || args.isEmpty()) {
            throw new Options.HelpException(parse.usage());
        }
        final String s = args.get(0);
        if ("start".equals(s)) {
            if (this.portListener != null) {
                throw new IllegalStateException("telnetd is already running on port " + this.port);
            }
            this.ip = parse.get("ip");
            this.port = parse.getNumber("port");
            this.start();
            this.status();
        }
        else if ("stop".equals(s)) {
            if (this.portListener == null) {
                throw new IllegalStateException("telnetd is not running.");
            }
            this.stop();
        }
        else {
            if (!"status".equals(s)) {
                throw parse.usageError("bad command: " + s);
            }
            this.status();
        }
    }
    
    private void status() {
        if (this.portListener != null) {
            System.out.println("telnetd is running on " + this.ip + ":" + this.port);
        }
        else {
            System.out.println("telnetd is not running.");
        }
    }
    
    private void start() throws IOException {
        (this.portListener = new PortListener("gogo", this.port, 10)).setConnectionManager(new ConnectionManager(this, 1000, 300000, 300000, 60000, null, null, false) {
            final Telnet this$0;
            
            Telnet$1(final Telnet this$0, final int n, final int n2, final int n3, final int n4, final ConnectionFilter connectionFilter, final String s, final boolean b) {
                this.this$0 = this$0;
                super(n, n2, n3, n4, connectionFilter, s, b);
            }
            
            @Override
            protected Connection createConnection(final ThreadGroup threadGroup, final ConnectionData connectionData) {
                return new Connection(this, threadGroup, connectionData) {
                    TelnetIO telnetIO;
                    final Telnet$1 this$1;
                    
                    Telnet$1$1(final Telnet$1 this$1, final ThreadGroup threadGroup, final ConnectionData connectionData) {
                        this.this$1 = this$1;
                        super(threadGroup, connectionData);
                    }
                    
                    @Override
                    protected void doRun() throws Exception {
                        (this.telnetIO = new TelnetIO()).setConnection(this);
                        this.telnetIO.initIO();
                        final Terminal build = TerminalBuilder.builder().type(this.getConnectionData().getNegotiatedTerminalType().toLowerCase()).streams(new InputStream(this) {
                            final Telnet$1$1 this$2;
                            
                            Telnet$1$1$1(final Telnet$1$1 this$2) {
                                this.this$2 = this$2;
                                super();
                            }
                            
                            @Override
                            public int read() throws IOException {
                                return this.this$2.telnetIO.read();
                            }
                            
                            @Override
                            public int read(final byte[] array, final int n, final int n2) throws IOException {
                                final int read = this.read();
                                if (read >= 0) {
                                    array[n] = (byte)read;
                                    return 1;
                                }
                                return -1;
                            }
                        }, new PrintStream(new OutputStream(this) {
                            final Telnet$1$1 this$2;
                            
                            Telnet$1$1$2(final Telnet$1$1 this$2) {
                                this.this$2 = this$2;
                                super();
                            }
                            
                            @Override
                            public void write(final int n) throws IOException {
                                this.this$2.telnetIO.write(n);
                            }
                            
                            @Override
                            public void flush() throws IOException {
                                this.this$2.telnetIO.flush();
                            }
                        })).system(false).name("telnet").build();
                        build.setSize(new Size(this.getConnectionData().getTerminalColumns(), this.getConnectionData().getTerminalRows()));
                        build.setAttributes(Telnet.access$000(this.this$1.this$0).getAttributes());
                        this.addConnectionListener(new ConnectionListener(this, build) {
                            final Terminal val$terminal;
                            final Telnet$1$1 this$2;
                            
                            Telnet$1$1$3(final Telnet$1$1 this$2, final Terminal val$terminal) {
                                this.this$2 = this$2;
                                this.val$terminal = val$terminal;
                                super();
                            }
                            
                            @Override
                            public void connectionTerminalGeometryChanged(final ConnectionEvent connectionEvent) {
                                this.val$terminal.setSize(new Size(this.this$2.getConnectionData().getTerminalColumns(), this.this$2.getConnectionData().getTerminalRows()));
                                this.val$terminal.raise(Terminal.Signal.WINCH);
                            }
                        });
                        try {
                            Telnet.access$100(this.this$1.this$0).shell(build, this.getConnectionData().getEnvironment());
                        }
                        finally {
                            this.close();
                        }
                    }
                    
                    @Override
                    protected void doClose() throws Exception {
                        this.telnetIO.closeOutput();
                        this.telnetIO.closeInput();
                    }
                };
            }
        });
        this.portListener.start();
    }
    
    private void stop() throws IOException {
        this.portListener.stop();
        this.portListener = null;
    }
    
    static Terminal access$000(final Telnet telnet) {
        return telnet.terminal;
    }
    
    static ShellProvider access$100(final Telnet telnet) {
        return telnet.provider;
    }
    
    static {
        functions = new String[] { "telnetd" };
    }
    
    public interface ShellProvider
    {
        void shell(final Terminal p0, final Map<String, String> p1);
    }
}
