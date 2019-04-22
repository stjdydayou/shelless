package com.axungu.webshell.ssh;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/7 23:24
 */
public class SessionStorage {

    private static ConcurrentHashMap<String, SSHClient> ONLINE_USERS = new ConcurrentHashMap<>();

    public static void add(String sessionId, SSHClient sshClient) {
        ONLINE_USERS.put(sessionId, sshClient);
    }

    public static void remove(String sessionId) {
        ONLINE_USERS.remove(sessionId);
    }

    public static int size() {
        return ONLINE_USERS.size();
    }

    public static SSHClient get(String sessionId) {
        return ONLINE_USERS.get(sessionId);
    }

}
