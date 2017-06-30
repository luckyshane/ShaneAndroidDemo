package com.shane.me.shanedemo.net.diagnosis;
/*
 * @author: Xian Jingxiong
 * @date: 2017/06/30
 */

import java.util.ArrayList;
import java.util.List;

public abstract class NetDiagnosisTask implements Task {

    private List<NetDiagnosisTask> nextTasks;

    protected long startTimestamp;


    public NetDiagnosisTask addNextTask(NetDiagnosisTask nextTask) {
        if (nextTasks == null) {
            this.nextTasks = new ArrayList<>();
        }
        this.nextTasks.add(nextTask);
        return this;
    }

    @Override
    public void exec(StringBuilder result) {
        execInternal(result);

        if (nextTasks != null && !nextTasks.isEmpty()) {
            for (NetDiagnosisTask task : nextTasks) {
                task.exec(result);
            }
        }
    }

    public abstract void execInternal(StringBuilder result);

    protected void markStart() {
        startTimestamp = System.currentTimeMillis();
    }

    protected long getTimeLast() {
        return System.currentTimeMillis() - startTimestamp;
    }



}
