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
 *
 * QOS: description
 * If a subscribing Client has been granted maximum QoS 1 for a particular Topic Filter,
 * then a QoS 0 Application Message matching the filter is delivered to the Client at QoS 0.
 * This means that at most one copy of the message is received by the Client.
 * On the other hand a QoS 2 Message published to the same topic is downgraded by the Server
 * to QoS 1 for delivery to the Client, so that Client might receive duplicate copies of the Message.
 *
 * If the subscribing Client has been granted maximum QoS 0,
 * then an Application Message originally published as QoS 2
 * might get lost on the hop to the Client, but the Server should never send a duplicate of that Message.
 * A QoS 1 Message published to the same topic might either get lost or duplicated on its transmission to that Client.
 *
 * Subscribing to a Topic Filter at QoS 2 is equivalent to saying
 * "I would like to receive Messages matching this filter at the QoS with which they were published".
 * This means a publisher is responsible for determining the maximum QoS a Message can be delivered at,
 * but a subscriber is able to require that the Server downgrades the QoS to one more suitable for its usage.
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
        if (null != mqtt && null != ldp) {
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
    }

    /**
     * Stop listen mqttservice.
     *
     * @param callbackId callback referenece handle
     */
    public void stopMqtt(final String callbackId){

        if (null != mqtt) {
            mqtt.stopMqtt(new ListenDeviceCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }

                @Override
                public void onFailure(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }
            });
        }
    }

    /**
     * Client Subscribe request.
     *
     * @param topic topic
     * @param qos qos within 0, 1, 2
     * @param callbackId callback referenece handle
     */
    public void subscribe(String topic, int qos, final String callbackId) {

        if (null != mqtt) {
            mqtt.subscribe(topic, qos, new ListenDeviceCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }

                @Override
                public void onFailure(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }
            });
        }
    }

    /**
     * Client Unsubscribe request.
     *
     * @param topic topic
     * @param callbackId callback referenece handle
     */
    public void unsubscribe(String topic, final String callbackId) {

        if (null != mqtt) {
            mqtt.unsubscribe(topic,new ListenDeviceCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }

                @Override
                public void onFailure(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }
            });
        }
    }

    /**
     * Publish message.
     *
     * @param topic topic
     * @param command command
     * @param qos qos within 0, 1, 2
     * @param retained Retained messages do not form part of the Session state in the Server,
     *                 they MUST NOT be deleted when the Session ends.
     * @param callbackId callback referenece handle
     */
    public void publish(String topic,
                        String command,
                        int qos,
                        boolean retained,
                        final String callbackId) {

        if (null != mqtt) {
            mqtt.publish(topic, command, qos, retained, new ListenDeviceCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }

                @Override
                public void onFailure(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }
            });
        }
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
