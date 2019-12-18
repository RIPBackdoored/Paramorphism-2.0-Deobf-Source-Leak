package org.jline.builtins.telnet;

import java.util.*;
import java.util.logging.*;
import java.net.*;
import java.text.*;
import java.io.*;

public abstract class ConnectionManager implements Runnable
{
    private static Logger LOG;
    private final List<Connection> openConnections;
    private Thread thread;
    private ThreadGroup threadGroup;
    private Stack<Connection> closedConnections;
    private ConnectionFilter connectionFilter;
    private int maxConnections;
    private int warningTimeout;
    private int disconnectTimeout;
    private int housekeepingInterval;
    private String loginShell;
    private boolean lineMode;
    private boolean stopping;
    
    public ConnectionManager() {
        super();
        this.lineMode = false;
        this.stopping = false;
        this.threadGroup = new ThreadGroup(this.toString() + "Connections");
        this.closedConnections = new Stack<Connection>();
        this.openConnections = Collections.synchronizedList(new ArrayList<Connection>(100));
    }
    
    public ConnectionManager(final int maxConnections, final int warningTimeout, final int disconnectTimeout, final int housekeepingInterval, final ConnectionFilter connectionFilter, final String loginShell, final boolean lineMode) {
        this();
        this.connectionFilter = connectionFilter;
        this.loginShell = loginShell;
        this.lineMode = lineMode;
        this.maxConnections = maxConnections;
        this.warningTimeout = warningTimeout;
        this.disconnectTimeout = disconnectTimeout;
        this.housekeepingInterval = housekeepingInterval;
    }
    
    public ConnectionFilter getConnectionFilter() {
        return this.connectionFilter;
    }
    
    public void setConnectionFilter(final ConnectionFilter connectionFilter) {
        this.connectionFilter = connectionFilter;
    }
    
    public int openConnectionCount() {
        return this.openConnections.size();
    }
    
    public Connection getConnection(final int n) {
        synchronized (this.openConnections) {
            return this.openConnections.get(n);
        }
    }
    
    public Connection[] getConnectionsByAdddress(final InetAddress inetAddress) {
        final ArrayList<Connection> list = new ArrayList<Connection>();
        synchronized (this.openConnections) {
            for (final Connection connection : this.openConnections) {
                if (connection.getConnectionData().getInetAddress().equals(inetAddress)) {
                    list.add(connection);
                }
            }
        }
        return list.toArray(new Connection[list.size()]);
    }
    
    public void start() {
        (this.thread = new Thread(this)).start();
    }
    
    public void stop() {
        ConnectionManager.LOG.log(Level.FINE, "stop()::" + this.toString());
        this.stopping = true;
        try {
            if (this.thread != null) {
                this.thread.join();
            }
        }
        catch (InterruptedException ex) {
            ConnectionManager.LOG.log(Level.SEVERE, "stop()", ex);
        }
        synchronized (this.openConnections) {
            for (final Connection connection : this.openConnections) {
                try {
                    connection.close();
                }
                catch (Exception ex2) {
                    ConnectionManager.LOG.log(Level.SEVERE, "stop()", ex2);
                }
            }
            this.openConnections.clear();
        }
        ConnectionManager.LOG.log(Level.FINE, "stop():: Stopped " + this.toString());
    }
    
    public void makeConnection(final Socket socket) {
        ConnectionManager.LOG.log(Level.FINE, "makeConnection()::" + socket.toString());
        if (this.connectionFilter == null || this.connectionFilter.isAllowed(socket.getInetAddress())) {
            final ConnectionData connectionData = new ConnectionData(socket, this);
            connectionData.setLoginShell(this.loginShell);
            connectionData.setLineMode(this.lineMode);
            if (this.openConnections.size() < this.maxConnections) {
                final Connection connection = this.createConnection(this.threadGroup, connectionData);
                ConnectionManager.LOG.info(MessageFormat.format("connection #{0,number,integer} made.", this.openConnections.size() + 1));
                synchronized (this.openConnections) {
                    this.openConnections.add(connection);
                }
                connection.start();
            }
        }
        else {
            ConnectionManager.LOG.info("makeConnection():: Active Filter blocked incoming connection.");
            try {
                socket.close();
            }
            catch (IOException ex) {}
        }
    }
    
