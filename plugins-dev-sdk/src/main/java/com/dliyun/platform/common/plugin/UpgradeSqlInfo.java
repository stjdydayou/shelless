package com.dliyun.platform.common.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UpgradeSqlInfo {

    private String version;
    private String script;
    private String fileMd5;
    private List<String> sqls = new ArrayList<>();

    public UpgradeSqlInfo(String fileName) {
        try {
            this.script = fileName;

            String[] array = this.script.split("__");
            this.version = array[0].replace("V", "");


            InputStream is = this.getClass().getClassLoader().getResourceAsStream(String.format("db.sql/%s.sql", this.script));
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                String tempString = null;
                int flag = 0;

                StringBuilder fullString = new StringBuilder();

                StringBuilder sb = new StringBuilder();
                while ((tempString = in.readLine()) != null) {
                    fullString.append(tempString);
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
                in.close();
                isr.close();
                this.fileMd5 = DigestUtils.md5Hex(fullString.toString());
            }
        } catch (Exception e) {
            log.error("build upgrade sql info error", e);
        }
    }

    public String getScript() {
        return script;
    }

    public List<String> getSqls() {
        return sqls;
    }

    public String getVersion() {
        return version;
    }

    public String getFileMd5() {
        return fileMd5;
    }
}
