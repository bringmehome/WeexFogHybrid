package io.fogcloud.fog2.utils;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.bridge.WXBridgeManager;

import java.util.HashMap;
import java.util.Map;

import io.fogcloud.fog_mqtt.api.MQTT;
import io.fogcloud.fog_mqtt.helper.ListenDeviceCallBack;
import io.fogcloud.fog_mqtt.helper.ListenDeviceParams;
import io.fogcloud.helper.PaMap;

/**
 * Created by Sin on 2016/08/02.
 * Email:88635653@qq.com
 */
public class MQTTUtils {
    private MQTT mqtt = null;
    private String instanceId;

    /**
     * Initialize MQTT..
     *
     * @param context Interface to global information about an application environment.
     * @param instanceId id of instance
     */
    public MQTTUtils(Context context, String instanceId) {
        this.mqtt = new MQTT(context);
        this.instanceId = instanceId;
    }

    /**
     * Start MQTT, it means we will send ssid and password to the device.
     *
     * @param parajson mqtt data
     * @param callbackId   callback referenece handle
     */
    public void startMqtt(JSONObject parajson, final String callbackId){
        ListenDeviceParams ldp = jsonToParams(parajson);

        mqtt.startMqtt(ldp, new ListenDeviceCallBack() {
            @Override
            public void onSuccess(int code, String message) {
                exeCallBack(callbackId, getResult(code, message), true);
            }

            @Override
            public void onFailure(int code, String message) {
                exeCallBack(callbackId, getResult(code, message), false);
            }

            @Override
            public void onDeviceStatusReceived(int code, String messages) {
                exeCallBack(callbackId, getResult(code, messages), true);
            }
        });
    }

    /**
     * Creates a new ListenDeviceParams with name/value mappings from the JSON.
     *
     * @param parajson JSON string
     * @return ListenDeviceParams
     */
    private ListenDeviceParams jsonToParams(JSONObject parajson){
        ListenDeviceParams ldp = new ListenDeviceParams();

        if (parajson.containsKey(PaMap._MQTT_HOST))
            ldp.host = parajson.getString(PaMap._MQTT_HOST);
        if (parajson.containsKey(PaMap._MQTT_PORT))
            ldp.port = parajson.getString(PaMap._MQTT_PORT);
        if (parajson.containsKey(PaMap._MQTT_USERNAME))
            ldp.userName = parajson.getString(PaMap._MQTT_USERNAME);
        if (parajson.containsKey(PaMap._MQTT_PSW))
            ldp.passWord = parajson.getString(PaMap._MQTT_PSW);
        if (parajson.containsKey(PaMap._MQTT_TOPIC))
            ldp.topic = parajson.getString(PaMap._MQTT_TOPIC);
        if (parajson.containsKey(PaMap._MQTT_CLIENTID))
            ldp.clientID = parajson.getString(PaMap._MQTT_CLIENTID);
        if (parajson.containsKey(PaMap._MQTT_ISENCRYPT))
            ldp.isencrypt = parajson.getBoolean(PaMap._MQTT_ISENCRYPT);

        return ldp;
    }

    /**
     * Return the Map from code and message.
     *
     * @param code    code of onSuccess/onFailure
     * @param message message of onSuccess/onFailure
     * @return String map
     */
    private Map<String, Object> getResult(int code, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("message", message);
        return result;
    }

    /**
     * Call back to js.
     *
     * @param callbackId callback referenece handle
     * @param message    callback data
     * @param keepAlive  if keep callback instance alive for later use
     */
    private void exeCallBack(String callbackId, Map<String, Object> message, boolean keepAlive) {
        WXBridgeManager.getInstance().callback(instanceId, callbackId, message, keepAlive);
    }
}
