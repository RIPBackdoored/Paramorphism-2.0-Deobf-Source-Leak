package org.jline.builtins.telnet;

import java.util.logging.*;
import java.io.*;
import java.text.*;
import java.net.*;

public class PortListener implements Runnable
{
    private static final Logger LOG;
    private static final String logmsg = "Listening to Port {0,number,integer} with a connectivity queue size of {1,number,integer}.";
    private String name;
    private int port;
    private int floodProtection;
    private ServerSocket serverSocket;
    private Thread thread;
    private ConnectionManager connectionManager;
    private boolean stopping;
    private boolean available;
    
    public PortListener(final String name, final int port, final int floodProtection) {
        super();
        this.serverSocket = null;
        this.stopping = false;
        this.name = name;
        this.available = false;
        this.port = port;
        this.floodProtection = floodProtection;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isAvailable() {
        return this.available;
    }
    
    public void setAvailable(final boolean available) {
        this.available = available;
    }
    
    public void start() {
        PortListener.LOG.log(Level.FINE, "start()");
        (this.thread = new Thread(this)).start();
        this.available = true;
    }
    
    public void stop() {
        PortListener.LOG.log(Level.FINE, "stop()::" + this.toString());
        this.stopping = true;
        this.available = false;
        this.connectionManager.stop();
        try {
            this.serverSocket.close();
        }
        catch (IOException ex) {
            PortListener.LOG.log(Level.SEVERE, "stop()", ex);
        }
        try {
            this.thread.join();
        }
        catch (InterruptedException ex2) {
            PortListener.LOG.log(Level.SEVERE, "stop()", ex2);
        }
        PortListener.LOG.info("stop()::Stopped " + this.toString());
    }
    
    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(this.port, this.floodProtection);
            PortListener.LOG.info(MessageFormat.format("Listening to Port {0,number,integer} with a connectivity queue size of {1,number,integer}.", this.port, this.floodProtection));
            do {
                try {
                    final Socket accept = this.serverSocket.accept();
                    if (this.available) {
                        this.connectionManager.makeConnection(accept);
                    }
                    else {
                        accept.close();
                    }
                }
                catch (SocketException ex) {
                    if (this.stopping) {
                        PortListener.LOG.log(Level.FINE, "run(): ServerSocket closed by stop()");
                    }
                    else {
                        PortListener.LOG.log(Level.SEVERE, "run()", ex);
                    }
                }
            } while (!this.stopping);
        }
        catch (IOException ex2) {
            PortListener.LOG.log(Level.SEVERE, "run()", ex2);
        }
        PortListener.LOG.log(Level.FINE, "run(): returning.");
    }
    
    public ConnectionManager getConnectionManager() {
        return this.connectionManager;
    }
    
    public void setConnectionManager(final ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    
    static {
        LOG = Logger.getLogger(PortListener.class.getName());
    }
}
