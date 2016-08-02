package io.fogcloud.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * The map for switch.
 * Created by Sin on 2016/07/28.
 * Email:88635653@qq.com
 */
public class PaMap {
    //The following are EasyLinkParams
    public final static String _EL_SSID  = "ssid";
    public final static String _EL_PSW  = "password";
    public final static String _EL_WORK  = "worktime";
    public final static String _EL_SLEEP  = "sleeptime";
    public final static String _EL_EXTRA  = "extradata";
    public final static String _EL_RC4  = "rc4key";

    public final static int _EL_STOP  = 4000;
    public final static int _EL_SUCCESS  = 0;

    //The following are mDNSParams
//    public final static int _MDNS_ON_DEV_FIND  = 200;

    //The following are mDNSParams
    public final static String _MQTT_HOST  = "host";
    public final static String _MQTT_PORT  = "port";
    public final static String _MQTT_USERNAME  = "username";
    public final static String _MQTT_PSW  = "password";
    public final static String _MQTT_TOPIC  = "topic";
    public final static String _MQTT_CLIENTID  = "clientid";
    public final static String _MQTT_ISENCRYPT  = "isencrypt";

    public final static String _MQTT_QOS  = "qos";
    public final static String _MQTT_COMMAND  = "command";
    public final static String _MQTT_RETAINED  = "retained";
    public final static int _MQTT_DEFAULT_QOS  = 0;
    public final static boolean _MQTT_DEFAULT_RETAINED  = false;

    //Parameter is null
    public final static int _EMPTY_CODE  = 5001;
    public final static String _EMPTY_MSG  = "invalid param";

    /**
     * there is something wrong with parameters
     * @return callback result of empty parameter
     */
    public final static Map<String, Object> getEmptyMessage(){
        Map<String, Object> message = new HashMap<String, Object>();
        message.put("code", _EMPTY_CODE);
        message.put("message", _EMPTY_MSG);
        return message;
    }

}
