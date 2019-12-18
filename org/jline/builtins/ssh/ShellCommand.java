package org.jline.builtins.ssh;

import org.apache.sshd.server.command.*;
import java.util.function.*;
import org.apache.sshd.server.session.*;
import org.apache.sshd.server.*;
import java.util.*;
import java.util.logging.*;
import java.io.*;

public class ShellCommand implements Command, SessionAware
{
    private static final Logger LOGGER;
    private final Consumer<Ssh.ExecuteParams> execute;
    private final String command;
    private InputStream in;
    private OutputStream out;
    private OutputStream err;
    private ExitCallback callback;
    private ServerSession session;
    private Environment env;
    
    public ShellCommand(final Consumer<Ssh.ExecuteParams> execute, final String command) {
        super();
        this.execute = execute;
        this.command = command;
    }
    
    public void setInputStream(final InputStream in) {
        this.in = in;
    }
    
    public void setOutputStream(final OutputStream out) {
        this.out = out;
    }
    
    public void setErrorStream(final OutputStream err) {
        this.err = err;
    }
    
    public void setExitCallback(final ExitCallback callback) {
        this.callback = callback;
    }
    
    public void setSession(final ServerSession session) {
        this.session = session;
    }
    
    public void start(final Environment env) throws IOException {
        this.env = env;
        new Thread(this::run).start();
    }
    
    private void run() {
        int n = 0;
        try {
            this.execute.accept(new Ssh.ExecuteParams(this.command, this.env.getEnv(), this.in, this.out, this.err));
        }
        catch (RuntimeException ex) {
            n = 1;
            ShellCommand.LOGGER.log(Level.SEVERE, "Unable to start shell", ex);
            try {
                this.err.write(((ex.getCause() != null) ? ex.getCause() : ex).toString().getBytes());
                this.err.flush();
            }
            catch (IOException ex2) {}
        }
        finally {
            ShellFactoryImpl.close(this.in, this.out, this.err);
            this.callback.onExit(n);
        }
    }
    
    public void destroy() {
    }
    
    static {
        LOGGER = Logger.getLogger(ShellCommand.class.getName());
    }
}
