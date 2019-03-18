/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.readServerEnv;

import shine.db.record.IOCfile2DB.*;
import java.util.ArrayList;
import java.util.Properties;
import org.epics.ca.Context;
import shine.db.record.api.RecordTypeAPI;
import shine.db.record.api.ServerAPI;
import shine.db.record.ca.CAChannelGet;
import shine.db.record.entity.RecordType;
import shine.db.record.entity.Server;

/**
 *
 * @author Lvhuihui
 */
public class Test {
     public static void main(String[] args){
      SSHInfo sshInfo = new SSHInfo("root", "sinap123", "10.40.18.143", 22);
        SSHExecutor ssh = new SSHExecutor(sshInfo);
        OSUtils os = new OSUtils();
        os.setSsh(ssh);
       String s= os.loadAverage();
       System.out.println(s);
         ssh.close();
    }
}
