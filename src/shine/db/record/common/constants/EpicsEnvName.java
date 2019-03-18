/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.common.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lvhuihui
 */
public class EpicsEnvName {
    //value:record name
     public static final Map<String,String> ENVLIST=new HashMap<String,String>(){{
          put("GTIM_CUR_SRC","GTIM_CUR_SRC");   //GTIM_CUR_SRC
          put("GTIM_EVT_SRC","GTIM_EVT_SRC");   //GTIM_EVT_SRC
          put("GTIM_HI_SRC","GTIM_HI_SRC");    //GTIM_HI_SRC
          put("EPICS Version","EPICS_VERS");  //EPICS_VERS
          put("EPICS_CA_MAX_ARRAY_BYTES","CA_MAX_ARRAY");  //CA_MAX_ARRAY
          put("EPICS_CA_CONN_TMO","CA_CONN_TIME");   //CA_CONN_TIME
          put("EPICS_CA_MAX_SEARCH_PERIOD","CA_SRCH_TIME");   //CA_SRCH_TIME
          put("EPICS_CA_BEACON_PERIOD","CA_BEAC_TIME");   //CA_BEAC_TIME
          put("EPICS_TIMEZONE","TIMEZONE");   //TIMEZONE
          put("EPICS_TS_NTP_INET","TS_NTP_INET");   //TS_NTP_INET
          put("EPICS_IOC_LOG_PORT","IOC_LOG_PORT");   //IOC_LOG_PORT
          put("EPICS_IOC_LOG_INET","IOC_LOG_INET");   //IOC_LOG_INET
          
    }};
}
