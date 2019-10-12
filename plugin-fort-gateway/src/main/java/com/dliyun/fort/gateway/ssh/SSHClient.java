package com.dliyun.fort.gateway.ssh;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 10:39
 */

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionMonitor;
import ch.ethz.ssh2.Session;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class SSHClient {
    private static final Charset UTF8 = Charset.forName("utf-8");
    private Connection connection;
    private Session session;

    public SSHClient() {
    }

    public static SSHClient connect(String host, int port, String userName, String password, int width, int height) throws Exception {
        SSHClient client = new SSHClient();
        Connection connection = new Connection(host, port);
        connection.connect();
        if (!connection.authenticateWithPassword(userName, password)) {
            return null;
        } else {
            Session session = connection.openSession();
            session.requestPTY("xterm", width, height, 0, 0, (byte[]) null);
            session.startShell();
            client.connection = connection;
            client.session = session;
            return client;
        }
    }

    public static SSHClient connectWithSSHKey(String host, int port, String user, String privateKey, String password, int width, int height) throws Exception {
        SSHClient client = new SSHClient();
        Connection connection = new Connection(host, port);
        connection.connect();
        if (!connection.authenticateWithPublicKey(user, privateKey.toCharArray(), password)) {
            return null;
        } else {
            Session session = connection.openSession();
            session.requestPTY("xterm", width, height, 0, 0, (byte[]) null);
            session.startShell();
            client.connection = connection;
            client.session = session;
            return client;
        }
    }

    public void setWindow(int width, int height) throws IOException {
        this.session.requestWindowChange(width, height, 0, 0);
    }

    public void write(String cmd) throws IOException {
        byte[] bytes = cmd.getBytes(UTF8);
        OutputStream outputStream = this.session.getStdin();
        outputStream.write(bytes, 0, bytes.length);
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void close() {
        if (this.session != null) {
            this.session.close();
        }

        if (this.connection != null) {
            this.connection.close();
        }

    }

    public void addListener(ConnectionMonitor connectionMonitor) {
        this.connection.addConnectionMonitor(connectionMonitor);
    }
}

