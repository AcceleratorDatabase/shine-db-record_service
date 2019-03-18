/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.readServerEnv;

import com.jcraft.jsch.UserInfo;

/**
 *
 * @author Lvhuihui
 */
public class MyUserInfo implements UserInfo {

    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean promptPassword(String string) {
        return false;
    }

    @Override
    public boolean promptPassphrase(String string) {
        return false;
    }

    @Override
    public boolean promptYesNo(String s) {
        System.out.println(s);
        System.out.println("true");
        return true;
    }

    @Override
    public void showMessage(String string) {
    }

}
