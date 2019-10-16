package com.dliyun.platform.common.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UpgradeSqlInfo {

    private String fileName;
    private String checksum;
    private List<String> sqls = new ArrayList<>();

    public UpgradeSqlInfo(String fileName) {
        try {
            this.fileName = fileName;

            InputStream is = this.getClass().getClassLoader().getResourceAsStream(String.format("db.sql/%s.sql", this.fileName));
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                String tempString = null;
                int flag = 0;

                StringBuilder sb = new StringBuilder();
                while ((tempString = in.readLine()) != null) {
                    if (StringUtils.isBlank(tempString) || tempString.startsWith("--")) {
                        continue;
                    }
                    if (";".equals(tempString.substring(tempString.length() - 1))) {
                        if (flag == 1) {
                            sb.append(tempString);
                            sqls.add(sb.toString());
                            sb.delete(0, sb.length());
                            flag = 0;
                        } else {
                            sqls.add(tempString);
                        }
                    } else {
                        flag = 1;
                        sb.append(tempString);
                    }
                }
            }
        } catch (Exception e) {
            log.error("build upgrade sql info error", e);
        }
    }

    public List<String> getSqls() {
        return sqls;
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

}
