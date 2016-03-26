package com.fzb.common;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CmdUtil2 {

    public static List<ProcessMsg> getProcess() {
        String printStr = sendCmd("tasklist", new String[]{"/NH", "/FO CSV"});
        System.out.println(printStr);
        String procStr[] = printStr.split("\n");
        List<ProcessMsg> procList = new ArrayList<ProcessMsg>();
        for (String string : procStr) {
            String ps[] = string.split(",\"");
            ProcessMsg pm = new ProcessMsg();
            pm.setProcName(ps[0].replace("\"", ""));
            pm.setPid(Integer.parseInt(ps[1].replace("\"", "")));
            pm.setSessionName(ps[2].replace("\"", ""));
            pm.setProcMem(Long.parseLong(ps[4].replace("\r", "").replace(" K", "").replace(",", "").replace("\"", "")));
            procList.add(pm);
        }
        return procList;
    }

    public static boolean isExistPros(String procName) {
        List<ProcessMsg> tempProcList = getProcess();
        for (ProcessMsg processMsg : tempProcList) {
            if (processMsg.getProcName().contains(procName)) {
                return true;
            }
        }
        return false;

    }

    public static void killIfExistPros(String procName) {
        while (isExistPros(procName)) {
            List<ProcessMsg> tempProcList = getProcess();
            for (ProcessMsg processMsg : tempProcList) {
                if (processMsg.getProcName().startsWith(procName)) {
                    System.out.println(sendCmd("taskkill", new String[]{"/F", "/PID " + processMsg.getPid()}));
                }
            }
        }
    }

    public static void killProcByPid(int pid) {
        sendCmd("kill -9 ", pid + "");
    }

    public static void killProcByPort(int port) {
        try {
            long start = System.currentTimeMillis();
            int pid = CmdUtil.findPidByPort(port);
            if (pid != -1) {
                killProcByPid(pid);
            }
            System.out.println(System.currentTimeMillis() - start);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String sendCmd(String cmd, String... args) {
        if (args != null) {
            cmd += " ";
            for (String str : args) {
                cmd += str + " ";
            }
        }
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(cmd);
            return new String(IOUtil.getByteByInputStream(pr.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //getProcess();
        long start = System.currentTimeMillis();
        System.out.println(isExistPros("dsaf"));
        System.out.println(System.currentTimeMillis() - start);

        killIfExistPros("vm");

        List<ProcessMsg> procs = getOrderByMem(getProcess());
        for (ProcessMsg processMsg : procs) {
            System.out.println(processMsg);
        }
    }

    public static List<ProcessMsg> getOrderByPid(List<ProcessMsg> processMsgs) {
        Integer[] ords = new Integer[processMsgs.size()];
        Map<Integer, ProcessMsg> maps = new HashMap<>();
        for (int i = 0; i < processMsgs.size(); i++) {
            ords[i] = processMsgs.get(i).getPid();
            maps.put(processMsgs.get(i).getPid(), processMsgs.get(i));
        }

        for (int i = 0; i < ords.length; i++) {
            for (int j = 0; j < ords.length - 1 - i; j++) {
                if (ords[j] > ords[j + 1]) {
                    //如果后一个数小于前一个数交换
                    int tmp = ords[j];
                    ords[j] = ords[j + 1];
                    ords[j + 1] = tmp;
                }
            }
        }
        processMsgs.clear();
        for (Integer integer : ords) {
            processMsgs.add(maps.get(integer));
        }
        return processMsgs;
    }

    public static List<ProcessMsg> getOrderByMem(List<ProcessMsg> processMsgs) {
        Long[] ords = new Long[processMsgs.size()];
        Map<Long, ProcessMsg> maps = new HashMap<Long, ProcessMsg>();
        for (int i = 0; i < processMsgs.size(); i++) {
            ords[i] = processMsgs.get(i).getProcMem();
            maps.put(processMsgs.get(i).getProcMem(), processMsgs.get(i));
        }

        for (int i = 0; i < ords.length; i++) {
            for (int j = 0; j < ords.length - 1 - i; j++) {
                if (ords[j] > ords[j + 1]) {   //如果后一个数小于前一个数交换
                    Long tmp = ords[j];
                    ords[j] = ords[j + 1];
                    ords[j + 1] = tmp;
                }
            }
        }
        processMsgs.clear();
        for (Long lon : ords) {
            processMsgs.add(maps.get(lon));
        }
        return processMsgs;
    }

}