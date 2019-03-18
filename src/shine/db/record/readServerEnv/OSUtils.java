/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.readServerEnv;

import com.jcraft.jsch.JSchException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import shine.db.record.common.constants.EnvName;

/**
 *
 * @author Lvhuihui
 */
public class OSUtils {

    private SSHExecutor ssh;

    public SSHExecutor getSsh() {
        return ssh;
    }

    public void setSsh(SSHExecutor ssh) {
        this.ssh = ssh;
    }

    //系统启动时间Startup Time
    public Date startupTime() {
        try {
            //   SSHInfo sshInfo = new SSHInfo("root", "sinap123", "10.40.18.43", 22);
            //  SSHExecutor ssh = new SSHExecutor(sshInfo);
            String s = null;
            try {
                s = ssh.exec("date -d \"$(awk -F. '{print $1}' /proc/uptime) second ago\" +\"%Y-%m-%d %H:%M:%S\" ");
                // System.out.println(s);
            } catch (IOException ex) {
                Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSchException ex) {
                Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // ssh.close();
            return sdf.parse(s);
        } catch (ParseException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //OS version
    public String OSvern() {
        String s = null;
        try {
            s = ssh.exec("cat /etc/redhat-release");
        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSchException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    //获取内存信息，返回Map，mem_total，mem_free，buffers，cached
    public Map<String, String> memoryinfo() {
        // SSHInfo sshInfo = new SSHInfo("root", "sinap123", "10.40.18.43", 22);
        // SSHExecutor ssh = new SSHExecutor(sshInfo);
        String s = null;
        try {
            s = ssh.exec("cat /proc/meminfo");
            //  System.out.println(s);
        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSchException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        // ssh.close();
        Map<String, String> map = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        int i = 0;
        try {
            while ((line = br.readLine()) != null) {
                // System.out.println(line + "*******" + i++);
                if (line.startsWith("MemTotal")) {
                    map.put("mem_total", line.substring(line.indexOf(":") + 1).replace(" ", ""));
                    i++;
                    //System.out.println(MemTotal);
                } else if (line.startsWith("MemFree")) {
                    map.put("mem_free", line.substring(line.indexOf(":") + 1).replace(" ", ""));
                    i++;
                } else if (line.startsWith("Buffers")) {
                    map.put("buffers", line.substring(line.indexOf(":") + 1).replace(" ", ""));
                    i++;
                } else if (line.startsWith("Cached")) {
                    map.put("cached", line.substring(line.indexOf(":") + 1).replace(" ", ""));
                    i++;
                }
                if (i > 3) {
                    break;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return map;
    }

    /**
     * 功能：获取Linux系统cpu使用率,CPU Load
     *
     */
    public double cpuUsage() {

        Map<?, ?> map1 = this.cpuinfo();
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<?, ?> map2 = this.cpuinfo();

        long user1 = Long.parseLong(map1.get("user").toString());
        long nice1 = Long.parseLong(map1.get("nice").toString());
        long system1 = Long.parseLong(map1.get("system").toString());
        long idle1 = Long.parseLong(map1.get("idle").toString());
        long iowait1 = Long.parseLong(map1.get("iowait").toString());
        long irq1 = Long.parseLong(map1.get("irq").toString());
        long softirq1 = Long.parseLong(map1.get("softirq").toString());
        long stealstolen1 = Long.parseLong(map1.get("stealstolen").toString());

        long user2 = Long.parseLong(map2.get("user").toString());
        long nice2 = Long.parseLong(map2.get("nice").toString());
        long system2 = Long.parseLong(map2.get("system").toString());
        long idle2 = Long.parseLong(map2.get("idle").toString());
        long iowait2 = Long.parseLong(map2.get("iowait").toString());
        long irq2 = Long.parseLong(map2.get("irq").toString());
        long softirq2 = Long.parseLong(map2.get("softirq").toString());
        long stealstolen2 = Long.parseLong(map2.get("stealstolen").toString());

        long total1 = user1 + system1 + nice1 + idle1 + iowait1 + irq1 + softirq1 + stealstolen1;
        long total2 = user2 + system2 + nice2 + idle2 + iowait2 + irq2 + softirq2 + stealstolen2;
        float total = total2 - total1;

        // long totalIdle1 = user1 + nice1 + system1 + idle1;
        // long totalIdle2 = user2 + nice2 + system2 + idle2;
        float totalidle = idle2 - idle1;
        float cpusage = (total - totalidle) / total;
        BigDecimal bg = new BigDecimal(cpusage);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //CPU型号和数量,返回map，cpu_model，cpu_num
    public Map<String, String> cpumodel() {
        // SSHInfo sshInfo = new SSHInfo("root", "sinap123", "10.40.18.43", 22);
        // SSHExecutor ssh = new SSHExecutor(sshInfo);
        Map<String, String> map = new HashMap<String, String>();
        String s = null;
        try {
            s = ssh.exec("cat /proc/cpuinfo |grep 'model name' | uniq");
            map.put("cpu_model", s.substring(s.indexOf(":") + 1).replace(" ", ""));
            s = ssh.exec("cat /proc/cpuinfo |grep 'cpu cores' | uniq");
            map.put("cpu_num", s.substring(s.indexOf(":") + 1).replace(" ", ""));
        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSchException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        // ssh.close();
        return map;
    }

    //文件描述符，返回map，file_free,file_max
    public Map<String, Integer> fileDesc() {
        //   SSHInfo sshInfo = new SSHInfo("root", "sinap123", "10.40.18.43", 22);
        //   SSHExecutor ssh = new SSHExecutor(sshInfo);
        Map<String, Integer> map = new HashMap<String, Integer>();
        String s = null;
        try {
            s = ssh.exec("cat /proc/sys/fs/file-nr");
            //System.out.println(s);
        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSchException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringTokenizer tokenizer = new StringTokenizer(s);
        List<String> temp = new ArrayList<String>();
        while (tokenizer.hasMoreElements()) {
            String value = tokenizer.nextToken();
            // System.out.println(value);
            temp.add(value);
        }
        map.put("file_free", Integer.parseInt(temp.get(2)) - Integer.parseInt(temp.get(0)));
        map.put("file_max", Integer.valueOf(temp.get(2)));
        //  ssh.close();
        return map;
    }

    /**
     * 功能：CPU使用信息
     *
     */
    public Map<?, ?> cpuinfo() {
        // SSHInfo sshInfo = new SSHInfo("root", "sinap123", "10.40.18.43", 22);
        // SSHExecutor ssh = new SSHExecutor(sshInfo);
        String s = null;
        try {
            s = ssh.exec("cat /proc/stat");
            //System.out.println(s);
        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSchException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        //ssh.close();
        Map<String, Object> map = new HashMap<String, Object>();

        // String line = s;
        // System.out.println(s);
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        StringBuffer strbuf = new StringBuffer();
        strbuf = strbuf.append("cpu").append(" ");
        try {
            int i = 0;
            while ((line = br.readLine()) != null) {
                //System.out.println(line + "*********" + (i++));

                if (line.startsWith(strbuf.toString())) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<String>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        // System.out.println(value);
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                }
                return map;
            }
        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return map;
    }

    /**
     * 功能：内存使用率
     *
     * @return
     */
    public double memoryUsage() {
        // SSHInfo sshInfo = new SSHInfo("root", "sinap123", "10.40.18.43", 22);
        // SSHExecutor ssh = new SSHExecutor(sshInfo);
        String s = null;
        try {
            s = ssh.exec("cat /proc/meminfo");
            //  System.out.println(s);
        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSchException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  ssh.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;

        String MemTotal = null, MemFree = null, Buffers = null, Cached = null;
        int i = 0;
        try {
            while ((line = br.readLine()) != null) {
                // System.out.println(line + "*******" + i++);
                if (line.startsWith("MemTotal")) {
                    MemTotal = line.substring(line.indexOf(":") + 1).replace(" ", "").replace("kB", "").trim();
                    i++;
                    //System.out.println(MemTotal);
                } else if (line.startsWith("MemFree")) {
                    MemFree = line.substring(line.indexOf(":") + 1).replace(" ", "").replace("kB", "").trim();
                    i++;
                } else if (line.startsWith("Buffers")) {
                    Buffers = line.substring(line.indexOf(":") + 1).replace(" ", "").replace("kB", "").trim();
                    i++;
                } else if (line.startsWith("Cached")) {
                    Cached = line.substring(line.indexOf(":") + 1).replace(" ", "").replace("kB", "").trim();
                    i++;
                }
                if (i > 3) {
                    break;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        long memTotal = Long.parseLong(MemTotal);

        long memFree = Long.parseLong(MemFree);

        long memused = memTotal - memFree;
        long buffers = Long.parseLong(Buffers);
        long cached = Long.parseLong(Cached);

        double usage = (double) (memused - buffers - cached) / memTotal;
        BigDecimal bg = new BigDecimal(usage);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 功能:CPU Load Average,分别是1、5、15分钟内进程队列中的平均进程数量
     *
     * @return
     */
    public String loadAverage() {
        String s = null;
        try {
            s = ssh.exec("uptime");
            //  System.out.println(s);
        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSchException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                int i = line.indexOf("load average");
                s = line.substring(i + 13);
            }

        } catch (IOException ex) {
            Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;

    }

//机器环境变量
    public Map<String, String> serverEnv() {
        String s = null;
        Map<String, String> map = new HashMap<String, String>();
        Iterator<String> it = EnvName.ENVLIST.iterator();
        while (it.hasNext()) {
            try {
                String name = it.next();
                s = ssh.exec("echo $" + name);
                map.put(name, s);
            } catch (IOException ex) {
                Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSchException ex) {
                Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(OSUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return map;
    }

}
