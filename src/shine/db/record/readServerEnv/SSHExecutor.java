/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.readServerEnv;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import static java.lang.String.format;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import shine.db.record.common.constants.ConnectSet;

/**
 *
 * @author Lvhuihui
 */
public class SSHExecutor {

    private JSch jsch = null;
    private Session session = null;

     public SSHExecutor(SSHInfo sshInfo) {
        try {
            jsch = new JSch();
            session = jsch.getSession(sshInfo.getUser(), sshInfo.getHost(), sshInfo.getPort());
            session.setPassword(sshInfo.getPassword());
            session.setUserInfo(new MyUserInfo());
            session.setConfig("userauth.gssapi-with-mic", "no");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(ConnectSet.SSH_SESSION_TIMEOUT);
        } catch (JSchException ex) {
            Logger.getLogger(SSHExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long shell(String cmd, String outputFileName) throws JSchException, IOException, InterruptedException {
        long start = System.currentTimeMillis();
        Channel channel = session.openChannel("shell");
        PipedInputStream pipeIn = new PipedInputStream();
        PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);
        FileOutputStream fileOut = new FileOutputStream(outputFileName, true);
        channel.setInputStream(pipeIn);
        channel.setOutputStream(fileOut);
        channel.connect(ConnectSet.SSH_CHANNEL_TIMEOUT);

        pipeOut.write(cmd.getBytes());
        Thread.sleep(ConnectSet.SSH_INTERVAL);
        pipeOut.close();
        pipeIn.close();
        fileOut.close();
        channel.disconnect();
        return System.currentTimeMillis() - start;
    }

    public String exec(String cmd) throws IOException, JSchException, InterruptedException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setCommand(cmd);
        channelExec.setInputStream(null);
        channelExec.setErrStream(System.err);
        InputStream in = channelExec.getInputStream();
        channelExec.connect();

        int res = -1;
        StringBuffer buf = new StringBuffer(1024);
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }
                buf.append(new String(tmp, 0, i));
            }
            if (channelExec.isClosed()) {
                res = channelExec.getExitStatus();
                //System.out.println(format("Exit-status: %d", res));
                break;
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
        //System.out.println(buf.toString());
        channelExec.disconnect();
        return buf.toString();
    }

    public Session getSession() {
        return session;
    }

    public void close() {
        getSession().disconnect();
    }

}
