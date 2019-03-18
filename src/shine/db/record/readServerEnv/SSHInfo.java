/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.readServerEnv;

/**
 *
 * @author Lvhuihui
 */
public class SSHInfo {

    private String user;
    private String password;
    private String host;
    private int port;

    public SSHInfo(String user, String password, String host, int port) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
