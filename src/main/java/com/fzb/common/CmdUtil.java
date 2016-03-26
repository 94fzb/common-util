package com.fzb.common;

import java.io.File;
import java.io.IOException;

public class CmdUtil {

    public static int findPidByPort(int port) throws IOException, InterruptedException {
        String cmdStr = "";
        if (File.separatorChar != '\\') {
            cmdStr = "netstat -anp";
        } else {
            cmdStr = "netstat -atu";
        }
        String content = sendCmd(cmdStr);
        if (content != null && content.length() > 0 && content.split("\n").length > 1) {
            String cons[] = content.split("\n");
            for (int i = 2; i < cons.length; i++) {
                content = cons[i];
                String nContext = "";
                //format
                for (int j = 0; j < content.length(); j++) {
                    if (j > 0) {
                        if (content.charAt(j) == ' ' && content.charAt(j) == content.charAt(j - 1)) {
                            continue;
                        }
                    }
                    nContext += content.charAt(j);
                }

                String strings[] = nContext.trim().split(" ");
                int flag = 1;
                if (RuntimeMessage.getSystemRm() == SystemType.LINUX) {
                    flag = 3;
                }
                if (strings.length <= flag) {
                    continue;
                }
                String[] ipPort = strings[flag].split(":");
                if (ipPort.length >= 2) {
                    Integer tempPort = Integer.parseInt(ipPort[ipPort.length - 1]);
                    if (port == tempPort) {
                        //
                        System.out.println(nContext);
                        String procMsg = strings[6];
                        if (RuntimeMessage.getSystemRm() == SystemType.LINUX && procMsg.contains("/")) {
                            return Integer.parseInt(procMsg.substring(0, strings[6].indexOf("/")));
                        }
                    }
                }
            }
        }
        return -1;
    }

    public static void killProcByPid(int pid) {
        sendCmd("kill -9 ", pid + "");
    }

    public static void killProcByPort(int port) {
        try {
            long start = System.currentTimeMillis();
            int pid = findPidByPort(port);
            if (pid != -1) {
                killProcByPid(pid);
            }
            System.out.println(System.currentTimeMillis() - start);
        } catch (IOException | InterruptedException e) {
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
        killProcByPort(80);
    }

}
