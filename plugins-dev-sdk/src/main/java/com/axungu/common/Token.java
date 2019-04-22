package com.axungu.common;


import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * =============================================================================
 * ==== name bit Byte number len max-len
 * -----------------------------------------------------------------------------
 * ---- mac 1 48 6 281,474,976,710,655 mac 2 49 562,949,953,421,311 9
 * 11,694,146,092,834,140 ------ MAC RAN(12) * E15 + mac
 * -----------------------------------------------------------------------------
 * ---- time 0 13,054,752,000 (from 2011.05.16 100ms) time 1 28,833,984,000
 * (from 2061.05.16 100ms) 7 3,142,742,836,021 ------ TIME RAN(32) * E11 + time
 * -----------------------------------------------------------------------------
 * ---- pid 16 2 65535 3 226,980 ------ PID pid
 * -----------------------------------------------------------------------------
 * ---- seq 26 67,108,864 5 844,596,300 ------ SEQ RAN(8) * E8 + seq
 * -----------------------------------------------------------------------------
 * ---- ip 32 4 4,294,967,296 6 51,520,374,361 ------ IP RAN(5) * E10 + ip
 * =============================================================================
 * ==== *
 *
 * @author jtoms
 */
@Slf4j
public class Token {

    private final static char[] BASE61 = "3ibqZmxNpXOjlHIPQVSTUhWCBgFDctuYAzKd1fenyE6957rwMaLJ2kR4vso8G".toCharArray();
    private final static int LEN = BASE61.length;
    private final static int DATA_LENGTH = 30;
    private final static int TOKEN_LENGTH = 33;
    private final static int[] BASE61_MAPPING = {32, 24, 23, 27, 41, 26, 60, 13, 14, 51, 34, 50, 48, 7, 10, 15, 16, 54, 18, 19, 20, 17, 22, 9, 31, 4, 49, 2, 28, 35, 38, 37, 25,
            21, 1, 11, 53, 12, 5, 39, 58, 8, 3, 46, 57, 29, 30, 56, 47, 6, 40, 33, 36, 52, 0, 55, 44, 42, 45, 59, 43};

    private final static long MAC_ADDR = mac();

