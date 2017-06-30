package com.shane.me.shanedemo.net.diagnosis;
/*
 * @author: Xian Jingxiong
 * @date: 2017/06/30
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSTask extends NetDiagnosisTask {

    private String host;

    public DNSTask(String host){
        this.host = host;
    }

    @Override
    public void execInternal(StringBuilder result) {
        markStart();
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            result.append(inetAddress.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        result.append("\n [End] parse dns: " + host + " in " + getTimeLast() + " ms\n");
    }



}
