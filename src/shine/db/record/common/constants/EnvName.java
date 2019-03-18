/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.common.constants;

import java.util.ArrayList;

/**
 *
 * @author Lvhuihui
 */
public class EnvName {
    

    public static final ArrayList<String> ENVLIST=new ArrayList<String>(){{
          add("HOSTNAME");
          add("EPICS_BASE");
          add("EPICS_EXTENSIONS");
          add("EPICS_HOST_ARCH");
          add("EPICS_CA_ADDR_LIST");
          add("EPICS_CA_AUTO_ADDR_LIST");
    }};

}