    protected abstract Connection createConnection(final ThreadGroup p0, final ConnectionData p1);
    
    @Override
    public void run() {
        try {
            do {
                this.cleanupClosed();
                this.checkOpenConnections();
                Thread.sleep(this.housekeepingInterval);
            } while (!this.stopping);
        }
        catch (Exception ex) {
            ConnectionManager.LOG.log(Level.SEVERE, "run()", ex);
        }
        ConnectionManager.LOG.log(Level.FINE, "run():: Ran out " + this.toString());
    }
    
    private void cleanupClosed() {
        if (this.stopping) {
            return;
        }
        while (!this.closedConnections.isEmpty()) {
            final Connection connection = this.closedConnections.pop();
            ConnectionManager.LOG.info("cleanupClosed():: Removing closed connection " + connection.toString());
            synchronized (this.openConnections) {
                this.openConnections.remove(connection);
            }
        }
    }
    
    private void checkOpenConnections() {
        if (this.stopping) {
            return;
        }
        synchronized (this.openConnections) {
            for (final Connection connection : this.openConnections) {
                final ConnectionData connectionData = connection.getConnectionData();
                if (!connection.isActive()) {
                    this.registerClosedConnection(connection);
                }
                else {
                    final long n = System.currentTimeMillis() - connectionData.getLastActivity();
                    if (n <= this.warningTimeout) {
                        continue;
                    }
                    if (n > this.disconnectTimeout + this.warningTimeout) {
                        ConnectionManager.LOG.log(Level.FINE, "checkOpenConnections():" + connection.toString() + " exceeded total timeout.");
                        connection.processConnectionEvent(new ConnectionEvent(connection, ConnectionEvent.Type.CONNECTION_TIMEDOUT));
                    }
                    else {
                        if (connectionData.isWarned()) {
                            continue;
                        }
                        ConnectionManager.LOG.log(Level.FINE, "checkOpenConnections():" + connection.toString() + " exceeded warning timeout.");
                        connectionData.setWarned(true);
                        connection.processConnectionEvent(new ConnectionEvent(connection, ConnectionEvent.Type.CONNECTION_IDLE));
                    }
                }
            }
        }
    }
    
    public void registerClosedConnection(final Connection connection) {
        if (this.stopping) {
            return;
        }
        if (!this.closedConnections.contains(connection)) {
            ConnectionManager.LOG.log(Level.FINE, "registerClosedConnection()::" + connection.toString());
            this.closedConnections.push(connection);
        }
    }
    
    public int getDisconnectTimeout() {
        return this.disconnectTimeout;
    }
    
    public void setDisconnectTimeout(final int disconnectTimeout) {
        this.disconnectTimeout = disconnectTimeout;
    }
    
    public int getHousekeepingInterval() {
        return this.housekeepingInterval;
    }
    
    public void setHousekeepingInterval(final int housekeepingInterval) {
        this.housekeepingInterval = housekeepingInterval;
    }
    
    public boolean isLineMode() {
        return this.lineMode;
    }
    
    public void setLineMode(final boolean lineMode) {
        this.lineMode = lineMode;
    }
    
    public String getLoginShell() {
        return this.loginShell;
    }
    
    public void setLoginShell(final String loginShell) {
        this.loginShell = loginShell;
    }
    
    public int getMaxConnections() {
        return this.maxConnections;
    }
    
    public void setMaxConnections(final int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    public int getWarningTimeout() {
        return this.warningTimeout;
    }
    
    public void setWarningTimeout(final int warningTimeout) {
        this.warningTimeout = warningTimeout;
    }
    
    static {
        ConnectionManager.LOG = Logger.getLogger(ConnectionManager.class.getName());
    }
}
