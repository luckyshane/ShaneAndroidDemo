package com.shane.me.shanedemo.net.diagnosis;
/*
 * @author: Xian Jingxiong
 * @date: 2017/06/30
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingTask extends NetDiagnosisTask {
    private String host;
    private int count;


    public PingTask(String host, int count) {
        this.host = host;
        this.count = count;
    }

    public PingTask(String host) {
        this(host, 4);
    }

    @Override
    public void execInternal(StringBuilder result) {
        if (result == null) return;
        markStart();
        execPing(result);
        result.append("[End] ping " + host + " in " + getTimeLast() + " ms\n");
    }

    private void execPing(StringBuilder sb) {
        String cmd = "/system/bin/ping -c ";
        Process process = null;
        BufferedReader reader = null;
        try {
            process = Runtime.getRuntime().exec(
                    cmd + count + " " + host);
            reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
    }




}