    private final static int PID = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);

    private final static AtomicInteger SEQ = new AtomicInteger((int) (System.currentTimeMillis() / 1000));

    private final static AtomicInteger[] SEN = {new AtomicInteger((int) (System.nanoTime())), new AtomicInteger((int) (System.nanoTime())),
            new AtomicInteger((int) (System.nanoTime())), new AtomicInteger((int) (System.nanoTime())), new AtomicInteger((int) (System.nanoTime()))};

    private long mac;

    private int pid;

    private int sequence;

    private long tokenTime;

    private String clientIp;


    public Token() {
    }

    public Token(String ipAddr) {
        this.mac = MAC_ADDR;
        this.tokenTime = System.currentTimeMillis() / 100;
        this.pid = PID;
        this.clientIp = ipAddr;
        this.sequence = SEQ.incrementAndGet() & 0x3ffffff;
    }

    public Token(long mac, long tokenTime, int pid, int sequence, String clientIp) {
        super();
        this.mac = mac;
        this.tokenTime = tokenTime;
        this.pid = pid;
        this.sequence = sequence;
        this.clientIp = clientIp;
    }

    public static Token init(String ipAddr) {
        return new Token(ipAddr);
    }

    public static boolean checkToken(String value) {
        if (value == null || value.length() != Token.TOKEN_LENGTH) {
            return false;
        }
        char[] chs = value.toCharArray();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            if (getNumber(chs[i]) < 0) {
                log.warn("value index [" + i + "] is invalid encode character [" + chs[i] + "], token: [" + value + "]");
                return false;
            }
        }
        char[] hash = hash(chs, 0, DATA_LENGTH, TOKEN_LENGTH - DATA_LENGTH);
        for (int i = DATA_LENGTH, j = 0; i < chs.length; i++, j++) {
            if (chs[i] != hash[j]) {
                log.warn("value hash is invalid, value hash: [" + new String(chs, DATA_LENGTH, TOKEN_LENGTH - DATA_LENGTH) + "], calculate hash: [" + new String(hash)
                        + "], token: [" + value + "]");
                return false;
            }
        }
        return true;
    }

    public static Token decodeTokenString(String value) {
        if (log.isDebugEnabled()) {
            log.debug("start check TOKEN_ID: " + value);
        }
        if (!Token.checkToken(value)) {
            log.warn("check token failed");
            return null;
        }
        char[] chs = value.toCharArray();
        int offset = 0;
        long mac = Token.decodeNode(chs, offset, 9, 1000000000000000L);
        if (mac < 0) {
            log.warn("token mac is invalid, mac: [" + new String(chs, 0, 9) + "]token: [" + value + "]");
            return null;
        }
        long tokenTime = Token.decodeNode(chs, (offset += 9), 7, 100000000000L);
        if (tokenTime < 0) {
            log.warn("token time is invalid, tokenTime: [" + new String(chs, offset, 7) + "]token: [" + value + "]");
            return null;
        }
        int pid = (int) Token.decodeNode(chs, (offset += 7), 3, 0);
        if (pid < 0 || pid > 226980) {
            log.warn("token pid is invalid, pid: [" + new String(chs, offset, 3) + "], token: [" + value + "]");
            return null;
        }
        int sequence = (int) Token.decodeNode(chs, (offset += 3), 5, 100000000L);
        if (sequence < 0) {
            log.warn("token sequence is invalid, sequence: [" + new String(chs, offset, 5) + "], token: " + value);
            return null;
        }
        long clientIp = Token.decodeNode(chs, (offset += 5), 6, 10000000000L);
        if (clientIp < 0 || clientIp > 0xffffffffL) {
            log.warn("token clientIp is invalid, clientIp: [" + new String(chs, offset, 6) + "], number client ip: [" + Long.toHexString(clientIp) + "], token: " + value);
            return null;
        }
        return new Token(mac, tokenTime, pid, sequence, Token.toIpAddr(clientIp));
    }

    static char[] hash(char[] chs, int offset, int length, int hashLength) {
        char[] hash = new char[hashLength];
        int p = 0;
        for (int i = offset, k = offset + length; i < k; i++) {
            p = p * 31 + chs[i];
        }
        p &= Integer.MAX_VALUE;
        for (int i = 0; i < hashLength; i++, p /= LEN) {
            hash[i] = BASE61[p % LEN];
        }
        return hash;
    }

    static String encode(long n, int max) {
        char[] chs = new char[max];
        int k = 0;
        while (n > 0) {
            chs[k++] = BASE61[(int) (n % LEN)];
            n /= LEN;
        }
        while (k < max) {
            chs[k++] = BASE61[0];
        }
        return new String(chs);
    }

    static Token parse(String token) {
        if (token == null || token.length() != TOKEN_LENGTH) {
            return null;
        }
        char[] chs = token.toCharArray();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            if (getNumber(chs[i]) < 0) {
                return null;
            }
        }
        char[] hash = hash(chs, 0, DATA_LENGTH, TOKEN_LENGTH - DATA_LENGTH);
        for (int i = DATA_LENGTH, j = 0; i < chs.length; i++, j++) {
            if (chs[i] != hash[j]) {
                log.warn("token: " + token + " hash error");
                return null;
            }
        }
        return parsetTokenString(chs);
    }

    private static int next(int mod, int offset) {
        return (SEN[offset].incrementAndGet() & 0x7fffffff) % mod;
    }

    private static Token parsetTokenString(char[] chs) {
        Token token = new Token();
        int offset = 0;
        token.mac = decodeNode(chs, offset, 9, 1000000000000000L);
        token.tokenTime = (long) decodeNode(chs, (offset += 9), 7, 100000000000L);
        token.pid = (int) decodeNode(chs, (offset += 7), 3, 0);
        token.sequence = (int) decodeNode(chs, (offset += 3), 5, 100000000L);
        token.clientIp = toIpAddr(decodeNode(chs, (offset += 5), 6, 10000000000L));
        return token;
    }

    private static int encodeNode(char[] chs, int ran, long value, int offset, int len) {
        return encodeNode(chs, ran, value, offset, len, 0, 0);
    }

    private static int encodeNode(char[] chs, int ran, long value, int offset, int len, int random, long randomBase) {
        if (random > 0) {
            value = (long) next(random, ran) * randomBase + value;
        }
        for (int i = 0; i < len; i++) {
            chs[offset + i] = BASE61[(int) (value % LEN)];
            value /= LEN;
        }
        return offset + len;
    }

    static long decode(String str) {
        char[] chs = str.toCharArray();
        long p = 0;
        for (int i = chs.length - 1; i >= 0; i--) {
            int k = getNumber(chs[i]);
            if (k < 0) {
                return -1;
            }
            p = p * LEN + k;
        }
        return p;
    }

    public static long decodeNode(char[] chs, int offset, int len, long randomBase) {
        long n = 0;
        for (int i = offset + len - 1; i >= offset; i--) {
            int k = getNumber(chs[i]);
            if (k < 0) {
                return -1;
            }
            n = n * LEN + k;
        }
        return randomBase < 1 ? n : n % randomBase;
    }

    private static long parseIp(String ip) {
        char[] chs = ip.toCharArray();
        long t = 0;
        int n = 0;
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] == '.') {
                t = (t << 8) | (n & 0xff);
                n = 0;
                continue;
            }
            if (chs[i] >= '0' && chs[i] <= '9') {
                n = n * 10 + (chs[i] - '0');
            }
        }
        if (n != 0) {
            t = (t << 8) + (n & 0xff);
        }
        return t & 0xffffffffL;
    }

    static int getNumber(char c) {
        if (c >= 'A' && c <= 'Z') {
            return BASE61_MAPPING[c - 'A'];
        }
        if (c >= 'a' && c <= 'z') {
            return BASE61_MAPPING[c - 'a' + 26];
        }
        if (c >= '1' && c <= '9') {
            return BASE61_MAPPING[c - '1' + 52];
        }
        return -1;
    }

    public static long mac() {
        byte[] bys = null;
        try {
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {
                byte[] mac = e.nextElement().getHardwareAddress();
                if (mac != null && mac.length == 6) {
                    bys = mac;
                    break;
                }
            }
        } catch (SocketException e) {
            log.error("can not get local machine MAC address, generate random 47bit simulate MAC", e);
        }
        if (bys == null) {
            bys = new byte[7];
            new Random().nextBytes(bys);
            bys[0] &= 1;
        }
        long mac = 0;
        for (int i = 0; i < bys.length; i++) {
            mac = mac * 256L + (bys[i] & 0xff);
        }
        mac &= 0x1ffffffffffffL;
        log.info("current MAC number is: " + mac + ", HEX: " + Long.toHexString(mac));
        return mac;
    }

    public static String toIpAddr(long ip) {
        return ((ip >> 24) & 0xff) + "." + ((ip >> 16) & 0xff) + "." + ((ip >> 8) & 0xff) + "." + (ip & 0xff);
    }

    public long getMac() {
        return mac;
    }

    public int getPid() {
        return pid;
    }

    public int getSequence() {
        return sequence;
    }

    public long getTokenTime() {
        return tokenTime * 100L;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String toTokenString() {
        int offset = 0;
        char[] chs = new char[TOKEN_LENGTH];
        int p = 0;
        offset = encodeNode(chs, p++, mac, offset, 9, 12, 1000000000000000L);
        offset = encodeNode(chs, p++, tokenTime, offset, 7, 32, 100000000000L);
        offset = encodeNode(chs, p++, pid, offset, 3);
        offset = encodeNode(chs, p++, sequence, offset, 5, 8, 100000000L);
        offset = encodeNode(chs, p++, parseIp(clientIp), offset, 6, 5, 10000000000L);
        char[] hash = hash(chs, 0, offset, TOKEN_LENGTH - DATA_LENGTH);
        System.arraycopy(hash, 0, chs, offset, hash.length);
        return new String(chs);
    }

    @Override
    public String toString() {
        return "Token [mac=" + mac + ", tokenTime=" + String.format("%tF %<tT", getTokenTime()) + ", pid=" + pid + ", sequence=" + sequence + ", clientIp=" + clientIp + "]";
    }

    public boolean equals(Token token) {
        if (!this.getClientIp().equals(token.getClientIp())) {
            return false;
        }
        if (this.getMac() != token.getMac()) {
            return false;
        }
        if (this.getPid() != token.getPid()) {
            return false;
        }
        if (this.getSequence() != token.getSequence()) {
            return false;
        }
        return true;
    }
}