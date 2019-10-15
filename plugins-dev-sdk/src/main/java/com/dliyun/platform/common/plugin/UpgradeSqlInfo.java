package com.dliyun.platform.common.plugin;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class UpgradeSqlInfo {

    private String fileName;
    private String checksum;
    private String sql;

    public UpgradeSqlInfo(String fileName) {
        try {
            this.fileName = fileName;

            InputStream is = this.getClass().getClassLoader().getResourceAsStream(String.format("db.sql/%s.sql", this.fileName));
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = in.readLine()) != null) {
                    buffer.append("\n").append(line);
                }
                this.sql = buffer.toString();
            }
        } catch (Exception e) {
            log.error("build upgrade sql info error", e);
        }
    }


    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public boolean equals(UpgradeSqlInfo obj) {
        return (this.checksum == obj.checksum);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
