package io.fogcloud.fog2.utils;

import android.content.Context;

import com.taobao.weex.bridge.WXBridgeManager;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import io.fogcloud.fog_mdns.api.MDNS;
import io.fogcloud.fog_mdns.helper.SearchDeviceCallBack;
import io.fogcloud.helper.CheckTool;
import io.fogcloud.helper.PaMap;

/**
 * Created by Sin on 2016/07/29.
 * Email:88635653@qq.com
 */
public class MDNSUtils {
    private MDNS mdns = null;
    private String instanceId;

    /**
     * Initialize MDNS.
     *
     * @param context Interface to global information about an application environment.
     * @param instanceId id of instance
     */
    public MDNSUtils(Context context, String instanceId) {
        this.mdns = new MDNS(context);
        this.instanceId = instanceId;
    }

    /**
     * Find devices by multicast DNS.
     *
     * @param servicename Service name of multicast DNS.
     * @param callbackId callback referenece handle
     */
    public void startSearchDevices(String servicename, final String callbackId){
        if (null != mdns) {
            if(!CheckTool.checkPara(servicename))
                servicename = "_easylink._tcp.local.";

            mdns.startSearchDevices(servicename, new SearchDeviceCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    exeCallBack(callbackId, getResult(message, code), true);
                }

                @Override
                public void onFailure(int code, String message) {
                    exeCallBack(callbackId, getResult(message, code), false);
                }

                @Override
                public void onDevicesFind(JSONArray deviceStatus) {
                    WXBridgeManager.getInstance().callback(instanceId, callbackId, getResult(deviceStatus.toString(), PaMap._MDNS_ON_DEV_FIND), true);
                }
            });
        }
    }

    /**
     * Stop multicast DNS.
     *
     * @param callbackId callback referenece handle
     */
    public void stopSearchDevices(final String callbackId){
        if (null != mdns){
            mdns.stopSearchDevices(new SearchDeviceCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    exeCallBack(callbackId, getResult(message, code), false);
                }

                @Override
                public void onFailure(int code, String message) {
                    exeCallBack(callbackId, getResult(message, code), false);
                }
            });
        }
    }

    /**
     * Return the Map from code and message.
     *
     * @param code    code of onSuccess/onFailure
     * @param message message of onSuccess/onFailure
     * @return String map
     */
    private Map<String, Object> getResult(String message, int ...code) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(code.length > 0)
            result.put("code", code[0]);
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
