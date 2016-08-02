package io.fogcloud.fog2;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXModuleAnno;

import org.json.JSONException;

import java.util.Map;

import io.fogcloud.fog2.utils.EasyLinkUtils;
import io.fogcloud.fog2.utils.MDNSUtils;
import io.fogcloud.fog2.utils.MQTTUtils;
import io.fogcloud.helper.CheckTool;
import io.fogcloud.helper.PaMap;

/**
 * Created by Sin on 2016/07/28.
 * Email:88635653@qq.com
 */
public class Fog extends WXModule {
    private Context mContext = null;
    private String instanceId = null;

    private EasyLinkUtils elu = null;
    private MDNSUtils mdnsu = null;
    private MQTTUtils mqttu = null;

    /**
     * Initialize wxmodule of fog
     */
    private void initFog(){
        if(null == mContext){
            mContext = mWXSDKInstance.getContext();
        }
        if(null == instanceId) {
            instanceId = mWXSDKInstance.getInstanceId();
        }
    }

    @WXModuleAnno
    public void fogTest(JSONObject param, String callbackId){
        Log.d("---fogTest---", param.toString());
    }

    /**
     * Get SSID, name of current wifi in android.
     *
     * @param callbackId callback referenece handle
     */
    @WXModuleAnno
    public void getSsid(String callbackId) {
        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();

        if(null == elu)
            elu = new EasyLinkUtils(mContext, instanceId);
        elu.getSsid(callbackId);
    }

    /**
     * Let device connect to wifi router by EasyLink
     *
     * @param parameters  parameters from js
     * @param callbackId callback referenece handle
     */
    @WXModuleAnno
    public void startEasyLink(JSONObject parameters, String callbackId) {

        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();

        if(!parameters.isEmpty()){
            if(null == elu)
                elu = new EasyLinkUtils(mContext, instanceId);
            elu.startEasyLink(parameters, callbackId);
        }else{
            exeCallBack(callbackId, PaMap.getEmptyMessage(), false);
        }
    }

    /**
     * Stop send data to device.
     *
     * @param callbackId callback referenece handle
     */
    @WXModuleAnno
    public void stopEasyLink(String callbackId) {

        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();

        if(null == elu)
            elu = new EasyLinkUtils(mContext, instanceId);
        elu.stopEasyLink(callbackId);
    }

    /**
     * Find devices by multicast DNS.
     *
     * @param servicename Service name of multicast DNS.
     * @param callbackId callback referenece handle
     */
    @WXModuleAnno
    public void startSearchDevices(String servicename, String callbackId){
        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();

        if(null == mdnsu)
            mdnsu = new MDNSUtils(mContext, instanceId);
        mdnsu.startSearchDevices(servicename, callbackId);
    }

    /**
     * Stop multicast DNS.
     *
     * @param callbackId callback referenece handle
     */
    @WXModuleAnno
    public void stopSearchDevices(String callbackId){
        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();

        if(null == mdnsu)
            mdnsu = new MDNSUtils(mContext, instanceId);
        mdnsu.stopSearchDevices(callbackId);
    }

    /**
     * Start MQTT, it means we will send ssid and password to the device.
     *
     * @param parajson mqtt data
     * @param callbackId   callback referenece handle
     */
    @WXModuleAnno
    public void startMqtt(JSONObject parajson, String callbackId){

        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();

        if(!parajson.isEmpty()){
            if(null == mqttu)
                mqttu = new MQTTUtils(mContext, instanceId);
            mqttu.startMqtt(parajson, callbackId);
        }else{
            exeCallBack(callbackId, PaMap.getEmptyMessage(), false);
        }
    }

    /**
     * Stop listen mqttservice.
     *
     * @param callbackId callback referenece handle
     */
    @WXModuleAnno
    public void stopMqtt(String callbackId){
        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();
        if(null == mqttu)
            mqttu = new MQTTUtils(mContext, instanceId);
        mqttu.stopMqtt(callbackId);
    }

    /**
     * Client Subscribe request.
     *
     * @param parajson topic qos within 0, 1, 2 json
     * @param callbackId callback referenece handle
     */
    @WXModuleAnno
    public void subscribe(JSONObject parajson, String callbackId) {
        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();

        String topic = null;
        int qos = parajson.containsKey(PaMap._MQTT_QOS) ? parajson.getInteger(PaMap._MQTT_QOS) : PaMap._MQTT_DEFAULT_QOS;

        if (parajson.containsKey(PaMap._MQTT_TOPIC)) {
            topic = parajson.getString(PaMap._MQTT_TOPIC);
        }

        if (CheckTool.checkPara(topic)) {
            if (null == mqttu)
                mqttu = new MQTTUtils(mContext, instanceId);
            mqttu.subscribe(topic, qos, callbackId);
        } else {
            exeCallBack(callbackId, PaMap.getEmptyMessage(), false);
        }
    }

    /**
     * Client Unsubscribe request.
     *
     * @param parajson topic json
     * @param callbackId callback referenece handle
     */
    @WXModuleAnno
    public void unsubscribe(JSONObject parajson, String callbackId) {
        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();

        String topic = null;
        if (parajson.containsKey(PaMap._MQTT_TOPIC)) {
            topic = parajson.getString(PaMap._MQTT_TOPIC);
        }

        if (CheckTool.checkPara(topic)) {
            if (null == mqttu)
                mqttu = new MQTTUtils(mContext, instanceId);
            mqttu.unsubscribe(topic, callbackId);
        } else {
            exeCallBack(callbackId, PaMap.getEmptyMessage(), false);
        }
    }

    /**
     * Publish message.
     *
     * @param parajson topic
     *                 command
     *                 qos within 0, 1, 2
     *                 Retained messages do not form part of the Session state in the Server,
     *                 they MUST NOT be deleted when the Session ends.
     * @param callbackId callback referenece handle
     */
    @WXModuleAnno
    public void publish(JSONObject parajson, String callbackId) {
        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();

        String topic = null, command = null;
        int qos = parajson.containsKey(PaMap._MQTT_QOS) ? parajson.getInteger(PaMap._MQTT_QOS) : PaMap._MQTT_DEFAULT_QOS;
        boolean retained = parajson.containsKey(PaMap._MQTT_RETAINED) ? parajson.getBoolean(PaMap._MQTT_RETAINED) : PaMap._MQTT_DEFAULT_RETAINED;

        if (parajson.containsKey(PaMap._MQTT_TOPIC)) {
            topic = parajson.getString(PaMap._MQTT_TOPIC);
        }
        if (parajson.containsKey(PaMap._MQTT_COMMAND)) {
            command = parajson.getString(PaMap._MQTT_COMMAND);
        }

        if (CheckTool.checkPara(topic, command)) {
            if (null == mqttu)
                mqttu = new MQTTUtils(mContext, instanceId);
            mqttu.publish(topic, command, qos, retained, callbackId);
        } else {
            exeCallBack(callbackId, PaMap.getEmptyMessage(), false);
        }
    }

    /**
     * Call back to js.
     *
     * @param callbackId callback referenece handle
     * @param message callback data
     * @param keepAlive  if keep callback instance alive for later use
     */
    private void exeCallBack(String callbackId, Map<String, Object> message, boolean keepAlive) {
        WXBridgeManager.getInstance().callback(instanceId, callbackId, message, keepAlive);
    }
}
