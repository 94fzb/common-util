package com.fzb.common;

public class ProcessMsg {

    private int pid;
    private long procMem;
    private String sessionName;
    private String procName;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public long getProcMem() {
        return procMem;
    }

    public void setProcMem(long procMem) {
        this.procMem = procMem;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    @Override
    public String toString() {
        return "ProcessMsg [pid=" + pid + ", procMem=" + procMem
                + ", sessionName=" + sessionName + ", procName=" + procName
                + "]";
    }

}
